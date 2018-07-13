package com.cdc.dc.cooperation.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 维修单数据
 * @author xms
 *
 */
public class YuanTable031 implements Serializable{
	private String column01; 
	private String column02; 
	private String column03; 
	private String column04;
	private String column05; 
	private Date createTime;
	private String datasourceRecordsId;
	private String normalId ;
	public String getColumn01() {
		return column01;
	}
	public void setColumn01(String column01) {
		this.column01 = column01;
	}
	public String getColumn02() {
		return column02;
	}
	public void setColumn02(String column02) {
		this.column02 = column02;
	}
	public String getColumn03() {
		return column03;
	}
	public void setColumn03(String column03) {
		this.column03 = column03;
	}
	public String getColumn04() {
		return column04;
	}
	public void setColumn04(String column04) {
		this.column04 = column04;
	}
	public String getColumn05() {
		return column05;
	}
	public void setColumn05(String column05) {
		this.column05 = column05;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDatasourceRecordsId() {
		return datasourceRecordsId;
	}
	public void setDatasourceRecordsId(String datasourceRecordsId) {
		this.datasourceRecordsId = datasourceRecordsId;
	}
	public String getNormalId() {
		return normalId;
	}
	public void setNormalId(String normalId) {
		this.normalId = normalId;
	}
}
