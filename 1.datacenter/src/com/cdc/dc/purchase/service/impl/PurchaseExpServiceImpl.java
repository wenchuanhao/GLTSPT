package com.cdc.dc.purchase.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.trustel.service.IEnterpriseService;

import com.cdc.dc.purchase.form.PurchaseForm;
import com.cdc.dc.purchase.service.IPurchaseExpService;
import com.cdc.system.core.util.SpringHelper;

public class PurchaseExpServiceImpl implements IPurchaseExpService{
	private IEnterpriseService enterpriseService;
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public List<Map<String, Object>> queryRecPurchase(PurchaseForm purchaseForm) throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select "+
		        "id," +
				"t.units,"+
				"substr(t.column_ar,1,7) flowMonth,"+
				"t.column_a,"+
				"t.column_b,"+
				"t.column_ac,"+
				"t.column_ad,"+
				"t.column_ae,"+
				"t.column_c,"+
				"t.column_af,"+
				"t.column_ag,"+
				"t.column_f,"+
				"t.column_g,"+
				"t.column_i,"+
				"t.column_j,"+
				"t.column_ah,"+
				"t.column_ai,"+
				"t.column_aj,"+
				"t.column_ak,"+
				"t.column_al,"+
				"t.column_n,"+
				"t.column_o,"+
				"t.column_am,"+
				"t.column_r,"+
				"t.column_s,"+
				"t.column_t,"+
				"t.column_u,"+
				"t.column_v,"+
				"t.column_w,"+
				"t.column_x,"+
				"t.column_y,"+
				"t.column_an,"+
				"t.column_ao,"+
				"t.column_ap,"+
				"t.column_aq,"+
				"t.column_ar,"+
				"t.column_as,"+
				"t.column_at,"+
				"t.column_bb,"+
				"t.column_bc,"+
				"t.column_bd "+
				" from JF_PURCHASE_info t where 1=1 ";
		if(purchaseForm.getBeginCreateTime()!=null ){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql+" and to_date(t.column_ar,'yyyy-mm-dd')>=to_date('"+beginTime+"','yyyy-mm-dd')";
		}
		if(purchaseForm.getEndCreateTime()!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql+" and to_date(t.column_ar,'yyyy-mm-dd')<=to_date('"+endTime+"','yyyy-mm-dd')";
		}
		if(StringUtils.isNotEmpty(purchaseForm.getIds())){
			sql = sql + " and id in ("+purchaseForm.getIds()+") ";
		}
		if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ")";
		}
		if(StringUtils.isNotEmpty(purchaseForm.getColumnB())){
			sql += " and t.column_b like '%" + purchaseForm.getColumnB().trim() + "%'";
		}
		

		sql = sql + " order by t.update_time desc";
		sql = "select rownum,a.* from (" + sql + ") a";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		
		return rows;
	}
    
	/**
	 * 获取当月数据
	 */
	@Override
	public List<Map<String, Object>> queryMonPurchase(String time, PurchaseForm purchaseForm)
			throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select z.type ,nvl(x.b,0) c1,nvl(x.b_rate,'0%') c2 ,nvl(x.c,0) c3,nvl(x.c_rate,'0%') c4,nvl(y.bb,0) c5 ,nvl(y.bb_rate,'0%') c6,nvl(y.cc,0) c7 ,nvl(y.cc_rate,'0%') c8 from ";
		 sql = sql + "(select "+ 
				     "a,  "+
				     "b,  "+
				     "round(b/(select b from  "+
				     "(select "+
				     "decode(grouping(t.column_j),'1','heji',column_j) a,count(1) b   "+
				     " from JF_PURCHASE_info t where 1=1";
		if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
				sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
			}
		if(time!=null && time!=""){
			  sql = sql + " and substr(t.column_ar,1,7) = substr('"+time+"',1,7)  ";
		}
		else{
			  sql = sql + " and substr(t.column_ar,1,7) = substr(to_char(sysdate,'yyyy-mm-dd'),1,7)  ";
		}
		sql = sql + " group by rollup (column_j))"+
				" where a='heji'),4)*100||'%' as b_rate, "+
				"c,  "+
				"round(c/(select c from  "+
				"(select "+
				"decode(grouping(t.column_j),'1','heji',column_j) a,sum(nvl(t.column_ao,0)) c  "+
				" from JF_PURCHASE_info t  where 1=1   ";
		if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
		}
		if(time!=null && time!=""){
			  sql = sql + " and substr(t.column_ar,1,7) = substr('"+time+"',1,7)  ";
		}
	    else{
			  sql = sql + " and substr(t.column_ar,1,7) = substr(to_char(sysdate,'yyyy-mm-dd'),1,7)  ";
		}
		
		sql = sql + "  group by rollup (column_j)) "+
				" where a='heji'),4)*100||'%' as c_rate"+
				" from "+
				"(select "+
				"decode(grouping(t.column_j),'1','heji',column_j) a,   "+
				"count(1) b ,  "+
				"sum(nvl(t.column_ao,0)) c   "+
				"from JF_PURCHASE_info t "+
				"where 1=1 ";
		if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
		}
		if(time!=null && time!=""){
			  sql = sql + " and substr(t.column_ar,1,7) = substr('"+time+"',1,7)  ";
		}
	    else{
			  sql = sql + " and substr(t.column_ar,1,7) = substr(to_char(sysdate,'yyyy-mm-dd'),1,7)  ";
		}
		sql = sql + " group by rollup (column_j))) x,";
		
		sql = sql + "(select "+ 
			     "aa,  "+
			     "bb,  "+
			     "round(bb/(select b from  "+
			     "(select "+
			     "decode(grouping(t.column_j),'1','heji',column_j) a,count(1) b   "+
			     " from JF_PURCHASE_info t where 1=1";
	if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
		}
	if(time!=null && time!="" ){
		  sql = sql + "and to_date(t.column_ar,'yyyy-mm-dd')>=trunc(to_date('"+time+"','yyyy-mm'),'yyyy')  "+
	                  "and to_date(t.column_ar,'yyyy-mm-dd')<=trunc(add_months(to_date('"+time+"','yyyy-mm'),1),'mm')";
	}
	else{
		  sql = sql + "and to_date(t.column_ar,'yyyy-mm-dd')>=trunc(sysdate,'yyyy')  "+
	                 "and to_date(t.column_ar,'yyyy-mm-dd')<=trunc(add_months(sysdate,1),'mm')";
	}
	sql = sql + " group by rollup (column_j))"+
			" where a='heji'),4)*100||'%' as bb_rate, "+
			"cc,  "+
			"round(cc/(select c from  "+
			"(select "+
			"decode(grouping(t.column_j),'1','heji',column_j) a,sum(nvl(t.column_ao,0)) c  "+
			" from JF_PURCHASE_info t  where 1=1   ";
	if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
		sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
	}
	if(time!=null && time!=""){
		sql = sql + "and to_date(t.column_ar,'yyyy-mm-dd')>=trunc(to_date('"+time+"','yyyy-mm'),'yyyy')  "+
                   "and to_date(t.column_ar,'yyyy-mm-dd')<=trunc(add_months(to_date('"+time+"','yyyy-mm'),1),'mm')";
	}
   else{
   	  sql = sql + "and to_date(t.column_ar,'yyyy-mm-dd')>=trunc(sysdate,'yyyy')  "+
                     "and to_date(t.column_ar,'yyyy-mm-dd')<=trunc(add_months(sysdate,1),'mm')";
	}
	
	sql = sql + "  group by rollup (column_j)) "+
			" where a='heji'),4)*100||'%' as cc_rate"+
			" from "+
			"(select "+
			"decode(grouping(t.column_j),'1','heji',column_j) aa,   "+
			"count(1) bb ,  "+
			"sum(nvl(t.column_ao,0)) cc  "+
			"from JF_PURCHASE_info t "+
			"where 1=1 ";
	if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
		sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
	}
	if(time!=null && time!=""){
		sql = sql + "and to_date(t.column_ar,'yyyy-mm-dd')>=trunc(to_date('"+time+"','yyyy-mm'),'yyyy')  "+
                   "and to_date(t.column_ar,'yyyy-mm-dd')<=trunc(add_months(to_date('"+time+"','yyyy-mm'),1),'mm')";
	}
   else{
   	  sql = sql + "and to_date(t.column_ar,'yyyy-mm-dd')>=trunc(sysdate,'yyyy')  "+
                "and to_date(t.column_ar,'yyyy-mm-dd')<=trunc(add_months(sysdate,1),'mm')";
	}
	sql = sql + " group by rollup (column_j))) y, JF_purchase_columnJ z where x.a(+)=y.aa and y.aa(+)=z.type and z.colum_name='column_j' order by z.order_num ";
					
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		
		return rows;
	}
    
	/**
	 * 南方基地项目时段采购统计汇总表
	 */
	@Override
	public List<Map<String, Object>> queryPerPurchase(PurchaseForm purchaseForm) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select y.type,nvl(x.bb,0) c1,nvl(x.bb_rate,'0%') c2 ,nvl(x.cc,0) c3 ,nvl(x.cc_rate,'0%') c4 "+
				"from "+
				"(select  "+
				"aa, "+
				"bb, "+
				"round(bb/(select b from  "+
				"(select  "+
				"decode(grouping(t.column_j),'1','heji',column_j) a,count(1) b "+
				" from JF_PURCHASE_info t where 1=1  ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and to_date(t.column_ar,'yyyy-mm-dd')>=sysdate "+
				    " and to_date(t.column_ar,'yyyy-mm-dd')<=sysdate  ";
			
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql +" and to_date(t.column_ar,'yyyy-mm-dd')>=to_date('"+beginTime+"','yyyy-mm-dd')"
					  +" and to_date(t.column_ar,'yyyy-mm-dd')<=sysdate ";
		
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql +" and to_date(t.column_ar,'yyyy-mm-dd')>=sysdate"
					  +" and to_date(t.column_ar,'yyyy-mm-dd')<=to_date('"+endTime+"','yyyy-mm-dd') ";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql +" and to_date(t.column_ar,'yyyy-mm-dd')>=to_date('"+beginTime+"','yyyy-mm-dd')"
					  +" and to_date(t.column_ar,'yyyy-mm-dd')<=to_date('"+endTime+"','yyyy-mm-dd') ";
		}
		
		sql = sql + " group by rollup (column_j))"+
				" where a='heji'),4)*100||'%' as bb_rate, "+
				"cc, "+
				"round(cc/(select c from  "+
				"(select  "+
				"decode(grouping(t.column_j),'1','heji',column_j) a, sum(nvl(t.column_ao,0)) c "+
				" from JF_PURCHASE_info t  where 1=1 ";
		if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
		}
		
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and to_date(t.column_ar,'yyyy-mm-dd')>=sysdate "+
				    " and to_date(t.column_ar,'yyyy-mm-dd')<=sysdate  ";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql +" and to_date(t.column_ar,'yyyy-mm-dd')>=to_date('"+beginTime+"','yyyy-mm-dd')"
					  +" and to_date(t.column_ar,'yyyy-mm-dd')<=sysdate ";
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql +" and to_date(t.column_ar,'yyyy-mm-dd')>=sysdate"
					  +" and to_date(t.column_ar,'yyyy-mm-dd')<=to_date('"+endTime+"','yyyy-mm-dd') ";
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql +" and to_date(t.column_ar,'yyyy-mm-dd')>=to_date('"+beginTime+"','yyyy-mm-dd')"
					  +" and to_date(t.column_ar,'yyyy-mm-dd')<=to_date('"+endTime+"','yyyy-mm-dd') ";
		}
		sql = sql + "  group by rollup (column_j)) "+
				" where a='heji'),4)*100||'%' as cc_rate  "+
				"from"+
				"(select  "+
				"decode(grouping(t.column_j),'1','heji',column_j) aa,"+
				"count(1) bb , "+
				"sum(nvl(t.column_ao,0)) cc  "+
				"from JF_PURCHASE_info t  "+
				"where 1=1 ";
		if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
		}
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and to_date(t.column_ar,'yyyy-mm-dd')>=sysdate "+
				    " and to_date(t.column_ar,'yyyy-mm-dd')<=sysdate  ";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql +" and to_date(t.column_ar,'yyyy-mm-dd')>=to_date('"+beginTime+"','yyyy-mm-dd')"
					  +" and to_date(t.column_ar,'yyyy-mm-dd')<=sysdate ";
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql +" and to_date(t.column_ar,'yyyy-mm-dd')>=sysdate"
					  +" and to_date(t.column_ar,'yyyy-mm-dd')<=to_date('"+endTime+"','yyyy-mm-dd') ";
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql +" and to_date(t.column_ar,'yyyy-mm-dd')>=to_date('"+beginTime+"','yyyy-mm-dd')"
					  +" and to_date(t.column_ar,'yyyy-mm-dd')<=to_date('"+endTime+"','yyyy-mm-dd') ";
		}
		    sql = sql +" group by rollup (column_j)) ) x,JF_purchase_columnJ y "+
		    		   "where x.aa(+) = y.type and y.colum_name='column_j' order by y.order_num ";
		    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		return rows;
	}

	/**
	 * tqc情况查询
	 */
	public List<Map<String, Object>> queryTqcPurchase(PurchaseForm purchaseForm) {
		//TODO
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select   "+
				"       count(column_l) c1,         "+
				"       count(decode(column_l,'收到请购单',1)) as c2,   "+
				"       count(decode(column_l,'工作组讨论',1)) as c3,   "+
				"       count(decode(column_l,'采购方案呈批',1)) as c4, "+
				"       count(decode(column_l,'发布公告',1)) as c5,     "+
				"       count(decode(column_l,'完成评审',1)) as c6,     "+
				"       count(decode(column_l,'结果确认',1)) as c7,     "+
				"       count(decode(column_l,'结果公示',1)) as c8,     "+
				"       count(decode(column_l,'合同签订呈批',1)) as c9, "+
				"       count(decode(column_l,'合同系统审批',1)) as c10,"+
				"       count(decode(column_l,'签订纸质合同',1)) as c11,"+
				"       count(decode(column_l,'已归档',1)) as c12,      "+
				"       count(decode(column_bl,'是',1))  as c13,        "+
				"       count(decode(column_l,'完成评审',1, '结果确认',1,'结果公示',1,'合同签订呈批',1,'合同系统审批',1,'签订纸质合同',1,'已归档',1 )) as c14, "+
				"       count(decode(column_l,'签订纸质合同',1,'已归档',1))  as c15 "+       
				"   from JF_PURCHASE_info t "+ 
				"   where 1=1 ";
		//拼时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
					    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
			
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
		
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') ))";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
					" or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') )) ";	
		}
		
		//拼需求部门
		if(purchaseForm.getColumnC()!=null && purchaseForm.getColumnC() !=""){
			sql = sql + " and t.column_c like '%"+purchaseForm.getColumnC()+"%'";
		}
		//拼经办人
		if(purchaseForm.getColumnB()!=null && purchaseForm.getColumnB() !=""){
			sql = sql + " and t.column_b like '%"+purchaseForm.getColumnB()+"%'";
		}
		if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
		}
		
		 List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	     return rows;
	}

	/**
	 * 总表 (需求确认时长)
	 */
	public List<Map<String, Object>> queryConTimePurchase(PurchaseForm purchaseForm) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select "+
				" nvl(round(avg( calc_workday(to_date(t.column_o,'yyyy-mm-dd'),to_date(t.column_q,'yyyy-mm-dd'))),1),0) avgtime,"+
				" nvl(max(calc_workday(to_date(t.column_o,'yyyy-mm-dd'),to_date(t.column_q,'yyyy-mm-dd'))),0) maxtime,"+
				" nvl(min( calc_workday(to_date(t.column_o,'yyyy-mm-dd'),to_date(t.column_q,'yyyy-mm-dd'))),0) mintime,"+
				" nvl(sum(case when  calc_workday(to_date(t.column_o,'yyyy-mm-dd'),to_date(t.column_q,'yyyy-mm-dd'))"+ 
				"  >=(select t.parameter_value from sys_parameter t where parameter_type_id= (select t.parameter_type_id from sys_parameter_type t where parameter_type_code='JF_THRESHOLD') and t.parameter_code='XQ_TH')"+
				" then 1 else 0 end),0) as  stime "+
				"from JF_PURCHASE_info t "+
				"  where 1=1 ";
		        if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			          sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
		        }
		//拼时间
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
					sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
							    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
					
				}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
					String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
					sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
						    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
				
				}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
					String endTime = sdf.format(purchaseForm.getEndCreateTime());
					sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
						    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') ))";
				
				}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
					String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
					String endTime = sdf.format(purchaseForm.getEndCreateTime());
					
					sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
							" or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') )) ";	
				}
				
				//拼需求部门
				if(purchaseForm.getColumnC()!=null && purchaseForm.getColumnC() != ""){
					sql = sql + " and t.column_c like '%"+purchaseForm.getColumnC()+"%'";
				}
				//拼经办人
				if(purchaseForm.getColumnB()!=null && purchaseForm.getColumnB() != ""){
					sql = sql + " and t.column_b like '%"+purchaseForm.getColumnB()+"%'";
				}
		
				List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			     return rows;
	}
   
	/**
	 * 需求确认完毕时间-评审时长
	 */
	@Override
	public List<Map<String, Object>> queryRevTimePurchase(PurchaseForm purchaseForm) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql ="select "+ 
				"  nvl(round(avg( calc_workday(to_date(t.column_q,'yyyy-mm-dd'),to_date(t.column_ab,'yyyy-mm-dd'))),1),0) avgtime,"+
				"  nvl(max(calc_workday(to_date(t.column_q,'yyyy-mm-dd'),to_date(t.column_ab,'yyyy-mm-dd'))),0) maxtime,"+
				"  nvl(min( calc_workday(to_date(t.column_q,'yyyy-mm-dd'),to_date(t.column_ab,'yyyy-mm-dd'))),0) mintime"+
				" from JF_PURCHASE_info t where 1=1 ";
		//拼时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
					    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
			
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
		
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') ))";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
					" or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') )) ";	
		}
		
		//拼需求部门
		if(purchaseForm.getColumnC()!=null && purchaseForm.getColumnC() != ""){
			sql = sql + " and t.column_c like '%"+purchaseForm.getColumnC()+"%'";
		}
		//拼经办人
		if(purchaseForm.getColumnB()!=null && purchaseForm.getColumnB() != ""){
			sql = sql + " and t.column_b like '%"+purchaseForm.getColumnB()+"%'";
		}
		//拼接角色
        if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
	          sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
        }
		    sql = sql + " and t.column_bl = '是'";
		   

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	     return rows;
	}
    
	/**
	 * 需求确认完毕-合同系统审批完毕
	 */
	@Override
	public List<Map<String, Object>> queryApprTimePurchase(PurchaseForm purchaseForm) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select "+
				"nvl(round(avg( calc_workday(to_date(t.column_q,'yyyy-mm-dd'),to_date(t.column_as,'yyyy-mm-dd'))),1),0) avgtime, "+
				"nvl(max(calc_workday(to_date(t.column_q,'yyyy-mm-dd'),to_date(t.column_as,'yyyy-mm-dd'))),0) maxtime, "+
				"nvl(min( calc_workday(to_date(t.column_q,'yyyy-mm-dd'),to_date(t.column_as,'yyyy-mm-dd'))),0) mintime "+
				" from JF_PURCHASE_info t where 1=1 ";
		//拼时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
					sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
							    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
					
				}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
					String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
					sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
						    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
				
				}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
					String endTime = sdf.format(purchaseForm.getEndCreateTime());
					sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
						    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') ))";
				
				}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
					String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
					String endTime = sdf.format(purchaseForm.getEndCreateTime());
					
					sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
							" or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') )) ";	
				}
				
				//拼需求部门
				if(purchaseForm.getColumnC()!=null && purchaseForm.getColumnC() != ""){
					sql = sql + " and t.column_c like '%"+purchaseForm.getColumnC()+"%'";
				}
				//拼经办人
				if(purchaseForm.getColumnB()!=null && purchaseForm.getColumnB() != ""){
					sql = sql + " and t.column_b like '%"+purchaseForm.getColumnB()+"%'";
				}
				//拼接角色
		        if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
			          sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
		        }

				List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			     return rows;
	}
    
	/**
	 * 规范性
	 */
	@Override
	public List<Map<String, Object>> queryTemTimePurchase(
			PurchaseForm purchaseForm) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select "+
				"       count(decode(column_bg,'未使用省公司模板',1)) as c1,"+
				"       count(decode(column_bf,'未使用省公司模板',1)) as c2,"+
				"       count(decode(column_be,'未使用省公司模板',1)) as c3,"+
				"       nvl(sum(decode(nvl(column_bh,'无'),'无',0,1)),0) as c4 "+
				" from JF_PURCHASE_info t  where 1=1 ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
					    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
			
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
		
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') ))";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
					" or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') )) ";	
		}
		
		//拼需求部门
		if(purchaseForm.getColumnC()!=null && purchaseForm.getColumnC() != ""){
			sql = sql + " and t.column_c like '%"+purchaseForm.getColumnC()+"%'";
		}
		//拼经办人
		if(purchaseForm.getColumnB()!=null && purchaseForm.getColumnB() != ""){
			sql = sql + " and t.column_b like '%"+purchaseForm.getColumnB()+"%'";
		}
		//拼接角色
        if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
	          sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
        }

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	     return rows;		
	}
    
	/**
	 * 流标情况
	 */
	@Override
	public List<Map<String, Object>> queryBidTimePurchase(PurchaseForm purchaseForm) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select "+
				"       nvl(sum(decode(column_ba,'-',0,'无',0,1)),0) as c1,"+
				"       nvl(sum(nvl(column_ay,0)),0) as c2,"+
				"       nvl(sum(decode(column_az,'-',0,'无',0,1)),0) as c3"+
				"   from JF_PURCHASE_info t  where 1=1 ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
					    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
			
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
		
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') ))";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
					" or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') )) ";	
		}
		
		//拼需求部门
		if(purchaseForm.getColumnC()!=null && purchaseForm.getColumnC() != ""){
			sql = sql + " and t.column_c like '%"+purchaseForm.getColumnC()+"%'";
		}
		//拼经办人
		if(purchaseForm.getColumnB()!=null && purchaseForm.getColumnB() != ""){
			sql = sql + " and t.column_b like '%"+purchaseForm.getColumnB()+"%'";
		}
		//拼接角色
        if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
	          sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
        }

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	     return rows;
	}
    
	/**
	 * 单一来源采购情况
	 */
	@Override
	public List<Map<String, Object>> queryOnlTimePurchase(
			PurchaseForm purchaseForm) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select  "+
				"       nvl (sum(decode(column_i,'单一来源采购',1,0)),0) as c1,"+
				"       case when count(column_l)<>0 then "+
				"             to_char(round( nvl (sum(decode(column_i,'单一来源采购',1,0)),0)/count(column_l),4)*100, 'fm9999990.9999')||'%'"+
				"            else '0%' end as c2,"+
				"       nvl(sum(decode(column_i,'单一来源采购',column_n,0)),0) as c3,"+
				"       case when sum(to_number(column_n))=0 or sum(to_number(column_n)) is null  then "+ 
				"       '0%'  "+
				"        else to_char(round(nvl(sum(decode(column_i,'单一来源采购',column_n,0)),0)/sum(to_number(column_n)),4)*100,'fm9999990.9999')||'%' end as c4 "+       
				"   from JF_PURCHASE_info t  where 1=1";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
					    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
			
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
		
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') ))";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
					" or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') )) ";	
		}
		
		//拼需求部门
		if(purchaseForm.getColumnC()!=null && purchaseForm.getColumnC() != ""){
			sql = sql + " and t.column_c like '%"+purchaseForm.getColumnC()+"%'";
		}
		//拼经办人
		if(purchaseForm.getColumnB()!=null && purchaseForm.getColumnB() != ""){
			sql = sql + " and t.column_b like '%"+purchaseForm.getColumnB()+"%'";
		}
		//拼接角色
        if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
	          sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
        }

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	     return rows;
	}
	
	/**
	 * 单一采购来源图表
	 * @param purchaseForm
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryOnlChartPurchase(
			PurchaseForm purchaseForm) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select  "+
			     " count(column_l) as c1,"+
			     " nvl(sum(decode(column_i, '单一来源采购', 1, 0)), 0) as c2,"+
			     " sum(to_number(column_n)) as c3,"+
			     " nvl(sum(decode(column_i, '单一来源采购', column_n, 0)), 0) as c4 "+
				 "  from JF_PURCHASE_info t  where 1=1";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
					    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
			
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
		
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') ))";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
					" or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') )) ";	
		}
		
		//拼需求部门
		if(purchaseForm.getColumnC()!=null && purchaseForm.getColumnC() != ""){
			sql = sql + " and t.column_c like '%"+purchaseForm.getColumnC()+"%'";
		}
		//拼经办人
		if(purchaseForm.getColumnB()!=null && purchaseForm.getColumnB() != ""){
			sql = sql + " and t.column_b like '%"+purchaseForm.getColumnB()+"%'";
		}
		//拼接角色
        if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
	          sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
        }

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	     return rows;
	}
    
    
	/**
	 * 采购成本监控表
	 */
	@Override
	public List<Map<String, Object>> queryCosTimePurchase(PurchaseForm purchaseForm) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		String sql = "select  "+
				"        nvl(sum(to_number(column_n)),0) as c1,"+
				"        nvl(sum(to_number(column_aw)),0) as c2,"+
				"        nvl(sum(to_number(column_n)),0) - nvl(sum(to_number(column_aw)),0) as c3,"+
				"        case when sum(to_number(column_n))=0 or sum(to_number(column_n)) is  null  then "+
				"         '0%'"+
				"        else to_char(round((nvl(sum(to_number(column_n)),0) - nvl(sum(to_number(column_aw)),0))/sum(to_number(column_n)),4)*100, 'fm9999990.9999')||'%' end as c4 "+       
				"   from JF_PURCHASE_info t  where 1=1 ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
					    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
			
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= trunc(sysdate,'dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  trunc(sysdate,'dd') ) )";
		
		}else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= trunc(sysdate,'yyyy') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
				    " or  ( to_date(t.column_o,'yyyy-mm-dd') >= trunc(sysdate,'yyyy')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') ))";
		
		}else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()!=null){
			String beginTime = sdf.format(purchaseForm.getBeginCreateTime());
			String endTime = sdf.format(purchaseForm.getEndCreateTime());
			
			sql = sql + " and (( nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) >= to_date('"+beginTime+"','yyyy-mm-dd') and  nvl(to_date(t.column_as,'yyyy-mm-dd'),sysdate) <= to_date('"+endTime+"','yyyy-mm-dd') )"+ 
					" or  ( to_date(t.column_o,'yyyy-mm-dd') >= to_date('"+beginTime+"','yyyy-mm-dd')  and  to_date(t.column_o,'yyyy-mm-dd')<=  to_date('"+endTime+"','yyyy-mm-dd') )) ";	
		}
		
		//拼需求部门
		if(purchaseForm.getColumnC()!=null && purchaseForm.getColumnC() != ""){
			sql = sql + " and t.column_c like '%"+purchaseForm.getColumnC()+"%'";
		}
		//拼经办人
		if(purchaseForm.getColumnB()!=null && purchaseForm.getColumnB() != ""){
			sql = sql + " and t.column_b like '%"+purchaseForm.getColumnB()+"%'";
		}
		//拼接角色
        if(StringUtils.isNotEmpty(purchaseForm.getManageAparts())){
	          sql += " and t.column_c in (" + purchaseForm.getManageAparts().trim() + ") ";
        }

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	     return rows;		
	}

	@Override
	public void purchaseAll(HttpServletRequest request, PurchaseForm purchaseForm) throws ParseException {
		//TODO
        //tqc情况数据数据
        List<Map<String, Object>> tqcRows = queryTqcPurchase(purchaseForm);
        List<Map<String, Object>> conRows = queryConTimePurchase(purchaseForm);
        List<Map<String, Object>> revRows = queryRevTimePurchase(purchaseForm);
        List<Map<String, Object>> apprRows = queryApprTimePurchase(purchaseForm);
        List<Map<String, Object>> temRows = queryTemTimePurchase(purchaseForm);
        List<Map<String, Object>> bidRows = queryBidTimePurchase(purchaseForm);
        List<Map<String, Object>> onlRows = queryOnlTimePurchase(purchaseForm);
        List<Map<String, Object>> cosRows = queryCosTimePurchase(purchaseForm);
        
     
        request.setAttribute("tqc",tqcRows);
        request.setAttribute("con",conRows);
        request.setAttribute("rev",revRows); //需求确认完毕时间-评审时长
        request.setAttribute("app",apprRows); //需求确认完毕-合同系统审批完毕
        request.setAttribute("tem",temRows);//规范性
        request.setAttribute("bid",bidRows);//流标情况
        request.setAttribute("onl",onlRows);//单一来源采购情况
        request.setAttribute("cos",cosRows);//采购成本监控表
        
        //默认取1.1-当前时间
    	String btime ="";
        String etime ="";
      	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){         	
          	 Calendar calendar1 =Calendar.getInstance();    
          	 Calendar calendar2 =Calendar.getInstance();
          	 calendar2.clear();
          	 calendar2.set(Calendar.YEAR, calendar1.get(Calendar.YEAR));  
          	 Date yearFirst = calendar2.getTime();
          	 btime = sdf.format(yearFirst);
          	 etime =  sdf.format(new Date());
          	 purchaseForm.setBeginCreateTime(sdf.parse(btime));
          	 purchaseForm.setEndCreateTime(sdf.parse(etime));
          	 
          }else{
        	  if(purchaseForm.getBeginCreateTime() != null){
        		  btime = sdf.format(purchaseForm.getBeginCreateTime());
        	  }
        	  if(purchaseForm.getEndCreateTime() != null){
        		  etime = sdf.format(purchaseForm.getEndCreateTime());      	
        	  }
          }
	}

	@Override
	public List<Object[]> getChartData(String type,PurchaseForm purchaseForm) {
		List<Object[]> list = new ArrayList<Object[]>();
		List<Map<String, Object>> tqcRows = queryTqcPurchase(purchaseForm); 
        List<Map<String, Object>> OnlChartRows = queryOnlChartPurchase(purchaseForm); 

          	
		Map<String, Object> map = tqcRows.get(0);
		Map<String, Object> onlMap = OnlChartRows.get(0);

		// 第一张图
		if ("container01".equals(type)) {
			Object[] row = new Object[2];
			row[0] = "收到请购单";
			row[1] = map.get("C2");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "工作组讨论";
			row1[1] = map.get("C3");
			list.add(row1);
			Object[] row2 = new Object[2];
			row2[0] = "采购方案呈批";
			row2[1] = map.get("C4");
			list.add(row2);
			Object[] row3 = new Object[2];
			row3[0] = "发布公告";
			row3[1] = map.get("C5");
			list.add(row3);
			Object[] row4 = new Object[2];
			row4[0] = "完成评审";
			BigDecimal a = (BigDecimal) map.get("C6");
			BigDecimal b = (BigDecimal) map.get("C7");
			BigDecimal c = (BigDecimal) map.get("C8");
			BigDecimal d = (BigDecimal) map.get("C9");
			BigDecimal e = (BigDecimal) map.get("C10");
			BigDecimal f = (BigDecimal) map.get("C11");
			BigDecimal g = (BigDecimal) map.get("C12");
			row4[1] = a.intValue() + b.intValue() + c.intValue() + d.intValue()
					+ e.intValue() + f.intValue() + g.intValue();
			list.add(row4);
			Object[] row5 = new Object[2];
			row5[0] = "已取消";
			row5[1] = map.get("C13");
			list.add(row5);
		}
		if ("container02".equals(type)) {
			Object[] row = new Object[2];
			row[0] = "完成评审";
			row[1] = map.get("C6");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "结果确认";
			row1[1] = map.get("C7");
			list.add(row1);
			Object[] row2 = new Object[2];
			row2[0] = "结果公示";
			row2[1] = map.get("C8");
			list.add(row2);
			Object[] row3 = new Object[2];
			row3[0] = "合同签订呈批";
			row3[1] = map.get("C9");
			list.add(row3);
			Object[] row4 = new Object[2];
			row4[0] = "合同系统审批";
			row4[1] = map.get("C10");
			list.add(row4);
			Object[] row5 = new Object[2];
			row5[0] = "已完成";
			BigDecimal a = (BigDecimal) map.get("C11");
			BigDecimal b = (BigDecimal) map.get("C12");
			row5[1] = a.intValue() + b.intValue();
			list.add(row5);
		}
		if ("container03".equals(type)) {
			Object[] row = new Object[2];
			row[0] = "签订纸质合同";
			row[1] = map.get("C12");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "已归档";
			row1[1] = map.get("C13");
			list.add(row1);
		}
		if ("container09".equals(type)) {
			Object[] row = new Object[2];
			row[0] = "采购项目总数";
			row[1] = onlMap.get("C1");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "单一来源采购总数";
			row1[1] = onlMap.get("C2");
			list.add(row1);
		}
		
		if ("container10".equals(type)) {
			Object[] row = new Object[2];
			row[0] = "采购项目总额";
			row[1] = onlMap.get("C3");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "单一来源采购总额";
			row1[1] = onlMap.get("C4");
			list.add(row1);
		}
		return list;
	}

	@Override
	public List<Object[]> getColumnData(String type, PurchaseForm purchaseForm) {
		List<Map<String, Object>> conRows = queryConTimePurchase(purchaseForm);
        List<Map<String, Object>> revRows = queryRevTimePurchase(purchaseForm);
        List<Map<String, Object>> apprRows = queryApprTimePurchase(purchaseForm);
        List<Map<String, Object>> temRows = queryTemTimePurchase(purchaseForm);
        List<Map<String, Object>> bidRows = queryBidTimePurchase(purchaseForm);
        List<Map<String, Object>> cosRows = queryCosTimePurchase(purchaseForm);

        List<Object[]> list = new ArrayList<Object[]>();

        	//需求确认时长柱状图
		if ("container04".equals(type)) {
			Map<String, Object> map = conRows.get(0);
			Object[] row = new Object[2];
			row[0] = "平均时长";
			row[1] = map.get("AVGTIME");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "最大时长";
			row1[1] = map.get("MAXTIME");
			list.add(row1);
			Object[] row2 = new Object[2];
			row2[0] = "最小时长";
			row2[1] = map.get("MINTIME");
			list.add(row2);
		}
		if ("container05".equals(type)) {
			Map<String, Object> map = revRows.get(0);
			Object[] row = new Object[2];
			row[0] = "平均时长";
			row[1] = map.get("AVGTIME");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "最大时长";
			row1[1] = map.get("MAXTIME");
			list.add(row1);
			Object[] row2 = new Object[2];
			row2[0] = "最小时长";
			row2[1] = map.get("MINTIME");
			list.add(row2);

		}
		if ("container06".equals(type)) {
			Map<String, Object> map = apprRows.get(0);
			Object[] row = new Object[2];
			row[0] = "平均时长";
			row[1] = map.get("AVGTIME");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "最大时长";
			row1[1] = map.get("MAXTIME");
			list.add(row1);
			Object[] row2 = new Object[2];
			row2[0] = "最小时长";
			row2[1] = map.get("MINTIME");
			list.add(row2);

		}
		
		if ("container07".equals(type)) {
			Map<String, Object> map = temRows.get(0);
			Object[] row = new Object[2];
			row[0] = "未使用技术评分表模板";
			row[1] = map.get("C1");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "未使用合同模板";
			row1[1] = map.get("C2");
			list.add(row1);
			Object[] row2 = new Object[2];
			row2[0] = "技术与商务比例不符合公司规定";
			row2[1] = map.get("C3");
			list.add(row2);
			Object[] row3 = new Object[3];
			row3[0] = "采购过程被供应商投诉";
			row3[1] = map.get("C4");
			list.add(row3);

		}
		
		if ("container08".equals(type)) {
			Map<String, Object> map = bidRows.get(0);
			Object[] row = new Object[2];
			row[0] = "发生过流标情况的项目数";
			row[1] = map.get("C1");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "流标次数总计";
			row1[1] = map.get("C2");
			list.add(row1);
			Object[] row2 = new Object[2];
			row2[0] = "流标后更改采购方式项目数";
			row2[1] = map.get("C3");
			list.add(row2);

		}
		
		if ("container11".equals(type)) {
			Map<String, Object> map = cosRows.get(0);
			Object[] row = new Object[2];
			row[0] = "采购项目预算金额";
			row[1] = map.get("C1");
			list.add(row);
			Object[] row1 = new Object[2];
			row1[0] = "签订合同金额";
			row1[1] = map.get("C2");
			list.add(row1);
			Object[] row2 = new Object[2];
			row2[0] = "节约金额";
			row2[1] = map.get("C3");
			list.add(row2);

		}
             return list; 
	}
	

}
