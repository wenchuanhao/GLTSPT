package com.cdc.dc.command.support.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.command.manage.model.CommandSupportorg;
import com.cdc.dc.command.support.service.ISupportService;
import com.trustel.common.ItemPage;

public class SupportServiceImpl implements ISupportService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage findSupport(CommandSupportorg support) {
		QueryBuilder query = new QueryBuilder(CommandSupportorg.class);
		query.where("supportorgName",support.getSupportorgName(),QueryAction.LIKE);
		query.where("status",CommandSupportorg.supportorg_Save,QueryAction.EQUAL);
		query.orderBy("updateTime desc");
		return enterpriseService.query(query, support);
	}
}
