package com.cdc.sys.smsManage.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.smsManage.form.SmsManageLogForm;
import com.cdc.sys.smsManage.model.SmsManageLog;
import com.cdc.sys.smsManage.service.ISmsManageLogService;
import com.trustel.common.DateFunc;
import com.trustel.common.ItemPage;

/**
 * 短信发送日志
 * 
 * @author
 * 
 */
public class SmsManageLogServiceImpl implements ISmsManageLogService {

	private IEnterpriseService enterpriseService;

	public IEnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage querySmsManageLog(SmsManageLogForm form) {
		QueryBuilder query = new QueryBuilder(SmsManageLog.class);
		
		if (StringUtils.isNotEmpty(form.getMobile())) {
			query.where("mobile", form.getMobile(), QueryAction.LIKE);
		} 
		if(StringUtils.isNotEmpty(form.getCreateTime())){			
			query.where("createTime", DateFunc.getDateFromString(form.getCreateTime(), "yyyy-MM-dd"), QueryAction.LIKE_POSTFIX);
		}
		query.orderBy("createTime",false);
		return enterpriseService.query(query, form);

	}
}
