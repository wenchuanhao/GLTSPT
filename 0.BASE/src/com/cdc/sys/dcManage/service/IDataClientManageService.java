package com.cdc.sys.dcManage.service;

import java.util.List;

import com.cdc.sys.dcManage.form.DataClientManageForm;
import com.cdc.sys.dcManage.model.DataClientManage;
import com.trustel.common.ItemPage;

/**
 * 获取数据接口
 * @author xms
 *
 */
public interface IDataClientManageService {
	
	/**
	 * 查询数据获取信息列表
	 * @return ItemPage
	 */
	ItemPage queryDataManagePage(DataClientManageForm manageForm);
	
	/**
	 * 根据ID查询数据获取单
	 * @param id
	 * @return
	 */
	List<DataClientManage> queryDataManageById(String id);
	
	/**
	 * 保存数据单
	 * @param clientManage
	 */
	void addDataManage(DataClientManage clientManage,String type);
	
	/**
	 * 
	 * @param ids
	 */
	void delDataManage(List<DataClientManage> manages);
	
	/**
	 * 查询数据分页
	 * @param clientManage
	 * @return
	 */
	ItemPage queryDataManage(DataClientManageForm clientManageForm);
}
