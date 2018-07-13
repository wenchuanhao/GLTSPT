package com.cdc.dc.command.manage.model;

import java.util.Date;

/**
 * 工程指令流转记录表
 * @author zengkai
 * @date 2016-08-30 09:11:02
 * CommandFlows entity. @author MyEclipse Persistence Tools
 */

public class CommandFlows implements java.io.Serializable {

	// Fields

	private String flowId;//流转ID
	private String commandInfoid;//指令编号
	// "1";//未流转 ： 发起人 "2";//流转中 ： 接收人  "3";//已归档：归档人  "4";//已作废：作废人
	private String flowStatus;//流转状态(发起/接收/归档)
	private String flowRoleid;//流转角色
	private String flowRolename;//流转角色名称
	private String flowUserid;//流转人id
	private String flowUsername;//流转人名称
	private String flowMobile;//流转人号码
	private Date flowTime;//流转时间
	private String fromFlowId;//上一流转id
	private String toFlowId;//下一流转id
	private String digEst;//摘要
	// Constructors

	public String getDigEst() {
		return digEst;
	}

	public void setDigEst(String digEst) {
		this.digEst = digEst;
	}

	/** default constructor */
	public CommandFlows() {
	}

	/** minimal constructor */
	public CommandFlows(String flowId) {
		this.flowId = flowId;
	}

	/** full constructor */
	public CommandFlows(String flowId, String commandInfoid, String flowStatus,
			String flowRoleid, String flowRolename, String flowUserid,
			String flowUsername, String flowMobile, Date flowTime,
			String fromFlowId, String toFlowId) {
		this.flowId = flowId;
		this.commandInfoid = commandInfoid;
		this.flowStatus = flowStatus;
		this.flowRoleid = flowRoleid;
		this.flowRolename = flowRolename;
		this.flowUserid = flowUserid;
		this.flowUsername = flowUsername;
		this.flowMobile = flowMobile;
		this.flowTime = flowTime;
		this.fromFlowId = fromFlowId;
		this.toFlowId = toFlowId;
	}

	// Property accessors

	public String getFlowId() {
		return this.flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getCommandInfoid() {
		return this.commandInfoid;
	}

	public void setCommandInfoid(String commandInfoid) {
		this.commandInfoid = commandInfoid;
	}

	public String getFlowStatus() {
		return this.flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getFlowRoleid() {
		return this.flowRoleid;
	}

	public void setFlowRoleid(String flowRoleid) {
		this.flowRoleid = flowRoleid;
	}

	public String getFlowRolename() {
		return this.flowRolename;
	}

	public void setFlowRolename(String flowRolename) {
		this.flowRolename = flowRolename;
	}

	public String getFlowUserid() {
		return this.flowUserid;
	}

	public void setFlowUserid(String flowUserid) {
		this.flowUserid = flowUserid;
	}

	public String getFlowUsername() {
		return this.flowUsername;
	}

	public void setFlowUsername(String flowUsername) {
		this.flowUsername = flowUsername;
	}

	public String getFlowMobile() {
		return this.flowMobile;
	}

	public void setFlowMobile(String flowMobile) {
		this.flowMobile = flowMobile;
	}

	public Date getFlowTime() {
		return this.flowTime;
	}

	public void setFlowTime(Date flowTime) {
		this.flowTime = flowTime;
	}

	public String getFromFlowId() {
		return this.fromFlowId;
	}

	public void setFromFlowId(String fromFlowId) {
		this.fromFlowId = fromFlowId;
	}

	public String getToFlowId() {
		return this.toFlowId;
	}

	public void setToFlowId(String toFlowId) {
		this.toFlowId = toFlowId;
	}

}