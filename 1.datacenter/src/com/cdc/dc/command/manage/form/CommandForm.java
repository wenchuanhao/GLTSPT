package com.cdc.dc.command.manage.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;
/**
 * 指令详情页
 * @author ZENGKAI
 * @date 2016-08-30 09:58:29
 */
public class CommandForm extends PageQueryForm{
	private String commandId;   
	private String commandType;
	private String commandNum;
	private String commandForid;
	private String orgId;
	private String orgName;
	private String contractCode;//合同编号
	private String contractName;//合同名称/工程名称
	private Date updateTime;
	private Date launchTime;
	private String launchUserid;
	private String launchUsername;
	private String commandStatus;
	
	
	public String getDigEst() {
		return digEst;
	}
	public void setDigEst(String digEst) {
		this.digEst = digEst;
	}
	private Date launchBeginTime;
	private Date launchEndTime;
	
	private String worksId;//工作指令ID
	private String worksType;//指令类型(A1,A2,B,C类)
	private String supportorgId;//支撑单位id
	private String supportorgName;//支撑单位名称
	private String constructionId;//施工单位id
	private String constructionName;//施工单位名称
	private String digEst;//摘要
	
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getCommandType() {
		return commandType;
	}
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	public String getCommandNum() {
		return commandNum;
	}
	public void setCommandNum(String commandNum) {
		this.commandNum = commandNum;
	}
	public String getCommandForid() {
		return commandForid;
	}
	public void setCommandForid(String commandForid) {
		this.commandForid = commandForid;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getLaunchTime() {
		return launchTime;
	}
	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}
	public String getLaunchUserid() {
		return launchUserid; 
	}
	public void setLaunchUserid(String launchUserid) {
		this.launchUserid = launchUserid;
	}
	public String getLaunchUsername() {
		return launchUsername;
	}
	public void setLaunchUsername(String launchUsername) {
		this.launchUsername = launchUsername;
	}
	public String getCommandStatus() {
		return commandStatus;
	}
	public void setCommandStatus(String commandStatus) {
		this.commandStatus = commandStatus;
	}
	
	public Date getLaunchBeginTime() {
		return launchBeginTime;
	}
	public void setLaunchBeginTime(Date launchBeginTime) {
		this.launchBeginTime = launchBeginTime;
	}
	public Date getLaunchEndTime() {
		return launchEndTime;
	}
	public void setLaunchEndTime(Date launchEndTime) {
		this.launchEndTime = launchEndTime;
	}
	public String getWorksId() {
		return worksId;
	}
	public void setWorksId(String worksId) {
		this.worksId = worksId;
	}
	public String getWorksType() {
		return worksType;
	}
	public void setWorksType(String worksType) {
		this.worksType = worksType;
	}
	public String getSupportorgId() {
		return supportorgId;
	}
	public void setSupportorgId(String supportorgId) {
		this.supportorgId = supportorgId;
	}
	public String getSupportorgName() {
		return supportorgName;
	}
	public void setSupportorgName(String supportorgName) {
		this.supportorgName = supportorgName;
	}
	public String getConstructionId() {
		return constructionId;
	}
	public void setConstructionId(String constructionId) {
		this.constructionId = constructionId;
	}
	public String getConstructionName() {
		return constructionName;
	}
	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}

	
}
