package com.cdc.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysModule;
import model.sys.entity.SysOrganization;
import model.sys.entity.SysRoleOrg;
import model.sys.entity.SysRolePrivilges;
import model.sys.entity.SysRoleUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysUserForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysPrivilegesService;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.system.core.authentication.controller.CommonController;

/**
 * 
 * @Description: 系统权限管理 <br>
 *               角色权限的分配<br>
 *               角色用户的分配<br>
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午06:09:35
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping("/sys/privilege/*")
public class SysPrivilgesController extends CommonController {
	/**
	 * 模块Service
	 */
	@Autowired
	private ISysPrivilegesService privilegesService;
	/**
	 * 角色Service
	 */
	@Autowired
	private ISysRoleService roleService;
	/**
	 * 日志Service
	 */
	@Autowired
	private ISysLogService sysLogService;


	/**
	 * 列出系统的模块
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listModule/{roleId}", method = RequestMethod.GET)
	public String list(HttpServletRequest request, @PathVariable String roleId) {
		try {
			// 系统权限
			List<SysModule> modules = privilegesService.queryAll();
			// 查询当前角色的权限
			List<SysRolePrivilges> roleModules = privilegesService.queryRoleModuleByRoleId(roleId);

			// 封装好的权限列表，当前角色的权限会有标识区别 status为3标识当前用户拥有该权限
			List<SysModule> privileges = new ArrayList<SysModule>();

			for (SysModule sysModule : modules) {
				for (SysRolePrivilges roleModule : roleModules) {
					if (sysModule.getModuleCode().equals(roleModule.getModuleCode())) {
						sysModule.setStatus("3");
					}
				}
				privileges.add(sysModule);
			}

			request.setAttribute("privileges", privileges);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/privilege/listModule";
	}

	/**
	 * 列出组织
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listOrg/{roleId}", method = RequestMethod.GET)
	public String listOrg(HttpServletRequest request, @PathVariable String roleId) {
		try {
			// 系统组织
			List<SysOrganization> orgs = privilegesService.queryOrg();
			// 查询当前角色的区域
			List<SysRoleOrg> roleOrgs = privilegesService.queryOrgByRoleId(roleId);

			// 封装好的区域权限，当前区域的权限会有标识区别 flag为3标识当前用户拥有该区域
			List<SysOrganization> roleOrgList = new ArrayList<SysOrganization>();

			for (SysOrganization sysOrg : orgs) {
				for (SysRoleOrg roleOrg : roleOrgs) {
					if (sysOrg.getOrganizationId().equals(roleOrg.getOrgId())) {
						sysOrg.setFlag("3");
					}
				}
				roleOrgList.add(sysOrg);
			}

			request.setAttribute("roleOrgList", roleOrgList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/privilege/listOrg";
	}
	
	
	/**
	 * 用户列表 / 搜索
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "listUser/{roleId}", method = RequestMethod.GET)
	public String getVisitors(@PathVariable String roleId, HttpServletRequest request) {
		request.setAttribute("roleId", roleId);
		request.setAttribute("rand", new Random().nextInt());
		return "sys/privilege/listUser";
	}
	
	/**
	 * 用户列表 / 搜索
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "allocatedUser/{roleId}", method = RequestMethod.GET)
	public String allocatedUser(@PathVariable String roleId, HttpServletRequest request) {
		request.setAttribute("roleId", roleId);
		request.setAttribute("rand", new Random().nextInt());
		return "sys/privilege/allocatedUser";
	}
	/**
	 * 用户权限分配的 <br>
	 * 用户列表
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "getUser/{flag}/{roleId}", method = { RequestMethod.POST, RequestMethod.GET })
	public String getVisitors(@PathVariable String roleId, @PathVariable String flag, HttpServletRequest request, SysUserForm form) {
		try {
			List<SysRoleUser> sysRoleUsers = privilegesService.queryRoleVisitorByRoleId(roleId);
			String orgIds=privilegesService.queryOrgIdsByRoleIds(roleId);
			String userIds = "";
			form.setPageSize(6);
			if (!orgIds.equals("")) {
				orgIds = orgIds.substring(0, orgIds.length() - 1);
			} else {
				orgIds = "1";
			}
			for (SysRoleUser sysRoleUser : sysRoleUsers) {
				userIds += "'" + sysRoleUser.getUserId() + "',";
			}
			if (!userIds.equals("")) {
				userIds = userIds.substring(0, userIds.length() - 1);
			} else {
				userIds = "1";
			}
			form.setUserIds(userIds);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, privilegesService.queryUser(form, flag,orgIds));
			request.setAttribute("roleId", roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag.equals("0")) {
			return "sys/privilege/notVisitors";
		} else {
			return "sys/privilege/visitors";
		}
	}

	/**
	 * 为角色分配权限
	 * 
	 * @param response
	 * @param privileges
	 *            权限id "","",""
	 * @param srId
	 */
	@RequestMapping(value = "assignprivilege", method = RequestMethod.POST)
	public @ResponseBody
	String assignRoleModule(HttpServletRequest request, HttpServletResponse response, String privileges, String srId) {
		String rs = "";
		try {
			if (null != srId && !"".equals(srId)) {
				// 先删除之前的权限和角色的关联关系
				privilegesService.deleteRoleModuleByRoleId(srId);
				if (null != privileges && !",".equals(privileges)) {
					List<SysRolePrivilges> roleModules = new ArrayList<SysRolePrivilges>();
					for (String privilege : privileges.split(",")) {
						roleModules.add(new SysRolePrivilges(srId, privilege));
					}
					String rName = roleService.getSysRoleById(srId).getRoleName();
					privilegesService.addRolePrivilege(roleModules);
					sysLogService.log(request, getVisitor(request),"系统管理-角色权限管理",
							"为角色分配权限", "为" + rName + "角色分配权限 ", new Date(), "3", new Date(), null);
				}
				rs = "ok";
			}
		} catch (Exception e) {
			rs = "error";
			e.printStackTrace();
		}
		return rs;
	}
	

	
	/**
	 * 为角色分配区域
	 * 
	 * @param response
	 * @param privileges
	 *            权限id "","",""
	 * @param srId
	 */
	@RequestMapping(value = "assignOrgs", method = RequestMethod.POST)
	public @ResponseBody
	String assignRoleOrg(HttpServletRequest request, HttpServletResponse response, String orgs, String srId) {
		String rs = "";
		try {
			if (null != srId && !"".equals(srId)) {
				// 先删除之前的权限和角色的关联关系
				privilegesService.deleteRoleOrgByRoleId(srId);
				if (null != orgs && !",".equals(orgs)) {
					List<SysRoleOrg> roleOrgs = new ArrayList<SysRoleOrg>();
					for (String org : orgs.split(",")) {
						roleOrgs.add(new SysRoleOrg(srId, org));
					}
					String rName = roleService.getSysRoleById(srId).getRoleName();
					privilegesService.addRoleOrg(roleOrgs);
					sysLogService.log(request, getVisitor(request),"系统管理-角色权限管理",
							"为角色分配权限", "为" + rName + "角色分配权限 ", new Date(), "3", new Date(), null);
				}
				rs = "ok";
			}
		} catch (Exception e) {
			rs = "error";
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 为角色分配用户
	 * 
	 * @param response
	 * @param visitors
	 *            用户id "","",""
	 * @param srId
	 */
	@RequestMapping(value = "assignvisitor", method = RequestMethod.POST)
	public @ResponseBody
	String assignVisitor(HttpServletRequest request, HttpServletResponse response, String userId, String srId, String flag) {
		try {
			if (null != srId && !"".equals(srId)) {
				String rName = roleService.getSysRoleById(srId).getRoleName();
				// 先删除当前角色下，之前的用户和角色的关联关系
				if (null != userId && !"".equals(userId)) {
					List<SysRoleUser> list = privilegesService.queryRoleVisitorByU_RId(srId, userId);
					if (list != null && list.size() > 0)
						privilegesService.deleteRoleVisitorBySrId(srId, userId);
					// logService.log(request, getVisitor(request),
					// "系统管理-角色权限管理", "删除角色下的用户", "删除" + rName + "角色下的用户");
					if (flag.equals("1")) {
						// 添加角色和用户关系
						privilegesService.addRoleVisitor(new SysRoleUser(userId, srId));
						sysLogService.log(request, getVisitor(request),"系统管理-角色权限管理",
								"为角色分配权限", "为" + rName + "角色分配权限 ", new Date(), "3", new Date(), null);
					}
				}

				return "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "error";
	}
}
