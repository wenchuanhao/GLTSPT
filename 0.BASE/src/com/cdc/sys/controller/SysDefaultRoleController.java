package com.cdc.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysRole;
import model.sys.entity.SysRoleSet;
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
 * @Description: 角色管理_默认角色管理
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/role/*")
public class SysDefaultRoleController extends CommonController {

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
	 * 角色配置首页__默认
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "defaultroleConfigIndex", method = { RequestMethod.GET, RequestMethod.POST })
	public String defaultRoleConIndex(HttpServletRequest request, SysRoleForm form) {
		try {
			SysParameter param=SysParamHelper.getSysParamByCode("SYSID");
			SysUser loginedUser = getVisitor(request);
				form.setIsDefaultRole("2");
				ItemPage itemPage = roleService.querySysRole(form);
				if(itemPage!=null && itemPage.getItems()!=null && itemPage.getItems().size()>0){
				  List<SysRole> list = (List<SysRole>)itemPage.getItems();
				  List<SysRole> list2 = new ArrayList<SysRole>();
					for(int i=0;i<list.size();i++){
						SysRole role = (SysRole)list.get(i);
						list2.add(setCrpAndPzRole(role));
						
					}
					itemPage.setItems(list2);
				}
				request.setAttribute("form", form);
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/defaultRoleConfigIndex";
	}
	
	/**
	 * 设置创建人和已分配角色
	 * @throws Exception 
	 */
	public SysRole setCrpAndPzRole(SysRole role) throws Exception{
		if(role!=null && role.getCreaterId()!=null && !role.getCreaterId().equals("")){//用来封装创建人姓名
			SysUser user = sysUserService.getSysUserById(role.getCreaterId());
			if(user!=null && user.getUserName()!=null){
				role.setCreatePerson(user.getUserName());
			}else{
				role.setCreatePerson("未知");
			}
		}else{
			role.setCreatePerson("未知");
		}
	  if(role!=null){
		  String roleNames="";
		  List<SysRoleSet> list = sysRoleSetService.getListSysRoleSetByRoleId(role.getRoleId());
		  List<SysRole> list_role = roleService.listAllQuery();
		  Map<String, String> map= new HashMap<String, String>();
		  if(list_role !=null && list_role.size()>0){
			  for(int i=0;i<list_role.size();i++){
				  map.put(list_role.get(i).getRoleId(), list_role.get(i).getRoleName());
			  }
		  }
		 if(list !=null && list.size()>0){
			 for(int j=0;j<list.size();j++){
				 roleNames += map.get(list.get(j).getParentId())+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			 }
		 }
		 
		 if(roleNames!=null && !roleNames.equals("")){
			 role.setPzRole(roleNames);
		 }else{
			 role.setPzRole("暂无");
		 }
	  }
		return role;
	}
}
