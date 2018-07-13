package com.cdc.sys.dict.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.trustel.service.form.PageQueryForm;

import com.cdc.sys.dict.model.SysParameter;

public class SysParameterForm extends PageQueryForm implements Serializable
{
	/**
	 * 
	 */
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
	private String parameterTypeName;
	
	private List<SysParameter> parameter;
	
	
	public SysParameterForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public SysParameterForm(String parameterId, String parameterTypeId,
			String parameterCode, String parameterName, String parameterValue,
			String parameterDesc, String allowUpdate, String createrId,
			Date createTime,List<SysParameter> parameter) {
		super();
		this.parameterId = parameterId;
		this.parameterTypeId = parameterTypeId;
		this.parameterCode = parameterCode;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
		this.parameterDesc = parameterDesc;
		this.allowUpdate = allowUpdate;
		this.createrId = createrId;
		this.createTime = createTime;
		this.parameter = parameter;
	}



	public String getParameterId() {
		return parameterId;
	}
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}
	public String getParameterTypeId() {
		return parameterTypeId;
	}
	public void setParameterTypeId(String parameterTypeId) {
		this.parameterTypeId = parameterTypeId;
	}
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getParameterDesc() {
		return parameterDesc;
	}
	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}
	public String getAllowUpdate() {
		return allowUpdate;
	}
	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public String getParameterTypeName() {
		return parameterTypeName;
	}



	public void setParameterTypeName(String parameterTypeName) {
		this.parameterTypeName = parameterTypeName;
	}



	public List<SysParameter> getParameter() {
		return parameter;
	}



	public void setParameter(List<SysParameter> parameter) {
		this.parameter = parameter;
	}

	
}