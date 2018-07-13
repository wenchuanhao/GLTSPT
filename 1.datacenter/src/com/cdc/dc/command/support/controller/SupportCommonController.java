package com.cdc.dc.command.support.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.command.manage.model.CommandSupportorg;
import com.cdc.dc.command.support.service.ISupportService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;
/**
 * 工程指令管理
 * @author ZengKai
 * @date 2016-8-29 下午16:21:49
 */
@Controller
@RequestMapping(value = "/support/")
public class SupportCommonController extends CommonController{

	@Autowired
	private ISupportService supportService;
	
	 
    /**
     * 选择支撑单位列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "selectList",method = {RequestMethod.GET,RequestMethod.POST})
    public String selectList(HttpServletRequest request,HttpServletResponse response,CommandSupportorg support) throws Exception{
    	
    	ItemPage itemPage = supportService.findSupport(support);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("support", support);
    	return "/dc/command/support/supportSelect";
    } 
}
