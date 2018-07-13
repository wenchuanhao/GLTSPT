package com.cdc.sys.dataWarn.service;

import com.cdc.sys.dataWarn.form.DataSourceWarnForm;
import com.cdc.sys.dataWarn.model.DataSourceWarn;
import com.trustel.common.ItemPage;

/**
 * 数据源告警
 * @author xms
 *
 */
public interface IDataWarnService {
	
	/**
	 * 查询数据预警分页
	 * @param warnForm
	 * @param type TODO
	 * @return
	 */
	ItemPage queryDataPage(DataSourceWarnForm warnForm, String type);
	
	/**
	 * 设置数据源阀值
	 * @param sourceWarn
	 */
	void addWarn(DataSourceWarn sourceWarn);
}
