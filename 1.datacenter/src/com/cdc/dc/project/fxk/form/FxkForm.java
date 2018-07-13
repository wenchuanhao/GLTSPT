package com.cdc.dc.project.fxk.form;

import org.trustel.service.form.PageQueryForm;

public class FxkForm extends PageQueryForm{
	
	public final static String FXK_DICT_TYPE =  "FXK_ROLE";//数据字典类型配置
	public final static String FXK_ADMIN =  "1";//风险库管理员
	public final static String GCZB_ADMIN =  "2";//周报管理员
	
	public final static String FXK_LEVEL1 =  "1";//阶段一级
	public final static String FXK_LEVEL2 =  "2";//阶段二级
	
	private String id;

	private String stageId;
	
	private String content;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
