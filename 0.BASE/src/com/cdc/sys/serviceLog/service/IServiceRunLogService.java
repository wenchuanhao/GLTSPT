package com.cdc.sys.serviceLog.service;

import com.cdc.sys.serviceLog.form.ServiceRunLogForm;
import com.trustel.common.ItemPage;

public interface IServiceRunLogService {
	
	/**
	 * 服务运行日志分页查询
	 * @param logForm
	 * @return
	 */
	ItemPage queryServiceLogPage(ServiceRunLogForm logForm,String types,String interTypes);
	
	/**
	 * 批量删除
	 */
	void delServiceLog(String ids);
}
