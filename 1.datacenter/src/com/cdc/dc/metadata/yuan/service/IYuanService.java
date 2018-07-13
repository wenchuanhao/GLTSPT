package com.cdc.dc.metadata.yuan.service;

import com.cdc.dc.metadata.yuan.model.YuanTableColumnManage;
import com.cdc.dc.metadata.yuan.model.YuanTableManage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-12 下午4:18:42
 */
public interface IYuanService {

	public YuanTableManage saveOrUpdate(YuanTableManage vo);

	public void saveOrUpdate(YuanTableColumnManage vo);
	
}
