package com.cdc.inter.client.db.oracle;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.trustel.common.Utils;

import com.cdc.dc.project.model.TzlhReport;
import com.cdc.inter.client.db.oracle.jdbc.OracleJdbcConnection;
import com.trustel.common.ItemPage;

/**
 * 投资系统数据源获取
 * @author WEIFEI
 * @date 2016-6-5 下午6:10:01
 */
public class TouziOracleService {

	private static final String MS_COE = "touzi.ms";		//工作流平台系统编码
	
	/**
	 * 查询所有数据
	 * @author WEIFEI
	 * @date 2016-6-5 下午6:11:39
	 * @param opercode
	 * @return	boolean
	 */
	public static ItemPage getList(TzlhReport tzlhReport) throws Exception{
    	
		int pageSize = tzlhReport.getPageSize();
    	int pageIndex = tzlhReport.getPageIndex();
		List<TzlhReport> list = new ArrayList<TzlhReport>();
		ItemPage itemPage = new ItemPage(list,0, pageIndex,pageSize);
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			String dept = tzlhReport.getDept(),deptSQL = "";
			if(dept != null && !dept.equals("")){
				deptSQL = " and t.dept ='"+dept+"' ";
			}	
			
			String ks = tzlhReport.getKs(),ksSQL = "";
			if(ks != null && !ks.equals("")){
				ksSQL = " and t.ks like '%"+ks+"%' ";
			}

			Date beginCreateTime = tzlhReport.getBeginCreateTime();
			Date endCreateTime = tzlhReport.getEndCreateTime();
			String sTimeSql = "";String eTimeSql = "";
	        SimpleDateFormat sdf = new SimpleDateFormat();
	        sdf.applyPattern("yyyyMM");
			//创建开始时间
			if (beginCreateTime != null && !beginCreateTime.equals("")) {
				tzlhReport.setBeginCreateTime(beginCreateTime);
				sTimeSql = " and to_date(yyyymm,'yyyymm') >=   to_date('"+sdf.format(beginCreateTime)+"','yyyymm') ";
			}
			
			//创建结束时间
			if (endCreateTime != null && !endCreateTime.equals("")) {
				tzlhReport.setEndCreateTime(endCreateTime);
				eTimeSql = " and to_date(yyyymm,'yyyymm') <=  to_date('"+sdf.format(endCreateTime)+"','yyyymm') ";
			}
			
			String select_sql = "select t.*,rownum row_num from tzbb_glts t  where t.ND is not null and t.yf is not null "+deptSQL+ksSQL+sTimeSql+eTimeSql;
			
			String c = 
"				select c.*,row_number()  over(order by c.yyyymm desc,c.row_num asc)num from (									" +
"					select * from("+select_sql+")		" +
"					union all										" +
"					select											" +
"					N''xmbh,N''xmmc,b.yyyymm yyyymm,N''nd,N''yf,N''dept,N''ks,N''fzr,																									" +
"					b.JHZZJEHZ,b.SJZZJEHZ,round(decode(b.JHZZJEHZ,0,0,((b.SJZZJEHZ/b.JHZZJEHZ)*100)),2)XMZZL,												" +
"					b.JNJHTZE,b.NDWCZBKZ,round(decode(b.JNJHTZE,0,0,((b.NDWCZBKZ/b.JNJHTZE)*100)),2)NDTZJHWCL,			" +
"					b.XMTZZE,b.XMHTJE,b.HTSL,round(decode(b.XMTZZE,0,0,((b.XMHTJE/b.XMTZZE)*100)),2)htwcl,to_number(b.yyyymm)row_num				" +
"					from (																																																			" +
"						select a.yyyymm,																																														" +
"					   	sum(a.JHZZJEHZ)JHZZJEHZ ,sum(a.SJZZJEHZ)SJZZJEHZ,																													" +
"					   	sum(a.JNJHTZE)JNJHTZE,sum(a.NDWCZBKZ)NDWCZBKZ,																								" +
"					   	sum(a.XMTZZE)XMTZZE,sum(a.XMHTJE)XMHTJE,sum(a.HTSL)HTSL																												" +
"					  	from ("+select_sql+") a group by a.yyyymm																																			" +
"					)b																																																					" +
"			)c order by num asc	" ;	
	    	
			conn = OracleJdbcConnection.getConnection(MS_COE);
	    	ps = conn.prepareStatement("select count(*) count from ("+c+")");
	    	rs = ps.executeQuery();
	    	long totals = 0;
			if(rs!=null){
				while(rs.next()){
					totals = rs.getLong("count");
				}
			}
			
	    	String sql = "select b.* from(select a.* from(" +c+")a where a.num <= "+pageSize*(pageIndex)+")b where b.num >=	"+((pageIndex-1)*pageSize+1);
	    	
	    	ps = conn.prepareStatement(sql);
	    	rs = ps.executeQuery();
	    	
			if(rs!=null){
				while(rs.next()){
					TzlhReport vo = new TzlhReport();
				    vo.setA(rs.getString("XMBH"));	//项目编码
				    vo.setB(rs.getString("XMMC"));	//项目名称
				    
				    if (rs.getBigDecimal("JHZZJEHZ") != null) {
				    	vo.setC(BigDecimal.valueOf(Double.valueOf(Utils.zero2Str(rs.getBigDecimal("JHZZJEHZ").doubleValue(),"0",2))));		//计划转资金额汇总（万元）
				    }
				    if (rs.getBigDecimal("SJZZJEHZ") != null) {
				    	vo.setD(BigDecimal.valueOf(Double.valueOf(Utils.zero2Str(rs.getBigDecimal("SJZZJEHZ").doubleValue(),"0",2))));		//实际转资金额汇总（万元）
				    }				    
				    if (rs.getBigDecimal("XMZZL") != null) {
				    	vo.setE(BigDecimal.valueOf(Double.valueOf(Utils.zero2Str(rs.getBigDecimal("XMZZL").doubleValue(),"0",2))));		//项目转资率
				    }				    
				    if (rs.getBigDecimal("JNJHTZE") != null) {
				    	vo.setF(BigDecimal.valueOf(Double.valueOf(Utils.zero2Str(rs.getBigDecimal("JNJHTZE").doubleValue(),"0",2))));		//年度资本开支进度计划（万元）
				    }
				    if (rs.getBigDecimal("NDWCZBKZ") != null) {
				    	vo.setG(BigDecimal.valueOf(Double.valueOf(Utils.zero2Str(rs.getBigDecimal("NDWCZBKZ").doubleValue(),"0",2))));		//年度完成资本开支（万元）
				    }
				    if (rs.getBigDecimal("NDTZJHWCL") != null) {
				    	vo.setH(BigDecimal.valueOf(Double.valueOf(Utils.zero2Str(rs.getBigDecimal("NDTZJHWCL").doubleValue(),"0",2))));		//年度投资计划完成率
				    }				    
				    if (rs.getBigDecimal("XMTZZE") != null) {
				    	vo.setI(BigDecimal.valueOf(Double.valueOf(Utils.zero2Str(rs.getBigDecimal("XMTZZE").doubleValue(),"0",2))));		//项目投资总额（万元）
				    }
				    if (rs.getBigDecimal("XMHTJE") != null) {
				    	vo.setJ(BigDecimal.valueOf(Double.valueOf(Utils.zero2Str(rs.getBigDecimal("XMHTJE").doubleValue(),"0",2))));		//合同金额（万元）
				    }
				    vo.setK(rs.getBigDecimal("HTSL"));	//合同数量
				    if (rs.getBigDecimal("HTWCL") != null) {
				    	vo.setL(BigDecimal.valueOf(Double.valueOf(Utils.zero2Str(rs.getBigDecimal("HTWCL").doubleValue(),"0",2))));		//合同完成率
					}
				    
				    vo.setYF(rs.getString("YF"));			//月份
				    vo.setND(rs.getString("ND"));		//年度
				    vo.setDept(rs.getString("dept"));	//部门
				    vo.setKs(rs.getString("ks"));			//科室
				    vo.setFzr(rs.getString("fzr"));			//负责人
				    vo.setYyyymm(rs.getString("yyyymm"));			//年月
				    
				    list.add(vo);
				}
				
				//生成分页结果
				itemPage = new ItemPage(list, totals, pageIndex,pageSize);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException("读取投资系统表时出错,详细信息:"+ e.getMessage());
		}finally{
			ps.close();
			conn.close();
		}
		return itemPage;
	}
}
