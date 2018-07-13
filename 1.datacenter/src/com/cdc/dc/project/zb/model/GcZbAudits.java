package com.cdc.dc.project.zb.model;

import java.util.Date;

/**
 * GcZbAudits entity. @author MyEclipse Persistence Tools
 * 周报审核记录表
 */

public class GcZbAudits implements java.io.Serializable {

	// Fields

	private String auditId;
	private String zbId;
	private String auditUserid;
	private String auditUsername;
	private String auditDepid;
	private String auditDepname;
	private String auditResult;
	private String auditDesc;
	private Date auditTime;

	// Constructors

	/** default constructor */
	public GcZbAudits() {
	}

	/** minimal constructor */
	public GcZbAudits(String auditId) {
		this.auditId = auditId;
	}

	/** full constructor */
	public GcZbAudits(String auditId, String zbId, String auditUserid,
			String auditUsername, String auditDepid, String auditDepname,
			String auditResult, String auditDesc, Date auditTime) {
		this.auditId = auditId;
		this.zbId = zbId;
		this.auditUserid = auditUserid;
		this.auditUsername = auditUsername;
		this.auditDepid = auditDepid;
		this.auditDepname = auditDepname;
		this.auditResult = auditResult;
		this.auditDesc = auditDesc;
		this.auditTime = auditTime;
	}

	// Property accessors

	public String getAuditId() {
		return this.auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getZbId() {
		return this.zbId;
	}

	public void setZbId(String zbId) {
		this.zbId = zbId;
	}

	public String getAuditUserid() {
		return this.auditUserid;
	}

	public void setAuditUserid(String auditUserid) {
		this.auditUserid = auditUserid;
	}

	public String getAuditUsername() {
		return this.auditUsername;
	}

	public void setAuditUsername(String auditUsername) {
		this.auditUsername = auditUsername;
	}

	public String getAuditDepid() {
		return this.auditDepid;
	}

	public void setAuditDepid(String auditDepid) {
		this.auditDepid = auditDepid;
	}

	public String getAuditDepname() {
		return this.auditDepname;
	}

	public void setAuditDepname(String auditDepname) {
		this.auditDepname = auditDepname;
	}

	public String getAuditResult() {
		return this.auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getAuditDesc() {
		return this.auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

}