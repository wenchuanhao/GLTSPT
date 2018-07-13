package com.cdc.dc.command.zla1.service;

import java.util.List;

import com.cdc.dc.rules.model.RulesFileUpload;

public interface IZla1Service {
	
	/**
	 * 查询附件列表
	 * @param fileTempId
	 * @return
	 */
	List<RulesFileUpload> queryRulesFileList(String fileTempId);

}
