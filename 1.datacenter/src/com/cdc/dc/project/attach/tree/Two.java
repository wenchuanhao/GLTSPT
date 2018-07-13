package com.cdc.dc.project.attach.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cdc.system.core.util.SpringHelper;

/**
 * 第二层
 * @author WEIFEI
 * @date 2016-7-28 下午12:09:00
 */
public class Two {
	private String ID;//	VARCHAR2(32)	N			资源ID
	private String PARENT_ID;//	VARCHAR2(32)	Y			父ID
	private String COLUMN_02;//	VARCHAR2(100)	Y			阶段名称
	private String SHOW_ORDER;//	NUMBER	Y			排序
	
	public List<Three> getThree(){
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from gc_ejd ge where ge.parent_id = '"+this.ID+"' order by show_order asc");
		
		List<Three> list2 = new ArrayList<Three>();
		
		for (int i = 0; i < list.size(); i++) {
			Three t = new Three();
			
			Map<String, Object> map = list.get(i);
			
			String ID = (String)map.get("ID");//资源ID
			String PARENT_ID = (String)map.get("PARENT_ID");//父ID
			String COLUMN_02 = (String)map.get("COLUMN_02");//	阶段名称
			
			t.setID(ID);
			t.setPARENT_ID(PARENT_ID);
			t.setCOLUMN_02(COLUMN_02);
			
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
