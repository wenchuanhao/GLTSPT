package com.cdc.dc.datacenter.task.model;

import java.util.Date;

/**
 * DcScheduleJob entity. @author MyEclipse Persistence Tools
 */

public class DcScheduleJob implements java.io.Serializable {

	// Fields

	private String jobId;
	private String jobGroup;
	private String jobName;
	private Integer jobStatus;
	private String cronPression;
	private String jobDesc;
	private Date createDate;
	private String createUserId;
	private Date updateDate;
	private String updateUserId;

	// Constructors

	/** default constructor */
	public DcScheduleJob() {
	}

	/** full constructor */
	public DcScheduleJob(String jobGroup, String jobName, Integer jobStatus,
			String cronPression, String jobDesc, Date createDate,
			String createUserId, Date updateDate, String updateUserId) {
		this.jobGroup = jobGroup;
		this.jobName = jobName;
		this.jobStatus = jobStatus;
		this.cronPression = cronPression;
		this.jobDesc = jobDesc;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.updateDate = updateDate;
		this.updateUserId = updateUserId;
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

	public Integer getJobStatus() {
		return this.jobStatus;
	}

	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronPression() {
		return this.cronPression;
	}

	public void setCronPression(String cronPression) {
		this.cronPression = cronPression;
	}

	public String getJobDesc() {
		return this.jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
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

}