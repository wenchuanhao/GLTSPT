package com.cdc.dc.cooperation.model;

import java.util.Date;

/**
 * ReportFixedAssets entity. @author MyEclipse Persistence Tools
 */

public class ReportFixedAssets implements java.io.Serializable {

	
	//月份
	private String month;
	//金额
	private String amounts;
	//固定字段
	private Date createTime;//创建时间
	private String datasourceRecordsId;//数据源记录
	private String normalId;//主键ID
	// Constructors

	/** default constructor */
	public ReportFixedAssets() {
	}

	/** minimal constructor */
	public ReportFixedAssets(String normalId) {
		this.normalId = normalId;
	}

	/** full constructor */
	public ReportFixedAssets(String normalId, String month, String amounts,
			Date createTime, String datasourceRecordsId) {
		this.normalId = normalId;
		this.month = month;
		this.amounts = amounts;
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

	public String getAmounts() {
		return this.amounts;
	}

	public void setAmounts(String amounts) {
		this.amounts = amounts;
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