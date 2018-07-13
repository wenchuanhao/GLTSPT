package com.cdc.dc.divsion.model;

import java.util.Date;

/**
 * DivisionResult entity. @author MyEclipse Persistence Tools
 */

public class DivisionResult implements java.io.Serializable {

	// Fields

	private String resultId;
	private String recordId;
	private String type;
	private String year;
	private String month;
	private String name;
	private String systemName;
	private Double value;
	private Date createTime;

	// Constructors

	/** default constructor */
	public DivisionResult() {
	}

	/** minimal constructor */
	public DivisionResult(String resultId) {
		this.resultId = resultId;
	}

	/** full constructor */
	public DivisionResult(String resultId, String recordId, String type,
			String year, String month, String name, String systemName,
			Double value, Date createTime) {
		this.resultId = resultId;
		this.recordId = recordId;
		this.type = type;
		this.year = year;
		this.month = month;
		this.name = name;
		this.systemName = systemName;
		this.value = value;
		this.createTime = createTime;
	}

	// Property accessors

	public String getResultId() {
		return this.resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}