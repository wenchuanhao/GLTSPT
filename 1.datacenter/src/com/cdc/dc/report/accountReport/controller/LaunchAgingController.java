package com.cdc.dc.report.accountReport.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.cdc.dc.report.accountReport.common.Common;
import com.cdc.dc.report.accountReport.service.ILaungchAgingService;

/**
 * 
 * @author xms
 * 发起报账时效分析
 */
@Controller
@RequestMapping(value="account/")
@SuppressWarnings("unused")
public class LaunchAgingController {
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	DateFormat dateYear = new SimpleDateFormat("yyyy");
	
	@Autowired
	private ILaungchAgingService laungchAgingService; 
	
	@RequestMapping(value="launchAging" , method = {RequestMethod.POST,RequestMethod.GET})
	public String launchAging(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
		request.setAttribute("type", "1");
		String linkName = new String(request.getParameter("linkName").getBytes("iso-8859-1"),"UTF-8");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(linkName==null){
			//linkName = "1";
			linkName = "3";
		}
		if(startTime==null){
			startTime = "201704";
		}
		if(endTime==null){
			endTime = "201806";
		}

		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("linkName", getLinkNameId(linkName));
		request.setAttribute("linkNames", linkName);
		return "dc/report/accountReport/launchAgingAccount";
	}
	
	@RequestMapping(value="getAgingData" , method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void getData(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
		String type = request.getParameter("type");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String linkName = request.getParameter("linkName");
		if(type==null){
			type="部门";
		}
		request.setAttribute("type", type);
		List list= laungchAgingService.queryLinkAging(linkName, startTime, endTime,type);
		JSONArray userListJSONArray = JSONArray.fromObject(list);
        String result = userListJSONArray.toString();
		PrintWriter out = response.getWriter();
		out.write(result);	
		out.close();
	}
	
	@RequestMapping(value="getClumnData" , method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void getClumnData(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String type = request.getParameter("type");//type:1部门  type:2费用  type:3问题类型
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String linkName = request.getParameter("linkName");
		String agingType = request.getParameter("agingType");
		if(type==null){
			type="部门";
		}
		request.setAttribute("type", type);
		//时效同比、环比分析
		getAgingYearOnYear(startTime, endTime, linkName, type, agingType,  response);
	}
	
	
	@RequestMapping(value="getChartData" , method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void getChartData(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String type = request.getParameter("type");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String linkName = request.getParameter("linkName");
		String ddString = getLinkName(linkName);
		int num = 5;
		List<Object[]> list = new ArrayList<Object[]>();
		JSONObject result = new JSONObject();
		if(type.equals("container02_bumen")){//部门同比
			list = laungchAgingService.queryTHbi(linkName, startTime,endTime, "部门",2,num);
			String str = "周期|部门名称|"+getLinkName(linkName).toString()+"时效|同比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container03_bumen")){//部门环比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "部门",1,num);
			String str = "周期|部门名称|"+getLinkName(linkName)+"时效|环比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container02_feiyong")){//费用同比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "费用",2,num);
			String str = "周期|费用名称|"+getLinkName(linkName)+"时效|同比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container03_feiyong")){//费用环比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "费用",1,num);
			String str = "周期|费用名称|"+getLinkName(linkName)+"时效|环比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container02_wentiType")){//问题类型同比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "问题类型",2,num);
			String str = "周期|问题类型名称|"+getLinkName(linkName)+"时效|同比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container03_wentiType")){//问题类型环比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "问题类型",1,num);
			String str = "周期|问题类型名称|"+getLinkName(linkName)+"时效|环比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		/*问题整改环节多出3个占比图*/
		if(type.equals("container05_bumen")){//部门问题整改占比环节-同比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "部门",2,num);
			String str = "周期|部门名称|"+getLinkName(linkName)+"占比|同比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container06_bumen")){//部门问题整改占比环节-环比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "部门",1,num);
			String str = "周期|部门名称|"+getLinkName(linkName)+"占比|环比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container05_feiyong")){//费用问题整改占比环节-同比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "费用",2,num);
			String str = "周期|费用名称|"+getLinkName(linkName)+"占比|同比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container06_feiyong")){//费用问题整改占比环节-环比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "费用",1,num);
			String str = "周期|费用名称|"+getLinkName(linkName)+"占比|环比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container05_wentiType")){//问题类型问题整改占比环节-同比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "问题类型",2,num);
			String str = "周期|问题类型名称|"+getLinkName(linkName)+"占比|同比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}else
		if(type.equals("container06_wentiType")){//问题类型问题整改占比环节-环比
			list = laungchAgingService.queryTHbi(linkName, startTime, endTime, "问题类型",1,num);
			String str = "周期|问题类型名称|"+getLinkName(linkName)+"占比|环比";
			result.put("head", str.split("\\|"));
			result.put("list", list);
		}
		PrintWriter out = response.getWriter();
		out.write(result.toString());
		out.close();
	}
	
	//时效同比分析
	private void getAgingYearOnYear(String startDate, String endDate, String link,
			String type, String agingType, HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();
		List<Object[]> list = laungchAgingService.queryTHbi(link, startDate, endDate, type,Integer.parseInt(agingType),6);
		List<Object> container01xAxis = new ArrayList<Object>();
		List<Object> container01series = new ArrayList<Object>();
		List<Object> container0103 = new ArrayList<Object>();
		String container01seriesName = "";

		if(null!=list && list.size()>0){
			for(int i = 0; i < list.size(); i++){
				Object[] objectArr = list.get(i);
				container01xAxis.add(objectArr[0]);//周期
				container01series.add(objectArr[2]);//维度名称
				container0103.add(objectArr[5]);//平均值
			}
			container01seriesName = (String) ((Object[])list.get(0))[2];
		}
		//去重
		for ( int i = 0 ; i < container01series.size() - 1 ; i ++ ) { 
			 for ( int j = container01series.size() - 1 ; j > i; j -- ) { 
				 if (container01series.get(j).equals(container01series.get(i))) { 
					 container01series.remove(j); 
				 } 
			 } 
		} 
		//去重
		for ( int i = 0 ; i < container01xAxis.size() - 1 ; i ++ ) { 
			 for ( int j = container01xAxis.size() - 1 ; j > i; j -- ) { 
				 if (container01xAxis.get(j).equals(container01xAxis.get(i))) { 
					 container01xAxis.remove(j); 
				 } 
			 } 
		} 

		//开始时间，结束时间
		result.put("container01Time", startDate+"-"+endDate);
		//同比/环比周期列表
		result.put("container01xAxis",JSONArray.fromObject(container01xAxis));
		//部门/费用/问题类型 三种维度
		result.put("container01series",JSONArray.fromObject(container01series));
		//无作用
		result.put("container01seriesName", container01seriesName);
		result.put("container0103", container0103);
		result.put("container01Link", link);//报账环节名称
		result.put("container01List", list);//结果集
		PrintWriter out = response.getWriter();
		out.write(result.toString());
		out.close();
	}
	
	private static String getLinkName(String link){
		if(StringUtils.isNoneBlank(link)){
			if(link.equals(Common.BZJF_LING_FQBZ)){
				return "发起报账时间";
			} else if(link.equals(Common.BZJF_LING_YWSP)){
				return "业务审批时间";
			} else if(link.equals(Common.BZJF_LING_WTDJZG)){
				return "问题单据整改时间";
			} else if(link.equals(Common.BZJF_LING_NJCWSP)){
				return "南基财务审批";
			} else if(link.equals(Common.BZJF_LING_JDLDSP)){
				return "基地领导审批";
			} else if(link.equals(Common.BZJF_LING_SCWSP)){
				return "省财务审批";
			} else if(link.equals(Common.BZJF_LING_CNZF)){
				return "出纳支付";
			}
		}
		return null;
	}
	
	private static int getLinkNameId(String linkName){
		if(StringUtils.isNoneBlank(linkName)){
			if(linkName.equals("发起报账时间")){
				return 1;
			} else if(linkName.equals("业务审批时间")){
				return 2;
			} else if(linkName.equals("问题单据整改时间")){
				return 3;
			} else if(linkName.equals("南基财务审批")){
				return 4;
			} else if(linkName.equals("基地领导审批")){
				return 5;
			} else if(linkName.equals("省财务审批")){
				return 6;
			} else if(linkName.equals("出纳支付")){
				return 7;
			}
		}
		return 1;
	}
}
