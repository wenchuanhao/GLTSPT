package com.cdc.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysRole;
import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.form.SysRoleForm;
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
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.system.core.service.IStartUpService;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 角色管理__查询角色
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/role/*")
public class SysSearchRoleController extends CommonController {

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
	 * 查询所有角色
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "querySysRole", method = { RequestMethod.GET, RequestMethod.POST })
	public String querySysRole(HttpServletRequest request, SysRoleForm form) {
		try {
			SysParameter param=SysParamHelper.getSysParamByCode("SYSID");
			SysUser loginedUser = getVisitor(request);
				form.setIsDefaultRole("1");
				ItemPage itemPage = roleService.querySysRole(form);
				if(itemPage!=null && itemPage.getItems()!=null && itemPage.getItems().size()>0){
				  List<SysRole> list = (List<SysRole>)itemPage.getItems();
					for(int i=0;i<list.size();i++){
						SysRole role = (SysRole)list.get(i);
						if(role!=null && role.getCreaterId()!=null){
							SysUser user = sysUserService.getSysUserById(role.getCreaterId());
							if(user!=null && user.getUserName()!=null){
								role.setCreatePerson(user.getUserName());
							}
						}
					}
					itemPage.setItems(list);
				}
				request.setAttribute("form", form);
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/querySysRole";
	}
	
}
