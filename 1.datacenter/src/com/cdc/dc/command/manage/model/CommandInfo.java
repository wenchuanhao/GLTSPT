package com.cdc.dc.command.manage.model;

import java.util.Date;
import java.util.List;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.command.manage.common.CommandCommon;
import com.cdc.system.core.util.SpringHelper;

/**
 * 工程指令信息表
 * @author zengkai
 * @date 2016-08-30 09:58:29
 * CommandInfo entity. @author MyEclipse Persistence Tools
 */

public class CommandInfo implements java.io.Serializable {

	// Fields

	private String commandId;//信息id
	private String commandType;//文件(指令)类型
	private String commandNum;//文件(指令)编号
	private String commandForid;//文件（指令）ID（外键）
	private String orgId;//单位ID
	private String orgName;//单位（施工/乙方/来文）名称 支撑单位
	private String contractCode;//合同编号
	private String contractName;//合同名称
	private Date updateTime;//更新时间
	private Date launchTime;//发起时间
	private String launchUserid;//发起人ID
	private String launchUsername;//发起人名称
	private String commandStatus;//状态
	private String qrDir;//二维码图片二进制
	private String digEst;
	
	// Constructors

	public String getDigEst() {
		return digEst;
	}

	public void setDigEst(String digEst) {
		this.digEst = digEst;
	}

	/**
	 * 获取归档信息
	 * @date 2016-11-29 下午5:25:01
	 * @return	CommandFolder
	 */
	public CommandFolder getCommandFolder(){
		QueryBuilder query = new QueryBuilder(CommandFolder.class);
		query.where("commandInfoid",this.commandId,QueryAction.EQUAL);
		query.where("status",CommandCommon.folderStatus_S,QueryAction.EQUAL);
		query.orderBy("folderTime", false);
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		List<CommandFolder> list = (List<CommandFolder>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new CommandFolder();
	}
	
	/**
	 * 是否已被关联，如关联显示施工过程编号
	 */
	public List<CommandMaterials> getCommandMaterials(){
		QueryBuilder query = new QueryBuilder(CommandMaterials.class);
		query.where("commandNum",this.commandNum,QueryAction.EQUAL);
		query.orderBy("createTime", false);
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		return (List<CommandMaterials>) enterpriseService.query(query, 0);
	}
	
	/** default constructor */
	public CommandInfo() {
	}

	/** minimal constructor */
	public CommandInfo(String commandId) {
		this.commandId = commandId;
	}

	/** full constructor */
	public CommandInfo(String commandId, String commandType, String commandNum,
			String commandForid, String orgId, String orgName,
			String contractCode, String contractName, Date updateTime,
			Date launchTime, String launchUserid, String launchUsername,
			String commandStatus,String qrDir) {
		this.commandId = commandId;
		this.commandType = commandType;
		this.commandNum = commandNum;
		this.commandForid = commandForid;
		this.orgId = orgId;
		this.orgName = orgName;
		this.contractCode = contractCode;
		this.contractName = contractName;
		this.updateTime = updateTime;
		this.launchTime = launchTime;
		this.launchUserid = launchUserid;
		this.launchUsername = launchUsername;
		this.commandStatus = commandStatus;
		this.qrDir = qrDir;
	}

	// Property accessors

	public String getCommandId() {
		return this.commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getCommandType() {
		return this.commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getCommandNum() {
		return this.commandNum;
	}

	public void setCommandNum(String commandNum) {
		this.commandNum = commandNum;
	}

	public String getCommandForid() {
		return this.commandForid;
	}

	public void setCommandForid(String commandForid) {
		this.commandForid = commandForid;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getLaunchTime() {
		return this.launchTime;
	}

	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}

	public String getLaunchUserid() {
		return this.launchUserid;
	}

	public void setLaunchUserid(String launchUserid) {
		this.launchUserid = launchUserid;
	}

	public String getLaunchUsername() {
		return this.launchUsername;
	}

	public void setLaunchUsername(String launchUsername) {
		this.launchUsername = launchUsername;
	}

	public String getCommandStatus() {
		return this.commandStatus;
	}

	public void setCommandStatus(String commandStatus) {
		this.commandStatus = commandStatus;
	}

	public String getQrDir() {
		return qrDir;
	}

	public void setQrDir(String qrDir) {
		this.qrDir = qrDir;
	}


}