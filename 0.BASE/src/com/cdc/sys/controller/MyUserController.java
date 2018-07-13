package com.cdc.sys.controller;


//import com.cdc.sms.service.ISmsService;
import com.cdc.sys.service.*;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.Encrypt;
import com.trustel.common.MD5Helper;
import com.trustel.common.MD5HelperCN;

import model.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 
 * @Description: 用户管理
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/myuser/*")
public class MyUserController extends CommonController {

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
	private ISysLogService sysLogService;
	@Autowired
	private ISysRoleUserService sysRoleUserService;

//    @Autowired
//    private ISmsService smsService;

	
	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param oldPwd
	 * @param newPwd
	 */
	@RequestMapping(value = "modifyPwd", method = RequestMethod.POST)
	public @ResponseBody
	String modifyPwd(HttpServletRequest request, HttpServletResponse response,
			String oldPwd, String newPwd) {
		if(oldPwd == null || oldPwd.equals("") || newPwd == null || newPwd.equals("")){
			return "1";
		}
		oldPwd = MD5HelperCN.getMD5(oldPwd.toUpperCase()).toUpperCase();
		newPwd = MD5HelperCN.getMD5(newPwd.toUpperCase()).toUpperCase();
		try {
			SysUser sysUser = getVisitor(request);
			if (sysUser.getPassword().equals(oldPwd)) {
				sysUser.setPassword(newPwd);//保存加密的新密码
				sysUser.setPasswordOld(oldPwd);//保存加密的旧密码
				sysUser.setPasswordNew(newPwd);//保存加密的新密码
				sysUser.setLastUpdateTime(new Date());
				sysUserService.modifySysUser(sysUser);
				sysLogService.log(request, getVisitor(request), "系统管理--用户管理",
						"修改密码", "用户" + sysUser.getUserName() + "修改了密码", new Date(), "3", new Date(), null);

                //发送短信提醒
//                smsService.sendUpdatePassword(sysUser);
				return "2";
			} else {
				return "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "3";
		}
	
	}

	/**
	 * 跳转到密码修改页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "changePassword", method = {RequestMethod.GET,RequestMethod.POST})
	public String toChangePassword() {
		return "sys/user/changePassword";
	}
	
}
