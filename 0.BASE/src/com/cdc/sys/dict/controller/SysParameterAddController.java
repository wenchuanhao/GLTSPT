package com.cdc.sys.dict.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.service.ISysParameterService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;

/**
 * 
 * @Description: 新增参数
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/parameter/*")
public class SysParameterAddController extends CommonController{
	private Log logger = LogFactory.getLog(getClass());

	@Autowired
	private ISysParameterService parameterService;
	
	@Autowired
	private ISysLogService sysLogService;
	/**
	 * 查询所有参数
	 * @param request
	 * @param form
	 * @return
	 */

	/**
	 * 新增参数页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addParameter", method = RequestMethod.GET)
	public String toAddParameter(HttpServletRequest request) {
		try {
			request.setAttribute("paramList",
					parameterService.queryParameterType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/parameter/addParameter";

	}
	/**
	 * 新增参数
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "addParameter", method = RequestMethod.POST)
	public String doAddParameter(HttpServletRequest request,SysParameter parameter) {
		try {
			parameterService.addParameter(parameter);
			sysLogService.log(request, getVisitor(request), "系统管理--系统参数",
					"新增参数", "添加【" + parameter.getParameterName() + "】参数", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/parameter/queryParameter";

	}
	
	/**
	 * 验证用户参数名称的唯一
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checknameuniquenessadd", method = RequestMethod.POST)
	public @ResponseBody String checkNameUniqueness(HttpServletRequest request,HttpServletResponse response,String parameterName){
		SysParameter parameter=null;
		try
		{
			parameter = parameterService.queryParameterByName(parameterName);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if(parameter==null)
			return "0";
		else
			return "1";
	}
}
