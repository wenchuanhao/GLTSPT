package com.cdc.dc.advertisement.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class AdvertisementForm extends PageQueryForm {
	private String configId;//配置ID
	private String picTitle;//图片广告标题
	private String picPath;//图片广告路径
	private String picUrl;//图片广告链接
	private String picDesc;//图片广告描述
	private String picType;//图片广告类型
	private String picSort;//排序
	private Date createTime;//创建时间
	private String createUserid;//创建人
	private String createUsername;//创建人名称
	private String status;//状态，1为生效，0为无效
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getPicTitle() {
		return picTitle;
	}
	public void setPicTitle(String picTitle) {
		this.picTitle = picTitle;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getPicDesc() {
		return picDesc;
	}
	public void setPicDesc(String picDesc) {
		this.picDesc = picDesc;
	}
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
	public String getPicSort() {
		return picSort;
	}
	public void setPicSort(String picSort) {
		this.picSort = picSort;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
