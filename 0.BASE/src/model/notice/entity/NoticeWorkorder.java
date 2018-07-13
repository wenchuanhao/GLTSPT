package model.notice.entity;

import java.util.Date;

/**
 * @author fuJ
 * 公告发布工单-工单信息表
 */

public class NoticeWorkorder implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String worksheetWorkorderId;//工单信息表编号
	private String workorderCode;//工单编号
	private String applyCompanyId;//组织id
	private String applyCompany;//申请公司
	private String applyUserId;//申请人ID
	private String applyUserName;//申请人姓名
	
	private Date launchTime;//发起时间
	private String workorderType;//工单类型(15)

	private Date planBeginTime;//任务计划时间开始
	private Date planEndTime;//任务计划时间结束
	
	private String applyTitle;//申请主题
	private String emergencyDegree;//紧急程度（1，低；2，中；3，高；4，极高）  (1 紧急  2 一般)
	private String applyDetail;//申请事由和业务安排
	
	private String schemeInfo;//任务操作方案
	private String schemeTimerange;//影响时间和范围
	private String schemeRisk;//风险描述
	
	private Date finishTime;//完成时间
	private String finishResult;//执行结果 0失败 1成功
	private String finishDescription;//执行结果说明
	
	private String wtStatus;//工单状态
	private String draftFlag;//草稿标记：0否，1是
	private Integer flowType;//对应flow_transition里的flow_type ，表示当前工单选择了哪种流向类型，默认为null
	
	private String repairSpeed;//解决问题速度--新增
	private String repairMass;//解决问题的质量--新增
	private String commentWords;//评语--新增
	
	private String actorName;//当前处理人
	private String actorId;//当前处理人ID
	
	private String flowChart ;//流程图
	
	private String organizationId;//所属科室id
	
	private String organizationName; //所属科室名称
	
	private String isFangAnxinxi; //是否需要填写具体操作方案
	
	// Constructors

	/** default constructor */
	public NoticeWorkorder() {
	}

	/** full constructor */
	public NoticeWorkorder(String workorderCode, String applyCompany,
			String applyUserId, String applyUserName, Date launchTime,
			String workorderType, String worksheetOpertype,
			String worksheetOpertypeSon, Date planBeginTime,
			Date planEndTime, String applyTitle, String emergencyDegree,
			String applyDetail, String schemeInfo, String schemeTimerange,
			String schemeRisk) {
		this.workorderCode = workorderCode;
		this.applyCompany = applyCompany;
		this.applyUserId = applyUserId;
		this.applyUserName = applyUserName;
		this.launchTime = launchTime;
		this.workorderType = workorderType;
		this.planBeginTime = planBeginTime;
		this.planEndTime = planEndTime;
		this.applyTitle = applyTitle;
		this.emergencyDegree = emergencyDegree;
		this.applyDetail = applyDetail;
		this.schemeInfo = schemeInfo;
		this.schemeTimerange = schemeTimerange;
		this.schemeRisk = schemeRisk;
	}

	// Property accessors

	public String getWorksheetWorkorderId() {
		return this.worksheetWorkorderId;
	}

	public void setWorksheetWorkorderId(String worksheetWorkorderId) {
		this.worksheetWorkorderId = worksheetWorkorderId;
	}

	public String getWorkorderCode() {
		return this.workorderCode;
	}

	public void setWorkorderCode(String workorderCode) {
		this.workorderCode = workorderCode;
	}

	public String getApplyCompany() {
		return this.applyCompany;
	}

	public void setApplyCompany(String applyCompany) {
		this.applyCompany = applyCompany;
	}

	public String getApplyUserId() {
		return this.applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getApplyUserName() {
		return this.applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public Date getLaunchTime() {
		return this.launchTime;
	}

	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}

	public String getWorkorderType() {
		return this.workorderType;
	}

	public void setWorkorderType(String workorderType) {
		this.workorderType = workorderType;
	}

	public Date getPlanBeginTime() {
		return this.planBeginTime;
	}

	public void setPlanBeginTime(Date planBeginTime) {
		this.planBeginTime = planBeginTime;
	}

	public Date getPlanEndTime() {
		return this.planEndTime;
	}

	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getApplyTitle() {
		return this.applyTitle;
	}

	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
	}

	public String getEmergencyDegree() {
		return this.emergencyDegree;
	}

	public void setEmergencyDegree(String emergencyDegree) {
		this.emergencyDegree = emergencyDegree;
	}

	public String getApplyDetail() {
		return this.applyDetail;
	}

	public void setApplyDetail(String applyDetail) {
		this.applyDetail = applyDetail;
	}

	public String getSchemeInfo() {
		return this.schemeInfo;
	}

	public void setSchemeInfo(String schemeInfo) {
		this.schemeInfo = schemeInfo;
	}

	public String getSchemeTimerange() {
		return this.schemeTimerange;
	}

	public void setSchemeTimerange(String schemeTimerange) {
		this.schemeTimerange = schemeTimerange;
	}

	public String getSchemeRisk() {
		return this.schemeRisk;
	}

	public void setSchemeRisk(String schemeRisk) {
		this.schemeRisk = schemeRisk;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getFinishResult() {
		return finishResult;
	}

	public void setFinishResult(String finishResult) {
		this.finishResult = finishResult;
	}

	public String getFinishDescription() {
		return finishDescription;
	}

	public void setFinishDescription(String finishDescription) {
		this.finishDescription = finishDescription;
	}

	public String getWtStatus() {
		return wtStatus;
	}

	public void setWtStatus(String wtStatus) {
		this.wtStatus = wtStatus;
	}

	public String getApplyCompanyId() {
		return applyCompanyId;
	}

	public void setApplyCompanyId(String applyCompanyId) {
		this.applyCompanyId = applyCompanyId;
	}

	public String getDraftFlag() {
		return draftFlag;
	}

	public void setDraftFlag(String draftFlag) {
		this.draftFlag = draftFlag;
	}

	public Integer getFlowType() {
		return flowType;
	}

	public void setFlowType(Integer flowType) {
		this.flowType = flowType;
	}

	public String getRepairSpeed() {
		return repairSpeed;
	}

	public void setRepairSpeed(String repairSpeed) {
		this.repairSpeed = repairSpeed;
	}

	public String getRepairMass() {
		return repairMass;
	}

	public void setRepairMass(String repairMass) {
		this.repairMass = repairMass;
	}

	public String getCommentWords() {
		return commentWords;
	}

	public void setCommentWords(String commentWords) {
		this.commentWords = commentWords;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getFlowChart() {
		return flowChart;
	}

	public void setFlowChart(String flowChart) {
		this.flowChart = flowChart;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getIsFangAnxinxi() {
		return isFangAnxinxi;
	}

	public void setIsFangAnxinxi(String isFangAnxinxi) {
		this.isFangAnxinxi = isFangAnxinxi;
	}
	
	
}