package com.cdc.sys.controller;


import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysOrganization;

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
 * @Description: 用户管理>>组织变更
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/user/*")
public class SysUpdateDepUserController extends CommonController {

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
	 * 跳转到组织变更页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateOrg",  method = { RequestMethod.GET, RequestMethod.POST })
	public String updateOrg(HttpServletRequest request, SysUserForm form) {
		try {
		//	form.setPageSize(5);
			String st=request.getParameter("st");
			if(st!=null&&st!=""){
				request.setAttribute("st", st);
			}
			request.setAttribute("orgs", organizationService.queryAll());
			if(form != null && form.getOrganizationId() != null && !form.getOrganizationId().equals("")){
				SysOrganization modelN = organizationService.getOrgById(form.getOrganizationId());
				String orgidsN = organizationService.getOrgIdsByOrgId(form.getOrganizationId(), modelN.getParentId());
				form.setuDepIds(orgidsN);
			}
			ItemPage itemPage = sysUserService.querySysUser2(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			request.setAttribute("back", "search");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/updateOrg";
	}

}
