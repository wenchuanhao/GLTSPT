package com.cdc.sys.smsManage.service;

import java.util.List;

import model.sys.entity.SysUser;

import com.cdc.sys.smsManage.form.SmsManageForm;
import com.cdc.sys.smsManage.model.SmsManage;
import com.cdc.sys.smsManage.model.SmsSendPerson;
import com.trustel.common.ItemPage;
/**
 * 短信管理接口
 * @author xms
 *
 */
public interface ISmsManageService {
	/**
	 * 短信接口分页查询
	 * @param form
	 * @return
	 */
	ItemPage querySmsManage(SmsManageForm form);
	
	/**
	 * 新增
	 * @param manage
	 * @param sendPerson
	 * @return
	 */
	String addSmsManage(SmsManage manage,SmsSendPerson sendPerson,SysUser user);
	
	/**
	 * 删除
	 * @param ids
	 */
	void delSmsManage(String ids);
	
	/**
	 * 编辑
	 * @param 
	 */
	void editSmsManage(SmsManage manage,SmsSendPerson person);
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	List<SmsManage> querySmsManageById(String id);
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	List<SmsSendPerson> querySmsSendPersons(String id);
}
