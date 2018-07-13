package com.cdc.sys.service;

import java.util.List;

import model.sys.entity.SysModule;
import model.sys.entity.SysOrganization;
import model.sys.entity.SysRoleOrg;
import model.sys.entity.SysRolePrivilges;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import com.cdc.sys.form.SysModuleForm;
import com.cdc.sys.form.SysUserForm;
import com.trustel.common.ItemPage;

/**
 * 
 * @author sunsf
 * 
 */
public interface ISysPrivilegesService {
	/**
	 * 查询所有的模块
	 * 
	 * @return
	 */
	public List<SysModule> queryAll()throws Exception;
	
	
	/**
	 * 根据条件查询模块
	 * 
	 * @return
	 */
	public List<SysModule> queryAll(SysModuleForm form)throws Exception;
	
	

	/**
	 * 为角色 添加权限
	 * 
	 * @param roleModule
	 */

	public void addRolePrivilege(SysRolePrivilges roleModule)throws Exception;

	/**
	 * 为角色 添加权限
	 * 
	 * @param roleModule
	 */

	public void addRolePrivilege(List<SysRolePrivilges> roleModules)throws Exception;

	/**
	 * 按照角色id查询 角色,权限
	 * 
	 * @param srId
	 * @return List<SystemRoleModule>
	 */
	public List<SysRolePrivilges> queryRoleModuleByRoleId(String roleId)throws Exception;
	
	/*public List queryTeamRoleModuleByRoleId(String teamRoleId)throws Exception;*/
	/**
	 * 按照团队角色id查询 角色,权限zj
	 * 
	 * @param srId
	 * @return List<SystemRoleModule>
	 */
	/*public List<TeamRolePrivilges> queryTeamRoleByRoleId(String roleId)throws Exception;*/
	
	
	/**
	 * 按照角色id删除 团队角色和权限的关联关系zj
	 * 
	 * @param srId
	 */
	/*public void deleteTeamRoleModuleByRoleId(String roleId)throws Exception;*/
	
	
	/**
	 * 为团队角色 添加权限zj
	 * 
	 * @param roleModule
	 */

   /*	public void addTeamRolePrivilege(List<TeamRolePrivilges> roleModules)throws Exception;*/

	/**
	 * 按照角色id用户id查询 角色,权限
	 * 
	 * @param srId
	 * @param id
	 * @return List<SystemRoleVisitor>
	 */
	public List<SysRoleUser> queryRoleVisitorByU_RId(String roleId, String userId)throws Exception;

	/**
	 * 按照角色id删除 角色和权限的关联关系
	 * 
	 * @param srId
	 */
	public void deleteRoleModuleByRoleId(String roleId)throws Exception;
	/**
	 * 按照角色id删除 角色和区域的关联关系
	 * 
	 * @param srId
	 */
	public void deleteRoleOrgByRoleId(String roleId)throws Exception;

	/**
	 * 查询 角色,权限
	 * 
	 * @return List<SystemRoleModule>
	 */
	public List<SysRolePrivilges> queryRoleModule()throws Exception;

	/**
	 * 按照角色id查询角色用户
	 * 
	 * @return
	 */
	public List<SysRoleUser> queryRoleVisitorByRoleId(String roleId)throws Exception;

	/**
	 * 为角色 添加用户
	 * 
	 * @param roleVisitor
	 * 
	 */
	public void addRoleVisitor(SysRoleUser roleVisitor)throws Exception;

	/**
	 * 为角色 添加用户
	 * 
	 * @param roleVisitor
	 * 
	 */
	public void addRoleVisitor(List<SysRoleUser> roleVisitors)throws Exception;

	/**
	 * 按照角色id和用户id删除 角色和用户的关联关系
	 * 
	 * @param srId
	 * 
	 * @param userId
	 */
	public void deleteRoleVisitorBySrId(String srId, String userId)throws Exception;

	/**
	 * 根据用户id查找用户的权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysRoleUser> queryRoleVisitorByUserId(String userId)throws Exception;

	public List<String> queryRoleIdByUserId(String userId) throws Exception;

	public ItemPage queryUser(SysUserForm form, String flag,String orgIds) throws Exception;
	public ItemPage queryUser(SysUserForm form, String flag) throws Exception;
	
	/**
	 * 按照角色id组和用户id删除 角色和用户的关联关系
	 * 
	 * @param srId
	 * 
	 * @param userId
	 */
	public void deleteRoleVisitorByRIdUId(String srId, String userId)throws Exception;
	
	public void addRoleVisitorIsList(List<SysRoleUser> roleVisitor)throws Exception;
	
	public List<SysRoleOrg> queryOrgByRoleId(String roleId) throws Exception;
	
	public List<SysOrganization> queryOrg()throws Exception;
	
	public void addRoleOrg(List<SysRoleOrg> roleOrg);
	
	public String queryOrgIdsByRoleIds(String roleId) throws Exception;
	
	public List<?> queryRolesByUserId(String userId) throws Exception;

	
	/**
	 * 按照模块code 查询对应的用户List
	 * @param moduleCode 模块code
	 */
	public List<SysUser> queryUsersByModuleCode(String moduleCode) ;
	

}
