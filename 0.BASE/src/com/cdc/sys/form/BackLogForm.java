package com.cdc.sys.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

/**
 * 待办事项
 * @author 
 *
 */
public class BackLogForm extends PageQueryForm{
	/**
	 * 需求创建待办id
	 */
	private String createWorkorderId;
	/**
	 * 需求发起待办id
	 */
	private String launchWorkorderId;
	/**
	 * 研发待办id
	 */
	private String developWorkorderId; 
	/**
	 * 版本待办id
	 */
	private String publishWorkorderId; 

	/**
	 * 工单编号
	 */
	private String workorderId;
	/**
	 * 需求负责人
	 */
	private String headId;
	/**
	 * 所属项目
	 */
	private String projectId;
	/**
	 * 项目版本
	 */
	private String versionId;
	/**
	 * 需求名称
	 */
	private String name;
	/**
	 * 需求类型
	 */
	private String type;
	/**
	 * 紧急程度
	 */
	private String emergencyDegree;
	/**
	 * 期望完成时间
	 */
	private Date expectFinishDate;
	/**
	 * 需求提出理由
	 */
	private String reason;
	/**
	 * 需求描述
	 */
	private String description;
	/**
	 * 预期带来收益
	 */
	private String expectBenefits;
	/**
	 * 创建者
	 */
	private String creater;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 当前流程状态
	 */
	private String currentStatus;
	/**
	 * 流程归档标识
	 */
	private String fileFlag;
	/**
	 * 研发发起时间
	 */
	private Date developDate;
	/**
	 * 流程编号
	 */
	private String flowCode;
	/**
	 * 当前节点ID
	 */
	private String nowNode;
	/**
	 * 节点类型
	 */
	private String nodeType;
	public String getWorkorderId() {
		return workorderId;
	}
	public void setWorkorderId(String workorderId) {
		this.workorderId = workorderId;
	}
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmergencyDegree() {
		return emergencyDegree;
	}
	public void setEmergencyDegree(String emergencyDegree) {
		this.emergencyDegree = emergencyDegree;
	}
	public Date getExpectFinishDate() {
		return expectFinishDate;
	}
	public void setExpectFinishDate(Date expectFinishDate) {
		this.expectFinishDate = expectFinishDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExpectBenefits() {
		return expectBenefits;
	}
	public void setExpectBenefits(String expectBenefits) {
		this.expectBenefits = expectBenefits;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getFileFlag() {
		return fileFlag;
	}
	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}
	public Date getDevelopDate() {
		return developDate;
	}
	public void setDevelopDate(Date developDate) {
		this.developDate = developDate;
	}
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public String getNowNode() {
		return nowNode;
	}
	public void setNowNode(String nowNode) {
		this.nowNode = nowNode;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	
	
	

	  
	
	
	
	
}
