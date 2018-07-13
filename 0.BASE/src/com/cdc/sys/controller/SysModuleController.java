package com.cdc.sys.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysModuleForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysModuleService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 模块管理__管理模块
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/module/*")
public class SysModuleController extends CommonController {
	private Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ISysModuleService moduleService;

	@Autowired
	private ISysLogService sysLogService;

	/**
	 * 管理所有模块
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "manageSysModule", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String manageSysModule(HttpServletRequest request, SysModuleForm form) {
		try {
			ItemPage itemPage = moduleService.querySysModule(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/module/manageSysModule";
	}
	
	
	/**
	 * 编辑模块页面
	 */
	@RequestMapping(value = "toModifySysModule/{moduleId}", method = RequestMethod.GET)
	public String toModifySysModule(HttpServletRequest request,
			@PathVariable String moduleId) {
		try {
			SysModule sysModule = moduleService.getSysModuleById(moduleId);
			request.setAttribute("sysModule", sysModule);
			request.setAttribute("modules", moduleService.queryAll());
			request.setAttribute("location", "sys/module/manageSysModule");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/module/updateModule";
	}

	/**
	 * 修改模块
	 * @param sysModule
	 * @return
	 */
	@RequestMapping(value = "modifySysModule", method = RequestMethod.POST)
	public String doModifySysModule(HttpServletRequest request,
			SysModule sysModule, String isRoot) {
		try {
			if (isRoot.equals("1"))
				sysModule.setParentCode("ROOT");
			moduleService.modifySysModule(sysModule);
			sysLogService.log(request, getVisitor(request), "系统管理--模块管理",
					"编辑模块", "修改【" + sysModule.getModuleName() + "】模块", new Date(), "3", new Date(), null);
			
			moduleService.loadModule();
			} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/module/manageSysModule";
	}
	
	/**
	 * 查看模块页面
	 * @return
	 */
	@RequestMapping(value = "detailSysModuleById/{moduleId}", method = RequestMethod.GET)
	public String detailSysModuleById(HttpServletRequest request,
			@PathVariable String moduleId) {
		try {
			SysModule sysModule = moduleService.getSysModuleById(moduleId);
			request.setAttribute("sysModule", sysModule);
			request.setAttribute("modules", moduleService.queryAll());
			request.setAttribute("location", "sys/module/manageSysModule");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/module/detailSysModule";
	}
	/**
	 * 从查询模块页面进入
	 * @param request
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(value = "detailSysModuleByIdAdd/{moduleId}", method = RequestMethod.GET)
	public String detailSysModuleByIdAdd(HttpServletRequest request,
			@PathVariable String moduleId) {
		try {
			SysModule sysModule = moduleService.getSysModuleById(moduleId);
			request.setAttribute("sysModule", sysModule);
			request.setAttribute("modules", moduleService.queryAll());
			request.setAttribute("location", "sys/module/querySysModule");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/module/detailSysModule";
	}
	
	/**
	 * 批量删除模块
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(value = "deleteSysModule", method = RequestMethod.POST)
	public String doDeleteSysModule(HttpServletRequest request,String moduleIds) {
			if (null != moduleIds && !"".equals(moduleIds)) {
				String[] ids = moduleIds.split(",");
				String deleteName = "";
					try {
						for (String moduleId : ids) {
							SysModule sysModule = moduleService.getSysModuleById(moduleId);
							moduleService.deleteSysModule(moduleId);
							deleteName += sysModule.getModuleName() + "，";
						}
						sysLogService.log(request, getVisitor(request), "系统管理--模块管理", "删除模块",
								"删除模块【" + deleteName.substring(0,deleteName.length()-1)+ "】", new Date(), "3", new Date(), null);
						
						moduleService.loadModule();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		return "redirect:/sys/module/manageSysModule";
	}
	
	/**
	 * 单个删除模块
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(value = "deleteSysModuleById/{moduleId}", method = RequestMethod.GET)
	public String deleteSysModuleById(HttpServletRequest request,@PathVariable String moduleId) {
		  if (null != moduleId && !"".equals(moduleId)) {
				try {
					SysModule sysModule = moduleService.getSysModuleById(moduleId);
					moduleService.deleteSysModule(moduleId);
					sysLogService.log(request, getVisitor(request), "系统管理-模块管理", "删除模块",
							"删除模块【" + sysModule.getModuleName()+ "】", new Date(), "3", new Date(), null);
					
					moduleService.loadModule();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return "redirect:/sys/module/manageSysModule";
	}
	/**
	 * 验证模块编码的唯一
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checknameuniqueness", method = RequestMethod.POST)
	public @ResponseBody String checkNameUniqueness(HttpServletRequest request,HttpServletResponse response,String moduleCode){
		SysModule module=null;
		try
		{
			module = moduleService.queryModuleByName(moduleCode);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
//		if(module==null)
//			return "0";
//		else
//			return "1";
		return "0";
	}
}