package com.cdc.sys.dcManage.service.impl;

import java.util.List;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.dcManage.form.DataClientManageForm;
import com.cdc.sys.dcManage.model.DataClientManage;
import com.cdc.sys.dcManage.service.IDataClientManageService;
import com.trustel.common.ItemPage;
/**
 * 获取数据实现类
 * @author xms
 *
 */
public class DataClientManageServiceImpl implements IDataClientManageService{
	
	private IEnterpriseService enterpriseService;

	public IEnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage queryDataManagePage(DataClientManageForm manageForm) {
		QueryBuilder query = new QueryBuilder(DataClientManage.class);
		if(manageForm.getSysId()!=null && !manageForm.getSysId().trim().equals("")){
			query.where("sysId",manageForm.getSysId(),QueryAction.EQUAL);
		}
		if(manageForm.getSysName()!=null && !manageForm.getSysName().trim().equals("")){
			query.where("sysName",manageForm.getSysName(),QueryAction.EQUAL);
		}
		query.where("deleted","N",QueryAction.EQUAL);
		return (ItemPage) enterpriseService.query(query, manageForm);
		
	}

	@Override
	public List<DataClientManage> queryDataManageById(String id) {
		QueryBuilder query = new QueryBuilder(DataClientManage.class);
		query.where("id",id.split(","));
		query.where("deleted","N",QueryAction.EQUAL);
		return (List<DataClientManage>) enterpriseService.query(query, 0);
	}

	@Override
	public void addDataManage(DataClientManage clientManage,String type) {
		if(type.equals("add")){
			enterpriseService.save(clientManage);
		}else {
			enterpriseService.updateObject(clientManage);
		}
		
	}

	@Override
	public void delDataManage(List<DataClientManage> manages) {
		enterpriseService.updateAll(manages);
		
	}

	@Override
	public ItemPage queryDataManage(DataClientManageForm manageForm) {
		StringBuffer sb = new StringBuffer();
		sb.append("select d.id,d.FREQUENCY,d.sys_name,d.type from data_client_manage d inner join "); 
		sb.append(" (select distinct a.id from data_client_manage  a left join sms_manage b  on b.p_id=a.id "); 
		sb.append(" where a.deleted='N' and a.id not in( select  p_id from sms_manage WHERE STAUS='0' ) ) y ");
		sb.append(" on d.id=y.id");
		
		if(manageForm.getSysName()!=null && !manageForm.getSysName().trim().equals("")){
			sb.append(" and d.sys_name like '%"+manageForm.getSysName()+"%' ");
		}
		
		return (ItemPage) enterpriseService.findBySql(sb.toString(), manageForm);
	}
	
}
