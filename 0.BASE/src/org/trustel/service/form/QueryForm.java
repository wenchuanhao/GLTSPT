package org.trustel.service.form;

public class QueryForm extends ExportForm implements IQueryForm {

	private String action;
	private String[] chk;

	public String getAction() {
		return action;
	}

	public String[] getChk() {
		return chk;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setChk(String[] chk) {
		this.chk = chk;
	}

}
