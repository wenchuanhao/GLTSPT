package model.mail.form;

import java.io.Serializable;
import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class MailForm extends PageQueryForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String moduleNote;
	private Date logStartTime;
	private Date logEndTime;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getModuleNote() {
		return moduleNote;
	}
	public void setModuleNote(String moduleNote) {
		this.moduleNote = moduleNote;
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
