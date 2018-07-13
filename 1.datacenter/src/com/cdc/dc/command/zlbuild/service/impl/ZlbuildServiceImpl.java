package com.cdc.dc.command.zlbuild.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;

import com.cdc.dc.command.zlbuild.service.IZlbuildService;




public class ZlbuildServiceImpl implements IZlbuildService{

	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
}
