package com.cdc.sys.service;

import java.util.List;

import oracle.net.aso.a;

import org.springframework.stereotype.Service;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.dcManage.model.DataClientManage;
import com.cdc.sys.form.AppSysManageForm;
import com.trustel.common.ItemPage;

import model.sys.entity.AppSysManage;

@Service
public class AppSysManageService implements IAppSysManage{
	private IEnterpriseService enterpriseService;

	public IEnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public void addMange(AppSysManage addMange,String type) {
		if(type.equals("add")){
			enterpriseService.save(addMange);
		}else {
			enterpriseService.saveOrUpdate(addMange);
		}
	}

	@Override
	public ItemPage queryMange(AppSysManageForm manageForm) {
		QueryBuilder query = new QueryBuilder(AppSysManage.class);
		if(manageForm.getCode()!=null && !manageForm.getCode().trim().equals("")){
			query.where("code",manageForm.getCode(),QueryAction.LIKE);
		}
		if(manageForm.getName()!=null && !manageForm.getName().trim().equals("")){
			query.where("name",manageForm.getName(),QueryAction.LIKE);
		}
		query.where("deleted","N",QueryAction.EQUAL);
		return (ItemPage) enterpriseService.query(query, manageForm);
	}
	
	@Override
	public ItemPage querySysMange(AppSysManageForm manageForm) {
		StringBuffer sb = new StringBuffer();
		sb.append("select d.id,d.code,d.name,d.type from app_sys_manage d inner join( " +
		"select distinct a.id from app_sys_manage  a left join data_client_manage b  on b.sys_id=a.id " +
		" where a.deleted='N' and a.id not in( select  sys_ID from data_client_manage WHERE deleted='N' ) ) y on d.id=y.id");
		
		if(manageForm.getName()!=null && !manageForm.getName().trim().equals("")){
			sb.append(" and a.name like '%"+manageForm.getName()+"%' ");
		}
		
		return (ItemPage) enterpriseService.findBySql(sb.toString(), manageForm);
	}
	
	@Override
	public void delManage(List<AppSysManage> sys) {
		enterpriseService.updateAll(sys);
		
	}

	@Override
	public void updateManage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AppSysManage> queryMangeById(String id) {
		QueryBuilder query = new QueryBuilder(AppSysManage.class);
		query.where("id",id.split(","));
		return (List<AppSysManage>) enterpriseService.query(query, 0);
	}

}
