package com.cdc.sys.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import model.sys.entity.SysModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysModuleService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.authentication.controller.DefaultController;

/**
 * 
 * @Description: 模块管理__新增模块
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/module/*")
public class SysAddModuleController extends CommonController {

	@Autowired
	private ISysModuleService moduleService;

	@Autowired
	private ISysLogService sysLogService;

	

	/**
	 * 添加模块页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addSysModule", method = RequestMethod.GET)
	public String toAddSysModule(HttpServletRequest request) {
		try {
			request.setAttribute("modules", moduleService.queryAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/module/addSysModule";
	}

	/**
	 * 新增模块
	 * @param sysModule
	 * @param isRoot
	 * @return
	 */

	@RequestMapping(value = "addSysModule", method = RequestMethod.POST)
	public String doAddSysModule(HttpServletRequest request,
			SysModule sysModule, String isRoot) {
		try {
			if (isRoot.equals("1"))
				sysModule.setParentCode("ROOT");
				moduleService.addSysModule(sysModule);
				sysLogService.log(request, getVisitor(request), "系统管理--模块管理",
						"新增模块", "添加【" + sysModule.getModuleName() + "】模块", new Date(), "3", new Date(), null);
				moduleService.loadModule();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/module/manageSysModule";
	}

}