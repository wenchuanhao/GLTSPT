package com.cdc.system.core.startup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.sys.entity.SysModule;
import model.sys.entity.SysRole;
import model.sys.entity.SysRolePrivilges;

import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.model.SysParameterType;
import com.cdc.system.core.cache.DataCache;
import com.cdc.system.core.service.IStartUpService;

/**
 * 
 * 
 * @Description: 初始化系统数据
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-25 下午6:07:04
 * @UpdateRemark:
 * @Version: V1.0
 */
public class DefaultLoadService {
	private IStartUpService startUpService;;

	public void setStartUpService(IStartUpService startUpService) {
		this.startUpService = startUpService;
	}

	public void loadParam() throws Exception {
		System.out.println("====>>开始加载系统参数");
		DataCache cache = DataCache.getInstance();
		List<SysParameterType> parameterTypes = startUpService.loadParamType();
		List<SysParameter> parameters = startUpService.loadParam();
		cache.setSysParameters(parameters);
		Map<String, List<SysParameter>> temp = new HashMap<String, List<SysParameter>>();
		for (SysParameterType sysParameterType : parameterTypes) {
			List<SysParameter> temp1 = new ArrayList<SysParameter>();
			for (SysParameter parameter : parameters) {
				if (parameter.getParameterTypeId().equals(sysParameterType.getParameterTypeId()))
					temp1.add(parameter);
			}
			temp.put(sysParameterType.getParameterTypeCode(), temp1);
		}
		cache.setParameters(temp);
		System.out.println("====>>加载系统参数成功");

	}

	public void loadRolePrivilges() throws Exception {
		System.out.println("====>>开始加载系统权限");
		List<SysRolePrivilges> rolePrivilges = startUpService.loadRolePrivilges();
		List<SysRole> roles = startUpService.loadRole();
		Map<String, List<String>> sysPrivilges = new HashMap<String, List<String>>();
		for (SysRole sysRole : roles) {
			List<String> privilges = new ArrayList<String>();
			for (SysRolePrivilges sysRolePrivilges : rolePrivilges) {
				if (sysRolePrivilges.getRoleId().equals(sysRole.getRoleId()))
					privilges.add(sysRolePrivilges.getModuleCode());
			}
			sysPrivilges.put(sysRole.getRoleName(), privilges);
		}
		/*List<RdTeamRolePrivilges> rdTeamRolePrivilges = startUpService.loadTeamRolePrivilges();
		List<RdTeamRole> rdTeamRole = startUpService.loadTeamRole();
		for (RdTeamRole sysRole : rdTeamRole) {
			List<String> privilges = new ArrayList<String>();
			for (RdTeamRolePrivilges sysRolePrivilges : rdTeamRolePrivilges) {
				if (sysRolePrivilges.getTeamRoleId().equals(sysRole.getTeamRoleId()))
					privilges.add(sysRolePrivilges.getModuleCode());
			}
			sysPrivilges.put(sysRole.getTeamRoleName(), privilges);
		}*/
		DataCache.getInstance().setSysPrivilges(sysPrivilges);
		System.out.println("====>>加载系统权限成功");
	}

	public void loadModule() throws Exception {
		System.out.println("====>>开始加载系统模块");
		List<?> list = startUpService.loadModule();
		List<SysModule> listTemp = new ArrayList<SysModule>();
		List<SysModule> listTemp1 = new ArrayList<SysModule>();
		List<SysModule> oneMenu = new ArrayList<SysModule>();
		Map<String, List<SysModule>> twoMenu = new HashMap<String, List<SysModule>>();
		Map<String, List<SysModule>> threeMenu = new HashMap<String, List<SysModule>>();
		Map<String, List<SysModule>> fourMenu = new HashMap<String, List<SysModule>>();
		if (null != list && 0 < list.size()) {
			// 一级菜单
			for (Object obj : list) {
				if (obj != null) {
					SysModule module = (SysModule) obj;
					if (null != module.getParentCode() && !"".equals(module.getParentCode()))// && !"0".equals(module.getIsMenu())
						if (module.getParentCode().equals("ROOT"))
							oneMenu.add(module);
						else
							listTemp.add(module);
				}
			}
			for (SysModule sysModule : listTemp) {
				if (sysModule.getMenuLevel() != 1)
					listTemp1.add(sysModule);
			}
			listTemp.clear();
			if (null != listTemp1) {
				// 二级菜单
				for (SysModule oneMenuTemp : oneMenu) {
					List<SysModule> temp = new ArrayList<SysModule>();
					for (SysModule sysModule : listTemp1) {
						if (sysModule.getParentCode().equals(oneMenuTemp.getModuleCode()))
							temp.add(sysModule);
					}
					twoMenu.put(oneMenuTemp.getModuleCode(), temp);
				}
				for (SysModule sysModule : listTemp1) {
					if (sysModule.getMenuLevel() != 2)
						listTemp.add(sysModule);
				}
				listTemp1.clear();
				if (null != listTemp) {
					// 三级菜单
					for (Entry<String, List<SysModule>> entry : twoMenu.entrySet()) {
						List<SysModule> twoMenus = entry.getValue();

						for (SysModule twoMenuTemp : twoMenus) {
							List<SysModule> temp = new ArrayList<SysModule>();
							for (SysModule sysModule : listTemp)
								if (sysModule.getParentCode().equals(twoMenuTemp.getModuleCode()))
									temp.add(sysModule);
							threeMenu.put(twoMenuTemp.getModuleCode(), temp);
						}

					}
					for (SysModule sysModule : listTemp) {
						if (sysModule.getMenuLevel() != 3)
							listTemp1.add(sysModule);
					}
					listTemp.clear();
					if (null != listTemp1) {
						// 四级菜单
						for (Entry<String, List<SysModule>> entry : threeMenu.entrySet()) {
							List<SysModule> threeMenus = entry.getValue();
							boolean flag2 = true;
							for (SysModule threeMenuTemp : threeMenus) {
								List<SysModule> temp = new ArrayList<SysModule>();
								for (SysModule sysModule : listTemp1)
									if (flag2 && sysModule.getParentCode().equals(threeMenuTemp.getModuleCode()))
										temp.add(sysModule);
								fourMenu.put(threeMenuTemp.getModuleCode(), temp);
							}
						}
						listTemp1.clear();
					}
				}
			}
		}
		DataCache cache = DataCache.getInstance();
		cache.setOneMenu(oneMenu);
		cache.setTwoMenu(twoMenu);
		cache.setThreeMenu(threeMenu);
		cache.setFourMenu(fourMenu);
		// oneMenu.clear();
		// twoMenu.clear();
		// threeMenu.clear();
		// fourMenu.clear();
		System.out.println("====>>加载系统模块成功");
	}
	
	
}
