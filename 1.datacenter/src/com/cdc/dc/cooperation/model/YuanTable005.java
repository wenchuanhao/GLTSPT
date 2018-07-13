package com.cdc.dc.cooperation.model;

import java.util.Date;

/**
 * YuanTable005 entity. @author MyEclipse Persistence Tools
 */

public class YuanTable005 implements java.io.Serializable {

	// Fields

	private String column01;
	private String column02;
	private String column03;
	private String column04;
	private Date createTime;
	private String datasourceRecordsId;
	private String normalId;

	// Constructors

	/** default constructor */
	public YuanTable005() {
	}

	/** minimal constructor */
	public YuanTable005(String normalId) {
		this.normalId = normalId;
	}

	/** full constructor */
	public YuanTable005(String normalId, String column01, String column02,
			String column03, String column04, Date createTime,
			String datasourceRecordsId) {
		this.normalId = normalId;
		this.column01 = column01;
		this.column02 = column02;
		this.column03 = column03;
		this.column04 = column04;
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

	public String getColumn03() {
		return this.column03;
	}

	public void setColumn03(String column03) {
		this.column03 = column03;
	}

	public String getColumn04() {
		return this.column04;
	}

	public void setColumn04(String column04) {
		this.column04 = column04;
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