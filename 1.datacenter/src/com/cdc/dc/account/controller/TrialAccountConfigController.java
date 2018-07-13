package com.cdc.dc.account.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.account.form.AccountConfigForm;
import com.cdc.dc.account.form.AccountForm;
import com.cdc.dc.account.model.AccountConfig;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

/**
 * 
 * @author xms
 *初审会计配置
 */
@Controller
@RequestMapping(value="account/")
public class TrialAccountConfigController  extends DefaultController{
	@Autowired
	private IAccountService accService;
	
    //初审会计配置首页
	@RequestMapping(value="trialAccountConfig" , method = {RequestMethod.POST,RequestMethod.GET})
	public String trialAccountConfig(HttpServletRequest request,AccountConfigForm accountForm) throws Exception{
		request.setAttribute("accountForm", accountForm);
		List<RulesType> rulesList = accService.queryCostType();
		ItemPage item = accService.queryTrialConfigList(accountForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, item);
		request.setAttribute("rulesList", rulesList);
		return "dc/account/trialConfig";
	}
	
	//新增初审会计配置跳转
	@RequestMapping(value="addTrialConfig" , method = {RequestMethod.POST,RequestMethod.GET})
	public String addTrialConfig(SysUser user,HttpServletRequest request,AccountConfig accountConfig) throws Exception{
		List<RulesType> rulesList = accService.queryCostType();
		request.setAttribute("rulesList", rulesList);
		return "dc/account/addTrialConfig";
	}
	
	//保存初审会计配置
	@RequestMapping(value="saveTrialConfig" , method = {RequestMethod.POST,RequestMethod.GET})
	public void saveTrialConfig(HttpServletRequest request,HttpServletResponse response,AccountConfig accountConfig) throws IOException{
		request.setAttribute("accountConfig", accountConfig);
		SysUser visitor=this.getVisitor(request);
		String department = request.getParameter("department");
		accountConfig.setDepartment(department);
		accountConfig.setCreateDate(new Date());
		accountConfig.setUserId(visitor.getUserId());
		//保存会计配置
		accService.saveTrialConfig(accountConfig);
		PrintWriter out = response.getWriter();
		out.write("1");
	}
	
	//编辑初审会计配置
	@RequestMapping(value="editTrialConfig" , method = {RequestMethod.POST,RequestMethod.GET})
	public String editTrialConfig(HttpServletRequest request,AccountConfigForm accountForm) throws Exception{
		request.setAttribute("accountForm", accountForm);
		String id = request.getParameter("id");
		AccountConfig config = accService.findTrialConfigById(id);
		List<SysUser> queryerList = accService.queSysUserById(config.getAccountingId());
		request.setAttribute("queryerList", queryerList);
		request.setAttribute("config", config);
		
		List<RulesType> rulesList = accService.queryCostType();
		request.setAttribute("rulesList", rulesList);
		return "dc/account/editTrialConfig";
	}
	
	//检查初审会计配置是否重复
	@RequestMapping(value="checkTrialConfig" , method = {RequestMethod.POST,RequestMethod.GET})
	public void checkTrialConfig(HttpServletRequest request,HttpServletResponse response,AccountConfig config) throws IOException{
		String cosIdTemp = request.getParameter("cosIdTemp");
		String depIdTemp = request.getParameter("depIdTemp");
		
		String str = "";
		String result = "";
		if(cosIdTemp!=null && depIdTemp!=null ){
			if( config.getCosId().equals(cosIdTemp) && config.getDepartmentId().equals(depIdTemp) ){
				str= "str";
				result = "0";
			}
		}
		
		if(str.equals("")){
			List<AccountConfig> list = accService.checkTrialConfig(config);
			if(list!=null && list.size()>0){
				result = "1";
			}else {
				result = "0";
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(result);
	}
	
	//修改初审会计配置
	@RequestMapping(value="updateTrialConfig" , method = {RequestMethod.POST,RequestMethod.GET})
	public void updateTrialConfig(HttpServletRequest request,HttpServletResponse response,AccountConfig config) throws IOException{
		SysUser visitor=this.getVisitor(request);
		String id = request.getParameter("id");
		String department = request.getParameter("department");
		String typeName = request.getParameter("type");
		//AccountConfig config =  accService.findTrialConfigById(id);
		//
		config.setUserId(visitor.getUserId());
		config.setCreateDate(new Date());
		accService.updateTrialConfig(config);
		PrintWriter out = response.getWriter();
		out.write("1");
	}
	
	//删除初审会计配置
	@RequestMapping(value="delTrialConfig" , method = {RequestMethod.POST,RequestMethod.GET})
	public String delTrialConfig(HttpServletRequest request,AccountConfig accountConfig){
		String requireIds=request.getParameter("requireIds");
		SysUser visitor=this.getVisitor(request);
		accService.delTrialConfig(requireIds,visitor.getUserId());
		return "redirect:/account/trialAccountConfig";
	}
}
