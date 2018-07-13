package com.cdc.dc.metadata.manyd;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cdc.system.core.util.SpringHelper;

public class ManYD {

	private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
	
	/**
	 * 调用存储过程，动态生成报表视图
	 * @author WEIFEI
	 * @date 2016-6-14 下午4:19:43
	 * @return	String
	 */
	public static String buildView(){
		String retValue = "";
		try {
			//1、动态生成视图
			jdbcTemplate.execute("call manyd_project()");
			
			Map<String, String> columnMap = new LinkedHashMap<String, String>();
			
			//2、中文列
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from yuan_table_029");
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				columnMap.put((String)map.get("NORMAL_ID"),(String)map.get("COLUMN_02"));
			}
			
			//2、读取视图的列
			String sql = "select column_name,comments from user_col_comments  where table_name='VIEW_MANYD_PROJECT_RESULT' ";
			List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
			for (int i = 0; i < tableList.size(); i++) {
				Map<String, Object> map = tableList.get(i);
				String column_name = ((String)map.get("column_name")).toLowerCase();
				if (column_name.equals("mm")) {
					retValue += "月份";
				}else {
					for (Map.Entry<String, String> entry : columnMap.entrySet()) {
					    if (entry.getKey().indexOf(column_name) != -1) {
					    	retValue += ","+entry.getValue();
						}
					}
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return retValue;
	}

}
