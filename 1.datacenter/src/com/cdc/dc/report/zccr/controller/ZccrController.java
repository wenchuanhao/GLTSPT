package com.cdc.dc.report.zccr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.common.properties.DCConfig;
import com.cdc.util.DesUtil;

/**
 * 登录自定义报表系统
 * @author WEIFEI
 * @date 2016-12-9 上午11:18:59
 */
@Controller
@RequestMapping(value="/zccr/")
public class ZccrController {
	
	@RequestMapping(value="login" , method = {RequestMethod.POST,RequestMethod.GET})
	public void login(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		SysUser sysUser = (SysUser)request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
		response.setContentType("text/html;charset=utf-8");
		try {
			String logonId = sysUser.getAccount();
			if(logonId !=null && !logonId.equals("")){
				if (logonId.equals("admin")) {
					logonId = "zhengwh";
				}
				String basePath = DCConfig.getProperty("ZCCR_URL");
				String password = DCConfig.getProperty("ZCCR_PWD");
				DesUtil des = new DesUtil();
				String s = "<script type='text/javascript' src='"+basePath+"/bpaf/js/commAjax.js'></script>" +
							"<script>sendURL('"+basePath+"/logout.do?flag=close');</script>"+
							"<form method='post' action='"+basePath+"/urlogin' name='loginform'>" +
							"<input type='hidden' name='username' value='"+logonId+"'/>" +
							"<input type='hidden' name='password' value='"+des.decrypt(password, DesUtil.DES_KEY_STRING)+"'/>" +
							"</form>" +
							"<script>loginform.submit();</script>";
							response.getWriter().print(s);							
			}
		} catch (Exception e) {
			response.getWriter().print("<script>alert('登录自定义报表系统失败！');window.close();</script>");
			e.printStackTrace();
		}
	}
}
