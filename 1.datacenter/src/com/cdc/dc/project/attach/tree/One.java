package com.cdc.dc.project.attach.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cdc.system.core.util.SpringHelper;

/**
 * 第一层
 * @author WEIFEI
 * @date 2016-7-28 下午12:09:00
 */
public class One {
	
	private String ID;//	VARCHAR2(32)	N			资源ID
	private String COLUMN_01;//		VARCHAR2(2)	Y			项目类型,1软件工程2配套工程3土建工程4征地工程
	private String COLUMN_02;//		VARCHAR2(32)	Y			工程模块类型1建设项目2投资编码3子项目4合同
	private String COLUMN_03;//		VARCHAR2(32)	Y			工程模块类型1建设项目2投资编码3子项目4合同
	private String SHOW_ORDER;//		NUMBER	Y			排序
	
	public List<Two> getTwo(){
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from gc_yjd gy where gy.parent_id = '"+this.ID+"' order by show_order asc");
		
		List<Two> list2 = new ArrayList<Two>();
		
		for (int i = 0; i < list.size(); i++) {
			Two t = new Two();
			
			Map<String, Object> map = list.get(i);
			
			String ID = (String)map.get("ID");//资源ID
			String PARENT_ID = (String)map.get("PARENT_ID");//父ID
			String COLUMN_02 = (String)map.get("COLUMN_02");//	阶段名称
			
			t.setID(ID);
			t.setPARENT_ID(PARENT_ID);
			t.setCOLUMN_02(COLUMN_02);
			
			t.setSHOW_ORDER(t.getThree().size()+"");
			
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
	public String getSHOW_ORDER() {
		return SHOW_ORDER;
	}
	public void setSHOW_ORDER(String sHOW_ORDER) {
		SHOW_ORDER = sHOW_ORDER;
	}
	
	
}
