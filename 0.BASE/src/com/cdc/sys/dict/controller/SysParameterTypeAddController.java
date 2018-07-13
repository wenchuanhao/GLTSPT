package com.cdc.sys.dict.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.sys.dict.model.SysParameterType;
import com.cdc.sys.dict.service.ISysParameterService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;

/**
 * 
 * @Description: 新增参数类型
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/paramtype/*")
public class SysParameterTypeAddController extends CommonController{

	@Autowired
	private ISysParameterService parameterService;
	
	@Autowired
	private ISysLogService sysLogService;

	/**
	 * 新增参数类型页面
	 * @return
	 */
	@RequestMapping(value = "addParameterType", method = RequestMethod.GET)
	public String toAddParameterType() {
		return "sys/paramtype/addParamType";

	}
	/**
	 * 新增参数类型
	 * @param request
	 * @param parameterType
	 * @return
	 */
	@RequestMapping(value = "addParameterType", method = RequestMethod.POST)
	public String doAddParameterType(HttpServletRequest request,SysParameterType parameterType) {
		try {
			parameterType.setCreateTime(new Date());
			parameterType.setCreaterId(getVisitor(request).getAccount());
			parameterService.addParameterType(parameterType);
			sysLogService.log(request, getVisitor(request), "系统管理--系统参数",
					"新增参数类型", "添加了【" + parameterType.getParameterTypeName() + "】参数类型", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/paramtype/queryParameterType";

	}

	/**
	 * 验证用户参数类型名称的唯一
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checknameuniquenessadd", method = RequestMethod.POST)
	public @ResponseBody String checkNameUniqueness(HttpServletRequest request,HttpServletResponse response,String parameterTypeName){
		SysParameterType parameterType=null;
		try
		{
			parameterType = parameterService.queryParameterTypeByName(parameterTypeName);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if(parameterType==null)
			return "0";
		else
			return "1";
	}
}
