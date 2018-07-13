package com.cdc.sys.controller;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;
import com.cdc.sys.form.SysModuleForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysModuleService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 模块管理__查询模块
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/module/*")
public class SysSearchModuleController extends DefaultController {

	@Autowired
	private ISysModuleService moduleService;

	@Autowired
	private ISysLogService sysLogService;

	
	/**
	 * 查询所有模块
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "querySysModule", method = { RequestMethod.GET,RequestMethod.POST })
	public String querySysModule(HttpServletRequest request, SysModuleForm form) {
		try {
			ItemPage itemPage = moduleService.querySysModule(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/module/querySysModule";
	}

	
}