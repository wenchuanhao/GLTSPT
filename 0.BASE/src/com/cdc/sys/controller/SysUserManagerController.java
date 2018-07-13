package com.cdc.sys.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.RUserGroup;
import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import model.sys.entity.UserRoleGroup;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysLogForm;
import com.cdc.sys.form.SysOrganizationForm;
import com.cdc.sys.form.SysRoleGroupForm;
import com.cdc.sys.form.SysUserForm;
import com.cdc.sys.service.IDefaultLoginService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysPrivilegesService;
import com.cdc.sys.service.ISysRoleGroupService;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.sys.service.ISysRoleUserService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;
import com.util.DCUtil;

/**
 * 
 * @Description: 用户管理>>管理用户
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/user/*")
public class SysUserManagerController extends CommonController {

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

    @Autowired
    private IDefaultLoginService defaultLoginService;
    
    

	/**
	 * 查询所有用户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "querySysUser", method = { RequestMethod.GET,RequestMethod.POST })
	public String querySysUser(HttpServletRequest request, SysUserForm form) {
		try {
			ItemPage itemPage = sysUserService.querySysUser(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/querySysUser";
	}
	/**
	 * 所有用户列表__改进版本———管理用户
	 * @param <E>
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryUser", method = { RequestMethod.GET,RequestMethod.POST })
	public  String queryUserList(HttpServletRequest request, SysUserForm form) {
		try {
			String st=null;
			String accountD=request.getParameter("accountD");
			st=request.getParameter("st");
			if(accountD!=null&&accountD!=""){
				form.setAccount(accountD);
			}
			request.setAttribute("listOrg", organizationService.queryAllNoRoot());
			request.setAttribute("orgs", organizationService.queryAll());
			if(form != null && form.getOrganizationId() != null && !form.getOrganizationId().equals("")){
				SysOrganization modelN = organizationService.getOrgById(form.getOrganizationId());
				String orgidsN = organizationService.getOrgIdsByOrgId(form.getOrganizationId(), modelN.getParentId());
				form.setuDepIds(orgidsN);
			}
			ItemPage itemPage = sysUserService.querySysUser2(form);	
			List list=itemPage.getItems();
			SysOrganization org;
			SysUser sysUser;
			for(int i=0;i<list.size();i++){
				//List list1=(List)list.get(i);
				Object[] object=(Object[])list.get(i);	
				org = (SysOrganization)object[2];
				String comOrg=sysUserService.getComyOrg(org.getOrganizationId());				
				sysUser=(SysUser)object[0];
				sysUser.setdNames(comOrg);
				object[0]=sysUser;
				list.set(i, object);
			}
			itemPage.setItems(list);
			/**
			 * 默认重置密码
			 */
			String defaultpwd = DCUtil.getProperty("DEFAULT_PWD");
			request.setAttribute("defaultpwd", StringUtils.isNotEmpty(defaultpwd)?defaultpwd:"NFjd2016");
			/**
			 * 默认角色
			 */
			List<SysRole> roleList = roleService.listAll();
			request.setAttribute("roleList", roleList);
		//	String comOrg=sysUserService.getComyOrg(orgId)
			request.setAttribute("form", form);
			request.getSession().putValue("dep", form);
			request.setAttribute("st", st);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/queryUser";
	}

	/*@RequestMapping(value = "queryUser", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryUserList(HttpServletRequest request, SysUserForm form) {
		try {
			String accountD=request.getParameter("accountD");
			if(accountD!=null&&accountD!=""){
				form.setAccount(accountD);
			}
			request.setAttribute("listOrg", organizationService.queryAllNoRoot());
			request.setAttribute("orgs", organizationService.queryAll());
			if(form != null && form.getOrganizationId() != null && !form.getOrganizationId().equals("")){
				SysOrganization modelN = organizationService.getOrgById(form.getOrganizationId());
				String orgidsN = organizationService.getOrgIdsByOrgId(form.getOrganizationId(), modelN.getParentId());
				form.setuDepIds(orgidsN);
			}
			ItemPage itemPage = sysUserService.querySysUser2(form);
			for(int i=0;i<itemPage.getItems().size();i++){
				List list=itemPage.getItems();
				
			}
			request.setAttribute("form", form);
			request.getSession().putValue("dep", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/queryUser";
	}*/
	/**
	 * 查看单个用户信息__改进版本———管理用户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryUserSingle", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryUserSingle(HttpServletRequest request, SysUserForm form) {
		try {
			request.setAttribute("orgs", organizationService.queryAll());
			List<SysUser> itemPage = sysUserService.querySysUseres(form);
			if(itemPage!=null && itemPage.size()>0){
				SysUser user=(SysUser)itemPage.get(0);
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
				}else{
					  request.setAttribute("isN", "n");
				}
			}else{
				 request.setAttribute("isN", "n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/singleUser";
	}
	/**
	 * 进入查询单个用户信息__改进版本———管理用户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "xtoSelectUser", method = { RequestMethod.GET,RequestMethod.POST })
	public String selectUserList(HttpServletRequest request, SysUserForm form) {
		return "sys/user/selectUser";
	}
	
	/**
	 * 用户组织机构树
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "userDtree", method = { RequestMethod.GET, RequestMethod.POST })
	public String dtreeUser(HttpServletRequest request, SysOrganizationForm form) {
		try {
			request.setAttribute("orgs", organizationService.queryAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/userDtree";
	}
	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addSysUser55/{orgId}", method = RequestMethod.GET)
	public String toAddSysUser(HttpServletRequest request,@PathVariable String orgId) {
		try {
			if(!orgId.equals("1")){
				request.setAttribute("org",organizationService.getOrgById(orgId));
			}else{
				request.setAttribute("org","1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/addSysUser";
	}
	

	/**
	 * 跳转到编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "modifySysUser/{userId}", method = RequestMethod.GET)
	public String toModifySysUser(HttpServletRequest request,
			@PathVariable String userId) {
		try {
			request.setAttribute("listrole", roleService.listAll());
			request.setAttribute("listOrg", organizationService.queryAllNoRoot());
			SysUser sysUser = sysUserService.getSysUserById(userId);
            DateTime nowDate = new DateTime();
            if (sysUser.getLastLoginFailDate() != null && sysUser.getLastLoginFailDate().after(nowDate.withTimeAtStartOfDay().toDate()) && sysUser.getLoginFailCount() >= 5) {
                sysUser.setFreezeStatus(SysUser.STATUS_FREEZE_FAILCOUNT);
                sysUser.setIsDongjie(true);
            }
			SysOrganization orgs=organizationService.getOrgById(sysUser.getOrganizationId());
			
			StringBuilder containStr = new StringBuilder("");
			organizationService.deptNames(containStr, orgs.getOrganizationId());	        
	        String newOrgName = containStr.toString();
	        String[] arrorg = newOrgName.split(">>");
	        String  orgaN="";
	        if(arrorg != null && arrorg.length>=0){
	        	int aindex=arrorg.length-1;	        	
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
	       	       
	        
	        request.setAttribute("orga",orgaN);
		    /*request.setAttribute("orga", orgs.getOrgName());*/
			request.setAttribute("user", sysUser);
			request.setAttribute("location", "sys/user/queryUser");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/modifySysUser";
	}
	/**
	 * 双击跳转到用户详细页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "sysUserInfo/{back}/{userId}", method = RequestMethod.GET)
	public String sysUserInfoAdd(HttpServletRequest request,
			@PathVariable String userId,@PathVariable String back) {
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
				String  createPerson="";
				if(user.getCreaterId() != null && !user.getCreaterId().equals("")){
					SysUser user_create = sysUserService.getSysUserById(user.getCreaterId());
					createPerson = user_create.getUserName();
				}

                //获取最后登录时间
                SysLogForm sysLogForm = new SysLogForm();
                sysLogForm.setPageIndex(1);
                sysLogForm.setPageSize(1);
                sysLogForm.setUserId(user.getAccount());
                sysLogForm.setLogModuleNote("登录");
                ItemPage lastLoginDate = logService.query(sysLogForm,"1");
                if (lastLoginDate != null && lastLoginDate.getItems() != null && !lastLoginDate.getItems().isEmpty()) {
                    request.setAttribute("lastLogined",lastLoginDate.getItems().get(0));
                }

				request.setAttribute("createPerson", createPerson);
				request.setAttribute("js", js);
				request.setAttribute("zt", zt);
		        SysRole role= roleService.getSysRoleById(user.getUserDefaultRole());
		        request.setAttribute("ro", role.getRoleName());
		        SysOrganization orgs=organizationService.getOrgById(user.getOrganizationId());
		       // request.setAttribute("orga", orgs.getOrgName());
		        
		        StringBuilder containStr = new StringBuilder("");
				organizationService.deptNames(containStr, orgs.getOrganizationId());	        
		        String newOrgName = containStr.toString();
		        String[] arrorg = newOrgName.split(">>");
		        String  orgaN="";
		        if(arrorg != null && arrorg.length>=0){
		        	int aindex=arrorg.length-1;	        	
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
		        request.setAttribute("isN", "y");
		        if(back.equals("search")){
		        	 request.setAttribute("location", "sys/user/updateOrg");
		        }else{
		        	request.setAttribute("location", "sys/user/queryUser");
		        }
		        
			}else{
				  request.setAttribute("isN", "n");
			}
			request.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/sysUserInfo";
	}
	@RequestMapping(value = "sysUserInfo/{userId}", method = RequestMethod.GET)
	public String sysUserInfo(HttpServletRequest request,
			@PathVariable String userId) {
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
				String  createPerson="";
				if(user.getCreaterId() != null && !user.getCreaterId().equals("")){
					SysUser user_create = sysUserService.getSysUserById(user.getCreaterId());
					createPerson = user_create.getUserName();
				}

                //获取最后登录时间
                SysLogForm sysLogForm = new SysLogForm();
                sysLogForm.setPageIndex(1);
                sysLogForm.setPageSize(1);
                sysLogForm.setUserId(user.getAccount());
                sysLogForm.setLogModuleNote("登录");
                ItemPage lastLoginDate = logService.query(sysLogForm,"1");
                if (lastLoginDate != null && lastLoginDate.getItems() != null && !lastLoginDate.getItems().isEmpty()) {
                    request.setAttribute("lastLogined",lastLoginDate.getItems().get(0));
                }

				request.setAttribute("createPerson", createPerson);
				request.setAttribute("js", js);
				request.setAttribute("zt", zt);
		        SysRole role= roleService.getSysRoleById(user.getUserDefaultRole());
		        request.setAttribute("ro", role.getRoleName());
		        SysOrganization orgs=organizationService.getOrgById(user.getOrganizationId());
 // request.setAttribute("orga", orgs.getOrgName());
		        
		        StringBuilder containStr = new StringBuilder("");
				organizationService.deptNames(containStr, orgs.getOrganizationId());	        
		        String newOrgName = containStr.toString();
		        String[] arrorg = newOrgName.split(">>");
		        String  orgaN="";
		        if(arrorg != null && arrorg.length>=0){
		        	int aindex=arrorg.length-1;	        	
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
		        request.setAttribute("isN", "y");
		        request.setAttribute("location", "sys/user/queryUser");
			}else{
				  request.setAttribute("isN", "n");
			}
			request.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/sysUserInfo";
	}
	
	
	/**
	 * 用户页面选择跳转到用户详细页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "sysCheckUserInfo/{userId}", method = RequestMethod.GET)
	public String sysCheckUserInfo(HttpServletRequest request,
			@PathVariable String userId) {
		try {
			request.setAttribute("user", sysUserService.getSysUserById(userId));
			List<SysOrganization> listOrg=sysUserService.getOrgFirst(this.getVisitor(request));
			request.setAttribute("listOrg", listOrg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/sysCheckUserInfo";
	}
	
	
	/**
	 * 跳转到排序页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "order/{orgId}", method = RequestMethod.GET)
	public String order(HttpServletRequest request,@PathVariable String orgId) {
		try {
			if(null==orgId && orgId.equals("1")){
				request.setAttribute("userList", sysUserService.allUser());
			}else{
				request.setAttribute("userList", sysUserService.allUserByOrgId(orgId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/orderSysUser";
	}
	/**
	 * 更新排序
	 * 
	 * @return
	 */
	@RequestMapping(value = "orderUpdate/{ids}/{seqs}", method = RequestMethod.GET)
	public String orderUpdate(HttpServletRequest request,
			@PathVariable String ids,@PathVariable String seqs) {
		try {
			sysUserService.orderUpdate(ids, seqs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/user/queryUser";
	}
	
	/**
	 * 更新用户信息
	 * 
	 * @param request
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "updateSysUser", method = RequestMethod.POST)
	public String doModifySysUser(HttpServletRequest request, SysUser sysUser) {
		try {
			String roleOld=request.getParameter("roleOld");
            SysUser oldSysUser = sysUserService.getSysUserById(sysUser.getUserId());
            oldSysUser.setAccount(sysUser.getAccount());
            oldSysUser.setUserName(sysUser.getUserName());
            oldSysUser.setMobile(sysUser.getMobile());
            oldSysUser.setEmail(sysUser.getEmail());
            oldSysUser.setUserDefaultRole(sysUser.getUserDefaultRole());
            oldSysUser.setIsReceiveSms(sysUser.getIsReceiveSms());

            oldSysUser.setOrganizationId(sysUser.getOrganizationId());
            if ("0".equals(sysUser.getIsActivate())) {
                //如果是禁用状态就把短信设置为不接受短信状态
                oldSysUser.setIsReceiveSms("0");
            }
            oldSysUser.setUserName(sysUser.getUserName().trim());

            if(SysUser.STATUS_ACTIVATE_DIS.equals(sysUser.getIsActivate()) && !StringUtils.equals(oldSysUser.getIsActivate(), sysUser.getIsActivate()) ){
                logService.log(request, getVisitor(request), "系统管理--用户管理", "修改用户",
                        getVisitor(request).getAccount()+"将【" + sysUser.getUserName() + "】的账号的账户状态设置为【注销】状态", new Date(), "3", new Date(), null);
            }else if(SysUser.STATUS_ACTIVATE_ENABLE.equals(sysUser.getIsActivate()) && !StringUtils.equals(oldSysUser.getIsActivate(), sysUser.getIsActivate()) ){
                logService.log(request, getVisitor(request), "系统管理--用户管理", "修改用户",
                        getVisitor(request).getAccount()+"将【" + sysUser.getUserName() + "】的账号的账户状态改为【启动】状态", new Date(), "3", new Date(), null);
            }
            oldSysUser.setIsActivate(sysUser.getIsActivate());

            if (SysUser.STATUS_FREEZE_FAILCOUNT.equals(sysUser.getFreezeStatus())) {
                //冻结账号
//                defaultLoginService.finalLoginFailCounter(sysUser.getUserId());
                if (oldSysUser.getLastLoginFailDate() != null && DateTime.now().withTimeAtStartOfDay().isBefore(new Date().getTime())){
                    logService.log(request, getVisitor(request), "系统管理--用户管理", "修改用户",
                            getVisitor(request).getAccount()+"将【" + sysUser.getUserName() + "】的账号的使用状态改为【冻结】状态", new Date(), "3", new Date(), null);
                }
                oldSysUser.setFreezeStatus(SysUser.STATUS_FREEZE_NORMAL);
                oldSysUser.setLastLoginFailDate(new Date());
                oldSysUser.setLoginFailCount(5l);
            }else if (SysUser.STATUS_FREEZE_DISABLE.equals(sysUser.getFreezeStatus())){

                if (!sysUser.getFreezeStatus().equals(oldSysUser.getFreezeStatus())){
                    logService.log(request, getVisitor(request), "系统管理--用户管理", "修改用户",
                            getVisitor(request).getAccount()+"将【" + sysUser.getUserName() + "】的账号的使用状态改为【禁用】状态", new Date(), "3", new Date(), null);
                    oldSysUser.setFreezeStatus(sysUser.getFreezeStatus());
                }
                oldSysUser.setLoginFailCount(0l);
            }else if (SysUser.STATUS_FREEZE_FAIL.equals(sysUser.getFreezeStatus())){

                if (!sysUser.getFreezeStatus().equals(oldSysUser.getFreezeStatus())){
                    logService.log(request, getVisitor(request), "系统管理--用户管理", "修改用户",
                            getVisitor(request).getAccount()+"将【" + sysUser.getUserName() + "】的账号的使用状态改为【失效/待激活】状态", new Date(), "3", new Date(), null);
                    oldSysUser.setFreezeStatus(sysUser.getFreezeStatus());
                }
                oldSysUser.setLoginFailCount(0l);
            }else if (SysUser.STATUS_FREEZE_NORMAL.equals(sysUser.getFreezeStatus())){

                if (!sysUser.getFreezeStatus().equals(oldSysUser.getFreezeStatus())){
                    logService.log(request, getVisitor(request), "系统管理--用户管理", "修改用户",
                            getVisitor(request).getAccount()+"将【" + sysUser.getUserName() + "】的账号的使用状态改为【正常】状态", new Date(), "3", new Date(), null);
                    oldSysUser.setFreezeStatus(sysUser.getFreezeStatus());
                }
                oldSysUser.setLoginFailCount(0l);
            }
            sysUserService.modifySysUser(oldSysUser);
			//修改对应用户角色表里面的数据
			//sysRoleUserService.delRoleUser(sysUser.getUserId());
			List<SysRoleUser> listRoleUser = sysRoleUserService.getRoleUsersByUserId(sysUser.getUserId(), roleOld);
			if(listRoleUser==null || listRoleUser.size()==0){
				 SysRoleUser model = new SysRoleUser();
				    model.setRoleId(sysUser.getUserDefaultRole());
				    model.setUserId(sysUser.getUserId());
				    sysRoleUserService.addRoleUser(model); 
			}else{
				for(int j=0;j<listRoleUser.size();j++){
					sysRoleUserService.deleteRoleUser(listRoleUser.get(j).getRoleuserId());
				}
				 SysRoleUser model = new SysRoleUser();
				    model.setRoleId(sysUser.getUserDefaultRole());
				    model.setUserId(sysUser.getUserId());
				    sysRoleUserService.addRoleUser(model); 
			}
			//sysRoleUserService.addRoleUser(model);
			logService.log(request, getVisitor(request), "系统管理--用户管理", "修改用户",
					"修改【" + sysUser.getUserName() + "】用户", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/user/queryUser";
	}
	
	
	/**
	 * to删除用户
	 * 
	 * @param userIds
	 * @return
	 */
	@RequestMapping(value = "toDelUser", method = RequestMethod.GET)
	public String toDelUser(HttpServletRequest request, SysUserForm form) {
		try {
			ItemPage itemPage = sysUserService.querySysUser(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/queryToDelSysUser";
	}
	
	/**
	 * 所有用户列表
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryDelUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queryDelUserList(HttpServletRequest request, SysUserForm form) {
		try {
			ItemPage itemPage = sysUserService.querySysUser(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/queryDelUser";
	}
	
	
	/**
	 * 删除用户
	 * 
	 * @param userIds
	 * @return
	 */
	@RequestMapping(value = "deleteSysUser", method = RequestMethod.POST)
	public String doDeleteSysUser(HttpServletRequest request, String orgIds) {
		if (null != orgIds && !"".equals(orgIds)) {
			String[] ids = orgIds.split(",");
			String deleteName = "";
			try {
					for (String userId : ids) {
						SysUser sysUser = sysUserService.getSysUserById(userId);
						deleteName += sysUser.getUserName() + "，";
						sysUserService.deleteSysUser(userId);
					}
					logService.log(request, getVisitor(request), "系统管理--用户管理",
							"删除用户", "删除用户【" + deleteName.substring(0,deleteName.length()-1) + "】", new Date(), "3", new Date(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String pageSize=request.getParameter("pageSize");
		String pageIndex=request.getParameter("pageIndex");
		String organizationId=request.getParameter("organizationId");
		return "redirect:/sys/user/queryUser?st=1&pageSize="+pageSize+"&pageIndex="+pageIndex+"&organizationId="+organizationId;
	}

	/**
	 * 验证用户account的唯一
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checknameuniqueness", method = RequestMethod.POST)
	public @ResponseBody
	String checkNameUniqueness(HttpServletRequest request,HttpServletResponse response, String account) {
		SysUser visitor = null;
		try {
			visitor = sysUserService.queryVisitorByName(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (visitor == null)
			return "0";
		else
			return "1";
	}
	
	/**
	 * 跳转到我的分配记录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "myDistribution", method = RequestMethod.GET)
	public String toMyDistribution(HttpServletRequest request) {
		try {
			request.setAttribute("userGroup",groupService.queryUserGroup(getVisitor(request).getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/myDistribution";
	}
	/**
	 * 撤销分配
	 * 
	 * @return
	 */
	@RequestMapping(value = "undo/{roleId}/{groupId}", method = RequestMethod.GET)
	public String undoDistribution(@PathVariable String roleId,@PathVariable String groupId) {
		try {
			groupService.deleteRUserGroupByU_RG(groupId, roleId);
			groupService.undo(roleId, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/user/myDistribution";
	}
	/**
	 * 跳转到查看用户角色页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryRoleUser", method = {RequestMethod.GET,RequestMethod.POST})
	public String toQueryRoleUser(HttpServletRequest request,String account) {
		try {
			if(account!=null){
				SysUser user=sysUserService.getUserByNameAndACC(account);
				request.setAttribute("user", user);
				if(user!=null){
					request.setAttribute("roleList", privilegesService.queryRolesByUserId(sysUserService.getUserByNameAndACC(account).getUserId()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/queryRoleUser";
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
		return "sys/user/sysRoleGroup";
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
				if (null != userId && !"".equals(userId)) {
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
	 * 修改组织变更
	 * 
	 * @return
	 */
	@RequestMapping(value = "upUser", method = RequestMethod.POST)
	public  String upUser(HttpServletRequest request, HttpServletResponse response,String userId,String organizationId) {
		try {
			sysUserService.upUserByUserId(userId, organizationId, "");
			SysUser sysUser = sysUserService.getSysUserById(userId);
			SysOrganization sysOrganization =  organizationService.getOrgById(organizationId);
			logService.log(request, getVisitor(request), "系统管理--用户管理", "组织变更",
					"修改用户" + sysUser.getUserName() + "的组织为" + sysOrganization.getOrgName(), new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/sys/user/closeSubPage";
	}
	/**
	 * 修改组织变更
	 * 
	 * @return
	 */
	@RequestMapping(value = "upUserOrgs", method = RequestMethod.POST)
	public  String upUserOrgs(HttpServletRequest request, HttpServletResponse response,String userId,String organizationId) {
		
		try {
			String userName = "";
			if (null != userId && !"".equals(userId)) {
				String[] ids = userId.split(",");
				if(ids!=null && ids.length>0){
					for(int i=0;i<ids.length;i++){
						SysUser sysUser = sysUserService.getSysUserById(ids[i]);
						userName += sysUser.getUserName() + "，";
						sysUserService.upUserByUserId(ids[i], organizationId, "");
					}
					SysOrganization sysOrganization =  organizationService.getOrgById(organizationId);
					logService.log(request, getVisitor(request), "系统管理--用户管理", "组织变更",
							"修改用户【" + userName.substring(0,userName.length()-1) + "】的组织为" + sysOrganization.getOrgName(), new Date(), "3", new Date(), null);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/sys/user/closeSubPage";
	}
	/**
	 * 跳转到编辑页面__组织变更————单个
	 * 
	 * @return  
	 */
	@RequestMapping(value = "modifyUserOrg/{userId}", method = RequestMethod.GET)
	public String toModifySysOrg(HttpServletRequest request,
			@PathVariable String userId) {
		try {
			request.setAttribute("listOrg", organizationService.queryAllNoRoot());
			if (null != userId && !"".equals(userId)) {
				String[] ids = userId.split(",");
				userId = ids[0];
			}
				
			request.setAttribute("user", sysUserService.getSysUserById(userId));
			SysUser sysuser=sysUserService.getSysUserById(userId);
			if(sysuser!=null){
				String js="不接受";
				if(sysuser.getIsReceiveSms().equals("1")){
					js="接受";
				}
				String zt="禁用";
				if(sysuser.getIsActivate().equals("1")){
					zt="正常";
				}
				request.setAttribute("js", js);
				request.setAttribute("zt", zt);
		        SysRole role= roleService.getSysRoleById(sysuser.getUserDefaultRole());
		        request.setAttribute("ro", role.getRoleName());
		        SysOrganization orgs=organizationService.getOrgById(sysuser.getOrganizationId());
		        request.setAttribute("orga", orgs.getOrgName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/updateOrgByOne";
	}
	/**
	 * 跳转到编辑页面__组织变更————批量
	 * 
	 * @return 
	 */
	@RequestMapping(value = "smodifyUserOrg/{userId}", method = RequestMethod.GET)
	public String toModifySysOrgs(HttpServletRequest request,
			@PathVariable String userId) {
		try {
			String oneUserId="";
			if (null != userId && !"".equals(userId)) {
				String[] ids = userId.split(",");
				oneUserId = ids[0];
			}
			request.setAttribute("listOrg", organizationService.queryAllNoRoot());
			SysUser sysu= sysUserService.getSysUserById(oneUserId);
			SysOrganization orgs=organizationService.getOrgById(sysu.getOrganizationId());
	        request.setAttribute("orga", orgs.getOrgName());
	        request.setAttribute("orgid", orgs.getOrganizationId());
	        request.setAttribute("userIds", userId);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/updateOrgByMore";
	}
	/**
	 * 用户组织机构树（删除用户）
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "userDeltree", method = { RequestMethod.GET, RequestMethod.POST })
	public String userDeltree(HttpServletRequest request, SysOrganizationForm form) {
		try {
			request.setAttribute("orgs", organizationService.queryAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/userDeltree";
	}
	/**
	 * 解除冻结账户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "unfreezeUser", method = {RequestMethod.POST})
	public @ResponseBody void unfreezeUser(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		String userId = request.getParameter("userId");
    		String result = "0";
  	        response.setContentType("text/html; charset=utf-8");
  	        if(userId != null && !"".equals(userId)){
  	        	sysUserService.unfreezeUser(userId);
  	        	result = "1";
  	        }
  	        PrintWriter writer = response.getWriter();
  	        writer.write(result);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 
	}
	/**
	 * 系统用户排序
	 * @param request
	 * @param depId
	 * @param moveFlag
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "exchangeOrderNum",method =RequestMethod.POST)
	public @ResponseBody 
	String exchangeOrderNum(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String result="0";
		String moveFlag=request.getParameter("moveFlag");//移动标识：U上移，D下移
		String userId=request.getParameter("userId");//会员userId
		String depId=request.getParameter("depId");//部门id
		if(depId==null || depId.equals("") || depId.equals("ROOT")){
			return "1";//请在点击左边树形菜单后再进行排序操作
		}
		SysUserForm form = (SysUserForm)request.getSession().getAttribute("dep");
		if(form==null || form.getOrganizationId()==null || form.getOrganizationId().equals("")){
			return "1";//请在点击左边树形菜单后再进行排序操作
		}
		
		if(moveFlag==null || moveFlag.equals("") || userId==null || userId.equals("")){
			return "2";//参数传递出错
		}
		depId=organizationService.getOrgIdsByOrgId(depId, "");
		SysUser model=sysUserService.getSysUserById(userId);
		if(model==null){
			return "3";//用户不存在或者已被删除
		}
		if(moveFlag.equals("U")){
			List<SysUser> listProjectU=sysUserService.getMaxOrMinOrderNum(depId, "U");
			if(listProjectU.size()==1 || (model.getUserId().equals(listProjectU.get(0).getUserId()))){
				return "4";//该用户已处于最顶端
			}
			int pIndex=0;
			for(int i=0;i<listProjectU.size();i++){
				if(listProjectU.get(i).getUserId().equals(model.getUserId())){
					pIndex=i;
					break;
				}
			}
			SysUser rmpbProjectManagement = listProjectU.get(pIndex);//被点击的用户
			int ord=rmpbProjectManagement.getOrderNum();
			SysUser rmpbProjectManagement2 = listProjectU.get(pIndex-1);//处在被点击用户的上个用户
			int ord2=rmpbProjectManagement2.getOrderNum();
//			if(ord2==0){
//				ord2=1;
//			}
			rmpbProjectManagement.setOrderNum(ord2);
			rmpbProjectManagement2.setOrderNum(ord);
			sysUserService.modifySysUser(rmpbProjectManagement);
			sysUserService.modifySysUser(rmpbProjectManagement2);
			
		}
		if(moveFlag.equals("D")){
			List<SysUser> listProjectD=sysUserService.getMaxOrMinOrderNum(depId, "D");
			if(listProjectD.size()==1 || (model.getUserId().equals(listProjectD.get(0).getUserId()))){
				return "5";//该用户已处于最底端
			}
			int pIndex=0;
			for(int i=0;i<listProjectD.size();i++){
				if(listProjectD.get(i).getUserId().equals(model.getUserId())){
					pIndex=i;
					break;
				}
			}
			SysUser rmpbProjectManagement = listProjectD.get(pIndex);//被点击的用户
			int ord=rmpbProjectManagement.getOrderNum();
			SysUser rmpbProjectManagement2 = listProjectD.get(pIndex-1);//处在被点击用户的上个用户
			int ord2=rmpbProjectManagement2.getOrderNum();
//			if(ord2==0){
//				ord2=-1;
//			}
			rmpbProjectManagement.setOrderNum(ord2);
			rmpbProjectManagement2.setOrderNum(ord);
			sysUserService.modifySysUser(rmpbProjectManagement);
			sysUserService.modifySysUser(rmpbProjectManagement2);
		}
		return result;
	}
}
