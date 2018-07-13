package com.cdc.sys.dict.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

/**
 * SysParameterType entity. @author MyEclipse Persistence Tools
 */

public class SysParameterTypeForm extends PageQueryForm implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String parameterTypeId;
	private String parameterTypeCode;
	private String parameterTypeValue;
	private String parameterTypeName;
	private String parameterTypeDesc;
	private String createrId;
	private String allowUpdate;
	private Date createTime;
	
	
	
	
	public SysParameterTypeForm(String parameterTypeId,
			String parameterTypeCode, String parameterTypeValue,
			String parameterTypeName, String parameterTypeDesc,
			String createrId, String allowUpdate, Date createTime) {
		super();
		this.parameterTypeId = parameterTypeId;
		this.parameterTypeCode = parameterTypeCode;
		this.parameterTypeValue = parameterTypeValue;
		this.parameterTypeName = parameterTypeName;
		this.parameterTypeDesc = parameterTypeDesc;
		this.createrId = createrId;
		this.allowUpdate = allowUpdate;
		this.createTime = createTime;
	}
	public SysParameterTypeForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getParameterTypeId() {
		return parameterTypeId;
	}
	public void setParameterTypeId(String parameterTypeId) {
		this.parameterTypeId = parameterTypeId;
	}
	public String getParameterTypeCode() {
		return parameterTypeCode;
	}
	public void setParameterTypeCode(String parameterTypeCode) {
		this.parameterTypeCode = parameterTypeCode;
	}
	public String getParameterTypeValue() {
		return parameterTypeValue;
	}
	public void setParameterTypeValue(String parameterTypeValue) {
		this.parameterTypeValue = parameterTypeValue;
	}
	public String getParameterTypeName() {
		return parameterTypeName;
	}
	public void setParameterTypeName(String parameterTypeName) {
		this.parameterTypeName = parameterTypeName;
	}
	public String getParameterTypeDesc() {
		return parameterTypeDesc;
	}
	public void setParameterTypeDesc(String parameterTypeDesc) {
		this.parameterTypeDesc = parameterTypeDesc;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public String getAllowUpdate() {
		return allowUpdate;
	}
	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	// Constructors



}