package com.cdc.dc.account.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.dc.account.common.AccountCommon;
import com.cdc.dc.account.form.AccountForm;
import com.cdc.dc.account.model.AccountConfig;
import com.cdc.dc.account.model.AccountInfo;
import com.cdc.dc.account.model.AccountInvoice;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
/**
 * 
 * @author xms
 *接单录入
 */
@Controller
@RequestMapping(value="account/")
public class AccountAddController extends DefaultController{
	
	@Autowired
	private IAccountService accService;
	@Autowired
	private IRulesService rulesService;
	@Autowired
	private ISysUserService sysUserService;
	
	//接单录入首页
	@RequestMapping(value="accountAdd" , method = {RequestMethod.POST,RequestMethod.GET})
	public String accountAdd(HttpServletRequest request,AccountForm accountForm) throws Exception{
		request.setAttribute("accountForm", accountForm);
		List<RulesType> rulelist = accService.queryCostType();
		request.setAttribute("rulesList", rulelist);
		return "dc/account/addAccount";
	}
	
	//保存
	@RequestMapping(value="accountSave" , method = {RequestMethod.POST,RequestMethod.GET})
	public void accountSave(HttpServletRequest request,HttpServletResponse response,AccountInfo aInfo) throws IOException{
		SysUser visitor=this.getVisitor(request);
		Date createDate = new Date();
		String createName = visitor.getUserName();
		
		try {
			aInfo.setCreateDate(createDate);
			aInfo.setCreateName(createName);
			aInfo.setUserId(visitor.getUserId());
			aInfo.settDTime("0");//退单次数默认为0
			aInfo.setIstimeOut("0");//是否超时：否
			aInfo.setOrderId(aInfo.getOrderId().trim());
			aInfo.setTrialFlat(AccountCommon.ACCOUNT_ACCOUNT_FLAT_DB);//1代办
			//设置状态为2-无问题
			aInfo.setStatus(AccountCommon.ACCOUNT_STATUS_WWT);
			aInfo.setCurrentLink(AccountCommon.CURRENT_LINK_CSLR);
			//保存
			accService.addAccount(aInfo);
			
			//保存发票信息
			if(StringUtils.isNotEmpty(aInfo.getId())){
				accService.saveInvoice(aInfo);
			}
			//给用户赋予角色
			SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(AccountCommon.ACC_DICT_TYPE, AccountCommon.ACCOUNT_KJ).getParameterValue());
	    	String[] users = aInfo.getTrialAccountId().split(",");
	    	sysUserService.addSysUserRoleId(sysRole,users);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.write("1");
	}
	
	//修改报账单
	@RequestMapping(value="updateTrial" , method = {RequestMethod.POST,RequestMethod.GET})
	public void updateTrial(HttpServletRequest request,HttpServletResponse response,AccountInfo aInfo) throws IOException{
		accService.updateTrialAccount(aInfo);
		PrintWriter out = response.getWriter();
		out.write("1");
	}
	
	//异步 ajax 查询 会计
	@RequestMapping(value="queryAccountById",method={RequestMethod.GET,RequestMethod.POST})
	public void  queryAccountById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String costTypeId = request.getParameter("costTypeId");
		String depId = request.getParameter("depId");
		//根据ID查询报账单
		List<AccountConfig> config = (List<AccountConfig>) accService.queryAccountById(costTypeId, depId);
		
		JSONArray userListJSONArray = JSONArray.fromObject(config);
        String result = userListJSONArray.toString();
		PrintWriter out = response.getWriter();
		out.write(result);
	}
	
	//根据userID查询部门
	@RequestMapping(value="queryDepartmentById",method={RequestMethod.GET,RequestMethod.POST})
	public void queryDepartmentById(HttpServletResponse response,String userid) throws Exception{
		String result =  accService.queryDepartmentById(userid);
		
		PrintWriter out=response.getWriter();
		out.write(result);
		out.close();
	}
	
	//检测报账单号是否重复
	@RequestMapping(value="ajaxCheck",method={RequestMethod.POST,RequestMethod.GET})
	public void ajaxCheck(HttpServletRequest reques,HttpServletResponse response,String accoutId) throws IOException{
		String result = "";
		String orderTemp = reques.getParameter("orderTemp"); 
		if( orderTemp!=accoutId){
			AccountInfo info = accService.findTrialAccountByid(accoutId);
			if(info!=null && !accoutId.equals("")){
				result= "1";
			}else {
				result= "0";
			}
		}else{
			result= "0";
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	}
	
	//联想查询部门
	@RequestMapping(value = "searchOrganization", method = {RequestMethod.POST})
	@ResponseBody
	public void searchOrganization(HttpServletRequest request,HttpServletResponse response){
		try {
			response.setContentType("text/html; charset=utf-8");
			String orgValue = request.getParameter("orgValue");
    		String result = "";
    		if(orgValue!=null && !"".equals(orgValue)){
    			List<Object[]> orgList = accService.searchDepartmenByName(orgValue);
    			JSONArray orgListJSONArray = new JSONArray();
    			if(orgList!=null && !orgList.isEmpty()){
    				JSONObject jsonObject = null;
                    Object[] obj = null;
                    for(int i = 0; i < orgList.size(); i++){
                    	obj = orgList.get(i);
                    	jsonObject = new JSONObject();
                        jsonObject.put("orgId", String.valueOf(obj[0]));
                        jsonObject.put("orgName", String.valueOf(obj[1]));
                        orgListJSONArray.add(jsonObject);
                    }
    			}
    			result = orgListJSONArray.toString();
    		}
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增发票信息弹窗页面
	 * @return
	 */
	@RequestMapping(value = "addInvoice",method = {RequestMethod.GET,RequestMethod.POST})
	public String addInvoice(HttpServletRequest request,HttpServletResponse response,AccountInvoice form){
		String pId = request.getParameter("pId");
		request.setAttribute("pId", pId);
		
		AccountInvoice invoice = null;
		if(StringUtils.isNotEmpty(form.getId())){
			invoice = accService.getInvoiceById(form.getId());
		}
		request.setAttribute("vo", invoice);
		return "dc/account/addInvoice";
	}
	
	/**
	 * 新增/更新发票信息
	 * @param request
	 * @param response
	 * @param invoice
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdateInvoice",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void saveOrUpdateInvoice(HttpServletRequest request,HttpServletResponse response,AccountInvoice form){
		SysUser sysUser = getVisitor(request);
		String mess = "";
		String result = "0";
		try {
			if(StringUtils.isEmpty(form.getId())){
				AccountInvoice invoice = new AccountInvoice();
				invoice.setParentId(form.getParentId());
				invoice.setInvoiceType(form.getInvoiceType());
				invoice.setGoodsName(form.getGoodsName());
				invoice.setInvoiceCode(form.getInvoiceCode());
				invoice.setInvoiceNum(form.getInvoiceNum());
				invoice.setCreateDate(form.getCreateDate());
				invoice.setMoneyNoTax(form.getMoneyNoTax());
				invoice.setTaxNum(form.getTaxNum());
				invoice.setTaxRate(form.getTaxRate());
				invoice.setGfTaxpayerNum(form.getGfTaxpayerNum());
				invoice.setGfTaxpayerName(form.getGfTaxpayerName());
				invoice.setXfTaxpayerNum(form.getXfTaxpayerNum());
				invoice.setXfTaxpayerName(form.getXfTaxpayerName());
				invoice.setUpdateUserId(sysUser.getUserId());
				invoice.setUpdateUserName(sysUser.getUserName());
				invoice.setUpdateDate(new Date());
				accService.saveInvoice(invoice);
			} else {
				AccountInvoice invoice = accService.getInvoiceById(form.getId());
				invoice.setInvoiceType(form.getInvoiceType());
				invoice.setGoodsName(form.getGoodsName());
				invoice.setInvoiceCode(form.getInvoiceCode());
				invoice.setInvoiceNum(form.getInvoiceNum());
				invoice.setCreateDate(form.getCreateDate());
				invoice.setMoneyNoTax(form.getMoneyNoTax());
				invoice.setTaxNum(form.getTaxNum());
				invoice.setTaxRate(form.getTaxRate());
				invoice.setGfTaxpayerNum(form.getGfTaxpayerNum());
				invoice.setGfTaxpayerName(form.getGfTaxpayerName());
				invoice.setXfTaxpayerNum(form.getXfTaxpayerNum());
				invoice.setXfTaxpayerName(form.getXfTaxpayerName());
				invoice.setUpdateUserId(sysUser.getUserId());
				invoice.setUpdateUserName(sysUser.getUserName());
				invoice.setUpdateDate(new Date());
				accService.updateInvoice(invoice);
			}
			result = "1";
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除发票信息
	 */
	@RequestMapping(value = "delInvoice", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void delInvoice(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String result = "0";
		long flag = 0;
		try {
			if(StringUtils.isNotEmpty(id)){
				String[] ids = id.split(",");
				flag = accService.batchDelInvoice(ids);
			}
			if(flag > 0){
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
