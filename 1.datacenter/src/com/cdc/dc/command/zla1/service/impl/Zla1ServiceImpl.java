package com.cdc.dc.command.zla1.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.command.zla1.service.IZla1Service;
import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.model.RulesFileUpload;

public class Zla1ServiceImpl implements IZla1Service {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public List<RulesFileUpload> queryRulesFileList(String fileTempId) {
		if(!StringUtils.isNotEmpty(fileTempId)){
			return new ArrayList<RulesFileUpload>();
		}
		QueryBuilder query = new QueryBuilder(RulesFileUpload.class);
		query.where("rulesInfoid", fileTempId, QueryAction.EQUAL);//根据ID
		query.where("status", RulesCommon.rulesFileUploadStatus_Save, QueryAction.EQUAL );//已存
		query.orderBy("createTime desc");//按创建时间排序
		return (List)enterpriseService.query(query, 0);
	}
}
