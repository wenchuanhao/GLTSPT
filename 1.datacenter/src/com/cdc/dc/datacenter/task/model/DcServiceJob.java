package com.cdc.dc.datacenter.task.model;

import java.util.Date;

/**
 * DcServiceJob entity. @author MyEclipse Persistence Tools
 */

public class DcServiceJob implements java.io.Serializable {

	// Fields

	private String jobId;
	private String jobGroup;
	private String jobName;
	private String jobCode;
	private String serviceType;
	private String interfaceType;
	private String beanClass;
	private String cronExpression;
	private String cronDesc;
	private Integer serviceStatus;
	private String remark;
	private Date createDate;
	private String createUserId;
	private Date updateDate;
	private String updateUserId;
	private Integer isTrigging;

	// Constructors

	/** default constructor */
	public DcServiceJob() {
	}

	/** full constructor */
	public DcServiceJob(String jobGroup, String jobName, String jobCode,
			String serviceType, String interfaceType, String beanClass,
			String cronExpression, String cronDesc, Integer serviceStatus,
			String remark, Date createDate, String createUserId,
			Date updateDate, String updateUserId, Integer isTrigging) {
		this.jobGroup = jobGroup;
		this.jobName = jobName;
		this.jobCode = jobCode;
		this.serviceType = serviceType;
		this.interfaceType = interfaceType;
		this.beanClass = beanClass;
		this.cronExpression = cronExpression;
		this.cronDesc = cronDesc;
		this.serviceStatus = serviceStatus;
		this.remark = remark;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.updateDate = updateDate;
		this.updateUserId = updateUserId;
		this.isTrigging = isTrigging;
	}

	// Property accessors

	public String getJobId() {
		return this.jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobCode() {
		return this.jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getInterfaceType() {
		return this.interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getBeanClass() {
		return this.beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getCronExpression() {
		return this.cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getCronDesc() {
		return this.cronDesc;
	}

	public void setCronDesc(String cronDesc) {
		this.cronDesc = cronDesc;
	}

	public Integer getServiceStatus() {
		return this.serviceStatus;
	}

	public void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getIsTrigging() {
		return this.isTrigging;
	}

	public void setIsTrigging(Integer isTrigging) {
		this.isTrigging = isTrigging;
	}

}