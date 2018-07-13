package com.cdc.dc.command.manage.model;

import java.util.Date;

/**
 * 工程指令号码绑定组织表
 * CommandMoborg entity. @author MyEclipse Persistence Tools
 */

public class CommandMoborg implements java.io.Serializable {

	// Fields

	private String moborgId;
	private String mobile;//手机号码
	private String orgId;//组织ID
	private Date createTime;
	private String createUserid;//组织架构账号用户名
	private String createUsername;
	private String status;//状态（1：保存，0：删除）
	
	public final static String Status_S = "1";//已归档
	public final static String Status_D = "0";//已撤销

	// Constructors

	/** default constructor */
	public CommandMoborg() {
	}

	/** minimal constructor */
	public CommandMoborg(String moborgId) {
		this.moborgId = moborgId;
	}

	/** full constructor */
	public CommandMoborg(String moborgId, String mobile, String orgId,
			Date createTime, String createUserid, String createUsername,
			String status) {
		this.moborgId = moborgId;
		this.mobile = mobile;
		this.orgId = orgId;
		this.createTime = createTime;
		this.createUserid = createUserid;
		this.createUsername = createUsername;
		this.status = status;
	}

	// Property accessors

	public String getMoborgId() {
		return this.moborgId;
	}

	public void setMoborgId(String moborgId) {
		this.moborgId = moborgId;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserid() {
		return this.createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getCreateUsername() {
		return this.createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}