package com.cdc.dc.datacenter.task.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class DatacenterForm extends PageQueryForm{
	
	private static final long serialVersionUID = 203110386701101670L;
	
	private String jobId;//服务Id
	private String jobName;//服务名称
	private String jobCode;//服务编码
	private String serviceType;//服务类型
	private String beanClass;//调用的类名
	private String cronExpression;//任务运行时间表达式
	private String remark;//备注
	private String serviceStatus;//服务状态
	private String ftpAccount;//ftp账号
    private String ftpPasswd;//ftp密码
    private String filePath;//文件目录
    private String filePattern;//文件格式
    private String dbTablename;//数据库表名
    
    private Date runDate;//执行时间
    private String runStatus;//执行状态
	
	private Date beginCreateTime;//开始时间
    private Date endCreateTime;//结束时间
    
    private String interfaceType;//接口类型
    private String cronDesc;//任务运行时间描述
    private String jdbcUsername;//JDBC账号
    private String jdbcPassword;//JDBC密码
    private String jdbcUrl;//JDBC连接地址
    
    
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getFtpAccount() {
		return ftpAccount;
	}
	public void setFtpAccount(String ftpAccount) {
		this.ftpAccount = ftpAccount;
	}
	public String getFtpPasswd() {
		return ftpPasswd;
	}
	public void setFtpPasswd(String ftpPasswd) {
		this.ftpPasswd = ftpPasswd;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFilePattern() {
		return filePattern;
	}
	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}
	public String getDbTablename() {
		return dbTablename;
	}
	public void setDbTablename(String dbTablename) {
		this.dbTablename = dbTablename;
	}
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}
	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	public Date getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	public Date getRunDate() {
		return runDate;
	}
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}
	public String getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getCronDesc() {
		return cronDesc;
	}
	public void setCronDesc(String cronDesc) {
		this.cronDesc = cronDesc;
	}
	public String getJdbcUsername() {
		return jdbcUsername;
	}
	public void setJdbcUsername(String jdbcUsername) {
		this.jdbcUsername = jdbcUsername;
	}
	public String getJdbcPassword() {
		return jdbcPassword;
	}
	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
    
}
