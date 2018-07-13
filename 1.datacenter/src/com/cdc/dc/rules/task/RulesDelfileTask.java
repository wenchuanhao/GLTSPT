package com.cdc.dc.rules.task;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.CommonController;

public class RulesDelfileTask extends CommonController{

	private Log logger = LogFactory.getLog(RulesDelfileTask.class);
	private IRulesService rulesService;
	
	public IRulesService getRulesService() {
		return rulesService;
	}

	public void setRulesService(IRulesService rulesService) {
		this.rulesService = rulesService;
	}


	public void delFileTask(){
		logger.info("***********附件清理定时任务开始**********");
		List<RulesFileUpload> list  = rulesService.queryDelRulesFileUpload();
		if(list != null){
			for (RulesFileUpload rulesFileUpload : list) {
				
				Date delTime = rulesFileUpload.getUpdateTime();
				//清理大于30天的数据
				Long timeout = rulesService.getConfigLong("DEL_FILE_TIMEOUT", 30L);
				if(new Date().getTime() - delTime.getTime() < timeout * 24 * 3600 * 1000){
					continue;
				}
				
				//附件信息
				if(RulesCommon.rulesFileUploadIsParent_N.equals(rulesFileUpload.getIsParent())){
					String filePath = rulesFileUpload.getFilePath();
					File file = new File(filePath);
					if(file.exists()){
						file.delete();//物理删除
					}
				}
				rulesService.delEntity(rulesFileUpload);
			}
		}
	    logger.info("***********附件清理定时任务结束**********");
	}

}
