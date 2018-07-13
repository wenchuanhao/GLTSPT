package com.cdc.sys.controller;

import com.cdc.sys.service.*;
import com.cdc.system.core.authentication.controller.CommonController;
import model.sys.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.util.DateUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 
 * @Description: 用户管理>>管理用户公用
 * @Author: 
 * @Version: 
 */
@Controller
@RequestMapping(value = "/sys/user/*")
public class SysUserCommonController extends CommonController {

	@Autowired
	private ISysUserService sysUserService;
	
	/**
	 * 日志Service
	 */
	@Autowired
	private ISysLogService logService;
	
	@Autowired
	private ISysRoleUserService sysRoleUserService;
	
	/**
	 * 新增用户__
	 * 
	 * @param request
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "addSysUser", method = RequestMethod.POST)
	public @ResponseBody
	String doAddSysUser(HttpServletRequest request,HttpServletResponse response, SysUser sysUser) {
		try {
			SysUser user = getVisitor(request);
			sysUser.setCreaterId(user.getUserId());
//			sysUser.setCreaterName(user.getUserName());
			sysUser.setCreateTime(DateUtils.get(new Date(), "GM+8"));
			sysUser.setOrderNum(sysUserService.getOrderNum());
			sysUser.setUserName(sysUser.getUserName().trim());
			String backId = sysUserService.addSysUserBackId(sysUser);
			sysUser.setFreezeStatus("0");
			 //加入用户角色表
			SysRoleUser model = new SysRoleUser();
				model.setRoleId(sysUser.getUserDefaultRole());
				model.setUserId(backId);
				sysRoleUserService.addRoleUser(model);
			logService.log(request, getVisitor(request), "系统管理--用户管理", "新建用户",
					"新增【" + sysUser.getUserName() + "】用户", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
			return "s";
		}

		return "y";
	}
}
