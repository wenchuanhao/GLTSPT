package com.cdc.sys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysRole;
import model.sys.entity.SysRoleGroup;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.sys.form.SysUserForm;
import com.cdc.sys.service.ISysPrivilegesService;
import com.cdc.sys.service.ISysRoleGroupService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 
 * 
 * @Description: 角色组配置管理
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-29 下午5:33:45
 * @UpdateRemark:
 * @Version: V1.0
 */
@Controller
@RequestMapping("/sys/rolegroup/*")
public class SysRoleGroupCFGController extends CommonController {
	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private ISysRoleGroupService roleGroupService;

	@Autowired
	private ISysPrivilegesService privilegesService;

	@RequestMapping(value = "cfgRoleGroup", method = { RequestMethod.GET, RequestMethod.POST })
	public String cfgRoleGroup(HttpServletRequest request, SysUserForm form) {
		try {
			SysUser loginedUser = getVisitor(request);
			// 得到登录用户下的所有组织机构用户
			String orgId = loginedUser.getOrganizationId();
			ItemPage itemPage = sysUserService.getUserByOrgId(orgId, form,loginedUser.getUserId());
			request.setAttribute("ITEMPAGE", itemPage);
			request.setAttribute("form", form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/sys/rolegroup/cfgRoleGroup";
	}

	@RequestMapping(value = "groups/{userId}", method = RequestMethod.GET)
	public String listGroup(HttpServletRequest request, @PathVariable String userId) {
		try {
			SysUser loginedUser = getVisitor(request);
			List<String> roleIds = privilegesService.queryRoleIdByUserId(userId);
			List<?> list = roleGroupService.listRoleGroupByUserId(loginedUser.getUserId());
			List<SysRoleGroup> roleGroups = new ArrayList<SysRoleGroup>();
			for (Object object : list) {
				if (object != null) {
					Object[] objs = (Object[]) object;
					if (!roleGroups.contains((SysRoleGroup) objs[0]))
						roleGroups.add((SysRoleGroup) objs[0]);
				}
			}
			for (SysRoleGroup roleGroup : roleGroups) {
				List<SysRole> roles = new ArrayList<SysRole>();
				for (Object object : list) {
					if (object != null) {
						Object[] objs = (Object[]) object;
						SysRoleGroup rolegGroupTemp = (SysRoleGroup) objs[0];
						SysRole role = (SysRole) objs[1];
						if (rolegGroupTemp.getRoleGroupid().equals(roleGroup.getRoleGroupid())) {
							if (roleIds.contains(role.getRoleId()))
//								role.setAllowUpdate("99");
							roles.add(role);
						}

					}
				}
//				roleGroup.setRoles(roles);
			}
			request.setAttribute("userId", userId);
			request.setAttribute("user", sysUserService.getSysUserById(userId));
			request.setAttribute("roleGroups", roleGroups);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/rolegroup/groups";
	}

	@RequestMapping(value = "assignUserRole", method = RequestMethod.POST)
	public @ResponseBody String assignUserRole(String roleId, String userId, String allRole) {
		try {	
				//先删除
				privilegesService.deleteRoleVisitorByRIdUId(allRole.substring(0,allRole.length()-1), userId);
				//再添加
				List<SysRoleUser> roleUser=new ArrayList<SysRoleUser>();
				if (null != roleId && !"".equals(roleId)) {
					String[] ids = roleId.split(",");
						try {
							for (String rId : ids) {
								roleUser.add(new SysRoleUser(userId, rId));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
				if(roleUser.size()!=0){
					privilegesService.addRoleVisitor(roleUser);
				}
				return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
