package com.cdc.dc.datacenter.task.model;

import java.util.Date;

/**
 * DcScheduleJobRecord entity. @author MyEclipse Persistence Tools
 */

public class DcScheduleJobRecord implements java.io.Serializable {

	// Fields

	private String id;
	private String parentId;
	private Date prevRunDate;
	private Date planRunDate;
	private Date runDate;
	private Date nextRunDate;
	private Integer runStatus;
	private Integer triggerType;
	private Date createDate;
	private Date updateDate;

	// Constructors

	/** default constructor */
	public DcScheduleJobRecord() {
	}

	/** full constructor */
	public DcScheduleJobRecord(String parentId, Date prevRunDate,
			Date planRunDate, Date runDate, Date nextRunDate,
			Integer runStatus, Integer triggerType, Date createDate,
			Date updateDate) {
		this.parentId = parentId;
		this.prevRunDate = prevRunDate;
		this.planRunDate = planRunDate;
		this.runDate = runDate;
		this.nextRunDate = nextRunDate;
		this.runStatus = runStatus;
		this.triggerType = triggerType;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getPrevRunDate() {
		return this.prevRunDate;
	}

	public void setPrevRunDate(Date prevRunDate) {
		this.prevRunDate = prevRunDate;
	}

	public Date getPlanRunDate() {
		return this.planRunDate;
	}

	public void setPlanRunDate(Date planRunDate) {
		this.planRunDate = planRunDate;
	}

	public Date getRunDate() {
		return this.runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	public Date getNextRunDate() {
		return this.nextRunDate;
	}

	public void setNextRunDate(Date nextRunDate) {
		this.nextRunDate = nextRunDate;
	}

	public Integer getRunStatus() {
		return this.runStatus;
	}

	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}

	public Integer getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(Integer triggerType) {
		this.triggerType = triggerType;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}