package com.cdc.sys.smsManage.form;

import org.trustel.service.form.PageQueryForm;

public class SmsManageLogForm extends PageQueryForm {

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}

	private String createTime;//创建时间
	private String content;//消息内容
	private String mobile;//手机号码
	private String serviceFlag;//服务标识
	private String returnCode;//发送短信标识
}
