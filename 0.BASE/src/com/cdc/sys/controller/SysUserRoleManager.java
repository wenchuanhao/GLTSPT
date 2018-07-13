package com.cdc.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import model.sys.entity.SysUserArea;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysRoleForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.sys.service.ISysRoleUserService;
import com.cdc.sys.service.ISysUserAreaService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 用户角色管理
 * @author cyh
 *
 */
@Controller
@RequestMapping(value = "/sys/role/*")
public class SysUserRoleManager extends CommonController{
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysRoleService roleService;
	@Autowired
	private ISysOrganizationService organizationService;
	@Autowired
	private ISysRoleUserService sysRoleUserService;
	@Autowired
	private ISysUserAreaService	sysUserAreaService;
	@Autowired
	private ISysLogService sysLogService;
	/**
	 * 进入用户角色配置首页
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryUserRoleIndex", method = { RequestMethod.GET, RequestMethod.POST })
	public String sysUserRoleManager(HttpServletRequest request, SysRoleForm form,String account){
		try {	
				if(account != null && !account.trim().equals("")){
					form.setAccount(account);
				}
				ItemPage itemPage = sysRoleUserService.querySysRoleUser(form);
				List<Object[]> objec=(List<Object[]>)itemPage.getItems();
				if(objec!=null && objec.size()>0){
					for(int i=0;i<objec.size();i++){
						Object[] o=objec.get(i);
						if(o!=null){
							SysRoleUser model=(SysRoleUser)o[0];
							if(model!=null){
								String areaNames=getAreaName(model.getUserId(),model.getRoleId());
								model.setAreaName(areaNames);
							}
							
						}
					}
				}
				itemPage.setItems(objec);
			
				request.setAttribute("form", form);
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/queryRoleUserIndex";
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
	 * 角色编辑
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "editUserRole", method = { RequestMethod.GET, RequestMethod.POST })
	public String editUserRole(HttpServletRequest request,String userId, String roleId,String account){
		try {
				SysUser user = sysUserService.getSysUserById(userId);
				String zt="禁用";
				String js="不接受";
				if(user != null){
					if(user.getIsActivate()!=null&&user.getIsActivate().equals("1")){
						zt="正常";
					}else{
						if(user.getIsActivate()!=null&&user.getIsActivate().equals("0")){
							
						}else{
							zt="未知";
						}
						
					}
					if(user.getIsReceiveSms()!=null&&user.getIsReceiveSms().equals("1")){
						js="接受";
					}else{
						if(user.getIsReceiveSms()!=null&&user.getIsReceiveSms().equals("0")){
							
						}else{
							js="未知";
						}
						
					}
				}
				
				request.setAttribute("js", js);
		        SysOrganization orgs=organizationService.getOrgById(user.getOrganizationId());
		        StringBuilder containStr = new StringBuilder("");
				organizationService.deptNames(containStr, user.getOrganizationId());
		        containStr.append(">>ROOT");
		        String newOrgName = containStr.toString();
		        String[] arrorg = newOrgName.split(">>");
		        String  orgaN="";
		        if(arrorg != null && arrorg.length>=0){
		        	for(int i =arrorg.length-1;i>=0;i--){
		        		if(arrorg[i] !=null && !arrorg[i].equals("")){
		        			if(orgaN == null || orgaN.equals("")){
		        				orgaN = arrorg[i];
		        			}else{
		        				orgaN+=">>"+arrorg[i];
		        			}
		        		}
		        	}
		        }
		        request.setAttribute("orga", orgaN);
		        request.setAttribute("user", user);
		        request.setAttribute("zt", zt);
		        request.setAttribute("userid", userId);
		        //取用户对应的角色id
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
		      
		        request.setAttribute("listRoles",listRoles);
		        
		    request.setAttribute("location", "sys/role/queryUserRoleIndex");
			request.setAttribute("user", user);
			request.setAttribute("account", account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/role/editUserRole";
	}
	/**
	 * 按角色名称查询角色
	 * @param request
	 * @param roleName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "getRolesByText", method = { RequestMethod.GET,RequestMethod.POST })
	public String gueryRoleByName(HttpServletRequest request, String roleName,String userId) throws Exception {
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
	        List<SysRole>   list = roleService.queryRoleByIds(ids);
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
	 * 添加用户角色动作
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "addUserRole", method = RequestMethod.POST)
	public @ResponseBody
	String configUserRole(HttpServletRequest request, HttpServletResponse response, String userId,String roleId) throws Exception {
			if(userId==null || userId.equals("") || roleId==null || roleId.equals("")){
				return "n";
			}
			SysUser user = sysUserService.getSysUserById(userId);
			if(user==null){
				return "n";
			}
			SysRoleUser model= new SysRoleUser();
			     model.setRoleId(roleId);
			     model.setUserId(userId);
			sysRoleUserService.addRoleUser(model);//保存用户新角色
			
		return "y";
	}
	/**
	 * 删除用户角色
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "delUserRole", method = RequestMethod.GET)
	public String delUserRole(HttpServletRequest request, HttpServletResponse response, String userRoleId,String account) throws Exception {
		
		if(StringUtils.isNotEmpty(userRoleId)){
			String[] roles = userRoleId.split("_");
			for (String roleid : roles) {
				sysRoleUserService.deleteRoleUser(roleid);//保存用户新角色
				sysLogService.log(request, getVisitor(request), "系统管理-角色管理", "删除用户角色",
						"删除了ID为'" + roleid+ "'的记录", new Date(), "3", new Date(), null);
			}
		}
			
		String pageSize=request.getParameter("pageSize");
		String pageIndex=request.getParameter("pageIndex");
		return 	"redirect:/sys/role/queryUserRoleIndex?account="+account+"&pageSize="+pageSize+"&pageIndex="+pageIndex;
	}
	/**
	 * 查看用户角色详情信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "userRoleDetail", method = RequestMethod.GET)
	public String userRoleDetail(HttpServletRequest request, HttpServletResponse response, String userId) throws Exception{
		SysUser user = sysUserService.getSysUserById(userId);
		String zt="禁用";
		String js="不接受";
		if(user != null){
			if(user.getIsActivate()!=null&&user.getIsActivate().equals("1")){
				zt="正常";
			}else{
				if(user.getIsActivate()!=null&&user.getIsActivate().equals("0")){
					
				}else{
					zt="未知";
				}
				
			}
			if(user.getIsReceiveSms()!=null&&user.getIsReceiveSms().equals("1")){
				js="接受";
			}else{
				if(user.getIsReceiveSms()!=null&&user.getIsReceiveSms().equals("0")){
					
				}else{
					js="未知";
				}
				
			}
		}
		request.setAttribute("js", js);
		
        SysOrganization orgs=organizationService.getOrgById(user.getOrganizationId());
        StringBuilder containStr = new StringBuilder("");
		organizationService.deptNames(containStr, user.getOrganizationId());
        containStr.append(">>ROOT");
        String newOrgName = containStr.toString();
        String[] arrorg = newOrgName.split(">>");
        String  orgaN="";
        if(arrorg != null && arrorg.length>=0){
        	for(int i =arrorg.length-1;i>=0;i--){
        		if(arrorg[i] !=null && !arrorg[i].equals("")){
        			if(orgaN == null || orgaN.equals("")){
        				orgaN = arrorg[i];
        			}else{
        				orgaN+=">>"+arrorg[i];
        			}
        		}
        	}
        }
        request.setAttribute("orga", orgaN);
        request.setAttribute("user", user);
        request.setAttribute("zt", zt);
		
		
		
		String roleNames=""; //已配置的角色名称
		List<SysRoleUser> listRoleUser = sysRoleUserService.getRoleUsersByUserId(userId);
		if(listRoleUser!=null && listRoleUser.size()>0){
			for(int i=0;i<listRoleUser.size();i++){
				if(listRoleUser.get(i)!=null && listRoleUser.get(i).getRoleId()!=null){
					SysRole sysRole = roleService.getSysRoleById(listRoleUser.get(i).getRoleId());
					if(sysRole != null && sysRole.getRoleName() !=null){
						if(roleNames == null || roleNames.equals("")){
							roleNames = sysRole.getRoleName();
						}else{
							roleNames+="，"+sysRole.getRoleName();
						}
					}
				}
			}
		}
		if(roleNames == null || roleNames.equals("")){
			roleNames = "暂无配置角色";
		}
		request.setAttribute("roleNames", roleNames);
		
		String areaNames = ""; //已配置的区域名称
		List<SysUserArea> listUserArea = sysUserAreaService.getSysUserAreaByUserId(userId);
		if(listUserArea!=null && listUserArea.size()>0){
			for(int i=0;i<listUserArea.size();i++){
				if(listUserArea.get(i)!=null && listUserArea.get(i).getOrganizationId()!=null){
					SysOrganization org = organizationService.getOrgById(listUserArea.get(i).getOrganizationId());
					if(org != null && org.getOrgName() !=null){
						if(areaNames == null || areaNames.equals("")){
							areaNames = org.getOrgName();
						}else{
							areaNames+=">>"+org.getOrgName();
						}
					}
				}
			}
		}else{
			areaNames="全部区域";
		}
		if(areaNames == null || areaNames.equals("")){
			areaNames="暂无设置区域";
		}
		request.setAttribute("areaNames", areaNames);
		
		request.setAttribute("location", "sys/role/queryUserRoleIndex");
		
		return "sys/role/userRoleDetail"; 
	}
	/**
	 * 进入单个用户区域编辑
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "toEditRoleUserView", method = RequestMethod.GET)
	public String toEditRoleUserView(HttpServletRequest request, HttpServletResponse response, String userId,String roleId) throws Exception{
		SysUser user = sysUserService.getSysUserById(userId);//用户基本信息
		String zt="禁用";
		String js="不接受";
		if(user != null){
			if(user.getIsActivate()!=null&&user.getIsActivate().equals("1")){
				zt="正常";
			}else{
				if(user.getIsActivate()!=null&&user.getIsActivate().equals("0")){
					
				}else{
					zt="未知";
				}
				
			}
			if(user.getIsReceiveSms()!=null&&user.getIsReceiveSms().equals("1")){
				js="接受";
			}else{
				if(user.getIsReceiveSms()!=null&&user.getIsReceiveSms().equals("0")){
					
				}else{
					js="未知";
				}
				
			}
		}
		request.setAttribute("js", js);
        SysOrganization orgs=organizationService.getOrgById(user.getOrganizationId());
        StringBuilder containStr = new StringBuilder("");
		organizationService.deptNames(containStr, user.getOrganizationId());
        containStr.append(">>ROOT");
        String newOrgName = containStr.toString();
        String[] arrorg = newOrgName.split(">>");
        String  orgaN="";
        if(arrorg != null && arrorg.length>=0){
        	for(int i =arrorg.length-1;i>=0;i--){
        		if(arrorg[i] !=null && !arrorg[i].equals("")){
        			if(orgaN == null || orgaN.equals("")){
        				orgaN = arrorg[i];
        			}else{
        				orgaN+=">>"+arrorg[i];
        			}
        		}
        	}
        }
        request.setAttribute("orga", orgaN);
        request.setAttribute("user", user);
        request.setAttribute("zt", zt);
		
        String areaNames = getAreaName(userId,roleId); //已配置的区域名称
		
		request.setAttribute("areaNames", areaNames);
        
		List<SysOrganization> list = organizationService.queryAllNoRoot();//全部区域
		
		request.setAttribute("Areas", list);
		request.setAttribute("roleId", roleId);
		SysRole sysRole = roleService.getSysRoleById(roleId);
		String roleNameN="";
		if(sysRole!=null && sysRole.getRoleName()!=null && !sysRole.getRoleName().equals("")){
			roleNameN=sysRole.getRoleName();
		}
		request.setAttribute("roleNameN", roleNameN);
		
		request.setAttribute("location", "sys/role/queryUserRoleIndex");
		return "sys/role/editRoleUserView"; 
	}
	
	
	/**
	 * 编辑用户区域动作
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "editRoleUserView", method = RequestMethod.POST)
	public @ResponseBody
	String configUserRole(HttpServletRequest request, HttpServletResponse response, String userId,String roleId,String selectValue) throws Exception {
			if(userId==null || userId.equals("") || roleId==null || roleId.equals("")){
				return "n";
			}
			SysUser user = sysUserService.getSysUserById(userId);
			if(user==null){
				return "n";
			}
			if(selectValue==null || selectValue.trim().equals("")){
				return "n";
			}
			sysUserAreaService.delSysUserArea(userId,roleId);//先删除用户区域数据，以最后变更的区域为准
			
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
				SysUserArea sysmodel = new SysUserArea(); //保存用户区域数据
					sysmodel.setUserId(userId);
					sysmodel.setOrganizationId(arr2[0]);
					sysmodel.setRanks(Integer.parseInt(arr2[1]));
					sysmodel.setRoleId(roleId);
					sysUserAreaService.addSysUserArea(sysmodel);
			}
			
		return "y";
	}
	
}
