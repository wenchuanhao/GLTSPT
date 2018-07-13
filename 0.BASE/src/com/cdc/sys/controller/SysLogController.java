package com.cdc.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysLogForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 系统日志管理
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/log/*")
public class SysLogController extends DefaultController {
	@Autowired
	private ISysLogService sysLogService;

	@RequestMapping(value = "querySysLog", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryLog(HttpServletRequest request, SysLogForm form) {
		try {
			ItemPage itemPage = sysLogService.query(form,"");
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			request.setAttribute("form", form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/log/querySysLog";
	}

	
	/**
	 * 用户异常日记
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "exceptionLog", method = { RequestMethod.GET, RequestMethod.POST })
	public String exceptionLog(HttpServletRequest request, SysLogForm form) {
		try {
			ItemPage itemPage = sysLogService.query(form,"2");
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			request.setAttribute("form", form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/log/queryExceptionLog";
	}
	
	
}
