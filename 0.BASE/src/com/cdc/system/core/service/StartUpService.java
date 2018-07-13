package com.cdc.system.core.service;

import java.util.List;

import model.sys.entity.SysModule;
import model.sys.entity.SysRole;
import model.sys.entity.SysRolePrivilges;

import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.model.SysParameterType;


public class StartUpService implements IStartUpService { 

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public List<?> loadModule() throws Exception {
		QueryBuilder query = new QueryBuilder(SysModule.class);
		query.orderBy("seq");
		List<?> list = enterpriseService.query(query, 0);
		return list;
	}

	public List<SysParameter> loadParam() throws Exception {
		QueryBuilder query1 = new QueryBuilder(SysParameter.class);
		query1.orderBy("orderId");
		@SuppressWarnings("unchecked")
		List<SysParameter> parameters = (List<SysParameter>) enterpriseService.query(query1, 0);
		return parameters;
	}

	public List<SysParameterType> loadParamType() throws Exception {
		QueryBuilder query = new QueryBuilder(SysParameterType.class);
		@SuppressWarnings("unchecked")
		List<SysParameterType> parameterTypes = (List<SysParameterType>) enterpriseService.query(query, 0);
		return parameterTypes;
	}

	public List<SysRolePrivilges> loadRolePrivilges() throws Exception {
		@SuppressWarnings("unchecked")
		List<SysRolePrivilges> rolePrivilges = (List<SysRolePrivilges>) enterpriseService.query(new QueryBuilder(SysRolePrivilges.class), 0);
		return rolePrivilges;
	}

	public List<SysRole> loadRole() throws Exception {
		@SuppressWarnings("unchecked")
		List<SysRole> roles = (List<SysRole>) enterpriseService.query(new QueryBuilder(SysRole.class), 0);
		return roles;
	}

}
