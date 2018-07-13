package com.cdc.dc.cooperation.model;

import java.util.Date;

/**
 * ReportLaborCosts entity. @author MyEclipse Persistence Tools
 */

public class ReportLaborCosts implements java.io.Serializable {

	// Fields

	private String month;
	private String employeeType;
	private String laborCosts;
	private Date createTime;
	private String datasourceRecordsId;
	private String normalId;

	// Constructors

	/** default constructor */
	public ReportLaborCosts() {
	}

	/** minimal constructor */
	public ReportLaborCosts(String normalId) {
		this.normalId = normalId;
	}

	/** full constructor */
	public ReportLaborCosts(String normalId, String month, String employeeType,
			String laborCosts, Date createTime, String datasourceRecordsId) {
		this.normalId = normalId;
		this.month = month;
		this.employeeType = employeeType;
		this.laborCosts = laborCosts;
		this.createTime = createTime;
		this.datasourceRecordsId = datasourceRecordsId;
	}

	// Property accessors

	public String getNormalId() {
		return this.normalId;
	}

	public void setNormalId(String normalId) {
		this.normalId = normalId;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getEmployeeType() {
		return this.employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getLaborCosts() {
		return this.laborCosts;
	}

	public void setLaborCosts(String laborCosts) {
		this.laborCosts = laborCosts;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDatasourceRecordsId() {
		return this.datasourceRecordsId;
	}

	public void setDatasourceRecordsId(String datasourceRecordsId) {
		this.datasourceRecordsId = datasourceRecordsId;
	}

}