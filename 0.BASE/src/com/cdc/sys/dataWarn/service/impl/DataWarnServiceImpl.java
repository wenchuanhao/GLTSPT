package com.cdc.sys.dataWarn.service.impl;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.dataWarn.form.DataSourceWarnForm;
import com.cdc.sys.dataWarn.model.DataSourceWarn;
import com.cdc.sys.dataWarn.service.IDataWarnService;
import com.trustel.common.ItemPage;

public class DataWarnServiceImpl implements IDataWarnService {
	private IEnterpriseService enterpriseService;

	public IEnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
	@Override
	public ItemPage queryDataPage(DataSourceWarnForm warnForm, String type) {
		QueryBuilder query = new QueryBuilder(DataSourceWarn.class);
		if(warnForm.getStartDate()!=null){
			query.where("warnTime",warnForm.getStartDate(),QueryAction.GE);
		}
		if(warnForm.getEndDate()!=null){
			query.where("warnTime",warnForm.getEndDate(),QueryAction.LE);
		}
		query.where("config",type,QueryAction.EQUAL);
		return enterpriseService.query(query, warnForm);
	}

	@Override
	public void addWarn(DataSourceWarn sourceWarn) {
		enterpriseService.save(sourceWarn);
	}

}
