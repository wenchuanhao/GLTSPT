package com.cdc.sys.dict.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.dict.common.SysDictCommon;
import com.cdc.sys.dict.form.SysParameterForm;
import com.cdc.sys.dict.form.SysParameterTypeForm;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.model.SysParameterType;
import com.cdc.sys.dict.service.ISysParameterService;
import com.cdc.system.core.startup.DefaultLoadService;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;

public class SysParameterServiceImpl implements ISysParameterService {

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public void addParameterType(SysParameterType parameterType) throws Exception {
		enterpriseService.save(parameterType);
	}

	public void updateParameterType(SysParameterType parameterType) throws Exception {
		enterpriseService.updateObject(parameterType);
	}

	public void deleteParameterType(String sptId) throws Exception {
		QueryBuilder builder = new QueryBuilder(SysParameterType.class);
		builder.where("parameterTypeId", sptId, QueryAction.EQUAL);
		enterpriseService.delete(builder);
	}

	@SuppressWarnings("unchecked")
	public List<SysParameterType> queryParameterType() throws Exception {
		QueryBuilder builder = new QueryBuilder(SysParameterType.class);
		builder.where("status",SysDictCommon.sysParameterTypeStatus_Save,QueryAction.EQUAL);
		return (List<SysParameterType>) enterpriseService.query(builder, 0);
	}

	public SysParameterType queryParameterType(String sptId) throws Exception {
		return (SysParameterType) enterpriseService.getById(SysParameterType.class, sptId);
	}

	public void addParameter(SysParameter parameter) throws Exception {
		enterpriseService.save(parameter);
	}

	public void updateParameter(SysParameter parameter) throws Exception {
		enterpriseService.updateObject(parameter);
	}

	public void deleteParameter(String spId) throws Exception {
		QueryBuilder builder = new QueryBuilder(SysParameter.class);
		builder.where("parameterId", spId, QueryAction.EQUAL);
		enterpriseService.delete(builder);
	}

	@SuppressWarnings("unchecked")
	public List<SysParameter> queryParameter() throws Exception {
		QueryBuilder builder = new QueryBuilder(SysParameter.class);
		return (List<SysParameter>) enterpriseService.query(builder, 0);
	}

	@SuppressWarnings("unchecked")
	public List<SysParameter> queryParameterBySptId(String sptId) throws Exception {
		QueryBuilder builder = new QueryBuilder(SysParameter.class);
		builder.where("typeId", sptId, QueryAction.EQUAL);
		return (List<SysParameter>) enterpriseService.query(builder, 0);
	}

	public SysParameter queryParameter(String spId) throws Exception {
		return (SysParameter) enterpriseService.getById(SysParameter.class, spId);
	}

	public ItemPage queryParameter(SysParameterForm form) throws Exception {
		QueryBuilder query = new QueryBuilder("SysParameter p ,SysParameterType pt ");
		query.where("p.parameterTypeId = pt.parameterTypeId");
		// 搜索
		if (null != form) {
			if (null != form.getParameterName() && !form.getParameterName().equals("")){
				query.where("p.parameterName", form.getParameterName(), QueryAction.LIKE);
			}
			
			if (null != form.getParameterTypeName() && !form.getParameterTypeName().equals("")){
				query.where("pt.parameterTypeName", form.getParameterTypeName(), QueryAction.LIKE);
			}
			
			if (StringUtils.isNotBlank(form.getParameterCode())){//参数编码
				query.where("p.parameterCode", form.getParameterCode(), QueryAction.LIKE);
			}
			
			if (StringUtils.isNotBlank(form.getParameterValue())){//参数值
				query.where("p.parameterValue", form.getParameterValue(), QueryAction.LIKE);
			}
		}
		query.where("pt.status",SysDictCommon.sysParameterTypeStatus_Save,QueryAction.EQUAL);//已存
		query.where("p.status",SysDictCommon.sysParameterStatus_Save,QueryAction.EQUAL);//已存
		query.orderBy("p.parameterId", false);
		return enterpriseService.query(query, form);
	}

	public ItemPage queryParameterType(SysParameterTypeForm form) throws Exception {
		QueryBuilder query = new QueryBuilder(SysParameterType.class);
		// 搜索
		if (null != form) {
			if (null != form.getParameterTypeName() && !form.getParameterTypeName().equals(""))
				query.where("parameterTypeName", form.getParameterTypeName(), QueryAction.LIKE);
			if (null != form.getParameterTypeCode() && !form.getParameterTypeCode().equals(""))
				query.where("parameterTypeCode", form.getParameterTypeCode(), QueryAction.LIKE);
		}
		query.where("status",SysDictCommon.sysParameterTypeStatus_Save,QueryAction.EQUAL);//已存
		query.orderBy("createTime",false);
		return enterpriseService.query(query, form);
	}

	public SysParameterType queryParameterTypeByCode(String code) throws Exception {
		QueryBuilder query = new QueryBuilder(SysParameterType.class);
		query.where("parameterTypeCode", code, QueryAction.EQUAL);
		query.where("status",SysDictCommon.sysParameterTypeStatus_Save,QueryAction.EQUAL);//已存
		List<?> list = enterpriseService.query(query, 0);
		if (null != list && list.size() > 0)
			return (SysParameterType) list.get(0);
		else
			return null;
	}

	public SysParameter queryParameterByCode(String code) throws Exception {
		QueryBuilder query = new QueryBuilder(SysParameter.class);
		query.where("parameterCode", code, QueryAction.EQUAL);
		query.where("status",SysDictCommon.sysParameterStatus_Save,QueryAction.EQUAL);//已存
		List<?> list = enterpriseService.query(query, 0);
		if (null != list && list.size() > 0)
			return (SysParameter) list.get(0);
		else
			return null;
	}
	
	public SysParameter queryParameterByCode(String code,String parameterTypeId) {
		QueryBuilder query = new QueryBuilder(SysParameter.class);
		query.where("parameterCode", code, QueryAction.EQUAL);
		query.where("parameterTypeId", parameterTypeId, QueryAction.EQUAL);
		query.where("status",SysDictCommon.sysParameterStatus_Save,QueryAction.EQUAL);//已存
		List<?> list = enterpriseService.query(query, 0);
		if (null != list && list.size() > 0)
			return (SysParameter) list.get(0);
		else
			return null;
	}
	public SysParameter queryParameterByName(String parameterName) throws Exception {
		QueryBuilder query = new QueryBuilder(SysParameter.class);
		query.where("parameterName", parameterName, QueryAction.EQUAL);
		query.where("status",SysDictCommon.sysParameterStatus_Save,QueryAction.EQUAL);//已存
		List<?> list = enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return (SysParameter) list.get(0);
		return null;
	}
	public SysParameterType queryParameterTypeByName(String parameterTypeName) throws Exception {
		QueryBuilder query = new QueryBuilder(SysParameterType.class);
		query.where("parameterTypeName", parameterTypeName, QueryAction.EQUAL);
		query.where("status",SysDictCommon.sysParameterTypeStatus_Save,QueryAction.EQUAL);//已存
		List<?> list = enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return (SysParameterType) list.get(0);
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<SysParameter> checkParameter(String parameterTypeIds) throws Exception{
		QueryBuilder query=new QueryBuilder(SysParameter.class);
			query.where("parameterTypeId in ("+parameterTypeIds+")");
			query.where("status",SysDictCommon.sysParameterStatus_Save,QueryAction.EQUAL);//已存
		return (List<SysParameter>) enterpriseService.query(query, 0);
	}

	@Override
	public ItemPage queryDict(SysParameterForm form) {
		QueryBuilder query = new QueryBuilder("SysParameterType pt,SysParameter p");
		query.where("p.parameterTypeId = pt.parameterTypeId");
		// 搜索
		if (null != form) {
			if (null != form.getParameterName() && !form.getParameterName().equals("")){
				query.where("p.parameterName", form.getParameterName(), QueryAction.LIKE);
			}
			
			if (null != form.getParameterTypeName() && !form.getParameterTypeName().equals("")){
				query.where("pt.parameterTypeName", form.getParameterTypeName(), QueryAction.LIKE);
			}
			
			if (StringUtils.isNotBlank(form.getParameterCode())){//参数编码
				query.where("p.parameterCode", form.getParameterCode(), QueryAction.LIKE);
			}
			
			if (StringUtils.isNotBlank(form.getParameterValue())){//参数值
				query.where("p.parameterValue", form.getParameterValue(), QueryAction.LIKE);
			}
		}
		query.where("pt.status",SysDictCommon.sysParameterTypeStatus_Save,QueryAction.EQUAL);//已存
		query.where("p.status",SysDictCommon.sysParameterStatus_Save,QueryAction.EQUAL);//已存
		query.orderBy("p.parameterId", false);
		return enterpriseService.query(query, form);
	}

	@Override
	public String submit(SysParameterType parameterType, SysParameterForm parameter) throws Exception {
		String result = "1";
		
		//编辑更新
		if(StringUtils.isNotEmpty(parameterType.getParameterTypeId())){
			//删除原有类型,覆盖新类型

			QueryBuilder query = new QueryBuilder(SysParameterType.class);
			query.where("parameterTypeId",parameterType.getParameterTypeId(),QueryAction.EQUAL);
			enterpriseService.delete(query);
			
			QueryBuilder querySysParameter = new QueryBuilder(SysParameter.class);
			querySysParameter.where("parameterTypeId",parameterType.getParameterTypeId(),QueryAction.EQUAL);
			enterpriseService.delete(querySysParameter);
		}
		
		SysParameterType sysParameterType = queryParameterTypeByCode(parameterType.getParameterTypeCode());
		//更新操作
//		if(StringUtils.isNotEmpty(parameterType.getParameterTypeId())){
//			if(SysDictCommon.sysParameterTypeAllowUpdate_Y.equals(sysParameterType.getAllowUpdate())){//可修改
//				//更新类型
//				sysParameterType.setAllowUpdate(parameterType.getAllowUpdate());
//				sysParameterType.setCreaterId(parameterType.getCreaterId());
//				sysParameterType.setCreateTime(parameterType.getCreateTime());
//				sysParameterType.setParameterTypeCode(parameterType.getParameterTypeCode());
//				sysParameterType.setParameterTypeDesc(parameterType.getParameterTypeDesc());
//				sysParameterType.setParameterTypeName(parameterType.getParameterTypeName());
//				sysParameterType.setParameterTypeValue(parameterType.getParameterTypeValue());
//				sysParameterType.setStatus(parameterType.getStatus());
//				enterpriseService.updateObject(sysParameterType);
//			}
//		
//		//新增操作类型
//		}else{
//		}
		
		//检验类型编号
		if(sysParameterType != null){
			result = "2";//该编号已存在，请修改
			return result;
		}
		//保存类型
		parameterType.setStatus(SysDictCommon.sysParameterTypeStatus_Save);
		enterpriseService.save(parameterType);
		//参数列表
		List<SysParameter> list = parameter.getParameter();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {

				SysParameter sysParameter = list.get(i);
				
				//如果没有编码
				if(!StringUtils.isNotEmpty(sysParameter.getParameterCode())){
					continue;
				}
				
				//同一参数类型下，参数编号不能重复
				SysParameter parameters = queryParameterByCode(sysParameter.getParameterCode(),parameterType.getParameterTypeId());
				
				if(parameters != null){
					//如果code重复，则更新此参数数据
					if(SysDictCommon.sysParameterAllowUpdate_Y.equals(parameters.getAllowUpdate())){//可修改
						parameters.setAllowUpdate(sysParameter.getAllowUpdate());
						parameters.setCreaterId(parameterType.getCreaterId());
						parameters.setCreateTime(new Date());
						parameters.setOrderId(sysParameter.getOrderId());
						parameters.setParameterCode(sysParameter.getParameterCode());
						parameters.setParameterDesc(sysParameter.getParameterDesc());
						parameters.setParameterName(sysParameter.getParameterName());
						parameters.setParameterTypeId(parameterType.getParameterTypeId());
						parameters.setParameterValue(sysParameter.getParameterValue());
						parameters.setStatus(SysDictCommon.sysParameterStatus_Save);
						enterpriseService.updateObject(parameters);
					}
				}else if(sysParameter != null && StringUtils.isNotEmpty(sysParameter.getParameterCode())  && StringUtils.isNotEmpty(sysParameter.getParameterValue())){
					sysParameter.setCreaterId(parameterType.getCreaterId());
					sysParameter.setCreateTime(new Date());
					sysParameter.setParameterTypeId(parameterType.getParameterTypeId());//类型ID
					sysParameter.setStatus(SysDictCommon.sysParameterStatus_Save);//已存
					enterpriseService.save(sysParameter);
				}
			
			}
		}
		//刷新系统配置缓存
		DefaultLoadService defaultLoadService = (DefaultLoadService) SpringHelper.getBean("defaultLoadService");
		defaultLoadService.loadParam();
	
		return result;
	}

	@Override
	public void delDict(String id) throws Exception {
		QueryBuilder query = new QueryBuilder(SysParameterType.class);
		query.where("parameterTypeId",id,QueryAction.EQUAL);
		enterpriseService.delete(query);
		
		QueryBuilder querySysParameter = new QueryBuilder(SysParameter.class);
		querySysParameter.where("parameterTypeId",id,QueryAction.EQUAL);
		enterpriseService.delete(querySysParameter);
	}

	@Override
	public void importParameterType(List<SysParameterType> list_type, List<SysParameter> list_parameter) throws Exception {
		//删除原有类型
		for (SysParameterType sysParameterType : list_type) {
			delDict(sysParameterType.getParameterTypeId());
			
			String sql = "insert into sys_parameter_type (PARAMETER_TYPE_ID, PARAMETER_TYPE_CODE, PARAMETER_TYPE_VALUE, PARAMETER_TYPE_NAME, " +
					"PARAMETER_TYPE_DESC, CREATER_ID, ALLOW_UPDATE, CREATE_TIME, STATUS) " +
					"values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        Query query = enterpriseService.getSessions().createSQLQuery(sql);
	        query.setParameter(0,sysParameterType.getParameterTypeId());
	        query.setParameter(1,sysParameterType.getParameterTypeCode());
	        query.setParameter(2,sysParameterType.getParameterTypeValue());
	        query.setParameter(3,sysParameterType.getParameterTypeName());
	        query.setParameter(4,sysParameterType.getParameterTypeDesc());
	        query.setParameter(5,sysParameterType.getCreaterId());
	        query.setParameter(6,sysParameterType.getAllowUpdate());
	        query.setParameter(7,sysParameterType.getCreateTime());
	        query.setParameter(8,sysParameterType.getStatus());
	        query.executeUpdate();
		}
		enterpriseService.save(list_parameter);
		
		//刷新系统配置缓存
		DefaultLoadService defaultLoadService = (DefaultLoadService) SpringHelper.getBean("defaultLoadService");
		defaultLoadService.loadParam();
	}

}
