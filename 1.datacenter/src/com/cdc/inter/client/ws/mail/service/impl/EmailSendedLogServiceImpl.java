package com.cdc.inter.client.ws.mail.service.impl;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.inter.client.ws.mail.form.InterfaceMailinfoRecordsForm;
import com.cdc.inter.client.ws.mail.service.IEmailSendedLogService;
import com.cdc.inter.client.ws.mail.service.model.InterfaceMailinfoRecords;
import com.trustel.common.ItemPage;

public class EmailSendedLogServiceImpl implements IEmailSendedLogService {
	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
	@Override
	public ItemPage queryEmailSendedLog(InterfaceMailinfoRecordsForm form){
		QueryBuilder query = new QueryBuilder(InterfaceMailinfoRecords.class);
		
		//用户
		if (StringUtils.isNotEmpty(form.getCreateUserid())) {
			query.where("createUserid", form.getCreateUserid(), QueryAction.LIKE);
		}
		//邮件地址
		if (StringUtils.isNotEmpty(form.getSemail())) {
			query.where("semail", form.getSemail(), QueryAction.LIKE);
		}
		//时间区间
		if( form.getLogStartTime() != null ){
			query.where("createTime", form.getLogStartTime(), QueryAction.GT);
		}
		if( form.getLogEndTime() != null ){
			Calendar cd = Calendar.getInstance();
		    cd.setTime(form.getLogEndTime());
		    cd.add(Calendar.DATE, 1);//增加一天
			query.where("createTime", cd.getTime(), QueryAction.LE);
		}
		
		query.orderBy("createTime",false);
		
		return enterpriseService.query(query, form);
	}
}
