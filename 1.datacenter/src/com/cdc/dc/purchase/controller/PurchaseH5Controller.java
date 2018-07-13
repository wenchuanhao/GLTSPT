package com.cdc.dc.purchase.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cdc.dc.purchase.form.PurchaseForm;
import com.cdc.dc.purchase.model.PurchaseRole;
import com.cdc.dc.purchase.service.IPurchaseExpService;
import com.cdc.dc.purchase.service.IPurchaseService;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;

/**
*类描述：采购经分H5页面
*@author: zengkai
*@date： 日期：2016-12-26 时间：下午4:10:37
*@version 1.0
*/
@Controller
@RequestMapping(value = "/purchase/h5")
public class PurchaseH5Controller extends CommonController{

	@Autowired
	private ISysOrganizationService organizationService;
	@Autowired
    private IPurchaseExpService iPurchaseExpService;
	
	@Autowired
	private IRulesService rulesService;
	@Autowired
    private IPurchaseService iPurchaseService;
	/**
     * 采购全景监控总表-查询
     * @param request
     * @param response
     * @param purchaseForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "purchaseHome", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseAllQuery(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	checkPurchaseRole(request, purchaseForm);
    	
    	iPurchaseExpService.purchaseAll(request, purchaseForm);
    	
		return "dc/project/purchase/h5/purchaseHome";
		
    }
    
    public void checkPurchaseRole(HttpServletRequest request,PurchaseForm purchaseForm) throws Exception{
    	JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
    	Map<String, Object> map = jdbcTemplate.queryForMap("select user_id,user_name,organization_id from sys_user where account = '"+request.getParameter("loginId")+"' ");
    	String userName = (String)map.get("user_name");
    	String userId = (String)map.get("user_id");
    	String organizationId = (String)map.get("organization_id");
    	
		//系统管理员PURCHASE_ADMIN TODO
		//采购量领导PURCHASE_LEADER
		//报表只有领导和管理员可看
		SysRole sysAdmin = rulesService.querySysRoleByCode("PURCHASE_ADMIN");
		SysRoleUser purchaseAdmin = rulesService.getRoleUsersByUserId(sysAdmin.getRoleId(),userId);
		SysRole  sysLeader = rulesService.querySysRoleByCode("PURCHASE_LEADER");
		SysRoleUser purchaseLeader = rulesService.getRoleUsersByUserId(sysLeader.getRoleId(),userId);
		SysRole  sysNormal = rulesService.querySysRoleByCode("PURCHASE_NORMAL");
		SysRoleUser purchaseNormal = rulesService.getRoleUsersByUserId(sysNormal.getRoleId(),userId);

		List<SysOrganization> list = new ArrayList<SysOrganization>();
		
		//经办人
		if(null != purchaseNormal && null == purchaseAdmin){
			purchaseForm.setColumnB(userName);
			//经办人一级部门
			list = iPurchaseService.queryAllColumnC(userName);
		}
		//领导角色
		if(null != purchaseLeader){
			
			list = iPurchaseService.queryAllColumnC(userName);
			
			List<PurchaseRole> purchaseRoleList = iPurchaseService.queryPurchaseRoleByAcount(userId);
			StringBuffer roleSB = new StringBuffer();
			if(null != purchaseRoleList && purchaseRoleList.size()>0 ){
				//只会有一条记录
				PurchaseRole purchaseRole = purchaseRoleList.get(0);
				String[] orgIds = purchaseRole.getManageApartId().split(",");
				//拼接分管部门
				if(null != orgIds && orgIds.length>0){
					for(int i=0;i<orgIds.length;i++)
		    		{  
		    			List<SysOrganization> sys = organizationService.getOrgAndSonById(orgIds[i]);
		    			if(StringUtils.isNotEmpty(orgIds[i])){
		    				//领导的一级部门
		    				SysOrganization e = (SysOrganization) rulesService.getEntity(SysOrganization.class, orgIds[i]);
		    				boolean flag = true;
		    				for (int j = 0; j < list.size(); j++) {
								if(list.get(j).getOrgName().equals(e.getOrgName())){
									flag = false;
									break;
								}
							}
		    				if(flag){
		    					list.add(e);
		    				}
		    			}
		    			for(int j=0;j<sys.size();j++){
		    				SysOrganization u = sys.get(j);
		    				roleSB.append("'").append(u.getOrgName()).append("',");
		    			}
	    			
		    		}
		    		//去掉最后一个逗号
	    			roleSB.deleteCharAt(roleSB.length()-1);
				}

			}
			purchaseForm.setManageAparts(roleSB.toString());
			
		}
		if(null != purchaseAdmin){
			purchaseForm.setManageAparts(null);
			request.setAttribute("isAdmin", "true" );
			//所有一级部门
			list = iPurchaseService.queryAllColumnC(null);
		}
		//无关人员
		if(null == purchaseNormal && null == purchaseLeader && null == purchaseAdmin){
			purchaseForm.setManageAparts("'-'");
			//经办人一级部门
			list = iPurchaseService.queryAllColumnC(userName);
		}
		request.setAttribute("depList", list);
    }
    

    /**
     * 获取表格图形数据
     * @param request
     * @param response
     * @param purchaseForm
     * @throws Exception
     */
    
    @RequestMapping(value = "getChartData", method = {RequestMethod.GET,RequestMethod.POST})
    public void getChartData(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	String type = request.getParameter("searchType");   	
    	//校验角色
    	checkPurchaseRole(request, purchaseForm);
    	//TODO
        //tqc情况数据数据
    	JSONArray result = null;
    	List<Object[]> list = iPurchaseExpService.getChartData(type,purchaseForm);
        
        result = JSONArray.fromObject(list);          
        PrintWriter out = response.getWriter();
		if(result != null){
			out.write(result.toString());
		}else{
			out.write("[]");
		}
		
    }
    
    /**
     * 柱状图
     * @param request
     * @param response
     * @param purchaseForm
     * @throws Exception
     */
    @RequestMapping(value = "getColumnData", method = {RequestMethod.GET,RequestMethod.POST})
    public void getColumnData(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	String type = request.getParameter("searchType");
    	//校验角色
    	checkPurchaseRole(request, purchaseForm);
    	//TODO
        //tqc情况数据数据
    	JSONArray result = null;
    	List<Object[]> list = iPurchaseExpService.getColumnData(type,purchaseForm);
          
        result = JSONArray.fromObject(list);          
        PrintWriter out = response.getWriter();
		if(result != null){
			out.write(result.toString());
		}else{
			out.write("[]");
		}
		
    }
    
}
