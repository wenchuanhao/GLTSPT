package com.cdc.dc.cooperation.model;

import java.util.Date;

/**
 * YuanTable010 entity. @author MyEclipse Persistence Tools
 */

public class YuanTable010 implements java.io.Serializable {

	// Fields

	private String column01;
	private String column02;
	private String column03;
	private String column04;
	private String column05;
	private String column06;
	private String column07;
	private String column08;
	private String column09;
	private String column10;
	private String column11;
	private String column12;
	private String column13;
	private String column14;
	private String column15;
	private Date createTime;
	private String datasourceRecordsId;
	private String normalId;
	// Constructors

	/** default constructor */
	public YuanTable010() {
	}

	/** minimal constructor */
	public YuanTable010(String normalId) {
		this.normalId = normalId;
	}

	/** full constructor */
	public YuanTable010(String normalId, String column01, String column02,
			String column03, String column04, String column05, String column06,
			String column07, String column08, String column09, String column10,
			String column11, String column12, String column13, String column14,
			String column15, Date createTime, String datasourceRecordsId) {
		this.normalId = normalId;
		this.column01 = column01;
		this.column02 = column02;
		this.column03 = column03;
		this.column04 = column04;
		this.column05 = column05;
		this.column06 = column06;
		this.column07 = column07;
		this.column08 = column08;
		this.column09 = column09;
		this.column10 = column10;
		this.column11 = column11;
		this.column12 = column12;
		this.column13 = column13;
		this.column14 = column14;
		this.column15 = column15;
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

	public String getColumn05() {
		return this.column05;
	}

	public void setColumn05(String column05) {
		this.column05 = column05;
	}

	public String getColumn06() {
		return this.column06;
	}

	public void setColumn06(String column06) {
		this.column06 = column06;
	}

	public String getColumn07() {
		return this.column07;
	}

	public void setColumn07(String column07) {
		this.column07 = column07;
	}

	public String getColumn08() {
		return this.column08;
	}

	public void setColumn08(String column08) {
		this.column08 = column08;
	}

	public String getColumn09() {
		return this.column09;
	}

	public void setColumn09(String column09) {
		this.column09 = column09;
	}

	public String getColumn10() {
		return this.column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	public String getColumn11() {
		return this.column11;
	}

	public void setColumn11(String column11) {
		this.column11 = column11;
	}

	public String getColumn12() {
		return this.column12;
	}

	public void setColumn12(String column12) {
		this.column12 = column12;
	}

	public String getColumn13() {
		return this.column13;
	}

	public void setColumn13(String column13) {
		this.column13 = column13;
	}

	public String getColumn14() {
		return this.column14;
	}

	public void setColumn14(String column14) {
		this.column14 = column14;
	}

	public String getColumn15() {
		return this.column15;
	}

	public void setColumn15(String column15) {
		this.column15 = column15;
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