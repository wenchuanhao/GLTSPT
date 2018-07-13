package com.cdc.sys.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.trustel.service.form.PageQueryForm;

public class SysUserForm extends PageQueryForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String account;
	private String password;
	private String userName;
	private String organizationId;
	private String organizationName;
	private String mobile;
	private String email;
	private String isReceiveSms;
	private String isActivate;
	private String createrId;
	private String createrName;
	private Date createTime;
	private String modifyOperId;
	private String modifyOperName;
	private Date modifyOperTime;
	private String userIds;
	private String orgs;//部门id串
	private String uDepIds;//点部门之后获得id串
	private int pageIndex=1;//当前页码
	private int pageSize=10;//当前页面长度
	private String freezeStatus;//使用状态
	private String defaultRole;//默认角色
	public SysUserForm() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsReceiveSms() {
		return isReceiveSms;
	}

	public void setIsReceiveSms(String isReceiveSms) {
		this.isReceiveSms = isReceiveSms;
	}

	public String getIsActivate() {
		return isActivate;
	}

	public void setIsActivate(String isActivate) {
		this.isActivate = isActivate;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyOperId() {
		return modifyOperId;
	}

	public void setModifyOperId(String modifyOperId) {
		this.modifyOperId = modifyOperId;
	}

	public String getModifyOperName() {
		return modifyOperName;
	}

	public void setModifyOperName(String modifyOperName) {
		this.modifyOperName = modifyOperName;
	}

	public Date getModifyOperTime() {
		return modifyOperTime;
	}

	public void setModifyOperTime(Date modifyOperTime) {
		this.modifyOperTime = modifyOperTime;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}

	public String getuDepIds() {
		return uDepIds;
	}

	public void setuDepIds(String uDepIds) {
		this.uDepIds = uDepIds;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getFreezeStatus() {
		return freezeStatus;
	}

	public void setFreezeStatus(String freezeStatus) {
		this.freezeStatus = freezeStatus;
	}

	public String getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(String defaultRole) {
		this.defaultRole = defaultRole;
	}
	
	
}
