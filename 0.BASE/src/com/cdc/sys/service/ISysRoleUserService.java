package com.cdc.sys.service;

import java.util.List;

import com.cdc.sys.form.SysRoleForm;
import com.trustel.common.ItemPage;

import model.sys.entity.SysRoleUser;

public interface ISysRoleUserService {
	
	public void addRoleUser(SysRoleUser model) throws Exception;
	
	public void delRoleUser(String userId) throws Exception;
	
	public List<SysRoleUser> getRoleUsersByRoleId(String roleId) throws Exception;
	
	public SysRoleUser getRoleUserByUserId(String userId) throws Exception;
	
	public ItemPage querySysRoleUser(SysRoleForm form) throws Exception;
	
	public List<SysRoleUser>getRoleUsersByUserId(String userId) throws Exception;
	
	public List<SysRoleUser>getRoleUsersByUserId(String userId,String roleId) throws Exception;
	
	public void deleteRoleUser(String userRoleId) throws Exception;
	
	public void deleteRoleUserByRoleId(String roleId) throws Exception;

	public void deleteRoleUserByUserIdRoleId(String userId, String roleId);
}
