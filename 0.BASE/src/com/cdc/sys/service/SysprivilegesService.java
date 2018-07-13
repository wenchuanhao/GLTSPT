package com.cdc.sys.service;

import java.util.ArrayList;
import java.util.List;

import model.sys.entity.SysModule;
import model.sys.entity.SysOrganization;
import model.sys.entity.SysRoleOrg;
import model.sys.entity.SysRolePrivilges;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.form.SysModuleForm;
import com.cdc.sys.form.SysUserForm;
import com.cdc.system.core.cache.SysParamHelper;
import com.trustel.common.ItemPage;

public class SysprivilegesService implements ISysPrivilegesService {

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public void addRoleVisitor(SysRoleUser roleVisitor) {
		enterpriseService.save(roleVisitor);
	}
	
	public void addRoleVisitorIsList(List<SysRoleUser> roleVisitor) {
		enterpriseService.save(roleVisitor);
	}


	@SuppressWarnings("unchecked")
	public List<SysModule> queryAll()throws Exception {
		QueryBuilder query = new QueryBuilder("SysModule m");
		query.where("moduleName", "无", QueryAction.NOEQUAL);
		//query.where("url is not null");
		query.orderBy("m.seq");
		List<SysModule> modules = (List<SysModule>) enterpriseService.query(query, 0);
		return modules;
	}
	
	/**
	 * 根据条件查询模块
	 * 
	 * @return
	 */
	public List<SysModule> queryAll(SysModuleForm form)throws Exception{
		QueryBuilder query = new QueryBuilder("SysModule");
		query.orderBy("seq");
		if(null!=form.getModuleName() && !"".equals(form.getModuleName())){
			query.where("moduleName", form.getModuleName().trim(),QueryAction.LIKE);
		}else{
			query.where("moduleName", "无", QueryAction.NOEQUAL);
		}
		List<SysModule> modules = (List<SysModule>) enterpriseService.query(query, form).getItems();
		System.out.println(modules.size());
		return modules;
	}
	
	@SuppressWarnings("unchecked")
	public List<SysOrganization> queryOrg()throws Exception {
		SysParameter param=SysParamHelper.getSysParamByCode("ROOTID");
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("orgId", param.getParameterValue(), QueryAction.NOEQUAL);
		query.orderBy("orgOrder");
		return (List<SysOrganization>) enterpriseService.query(query, 0);
	}
	
	@SuppressWarnings("unchecked")
	public List<SysRoleOrg> queryOrgByRoleId(String roleId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleOrg.class);
		query.where("roleId", roleId, QueryAction.EQUAL);
		return (List<SysRoleOrg>) enterpriseService.query(query, 0);
	}
	public void addRolePrivilege(SysRolePrivilges roleModule)throws Exception {
		enterpriseService.save(roleModule);

	}
	public void addRoleOrg(SysRoleOrg roleOrg)throws Exception {
		enterpriseService.save(roleOrg);

	}

	public void addRoleUser(SysRoleUser roleVisitor)throws Exception {
		enterpriseService.save(roleVisitor);

	}

	@SuppressWarnings("unchecked")
	public List<SysRolePrivilges> queryRoleModule() throws Exception{
		QueryBuilder query = new QueryBuilder(SysRolePrivilges.class);
		List<SysRolePrivilges> roleModules = (List<SysRolePrivilges>) enterpriseService.query(query, 0);
		return roleModules;
	}

	public void deleteRoleModuleByRoleId(String roleId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRolePrivilges.class);
		query.where("roleId", roleId, QueryAction.EQUAL);
		enterpriseService.delete(query);
	}
	
	public void deleteRoleVisitorBySrId(String srId, String userId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("roleId", srId, QueryAction.EQUAL);
		query.where("userId", userId, QueryAction.EQUAL);
		enterpriseService.delete(query);

	}

	@SuppressWarnings("unchecked")
	public List<SysRolePrivilges> queryRoleModuleByRoleId(String roleId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRolePrivilges.class);
		query.where("roleId", roleId, QueryAction.EQUAL);
		return (List<SysRolePrivilges>) enterpriseService.query(query, 0);
		/**
		StringBuilder querySql = new StringBuilder();
		querySql.append(" select t4.MODULE_NAME, t4.MODULE_CODE, t4.PARENT_CODE, t3.role_privilges_id ");
		querySql.append(" from (select distinct t.MODULE_NAME, t.MODULE_CODE, t.PARENT_CODE, t.SEQ  from sys_module t, sys_role_user t1, sys_role_privilges t2");
		querySql.append(" where 1 = 1 and t2.module_code = t.module_code and t1.role_id = t2.role_id ) t4 left join sys_role_privilges t3  on t4.MODULE_CODE = t3.MODULE_CODE ");
		querySql.append(" and t3.role_id= ? order by t4.SEQ asc ");
		SQLQuery sqlQuery = enterpriseService.getSessions().createSQLQuery(querySql.toString());
        sqlQuery.setString(0, roleId);
        return sqlQuery.list();
        */
	}
	
	/**
	 * 按照角色id删除 团队角色和权限的关联关系zj
	 * 
	 * @param srId
	 */
	/*public void deleteTeamRoleModuleByRoleId(String roleId)throws Exception{
		QueryBuilder query = new QueryBuilder(TeamRolePrivilges.class);
		query.where("teamRoleId", roleId, QueryAction.EQUAL);
		enterpriseService.delete(query);
	}*/
	
	/**
	 * 按照团队角色id查询 角色,权限zj
	 * 
	 * @param srId
	 * @return List<SystemRoleModule>
	 */
	/*public List<TeamRolePrivilges> queryTeamRoleByRoleId(String roleId)throws Exception{
		QueryBuilder query = new QueryBuilder(TeamRolePrivilges.class);
		query.where("teamRoleId", roleId, QueryAction.EQUAL);
		return (List<TeamRolePrivilges>) enterpriseService.query(query, 0);
		*//**
		StringBuilder querySql = new StringBuilder();
		querySql.append(" select t4.MODULE_NAME, t4.MODULE_CODE, t4.PARENT_CODE, t3.team_role_privilges_id ");
		querySql.append(" from (select distinct t.MODULE_NAME, t.MODULE_CODE, t.PARENT_CODE, t.SEQ  from sys_module t, team_role t1, team_role_privilges t2");
		querySql.append(" where 1 = 1 and t2.module_code = t.module_code and t1.team_role_id = t2.team_role_id ) t4 left join team_role_privilges t3  on t4.MODULE_CODE = t3.MODULE_CODE ");
		querySql.append(" and t3.team_role_id= ? order by t4.SEQ asc ");
		SQLQuery sqlQuery = enterpriseService.getSessions().createSQLQuery(querySql.toString());
        sqlQuery.setString(0, roleId);
        return sqlQuery.list();
        *//*
	}*/

	@SuppressWarnings("unchecked")
	public List<SysRoleUser> queryRoleVisitorByU_RId(String roleId, String userId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("roleId", roleId, QueryAction.EQUAL);
		query.where("userId", userId, QueryAction.EQUAL);
		return (List<SysRoleUser>) enterpriseService.query(query, 0);
	}

	@SuppressWarnings("unchecked")
	public List<SysRoleUser> queryRoleVisitorByRoleId(String roleId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("roleId", roleId, QueryAction.EQUAL);
		return (List<SysRoleUser>) enterpriseService.query(query, 0);
	}

	@SuppressWarnings("unchecked")
	public List<SysRoleUser> queryRoleVisitorByUserId(String userId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("userId", userId, QueryAction.EQUAL);
		return (List<SysRoleUser>) enterpriseService.query(query, 0);
	}

	public ItemPage queryUser(SysUserForm form, String flag,String orgIds) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		if (flag.equals("1"))
			query.where("userId in(" + form.getUserIds() + ")");
		if (flag.equals("0"))
			query.where("userId not in(" + form.getUserIds() + ")");
		if (null != form) {
			if (null != form.getAccount() && !form.getAccount().equals(""))
				query.where("account", form.getAccount(), QueryAction.LIKE);
			if (null != form.getUserName() && !form.getUserName().equals(""))
				query.where("userName", form.getUserName(), QueryAction.LIKE);
		}
		query.where("organizationId in(" + orgIds + ")");
		return enterpriseService.query(query, form);
	}
	
	public ItemPage queryUser(SysUserForm form, String flag) throws Exception {
		QueryBuilder query = new QueryBuilder(SysUser.class);
		if (flag.equals("1"))
			query.where("userId in(" + form.getUserIds() + ")");
		if (flag.equals("0"))
			query.where("userId not in(" + form.getUserIds() + ")");
		if (null != form) {
			if (null != form.getAccount() && !form.getAccount().equals(""))
				query.where("account", form.getAccount(), QueryAction.LIKE);
			if (null != form.getUserName() && !form.getUserName().equals(""))
				query.where("userName", form.getUserName(), QueryAction.LIKE);
		}
		return enterpriseService.query(query, form);
	}
	@SuppressWarnings("unchecked")
	public List<String> queryRoleIdByUserId(String userId) throws Exception {
		QueryBuilder builder=new QueryBuilder(SysRoleUser.class);
		builder.select("roleId");
		builder.where("userId", userId);
		List<String> list=(List<String>) enterpriseService.query(builder, 0);
		String roleIds = "";
		for(String group : list){
			roleIds += "'" + group + "',";
		}
		if (!roleIds.equals("")) {
			roleIds = roleIds.substring(0, roleIds.length() - 1);
		} else {
			roleIds = "1";
		}
		QueryBuilder query = new QueryBuilder("RUserGroup s ,RRoleGroup r");
		query.select("r.roleId");
		query.where("s.roleId in("+roleIds+")");
		query.where("s.roleGroupId=r.roleGroupId");
		return (List<String>) enterpriseService.query(query, 0);
	}

	public void addRolePrivilege(List<SysRolePrivilges> roleModules) {
		enterpriseService.save(roleModules);
	}
	
	/**
	 * 为团队角色 添加权限zj
	 * 
	 * @param roleModule
	 */

	/*public void addTeamRolePrivilege(List<TeamRolePrivilges> roleModules)throws Exception{
		enterpriseService.save(roleModules);
	}*/
	
	
	public void addRoleOrg(List<SysRoleOrg> roleOrg) {
		enterpriseService.save(roleOrg);
	}
	public void addRoleVisitor(List<SysRoleUser> roleVisitors) throws Exception{
		enterpriseService.save(roleVisitors);
	}
	public void deleteRoleVisitorByRIdUId(String srId, String userId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("userId", userId, QueryAction.EQUAL);
		query.where("roleId in(" + srId+ ")");
		enterpriseService.delete(query);

	}
	
	public void deleteRoleOrgByRoleId(String roleId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleOrg.class);
		query.where("roleId", roleId, QueryAction.EQUAL);
		enterpriseService.delete(query);
	}
	@SuppressWarnings("unchecked")
	public String queryOrgIdsByRoleIds(String roleId) throws Exception {
		QueryBuilder query=new QueryBuilder(SysRoleOrg.class);
		query.select("orgId");
		query.where("roleId",roleId);
		List<String> orgIds= (List<String>) enterpriseService.query(query, 0);
		String orgs="";
		for(String orgId:orgIds){
			orgs+=getOrgIdByPerId(orgId);
		}
		return orgs;
	}
	public List<?> queryRolesByUserId(String userId) throws Exception{
		QueryBuilder query=new QueryBuilder("SysRole r,SysRoleUser rs");
		query.where("rs.userId",userId);
		query.where("r.roleId=rs.roleId");
		return enterpriseService.query(query, 0);
	}
	/**
	 * 根据组织Id获取其所有子组织的Id链接字符串（包括本身Id）
	 */
	@SuppressWarnings("unchecked")
	public String getOrgIdByPerId(String orgId) throws Exception {
//		QueryBuilder query = new QueryBuilder(SysOrganization.class);
//		List<SysOrganization> orgList = (List<SysOrganization>) enterpriseService
//				.query(query, 0);
//		List<SysOrganization> sublist = new ArrayList<SysOrganization>();
//		List<SysOrganization> newList = new ArrayList<SysOrganization>();
//		String orgIds = "";
//		orgIds = "'" + orgId + "',";
//		for (SysOrganization org : orgList) {
//			if (org.getParentId().equals(orgId)) {
//				sublist.add(org);
//				newList.add(org);
//			}
//		}
//		if (sublist.size() != 0) {
//			List<SysOrganization> allSubList = recursive(orgList, sublist,
//					newList);
//			for (SysOrganization org : allSubList) {
//				orgIds += "'" + org.getOrgId() + "',";
//			}
//		}
//		return orgIds;
		//递归查询
		List<SysOrganization> list=	enterpriseService.getSessions().createSQLQuery("select {t.*} from SYS_ORGANIZATION t start with t.ORGID="+orgId+"connect by prior t.ORGID=t.PARENT_ID")
				.addEntity("t",SysOrganization.class).list();
		String orgIds = "";
		for(SysOrganization org:list){
			orgIds += "'"+ org.getOrganizationId() + "',";
		}
			return orgIds;
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

	/*public List<TeamRolePrivilges> queryTeamRoleModuleByRoleId(String teamRoleId)
			throws Exception {
		QueryBuilder query = new QueryBuilder(TeamRolePrivilges.class);
		query.where("teamRoleId", teamRoleId, QueryAction.EQUAL);
		return (List<TeamRolePrivilges>) enterpriseService.query(query, 0);
		
	}*/
	
	
	/**
	 * 按照模块code 查询对应的用户List
	 * @param moduleCode 模块code
	 */
	public List<SysUser> queryUsersByModuleCode(String moduleCode) {
		List<SysUser> list = new ArrayList<SysUser>();
		Session session = enterpriseService.getSessions();
		String sql = " select distinct u.user_id " +
		" from sys_role_user ru  join sys_user u  on ru.user_id = u.user_id "+
		" where ru.role_id in ( "+
		" select rp.role_id   from sys_role_privilges rp  where rp.module_code = :module_code  " + 
		" ) ";
		SQLQuery q = session.createSQLQuery(sql);
		q.setParameter("module_code", moduleCode);
		List<String> userIdList = q.list();
		if(userIdList.isEmpty()) return list;
		
		//如果有用户id存在
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("userId", userIdList.toArray(new String[]{})  );
		list = (List<SysUser>) enterpriseService.query(query, 0);
		return list;
	}
	
}
