package com.cdc.sys.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysRoleGroup;
import model.sys.entity.SysRolegroupRole;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysRoleGroupForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysPrivilegesService;
import com.cdc.sys.service.ISysRoleGroupService;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 
 * 
 * @Description: 角色组管理
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-29 下午5:33:45
 * @UpdateRemark:
 * @Version: V1.0
 */
@Controller
@RequestMapping("/sys/rolegroup/*")
public class SysRoleGroupController extends CommonController {

	@Autowired
	private ISysRoleGroupService roleGroupService;

	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private ISysPrivilegesService privilegesService;
	/**
	 * 日志Service
	 */
	@Autowired
	private ISysLogService sysLogService;

	/**
	 * 查询角色组
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "querySysRoleGroup", method = { RequestMethod.GET, RequestMethod.POST })
	public String querySysRoleGroup(HttpServletRequest request, SysRoleGroupForm form) {
		try {
			ItemPage itemPage = roleGroupService.querySysRoleGroup(form,getVisitor(request).getUserId());
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/rolegroup/querySysRoleGroup";
	}

	/**
	 * 新增角色组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addRoleGroup", method = { RequestMethod.GET, RequestMethod.POST })
	public String addRoleGroup(HttpServletRequest request){
		String roleGroupid = request.getParameter("roleGroupid");
		String isView = request.getParameter("isView");
		SysRoleGroup sysrg = null;
		try {
			if(StringUtils.isNotEmpty(roleGroupid)){
				sysrg = roleGroupService.getSysRoleGroupById(roleGroupid);
			}
			//查询角色组所有的角色
			List<SysRole> list = (List<SysRole>) roleGroupService.querySysRoleByGroupId(roleGroupid);
			request.setAttribute("has_list", list);
			
			List<SysRole> not_list = (List<SysRole>) roleGroupService.querySysRoleNotInList(list,null);
			request.setAttribute("not_list", not_list);
			
			request.setAttribute("vo", sysrg);
			request.setAttribute("location", "sys/rolegroup/querySysRoleGroup");
			if(StringUtils.isNotEmpty(isView) && "1".equals(isView)){
				return "sys/rolegroup/viewRoleGroup";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/rolegroup/addRoleGroup";
	}
	
	/**
	 * 给角色组配置角色动作————
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "configGroupRole", method = RequestMethod.POST)
	public @ResponseBody
	String configGroupRole(HttpServletRequest request, HttpServletResponse response, String roleGroupid,String roleIds) throws Exception {
		
		if(StringUtils.isEmpty(roleGroupid) && StringUtils.isEmpty(roleIds)){
			return "n";
		}
		
		SysUser sysuser = getVisitor(request);
		String roleGroupcode = request.getParameter("roleGroupcode");
		String roleGroupname = request.getParameter("roleGroupname");
		String roleGroupdesc = request.getParameter("roleGroupdesc");
		
		//重复判断
		String result = roleGroupService.querySysRoleGroupByCodeOrName(roleGroupid,roleGroupcode,roleGroupname);
		if(!"0".equals(result)){
			return result;
		}
		
		SysRoleGroup  sysrg = null;
		//新增保存
		if(StringUtils.isEmpty(roleGroupid)){
			sysrg = new SysRoleGroup();
			sysrg.setRoleGroupcode(roleGroupcode);
			sysrg.setRoleGroupname(roleGroupname);
			sysrg.setRoleGroupdesc(roleGroupdesc);
			sysrg.setCreateTime(new Date());
			sysrg.setCreateUsername(sysuser.getUserName());
			sysrg.setCreateUserid(sysuser.getUserId());
			roleGroupService.saveEntity(sysrg);
			sysLogService.log(request, sysuser, "系统管理--角色管理--角色组配置", "添加角色组", "新增角色组【" + roleGroupname + "】", new Date(), "3", new Date(), null);
		}else{
			//编辑保存
			sysrg = roleGroupService.getSysRoleGroupById(roleGroupid);
			if(sysrg != null){
				sysrg.setRoleGroupcode(roleGroupcode);
				sysrg.setRoleGroupname(roleGroupname);
				sysrg.setRoleGroupdesc(roleGroupdesc);
				sysrg.setCreateTime(new Date());
				sysrg.setCreateUsername(sysuser.getUserName());
				sysrg.setCreateUserid(sysuser.getUserId());
				roleGroupService.updateSysRoleGroup(sysrg);
				sysLogService.log(request, sysuser, "系统管理--角色管理--角色组配置", "编辑角色组", "更新角色组【" + roleGroupname + "】", new Date(), "3", new Date(), null);
			}
		}
		
		if(sysrg == null){
			return "n";
		}
		//根据|分割
		String[] roles_arr=roleIds.split("\\|");
		//新增
		if(StringUtils.isEmpty(roleGroupid) && (roles_arr == null || roles_arr.length == 0)){
			return "n";
		}
		if(roles_arr != null && roles_arr.length > 0){
			//循环添加角色
			for (String roleId : roles_arr) {
				if(StringUtils.isNotEmpty(roleId)){
					SysRolegroupRole model = new SysRolegroupRole();
					model.setRoleId(roleId);
					model.setRoleGroupid(sysrg.getRoleGroupid());
					model.setCreateTime(new Date());
					model.setCreateUserid(sysuser.getUserId());
					model.setCreateUsername(sysuser.getUserName());
					roleGroupService.saveEntity(model);
					SysRole sysRole = sysRoleService.getSysRoleById(roleId);
					sysLogService.log(request, sysuser, "系统管理--角色管理--角色组配置", "给角色组赋予角色",
							"给角色组" + sysrg.getRoleGroupname() + "赋予角色【" + sysRole.getRoleName()+"】", new Date(), "4", new Date(), null);
				}
			}
		}
		
		return "y";
	}
	
	/**
     * 删除角色组
     */
    @RequestMapping(value = "delRoleById", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody void delRoleById(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	String roleGroupid = request.getParameter("roleGroupid");
		String result = "0";
		if(StringUtils.isNotEmpty(roleGroupid)){
			String[] roleGroupids = roleGroupid.split(",");
			if(roleGroupids != null && roleGroupids.length >= 0){
				for (String rgid : roleGroupids) {
					SysRoleGroup sysrg = roleGroupService.getSysRoleGroupById(rgid);
					roleGroupService.delRoleById(rgid);
					sysLogService.log(request, getVisitor(request), "系统管理--角色管理--角色组配置", "删除角色组", "删除角色组【" + sysrg.getRoleGroupname() + "】", new Date(), "3", new Date(), null);
				}
			}
		}
    	result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);
    }
    
    /**
     * 删除角色组角色
     */
    @RequestMapping(value = "delGroupRole", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String delGroupRole(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	String roleGroupid = request.getParameter("roleGroupid");
    	String roleId = request.getParameter("roleId");
    	String result = "0";
    	roleGroupService.delGroupRoleById(roleGroupid,roleId);
    	SysRole sysrole = sysRoleService.getSysRoleById(roleId);
    	SysRoleGroup sysrg = roleGroupService.getSysRoleGroupById(roleGroupid);
    	sysLogService.log(request, getVisitor(request), "系统管理--角色管理--角色组配置", "删除角色", "将角色组" + sysrg.getRoleGroupname() + "角色【" + sysrole.getRoleName() + "】删除", new Date(), "3", new Date(), null);
    	result = "1";
		return result;
    }
	/**
	 * 在登录人的角色范围内按角色名称查询角色————
	 * @param request
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "getRoleByRoleName", method = { RequestMethod.GET,RequestMethod.POST })
	public String gueryRoleByName(HttpServletRequest request, String roleName,String roleGroupid) throws Exception {
			List<SysRole> list = (List<SysRole>) roleGroupService.querySysRoleByGroupId(roleGroupid);
			
			List<SysRole> not_list = (List<SysRole>) roleGroupService.querySysRoleNotInList(list,roleName);
			if(not_list==null || not_list.size()==0){
				return "n";
			}
			request.setAttribute("listRoles", not_list);
		return "sys/role/selectRoleByName";
	}

}
