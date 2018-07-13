package com.cdc.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import model.sys.entity.SysUserTemp;

import com.cdc.sys.form.DicFailImportObject;
import com.cdc.sys.form.SysUserForm;
import com.trustel.common.ItemPage;

public interface ISysUserService {
	/**
	 * 查询所有用户
	 */
	public ItemPage querySysUser(SysUserForm form) throws Exception;
	/**
	 * 查询所有用户
	 */
	public ItemPage querySysUser2(SysUserForm form) throws Exception;
	/**
	 * 通过组织编号查询用户
	 */
	public ItemPage querySysUser2(String oraid,SysUserForm form) throws Exception;
	/**
	 * 添加用户
	 */
	public void addSysUser(SysUser sysUser) throws Exception;

	/**
	 * 修改用户
	 */
	public void modifySysUser(SysUser sysUser) throws Exception;

	/**
	 * 根据ID获取用户
	 */
	public SysUser getSysUserById(String userId) throws Exception;

	/**
	 * 查询所有用户
	 */
	public List<SysUser> allUser() throws Exception;
	
	/**
	 * 查询所有用户、组织
	 */
	public List allUserOrg() throws Exception;
	
	/**
	 * 查询所有用户(组织相应列出）
	 */
	public List allUserAndOrg() throws Exception;
	
	/**
	 * 查询所有移动用户
	 */
	public List<SysUser> allCmccUser() throws Exception;
	
	/**
	 * 查询所有移动用户、组织
	 */
	public List<SysUser> allCmccUserOrg() throws Exception;
	/**
	 * 根据组织ID查询所有用户
	 */
	public List<SysUser> allUserByOrgId(String orgId) throws Exception;

	/**
	 * 删除用户
	 */
	public void deleteSysUser(String userId) throws Exception;

	/**
	 * 根据用户登录账号查询用户
	 */
	public SysUser queryVisitorByName(String account) throws Exception;

	/**
	 * 根据组织ID，角色名称获取用户
	 */
	public List<SysUser> getUserInfo(String roleName, String orgId)
			throws Exception;

	/**
	 * 根据角色名称获取用户
	 */
	public List<SysUser> getUserInfo(String roleName) throws Exception;

	/**
	 * 根据组织Id获取其组织下的用户以及下属组织下的用户
	 */
	public ItemPage getUserByOrgId(String orgId, SysUserForm form,
			String currentUserId) throws Exception;

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
			List<SysOrganization> SubList, List<SysOrganization> newList);

	/**
	 * 根据角色名称跟项目ID查询用户
	 * 
	 * @param roleName
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getUserByRoleNameAndPJID(String roleName,
			String projectId) throws Exception;

	/**
	 * 根据用户ID获取项目ID
	 * 
	 * @param roleName
	 * @param projectId
	 * @return
	 * @throws Exception
	 *//*
	public List<String> getProjectIdListByUserId(String userId)
			throws Exception;
	*/
	/**
	 * 修改用户排序
	 */
	public void orderUpdate(String ids,String seqs) throws Exception;
	/**
	 * * 根据角色id字符串查询用户
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	 
	public List<SysUser> getUserByIDS(String ids) throws Exception;
	/**
	 * * 根据用户账号和名字查询用户
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	 
	@SuppressWarnings("unchecked")
	public SysUser getUserByNameAndACC(String account) throws Exception;
	
	/**
	 * 批量添加用户信息
	 * @param visitor
	 * @param file
	 * @param request
	 * @param organizationId
	 * @return
	 * @throws Exception
	 */
	public String saveUser(SysUser visitor,String file,HttpServletRequest request,String organizationId,String organizationName,String roleid) throws Exception;
	
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
			throws RuntimeException;
	
	
	/**
	 * 查询所有组织信息
	 * @param visitor
	 * @return
	 * @throws RuntimeException
	 */
	public List<SysOrganization> getAllOrganization(SysUser visitor)throws RuntimeException;
	
	/**
	 * 获取第一级组织机构
	 * @param visitor
	 * @return
	 * @throws RuntimeException
	 */
	public List<SysOrganization> getOrgFirst(SysUser visitor)throws RuntimeException;
	
	/**
	 * 根据组织ID 获取子组织信息
	 * @param visitor
	 * @param orgId
	 * @return
	 * @throws RuntimeException
	 */
	public List<SysOrganization> getNextOrg(SysUser visitor,String orgId)throws RuntimeException;
	
	/**
	 * 变更组织
	 * @param userId
	 * @throws RuntimeException
	 */
	public void upUserByUserId(String userId,String organizationId,String organizationName)throws RuntimeException;
	public ItemPage allUserByOrgId2(SysUserForm form);
	
	public List<SysUser> querySysUseres(SysUserForm form) throws Exception;

	
	/**
	 * 用户联想查询
	 */
	public List searchUser(String userValue);
	
	public ItemPage querySysUserByRoleId(SysUserForm form,String roleId,String orgid) throws Exception;
	
	/**
	 * 新增用户返回主键
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	public String addSysUserBackId(SysUser sysUser) throws Exception;
	
	/**
	 * 添加用户
	 */
	public void addSysUserRole(SysRoleUser model) throws Exception;
	
	public ItemPage querySysUserByDefaultRoleId(SysUserForm form,String roleId,String orgid) throws Exception;

	/**
	 * 解除冻结账户
	 */
	public void unfreezeUser(String userId) throws Exception; 
	
	/**
	 * 获取最大或者最小排序值
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getMaxOrMinOrderNum(String depId,String orderByFlag)throws Exception;
	/**
	 * 取得用户的排序顺序
	 * @return
	 */
	public int getOrderNum();
	public ItemPage getAllUser(SysUserForm form) throws Exception;

    void updateSysUser(SysUser sysUser);
    
    public String getComyOrg(String orgId);
    
    /**
     * 检测登录账户是否存在
     * @param account 账号
     * @return
     */
    public Boolean checkAccountName(String account);
    
    
    /**
     * 检测用户信息是否准确
     * @param account
     * @param userName
     * @param mobile
     * @return
     */
    public String checkUserInfo(String account,String userName,String mobile);
    
    /**
     * 通过账号获取用户组织
     * @param account
     * @return
     */
    public String getUserOrgNameByAccount(String account);
    
    /**
     * 通过账号获取用户信息
     * @param account
     * @return
     */
    public SysUser getSysUserByAccount(String account);
    
    /*删除用户信息，以便同步用户*/
    public void  dropUser();
    
    /*备份用户信息，以便同步用户*/
    public void  dropUserBackup();
    
    
    /*删除临时用户信息，以便同步用户*/
    public void  dropUserTemp();
    
    /**
	 * 向临时用户表添加数据，以便同步用户信息
	 */
	public void addSysUserTemp(SysUserTemp sysUser) throws Exception;
	
	/**
	 * 查询所有临时用户
	 */
	public List<SysUserTemp> allUserTemp() throws Exception;
	
	/**
	 * 添加临时用户表到正式表
	 */
	public void addSysUserByTemp( ) throws Exception;
	
	/**
	 * * 根据菜单权限字符串查询用户
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getUserByMenu(String menuCode) throws Exception;
	
	/**
	 * 根据用户名查询用户
	 * @param parm
	 */
	public SysUser getSysUserByName(String userName);
	
	/**
	 *临时同步用户时，同步id字段 
	 */
	 public void  updateSysUserIdTemp();
	 
	 /**
		 * 根据用户名或用户账号查询用户
		 * @param parm
		 */
	public SysUser getSysUserByIdOrName(String userId,String userName);
	/**
	 * 给用户添加默认角色
	 * @throws Exception 
	 */
	public void addSysUserDefaultRoleId() throws Exception;
	/**
	 * 批量给用户添加角色
	 * @param sysRole
	 * @param users
	 * @throws Exception 
	 */
	public void addSysUserRoleId(SysRole sysRole, String[] users) throws Exception;
	

}
