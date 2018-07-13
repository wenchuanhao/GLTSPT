package com.cdc.sys.form;

import java.io.Serializable;
import java.util.Date;

import org.trustel.service.form.PageQueryForm;

/** 
 * @author fuJ
 * 系统公告查询表
 * date:2015-01-04
 */

public class SysNoticeForm extends PageQueryForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sysNoticeId;         //序号
    private String title;               //标题
    private String type;                //公告类型        1：紧急公告 ； 0：普通公告  
    private String status;              //状态   	  1:待审核；2：审核通过；3：审核不通过
	private String notice;              //公告内容
	private String isdrag;              //是否是草稿         1:是草稿 ； 0：不是草稿
	private String isActive;            //是否是使用状态      1:使用中 ； 0：未使用或已删除
	private String usableTime;            //存在时限
	private String toRoles;             //接收通告角色
	private String createrId;           //创建Id
	private String createrName;         //创建人	
	private String startDate;        //拟稿开始时间	
	private String finishDate;       //拟稿结束时间	
	private String usableTimeType;      //存在时限类型	1：短期(6个月)；2：长期(12个月)；3：超长期(24个月)；4：永久
	private String passUserId;           //审批人Id
	private String passUserName;         //审批人姓名
	private String noticeCategory;     //通知种类	1:系统升级;2：系统维护
	private String publishPeople;      //发布人
	private Date publishTime;          //发布时间
	private Date approveTime;			//审批日期
	private String roles;			//发布范围    1：全部；2：部分
	private String menuFlag;		//点击的菜单	1:草稿箱,2：创建的通知,3：发布的通知
	private String showStatus;		//展示状态		1:已生效，2：已过期
	private String approveTimeStrat;
	private String approveTimeEnd;
	private Date preApproveTime;   //待办产生时间
	private String preApproveTimeStart;
	private String preApproveTimeEnd;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public String getSysNoticeId() {
		return sysNoticeId;
	}
	public void setSysNoticeId(String sysNoticeId) {
		this.sysNoticeId = sysNoticeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getIsdrag() {
		return isdrag;
	}
	public void setIsdrag(String isdrag) {
		this.isdrag = isdrag;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getUsableTime() {
		return usableTime;
	}
	public void setUsableTime(String usableTime) {
		this.usableTime = usableTime;
	}
	public String getToRoles() {
		return toRoles;
	}
	public void setToRoles(String toRoles) {
		this.toRoles = toRoles;
	}
	public String getUsableTimeType() {
		return usableTimeType;
	}
	public void setUsableTimeType(String usableTimeType) {
		this.usableTimeType = usableTimeType;
	}
	public String getPassUserId() {
		return passUserId;
	}
	public void setPassUserId(String passUserId) {
		this.passUserId = passUserId;
	}
	public String getPassUserName() {
		return passUserName;
	}
	public void setPassUserName(String passUserName) {
		this.passUserName = passUserName;
	}
	public String getNoticeCategory() {
		return noticeCategory;
	}
	public void setNoticeCategory(String noticeCategory) {
		this.noticeCategory = noticeCategory;
	}
	public String getPublishPeople() {
		return publishPeople;
	}
	public void setPublishPeople(String publishPeople) {
		this.publishPeople = publishPeople;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getMenuFlag() {
		return menuFlag;
	}
	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getApproveTimeStrat() {
		return approveTimeStrat;
	}
	public void setApproveTimeStrat(String approveTimeStrat) {
		this.approveTimeStrat = approveTimeStrat;
	}
	public String getApproveTimeEnd() {
		return approveTimeEnd;
	}
	public void setApproveTimeEnd(String approveTimeEnd) {
		this.approveTimeEnd = approveTimeEnd;
	}

	public Date getPreApproveTime() {
		return preApproveTime;
	}
	public void setPreApproveTime(Date preApproveTime) {
		this.preApproveTime = preApproveTime;
	}
	public String getPreApproveTimeStart() {
		return preApproveTimeStart;
	}
	public void setPreApproveTimeStart(String preApproveTimeStart) {
		this.preApproveTimeStart = preApproveTimeStart;
	}
	public String getPreApproveTimeEnd() {
		return preApproveTimeEnd;
	}
	public void setPreApproveTimeEnd(String preApproveTimeEnd) {
		this.preApproveTimeEnd = preApproveTimeEnd;
	}
	
}
