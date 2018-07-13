package com.cdc.sys.dict.model;

import java.util.Date;

/**
 * 
 * 
 * @Description:
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-29 下午4:32:59
 * @UpdateRemark:
 * @Version: V1.0
 */
public class SysParameter implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String parameterId;
	private String parameterTypeId;
	private String parameterCode;
	private String parameterName;
	private String parameterValue;
	private String parameterDesc;
	private String allowUpdate;
	private String createrId;
	private Date createTime;
	private Integer orderId;//排序号
	private String status;//状态；1：已存，0：删除
	
	public SysParameter() {
	}

	public SysParameter(String parameterTypeId, String parameterCode, String parameterName, String parameterValue, String parameterDesc, String allowUpdate, String createrId, Date createTime,String status) {
		this.parameterTypeId = parameterTypeId;
		this.parameterCode = parameterCode;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
		this.parameterDesc = parameterDesc;
		this.allowUpdate = allowUpdate;
		this.createrId = createrId;
		this.createTime = createTime;
		this.status = status;
	}

	public String getParameterId() {
		return this.parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getParameterTypeId() {
		return this.parameterTypeId;
	}

	public void setParameterTypeId(String parameterTypeId) {
		this.parameterTypeId = parameterTypeId;
	}

	public String getParameterCode() {
		return this.parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterValue() {
		return this.parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getAllowUpdate() {
		return this.allowUpdate;
	}

	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
	}


	public String getCreaterId() {
		return this.createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getParameterDesc() {
		return parameterDesc;
	}

	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}