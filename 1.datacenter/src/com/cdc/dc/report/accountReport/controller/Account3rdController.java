package com.cdc.dc.report.accountReport.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cdc.dc.report.accountReport.common.Common;
import com.cdc.dc.report.accountReport.service.ILaungchAgingService;

/**
 * 报账经分三级页面
 * @author lxl
 * @date 2016-6-23
 */
@Controller
@RequestMapping(value = "/Account3rdController/")
public class Account3rdController {
	
	@Autowired
	private ILaungchAgingService laungchAgingService;

	@RequestMapping(value = "toPage", method = { RequestMethod.POST,RequestMethod.GET })
	public String toPage(HttpServletRequest request) throws UnsupportedEncodingException {
		String linkName = new String(request.getParameter("linkName").getBytes("iso-8859-1"),"UTF-8");
		String bsnType = new String(request.getParameter("bsnType").getBytes("iso-8859-1"),"UTF-8");
		String startDate = request.getParameter("startTime");
		String endDate = request.getParameter("endTime");
		String type = new String(request.getParameter("type").getBytes("iso-8859-1"),"UTF-8");
		String dimensionKey = "";
		String dimensionValue = "";
		if(type == null || type.equals("部门") || type.equals("")){
			dimensionKey = "department";
			dimensionValue = laungchAgingService.getDepIdByName(bsnType);
		}else if(type.equals("费用")){
			List<Object[]> list = laungchAgingService.getCostIdByName(bsnType);
			if(list!=null && list.size()>0){
				for (Object[] obj : list) {
					if(obj[1].toString().equals("ROOT")){
						dimensionKey = "parent_cos";
					}else {
						dimensionKey = "cos";
					}
					dimensionValue = obj[0].toString();
				}
			}
			
		}else if(type.equals("问题类型")){
			dimensionKey = "problem";
			dimensionValue = laungchAgingService.getProTypeByName(bsnType);
		}
	
		//时效同比分析
		getAgingYearOnYear(startDate, endDate, linkName, dimensionKey, dimensionValue, request);
		//时效环比分析
		getAgingMonthOnMonth(startDate, endDate, linkName, dimensionKey, dimensionValue, request);
		
		//如果是问题整改环节，则再多问题单据占比的两个图表。
		if(linkName.equals(Common.BZJF_LING_WTDJZG)){
			//问题单据占比-同比分析
			getProblemOrderYearOnYear(startDate, endDate, linkName, dimensionKey, dimensionValue, request);
			//问题单据占比-环比分析
			getProblemOrderMonthOnMonth(startDate, endDate, linkName, dimensionKey, dimensionValue, request);
		}
		
		request.setAttribute("link", linkName);
		request.setAttribute("dimensionKey", dimensionKey);
		
		return "dc/report/accountReport/Account3rd";
	}
	
	//时效同比分析
	private void getAgingYearOnYear(String startDate, String endDate, String link,
			String dimensionKey, String dimensionValue, HttpServletRequest request) {
		List<Object[]> list = laungchAgingService.getAgingData(startDate, endDate,
				link, dimensionKey, dimensionValue, Common.BZJF_COMPARE_YOY);
		List<Object> container01xAxis = new ArrayList<Object>();
		List<Object> container01series = new ArrayList<Object>();
		String container01seriesName = "";
		
		if(null!=list && list.size()>0){
			for(int i = 0; i < list.size(); i++){
				Object[] objectArr = list.get(i);
				container01xAxis.add(objectArr[0]);
				container01series.add(objectArr[5]);
			}
			container01seriesName = (String) ((Object[])list.get(0))[2];
		}
		
		request.setAttribute("container01Time", startDate+"-"+endDate);
		request.setAttribute("container01xAxis",JSONArray.fromObject(container01xAxis));
		request.setAttribute("container01series",JSONArray.fromObject(container01series));
		request.setAttribute("container01seriesName", container01seriesName);
		request.setAttribute("container01Link", getLinkName(link));
		request.setAttribute("container01List", list);
		if(!link.equals(Common.BZJF_LING_WTDJZG)) {
			request.setAttribute("container04xAxis",JSONArray.fromObject(JSONArray.fromObject(container01xAxis)));
			request.setAttribute("container03xAxis",JSONArray.fromObject(JSONArray.fromObject(container01xAxis)));
			request.setAttribute("container03series",JSONArray.fromObject(container01series));
			request.setAttribute("container04series",JSONArray.fromObject(container01series));
		}
	}
	
	//时效环比分析
	private void getAgingMonthOnMonth(String startDate, String endDate, String link,
			String dimensionKey, String dimensionValue, HttpServletRequest request){
		List<Object[]> list = laungchAgingService.getAgingData(startDate, endDate,
				link, dimensionKey, dimensionValue, Common.BZJF_COMPARE_MOM);
		List<Object> container01xAxis = new ArrayList<Object>();
		List<Object> container01series = new ArrayList<Object>();
		String container02seriesName = "";
		
		if(null!=list && list.size()>0){
			for(int i = 0; i < list.size(); i++){
				Object[] objectArr = list.get(i);
				container01xAxis.add(objectArr[0]);
				container01series.add(objectArr[5]);
			}
			
			container02seriesName = (String) ((Object[])list.get(0))[2];
		}
		
		request.setAttribute("container02Time", startDate+"-"+endDate);
		request.setAttribute("container02xAxis",JSONArray.fromObject(container01xAxis));
		request.setAttribute("container02series",JSONArray.fromObject(container01series));
		request.setAttribute("container02seriesName", container02seriesName);
		request.setAttribute("container02Link", getLinkName(link));
		request.setAttribute("container02List", list);
		
	}
	
	//问题单据占比-同比分析
	private void getProblemOrderYearOnYear(String startDate, String endDate, String link,
			String dimensionKey, String dimensionValue, HttpServletRequest request){
		List<Object[]> list = laungchAgingService.getProblemOrderData(startDate, endDate,
				link, dimensionKey, dimensionValue, Common.BZJF_COMPARE_YOY);
		List<Object> container03xAxis = new ArrayList<Object>();
		List<Object> container03series = new ArrayList<Object>();
		String container03seriesName = "";
		
		if(null!=list && list.size()>0){
			for(int i = 0; i < list.size(); i++){
				Object[] objectArr = list.get(i);
				container03xAxis.add(objectArr[0]);
				container03series.add(objectArr[6]);
			}
			
			container03seriesName = (String) ((Object[])list.get(0))[2];
		}
		
		request.setAttribute("container03Time", startDate+"-"+endDate);
		request.setAttribute("container03xAxis",JSONArray.fromObject(container03xAxis));
		request.setAttribute("container03series",JSONArray.fromObject(container03series));
		request.setAttribute("container03seriesName", container03seriesName);
		request.setAttribute("container03List", list);
	}
	
	//问题单据占比-环比分析
	private void getProblemOrderMonthOnMonth(String startDate, String endDate, String link,
			String dimensionKey, String dimensionValue, HttpServletRequest request){
		List<Object[]> list = laungchAgingService.getProblemOrderData(startDate, endDate,
				link, dimensionKey, dimensionValue, Common.BZJF_COMPARE_MOM);
		List<Object> container04xAxis = new ArrayList<Object>();
		List<Object> container04series = new ArrayList<Object>();
		String container04seriesName = "";
		
		if(null!=list && list.size()>0){
			for(int i = 0; i < list.size(); i++){
				Object[] objectArr = list.get(i);
				container04xAxis.add(objectArr[0]);
				container04series.add(objectArr[6]);
			}
			
			container04seriesName = (String) ((Object[])list.get(0))[2];
		}
		
		request.setAttribute("container04Time", startDate+"-"+endDate);
		request.setAttribute("container04xAxis",JSONArray.fromObject(container04xAxis));
		request.setAttribute("container04series",JSONArray.fromObject(container04series));
		request.setAttribute("container04seriesName", container04seriesName);
		request.setAttribute("container04List", list);
	}
	
	
	private static String getLinkName(String link){
		if(StringUtils.isNoneBlank(link)){
			if(link.equals(Common.BZJF_LING_FQBZ)){
				return "发起报账";
			} else if(link.equals(Common.BZJF_LING_YWSP)){
				return "业务审批";
			} else if(link.equals(Common.BZJF_LING_WTDJZG)){
				return "问题单据整改";
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
			if(linkName.equals("发起报账")){
				return 1;
			} else if(linkName.equals("业务审批")){
				return 2;
			} else if(linkName.equals("问题单据整改")){
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
