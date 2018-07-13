package com.cdc.dc.account.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.account.form.AccountForm;
import com.cdc.dc.account.form.FlowInfoForm;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;
/**
 * 信息补录
 * @author xms
 *
 */
@Controller
@RequestMapping(value="account/")
public class InfoCollectController extends DefaultController {
	@Autowired
	private IAccountService accService;
	
	//信息补录首页
	@RequestMapping(value="infoCollectIndex",method={RequestMethod.GET,RequestMethod.POST})
	public String infoCollectIndex(HttpServletRequest request,AccountForm accountForm) throws Exception{
		request.setAttribute("accountForm",accountForm);
		//费用类型
		List<RulesType> rulelist = accService.queryCostType();
		request.setAttribute("rulesList", rulelist);
		
		SysUser visitor=this.getVisitor(request);
		String type = request.getParameter("type");
		// type 1代办 ，type  2已办
		if(type==null){
			type="1";
		}
		String people = request.getParameter("people");
		request.setAttribute("people", people);
		
		ItemPage itemPage;
		try {
			itemPage =(ItemPage)accService.queryInfoCollectList(accountForm,type,visitor,people);
			request.setAttribute("type", type);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "dc/account/infoCollect";
	}
	
	//tal跳转
	@RequestMapping(value="importInfoCollect",method={RequestMethod.GET,RequestMethod.POST})
	public String importInfoCollect(HttpServletRequest request,FlowInfoForm flowInfoForm) throws Exception{
		String type = request.getParameter("type");
		if(!StringUtils.isNotEmpty(type)){
			type = "1";
		}
		//type1 税务信息  type2合同发票信息  type3审批流程信息
		if(type.equals("3")){
			//查询审批流程信息列表
			ItemPage itemPage = accService.queryFlowInfoList(flowInfoForm);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		}else{
			//查询税务合同信息列表
			ItemPage itemPage = accService.queryTaxInfoList(flowInfoForm);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		}
		request.setAttribute("flowInfoForm", flowInfoForm);
		request.setAttribute("type", type);
		return "dc/account/addInfoCollect";
	}
	
	//查询审批流程信息
	@RequestMapping(value="queryFlowInfo")
	public String queryFlowInfo(HttpServletRequest request ,FlowInfoForm flowInfoForm) throws Exception{
		ItemPage itemPage = accService.queryFlowInfoList(flowInfoForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		//type3审批流程信息
		request.setAttribute("type", 3);
		return "dc/account/addInfoCollect";
	}
	
}
