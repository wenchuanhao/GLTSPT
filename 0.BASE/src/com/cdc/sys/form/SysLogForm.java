package com.cdc.sys.form;

import java.io.Serializable;
import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class SysLogForm extends PageQueryForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private String logId;
	private String logModuleType;
	private String logModuleNote;
	private String operaterIP;
	private String logDesc;
	private Date operateTime;
	private String logType;
	private String logStartTime;
	private String logEndTime;
	private String userId;
	private String userName;



	public SysLogForm() {
	}



	public SysLogForm(String logId, String logModuleType, String logModuleNote,
			String operaterIP, String logDesc, Date operateTime,
			String logType, String logStartTime, String logEndTime) {
		super();
		this.logId = logId;
		this.logModuleType = logModuleType;
		this.logModuleNote = logModuleNote;
		this.operaterIP = operaterIP;
		this.logDesc = logDesc;
		this.operateTime = operateTime;
		this.logType = logType;
		this.logStartTime = logStartTime;
		this.logEndTime = logEndTime;
	}



	public SysLogForm(String logId, String logModuleType, String logModuleNote,
			String operaterIP, String logDesc, Date operateTime,
			String logType, String logStartTime, String logEndTime,
			String userId, String userName) {
		super();
		this.logId = logId;
		this.logModuleType = logModuleType;
		this.logModuleNote = logModuleNote;
		this.operaterIP = operaterIP;
		this.logDesc = logDesc;
		this.operateTime = operateTime;
		this.logType = logType;
		this.logStartTime = logStartTime;
		this.logEndTime = logEndTime;
		this.userId = userId;
		this.userName = userName;
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



	public String getLogId() {
		return logId;
	}



	public void setLogId(String logId) {
		this.logId = logId;
	}



	public String getLogModuleType() {
		return logModuleType;
	}



	public void setLogModuleType(String logModuleType) {
		this.logModuleType = logModuleType;
	}



	public String getLogModuleNote() {
		return logModuleNote;
	}



	public void setLogModuleNote(String logModuleNote) {
		this.logModuleNote = logModuleNote;
	}



	public String getOperaterIP() {
		return operaterIP;
	}



	public void setOperaterIP(String operaterIP) {
		this.operaterIP = operaterIP;
	}



	public String getLogDesc() {
		return logDesc;
	}



	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}



	public Date getOperateTime() {
		return operateTime;
	}



	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}



	public String getLogType() {
		return logType;
	}



	public void setLogType(String logType) {
		this.logType = logType;
	}



	public String getLogStartTime() {
		return logStartTime;
	}



	public void setLogStartTime(String logStartTime) {
		this.logStartTime = logStartTime;
	}



	public String getLogEndTime() {
		return logEndTime;
	}



	public void setLogEndTime(String logEndTime) {
		this.logEndTime = logEndTime;
	}

	
}
