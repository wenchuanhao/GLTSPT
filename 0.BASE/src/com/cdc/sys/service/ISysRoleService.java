package com.cdc.sys.service;

import java.util.List;


import model.sys.entity.SysRole;
import model.sys.entity.SysUser;

import com.cdc.sys.form.SysRoleForm;
import com.trustel.common.ItemPage;

public interface ISysRoleService {

	/**
	 * 查询角色列表
	 */
	public ItemPage querySysRole(SysRoleForm form) throws Exception;

	/**
	 * 添加角色
	 */
	public void addSysRole(SysRole sysRole) throws Exception;

	/**
	 * 修改角色
	 */
	public void modifySysRole(SysRole sysRole) throws Exception;

	/**
	 * 根据ID获取角色
	 */
	public SysRole getSysRoleById(String roleId) throws Exception;

	/**
	 * 删除角色
	 */
	public void deleteSysRole(String roleId) throws Exception;

	/**
	 * 查询所有角色
	 */
	public List<SysRole> listAll(String userId) throws Exception;
	/**
	 * 查询所有角色
	 */
	public List<SysRole> listAll() throws Exception;

	/**
	 * 根据角色名称获取角色
	 */
	public SysRole queryRoleByName(String roleName) throws Exception;
	/**
	 * 根据角色编码获取角色
	 */
	public SysRole queryRoleByRoleCode(String roleCode) throws Exception;
	/**
	 * 查询角色下是否有用户
	 */
	public List<SysUser> checkSubUser(String sysRoles) throws Exception;
	/**
	 * 查询角色列表(非系统用户)
	 */
	public ItemPage querySysRoleInNotIstrator(SysRoleForm form,String roleIds) throws Exception;
	/**
	 * 查询所有角色(当前用户拥有的角色组角色权限)
	 */
	public List<SysRole> listAll(String userId,String orgId) throws Exception;
	public List<SysRole> listAllByCondition(SysRole model,String roleId) throws Exception;
	public List<SysRole> listAllByCondition(SysRole model,String roleid,List<String> roleids) throws Exception;
	
	public List<SysRole> getRoleByIds(String ids) throws Exception;
	/**
	 * 按类型查所有角色
	 * @param roleType
	 * @return
	 * @throws Exception
	 */
	public List<SysRole> listAllByType(String roleType) throws Exception;
	
	public List<SysRole> listAllQuery() throws Exception;
	
	public List<SysRole> queryRoleByIds(String ids) throws Exception;

	/**
	 * 根据角色查询
	 * @param flag
	 * @param roleId
	 * @return
	 */
	public List<SysRole> listAllByType(String roleType, String roleId);
}
