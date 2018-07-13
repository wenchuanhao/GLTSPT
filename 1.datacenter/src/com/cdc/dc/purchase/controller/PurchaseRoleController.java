package com.cdc.dc.purchase.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.purchase.form.PurchaseForm;
import com.cdc.dc.purchase.model.PurchaseRole;
import com.cdc.dc.purchase.service.IPurchaseService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 采购角色管理
 * @author ywc
 * @date 2016-10-18 16:31:20
 *
 */
@Controller
@RequestMapping(value = "/purchase/")
public class PurchaseRoleController extends CommonController{
	private Log logger = LogFactory.getLog(getClass());
	@Autowired
    private IPurchaseService purchaseService;
	
	@Autowired
	private ISysOrganizationService organizationService;
	/**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "purchaseRoleManage",method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseRoleManage(HttpServletRequest request,HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	ItemPage itemPage = purchaseService.queryPurchaseRole(purchaseForm);
    	request.setAttribute("purchaseForm",purchaseForm );
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	//需求确认时长超时预警
    	
		return "dc/project/purchase/purchaseRoleManage";
    }
    
	/**
	 * 新增采购配置跳转
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "purchaseRoleAdd",method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseRoleAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	//获取组织机构
    	request.setAttribute("orgs", organizationService.queryAll());
		return "dc/project/purchase/purchaseRoleAdd";
    }
    
    /**
     * 新增采购量化角色
     * @param request
     * @param response
     * @param purchaseForm
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "submitPurchaseRole",method = {RequestMethod.GET,RequestMethod.POST})
    public void submitPurchaseRole(HttpServletRequest request,HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	String[] orgIds= request.getParameterValues("orgIds");
    	String[] userIds= request.getParameterValues("userId");
    	
    	//拼接组织机构id 和组织机构名
    	StringBuffer apartNames = new StringBuffer();
    	StringBuffer apartIds = new StringBuffer();
    	if(null != apartIds && orgIds.length>0 ){
    		for(int i=0;i<orgIds.length;i++)
    		{  
    			SysOrganization sys = organizationService.getOrgById(orgIds[i]);
    			apartNames.append(sys.getOrgName()).append(",");
    			apartIds.append(orgIds[i]).append(",");
    		}
    	}
    	//删除最后一个逗号
    	apartNames.deleteCharAt(apartNames.length()-1);
    	apartIds.deleteCharAt(apartIds.length()-1);
    	
    	//拼接分管人id和分管人名字
    	StringBuffer manageNames = new StringBuffer();
    	StringBuffer manageIds = new StringBuffer();
    	if(null != userIds && userIds.length>0 ){
    		for(int i=0;i<userIds.length;i++)
    		{  
    			//
    			SysUser user  = (SysUser) purchaseService.getEntity(SysUser.class,userIds[i] );
    			//给分管人赋予采购量化领导角色PURCHASE_LEADER
    			purchaseService.addUserRoleByCode(user,"PURCHASE_LEADER");
    			manageNames.append(user.getUserName()).append(",");
    			manageIds.append(user.getUserId()).append(",");
    		}
    	}
    	manageNames.deleteCharAt(manageNames.length()-1);
    	manageIds.deleteCharAt(manageIds.length()-1);
    	
    	SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
    	purchaseForm.setOperatorName(sysUser.getAccount());
    	purchaseForm.setUserId(manageIds.toString());
    	purchaseForm.setManageName(manageNames.toString());
    	purchaseService.SavePurchaseRole(purchaseForm, apartIds.toString(), apartNames.toString());
    	PrintWriter out = response.getWriter();
    	out.write("1");
    }
    
    /**
     * 根据id删除采购量化角色
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
   	@RequestMapping(value = "purchaseRoleDelByid",method = {RequestMethod.GET,RequestMethod.POST})
       public void purchaseRoleDel(HttpServletRequest request,HttpServletResponse response) throws Exception{
       	    String id = request.getParameter("id");
       	    purchaseService.delPurchaseRoleByid(id);
       	    PrintWriter out = response.getWriter();
     	    out.write("1");
       }
    
    /**
     * 编辑页面跳转
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
   	@RequestMapping(value = "purchaseRoleEdit",method = {RequestMethod.GET,RequestMethod.POST})
       public String purchaseRoleEdit(HttpServletRequest request,HttpServletResponse response) throws Exception{
       	 String id = request.getParameter("id");
       	 PurchaseRole purchaseRole = purchaseService.queryPurchaseRoleByid(id);
       	 //获取分管部门的id
       	 List<SysOrganization> list = new ArrayList<SysOrganization>(); 
       	 String manageApartIds = purchaseRole.getManageApartId();
       	 String[] aparts = manageApartIds.split(",");
       	 for(int i =0 ;i<aparts.length;i++){
       		SysOrganization sys = organizationService.getOrgById(aparts[i]);
       		list.add(sys);       		
       	 }
       	 //获取分管人id
       	 List<SysUser> slist = new ArrayList<SysUser>();
       	 String manageIds = purchaseRole.getManageAcount();
       	 String[] ids = manageIds.split(",");
       	 for(int i =0 ;i<ids.length;i++){
       		SysUser user = (SysUser) purchaseService.getEntity(SysUser.class, ids[i]);
       		slist.add(user);  
       	 }
         //塞数据给前端
       	 request.setAttribute("orgs", organizationService.queryAll());
       	 request.setAttribute("purchaseRole", purchaseRole);
       	 request.setAttribute("aparts", list);
       	 request.setAttribute("users", slist);
       	 return "dc/project/purchase/purchaseRoleEdit";
       }
    /**
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "updatePurchaseRole",method = {RequestMethod.GET,RequestMethod.POST})
    public void updatePurchaseRole(HttpServletRequest request,HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	//获取组织机构id
    	String[] orgIds= request.getParameterValues("orgIds");
    	String[] userIds= request.getParameterValues("userId");
    	StringBuffer apartNames = new StringBuffer();
    	StringBuffer apartIds = new StringBuffer();
    	//拼接组织机构id 和组织机构名
    	if(null != apartIds && orgIds.length>0 ){
    		for(int i=0;i<orgIds.length;i++)
    		{  
    			SysOrganization sys = organizationService.getOrgById(orgIds[i]);
    			apartNames.append(sys.getOrgName()).append(",");
    			apartIds.append(orgIds[i]).append(",");
    		}
    	}
    	//删除最后一个逗号
    	apartNames.deleteCharAt(apartNames.length()-1);
    	apartIds.deleteCharAt(apartIds.length()-1);
    	
    	//拼接分管人id和分管人名字
    	StringBuffer manageNames = new StringBuffer();
    	StringBuffer manageIds = new StringBuffer();
    	if(null != userIds && userIds.length>0 ){
    		for(int i=0;i<userIds.length;i++)
    		{  
    			//
    			SysUser user  = (SysUser) purchaseService.getEntity(SysUser.class,userIds[i] );
    			//给分管人赋予采购量化领导角色PURCHASE_LEADER
    			purchaseService.addUserRoleByCode(user,"PURCHASE_LEADER");
    			manageNames.append(user.getUserName()).append(",");
    			manageIds.append(user.getUserId()).append(",");
    		}
    	}
    	manageNames.deleteCharAt(manageNames.length()-1);
    	manageIds.deleteCharAt(manageIds.length()-1);
    	SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
    	purchaseForm.setOperatorName(sysUser.getAccount());
    	purchaseForm.setManageName(manageNames.toString());
    	purchaseForm.setManageAcount(manageIds.toString());
    	purchaseService.updatePurchaseRole(purchaseForm, apartIds.toString(), apartNames.toString());
    	PrintWriter out = response.getWriter();
    	out.write("1");
    }
    
    /**
     * 批量删除
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
   	@RequestMapping(value = "purchaseRoleDelByBatch",method = {RequestMethod.GET,RequestMethod.POST})
       public String purchaseRoleDelByBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String ids=request.getParameter("ids");
    	purchaseService.delpurchaseRoleBybatch(ids);
		return "redirect:/purchase/purchaseRoleManage";
       }
    /**
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
   	@RequestMapping(value = "purchaseRoleByAcount",method = {RequestMethod.GET,RequestMethod.POST})
     public void purchaseRoleByAcount(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	 PrintWriter out = response.getWriter();
   	     String manageAcount = request.getParameter("manageAcount");
   	     List<PurchaseRole> list = purchaseService.queryPurchaseRoleByAcount(manageAcount);
   	     if(null != list && list.size()>0){
   	    	 out.write("1");	
   	     }else{
   	    	 out.write("0"); 
   	     }
       }
    
}
