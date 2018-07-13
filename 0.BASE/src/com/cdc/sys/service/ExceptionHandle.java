package com.cdc.sys.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysLog;
import model.sys.entity.SysUser;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;
import org.trustel.util.IpUtil;

import antlr.StringUtils;

public class ExceptionHandle  implements HandlerExceptionResolver  {
    
	private IEnterpriseService enterpriseService;
    
    public void setEnterpriseService(IEnterpriseService service) {
		this.enterpriseService = service;
	}
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception ex) {
		String msg = "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try
		{
		   ex.printStackTrace(pw);
		   msg = sw.toString();
		}
		finally
		{
		   pw.close();
		}
    	SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
		String operatiorIp = IpUtil.getIpAddrByRequest(request);
		SysLog log = new SysLog();
		log.setLogModuleType(request.getRequestURI());
		if(null != ex.getMessage() ){
			int length = ex.getMessage().length();
			if(length > 500) length = 500;
		  log.setLogModuleNote(ex.getMessage().substring(0,length));
		}else{
		  log.setLogModuleNote("未知异常");
		}
		if(msg != ""){
		  int length = msg.length();
		  if(length > 4000) length = 4000;
		  log.setLogDesc(msg.substring(0, length));
		}
		else{
	      log.setLogDesc(msg);
		}
		log.setOperateTime(new Date());
		log.setLogType("2");//异常为2
		log.setOperaterIP(operatiorIp);
		log.setUserId(sysUser.getAccount());
		log.setUserName(sysUser.getUserName());
		log.setLogStartTime(new Date());
		log.setLogEndTime(null);
		this.enterpriseService.save(log);
		
		return null;
	}

	public static void main(String[] args) {
		System.out.println("ssd".substring(0, 500));
	}

}
