package com.cdc.dc.cooperation.model;

import java.util.Date;

/**
 * 数据源/业务报表配置表
 * @author ZENGKAI
 * @date 2016-05-11 09:58:29
 */

public class CooperationDatasourceType implements java.io.Serializable {

	// Fields

	private String datasourceId;//配置ID
	private String datasourceCode;//配置编号
	private String datasourceName;//配置名称
	private String parentDatasourceId;//父类配置ID
	private String parentDatasourceName;//父类配置名称
	private String datasourceLevel;//层级
	private Date createTime;//创建时间
	private String createUserid;//创建人ID
	private String createUsername;//创建人名称
	private Date updateTime;//更新时间
	private String updateUserid;//更新人ID
	private String updateUsername;//更新人名称
	private String busTypes;//业务类型（数据源/业务报表）
	private String datasourceSource;//来源（录入/导入/接口）
	private String interfaceTable;//接口地址(表英文名)
	private String interfaceTableName;//、接口系统名称（表中文名）
	private String status;//有效性，有效/停用

	// Constructors

	/** default constructor */
	public CooperationDatasourceType() {
	}

	/** minimal constructor */
	public CooperationDatasourceType(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	/** full constructor */
	public CooperationDatasourceType(String datasourceId,
			String datasourceCode, String datasourceName,
			String parentDatasourceId, String parentDatasourceName,
			String datasourceLevel, Date createTime, String createUserid,
			String createUsername, Date updateTime, String updateUserid,
			String updateUsername, String busTypes, String datasourceSource, String interfaceTable, String interfaceTableName, String status) {
		this.datasourceId = datasourceId;
		this.datasourceCode = datasourceCode;
		this.datasourceName = datasourceName;
		this.parentDatasourceId = parentDatasourceId;
		this.parentDatasourceName = parentDatasourceName;
		this.datasourceLevel = datasourceLevel;
		this.createTime = createTime;
		this.createUserid = createUserid;
		this.createUsername = createUsername;
		this.updateTime = updateTime;
		this.updateUserid = updateUserid;
		this.updateUsername = updateUsername;
		this.busTypes = busTypes;
		this.datasourceSource = datasourceSource;
		this.interfaceTable = interfaceTable;
		this.interfaceTableName = interfaceTableName;
		this.status = status;
	}

	// Property accessors
	public String getDatasourceId() {
		return this.datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public String getDatasourceCode() {
		return this.datasourceCode;
	}

	public void setDatasourceCode(String datasourceCode) {
		this.datasourceCode = datasourceCode;
	}

	public String getDatasourceName() {
		return this.datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public String getParentDatasourceId() {
		return this.parentDatasourceId;
	}

	public void setParentDatasourceId(String parentDatasourceId) {
		this.parentDatasourceId = parentDatasourceId;
	}

	public String getParentDatasourceName() {
		return this.parentDatasourceName;
	}

	public void setParentDatasourceName(String parentDatasourceName) {
		this.parentDatasourceName = parentDatasourceName;
	}

	 
	public String getDatasourceLevel() {
		return datasourceLevel;
	}

	public void setDatasourceLevel(String datasourceLevel) {
		this.datasourceLevel = datasourceLevel;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserid() {
		return this.createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getCreateUsername() {
		return this.createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserid() {
		return this.updateUserid;
	}

	public void setUpdateUserid(String updateUserid) {
		this.updateUserid = updateUserid;
	}

	public String getUpdateUsername() {
		return this.updateUsername;
	}

	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}

	public String getBusTypes() {
		return this.busTypes;
	}

	public void setBusTypes(String busTypes) {
		this.busTypes = busTypes;
	}


	public String getDatasourceSource() {
		return datasourceSource;
	}

	public void setDatasourceSource(String datasourceSource) {
		this.datasourceSource = datasourceSource;
	}

	public String getInterfaceTable() {
		return interfaceTable;
	}

	public void setInterfaceTable(String interfaceTable) {
		this.interfaceTable = interfaceTable;
	}

	public String getInterfaceTableName() {
		return interfaceTableName;
	}

	public void setInterfaceTableName(String interfaceTableName) {
		this.interfaceTableName = interfaceTableName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}