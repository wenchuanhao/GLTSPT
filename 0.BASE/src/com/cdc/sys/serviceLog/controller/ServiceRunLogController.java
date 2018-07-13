package com.cdc.sys.serviceLog.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.serviceLog.form.ServiceRunLogForm;
import com.cdc.sys.serviceLog.service.IServiceRunLogService;
import com.trustel.common.ItemPage;

@Controller
@RequestMapping(value="/sys/log/")
public class ServiceRunLogController {
	
	@Autowired 	
	private IServiceRunLogService logService;
	
	//日志首页
	@RequestMapping(value="queryServiceRunLog",method={RequestMethod.GET,RequestMethod.POST})
	public String queryServiceRunLog(HttpServletRequest request,ServiceRunLogForm logForm,String types,String interTypes){
		request.setAttribute("logForm", logForm);
		request.setAttribute("types", types);
		request.setAttribute("interTypes", interTypes);
		
		ItemPage itemPage = logService.queryServiceLogPage(logForm,types,interTypes);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		return "sys/serviceLog/serviceRunLog";
	}
	
	//删除日志
	@RequestMapping(value="delServiceRunLog",method={RequestMethod.POST,RequestMethod.GET})
	public String delServiceRunLog(String ids){
		logService.delServiceLog(ids);
		return "redirect:/sys/log/queryServiceRunLog";
	}
}
