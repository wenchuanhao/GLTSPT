package com.cdc.inter.client.ws.mail.service;

import com.cdc.inter.client.ws.mail.form.InterfaceMailinfoRecordsForm;
import com.trustel.common.ItemPage;

public interface IEmailSendedLogService {

	ItemPage queryEmailSendedLog(InterfaceMailinfoRecordsForm form);

}
