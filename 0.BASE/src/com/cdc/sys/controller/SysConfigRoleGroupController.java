package com.cdc.sys.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleGroup;
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
 * @Description: 角色管理_角色组管理
 * @Author: zengkai
 * @Version: V1.0
 * @date 20161013 17:36
 */
@Controller
@RequestMapping(value = "/sys/rolegroup/*")
public class SysConfigRoleGroupController extends CommonController {

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
	@Autowired
	private ISysRoleGroupService roleGroupService;
	/**
	 * 日志Service
	 */
	@Autowired
	private ISysLogService logService;
	
	@Autowired
	private ISysRoleGroupService groupService;

	
	
	/**
	 * 给角色组赋予用户首页———
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "groupUser", method = { RequestMethod.GET,RequestMethod.POST })
	public String userRole(HttpServletRequest request, SysUserForm form) {
		try {
			String roleGroupid = request.getParameter("roleGroupid");
			//查询所有角色组
			List<SysRoleGroup> sysRoleGroupList = roleGroupService.querySysRoleGroupByGroupId(roleGroupid);
			request.setAttribute("sysRoleGroupList",sysRoleGroupList);
			request.setAttribute("location", "sys/rolegroup/querySysRoleGroup");
			if(StringUtils.isNotEmpty(roleGroupid)){
				request.setAttribute("roleGroupid", roleGroupid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/rolegroup/groupUser";
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
		return "sys/rolegroup/groupOrganTree";
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
				//查询该角色组配置的人员
				List<SysRoleUser> list  = groupService.getRoleUsersByGroupRoleId(roleId);
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
		
		
		return "sys/rolegroup/groupUserList";
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
		String roleGroupi = request.getParameter("flowNodeId");
		if(StringUtils.isNotEmpty(roleGroupi)){
			try {
				form.setSysRoleGroupid(roleGroupi);
				//查询已配置人员列表
				ItemPage itemPage = groupService.querySysRoleUser(form);
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
		}
		request.setAttribute("pageSize2", Integer.toString(form.getPageSize()));
		
		return "sys/rolegroup/groupAndNode";
	}
	
	/**
	 * 角色组用户配置保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveConfigUser",method = {RequestMethod.POST,RequestMethod.GET}) 
	public @ResponseBody String saveConfigUser(HttpServletRequest request){
		String userId =request.getParameter("userId");
		String roleGroupid = request.getParameter("flowNodeId");
		String s = "0";
		if(StringUtils.isNotEmpty(userId)){
			try {
				SysRoleGroup sysrg = roleGroupService.getSysRoleGroupById(roleGroupid);
				//查询角色组角色列表
				List<SysRole> list = groupService.querySysRoleByGroupId(roleGroupid);
				if(list != null && list.size() > 0){
					for (SysRole sysRole : list) {
						List<SysRoleUser> modelList = sysRoleUserService.getRoleUsersByUserId(userId, sysRole.getRoleId());
						//不存在，才能新增
						if (modelList == null || modelList.size() == 0){
							SysRoleUser model= new SysRoleUser();
							model.setRoleId(sysRole.getRoleId());
							model.setUserId(userId);
							sysRoleUserService.addRoleUser(model);//保存用户新角色
						}
					}
					SysUser user = sysUserService.getSysUserById(userId);
					logService.log(request, getVisitor(request), "系统管理--角色管理--角色组配置", "给角色组赋予用户",
							"给角色组" + sysrg.getRoleGroupname() + "添加用户" + user.getUserName(), new Date(), "4", new Date(), null);
						
				}
				
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
	@RequestMapping(value = "delGroupUser",method = {RequestMethod.POST,RequestMethod.GET}) 
	public @ResponseBody String delGroupUser(HttpServletRequest request){
		String userId =request.getParameter("roleuserId");
		String roleGroupid = request.getParameter("flowNodeId");
		if(StringUtils.isNotEmpty(userId)){
			try {
				SysRoleGroup sysrg = roleGroupService.getSysRoleGroupById(roleGroupid);
				//查询角色组角色列表
				List<SysRole> list = groupService.querySysRoleByGroupId(roleGroupid);
				if(list != null && list.size() > 0){
					for (SysRole sysRole : list) {
						sysRoleUserService.deleteRoleUserByUserIdRoleId(userId, sysRole.getRoleId());//删除角色配置
						
					}
					SysUser user = sysUserService.getSysUserById(userId);
					logService.log(request, getVisitor(request), "系统管理-角色管理-角色组配置", "删除角色组" + sysrg.getRoleGroupname() + "用户",
							"删除了用户【" + user.getUserName()+ "】的配置", new Date(), "3", new Date(), null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String s = "0";
		s = "1";
		return s;
	}
	/**
	 * 查询角色组配置的角色列表
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "queryConfigRole",method = {RequestMethod.POST,RequestMethod.GET}) 
	public @ResponseBody void queryConfigRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		String s = "";
		String roleId = request.getParameter("roleGroupid");
		List<SysRole> list = groupService.querySysRoleByGroupId(roleId);
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				if( i == 0){
					s += list.get(i).getRoleName();
				}else{
					s += "，"+list.get(i).getRoleName();
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.write(s);
	}
}
