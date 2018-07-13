package com.cdc.sys.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysLoginFailure;
import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import model.sys.entity.SysUserTemp;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;
import org.trustel.util.BatchUserUtil;
import org.trustel.util.DateUtils;

import com.cdc.sys.form.DicFailImportObject;
import com.cdc.sys.form.SysUserForm;
import com.trustel.common.ItemPage;
import com.trustel.common.MD5Helper;
import com.util.DCUtil;

public class SysUserService implements ISysUserService {

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	/**
	 * 查询所有用户
	 */
	public ItemPage querySysUser(SysUserForm form) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		if (null != form) {
			if (null != form.getUserName() && !form.getUserName().equals(""))
				query.where("userName", form.getUserName(), QueryAction.LIKE);
			if (null != form.getAccount() && !form.getAccount().equals(""))
				query.where("account", form.getAccount(), QueryAction.LIKE);
			if (null != form.getOrganizationId()
					&& !form.getOrganizationId().equals(""))
				query.where("organizationId in ("+getOrgIdByPerId(form.getOrganizationId())+") ");
		}
		//query.orderBy("seq", true);
		query.orderBy("orderNum",false);
		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}
	/**
	 * 查询所有用户
	 */
	public ItemPage querySysUser2(SysUserForm form) throws Exception {
		QueryBuilder query = new QueryBuilder("SysUser u,SysRole r,SysOrganization o,SysOrganization p");
		query.where("u.userDefaultRole = r.roleId");
		query.where("u.organizationId = o.organizationId");
		query.where("o.parentId = p.organizationId");
		if (null != form) {
			if (null != form.getUserName() && !form.getUserName().equals(""))
				query.where("u.userName", form.getUserName(), QueryAction.LIKE);
			if (null != form.getAccount() && !form.getAccount().equals(""))
				query.where("u.account", form.getAccount(), QueryAction.LIKE);
			if(null != form.getuDepIds() && !form.getuDepIds().equals("")){
				query.where("u.organizationId in ("+form.getuDepIds()+")");

			}
			if(StringUtils.isNotBlank(form.getDefaultRole())){//默认角色
				query.where("r.roleId" ,form.getDefaultRole().trim(), QueryAction.EQUAL);
				
			}
			if (null != form.getMobile() && !form.getMobile().equals(""))
				query.where("u.mobile", form.getMobile(), QueryAction.LIKE);
			if (null != form.getEmail() && !form.getEmail().equals(""))
				query.where("u.email", form.getEmail(), QueryAction.LIKE);
			if (null != form.getIsActivate() && !form.getIsActivate().equals(""))
				query.where("u.isActivate", form.getIsActivate());
            if (SysUser.STATUS_FREEZE_FAILCOUNT.equals(form.getFreezeStatus())){
                //冻结
                query.where("u.loginFailCount", 5l,QueryAction.GE);
                query.where("u.lastLoginFailDate>=to_date('"+ DateTime.now().withTimeAtStartOfDay().toString("yyyy-MM-dd HH:mm:ss")+"','yyyy-MM-dd hh24:mi:ss')");
            }else if (StringUtils.isNotBlank(form.getFreezeStatus())){
                query.where("u.freezeStatus", form.getFreezeStatus());
            }
			
			//if (null != form.getOrganizationId()&& !form.getOrganizationId().equals("") && !"ROOT".equals(form.getOrganizationId())){
				//query.where("u.organizationId in ("+getOrgIdByPerId(form.getOrganizationId())+")");
			  //  query.where("u.organizationId",form.getOrganizationId(),QueryAction.EQUAL);
			//}
			
			if(null != form.getOrgs() && !form.getOrgs().equals("")){
				query.where("u.organizationId in ("+form.getOrgs()+")");

			}
				
		}
		//query.orderBy("seq", true);
		query.orderBy("u.orderNum",false);
		String hh=query.getHQL();
		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}
	public ItemPage getAllUser(SysUserForm form) throws Exception{
		// TODO Auto-generated method stub
		QueryBuilder builder = new QueryBuilder(SysUser.class);
		if(checkStrIsNotNull(form.getAccount())){
			builder.where("account",form.getAccount(),QueryAction.LIKE);
		}
		if(checkStrIsNotNull(form.getUserName())){
			builder.where("userName",form.getUserName(),QueryAction.LIKE);
		}
		if(checkStrIsNotNull(form.getOrganizationId())){
			SysOrganization org = (SysOrganization) enterpriseService.getById(SysOrganization.class, form.getOrganizationId());
			builder.where("organizationId in("+getOrgIdsByOrgId(form.getOrganizationId(),org.getParentId())+")");
		}
		return enterpriseService.query(builder, form);
	}
	private boolean checkStrIsNotNull(String str){
		if(null!=str&&!"".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 查询角色对应下的用户
	 */
	public ItemPage querySysUserByRoleId(SysUserForm form,String roleId,String orgid) throws Exception {
		QueryBuilder query = new QueryBuilder("SysUser u,SysRoleUser r,SysOrganization o");
		query.where("u.userId = r.userId");
		query.where("u.organizationId = o.organizationId");
		if(roleId!=null && !roleId.equals("")){
			query.where("r.roleId", roleId, QueryAction.EQUAL);
		}
		if(orgid!=null && !orgid.equals("")){
			query.where("u.organizationId", orgid, QueryAction.EQUAL);
		}
		if (null != form) {
			if (null != form.getUserName() && !form.getUserName().equals(""))
				query.where("u.userName", form.getUserName(), QueryAction.LIKE);
			if (null != form.getAccount() && !form.getAccount().equals(""))
				query.where("u.account", form.getAccount(), QueryAction.LIKE);
			if (null != form.getOrganizationId()&& !form.getOrganizationId().equals("") && !"80df8fa55ca4048ac2314dab1a52d75e".equals(form.getOrganizationId())){
				//query.where("u.organizationId in ("+getOrgIdByPerId(form.getOrganizationId())+")");
			    query.where("u.organizationId",form.getOrganizationId(),QueryAction.EQUAL);
			}
					
				
		}
		//query.orderBy("seq", true);
		query.orderBy("u.createTime",false);
		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}
	/**
	 * 查询默认角色对应下的用户
	 */
	public ItemPage querySysUserByDefaultRoleId(SysUserForm form,String roleId,String orgid) throws Exception {
		QueryBuilder query = new QueryBuilder("SysUser u,SysRole r,SysOrganization o");
		query.where("u.userDefaultRole = r.roleId");
		query.where("u.organizationId = o.organizationId");
		if(roleId!=null && !roleId.equals("")){
			query.where("u.userDefaultRole", roleId, QueryAction.EQUAL);
		}
		if(orgid!=null && !orgid.equals("")){
			query.where("u.organizationId", orgid, QueryAction.EQUAL);
		}
		if (null != form) {
			if (null != form.getUserName() && !form.getUserName().equals(""))
				query.where("u.userName", form.getUserName(), QueryAction.LIKE);
			if (null != form.getAccount() && !form.getAccount().equals(""))
				query.where("u.account", form.getAccount(), QueryAction.LIKE);
			if (null != form.getOrganizationId()&& !form.getOrganizationId().equals("") && !"80df8fa55ca4048ac2314dab1a52d75e".equals(form.getOrganizationId())){
				//query.where("u.organizationId in ("+getOrgIdByPerId(form.getOrganizationId())+")");
			    query.where("u.organizationId",form.getOrganizationId(),QueryAction.EQUAL);
			}
					
				
		}
		//query.orderBy("seq", true);
		query.orderBy("u.createTime",false);
		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}
	public void getOrgIds(String userId){
		QueryBuilder query=new QueryBuilder("SysRoleUser ru,SysRoleOrg or");
		query.select("or.orgId");
		query.where("ru.userId",userId);
		query.where("ru.roleId=or.roleId");
		List<String> orgs=(List<String>) enterpriseService.query(query,0);
		
	}
	/**
	 * 添加用户
	 */
	public void addSysUser(SysUser sysUser) throws Exception {
		if(null!=sysUser.getPassword()){
			MD5Helper md5 = new MD5Helper();
			String md5Pwd = md5.getDoubleMD5ofStr(sysUser.getPassword());
			sysUser.setPassword(md5Pwd);
			sysUser.setCreateTime(DateUtils.get(new Date(), "GM+8"));
		}
		enterpriseService.save(sysUser);

	}
	
	/**
	 * 添加用户__返回主键
	 */
	public String addSysUserBackId(SysUser sysUser) throws Exception {
		MD5Helper md5 = new MD5Helper();
		String md5Pwd = md5.getDoubleMD5ofStr(sysUser.getPassword());
		sysUser.setPassword(md5Pwd);
		sysUser.setCreateTime(DateUtils.get(new Date(), "GM+8"));
		return (String)enterpriseService.save(sysUser);

	}

	/**
	 * 修改用户
	 */
	public void modifySysUser(SysUser sysUser) throws Exception {
		enterpriseService.updateObject(sysUser);

	}

	/**
	 * 根据ID获取用户
	 */
	public SysUser getSysUserById(String userId) throws Exception {
		return (SysUser) enterpriseService.getById(SysUser.class, userId);

	}

	/**
	 * 查询所有用户
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> allUser() throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.orderBy("createTime", true);
		return (List<SysUser>) enterpriseService.query(query, 0);
	}
	
	
	/**
	 * 查询所有用户、组织
	 */
	public List allUserOrg() throws Exception{
		 StringBuilder sql = new StringBuilder();
	        sql.append("select t1.user_id,t1.user_name,t1.account,( select t.org_name from sys_organization t ")
	        .append("where t.org_level=2 start with t.organization_id = t1.organization_id ")
	        .append("connect by prior t.parent_id = t.organization_id ) from sys_user t1 ")
	        .append(" where t1.is_activate='").append(SysUser.STATUS_ACTIVATE_ENABLE).append("'");
	        Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
	        return query.list();
	}
	
	
	/**
	 * 查询所有用户(组织相应列出）
	 */
	public List allUserAndOrg() throws Exception{
		 StringBuilder sql = new StringBuilder();
	        sql.append("select t1.user_id,t1.user_name,t1.account,( select t.org_name from sys_organization t ")
	        .append(" where t.organization_id = t1.organization_id ) ")
	        .append(" from sys_user t1 ")
	        .append(" where t1.is_activate='").append(SysUser.STATUS_ACTIVATE_ENABLE).append("'");
	        Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
	        return query.list();
	}
	/**
	 * 查询所有移动用户
	 */
	public List<SysUser> allCmccUser() throws Exception{
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("orgName", "中国移动", QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		String org="";
		if (list != null && list.size() > 0){
			SysOrganization ss=(SysOrganization) list.get(0);
			org=getOrgIdByPerIdAdd(ss.getOrganizationId());
		}
		QueryBuilder query1= new QueryBuilder(SysUser.class);
		if(org!=""){
			query1.where(" organizationId in ("+org+") ");
		}
		return (List<SysUser>) enterpriseService.query(query1, 0);
		
		
		//select * from SYS_ORGANIZATION t    start with  t.organization_id='13080915070416300001' connect by  t.parent_id= prior t.organization_id 
	}
	
	
	/**
	 * 查询所有移动用户、组织
	 */
	public List<SysUser> allCmccUserOrg() throws Exception{
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("orgName", "中国移动", QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		String org="";
		if (list != null && list.size() > 0){
			SysOrganization ss=(SysOrganization) list.get(0);
			org=getOrgIdByPerIdAdd(ss.getOrganizationId());
		}
		StringBuilder sql = new StringBuilder();
	    sql.append("select t1.user_id,t1.user_name,t1.account,( select t.org_name from sys_organization t ")
	    .append("where t.org_level=2 start with t.organization_id = t1.organization_id ")
	    .append("connect by prior t.parent_id = t.organization_id ) from sys_user t1 ")
	    .append(" where t1.is_activate='").append(SysUser.STATUS_ACTIVATE_ENABLE).append("'")
	    .append(" and t1.organization_id in("+org+") ");
	    Query query1 = enterpriseService.getSessions().createSQLQuery(sql.toString());
	    return query1.list();
	}
	
	/**
	 * 根据组织ID查询所有用户
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> allUserByOrgId(String orgId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("organizationId in ("+getOrgIdByPerId(orgId)+")");
		query.orderBy("seq", true);
		return (List<SysUser>) enterpriseService.query(query, 0);
	}
	/**
	 * 根据组织ID查询所有用户
	 */
	@SuppressWarnings("unchecked")
	public ItemPage allUserByOrgId2(SysUserForm form) {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		if (null != form) {
			if (null != form.getUserName() && !form.getUserName().equals(""))
				query.where("userName", form.getUserName(), QueryAction.LIKE);
			if (null != form.getAccount() && !form.getAccount().equals(""))
				query.where("account", form.getAccount(), QueryAction.LIKE);
			if (null != form.getOrganizationId()
					&& !form.getOrganizationId().equals(""))
				query.where("organizationId",form.getOrganizationId(),QueryAction.EQUAL);
		}
		ItemPage userItems=enterpriseService.query(query, form);
		return userItems;
	}
	/**
	 * 删除用户
	 */
	public void deleteSysUser(String userId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("userId", userId);
		enterpriseService.delete(query);
	}

	/**
	 * 根据用户登录账号查询用户
	 */
	public SysUser queryVisitorByName(String account) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("account", account, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return (SysUser) list.get(0);
		return null;
	}

	/**
	 * 根据组织ID，角色名称获取用户
	 */
	public List<SysUser> getUserInfo(String roleName, String orgId)
			throws Exception {
		QueryBuilder query = new QueryBuilder(
				"SysUser s,SysRoleUser t,SysRole r");
		query.where("t.userId=s.userId");
		query.where("t.roleId=r.roleId");
		query.where("r.roleName", roleName, QueryAction.EQUAL);
		query.where("s.organizationId", orgId, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null) {
			List<SysUser> userList = new ArrayList<SysUser>();
			for (Object object : list)
				if (object != null) {
					Object[] objs = (Object[]) object;
					userList.add((SysUser) objs[0]);
				}
			return userList;
		}
		return null;
	}

	/**
	 * 根据角色名称获取用户
	 */
	public List<SysUser> getUserInfo(String roleName) throws Exception {
		QueryBuilder query = new QueryBuilder(
				" SysUser s,SysRoleUser t,SysRole r ");
		query.where("t.userId=s.userId");
		query.where("t.roleId=r.roleId");
		query.where("r.roleName", roleName, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null) {
			List<SysUser> userList = new ArrayList<SysUser>();
			for (Object object : list)
				if (object != null) {
					Object[] objs = (Object[]) object;
					userList.add((SysUser) objs[0]);
				}
			return userList;
		}
		return null;
	}
	/**
	 * 修改用户排序
	 */
	public void orderUpdate(String ids,String seqs) throws Exception {
		if (null != ids && !"".equals(ids) && null != seqs && !"".equals(seqs)) {
				String[] idList = ids.split(",");
				String[] seqList = seqs.split(",");
				if(idList.length==seqList.length){
					for(int i=0;i<idList.length;i++){
						SysUser user=(SysUser) enterpriseService.getById(SysUser.class,idList[i]);
						float seq = Float.parseFloat(seqList[i]);
//						if(user.getSeq()!=seq){
//							user.setSeq(seq);
//							enterpriseService.updateObject(user);
//						}
					}
			 }
		}
	}
	/**
	 * 根据组织Id获取其组织下的用户以及下属组织下的用户
	 */
	@SuppressWarnings("unchecked")
	public ItemPage getUserByOrgId(String orgId, SysUserForm form,
			String currentUserId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		List<SysOrganization> orgList = (List<SysOrganization>) enterpriseService
				.query(query, 0);
		List<SysOrganization> sublist = new ArrayList<SysOrganization>();
		List<SysOrganization> newList = new ArrayList<SysOrganization>();
		String orgIds = "";
		orgIds = "'" + orgId + "',";
		for (SysOrganization org : orgList) {
			if (org.getParentId().equals(orgId)) {
				sublist.add(org);
				newList.add(org);
			}
		}
		if (sublist.size() != 0) {
			List<SysOrganization> allSubList = recursive(orgList, sublist,
					newList);
			for (SysOrganization org : allSubList) {
				orgIds += "'" + org.getOrganizationId() + "',";
			}
		}
		QueryBuilder queryBuilder = new QueryBuilder(SysUser.class);
		queryBuilder.where("organizationId in("
				+ orgIds.substring(0, orgIds.length() - 1) + ")");
		if (null != form) {
			if (null != form.getUserName() && !form.getUserName().equals(""))
				queryBuilder.where("userName", form.getUserName(),
						QueryAction.LIKE);
			if (null != form.getAccount() && !form.getAccount().equals(""))
				queryBuilder.where("account", form.getAccount(),
						QueryAction.LIKE);
			if (null != form.getOrganizationId()
					&& !form.getOrganizationId().equals(""))
				queryBuilder.where("organizationId", form.getOrganizationId(),
						QueryAction.LIKE);
		}
		queryBuilder.where("userId", currentUserId, QueryAction.NOEQUAL);
		return enterpriseService.query(queryBuilder, form);
	}
	/**
	 * 根据组织Id获取其所有子组织的Id链接字符串（包括本身Id）
	 */
	@SuppressWarnings("unchecked")
	public String getOrgIdByPerId(String orgId) throws Exception {
		List<SysOrganization> list=	enterpriseService.getSessions().createSQLQuery("select {t.*} from SYS_ORGANIZATION t start with t.ORGANIZATION_ID='"+orgId+"' connect by prior t.ORGANIZATION_ID=t.PARENT_ID")
				.addEntity("t",SysOrganization.class).list();
		String orgIds = "";
		for(SysOrganization org:list){
			orgIds += "'"+ org.getOrganizationId() + "',";
		}
		if(!orgIds.equals("")){
			return orgIds.substring(0,orgIds.length()-1);
		}else{
			orgIds="1";
			return orgIds;
		}
	}
	
	public String getOrgIdByPerIdAdd(String orgId) throws Exception {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select t.organization_id from SYS_ORGANIZATION t  start with  t.organization_id='"+orgId+"' connect by  t.parent_id= prior t.organization_id");
		//List list = enterpriseService.findBySql(querySql.toString(), new SysUserForm()).getItems();
		Query query = enterpriseService.getSessions().createSQLQuery(querySql.toString());
		List list = query.list();		
		String orgIds="";
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				orgIds+="'"+ list.get(i) + "',";
			}
		}
		return orgIds.substring(0,orgIds.trim().length()-1);
	}


	/**
	 * 递归获取某个组织所有子组织
	 * 
	 * @param orgList
	 *            所有组织
	 * @param SubList
	 *            子组织变量
	 * @param newList
	 *            存某个组织下的所有子组织
	 * @return
	 */
	public List<SysOrganization> recursive(List<SysOrganization> orgList,
			List<SysOrganization> SubList, List<SysOrganization> newList) {
		// 子组织
		List<SysOrganization> SuborgList = new ArrayList<SysOrganization>();
		for (SysOrganization org : orgList) {
			for (SysOrganization Suborg : SubList) {
				if (Suborg.getOrganizationId().equals(org.getParentId())) {
					newList.add(org);
					SuborgList.add(org);
				}
			}
		}
		if (SuborgList.size() != 0) {
			this.recursive(orgList, SuborgList, newList);
		}
		return newList;
	}

	/**
	 * 根据角色名称跟项目ID查询用户
	 * 
	 * @param roleName
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getUserByRoleNameAndPJID(String roleName,
			String projectId) throws Exception {
		QueryBuilder query = new QueryBuilder(" SysUser s,TermConfig t");
		query.where("t.userId=s.userId");
		query.where("t.userName=s.userName");
		query.where("t.roleName", roleName, QueryAction.EQUAL);
		query.where("t.projectId", projectId, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null) {
			List<SysUser> userList = new ArrayList<SysUser>();
			for (Object object : list)
				if (object != null) {
					Object[] objs = (Object[]) object;
					userList.add((SysUser) objs[0]);
				}
			return userList;
		}
		return null;

	}
	/**
	 * * 根据角色id字符串查询用户
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	 
	@SuppressWarnings("unchecked")
	public List<SysUser> getUserByIDS(String ids) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("userId in ("+ids+")");
		return (List<SysUser>) enterpriseService.query(query, 0);
	}
	/**
	 * 根据用户ID获取项目ID
	 * 
	 * @param roleName
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	public List<String> getProjectIdListByUserId(String userId)
			throws Exception {
		QueryBuilder query = new QueryBuilder(TermConfig.class);
		query.where("userId", userId, QueryAction.EQUAL);
		List<TermConfig> list = (List<TermConfig>) enterpriseService.query(
				query, 0);
		List<String> projectIdList = new ArrayList<String>();
		if (list != null) {
			for (TermConfig team : list) {
				projectIdList.add(team.getProjectId());
			}
		}
		return projectIdList;
	}*/
	
	/**
	 * * 根据用户账号和名字查询用户
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	 
	@SuppressWarnings("unchecked")
	public SysUser getUserByNameAndACC(String account) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		if(null!=account&& !account.equals("")){
			query.where("account",account);
		}else{
			query.where("account",1);
		}
		List <SysUser> list= (List<SysUser>) enterpriseService.query(query, 0);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	

	
	/**
	 * 批量添加用户信息
	 * @param visitor
	 * @param file
	 * @param request
	 * @param organizationId
	 * @return
	 * @throws Exception
	 */
	public String saveUser(SysUser visitor,String file,HttpServletRequest request,String organizationId,String organizationName,String roleid) throws Exception{
		boolean flag=false;
		String[][] result = null;
		SysUser user=null;
		String ids = "";
		int orderNum=getOrderNum();
		try {
			result = BatchUserUtil.getData(new File(file), 2, 0);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}// 忽略前两行（标题行）
		for (int i = 0; i < result.length; i++) {
			user = new SysUser();
			user.setAccount(result[i][0]);
            user.setUserName(result[i][1]);
    		user.setOrderNum(orderNum+i+1);
			if (result[i][2].equals("接受".trim())) {
				user.setIsReceiveSms("1");
			}if (result[i][2].equals("不接受".trim())) {
				user.setIsReceiveSms("0");
			}if (result[i][3].equals("正常".trim())) {
				user.setIsActivate("1");
			}if (result[i][3].equals("禁用".trim())) {
				user.setIsActivate("0");
			}
			user.setFreezeStatus("0");//冻结状态
//			MD5Helper md5 = new MD5Helper();
//    		String md5Pwd = md5.getDoubleMD5ofStr(result[i][4].trim());
//    		System.out.println("密码=="+md5Pwd);
    		user.setPassword(result[i][4].trim());
    		
    		user.setEmail(result[i][5]);
    		user.setMobile(result[i][6]);
    		user.setCreateTime(DateUtils.get(new Date(), "GM+8"));
    		user.setUserDefaultRole(roleid);
    		user.setOrganizationId(organizationId);
//    		user.setOrganizationName(organizationName);
    		
    		user.setCreaterId(visitor.getUserId());
//    		user.setCreaterName(visitor.getCreaterName());
    		
//    		user.setDefaultProject("");
//    		user.setSeq(0);

			try {
				String id=addSysUserBackId(user);
				if(id!=null && !id.equals("")){
					if(ids.equals("")){
						ids=id;
					}else{
						ids+=","+id;
					}
				}
				flag = true;
			} catch (Exception e) {
              e.printStackTrace();
			}
			
		}

		return ids;
	}
	
	/**
	 * 批量保存用户信息
	 * @param visitor
	 * @param file
	 * @param request
	 * @param specificationId
	 * @return
	 * @throws RuntimeException
	 */
	public List<DicFailImportObject> saveData(SysUser visitor, String file,
			HttpServletRequest request, String organizationId,String organizationName,String roleid)
			throws RuntimeException{
		List<DicFailImportObject> listObject=null;
		DicFailImportObject object=new DicFailImportObject();
		try {
			listObject=this.validateWare(visitor, file, request, organizationId);
			if(listObject.size()==0){
				String f=this.saveUser(visitor, file, request, organizationId,organizationName,roleid);
				if(f==null || f.equals("")){
					object.setFailReason("保存失败,请检查录入数据!");
					object.setSheetIndex(-1);
					listObject.add(object);
				}else{
					String[] ids = f.split(",");//保存用户角色表
					if(ids !=null && ids.length>0){
						for(int i=0;i<ids.length;i++){
							if(ids[i]!=null && !ids[i].equals("")){
								SysRoleUser model = new SysRoleUser();
								   model.setRoleId(roleid);
								   model.setUserId(ids[i]);
								   addSysUserRole(model);
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listObject;
	}
	
	
	/**
	 * excel数据验证
	 * 
	 * @param visitor
	 * @param file
	 * @param request
	 * @param specificationId
	 * @return
	 * @throws RuntimeException
	 */

	public List<DicFailImportObject> validateWare(SysUser visitor, String file,
			HttpServletRequest request, String specificationId)
			throws RuntimeException {
		String userResult[][] = null;//用户信息

		DicFailImportObject dicFailImportObject = null;//记录一条错误的信息
		List<DicFailImportObject> listobject=null;//所有错误
		try {
			userResult = BatchUserUtil.getData(new File(file), 2, 0);
			listobject=new ArrayList<DicFailImportObject>();
			if(userResult==null || userResult.length==0){
//				DicFailImportObject dicFailImportObject0 = new DicFailImportObject();
//				listobject = new ArrayList<DicFailImportObject>();
				dicFailImportObject.setFailReason("至少要填入一条数据!");
				listobject.add(dicFailImportObject);
				return listobject;
			}
			//循环用户信息
			for (int i = 0; i < userResult.length; i++) {//除检查重复之外的其他数据检查机制
				
				if (null == userResult[i][0] ||  "".equals(userResult[i][0])) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("用户登录账号为空!");
					listobject.add(dicFailImportObject);
				}else{
					SysUser userModel = getUserByNameAndACC(userResult[i][0]);
					if(userModel != null){
						dicFailImportObject = new DicFailImportObject();
						dicFailImportObject.setSheetIndex(0);
						dicFailImportObject.setRow(i + 3);
						dicFailImportObject.setFailReason("用户登录账号已存在!");
						listobject.add(dicFailImportObject);
					} else {
						int count=0;
						for(int j=0;j<userResult.length;j++){
							if(userResult[i][0].equals(userResult[j][0])){
								count+=1;
							}
						}
						if(count>=2){
							dicFailImportObject = new DicFailImportObject();
							dicFailImportObject.setSheetIndex(0);
							dicFailImportObject.setRow(i + 3);
							dicFailImportObject.setFailReason(userResult[i][0]+"用户登录账号在此份excle中重复存在"+count+"个!");
							listobject.add(dicFailImportObject);
						}
					}
				}
				if(null!=userResult[i][0] && !"".equals(userResult[i][0])){
					if(userResult[i][0].length()<3 || userResult[i][0].length()>20){
						dicFailImportObject = new DicFailImportObject();
						dicFailImportObject.setSheetIndex(0);
						dicFailImportObject.setRow(i + 3);
						dicFailImportObject.setFailReason("用户登录账号应在3-20位字符之间!");
						listobject.add(dicFailImportObject);
					}
					
				}
				
				if ("".equals(userResult[i][1]) || null == userResult[i][1]) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("用户姓名为空!");
					listobject.add(dicFailImportObject);
				} 
				
				if ("".equals(userResult[i][4]) || null == userResult[i][4]) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("密码为空!");
					listobject.add(dicFailImportObject);
				} 
				
				
				if(StringUtils.isBlank(userResult[i][5])){
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("邮箱为空!");
					listobject.add(dicFailImportObject);
				}
				String check = "^([a-z0-9A-Z]+[_-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"; 
				if(null!=userResult[i][5] && !"".equals(userResult[i][5])){
					Pattern regex = Pattern.compile(check);  
				    Matcher matcher = regex.matcher(userResult[i][5]);  
				    boolean flag = matcher.matches();
				    if(flag==false){
				    	dicFailImportObject = new DicFailImportObject();
						dicFailImportObject.setSheetIndex(0);
						dicFailImportObject.setRow(i + 3);
						dicFailImportObject.setFailReason("邮箱格式不正确!");
						listobject.add(dicFailImportObject);
				    }
					
				}
				
				
				if(StringUtils.isBlank(userResult[i][6])){
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 3);
					dicFailImportObject.setFailReason("手机号码为空!");
					listobject.add(dicFailImportObject);
				}
				if(null!=userResult[i][6] && !"".equals(userResult[i][6])){
					Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");  
				    Matcher m = p.matcher(userResult[i][6]);  
				    boolean flag = m.matches();
				    if(flag==false){
				    	dicFailImportObject = new DicFailImportObject();
						dicFailImportObject.setSheetIndex(0);
						dicFailImportObject.setRow(i + 3);
						dicFailImportObject.setFailReason("手机号码格式不正确!");
						listobject.add(dicFailImportObject);
				    }
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return listobject;
	}
	
	
	/**
	 * 查询所有组织信息
	 * @param visitor
	 * @return
	 * @throws RuntimeException
	 */
	public List<SysOrganization> getAllOrganization(SysUser visitor)throws RuntimeException{
	    QueryBuilder builder=new QueryBuilder(SysOrganization.class);
		
	    List<SysOrganization> listOrg =(List<SysOrganization>) enterpriseService.query(builder, 0);
		return listOrg;
	}
	
	
	/**
	 * 获取组织机构
	 * @param visitor
	 * @return
	 * @throws RuntimeException
	 */
	public List<SysOrganization> getOrgFirst(SysUser visitor)throws RuntimeException{
		QueryBuilder builder=new QueryBuilder(SysOrganization.class);
		String flag="80df8fa55ca4048ac2314dab1a52d75e";
		builder.where("parentId",flag.trim());
		builder.where("organizationId",flag.trim(),QueryAction.NOEQUAL);
		builder.orderBy("orgOrder", true);
		List<SysOrganization> listOrg =(List<SysOrganization>) enterpriseService.query(builder, 0);
		return listOrg;
	}
	
	/**
	 * 根据组织ID 获取子组织信息
	 * @param visitor
	 * @param orgId
	 * @return
	 * @throws RuntimeException
	 */
	public List<SysOrganization> getNextOrg(SysUser visitor,String orgId)throws RuntimeException{
		QueryBuilder builder=new QueryBuilder(SysOrganization.class);
		List<SysOrganization> listOrg=null;
	    if(null!=orgId && !"".equals(orgId)){
	    	builder.where("parentId",orgId);
	    	builder.orderBy("orgOrder", true);
			listOrg =(List<SysOrganization>) enterpriseService.query(builder, 0);
	    }
		
		return listOrg;
	}
	
	
	/**
	 * 变更组织
	 * @param userId
	 * @throws RuntimeException
	 */
	public void upUserByUserId(String userId,String organizationId,String organizationName)throws RuntimeException{
		SysUser user=(SysUser) enterpriseService.getById(SysUser.class, userId);
		user.setOrganizationId(organizationId);
//		user.setOrganizationName(organizationName);
		enterpriseService.updateObject(user);
	}
	
	/**
	 * 查询所有用户
	 */
	public List<SysUser> querySysUseres(SysUserForm form) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		if (null != form) {
			if (null != form.getUserName() && !form.getUserName().equals(""))
				query.where("userName", form.getUserName(), QueryAction.LIKE);
			if (null != form.getAccount() && !form.getAccount().equals(""))
				query.where("account", form.getAccount(), QueryAction.LIKE);
			
		}
		query.orderBy("createTime",false);
		List<SysUser> sjs = (List<SysUser>) enterpriseService.query(query,0);
		return sjs;
	}
	

	/**
	 * 用户联想查询
	 */
	public List searchUser(String userValue){
		return searchUser(userValue,SysUser.STATUS_ACTIVATE_ENABLE);
	}

    public List searchUser(String userValue,String userStatus){
        Class<?>[] pojos={SysUser.class,SysOrganization.class};
        QueryBuilder builder = new QueryBuilder(pojos);
        //builder.where("a.userName", userValue, QueryAction.LIKE);
        builder.where("(a.userName like '%"+userValue+"%' or a.account like '%"+userValue+"%')");
        builder.where("a.organizationId=b.organizationId");
        if (StringUtils.isNotBlank(userStatus)) {
            builder.where("a.isActivate",userStatus);
        }
        List list = enterpriseService.query(builder, 0);
        return list;
    }

	public void addSysUserRole(SysRoleUser model) throws Exception {
		enterpriseService.save(model);
		
	}
	/**
	 * 解除冻结账户
	 */
	public void unfreezeUser(String userId) throws Exception{
		SysUser sysUser = (SysUser)enterpriseService.getById(SysUser.class, userId);
		//sysUser.setFreezeStatus("0");
		//enterpriseService.updateObject(sysUser);
		
		String account = sysUser.getAccount();
		QueryBuilder query = new QueryBuilder(SysLoginFailure.class);
		query.where("account", account);
		enterpriseService.delete(query);
		
	}
	
	/**
	 * 获取最大或者最小排序值
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getMaxOrMinOrderNum(String depId,String orderByFlag) throws Exception {
		QueryBuilder builder=new QueryBuilder(SysUser.class);
		 // builder.where("organizationId",depId,QueryAction.EQUAL);
		 builder.where("organizationId in ("+depId+")");
		 if(orderByFlag != null && orderByFlag.equals("U")){
			 builder.orderBy("orderNum", false);
		 }
		 if(orderByFlag != null && orderByFlag.equals("D")){
			 builder.orderBy("orderNum", true);
		 }
		 List<SysUser> list = (List<SysUser>)this.enterpriseService.query(builder, 0);
		 return list;
	}
	/**
	 * 获取排序值
	 * @return
	 */
	public int getOrderNum(){
		int orderNum=0;
		QueryBuilder builder=new QueryBuilder(SysUser.class);
		builder.orderBy("orderNum", false);
		List<SysUser> list = (List<SysUser>)this.enterpriseService.query(builder, 0);
		if(list!=null && list.size()>0){
			orderNum=list.get(0).getOrderNum()+1;
		}
		return orderNum;
	}

	
	public ItemPage querySysUser2(String orgid,SysUserForm form) throws Exception {
		// TODO Auto-generated method stub
		SysOrganization org = (SysOrganization) enterpriseService.getById(SysOrganization.class, orgid);
		QueryBuilder builder = new QueryBuilder("SysUser u,SysRole r,SysOrganization o,SysOrganization p");
		builder.where("u.userDefaultRole = r.roleId");
		builder.where("u.organizationId = o.organizationId");
		builder.where("o.parentId = p.organizationId");
		builder.where("u.organizationId in("+getOrgIdsByOrgId(orgid,org.getParentId())+")");
		return enterpriseService.query(builder, form);
	}
	private String getOrgIdsByOrgId(String orgId,String pid) throws Exception {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		List<SysOrganization> orgList = (List<SysOrganization>) enterpriseService.query(query, 0);
		if(orgId != null && !orgId.equals("") && orgId.equals("80df8fa55ca4048ac2314dab1a52d75e")){
			String orgIdN = "'" + orgId + "',";
			for (SysOrganization org : orgList) {
				orgIdN += "'" + org.getOrganizationId() + "',";
			}
			return orgIdN.substring(0,orgIdN.length()-1);
		}
	//	QueryBuilder querystr = new QueryBuilder(SysOrganization.class);//获取平级组织Id
		//querystr.where("parentId", pid, QueryAction.EQUAL);
		//List<SysOrganization> bList = (List<SysOrganization>) enterpriseService.query(querystr, 0);
		
		List<SysOrganization> sublist = new ArrayList<SysOrganization>();
		List<SysOrganization> newList = new ArrayList<SysOrganization>();
		String orgIds = "";
		orgIds = "'" + orgId + "',";
//		for(SysOrganization org:bList){
//			orgIds+="'"+org.getOrganizationId()+"',";
//		}
		for (SysOrganization org : orgList) {
			//for(SysOrganization brg :bList){
				if (org.getParentId().equals(orgId)) {
					sublist.add(org);
					newList.add(org);
				}
			//}
		}
		if (sublist.size() != 0) {
			List<SysOrganization> allSubList = recursive(orgList, sublist,
					newList);
			for (SysOrganization org : allSubList) {
				orgIds += "'" + org.getOrganizationId() + "',";
			}
		}
		return orgIds.substring(0,orgIds.length()-1);
	}

    @Override
    public void updateSysUser(SysUser sysUser){
        enterpriseService.updateObject(sysUser);
    }
    
    @Override
    public String getComyOrg(String orgId){
    	   		
    	SysOrganization sysOrganization=null;
    	SysOrganization sysOrganizationTemp=null;
    	sysOrganization=this.getOrgById(orgId);
    	sysOrganizationTemp=sysOrganization;
    	while(sysOrganization.getOrgLevel()>5){
    		String pId=sysOrganization.getParentId();
    		sysOrganization=this.getOrgById(pId);
    		sysOrganizationTemp=sysOrganization;
    	}
    	return sysOrganizationTemp.getOrgName();
    }
    
    public SysOrganization getOrgById(String orgId){
    	QueryBuilder builder=new QueryBuilder(SysOrganization.class);
		builder.where("organizationId",orgId);    		
		List<SysOrganization> list = (List<SysOrganization>)this.enterpriseService.query(builder, 0);
		SysOrganization sysOrganization=null;
		if(list.size()>0){
			sysOrganization=list.get(0);
		}    	
		return sysOrganization;
    }
    
    /**
     * 检测登录账户是否存在
     * @param account 账号
     * @return
     */
    public Boolean checkAccountName(String account){
    	Boolean status=true;
    	try{
	    	if(account != null && !account.trim().equals("")){
				QueryBuilder builder = new QueryBuilder(SysUser.class);
				builder.where("account", account);
				builder.where("isActivate", "1");
				List list = enterpriseService.query(builder, 0);
				if(list.size()>0){
					status=true;
				}else{
					status=false;
				}
			}		    						
		}catch(Exception e){
			e.printStackTrace();
		}
    
		return status;
    }
    
    
    /**
     * 检测用户信息是否准确
     * @param account
     * @param userName
     * @param mobile
     * @return 0 检测用户信息正确；2 用户名或手机不符合；7 账号不存在
     */
    public String checkUserInfo(String account,String userName,String mobile){
    	String result="0";
    	try{
	    	if(account != null && !account.trim().equals("")){
				QueryBuilder builder = new QueryBuilder(SysUser.class);
				builder.where("account", account);
				builder.where("isActivate", "1");
				List list = enterpriseService.query(builder, 0);
				if(list.size()>0){
					SysUser sysUser=(SysUser)list.get(0);
					if(sysUser.getUserName().equals(userName)&&sysUser.getMobile().equals(mobile)){
						result="0";
					}else{
						result="2";
					}
				}					
			}else{
				result="7";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    	return result;
    }
    
    /**
     * 通过账号获取用户组织
     * @param account
     * @return
     */
    public String getUserOrgNameByAccount(String account){
    	if(account != null && !account.trim().equals("")){
			QueryBuilder builder = new QueryBuilder(SysUser.class);
			builder.where("account", account);
			builder.where("isActivate", "1");
			List list = enterpriseService.query(builder, 0);
			if(list.size()>0){
				SysUser sysUser=(SysUser)list.get(0);
				String orgId=sysUser.getOrganizationId();
				if(orgId!=null&&orgId!=""){
					SysOrganization org=(SysOrganization) enterpriseService.getById(SysOrganization.class, orgId);
					return org.getOrgName();
				}else{
					return null;
				}				
			}
			return null;
		}else{
			return null;
		}
    }
    
    /**
     * 通过账号获取用户信息
     * @param account
     * @return
     */
    public SysUser getSysUserByAccount(String account){
    	if(account != null && !account.trim().equals("")){
			QueryBuilder builder = new QueryBuilder(SysUser.class);
			builder.where("account", account);
			builder.where("isActivate", "1");
			List list = enterpriseService.query(builder, 0);
			if(list.size()>0){
				SysUser sysUser=(SysUser)list.get(0);
				return 	sysUser;	
			}
			return null;
		}else{
			return null;
		}
    }
    
    /*删除用户信息，以便同步用户*/
    public void  dropUser(){
    	Query query = enterpriseService.getSessions().createSQLQuery("delete from SYS_USER t where t.is_from_web='1'");
		query.executeUpdate();
    }
    
    /*备份用户信息，以便同步用户*/
    public void  dropUserBackup(){
    	Query query = enterpriseService.getSessions().createSQLQuery("delete from SYS_USER_BACKUP");
		query.executeUpdate();
		Query query1 = enterpriseService.getSessions().createSQLQuery("insert into SYS_USER_BACKUP(USER_ID,USER_NAME,ACCOUNT,PASSWORD,"
				+ "MOBILE,EMAIL,IS_RECEIVE_SMS,USER_DEFAULT_ROLE,IS_ACTIVATE,ORGANIZATION_ID,CREATER_ID,CREATE_TIME,FREEZE_STATUS,ORDER_NUM,"
				+ "IS_IE_TIP,PASSWORD_OLD,PASSWORD_NEW,LAST_UPDATE_TIME,LOGIN_FAIL_COUNT,LAST_LOGIN_FAIL_DATE,EMPLOYEE,FULL_NAME,OU_GUID,OU_ID,JOB_TYPE,"
				+ "USER_TYPE,WORK_PHONE,TELE_PHONE,ADDRESS,USER_BIRTHDAY,SEX,TITLE,ORDER_ID,USER_JOININ_DATE,USER_NATION,USER_RELIGION,USER_QUIT_DATE,"
				+ "CHANGE_TIME,LAST_UPDATE_DATE,IS_FROM_WEB) select * from SYS_USER");
		query1.executeUpdate();
    }
    
    /*删除临时用户信息，以便同步用户*/
    public void  dropUserTemp(){
    	Query query = enterpriseService.getSessions().createSQLQuery("delete from SYS_USER_TEMP");
		query.executeUpdate();
    }
    
    /**
  	 * 向临时用户表添加数据，以便同步用户信息
  	 */
  	public void addSysUserTemp(SysUserTemp sysUser) throws Exception{
  		sysUser.setCreateTime(DateUtils.get(new Date(), "GM+8"));
		enterpriseService.save(sysUser);
  	}
  	
  	/**
	 * 查询所有临时用户
	 */
	public List<SysUserTemp> allUserTemp() throws Exception{
		QueryBuilder query = new QueryBuilder(SysUserTemp.class);
		query.orderBy("createTime", true);
		return (List<SysUserTemp>) enterpriseService.query(query, 0);
	}
	
	/**
	 * 添加临时用户表到正式表
	 */
	public void addSysUserByTemp( ) throws Exception{
		Query query = enterpriseService.getSessions().createSQLQuery("insert into sys_user  select * from sys_user_temp t where t.account is not null and t.organization_id in (select  s.organization_id from sys_organization s )");
		query.executeUpdate();
	}
	
	/**
	 * * 根据菜单权限字符串查询用户
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getUserByMenu(String menuCode) throws Exception{
		QueryBuilder query = new QueryBuilder(
				" SysUser s,SysRoleUser t,SysRole r ,SysRolePrivilges u");
		query.where("t.userId=s.userId");
		query.where("t.roleId=r.roleId");
		query.where("t.roleId=u.roleId");
		query.where("u.moduleCode", menuCode, QueryAction.EQUAL);
		String hh=query.getSQL();
		List<?> list = enterpriseService.query(query, 0);
		if (list != null) {
			List<SysUser> userList = new ArrayList<SysUser>();
			for (Object object : list)
				if (object != null) {
					Object[] objs = (Object[]) object;
					SysUser temp=(SysUser) objs[0];
					if(!userList.contains(temp)){
						userList.add(temp);
					}
				}
			return userList;
		}
		return null;
	}
	
	/**
	 * 根据用户名查询用户
	 * @param parm
	 */
	public SysUser getSysUserByName(String userName){
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("userName", userName, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return (SysUser) list.get(0);
		return null;
	}
	
	/**
	 *临时同步用户时，同步id字段 
	 */
	 public void  updateSysUserIdTemp(){
		 Query query = enterpriseService.getSessions().createSQLQuery("update SYS_USER_TEMP t set t.USER_ID=t.EMPLOYEE where t.USER_ID!='13081319154584000001'");
		 query.executeUpdate();
	 }
	 
	 /**
		 * 根据用户名或用户账号查询用户
		 * @param parm
		 */
	public SysUser getSysUserByIdOrName(String userId,String userName){
		if(null!=userId&&userId.trim()!=""){
			QueryBuilder query = new QueryBuilder(SysUser.class);
			query.where("account", userId, QueryAction.EQUAL);
			List<?> list = enterpriseService.query(query, 0);
			if (list != null && list.size() > 0){
				return (SysUser) list.get(0);
			}else{
				return null;
			}
		}else{
			QueryBuilder query = new QueryBuilder(SysUser.class);
			query.where("userName", userName, QueryAction.EQUAL);
			List<?> list = enterpriseService.query(query, 0);
			if (list != null && list.size() > 0){
				return (SysUser) list.get(0);
			}else{
				return null;
			}
		}
		
	}

	@Override
	public void addSysUserDefaultRoleId() throws Exception {
		//获取默认角色编号
		String defcode = DCUtil.getProperty("DEFAULT_ROLECODE");
		if(StringUtils.isEmpty(defcode)){
			defcode = "CMCC";
		}
		//根据默认角色编号查询角色信息
		SysRole sysrole = null;
		QueryBuilder query = new QueryBuilder(SysRole.class);
		query.where("roleCode", defcode, QueryAction.EQUAL);
		List<SysRole> list = (List<SysRole>) enterpriseService.query(query, 0);
		if (list != null && list.size() > 0){
			sysrole = list.get(0);
		}
		if(sysrole == null){
			return;
		}
		
		//查询所有用户列表
		List<SysRoleUser> result = new ArrayList<SysRoleUser>();
		
		//拼装角色id、用户id结果集
		QueryBuilder queryuser = new QueryBuilder(SysUser.class);
		queryuser.where("isFromWeb","1");
		List<SysUser> userList = (List<SysUser>) enterpriseService.query(queryuser, 0);
		if (userList != null && userList.size() > 0){
			for (SysUser sysUser : userList) {
				if(sysUser != null && StringUtils.isNotEmpty(sysUser.getUserId()) && StringUtils.isNotEmpty(sysrole.getRoleId())){
					//重复判断
					SysRoleUser model = getRoleUsersByUserId(sysUser.getUserId(),sysrole.getRoleId());
					if(model == null){
						model = new SysRoleUser();
						model.setRoleId(sysrole.getRoleId());
						model.setUserId(sysUser.getUserId());
						result.add(model);
					}
				}
						
			}
		}
		enterpriseService.save(result);
	}

	public SysRoleUser getRoleUsersByUserId(String userId,String roleId) throws Exception{
		//查询角色id配置
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(roleId)){
			return null;
		}
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("userId", userId, QueryAction.EQUAL);
		query.where("roleId", roleId, QueryAction.EQUAL);
		List<SysRoleUser> list = (List<SysRoleUser>)enterpriseService.query(query, 0);
		if (list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void addSysUserRoleId(SysRole sysRole, String[] users) throws Exception {
		// TODO Auto-generated method stub
		if(sysRole == null){
			return;
		}
		if(users == null || users.length <= 0){
			return;
		}
		//查询所有用户列表
		List<SysRoleUser> result = new ArrayList<SysRoleUser>();
		
		for (String userId : users) {
			if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(sysRole.getRoleId())){
				//重复判断
				SysRoleUser model = getRoleUsersByUserId(userId,sysRole.getRoleId());
				if(model == null){
					model = new SysRoleUser();
					model.setRoleId(sysRole.getRoleId());
					model.setUserId(userId);
					result.add(model);
				}
			}
					
		}
		enterpriseService.save(result);
	}
}

