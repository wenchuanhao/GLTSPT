package com.cdc.sys.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysPrivilegesService;
import com.cdc.sys.service.ISysRoleGroupService;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.sys.service.ISysRoleUserService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;

/**
 * 
 * @Description: 用户管理>>新增用户
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/user/*")
public class SysAddUserController extends CommonController {

	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysRoleService roleService;
	@Autowired
	private ISysOrganizationService organizationService;

	@Autowired
	private ISysPrivilegesService privilegesService;
	
	@Autowired
	private ISysRoleGroupService groupService;
	/**
	 * 日志Service
	 */
	@Autowired
	private ISysLogService logService;
	
	@Autowired
	private ISysRoleUserService sysRoleUserService;

	
	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "addUser", method = RequestMethod.GET)
	public String addUser(HttpServletRequest request) throws Exception {
		String st=request.getParameter("st");
		if(st!=null&&st!=""){
			request.setAttribute("st", st);
		}
		request.setAttribute("orgs", organizationService.queryAll());
		request.setAttribute("listOrg", organizationService.queryAllNoRoot());
		request.setAttribute("listrole", roleService.listAll());
		request.setAttribute("location", "sys/user/queryUser");
		return "sys/user/addUser";
	}
	
	
	/**
	 * 获取下一级组织机构
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "setUserNestOrg", method = {RequestMethod.POST })
	public @ResponseBody void setUserNestOrg(HttpServletRequest request,HttpServletResponse response) {
		String orgId=request.getParameter("orgId");
		String selectString="";
    	try {
    		request.setCharacterEncoding("utf-8");
  	        response.setContentType("text/html; charset=utf-8");
  	        if(null!=orgId && !"".equals(orgId)){
  	        	List<SysOrganization> listOrg=sysUserService.getNextOrg(this.getVisitor(request), orgId);	
  	  	        if(null!=listOrg && listOrg.size()>0){
  	  	        	for(int i=0;i<listOrg.size();i++){
  	  	    			SysOrganization org = (SysOrganization) listOrg.get(i);
  	  	    			selectString=selectString+"<option value='"+org.getOrganizationId()+","+org.getOrgName()+"'>"+org.getOrgName()+"</option>";    				
  	  	    		}
  	  	        	selectString=selectString+"</select>";
  	  	        }else{
  	  	        	selectString="0";
  	  	        }
  	        }else{
  	        	selectString="0";
  	        }
    		PrintWriter writer = response.getWriter();
    		writer.write(selectString);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
	
	/**
	 * 验证用户account的唯一
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "userchecknameuniqueness", method = RequestMethod.POST)
	public @ResponseBody
	String userchecknameuniqueness(HttpServletRequest request,HttpServletResponse response, String account) {
		SysUser visitor = null;
		try {
			visitor = sysUserService.queryVisitorByName(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (visitor == null)
			return "0";
		else
			return "1";
	}
}
