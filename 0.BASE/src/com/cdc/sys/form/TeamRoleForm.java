package com.cdc.sys.form;

import java.io.Serializable;
import java.util.Date;

import org.trustel.service.form.PageQueryForm;

/**
 * 团队角色
 * @author ZouJing
 *
 */
public class TeamRoleForm extends PageQueryForm implements Serializable {

	private String teamRoleId;
	private String teamRoleName;
	private String teamRoleCode;
	private String teamRoleDesc;
	private String projectId;
	private String createrId;
	private Date createTime;
	private String projectName;
	
	private String teamRoleUserId;
	private String userId;
	private String userName;
	private String account;
	private String status;
	private String isPublic;
	
	public String getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	public String getTeamRoleId() {
		return teamRoleId;
	}
	public void setTeamRoleId(String teamRoleId) {
		this.teamRoleId = teamRoleId;
	}
	public String getTeamRoleName() {
		return teamRoleName;
	}
	public void setTeamRoleName(String teamRoleName) {
		this.teamRoleName = teamRoleName;
	}
	public String getTeamRoleCode() {
		return teamRoleCode;
	}
	public void setTeamRoleCode(String teamRoleCode) {
		this.teamRoleCode = teamRoleCode;
	}
	public String getTeamRoleDesc() {
		return teamRoleDesc;
	}
	public void setTeamRoleDesc(String teamRoleDesc) {
		this.teamRoleDesc = teamRoleDesc;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTeamRoleUserId() {
		return teamRoleUserId;
	}
	public void setTeamRoleUserId(String teamRoleUserId) {
		this.teamRoleUserId = teamRoleUserId;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}