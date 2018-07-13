package com.cdc.system.core.authentication.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.system.SystemConstant;

import model.sys.entity.SysUser;

public class DefaultAuthenticationInterceptorService implements IAuthenticationInterceptorService
{
	private Log logger = LogFactory.getLog(getClass());
	private static DefaultAuthenticationInterceptorService instance = null;

	private DefaultAuthenticationInterceptorService()
	{

	}

	public static DefaultAuthenticationInterceptorService getInstance()
	{
		if (instance == null)
			instance = new DefaultAuthenticationInterceptorService();
		return instance;
	}

	private boolean accessibleCache(HttpServletRequest request, String privilegeCode)
	{
		logger.info("通过SESSION缓存判断用户权限！");
		SysUser visitor = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
		if (visitor != null)
		{
			List<String> privileges = visitor.getPrivileges();
			if (privileges.contains(privilegeCode))
				return true;
		}
		return false;
	}

	public boolean accessible(HttpServletRequest request, SysUser visitor, String privilegeCode)
	{
		if (visitor != null)
		{// 通过缓存判断
			return accessibleCache(request, privilegeCode);
		} else
			return false;
	}


}
