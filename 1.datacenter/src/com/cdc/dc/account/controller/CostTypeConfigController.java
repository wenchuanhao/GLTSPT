package com.cdc.dc.account.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.common.BusTypes;
import com.cdc.dc.account.common.ComtreeTransitionDTO;
import com.cdc.dc.account.form.AccountForm;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

/**
 * 
 * @author xms
 *费用类型配置
 */
@Controller
@RequestMapping(value="account/")
public class CostTypeConfigController extends DefaultController{
	@Autowired
	private IAccountService accService;
	
	@Autowired
	private IRulesService rulesService;
	
	//费用类型首页
	@RequestMapping(value="costTypeConfig" , method = {RequestMethod.POST,RequestMethod.GET})
	public String costTypeConfig(HttpServletRequest request,AccountForm accountForm,RulesType rulesType){
		
		//费用类型树，公用制度方法
		List<RulesType> rulesTypeList = rulesService.queryAllByBusType(BusTypes.busTypes_BZ,null);
		request.setAttribute("rulesTypeList", JSONArray.fromObject(rulesTypeList).toString());
		request.setAttribute("rulesType", rulesType);
		request.setAttribute("list", rulesTypeList);
		return "dc/account/costTypeConfig";
	}
	
	
	/**
	 * 提交动作
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addCostType", method = {RequestMethod.POST})
	public String addCostType(HttpServletRequest request,RulesType rulesType) throws Exception{
		SysUser sysUser = getVisitor(request);
		//BZ报账类型  ，ZD制度类型
		rulesService.addRulesType(rulesType,sysUser,BusTypes.busTypes_BZ);
		return "redirect:/account/costTypeConfig";
	}
	
	
}
