package com.cdc.inter.client.db.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cdc.inter.client.db.sqlserver.jdbc.SqlJdbcConnection;
import com.cdc.system.core.util.SpringHelper;

/**
 * 工作流平台数据源获取
 * @author WEIFEI
 * @date 2016-6-5 下午6:10:01
 */
public class GongzuoliuMSService {

	public static final String MS_COE = "gongzuoliupingtai.ms";		//工作流平台系统编码
	
	/**
	 * 查询所有数据
	 * @author WEIFEI
	 * @date 2016-6-5 下午6:11:39
	 * @param opercode
	 * @return	boolean
	 */
	public static boolean queryAll() throws Exception{
		
		boolean retValue = false;
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
			conn = SqlJdbcConnection.getConnection(MS_COE);
			String sql;
			
			//房间
			String select_sql="SELECT * FROM BPM_AllDocument_ElectronicRepairProcess";
			ps = conn.prepareStatement(select_sql);
			rs = ps.executeQuery();
			if(rs!=null){
				List<String> sqlList = new ArrayList<String>();
				
				String wf_docstate = "";
				String baozhangbumen = "";
				
				while(rs.next()){
					sql = 
"							insert into bpm_maindata	" +
"							  (id, wf_docnumber, wf_doccreated, wf_endtime, wf_docstate, baozhangbumen)		" +
"							values	" +
"							  (SYS_GUID(), "+isNull(rs.getString("wf_docnumber"))+", "+isNull(rs.getString("wf_doccreated"))+", "+isNull(rs.getString("wf_endtime"))+", "+isNull(rs.getString("WF_DocStatus"))+", "+getDeptName(rs.getString("XmlData"))+")"
							;
					sqlList.add(sql);
				}
				
				//清空表数据
				jdbcTemplate.execute("truncate table bpm_maindata");
				
				//批量执行
				if(sqlList.size() > 0){
					jdbcTemplate.batchUpdate(sqlList.toArray(new String[sqlList.size()]));
				}
				
				//执行成功
				retValue = true;		 
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException("更新到应用表时出错,详细信息:"+ e.getMessage());
		}finally{
			ps.close();
			conn.close();
		}
		return retValue;
	}
	
	/**
	 * String类型是否为空处理
	 * @date 2016-6-18 上午10:41:11
	 * @param str
	 * @return	String
	 */
	private static String isNull(String str){
		if (str == null) {
			return null;
		}
		else if (str.trim().equals("")){
			return null;
		}else {
			return "'"+str.trim()+"'";
		}
	}
	
	private static String getDeptName(String str) throws Exception{
		if (str == null || str.equals("")) {
			return null;
		}else {
	        Pattern p = Pattern.compile("<WFItem name=\"BaozhangBuMen\" type=\"1280\">([^</]+)</WFItem>");
	        Matcher m = p.matcher(str);
	        while (m.find()) {
	            return "'"+m.group(1)+"'";
	        }
			return null;
		}
	}
	
	/**
	 * @date 2016-6-5 下午6:09:58
	 * @param args	void
	 */
	public static void main() throws Exception{
		//boolean b1 = queryAll();
		
		//System.out.println(b1== true?"定时获取bpm_maindata数据----成功。":"定时获取bpm_maindata数据----失败！");
		
	}

}
