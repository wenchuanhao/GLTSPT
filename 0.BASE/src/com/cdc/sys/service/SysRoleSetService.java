package com.cdc.sys.service;

import java.util.List;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import model.sys.entity.SysRole;
import model.sys.entity.SysRoleSet;

public class SysRoleSetService implements ISysRoleSetService{
	private IEnterpriseService enterpriseService;
	
	public void addSysRoleSet(SysRoleSet sysRoleSet) throws Exception {
		enterpriseService.save(sysRoleSet);
		
	}

	public void delSysRoleSet(String roleId,String pid) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleSet.class);
		query.where("roleId", roleId);
		query.where("parentId", pid);
		enterpriseService.delete(query);
		
	}
	
	public void delSysRoleSet(String roleId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleSet.class);
		query.where("roleId", roleId);
		enterpriseService.delete(query);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<SysRoleSet> getListSysRoleSetByRoleId(String roleId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleSet.class);
		query.where("roleId", roleId);
		
		List<?> list = enterpriseService.query(query, 0);
		if(list!=null){
			return (List<SysRoleSet>)list;
		}
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<SysRoleSet> getListSysRoleSetByRoleIdJL(String roleId) throws Exception {
		QueryBuilder query = new QueryBuilder("SysRoleSet t,SysRole r");
		query.where("t.parentId=r.roleId");
		query.where("t.roleId",roleId,QueryAction.EQUAL);
		List<?> list = enterpriseService.query(query, 0);
		if(list!=null){
			return (List<SysRoleSet>)list;
		}
		return null;
		
	}
	@SuppressWarnings("unchecked")
	public List<SysRoleSet> getListSysRoleSetByRoleIdJLs(String roleId,List<String> lists) throws Exception {
		QueryBuilder query = new QueryBuilder("SysRoleSet t,SysRole r");
		query.where("t.parentId=r.roleId");
		if(lists!=null && lists.size()>0){
			for(int i=0;i<lists.size();i++){
				if(lists.get(i)!=null && !lists.get(i).equals("")){
					query.where("t.parentId",lists.get(i),QueryAction.NOEQUAL);
				}
			}
		}
		List<?> list = enterpriseService.query(query, 0);
		if(list!=null){
			return (List<SysRoleSet>)list;
		}
		return null;
		
	}
	public IEnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
	
}
