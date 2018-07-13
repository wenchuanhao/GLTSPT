package com.cdc.dc.rules.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * RulesType entity. @author ZENGKAI
 * 制度分类表
 */

public class RulesType implements java.io.Serializable {
	
	private String typeId;//分类ID，主键
	private String typeName;//分类名称
	private String parentTypeId;//父分类，外键
	private String parentTypeName;//父分类名称
	private String isRoot;//是否根节点；0：否，1：是
	private Date createTime;//创建时间
	private String createUserid;//创建人ID
	private String createUsername;//创建人名称
	private String busTypes;//业务类型
	private String workDay;//整改超时工作日  （报账费用类型字段）
	private String status;//状态，1：保存，0：删除
	/**
	 * 当前菜单的下一级菜单
	 */
	private List<RulesType> nextList = new ArrayList<RulesType>();
	public String getWorkDay() {
		return workDay;
	}

	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}

	public RulesType(){
		
	}
	
	public RulesType(String typeId, String typeName, String parentTypeId,String parentTypeName,
			String isRoot, Date createTime, String createUserid,
			String createUsername,String busTypes,String status) {
		this.typeId = typeId;
		this.typeName = typeName;
		this.parentTypeId = parentTypeId;
		this.parentTypeName = parentTypeName;
		this.isRoot = isRoot;
		this.createTime = createTime;
		this.createUserid = createUserid;
		this.createUsername = createUsername;
		this.busTypes = busTypes;
		this.status = status;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getParentTypeId() {
		return parentTypeId;
	}
	public void setParentTypeId(String parentTypeId) {
		this.parentTypeId = parentTypeId;
	}
	
	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	public String getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(String isRoot) {
		this.isRoot = isRoot;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getBusTypes() {
		return busTypes;
	}

	public void setBusTypes(String busTypes) {
		this.busTypes = busTypes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RulesType> getNextList() {
		return nextList;
	}

	public void setNextList(List<RulesType> nextList) {
		this.nextList = nextList;
	}
	
	
}