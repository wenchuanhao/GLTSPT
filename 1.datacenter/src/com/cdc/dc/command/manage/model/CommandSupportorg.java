package com.cdc.dc.command.manage.model;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

/**
 * 工程指令支撑单位配置表
 * @author zengkai
 * @date 2016-08-30 09:09:00
 * CommandSupportorg entity. @author MyEclipse Persistence Tools
 */

public class CommandSupportorg extends PageQueryForm implements java.io.Serializable {

	// Fields

	private String supportorgId;//配置ID
	private String supportorgName;//支撑单位名称
	private Date createTime;//新增时间
	private Date updateTime;//更新时间
	private String updateUserid;//更新人ID
	private String updateUsername;//更新人名称
	private String status;//状态

	public final static String supportorg_Save =  "1";
	public final static String supportorg_del =  "0";
			
	// Constructors

	/** default constructor */
	public CommandSupportorg() {
	}

	/** minimal constructor */
	public CommandSupportorg(String supportorgId) {
		this.supportorgId = supportorgId;
	}

	/** full constructor */
	public CommandSupportorg(String supportorgId, String supportorgName,
			Date createTime, Date updateTime, String updateUserid,
			String updateUsername, String status) {
		this.supportorgId = supportorgId;
		this.supportorgName = supportorgName;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.updateUserid = updateUserid;
		this.updateUsername = updateUsername;
		this.status = status;
	}

	// Property accessors

	public String getSupportorgId() {
		return this.supportorgId;
	}

	public void setSupportorgId(String supportorgId) {
		this.supportorgId = supportorgId;
	}

	public String getSupportorgName() {
		return this.supportorgName;
	}

	public void setSupportorgName(String supportorgName) {
		this.supportorgName = supportorgName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserid() {
		return this.updateUserid;
	}

	public void setUpdateUserid(String updateUserid) {
		this.updateUserid = updateUserid;
	}

	public String getUpdateUsername() {
		return this.updateUsername;
	}

	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}