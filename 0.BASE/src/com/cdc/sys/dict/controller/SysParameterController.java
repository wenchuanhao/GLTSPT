package com.cdc.sys.dict.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.sys.dict.form.SysParameterForm;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.service.ISysParameterService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 参数管理
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/parameter/*")
public class SysParameterController extends CommonController{
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
	@RequestMapping(value = "queryParameter", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queryParameter(HttpServletRequest request, SysParameterForm form) {
		try {
			ItemPage itemPage = parameterService.queryParameter(form);
			
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/parameter/queryParameter";
	}

	/**
	 * 查看参数页面
	 * @param request
	 * @param ParId
	 * @return
	 */
	@RequestMapping(value = "parameterDetail/{parId}", method = RequestMethod.GET)
	public String parameterTypeDetail(HttpServletRequest request, @PathVariable String parId) {
		try {
			request.setAttribute("paramList",
					parameterService.queryParameterType());
			request.setAttribute("parameter", parameterService.queryParameter(parId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("location", "sys/parameter/queryParameter");
		return "sys/parameter/detailParameter";
	}
	
	/**
	 * 编辑参数页面
	 * @param request
	 * @param ParId
	 * @return
	 */
	@RequestMapping(value = "toModifyParameter/{ParId}", method = RequestMethod.GET)
	public String toModifyParameter(HttpServletRequest request,
			@PathVariable String ParId) {
		try {
			request.setAttribute("paramList",
					parameterService.queryParameterType());
			request.setAttribute("parameter", parameterService.queryParameter(ParId));
			request.setAttribute("location", "sys/parameter/queryParameter");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/parameter/modifyParameter";
	}
	/**
	 * 编辑参数
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "modifyParameter", method = RequestMethod.POST)
	public String doModifyParameter(HttpServletRequest request,SysParameter parameter) {
		try {
			parameterService.updateParameter(parameter);
			sysLogService.log(request, getVisitor(request), "系统管理--系统参数",
					"修改参数", "更新【" + parameter.getParameterName() + "】参数", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/parameter/queryParameter";
	}
	/**
	 * 删除参数
	 * @param ParIds
	 * @return
	 */
	@RequestMapping(value = "deleteParameter", method = RequestMethod.POST)
	public String doDeleteParameter(HttpServletRequest request,String ParIds) {
		if (null != ParIds && !"".equals(ParIds)) {
			String[] ids = ParIds.split(",");
			String deleteName = "";
			try {
					for (String sptId : ids) {
						SysParameter sysParameter = parameterService.queryParameter(sptId);
						deleteName += sysParameter.getParameterName() + "，";
						parameterService.deleteParameter(sptId);
							
					}
					sysLogService.log(request, getVisitor(request), "系统管理--系统参数", "删除参数",
						"删除参数【" + deleteName.substring(0,deleteName.length()-1)+ "】", new Date(), "3", new Date(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/sys/parameter/queryParameter";
	}
	
	/**
	 * 单行删除参数
	 * @param paramTyprIds
	 * @return
	 */
	@RequestMapping(value = "deleteParameterById/{paramTyprId}", method = RequestMethod.GET)
	public String deleteParameterTypeById(HttpServletRequest request,@PathVariable String paramTyprId) {
		if (null != paramTyprId && !"".equals(paramTyprId)) {
			try {	
				SysParameter sysParameter = parameterService.queryParameter(paramTyprId);
				parameterService.deleteParameter(paramTyprId);
				sysLogService.log(request, getVisitor(request), "系统管理--系统参数", "删除参数类型",
						"删除参数【" + sysParameter.getParameterName() + "】", new Date(), "3", new Date(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	@RequestMapping(value = "checknameuniqueness", method = RequestMethod.POST)
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
