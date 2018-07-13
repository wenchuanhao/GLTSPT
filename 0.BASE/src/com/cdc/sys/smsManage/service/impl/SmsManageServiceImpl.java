package com.cdc.sys.smsManage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.sys.entity.SysUser;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.dict.model.SysParameterType;
import com.cdc.sys.smsManage.form.SmsManageForm;
import com.cdc.sys.smsManage.model.SmsManage;
import com.cdc.sys.smsManage.model.SmsSendPerson;
import com.cdc.sys.smsManage.service.ISmsManageService;
import com.trustel.common.ItemPage;

/**短信管理service
 * 
 * @author xms
 *
 */
public class SmsManageServiceImpl implements ISmsManageService{
	
	
	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public void addParameterType(SysParameterType parameterType) throws Exception {
		enterpriseService.save(parameterType);
	}

	@Override
	public ItemPage querySmsManage(SmsManageForm form) {
		QueryBuilder query = new QueryBuilder(SmsManage.class);
		if(form.getSmsGroupNmae()!=null && !form.getSmsGroupNmae().trim().equals("")){
			query.where("smsGroupNmae",form.getSmsGroupNmae(),QueryAction.EQUAL);
		}
		return enterpriseService.query(query, form);
	}

	@Override
	public String addSmsManage(SmsManage manage, SmsSendPerson sendPerson,SysUser user) {
		manage.setCreateDate(new Date());
		manage.setCreatePerson(user.getUserName());
		manage.setCreatePersonId(user.getUserId());
		manage.setStaus("0");
		enterpriseService.save(manage);
		List<SmsSendPerson> list = manage.getSendPersons();
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setSmsManageId(manage.getId());
			}
		}
		enterpriseService.save(list);
		return "1";
	}

	@Override
	public void delSmsManage(String ids) {
		QueryBuilder query = new QueryBuilder(SmsManage.class);
		query.where("id",ids.split(","));
		enterpriseService.delete(query);
		
		//删除子表
		QueryBuilder query2 = new QueryBuilder(SmsSendPerson.class);
		query2.where("smsManageId",ids.split(","));
		enterpriseService.delete(query2);
	}

	@Override
	public void editSmsManage(SmsManage manage,SmsSendPerson person) {
		enterpriseService.updateObject(manage);
		List<SmsSendPerson> persons = manage.getSendPersons();
		if(persons.size()!=0){
			List<SmsSendPerson> list  =new ArrayList<SmsSendPerson>();
			List<SmsSendPerson> list2  =new ArrayList<SmsSendPerson>();
			for (int i = 0; i < persons.size(); i++) {
				if(persons.get(i).getId()!=null){
					SmsSendPerson person2 = new SmsSendPerson();
					person2.setDetail(persons.get(i).getDetail());
					person2.setId(persons.get(i).getId());
					person2.setMobile(persons.get(i).getMobile());
					person2.setName(persons.get(i).getName());
					person2.setNameId(persons.get(i).getNameId());
					person2.setSmsManageId(persons.get(i).getSmsManageId());
					list.add(person2);
				}else {
					SmsSendPerson sendPerson = new SmsSendPerson();
					sendPerson.setDetail(persons.get(i).getDetail());
					sendPerson.setId(persons.get(i).getId());
					sendPerson.setMobile(persons.get(i).getMobile());
					sendPerson.setName(persons.get(i).getName());
					sendPerson.setNameId(persons.get(i).getNameId());
					sendPerson.setSmsManageId(manage.getId());
					list2.add(sendPerson);
				}
			}
			if(list.size()>0){
				enterpriseService.updateAll(list);
			}
			
			if(list2.size()>0){
				enterpriseService.save(list2);
			}
			
		}
	}

	@Override
	public List<SmsManage> querySmsManageById(String id) {
		QueryBuilder query = new QueryBuilder(SmsManage.class);
		query.where("id",id,QueryAction.EQUAL);
		return (List<SmsManage>) enterpriseService.query(query,0);
		 
	}

	@Override
	public List<SmsSendPerson> querySmsSendPersons(String id) {
		QueryBuilder query = new QueryBuilder(SmsSendPerson.class);
		query.where("smsManageId",id,QueryAction.EQUAL);
		return (List<SmsSendPerson>) enterpriseService.query(query, 0);
	}
}
