package com.cdc.sys.controller;

import java.util.List;
import model.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;

/**
 * 初始化系统用户表排序列值
 * @author cyh
 *
 */
@Controller
@RequestMapping(value = "/cyhcore/*")
public class InitDataSysUserController extends CommonController{
	@Autowired
	private ISysUserService sysUserService; 
	/**
	 * 执行批量给系统用户表排序列赋值从1开始
	 * 
	 * @return login.jsp
	 * @throws Exception 
	 */
	@RequestMapping(value = "initNum", method = RequestMethod.GET)
	public String toLogin() throws Exception {
		List<SysUser> list = sysUserService.allUser();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				SysUser model=null;
				model=list.get(i);
				if(model!=null){
					model.setOrderNum(i+1);
					sysUserService.modifySysUser(model);
				}
			}
		}
		return "core/login/login";
	}
}
