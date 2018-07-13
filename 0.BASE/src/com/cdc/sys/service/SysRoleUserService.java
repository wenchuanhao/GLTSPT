package com.cdc.sys.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.form.SysRoleForm;
import com.trustel.common.ItemPage;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

public class SysRoleUserService implements ISysRoleUserService{
	
	private IEnterpriseService enterpriseService;
	
	public void addRoleUser(SysRoleUser model) throws Exception {
		enterpriseService.save(model);
	}

	public void delRoleUser(String userId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("userId", userId);
		enterpriseService.delete(query);	
	}
	
	public void deleteRoleUser(String userRoleId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("roleuserId", userRoleId);
		enterpriseService.delete(query);	
	}
	
	public void deleteRoleUserByRoleId(String roleId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("roleId", roleId);
		enterpriseService.delete(query);
	}
	
	public SysRoleUser getRoleUserByUserId(String userId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("userId", userId, QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return (SysRoleUser) list.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<SysRoleUser>getRoleUsersByUserId(String userId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("userId", userId, QueryAction.EQUAL);
		List<SysRoleUser> list = (List<SysRoleUser>)enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return list;
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<SysRoleUser>getRoleUsersByUserId(String userId,String roleId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("userId", userId, QueryAction.EQUAL);
		query.where("roleId", roleId, QueryAction.EQUAL);
		List<SysRoleUser> list = (List<SysRoleUser>)enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return list;
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<SysRoleUser> getRoleUsersByRoleId(String roleId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("roleId", roleId, QueryAction.EQUAL);
		return (List<SysRoleUser>)enterpriseService.query(query, 0);
	}

	public IEnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public ItemPage querySysRoleUser(SysRoleForm form) throws Exception {
		QueryBuilder query = new QueryBuilder(
				"SysRoleUser a,SysUser b,SysRole c,SysOrganization d,SysOrganization e");
		query.where("a.userId = b.userId");
		query.where("a.roleId = c.roleId");
		query.where("b.organizationId = d.organizationId");
		query.where("d.parentId = e.organizationId");
		if (null != form) {
			if (null != form.getUserName() && !form.getUserName().equals("")){
				query.where("b.userName", form.getUserName(), QueryAction.LIKE);
			}	
			if (null != form.getAccount() && !form.getAccount().equals("")){
				query.where("b.account", form.getAccount(), QueryAction.LIKE);
			}
			if("1".equals(form.getIsDisplay())){
				query.where("c.isDefaultRole='1'");
			}
			if("2".equals(form.getIsDisplay())){
				query.where("c.isDefaultRole='2'");
			}
			query.where("a.roleId", form.getRoleId(), QueryAction.EQUAL);
		}
		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}

	@Override
	public void deleteRoleUserByUserIdRoleId(String userId, String roleId) {
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(roleId)){
			return;
		}
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("roleId", roleId);
		query.where("userId", userId);
		enterpriseService.delete(query);
	}
	
}
