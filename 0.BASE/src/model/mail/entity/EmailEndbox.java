package model.mail.entity;

import java.util.Date;

/**
 * 邮件已发送表
 */

public class EmailEndbox implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	
	private String id;//主键
	private String taskId;//外键
	private String moduleType;//日志记录类型
	private String moduleNote;//日志记录内容
	private String servMail;//接收email
	private String mailSubject;//email标题
	private String mailInfo;//email内容
	private String procStatus;//0/未处理、1/处理中
	private int failCount;//每发送失败+1
	private Date requestTime;//创建记录时间
	private Date sendTime;//email发送时间
	private String sendStatus;//发送状态
	
	
	private String userId;//用户id
	private String userAccount;//登录名
	private String userName;//用户名
	private String userCompany;//用户所属公司（组织架构2级）
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public String getModuleNote() {
		return moduleNote;
	}
	public void setModuleNote(String moduleNote) {
		this.moduleNote = moduleNote;
	}
	public String getServMail() {
		return servMail;
	}
	public void setServMail(String servMail) {
		this.servMail = servMail;
	}
	public String getMailInfo() {
		return mailInfo;
	}
	public void setMailInfo(String mailInfo) {
		this.mailInfo = mailInfo;
	}
	public String getProcStatus() {
		return procStatus;
	}
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	
	
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserCompany() {
		return userCompany;
	}
	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}


	
	
	
	
}