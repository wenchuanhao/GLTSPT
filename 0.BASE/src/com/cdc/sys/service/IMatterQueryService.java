package com.cdc.sys.service;


import java.util.List;
import java.util.Map;

import model.sys.entity.UserSetingMapping;

import com.cdc.sys.form.BackLogForm;
import com.trustel.common.ItemPage;




//我的工作台  查询
public interface IMatterQueryService {
	
	//查询我的所有的待办事项
	public ItemPage queryMatterList(int flag,BackLogForm backlogform,
			String backLogIdsStr,String actorId,String type);
	//获取待办待阅的条数
	public Map<String, ItemPage> queryMatterCount(int flag,BackLogForm backlogform,
			String backLogIdsStr,String actorId,String type);
	//查询角色权限
	public List queryUserRoleList(String userId);
	//保存选中的模块
	public void saveUserSelectMenu(UserSetingMapping usm);
	public ItemPage queryUserSelectMenu(String userId);
	
	/**
	 * 保存用户快捷菜单配置
	 * @param menuCodes
	 * @param userId
	 * @return
	 */
	public int flushUserMenuSetting(String[] menuCodes,String userId);
}
