package com.cdc.sys.controller;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import model.sys.entity.SysUserArea;
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
import com.cdc.sys.service.ISysRoleSetService;
import com.cdc.sys.service.ISysRoleUserService;
import com.cdc.sys.service.ISysUserAreaService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.service.IStartUpService;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 角色管理_给用户赋予角色
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/role/*")
public class SysConfigUserRoleController extends CommonController {

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
	@RequestMapping(value = "roleUser", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryUserList(HttpServletRequest request, SysUserForm form) {
		try {
			SysUser loginedUser = getVisitor(request);
			SysRoleUser sysroleuser = sysRoleUserService.getRoleUserByUserId(loginedUser.getUserId());//查询用户角色表
			SysRole sysrole=roleService.getSysRoleById(sysroleuser.getRoleId());//查询登录人所属角色
//			if(sysrole!=null && !sysrole.getRoleCode().equals("admin")){
//				List<SysUserArea> listUserArea = sysUserAreaService.getSysUserAreaByUserId(loginedUser.getUserId());
//				if(listUserArea == null || listUserArea.size()<=0){
//					form.setOrgs("");
//				}else{
//					String orgids = organizationService.getOrgIdsByOrgId(listUserArea.get(listUserArea.size()-1).getOrganizationId(), "");
//					form.setOrgs(orgids);
//				}
//				
//		
//			}
			if(form != null && form.getOrganizationId() != null && !form.getOrganizationId().equals("")){
				SysOrganization modelN = organizationService.getOrgById(form.getOrganizationId());
				String orgidsN = organizationService.getOrgIdsByOrgId(form.getOrganizationId(), modelN.getParentId());
				form.setuDepIds(orgidsN);
			}
			request.setAttribute("orgs", organizationService.queryAll());
			ItemPage itemPage = sysUserService.querySysUser2(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/roleUser";
	}

}
