package com.cdc.dc.divsion.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.trustel.service.IEnterpriseService;

import com.cdc.dc.divsion.service.IDivsionElecService;

public class DivsionElecServiceImpl implements IDivsionElecService{
	
	private IEnterpriseService enterpriseService;
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	@Override
	public List<Object[]> getSeriesDataByType(String sql) {
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
		return query.list();
	}
	@Override
	public void saveEntity(Object item) {
		enterpriseService.save(item);
	}
	
	@Override
	public void save(List<?> entityList) {
		enterpriseService.save(entityList);
	}
}
