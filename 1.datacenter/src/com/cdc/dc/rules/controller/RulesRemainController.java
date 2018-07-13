package com.cdc.dc.rules.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.form.RulesForm;
import com.cdc.dc.rules.model.RulesInfo;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

/**
 * 待办制度审核
 * @author ZENGKAI
 * @date 2016-04-13 11:58:29
 */
@Controller
@RequestMapping(value = "/rulesController/")
public class RulesRemainController extends DefaultController{

	@Autowired
	private IRulesService rulesService;

	@ModelAttribute
	public void doBeforeCall(HttpServletRequest request,RulesForm rulesForm){
		//待办
		ItemPage itemPage =(ItemPage) rulesService.queryRemainRulesInfoItemPage(rulesForm,RulesCommon.rulesFlowInsactorsHandelStatus_NONE,RulesCommon.rulesFlowInsactorsType_DB);
		request.setAttribute("remainCount",itemPage.getTotal() );
		//已办
		itemPage =(ItemPage) rulesService.queryDonesRulesInfoItemPage(rulesForm,RulesCommon.rulesFlowInsactorsHandelStatus_DONE,RulesCommon.rulesFlowInsactorsType_DB);
		request.setAttribute("donesCount",itemPage.getTotal() );
		//待阅
		itemPage =(ItemPage) rulesService.queryRemainRulesInfoItemPage(rulesForm,RulesCommon.rulesFlowInsactorsHandelStatus_NONE,RulesCommon.rulesFlowInsactorsType_DY);
		request.setAttribute("tobeReadCount",itemPage.getTotal() );
		//已阅
		itemPage =(ItemPage) rulesService.queryDonesRulesInfoItemPage(rulesForm,RulesCommon.rulesFlowInsactorsHandelStatus_DONE,RulesCommon.rulesFlowInsactorsType_DY);
		request.setAttribute("haveReadCount",itemPage.getTotal() );
	}
	/**
	 * 制度审核
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "remain", method = {RequestMethod.POST,RequestMethod.GET})
	public String remain(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		request.setAttribute("rulesForm",rulesForm);
		SysUser sysUser = getVisitor(request);
		//查询待办、未处理的列表
		ItemPage itemPage =(ItemPage) rulesService.queryRemainRulesInfoItemPage(rulesForm,RulesCommon.rulesFlowInsactorsHandelStatus_NONE,RulesCommon.rulesFlowInsactorsType_DB);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		rulesService.setCommonAttribute(request);
		
		return "/dc/rules/rulesRemain";
	}
	
	/**
	 * 制度审核发布
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "releaseRemain", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void releaseRemain(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String result = "0";
		SysUser sysUser = getVisitor(request);
		String rulesId = request.getParameter("rulesId");
		if(StringUtils.isNotEmpty(rulesId)){
			String[] rulesIds = rulesId.split(",");
			for (String id : rulesIds) {
				RulesInfo rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, id);
				//审核中才能进行发布
				if(rulesInfo != null  && RulesCommon.rulesInfoStatus_SH.equals(rulesInfo.getStatus())){
					rulesInfo.setStatus(RulesCommon.rulesInfoStatus_FB);//已发布
					rulesService.releaseRemain(rulesInfo,sysUser);//发布
				}
			}
			result = "1";
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	
	}
	
	/**  
	 * 跳转至制度退回页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "returnViewRemain", method = {RequestMethod.GET})
	public String returnViewRemain(HttpServletRequest request) throws Exception{
		String rulesId = request.getParameter("rulesId");
		//如果有ID，查询信息
		if(StringUtils.isNotEmpty(rulesId)){
			request.setAttribute("rulesId", rulesId);
		}
		
		//制度退回页面
		return "/dc/rules/returnViewRemain";
	}
	
	/**  
	 * 制度退回
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "returnRemain", method = {RequestMethod.POST,RequestMethod.GET})
	public void returnRemain(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String result = "0";
		SysUser sysUser = getVisitor(request);
		String rulesId = request.getParameter("rulesId");
		String handelOpinion = request.getParameter("handelOpinion");
		if(StringUtils.isNotEmpty(rulesId)){
			String[] rulesIds = rulesId.split(",");
			for (String id : rulesIds) {
				RulesInfo rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, id);
				//审核中才能退回
				if(rulesInfo != null  && RulesCommon.rulesInfoStatus_SH.equals(rulesInfo.getStatus())){
					rulesInfo.setStatus(RulesCommon.rulesInfoStatus_TH);//已退回
					rulesService.returnRemain(rulesInfo,sysUser,handelOpinion);//退回
				}
			}
			result = "1";
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	
	}
	
	
	/**
	 * 已办
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dones", method = {RequestMethod.POST,RequestMethod.GET})
	public String dones(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		request.setAttribute("rulesForm",rulesForm);
		SysUser sysUser = getVisitor(request);
		//已办，超时状态批量变更
		rulesService.updateRulesFlowInsactorTimeoutStatus();
		//查询待办、已处理的列表
		ItemPage itemPage =(ItemPage) rulesService.queryDonesRulesInfoItemPage(rulesForm,RulesCommon.rulesFlowInsactorsHandelStatus_DONE,RulesCommon.rulesFlowInsactorsType_DB);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		request.setAttribute("showInclude",true);
		rulesService.setCommonAttribute(request);
		return "/dc/rules/rulesDones";
	}
	
	/**
	 * 待阅制度
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "tobeRead", method = {RequestMethod.POST,RequestMethod.GET})
	public String tobeRead(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		request.setAttribute("rulesForm",rulesForm);
		SysUser sysUser = getVisitor(request);
		//查询待办、未处理的列表
		ItemPage itemPage =(ItemPage) rulesService.queryRemainRulesInfoItemPage(rulesForm,RulesCommon.rulesFlowInsactorsHandelStatus_NONE,RulesCommon.rulesFlowInsactorsType_DY);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		rulesService.setCommonAttribute(request);
		
		return "/dc/rules/rulesToberead";
	}
	
	/**
	 * 制度待阅已阅操作
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "releaseRead", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void releaseRead(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String result = "0";
		SysUser sysUser = getVisitor(request);
		String rulesId = request.getParameter("rulesId");
		if(StringUtils.isNotEmpty(rulesId)){
			String[] rulesIds = rulesId.split(",");
			for (String id : rulesIds) {
				RulesInfo rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, id);
				//已废止及已修订的制度才能已阅
				if(rulesInfo != null  && (RulesCommon.rulesInfoStatus_FZ.equals(rulesInfo.getStatus()) || RulesCommon.rulesInfoStatus_XD.equals(rulesInfo.getStatus()))){
					rulesService.releaseRead(rulesInfo,sysUser);//已阅操作
				}
			}
			result = "1";
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	
	}
	
	/**
	 * 已阅制度
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "haveRead", method = {RequestMethod.POST,RequestMethod.GET})
	public String haveRead(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		request.setAttribute("rulesForm",rulesForm);
		SysUser sysUser = getVisitor(request);
		//制度已阅，超时状态批量变更
		rulesService.updateRulesFlowInsactorTimeoutStatus();
		//查询待办、未处理的列表
		ItemPage itemPage =(ItemPage) rulesService.queryDonesRulesInfoItemPage(rulesForm,RulesCommon.rulesFlowInsactorsHandelStatus_DONE,RulesCommon.rulesFlowInsactorsType_DY);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		rulesService.setCommonAttribute(request);
		return "/dc/rules/rulesHaveread";
	}
}
