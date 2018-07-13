package com.cdc.sys.service;

import java.util.List;

import model.sys.entity.SysRoleSet;

public interface ISysRoleSetService {
	
	public void addSysRoleSet(SysRoleSet sysRoleSet) throws Exception;
	
	public void delSysRoleSet(String roleId,String pid) throws Exception;
	
	public void delSysRoleSet(String roleId) throws Exception;
	
	public List<SysRoleSet> getListSysRoleSetByRoleId(String roleId) throws Exception;
	
	public List<SysRoleSet> getListSysRoleSetByRoleIdJL(String roleId) throws Exception;
	
	public List<SysRoleSet> getListSysRoleSetByRoleIdJLs(String roleId,List<String> lists) throws Exception;
}
