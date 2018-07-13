package model.notice.entity;

import java.util.Date;

/**
 * @author fuJ
 * 公告发布工单-任务操作安排计划
 */

public class NoticeSchemePlan implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String worksheetSchemePlanId;//任务操作安排计划 主键ID
	private String worksheetWorkorderId;//工单信息表编号
	private String operInfo;//操作内容
	private String operUserName;//操作人
	private String operUserId;//操作人ID
	private Date operBeginTime;//开始时间
	private Date operEndTime;//结束时间
	private Date createTime;//创建时间
	private Integer orderId;//排序ID
	// Constructors

	/** default constructor */
	public NoticeSchemePlan() {
	}

	/** full constructor */
	public NoticeSchemePlan(String operInfo, String operUserName,
			String operUserId, Date operBeginTime, Date operEndTime) {
		this.operInfo = operInfo;
		this.operUserName = operUserName;
		this.operUserId = operUserId;
		this.operBeginTime = operBeginTime;
		this.operEndTime = operEndTime;
	}

	// Property accessors

	public String getWorksheetSchemePlanId() {
		return this.worksheetSchemePlanId;
	}

	public void setWorksheetSchemePlanId(String worksheetSchemePlanId) {
		this.worksheetSchemePlanId = worksheetSchemePlanId;
	}

	public String getOperInfo() {
		return this.operInfo;
	}

	public void setOperInfo(String operInfo) {
		this.operInfo = operInfo;
	}

	public String getOperUserName() {
		return this.operUserName;
	}

	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
	}

	public String getOperUserId() {
		return this.operUserId;
	}

	public void setOperUserId(String operUserId) {
		this.operUserId = operUserId;
	}

	public Date getOperBeginTime() {
		return this.operBeginTime;
	}

	public void setOperBeginTime(Date operBeginTime) {
		this.operBeginTime = operBeginTime;
	}

	public Date getOperEndTime() {
		return this.operEndTime;
	}

	public void setOperEndTime(Date operEndTime) {
		this.operEndTime = operEndTime;
	}

	public String getWorksheetWorkorderId() {
		return worksheetWorkorderId;
	}

	public void setWorksheetWorkorderId(String worksheetWorkorderId) {
		this.worksheetWorkorderId = worksheetWorkorderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}