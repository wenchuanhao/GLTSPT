package com.cdc.dc.rules.model;

import java.util.Date;

/**
 * RulesFlowInsactor entity. @author MyEclipse Persistence Tools
 * 待办待阅表
 */

public class RulesFlowInsactor implements java.io.Serializable {

	// Fields

	private String insacatorId;//检查ID,主键
	private String insacatorType;//检查类型(1:待办,2:待阅,3:发布信息)
	private String flowId;//流程ID，关联流程信息表
	private String rulesInfoid;//制度编号
	private String handelUserid;//处理人ID
	private String handelUsername;//处理人名称
	private Date createTime;//产生时间
	private Date handelTime;//处理时间
	private String handelStatus;//处理状态（1:完成,0:未完成）
	/**
	 * 待办,单位：日,系统配置
	 * 待阅,0,无超时
	 * 发布信息,单位:月,系统配置
	 */
	private Long timeoutDays;//超时时限：0：无超时，
	private String timeoutStatus;//超时状态：1：已超时，0：未超时
	
	// Constructors

	/** default constructor */
	public RulesFlowInsactor() {
	}

	/** minimal constructor */
	public RulesFlowInsactor(String insacatorId) {
		this.insacatorId = insacatorId;
	}

	/** full constructor */
	public RulesFlowInsactor(String insacatorId, String insacatorType,String flowId,
			String rulesInfoid, String handelUserid, String handelUsername,
			Date createTime, Date handelTime, Long timeoutDays,
			String timeoutStatus, String handelStatus) {
		this.insacatorId = insacatorId;
		this.insacatorType = insacatorType;
		this.flowId = flowId;
		this.rulesInfoid = rulesInfoid;
		this.handelUserid = handelUserid;
		this.handelUsername = handelUsername;
		this.createTime = createTime;
		this.handelTime = handelTime;
		this.timeoutDays = timeoutDays;
		this.timeoutStatus = timeoutStatus;
		this.handelStatus = handelStatus;
	}

	// Property accessors

	public String getInsacatorId() {
		return this.insacatorId;
	}

	public void setInsacatorId(String insacatorId) {
		this.insacatorId = insacatorId;
	}

	public String getInsacatorType() {
		return this.insacatorType;
	}

	public void setInsacatorType(String insacatorType) {
		this.insacatorType = insacatorType;
	}

	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getRulesInfoid() {
		return this.rulesInfoid;
	}

	public void setRulesInfoid(String rulesInfoid) {
		this.rulesInfoid = rulesInfoid;
	}

	public String getHandelUserid() {
		return this.handelUserid;
	}

	public void setHandelUserid(String handelUserid) {
		this.handelUserid = handelUserid;
	}

	public String getHandelUsername() {
		return this.handelUsername;
	}

	public void setHandelUsername(String handelUsername) {
		this.handelUsername = handelUsername;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getHandelTime() {
		return this.handelTime;
	}

	public void setHandelTime(Date handelTime) {
		this.handelTime = handelTime;
	}

	public Long getTimeoutDays() {
		return this.timeoutDays;
	}

	public void setTimeoutDays(Long timeoutDays) {
		this.timeoutDays = timeoutDays;
	}

	public String getTimeoutStatus() {
		return this.timeoutStatus;
	}

	public void setTimeoutStatus(String timeoutStatus) {
		this.timeoutStatus = timeoutStatus;
	}

	public String getHandelStatus() {
		return this.handelStatus;
	}

	public void setHandelStatus(String handelStatus) {
		this.handelStatus = handelStatus;
	}

}