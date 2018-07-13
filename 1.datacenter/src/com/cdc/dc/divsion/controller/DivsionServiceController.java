package com.cdc.dc.divsion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.inter.client.db.mysql.jdbc.MysqlJdbcConnection;
import com.cdc.inter.client.db.sqlserver.jdbc.SqlJdbcConnection;
import com.cdc.system.core.authentication.controller.CommonController;
/**
 * 服务平台经分统计表
 * @author ZENGKAI
 * @date 2016-12-06 09:39:29
 */
@Controller
@RequestMapping(value = "/divsion/")
public class DivsionServiceController extends CommonController{
	
	/**
	 * 服务平台经分首页
	 */
	@RequestMapping(value="service", method = {RequestMethod.POST,RequestMethod.GET})
	public String service(HttpServletRequest request) {
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
		return "/dc/divsion/service/servicehome";
	}
	

	/**
	 * 掌上南基二级页面
	 */
	@RequestMapping(value="servicesnd", method = {RequestMethod.POST,RequestMethod.GET})
	public String servicesnd(HttpServletRequest request) {
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
		String name = request.getParameter("name");
		if(StringUtils.isNotEmpty(name)){
			try {
				name = URLDecoder.decode(name, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("name", name);//系统
		request.setAttribute("month", month);//月份
		request.setAttribute("year", year);//年份
		request.setAttribute("container", container);//一级页面id
		request.setAttribute("cycle", year+"-"+month);//周期
		return "/dc/divsion/service/servicesnd";
	}
	
	/**
	 * 获得图表图形数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getServiceData", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void getServiceData(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
		String name = request.getParameter("name");
		JSONArray result = null;
		List<Object[]> list = null;
		String sql = "";
		String sqlType = "SqlServer";
		try{
			//一级页面查询
			if("containerService01".equals(type)){
				sql = "SELECT LEFT ( CONVERT (VARCHAR, RequestTime, 23),7) month_str,'访问人数(按月)' name,COUNT (1) value FROM SysUrlRequestLog " +
						"WHERE  RequestUrl='/index.aspx' and  LEFT (CONVERT (VARCHAR, RequestTime, 23),4)='"+year+"' GROUP BY LEFT (CONVERT (VARCHAR, RequestTime, 23), 7) ";
			}else if("containerService02".equals(type)){
				sqlType = "MySQL";
				sql = "select DATE_FORMAT(temp.month_str, '%Y-%m') month_str,max(temp.`VALUE`) VALUE from  " +
						" (SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.`all`) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID where ifnull(`org`.PARENT_ID,'')='' GROUP BY t.`year`,t.`month`,t.`day` ORDER BY t.`year` DESC,t.`month` DESC,t.`day` DESC ) temp WHERE " +
						" DATE_FORMAT(temp.month_str, '%Y') = '"+year+"'  GROUP BY DATE_FORMAT(temp.month_str, '%Y-%m') ORDER BY DATE_FORMAT(temp.month_str, '%Y-%m')";
			
			}else if("containerService03".equals(type)){
				sqlType = "MySQL";
				sql = "select DATE_FORMAT(temp.month_str, '%Y-%m') month_str,SUM(temp.`VALUE`) VALUE from  " +
						" (SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.today) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID where ifnull(`org`.PARENT_ID,'')='' GROUP BY t.`year`,t.`month`,t.`day` ORDER BY t.`year` DESC,t.`month` DESC,t.`day` DESC ) temp WHERE " +
						" DATE_FORMAT(temp.month_str, '%Y') = '"+year+"'  GROUP BY DATE_FORMAT(temp.month_str, '%Y-%m') ORDER BY DATE_FORMAT(temp.month_str, '%Y-%m')";
				//第一个图表二级页
			}else if(type.indexOf("containerService01_") == 0){
				
				if("containerService01_01".equals(type)){
					sql = "select CONVERT (VARCHAR, RequestTime, 23) month_str,'访问人数(按天)' name,count(1) value from SysUrlRequestLog " +
							" WHERE  RequestUrl='/index.aspx' and  LEFT (CONVERT (VARCHAR, RequestTime, 23),7) = '"+year+"-"+month+"' " +
							" GROUP BY CONVERT (VARCHAR, RequestTime, 23) ORDER BY CONVERT (VARCHAR, RequestTime, 23)";
				}else if("containerService01_02".equals(type)){
					sql = "SELECT LEFT ( CONVERT (VARCHAR, RequestTime, 23),7) month_str,'访问人数' name,COUNT (1) value FROM SysUrlRequestLog " +
							"WHERE  RequestUrl='/index.aspx' and  LEFT (CONVERT (VARCHAR, RequestTime, 23),4)='"+year+"' or LEFT (CONVERT (VARCHAR, RequestTime, 23),4)='"+(Integer.valueOf(year)-1)+"' GROUP BY LEFT (CONVERT (VARCHAR, RequestTime, 23), 7) ";
				}
			}else if(type.indexOf("containerService02_") == 0){
				//第二个图表二级页
				if("containerService02_01".equals(type)){
					sqlType = "MySQL";
					sql = "SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.`all`) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID " +
							" where t.`year`='"+year+"' AND t.`month`='"+month+"' AND ifnull(`org`.PARENT_ID,'')='' " +
							" GROUP BY t.`year`, t.`month`, t.`day` ORDER BY t.`year` DESC, t.`month` DESC, t.`day` DESC";
				}else if("containerService02_02".equals(type)){

					sqlType = "MySQL";
					sql = "select DATE_FORMAT(temp.month_str, '%Y-%m') month_str,max(temp.`VALUE`) VALUE from  " +
							" (SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.`all`) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID where ifnull(`org`.PARENT_ID,'')='' GROUP BY t.`year`,t.`month`,t.`day` ORDER BY t.`year` DESC,t.`month` DESC,t.`day` DESC ) temp WHERE " +
							" DATE_FORMAT(temp.month_str, '%Y') = '"+year+"' OR DATE_FORMAT(temp.month_str, '%Y') = '"+(Integer.valueOf(year)-1)+"' GROUP BY DATE_FORMAT(temp.month_str, '%Y-%m') ORDER BY DATE_FORMAT(temp.month_str, '%Y-%m')";
				
				}
			}else if(type.indexOf("containerService03_") == 0){
				//第三个图表二级页
				if("containerService03_01".equals(type)){
					sqlType = "MySQL";
					sql = "SELECT  concat(   t.`year`,   '-',   t.`month`,   '-',   t.`day`  ) month_str,  SUM(t.today) VALUE  FROM  emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID WHERE  t.`year` = '"+year+"' AND t.`month` = '"+month+"' AND ifnull(`org`.PARENT_ID, '') = '' GROUP BY  t.`year`,  t.`month`,  t.`day` ORDER BY  t.`year` DESC,  t.`month` DESC,  t.`day` DESC ";
				}else if("containerService03_02".equals(type)){
					sqlType = "MySQL";
					sql = "select DATE_FORMAT(temp.month_str, '%Y-%m') month_str,SUM(temp.`VALUE`) VALUE from  " +
							" (SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.today) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID where ifnull(`org`.PARENT_ID,'')='' GROUP BY t.`year`,t.`month`,t.`day` ORDER BY t.`year` DESC,t.`month` DESC,t.`day` DESC ) temp WHERE " +
							" DATE_FORMAT(temp.month_str, '%Y') = '"+year+"' OR DATE_FORMAT(temp.month_str, '%Y') = '"+(Integer.valueOf(year)-1)+"'  GROUP BY DATE_FORMAT(temp.month_str, '%Y-%m') ORDER BY DATE_FORMAT(temp.month_str, '%Y-%m')";
				}
			}
			if("SqlServer".equals(sqlType)){
				list = getSeriesDataByJDBC(sql);
			}else if("MySQL".equals(sqlType)){
				list = getSeriesDataByMyJDBC(sql);
			}
			//月平均数计算
			if("containerService03".equals(type) || "containerService03_02".equals(type)){
				list = getAvg(year,month,list);
			}
			//累计数计算
			if("containerService02_01".equals(type)){
				list = getTotal(year,month,list);
			}
			
			//同环比计算
			if("containerService01_02".equals(type) || "containerService02_02".equals(type) || "containerService03_02".equals(type)){
				//计算同环比
				list = getPercent(year,month,list);
			}
			result = JSONArray.fromObject(list);
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
	 * 获得图表表格数据
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws Exception
	 */
	@RequestMapping(value = "serviceChartData", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void serviceChartData(HttpServletRequest request,HttpServletResponse response) throws SQLException{
		String type = request.getParameter("type");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		String name = request.getParameter("name");
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
		JSONObject result = new JSONObject();
		List<Object[]> list = null;
		String sql = "";
		String sqlType = "SqlServer";
		//判空
		if(type == null){
			type = "";
		}
		//一级页面查询
		if("tbodycontainerService01".equals(type)){
			sql = "SELECT LEFT ( CONVERT (VARCHAR, RequestTime, 23),7) month_str,'访问人数' name,COUNT (1) value FROM SysUrlRequestLog " +
					"WHERE RequestUrl='/index.aspx' and  LEFT (CONVERT (VARCHAR, RequestTime, 23),4)='"+year+"' GROUP BY LEFT (CONVERT (VARCHAR, RequestTime, 23), 7)  ORDER BY LEFT (CONVERT (VARCHAR, RequestTime, 23),7) ";
			result.put("head", "月份|1月|2月|3月|4月|5月|6月|7月|8月|9月|10月|11月|12月|总计".split("\\|"));
		}else if("tbodycontainerService02".equals(type)){
			sqlType = "MySQL";
			sql = "select DATE_FORMAT(temp.month_str, '%Y-%m') month_str,max(temp.`VALUE`) VALUE from  " +
					" (SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.`all`) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID where ifnull(`org`.PARENT_ID,'')='' GROUP BY t.`year`,t.`month`,t.`day` ORDER BY t.`year` DESC,t.`month` DESC,t.`day` DESC ) temp WHERE " +
					" DATE_FORMAT(temp.month_str, '%Y') = '"+year+"'  GROUP BY DATE_FORMAT(temp.month_str, '%Y-%m') ORDER BY DATE_FORMAT(temp.month_str, '%Y-%m')";
		
			result.put("head", "月份|1月|2月|3月|4月|5月|6月|7月|8月|9月|10月|11月|12月".split("\\|"));
		}else if("tbodycontainerService03".equals(type)){
			sqlType = "MySQL";
			sql = "select DATE_FORMAT(temp.month_str, '%Y-%m') month_str,SUM(temp.`VALUE`) VALUE from  " +
					" (SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.today) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID where ifnull(`org`.PARENT_ID,'')='' GROUP BY t.`year`,t.`month`,t.`day` ORDER BY t.`year` DESC,t.`month` DESC,t.`day` DESC ) temp WHERE " +
					" DATE_FORMAT(temp.month_str, '%Y') = '"+year+"'  GROUP BY DATE_FORMAT(temp.month_str, '%Y-%m') ORDER BY DATE_FORMAT(temp.month_str, '%Y-%m')";
			result.put("head", "月份|1月|2月|3月|4月|5月|6月|7月|8月|9月|10月|11月|12月|总计".split("\\|"));
			//第一个图表二级页
		}else if(type.indexOf("tbodycontainerService01_") == 0){
			
			if("tbodycontainerService01_01".equals(type)){
				sql = "select CONVERT (VARCHAR, RequestTime, 23) month_str,'访问人数(按天)' name,count(1) value from SysUrlRequestLog " +
						" WHERE  RequestUrl='/index.aspx' and  LEFT (CONVERT (VARCHAR, RequestTime, 23),7) = '"+year+"-"+month+"' " +
						" GROUP BY CONVERT (VARCHAR, RequestTime, 23) ORDER BY CONVERT (VARCHAR, RequestTime, 23)";
				int days = getDaysByYearMonth(year,month);
				StringBuffer sb = new StringBuffer("月份|");
				for (int i = 1; i <= days; i++) {
					sb.append(i+"号|");
				}
				sb.append("总计");
				result.put("head", sb.toString().split("\\|"));
			}else if("tbodycontainerService01_02".equals(type)){
				sql = "SELECT LEFT ( CONVERT (VARCHAR, RequestTime, 23),7) month_str,'访问人数' name,COUNT (1) value FROM SysUrlRequestLog " +
						"WHERE  RequestUrl='/index.aspx' and  LEFT (CONVERT (VARCHAR, RequestTime, 23),4)='"+year+"' or LEFT (CONVERT (VARCHAR, RequestTime, 23),4)='"+(Integer.valueOf(year)-1)+"' GROUP BY LEFT (CONVERT (VARCHAR, RequestTime, 23), 7) ";
				result.put("head", "月份|1月|2月|3月|4月|5月|6月|7月|8月|9月|10月|11月|12月".split("\\|"));
			}
		}else if(type.indexOf("tbodycontainerService02_") == 0){
			//第二个图表二级页
			if("tbodycontainerService02_01".equals(type)){
				sqlType = "MySQL";
				sql = "SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.`all`) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID " +
						" where t.`year`='"+year+"' AND t.`month`='"+month+"' AND ifnull(`org`.PARENT_ID,'')='' " +
						" GROUP BY t.`year`, t.`month`, t.`day` ORDER BY t.`year` DESC, t.`month` DESC, t.`day` DESC";
			
				int days = getDaysByYearMonth(year,month);
				StringBuffer sb = new StringBuffer("月份|");
				for (int i = 1; i <= days; i++) {
					sb.append(i+"号|");
				}
//				sb.append("总计");
				result.put("head", sb.toString().split("\\|"));
			}else if("tbodycontainerService02_02".equals(type)){
				sqlType = "MySQL";
				sql = "select DATE_FORMAT(temp.month_str, '%Y-%m') month_str,max(temp.`VALUE`) VALUE from  " +
						" (SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.`all`) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID where ifnull(`org`.PARENT_ID,'')='' GROUP BY t.`year`,t.`month`,t.`day` ORDER BY t.`year` DESC,t.`month` DESC,t.`day` DESC ) temp WHERE " +
						" DATE_FORMAT(temp.month_str, '%Y') = '"+year+"' OR DATE_FORMAT(temp.month_str, '%Y') = '"+(Integer.valueOf(year)-1)+"' GROUP BY DATE_FORMAT(temp.month_str, '%Y-%m') ORDER BY DATE_FORMAT(temp.month_str, '%Y-%m')";
				result.put("head", "月份|1月|2月|3月|4月|5月|6月|7月|8月|9月|10月|11月|12月".split("\\|"));
			}
		}else if(type.indexOf("tbodycontainerService03_") == 0){
			//第三个图表二级页
			if("tbodycontainerService03_01".equals(type)){
				sqlType = "MySQL";
				sql = "SELECT  concat(   t.`year`,   '-',   t.`month`,   '-',   t.`day`  ) month_str,  SUM(t.today) VALUE  FROM  emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID WHERE  t.`year` = '"+year+"' AND t.`month` = '"+month+"' AND ifnull(`org`.PARENT_ID, '') = '' GROUP BY  t.`year`,  t.`month`,  t.`day` ORDER BY  t.`year` DESC,  t.`month` DESC,  t.`day` DESC ";
				int days = getDaysByYearMonth(year,month);
				StringBuffer sb = new StringBuffer("月份|");
				for (int i = 1; i <= days; i++) {
					sb.append(i+"号|");
				}
				sb.append("总计");
				result.put("head", sb.toString().split("\\|"));
			}else if("tbodycontainerService03_02".equals(type)){
				sqlType = "MySQL";
				sql = "select DATE_FORMAT(temp.month_str, '%Y-%m') month_str,SUM(temp.`VALUE`) VALUE from  " +
						" (SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.today) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID where ifnull(`org`.PARENT_ID,'')='' GROUP BY t.`year`,t.`month`,t.`day` ORDER BY t.`year` DESC,t.`month` DESC,t.`day` DESC ) temp WHERE " +
						" DATE_FORMAT(temp.month_str, '%Y') = '"+year+"' OR DATE_FORMAT(temp.month_str, '%Y') = '"+(Integer.valueOf(year)-1)+"'  GROUP BY DATE_FORMAT(temp.month_str, '%Y-%m') ORDER BY DATE_FORMAT(temp.month_str, '%Y-%m')";
				result.put("head", "月份|1月|2月|3月|4月|5月|6月|7月|8月|9月|10月|11月|12月".split("\\|"));
			}
		}
		if("SqlServer".equals(sqlType)){
			list = getSeriesDataByJDBC(sql);
		}else if("MySQL".equals(sqlType)){
			list = getSeriesDataByMyJDBC(sql);
		}
		
		//月平均数计算
		if("tbodycontainerService03".equals(type) || "tbodycontainerService03_02".equals(type)){
			list = getAvg(year,month,list);
		}
		
		//累计数计算
		if("tbodycontainerService02_01".equals(type)){
			list = getTotal(year,month,list);
		}
		
		if("tbodycontainerService01_02".equals(type) || "tbodycontainerService02_02".equals(type) || "tbodycontainerService03_02".equals(type)){
			//计算同环比
			list = getPercent(year,month,list);
		}
		result.put("list", list);
		try {
			PrintWriter out = response.getWriter();
			out.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private List<Object[]> getTotal(String year, String month, List<Object[]> list) throws SQLException {
		List<Object[]> result = new ArrayList<Object[]>();
		if(list == null || list.size() <= 0)return result;
		Map<String,Double> valmap = new HashMap<String,Double>();
		for (int i = 0; i < list.size(); i++) {
			valmap.put((String)list.get(i)[0],Double.valueOf((Long) list.get(i)[2]));
		}
		int days = getDaysByYearMonth(year,month);
		for (int i = 1; i <= days; i++) {
			//同比
			Object[] objtb = new Object[3];
			String ym = year + "-" + month + "-" + (i >= 10 ? i : "0"+i);
			objtb[0] = ym;
			objtb[1] = "用户数";
			if(i > 1){
				objtb[2] = (valmap.get(ym) == null || valmap.get(ym) == 0) ? result.get(i - 2)[2] : valmap.get(ym);
			}else{
				if(valmap.get(ym) == null || valmap.get(ym) == 0){
					String sql = "SELECT concat(t.`year`,'-' ,t.`month`,'-' ,t.`day`) month_str,SUM(t.`all`) VALUE FROM emb_analys t JOIN uam_organization org ON `t`.ORG_ID = `org`.ORG_ID " +
							" where t.`year`='"+year+"' AND t.`month`='"+(Integer.valueOf(month) - 1)+"' AND ifnull(`org`.PARENT_ID,'')='' " +
							" GROUP BY t.`year`, t.`month`, t.`day` ORDER BY t.`year` DESC, t.`month` DESC, t.`day` DESC";
					List<Object[]> tempList = getSeriesDataByMyJDBC(sql);
					if(tempList != null && tempList.size() > 0){
						objtb[2] = tempList.get(0)[2];
					}
				}else{
					objtb[2] = valmap.get(ym);
				}
			}
			result.add(objtb);
		}
		return result;
	}
	private List<Object[]> getPercent(String year, String month, List<Object[]> list) {
		List<Object[]> result = new ArrayList<Object[]>();
		if(list == null || list.size() <= 0)return result;
		Map<String,Double> valmap = new HashMap<String,Double>();
		for (int i = 0; i < list.size(); i++) {
			valmap.put((String)list.get(i)[0],Double.valueOf((Long) list.get(i)[2]));
		}
		
		for (int i = 1; i <= 12; i++) {
			//同比
			Object[] objtb = new Object[3];
			String ym = year + "-" + (i >= 10 ? i : "0"+i);
			//去年同期
			String ly = (Integer.valueOf(year) - 1) + "-" + (i >= 10 ? i : "0"+i);
			objtb[0] = ym;
			objtb[1] = "同比";
			double max = valmap.get(ym) == null ? 0d : valmap.get(ym);
			double min = valmap.get(ly) == null ? 0d : valmap.get(ly);
			objtb[2] = min != 0 ? (max - min)/min * 100 : 0;
			result.add(objtb);
			
			//环比
			Object[] objhb = new Object[3];
			//上月同期
			String lm = (i == 1 ? ((Integer.valueOf(year) - 1) + "-12") : (year + "-" + ((i-1) >= 10 ? i-1 : "0"+(i-1))));
			objhb[0] = ym;
			objhb[1] = "环比";
			double hbmin = valmap.get(lm) == null ? 0d : valmap.get(lm);
			objhb[2] = hbmin != 0 ? (max - hbmin) / hbmin * 100 : 0;
			//new DecimalFormat("#0.00").format()
			result.add(objhb);
		}
		return result;
	}


	private List<Object[]> getAvg(String year, String month, List<Object[]> list) {
		if(list == null || list.size() <= 0)return list;
		for (int i = 0; i < list.size(); i++) {
			Object[] objtb = list.get(i);
			int days = getDaysByYearMonth(list.get(i)[0].toString().substring(0,4),list.get(i)[0].toString().substring(5,7));
			objtb[2] =  (Long) list.get(i)[2] / days;
		}
		
		return list;
	}

	 /** 
     * 根据年 月 获取对应的月份 天数 
     * */  
    public static int getDaysByYearMonth(String year, String month) {  
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, Integer.valueOf(year));  
        a.set(Calendar.MONTH, Integer.valueOf(month) - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }  
    
    

	public List<Object[]> getSeriesDataByJDBC(String sql) throws SQLException {
		
		List<Object[]> result = new ArrayList<Object[]>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = SqlJdbcConnection.getConnection("southbasee.ms");
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs!=null){
				
				while(rs.next()){
					Object[] obj = new Object[3];
					obj[0] = isNull(rs.getString("month_str"));
					obj[1] = isNull(rs.getString("name"));
					obj[2] = rs.getLong("value");
					result.add(obj);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException("查询出错,详细信息:"+ e.getMessage());
		}finally{
			if(rs != null)rs.close();
			if(ps != null)ps.close();
			if(conn != null)conn.close();
		}
		return result;
	}
	
	/**
	 * String类型是否为空处理
	 * @date 2016-6-18 上午10:41:11
	 * @param str
	 * @return	String
	 */
	private String isNull(String str){
		if (str == null) {
			return null;
		}
		else if (str.trim().equals("")){
			return null;
		}else {
			return ""+str.trim()+"";
		}
	}
	public List<Object[]> getSeriesDataByMyJDBC(String sql) throws SQLException {
		
		List<Object[]> result = new ArrayList<Object[]>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = MysqlJdbcConnection.getConnection("zsnj.link");
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs!=null){
				
				while(rs.next()){
					Object[] obj = new Object[3];
					obj[0] = isNull(rs.getString("month_str"));
					obj[1] = "用户数";
					obj[2] = rs.getLong("value");
					result.add(obj);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException("查询出错,详细信息:"+ e.getMessage());
		}finally{
			if(rs != null)rs.close();
			if(ps != null)ps.close();
			if(conn != null)conn.close();
		}
		return result;
	}
	
}
