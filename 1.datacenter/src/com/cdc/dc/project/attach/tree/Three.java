package com.cdc.dc.project.attach.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cdc.system.core.util.SpringHelper;

/**
 * 第三层
 * @author WEIFEI
 * @date 2016-7-28 下午12:09:00
 */
public class Three {
	private String ID;//	VARCHAR2(32)	N			资源ID
	private String PARENT_ID;//	VARCHAR2(32)	Y			父ID
	private String COLUMN_02;//	VARCHAR2(100)	Y			阶段名称
	private String SHOW_ORDER;//	NUMBER	Y			排序
	
	public List<Four> getFour(){
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from gc_folder gf where gf.parent_id = '"+this.ID+"' order by show_order asc");
		
		List<Four> list2 = new ArrayList<Four>();
		
		for (int i = 0; i < list.size(); i++) {
			Four t = new Four();
			
			Map<String, Object> map = list.get(i);
			
			String ID = (String)map.get("ID");//资源ID
			String PARENT_ID = (String)map.get("PARENT_ID");//父ID
			String COLUMN_01 = (String)map.get("COLUMN_01");//名称
			String COLUMN_02 = (String)map.get("COLUMN_02");//	必须
			String COLUMN_03 = (String)map.get("COLUMN_03");//运维移交需要
			String COLUMN_04 = (String)map.get("COLUMN_04");//文档要求
			String COLUMN_05 = (String)map.get("COLUMN_05");//	对标管理制度
			
			t.setID(ID);
			t.setPARENT_ID(PARENT_ID);
			t.setCOLUMN_01(COLUMN_01);
			t.setCOLUMN_02(COLUMN_02);
			t.setCOLUMN_03(COLUMN_03);
			t.setCOLUMN_04(COLUMN_04);
			t.setCOLUMN_05(COLUMN_05);
			
			list2.add(t);
		}
		
		return list2;
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
	public String getCOLUMN_02() {
		return COLUMN_02;
	}
	public void setCOLUMN_02(String cOLUMN_02) {
		COLUMN_02 = cOLUMN_02;
	}
	public String getSHOW_ORDER() {
		return SHOW_ORDER;
	}
	public void setSHOW_ORDER(String sHOW_ORDER) {
		SHOW_ORDER = sHOW_ORDER;
	}
	
	
}
