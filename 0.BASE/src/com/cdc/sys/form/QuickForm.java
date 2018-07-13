package com.cdc.sys.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

/**
 * 快捷方式
 * @author 
 *
 */
public class QuickForm extends PageQueryForm{

	private String userId;//用户id
	private String menuCode;//菜单id
	private Date createTime;//创建时间
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

	  
	
	
	
	
}
