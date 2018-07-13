package com.cdc.dc.metadata.yuan.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;

import com.cdc.dc.metadata.yuan.model.YuanTableColumnManage;
import com.cdc.dc.metadata.yuan.model.YuanTableManage;
import com.cdc.dc.metadata.yuan.service.IYuanService;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-12 下午4:18:37
 */
public class YuanServiceImpl implements IYuanService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public YuanTableManage saveOrUpdate(YuanTableManage vo) {
		enterpriseService.saveOrUpdate(vo);
		return vo;
	}

	@Override
	public void saveOrUpdate(YuanTableColumnManage vo) {
		enterpriseService.saveOrUpdate(vo);
	}
	
	
}
