package com.cdc.sys.form;

import java.io.Serializable;
import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class SysOrganizationForm extends PageQueryForm implements Serializable {

	private String organizationId;
	private String orgName;
	private String orgCode;
	private Short orgOrder;
	private String parentId;
	private String descrption;
	private String liveFlag;
	private String createrId;
	private Date createTime;
	private int pageIndex=1;//当前页码
	private int pageSize=10;//当前页面长度

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Short getOrgOrder() {
		return orgOrder;
	}

	public void setOrgOrder(Short orgOrder) {
		this.orgOrder = orgOrder;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	public String getLiveFlag() {
		return liveFlag;
	}

	public void setLiveFlag(String liveFlag) {
		this.liveFlag = liveFlag;
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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
