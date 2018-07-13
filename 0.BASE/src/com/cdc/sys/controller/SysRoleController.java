package com.cdc.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.RUserGroup;
import model.sys.entity.SysModule;
import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRolePrivilges;
import model.sys.entity.SysRoleSet;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import model.sys.entity.SysUserArea;
import model.sys.entity.UserRoleGroup;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;
import org.trustel.util.DateUtils;

import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.model.SysParameterType;
import com.cdc.sys.form.RoleAreaModel;
import com.cdc.sys.form.SysModuleForm;
import com.cdc.sys.form.SysRoleForm;
import com.cdc.sys.form.SysRoleGroupForm;
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
import com.cdc.system.core.cache.DataCache;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.system.core.service.IStartUpService;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 角色管理_系统角色管理
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/role/*")
public class SysRoleController extends CommonController {

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
	 * 角色配置首页__
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "roleConfigIndex", method = { RequestMethod.GET, RequestMethod.POST })
	public String roleConIndex(HttpServletRequest request, SysRoleForm form) {
		try {
			SysParameter param=SysParamHelper.getSysParamByCode("SYSID");
			SysUser loginedUser = getVisitor(request);
				if(StringUtils.isEmpty(form.getIsDefaultRole())){
					form.setIsDefaultRole("1");
				}
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
		return "sys/role/roleConfigIndex";
	}
	
	/**
	 * 给用户配置角色动作————
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "configUserRole", method = RequestMethod.POST)
	public @ResponseBody
	String configUserRole(HttpServletRequest request, HttpServletResponse response, String userId,String roleIds,String selectValue) throws Exception {
			if(userId==null || userId.equals("") || roleIds==null || roleIds.equals("")){
				return "n";
			}
			SysUser user = sysUserService.getSysUserById(userId);
			if(user==null){
				return "n";
			}
			//根据|分割
			String[] roles_arr=roleIds.split("\\|");
			if(roles_arr == null || roles_arr.length == 0){
				return "n";
			}
			//循环添加角色
			for (String roleId : roles_arr) {
				if(StringUtils.isNotEmpty(roleId)){
					//	sysRoleUserService.delRoleUser(userId);//先删除用户原先的角色保持用户角色的唯一性
					SysRoleUser model= new SysRoleUser();
					model.setRoleId(roleId);
					model.setUserId(userId);
					sysRoleUserService.addRoleUser(model);//保存用户新角色
					//	sysUserAreaService.delSysUserArea(userId);//先删除用户区域数据，以最后变更的区域为准
					SysRole sysRole = roleService.getSysRoleById(roleId);
					logService.log(request, getVisitor(request), "系统管理--角色管理--给用户赋予角色", "给用户赋予角色",
							"给用户" + user.getUserName() + "赋予角色" + sysRole.getRoleName(), new Date(), "4", new Date(), null);
				}
			}
			//区域数据为空,则已经成功
			if(selectValue==null || selectValue.trim().equals("")){
				return "y";
			}
			String[] arr=selectValue.split(";");
			if(arr==null || arr.length==0){
				return "n";
			}
			
			for(int i=0;i<arr.length;i++){
				if(arr[i]==null && arr[i].equals("")){
					continue;
				}
				String[] arr2=arr[i].split("\\|");
				if(arr2==null || arr2.equals("")){
					continue;
				}
				if(arr2[0]==null || arr2[0].equals("") || arr2[1]==null || arr2[1].equals("")){
					continue;
				}
				//区域数据一般是单选
				for (String roleId : roles_arr) {
					if(StringUtils.isNotEmpty(roleId)){
						SysUserArea sysmodel = new SysUserArea(); //保存用户区域数据
						sysmodel.setUserId(userId);
						sysmodel.setOrganizationId(arr2[0]);
						sysmodel.setRanks(Integer.parseInt(arr2[1]));
						sysmodel.setRoleId(roleId);//保存角色编码
						sysUserAreaService.addSysUserArea(sysmodel);
					}
				}
			}
			
			
		return "y";
	}

	/**
	 *点击角色查看已分配用户首页———
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "fpRoleUser", method = { RequestMethod.GET,RequestMethod.POST })
	public String fpRoleUser(HttpServletRequest request, SysUserForm form,String roleId,String orgid) {
		try {
			request.setAttribute("orgs", organizationService.queryAll());
			if(orgid!=null && !orgid.equals("") && orgid.equals("80df8fa55ca4048ac2314dab1a52d75e")){
				orgid="";
			}
			ItemPage itemPage = sysUserService.querySysUserByRoleId(form,roleId,orgid);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			request.setAttribute("roleId", roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/fpRoleUser";
	}
	/**
	 *点击角色查看已分配用户首页———默认角色配置
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "fpRoleUserDefault", method = { RequestMethod.GET,RequestMethod.POST })
	public String fpRoleUserDefault(HttpServletRequest request, SysUserForm form,String roleId,String orgid) {
		try {
			request.setAttribute("orgs", organizationService.queryAll());
			if(orgid!=null && !orgid.equals("") && orgid.equals("80df8fa55ca4048ac2314dab1a52d75e")){
				orgid="";
			}
			//ItemPage itemPage = sysUserService.querySysUserByDefaultRoleId(form,roleId,orgid);
			ItemPage itemPage = sysUserService.querySysUserByRoleId(form,roleId,orgid);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			request.setAttribute("roleId", roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/fpRoleUserDefault";
	}
	/**
	 *单个撤消已分配角色用户———
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "deleRolsUser", method = { RequestMethod.GET,RequestMethod.POST })
	public String delRolsUser(HttpServletRequest request, SysUserForm form,String roleId,String userId,String userRoleId) {
		try {
			if(roleId!=null && !roleId.equals("") && userId!=null && !userId.equals("")){
				SysUser sysUser = sysUserService.getSysUserById(userId);
				SysRole sysRole = roleService.getSysRoleById(roleId);
			//	sysRoleUserService.delRoleUser(userId);
				sysRoleUserService.deleteRoleUser(userRoleId);
				logService.log(request, getVisitor(request), "系统管理--角色管理--角色配置", "分配权限",
						"撤销用户" + sysUser.getUserName() + "的" + sysRole.getRoleName() + "角色", new Date(), "4", new Date(), null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/role/fpRoleUser?roleId="+roleId;
	}
	/**
	 *单个撤消已分配角色用户———
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "delUserRole", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody String delUserRole(HttpServletRequest request, SysUserForm form,String roleId,String userId,String userRoleId) {
		String s = "0";
		try {
			if(roleId!=null && !roleId.equals("") && userId!=null && !userId.equals("")){
				SysUser sysUser = sysUserService.getSysUserById(userId);
				SysRole sysRole = roleService.getSysRoleById(roleId);
				//	sysRoleUserService.delRoleUser(userId);
				sysRoleUserService.deleteRoleUser(userRoleId);
				logService.log(request, getVisitor(request), "系统管理--角色管理--角色配置", "分配权限",
						"撤销用户" + sysUser.getUserName() + "的" + sysRole.getRoleName() + "角色", new Date(), "4", new Date(), null);
				s = "1";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	/**
	 *单个撤消已分配角色用户———
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "deleRolsUsers", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody
	String delRolsUsers(HttpServletRequest request, SysUserForm form,String roles,String roleId) {
		try {
			if(roleId!=null && !roleId.equals("") && roles!=null && !roles.equals("")){
				
				String[] userids = roles.split(",");
				if(userids!=null && userids.length>0){
					for(int i=0;i<userids.length;i++){
						sysRoleUserService.deleteRoleUser(userids[i]);
					}
					return "ok";
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 根据用户id与角色id查询区域名称
	 * @return
	 */
	public String getAreaName(String userId,String roleId) throws Exception{
	    String areaNames = ""; //已配置的区域名称
		List<SysUserArea> listUserArea = sysUserAreaService.getSysUserAreaByUserId(userId,roleId);
		if(listUserArea==null || listUserArea.size()<0){
			return "暂无分配区域";
		}
		if(listUserArea.size()==1){
			String orgId=listUserArea.get(0).getOrganizationId();
			if(orgId==null || orgId.equals("")){
				return "暂无分配区域";
			}
			if(orgId.equals("NO")){
				return "暂无分配区域";
			}
			if(orgId.equals("ALL")){
				return "全部区域";
			}
		}
		if(listUserArea!=null && listUserArea.size()>=1){
			for(int i=0;i<listUserArea.size();i++){
				if(listUserArea.get(i)!=null && listUserArea.get(i).getOrganizationId()!=null){
					SysOrganization org = organizationService.getOrgById(listUserArea.get(i).getOrganizationId());
					if(org != null && org.getOrgName() !=null){
						if(areaNames == null || areaNames.equals("")){
							areaNames = "ROOT>>"+org.getOrgName();
						}else{
							areaNames+=">>"+org.getOrgName();
						}
					}
				}
			}
		}
		if(areaNames == null || areaNames.equals("")){
			areaNames="暂无分配区域";
		}
		return areaNames;
	}
	/**
	 * 用户配置角色页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "cofigUserRole/{userId}", method = RequestMethod.GET)
	public String sysUserInfo(HttpServletRequest request,@PathVariable String userId) {
		try {
			SysUser user = sysUserService.getSysUserById(userId);
			if(user!=null){
				String js="不接受";
				if(user.getIsReceiveSms()!=null&&user.getIsReceiveSms().equals("1")){
					js="接受";
				}else{
					if(user.getIsReceiveSms()!=null&&user.getIsReceiveSms().equals("0")){
						
					}else{
						js="未知";
					}
					
				}
				String zt="禁用";
				if(user.getIsActivate()!=null&&user.getIsActivate().equals("1")){
					zt="正常";
				}else{
					if(user.getIsActivate()!=null&&user.getIsActivate().equals("0")){
						
					}else{
						zt="未知";
					}
					
				}
				request.setAttribute("js", js);
				request.setAttribute("zt", zt);
		        SysRole role= roleService.getSysRoleById(user.getUserDefaultRole());
		        request.setAttribute("ro", role.getRoleName());
		        SysOrganization orgs=organizationService.getOrgById(user.getOrganizationId());
		        request.setAttribute("orga", orgs.getOrgName());
		        request.setAttribute("user", user);
		        request.setAttribute("isN", "y");
		        //当前登录人的角色
		        request.setAttribute("listRoles",getRoleByLoginUser(getVisitor(request),request,userId));
		        //当前登录人的所在区域
		        request.setAttribute("listArea",getUserAreaByLoginUser(getVisitor(request),request));
		        //当前登录人的所在区域的最一级可以动态选择
		        request.setAttribute("nextArea",getNextByLoginUser(getVisitor(request),request));
		        
			}else{
				  request.setAttribute("isN", "n");
			}
			/**
			 * 查询用户所属的角色
			 */
			List<SysRoleUser> listRoles = sysRoleUserService.getRoleUsersByUserId(userId);
			List<RoleAreaModel> roleAreaList = new ArrayList<RoleAreaModel>();
			if(listRoles==null || listRoles.size()==0){
				roleAreaList=null;
			}
			if(listRoles!=null && listRoles.size()>0){
				for(int i=0;i<listRoles.size();i++){
					SysRole model=roleService.getSysRoleById(listRoles.get(i).getRoleId());
					String sysAreaName=getAreaName(userId,listRoles.get(i).getRoleId());
					if(model!=null){
						RoleAreaModel roleAreaModel = new RoleAreaModel();
						roleAreaModel.setRoleName(model.getRoleName());
						roleAreaModel.setRoleCode(model.getRoleCode());
						roleAreaModel.setRoleId(model.getRoleId());
						roleAreaModel.setRoleuserId(listRoles.get(i).getRoleuserId());
						roleAreaModel.setAreaName(sysAreaName);
						roleAreaList.add(roleAreaModel);
					}
				}
			}
			String areasFlag="n";
			if(roleAreaList!=null && roleAreaList.size()>0){
				areasFlag="y";
			}
			request.setAttribute("areasFlag", areasFlag);//有无角色区域标识
			request.setAttribute("roleAreaList", roleAreaList);//角色区域集合
			
			
			request.setAttribute("location", "sys/user/queryUser");
			request.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/cofigUserRole";
	}
	/**
	 * 获取登录人的角色————
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	public List<SysRole> getRoleByLoginUser(SysUser loginUser,HttpServletRequest request,String userId) throws Exception{
		List<SysRole> list = roleService.listAllByType("1"); //查询所有角色
		if(loginUser==null) {
			return null;
		}
		
		List<SysRoleUser> roleUserList = sysRoleUserService.getRoleUsersByUserId(loginUser.getUserId());//获取用户角色列表
		if(roleUserList == null || roleUserList.size()<=0){
			return null;
		}
        for(int i=0;i<roleUserList.size();i++){
        	SysRole model = roleService.getSysRoleById(roleUserList.get(i).getRoleId());//获取角色名称
        	if(model==null){
        		continue;
        	}
        	if(model.getRoleCode()==null || model.getRoleCode().equals("")){
        		continue;
        	}
        	if(model.getRoleCode().equals("admin")){
        		request.setAttribute("isAdmin","1");
        		break;
        	}else{
        		request.setAttribute("isAdmin","0");
        	}
        }
		
		
		
		
		
//    	SysRoleUser sysroleuser = sysRoleUserService.getRoleUserByUserId(loginUser.getUserId());//查询用户角色表
//    	if(sysroleuser==null) {
//    		return null;
//    	}
//        SysRole sysrole=roleService.getSysRoleById(sysroleuser.getRoleId());//查询登录人所属角色
//    	if(sysrole==null){
//    		return null;
//    	}
//        if(sysrole.getRoleCode()==null || sysrole.getRoleCode().equals("")){
//        	return null;
//        }
//		if(sysrole.getRoleCode().equals("admin")){//判断是不是系统管理员
//			request.setAttribute("isAdmin","1");
//			//return list;//如果是系统管理员直接返回所有角色列表，否则继续角色设置表
//		}else{
//			request.setAttribute("isAdmin","0");
//		}
		
		
		
		if(1==1){
			
			   List<SysRoleUser> listRoleUser = sysRoleUserService.getRoleUsersByUserId(userId);
		        String ids = "";
		        if(listRoleUser != null && listRoleUser.size()>0){
		        	for (SysRoleUser rol : listRoleUser) {
		        		ids += "'" + rol.getRoleId() + "',";
					}
		        }
		        if(ids !=null && !ids.trim().equals("")){
		        	ids = ids.substring(0,ids.length()-1);
		        }
		        List<SysRole>   listRoles = roleService.queryRoleByIds(ids);
			
			return listRoles;
		}
		
		//List<SysRoleSet> listRoleSet = sysRoleSetService.getListSysRoleSetByRoleId(sysroleuser.getRoleId());
		//System.out.println("listRoleSet的长度为"+listRoleSet.size());
//		if(listRoleSet==null || listRoleSet.size()<=0){
//			return null;
//		}
		if(list==null || list.size()<=0){
			return null;
		}
		List<SysRole> rolelist= new ArrayList<SysRole>();
		/*for(int i=0;i<listRoleSet.size();i++){//跟角色列表匹配，找出登录人对应的角色设置的角色
			for(int j=0;j<list.size();j++){
				if(listRoleSet.get(i).getRoleId().equals(list.get(j).getRoleId())){
					rolelist.add(list.get(j));
					continue;
				}
			}
		}*/
		String s = "";
//		if(listRoleSet!=null && listRoleSet.size()>0){
//			for(int i=0;i<listRoleSet.size();i++){//跟组织列表匹配，找出登录人对应的区域设置的区域
//				SysRoleSet sua = listRoleSet.get(i);
//				if(listRoleSet.size() == 1) {
//					s = "'"+sua.getParentId()+"'";
//				}else {
//					s += "'"+sua.getParentId()+"',";
//				}
//			}
//			if(listRoleSet.size() != 1) {
//				if(!s.equals("")){
//					s = s.substring(0,s.length()-1);
//				}
//			}
//			//System.out.println("s的值为"+s);
//			rolelist = roleService.getRoleByIds(s);
//		}
		if(rolelist==null || rolelist.size()<=0){ //如果登录人所属角色没有设置角色则将登录人所属角色作为配置角色
			rolelist= new ArrayList<SysRole>();
			//rolelist.add(sysrole);
		}
		return rolelist;
	}
	/**
	 * 获取登录人所在区域__除去最后一个————
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	public List<SysOrganization> getUserAreaByLoginUser(SysUser loginUser,HttpServletRequest request) throws Exception{
		List<SysOrganization> list = organizationService.queryAllNoRoot();
		if(loginUser==null) {
			return null;
		}
		//现在不用区分是否是系统管理员角色admin
		if(1>0){
			request.setAttribute("isAdmin","1");
			return list;
		}
		
		List<SysRoleUser> roleUserList = sysRoleUserService.getRoleUsersByUserId(loginUser.getUserId());//获取用户角色列表
		if(roleUserList == null || roleUserList.size()<=0){
			return null;
		}
        for(int i=0;i<roleUserList.size();i++){
        	SysRole model = roleService.getSysRoleById(roleUserList.get(i).getRoleId());//获取角色名称
        	if(model==null){
        		continue;
        	}
        	if(model.getRoleCode()==null || model.getRoleCode().equals("")){
        		continue;
        	}
        	if(model.getRoleCode().equals("admin")){
        		return list;//如果是系统管理员直接返回所有组织列表，否则继续查询区域设置表
        	}
        }
		
		
		
		
//    	SysRoleUser sysroleuser = sysRoleUserService.getRoleUserByUserId(loginUser.getUserId());//查询用户角色表
//    	if(sysroleuser==null) {
//    		return null;
//    	}
//        SysRole sysrole=roleService.getSysRoleById(sysroleuser.getRoleId());//查询登录人所属角色
//    	if(sysrole==null){
//    		return null;
//    	}
//        if(sysrole.getRoleCode()==null || sysrole.getRoleCode().equals("")){
//        	return null;
//        }
//		if(sysrole.getRoleCode().equals("admin")){//判断是不是系统管理员
//			return list;//如果是系统管理员直接返回所有组织列表，否则继续查询区域设置表
//		}
		List<SysUserArea> listUserArea = sysUserAreaService.getSysUserAreaByUserId(loginUser.getUserId());
		//System.out.println("listUserArea的长度="+listUserArea.size());
		if(listUserArea==null || listUserArea.size()<=0){
			request.setAttribute("isAdmin","1");
			return list;
		}
		if(listUserArea.size()==1){
			request.setAttribute("isAdmin","1");
			return list;
		}
		if(list==null || list.size()<=0){
			return null;
		}
		List<SysOrganization> orglist= new ArrayList<SysOrganization>();
		String s = "";
		for(int i=0;i<listUserArea.size();i++){//跟组织列表匹配，找出登录人对应的区域设置的区域
			SysUserArea sua = listUserArea.get(i);
//			if(s == null || s.equals("")){
//				s= "'"+sua.getOrganizationId()+"'";
//			}else{
//				s+=",'"+sua.getOrganizationId()+"'";
//			}
			SysOrganization model = organizationService.getOrgById(sua.getOrganizationId());
			orglist.add(model);
//			if(listUserArea.size() == 1) {
//				s = "'"+sua.getOrganizationId()+"'";
//			}else {
//				s += "'"+sua.getOrganizationId()+"',";
//			}
		}
//		if(listUserArea.size() != 1) {
//			if(!s.equals("")){
//				s = s.substring(0,s.length()-1);
//			}
//		}
	//	orglist = organizationService.getOrgByIds(s);
		return orglist;
	}
	/**
	 * 获取登录人所在区域__最后一个————
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	public List<SysOrganization> getNextByLoginUser(SysUser loginUser,HttpServletRequest request) throws Exception{
		if(loginUser==null) {
			return null;
		}
		
		
		List<SysRoleUser> roleUserList = sysRoleUserService.getRoleUsersByUserId(loginUser.getUserId());//获取用户角色列表
		if(roleUserList == null || roleUserList.size()<=0){
			return null;
		}
        for(int i=0;i<roleUserList.size();i++){
        	SysRole model = roleService.getSysRoleById(roleUserList.get(i).getRoleId());//获取角色名称
        	if(model==null){
        		continue;
        	}
        	if(model.getRoleCode()==null || model.getRoleCode().equals("")){
        		continue;
        	}
        	if(model.getRoleCode().equals("admin")){
        		return null;//如果是系统管理员直接返回所有组织列表，否则继续查询区域设置表
        	}
        }
		//不用区分是否是系统管理员
		if(1>0){
			return null;
		}
		
//    	SysRoleUser sysroleuser = sysRoleUserService.getRoleUserByUserId(loginUser.getUserId());//查询用户角色表
//    	if(sysroleuser==null) {
//    		return null;
//    	}
//        SysRole sysrole=roleService.getSysRoleById(sysroleuser.getRoleId());//查询登录人所属角色
//    	if(sysrole==null){
//    		return null;
//    	}
//        if(sysrole.getRoleCode()==null || sysrole.getRoleCode().equals("")){
//        	return null;
//        }
//		if(sysrole.getRoleCode().equals("admin")){//判断是不是系统管理员
//			request.setAttribute("ishave", "2");
//			return null;//如果是系统管理员直接返回所有组织列表，否则继续查询区域设置表
//		}
		
		
		
		List<SysUserArea> listUserArea = sysUserAreaService.getSysUserAreaByUserId(loginUser.getUserId());
		
		if(listUserArea==null || listUserArea.size()<=0){
			request.setAttribute("ishave", "2");
			return null;
		}
		if(listUserArea.size()==1){
			request.setAttribute("ishave", "2");
			return null;
		}
		int j=0;
		j=listUserArea.size()-1;
		List<SysOrganization> orglist= organizationService.queryNextLevelOrgByOrgId(listUserArea.get(j).getOrganizationId());
		if(orglist==null || orglist.size()<=0){
			orglist = new ArrayList<SysOrganization>();
			SysOrganization sys = organizationService.getOrgById(listUserArea.get(j).getOrganizationId());
			//orglist=new ArrayList<SysOrganization>();
			//orglist.add(sys);
			orglist = null;
			request.setAttribute("ishave", "2");
		}else{
			request.setAttribute("ishave", "1");
		}
		return orglist;
	}
	/**
	 * 在登录人的角色范围内按角色名称查询角色————
	 * @param request
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "getRoleByRoleName", method = { RequestMethod.GET,RequestMethod.POST })
	public String gueryRoleByName(HttpServletRequest request, String roleName,String userId) throws Exception {
			List<SysRole> list=getRoleByLoginUser(getVisitor(request),request,userId);
			if(list==null || list.size()<=0){
				return "n";
			}
			if(roleName.trim()==null || roleName.trim().equals("")){
				request.setAttribute("listRoles", list);
				return "sys/role/selectRoleByName";
			}
			List<SysRole> newList = new ArrayList<SysRole>();
			
			for(int i=0;i<list.size();i++){
				if(list.get(i).getRoleName().indexOf(roleName)!=-1){
					newList.add(list.get(i));
				}
			}
			if(newList==null || newList.size()==0){
				return "n";
			}
			request.setAttribute("listRoles", newList);
		return "sys/role/selectRoleByName";
	}
	/**
	 * 查询所有角色
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "querySysRoleInDel", method = { RequestMethod.GET, RequestMethod.POST })
	public String querySysRoleInDel(HttpServletRequest request, SysRoleForm form) {
		try {
			SysParameter param=SysParamHelper.getSysParamByCode("SYSID");
			SysUser loginedUser = getVisitor(request);
			List<String> list=privilegesService.queryRoleIdByUserId(loginedUser.getUserId());
			String roleIds="";
			if(list.contains(param.getParameterValue())){
				form.setIsAdminstrator("0");
				ItemPage itemPage = roleService.querySysRole(form);
				request.setAttribute("form", form);
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			}else{
				for(String roleId:list){
					roleIds+="'" + roleId + "',";
				}
				form.setIsAdminstrator("1");
				if(!roleIds.equals("")){
					roleIds=roleIds.substring(0,roleIds.length()-1);
				}else{
					roleIds="1";
				}
					ItemPage itemPage = roleService.querySysRoleInNotIstrator(form, roleIds);
					request.setAttribute("form", form);
					request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/querySysRoleInDel";
	}
	/**
	 * 进入新增角色页面————
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addSysRole", method = RequestMethod.GET)
	public String toAddSysRole(HttpServletRequest request,String flag) {
		request.setAttribute("flag", flag);
		if(flag.equals("1")){
			request.setAttribute("location", "sys/role/roleConfigIndex");
		}else{
			request.setAttribute("location", "sys/role/roleConfigIndex?isDefaultRole=2");
		}		
		return "sys/role/addSysRole";

	}

	/**
	 * 新增角色
	 * 
	 * @param request
	 * @param sysRole
	 * @return
	 */
	@RequestMapping(value = "saveSysRole", method = RequestMethod.POST)
	public String doAddSysRole(HttpServletRequest request, SysRole sysRole) {
		try {
			sysRole.setCreateTime(DateUtils.getToday(true, "GM+8"));
			sysRole.setCreaterId(getVisitor(request).getUserId());
			roleService.addSysRole(sysRole);
			logService.log(request, getVisitor(request), "系统管理--角色管理", "添加角色", "新增角色【" + sysRole.getRoleName() + "】", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(sysRole.getIsDefaultRole().equals("2")){
		  return	"redirect:/sys/role/roleConfigIndex?isDefaultRole=2";
		}
		return "redirect:/sys/role/roleConfigIndex";

	}

	/**
	 * 修改角色页面————
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "modifySysRole/{roleId}", method = RequestMethod.GET)
	public String toModifySysRole(HttpServletRequest request, @PathVariable String roleId) {
		try {
			SysRole sysRole=roleService.getSysRoleById(roleId);			
			request.setAttribute("role", roleService.getSysRoleById(roleId));			
			request.setAttribute("location", "sys/role/roleConfigIndex?isDefaultRole=2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/modifySysRole";
	}
	
	@RequestMapping(value = "modifySysRoleAdd/{roleId}", method = RequestMethod.GET)
	public String toModifySysRoleAdd(HttpServletRequest request, @PathVariable String roleId) {
		try {
			SysRole sysRole=roleService.getSysRoleById(roleId);
			
			request.setAttribute("role", roleService.getSysRoleById(roleId));
			request.setAttribute("location", "sys/role/roleConfigIndex?isDefaultRole="+(sysRole!=null?sysRole.getIsDefaultRole():"1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/modifySysRole";
	}
	/**
	 * 进入角色配置权限页面__
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "selectModuleIndex/{roleId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String selectModuleIndex(HttpServletRequest request,SysModuleForm form,@PathVariable String roleId) {
		try {
			// 系统权限
			List<SysModule> modules = privilegesService.queryAll();
			// 查询当前角色的权限
			List<SysRolePrivilges> roleModules = privilegesService.queryRoleModuleByRoleId(roleId);

			// 封装好的权限列表，当前角色的权限会有标识区别 status为3标识当前用户拥有该权限
			List<SysModule> privileges = new ArrayList<SysModule>();
			for (SysModule sysModule : modules) {
				if(null!=roleModules && roleModules.size()>0){
					for (SysRolePrivilges roleModule : roleModules) {
						if (sysModule.getModuleCode().equals(roleModule.getModuleCode())) {
							sysModule.setStatus("3");
						}
					}
				}
				
				privileges.add(sysModule);
			}
			request.setAttribute("form", form);
			request.setAttribute("roleId", roleId);
			request.setAttribute("privileges", privileges);
		} catch (Exception e) {
           e.printStackTrace();
		}
	
		return "sys/role/selectModuleIndex";
	}
	/**
	 * 角色配置权限动作————
	 * 
	 * @param response
	 * @param privileges
	 *            权限id "","",""
	 * @param srId
	 */
	@RequestMapping(value = "saveRoleprivilege", method = RequestMethod.POST)
	public @ResponseBody
	String saveRoleprivilege(HttpServletRequest request, HttpServletResponse response, String privileges, String roleId) {
		String rs = "";
		try {
			if (null != roleId && !"".equals(roleId)) {
				// 先删除之前的权限和角色的关联关系
				privilegesService.deleteRoleModuleByRoleId(roleId);
				if(null != privileges && !",".equals(privileges)){
					List<SysRolePrivilges> roleModules=new ArrayList<SysRolePrivilges>();
					for (String privilege : privileges.split(",")) {
						roleModules.add(new SysRolePrivilges(roleId,privilege));
					}
					
//				    SysRolePrivilges model = new SysRolePrivilges();
//			    	model.setModuleCode("PortalController");
//			    	model.setRoleId(roleId);
//				    roleModules.add(model);
					privilegesService.addRolePrivilege(roleModules);
					SysRole sysRole = roleService.getSysRoleById(roleId);
					logService.log(request, getVisitor(request), "系统管理--角色管理--角色配置", "分配权限",
							"给角色" + sysRole.getRoleName() + "分配" + roleModules.size() + "项权限", new Date(), "4", new Date(), null);
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
	 * 进入角色配置角色页面__
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "selectRoleIndex/{roleId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String selectRoleIndex(HttpServletRequest request,SysRole form,@PathVariable String roleId) {
		try {
			// 系统角色
			List<SysRole> roles = roleService.listAllByCondition(form,roleId);
			// 查询当前角色所配的角色
			List<SysRoleSet> roleSets = sysRoleSetService.getListSysRoleSetByRoleId(roleId);
			List<String> roldIds = new ArrayList<String>();
		//	System.out.println("roleSets的长度"+roleSets.size());
			List<SysRole> listNew = new ArrayList<SysRole>();
			
//			if(roleSets==null || roleSets.size()<=0){
//				listNew = roles;
//			}
		//	System.out.println("roles的长度="+roles.size());
			if(roles!=null && roles.size()>0 && roleSets !=null && roleSets.size()>0){
				
				for(int i =0;i<roles.size();i++){
					for(int j=0;j<roleSets.size();j++){
						if(roles.get(i).getRoleId().equals(roleSets.get(j).getParentId())){
							roldIds.add(roles.get(i).getRoleId());
							break;
						}
					}
				}
			}
			
			if(roleSets==null || roleSets.size()==0){
				
				request.setAttribute("haveis", "y");
			}else{
				roleSets =null;
				roleSets=sysRoleSetService.getListSysRoleSetByRoleIdJL(roleId);
				request.setAttribute("haveis", "n");
				roles = roleService.listAllByCondition(form, roleId, roldIds);
			}
			
			request.setAttribute("form", form);
			request.setAttribute("roleId", roleId);
			request.setAttribute("roledd", roleId);
			//System.out.println("listNew的长度=="+listNew.size());
			request.setAttribute("roles", roles);
			request.setAttribute("roleSets", roleSets);
		} catch (Exception e) {
           e.printStackTrace();
		}
	
		return "sys/role/selectRoleIndex";
	}
	/**
	 * 进入角色配置角色页面__默认
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "selectRoleIndexDefault/{roleId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String selectRoleIndexDefault(HttpServletRequest request,SysRole form,@PathVariable String roleId) {
		try {
			// 系统角色
			List<SysRole> roles = roleService.listAllByCondition(form,roleId);
			// 查询当前角色所配的角色
			List<SysRoleSet> roleSets = sysRoleSetService.getListSysRoleSetByRoleId(roleId);
			List<SysRoleSet> roleSets_n = roleSets;
			List<String> roldIds = new ArrayList<String>();
			//System.out.println("roleSets的长度"+roleSets.size());
			List<SysRole> listNew = new ArrayList<SysRole>();
			
//			
//			System.out.println("roles的长度="+roles.size());
//			if(roles!=null && roles.size()>0 && roleSets !=null && roleSets.size()>0){
//				System.out.println("eeeeeeeeeeeeeeeee");
//				for(int i =0;i<roles.size();i++){
//					System.out.println(roles.get(i).getRoleId()+"--------");
//					for(int j=0;j<roleSets.size();j++){
//						System.out.println(roleSets.get(j).getRoleId()+"****************");
//						if(roles.get(i).getRoleId().equals(roleSets.get(j).getParentId())){
//							roldIds.add(roles.get(i).getRoleId());
//							break;
//						}
//					}
//				}
//			}
			
			if(roleSets==null || roleSets.size()==0){
				
				request.setAttribute("haveis", "y");
			}else{
				roleSets =null;
				roleSets=sysRoleSetService.getListSysRoleSetByRoleIdJL(roleId);
				request.setAttribute("haveis", "n");
			//	roles = roleService.listAllByCondition(form, roleId, roldIds);
			}
			String roleFlag = "n";
			List<SysRole> roleNames = new ArrayList<SysRole>();
			if(form != null && form.getRoleName() != null && !form.getRoleName().equals("")){
			roleFlag = "y";
				if(roleSets_n != null && roleSets_n.size()>=0){
					for(int i=0;i<roleSets_n.size();i++){
						SysRole role = roleService.getSysRoleById(roleSets_n.get(i).getParentId());
						if((role != null && role.getRoleName().indexOf(form.getRoleName())!=-1)){
							roleNames.add(role);
						}
					}
					
				}
			}
			
			request.setAttribute("form", form);
			request.setAttribute("roleId", roleId);
			request.setAttribute("roledd", roleId);
			//System.out.println("listNew的长度=="+listNew.size());
			request.setAttribute("roles", roles);
			request.setAttribute("roleSets", roleSets);
			request.setAttribute("roleFlag", roleFlag);
			request.setAttribute("roleNames", roleNames);
		} catch (Exception e) {
           e.printStackTrace();
		}
	
		return "sys/role/selectRoleIndexDefault";
	}
	/**
	 * 角色设置角色————单击添加链接按钮
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "roleAddRole", method = RequestMethod.POST)
	public String roleAddRole(HttpServletRequest request, String roleids,String pid) {
		try {
			if(roleids!=null && !roleids.equals("") && pid!=null &&  !pid.equals("")){
				//sysRoleSetService.delSysRoleSet(roleid,pid);
				SysRoleSet model = new SysRoleSet();
				    model.setParentId(pid);
				    model.setRoleId(roleids);
				    sysRoleSetService.addSysRoleSet(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/role/selectRoleIndex/"+roleids;
	}
	/**
	 * 角色设置角色————单击复选添加链接按钮
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "saveRoles", method = RequestMethod.POST)
	public @ResponseBody
	String saveRoles(HttpServletRequest request, String roles,String roleId) {
		try {
			if(roles!=null && !roles.equals("") && roleId!=null &&  !roleId.equals("")){
				String[] nroles = roles.split(",");
				if(nroles==null || nroles.length<=0){
					return "";
				}
				for(int i=0;i<nroles.length;i++){
					SysRoleSet model = new SysRoleSet();
				    model.setParentId(nroles[i]);
				    model.setRoleId(roleId);
				    sysRoleSetService.addSysRoleSet(model);
				}
				loadParam();
				loadRolePrivilges();
				loadModule();
				return "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	private void loadParam() throws Exception {
		DataCache cache = DataCache.getInstance();
		List<SysParameterType> parameterTypes = startUpService.loadParamType();
		List<SysParameter> parameters = startUpService.loadParam();
		cache.setSysParameters(parameters);
		Map<String, List<SysParameter>> temp = new HashMap<String, List<SysParameter>>();
		for (SysParameterType sysParameterType : parameterTypes) {
			List<SysParameter> temp1 = new ArrayList<SysParameter>();
			for (SysParameter parameter : parameters) {
				if (parameter.getParameterTypeId().equals(sysParameterType.getParameterTypeId()))
					temp1.add(parameter);
			}
			temp.put(sysParameterType.getParameterTypeCode(), temp1);
		}
		cache.setParameters(temp);
		System.out.println("====>>加载系统参数成功");

	}

	private void loadRolePrivilges() throws Exception {
		System.out.println("====>>开始加载系统权限");
		List<SysRolePrivilges> rolePrivilges = startUpService.loadRolePrivilges();
		List<SysRole> roles = startUpService.loadRole();
		Map<String, List<String>> sysPrivilges = new HashMap<String, List<String>>();
		for (SysRole sysRole : roles) {
			List<String> privilges = new ArrayList<String>();
			for (SysRolePrivilges sysRolePrivilges : rolePrivilges) {
				if (sysRolePrivilges.getRoleId().equals(sysRole.getRoleId()))
					privilges.add(sysRolePrivilges.getModuleCode());
			}
			sysPrivilges.put(sysRole.getRoleName(), privilges);
		}
		DataCache.getInstance().setSysPrivilges(sysPrivilges);
		System.out.println("====>>加载系统权限成功");
	}

	private  void loadModule() throws Exception {
		System.out.println("====>>开始加载系统模块");
		List<?> list = startUpService.loadModule();
		List<SysModule> listTemp = new ArrayList<SysModule>();
		List<SysModule> listTemp1 = new ArrayList<SysModule>();
		List<SysModule> oneMenu = new ArrayList<SysModule>();
		Map<String, List<SysModule>> twoMenu = new HashMap<String, List<SysModule>>();
		Map<String, List<SysModule>> threeMenu = new HashMap<String, List<SysModule>>();
		Map<String, List<SysModule>> fourMenu = new HashMap<String, List<SysModule>>();
		/*************
		 * 
		 * 加载需要权限校验的url
		 * **************
		Map<String, SysModule> map = new HashMap<String, SysModule>();
		for(Object s :list){
			SysModule module = (SysModule) s;
			map.put(module.getUrl(), module);
		}*/
		System.out.println("====>>加载系统参数成功(List<SysModule>)的长度"+list.size());
		if (null != list && 0 < list.size()) {
			// 一级菜单
			for (Object obj : list) {
				if (obj != null) {
					SysModule module = (SysModule) obj;
					if (null != module.getParentCode() && !"".equals(module.getParentCode()) && !"0".equals(module.getIsMenu()))
						if (module.getParentCode().equals("ROOT"))
							oneMenu.add(module);
						else
							listTemp.add(module);
				}
			}
			for (SysModule sysModule : listTemp) {
				if (sysModule.getMenuLevel() != 1)
					listTemp1.add(sysModule);
			}
			listTemp.clear();
			if (null != listTemp1) {
				// 二级菜单
				for (SysModule oneMenuTemp : oneMenu) {
					List<SysModule> temp = new ArrayList<SysModule>();
					for (SysModule sysModule : listTemp1) {
						if (sysModule.getParentCode().equals(oneMenuTemp.getModuleCode()))
							temp.add(sysModule);
					}
					twoMenu.put(oneMenuTemp.getModuleCode(), temp);
				}
				for (SysModule sysModule : listTemp1) {
					if (sysModule.getMenuLevel() != 2)
						listTemp.add(sysModule);
				}
				listTemp1.clear();
				if (null != listTemp) {
					// 三级菜单
					for (Entry<String, List<SysModule>> entry : twoMenu.entrySet()) {
						List<SysModule> twoMenus = entry.getValue();

						for (SysModule twoMenuTemp : twoMenus) {
							List<SysModule> temp = new ArrayList<SysModule>();
							for (SysModule sysModule : listTemp)
								if (sysModule.getParentCode().equals(twoMenuTemp.getModuleCode()))
									temp.add(sysModule);
							threeMenu.put(twoMenuTemp.getModuleCode(), temp);
						}

					}
					for (SysModule sysModule : listTemp) {
						if (sysModule.getMenuLevel() != 3)
							listTemp1.add(sysModule);
					}
					listTemp.clear();
					if (null != listTemp1) {
						// 四级菜单
						for (Entry<String, List<SysModule>> entry : threeMenu.entrySet()) {
							List<SysModule> threeMenus = entry.getValue();
							boolean flag2 = true;
							for (SysModule threeMenuTemp : threeMenus) {
								List<SysModule> temp = new ArrayList<SysModule>();
								for (SysModule sysModule : listTemp1)
									if (flag2 && sysModule.getParentCode().equals(threeMenuTemp.getModuleCode()))
										temp.add(sysModule);
								fourMenu.put(threeMenuTemp.getModuleCode(), temp);
							}
						}
						listTemp1.clear();
					}
				}
			}
		}
		DataCache cache = DataCache.getInstance();
		cache.setOneMenu(oneMenu);
		cache.setTwoMenu(twoMenu);
		cache.setThreeMenu(threeMenu);
		cache.setFourMenu(fourMenu);
	//	cache.setSysModule(map);
		// oneMenu.clear();
		// twoMenu.clear();
		// threeMenu.clear();
		// fourMenu.clear();
		System.out.println("====>>加载系统模块成功");
	}
	/**
	 * 角色设置角色————单击删除链接按钮
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "roleDelRole", method = RequestMethod.POST)
	public String roleDelRole(HttpServletRequest request, String roleId,String pid) {
		try {
			if(roleId!=null && !roleId.equals("") && pid!=null &&  !pid.equals("")){
				
				    sysRoleSetService.delSysRoleSet(roleId,pid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/role/selectRoleIndex/"+roleId;
	}
	/**
	 * 角色详情页面————
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "sysRoleDetail/{roleId}", method = RequestMethod.GET)
	public String detailSysRole(HttpServletRequest request, @PathVariable String roleId) {
		try {
			SysRole sysRole=roleService.getSysRoleById(roleId);
			
			request.setAttribute("role", setCrpAndPzRole(sysRole));
			request.setAttribute("location", "sys/role/roleConfigIndex?isDefaultRole=2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/sysRoleDetail";
	}
	
	@RequestMapping(value = "sysRoleDetailAdd/{roleId}", method = RequestMethod.GET)
	public String detailSysRoleAdd(HttpServletRequest request, @PathVariable String roleId) {
		try {
			SysRole sysRole=roleService.getSysRoleById(roleId);
			
			request.setAttribute("role", setCrpAndPzRole(sysRole));
			request.setAttribute("location", "sys/role/roleConfigIndex?isDefaultRole="+(sysRole!=null?sysRole.getIsDefaultRole():"1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/sysRoleDetail";
	}
	
	@RequestMapping(value = "sysRoleDetailSearch/{roleId}", method = RequestMethod.GET)
	public String sysRoleDetailSearch(HttpServletRequest request, @PathVariable String roleId) {
		try {
			SysRole sysRole=roleService.getSysRoleById(roleId);
			
			request.setAttribute("role", setCrpAndPzRole(sysRole));
			request.setAttribute("location", "sys/role/querySysRole");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/sysRoleDetail";
	}
	
	/**
	 * 修改角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@RequestMapping(value = "modifySysRole", method = RequestMethod.POST)
	public String doModifySysRole(HttpServletRequest request, SysRole sysRole) {
		try {
			
			sysRole.setRoleCode(request.getParameter("roleCode"));
			sysRole.setRoleName(request.getParameter("roleName"));
			sysRole.setRoleDesc(request.getParameter("roleDesc"));
			//sysRole.setCreateTime(DateUtils.getToday(true, "GM+8"));
			roleService.modifySysRole(sysRole);
			logService.log(request, getVisitor(request), "系统管理--角色管理", "编辑角色", "更新角色【" + sysRole.getRoleName() + "】", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(sysRole.getIsDefaultRole().equals("2")){
			return "redirect:/sys/role/roleConfigIndex?isDefaultRole=2";
		}
		return "redirect:/sys/role/roleConfigIndex";
	}
	
	
	/**
	 * to删除角色页面
	 * 
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "delSysRole", method = RequestMethod.GET)
	public String delSysRole(HttpServletRequest request, SysRoleForm form) {
		try {
			SysParameter param=SysParamHelper.getSysParamByCode("SYSID");
			SysUser loginedUser = getVisitor(request);
			List<String> list=privilegesService.queryRoleIdByUserId(loginedUser.getUserId());
			String roleIds="";
			if(list.contains(param.getParameterValue())){
				form.setIsAdminstrator("0");
				ItemPage itemPage = roleService.querySysRole(form);
				request.setAttribute("form", form);
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			}else{
				for(String roleId:list){
					roleIds+="'" + roleId + "',";
				}
				form.setIsAdminstrator("1");
				if(!roleIds.equals("")){
					roleIds=roleIds.substring(0,roleIds.length()-1);
				}else{
					roleIds="1";
				}
					ItemPage itemPage = roleService.querySysRoleInNotIstrator(form, roleIds);
					request.setAttribute("form", form);
					request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/queryDelSysRole";
	}

	/**
	 * 删除角色
	 * 
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "deleteSysRole", method = RequestMethod.POST)
	public String doDeleteSysRole(HttpServletRequest request, String roleIds,String flag) {
		if (null != roleIds && !"".equals(roleIds)) {
			String[] ids = roleIds.split(",");
			String deleteName = "";
			try {
				for (String roleId : ids) {
					SysRole sysRole = roleService.getSysRoleById(roleId);
					sysRoleUserService.deleteRoleUserByRoleId(roleId);//先删除对应的用户
					roleService.deleteSysRole(roleId);
					deleteName += sysRole.getRoleName() + "，";
				}
				logService.log(request, getVisitor(request), "系统管理--角色管理", "删除角色", "删除角色【" + deleteName.substring(0, deleteName.length()-1) + "】", new Date(), "3", new Date(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(flag.equals("2")){
			return "redirect:/sys/role/roleConfigIndex?isDefaultRole=2";
		}
		return "redirect:/sys/role/roleConfigIndex";
	}

	/**
	 * 验证角色名称的唯一
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checknameuniqueness", method = RequestMethod.POST)
	public @ResponseBody
	String checkNameUniqueness(HttpServletRequest request, HttpServletResponse response, String roleName) {
		SysRole role = null;
		try {
			role = roleService.queryRoleByName(roleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (role == null)
			return "0";
		else
			return "1";
	}
	/**
	 * 验证角色编码的唯一
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checkcodeuniqueness", method = RequestMethod.POST)
	public @ResponseBody
	String checkCodeUniqueness(HttpServletRequest request, HttpServletResponse response, String roleCode) {
		SysRole role = null;
		try {
			role = roleService.queryRoleByRoleCode(roleCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (role == null)
			return "0";
		else
			return "1";
	}
	/**
	 * 查询角色组
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "sysRoleGroup", method = { RequestMethod.GET, RequestMethod.POST })
	public String querySysRoleGroup(HttpServletRequest request, SysRoleGroupForm form) {
		try {
			ItemPage itemPage = groupService.querySysRoleGroup(form,getVisitor(request).getUserId());
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/groupList";
	}
	
	/**
	 * 为角色分配角色组
	 * 
	 * @param response
	 * @param privileges
	 *            权限id "","",""
	 * @param srId
	 */
	@RequestMapping(value = "assignvisitor", method = RequestMethod.POST)
	public @ResponseBody
	String assignvisitor(HttpServletRequest request, String userId, String roleGroupId, String flag,String roleName,String groupName,String roleId) {
		try {
			if (null != roleGroupId && !"".equals(roleGroupId)) {
					if (flag.equals("1")) {
						groupService.deleteRUserGroupByU_RG(roleGroupId, roleId);
						groupService.addRUserGroup(new RUserGroup(roleGroupId, roleId));
						//添加我的分配记录
						UserRoleGroup userRoleGroup=new UserRoleGroup(getVisitor(request).getUserId(),userId,roleGroupId,roleName,groupName,roleId);
						userRoleGroup.setCreateData(new Date());
						groupService.addUserGroup(userRoleGroup);
					}else{
						groupService.deleteRUserGroupByU_RG(roleGroupId, roleId);
					}
			
				return "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "error";
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
	/**
	 * 我分配角色的记录表
	 * @return
	 */
	public String myRoleConfig(HttpServletRequest request){
		SysUser loginedUser = getVisitor(request);
		return "";
	}
}
