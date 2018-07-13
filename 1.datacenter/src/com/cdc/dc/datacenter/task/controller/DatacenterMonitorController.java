package com.cdc.dc.datacenter.task.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.datacenter.task.form.DatacenterForm;
import com.cdc.dc.datacenter.task.service.IDatacenterService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 数据监控
 * @author lxl
 * @date 2016-9-26
 */
@Controller
@RequestMapping(value = "/datacenterMonitorController/")
public class DatacenterMonitorController extends CommonController{
	
	@Autowired
	private IDatacenterService datacenterService;

	@RequestMapping(value="queryDcMonitor", method = {RequestMethod.POST,RequestMethod.GET})
	public String queryDcMonitor(HttpServletRequest request,HttpServletResponse response,DatacenterForm datacenterForm){
		ItemPage itemPage = datacenterService.getDcMonitorList(datacenterForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		request.setAttribute("datacenterImpForm", datacenterForm);
		return "/dc/datacenter/datacenterMonitorList";
	}
}
