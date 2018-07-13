package com.cdc.dc.command.manage.model;

import java.util.Date;
import java.util.List;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.system.core.util.SpringHelper;

/**
 * CommandMaterials entity. @author MyEclipse Persistence Tools
 */

public class CommandMaterials implements java.io.Serializable {

	// Fields

	private String worksId;//工作指令ID
	private String worksType;//指令类型(10:施工过程资料)
	private String contractCode;//合同编号
	private String contractName;//合同名称
	private String constructionId;//来文单位id
	private String constructionName;//来文单位名称
	private String fileType;//文件类型
	private Date receivedTime;//收文时间
	private Long fileNum;//原件分数
	private String fileSummary;//文件摘要
	private Date createTime;//创建时间
	private String commandNum;//关联指令编号
	// Constructors

	/** default constructor */
	public CommandMaterials() {
	}

	/** minimal constructor */
	public CommandMaterials(String worksId) {
		this.worksId = worksId;
	}

	/** full constructor */
	public CommandMaterials(String worksId, String worksType,
			String contractCode, String contractName, String constructionId,
			String constructionName, String fileType, Date receivedTime,
			Long fileNum, String fileSummary, Date createTime,String commandNum) {
		this.worksId = worksId;
		this.worksType = worksType;
		this.contractCode = contractCode;
		this.contractName = contractName;
		this.constructionId = constructionId;
		this.constructionName = constructionName;
		this.fileType = fileType;
		this.receivedTime = receivedTime;
		this.fileNum = fileNum;
		this.fileSummary = fileSummary;
		this.createTime = createTime;
		this.commandNum = commandNum;
	}

	/**
	 * 获取归档信息
	 * @date 2016-11-29 下午5:25:01
	 * @return	CommandFolder
	 */
	public CommandInfo getCommandInfo(){
		QueryBuilder query = new QueryBuilder(CommandInfo.class);
		query.where("commandForid",this.worksId,QueryAction.EQUAL);
		query.orderBy("launchTime", false);
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		List<CommandInfo> list = (List<CommandInfo>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new CommandInfo();
	}
	// Property accessors

	public String getWorksId() {
		return this.worksId;
	}

	public void setWorksId(String worksId) {
		this.worksId = worksId;
	}

	public String getWorksType() {
		return this.worksType;
	}

	public void setWorksType(String worksType) {
		this.worksType = worksType;
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

	public String getConstructionId() {
		return this.constructionId;
	}

	public void setConstructionId(String constructionId) {
		this.constructionId = constructionId;
	}

	public String getConstructionName() {
		return this.constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getReceivedTime() {
		return this.receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Long getFileNum() {
		return this.fileNum;
	}

	public void setFileNum(Long fileNum) {
		this.fileNum = fileNum;
	}

	public String getFileSummary() {
		return this.fileSummary;
	}

	public void setFileSummary(String fileSummary) {
		this.fileSummary = fileSummary;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCommandNum() {
		return this.commandNum;
	}

	public void setCommandNum(String commandNum) {
		this.commandNum = commandNum;
	}
}