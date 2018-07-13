package com.cdc.sys.smsManage.form;

import java.util.Date;
import java.util.List;

import org.trustel.service.form.PageQueryForm;

import com.cdc.sys.smsManage.model.SmsSendPerson;

public class SmsManageForm extends PageQueryForm{
	private String id;//组件ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getSmsGroupNmae() {
		return smsGroupNmae;
	}
	public void setSmsGroupNmae(String smsGroupNmae) {
		this.smsGroupNmae = smsGroupNmae;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getCreatePersonId() {
		return createPersonId;
	}
	public void setCreatePersonId(String createPersonId) {
		this.createPersonId = createPersonId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdatePerson() {
		return updatePerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	public String getUpdatePersonId() {
		return updatePersonId;
	}
	public void setUpdatePersonId(String updatePersonId) {
		this.updatePersonId = updatePersonId;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getStaus() {
		return staus;
	}
	public void setStaus(String staus) {
		this.staus = staus;
	}
	private List<SmsSendPerson> sendPersons;
	public List<SmsSendPerson> getSendPersons() {
		return sendPersons;
	}
	public void setSendPersons(List<SmsSendPerson> sendPersons) {
		this.sendPersons = sendPersons;
	}
	private String pId;//外键资源ID
	private String pName;//第三方名称   
	private String smsGroupNmae;//短信组名称
	private String content;//发送内容
	private Date createDate;//创建时间
	private String createPerson;//创建人
	private String createPersonId;//创建人ID
	private Date updateDate;//修改时间
	private String updatePerson;//修改人
	private String updatePersonId;//修改人ID
	private String detail;//备注
	private String staus;//删除状态 1已删除   0未删除
}
