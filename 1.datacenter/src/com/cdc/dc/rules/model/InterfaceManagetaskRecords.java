package com.cdc.dc.rules.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * InterfaceManagetaskRecords entity. @author MyEclipse Persistence Tools
 */

public class InterfaceManagetaskRecords implements java.io.Serializable {

	// Fields

	private String recordId;
	private String bustypeId;
	private Date createTime;
	private String createUserid;
	private String createUsername;
	private String handelStatus;
	//请求体
	private String msgHeader;
	private String taskId;
	private String fromEmployeeId;
	private String toEmployeeId;
	private String subject;
	private String taskUrl;
	private String createTimeStr;
	private String warningTimeStr;
	private BigDecimal taskType;
	private BigDecimal action;
	//响应
	private String serviceFlag;
	private String serviceMessage;
	private String instanceId;
	private BigDecimal totalRecord;
	private BigDecimal totalPage;
	private BigDecimal pageSize;
	private BigDecimal currentPage;
	private String taskIdRetrun;
	private String returns;
	private String msgerror;

	// Constructors

	/** default constructor */
	public InterfaceManagetaskRecords() {
	}

	/** minimal constructor */
	public InterfaceManagetaskRecords(String recordId, String bustypeId) {
		this.recordId = recordId;
		this.bustypeId = bustypeId;
	}

	/** full constructor */
	public InterfaceManagetaskRecords(String recordId, String bustypeId,
			Date createTime, String createUserid, String createUsername,
			String handelStatus, String msgHeader, String taskId,
			String fromEmployeeId, String toEmployeeId, String subject,
			String taskUrl, String createTimeStr, String warningTimeStr,
			BigDecimal taskType, BigDecimal action, String serviceFlag,
			String serviceMessage, String instanceId, BigDecimal totalRecord,
			BigDecimal totalPage, BigDecimal pageSize, BigDecimal currentPage,
			String taskIdRetrun, String returns, String msgerror) {
		this.recordId = recordId;
		this.bustypeId = bustypeId;
		this.createTime = createTime;
		this.createUserid = createUserid;
		this.createUsername = createUsername;
		this.handelStatus = handelStatus;
		this.msgHeader = msgHeader;
		this.taskId = taskId;
		this.fromEmployeeId = fromEmployeeId;
		this.toEmployeeId = toEmployeeId;
		this.subject = subject;
		this.taskUrl = taskUrl;
		this.createTimeStr = createTimeStr;
		this.warningTimeStr = warningTimeStr;
		this.taskType = taskType;
		this.action = action;
		this.serviceFlag = serviceFlag;
		this.serviceMessage = serviceMessage;
		this.instanceId = instanceId;
		this.totalRecord = totalRecord;
		this.totalPage = totalPage;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.taskIdRetrun = taskIdRetrun;
		this.returns = returns;
		this.msgerror = msgerror;
	}

	// Property accessors

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getBustypeId() {
		return this.bustypeId;
	}

	public void setBustypeId(String bustypeId) {
		this.bustypeId = bustypeId;
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

	public String getHandelStatus() {
		return this.handelStatus;
	}

	public void setHandelStatus(String handelStatus) {
		this.handelStatus = handelStatus;
	}

	public String getMsgHeader() {
		return this.msgHeader;
	}

	public void setMsgHeader(String msgHeader) {
		this.msgHeader = msgHeader;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFromEmployeeId() {
		return this.fromEmployeeId;
	}

	public void setFromEmployeeId(String fromEmployeeId) {
		this.fromEmployeeId = fromEmployeeId;
	}

	public String getToEmployeeId() {
		return this.toEmployeeId;
	}

	public void setToEmployeeId(String toEmployeeId) {
		this.toEmployeeId = toEmployeeId;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTaskUrl() {
		return this.taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public String getCreateTimeStr() {
		return this.createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getWarningTimeStr() {
		return this.warningTimeStr;
	}

	public void setWarningTimeStr(String warningTimeStr) {
		this.warningTimeStr = warningTimeStr;
	}

	public BigDecimal getTaskType() {
		return this.taskType;
	}

	public void setTaskType(BigDecimal taskType) {
		this.taskType = taskType;
	}

	public BigDecimal getAction() {
		return this.action;
	}

	public void setAction(BigDecimal action) {
		this.action = action;
	}

	public String getServiceFlag() {
		return this.serviceFlag;
	}

	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}

	public String getServiceMessage() {
		return this.serviceMessage;
	}

	public void setServiceMessage(String serviceMessage) {
		this.serviceMessage = serviceMessage;
	}

	public String getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public BigDecimal getTotalRecord() {
		return this.totalRecord;
	}

	public void setTotalRecord(BigDecimal totalRecord) {
		this.totalRecord = totalRecord;
	}

	public BigDecimal getTotalPage() {
		return this.totalPage;
	}

	public void setTotalPage(BigDecimal totalPage) {
		this.totalPage = totalPage;
	}

	public BigDecimal getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(BigDecimal pageSize) {
		this.pageSize = pageSize;
	}

	public BigDecimal getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(BigDecimal currentPage) {
		this.currentPage = currentPage;
	}

	public String getTaskIdRetrun() {
		return this.taskIdRetrun;
	}

	public void setTaskIdRetrun(String taskIdRetrun) {
		this.taskIdRetrun = taskIdRetrun;
	}

	public String getReturns() {
		return this.returns;
	}

	public void setReturns(String returns) {
		this.returns = returns;
	}

	public String getMsgerror() {
		return this.msgerror;
	}

	public void setMsgerror(String msgerror) {
		this.msgerror = msgerror;
	}

}