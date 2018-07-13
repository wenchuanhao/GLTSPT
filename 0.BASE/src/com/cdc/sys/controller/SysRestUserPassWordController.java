package com.cdc.sys.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import model.sys.entity.SysUserArea;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysRoleUserService;
import com.cdc.sys.service.ISysUserAreaService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.MD5Helper;
import com.util.DCUtil;

/**
 * 用户管理 ——重置用户密码
 * @author cyh
 * 2013-12-23
 */
@Controller
@RequestMapping(value = "/sys/user/*")
public class SysRestUserPassWordController extends CommonController{
	
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysOrganizationService organizationService;
	@Autowired
	private ISysUserAreaService	sysUserAreaService;
	@Autowired
	private ISysRoleUserService sysRoleUserService;

//    @Autowired
//    private ISmsService smsService;

    @Autowired
    private ISysLogService sysLogService;
	
	
	@RequestMapping(value = "toRestUserPwd", method = {RequestMethod.POST,RequestMethod.GET})
	public String toRestUserPwd(){
		return "sys/user/restUserPwd";
	}
	
	
	
	/**
	 * 根据用户查询区域，确认登录用户有没有重置用户密码的权限
	 * @throws Exception 
	 */
	public String hasSelect(SysUser user,String depId) throws Exception{
		
		String b="1001"; //有权限
		if(depId==null || depId.trim().equals("")){
			b="1002"; //用户部门编号为空--参数错误--数据异常
			return b;
		}
		List<SysRoleUser> listRole = sysRoleUserService.getRoleUsersByUserId(user.getUserId());
		if(listRole==null || listRole.size()==0){
			b="1001"; //没有设置角色
			return b;
		}
		String deps="";
		for(int i=0;i<listRole.size();i++){
			List<SysUserArea> listArea = sysUserAreaService.getSysUserAreaByUserId(user.getUserId(),listRole.get(i).getRoleId());
			if(listArea==null || listArea.size()==0){
				continue;
			}
			if(listArea.get(0).getOrganizationId()==null || listArea.get(0).getOrganizationId().trim().equals("NO")){
				continue;
			}
			if(listArea.get(0).getOrganizationId().trim().equals("ALL")){
				b="100"; //有权限
				return b;
			}
			String dep=organizationService.getOrgIdsByOrgIdNew(listArea.get(listArea.size()-1).getOrganizationId());
			if(dep==null || dep.trim().equals("")){
				continue;
			}
			if(deps.equals("")){
				deps=dep+",";
			}else{
				deps+=","+dep;
			}
		}
		if(deps.equals("")){
			b="1001"; //没有设置角色
			return b;
		}
		if(deps.indexOf(depId)!=-1){
			b="100"; //有权限
			return b;
		}
		/**
		List<SysUserArea> listArea = sysUserAreaService.getSysUserAreaByUserId(user.getUserId());
		if(listArea==null || listArea.size()==0){
			b="1001"; //没有设置区域
			return b;
		}
		
		for(int i=0;i<listArea.size();i++){
			if(listArea.get(i).getOrganizationId()!=null && !listArea.get(i).getOrganizationId().trim().equals("")){
				String dep=organizationService.getOrgIdsByOrgIdNew(listArea.get(i).getOrganizationId());
				if(dep!=null && !dep.equals("")){
					if(deps.equals("")){
						deps=dep+",";
					}else{
						deps+=dep;
					}
				}
			}
		}
		if(deps.equals("")){
			b="1001"; //没有设置区域
			return b;
		}
		if(deps.indexOf(depId)!=-1){
			b="100"; //有权限
			return b;
		}
		*/
		return b;
	}
	/**
	 * 根据登录帐户，查询出用户信息
	 */
	@RequestMapping(value = "getUserInfoes", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void getUserInfoes(HttpServletRequest request,HttpServletResponse response,String acc)  throws Exception{
		String result = "";
		SysUser model  = sysUserService.getUserByNameAndACC(acc);
		if(model==null){
			JSONObject qualityTemplateDetailJSONArray = JSONObject.fromObject(model);
	        result = qualityTemplateDetailJSONArray.toString();
	        PrintWriter writer = response.getWriter();
			writer.write(result);
			return;
		}
		SysUser loginedUser = getVisitor(request);
		
		//去掉重置权限 weif 20160604
//		String canSelect=hasSelect(loginedUser,model.getOrganizationId());
//		if(!canSelect.equals("100")){
//			PrintWriter writer = response.getWriter();
//			writer.write(canSelect);
//			return;
//		}
		if(model.getOrganizationId()==null || model.getOrganizationId().equals("")){
			model.setdNames("未知");
		}else{
			String dpp="";
			SysOrganization dep = organizationService.getOrgById(model.getOrganizationId());
			if(dep==null || dep.getOrgName().equals("")){
				model.setdNames("未知");//当前级
			}else{
				/**
				dpp=dep.getOrgName();
				SysOrganization dep2 = organizationService.getOrgById(dep.getParentId());
				if(dep2!=null && !dep2.getOrgName().equals("")){
					dpp=dep2.getOrgName()+"-"+dep.getOrgName();
				}
				**/
				SysOrganization orgs=organizationService.getOrgById(dep.getOrganizationId());
		        StringBuilder containStr = new StringBuilder("");
				organizationService.deptNames(containStr, dep.getOrganizationId());
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
				if(!orgaN.equals("")){
					if(orgaN.indexOf(">")!=-1){
						int k=orgaN.indexOf(">")+2;
						String ndpp=orgaN.substring(k);
						if(ndpp.indexOf(">")!=-1){
							int j=ndpp.indexOf(">")+2;
							ndpp=ndpp.substring(j);
						}
					dpp=ndpp.replaceAll(">>", "->");
					}
				}
				
				model.setdNames(dpp);
			}
		}
		JSONObject qualityTemplateDetailJSONArray = JSONObject.fromObject(model);
        result = qualityTemplateDetailJSONArray.toString();
        PrintWriter writer = response.getWriter();
		writer.write(result);
	}
	/**
	 * 重置用户密码动作
	 * @throws Exception 
	 */
	@RequestMapping(value = "restUserPwd", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String restUserPwd(HttpServletRequest request,HttpServletResponse response,String acc) throws Exception{
		if(acc==null || acc.equals("")){
			return "1";//参数传递为空
		}
		SysUser model  = sysUserService.getSysUserById(acc);
		if(model==null){
			return "2";//无此用户信息
		}
		String defaultpwd = DCUtil.getProperty("DEFAULT_PWD");
		String resetPassword = StringUtils.isNotEmpty(defaultpwd)?defaultpwd:"NFjd2016";
		MD5Helper md5 = new MD5Helper();
		String md5Pwd = md5.getDoubleMD5ofStr(resetPassword);
		model.setPassword(md5Pwd);
		sysUserService.modifySysUser(model);

//        smsService.sendPasswordReset(model,resetPassword);
        sysLogService.log(request, getVisitor(request), "系统管理--用户管理",
                "重置密码", "用户[" + model.getUserName() + "]["+model.getAccount()+"]密码重置成功", new Date(), "3", new Date(), null);
		return "3"; //密码重置成功
	}
}

