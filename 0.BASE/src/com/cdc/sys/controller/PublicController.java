package com.cdc.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysOrganizationForm;
import com.cdc.sys.form.SysUserForm;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

@Controller
public class PublicController extends CommonController{
	
	@Autowired
	private ISysUserService sysUserService;
	

	@Autowired
	private ISysOrganizationService organizationService;
	/**
	 * 跳转到用户页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sys/user/listUser", method = RequestMethod.GET)
	public String toUserList() {
		return "sys/public/listUser";
	}

	/**
	 * 查询所有用户（单选）
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/sys/user/queryUserLists", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queryUserList(HttpServletRequest request, SysUserForm form) {
		try {
			ItemPage itemPage = sysUserService.querySysUser(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/public/queryUserList";
	}
	/**
	 * 用户组织机构树
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/sys/user/dtreeUser", method = { RequestMethod.GET, RequestMethod.POST })
	public String dtreeUser(HttpServletRequest request, SysOrganizationForm form) {
		try {
			request.setAttribute("orgs", organizationService.queryAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/public/userDtree";
	}
	/**
	 * 所有组织（列表）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sys/org/queryOrglist", method = RequestMethod.GET)
	public String queryOrglist(HttpServletRequest request) {
		try {
			request.setAttribute("orgs",organizationService.queryAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/org/orgList";
	}
	/**
	 * 跳转到用户页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sys/user/listUserByGoing", method = RequestMethod.GET)
	public String toUserListByGoing() {
		return "sys/public/listUserByGoing";
	}

	/**
	 * 查询所有用户（多选）
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/sys/user/queryUserListByGoing", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queryUserListByGoing(HttpServletRequest request, SysUserForm form) {
		try {
			ItemPage itemPage = sysUserService.querySysUser(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/public/queryUserListByGoing";
	}
	/**
	 * 用户组织机构树
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/sys/user/dtreeUserByGoing", method = { RequestMethod.GET, RequestMethod.POST })
	public String dtreeUserByGoing(HttpServletRequest request, SysOrganizationForm form) {
		try {
			request.setAttribute("orgs", organizationService.queryAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/public/userDtreeByGoing";
	}
}
