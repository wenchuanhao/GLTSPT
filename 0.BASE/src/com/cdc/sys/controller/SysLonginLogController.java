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

@Controller
@RequestMapping(value = "/sys/log/*")
public class SysLonginLogController extends DefaultController{
	@Autowired
	private ISysLogService sysLogService;
	/**
	 * 用户登录日记
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "loginLog", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginLog(HttpServletRequest request, SysLogForm form) {
		try {
			ItemPage itemPage = sysLogService.query(form,"1");
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			request.setAttribute("form", form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/log/queryLoginLog";
	}
}
