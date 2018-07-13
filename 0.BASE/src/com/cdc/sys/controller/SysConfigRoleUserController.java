package com.cdc.sys.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysRoleForm;
import com.cdc.sys.form.SysUserForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysPrivilegesService;
import com.cdc.sys.service.ISysRoleGroupService;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.sys.service.ISysRoleSetService;
import com.cdc.sys.service.ISysRoleUserService;
import com.cdc.sys.service.ISysUserAreaService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.service.IStartUpService;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 角色管理_给角色赋予用户
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/role/*")
public class SysConfigRoleUserController extends CommonController {

	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysRoleService roleService;
	@Autowired
	private ISysOrganizationService organizationService;
	@Autowired
	private ISysPrivilegesService privilegesService;
	@Autowired
	private ISysRoleUserService sysRoleUserService;
	@Autowired
	private ISysRoleSetService	sysRoleSetService;
	@Autowired
	private ISysUserAreaService	sysUserAreaService;
	@Autowired
	private IStartUpService startUpService;
	/**
	 * 日志Service
	 */
	@Autowired
	private ISysLogService logService;
	
	@Autowired
	private ISysRoleGroupService groupService;

	
	
	/**
	 * 给用户赋予角色首页———
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "userRole", method = { RequestMethod.GET,RequestMethod.POST })
	public String userRole(HttpServletRequest request, SysUserForm form) {
		try {
			SysUser loginedUser = getVisitor(request);
			String flag = request.getParameter("flag");
			String roleId = request.getParameter("roleId");
			List<SysRole> sysRoleList = roleService.listAllByType(flag,roleId);
			request.setAttribute("sysRoleList", sysRoleList);
			request.setAttribute("location", "sys/role/roleConfigIndex?isDefaultRole="+flag);
			if(StringUtils.isNotEmpty(roleId)){
				request.setAttribute("roleId", roleId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/userRole";
	}

	/**
	 * 组织架构树
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getOrganizationTree", method = {RequestMethod.GET})
	public String LoadOrganizationTree(HttpServletRequest request){
		
		try {
			String flowNodeId = request.getParameter("flowNodeId");
			List<SysOrganization> list = organizationService.queryAll();
			request.setAttribute("orgs", list);
			request.setAttribute("nodeId", flowNodeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/organTree";
	}
	
	
	@RequestMapping(value = "userList", method = {RequestMethod.POST,RequestMethod.GET})
	public String getUserList(HttpServletRequest request,SysUserForm form){
		String roleId = request.getParameter("flowNodeId");
		String flowNodeIdTemp = request.getParameter("flowNodeIdTemp");
		String organizationId=request.getParameter("organizationId");
		String account=request.getParameter("account");
		String userName=request.getParameter("userName");
		
		ItemPage itemPage=null;
		if(null!=organizationId&&!"".equals(organizationId)){
			form.setOrganizationId(organizationId);
		}else{
			form.setOrganizationId("");
		}
		if(null!=account&&!"".equals(account)){
			form.setAccount(account);
		}
		if(null!=userName&&!"".equals(userName)){
			form.setUserName(userName);
		}
		String pageSize1=request.getParameter("pageSize1");
		if(form.getPageSize1()==0){
			if(pageSize1!=null&&pageSize1!=""){
				form.setPageSize(Integer.parseInt(pageSize1));
			}			
		}
		
		String pageIndex1=request.getParameter("pageIndex1");
		if(pageIndex1!=null&&pageIndex1!=""){
			form.setPageIndex(Integer.parseInt(pageIndex1));
		}
		try {
			itemPage = sysUserService.querySysUser(form);
			if(StringUtils.isEmpty(roleId)){
				roleId = flowNodeIdTemp;
			}
			if(StringUtils.isNotEmpty(roleId)){
				List<SysRoleUser> list  = sysRoleUserService.getRoleUsersByRoleId(roleId);
				HashMap mapFlow=new HashMap();
				if(list != null && list.size() > 0){
					for (SysRoleUser sysRoleUser : list) {
						mapFlow.put(sysRoleUser.getUserId(), sysRoleUser.getUserId());
					}
				}
				request.setAttribute("listFlow", mapFlow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("form", form);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		
		
		return "sys/role/userList";
	}
	
	
	@RequestMapping(value = "getAllConfigUser",method = {RequestMethod.POST,RequestMethod.GET})
	public String getAllConfigUser(HttpServletRequest request,SysRoleForm form){
		String pageSize3=request.getParameter("pageSize3");	
		String pageSize2=request.getParameter("pageSize2");	
		String pageIndexTemp=request.getParameter("pageIndexTemp");
		if(pageSize3==null||pageSize3==""){
			if(pageSize2!=null&&pageSize2!=""){
				form.setPageSize(Integer.parseInt(pageSize2));				
			}			
		}			
		if(pageIndexTemp!=null&&pageIndexTemp!=""){
			form.setPageIndex(Integer.parseInt(pageIndexTemp));
		}
		String roleId = request.getParameter("flowNodeId");
		if(StringUtils.isNotEmpty(roleId)){
			try {
				form.setRoleId(roleId);
				ItemPage itemPage = sysRoleUserService.querySysRoleUser(form);
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
		}
		request.setAttribute("pageSize2", Integer.toString(form.getPageSize()));
		
		return "sys/role/roleAndNode";
	}
	
	/**
	 * 用户配置保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveConfigUser",method = {RequestMethod.POST,RequestMethod.GET}) 
	public @ResponseBody String saveConfigUser(HttpServletRequest request){
		String userId =request.getParameter("userId");
		String roleId = request.getParameter("flowNodeId");
		String s = "0";
		if(StringUtils.isNotEmpty(userId)){
			try {
				SysUser user = sysUserService.getSysUserById(userId);
				SysRoleUser model= new SysRoleUser();
				model.setRoleId(roleId);
				model.setUserId(userId);
				sysRoleUserService.addRoleUser(model);//保存用户新角色
				SysRole sysRole = roleService.getSysRoleById(roleId);
				logService.log(request, getVisitor(request), "系统管理--角色管理--给角色赋予用户", "给角色赋予用户",
						"给角色" + sysRole.getRoleName()  + "赋予用户" + user.getUserName(), new Date(), "4", new Date(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		s = "1";
		return s;
	}
	/**
	 * 用户配置删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delRoleUser",method = {RequestMethod.POST,RequestMethod.GET}) 
	public @ResponseBody String delRoleUser(HttpServletRequest request){
		String userRoleId =request.getParameter("roleuserId");
		String roleId = request.getParameter("flowNodeId");
		if(StringUtils.isNotEmpty(userRoleId)){
			try {
				String[] roles = userRoleId.split("_");
				for (String roleid : roles) {
					sysRoleUserService.deleteRoleUser(roleid);//删除角色配置
					logService.log(request, getVisitor(request), "系统管理-角色管理", "删除角色用户",
							"删除了ID为'" + roleid+ "'的记录", new Date(), "3", new Date(), null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String s = "0";
		s = "1";
		return s;
	}
}
