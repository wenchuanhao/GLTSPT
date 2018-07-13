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

import com.cdc.dc.metadata.cangku_ms.model.TbShareCheckbill;
import com.cdc.dc.metadata.cangku_ms.model.TbShareCheckbillLog;
import com.cdc.dc.metadata.cangku_ms.model.TbShareDepartmentall;
import com.cdc.dc.metadata.cangku_ms.model.TbShareDepartmentallLog;
import com.cdc.dc.metadata.cangku_ms.model.TbShareExpstoragebill;
import com.cdc.dc.metadata.cangku_ms.model.TbShareExpstoragebillLog;
import com.cdc.dc.metadata.cangku_ms.model.TbShareRecyclebill;
import com.cdc.dc.metadata.cangku_ms.model.TbShareRecyclebillLog;
import com.cdc.dc.metadata.cangku_ms.model.TbShareScrapbill;
import com.cdc.dc.metadata.cangku_ms.model.TbShareScrapbillLog;
import com.cdc.inter.client.db.sqlserver.jdbc.SqlJdbcConnection;
import com.cdc.system.core.util.SpringHelper;

/**
 * 仓库管理系统数据源获取
 * @author WEIFEI
 * @date 2016-6-5 下午6:10:01
 */
public class CangkuMSService {

	public static final String MS_COE = "cangku.ms";		//仓库管理系统编码
	
	
	/*V_Share_CheckBill --盘点单
	V_Share_DepartmentAllocateBill --调拨单
	V_Share_ExpStorageBill  --出库单
	V_Share_RecycleBill --退库单
	V_Share_ScrapBill --报废单*/
	
	/**
	 * 共享-盘点单
	 * @author WEIFEI
	 * @date 2016-6-5 下午6:11:39
	 * @param opercode
	 * @return	boolean
	 */
	public static boolean queryTbShareCheckbill() throws Exception{
		
		boolean retValue = false;
		List<TbShareCheckbill> list = new ArrayList<TbShareCheckbill>();
		List<TbShareCheckbillLog> list_Logs = new ArrayList<TbShareCheckbillLog>();
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = SqlJdbcConnection.getConnection(MS_COE);
			String select_sql="SELECT * FROM V_Share_CheckBill";
			ps = conn.prepareStatement(select_sql);
			rs = ps.executeQuery();
			if(rs!=null){
				JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
				List<String> sqlList = new ArrayList<String>();
				String sql;
				while(rs.next()){
					TbShareCheckbillLog vo = new TbShareCheckbillLog();	
					
					//vo.setId(rs.getString("id"));
					vo.setBillitype(rs.getInt("BillType"));
					vo.setCreatetime(rs.getTimestamp("createtime"));
					vo.setCheckbillcode(isNull(rs.getString("checkbillcode")));
					vo.setCatagoryid(rs.getInt("catagoryid"));
					vo.setCatagoryname(isNull(rs.getString("catagoryname")));
					vo.setMaterialsid(isNull(rs.getString("materialsid")));
					vo.setMaterialscode(isNull(rs.getString("materialscode")));
					vo.setMaterialsname(isNull(rs.getString("materialsname")));
					vo.setPrice(rs.getDouble("price"));
					vo.setQty(rs.getDouble("qty"));
					vo.setAmount(rs.getDouble("amount"));
					vo.setDepartmentid(rs.getInt("departmentid"));
					vo.setDepartmentname(isNull(rs.getString("departmentname")));
					
					sql = "insert into tb_share_checkbill_log	" +
								"(id, billitype, createtime, checkbillcode, catagoryid, catagoryname, materialsid, materialscode, materialsname, price, qty, amount, departmentid, departmentname)	" +
								"values	" +
								"(SYS_GUID(), "+vo.getBillitype()+","+getSqlDateStr(vo.getCreatetime(), "yyyy-MM-dd HH:mm:ss")+","+vo.getCheckbillcode()+","+vo.getCatagoryid()+" ," +
										""+vo.getCatagoryname()+","+vo.getMaterialsid()+","+vo.getMaterialscode()+","+vo.getMaterialsname()+"," +
												""+vo.getPrice()+","+vo.getQty()+","+vo.getAmount()+","+vo.getDepartmentid()+","+vo.getDepartmentname()+")	 ";
								
					sqlList.add(sql);
					list_Logs.add(vo);
				}
				//清空表数据
				jdbcTemplate.execute("truncate table tb_share_checkbill_log");
				
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
	 * 共享-调拨单
	 * @author WEIFEI
	 * @date 2016-6-17 上午10:40:37
	 * @return
	 * @throws Exception	List<TbShareDepartmentall>
	 */
	public static boolean queryTbShareDepartmentall() throws Exception{
		
		boolean retValue = false;
		List<TbShareDepartmentall> list = new ArrayList<TbShareDepartmentall>();
		List<TbShareDepartmentallLog> list_Logs = new ArrayList<TbShareDepartmentallLog>();
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = SqlJdbcConnection.getConnection(MS_COE);
			String select_sql="SELECT * FROM V_Share_DepartmentAllocateBill";
			ps = conn.prepareStatement(select_sql);
			rs = ps.executeQuery();
			if(rs!=null){
				JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
				List<String> sqlList = new ArrayList<String>();
				String sql;
				while(rs.next()){
					TbShareDepartmentallLog vo = new TbShareDepartmentallLog();	
					
					//vo.setId(id);
					vo.setBillitype(rs.getInt("BillIType"));
					vo.setCreatetime(rs.getTimestamp("createtime"));
					vo.setAllocatebillcode(isNull(rs.getString("allocatebillcode")));
					vo.setCatagoryid(rs.getInt("catagoryid"));
					vo.setCatagoryname(isNull(rs.getString("catagoryname")));
					vo.setMaterialsid(isNull(rs.getString("materialsid")));
					vo.setMaterialscode(isNull(rs.getString("materialscode")));
					vo.setMaterialsname(isNull(rs.getString("materialsname")));
					vo.setPrice(rs.getDouble("price"));
					vo.setQty(rs.getDouble("qty"));
					vo.setAmount(rs.getDouble("amount"));
					vo.setSourcewarehouseid(rs.getInt("sourcewarehouseid"));
					vo.setSourcewarehousename(isNull(rs.getString("sourcewarehousename")));
					vo.setTargetwarehouseid(rs.getInt("targetwarehouseid"));
					vo.setTargetwarehousename(isNull(rs.getString("targetwarehousename")));
					vo.setDepartmentid(rs.getInt("departmentid"));
					vo.setDepartmentname(isNull(rs.getString("departmentname")));
					
					sql = "insert into tb_share_departmentall_log " +
							  " (id, billitype, createtime, allocatebillcode, catagoryid, catagoryname, materialsid, materialscode, materialsname, price, qty, amount, sourcewarehouseid, sourcewarehousename, targetwarehouseid, targetwarehousename, departmentid, departmentname) " +
							" values " +
							 "  (SYS_GUID(), "+vo.getBillitype()+", "+getSqlDateStr(vo.getCreatetime(), "yyyy-MM-dd HH:mm:ss")+", "+vo.getAllocatebillcode()+", "+vo.getCatagoryid()+", "+vo.getCatagoryname()+"" +
							 		","+vo.getMaterialsid()+", "+vo.getMaterialscode()+", "+vo.getMaterialsname()+", "+vo.getPrice()+", "+vo.getQty()+", "+vo.getAmount()+"" +
							 				","+vo.getSourcewarehouseid()+" , "+vo.getSourcewarehousename()+", "+vo.getTargetwarehouseid()+", "+vo.getTargetwarehousename()+"" +
							 						", "+vo.getDepartmentid()+", "+vo.getDepartmentname()+")" 
								;
					sqlList.add(sql);
					list_Logs.add(vo);
				}
				//清空表数据
				jdbcTemplate.execute("truncate table tb_share_departmentall_log");
				
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
	 * 共享-出库库单
	 * @author WEIFEI
	 * @date 2016-6-17 上午10:53:21
	 * @return
	 * @throws Exception	List<TbShareExpstoragebill>
	 */
	public static boolean queryTbShareExpstoragebill() throws Exception{
		
		boolean retValue = false;
		List<TbShareExpstoragebill> list = new ArrayList<TbShareExpstoragebill>();
		List<TbShareExpstoragebillLog> list_Logs = new ArrayList<TbShareExpstoragebillLog>();
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = SqlJdbcConnection.getConnection(MS_COE);
			String select_sql="select * from V_Share_ExpStorageBill";
			ps = conn.prepareStatement(select_sql);
			rs = ps.executeQuery();
			if(rs!=null){
				JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
				List<String> sqlList = new ArrayList<String>();
				String sql;
				while(rs.next()){
					TbShareExpstoragebillLog vo = new TbShareExpstoragebillLog();	
					
					//vo.setId(id);
					vo.setBillitype(rs.getInt("BillIType"));
					vo.setCreatetime(rs.getTimestamp("createtime"));
					vo.setExpbillcode(isNull(rs.getString("expbillcode")));
					vo.setCatagoryid(rs.getInt("catagoryid"));
					vo.setCatagoryname(isNull(rs.getString("catagoryname")));
					vo.setMaterialsid(isNull(rs.getString("materialsid")));
					vo.setMaterialscode(isNull(rs.getString("materialscode")));
					vo.setMaterialsname(isNull(rs.getString("materialsname")));
					vo.setPrice(rs.getDouble("price"));
					vo.setQty(rs.getDouble("qty"));
					vo.setAmount(rs.getDouble("amount"));
					vo.setWarehouseid(rs.getInt("warehouseid"));
					vo.setWarehousename(isNull(rs.getString("warehousename")));
					vo.setDepartmentid(rs.getInt("departmentid"));
					vo.setDepartmentname(isNull(rs.getString("departmentname")));
					
					sql = "insert into tb_share_expstoragebill_log  " +
							  "(id, billitype, createtime, expbillcode, catagoryid, catagoryname, materialsid, materialscode, materialsname, price, qty, amount, warehouseid, warehousename, departmentid, departmentname)" +
							"values" +
							 " (SYS_GUID(), "+vo.getBillitype()+", "+getSqlDateStr(vo.getCreatetime(), "yyyy-MM-dd HH:mm:ss")+", "+vo.getExpbillcode()+", "+vo.getCatagoryid()+", "+vo.getCatagoryname()+"" +
							 		", "+vo.getMaterialsid()+", "+vo.getMaterialscode()+", "+vo.getMaterialsname()+", "+vo.getPrice()+", "+vo.getQty()+", "+vo.getAmount()+"" +
							 				", "+vo.getWarehouseid()+", "+vo.getWarehousename()+", "+vo.getDepartmentid()+", "+vo.getDepartmentname()+")" 
								;
					sqlList.add(sql);
					list_Logs.add(vo);
				}
				//清空表数据
				jdbcTemplate.execute("truncate table tb_share_expstoragebill_log");
				
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
	 * 共享-退库单
	 * @author WEIFEI
	 * @date 2016-6-17 上午11:06:28
	 * @return
	 * @throws Exception	List<TbShareRecyclebill>
	 */
	public static boolean queryTbShareRecyclebill() throws Exception{
		
		boolean retValue = false;
		List<TbShareRecyclebill> list = new ArrayList<TbShareRecyclebill>();
		List<TbShareRecyclebillLog> list_Logs = new ArrayList<TbShareRecyclebillLog>();
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = SqlJdbcConnection.getConnection(MS_COE);
			String select_sql="select * from V_Share_RecycleBill";
			ps = conn.prepareStatement(select_sql);
			rs = ps.executeQuery();
			if(rs!=null){
				JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
				List<String> sqlList = new ArrayList<String>();
				String sql;
				while(rs.next()){
					TbShareRecyclebillLog vo = new TbShareRecyclebillLog();	
					
					//vo.setId(id);
					vo.setBillitype(rs.getInt("BillIType"));
					vo.setCreatetime(rs.getTimestamp("createtime"));
					vo.setRecyclebillcode(isNull(rs.getString("recyclebillcode")));
					vo.setCatagoryid(rs.getInt("catagoryid"));
					vo.setCatagoryname(isNull(rs.getString("catagoryname")));
					vo.setMaterialsid(isNull(rs.getString("materialsid")));
					vo.setMaterialscode(isNull(rs.getString("materialscode")));
					vo.setMaterialsname(isNull(rs.getString("materialsname")));
					vo.setPrice(rs.getDouble("price"));
					vo.setQty(rs.getDouble("qty"));
					vo.setAmount(rs.getDouble("amount"));
					vo.setWarehouseid(rs.getInt("warehouseid"));
					vo.setWarehousename(isNull(rs.getString("warehousename")));
					vo.setDepartmentid(rs.getInt("departmentid"));
					vo.setDepartmentname(isNull(rs.getString("departmentname")));
					
					sql = "insert into tb_share_recyclebill_log  " +
							 " (id, billitype, createtime, recyclebillcode, catagoryid, catagoryname, materialsid, materialscode, materialsname, price, qty, amount, warehouseid, warehousename, departmentid, departmentname)  " +
							"values  " +
							" (SYS_GUID(), "+vo.getBillitype()+", "+getSqlDateStr(vo.getCreatetime(), "yyyy-MM-dd HH:mm:ss")+", "+vo.getRecyclebillcode()+", "+vo.getCatagoryid()+", "+vo.getCatagoryname()+", "+vo.getMaterialsid()+"" +
									", "+vo.getMaterialscode()+", "+vo.getMaterialsname()+", "+vo.getPrice()+", "+vo.getQty()+", "+vo.getAmount()+", "+vo.getWarehouseid()+", "+vo.getWarehousename()+"" +
											", "+vo.getDepartmentid()+", "+vo.getDepartmentname()+")  " 

								;
					sqlList.add(sql);
					list_Logs.add(vo);
				}
				//清空表数据
				jdbcTemplate.execute("truncate table tb_share_recyclebill_log");
				
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
	 * 共享-报废单
	 * @author WEIFEI
	 * @date 2016-6-17 上午11:13:01
	 * @return
	 * @throws Exception	List<TbShareScrapbill>
	 */
	public static boolean queryTbShareScrapbill() throws Exception{
		
		boolean retValue = false;
		List<TbShareScrapbill> list = new ArrayList<TbShareScrapbill>();
		List<TbShareScrapbillLog> list_Logs = new ArrayList<TbShareScrapbillLog>();
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = SqlJdbcConnection.getConnection(MS_COE);
			String select_sql="select * from V_Share_ScrapBill";
			ps = conn.prepareStatement(select_sql);
			rs = ps.executeQuery();
			if(rs!=null){
				JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
				List<String> sqlList = new ArrayList<String>();
				String sql;
				while(rs.next()){
					TbShareScrapbillLog vo = new TbShareScrapbillLog();	
					
					//vo.setId(id);
					vo.setBillitype(rs.getInt("BillIType"));
					vo.setCreatetime(rs.getTimestamp("createtime"));
					vo.setScrapbillcode(isNull(rs.getString("scrapbillcode")));
					vo.setCatagoryid(rs.getInt("catagoryid"));
					vo.setCatagoryname(isNull(rs.getString("catagoryname")));
					vo.setMaterialsid(isNull(rs.getString("materialsid")));
					vo.setMaterialscode(isNull(rs.getString("materialscode")));
					vo.setMaterialsname(isNull(rs.getString("materialsname")));
					vo.setPrice(rs.getDouble("price"));
					vo.setQty(rs.getDouble("qty"));
					vo.setAmount(rs.getDouble("amount"));
					vo.setWarehouseid(rs.getInt("warehouseid"));
					vo.setWarehousename(isNull(rs.getString("warehousename")));
					vo.setDepartmentid(rs.getInt("departmentid"));
					vo.setDepartmentname(isNull(rs.getString("departmentname")));
					
					sql = "insert into tb_share_scrapbill_log  " +
							  "(id, billitype, createtime, scrapbillcode, catagoryid, catagoryname, materialsid, materialscode, materialsname, price, qty, amount, warehouseid, warehousename, departmentid, departmentname)  " +
							"values  " +
							  "(SYS_GUID(),  "+vo.getBillitype()+", "+getSqlDateStr(vo.getCreatetime(), "yyyy-MM-dd HH:mm:ss")+", "+vo.getScrapbillcode()+", "+vo.getCatagoryid()+", "+vo.getCatagoryname()+"" +
							  		", "+vo.getMaterialsid()+", "+vo.getMaterialscode()+", "+vo.getMaterialsname()+", "+vo.getPrice()+", "+vo.getQty()+", "+vo.getAmount()+"" +
							  				", "+vo.getWarehouseid()+", "+vo.getWarehousename()+", "+vo.getDepartmentid()+", "+vo.getDepartmentname()+")  "
								;
					sqlList.add(sql);
					
					list_Logs.add(vo);
				}
				//清空表数据
				jdbcTemplate.execute("truncate table tb_share_scrapbill_log");
				
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
	
	private static String getSqlDateStr(Date date,String format){
		try{
			DateFormat df = new SimpleDateFormat(format);
			if(date != null){
				String result = df.format(date);
				return "to_date('"+result+"','yyyy-mm-dd hh24:mi:ss')";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author WEIFEI
	 * @date 2016-6-5 下午6:09:58
	 * @param args	void
	 */
	public static void main()  throws Exception{
		/*queryTbShareCheckbill();
		queryTbShareDepartmentall();
		queryTbShareExpstoragebill();
		queryTbShareRecyclebill();
		queryTbShareScrapbill();*/
		
		//GongzuoliuMSService.queryAll();
		
		//QianlimaMSService.main();
	}
	
	public static boolean queryAll() throws Exception{
		if(queryTbShareCheckbill() && queryTbShareDepartmentall() && queryTbShareExpstoragebill()
				&& queryTbShareRecyclebill() && queryTbShareScrapbill()){
			return true;
		}
		return false;
	}

}
