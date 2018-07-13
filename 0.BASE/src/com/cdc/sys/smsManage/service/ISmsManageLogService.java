package com.cdc.sys.smsManage.service;

import com.cdc.sys.smsManage.form.SmsManageLogForm;
import com.trustel.common.ItemPage;

/**
 * 短信发送日志
 * 
 * @author 
 * 
 */
public interface ISmsManageLogService {

	ItemPage querySmsManageLog(SmsManageLogForm form);

}
