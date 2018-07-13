package model.sys.entity;

import java.util.Date;

public class ViewWordAllRead {
	private String id;//id
	private String name;
	private String emergencyDegree;
	public String getEmergencyDegree() {
		return emergencyDegree;
	}
	public void setEmergencyDegree(String emergencyDegree) {
		this.emergencyDegree = emergencyDegree;
	}
	private String days;
	private String lefttime;
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getLefttime() {
		return lefttime;
	}
	public void setLefttime(String lefttime) {
		this.lefttime = lefttime;
	}
	private String userName;
	private String orgName;
	private Date createDate;
	private String actorId;
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	private String flag;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
	 
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	 
	
	
	
}
