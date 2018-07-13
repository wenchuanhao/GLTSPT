package model.sys.entity;

import java.util.Date;

public class NewViewWordAll {
	private String id;//id
	private String name;
	private String emergencyDegree;
	private String isCc;
	
	public String getIsCc() {
		return isCc;
	}
	public void setIsCc(String isCc) {
		this.isCc = isCc;
	}
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
	private String flowId;
	
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
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
