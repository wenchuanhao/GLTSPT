package com.cdc.sys.dataWarn.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class DataSourceWarnForm extends PageQueryForm{
	private String id;//主键ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public Date getWarnTime() {
		return warnTime;
	}
	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAviliablevalueGe() {
		return aviliablevalueGe;
	}
	public void setAviliablevalueGe(String aviliablevalueGe) {
		this.aviliablevalueGe = aviliablevalueGe;
	}
	public String getAviliablevalueLe() {
		return aviliablevalueLe;
	}
	public void setAviliablevalueLe(String aviliablevalueLe) {
		this.aviliablevalueLe = aviliablevalueLe;
	}
	private Date startDate;
	private Date endDate;
	private String config;//是否人工配置 2是  1否
	private String dataSource;//数据源
	private String aviliablevalueGe;//阀值区间-大于
	private String aviliablevalueLe;//阀值区间-小于
	private String value;//文件值
	private Date warnTime;//创建时间
}
