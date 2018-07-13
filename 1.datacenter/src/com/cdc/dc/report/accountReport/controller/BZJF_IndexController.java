package com.cdc.dc.report.accountReport.controller;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysHolidayInfo;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.util.DateUtils;

import com.cdc.dc.account.common.AccountCommon;
import com.cdc.dc.account.model.AccountImportInfo;
import com.cdc.dc.account.model.FlowInfo;
import com.cdc.dc.account.model.ProblemList;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.report.accountReport.service.ILaungchAgingService;
import com.cdc.dc.report.accountReport.vo.PieVO;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.util.DateUtil;
import com.cdc.util.ExcelUtil;

/**
 * 计财经分首页
 * @author WEIFEI
 * @date 2016-6-23 上午10:39:57
 */
@Controller
@RequestMapping(value="/BZJF/")
public class BZJF_IndexController {
	
	@Autowired
	private ILaungchAgingService laungchAgingService;
	@Autowired
	private IAccountService accService;
	/**
	 * key : (1--7) , value (name1,name2---name7)
	 */
	public static Map<String, String> flowMap = new LinkedHashMap<String, String>();
	
	public static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
	
	static {
		flowMap.put("1", "发起报账");
		flowMap.put("2", "业务审批");
		flowMap.put("3", "问题单据整改");
		flowMap.put("4", "南基财务审批");
		flowMap.put("5", "基地领导审批");
		flowMap.put("6", "省财务审批");
		flowMap.put("7", "出纳支付");
	}
	
	class IndexRowMapper implements RowMapper{
		
		public PieVO mapRow(ResultSet rs, int arg1) throws SQLException {
			PieVO vo = new PieVO();
			if(rs != null){
				vo.setPAYMENT_TIME(rs.getString("PAYMENT_TIME"));	//达到付款条件时间
				vo.setSUBMITER_DATE(rs.getString("SUBMITER_DATE"));	//提交报账单时间
				vo.setREACH_SEMENT(rs.getString("REACH_SEMENT"));	//达到报账时间
				vo.setBMLDAPPROVE_DATE(rs.getString("BMLDAPPROVE_DATE"));	//部门领导审批完成时间
				vo.setRECTIFY_TIME(rs.getString("RECTIFY_TIME"));	//问题单据整改时间
				vo.setIS_NO(rs.getString("IS_NO"));	//是否有问题单据
				vo.setEND_TIME(rs.getString("END_TIME"));	//问题单据整改完成时间
				vo.setSTART_TIME(rs.getString("START_TIME"));	//问题单据整改完成时间
				vo.setZBKJAPPROVE_DATE(rs.getString("ZBKJAPPROVE_DATE"));//主办会计审批时间
				vo.setPAGE_SUBMIT_DATE(rs.getString("PAGE_SUBMIT_DATE"));//纸质提交财务时间
				vo.setCWJLAPPROVE_DATE(rs.getString("CWJLAPPROVE_DATE"));	//南基财务审批完成时间
				vo.setNJLDAPPROVE_DATE(rs.getString("NJLDAPPROVE_DATE"));	//基地领导审批完成时间
				vo.setSCWAPPROVE_DATE(rs.getString("SCWAPPROVE_DATE"));	//省财务审批完成时间
				vo.setCNFKAPPROVE_DATE(rs.getString("CNFKAPPROVE_DATE"));	//出纳支付完成时间
			}
			return vo;
		}		
	}
	
	@RequestMapping(value="index" , method = {RequestMethod.POST,RequestMethod.GET})
	public String index(HttpServletRequest request) throws Exception{
		return "dc/report/accountReport/Index";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="indexChild" , method = {RequestMethod.POST,RequestMethod.GET})
	public String indexChild(HttpServletRequest request) throws Exception{
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
		
		if (startDate == null || startDate.equals("")) {
			startDate = sdf1.format(DateUtils.add(new Date(), DateUtils.TIME_MONTH, -1));
			endDate = sdf1.format(new Date());
		}

		startDate = sdf2.format(sdf1.parse(startDate));
		endDate = sdf2.format(sdf1.parse(endDate));
		
		 // endDate本月的最后一天 
		Date date = sdf2.parse(endDate);
		Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.set( Calendar.DATE,  1 );
		 calendar.roll(Calendar.DATE,  - 1 );
		 SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "dd" );
		 String lastDay = simpleFormate.format(calendar.getTime());
		 
		String sql = 
/*				1发起报账时间=达到付款条件时间-提交报账单时间
				2业务审批时间 = 部门领导审批完成时间-提交电子报账单完成时间
				3问题单据整改时间：各个阶段，问题整改时长（初审）进行累加。参考初审处理
				4南基财务审批=南基财务审批完成时间-问题单据整改完成时间（非问题单据为，南基财务审批=南基财务审批完成时间-部门领导审批完成时间）
				5基地领导审批=基地领导审批完成时间-南基财务审批完成时间
				6省财务审批=省财务审批完成时间-基地领导审批完成时间
				7出纳支付=出纳支付完成时间-省财务审批完成时间
*/
				"select b.PAYMENT_TIME,b.REACH_SEMENT, o.SUBMITER_DATE,				" + // --(1) 发起报账时间
				"o.BMLDAPPROVE_DATE,			" + //--(2)业务审批时间
				"nvl(b.RECTIFY_TIME,0)RECTIFY_TIME, 						" + //--问题单据整改时间,
				"case when (select count(*) from problem_list p where p.end_time is not null and p.account_order_id = b.order_id /*and p.status =2*/) > 0 then 'Y' else 'N' end IS_NO,	" + // --as 是否有问题单据,
				"(select max(p.end_time) from problem_list p where p.end_time is not null and p.account_order_id = b.order_id /*and p.status =2*/) end_time," +//--问题单据整改完成时间
				"(select min(p.start_time) from problem_list p where p.start_time is not null and p.account_order_id = b.order_id /*and p.status =2*/) start_time,"+
				"b.page_submit_date,				" + //--(3) 纸质提交财务时间
				"o.ZBKJAPPROVE_DATE,				" + //--(3) 主办会计
				"o.CWJLAPPROVE_DATE,				" + //--(4) 南基财务审批
				"o.NJLDAPPROVE_DATE,o.CWJLAPPROVE_DATE,	" + //--(5) 基地领导审批
				"o.SCWAPPROVE_DATE,o.NJLDAPPROVE_DATE,		" + //--(6) 省财务审批
				"o.CNFKAPPROVE_DATE,o.SCWAPPROVE_DATE		" + //--(7) 出纳支付
				 "from account_tab b,import_flow_info o					" +
				 "where b.order_id = o.order_id	 and b.RECORD_FLAT = '2' and b.TDTIME = '0'								" +
				 "and to_date(to_char(to_date(o.CSKJAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss'),'yyyymmdd'),'yyyymmdd') between to_date('"+startDate+"','yyyymm') and to_date('"+endDate+lastDay+"','yyyymmdd')	";
		
				 //--and b.RECORD_FLAT = '2'
				 //--and b.TDTIME = '0'
		
		List<PieVO> list = jdbcTemplate.query(sql, new IndexRowMapper());
		
		//查询节假日信息
		List<SysHolidayInfo> sysHolidayInfos = AccountCommon.getHoidlist(null);
		
		//每个单据，每个环节的时长
		for (int i = 0; i < list.size(); i++) {
			PieVO vo = list.get(i);
			vo.setHJ_1_SC(AccountCommon.getDate(getStrToDate(vo.getREACH_SEMENT()), getStrToDate(vo.getSUBMITER_DATE()), sysHolidayInfos)/24+"");	//百分比
			//业务审批时间 = 取晚（大）值(部门领导审批完成时间，纸质提交财务时间)-提交电子报账单完成时间
			vo.setHJ_2_SC(AccountCommon.getDate(getStrToDate(vo.getSUBMITER_DATE()),max(getStrToDate(vo.getBMLDAPPROVE_DATE()),getStrToDate(vo.getPAGE_SUBMIT_DATE())), sysHolidayInfos)/24+"");	//百分比
			//vo.setHJ_3_SC(vo.getRECTIFY_TIME()+"");	//百分比 
			vo.setHJ_3_SC(AccountCommon.getDate(getStrToDate(vo.getSTART_TIME()), getStrToDate(vo.getEND_TIME()), sysHolidayInfos)/24+"");
			//南基财务审批完成时间 = max (主办会计审批zbkjApproveDate,财务经理审批cwjlApproveDate)
			//南基财务审批完成时间-问题单据整改完成时间
			//关账期27号 - 下月4号（包含）
			Calendar cd = Calendar.getInstance();
			cd.setTime(max(getStrToDate(vo.getCWJLAPPROVE_DATE()),getStrToDate(vo.getZBKJAPPROVE_DATE())));
			cd.set(Calendar.HOUR_OF_DAY,0);
			cd.set(Calendar.MINUTE,0);
			cd.set(Calendar.SECOND,0);
			cd.set(Calendar.DAY_OF_MONTH,5);
			//本月五号
			Date date5 = cd.getTime();
			//上月27号
			cd.add(Calendar.MONTH, -1);
			cd.set(Calendar.DAY_OF_MONTH,27);
			Date date26 = cd.getTime();
			if (vo.getIS_NO().equals("Y")) {
				//跨了关账期
				if(max(getStrToDate(vo.getCWJLAPPROVE_DATE()),getStrToDate(vo.getZBKJAPPROVE_DATE())).after(date5) && getStrToDate(vo.getEND_TIME()).before(date26)){
					double beginD = AccountCommon.getDate(date26, date5, sysHolidayInfos);
					double endD = 0d;

					endD = AccountCommon.getDate(getStrToDate(vo.getEND_TIME()), max(getStrToDate(vo.getCWJLAPPROVE_DATE()),getStrToDate(vo.getZBKJAPPROVE_DATE())), sysHolidayInfos);
					vo.setHJ_4_SC((endD - beginD ) /24+"");	//百分比
				}else{

					vo.setHJ_4_SC(AccountCommon.getDate(getStrToDate(vo.getEND_TIME()), max(getStrToDate(vo.getCWJLAPPROVE_DATE()),getStrToDate(vo.getZBKJAPPROVE_DATE())), sysHolidayInfos)/24+"");	//百分比
				
				}
			}else {
				//非问题单据为，南基财务审批=南基财务审批完成时间 - 取晚（大）值(部门领导审批完成时间，纸质提交财务时间)   
				//跨了关账期
				if(max(getStrToDate(vo.getCWJLAPPROVE_DATE()),getStrToDate(vo.getZBKJAPPROVE_DATE())).after(date5) && max(getStrToDate(vo.getBMLDAPPROVE_DATE()),getStrToDate(vo.getPAGE_SUBMIT_DATE())).before(date26)){
					double beginD = AccountCommon.getDate(date26, date5, sysHolidayInfos);
					double endD = 0d;
					//非问题单据为，南基财务审批=南基财务审批完成时间 - 取晚（大）值(部门领导审批完成时间，纸质提交财务时间)   
					endD = AccountCommon.getDate(max(getStrToDate(vo.getBMLDAPPROVE_DATE()),getStrToDate(vo.getPAGE_SUBMIT_DATE())),
							max(getStrToDate(vo.getCWJLAPPROVE_DATE()),getStrToDate(vo.getZBKJAPPROVE_DATE())) , sysHolidayInfos);
					vo.setHJ_4_SC((endD - beginD ) /24+"");	//百分比
				
				}else{

					//非问题单据为，南基财务审批=南基财务审批完成时间 - 取晚（大）值(部门领导审批完成时间，纸质提交财务时间)   
					vo.setHJ_4_SC(AccountCommon.getDate(max(getStrToDate(vo.getBMLDAPPROVE_DATE()),getStrToDate(vo.getPAGE_SUBMIT_DATE())),
							max(getStrToDate(vo.getCWJLAPPROVE_DATE()),getStrToDate(vo.getZBKJAPPROVE_DATE())) , sysHolidayInfos)/24+"");	//百分比
				
				}
			}
			
			vo.setHJ_5_SC(AccountCommon.getDate(max(getStrToDate(vo.getCWJLAPPROVE_DATE()),getStrToDate(vo.getZBKJAPPROVE_DATE())), getStrToDate(vo.getNJLDAPPROVE_DATE()), sysHolidayInfos)/24+"");	//百分比
			//基地领导审批完成时间 可能为空,则减  南基财务审批完成时间
			if(getStrToDate(vo.getNJLDAPPROVE_DATE()) != null){
				vo.setHJ_6_SC(AccountCommon.getDate(getStrToDate(vo.getNJLDAPPROVE_DATE()), getStrToDate(vo.getSCWAPPROVE_DATE()), sysHolidayInfos)/24+"");	//百分比
			}else{
				vo.setHJ_6_SC(AccountCommon.getDate(max(getStrToDate(vo.getCWJLAPPROVE_DATE()),getStrToDate(vo.getZBKJAPPROVE_DATE())), getStrToDate(vo.getSCWAPPROVE_DATE()), sysHolidayInfos)/24+"");	//百分比
			}
			vo.setHJ_7_SC(AccountCommon.getDate(getStrToDate(vo.getSCWAPPROVE_DATE()), getStrToDate(vo.getCNFKAPPROVE_DATE()), sysHolidayInfos)/24+"");	//百分比
		}
		
		//key:每个环节，value:所有单据同一环节总时长
	    Map<String, Double> scMap = new LinkedHashMap<String, Double>();
	    scMap.put("1",0d);
	    scMap.put("2",0d);
	    scMap.put("3",0d);
	    scMap.put("4",0d);
	    scMap.put("5",0d);
	    scMap.put("6",0d);
	    scMap.put("7",0d);
	    
		//所有单据，各个环节总时长
		for (int i = 0; i < list.size(); i++) {
			PieVO vo = list.get(i);
			
			scMap.put("1", scMap.get("1") + Double.valueOf(vo.getHJ_1_SC()));
			scMap.put("2", scMap.get("2") + Double.valueOf(vo.getHJ_2_SC()));
			scMap.put("3", scMap.get("3") + Double.valueOf(vo.getHJ_3_SC()));
			scMap.put("4", scMap.get("4") + Double.valueOf(vo.getHJ_4_SC()));
			scMap.put("5", scMap.get("5") + Double.valueOf(vo.getHJ_5_SC()));
			scMap.put("6", scMap.get("6") + Double.valueOf(vo.getHJ_6_SC()));
			scMap.put("7", scMap.get("7") + Double.valueOf(vo.getHJ_7_SC()));
		}
		
		//key:每个环节，value:所有单据各个环节平均时效
	    Map<String, Double> pjsxMap = new LinkedHashMap<String, Double>();
	    
	    Double zpjsx = 0d;//总平均时效=各环节平均时效的合计
	    
		for (Map.Entry<String, Double> entry : scMap.entrySet()) {
			if(list != null && list.size() > 0 && entry.getValue() > 0){
				pjsxMap.put(entry.getKey(), entry.getValue() / list.size() < 0 ?0d:entry.getValue() / list.size());
			}else{
				pjsxMap.put(entry.getKey(), 0d);
			}
			
			zpjsx += pjsxMap.get(entry.getKey());
		}
		
		//全流程各环节平均时效占总平均时效的百分比
		Map<String, Double> ghjzbMap = new LinkedHashMap<String, Double>();
		
		for (Map.Entry<String, Double> entry : pjsxMap.entrySet()) {
			String key = entry.getKey();
			Double keyValue = 0d;
			
			if (entry.getValue() > 0) {
				keyValue = new BigDecimal((entry.getValue() / zpjsx) * 100).setScale(2, BigDecimal.ROUND_HALF_UP ).doubleValue();
			}
			//各环节占比
			ghjzbMap.put(key, keyValue);
		}
		
		String container1 = "['发起报账时间', "+ghjzbMap.get("1")+"], ['业务审批时间', "+ghjzbMap.get("2")+"], ['问题单据整改时间', "+ghjzbMap.get("3")+"], {name: '南基财务审批',y: "+ghjzbMap.get("4")+",sliced: true,selected: true},['基地领导审批', "+ghjzbMap.get("5")+"], ['省财务审批', "+ghjzbMap.get("6")+"], ['出纳支付', "+ghjzbMap.get("7")+"]";
		
		//-----------------------------------------------图表1-----------------------------------------------
		request.setAttribute("container1", container1);
		
		//-----------------------------------------------图表5-----------------------------------------------
		List<PieVO> container5_list = new ArrayList<PieVO>();
		String container5_value = "";
		for (Map.Entry<String, Double> entry : pjsxMap.entrySet()) {
			PieVO vo = new PieVO();
			String key = entry.getKey();
			Double value = new BigDecimal(entry.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP ).doubleValue();
			
			container5_value += ","+value+"";
			
			//列表数据
			vo.setZQ(startDate+"-"+endDate);//周期
			vo.setHJ_NAME(flowMap.get(key));	//环节名称
			vo.setHJ_PJSC(new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP ).doubleValue()+"");	//环节平均时长
			vo.setZPJSC(new BigDecimal(zpjsx).setScale(2, BigDecimal.ROUND_HALF_UP ).doubleValue()+"");	//总平均时长
			vo.setDJL(list.size()+"");//单据量
			container5_list.add(vo);
		}
		request.setAttribute("container5_key", getHJ_NameS());
		request.setAttribute("container5_value", container5_value.substring(1));
		request.setAttribute("container5_list", container5_list);
		
		request.setAttribute("zq", startDate + "-" + endDate);//统计周期
		
		//-----------------------------------------------图表2-----------------------------------------------
		tb(request, startDate, endDate);
		
		//-----------------------------------------------图表3-----------------------------------------------
		hb(request, startDate, endDate);
		
		//-----------------------------------------------图表4-----------------------------------------------
		Qst(request, startDate, endDate);
		
		//-----------------------------------------------公共参数-----------------------------------------------
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		
		request.setAttribute("cMon", Integer.valueOf(sdf2.format(new Date()).substring(4)));//默认当前月份
		Date date1 = DateUtils.valueOf(startDate, "yyyyMM");
		Date date2 = DateUtils.valueOf(endDate, "yyyyMM");
		int delta = DateUtil.getMonthDiff(date1, date2);
		request.setAttribute("cSc", delta);//当前时差
		
		return "dc/report/accountReport/IndexChild";
	}
	
	//图表4
	private void Qst(HttpServletRequest request,String startDate,String endDate) throws Exception{
		
		 // 本月的最后一天 
		Date date = new SimpleDateFormat( "yyyyMM" ).parse(endDate);
		Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.set( Calendar.DATE,  1 );
		 calendar.roll(Calendar.DATE,  - 1 );
		 SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "dd" );
		 String lastDay = simpleFormate.format(calendar.getTime());
		
		String sql =
		 "select department,type_name,round(sum(a1)/sum(i),2) a1,round(sum(a2)/sum(i),2) a2,round(sum(a3)/sum(i),2) a3,round(sum(a4)/sum(i),2) a4,round(sum(a5)/sum(i),2) a5,round(sum(a6)/sum(i),2) a6,round(sum(a7)/sum(i),2) a7,sum(i) djl		" +
		 "from (select * from (with rules_type_view as (select * from rules_type where status = '1' and bus_types = 'BZ')		" +
		 "                select (select type_name from rules_type_view where type_id = (select parent_type_id from rules_type_view r where r.type_id = b.cos_id)) type_name, b.*		" +
		 "                  from BZ_JF_INDEX_TB_VIEW b where to_date(to_char(b.PAYMENT_TIME,'yyyymmdd'),'yyyymmdd') between to_date('"+startDate+"', 'yyyymm') and to_date('"+endDate+lastDay+"', 'yyyymmdd')		" +
		 "                ) bzdept where type_name is not null		" +
		 "       ) a		" +
		 "group by department, type_name order by department		"
		 ;
		
		List<PieVO> list = jdbcTemplate.query(sql, new QstRowMapper());
		//表
		List<PieVO> container_list = new ArrayList<PieVO>();
	    
	    Map<String, List<PieVO>> scMap = new LinkedHashMap<String, List<PieVO>>();
	   
	    String container_value = "",container_key = "";
	    
		for (int i = 0; i < list.size(); i++) {
			PieVO vo = list.get(i);
			
			PieVO vo1 = new PieVO();
			vo1.setHJ_PJSC(vo.getHJ_1_SC());
			PieVO vo2 = new PieVO();
			vo2.setHJ_PJSC(vo.getHJ_2_SC());
			PieVO vo3 = new PieVO();
			vo3.setHJ_PJSC(vo.getHJ_3_SC());
			PieVO vo4 = new PieVO();
			vo4.setHJ_PJSC(vo.getHJ_4_SC());
			PieVO vo5 = new PieVO();
			vo5.setHJ_PJSC(vo.getHJ_5_SC());
			PieVO vo6 = new PieVO();
			vo6.setHJ_PJSC(vo.getHJ_6_SC());
			PieVO vo7 = new PieVO();
			vo7.setHJ_PJSC(vo.getHJ_7_SC());
			
			List<PieVO> list1 = scMap.get("1") == null ? new ArrayList<PieVO>():scMap.get("1"); list1.add(vo1);
			List<PieVO> list2 = scMap.get("2") == null ? new ArrayList<PieVO>():scMap.get("2");list2.add(vo2);
			List<PieVO> list3 = scMap.get("3") == null ? new ArrayList<PieVO>():scMap.get("3");list3.add(vo3);
			List<PieVO> list4 = scMap.get("4") == null ? new ArrayList<PieVO>():scMap.get("4");list4.add(vo4);
			List<PieVO> list5 = scMap.get("5") == null ? new ArrayList<PieVO>():scMap.get("5");list5.add(vo5);
			List<PieVO> list6 = scMap.get("6") == null ? new ArrayList<PieVO>():scMap.get("6");list6.add(vo6);
			List<PieVO> list7 = scMap.get("7") == null ? new ArrayList<PieVO>():scMap.get("7");list7.add(vo7);
			
			scMap.put("1", list1);
			scMap.put("2", list2);
			scMap.put("3", list3);
			scMap.put("4", list4);
			scMap.put("5", list5);
			scMap.put("6", list6);
			scMap.put("7", list7);
		}
	    
	    Double a1tb = 0d,a2tb = 0d,a3tb = 0d,a4tb = 0d,a5tb = 0d,a6tb = 0d,a7tb = 0d;
	    
		//所有单据，各个环节同比
		for (int i = 0; i < list.size(); i++) {
			PieVO vo = list.get(i);
			a1tb = Double.valueOf(vo.getHJ_1_SC());
			a2tb = Double.valueOf(vo.getHJ_2_SC());
			a3tb = Double.valueOf(vo.getHJ_3_SC());
			a4tb = Double.valueOf(vo.getHJ_4_SC());
			a5tb = Double.valueOf(vo.getHJ_5_SC());
			a6tb = Double.valueOf(vo.getHJ_6_SC());
			a7tb = Double.valueOf(vo.getHJ_7_SC());
			
			container_key += ",'<b>部门："+vo.getDEPARTMENT() +"</b><br/><b>类型： "+ vo.getTYPE_NAME()+"</b><br/>'";
			
			//列表数据
			vo.setZQ((String)request.getAttribute("zq"));
			container_list.add(vo);
		}
		
		for (Map.Entry<String, String> entry : flowMap.entrySet()) {
			List<PieVO> list0 = scMap.get(entry.getKey());
			if (list0 != null && list0.size() > 0) {
				String d = "";
				for (int i = 0; i < list0.size(); i++) {
					Double v = Double.valueOf(list0.get(i).getHJ_PJSC());
					d += ","+v+"";
				}
				container_value += ",{name: '"+entry.getValue()+"',data: ["+d.substring(1)+"]}"; 
			}else {
				container_value += ",{name: '"+entry.getValue()+"',data: ['']}"; 
			}
		}
		
		request.setAttribute("container4_key", container_key.equals("") == true ? "":container_key.substring(1));
		request.setAttribute("container4_value", (container_value.equals("") == true ? "{name: '',data: ["+a1tb+", "+a2tb+", "+a3tb+", "+a4tb+", "+a5tb+", "+a6tb+", "+a7tb+"]}":container_value.substring(1)));
		request.setAttribute("container4_list", container_list);
	}
	
	class QstRowMapper implements RowMapper{
		
		public PieVO mapRow(ResultSet rs, int arg1) throws SQLException {
			PieVO vo = new PieVO();
			if(rs != null){
				vo.setDEPARTMENT(rs.getString("DEPARTMENT"));
				vo.setTYPE_NAME(rs.getString("TYPE_NAME"));
				vo.setHJ_1_SC(String.valueOf(rs.getDouble("A1")));	
				vo.setHJ_2_SC(String.valueOf(rs.getDouble("A2")));	
				vo.setHJ_3_SC(String.valueOf(rs.getDouble("A3")));	
				vo.setHJ_4_SC(String.valueOf(rs.getDouble("A4")));	
				vo.setHJ_5_SC(String.valueOf(rs.getDouble("A5")));	
				vo.setHJ_6_SC(String.valueOf(rs.getDouble("A6")));	
				vo.setHJ_7_SC(String.valueOf(rs.getDouble("A7")));	
				vo.setDJL(rs.getString("DJL"));
			}
			return vo;
		}		
	}
	

	
	/**
	 * 同比
	 */
	@SuppressWarnings("unchecked")
	private void tb(HttpServletRequest request,String startDate,String endDate) throws Exception{
		
		List<String>dateList = DateUtil.getTimePeriods(startDate, endDate, 2, 5);
		
		thbM(request, dateList, "TB");

	}
	
	/**
	 * 环比
	 */
	@SuppressWarnings("unchecked")
	private void hb(HttpServletRequest request,String startDate,String endDate) throws Exception{
		
		List<String>dateList = DateUtil.getTimePeriods(startDate, endDate, 1, 5);
		
		thbM(request, dateList, "HB");
	}
	
	/**
	 * 同比，环比
	 */
	private void thbM(HttpServletRequest request,List<String>dateList ,String type) throws Exception{
		
		String c_sql = "";
		
		for (int i = 0; i < dateList.size(); i++) {
			
			if (i > 0) {
				c_sql +=		"union all		";
			}
			
			String [] d = dateList.get(i).split("-");
			String s = d[0];
			String e = d[1];
			// e的最后一天 
			Date date = new SimpleDateFormat( "yyyyMM" ).parse(e);
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.set( Calendar.DATE,  1 );
			calendar.roll(Calendar.DATE,  - 1 );
			SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "dd" );
			String lastDay = simpleFormate.format(calendar.getTime());
			
			c_sql += 
					"select '"+dateList.get(i)+"' zq,sum(t1.a1)/sum(t1.i) a1,sum(t1.a2)/sum(t1.i)a2,sum(t1.a3)/sum(t1.i)a3,sum(t1.a4)/sum(t1.i)a4,sum(t1.a5)/sum(t1.i)a5,sum(t1.a6)/sum(t1.i)a6,sum(t1.a7)/sum(t1.i)a7,sum(t1.i) DJL from (		" +
					"select * from BZ_JF_INDEX_TB_VIEW t where to_date(to_char(t.PAYMENT_TIME,'yyyymmdd'),'yyyymmdd') between to_date('"+s+"','yyyymm') and to_date('"+e+lastDay+"','yyyymmdd')		" +
					") t1		" +
					"group by '"+dateList.get(i)+"'		"
					;
		}
		
		
		String sql =
		"select a.*,		" +
		"case when nvl(lag(a1) over(order by zq),'0') != 0 then round((a1-lag(a1) over(order by zq))/lag(a1) over(order by zq) *100,2) else 0 end a1tb,		" +
		"case when nvl(lag(a2) over(order by zq),'0') != 0 then round((a2-lag(a2) over(order by zq))/lag(a2) over(order by zq) *100,2) else 0 end a2tb,		" +
		"case when nvl(lag(a3) over(order by zq),'0') != 0 then round((a3-lag(a3) over(order by zq))/lag(a3) over(order by zq) *100,2) else 0 end a3tb,		" +
		"case when nvl(lag(a4) over(order by zq),'0') != 0 then round((a4-lag(a4) over(order by zq))/lag(a4) over(order by zq) *100,2) else 0 end a4tb,		" +
		"case when nvl(lag(a5) over(order by zq),'0') != 0 then round((a5-lag(a5) over(order by zq))/lag(a5) over(order by zq) *100,2) else 0 end a5tb,		" +
		"case when nvl(lag(a6) over(order by zq),'0') != 0 then round((a6-lag(a6) over(order by zq))/lag(a6) over(order by zq) *100,2) else 0 end a6tb,		" +
		"case when nvl(lag(a7) over(order by zq),'0') != 0 then round((a7-lag(a7) over(order by zq))/lag(a7) over(order by zq) *100,2) else 0 end a7tb,djl		" +
		"from (	" +c_sql+")a order by zq desc" ;
		
		
		List<PieVO> list = jdbcTemplate.query(sql, new TbRowMapper());
		//表
		List<PieVO> container_list = new ArrayList<PieVO>();
		
	    String container_value = "",zq = (String)request.getAttribute("zq");
	    
	    Double a1tb = 0d,a2tb = 0d,a3tb = 0d,a4tb = 0d,a5tb = 0d,a6tb = 0d,a7tb = 0d;
	    
		//所有单据，各个环节同比
		for (int i = 0; i < dateList.size(); i++) {
			String dateListZQ = dateList.get(i);
			PieVO vo;
			String size = "0";
			if (type.equals("TB"))  zq = dateListZQ.split("-")[0].substring(0, 4); else  zq = dateListZQ;
			
			try {
				vo = list.get(i);
				size = vo.getDJL()+"";
				a1tb = Double.valueOf(vo.getHJ_1_BFB());
				a2tb = Double.valueOf(vo.getHJ_2_BFB());
				a3tb = Double.valueOf(vo.getHJ_3_BFB());
				a4tb = Double.valueOf(vo.getHJ_4_BFB());
				a5tb = Double.valueOf(vo.getHJ_5_BFB());
				a6tb = Double.valueOf(vo.getHJ_6_BFB());
				a7tb = Double.valueOf(vo.getHJ_7_BFB());
			} catch (Exception e) {
				vo = new PieVO();
				vo.setHJ_1_BFB("0");
				vo.setHJ_2_BFB("0");
				vo.setHJ_3_BFB("0");
				vo.setHJ_4_BFB("0");
				vo.setHJ_5_BFB("0");
				vo.setHJ_6_BFB("0");
				vo.setHJ_7_BFB("0");
			}
			vo.setZQ(zq);
			
			container_value += ",{name: '"+zq+"',data: ["+a1tb+", "+a2tb+", "+a3tb+", "+a4tb+", "+a5tb+", "+a6tb+", "+a7tb+"]}"; 
			
			vo.setDJL(size);//单据量
			container_list.add(vo);
		}
		
		String tbid = type.equals("TB") == true ? "2":"3";
		
		request.setAttribute("container_key", getHJ_NameS());
		request.setAttribute("container"+tbid+"_value", (container_value.equals("") == true ? "{name: '"+zq+"',data: ["+a1tb+", "+a2tb+", "+a3tb+", "+a4tb+", "+a5tb+", "+a6tb+", "+a7tb+"]}":container_value.substring(1)));
		request.setAttribute("container"+tbid+"_list", container_list);
	}
	
	class TbRowMapper implements RowMapper{
		
		public PieVO mapRow(ResultSet rs, int arg1) throws SQLException {
			PieVO vo = new PieVO();
			if(rs != null){
				vo.setZQ(rs.getString("ZQ"));
				vo.setHJ_1_SC(rs.getString("A1"));	
				vo.setHJ_1_BFB(rs.getString("A1TB"));
				vo.setHJ_2_SC(rs.getString("A2"));	
				vo.setHJ_2_BFB(rs.getString("A2TB"));
				vo.setHJ_3_SC(rs.getString("A3"));	
				vo.setHJ_3_BFB(rs.getString("A3TB"));
				vo.setHJ_4_SC(rs.getString("A4"));	
				vo.setHJ_4_BFB(rs.getString("A4TB"));
				vo.setHJ_5_SC(rs.getString("A5"));	
				vo.setHJ_5_BFB(rs.getString("A5TB"));
				vo.setHJ_6_SC(rs.getString("A6"));	
				vo.setHJ_6_BFB(rs.getString("A6TB"));
				vo.setHJ_7_SC(rs.getString("A7"));	
				vo.setHJ_7_BFB(rs.getString("A7TB"));
				vo.setDJL(rs.getString("DJL"));
			}
			return vo;
		}		
	}
	
	
	public static String getHJ_NameS(){
		String container_key = "";
		for (Map.Entry<String, String> entry : flowMap.entrySet()) {
			container_key += ",'"+entry.getValue()+"'";
		}
		return container_key.substring(1);
	}
	
	private static Date getStrToDate(String date){
		try{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(date != null){
				return df.parse(date);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	private Date max(Date date1,Date date2){
		if(date1 != null && date2 != null && date1.after(date2)){
			return date1;
		}
		return date2;
	}
	
	
	/**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");
		HSSFWorkbook book = new HSSFWorkbook();
		//样式设置
		HSSFCellStyle style = book.createCellStyle();
		HSSFFont font = book.createFont();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中对齐
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);

		// 表头状态
		String[] header = {"","单据编号","报账人","报账部门","单据类型","业务类型","单据状态","提单日期","申请金额","付款金额","核定金额","不含税金额","税额","含税金额","紧急支付","支付类型","供应商名称","合同编号","合同名称","投资项目编号","投资项目名称","预算项目编号","预算项目名称","是否包含增值税扣税凭证","发票编号","发票代码","开票日期","是否认证通过","价款","税额","税率","增值税抵扣凭证类型","摘要","室经理/室副经理时间","室经理/室副经理审批人","部门副总经理时间","部门副总经理审批人","部门总经理时间","部门总经理审批人","部门总经理.时间","部门总经理.审批人","部门总经理..时间","部门总经理..审批人","核算中心副主任时间","核算中心副主任审批人","财务部副总经理时间","财务部副总经理审批人","财务部总经理时间","财务部总经理审批人","公司副总时间","公司副总审批人","公司副总经理时间","公司副总经理审批人","公司副总经理.时间","公司副总经理审批人","公司总经理时间","公司总经理审批人","分管领导时间","分管领导审批人","初审会计时间","初审会计审批人","客户服务部复审会计时间","客户服务部复审会计审批人","客户服务部财务经理时间","客户服务部财务经理审批人","计划财务室主办会计","计划财务室主办会计","财务部室经理时间","财务部室经理审批人","筹建办公室财务部副总经理","筹建办公室财务部副总经理审批人","财务部副总经理.时间","财务部副总经理审批人","财务核算中心副主任时间","财务核算中心副主任审批人","财务部总经理.时间","财务部总经理审批人","公司副总.时间","公司副总.审批人","公司副总经理..时间","公司副总经理..审批人","公司副总经理...时间","公司副总经理...审批人","公司总经理.时间","公司总经理.审批人","复审会计时间","复审会计审批人","创建会计科目","创建会计科目.审批人","付款凭证制单","付款凭证制单.审批人","提交银企单据","提交银企单据.审批人","资金复核","资金复核.审批人","付款完成","付款完成.审批人","部门传递","财务初审处接收","部门接收","财务会计接收","财务出纳接收","财务初审处退单","当前审批人","问题类型"};
		
		

		List<Object[]> listO =  new ArrayList<Object[]>();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
		
		if (startDate == null || startDate.equals("")) {
			startDate = sdf1.format(DateUtils.add(new Date(), DateUtils.TIME_MONTH, -1));
			endDate = sdf1.format(new Date());
		}

		startDate = sdf2.format(sdf1.parse(startDate));
		endDate = sdf2.format(sdf1.parse(endDate));
		
		 // endDate本月的最后一天 
		Date date = sdf2.parse(endDate);
		Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.set( Calendar.DATE,  1 );
		 calendar.roll(Calendar.DATE,  - 1 );
		 SimpleDateFormat simpleFormate  =   new  SimpleDateFormat( "dd" );
		 String lastDay = simpleFormate.format(calendar.getTime());
		 
		List<AccountImportInfo> list = laungchAgingService.queryFlowInfoByTime(startDate,endDate+lastDay);
		if(list != null && list.size() > 0){
			int i = 1;
			for (AccountImportInfo vo : list) {
				Object [] obj =  new Object[105];
				obj[0] = i;//编号
				obj[1] = vo.getColumn01();
				obj[2] =vo.getColumn02();
				obj[3] =vo.getColumn03();
				obj[4] =vo.getColumn04();
				obj[5] =vo.getColumn05();
				obj[6] =vo.getColumn06();
				obj[7] =vo.getColumn07();
				obj[8] =vo.getColumn08();
				obj[9] =vo.getColumn09();
				obj[10] =vo.getColumn10();
				obj[11] =vo.getColumn11();
				obj[12] =vo.getColumn12();
				obj[13] =vo.getColumn13();
				obj[14] =vo.getColumn14();
				obj[15] =vo.getColumn15();
				obj[16] =vo.getColumn16();
				obj[17] =vo.getColumn17();
				obj[18] =vo.getColumn18();
				obj[19] =vo.getColumn19();
				obj[20] =vo.getColumn20();
				obj[21] =vo.getColumn21();
				obj[22] =vo.getColumn22();
				obj[23] =vo.getColumn23();
				obj[24] =vo.getColumn24();
				obj[25] =vo.getColumn25();
				obj[26] =vo.getColumn26();
				obj[27] =vo.getColumn27();
				obj[28] =vo.getColumn28();
				obj[29] =vo.getColumn29();
				obj[30] =vo.getColumn30();
				obj[31] =vo.getColumn31();
				obj[32] =vo.getColumn32();
				obj[33] =vo.getColumn33();
				obj[34] =vo.getColumn34();
				obj[35] =vo.getColumn35();
				obj[36] =vo.getColumn36();
				obj[37] =vo.getColumn37();
				obj[38] =vo.getColumn38();
				obj[39] =vo.getColumn39();
				obj[40] =vo.getColumn40();
				obj[41] =vo.getColumn41();
				obj[42] =vo.getColumn42();
				obj[43] =vo.getColumn43();
				obj[44] =vo.getColumn44();
				obj[45] =vo.getColumn45();
				obj[46] =vo.getColumn46();
				obj[47] =vo.getColumn47();
				obj[48] =vo.getColumn48();
				obj[49] =vo.getColumn49();
				obj[50] =vo.getColumn50();
				obj[51] =vo.getColumn51();
				obj[52] =vo.getColumn52();
				obj[53] =vo.getColumn53();
				obj[54] =vo.getColumn54();
				obj[55] =vo.getColumn55();
				obj[56] =vo.getColumn56();
				obj[57] =vo.getColumn57();
				obj[58] =vo.getColumn58();
				obj[59] =vo.getColumn59();
				obj[60] =vo.getColumn60();
				obj[61] =vo.getColumn61();
				obj[62] =vo.getColumn62();
				obj[63] =vo.getColumn63();
				obj[64] =vo.getColumn64();
				obj[65] =vo.getColumn65();
				obj[66] =vo.getColumn66();
				obj[67] =vo.getColumn67();
				obj[68] =vo.getColumn68();
				obj[69] =vo.getColumn69();
				obj[70] =vo.getColumn70();
				obj[71] =vo.getColumn71();
				obj[72] =vo.getColumn72();
				obj[73] =vo.getColumn73();
				obj[74] =vo.getColumn74();
				obj[75] =vo.getColumn75();
				obj[76] =vo.getColumn76();
				obj[77] =vo.getColumn77();
				obj[78] =vo.getColumn78();
				obj[79] =vo.getColumn79();
				obj[80] =vo.getColumn80();
				obj[81] =vo.getColumn81();
				obj[82] =vo.getColumn82();
				obj[83] =vo.getColumn83();
				obj[84] =vo.getColumn84();
				obj[85] =vo.getColumn85();
				obj[86] =vo.getColumn86();
				obj[87] =vo.getColumn87();
				obj[88] =vo.getColumn88();
				obj[89] =vo.getColumn89();
				obj[90] =vo.getColumn90();
				obj[91] =vo.getColumn91();
				obj[92] =vo.getColumn92();
				obj[93] =vo.getColumn93();
				obj[94] =vo.getColumn94();
				obj[95] =vo.getColumn95();
				obj[96] =vo.getColumn96();
				obj[97] =vo.getColumn97();
				obj[98] =vo.getColumn98();
				obj[99] =vo.getColumn99();
				obj[100] =vo.getColumn100();
				obj[101] =vo.getColumn101();
				obj[102] =vo.getColumn102();
				obj[103] =vo.getColumn103();
				
				List<ProblemList> problemList =  accService.queryProblemList(vo.getColumn01());
				if( problemList==null ||  problemList.size() == 0 ){
					obj[104] = "—";
				}else{
					String dateStr = "";
					for (int k = 0; k < problemList.size(); k++) {
						ProblemList problem = problemList.get(k);
						if(k == 0){
							dateStr += SysParamHelper.getSysParamByCode("BZ_TCTYPE", problem.getType()).getParameterValue();
						}else{
							dateStr += "；"  + SysParamHelper.getSysParamByCode("BZ_TCTYPE", problem.getType()).getParameterValue();
						}
					}
					
					obj[104] = dateStr;
				}
				
				
				listO.add(obj);
				i++;
			}
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			//文件名
			String fileName = "报账经分" + sdf.format(new Date());
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
