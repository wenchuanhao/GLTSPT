package com.cdc.dc.cooperation.form;

import org.trustel.service.form.PageQueryForm;

public class CooperationForm extends PageQueryForm{

	private String busTypes;//业务类型（数据源/业务报表）
	
	private String datasourceId;//配置ID
	private String datasourceName;//配置名称
	private String parentDatasourceId;//父类配置ID
	private String parentDatasourceName;//父类配置名称
	
    private String userid;//创建人ID
    private String username;//创建人名称
    
    private String month;//月份
    
    private String beginMonth;
    private String endMonth;
    private String ids;//配置ID列表
    
    private String status;//审核状态
	public String getBusTypes() {
		return busTypes;
	}
	public void setBusTypes(String busTypes) {
		this.busTypes = busTypes;
	}
	public String getDatasourceId() {
		return datasourceId;
	}
	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}
	public String getDatasourceName() {
		return datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	public String getParentDatasourceId() {
		return parentDatasourceId;
	}
	public void setParentDatasourceId(String parentDatasourceId) {
		this.parentDatasourceId = parentDatasourceId;
	}
	public String getParentDatasourceName() {
		return parentDatasourceName;
	}
	public void setParentDatasourceName(String parentDatasourceName) {
		this.parentDatasourceName = parentDatasourceName;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getBeginMonth() {
		return beginMonth;
	}
	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
}
