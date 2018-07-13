package com.cdc.inter.client.ws.mail.form;

import java.math.BigDecimal;
import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class InterfaceMailinfoRecordsForm extends PageQueryForm {
	private String createUserid;
	private String semail;
	
	private Date logStartTime;
	private Date logEndTime;
	public String getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public String getSemail() {
		return semail;
	}
	public void setSemail(String semail) {
		this.semail = semail;
	}
	public Date getLogStartTime() {
		return logStartTime;
	}
	public void setLogStartTime(Date logStartTime) {
		this.logStartTime = logStartTime;
	}
	public Date getLogEndTime() {
		return logEndTime;
	}
	public void setLogEndTime(Date logEndTime) {
		this.logEndTime = logEndTime;
	}
	
}
