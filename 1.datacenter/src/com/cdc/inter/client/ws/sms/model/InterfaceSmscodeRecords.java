package com.cdc.inter.client.ws.sms.model;

import java.util.Date;

/**
 * InterfaceSmscodeRecords entity. @author MyEclipse Persistence Tools
 */

public class InterfaceSmscodeRecords implements java.io.Serializable {

	// Fields

	private String mobile;
	private String smscode;
	private Long sendTime;
	private Date createTime;

	// Constructors

	/** default constructor */
	public InterfaceSmscodeRecords() {
	}

	/** minimal constructor */
	public InterfaceSmscodeRecords(String mobile) {
		this.mobile = mobile;
	}

	/** full constructor */
	public InterfaceSmscodeRecords(String mobile, String smscode,
			Long sendTime, Date createTime) {
		this.mobile = mobile;
		this.smscode = smscode;
		this.sendTime = sendTime;
		this.createTime = createTime;
	}

	// Property accessors

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSmscode() {
		return this.smscode;
	}

	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}

	public Long getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}