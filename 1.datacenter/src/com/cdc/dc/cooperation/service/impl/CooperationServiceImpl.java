package com.cdc.dc.cooperation.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.sys.entity.SysCount;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.ServiceException;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.common.BusTypes;
import com.cdc.common.properties.DCConfig;
import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.cooperation.model.CooperationDatasourceRecords;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.model.CooperationTypeRoleUser;
import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.dc.metadata.yuan.model.YuanTableColumnManage;
import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.model.InterfaceManagetaskRecords;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.dc.rules.model.RulesFlowInfo;
import com.cdc.dc.rules.model.RulesFlowInsactor;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.inter.client.ws.sms.service.ISmsService;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.CommonUtil;
import com.trustel.common.ItemPage;

public class CooperationServiceImpl implements ICooperationService {
	private IEnterpriseService enterpriseService;
	private IRulesService rulesService;
	private ISmsService smsService;
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
	public void setRulesService(IRulesService rulesService) {
		this.rulesService = rulesService;
	}

	public void setSmsService(ISmsService smsService) {
		this.smsService = smsService;
	}

	@Override
	public void saveEntity(Object item) throws ServiceException {
		enterpriseService.save(item);
	}
	
	@Override
	public void saveEntity(List<?> list) {
		if (list != null && list.size() > 0)
			if (list != null)
				for (int i = 0; i < list.size(); i++) {
					saveEntity(list.get(i));
				}
	}
	@Override
	public void updateEntity(Object item) {
		enterpriseService.updateObject(item);
	}

	@Override
	public void updateAll(List<?> list) {
		enterpriseService.updateAll(list);
	}
	
	@Override
	public void delEntity(Object obj) {
		HibernateTemplate hibernateTemplate = enterpriseService.getHibernateTemplates();
		hibernateTemplate.delete(obj);
	}
	
	@Override
	public List<CooperationDatasourceType> queryCooperationDatasourceTypeList(String busTypes, String parentDatasourceId, String status) {
		QueryBuilder query = new QueryBuilder(CooperationDatasourceType.class);
		query.where("busTypes",busTypes,QueryAction.EQUAL);
		query.where("parentDatasourceId",parentDatasourceId,QueryAction.EQUAL);
		query.where("status",status,QueryAction.EQUAL);
		query.orderBy("createTime");
		return (List<CooperationDatasourceType>) enterpriseService.query(query, 0);
	}
	@Override
	public void addDatasourceType(CooperationDatasourceType datasourceType, SysUser sysUser, String busTypes) {
		CooperationDatasourceType datasourceTypeOld = null;
		CooperationDatasourceType datasourceTypeParent = null;
		if(StringUtils.isNotEmpty(datasourceType.getDatasourceId())){
			datasourceTypeOld = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceType.getDatasourceId());
		}
		if(StringUtils.isNotEmpty(datasourceType.getParentDatasourceId())){
			datasourceTypeParent = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceType.getParentDatasourceId());
		}
		
		//有父节点
		if(datasourceTypeParent != null){
			datasourceType.setDatasourceLevel(""+ (Integer.parseInt(datasourceTypeParent.getDatasourceLevel()) + 1));//层级递增
			datasourceType.setParentDatasourceName(datasourceTypeParent.getDatasourceName());
		}else{
			//父节点
			datasourceType.setDatasourceLevel(CooperationCommon.datasourceRoot_level);//一级配置
			datasourceType.setParentDatasourceId(CooperationCommon.datasourceRoot_parentId);//root
		}
		
		if(datasourceTypeOld != null){
			datasourceType.setDatasourceCode(datasourceTypeOld.getDatasourceCode());
			datasourceType.setCreateTime(datasourceTypeOld.getCreateTime());
			datasourceType.setCreateUserid(datasourceTypeOld.getCreateUserid());
			datasourceType.setCreateUsername(datasourceTypeOld.getCreateUsername());
			datasourceType.setParentDatasourceName(datasourceTypeOld.getParentDatasourceName());
			datasourceType.setUpdateTime(new Date());
			datasourceType.setUpdateUserid(sysUser.getUserId());
			datasourceType.setUpdateUsername(sysUser.getUserName());
			enterpriseService.updateObject(datasourceType);
			//查询子节点并更新
			QueryBuilder query = new QueryBuilder(CooperationDatasourceType.class);
			query.where("parentDatasourceId",datasourceType.getDatasourceId() ,QueryAction.EQUAL);
			List<CooperationDatasourceType> listChild = (List<CooperationDatasourceType>) enterpriseService.query(query, 0);
			if(listChild != null && listChild.size() > 0 ){
				//更新
				for (CooperationDatasourceType childType : listChild) {
					childType.setParentDatasourceName(datasourceType.getDatasourceName());
					enterpriseService.updateObject(childType);
				}
			}
		}else{
//			1.数据源用SRC+‘三位数’ 
			if(BusTypes.busTypes_DS.equals(busTypes)){
				datasourceType.setDatasourceCode("SRC" + getIds("SRC",busTypes));
			}
//			2.业务报表用REP+‘三位数’DS YB
			if(BusTypes.busTypes_YB.equals(busTypes)){
				datasourceType.setDatasourceCode("REP" + getIds("REP",busTypes));
			}
			datasourceType.setCreateTime(new Date());
			datasourceType.setCreateUserid(sysUser.getUserId());
			datasourceType.setCreateUsername(sysUser.getUserName());
			datasourceType.setBusTypes(busTypes);
			enterpriseService.save(datasourceType);
		}
		logger.info("数据源配置提交……");
	}
	@Override
	public void delDatasourceType(SysUser sysUser, String datasourceId) {

		CooperationDatasourceType datasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
		//保存才能删除
		if(datasourceType != null){
			datasourceType.setUpdateTime(new Date());
			datasourceType.setUpdateUserid(sysUser.getUserId());
			datasourceType.setUpdateUsername(sysUser.getUserName());
			datasourceType.setStatus(CooperationCommon.datasourceStatus_Del);//删除状态
			enterpriseService.updateObject(datasourceType);//逻辑删除
		}
		//删除子节点
		List<CooperationDatasourceType> list = queryCooperationDatasourceTypeList(null,datasourceId,null);
		if(list != null){
			//遍历删除
			for (CooperationDatasourceType rt : list) {
				delDatasourceType(sysUser,rt.getDatasourceId());
			}
		}
	
	}

	/**
	 * 根据字符串获取流水号
	 * @param formatdate
	 * @param type
	 * @return
	 */
	public String getIds(String str, String type) {
		QueryBuilder builder = new QueryBuilder(SysCount.class);
		builder.where("type", type);
		builder.where("formatdate", str);
		List<SysCount> list = (List<SysCount>) enterpriseService.query(builder, 0);
		String no = "001";
		if(list != null && list.size() > 0) {
			SysCount sc = list.get(0);
			no = sc.getCount();
			if (StringUtils.isNotEmpty(no)) {
    			int i = Integer.parseInt(no) + 1;
    			no = ("00" + i).substring(("00" + i).length() - 3);
    		}else{
    			no = "001";
    		}
			sc.setCount(no);
			enterpriseService.updateObject(sc);
		}else {
			enterpriseService.save(new SysCount(type,"001",str));
		}
		return no;
	}
	@Override
	public ItemPage queryPrivilgesItemPage(CooperationForm cooperationForm) {
		Class<?>[] pojos = {CooperationTypeRoleUser.class,CooperationDatasourceType.class,SysRole.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.select("max(a),max(b),max(c)");
		query.where(" a.datasourceId=b.datasourceId ");//数据源类型
		query.where(" a.roleId=c.roleId ");//角色权限
		query.where(" b.datasourceId",cooperationForm.getDatasourceId());
		query.where(" b.parentDatasourceId",cooperationForm.getParentDatasourceId());
		query.where(" b.busTypes", cooperationForm.getBusTypes());
		if(StringUtils.isNotEmpty(cooperationForm.getUserid())){
			query.where(" a.userId", cooperationForm.getUserid());
		}else{
			query.where(" a.userId", cooperationForm.getUsername());
		}
		query.where("a.status", CooperationCommon.typeRoleUserStatus_Save, QueryAction.EQUAL );//已存
		query.where("b.status", CooperationCommon.datasourceStatus_Save, QueryAction.EQUAL );//已存
		query.groupby("a.roleId,a.datasourceId");
//		query.orderBy("a.createTime", false);
		
		return enterpriseService.query(query, cooperationForm);
	}
	@Override
	public void configureSubmit(SysUser sysUser, String datasourceId,String[] importUserId, String[] remainUserId, String[] queryUserId, String busTypes, boolean flag) {
		//如果数据源ID为空，不能进行操作
		if(!StringUtils.isNotEmpty(datasourceId)){
			return;
		}
		//是否删除原配置
		if(flag){
			QueryBuilder query = new QueryBuilder(CooperationTypeRoleUser.class);
			query.where("datasourceId",datasourceId,QueryAction.EQUAL);
			query.orderBy("createTime");
			
			//删除原有角色，用户关系
			List<CooperationTypeRoleUser> delList = (List<CooperationTypeRoleUser>) enterpriseService.query(query, 0);
			// TODO: 删除原有角色，用户关系
			if(delList != null && delList.size() > 0){
				for (CooperationTypeRoleUser cooperationTypeRoleUser : delList) {
					delRoleUser(cooperationTypeRoleUser.getRoleId(),cooperationTypeRoleUser.getUserId());
				}
			}
			//删除原配置
			enterpriseService.delete(query);
		}
		
		List<CooperationTypeRoleUser> addList = new ArrayList<CooperationTypeRoleUser>();
		if(BusTypes.busTypes_DS.equals(busTypes)){
			//数据录入员
			addList.addAll(getAddList(sysUser,datasourceId,importUserId,CooperationCommon.DS_IMPORTER));
			//数据审核员
			addList.addAll(getAddList(sysUser,datasourceId,remainUserId,CooperationCommon.DS_REMAINER));
			//数据查询员
			addList.addAll(getAddList(sysUser,datasourceId,queryUserId,CooperationCommon.DS_QUERYER));
		}else if(BusTypes.busTypes_YB.equals(busTypes)){
			//报表查询员
			addList.addAll(getAddList(sysUser,datasourceId,queryUserId,CooperationCommon.YB_QUERYER));
		}
		enterpriseService.save(addList);
	}
	
	private List<CooperationTypeRoleUser> getAddList(SysUser sysUser, String datasourceId,String[] userIds,String dicCode){
		//结果
		List<CooperationTypeRoleUser> result = new ArrayList<CooperationTypeRoleUser>();
		SysRole sysRole = querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, dicCode).getParameterValue());
		String roleId = sysRole.getRoleId();
		if(userIds ==null){
			return result;
		}
		for (String userId : userIds) {
			if(StringUtils.isEmpty(userId)){
				continue;
			}
			CooperationTypeRoleUser ctru = new CooperationTypeRoleUser();
			ctru.setDatasourceId(datasourceId);
			ctru.setRoleId(roleId);
			ctru.setUserId(userId);
			ctru.setStatus(CooperationCommon.typeRoleUserStatus_Save);
			ctru.setCreateTime(new Date());
			ctru.setCreateUserid(sysUser.getUserId());
			ctru.setCreateUsername(sysUser.getUserName());
			
			// TODO: 保存新的角色、用户关系
			//查询该用户原有权限配置
			QueryBuilder query = new QueryBuilder(SysRoleUser.class);
			query.where("roleId",roleId,QueryAction.EQUAL);
			query.where("userId",userId,QueryAction.EQUAL);
			List<SysRoleUser> list = (List<SysRoleUser>)enterpriseService.query(query, 0);
			
			if(list == null || list.size()==0 ){
				//保存新的权限配置
				SysRoleUser model= new SysRoleUser();
				model.setRoleId(roleId);
				model.setUserId(userId);
				enterpriseService.save(model);
			}
			QueryBuilder query1 = new QueryBuilder(CooperationTypeRoleUser.class);
			query1.where("datasourceId",datasourceId,QueryAction.EQUAL);
			query1.where("roleId",roleId,QueryAction.EQUAL);
			query1.where("userId",userId,QueryAction.EQUAL);
			query1.where("status",CooperationCommon.typeRoleUserStatus_Save,QueryAction.EQUAL);
			List<CooperationTypeRoleUser> list1 = (List<CooperationTypeRoleUser>)enterpriseService.query(query1, 0);
			if(list1 == null || list1.size()==0 ){
				result.add(ctru);
			}
		}
		return result;
	}
	/**
	 * 根据角色ID、用户ID删除角色用户关系配置
	 * @param roleId
	 * @param userId
	 */
	private void delRoleUser(String roleId, String userId) {
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("userId", userId);
		query.where("roleId", roleId);
		enterpriseService.delete(query);
		logger.info("删除角色用户关联关系");
	}
	/**
	 * 根据角色编号查询角色信息
	 * @param roleCode
	 * @return
	 */
	@Override
	public SysRole querySysRoleByCode(String roleCode) {
		QueryBuilder query = new QueryBuilder(SysRole.class);
		query.where("roleCode",roleCode,QueryAction.EQUAL);
		query.orderBy("createTime desc");
		List<SysRole> list = (List<SysRole>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return new SysRole();
	}
	/**
	 * 根据数据源ID查询类型角色用户配置记录
	 * @param datasourceId
	 * @return
	 */
	private List<CooperationTypeRoleUser> queryCooperationTypeRoleUserListById(String datasourceId) {
		QueryBuilder query = new QueryBuilder(CooperationTypeRoleUser.class);
		query.where("datasourceId",datasourceId,QueryAction.EQUAL);
		query.orderBy("createTime");
		return (List<CooperationTypeRoleUser>) enterpriseService.query(query, 0);
	}
	@Override
	public List<SysUser> fingSysUserListById(String datasourceId, String roleId) {
		List<SysUser> result = new ArrayList<SysUser>();
		QueryBuilder query = new QueryBuilder(CooperationTypeRoleUser.class);
		query.where("datasourceId",datasourceId,QueryAction.EQUAL);
		query.where("roleId",roleId,QueryAction.EQUAL);
		List<CooperationTypeRoleUser> list = (List<CooperationTypeRoleUser>) enterpriseService.query(query, 0);
		if(list != null){
			for (CooperationTypeRoleUser cooperationTypeRoleUser : list) {
				SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, cooperationTypeRoleUser.getUserId());
				if(sysuser != null){
					result.add(sysuser);
				}
			}
		}
		return result;
	}
	@Override
	public List<CooperationTypeRoleUser> queryCooperationTypeRoleUserList(String roleId, String userId) {
		QueryBuilder query = new QueryBuilder(CooperationTypeRoleUser.class);
		query.where("roleId",roleId,QueryAction.EQUAL);
		query.where("userId",userId,QueryAction.EQUAL);
		return (List<CooperationTypeRoleUser>) enterpriseService.query(query, 0);
	}
	@Override
	public List<CooperationDatasourceType> queryCooperationDatasourceByIds(String datasourceIds, String status) {
		QueryBuilder query=new QueryBuilder(CooperationDatasourceType.class);
		query.where("datasourceId in ("+datasourceIds+")");
		query.where("status",status,QueryAction.EQUAL);//已存
		return (List<CooperationDatasourceType>) enterpriseService.query(query, 0);
	
	}
	@Override
	public RulesFileUpload queryRulesFileUploadByInfoId(String fileTempId) {
		QueryBuilder query = new QueryBuilder(RulesFileUpload.class);
		query.where("rulesInfoid",fileTempId,QueryAction.EQUAL);
		query.orderBy("createTime desc");
		List<RulesFileUpload> list = (List<RulesFileUpload>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ItemPage queryReportsItemPage(CooperationDatasourceRecords datasourceRecords, CooperationDatasourceType cdt, CooperationForm cooperationForm) {
		if(datasourceRecords == null)return null;
		if(cdt == null)return null;
		String sql = "select * from "+cdt.getInterfaceTable()+" where datasource_records_id='"+datasourceRecords.getRecordId()+"' order by create_time desc";
		return enterpriseService.findBySql(sql, cooperationForm);
	}

	@Override
	public List queryReportTableVoByTableName(CooperationDatasourceType cdt) {
		String sql = " SELECT TABLE_NAME as tableName,COLUMN_NAME as columnName,COMMENTS as comments FROM USER_COL_COMMENTS WHERE TABLE_NAME = '"+cdt.getInterfaceTable()+"' and COLUMN_NAME !='DATASOURCE_RECORDS_ID' and COLUMN_NAME !='NORMAL_ID' and COLUMN_NAME !='CREATE_TIME' order by COLUMN_NAME";
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
		List<Object[]> list = query.list();
		List<Object[]> result = new ArrayList<Object[]>();
		if(list != null){
			for (Object[] object : list) {
				Object[] obj = new Object[3];
				String tableName = (String) object[0];
				String columnName = (String) object[1];
				String comments = (String) object[2];
				obj[0] = tableName;
				obj[1] = CommonUtil.replaceUnderlineAndfirstToUpper(columnName,"_","");
				obj[2] = comments;
				result.add(obj);
			}
		}
		return result;
	}

	@Override
	public void submit(CooperationDatasourceRecords datasourceRecords,SysUser sysUser) {
		logger.info("数据录入/导入提交或暂存……");
		datasourceRecords.setCreateTime(new Date());
		datasourceRecords.setCreateUserid(sysUser.getUserId());
		datasourceRecords.setCreateUsername(sysUser.getUserName());
		//更新
		updateEntity(datasourceRecords);
		//提交才保存流程信息及待办信息
		if(CooperationCommon.datasourceRecordsStatus_SH.equals(datasourceRecords.getStatus())){
			//未处理的已退回流程
			RulesFlowInfo rulesFlowInfo = rulesService.queryRulesFlowInfoStatus(datasourceRecords.getRecordId(),RulesCommon.rulesFlowInfoFlowLink_TH,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
			//流程信息及待办信息保存
			saveFlowInfoAndFlowInsactor(datasourceRecords,sysUser,rulesFlowInfo,
					RulesCommon.rulesFlowInfoFlowLink_CJ,"数据提交,创建",RulesCommon.rulesFlowInfoHandelResult_TJ,
					null,RulesCommon.rulesFlowInsactorsType_DB,true);
			
			//提交审核发送待办
			try {
				CooperationDatasourceType datasourceType = (CooperationDatasourceType) enterpriseService.getById(CooperationDatasourceType.class, datasourceRecords.getDatasourceId());
				//审核员权限
				SysRole sysRole = querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_REMAINER).getParameterValue());
				//查询改数据源配置的审核人员
				List<SysUser> list = getUserByDatasourceIdAndRoleId(datasourceType.getDatasourceId(),sysRole.getRoleId());
				if(list != null && list.size() > 0 && "Y".equals(DCConfig.getProperty("FLOW"))){
					rulesService.sendManagetask(RulesCommon.ACTION_ADD,sysUser,datasourceType.getParentDatasourceName()+"/"+datasourceType.getDatasourceName()+"，请审核",null,RulesCommon.TASK_TYPE_DB,SysParamHelper.getSysParamByCode(RulesCommon.OA_URL, RulesCommon.COOPERATION_REMAIN_URL).getParameterValue(),
							list,null,datasourceRecords.getRecordId(),RulesCommon.rulesFlowInsactorsHandelStatus_NONE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<SysUser> getUserByDatasourceIdAndRoleId(String datasourceId,String roleId) {
		// TODO Auto-generated method stub
		QueryBuilder query = new QueryBuilder(CooperationTypeRoleUser.class);
		query.where("datasourceId",datasourceId,QueryAction.EQUAL);
		query.where("roleId",roleId,QueryAction.EQUAL);
		List<CooperationTypeRoleUser> list = (List<CooperationTypeRoleUser>) enterpriseService.query(query, 0);
		
		String userIds = "";
		if(list != null && list.size() > 0 ){
			for (int i = 0; i < list.size(); i++) {
				if( i == 0 ){
					userIds += "'" + list.get(i).getUserId() + "'";
				}else{
					userIds += ",'" + list.get(i).getUserId() + "'";
				}
			}
		}
		QueryBuilder querys = new QueryBuilder(SysUser.class);
		querys.where(" userId in ("+ userIds +")");
		
		return (List<SysUser>) enterpriseService.query(querys, 0);
	}

	/**
	 * 
	 * @param datasourceRecords	录入/导入记录详情
	 * @param sysUser	登录用户
	 * @param rulesFlowInfo	上一流程信息
	 * @param flowInfoFlowLink	本次新增的流程环节
	 * @param desc	处理意见/描述
	 * @parem flowInfoHandelResult 处理结果
	 * @param rulesFlowInsactor	历史待办功能
	 * @param flowInsactorsType	待办/待阅/已发布超时记录信息
	 * @param flag	是否新建待办/待阅信息
	 */
	private void saveFlowInfoAndFlowInsactor(CooperationDatasourceRecords datasourceRecords,SysUser sysUser,
			RulesFlowInfo rulesFlowInfo,String flowInfoFlowLink,String desc,String flowInfoHandelResult,
			RulesFlowInsactor rulesFlowInsactor,String flowInsactorsType,boolean flag){
		//新增流程
		RulesFlowInfo rulesFlowInfoNew = new RulesFlowInfo();
		rulesFlowInfoNew.setFlowLink(flowInfoFlowLink);//制度已发布
		rulesFlowInfoNew.setRulesInfoid(datasourceRecords.getRecordId());
		rulesFlowInfoNew.setCreateTime(new Date());//创建时间
		rulesFlowInfoNew.setHandelUserid(sysUser.getUserId());//处理人ID
		rulesFlowInfoNew.setHandelUsername(sysUser.getUserName());//处理人
		rulesFlowInfoNew.setHandelOpinion(desc);//处理意见/描述
		rulesFlowInfoNew.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_NONE);//下一流程处理状态,标识最新.有且仅有一条记录为NONE
		if(rulesFlowInfo != null){
			rulesFlowInfoNew.setFromFlowId(rulesFlowInfo.getFlowId());//上一流程
		}
		saveEntity(rulesFlowInfoNew);
		
		if(rulesFlowInfo != null){
			//更新流程
			rulesFlowInfo.setHandelTime(new Date());
			rulesFlowInfo.setHandelResult(flowInfoHandelResult);//发布
			rulesFlowInfo.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_DONE);//已处理
			rulesFlowInfo.setToFlowId(rulesFlowInfoNew.getFlowId());//下一流程
			updateEntity(rulesFlowInfo);
		}
	
		if(rulesFlowInsactor != null){
			//更新待办信息
			rulesFlowInsactor.setHandelTime(new Date());
			rulesFlowInsactor.setHandelUserid(sysUser.getUserId());
			rulesFlowInsactor.setHandelUsername(sysUser.getUserName());
			rulesFlowInsactor.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_DONE);
			updateEntity(rulesFlowInsactor);
		}
		if(flag){
			//新增待办
			RulesFlowInsactor rulesFlowInsactorNew = new RulesFlowInsactor();
			rulesFlowInsactorNew.setInsacatorType(flowInsactorsType);
			rulesFlowInsactorNew.setFlowId(rulesFlowInfoNew.getFlowId());//流程信息ID
			rulesFlowInsactorNew.setRulesInfoid(datasourceRecords.getRecordId());//录入/导入信息编号
			rulesFlowInsactorNew.setCreateTime(new Date());//产生时间
			rulesFlowInsactorNew.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_NONE);//未完成
			rulesFlowInsactorNew.setTimeoutDays(0L);//无时限
			rulesFlowInsactorNew.setTimeoutStatus(RulesCommon.rulesFlowInsactorsTimeoutStatuss_N);//超时状态,未超时
			saveEntity(rulesFlowInsactorNew);
		}
	}

	@Override
	public ItemPage queryMydatasourceItemPage(CooperationForm cooperationForm,String datasourceTypeList) {
		Class<?>[] pojos = {CooperationDatasourceRecords.class,CooperationDatasourceType.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.datasourceId=b.datasourceId ");
		query.where(" a.status in " + cooperationForm.getStatus());
		query.where(" a.month",cooperationForm.getMonth());
		query.where(" b.datasourceId",cooperationForm.getDatasourceId());
		query.where(" b.parentDatasourceId",cooperationForm.getParentDatasourceId());
		query.where(" b.datasourceId in ("+datasourceTypeList+")");
		query.where(" a.status",CooperationCommon.datasourceRecordsStatus_DEL,QueryAction.NOEQUAL);
		query.orderBy(" a.createTime ", false);
		return enterpriseService.query(query, cooperationForm);
	}
	@Override
	public ItemPage queryMydatasourceItemPage(CooperationForm cooperationForm, String type, SysUser user,Map map) {
		Class<?>[] pojos = {CooperationDatasourceRecords.class,CooperationDatasourceType.class,CooperationTypeRoleUser.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.select(" distinct a,b ");
		query.where(" a.datasourceId=b.datasourceId ");
		query.where("b.datasourceId=c.datasourceId");
		query.where(" a.month",cooperationForm.getMonth());
		query.where(" b.datasourceId",cooperationForm.getDatasourceId());
		query.where(" b.parentDatasourceId",cooperationForm.getParentDatasourceId());
		
		//我的数据列表
		if(type.equals("0")){
			query.where(" a.status in ('1','2','3','5') ");
		}
		
		//已审核
		if(type.equals(CooperationCommon.datasourceRecordsStatus_TG)){

			query.where(" a.status",CooperationCommon.datasourceRecordsStatus_SH,QueryAction.NOEQUAL);
			query.where(" a.status",CooperationCommon.datasourceRecordsStatus_CG,QueryAction.NOEQUAL);
			query.where(" a.status",CooperationCommon.datasourceRecordsStatus_DEL,QueryAction.NOEQUAL);
		
			String sql = "( ";
			sql += " c.roleId='"+ map.get("importer").toString()+"'";
			sql += " or ";
			sql += " c.roleId='" + map.get("remainer").toString()+"'";
			sql += " or ";
			sql += " c.roleId='" + map.get("queryer").toString()+"'";
			sql += " )";
			query.where(sql);
			
		}
		
		//审核中
		if(type.equals(CooperationCommon.datasourceRecordsStatus_SH)){
			query.where(" a.status",CooperationCommon.datasourceRecordsStatus_SH);
			String sql = " ( ";
			sql += " c.roleId='"+ map.get("importer").toString()+"'";
			sql += " or ";
			sql += " c.roleId='" + map.get("remainer").toString()+"'";
			sql += " ) ";
			query.where(sql);
		}
		
		//草稿
		if(type.equals(CooperationCommon.datasourceRecordsStatus_CG)){
			query.where(" a.status",CooperationCommon.datasourceRecordsStatus_CG);
			query.where("c.roleId",map.get("importer").toString(),QueryAction.IN);
		}
		
		//已修订
		if(type.equals(CooperationCommon.datasourceRecordsStatus_XD)){
			query.where(" a.status",CooperationCommon.datasourceRecordsStatus_XD);
			String sql = " ( ";
			sql += " c.roleId='"+ map.get("importer").toString()+"'";
			sql += " or ";
			sql += " c.roleId='" + map.get("remainer").toString()+"'";
			sql += " ) ";
			query.where(sql);
		}
		query.where("c.userId",user.getUserId());
		query.orderBy(" a.createTime ", false);
		return enterpriseService.query(query, cooperationForm);
	}

	@Override
	public ItemPage queryRemainItemPage(CooperationForm cooperationForm) {
		Class<?>[] pojos = {CooperationDatasourceRecords.class,CooperationDatasourceType.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.datasourceId=b.datasourceId ");
		query.where(" a.month",cooperationForm.getBeginMonth(),QueryAction.GE);
		query.where(" a.month",cooperationForm.getEndMonth(),QueryAction.LE);
		query.where(" b.datasourceId in ("+cooperationForm.getIds()+")");
		query.where(" b.datasourceId",cooperationForm.getDatasourceId());
		query.where(" b.parentDatasourceId",cooperationForm.getParentDatasourceId());
		if(CooperationCommon.datasourceRecordsStatus_SH.equals(cooperationForm.getStatus())){
			query.where(" a.status",cooperationForm.getStatus());
			query.orderBy(" a.createTime ", false);
		}else{
			query.where(" a.status",CooperationCommon.datasourceRecordsStatus_SH,QueryAction.NOEQUAL);
			query.where(" a.status",CooperationCommon.datasourceRecordsStatus_CG,QueryAction.NOEQUAL);
			query.where(" a.status",CooperationCommon.datasourceRecordsStatus_DEL,QueryAction.NOEQUAL);
			query.orderBy(" a.checkTime ", false);
		}
		return enterpriseService.query(query, cooperationForm);
	}

	/**
	 * 数据审核通过
	 */
	@Override
	public void pastRemain(CooperationDatasourceRecords datasourceRecords, SysUser sysUser) {
		if(datasourceRecords == null || sysUser == null){
			return;
		}
		
		//查询已经审核通过的记录,并记录已修订
		List<CooperationDatasourceRecords> list = queryCooperationDatasourceRecordsList(datasourceRecords.getDatasourceId(),datasourceRecords.getMonth(),CooperationCommon.datasourceRecordsStatus_TG);
		//修订，同一月份下同一数据只有一份审核通过。其余为已修订
		if(list != null){
			for (CooperationDatasourceRecords cooperationDatasourceRecords : list) {
				cooperationDatasourceRecords.setStatus(CooperationCommon.datasourceRecordsStatus_XD);
				reviseRules(cooperationDatasourceRecords,sysUser);
			}
		}
		
		datasourceRecords.setCheckTime(new Date());
		datasourceRecords.setCheckUserid(sysUser.getUserId());
		datasourceRecords.setCheckUsername(sysUser.getUserName());
		datasourceRecords.setCheckOpinion("审核通过");
		datasourceRecords.setUpdateTime(new Date());
		datasourceRecords.setUpdateUserid(sysUser.getUserId());
		datasourceRecords.setUpdateUsername(sysUser.getUserName());
		//更新
		updateEntity(datasourceRecords);
		
		//查询最新的未处理流程
		RulesFlowInfo rulesFlowInfo = rulesService.queryRulesFlowInfoStatus(datasourceRecords.getRecordId(),null,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
		//查询最新的未处理待办
		RulesFlowInsactor rulesFlowInsactor = rulesService.queryRulesFlowInsactorStatus(datasourceRecords.getRecordId(),RulesCommon.rulesFlowInsactorsType_DB,RulesCommon.rulesFlowInsactorsHandelStatus_NONE);
		
		if(rulesFlowInfo == null || rulesFlowInsactor == null){
			return;
		}
		
		saveFlowInfoAndFlowInsactor(datasourceRecords,sysUser,rulesFlowInfo,
				RulesCommon.rulesFlowInfoFlowLink_FB,"数据审核通过",RulesCommon.rulesFlowInfoFlowLink_FB,
				rulesFlowInsactor,RulesCommon.rulesFlowInsactorsType_FB,true);
		sendOaDone(datasourceRecords,sysUser);
	}
	
	
	private List<CooperationDatasourceRecords> queryCooperationDatasourceRecordsList(String datasourceId,String month,String status){
		QueryBuilder query = new QueryBuilder(CooperationDatasourceRecords.class);
		query.where("datasourceId",datasourceId,QueryAction.EQUAL);
		query.where("month",month,QueryAction.EQUAL);
		query.where("status",status,QueryAction.EQUAL);
		return (List<CooperationDatasourceRecords>) enterpriseService.query(query, 0);
	}
	
	/**
	 * 数据修订
	 */
	public void reviseRules(CooperationDatasourceRecords datasourceRecords, SysUser sysUser) {
		if(datasourceRecords == null || sysUser == null){
			return;
		}
		datasourceRecords.setCheckTime(new Date());
		datasourceRecords.setCheckUserid(sysUser.getUserId());
		datasourceRecords.setCheckUsername(sysUser.getUserName());
		datasourceRecords.setCheckOpinion("数据已修订");
		datasourceRecords.setUpdateTime(new Date());
		datasourceRecords.setUpdateUserid(sysUser.getUserId());
		datasourceRecords.setUpdateUsername(sysUser.getUserName());
		//更新
		updateEntity(datasourceRecords);
		
		//查询最新的未处理流程
		RulesFlowInfo rulesFlowInfo = rulesService.queryRulesFlowInfoStatus(datasourceRecords.getRecordId(),null,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
		if(rulesFlowInfo == null){
			return;
		}
		//记录已修订流程，并产生待阅
		saveFlowInfoAndFlowInsactor(datasourceRecords,sysUser,rulesFlowInfo,
				RulesCommon.rulesFlowInfoFlowLink_XD,"数据已修订",RulesCommon.rulesFlowInfoHandelResult_XD,
				null,RulesCommon.rulesFlowInsactorsType_DY,true);
	}

	@Override
	public void returnRemain(CooperationDatasourceRecords datasourceRecords,SysUser sysUser, String handelOpinion, String checkboxSMS) {
		if(datasourceRecords == null || sysUser == null){
			return;
		}
		datasourceRecords.setCheckTime(new Date());
		datasourceRecords.setCheckUserid(sysUser.getUserId());
		datasourceRecords.setCheckUsername(sysUser.getUserName());
		datasourceRecords.setCheckOpinion(handelOpinion);
		datasourceRecords.setUpdateTime(new Date());
		datasourceRecords.setUpdateUserid(sysUser.getUserId());
		datasourceRecords.setUpdateUsername(sysUser.getUserName());
		//更新
		updateEntity(datasourceRecords);
		//查询最新的未处理流程
		RulesFlowInfo rulesFlowInfo = rulesService.queryRulesFlowInfoStatus(datasourceRecords.getRecordId(),null,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
		//查询最新的未处理待办
		RulesFlowInsactor rulesFlowInsactor = rulesService.queryRulesFlowInsactorStatus(datasourceRecords.getRecordId(),RulesCommon.rulesFlowInsactorsType_DB,RulesCommon.rulesFlowInsactorsHandelStatus_NONE);
				
		if(rulesFlowInfo == null || rulesFlowInsactor == null){
			return;
		}
		//退回流程保存，无待办待阅
		saveFlowInfoAndFlowInsactor(datasourceRecords,sysUser,rulesFlowInfo,
				RulesCommon.rulesFlowInfoFlowLink_TH,handelOpinion,RulesCommon.rulesFlowInfoFlowLink_TH,
				null,null,false);

		//已办处理::
		sendOaDone(datasourceRecords,sysUser);
		
		
		//待办处理人
		List<SysUser> list = new ArrayList<SysUser>();
		SysUser sys = (SysUser) enterpriseService.getById(SysUser.class, datasourceRecords.getCreateUserid());
		list.add(sys);
		CooperationDatasourceType datasourceType = (CooperationDatasourceType) enterpriseService.getById(CooperationDatasourceType.class, datasourceRecords.getDatasourceId());
		
		String msg = datasourceType.getParentDatasourceName()+"/"+datasourceType.getDatasourceName()+"，数据审核不通过，请删除，并重新录入/导入。";
		//发送短信
		if("1".equals(checkboxSMS)){
			smsService.sendMessage(sys.getMobile(), msg);
		}
		if(list != null && list.size() > 0 && "Y".equals(DCConfig.getProperty("FLOW"))){
			rulesService.sendManagetask(RulesCommon.ACTION_ADD,sysUser,msg,null,RulesCommon.TASK_TYPE_DB,SysParamHelper.getSysParamByCode(RulesCommon.OA_URL, RulesCommon.COOPERATION_MYALL_URL).getParameterValue(),
					list,null,datasourceRecords.getRecordId(),RulesCommon.rulesFlowInsactorsHandelStatus_NONE);
			
		}
	}

	private void sendOaDone(CooperationDatasourceRecords datasourceRecords,SysUser sysUser) {
		QueryBuilder query = new QueryBuilder(InterfaceManagetaskRecords.class);
		query.where("bustypeId",datasourceRecords.getRecordId(),QueryAction.EQUAL);
		query.where("handelStatus",RulesCommon.rulesFlowInsactorsHandelStatus_NONE,QueryAction.EQUAL);
		List<InterfaceManagetaskRecords> saveList = (List<InterfaceManagetaskRecords>) enterpriseService.query(query, 0);
		//任务ID
		List<String> doneTsskList = new ArrayList<String>();
		//任务所有者
		List<SysUser> doneUserlist = new ArrayList<SysUser>();
		for (InterfaceManagetaskRecords records : saveList) {
			doneTsskList.add(records.getTaskIdRetrun());
			SysUser toUserS = new SysUser();
			toUserS.setEmployee(records.getToEmployeeId());
			toUserS.setAccount(records.getToEmployeeId());//账号id
			doneUserlist.add(toUserS);
			
			records.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_DONE);//已处理完成
		}
		if("Y".equals(DCConfig.getProperty("FLOW"))){
			rulesService.sendManagetask(RulesCommon.ACTION_DONE,sysUser,null,doneTsskList,RulesCommon.TASK_TYPE_DB,null,
					doneUserlist,null,datasourceRecords.getRecordId(),RulesCommon.rulesFlowInsactorsHandelStatus_DONE);
			enterpriseService.updateAll(saveList);
		}
	}

	@Override
	public void delMyData(String id) {
		QueryBuilder query = new QueryBuilder(CooperationDatasourceRecords.class);
		query.where("recordId",id,QueryAction.EQUAL);
		enterpriseService.delete(query);
	}

	@Override
	public ItemPage queryMyDataSH(CooperationForm cooperationForm,  SysUser user,Map map) {
		Class<?>[] pojos = {CooperationDatasourceRecords.class,CooperationDatasourceType.class,CooperationTypeRoleUser.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.select(" distinct a,b ");
		query.where(" a.datasourceId=b.datasourceId ");
		query.where("b.datasourceId=c.datasourceId");
		query.where(" a.month",cooperationForm.getMonth());
		query.where(" b.datasourceId",cooperationForm.getDatasourceId());
		query.where(" b.parentDatasourceId",cooperationForm.getParentDatasourceId());
		

		query.where(" a.status",CooperationCommon.datasourceRecordsStatus_SH,QueryAction.NOEQUAL);
		query.where(" a.status",CooperationCommon.datasourceRecordsStatus_CG,QueryAction.NOEQUAL);
		query.where(" a.status",CooperationCommon.datasourceRecordsStatus_DEL,QueryAction.NOEQUAL);
	
		String sql = "( ";
		sql += " c.roleId='"+ map.get("importer").toString()+"'";
		sql += " or ";
		sql += " c.roleId='" + map.get("remainer").toString()+"'";
		sql += " or ";
		sql += " c.roleId='" + map.get("queryer").toString()+"'";
		sql += " )";
		query.where(sql);
		
		query.where("c.userId",user.getUserId());
		query.orderBy(" a.createTime ", false);
		return enterpriseService.query(query, cooperationForm);
	}

	@Override
	public ItemPage queryMyDataSHZ(CooperationForm cooperationForm,
			SysUser user, Map map) {
		Class<?>[] pojos = {CooperationDatasourceRecords.class,CooperationDatasourceType.class,CooperationTypeRoleUser.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.select(" distinct a,b ");
		query.where(" a.datasourceId=b.datasourceId ");
		query.where("b.datasourceId=c.datasourceId");
		query.where(" a.month",cooperationForm.getMonth());
		query.where(" b.datasourceId",cooperationForm.getDatasourceId());
		query.where(" b.parentDatasourceId",cooperationForm.getParentDatasourceId());
		query.where(" a.status",CooperationCommon.datasourceRecordsStatus_SH);
		String sql = " ( ";
		sql += " c.roleId='"+ map.get("importer").toString()+"'";
		sql += " or ";
		sql += " c.roleId='" + map.get("remainer").toString()+"'";
		sql += " ) ";
		query.where(sql);
		
		query.where("c.userId",user.getUserId());
		query.orderBy(" a.createTime ", false);
		return enterpriseService.query(query, cooperationForm);
	}

	@Override
	public ItemPage queryMyDataCG(CooperationForm cooperationForm,
			SysUser user, Map map) {
		Class<?>[] pojos = {CooperationDatasourceRecords.class,CooperationDatasourceType.class,CooperationTypeRoleUser.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.select(" distinct a,b ");
		query.where(" a.datasourceId=b.datasourceId ");
		query.where("b.datasourceId=c.datasourceId");
		query.where(" a.month",cooperationForm.getMonth());
		query.where(" b.datasourceId",cooperationForm.getDatasourceId());
		query.where(" b.parentDatasourceId",cooperationForm.getParentDatasourceId());
		query.where(" a.status",CooperationCommon.datasourceRecordsStatus_CG);
		query.where("c.roleId",map.get("importer").toString(),QueryAction.IN);
		query.where("c.userId",user.getUserId());
		query.orderBy(" a.createTime ", false);
		return enterpriseService.query(query, cooperationForm);
	}

	@Override
	public ItemPage queryMyDataXD(CooperationForm cooperationForm, 
			SysUser user, Map map) {
		Class<?>[] pojos = {CooperationDatasourceRecords.class,CooperationDatasourceType.class,CooperationTypeRoleUser.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.select(" distinct a,b ");
		query.where(" a.datasourceId=b.datasourceId ");
		query.where("b.datasourceId=c.datasourceId");
		query.where(" a.month",cooperationForm.getMonth());
		query.where(" b.datasourceId",cooperationForm.getDatasourceId());
		query.where(" b.parentDatasourceId",cooperationForm.getParentDatasourceId());
		query.where(" a.status",CooperationCommon.datasourceRecordsStatus_XD);
		String sql = " ( ";
		sql += " c.roleId='"+ map.get("importer").toString()+"'";
		sql += " or ";
		sql += " c.roleId='" + map.get("remainer").toString()+"'";
		sql += " ) ";
		query.where(sql);
		query.where("c.userId",user.getUserId());
		query.orderBy(" a.createTime ", false);
		return enterpriseService.query(query, cooperationForm);
	}

	@Override
	public ItemPage queryReportQueryItemPage(CooperationForm cooperationForm,CooperationDatasourceType datasourceType) {
		if(!StringUtils.isNotEmpty(cooperationForm.getMonth())){
			Calendar c = Calendar.getInstance();
	        c.add(Calendar.MONTH, -1);
	        //月报表
	        if(CooperationCommon.datasourceSource_2nd.equals(datasourceType.getDatasourceSource())){
	        	cooperationForm.setMonth(new SimpleDateFormat("yyyy-MM").format(c.getTime()));
	        }else{
	        	cooperationForm.setMonth(new SimpleDateFormat("yyyy").format(c.getTime()));
	        }
		}
		String sql = "";
		if(StringUtils.isNotEmpty(datasourceType.getInterfaceTable())){
			sql = "select * from "+datasourceType.getInterfaceTable()+" t where 1=1 ";
			sql += " and t.month_str like '%" + cooperationForm.getMonth() + "%' ";
			return enterpriseService.findBySql(sql, cooperationForm);
		}else{
			return null;
		}
//		sql += " and t.product_name != '其他'";
	}

	@Override
	public ItemPage queryMydatasourceItemPage(CooperationForm cooperationForm,SysUser sysUser,SysRole sysRoleqImporter,SysRole	sysRoleQuery,SysRole sysRoleRemainer) {

		Class<?>[] pojos = {CooperationDatasourceRecords.class,CooperationDatasourceType.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.datasourceId=b.datasourceId ");
		query.where(" a.month",cooperationForm.getMonth());
		query.where(" b.datasourceId",cooperationForm.getDatasourceId());
		query.where(" b.parentDatasourceId",cooperationForm.getParentDatasourceId());
//		query.where(" b.datasourceId in ("+datasourceTypeList+")");
		String sql = " ( ";
		//已审核
		List<CooperationDatasourceType> datasourceTypeList = new ArrayList<CooperationDatasourceType>();
		List<CooperationTypeRoleUser> list = null;
		cooperationForm.setStatus("('"+CooperationCommon.datasourceRecordsStatus_TG+"'" +
				",'"+CooperationCommon.datasourceRecordsStatus_FZ+"'" +
				",'"+CooperationCommon.datasourceRecordsStatus_XD+"'" +
				",'"+CooperationCommon.datasourceRecordsStatus_WTG+"'" +
				",'"+CooperationCommon.datasourceRecordsStatus_TB+"'" +
						",'"+CooperationCommon.datasourceRecordsStatus_WTB+"')");
		list = queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
		list.addAll(queryCooperationTypeRoleUserList(sysRoleQuery.getRoleId(),sysUser.getUserId()));
		list.addAll(queryCooperationTypeRoleUserList(sysRoleRemainer.getRoleId(),sysUser.getUserId()));
		//二级
		datasourceTypeList = queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		sql += "( a.status in " + cooperationForm.getStatus() + " and  b.datasourceId in (" +CooperationCommon.getIds(datasourceTypeList )+") )";
		sql += " or ";
		//审核中,录入员、审核员
		cooperationForm.setStatus("('"+CooperationCommon.datasourceRecordsStatus_SH+"')");
		list.clear();
		list = queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
		list.addAll(queryCooperationTypeRoleUserList(sysRoleRemainer.getRoleId(),sysUser.getUserId()));
		//二级
		datasourceTypeList = queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		sql += "( a.status in " + cooperationForm.getStatus() + " and  b.datasourceId in (" +CooperationCommon.getIds(datasourceTypeList )+") )";
		sql += " or ";
		//草稿箱,录入员
		cooperationForm.setStatus("('"+CooperationCommon.datasourceRecordsStatus_CG+"')");
		list.clear();
		list = queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
		//二级
		datasourceTypeList = queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		sql += "( a.status in " + cooperationForm.getStatus() + " and  b.datasourceId in (" +CooperationCommon.getIds(datasourceTypeList )+") )";
		sql += " or ";
		//已修订,录入员、审核员
		cooperationForm.setStatus("('"+CooperationCommon.datasourceRecordsStatus_XD+"')");
		list.clear();
		list = queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
		list.addAll(queryCooperationTypeRoleUserList(sysRoleRemainer.getRoleId(),sysUser.getUserId()));
		//二级
		datasourceTypeList = queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		sql += "( a.status in " + cooperationForm.getStatus() + " and  b.datasourceId in (" +CooperationCommon.getIds(datasourceTypeList )+") )";
		sql += " ) ";
		query.where(sql);
		query.where(" a.status",CooperationCommon.datasourceRecordsStatus_DEL,QueryAction.NOEQUAL);
		query.orderBy(" a.createTime ", false);
		return enterpriseService.query(query, cooperationForm);
	
	}

	@Override
	public ItemPage queryCooperationDatasourceTypeItemPage(CooperationForm cooperationForm, String bustype, String status) {
		
		String sql = "select t.datasource_id,max(t.datasource_code),max(t.datasource_name),max(t.parent_datasource_id),max(t.parent_datasource_name),max(t.datasource_source),max(t.bus_types),max(t.interface_table_name)," +
				" max(tt.month),max(tt.create_time),max(tt.sync_time),max(tt.sync_status),max(tt.status) " +
				" from cooperation_datasource_type t left join (select * from cooperation_datasource_records" +
				" inner join (select max(dr.sync_time) as sync_time, dr.datasource_id from cooperation_datasource_records dr group by dr.datasource_id) t1 " +
				" using(sync_time,datasource_id)) tt on t.datasource_id=tt.datasource_id " +
				" where t.datasource_source in ('3','4') and t.bus_types='"+bustype+"' and t.status='"+status+"' ";
		if(StringUtils.isNotEmpty(cooperationForm.getMonth())){
			sql += " and tt.month = '"+cooperationForm.getMonth()+"' ";
		}
		if(StringUtils.isNotEmpty(cooperationForm.getStatus())){
			sql += " and tt.sync_status = '"+cooperationForm.getStatus()+"' ";
		}
		sql += " group by t.datasource_id order by max(tt.create_time) desc";
		return enterpriseService.findBySql(sql, cooperationForm);
	}

	@Override
	public CooperationDatasourceRecords querySyncDataByDatasourceId(String datasourceId) {
		QueryBuilder query = new QueryBuilder(CooperationDatasourceRecords.class);
		query.where("datasourceId",datasourceId,QueryAction.EQUAL);
		query.orderBy("createTime desc");
		List<CooperationDatasourceRecords> list = (List<CooperationDatasourceRecords>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Object[]> getSeriesDataByType(String sql) {
		if(StringUtils.isEmpty(sql)){
			return new ArrayList<Object[]>();
		}
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public String queryResultBySql(String column, String table, int year) {
		String sql = "select "+column+" from "+table+"  t left join cooperation_datasource_records cd on t.datasource_records_id = cd.record_id where cd.status = '3' and t.column_01='"+year+"'";
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
		List<String> list = query.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<YuanTableColumnManage> queryManageByTableName(int length,String tableName) {
		QueryBuilder query = new QueryBuilder(YuanTableColumnManage.class);
		query.where("tableId",tableName,QueryAction.EQUAL);
		query.orderBy("columnName");
		List<YuanTableColumnManage> list = (List<YuanTableColumnManage>) enterpriseService.query(query, 0);
		//如果数据校验未配置,使用默认配置
		if(list == null || list.size() == 0){
			for (int i = 1; i <= length; i++) {
				YuanTableColumnManage ytc = new YuanTableColumnManage();
				ytc.setTableId(tableName);
				ytc.setColumnName("COLUMN_0"+i);
				ytc.setNullable("1");//是否可为空
				ytc.setDataLength(1000);//字符长度
				ytc.setDataType("String");//数据类型
				ytc.setDataFormat("");//数据格式
				ytc.setShowOrder(i);
				list.add(ytc);
			}
		}
		return list;
	}

	@Override
	public List queryTableNameByDict(String id) {
		String sql = "select t1.* from SYS_PARAMETER t1 join SYS_PARAMETER_TYPE t2 on t1.parameter_type_id = t2.parameter_type_id " +
				"where t2.parameter_type_code = 'DS_DETAIL' and t1.parameter_code = '" + id + "'";
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public List queryReportTableVoByTableName(String tab) {
//		String sql = "select TABLE_NAME as tableName, COLUMN_NAME as columnName, COMMENTS as comments FROM USER_COL_COMMENTS WHERE TABLE_NAME = '" + tab + "'";
		String sql = "SELECT t1.TABLE_NAME tableName,t1.COLUMN_NAME columnName,t1.COMMENTS comments,t2.COMMENTS tab_comments " +
				"FROM USER_COL_COMMENTS t1, user_tab_comments t2 " +
				"WHERE t1.TABLE_NAME = t2.TABLE_NAME AND t1.TABLE_NAME = '" + tab + "' ";
//				"ORDER BY COLUMN_NAME"
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
		List<Object[]> list = query.list();
		List<Object[]> result = new ArrayList<Object[]>();
		if(list != null){
			for (Object[] object : list) {
				Object[] obj = new Object[4];
				String tableName = (String) object[0];
				String columnName = (String) object[1];
				String comments = (String) object[2];
				String tabComments = (String) object[3];
				obj[0] = tableName;
				obj[1] = CommonUtil.replaceUnderlineAndfirstToUpper(columnName,"_","");
				obj[2] = comments;
				obj[3] = tabComments;
				result.add(obj);
			}
		}
		return result;
	}

	@Override
	public ItemPage queryTableItemPageByTableName(String tab, CooperationForm cooperationForm) {
		if(StringUtils.isNotBlank(tab)){
			String sql = "select * from " + tab;
			return enterpriseService.findBySql(sql, cooperationForm);
		}
		return null;
	}

	@Override
	public SysUser querySysUserByName(String userName) {
		if(StringUtils.isEmpty(userName)){
			return null;
		}
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("userName",userName,QueryAction.EQUAL);
		List<SysUser> list = (List<SysUser>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
}
 