package com.cdc.dc.datacenter.task.model;

import java.util.Date;

/**
 * DcJdbcInterfaceInfo entity. @author MyEclipse Persistence Tools
 */

public class DcJdbcInterfaceInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String parentId;
	private String jdbcUrl;
	private String jdbcUsername;
	private String jdbcPassword;
	private Date createDate;
	private String createUserId;
	private String createUserName;
	private Date updateDate;
	private String updateUserId;
	private String updateUserName;
	private String jdbcTablename;

	// Constructors

	/** default constructor */
	public DcJdbcInterfaceInfo() {
	}

	/** full constructor */
	public DcJdbcInterfaceInfo(String parentId, String jdbcUrl,
			String jdbcUsername, String jdbcPassword, Date createDate,
			String createUserId, String createUserName, Date updateDate,
			String updateUserId, String updateUserName, String jdbcTablename) {
		this.parentId = parentId;
		this.jdbcUrl = jdbcUrl;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.updateDate = updateDate;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.jdbcTablename = jdbcTablename;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getJdbcUrl() {
		return this.jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcUsername() {
		return this.jdbcUsername;
	}

	public void setJdbcUsername(String jdbcUsername) {
		this.jdbcUsername = jdbcUsername;
	}

	public String getJdbcPassword() {
		return this.jdbcPassword;
	}

	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getJdbcTablename() {
		return this.jdbcTablename;
	}

	public void setJdbcTablename(String jdbcTablename) {
		this.jdbcTablename = jdbcTablename;
	}

}