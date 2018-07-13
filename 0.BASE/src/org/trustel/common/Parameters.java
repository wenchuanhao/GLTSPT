package org.trustel.common;

import java.io.Serializable;
import java.util.Date;

/**
 * 参数定义对象模型
 * @author Laipl
 * @date 2012-3-5
 */
public class Parameters implements Serializable {
	/** * 	 */
	private static final long serialVersionUID = 1L;
	
	private String parameterId;
	private String parameterstypeId; //参数类型ID
	private String code;  //参数代码
	private String name; //参数名
	private String value;  //参数值 
	private String description; //参数描述
	private String allowUpdate; //是否允许修改
	private String creatorId; //创建者ID
	private String creatorName; //创建者姓名
	private Date createTime; //创建时间
	
	public Parameters(){
		
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getCreatorName() {
		return creatorName;
	}


	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}


	public String getParameterstypeId() {
		return parameterstypeId;
	}


	public void setParameterstypeId(String parameterstypeId) {
		this.parameterstypeId = parameterstypeId;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAllowUpdate() {
		return allowUpdate;
	}
	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
}
