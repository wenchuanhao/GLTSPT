package com.cdc.sys.form;

import java.io.Serializable;
import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class SysRoleForm extends PageQueryForm implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String roleId;

	/**
	 * 角色组id
	 */
	private String sysRoleGroupid;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 角色描述
	 */
	private String roleDesc;
	/**
	 * 是否允许修改
	 */
	private String allowUpdate;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 是否管理员（0，是。1，否）
	 */
	private String isAdminstrator;
	
	/**
	 * 是否默认角色
	 */
	private String isDefaultRole; //1.不是 2.是

	/**
	 * 角色编码
	 */
    private String roleCode;
    
    private String account;//用户登录帐号
    
    private String userName;//用户姓名
    
    private String isDisplay;//是否显示默认角色

	public String getSysRoleGroupid() {
		return sysRoleGroupid;
	}

	public void setSysRoleGroupid(String sysRoleGroupid) {
		this.sysRoleGroupid = sysRoleGroupid;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getAllowUpdate() {
		return allowUpdate;
	}

	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setIsAdminstrator(String isAdminstrator) {
		this.isAdminstrator = isAdminstrator;
	}

	public String getIsAdminstrator() {
		return isAdminstrator;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getIsDefaultRole() {
		return isDefaultRole;
	}

	public void setIsDefaultRole(String isDefaultRole) {
		this.isDefaultRole = isDefaultRole;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}
	
	
}