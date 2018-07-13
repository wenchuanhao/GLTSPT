package com.cdc.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysUserForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysPrivilegesService;
import com.cdc.sys.service.ISysRoleGroupService;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.sys.service.ISysRoleUserService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 用户管理>>删除用户
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/user/*")
public class SysDeleteUserController extends CommonController {

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
	 * 所有用户列表(有删除权限)
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryUserInDel", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryUserInDel(HttpServletRequest request, SysUserForm form) {
		try {
			String st=request.getParameter("st");
			if(st!=null&&st!=""){
				request.setAttribute("st", st);
			}
			request.setAttribute("orgs", organizationService.queryAll());
			if(form!=null && form.getOrganizationId()!=null && !form.getOrganizationId().equals("")){
				String ids = organizationService.getOrgIdsByOrgId(form.getOrganizationId(), "");
				form.setuDepIds(ids);
			}
			ItemPage itemPage = sysUserService.querySysUser2(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/queryUserInDel";
	}

	
}
