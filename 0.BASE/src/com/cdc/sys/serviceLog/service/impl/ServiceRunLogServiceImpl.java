package com.cdc.sys.serviceLog.service.impl;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.serviceLog.form.ServiceRunLogForm;
import com.cdc.sys.serviceLog.model.ServiceRunLog;
import com.cdc.sys.serviceLog.service.IServiceRunLogService;
import com.trustel.common.ItemPage;

public class ServiceRunLogServiceImpl implements IServiceRunLogService{
	
	private IEnterpriseService enterpriseService;

	public IEnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage queryServiceLogPage(ServiceRunLogForm logForm,String types, String interTypes) {
		QueryBuilder query = new QueryBuilder(ServiceRunLog.class);
		if(types!=null && !types.trim().equals("")){
			query.where("type",Integer.parseInt(types),QueryAction.EQUAL);
		}
		if(interTypes!=null && !interTypes.trim().equals("")){
			query.where("interType",Integer.parseInt(interTypes),QueryAction.EQUAL);
		}
		ItemPage iPage = enterpriseService.query(query, logForm);
		return iPage;
	}

	@Override
	public void delServiceLog(String ids) {
		QueryBuilder query = new QueryBuilder(ServiceRunLog.class);
		query.where("id",ids.split(","));
		enterpriseService.delete(query);
		
	}
}
