package com.cdc.sys.service;

import java.util.List;

import com.cdc.sys.form.AppSysManageForm;
import com.trustel.common.ItemPage;

import model.sys.entity.AppSysManage;

/**
 * 
 * @author xms
 *应用系统管理接口
 */
public interface IAppSysManage {
	/**
	 * 新增
	 */
	public void addMange(AppSysManage appSysManage,String type);
	
	/**
	 * 查询
	 */
	public ItemPage queryMange(AppSysManageForm manageForm);
	
	/**
	 * 查询应用系统
	 */
	public ItemPage querySysMange(AppSysManageForm manageForm);
	
	/**
	 * 根据ID查询
	 */
	public List<AppSysManage> queryMangeById(String  id);
	
	/**
	 * 删除
	 */
	void delManage(List<AppSysManage> sys);
	
	/**
	 * 修改
	 */
	void updateManage();
}
