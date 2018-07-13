package com.cdc.dc.datacenter.task.model;

/**
 * 计划任务信息
 * @author lxl
 * @date 2016-9-17
 */
public class ScheduleJob {
	/**
	 * 任务id
	 */
	private String jobId;
	/**
	 * 任务分组
	 */
	private String jobGroup;
	/**
	 * 任务名称
	 */
	private String jobName;
	/**
	 * 任务状态，0=禁用、1=启动、2=删除
	 */
	private String jobStatus;
	/**
	 * 任务运行时间表达式
	 */
	private String cronExpression;
	/**
	 * 任务描述
	 */
	private String desc;
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
