package com.cdc.dc.command.manage.model;

import java.util.Date;

/**
 * 工程指令工作指令类
 * @author zengkai
 * @date 2016-08-30 09:13:00
 * CommandWorks entity. @author MyEclipse Persistence Tools
 */

public class CommandWorks implements java.io.Serializable {

	// Fields

	private String worksId;//工作指令ID
	private String worksType;//指令类型(A1,A2,B,C类)
	private String supportorgId;//支撑单位id
	private String supportorgName;//支撑单位名称
	private String contractCode;//合同编号
	private String contractName;//合同名称/工程名称
	private String constructionId;//施工单位id
	private String constructionName;//施工单位名称
	private String orgId;//部位id
	private String orgName;//部位名称
	private String worksContent;//内容
	private String reason;//原因
	private Date createTime;//创建时间
	private String digEst;
	// Constructors

	public String getDigEst() {
		return digEst;
	}

	public void setDigEst(String digEst) {
		this.digEst = digEst;
	}

	/** default constructor */
	public CommandWorks() {
	}

	/** minimal constructor */
	public CommandWorks(String worksId) {
		this.worksId = worksId;
	}

	/** full constructor */
	public CommandWorks(String worksId, String worksType, String supportorgId,
			String supportorgName, String contractCode, String contractName,
			String constructionId, String constructionName, String orgId,
			String orgName, String worksContent, String reason, Date createTime) {
		this.worksId = worksId;
		this.worksType = worksType;
		this.supportorgId = supportorgId;
		this.supportorgName = supportorgName;
		this.contractCode = contractCode;
		this.contractName = contractName;
		this.constructionId = constructionId;
		this.constructionName = constructionName;
		this.orgId = orgId;
		this.orgName = orgName;
		this.worksContent = worksContent;
		this.reason = reason;
		this.createTime = createTime;
	}

	// Property accessors

	public String getWorksId() {
		return this.worksId;
	}

	public void setWorksId(String worksId) {
		this.worksId = worksId;
	}

	public String getWorksType() {
		return this.worksType;
	}

	public void setWorksType(String worksType) {
		this.worksType = worksType;
	}

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

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getConstructionId() {
		return this.constructionId;
	}

	public void setConstructionId(String constructionId) {
		this.constructionId = constructionId;
	}

	public String getConstructionName() {
		return this.constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getWorksContent() {
		return this.worksContent;
	}

	public void setWorksContent(String worksContent) {
		this.worksContent = worksContent;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}