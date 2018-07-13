package com.cdc.inter.client.db.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cdc.inter.client.db.sqlserver.jdbc.SqlJdbcConnection;
import com.cdc.system.core.util.SpringHelper;

/**
 * 千里马数据源获取
 * @author WEIFEI
 * @date 2016-6-5 下午6:10:01
 */
public class QianlimaMSService {

	public static final String MS_COE = "qianlima.ms";		//千里马系统编码
	
	/**
	 * 查询所有房间数据
	 * @author WEIFEI
	 * @date 2016-6-5 下午6:11:39
	 * @param opercode
	 * @return	boolean
	 */
	public static boolean queryRoomAll() throws Exception{
		
		boolean retValue = false;
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
			conn = SqlJdbcConnection.getConnection(MS_COE);
			String sql;
			
			//房间
			String select_sql="SELECT * FROM ROOM";
			ps = conn.prepareStatement(select_sql);
			rs = ps.executeQuery();
			if(rs!=null){
				List<String> sqlList = new ArrayList<String>();
				while(rs.next()){
					sql = 
							"	insert into room	" +
						    "  (id, code, rmtype, building, floor, features, stdpax, maxpax, ratecode, layout, photo, fostat, hkstat, rmstat, discrep, notes, tmstamp, remarks, flag, segment, holdrsn)		" +
						    "	values	" +
						    "  (SYS_GUID(), "+isNull(rs.getString("code"))+", "+isNull(rs.getString("rmtype"))+", "+isNull(rs.getString("building"))+", "+isNull(rs.getString("floor"))+"," +
							"'"+rs.getInt("features")+"', '"+rs.getInt("stdpax")+"', "+isNull(rs.getString("maxpax"))+", "+isNull(rs.getString("ratecode"))+", "+isNull(rs.getString("layout"))+", "+isNull(rs.getString("photo"))+", "+isNull(rs.getString("fostat"))+"," +
							""+isNull(rs.getString("hkstat"))+", "+isNull(rs.getString("rmstat"))+", "+isNull(rs.getString("discrep"))+", "+isNull(rs.getString("notes"))+", "+getSqlDateStr(rs.getTimestamp("tmstamp"), "yyyy-MM-dd HH:mm:ss")+", "+isNull(rs.getString("remarks"))+"," +
							""+isNull(rs.getString("flag"))+", "+isNull(rs.getString("segment"))+", "+isNull(rs.getString("holdrsn"))+") ";
							
					sqlList.add(sql);
				}
				
				//清空表数据
				jdbcTemplate.execute("truncate table room ");
				
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
	private static String getSqlDateStr(Date date,String format){
		try{
			DateFormat df = new SimpleDateFormat(format);
			if(date != null){
				String result = df.format(date);
				return "to_date('"+result+"','yyyy-MM-dd hh24:mi:ss')";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
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
	
	/**
	 * 查询所有房间历史数据
	 * @author WEIFEI
	 * @date 2016-6-5 下午6:11:39
	 * @param opercode
	 * @return	boolean
	 */
	public static boolean queryRmlogAll() throws Exception{
		
		boolean retValue = false;
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
			conn = SqlJdbcConnection.getConnection(MS_COE);
			String sql;
			
			//房间
			String select_sql="SELECT * FROM RMLOG";
			ps = conn.prepareStatement(select_sql);
			rs = ps.executeQuery();
			if(rs!=null){
				List<String> sqlList = new ArrayList<String>();
				while(rs.next()){
					sql = 
"							insert into rmlog	" +
"							  (id, rmlogid, accid, rmno, rmstat, begdate, enddate, clerk, remarks, flag)		" +
"							values" +
"							  (SYS_GUID(), "+rs.getInt("rmlogid")+", "+rs.getInt("accid")+", "+isNull(rs.getString("rmno"))+", "+isNull(rs.getString("rmstat"))+"," +
							""+getSqlDateStr(rs.getTimestamp("begdate"), "yyyy-MM-dd HH:mm:ss")+", "+getSqlDateStr(rs.getTimestamp("enddate"), "yyyy-MM-dd HH:mm:ss")+", "+isNull(rs.getString("clerk"))+", "+isNull(rs.getString("remarks"))+", "+isNull(rs.getString("flag"))+")	"
							;
					sqlList.add(sql);
				}
				
				//清空表数据
				jdbcTemplate.execute("truncate table rmlog ");
				
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
	 * @date 2016-6-5 下午6:09:58
	 * @param args	void
	 */
	public static void main() {
		try {
			boolean b1 = queryRoomAll();
			System.out.println(b1== true?"定时获取Room数据----成功。":"定时获取Room数据----失败！");
			
			boolean b2 = queryRmlogAll();
			System.out.println(b2== true?"定时获取Rmlog数据----成功。":"定时获取Rmlog数据----失败！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean queryAll() throws Exception{
		if(queryRoomAll() && queryRmlogAll()){
			return true;
		}
		return false;
	}

}
