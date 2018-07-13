package com.cdc.sys.smsManage.form;

import org.trustel.service.form.PageQueryForm;

/**
 * 短信发送人员表form
 * @author xms
 *
 */
public class SmsSendPersonForm  extends PageQueryForm{
	private String id;//组件ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getSmsManageId() {
		return smsManageId;
	}
	public void setSmsManageId(String smsManageId) {
		this.smsManageId = smsManageId;
	}
	
	public String getNameId() {
		return nameId;
	}
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	private String nameId;//姓名ID
	private String smsManageId;//短信发送管理ID
	private String mobile;//手机号码
	private String name;//姓名
	private String detail;//备注
}
