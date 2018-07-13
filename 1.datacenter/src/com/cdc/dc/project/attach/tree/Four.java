package com.cdc.dc.project.attach.tree;

import java.util.List;

import com.cdc.dc.project.attach.model.GcAttach;
import com.cdc.dc.project.attach.service.IGcAttachService;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;

/**
 * 第四层
 * @author WEIFEI
 * @date 2016-7-28 下午12:09:00
 */
public class Four {
	private String ID;//	VARCHAR2(32)	N			资源ID
	private String PARENT_ID;//	VARCHAR2(32)	Y			父ID
	private String COLUMN_01;//	VARCHAR2(500)	Y			名称
	private String COLUMN_02;//	VARCHAR2(10)	Y			必须
	private String COLUMN_03;//	VARCHAR2(32)	Y			运维移交需要
	private String COLUMN_04;//	VARCHAR2(500)	Y			文档要求
	private String COLUMN_05;//	VARCHAR2(500)	Y			对标管理制度
	private String SHOW_ORDER;//	NUMBER	Y			排序
	
	
	public List<GcAttach> getListAttach() throws Exception{
		IGcAttachService gcAttachService = (IGcAttachService)SpringHelper.getBean("gcAttachService");
		GcAttach attach = new GcAttach();
		attach.setParentId(this.ID);
		attach.setColumn10(GcAttach.TYPE_ID);
		attach.setJsxmId(GcAttach.JSXM_ID);
		attach.setPageSize(Integer.MAX_VALUE - 1);
		ItemPage itemPage =gcAttachService.findGcAttach(attach);
		return (List<GcAttach>)itemPage.getItems();
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getCOLUMN_01() {
		return COLUMN_01;
	}
	public void setCOLUMN_01(String cOLUMN_01) {
		COLUMN_01 = cOLUMN_01;
	}
	public String getCOLUMN_02() {
		return COLUMN_02;
	}
	public void setCOLUMN_02(String cOLUMN_02) {
		COLUMN_02 = cOLUMN_02;
	}
	public String getCOLUMN_03() {
		return COLUMN_03;
	}
	public void setCOLUMN_03(String cOLUMN_03) {
		COLUMN_03 = cOLUMN_03;
	}
	public String getCOLUMN_04() {
		return COLUMN_04;
	}
	public void setCOLUMN_04(String cOLUMN_04) {
		COLUMN_04 = cOLUMN_04;
	}
	public String getCOLUMN_05() {
		return COLUMN_05;
	}
	public void setCOLUMN_05(String cOLUMN_05) {
		COLUMN_05 = cOLUMN_05;
	}
	public String getSHOW_ORDER() {
		return SHOW_ORDER;
	}
	public void setSHOW_ORDER(String sHOW_ORDER) {
		SHOW_ORDER = sHOW_ORDER;
	}
	
	
}
