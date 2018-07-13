package com.cdc.sys.service;

import java.util.ArrayList;
import java.util.List;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysUser;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.form.SysRoleForm;
import com.trustel.common.ItemPage;

public class SysRoleService implements ISysRoleService {

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	/**
	 * 查询角色列表
	 */
	public ItemPage querySysRole(SysRoleForm form) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		if (null != form) {
			if (!"".equals(form.getRoleName()))
				query.where("roleName", form.getRoleName(), QueryAction.LIKE);
			if (!"".equals(form.getRoleCode()))
				query.where("roleCode", form.getRoleCode(),QueryAction.LIKE);
			if (!"".equals(form.getIsDefaultRole()))
				query.where("isDefaultRole", form.getIsDefaultRole(),QueryAction.EQUAL);
		}

		query.orderBy("createTime", false);

		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}
	/**
	 * 查询角色列表(非系统用户)
	 */
	public ItemPage querySysRoleInNotIstrator(SysRoleForm form,String roleIds) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		if (null != form) {
			if (!"".equals(form.getRoleName()))
				query.where("roleName", form.getRoleName(), QueryAction.LIKE);
			if (!"".equals(form.getAllowUpdate()) && !"-1".equals(form.getAllowUpdate()))
				query.where("allowUpdate", form.getAllowUpdate());
			if (!"".equals(form.getSysRoleGroupid()) && form.getSysRoleGroupid() != null)
				query.where("sysRoleGroupid", form.getSysRoleGroupid());
		}
		query.where("roleId in (" + roleIds + ")");
		query.orderBy("createTime", false);

		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}
	/**
	 * 添加角色
	 */
	public void addSysRole(SysRole sysRole) throws Exception {
		enterpriseService.save(sysRole);

	}
	/**
	 * 修改角色
	 */
	public void modifySysRole(SysRole sysRole) throws Exception {
		enterpriseService.updateObject(sysRole);

	}
	/**
	 * 根据ID获取角色
	 */
	public SysRole getSysRoleById(String roleId) throws Exception {
		return (SysRole) enterpriseService.getById(SysRole.class, roleId);

	}
	/**
	 * 删除角色
	 */
	public void deleteSysRole(String roleId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		query.where("roleId", roleId);
		enterpriseService.delete(query);
	}
	/**
	 * 查询所有角色(当前用户拥有的角色组角色权限)
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> listAll(String userId) throws Exception {
		QueryBuilder query = new QueryBuilder("SysRoleUser ru,RUserGroup s,RRoleGroup r,SysRole role");
		query.where("ru.userId", userId);
		query.where("ru.roleId=s.roleId");
		query.where("s.roleGroupId=r.roleGroupId");
		query.where("r.roleId=role.roleId");
		List<Object[]> list = (List<Object[]>) enterpriseService.query(query, 0);
		List<SysRole> tempRole = new ArrayList<SysRole>();
		if (list != null){
			for(Object[] obj:list){
				tempRole.add((SysRole) obj[3]);
			}
		}
		return tempRole;
	}
	/**
	 * 查询所有角色(当前用户拥有的角色组角色权限)
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> listAll(String userId,String orgId) throws Exception {
		QueryBuilder query = new QueryBuilder("SysRoleUser ru,RUserGroup s,RRoleGroup r,SysRole role,SysRoleOrg ro");
		query.where("ru.userId", userId);
		query.where("ru.roleId=s.roleId");
		query.where("s.roleGroupId=r.roleGroupId");
		query.where("r.roleId=role.roleId");
		query.where("ro.orgId in ("+getOrgIdByPerId(orgId)+")");
		query.where("ro.roleId=role.roleId");
		List<Object[]> list = (List<Object[]>) enterpriseService.query(query, 0);
		List<SysRole> tempRole = new ArrayList<SysRole>();
		if (list != null){
			for(Object[] obj:list){
				if(!tempRole.contains((SysRole)obj[3]))
					tempRole.add((SysRole) obj[3]);
			}
		}
		return tempRole;
	}
	/**
	 * 根据组织Id获取其所有子组织的Id链接字符串（包括本身Id）
	 */
	@SuppressWarnings("unchecked")
	public String getOrgIdByPerId(String orgId) throws Exception {
		List<SysOrganization> list=	enterpriseService.getSessions().createSQLQuery("select {t.*} from SYS_ORGANIZATION t start with t.ORGID="+orgId+"connect by prior t.ORGID=t.PARENT_ID")
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
	/**
	 * 查询所有角色
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> listAll() throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		 query.where("isDefaultRole","2",QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null)
			return ((List<SysRole>) list);
		return null;
	}
	/**
	 * 查询所有角色
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> listAllQuery() throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null)
			return ((List<SysRole>) list);
		return null;
	}
	/**
	 * 查询所有角色
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> listAllByType(String roleType) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
			query.where("isDefaultRole",roleType,QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null)
			return ((List<SysRole>) list);
		return null;
	}
	/**
	 * 查询所有角色__按条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> listAllByCondition(SysRole model,String roleid) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
	         query.where("roleId",roleid,QueryAction.NOEQUAL);
		if(model!=null){
			if(model.getRoleName()!=null && !model.getRoleName().equals("")){
				query.where("roleName",model.getRoleName(),QueryAction.LIKE);
			}
		}
		List<?> list = enterpriseService.query(query, 0);
		if (list != null)
			return ((List<SysRole>) list);
		return null;
	}
	/**
	 * 查询所有角色__按条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> listAllByCondition(SysRole model,String roleid,List<String> roleids) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
	         query.where("roleId",roleid,QueryAction.NOEQUAL);
	     if(roleids!=null && roleids.size()>0){
	    	 for(int i=0;i<roleids.size();i++){
	    		 if(roleids.get(i)!=null && !roleids.get(i).equals("")){
	    			 query.where("roleId",roleids.get(i),QueryAction.NOEQUAL);
	    		 }
	    	 }
	     }
		if(model!=null){
			if(model.getRoleName()!=null && !model.getRoleName().equals("")){
				query.where("roleName",model.getRoleName(),QueryAction.LIKE);
			}
		}
		List<?> list = enterpriseService.query(query, 0);
		if (list != null)
			return ((List<SysRole>) list);
		return null;
	}
	/**
	 * 根据角色名称获取角色
	 */
	public SysRole queryRoleByName(String roleName) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		query.where("roleName", roleName, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return (SysRole) list.get(0);
		return null;
	}
	/**
	 * 根据角色编码获取角色
	 */
	public SysRole queryRoleByRoleCode(String roleCode) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		query.where("roleCode", roleCode, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return (SysRole) list.get(0);
		return null;
	}
	/**
	 * 查询角色下是否有用户
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> checkSubUser(String sysRoles) throws Exception {
		QueryBuilder query = new QueryBuilder("SysUser s,SysRole r,SysRoleUser u");
		query.where("s.userId=u.userId and r.roleId=u.roleId");
		query.where("s.");
		query.where("sysRoleGroupid in (" + sysRoles + ")");
		return (List<SysUser>) enterpriseService.query(query, 0);
	}

	public List<SysRole> getRoleByIds(String ids) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		query.where("roleId in ("+ids+")");
		List<SysRole> orgList = (List<SysRole>) enterpriseService
				.query(query, 0);
		if(orgList != null && orgList.size() > 0) {
			return orgList;
		} else {
			return null;
		}
	}
	
	public List<SysRole> queryRoleByIds(String ids) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		query.where("isDefaultRole='1'");
		if(ids!=null && !ids.trim().equals("")){
			query.where("roleId not in ("+ids+")");
		}
		
		List<SysRole> orgList = (List<SysRole>) enterpriseService
				.query(query, 0);
		if(orgList != null && orgList.size() > 0) {
			return orgList;
		} else {
			return null;
		}
	}
	@Override
	public List<SysRole> listAllByType(String roleType, String roleId) {
			QueryBuilder query = new QueryBuilder(SysRole.class);
				query.where("isDefaultRole",roleType,QueryAction.EQUAL);
				query.where("roleId",roleId,QueryAction.EQUAL);
			List<?> list = enterpriseService.query(query, 0);
			if (list != null)
				return ((List<SysRole>) list);
			return null;
	}
}
