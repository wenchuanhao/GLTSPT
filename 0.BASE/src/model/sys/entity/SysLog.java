package model.sys.entity;

import java.util.Date;

/**
 * 
 * 
 * @Description:
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-22 下午5:27:11
 * @UpdateRemark:
 * @Version: V1.0
 */
public class SysLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String logId; //日志id
	private String logModuleType;//日志模块类型
	private String logModuleNote;//日志模块记录内容
	private String operaterIP; //操作IP地址
	private String logDesc;//日志描述
	private Date operateTime;//操作时间
	private String logType;//日志类型
	private Date logStartTime;//日志开始时间
	private Date logEndTime;//日志结束时间
	
	private String userId;//用户编码
	private String userName;//操作名称
	


	public SysLog(String logModuleType, String logModuleNote,
			String operaterIP, String logDesc, Date operateTime,
			String logType, Date logStartTime, Date logEndTime,
			String userId, String userName) {
		super();
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

	public SysLog() {

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

	public Date getLogStartTime() {
		return logStartTime;
	}

	public void setLogStartTime(Date logStartTime) {
		this.logStartTime = logStartTime;
	}

	public Date getLogEndTime() {
		return logEndTime;
	}

	public void setLogEndTime(Date logEndTime) {
		this.logEndTime = logEndTime;
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

	

}