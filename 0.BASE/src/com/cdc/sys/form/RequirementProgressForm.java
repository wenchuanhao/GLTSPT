package com.cdc.sys.form;

import org.trustel.service.form.PageQueryForm;

public class RequirementProgressForm extends PageQueryForm implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6495019172104424227L;

	private String projectId;
	
	private String versionId;
	
	public RequirementProgressForm(){
		
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	
	
}
