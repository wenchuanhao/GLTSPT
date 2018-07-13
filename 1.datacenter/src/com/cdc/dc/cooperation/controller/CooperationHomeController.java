package com.cdc.dc.cooperation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;

import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.dc.metadata.manyd.ManYD;
import com.cdc.system.core.authentication.controller.CommonController;
/**
 * 合交经分首页图表页
 * @author ZENGKAI
 * @date 2016-06-20 15:45:29
 */
@Controller
@RequestMapping(value = "/copperationController/")
public class CooperationHomeController extends CommonController{
	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IEnterpriseService enterpriseService;
	
	/**
	 * 合交经分首页
	 */
	@RequestMapping(value="home", method = {RequestMethod.POST,RequestMethod.GET})
	public String home(HttpServletRequest request) {
		
		Calendar c = Calendar.getInstance();
		int monthstr = c.get(Calendar.YEAR);//年份
		request.setAttribute("year", monthstr);
		//月份
		String month = String.valueOf(new Date().getMonth() + 1);
		//加0
		if(new Date().getMonth() < 9){
			month += "0";
		}
		
		//收入指标完成情况数据
		String sql01 = "select month_str||'-'||to_char(to_date(月份,'mm'),'mm') as 月份,当月完成,累计完成,进度差,round(累计完成/累计目标*100,2)||'%' as 完成比例 from report_query_30 where month_str='"+monthstr+"' order by 月份";
		List<Object[]> dataContainer01 = cooperationService.getSeriesDataByType(sql01);
		request.setAttribute("dataContainer01", dataContainer01);
		//服务指标完成情况数据
		String sql02 = "select month_str||'-'||to_char(to_date(yue,'mm'),'mm') as monthstr,dangyuewanc,lejwc from report_query_28 where month_str='"+monthstr+"' order by monthstr";
		List<Object[]> dataContainer02 = cooperationService.getSeriesDataByType(sql02);
		request.setAttribute("dataContainer02", dataContainer02);
		//成本使用情况数据
		String sql03 = "select month_str||'-'||to_char(to_date(monthstr,'mm'),'mm') as 月份,round(本月成本/10000,2),round(累计成本 /10000,2)from report_query_cbsyqk_01 where month_str='"+monthstr+"' order by 月份";
		List<Object[]> dataContainer03 = cooperationService.getSeriesDataByType(sql03);
		request.setAttribute("dataContainer03", dataContainer03);
		
		//人均成本使用情况数据
		String sql04 = "select t.month_str,t.monthstr,C,E,G,I,K,M,tt.SC,tt.SE,tt.SG,tt.SI,tt.SK,tt.SM from " +
				" REPORT_QUERY_RJCB_08 t " +
				" left join (" +
				"  select month_str,round(sum(C)/count(1),2) SC,round(sum(E)/count(1),2) SE,round(sum(G)/count(1),2) SG,round(sum(I)/count(1),2) SI," +
				"round(sum(K)/count(1),2) SK,round(sum(M)/count(1),2) SM " +
				"from REPORT_QUERY_RJCB_08 group by month_str ) tt on t.month_str=tt.month_str " +
				" where t.month_str='"+monthstr+"' order by t.monthstr ";
		List<Object[]> temp = cooperationService.getSeriesDataByType(sql04);
		List<Object[]> dataContainer04 = new ArrayList<Object[]>();
		String[] str = {"人餐宴会","人餐食材","房晚服务","房晚成本","人均会场服务","人均会场"};
		for (Object[] objects : temp) {
			for (int i = 0; i < 6; i++) {
				Object[] newObj = new Object[4];
				newObj[0] = objects[1];
				newObj[1] = str[i];
				newObj[2] = objects[i+2];
				newObj[3] = objects[i+8];
				dataContainer04.add(newObj);
			}
		}
		request.setAttribute("dataContainer04", dataContainer04);
		
		//预算完成情况数据
		String sql05 = "select month_str||'-'||to_char(to_date(substr(D1,1,LENGTH(D1) - 1),'mm'),'mm') as monthstr,D8,D9,D17,D15 from view_yswcfx_029_2 where month_str='"+monthstr+"' order by monthstr";
		List<Object[]> dataContainer05 = cooperationService.getSeriesDataByType(sql05);
		request.setAttribute("dataContainer05", dataContainer05);
		
		//满意度部门维度数据
		String sql06 = "select month_str||'-'||to_char(to_date(substr(月份,1,LENGTH(月份) - 1),'mm'),'mm') as monthstr, " +
				" sum(前台礼宾),sum(客房),sum(餐饮),sum(会议),sum(康乐),sum(安保),max(总计),max(总计),max(总计),max(总计),max(总计),max(总计) " +
				" from VIEW_MANYD_DEPT where month_str='"+monthstr+"' group by month_str||'-'||to_char(to_date(substr(月份,1,LENGTH(月份) - 1),'mm'),'mm') " +
				" order by month_str||'-'||to_char(to_date(substr(月份,1,LENGTH(月份) - 1),'mm'),'mm')";
		List<Object[]> temp06 = cooperationService.getSeriesDataByType(sql06);
		
		List<Object[]> dataContainer06 = new ArrayList<Object[]>();
		String[] str06 = {"前台礼宾","客房","餐饮","会议","康乐","安保"};
		for (int j = 0; j < temp06.size();j++) {
			Object[] objects = temp06.get(j);
			for (int i = 0; i < 6; i++) {
//				月份|部门|满意度|累计|月平均|全年平均
				Double leiji = 0d;
				for (int k = 0; k <= j; k++) {
					leiji += Double.valueOf(((BigDecimal) temp06.get(k)[i+1]).toString());//遍历上个月相加得出累计
				}
				Double zongji = 0d;
				for (int k = 0; k < temp06.size();k++) {
					zongji += Double.valueOf(((BigDecimal) temp06.get(k)[i+7]).toString());//所有总计相加得出全年
				}
				Object[] newObj = new Object[6];
				newObj[0] = objects[0];//月份
				newObj[1] = str06[i];//部门
				newObj[2] = objects[i+1];//满意度
				newObj[3] = leiji;//累计
				newObj[4] = objects[i+7];//月平均
				newObj[5] = new DecimalFormat("#.00").format(zongji / temp06.size());//全年平均
				dataContainer06.add(newObj);
			}
		}
		request.setAttribute("dataContainer06", dataContainer06);

		return "/dc/cooperation/datasourceHome";
	}
	/**
	 * 获得本年本月度经营数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getTopData", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void getTopData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Calendar c = Calendar.getInstance();
		int monthstr = c.get(Calendar.YEAR);//年份
		request.setAttribute("year", monthstr);
		//月份
		int mon = new Date().getMonth() + 1;
		String month = String.valueOf(mon);
		//加0
		if(mon < 10){
			month = "0" + month;
		}
		String target = request.getParameter("target");
		JSONObject result = new JSONObject();
		String sql = "";
		List<Object[]> list = null;
		//经营目标01
		if( target.indexOf("01_") == 0 ){
			if("01_fuwurentian".equals(target)){
				//服务人天：> 5000  -- 《x.21服务量指标》的服务数量指标
				String fwrt = cooperationService.queryResultBySql("column_02","YUAN_TABLE_026",monthstr);
				result.put("value", fwrt);
			}else if("01_yingyeshouru".equals(target)){
				//营业收入：> 5000元  --《x.23收入指标录入》的收入指标
				String yysr = cooperationService.queryResultBySql("column_02","YUAN_TABLE_028",monthstr);
				result.put("value", yysr);
			}else if("01_zonghemanyidu".equals(target)){
				//综合满意度：>85%  --《x.22满意度指标》的分值。
				String manyidu = cooperationService.queryResultBySql("column_02","YUAN_TABLE_022",monthstr);
				result.put("value", (Double.valueOf(manyidu) * 10) + "%");
			}
		}else
		//经营目标02
		if( target.indexOf("02_") == 0 ){
		 	if("02_yusuanjiesuanqian".equals(target)){
				//预算结算前：1.5千万-- 《x.34 年度预算目标》的当年预算目标（结算前）（增加）
				String yusuanqian = cooperationService.queryResultBySql("column_02","YUAN_TABLE_027",monthstr);
				result.put("value", yusuanqian);
			}else if("02_yusuanjiesuanhou".equals(target)){
				//预算目标：-15万--《x.34 年度预算目标》的当年预算目标（增加）"
				String yusuanmubiao = cooperationService.queryResultBySql("column_03","YUAN_TABLE_027",monthstr);
				result.put("value", yusuanmubiao);
			}
		}else
		//经营收入
		if( target.indexOf("03_") == 0 ){
			if("03_benyueshouru".equals(target)){
				//		本月营收：X万--《11.收入》的当月合计
				sql = "select month_str,sum(t.an) count from REPORT_QUERY_010 t where t.F='收入' and month_str='"+monthstr+"-"+month+"' group by month_str";
				list = cooperationService.getSeriesDataByType(sql);
				Double benyueshouru = 0d;
				if(list != null && list.size() > 0){
					benyueshouru = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
					result.put("value", benyueshouru);
				}
			}else if("03_benyuemaoli".equals(target)){
				sql = "select month_str,sum(t.an) count from REPORT_QUERY_010 t where t.F='收入' and month_str='"+monthstr+"-"+month+"' group by month_str";
				list = cooperationService.getSeriesDataByType(sql);
				Double benyueshouru = 0d;
				if(list != null && list.size() > 0){
					benyueshouru = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
					result.put("value", benyueshouru);
				}
				//《11.收入》的当月合计 - 《1.成本汇总表》的当月合计。
				sql = "select month_str,sum(ttp) count from REPORT_QUERY_01_T where month_str='"+monthstr+"-"+month+"' group by month_str";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					Double benyuechengben = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
					//本月毛利
					result.put("value", benyueshouru - benyuechengben);
				}
			}else if("03_nianduleijiyingshou".equals(target)){
//				${dangnianheji }万（90%）
				sql = "select ltrim(substr(month_str, 0, 4), '0') as month_str,round(sum(t.an)/10000,2) count from REPORT_QUERY_010 t " +
						"where t.F='收入' and month_str like '"+monthstr+"%' group by " +
						"ltrim(substr(month_str, 0, 4), '0')";
				list = cooperationService.getSeriesDataByType(sql);
				
				Double d = 0d;
				if(list != null && list.size() > 0){
					//当年合计
					d = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
				}
				
				sql = "select month_str,round(累计完成/累计目标*100,2) from report_query_30 where month_str='"+monthstr+"' and 月份='"+mon+"' ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					double percent = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
					if(percent < 100){
						result.put("color", "red");
					}
					result.put("value", d + "万（"+list.get(0)[1]+"%）");
				}
			}
		}else if( target.indexOf("04_") == 0 ){
            //本月团队数
            if("04_benyuetuandui".equals(target)){
            	sql = "select month_str,合计 from REPORT_QUERY_20_1 where month_str='"+monthstr+"' and 月份='"+mon+"月'";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
				}
            	//本月人天
            }else if("04_benyuerenti".equals(target)){
            	sql = "select month_str,合计 from REPORT_QUERY_20_2 where month_str='"+monthstr+"' and 月份='"+mon+"月'";
            	list = cooperationService.getSeriesDataByType(sql);
            	if(list != null && list.size() > 0){
            		result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
            	}
            	//累计团队
            }else if("04_leijituandui".equals(target)){
            	sql = "select month_str,sum(合计) from REPORT_QUERY_20_1  where month_str='"+monthstr+"'  group by month_str ";
            	list = cooperationService.getSeriesDataByType(sql);
            	if(list != null && list.size() > 0){
            		result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
            	}
            	//累计人天
            }else if("04_leijirenti".equals(target)){
            	//《28.服务量完成》的 累计完成（《28.服务量完成》的 累计完成/累计目标。）
            	sql = "select month_str,yue,lejwc from report_query_28 where month_str='"+monthstr+"' and yue like '%"+mon+"' ";
				list = cooperationService.getSeriesDataByType(sql);
				
				Double d = 0d;
				if(list != null && list.size() > 0){
					//当年合计
					d = Double.valueOf(((BigDecimal) list.get(0)[2]==null?0:list.get(0)[2]).toString());
				}
				
				sql = "select month_str,round(lejwc/ljmb*100,2) from report_query_28 where month_str='"+monthstr+"' and yue like '%"+mon+"' ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					double percent = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
					if(percent < 100){
						result.put("color", "red");
					}
					result.put("value", d + "万（"+list.get(0)[1]+"%）");
				}else{
					result.put("color", "red");
					result.put("value", d + "万（0%）");
				}
            }
            //人均成本
		}else if( target.indexOf("05_") == 0 ){
            //本月总成本
            if("05_benyuezongchengben".equals(target)){
            	sql = "select month_str,sum(ttp)/10000 from REPORT_QUERY_01_T t where t.MONTH_STR = '"+monthstr+"-"+month+"' group by month_str";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
				}
            	//年度累计成本
            }else if("05_nianduleijichengben".equals(target)){
            	sql = "select ltrim(substr(month_str, 0, 4), '0') as month_str,round(sum(ttp)/10000,2) count  from REPORT_QUERY_01_T t where t.MONTH_STR like '"+monthstr+"%' group by ltrim(substr(month_str, 0, 4), '0') ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
				}
            	//人餐宴会成本
            }else if("05_rencanyanhuichengben".equals(target)){
            	sql = "select monthstr,c from report_query_rjcb_08 where monthstr='"+monthstr+"-"+month+"' ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
				}
            	//人餐食材成本
            }else if("05_rencanshicaichengben".equals(target)){
            	sql = "select monthstr,e from report_query_rjcb_08 where monthstr='"+monthstr+"-"+month+"' ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
				}
            }
		}else if( target.indexOf("06_") == 0 ){
            //房晚成本
            if("06_fanwanchengben".equals(target)){
				
				sql = "select monthstr,i from report_query_rjcb_08 where monthstr='"+monthstr+"-"+month+"' ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
				}
            	//房晚服务成本
            }else if("06_fanwanfuwuchengben".equals(target)){
            	
            	sql = "select monthstr,g from report_query_rjcb_08 where monthstr='"+monthstr+"-"+month+"' ";
            	list = cooperationService.getSeriesDataByType(sql);
            	if(list != null && list.size() > 0){
            		result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
            	}
            	
            
            	//人均会场服务成本
            }else if("06_renjunhuichangfuwuchengben".equals(target)){
            	sql = "select monthstr,k from report_query_rjcb_08 where monthstr='"+monthstr+"-"+month+"' ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
				}
            	//人均会场成本
            }else if("06_renjunhuichangchengben".equals(target)){
            	sql = "select monthstr,m from report_query_rjcb_08 where monthstr='"+monthstr+"-"+month+"' ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
				}
            }
		}else if( target.indexOf("07_") == 0 ){

			//--累计预算目标（结算前）,累计预算目标（结算后）,累计预算完成（结算前）,累计预算完成（结算后）
			//select month_str,D1,D4,D7,D9,D14 from view_yswcfx_029_2;
            //本月预算结算前
            if("07_benyueyusuanjiesuanqian".equals(target)){
            	sql = "select month_str,round(D9/10000,2) from view_yswcfx_029_2 where month_str='"+monthstr+"' and D1='"+mon+"月'  ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
				}
            	//年度累计完成结算前
            }else if("07_nianduleijiwanchengqian".equals(target)){

            	//《29.预算完成分析》的 累计预算完成（结算前）/ 累计预算目标（结算前）
            	sql = "select month_str,round(D9/10000,2) from view_yswcfx_029_2 where month_str='"+monthstr+"' and D1='"+mon+"月'  ";
				list = cooperationService.getSeriesDataByType(sql);
				
				Double d = 0d;
				if(list != null && list.size() > 0){
					//累计预算完成
					d = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
				}
				sql = "select month_str,round(D9/D4*100,2) from view_yswcfx_029_2 where month_str='"+monthstr+"' and D1='"+mon+"月'  ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					double percent = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
					if(percent < 100){
						result.put("color", "red");
					}
					result.put("value", d + "万（"+list.get(0)[1]+"%）");
				}else{
					result.put("color", "red");
					result.put("value", d + "万（0%）");
				}
            
            	//本月预算结算后
            }else if("07_benyueyusuanjiesuanhou".equals(target)){
            	sql = "select month_str,round(D14/10000,2) from view_yswcfx_029_2 where month_str='"+monthstr+"' and D1='"+mon+"月'  ";
            	list = cooperationService.getSeriesDataByType(sql);
            	if(list != null && list.size() > 0){
            		result.put("value", Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString()));
            	}
            	//年度累计完成结算后
            }else if("07_nianduleijiwanchenghou".equals(target)){
            	//《29.预算完成分析》的 累计预算完成（结算前）/ 累计预算目标（结算前）
            	sql = "select month_str,round(D14/10000,2) from view_yswcfx_029_2 where month_str='"+monthstr+"' and D1='"+mon+"月'  ";
				list = cooperationService.getSeriesDataByType(sql);
				
				Double d = 0d;
				if(list != null && list.size() > 0){
					//累计预算完成
					d = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
				}
				sql = "select month_str,round(D14/D7*100,2) from view_yswcfx_029_2 where month_str='"+monthstr+"' and D1='"+mon+"月'  ";
				list = cooperationService.getSeriesDataByType(sql);
				if(list != null && list.size() > 0){
					double percent = Double.valueOf(((BigDecimal) list.get(0)[1]==null?0:list.get(0)[1]).toString());
					if(percent < 100){
						result.put("color", "red");
					}
					result.put("value", d + "万（"+list.get(0)[1]+"%）");
				}else{
					result.put("color", "red");
					result.put("value", d + "万（0%）");
				}
            }
		}
		
		try {
			PrintWriter out = response.getWriter();
			out.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得图表图形数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getSeriesData", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void getSeriesData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String type = request.getParameter("type");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		if(!StringUtils.isNotEmpty(year)){
			Calendar c = Calendar.getInstance();
			year = String.valueOf(c.get(Calendar.YEAR));
		}
		if(!StringUtils.isNotEmpty(month)){
			int i = new Date().getMonth();
			if(i == 0){
				month = "12";
			}else{
				month = String.valueOf(i);
			}
		}
		//同比
		String yearCycle = getCycle(Integer.valueOf(year),Integer.valueOf(month),1,"yyyy-MM","YEAR");
		//环比
		String monCycle = getCycle(Integer.valueOf(year),Integer.valueOf(month),1,"yyyy-MM","MON");
		
		JSONArray result = null;
		List<Object[]> list = null;
		try {
			//判空
			if(type == null){
				type = "";
			}
			//一级页面查询
			if(type.indexOf("container0") == 0){
//			收入指标完成情况
				if("container01".equals(type)){
					String sql = "select 累计完成,累计目标  from report_query_30  where month_str='"+year+"' order by to_number(月份) ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
//			服务指标完成情况
				if("container02".equals(type)){
					String sql = "select lejwc,ljmb from report_query_28 where month_str='"+year+"' order by to_number(yue)";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
//			成本使用情况
				if("container03".equals(type)){
					String sql = "select round(本月成本/10000,2), round(上月累计/10000,2), round(累计成本/10000,2) from report_query_cbsyqk_01 where month_str='"+year+"' order by to_number(monthstr) ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
//			人均成本使用情况
				if("container04".equals(type)){
					//初始化六种类型月份数据
					list = new ArrayList<Object[]>();
					for (int i = 0; i < 6; i++) {
						Object[] newObj = {0,0,0,0,0,0,0,0,0,0,0,0};
						list.add(newObj);
					}
					//按年查询人均成本
					String sql = "select monthstr,c,e,g,i,k,m from REPORT_QUERY_RJCB_08 t where month_str='"+year+"'";
					List<Object[]> temp = cooperationService.getSeriesDataByType(sql);
					for (Object[] objects : temp) {
						String monthstr = (String) objects[0];//月份
						int index = Integer.valueOf(monthstr.substring(5)) - 1;
						list.get(0)[index] = objects[1] != null ? objects[1] : 0;
						list.get(1)[index] = objects[2] != null ? objects[2] : 0;
						list.get(2)[index] = objects[3] != null ? objects[3] : 0;
						list.get(3)[index] = objects[4] != null ? objects[4] : 0;
						list.get(4)[index] = objects[5] != null ? objects[5] : 0;
						list.get(5)[index] = objects[5] != null ? objects[6] : 0;
					}
					
					result = JSONArray.fromObject(list);
				}else
//			预算完成情况--结算前
				if("container0501".equals(type)){
					String sql = "select D9,D4 from view_yswcfx_029_2 where month_str='"+year+"' order by to_number(D2)";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//结算后
				if("container0502".equals(type)){
					String sql = "select D14,D7 from view_yswcfx_029_2 where month_str='"+year+"' order by to_number(D2)";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//满意度-部门维度
				if("container0601".equals(type)){
					String sql = "select yiyue*10 yiyue,eryue*10 eryue,sanyue*10 sanyue,siyue*10 siyue,wuyue*10 wuyue,liuyue*10 liuyue," +
							"qiyue*10 qiyue,bayue*10 bayue,jiuyue*10 jiuyue,shiyue*10 shiyue,yiyiyue*10 yiyiyue,yieryue*10 yieryue from report_query_myddbqk_02 where month_str='"+year+"' order by sortno";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//满意度-项目(主题)维度
				if("container0602".equals(type)){
					//项目维度满意度
					String sql = "select * from VIEW_MANYD_PROJECT_RESULT where month_str='"+year+"' order by MM";
					list = cooperationService.getSeriesDataByType(sql);
					//满意度标题
					String[] title = ManYD.buildView().split(",");
					
					String targetSql = "select column_01,column_02 from yuan_table_022 t left join cooperation_datasource_records tt on t.datasource_records_id=tt.record_id where t.column_01='"+year+"' and tt.status='3'";
					List<Object[]> target = cooperationService.getSeriesDataByType(targetSql);
					
					JSONObject obj = new JSONObject();
					obj.put("title", title);
					obj.put("list", list);
					obj.put("target", target);
					
					PrintWriter out = response.getWriter();
					out.write(obj.toString());
					return;
				}
				//二级页面查询
			} else if(type.indexOf("sndlevel01") == 0){
				
				/****************************总营业收入二级 begin******************************/
				//总营业收入构成-主题-主题维度
				if("sndlevel0101_zhuti".equals(type)){
					String sql = "select t.d topic,t.B,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c='"+year+"-"+month+"' group by t.c,t.d,t.B ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//总营业收入构成-部门维度
				if("sndlevel0101_bumen".equals(type)){
					String sql = "select t.DEP topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c='"+year+"-"+month+"' group by t.c,t.DEP ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//总营业收入构成-结算类型维度
				if("sndlevel0101_jiesuan".equals(type)){
					String sql = "select t.F topic,sum(t.an) total from REPORT_QUERY_010 t where t.F != '收入' and t.c='"+year+"-"+month+"' group by t.c,t.F ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
					//项目维度,二级饼图
				}else 
					if("sndlevel0101_xiangmu".equals(type)){
						String sql = "select pro,product,total from report_query_shouruxm where month_str='"+year+"-"+month+"'";
						list = cooperationService.getSeriesDataByType(sql);
						result = JSONArray.fromObject(list);
					}else
						//同比
						if("sndlevel0102_xiangmu".equals(type)){
							String sql = "select month_str,pro,total from report_query_shouruxm where month_str in " +yearCycle;
							list = cooperationService.getSeriesDataByType(sql);
							result = JSONArray.fromObject(list);
							//反比
						}else
							if("sndlevel0103_xiangmu".equals(type)){
								String sql = "select month_str,pro,total from report_query_shouruxm where month_str in " +monCycle;
								list = cooperationService.getSeriesDataByType(sql);
								result = JSONArray.fromObject(list);
								//趋势
							}else
								if("sndlevel0104_xiangmu".equals(type)){
									String sql = "select month_str,pro,total from report_query_shouruxm where month_str like '"+year+"-%'";
									list = cooperationService.getSeriesDataByType(sql);
									result = JSONArray.fromObject(list);
								}else
				/****************************总营业收入同比同比******************************/
				//总营业收入同比-主题维度
				if("sndlevel0102_zhuti".equals(type)){
					String sql = "select t.c,t.d topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c in "+yearCycle+" group by t.c,t.d ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//总营业收入同比-部门维度
				if("sndlevel0102_bumen".equals(type)){
					String sql = "select t.c,t.DEP topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c  in "+yearCycle+" group by t.c,t.DEP ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//总营业收入同比-结算类型维度
				if("sndlevel0102_jiesuan".equals(type)){
					String sql = "select t.c,t.F topic,sum(t.an) total from REPORT_QUERY_010 t where t.F != '收入' and t.c  in "+yearCycle+" group by t.c,t.F ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				/****************************总营业收入环比******************************/
				//总营业收入环比-主题维度
				if("sndlevel0103_zhuti".equals(type)){
					String sql = "select t.c,t.d topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c in "+monCycle+" group by t.c,t.d ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//总营业收入环比-部门维度
				if("sndlevel0103_bumen".equals(type)){
					String sql = "select t.c,t.DEP topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c  in "+monCycle+" group by t.c,t.DEP ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//总营业收入环比-结算类型维度
				if("sndlevel0103_jiesuan".equals(type)){
					String sql = "select t.c,t.F topic,sum(t.an) total from REPORT_QUERY_010 t where t.F != '收入' and t.c  in "+monCycle+" group by t.c,t.F ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//总营业收入趋势-主题维度
				if("sndlevel0104_zhuti".equals(type)){
					String sql = "select t.c,t.d topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c like '"+year+"-%' group by t.c,t.d ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//总营业收入趋势-部门维度
				if("sndlevel0104_bumen".equals(type)){
					String sql = "select t.c,t.dep topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c like '"+year+"-%' group by t.c,t.dep ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}
				/****************************总营业收入二级 end******************************/
				
			}else if(type.indexOf("sndlevel02") == 0){
				
				/****************************服务指标完成情况二级  begin******************************/
				//服务指标完成情况二级-主题维度
				if("sndlevel0201".equals(type)){
					String sql = "select column_04,total from report_query_20_2_T where month_str='"+year+"-"+month+"' ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else if("sndlevel0201_bumen".equals(type)){
					String sql = "select column_06,total from report_query_20_2_BUWEIDU where month_str='"+year+"-"+month+"' ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//服务指标完成情况二级-部门维度同比
				if("sndlevel0202".equals(type)){
					String sql = "select month_str,column_06,total from report_query_20_2_BUWEIDU where month_str in "+yearCycle;
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//服务指标完成情况二级-部门维度环比monCycle
				if("sndlevel020201".equals(type)){
					String sql = "select month_str,column_06,total from report_query_20_2_BUWEIDU where month_str in "+monCycle;
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//服务指标全年趋势
				if("sndlevel0203_bumen".equals(type)){
					String sql = "select * from report_query_20_2_BUWEIDU where month_str like '"+year+"-%' ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else if("sndlevel0203_zhuti".equals(type)){
					String sql = "select * from report_query_20_2_T where month_str like '"+year+"-%' ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}
				/****************************服务指标完成情况二级 end******************************/
				
			}else if(type.indexOf("sndlevel03") == 0){
				
				/****************************总成本二级 begin******************************/
				//部门维度
				if("sndlevel0301_bumen".equals(type)){
					String sql = "select DEP,TOTAL from report_query_cbsyqk_bumen01 t where t.month_str='"+year+"-"+month+"' ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//同比
				if("sndlevel0302_bumen".equals(type)){
					String sql = "select MONTH_STR,DEP,TOTAL from report_query_cbsyqk_bumen01 t where t.month_str in "+yearCycle;
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//环比
				if("sndlevel0303_bumen".equals(type)){
					String sql = "select MONTH_STR,DEP,TOTAL from report_query_cbsyqk_bumen01 t where t.month_str in "+monCycle;
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//趋势
				if("sndlevel0304_bumen".equals(type)){
					String sql = "select MONTH_STR,DEP,TOTAL from report_query_cbsyqk_bumen01 t where t.month_str like '"+year+"-%'";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//项目维度
				if("sndlevel0301_xiangmu".equals(type)){
					String sql = "select product_name,sum(ttp) as total from REPORT_QUERY_01_T t where t.month_str ='"+year+"-"+month+"' group by month_str,product_name ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//同比
				if("sndlevel0302_xiangmu".equals(type)){
					String sql = "select month_str,product_name,sum(ttp) as total from REPORT_QUERY_01_T t where t.month_str in "+yearCycle+" group by month_str,product_name ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//环比
				if("sndlevel0303_xiangmu".equals(type)){
					String sql = "select month_str,product_name,sum(ttp) as total from REPORT_QUERY_01_T t where t.month_str in "+monCycle+" group by month_str,product_name ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else
				//趋势
				if("sndlevel0304_xiangmu".equals(type)){
					String sql = "select month_str,product_name,sum(ttp) as total from REPORT_QUERY_01_T t where t.month_str like '"+year+"-%' group by month_str,product_name ";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}
				
				/****************************总成本二级 end******************************/
			}else if(type.indexOf("sndlevel04") == 0){
				/****************************人均成本二级 begin******************************/
				List<Object[]> temp = null;
				//同比
				if("sndlevel0401".equals(type)){
					String sql = "select monthstr,c,e,g,i,k,m from REPORT_QUERY_RJCB_08 t where t.monthstr in "+yearCycle;
					temp = cooperationService.getSeriesDataByType(sql);
				}else
				//环比
				if("sndlevel0402".equals(type)){
					String sql = "select monthstr,c,e,g,i,k,m from REPORT_QUERY_RJCB_08 t where t.monthstr in "+monCycle;
					temp = cooperationService.getSeriesDataByType(sql);
				}else
				//趋势
				if("sndlevel0403".equals(type)){
					String sql = "select monthstr,c,e,g,i,k,m from REPORT_QUERY_RJCB_08 t where t.monthstr like '"+year+"-%'";
					temp = cooperationService.getSeriesDataByType(sql);
				}
				String[] str = {"人餐宴会成本","人餐食材成本","房晚服务成本","房晚成本","人均会场服务成本","人均会场成本"};
				list = new ArrayList<Object[]>();
				for (Object[] objects : temp) {
					for (int i = 0; i < 6; i++) {
						Object[] newObj = new Object[3];
						newObj[0] = objects[0];//时间
						newObj[1] = str[i];//类型
						if(objects[i+1] == null){
							newObj[2] = 0;
						}else{
							newObj[2] = objects[i+1];//人均成本
						}
						list.add(newObj);
					}
				}
				result = JSONArray.fromObject(list);
				/****************************人均成本二级 end******************************/
				
			}else if(type.indexOf("sndlevel06") == 0){
				/****************************满意度二级 begin******************************/
				//满意度同比（部门维度）
				if("sndlevel0601_bumen".equals(type)){
					String sql = "select MONTH_STR,DEP,TOTAL from VIEW_MANYD_DEPT_01T t where t.month_str in "+yearCycle;
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else if("sndlevel0602_bumen".equals(type)){
					String sql = "select MONTH_STR,DEP,TOTAL from VIEW_MANYD_DEPT_01T t where t.month_str in "+monCycle;
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
				}else if("sndlevel0603_bumen".equals(type)){
					String sql = "select MONTH_STR,DEP,TOTAL from VIEW_MANYD_DEPT_01T t where t.month_str like '"+year+"-%'";
					list = cooperationService.getSeriesDataByType(sql);
					result = JSONArray.fromObject(list);
					//满意度同比（主题维度）
				}else if("sndlevel0601_zhuti".equals(type)){
					String sql = "select * from ( select month_str||'-'||to_char(to_date(substr(t.MM,1,LENGTH(t.MM) - 1),'mm'),'mm') as monthstr,t.* from VIEW_MANYD_PROJECT_RESULT t ) where monthstr in "+yearCycle;
					list = cooperationService.getSeriesDataByType(sql);
					List<Object[]> resultList = new ArrayList<Object[]>();
					//主题列表
					String[] title = ManYD.buildView().split(",");
					for (Object[] vals : list) {
						for (int j = 3; j < vals.length; j++) {
							Object[] obj = new Object[3];
							obj[0] = vals[0];//年月
							obj[1] = title[j-2];//主题名称
							obj[2] = vals[j];//满意度值
							resultList.add(obj);
						}
					}
					result = JSONArray.fromObject(resultList);
				}else if("sndlevel0602_zhuti".equals(type)){
					String sql = "select * from ( select month_str||'-'||to_char(to_date(substr(t.MM,1,LENGTH(t.MM) - 1),'mm'),'mm') as monthstr,t.* from VIEW_MANYD_PROJECT_RESULT t ) where monthstr in "+monCycle;
					list = cooperationService.getSeriesDataByType(sql);
					List<Object[]> resultList = new ArrayList<Object[]>();
					//主题列表
					String[] title = ManYD.buildView().split(",");
					for (Object[] vals : list) {
						for (int j = 3; j < vals.length; j++) {
							Object[] obj = new Object[3];
							obj[0] = vals[0];//年月
							obj[1] = title[j-2];//主题名称
							obj[2] = vals[j];//满意度值
							resultList.add(obj);
						}
					}
					result = JSONArray.fromObject(resultList);
				}else if("sndlevel0603_zhuti".equals(type)){
					String sql = "select * from ( select month_str||'-'||to_char(to_date(substr(t.MM,1,LENGTH(t.MM) - 1),'mm'),'mm') as monthstr,t.* from VIEW_MANYD_PROJECT_RESULT t ) where monthstr like '"+year+"-%'";
					list = cooperationService.getSeriesDataByType(sql);
					List<Object[]> resultList = new ArrayList<Object[]>();
					//主题列表
					String[] title = ManYD.buildView().split(",");
					for (Object[] vals : list) {
						for (int j = 3; j < vals.length; j++) {
							Object[] obj = new Object[3];
							obj[0] = vals[0];//年月
							obj[1] = title[j-2];//主题名称
							obj[2] = vals[j];//满意度值
							resultList.add(obj);
						}
					}
					result = JSONArray.fromObject(resultList);
				}
				
				/****************************满意度二级 end******************************/
			}
			PrintWriter out = response.getWriter();
			if(result != null){
				out.write(result.toString());
			}else{
				out.write("[]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 合交经分二级页面
	 */
	@RequestMapping(value="initLevel", method = {RequestMethod.POST,RequestMethod.GET})
	public String initLevel(HttpServletRequest request) {
		String container = request.getParameter("container");//图表
		String month = request.getParameter("month");//月份
		if(StringUtils.isNotEmpty(month)){
			if(month.length() == 1){
				month = "0"+month;
			}
		}
		String year = request.getParameter("year");//年份
		if(!StringUtils.isNotEmpty(year)){
			Calendar c = Calendar.getInstance();
			year = String.valueOf(c.get(Calendar.YEAR));
		}
		request.setAttribute("month", month);//月份
		request.setAttribute("year", year);//年份
		request.setAttribute("container", container);//一级页面id
		request.setAttribute("cycle", year+"-"+month);//周期
		return "/dc/cooperation/datasourceSndLevel";
	}
	
	/**
	 * 获得图表表格数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getChartData", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void getChartData(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		if(!StringUtils.isNotEmpty(year)){
			Calendar c = Calendar.getInstance();
			year = String.valueOf(c.get(Calendar.YEAR));
		}
		if(!StringUtils.isNotEmpty(month)){
			int i = new Date().getMonth();
			if(i == 0){
				month = "12";
			}else{
				month = String.valueOf(i);
			}
		}
		//同比
		String yearCycle = getCycle(Integer.valueOf(year),Integer.valueOf(month),1,"yyyy-MM","YEAR");
		//环比
		String monCycle = getCycle(Integer.valueOf(year),Integer.valueOf(month),1,"yyyy-MM","MON");
		
		JSONObject result = new JSONObject();
		List<Object[]> list = null;
		
		
		//判空
		if(type == null){
			type = "";
		}
		/*********************************************************总营业收入数据 begin *****************************************************************************/
		if(type.indexOf("container01_") == 0){
			//总营业收入构成-主题
			if("container01_01_zhuti".equals(type)){
				String sql = "select t.c,t.d topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c='"+year+"-"+month+"' group by t.c,t.d ";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|项目|营收（元）|占比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", 2);
			}else
			//总营业收入构成-部门
			if("container01_01_bumen".equals(type)){
				String sql = "select t.c,t.dep topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c='"+year+"-"+month+"' group by t.c,t.dep ";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|营收（元）|占比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", 2);
			}else
			//总营业收入构成-结算类型
			if("container01_01_jiesuan".equals(type)){
				String sql = "select t.c,t.F topic,sum(t.an) total from REPORT_QUERY_010 t where t.F != '收入' and t.c='"+year+"-"+month+"' group by t.c,t.F ";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|结算类别|营收（元）|占比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", 2);
				//总营业收入同比-主题维度
			}else if("container01_02_zhuti".equals(type)){
				String sql = "select t.c,t.d topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c in "+yearCycle+" group by t.c,t.d order by t.d,t.c ";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|主题|营业收入|同比".split("\\|"));
				//时间索引、分组索引、值索引
				result.put("fromIndex", "0,1,2".split(","));
				result.put("list", list);
			}else
			//总营业收入同比-部门维度
			if("container01_02_bumen".equals(type)){
				String sql = "select t.c,t.DEP topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c  in "+yearCycle+" group by t.c,t.DEP  order by t.DEP,t.c";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|营业收入|同比".split("\\|"));
				result.put("fromIndex", "0,1,2".split(","));
				result.put("list", list);
			}else
			//总营业收入同比-结算类型维度
			if("container01_02_jiesuan".equals(type)){
				String sql = "select t.c,t.F topic,sum(t.an) total from REPORT_QUERY_010 t where t.F != '收入' and t.c  in "+yearCycle+" group by t.c,t.F order by t.F,t.c ";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|结算类别|营业收入|同比".split("\\|"));
				result.put("fromIndex", "0,1,2".split(","));
				result.put("list", list);
				//环比
			}else if("container01_03_zhuti".equals(type)){
				String sql = "select t.c,t.d topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c in "+monCycle+" group by t.c,t.d order by t.d,t.c";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|主题|营业收入|环比".split("\\|"));
				result.put("fromIndex", "0,1,2".split(","));
				result.put("list", list);
			}else
			//总营业收入环比-部门维度
			if("container01_03_bumen".equals(type)){
				String sql = "select t.c,t.DEP topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c  in "+monCycle+" group by t.c,t.DEP order by t.DEP,t.c";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|营业收入|环比".split("\\|"));
				result.put("fromIndex", "0,1,2".split(","));
				result.put("list", list);
			}else
			//总营业收入环比-结算类型维度
			if("container01_03_jiesuan".equals(type)){
				String sql = "select t.c,t.F topic,sum(t.an) total from REPORT_QUERY_010 t where t.F != '收入' and t.c  in "+monCycle+" group by t.c,t.F order by t.F,t.c ";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|结算类别|营业收入|环比".split("\\|"));
				result.put("fromIndex", "0,1,2".split(","));
				result.put("list", list);
			}else
			//总营业收入趋势-主题维度
			if("container01_04_zhuti".equals(type)){
				String sql = "select t.c,t.d topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c like '"+year+"-%' group by t.c,t.d order by t.d,t.c ";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|主题|营业收入".split("\\|"));
				result.put("list", list);
			}else
			//总营业收入趋势-部门维度
			if("container01_04_bumen".equals(type)){
				String sql = "select t.c,t.dep topic,sum(t.an) total from REPORT_QUERY_010 t where t.F='收入' and  t.c like '"+year+"-%' group by t.c,t.dep order by t.DEP,t.c ";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|营业收入".split("\\|"));
				result.put("list", list);
			}else 
				if("container01_01_xiangmu".equals(type)){
					String sql = "select month_str,pro,total from report_query_shouruxm where month_str='"+year+"-"+month+"'";
					list = cooperationService.getSeriesDataByType(sql);
					result.put("head", "时间|项目|营收（元）|占比".split("\\|"));
					result.put("list", list);
					result.put("fromIndex", 2);
				}else
					//同比
					if("container01_02_xiangmu".equals(type)){
						String sql = "select month_str,pro,total from report_query_shouruxm where month_str in " +yearCycle;
						list = cooperationService.getSeriesDataByType(sql);
						result.put("head", "时间|项目|营业收入|同比".split("\\|"));
						result.put("fromIndex", "0,1,2".split(","));
						result.put("list", list);
						//反比
					}else
						if("container01_03_xiangmu".equals(type)){
							String sql = "select month_str,pro,total from report_query_shouruxm where month_str in " +monCycle;
							list = cooperationService.getSeriesDataByType(sql);
							result.put("head", "时间|项目|营业收入|环比".split("\\|"));
							result.put("fromIndex", "0,1,2".split(","));
							result.put("list", list);
							//趋势
						}else
							if("container01_04_xiangmu".equals(type)){
								String sql = "select month_str,pro,total from report_query_shouruxm where month_str like '"+year+"-%'";
								list = cooperationService.getSeriesDataByType(sql);
								result.put("head", "时间|项目|营业收入".split("\\|"));
								result.put("list", list);
							}
			
		}else
		/*********************************************************总营业收入数据 end *****************************************************************************/
		/*********************************************************业务规模-服务人天指标情况数据 begin *****************************************************************************/
		if(type.indexOf("container02_") == 0){
			//服务人天构成（部门维度维度）
			if("container02_01_bumen".equals(type)){
				String sql = "select * from report_query_20_2_BUWEIDU t where t.month_str='"+year+"-"+month+"'";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|服务类别|服务人天".split("\\|"));
				result.put("list", list);
				//服务人天构成（主题维度）
			}else if("container02_01_zhuti".equals(type)){
				String sql = "select * from report_query_20_2_T t where t.month_str='"+year+"-"+month+"'";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|服务类别|服务人天".split("\\|"));
				result.put("list", list);
				//同比（部门维度）
			}else if("container02_02_bumen".equals(type) || "container02_02_zhuti".equals(type)){
				String sql = "select * from report_query_20_2_BUWEIDU t where t.month_str in "+yearCycle+" order by t.column_06,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|服务类别|服务人天|同比".split("\\|"));
				result.put("fromIndex", "0,1,2".split(","));
				result.put("list", list);
				//环比（部门维度）
			}else if("container02_04_bumen".equals(type) || "container02_04_zhuti".equals(type)){
				String sql = "select * from report_query_20_2_BUWEIDU t where t.month_str in "+monCycle+" order by t.column_06,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|服务类别|服务人天|环比".split("\\|"));
				result.put("fromIndex", "0,1,2".split(","));
				result.put("list", list);
				//趋势，主题维度
			}else if("container02_03_bumen".equals(type)){
				String sql = "select * from report_query_20_2_BUWEIDU t where t.month_str like '"+year+"-%'  order by t.column_06,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|服务类别|服务人天".split("\\|"));
				result.put("list", list);
			}else if("container02_03_zhuti".equals(type)){
				String sql = "select * from report_query_20_2_T t where t.month_str like '"+year+"-%'  order by t.column_04,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|服务类别|服务人天".split("\\|"));
				result.put("list", list);
			}
		}else
		/*********************************************************业务规模-服务人天指标情况数据 end *****************************************************************************/
			/*********************************************************总成本数据 begin *****************************************************************************/
		if(type.indexOf("container03_") == 0){
			//总成本部门维度
			if("container03_01_bumen".equals(type)){
				String sql = "select * from report_query_cbsyqk_bumen01 t where t.month_str='"+year+"-"+month+"'";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|成本（元）|占比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", 2);
			}else
			//总成本项目维度
			if("container03_01_xiangmu".equals(type)){
				String sql = "select month_str,product_name,sum(ttp) as total from REPORT_QUERY_01_T t where t.month_str='"+year+"-"+month+"' group by product_name,month_str ";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|项目|成本（元）|占比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", 2);
			}else
			//成本同比部门维度
			if("container03_02_bumen".equals(type)){
				String sql = "select * from report_query_cbsyqk_bumen01 t where t.month_str in "+yearCycle+" order by dep,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|成本（元）|同比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", "0,1,2".split(","));
			}else
			//成本同比项目维度
			if("container03_02_xiangmu".equals(type)){
				String sql = "select month_str,product_name,sum(ttp) as total from REPORT_QUERY_01_T t where t.month_str in "+yearCycle+" group by month_str,product_name order by product_name,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|项目|成本（元）|同比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", "0,1,2".split(","));
			}else
			//成本环比部门维度
			if("container03_03_bumen".equals(type)){
				String sql = "select * from report_query_cbsyqk_bumen01 t where t.month_str in "+monCycle+" order by dep,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|成本（元）|环比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", "0,1,2".split(","));
			}else
				//成本环比项目维度
			if("container03_03_xiangmu".equals(type)){
				String sql = "select month_str,product_name,sum(ttp) as total from REPORT_QUERY_01_T t where t.month_str in "+monCycle+" group by month_str,product_name order by product_name,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|项目|成本（元）|环比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", "0,1,2".split(","));
			}else
			//成本趋势部门维度
			if("container03_04_bumen".equals(type)){
				String sql = "select * from report_query_cbsyqk_bumen01 t where t.month_str like '"+year+"-%' order by DEP,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|成本（元）".split("\\|"));
				result.put("list", list);
			}else
			//成本趋势项目维度
			if("container03_04_xiangmu".equals(type)){
				String sql = "select month_str,product_name,sum(ttp) as total from REPORT_QUERY_01_T t where t.month_str like '"+year+"-%' group by month_str,product_name order by product_name,t.month_str";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|项目|成本（元）".split("\\|"));
				result.put("list", list);
			}
		}else
		/*********************************************************总成本数据 end *****************************************************************************/
		
		if(type.indexOf("container04_") == 0){
			
			list = new ArrayList<Object[]>();
			String[] str = {"宴会","食材","房晚服务","房晚","会场服务","会场"};
			String sql = "";
			if("container04_01_other".equals(type)){
				//人均成本使用情况数据
				sql = "select monthstr,c,e,g,i,k,m,b,d,f,h,j,l from REPORT_QUERY_RJCB_08 t where t.monthstr in "+yearCycle + " order by monthstr";
			}else //环比
			if("container04_02_other".equals(type)){
				//人均成本使用情况数据
				sql = "select monthstr,c,e,g,i,k,m,b,d,f,h,j,l from REPORT_QUERY_RJCB_08 t where t.monthstr in "+monCycle + " order by monthstr";
			}else if("container04_03_other".equals(type)){
				//人均成本使用情况数据
				sql = "select monthstr,c,e,g,i,k,m,b,d,f,h,j,l from REPORT_QUERY_RJCB_08 t where t.monthstr like '"+year+"-%'" + " order by monthstr";
			}
			
			List<Object[]> temp = cooperationService.getSeriesDataByType(sql);
			
//				时间|类型|服务量|成本|人均成本|同比
			for (Object[] objects : temp) {
				//开房量
				String sqlKF = "select 开房量 from report_query_023_t where month_str||'-'||monthstr='"+objects[0]+"'";
				//宴会部服务量
				String sqlYH = "select 合计 from report_query_yhbfwl where 月份='"+objects[0]+"'";
				//会场部累计服务量
				String sqlHCB = "select 合计 from report_query_19_1 where monthstr='"+objects[0]+"'";
				
				for (int i = 0; i < 6; i++) {
					Object[] newObj = new Object[5];
					newObj[0] = objects[0];//时间
					newObj[1] = str[i];//类型
					//宴会部服务量
					List<Object[]> sqlYHList = null;
					if(i == 0 || i == 1){
						sqlYHList = cooperationService.getSeriesDataByType(sqlYH);
						//开房量
					}else if(i == 2 || i == 3){
						sqlYHList = cooperationService.getSeriesDataByType(sqlKF);
						//会场部累计服务量
					}else if(i == 4 || i == 5){
						sqlYHList = cooperationService.getSeriesDataByType(sqlHCB);
					}
					if(sqlYHList != null && sqlYHList.size() > 0){
						newObj[2] = sqlYHList.get(0);
					}else{
						newObj[2] = 0;
					}
					
					if(objects[i+7] == null){
						newObj[3] = 0;
					}else{
						newObj[3] = objects[i+7];//成本
					}
					if(objects[i+1] == null){
						newObj[4] = 0;
					}else{
						newObj[4] = objects[i+1];//人均成本
					}
					list.add(newObj);
				}
			}
			//同比
			if("container04_01_other".equals(type)){
				result.put("head", "时间|类型|服务/开房量|成本|人均成本|同比".split("\\|"));
				//环比
			}else if("container04_02_other".equals(type)){
				result.put("head", "时间|类型|服务/开房量|成本|人均成本|环比".split("\\|"));
			}else if("container04_03_other".equals(type)){
				result.put("head", "时间|类型|服务/开房量|成本|人均成本".split("\\|"));
			}
			result.put("list", list);
			result.put("fromIndex", "0,1,4".split(","));
			
		}else
		/*********************************************************预算完成情况数据 begin *****************************************************************************/
		if(type.indexOf("container05") == 0){
			
			if("container0501".equals(type)){
				//预算完成情况数据--结算前
				String sql05 = "select month_str||'-'||to_char(to_date(substr(D1,1,LENGTH(D1) - 1),'mm'),'mm') as monthstr,D8,D9,D17,D15 from view_yswcfx_029_2 where month_str='"+year+"' order by monthstr";
				List<Object[]> dataContainer05 = cooperationService.getSeriesDataByType(sql05);
				result.put("head", "月份|当月完成（结算前）|累计完成（结算前）|进度差|完成比例".split("\\|"));
				result.put("list", dataContainer05);
			}else
			//结算后
			if("container0502".equals(type)){
				String sql05 = "select month_str||'-'||to_char(to_date(substr(D1,1,LENGTH(D1) - 1),'mm'),'mm') as monthstr,D13,D14,D18,D16 from view_yswcfx_029_2 where month_str='"+year+"' order by monthstr";
				List<Object[]> dataContainer05 = cooperationService.getSeriesDataByType(sql05);
				result.put("head", "月份|当月完成（结算后）|累计完成（结算后）|进度差|完成比例".split("\\|"));
				result.put("list", dataContainer05);
				//满意度部门维度
			}else if("container05_0601".equals(type)){
				String sql06 = "select month_str||'-'||to_char(to_date(substr(月份,1,LENGTH(月份) - 1),'mm'),'mm') as monthstr, " +
						" sum(前台礼宾),sum(客房),sum(餐饮),sum(会议),sum(康乐),sum(安保),max(总计),max(总计),max(总计),max(总计),max(总计),max(总计) " +
						" from VIEW_MANYD_DEPT where month_str='"+year+"' group by month_str||'-'||to_char(to_date(substr(月份,1,LENGTH(月份) - 1),'mm'),'mm') " +
						" order by month_str||'-'||to_char(to_date(substr(月份,1,LENGTH(月份) - 1),'mm'),'mm')";
				List<Object[]> temp06 = cooperationService.getSeriesDataByType(sql06);
				
				List<Object[]> dataContainer06 = new ArrayList<Object[]>();
				String[] str06 = {"前台礼宾","客房","餐饮","会议","康乐","安保"};
				for (int j = 0; j < temp06.size();j++) {
					Object[] objects = temp06.get(j);
					for (int i = 0; i < 6; i++) {
//						月份|部门|满意度|累计|月平均|全年平均
						Double leiji = 0d;
						for (int k = 0; k <= j; k++) {
							leiji += Double.valueOf(((BigDecimal) temp06.get(k)[i+1]).toString());//遍历上个月相加得出累计
						}
						Double zongji = 0d;
						for (int k = 0; k < temp06.size();k++) {
							zongji += Double.valueOf(((BigDecimal) temp06.get(k)[i+7]).toString());//所有总计相加得出全年
						}
						Object[] newObj = new Object[6];
						newObj[0] = objects[0];//月份
						newObj[1] = str06[i];//部门
						newObj[2] = objects[i+1];//满意度
						newObj[3] = leiji;//累计
						newObj[4] = objects[i+7];//月平均
						newObj[5] = new DecimalFormat("#.00").format(zongji / temp06.size());//全年平均
						dataContainer06.add(newObj);
					}
				}
//				request.setAttribute("dataContainer06", dataContainer06);
				result.put("head", "月份|部门|满意度|累计|月平均|全年平均".split("\\|"));
				result.put("list", dataContainer06);
			}else if("container05_0602".equals(type)){
				//主题列表
				String[] title = ManYD.buildView().split(",");
				String sql = "select * from ( select month_str||'-'||to_char(to_date(substr(t.MM,1,LENGTH(t.MM) - 1),'mm'),'mm') as monthstr,t.* from VIEW_MANYD_PROJECT_RESULT t ) where monthstr like '"+year+"-%' order by monthstr";
				list = cooperationService.getSeriesDataByType(sql);
				List<Object[]> resultList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] vals = list.get(i);
					for (int j = 3; j < vals.length; j++) {
						Double leiji = 0d;
						for (int k = 0; k <= i; k++) {
							leiji += Double.valueOf(((BigDecimal) list.get(k)[j]).toString());//遍历上个月相加得出累计
						}
						Double zongji = 0d;
						for (int k = 0; k < list.size();k++) {
							for (int jk = 3; jk < vals.length; jk++) {
								zongji += Double.valueOf(((BigDecimal) list.get(k)[jk]).toString());//所有总计相加得出全年
							}
						}
						double yuepj = 0d;
						for (int k = 3; k < vals.length; k++) {
							yuepj += Double.valueOf(((BigDecimal) vals[k]).toString());//月总值
						}
						Object[] obj = new Object[6];
						obj[0] = vals[0];//年月
						obj[1] = title[j-2];//主题名称
						obj[2] = vals[j];//满意度值
						obj[3] = leiji;//累计
						obj[4] = new DecimalFormat("#.00").format(yuepj / (title.length - 1));//月平均,部门个数 - 月份列
						obj[5] = new DecimalFormat("#0.00").format(zongji / list.size() / (title.length - 1));//全年平均
						resultList.add(obj);
					}
				}
				result.put("head", "时间|项目|满意度|累计|月平均|全年平均".split("\\|"));
				result.put("list", resultList);
			}
			
		}else
		/*********************************************************预算完成情况数据 end *****************************************************************************/
			
		/*********************************************************满意度数据 begin *****************************************************************************/
		if(type.indexOf("container06_") == 0){
			//同比
			if("container06_01_bumen".equals(type)){
				String sql = "select MONTH_STR,DEP,TOTAL from VIEW_MANYD_DEPT_01T t where t.month_str in "+yearCycle + " order by DEP,MONTH_STR";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|满意度|同比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", "0,1,2".split(","));
				//环比
			}else if("container06_02_bumen".equals(type)){
				String sql = "select MONTH_STR,DEP,TOTAL from VIEW_MANYD_DEPT_01T t where t.month_str in "+monCycle + " order by DEP,MONTH_STR";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|满意度|环比".split("\\|"));
				result.put("list", list);
				result.put("fromIndex", "0,1,2".split(","));
				//部门趋势
			}else if("container06_03_bumen".equals(type)){
				String sql = "select MONTH_STR,DEP,TOTAL from VIEW_MANYD_DEPT_01T t where t.month_str like '"+year+"-%'" + " order by DEP,MONTH_STR";
				list = cooperationService.getSeriesDataByType(sql);
				result.put("head", "时间|部门|满意度".split("\\|"));
				result.put("list", list);
				//满意度同比（主题维度）
			}else if("container06_01_zhuti".equals(type)){
				String[] title = ManYD.buildView().split(",");
				String sql = "select * from ( select month_str||'-'||to_char(to_date(substr(t.MM,1,LENGTH(t.MM) - 1),'mm'),'mm') as monthstr,t.* from VIEW_MANYD_PROJECT_RESULT t ) where monthstr in "+yearCycle;
				list = cooperationService.getSeriesDataByType(sql);
				List<Object[]> resultList = new ArrayList<Object[]>();
				//主题列表
				for (Object[] vals : list) {
					for (int j = 3; j < vals.length; j++) {
						Object[] obj = new Object[3];
						obj[0] = vals[0];//年月
						obj[1] = title[j-2];//主题名称
						obj[2] = vals[j];//满意度值
						resultList.add(obj);
					}
				}
				result.put("head", "时间|项目|满意度|同比".split("\\|"));
				result.put("list", resultList);
				result.put("fromIndex", "0,1,2".split(","));
			}else if("container06_02_zhuti".equals(type)){
				String[] title = ManYD.buildView().split(",");
				String sql = "select * from ( select month_str||'-'||to_char(to_date(substr(t.MM,1,LENGTH(t.MM) - 1),'mm'),'mm') as monthstr,t.* from VIEW_MANYD_PROJECT_RESULT t ) where monthstr in "+monCycle;
				list = cooperationService.getSeriesDataByType(sql);
				List<Object[]> resultList = new ArrayList<Object[]>();
				//主题列表
				for (Object[] vals : list) {
					for (int j = 3; j < vals.length; j++) {
						Object[] obj = new Object[3];
						obj[0] = vals[0];//年月
						obj[1] = title[j-2];//主题名称
						obj[2] = vals[j];//满意度值
						resultList.add(obj);
					}
				}
				result.put("head", "时间|项目|满意度|环比".split("\\|"));
				result.put("list", resultList);
				result.put("fromIndex", "0,1,2".split(","));
			}else if("container06_03_zhuti".equals(type)){
				//主题列表
				String[] title = ManYD.buildView().split(",");
				String sql = "select * from ( select month_str||'-'||to_char(to_date(substr(t.MM,1,LENGTH(t.MM) - 1),'mm'),'mm') as monthstr,t.* from VIEW_MANYD_PROJECT_RESULT t ) where monthstr like '"+year+"-%'";
				list = cooperationService.getSeriesDataByType(sql);
				List<Object[]> resultList = new ArrayList<Object[]>();
				for (Object[] vals : list) {
					for (int j = 3; j < vals.length; j++) {
						Object[] obj = new Object[3];
						obj[0] = vals[0];//年月
						obj[1] = title[j-2];//主题名称
						obj[2] = vals[j];//满意度值
						resultList.add(obj);
					}
				}
				result.put("head", "时间|项目|满意度".split("\\|"));
				result.put("list", resultList);
			}
		}
		/*********************************************************满意度数据 end *****************************************************************************/
		try {
			PrintWriter out = response.getWriter();
			out.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 同比、环比
	 * @param year
	 * @param month
	 * @param day
	 * @param format
	 * @return
	 */
	private String getCycle(int year,int month,int day,String format,String type){
		
		DateFormat daf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		String cycle = "(";
		//环比
		if("MON".equals(type)){
			for (int i = 0; i < 6; i++) {
				c.add(Calendar.MONTH, -1);
				if(i == 0){
					cycle += "'"+ daf.format(c.getTime()) +"'";
				}else{
					cycle += ",'"+ daf.format(c.getTime()) +"'";
				}
			}
			//同比
		}else if("YEAR".equals(type)){
			c.add(Calendar.MONTH, -1);
			for (int i = 0; i < 6; i++) {
				if(i == 0){
					cycle += "'"+ daf.format(c.getTime()) +"'";
				}else{
					c.add(Calendar.YEAR, -1);
					cycle += ",'"+ daf.format(c.getTime()) +"'";
				}
			}
		}
		cycle += ")";
		return cycle;
	}
}
