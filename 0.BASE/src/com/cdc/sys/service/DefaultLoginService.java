package com.cdc.sys.service;

import com.cdc.system.core.cache.DataCache;
import com.trustel.common.DateUtil;
import com.trustel.common.MD5Helper;
import model.sys.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;
import org.trustel.system.SystemConstant;
import org.trustel.util.IpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class DefaultLoginService implements IDefaultLoginService {

	private IEnterpriseService enterpriseService;

    public void setEnterpriseService(IEnterpriseService service) {
		this.enterpriseService = service;
	}

	public SysUser checkLogin(String account, String pwd) throws Exception {
		if (account == null || account.trim().equals("") || pwd == null || pwd.trim().equals(""))
			return null;
		// 通过md5加密密码
		MD5Helper md5 = new MD5Helper();
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("account", account, QueryAction.EQUAL);
		query.where("password", md5.getDoubleMD5ofStr(pwd), QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		if (list != null && list.size() == 1) {
			SysUser loginedUser = (SysUser) list.get(0);
			map = loadUserPrivilges(loginedUser.getUserId());
			loginedUser.setPrivileges(map.get("privileges"));			
			//放当前用户url
//			loginedUser.setUrlCode(map.get("urlCode"));
			return loginedUser;
		}
		return null;
	}
	/**
	 * 根据登录帐号查询系统用户信息
	 */
	public SysUser checkUserByAccount(String account)throws Exception{
		if (account == null || account.trim().equals("")){
			return null;
		}
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("account", account, QueryAction.EQUAL);
		List<SysUser> list = (List<SysUser>)enterpriseService.query(query, 0);
		if(list==null || list.size()<=0){
			return null;
		}
		return list.get(0);
	}
	/**
	 * 加载用户的权限
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
    public Map<String,List<String>>  loadUserPrivilges(String userId) throws Exception {
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		List<String> privileges = new ArrayList<String>();
		List<String> urlCode = new ArrayList<String>();
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.select(" roleId ");
		query.where("userId", userId);
		@SuppressWarnings("unchecked")
		List<String> roleIds = (List<String>) enterpriseService.query(query, 0);

		Map<String, List<String>> sysPrivilges = DataCache.getInstance().getSysPrivilges();
		for (String roleId : roleIds) {
			List<String> temps = sysPrivilges.get(getRoleName(roleId));
			if (temps != null)
				for (String temp : temps)
					if (!privileges.contains(temp)){
						privileges.add(temp);
					}
		}
		map.put("privileges", privileges);
		
		return map;
	}

	private String getRoleName(String roleId) {
		return ((SysRole) enterpriseService.getById(SysRole.class, roleId)).getRoleName();
	}

	/**
	 * 清除用户的session信息
	 */
	public SysUser cleanSession(HttpServletRequest request) throws Exception {
		// 移除用户的session信息
		HttpSession session = request.getSession();
		SysUser visitor = (SysUser) session.getAttribute(SystemConstant.SESSION_VISITOR);
		if (session != null) {
			Enumeration<?> names = session.getAttributeNames();
			while (names.hasMoreElements()) {
				session.removeAttribute((String) names.nextElement());
			}
		}
		return visitor;
	}
	
	/**
	 * 检查当天登录错误次数
	 */
	public boolean checkLoginFailure(HttpServletRequest request, String account) throws Exception{
		boolean numberOut = false;
		Date startDate = new Date();
		Date endDate = new Date();
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		endDate = calendar.getTime();
		QueryBuilder query = new QueryBuilder(SysLoginFailure.class);
		query.where("account", account.trim(), QueryAction.EQUAL);
		query.where("loginTime", startDate, QueryAction.GT);
		query.where("loginTime", endDate, QueryAction.LE);
		List list = enterpriseService.query(query, 0);
		if(list.size() >= 5){
			numberOut = true;
		}
		return numberOut;
	}

    /**
     * 检查距离上次修改密码是否超过3个月
     * @param request
     * @param account 用户账号
     * @return
     */
    @Override
    public boolean checkLastUpdatePassword(HttpServletRequest request, String account){

        Boolean sessionIsExpire = (Boolean)request.getSession().getAttribute(SystemConstant.ATTRIBUTE_FORCE_UPDATE_PASSWORD);

        sessionIsExpire = sessionIsExpire == null ? true : sessionIsExpire;

        if (sessionIsExpire) {
            QueryBuilder query = new QueryBuilder(SysLog.class);

            query.select("operateTime");

            query.where("logModuleNote","修改密码");
            query.where("userId",account);

            //按照操作时间倒序排序
            query.orderBy("operateTime",false);

            List result = enterpriseService.query(query,1);
            if (result != null && !result.isEmpty()) {
                Date lastUpdateDate = (Date)result.get(0);
                Calendar calendar = Calendar.getInstance();
                //1个月
                calendar.add(Calendar.MONTH,-1);

                sessionIsExpire = calendar.getTime().after(lastUpdateDate);
                request.getSession().setAttribute(SystemConstant.ATTRIBUTE_FORCE_UPDATE_PASSWORD,sessionIsExpire);
            }
        }

        return sessionIsExpire;
    }
	
	/**
	 * 保存系统登录错误日志
	 */
	public void saveSysLoginFailure(HttpServletRequest request, String account) throws Exception{
		if (account == null || account.trim().equals("") ){
			return;
		}
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("account", account.trim(), QueryAction.EQUAL);
		List list = enterpriseService.query(query, 0);
		if(list.size() != 0){
			//保存用户登录失败日志
			String operatiorIp = IpUtil.getIpAddrByRequest(request);
			SysLog log = new SysLog();
			log.setLogModuleType("用户登录");
			log.setLogModuleNote("登录");
			log.setLogDesc("登录失败!");
			log.setOperateTime(new Date());
			log.setLogType("1");
			log.setOperaterIP(operatiorIp);
			log.setUserId(account);
			log.setUserName(account);
			log.setLogStartTime(new Date());
			log.setLogEndTime(null);
			this.enterpriseService.save(log);
			
			SysLoginFailure sysLoginFailure = new SysLoginFailure();
			sysLoginFailure.setAccount(account.trim());
			sysLoginFailure.setLoginTime(new Date());
			enterpriseService.save(sysLoginFailure);
			
		}
	}

    /**
	 * 更新用户冻结状态
	 */
	public void updateFreezeStatus(String account) throws Exception{
		if (account == null || account.trim().equals("") ){
			return;
		}
		Date startDate = new Date();
		Date endDate = new Date();
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		endDate = calendar.getTime();
		QueryBuilder query = new QueryBuilder(SysLoginFailure.class);
		query.where("account", account.trim(), QueryAction.EQUAL);
		query.where("loginTime", startDate, QueryAction.GT);
		query.where("loginTime", endDate, QueryAction.LE);
		List list = enterpriseService.query(query, 0);
		if(list.size() >= 5){
			QueryBuilder builder = new QueryBuilder(SysUser.class);
			builder.where("account", account);
            builder.where("freezeStatus","1",QueryAction.NOEQUAL);
			List userList = enterpriseService.query(builder, 0);
            if (userList != null && !userList.isEmpty()) {
                SysUser sysUser = (SysUser)userList.get(0);
                sysUser.setFreezeStatus("1");
                enterpriseService.updateObject(sysUser);

                //下发短信提醒
//                smsService.sendAccountDisable(sysUser);
            }

		}
	}
	
	/**
	 * 查询今天登陆错误次数
	 */
	public int querySysLoginFailureTimes(String account){
		Date startDate = new Date();
		Date endDate = new Date();
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		endDate = calendar.getTime();
		QueryBuilder query = new QueryBuilder(SysLoginFailure.class);
		query.where("account", account.trim(), QueryAction.EQUAL);
		query.where("loginTime", startDate, QueryAction.GT);
		query.where("loginTime", endDate, QueryAction.LE);
		List list = enterpriseService.query(query, 0);
		return list.size();
	}
	
	/**
	 * 清除登录错误记录
	 */
	public void clearLoginFailure(String account) throws Exception{
		QueryBuilder query = new QueryBuilder(SysLoginFailure.class);
		query.where("account", account.trim(), QueryAction.EQUAL);
		enterpriseService.delete(query);
	}

	public boolean checkGetCodeTimes(String account) {
		boolean numberOut = false;
		Date startDate = new Date();
		Date endDate = new Date();
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		endDate = calendar.getTime();
		QueryBuilder query = new QueryBuilder(SysValidCodeFailuer.class);
		query.where("account", account.trim(), QueryAction.EQUAL);
		query.where("loginTime", startDate, QueryAction.GT);
		query.where("loginTime", endDate, QueryAction.LE);
		List list = enterpriseService.query(query, 0);
		if(list.size() >= 5){
			numberOut = true;
		}
		return numberOut;
	}

	public void saveSysGetCodeTimes(String account) {
		SysValidCodeFailuer codeFailuer = new SysValidCodeFailuer();
		codeFailuer.setAccount(account);
		codeFailuer.setLoginTime(new Date());
		enterpriseService.save(codeFailuer);
		
	}

	public int queryGetCodeTimes(String account) {
		Date startDate = new Date();
		Date endDate = new Date();
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		endDate = calendar.getTime();
		QueryBuilder query = new QueryBuilder(SysValidCodeFailuer.class);
		query.where("account", account.trim(), QueryAction.EQUAL);
		query.where("loginTime", startDate, QueryAction.GT);
		query.where("loginTime", endDate, QueryAction.LE);
		List list = enterpriseService.query(query, 0);
		if(list!=null && list.size()>0){
			return list.size();
		}else{
			return 0;
		}
		
	}

    /**
     * 查询指定账户的时间段内的操作日志条数
     * @param account
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public long neverUseBetweenTime(String account, Date startDate, Date endDate,String moduleNote){
        QueryBuilder query = new QueryBuilder(SysLog.class);

        if (StringUtils.isNotBlank(account)) {
            query.where("userId", account.trim(), QueryAction.EQUAL);
        }

        if (startDate != null) {
            query.where("operateTime", startDate, QueryAction.GT);
        }

        if (endDate != null) {
            query.where("operateTime", endDate, QueryAction.LT);
        }

        if (StringUtils.isNotBlank(moduleNote)) {
            query.where("logModuleNote","%"+moduleNote+"%",QueryAction.LIKE);
        }

        query.where("logModuleType","系统后台",QueryAction.NOEQUAL);
        return enterpriseService.getRecordCount(query);
    }

    /**
     * 重置登录失败计数器
     * @param userId
     */
    @Override
    public void resetLoginFailCounter(String userId){
        Session session = enterpriseService.getSessions();
        session.createQuery("UPDATE SysUser t SET t.loginFailCount=0 WHERE t.userId='"+userId+"'").executeUpdate();
        session.flush();
    }

    @Override
    public void finalLoginFailCounter(String userId){
        Session session = enterpriseService.getSessions();
        Query query = session.createQuery("UPDATE SysUser t SET t.loginFailCount=5,t.lastLoginFailDate=? WHERE t.userId='"+userId+"'");
        query.setDate(0, DateTime.now().toDate());
        query.executeUpdate();
        session.flush();
    }

    /**
     * 修改用户状态
     * @param userId
     * @param freezeStatus
     */
    @Override
    public void updateUserFreeStatus(String userId, String freezeStatus){
        Session session = enterpriseService.getSessions();
        session.createQuery("UPDATE SysUser t SET t.freezeStatus='"+freezeStatus+"' WHERE t.userId='"+userId+"'").executeUpdate();
    }


}
