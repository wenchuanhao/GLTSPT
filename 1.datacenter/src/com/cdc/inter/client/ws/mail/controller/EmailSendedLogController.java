package com.cdc.inter.client.ws.mail.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.inter.client.ws.mail.form.InterfaceMailinfoRecordsForm;
import com.cdc.inter.client.ws.mail.service.IEmailSendedLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 邮件发送历史查询
 * @UpdateUser: 
 * @UpdateDate: 2016-10-17 上午11:57:20
 */
@Controller
@RequestMapping(value="/sys/log/")
public class EmailSendedLogController extends CommonController {
	@Autowired
	private IEmailSendedLogService emailSendedLogService;
	
	@RequestMapping(value = "emailSendedLog", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryLog(HttpServletRequest request,InterfaceMailinfoRecordsForm form){
		ItemPage itemPage = emailSendedLogService.queryEmailSendedLog(form);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		request.setAttribute("form", form);
		
		return "sys/log/emailSendedLog";
	}
}
