package com.cdc.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;



import model.sys.entity.SysModule;
import model.sys.entity.SysUser;

import com.cdc.sys.form.DicFailImportObject;
import com.cdc.sys.form.SysModuleForm;
import com.trustel.common.ItemPage;

public interface ISysModuleService {
	public ItemPage querySysModule(SysModuleForm form) throws Exception;

	public void addSysModule(SysModule sysModule) throws Exception;

	public void modifySysModule(SysModule sysModule) throws Exception;

	public SysModule getSysModuleById(String moduleId) throws Exception;

	public void deleteSysModule(String moduleId) throws Exception;

	public List<SysModule> queryAll() throws Exception;
	
	public SysModule queryModuleByName(String moduleCode) throws Exception;
	
	/**
	 * 批量保存菜单
	 * @param visitor
	 * @param file
	 * @param request
	 * @param specificationId
	 * @return
	 * @throws RuntimeException
	 */
	public List<DicFailImportObject> saveData(SysUser visitor, String file,
			HttpServletRequest request)
			throws RuntimeException;
	
	/**
	 * 刷新菜单缓存
	 * @author WEIFEI
	 * @date 2016-4-21 下午2:43:54	void
	 */
	public void loadModule() throws Exception;
}
