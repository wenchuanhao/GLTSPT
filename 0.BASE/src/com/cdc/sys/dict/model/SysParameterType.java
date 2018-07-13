package com.cdc.sys.dict.model;

import java.util.Date;

public class SysParameterType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String parameterTypeId;
	private String parameterTypeCode;
	private String parameterTypeValue;
	private String parameterTypeName;
	private String parameterTypeDesc;
	private String createrId;
	private String allowUpdate;
	private Date createTime;	
	private String status;//状态；1：已存，0：删除

	public SysParameterType() {
	}

	public SysParameterType(String parameterTypeCode, String parameterTypeValue, String parameterTypeName, String parameterTypeDesc, String createrId, String allowUpdate, Date createTime,String status) {
		this.parameterTypeCode = parameterTypeCode;
		this.parameterTypeValue = parameterTypeValue;
		this.parameterTypeName = parameterTypeName;
		this.parameterTypeDesc = parameterTypeDesc;
		this.createrId = createrId;
		this.allowUpdate = allowUpdate;
		this.createTime = createTime;
		this.status = status;
	}

	// Property accessors

	public String getParameterTypeId() {
		return this.parameterTypeId;
	}

	public void setParameterTypeId(String parameterTypeId) {
		this.parameterTypeId = parameterTypeId;
	}

	public String getParameterTypeCode() {
		return this.parameterTypeCode;
	}

	public void setParameterTypeCode(String parameterTypeCode) {
		this.parameterTypeCode = parameterTypeCode;
	}

	public String getParameterTypeValue() {
		return this.parameterTypeValue;
	}

	public void setParameterTypeValue(String parameterTypeValue) {
		this.parameterTypeValue = parameterTypeValue;
	}

	public String getCreaterId() {
		return this.createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getAllowUpdate() {
		return this.allowUpdate;
	}

	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	public String getParameterTypeName() {
		return this.parameterTypeName;
	}

	public void setParameterTypeName(String parameterTypeName) {
		this.parameterTypeName = parameterTypeName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getParameterTypeDesc() {
		return parameterTypeDesc;
	}

	public void setParameterTypeDesc(String parameterTypeDesc) {
		this.parameterTypeDesc = parameterTypeDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}