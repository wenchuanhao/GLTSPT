package com.cdc.dc.divsion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.cdc.dc.divsion.service.IDivsionElecService;

/**
 * 行政经分天然气统计
 * @author lxl
 * @date 2016-10-21
 */
@Controller
@RequestMapping(value = "/divsionGas/")
public class DivsionGasController {
	
	@Autowired
	private IDivsionElecService  divsionService;
	
	/**
	 * 天然气首页
	 */
	@RequestMapping(value="home", method = {RequestMethod.POST,RequestMethod.GET})
	public String electric(HttpServletRequest request) {
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
		return "/dc/divsion/gas/gashome";
	}
	
	/**
	 * 天然气二级页面
	 */
	@RequestMapping(value="gasSnd", method = {RequestMethod.POST,RequestMethod.GET})
	public String gasSnd(HttpServletRequest request) {
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
		return "/dc/divsion/gas/gasSnd";
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
		String name = request.getParameter("name");
		JSONArray result = null;
		List<Object[]> list = null;
		String sql = "";
		try{

			//一级页面查询
			if(type.indexOf("container0") == 0){
				if("container01".equals(type)){
					sql = "select year||'-'||month,name,value from division_result where type='3' and year='"+year+"'";
				}else if("container02".equals(type)){
					sql = "select name,sum(value) from division_result where type='3' and year='"+year+"' group by name";
				}
			}else if(type.indexOf("sndlevel01") == 0){
				if("sndlevel0104".equals(type)){
					sql = "select year||'-'||month month_str,name,value from division_result where type='3' and name='"+name+"' and year='"+year+"'";
				}else if("sndlevel0102".equals(type)){
					sql = "　select t.month_str,cast('环比' as varchar2(6)) name,case when nvl(lag(value) over(order by t.month_str),0) != 0 then nvl(round(value/lag(value) over(order by t.month_str)*100,2)-100,'0') else 0 end value from  " +
							"　(select year||'-'||month month_str,value from division_result where type='3' and (year='"+(Integer.valueOf(year)-1)+"' or year='"+year+"')　and name='"+name+"' order by month_str) t " +
							" union all " +
							" select t.year||'-'||t.month month_str,cast('同比' as varchar2(6)) name,case when nvl(lag(value) over(partition by month order by year),0) != 0 then nvl(round(value/lag(value) over(partition by month order by year)*100,2)-100,'0') else 0 end value from " +
							" (select year,month,value from division_result where type='3' and (year='"+(Integer.valueOf(year)-1)+"'  or year='"+year+"')  and name='"+name+"' order by month) t";
				}
			}
			
			list = divsionService.getSeriesDataByType(sql);
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
	 * @throws Exception
	 */
	@RequestMapping(value = "getChartData", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void getChartData(HttpServletRequest request,HttpServletResponse response){
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
		//判空
		if(type == null){
			type = "";
		}
		//一级页面查询
		if("tbodycontainer01".equals(type)){
			sql = "select year||'-'||month month_str,name,value from division_result where type='3' and year='"+year+"'";
			result.put("head", "区域|1月|2月|3月|4月|5月|6月|7月|8月|9月|10月|11月|12月|总计".split("\\|"));
		}else if("tbodycontainer02".equals(type)){
			sql = "select name,sum(value) from division_result where type='3' and year='"+year+"' group by name";
			result.put("head", "系统名称|用气量|占比".split("\\|"));
		}else if(type.indexOf("tbodycontainer01_") == 0){
			if("tbodycontainer01_04".equals(type)){
				sql = "select year||'-'||month month_str,name,value from division_result where type='3' and name='"+name+"' and year='"+year+"' order by year||'-'||month";
				result.put("head", "区域|1月|2月|3月|4月|5月|6月|7月|8月|9月|10月|11月|12月|总计".split("\\|"));
				
			}else if("tbodycontainer01_02".equals(type)){
				sql = "　select t.month_str,cast('环比' as varchar2(6)) name,case when nvl(lag(value) over(order by t.month_str),0) != 0 then nvl(round(value/lag(value) over(order by t.month_str)*100,2)-100,'0') || '%' else '-' end value from  " +
						"　(select year||'-'||month month_str,sum(value) value from division_result where type='3' and (year='"+(Integer.valueOf(year)-1)+"' or year='"+year+"')　and name='"+name+"' group by year,month order by month_str) t " +
						" union all " +
						" select t.year||'-'||t.month month_str,cast('同比' as varchar2(6)) name,case when nvl(lag(value) over(order by t.month),0) != 0 then nvl(round(value/lag(value) over(partition by month order by year)*100,2)-100,'0') || '%' else '-' end value from " +
						" (select year,month,sum(value) value from division_result where type='3' and (year='"+(Integer.valueOf(year)-1)+"'  or year='"+year+"')  and name='"+name+"' group by year,month order by month) t";
				result.put("head", "月份|1月|2月|3月|4月|5月|6月|7月|8月|9月|10月|11月|12月".split("\\|"));
			}
		}
		list = divsionService.getSeriesDataByType(sql);
		result.put("list", list);
		try {
			PrintWriter out = response.getWriter();
			out.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
}
