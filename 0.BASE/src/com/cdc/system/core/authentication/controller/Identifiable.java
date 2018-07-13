package com.cdc.system.core.authentication.controller;

/**
 * 
 * @Description:  验证和使用实现给接口类的功能的权限编码,建议使用类名或有意义的名字  
 * @Author:       sunsf    
 * @UpdateUser:   sunsf  
 * @UpdateDate:   2012-7-18 下午03:27:07   
 * @UpdateRemark: 第一个版本功能完善
 * @Version:      V1.0
 */
public interface Identifiable
{
	/**
	 * 
	 * 
	 * @return 返回本功能本功能的权限编码，通常对应 SystemModule 中的code字段值
	 */
	public String getPrivilegeCode();
}
