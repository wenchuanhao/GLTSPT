package com.cdc.dc.account.model;

import java.io.Serializable;
import java.util.Date;

import model.sys.entity.SysUser;

public class ProblemList implements Serializable{
    private String id;              
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAccountOrderId() {
		return accountOrderId;
	}
	public void setAccountOrderId(String accountOrderId) {
		this.accountOrderId = accountOrderId;
	}
	public Date getNoticeOutTime() {
		return noticeOutTime;
	}
	public void setNoticeOutTime(Date noticeOutTime) {
		this.noticeOutTime = noticeOutTime;
	}
	public String getNopassReason() {
		return nopassReason;
	}
	public void setNopassReason(String nopassReason) {
		this.nopassReason = nopassReason;
	}
	public String getRectify() {
		return rectify;
	}
	public void setRectify(String rectify) {
		this.rectify = rectify;
	}
	public String getRemindWay() {
		return remindWay;
	}
	public void setRemindWay(String remindWay) {
		this.remindWay = remindWay;
	}
	public String getIsOutTime() {
		return isOutTime;
	}
	public void setIsOutTime(String isOutTime) {
		this.isOutTime = isOutTime;
	}
	public String getOutDay() {
		return outDay;
	}
	public void setOutDay(String outDay) {
		this.outDay = outDay;
	}
	
	public String getSetTime() {
		return setTime;
	}
	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}
	private String isOutTime; //是否超时  1是   0否
	private String setTime; //耗时时长	
	private String outDay;    //超时天数
	private String rectify; //通知整改  1是   0否
	private String remindWay; //短信1  邮箱0
	private String phase; //问题提出阶段
	private String accountOrderId;//报账单号
	private Date noticeOutTime;//通知超时时间
	private String nopassReason;//整改不通过原因 
	private String type; //问题类型
    private String detail ;     //问题详情
    private Date startTime;//通知整改时间
    private Date endTime; //整改结束时间    ,
    private String status;// 整改状态  1-整改中  2-整改通过  3-整改不通过   4-退单  5-草稿/暂存
    private String createName; //创建人
    private String createId;//创建人ID
    private Date createTime;// 创建时间
}
