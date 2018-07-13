package com.cdc.inter.client.ws.sms.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * InterfaceSmsinfoRecords entity. @author MyEclipse Persistence Tools
 */

public class InterfaceSmsinfoRecords implements java.io.Serializable {

	// Fields

	private String recordId;
	private Date createTime;
	private String createUserid;
	private String createUsername;
	private String msgHeader;
	private String content;
	private String mobile;
	private Date sendTime;
	private String serviceFlag;
	private String serviceMessage;
	private String instanceId;
	private BigDecimal totalRecord;
	private BigDecimal totalPage;
	private BigDecimal pageSize;
	private BigDecimal currentPage;
	private BigDecimal returnCode;
	private String returnMessage;
	private String sendId;

	// Constructors

	/** default constructor */
	public InterfaceSmsinfoRecords() {
	}

	/** minimal constructor */
	public InterfaceSmsinfoRecords(String recordId) {
		this.recordId = recordId;
	}

	/** full constructor */
	public InterfaceSmsinfoRecords(String recordId, Date createTime,
			String createUserid, String createUsername, String msgHeader,
			String content, String mobile, Date sendTime, String serviceFlag,
			String serviceMessage, String instanceId, BigDecimal totalRecord,
			BigDecimal totalPage, BigDecimal pageSize, BigDecimal currentPage,
			BigDecimal returnCode, String returnMessage, String sendId) {
		this.recordId = recordId;
		this.createTime = createTime;
		this.createUserid = createUserid;
		this.createUsername = createUsername;
		this.msgHeader = msgHeader;
		this.content = content;
		this.mobile = mobile;
		this.sendTime = sendTime;
		this.serviceFlag = serviceFlag;
		this.serviceMessage = serviceMessage;
		this.instanceId = instanceId;
		this.totalRecord = totalRecord;
		this.totalPage = totalPage;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
		this.sendId = sendId;
	}

	// Property accessors

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
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

	public String getMsgHeader() {
		return this.msgHeader;
	}

	public void setMsgHeader(String msgHeader) {
		this.msgHeader = msgHeader;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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

	public BigDecimal getReturnCode() {
		return this.returnCode;
	}

	public void setReturnCode(BigDecimal returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return this.returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getSendId() {
		return this.sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

}