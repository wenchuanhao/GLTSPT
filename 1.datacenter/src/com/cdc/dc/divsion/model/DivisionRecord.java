package com.cdc.dc.divsion.model;

import java.util.Date;

/**
 * DivisionRecord entity. @author MyEclipse Persistence Tools
 */

public class DivisionRecord implements java.io.Serializable {

	// Fields

	private String recordId;
	private String requestUrl;
	private String requestYear;
	private String requestToken;
	private String errorCode;
	private String errorMessage;
	private Date createTime;

	// Constructors

	/** default constructor */
	public DivisionRecord() {
	}

	/** minimal constructor */
	public DivisionRecord(String recordId) {
		this.recordId = recordId;
	}

	/** full constructor */
	public DivisionRecord(String recordId, String requestUrl,
			String requestYear, String requestToken, String errorCode,
			String errorMessage, Date createTime) {
		this.recordId = recordId;
		this.requestUrl = requestUrl;
		this.requestYear = requestYear;
		this.requestToken = requestToken;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.createTime = createTime;
	}

	// Property accessors

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRequestUrl() {
		return this.requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestYear() {
		return this.requestYear;
	}

	public void setRequestYear(String requestYear) {
		this.requestYear = requestYear;
	}

	public String getRequestToken() {
		return this.requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}