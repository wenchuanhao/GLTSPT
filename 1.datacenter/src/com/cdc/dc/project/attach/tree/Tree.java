package com.cdc.dc.project.attach.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cdc.system.core.util.SpringHelper;

/**
 * 文档目录树
 * @author WEIFEI
 * @date 2016-7-27 下午6:09:28
 */
public class Tree {

	/**项目类型**/
	public final static Map<String, String> XMLX = new LinkedHashMap<String, String>();
	
	/**阶段**/
	public final static Map<String, String> JD = new LinkedHashMap<String, String>();
	
	/**子阶段**/
	public final static Map<String, String> ZJD = new LinkedHashMap<String, String>();
	
	/**文档目录**/
	public final static Map<String, String> WDML = new LinkedHashMap<String, String>();
	
	//项目类型,	1软件工程	2集成工程	3土建工程	4征地工程
	//工程模块类型	1建设项目文档		2投资编码文档		3子项目文档		4合同文档
	
	public static List<One> getTree(String xmlx,String gcmklx) throws Exception{
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		String sql = "";
		
		if(gcmklx != null && !gcmklx.equals("")){
			sql = "and gt.column_02 = '"+gcmklx+"'";
		}
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from gc_type gt where gt.column_01 = '"+xmlx+"'  "+sql+" order by gt.show_order asc");
		
		List<One> list2 = new ArrayList<One>();
		
		for (int i = 0; i < list.size(); i++) {
			One o = new One();
			
			Map<String, Object> map = list.get(i);
			
			String ID = (String)map.get("ID");	//资源ID
			String COLUMN_01 = (String)map.get("COLUMN_01");	//项目类型,1软件工程2配套工程3土建工程4征地工程
			String COLUMN_02 = (String)map.get("COLUMN_02");	//工程模块类型1建设项目2投资编码3子项目4合同
			String COLUMN_03 = (String)map.get("COLUMN_03");	//工程模块类型1建设项目2投资编码3子项目4合同
			
			o.setID(ID);
			o.setCOLUMN_01(COLUMN_01);
			o.setCOLUMN_02(COLUMN_02);
			o.setCOLUMN_03(COLUMN_03);
			
			list2.add(o);
		}
		
		return list2;
	}
}
