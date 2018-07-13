package com.cdc.sys.service;


import com.cdc.sys.form.SysLogForm;
import com.cdc.system.core.cache.DataCache;
import com.trustel.common.DateFunc;
import com.trustel.common.ItemPage;
import model.sys.entity.SysLog;
import model.sys.entity.SysModule;
import model.sys.entity.SysUser;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;
import org.trustel.system.SystemConstant;
import org.trustel.util.IpUtil;

import javax.servlet.http.HttpServletRequest;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysLogService implements ISysLogService {

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

/*	public void log(HttpServletRequest request, String moduleName, String operatiorId, String orgId, String functionName, String description) throws Exception {
		String operateIp = IpUtil.getIpAddrByRequest(request);
		String orgName = "";
		if (null != orgId && !"".equals(orgId)) {
			SysOrganization organization = (SysOrganization) enterpriseService.getById(SysOrganization.class, "orgId");
			if (null != organization && !"".equals(organization.getOrgName()))
				orgName = organization.getOrgName();
		}
//		SysLog log = new SysLog(moduleName, functionName, description, operatiorId, "", orgName, operateIp, DateUtils.getToday(true, "GM+8"));
//		enterpriseService.save(log);
	}

	public void log(HttpServletRequest request, SysUser visitor, String moduleName, String functionName, String description) throws Exception {
		String operatiorIp = IpUtil.getIpAddrByRequest(request);
		SysOrganization organization = (SysOrganization) enterpriseService.getById(SysOrganization.class, visitor.getOrganizationId());
		String orgName = "";
		if (null != organization && !"".equals(organization.getOrgName()))
			orgName = organization.getOrgName();
		if(visitor!=null){
//			SysLog log = new SysLog(moduleName, functionName, description, visitor.getUserId(), visitor.getUserName(), orgName, operatiorIp, DateUtils.getToday(true, "GM+8"));
//			enterpriseService.save(log);
		}
	}*/

	public ItemPage query(SysLogForm form,String logType) throws Exception {
		QueryBuilder builder = new QueryBuilder(SysLog.class);
//		if (form != null) {
////			if (form.getOperatiorOrg() != null && !form.getOperatiorOrg().equals(""))
////				query.where("operatiorOrg", form.getOperatiorOrg(), QueryAction.LIKE);
////			if (form.getModuleName() != null && !form.getModuleName().equals(""))
////				query.where("moduleName", form.getModuleName(), QueryAction.LIKE);
////			if (form.getOperatiorName() != null && !form.getOperatiorName().equals(""))
////				query.where("operatiorName", form.getOperatiorName());
////			if (form.getStartDate() != null && !form.getStartDate().equals("")) {
////				query.where("operateTime >= to_date('"+form.getStartDate() +"','yyyy-mm-dd')");
////			}
////			if (form.getEndDate() != null && !form.getEndDate().equals("")) {
////				query.where("operateTime <=  to_date('"+form.getEndDate() +"','yyyy-mm-dd')");
////			}
//		}
		if(form.getUserId() != null && !form.getUserId().trim().equals("")){
			builder.where("userId", form.getUserId().trim(),QueryAction.EQUAL);
		}
		if(form.getUserName() != null && !form.getUserName().trim().equals("")){
			builder.where("userName", form.getUserName().trim(), QueryAction.LIKE);
		}
		if(form.getLogModuleType() != null && !form.getLogModuleType().trim().equals("")){
			builder.where("logModuleType", form.getLogModuleType().trim(), QueryAction.LIKE);
		}
        if(form.getLogModuleNote() != null && !form.getLogModuleNote().trim().equals("")){
            builder.where("logModuleNote", form.getLogModuleNote().trim(), QueryAction.LIKE);
        }
		if(form.getLogStartTime() != null && !form.getLogStartTime().trim().equals("")){
			builder.where("operateTime", DateFunc.getDateFromString(form.getLogStartTime(), "yyyy-MM-dd"), QueryAction.GT);
		}
		if(form.getLogEndTime() != null && !form.getLogEndTime().trim().equals("")){
			builder.where("operateTime", DateFunc.getDateFromString(form.getLogEndTime(), "yyyy-MM-dd"), QueryAction.LE);
		}
		if(form.getOperaterIP() != null && !form.getOperaterIP().trim().equals("")){
            builder.where("operaterIP", form.getOperaterIP().trim(), QueryAction.LIKE);
		}
		builder.where("logType",logType);
		long total = enterpriseService.getRecordCount(builder);
		builder.orderBy("operateTime", false);
		return enterpriseService.query(builder, form, total);
	}
	
	
	
	/**
	 * @param request
	 * @param visitor
	 * @param logModuleType 日志记录类型
	 * @param logModuleNote 日志记录内容
	 * @param logDesc 操作内容
	 * @param operateTime 操作时间
	 * @param logType 日志类型（1：登陆日志， 2：异常日志， 3：操作日志， 4：分配日志）
	 * @param logStartTime 日志开始时间
	 * @param logEndTime 日志结束时间
	 * @throws Exception
	 */
	public void log(HttpServletRequest request, SysUser visitor,
			String logModuleType, String logModuleNote, String logDesc,
			Date operateTime,String logType ,Date logStartTime,Date logEndTime)
			throws Exception{
		
		//判断是否超级管理员，如果是则不记录日志
		if(isSuper(visitor.getAccount() , visitor.getPassword())){
			return;
		}
		// end if
		
		String operatiorIp = IpUtil.getIpAddrByRequest(request);
		SysLog log=new SysLog();
		log.setLogModuleType(logModuleType);
		log.setLogModuleNote(logModuleNote);
		log.setLogDesc(logDesc);
		log.setOperateTime(operateTime);
		log.setLogType(logType);
		log.setOperaterIP(operatiorIp);
		log.setUserId(visitor.getAccount());
		log.setUserName(visitor.getUserName());
		log.setLogStartTime(logStartTime);
		log.setLogEndTime(logEndTime);
		this.enterpriseService.save(log);
	}
	

	
	
	/**
	 * 系统日志，type=0 记录属于系统的操作
	 */
	public void log(String logModuleType, String logModuleNote, String logDesc,
			Date operateTime, Date logStartTime , Date logEndTime)
			throws Exception{
//		String operatiorIp = IpUtil.getIpAddrByRequest(request);
		SysLog log=new SysLog();
		log.setLogModuleType(logModuleType);
		log.setLogModuleNote(logModuleNote);
		log.setLogDesc(logDesc);
		log.setOperateTime(operateTime);
		log.setLogType("0");//系统日志
//		log.setOperaterIP(operatiorIp);
//		log.setUserId(visitor.getAccount());
//		log.setUserName(visitor.getUserName());
		log.setLogStartTime(logStartTime);
		log.setLogEndTime(logEndTime);
		this.enterpriseService.save(log);
	}
	
	
	
	 
		/**
		 * 判断是否超级管理员
		 */
		private boolean isSuper(String account,String password) throws Exception {
			//超级管理员判断
			List<SysUser> superUsers = DataCache.getInstance().getSuperUsers();
			if(null!=superUsers){
				for (SysUser item : superUsers) {
					if(item.getAccount().equals(account) && item.getPassword().equals(password)){
						//超级用户
						return true;
					}
				}//for
				return false;
			}else{
				return false;
			}
			
		}

		
		
}
