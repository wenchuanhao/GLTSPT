package com.cdc.dc.cooperation.model;

import java.util.Date;

/**
 * YuanTable032 entity. @author MyEclipse Persistence Tools
 */

public class YuanTable032 implements java.io.Serializable {

	// Fields

	private String column01;
	private String column02;
	private Date createTime;
	private String datasourceRecordsId;
	private String normalId;

	// Constructors

	/** default constructor */
	public YuanTable032() {
	}

	/** minimal constructor */
	public YuanTable032(String normalId) {
		this.normalId = normalId;
	}

	/** full constructor */
	public YuanTable032(String normalId, String column01, String column02,
			Date createTime, String datasourceRecordsId) {
		this.normalId = normalId;
		this.column01 = column01;
		this.column02 = column02;
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

	public String getColumn01() {
		return this.column01;
	}

	public void setColumn01(String column01) {
		this.column01 = column01;
	}

	public String getColumn02() {
		return this.column02;
	}

	public void setColumn02(String column02) {
		this.column02 = column02;
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