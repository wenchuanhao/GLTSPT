package com.cdc.sys.smsManage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;


import com.cdc.sys.smsManage.form.SmsManageLogForm;
import com.cdc.sys.smsManage.service.ISmsManageLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 短信发送日志
 * @UpdateUser: 
 * @UpdateDate: 2016-10-10 下午04:45:05
 */
@Controller
@RequestMapping(value = "/sys/sms/")
public class SysManageLogController extends CommonController {
	@Autowired
	private  ISmsManageLogService smsManageLogService;

	@RequestMapping(value = "smsManageLog", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryLog(HttpServletRequest request, SmsManageLogForm form) {
	
			ItemPage itemPage = smsManageLogService.querySmsManageLog(form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			request.setAttribute("form", form);
		
		return "sys/smsManage/SmsManageLog";
	}

}
