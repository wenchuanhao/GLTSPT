package com.cdc.inter.client.db.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cdc.inter.client.db.oracle.jdbc.OracleJdbcConnection;
import com.cdc.system.core.util.SpringHelper;

public class HolidayOracleService {
	public static final String MS_HOL = "holiday.ms";
	/**
	 * 同步节假日
	 * @return
	 * @throws Exception
	 */
	public static boolean synHoliday() throws Exception{
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<String> sqlList = new ArrayList<String>();
		//Object[] ob = new Object[];
		try {
			String sql = "select to_char(t.date_,'yyyy-mm-dd'),t.date_,'workday' from   ATD_FURLOUGH_LEGAL t "+ 
					     " where t.week_ in('星期六','星期日') and t.is_furlough=0 "+
					     " union all " + 
					     " select to_char(t.date_,'yyyy-mm-dd'),t.date_,'holiday' from   ATD_FURLOUGH_LEGAL t "+ 
					     " where t.week_ in('星期一','星期二','星期三','星期四','星期五') and t.is_furlough=1 ";
			conn = OracleJdbcConnection.getConnection(MS_HOL);
	    	ps = conn.prepareStatement(sql);
	    	rs = ps.executeQuery();
	    	JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
			while(rs.next()){
				String intser_sql ="insert into SYS_HOLIDAY_INFO( id, target_date,day_type, last_update,last_update_user) "+
				                   " values ('"+rs.getString(1)+"',"+getSqlDateStr(rs.getDate(2),"yyyy-MM-dd")+",'"+rs.getString(3)+"',sysdate,'系统自动同步')";
				sqlList.add(intser_sql);
			}
			
			if(sqlList.size() > 0){
				//先清空表数据
				jdbcTemplate.execute("truncate table SYS_HOLIDAY_INFO");
				//批量执行
				jdbcTemplate.batchUpdate(sqlList.toArray(new String[sqlList.size()]));
				System.out.println("更新成功");
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e; 	
		}
		
		return true;
	}
	
	private static String getSqlDateStr(Date date,String format){
		try{
			DateFormat df = new SimpleDateFormat(format);
			if(date != null){
				String result = df.format(date);
				return "to_date('"+result+"','yyyy-mm-dd')";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {		
		//synHoliday();
	}
	
}
