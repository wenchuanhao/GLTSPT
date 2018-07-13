package com.cdc.inter.client.ws.mail.service.model;

import java.math.BigDecimal;
import java.util.Date;

public class InterfaceMailinfoRecords implements java.io.Serializable {
	private String recordId;
	private Date createTime;
	private String createUserid;
	private String createUsername;
	private String msgHeader;
	private String content;
	private String semail;
	private String temail;
	private Date sendTime;
	private String serviceFlag;
	private String serviceMessage;
	private String instanceId;
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	public String getMsgHeader() {
		return msgHeader;
	}
	public void setMsgHeader(String msgHeader) {
		this.msgHeader = msgHeader;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getSemail() {
		return semail;
	}
	public void setSemail(String semail) {
		this.semail = semail;
	}
	public String getTemail() {
		return temail;
	}
	public void setTemail(String temail) {
		this.temail = temail;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getServiceFlag() {
		return serviceFlag;
	}
	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}
	public String getServiceMessage() {
		return serviceMessage;
	}
	public void setServiceMessage(String serviceMessage) {
		this.serviceMessage = serviceMessage;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public BigDecimal getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(BigDecimal totalRecord) {
		this.totalRecord = totalRecord;
	}
	public BigDecimal getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(BigDecimal totalPage) {
		this.totalPage = totalPage;
	}
	public BigDecimal getPageSize() {
		return pageSize;
	}
	public void setPageSize(BigDecimal pageSize) {
		this.pageSize = pageSize;
	}
	public BigDecimal getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(BigDecimal currentPage) {
		this.currentPage = currentPage;
	}
	public BigDecimal getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(BigDecimal returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	private BigDecimal totalRecord;
	private BigDecimal totalPage;
	private BigDecimal pageSize;
	private BigDecimal currentPage;
	private BigDecimal returnCode;
	private String returnMessage;
	private String sendId;

}
