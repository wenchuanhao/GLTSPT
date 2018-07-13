package com.cdc.system.core.authentication.service;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysUser;

/**
 * 
 * @Description:  权限验证的service  
 * @Author:       sunsf    
 * @UpdateUser:   sunsf  
 * @UpdateDate:   2012-7-18 下午03:30:11   
 * @UpdateRemark: 第一个版本功能完善
 * @Version:      V1.0
 */
public interface IAuthenticationInterceptorService
{

	public boolean accessible(HttpServletRequest request, SysUser visitor, String privilegeCode);
}
