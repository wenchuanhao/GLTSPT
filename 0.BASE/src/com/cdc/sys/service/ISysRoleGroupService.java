package com.cdc.sys.service;

import java.util.List;

import org.trustel.service.ServiceException;
import org.trustel.service.sql.QueryBuilder;


import model.sys.entity.RRoleGroup;
import model.sys.entity.RUserGroup;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleGroup;
import model.sys.entity.SysRoleUser;
import model.sys.entity.UserRoleGroup;

import com.cdc.sys.form.SysRoleForm;
import com.cdc.sys.form.SysRoleGroupForm;
import com.trustel.common.ItemPage;

/**
 * 
 * 
 * @Description:
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-29 下午5:40:08
 * @UpdateRemark:
 * @Version: V1.0
 */
public interface ISysRoleGroupService {

	/**
	 * 新增实体
	 * @param item
	 * @throws ServiceException
	 */
	public void saveEntity(Object item)throws ServiceException;
	/**
	 * 查询
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public ItemPage querySysRoleGroup(SysRoleGroupForm form,String userId) throws Exception;

	/**
	 * 添加
	 * 
	 * @param sysRoleGroup
	 * @throws Exception
	 */
	public String addSysRoleGroup(SysRoleGroup sysRoleGroup) throws Exception;

	/**
	 * 修改
	 * 
	 * @param sysRoleGroup
	 * @throws Exception
	 */
	public void updateSysRoleGroup(SysRoleGroup sysRoleGroup) throws Exception;

	/**
	 * 按照id获取角色组
	 * 
	 * @param sysRoleGroupid
	 * @return
	 * @throws Exception
	 */
	public SysRoleGroup getSysRoleGroupById(String sysRoleGroupid) throws Exception;

	/**
	 * 删除角色组
	 * 
	 * @param sysRoleGroupid
	 * @throws Exception
	 */
	public void deleteSysRoleGroup(String sysRoleGroupid) throws Exception;

	/**
	 * 列出角色和角色组关系 （对象集合）
	 * 
	 * @param roleGroupId
	 * @return
	 * @throws Exception
	 */
	public List<RRoleGroup> getRoleAndRoleGroupR(String roleGroupId) throws Exception;

	/**
	 * 列出角色和角色组关系 （角色id String集合）
	 * 
	 * @param roleGroupId
	 * @return
	 * @throws Exception
	 */
	public List<String> getStrRoleAndRoleGroupR(String roleGroupId) throws Exception;

	/**
	 * 添加角色组
	 * 
	 * @param roleGroupId
	 * @param roles
	 * @throws Exception
	 */
	public void addRRoleGroup(String roleGroupId, String roles) throws Exception;

	/**
	 * 删除角色组
	 * 
	 * @param roleGroupId
	 * @throws Exception
	 */
	public void deleteRRoleGroup(String roleGroupId) throws Exception;

	/**
	 * 按照角色组id查询角色组
	 * 
	 * @param roleGroupId
	 * @throws Exception
	 */
	public List<RUserGroup> listRUserGroup(String roleGroupId) throws Exception;

	/**
	 * 按照角色组id和用户id查询
	 * 
	 * @param roleGroupId
	 * @throws Exception
	 */
	public List<RUserGroup> listRUserGroupByU_RG(String roleGroupId, String userId) throws Exception;

	/**
	 * 按照用户id和角色组添加
	 * 
	 * @param rUserGroup
	 * @throws Exception
	 */
	public void addRUserGroup(RUserGroup rUserGroup) throws Exception;

	/**
	 * 按照用户id和角色组删除
	 * 
	 * @param rUserGroup
	 * @throws Exception
	 */
	public void deleteRUserGroupByU_RG(String roleGroupId, String userId) throws Exception;

	/**
	 * 按照用户的id获取用的角色组
	 * 
	 * @param userId
	 * @return SysRoleGroup [0] ,SysRole [1],RRoleGroup[2]
	 * @throws Exception
	 */

	public List<?> listRoleGroupByUserId(String userId) throws Exception;
	
	public void GroupUser( String userId, String roleGroupId,String flag) throws Exception;
	
	public void addUserGroup(UserRoleGroup userRoleGroup) throws Exception;
	/**
	 * 查询我的分配记录
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserRoleGroup> queryUserGroup(String userId) throws Exception;
	
	public void undo(String userId,String groupId) throws Exception;
	
	public List<?> getRole(String roleGroupId)
			throws Exception;
	/**
	 * 根据角色组查询角色
	 * @param roleGroupid
	 * @return
	 */
	public List<SysRole> querySysRoleByGroupId(String roleGroupid);

	/**
	 * 查询角色组不包含的角色
	 * @param list
	 * @param roleName 
	 * @return
	 */
	public List<SysRole> querySysRoleNotInList(List<SysRole> list, String roleName);
	/**
	 * 删除角色组
	 * @param roleGroupid
	 */
	public void delRoleById(String roleGroupid);
	/**
	 * 角色组重复判断
	 * @param roleGroupcode
	 * @param roleGroupname
	 * @param roleGroupid 
	 * @return
	 */
	public String querySysRoleGroupByCodeOrName(String roleGroupid, String roleGroupcode, String roleGroupname);
	/**
	 * 查询角色组表
	 * @param roleGroupid
	 * @return
	 */
	public List<SysRoleGroup> querySysRoleGroupByGroupId(String roleGroupid);
	/**
	 * 根据角色组id查询该角色组下所有用户
	 * @param roleId
	 * @return
	 */
	public List<SysRoleUser> getRoleUsersByGroupRoleId(String roleGroupid);
	/**
	 * 查询已配置的人员列表
	 * @param form
	 * @return
	 */
	public ItemPage querySysRoleUser(SysRoleForm form);
	/**
	 * 删除角色组角色配置
	 * @param roleGroupid
	 * @param roleId
	 */
	public void delGroupRoleById(String roleGroupid, String roleId);
	
}
