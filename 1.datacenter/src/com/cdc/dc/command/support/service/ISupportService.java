package com.cdc.dc.command.support.service;

import com.cdc.dc.command.manage.model.CommandSupportorg;
import com.trustel.common.ItemPage;

public interface ISupportService {

	ItemPage findSupport(CommandSupportorg support);

}
