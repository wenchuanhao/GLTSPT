package com.cdc.dc.rules.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.XMLGregorianCalendar;

import model.sys.entity.SysCount;
import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.ServiceException;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;
import org.trustel.service.sql.a.ICondition;
import org.trustel.system.SystemConstant;
import org.trustel.util.DateUtils;

import com.cdc.common.BusTypes;
import com.cdc.dc.metadata.yuan.model.YuanTableColumnManage;
import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.form.RulesForm;
import com.cdc.dc.rules.model.InterfaceManagetaskRecords;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.dc.rules.model.RulesFlowInfo;
import com.cdc.dc.rules.model.RulesFlowInsactor;
import com.cdc.dc.rules.model.RulesInfo;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.model.SysParameterType;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.cache.SysParamHelper;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvInputCollection;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvInputItem;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvOutputCollection;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvOutputItem;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvRequest;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvResponse;
import com.cmcc.webservice.managertasktodosrv.ManageTaskClient;
import com.trustel.common.ItemPage;
/**
 * 制度接口业务实现
 * @author ZENGKAI
 * @date 2016-04-07 09:58:29
 */
public class RulesServiceImpl implements IRulesService{

	private IEnterpriseService enterpriseService;
	private Log logger = LogFactory.getLog(getClass());
	private DateFormat DF_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat DF_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ManageTaskClient manageTaskClient;    
    
    public ManageTaskClient getManageTaskClient() {
		return manageTaskClient;
	}

	public void setManageTaskClient(ManageTaskClient manageTaskClient) {
		this.manageTaskClient = manageTaskClient;
	}

    public void setEnterpriseService(IEnterpriseService service) {
		this.enterpriseService = service;
	}

	@Override
	public void saveEntity(Object item) throws ServiceException {
		enterpriseService.save(item);
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
	public void saveOrUpdateEntity(Object item) {
		enterpriseService.saveOrUpdate(item);
	}

	@Override
	public Object getEntity(Class clazz, String id) {
		Session session = enterpriseService.getSessions();
		Object obj = enterpriseService.getById(clazz,id);
		session.clear();
		session = null;
		return  obj;
	}
	

	@Override
	public void delEntity(Object obj) {
		HibernateTemplate hibernateTemplate = enterpriseService.getHibernateTemplates();
		hibernateTemplate.delete(obj);
	}

	@Override
	public void setCommonAttribute(HttpServletRequest request) {
		//制度等级
		List<SysParameter> rulesGrades = SysParamHelper.getSysParamListByParamTypeCode("RULES_GRADES_CONFIG");
		//制度分类
		List<RulesType> rulesTypeList = queryAllByBusType(BusTypes.busTypes_ZD,null);
		request.setAttribute("rulesGrades", rulesGrades);//制度等级
		request.setAttribute("rulesTypeList", treeList(rulesTypeList));//制度分类
		
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("organizationId",RulesCommon.sysorganizationrootID,QueryAction.NOEQUAL);
		query.where("parentId",RulesCommon.sysorganizationrootID,QueryAction.EQUAL);
		List<SysOrganization> list = (List<SysOrganization>)enterpriseService.query(query, 0);
		
		request.setAttribute("rulesLeadDepList", list);//牵头部门
	}

	/**
	 * 组装树形
	 * @author WEIFEI
	 * @date 2016-4-21 下午2:39:30
	 * @param list
	 * @return
	 * @throws Exception	List<RulesType>
	 */
	private List<RulesType> treeList(List<RulesType> list){
		Map<String, RulesType> map = new HashMap<String, RulesType>();
		for (RulesType vo : list) {
			map.put(vo.getTypeId(), vo);
		}
		List<RulesType> newList = new ArrayList<RulesType>();
		for (RulesType vo : list) {
			if (RulesCommon.rulesTypeRootParentId.equals(vo.getParentTypeId())) {//vo.getIsMenu().equals("1") && 
				RulesType mapSM = map.get(vo.getTypeId());
				newList.add(mapSM);
			}
			if (map.containsKey(vo.getParentTypeId())&& !RulesCommon.rulesTypeRootParentId.equals(vo.getParentTypeId())) {// && vo.getIsMenu().equals("1") 
				//当前目录的上一级目录
				RulesType parentSM = map.get(vo.getParentTypeId());
				//上级目录添加子目录
				parentSM.getNextList().add(vo);
			}
		}
		return newList;
	}
	@Override
	public String saveOrUpdateFileInfo(RulesFileUpload fileUpload) {
		logger.info("附件更新或删除……");
		if(StringUtils.isNotEmpty(fileUpload.getFileId())){
			enterpriseService.updateObject(fileUpload);
			return fileUpload.getFileId();
		}
		return (String) enterpriseService.save(fileUpload);
		
	}

	@Override
	public List<RulesFileUpload> queryRulesFileList(String fileTempId,String type) {
		if(!StringUtils.isNotEmpty(fileTempId)){
			return new ArrayList<RulesFileUpload>();
		}
		QueryBuilder query = new QueryBuilder(RulesFileUpload.class);
		query.where("rulesInfoid", fileTempId, QueryAction.EQUAL);//制度编号
		query.where("types", type, QueryAction.EQUAL );//附件类型
		query.where("isParent", RulesCommon.rulesFileUploadIsParent_Y, QueryAction.NOEQUAL );//非父信息
		query.where("status", RulesCommon.rulesFileUploadStatus_Save, QueryAction.EQUAL );//已存
//		query.where("busTypes", BusTypes.busTypes_ZD, QueryAction.EQUAL );//制度管理
		query.orderBy("createTime desc");
		return (List)enterpriseService.query(query, 0);
	
	}
	
	@Override
	public List<RulesFileUpload> queryRulesFileUploadByRulesId(String rulesId,String type) {

		QueryBuilder query = new QueryBuilder(RulesFileUpload.class);
		query.where("rulesInfoid", rulesId, QueryAction.EQUAL);//制度编号
		query.where("types", type, QueryAction.EQUAL );//附件类型
		query.where("isParent", RulesCommon.rulesFileUploadIsParent_Y, QueryAction.EQUAL );//父信息
		query.where("status", RulesCommon.rulesFileUploadStatus_Save, QueryAction.EQUAL );//已存
//		query.where("busTypes", BusTypes.busTypes_ZD, QueryAction.EQUAL );//制度管理
		query.orderBy("createTime desc");
		return (List<RulesFileUpload>) enterpriseService.query(query, 0);
	
	}

	@Override
	public String getRulesId(String org) {
		//制度编号,头部
		String head = getConfigStr("RULESIDCONFIG","NFJD");
		SysOrganization parentOrg = queryParentOrg(org);
		//制度编号,部门简称
		String orgShortName = "";
		if(parentOrg != null && StringUtils.isNotEmpty(parentOrg.getOrganizationId())){
			orgShortName = SysParamHelper.getSysParamByCode(RulesCommon.DEPARTMENT_JP, parentOrg.getOrganizationId()).getParameterValue();
			if(!StringUtils.isNotEmpty(orgShortName)){
				orgShortName = "BMJP";
			}
		}
		//根据部门简称获取流水号
		String id = getIds(orgShortName,BusTypes.busTypes_ZD);
		return head + "-" + orgShortName + "-" + id;
	}

	/**
	 * 根据系统配置类型获取系统配置类型值
	 * @param code
	 * @param def
	 * @return
	 */
	public String getConfigStr(String code,String def) {
		QueryBuilder builder=new QueryBuilder(SysParameterType.class);
		builder.where("parameterTypeCode",code,QueryAction.EQUAL);
		List<SysParameterType> parameterTypes = (List<SysParameterType>)enterpriseService.query(builder,0);
		if(parameterTypes != null && parameterTypes.size() > 0){
			return parameterTypes.get(0).getParameterTypeValue();
		}
		return def;
	}

	@Override
	public Long getConfigLong(String code, Long def) {
		QueryBuilder builder=new QueryBuilder(SysParameterType.class);
		builder.where("parameterTypeCode",code,QueryAction.EQUAL);
		List<SysParameterType> parameterTypes = (List<SysParameterType>)enterpriseService.query(builder,0);
		if(parameterTypes != null && parameterTypes.size() > 0){
			return Long.parseLong(parameterTypes.get(0).getParameterTypeValue());
		}
		return def;
	}
	
	/**
	 * 根据部门简称获取流水号
	 * @param formatdate
	 * @param type
	 * @return
	 */
	public String getIds(String formatdate, String type) {
		QueryBuilder builder = new QueryBuilder(SysCount.class);
		builder.where("type", type);
		builder.where("formatdate", formatdate);
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
			enterpriseService.save(new SysCount(type,"001",formatdate));
		}
		return no;
	}

	@Override
	public List<RulesType> queryAllByBusType(String bustype,String parentTypeId) {
		QueryBuilder query = new QueryBuilder(RulesType.class);
		query.where("busTypes",bustype,QueryAction.EQUAL);
		query.where("parentTypeId",parentTypeId,QueryAction.EQUAL);
		query.where("status",RulesCommon.rulesTypeStatus_Save,QueryAction.EQUAL);
		query.orderBy("createTime");
		return (List<RulesType>) enterpriseService.query(query, 0);
	
	}

	@Override
	public ItemPage queryDraftRulesInfoItemPage(RulesForm rulesForm) {
		StringBuffer sb = new StringBuffer();
		sb.append("select " +
				" a.rules_id,a.rules_code,a.rules_name,a.rules_grade,a.rules_type_id,a.lead_dep_id,to_char(a.update_time,'yyyy-mm-dd HH24:MI:SS') as create_time,a.create_userid,a.create_username,a.status," +
				" b.type_id,b.type_name,b.parent_type_id,b.parent_type_name,b.is_root,to_char(b.create_time,'yyyy-mm-dd HH24:MI:SS') as createtime,b.create_userid as createuserid,b.create_username as createusername,b.bus_types " +
				" from rules_info a left join rules_type b on a.rules_type_id=b.type_id where 1=1 ");
		if(StringUtils.isNotEmpty(rulesForm.getRulesName())){ //制度名称
			sb.append(" and a.rules_name like '%"+rulesForm.getRulesName()+"%'");
		}
		if(StringUtils.isNotEmpty(rulesForm.getCreateUserid()) && !StringUtils.isNotEmpty(rulesForm.getLeadDepId())){ //创建人
			sb.append(" and a.create_userid='"+rulesForm.getCreateUserid()+"' and a.status='"+RulesCommon.rulesInfoStatus_CG+"' ");
		}
		if(!StringUtils.isNotEmpty(rulesForm.getCreateUserid()) && StringUtils.isNotEmpty(rulesForm.getLeadDepId())){ //牵头部门
			sb.append(" and a.lead_dep_id='"+rulesForm.getLeadDepId()+"' and a.status='"+RulesCommon.rulesInfoStatus_TH+"' ");
		}
		//创建人及牵头部门
		if(StringUtils.isNotEmpty(rulesForm.getCreateUserid()) && StringUtils.isNotEmpty(rulesForm.getLeadDepId())){
			sb.append(" and ( (a.create_userid='"+rulesForm.getCreateUserid()+"' and a.status='"+RulesCommon.rulesInfoStatus_CG+"' )or " +
					"(a.lead_dep_id='"+rulesForm.getLeadDepId()+"'  and a.status='"+RulesCommon.rulesInfoStatus_TH+"' ) ) ");
		}
		
		sb.append(" order by a.update_time desc");
		
		return enterpriseService.findBySql(sb.toString(), rulesForm);
	}

	/**
	 * 制度提交
	 */
	@Override
	public void submit(RulesInfo rulesInfo,SysUser sysUser) {
		//制度信息保存
		if(sysUser != null){
			rulesInfo.setCreateTime(new Date());
			rulesInfo.setCreateUserid(sysUser.getUserId());
			rulesInfo.setCreateUsername(sysUser.getUserName());
			SysOrganization parentOrg = queryParentOrg(sysUser.getOrganizationId());
			rulesInfo.setLeadDepId(parentOrg.getOrganizationId());//牵头部门
			rulesInfo.setUpdateTime(new Date());
			rulesInfo.setUpdateUserid(sysUser.getUserId());
			rulesInfo.setUpdateUsername(sysUser.getUserName());
		}
		if(StringUtils.isNotEmpty(rulesInfo.getRulesId())){
			RulesInfo ruletmp = (RulesInfo) getEntity(RulesInfo.class, rulesInfo.getRulesId());
			//新增
			if(ruletmp == null){
				rulesInfo.setRulesCode(getRulesId(sysUser.getOrganizationId()));//生成附件编号
				saveEntity(rulesInfo);
				//更新
			}else{
				if(!StringUtils.isNotEmpty(ruletmp.getRulesCode())){
					rulesInfo.setRulesCode(getRulesId(sysUser.getOrganizationId()));//生成附件编号
				}
				rulesInfo.setCreateTime(ruletmp.getCreateTime());
				rulesInfo.setCreateUserid(ruletmp.getCreateUserid());
				rulesInfo.setCreateUsername(ruletmp.getCreateUsername());
				rulesInfo.setLeadDepId(ruletmp.getLeadDepId());//牵头部门
				updateEntity(rulesInfo);
			}
		}
		//判断是否制度退回的草稿
		//查询退回的最新的未处理流程
		RulesFlowInfo rulesFlowInfo_th = queryRulesFlowInfoStatus(rulesInfo.getRulesId(),RulesCommon.rulesFlowInfoFlowLink_TH,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
		
		
		//流程信息保存
		RulesFlowInfo rulesFlowInfo = new RulesFlowInfo();
		rulesFlowInfo.setRulesInfoid(rulesInfo.getRulesId());
		rulesFlowInfo.setFlowLink(RulesCommon.rulesFlowInfoFlowLink_CJ);
		rulesFlowInfo.setCreateTime(new Date());
		rulesFlowInfo.setHandelUserid(sysUser.getUserId());
		rulesFlowInfo.setHandelUsername(sysUser.getUserName());
		rulesFlowInfo.setHandelOpinion("制度创建");
		rulesFlowInfo.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_NONE);
		if(rulesFlowInfo_th != null){
			rulesFlowInfo.setFromFlowId(rulesFlowInfo_th.getFlowId());//上一流程
		}
		saveEntity(rulesFlowInfo);
		
		if(rulesFlowInfo_th != null){
			//更新退回流程
			rulesFlowInfo_th.setHandelTime(new Date());
			rulesFlowInfo_th.setHandelResult(RulesCommon.rulesFlowInfoHandelResult_TJ);//再提交
			rulesFlowInfo_th.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_DONE);//已处理
			rulesFlowInfo_th.setToFlowId(rulesFlowInfo.getFlowId());//下一流程
			updateEntity(rulesFlowInfo_th);
		}
		
		//待办保存
		RulesFlowInsactor rulesFlowInsactor = new RulesFlowInsactor();
		rulesFlowInsactor.setInsacatorType(RulesCommon.rulesFlowInsactorsType_DB);//待办,检查类型
		rulesFlowInsactor.setFlowId(rulesFlowInfo.getFlowId());//流程信息ID
		rulesFlowInsactor.setRulesInfoid(rulesInfo.getRulesId());//制度编号
		rulesFlowInsactor.setCreateTime(new Date());//产生时间
		rulesFlowInsactor.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_NONE);//未完成
		rulesFlowInsactor.setTimeoutDays(getConfigLong("RULES_TIMEOUTDAYS",2L));//超时时限
		rulesFlowInsactor.setTimeoutStatus(RulesCommon.rulesFlowInsactorsTimeoutStatuss_N);//超时状态
		saveEntity(rulesFlowInsactor);
		logger.info("制度提交……");
		
	}

	@Override
	public ItemPage queryMineRulesInfoItemPage(RulesForm rulesForm) {

		Class<?>[] pojos = {RulesInfo.class,RulesType.class};
		QueryBuilder query = new QueryBuilder(pojos);
		
		query.where(" a.rulesTypeId=b.typeId ");
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesName())){ //制度名称
			query.where("a.rulesName", rulesForm.getRulesName(), QueryAction.LIKE);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesGrade())){//制度等级
			query.where("a.rulesGrade",rulesForm.getRulesGrade(),QueryAction.EQUAL);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesTypeId())){//制度分类
			query.where("a.rulesTypeId",rulesForm.getRulesTypeId(),QueryAction.EQUAL);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getCreateUserid()) && !StringUtils.isNotEmpty(rulesForm.getLeadDepId())){ //创建人
			query.where("a.createUserid", rulesForm.getCreateUserid(), QueryAction.EQUAL);
		}
		
		if(!StringUtils.isNotEmpty(rulesForm.getCreateUserid()) && StringUtils.isNotEmpty(rulesForm.getLeadDepId())){ //牵头部门
			query.where("a.leadDepId", rulesForm.getLeadDepId(), QueryAction.EQUAL);
		}
		//创建人及牵头部门
		if(StringUtils.isNotEmpty(rulesForm.getCreateUserid()) && StringUtils.isNotEmpty(rulesForm.getLeadDepId())){
			query.where(" (a.createUserid='"+rulesForm.getCreateUserid()+"' or a.leadDepId='"+rulesForm.getLeadDepId()+"') ");
		}
		
		//状态
		if(StringUtils.isNotEmpty(rulesForm.getStatus())){
			String[] status = rulesForm.getStatus().split(",");
			if(RulesCommon.rulesInfoStatus_QXZ.equals(rulesForm.getStatus())){
				status = new String[]{RulesCommon.rulesInfoStatus_SH,RulesCommon.rulesInfoStatus_FB,RulesCommon.rulesInfoStatus_FZ,RulesCommon.rulesInfoStatus_XD};
			}
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0;i < status.length; i++) {
				if(i==0){
					sb.append(" a.status='"+status[i]+"'");
				}else{
					sb.append(" or a.status='"+status[i]+"'");
				}
			}
			sb.append(")");
			query.where(sb.toString());
		}else{
			//query.where(" (a.status='"+RulesCommon.rulesInfoStatus_SH+"' or a.status='"+RulesCommon.rulesInfoStatus_FB+"') ");//已发布或审核中
			query.where(" (a.status='"+RulesCommon.rulesInfoStatus_SH+"' or a.status='"+RulesCommon.rulesInfoStatus_FB+"' or a.status='"+RulesCommon.rulesInfoStatus_FZ+"' or a.status='"+RulesCommon.rulesInfoStatus_XD+"') ");//已发布或审核中或已修订或已废止
		}
		query.orderBy("a.createTime", false);
		
		
		//查询最新发布时间、废止时间、相关文档数量等
		return enterpriseService.query(query, rulesForm);
	
	}

	/**
	 * 根据条件查询流程信息
	 * @param rulesInfoid	制度ID
	 * @param handelStatus	处理状态，1，处理中，2：已处理
	 * @param handelResult	处理结果
	 * @return
	 */
	public RulesFlowInfo queryRulesFlowInfoStatus(String rulesInfoid,String flowLink,String handelStatus,String handelResult) {
		QueryBuilder builder=new QueryBuilder(RulesFlowInfo.class);
		if(StringUtils.isNotEmpty(rulesInfoid)){
			builder.where("rulesInfoid",rulesInfoid,QueryAction.EQUAL);
		}
		if(StringUtils.isNotEmpty(flowLink)){
			builder.where("flowLink",flowLink,QueryAction.EQUAL);
		}
		if(StringUtils.isNotEmpty(handelStatus)){
			builder.where("handelStatus",handelStatus,QueryAction.EQUAL);
		}
		if(StringUtils.isNotEmpty(handelResult)){
			builder.where("handelResult",handelResult,QueryAction.EQUAL);
		}
		//制度创建查最早的时间
		if(RulesCommon.rulesFlowInfoFlowLink_CJ.equals(flowLink)){
			builder.orderBy("createTime", true);
		}else{
			builder.orderBy("createTime", false);
		}
		List<RulesFlowInfo> list = (List<RulesFlowInfo>)enterpriseService.query(builder,0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据条件查询待办待阅信息
	 * @param rulesId	制度信息ID
	 * @param insactorsType 待办待阅区分
	 * @param handelStatus  处理状态
	 * @return
	 */
	public RulesFlowInsactor queryRulesFlowInsactorStatus(String rulesInfoid,String insactorsType,String handelStatus) {
		QueryBuilder builder=new QueryBuilder(RulesFlowInsactor.class);
		if(StringUtils.isNotEmpty(rulesInfoid)){
			builder.where("rulesInfoid",rulesInfoid,QueryAction.EQUAL);
		}
		if(StringUtils.isNotEmpty(insactorsType)){
			builder.where("insacatorType",insactorsType,QueryAction.EQUAL);
		}
		if(StringUtils.isNotEmpty(handelStatus)){
			builder.where("handelStatus",handelStatus,QueryAction.EQUAL);
		}
		builder.orderBy("createTime", false);
		List<RulesFlowInsactor> list = (List<RulesFlowInsactor>)enterpriseService.query(builder,0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 待办、待阅
	 */
	@Override
	public ItemPage queryRemainRulesInfoItemPage(RulesForm rulesForm,String insactorsHandelStatus,String insactorsType) {


		Class<?>[] pojos = {RulesInfo.class,RulesType.class,SysOrganization.class,RulesFlowInsactor.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.rulesTypeId=b.typeId ");
		query.where(" a.leadDepId=c.organizationId ");
		query.where(" a.rulesId=d.rulesInfoid ");
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesName())){ //制度名称
			query.where("a.rulesName", rulesForm.getRulesName(), QueryAction.LIKE);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesGrade())){//制度等级
			query.where("a.rulesGrade",rulesForm.getRulesGrade(),QueryAction.EQUAL);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesTypeId())){//制度分类
			query.where("a.rulesTypeId",rulesForm.getRulesTypeId(),QueryAction.EQUAL);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getLeadDepId())){ //牵头部门
			query.where("a.leadDepId", rulesForm.getLeadDepId(), QueryAction.EQUAL);
		}
		//制度状态
		if(StringUtils.isNotEmpty(rulesForm.getStatus())){
			String[] status = rulesForm.getStatus().split(",");
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0;i < status.length; i++) {
				if(i==0){
					sb.append(" a.status='"+status[i]+"'");
				}else{
					sb.append(" or a.status='"+status[i]+"'");
				}
			}
			sb.append(")");
			query.where(sb.toString());
		}
		if( rulesForm.getHandelBeginDate() != null ){
			query.where("d.handelTime", rulesForm.getHandelBeginDate(), QueryAction.GT);
		}
		if( rulesForm.getHandelEndDate() != null ){
			query.where("d.handelTime", rulesForm.getHandelEndDate(), QueryAction.LE);
		}
		query.where("d.handelStatus",insactorsHandelStatus, QueryAction.EQUAL);//未处理
		query.where("d.insacatorType",insactorsType, QueryAction.EQUAL);//待办、待阅
		
		query.orderBy("d.createTime", false);
		
		return enterpriseService.query(query, rulesForm);
	
	}
	/**
	 * 已办、已阅
	 */
	public ItemPage queryDonesRulesInfoItemPage(RulesForm rulesForm,String insactorsHandelStatus,String insactorsType) {
		
		
		QueryBuilder query = new QueryBuilder("RULES_INFO a, RULES_TYPE b, SYS_ORGANIZATION c, RULES_FLOW_INSACTOR d ");
		
		query.select("a.rules_id,max(a.rules_code),max(a.rules_name),max(a.rules_grade),max(a.STATUS)," +
				"max(b.parent_type_name),max(b.type_name)," +
				"max(c.org_name)," +
				"to_char(max(d.create_time),'yyyy-mm-dd HH24:MI:SS'),max(d.timeout_status),to_char(max(d.handel_time),'yyyy-mm-dd HH24:MI:SS'),max(d.handel_status)");
		
		query.where(" a.rules_type_id=b.type_id ");
		query.where(" a.lead_dep_id=c.organization_id ");
		query.where(" a.rules_id=d.rules_infoid ");
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesName())){ //制度名称
			query.where("a.rules_name", rulesForm.getRulesName(), QueryAction.LIKE);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesGrade())){//制度等级
			query.where("a.rules_grade",rulesForm.getRulesGrade(),QueryAction.EQUAL);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesTypeId())){//制度分类
			query.where("a.rules_type_id",rulesForm.getRulesTypeId(),QueryAction.EQUAL);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getLeadDepId())){ //牵头部门
			query.where("a.lead_dep_id", rulesForm.getLeadDepId(), QueryAction.EQUAL);
		}
		//制度状态
		if(StringUtils.isNotEmpty(rulesForm.getStatus())){
			String[] status = rulesForm.getStatus().split(",");
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0;i < status.length; i++) {
				if(i==0){
					sb.append(" a.status='"+status[i]+"'");
				}else{
					sb.append(" or a.status='"+status[i]+"'");
				}
			}
			sb.append(")");
			query.where(sb.toString());
		}
		if( rulesForm.getHandelBeginDate() != null ){
			query.where("d.handel_time", rulesForm.getHandelBeginDate(), QueryAction.GT);
		}
		if( rulesForm.getHandelEndDate() != null ){
			query.where("d.handel_time", rulesForm.getHandelEndDate(), QueryAction.LE);
		}
		query.where("d.handel_status",insactorsHandelStatus, QueryAction.EQUAL);//处理状态
		query.where("d.insacator_type",insactorsType, QueryAction.EQUAL);//已办、已阅
		query.groupby("a.rules_id");
		query.orderBy("max(d.create_time)", false);
		
		return enterpriseService.queryBySql(query, rulesForm , false);
		
	}

	/**
	 * 制度发布
	 */
	@Override
	public void releaseRemain(RulesInfo rulesInfo, SysUser sysUser) {
		if(rulesInfo == null || sysUser == null){
			return;
		}
		rulesInfo.setUpdateTime(new Date());
		rulesInfo.setUpdateUserid(sysUser.getUserId());
		rulesInfo.setUpdateUsername(sysUser.getUserName());
		//更新制度信息表
		updateEntity(rulesInfo);
		
		//查询最新的未处理流程
		RulesFlowInfo rulesFlowInfo = queryRulesFlowInfoStatus(rulesInfo.getRulesId(),null,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
		//查询最新的未处理待办
		RulesFlowInsactor rulesFlowInsactor = queryRulesFlowInsactorStatus(rulesInfo.getRulesId(),RulesCommon.rulesFlowInsactorsType_DB,RulesCommon.rulesFlowInsactorsHandelStatus_NONE);
		
		if(rulesFlowInfo == null || rulesFlowInsactor == null){
			return;
		}
		
		//新增已发布流程
		RulesFlowInfo rulesFlowInfoNew = new RulesFlowInfo();
		rulesFlowInfoNew.setFlowLink(RulesCommon.rulesFlowInfoFlowLink_FB);//制度已发布
		rulesFlowInfoNew.setRulesInfoid(rulesInfo.getRulesId());
		rulesFlowInfoNew.setCreateTime(new Date());//创建时间
		rulesFlowInfoNew.setHandelUserid(sysUser.getUserId());//处理人ID
		rulesFlowInfoNew.setHandelUsername(sysUser.getUserName());//处理人
		rulesFlowInfoNew.setHandelOpinion("制度发布");//处理意见/描述
		rulesFlowInfoNew.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_NONE);//下一流程处理状态,标识最新.有且仅有一条记录为NONE
		rulesFlowInfoNew.setFromFlowId(rulesFlowInfo.getFlowId());//上一流程
		saveEntity(rulesFlowInfoNew);
		
		//更新新建流程，新增发布流程
		rulesFlowInfo.setHandelTime(new Date());
		rulesFlowInfo.setHandelResult(RulesCommon.rulesFlowInfoHandelResult_FB);//发布
		rulesFlowInfo.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_DONE);//已处理
		rulesFlowInfo.setToFlowId(rulesFlowInfoNew.getFlowId());//下一流程
		updateEntity(rulesFlowInfo);
		
		//更新待办信息
		rulesFlowInsactor.setHandelTime(new Date());
		rulesFlowInsactor.setHandelUserid(sysUser.getUserId());
		rulesFlowInsactor.setHandelUsername(sysUser.getUserName());
		rulesFlowInsactor.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_DONE);
		updateEntity(rulesFlowInsactor);
		
		
		//发布超时信息保存
		RulesFlowInsactor rulesFlowInsactorFB = new RulesFlowInsactor();
		rulesFlowInsactorFB.setInsacatorType(RulesCommon.rulesFlowInsactorsType_FB);//发布超时信息保存
		rulesFlowInsactorFB.setFlowId(rulesFlowInfoNew.getFlowId());//流程信息ID
		rulesFlowInsactorFB.setRulesInfoid(rulesInfo.getRulesId());//制度编号
		rulesFlowInsactorFB.setCreateTime(new Date());//产生时间
//		rulesFlowInsactorFB.setHandelTime(new Date());
//		rulesFlowInsactorFB.setHandelUserid(sysUser.getUserId());
//		rulesFlowInsactorFB.setHandelUsername(sysUser.getUserName());
		rulesFlowInsactorFB.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_NONE);//未完成
		//基地级、跨部门级制度才需要更新超时提醒
		if(RulesCommon.rulesInfoGrade_BM.equals(rulesInfo.getRulesGrade())){
			rulesFlowInsactorFB.setTimeoutDays(0L);//部门级无更新时限
		}else{
			rulesFlowInsactorFB.setTimeoutDays(getConfigLong("RULES_TIMEOUTMONTHS",12L));//更新超时时限，按月
		}
		rulesFlowInsactorFB.setTimeoutStatus(RulesCommon.rulesFlowInsactorsTimeoutStatuss_N);//超时状态
		saveEntity(rulesFlowInsactorFB);
		logger.info("制度发布……");
	}

	/**
	 * 制度修订
	 */
	@Override
	public void reviseRules(RulesInfo rulesInfo, SysUser sysUser) {
		// //将该制度进行废止，状态为’已修订‘

		if(rulesInfo == null || sysUser == null){
			return;
		}
		rulesInfo.setUpdateTime(new Date());
		rulesInfo.setUpdateUserid(sysUser.getUserId());
		rulesInfo.setUpdateUsername(sysUser.getUserName());
		//更新制度信息表
		updateEntity(rulesInfo);
		
		//查询最新的未处理流程
		RulesFlowInfo rulesFlowInfo = queryRulesFlowInfoStatus(rulesInfo.getRulesId(),null,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
		if(rulesFlowInfo == null){
			return;
		}
		//新增已修订流程
		RulesFlowInfo rulesFlowInfoNew = new RulesFlowInfo();
		rulesFlowInfoNew.setFlowLink(RulesCommon.rulesFlowInfoFlowLink_XD);//制度修订
		rulesFlowInfoNew.setRulesInfoid(rulesInfo.getRulesId());
		rulesFlowInfoNew.setCreateTime(new Date());//创建时间
		rulesFlowInfoNew.setHandelUserid(sysUser.getUserId());//处理人ID
		rulesFlowInfoNew.setHandelUsername(sysUser.getUserName());//处理人
		rulesFlowInfoNew.setHandelOpinion("制度修订");//处理意见/描述
		rulesFlowInfoNew.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_NONE);//下一流程处理状态,标识最新.有且仅有一条记录为NONE
		rulesFlowInfoNew.setFromFlowId(rulesFlowInfo.getFlowId());//上一流程
		saveEntity(rulesFlowInfoNew);
		
		//更新发布流程，新增已修订流程
		rulesFlowInfo.setHandelTime(new Date());
		rulesFlowInfo.setHandelResult(RulesCommon.rulesFlowInfoHandelResult_XD);//修订
		rulesFlowInfo.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_DONE);//已处理
		rulesFlowInfo.setToFlowId(rulesFlowInfoNew.getFlowId());//下一流程
		updateEntity(rulesFlowInfo);
		
		//待阅
		RulesFlowInsactor rulesFlowInsactor = new RulesFlowInsactor();
		rulesFlowInsactor.setInsacatorType(RulesCommon.rulesFlowInsactorsType_DY);//待阅,检查类型
		rulesFlowInsactor.setFlowId(rulesFlowInfoNew.getFlowId());//流程信息ID
		rulesFlowInsactor.setRulesInfoid(rulesInfo.getRulesId());//制度编号
		rulesFlowInsactor.setCreateTime(new Date());//产生时间
		rulesFlowInsactor.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_NONE);//未完成
		rulesFlowInsactor.setTimeoutDays(0L);//超时时限，待阅无超时
		rulesFlowInsactor.setTimeoutStatus(RulesCommon.rulesFlowInsactorsTimeoutStatuss_N);//超时状态
		saveEntity(rulesFlowInsactor);
		logger.info("制度修订……");
	}

	/**
	 * 制度废止
	 */
	@Override
	public void repealRules(RulesInfo rulesInfo, SysUser sysUser, String handelOpinion) {

		if(rulesInfo == null || sysUser == null){
			return;
		}
		rulesInfo.setUpdateTime(new Date());
		rulesInfo.setUpdateUserid(sysUser.getUserId());
		rulesInfo.setUpdateUsername(sysUser.getUserName());
		//更新制度信息表
		updateEntity(rulesInfo);
		
		//查询最新的未处理流程
		RulesFlowInfo rulesFlowInfo = queryRulesFlowInfoStatus(rulesInfo.getRulesId(),null,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
		
		if(rulesFlowInfo == null){
			return;
		}
		
		//新增已废止流程
		RulesFlowInfo rulesFlowInfoNew = new RulesFlowInfo();
		rulesFlowInfoNew.setFlowLink(RulesCommon.rulesFlowInfoFlowLink_FZ);//制度废止
		rulesFlowInfoNew.setRulesInfoid(rulesInfo.getRulesId());
		rulesFlowInfoNew.setCreateTime(new Date());//创建时间
		rulesFlowInfoNew.setHandelUserid(sysUser.getUserId());//处理人ID
		rulesFlowInfoNew.setHandelUsername(sysUser.getUserName());//处理人
		rulesFlowInfoNew.setHandelOpinion(handelOpinion);//处理意见/描述
		rulesFlowInfoNew.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_NONE);//下一流程处理状态,标识最新.有且仅有一条记录为NONE
		rulesFlowInfoNew.setFromFlowId(rulesFlowInfo.getFlowId());//上一流程
		saveEntity(rulesFlowInfoNew);
		
		//更新发布流程，新增已修订流程
		rulesFlowInfo.setHandelTime(new Date());
		rulesFlowInfo.setHandelResult(RulesCommon.rulesFlowInfoHandelResult_FZ);//废止
		rulesFlowInfo.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_DONE);//已处理
		rulesFlowInfo.setToFlowId(rulesFlowInfoNew.getFlowId());//下一流程
		updateEntity(rulesFlowInfo);
		
		//待阅
		RulesFlowInsactor rulesFlowInsactor = new RulesFlowInsactor();
		rulesFlowInsactor.setInsacatorType(RulesCommon.rulesFlowInsactorsType_DY);//待阅,检查类型
		rulesFlowInsactor.setFlowId(rulesFlowInfoNew.getFlowId());//流程信息ID
		rulesFlowInsactor.setRulesInfoid(rulesInfo.getRulesId());//制度编号
		rulesFlowInsactor.setCreateTime(new Date());//产生时间
		rulesFlowInsactor.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_NONE);//未完成
		rulesFlowInsactor.setTimeoutDays(0L);//超时时限，待阅无超时
		rulesFlowInsactor.setTimeoutStatus(RulesCommon.rulesFlowInsactorsTimeoutStatuss_N);//超时状态
		saveEntity(rulesFlowInsactor);
		logger.info("制度废止……");
	}

	@Override
	public ItemPage queryRemindRulesInfoItemPage(RulesForm rulesForm) {



		Class<?>[] pojos = {RulesInfo.class,RulesType.class,RulesFlowInsactor.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.rulesTypeId=b.typeId ");
		query.where(" a.rulesId=c.rulesInfoid ");
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesName())){ //制度名称
			query.where("a.rulesName", rulesForm.getRulesName(), QueryAction.LIKE);
		}
		
//		if(StringUtils.isNotEmpty(rulesForm.getRulesGrade())){//制度等级
//			query.where("a.rulesGrade",rulesForm.getRulesGrade(),QueryAction.EQUAL);
//		}
		if(StringUtils.isNotEmpty(rulesForm.getCreateUserid()) && !StringUtils.isNotEmpty(rulesForm.getLeadDepId())){ //创建人
			query.where("a.createUserid", rulesForm.getCreateUserid(), QueryAction.EQUAL);
		}
		
		if(!StringUtils.isNotEmpty(rulesForm.getCreateUserid()) && StringUtils.isNotEmpty(rulesForm.getLeadDepId())){ //牵头部门
			query.where("a.leadDepId", rulesForm.getLeadDepId(), QueryAction.EQUAL);
		}
		//创建人及牵头部门
		if(StringUtils.isNotEmpty(rulesForm.getCreateUserid()) && StringUtils.isNotEmpty(rulesForm.getLeadDepId())){
			query.where(" (a.createUserid='"+rulesForm.getCreateUserid()+"' or a.leadDepId='"+rulesForm.getLeadDepId()+"') ");
		}
		if(StringUtils.isNotEmpty(rulesForm.getRulesTypeId())){//制度分类
			query.where("a.rulesTypeId",rulesForm.getRulesTypeId(),QueryAction.EQUAL);
		}
		
		//制度状态
		if(StringUtils.isNotEmpty(rulesForm.getStatus())){
			String[] status = rulesForm.getStatus().split(",");
			if(RulesCommon.rulesInfoStatus_QXZ.equals(rulesForm.getStatus())){
				status = new String[]{RulesCommon.rulesInfoStatus_SH,RulesCommon.rulesInfoStatus_FB,RulesCommon.rulesInfoStatus_FZ,RulesCommon.rulesInfoStatus_XD};
			}
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0;i < status.length; i++) {
				if(i==0){
					sb.append(" a.status='"+status[i]+"'");
				}else{
					sb.append(" or a.status='"+status[i]+"'");
				}
			}
			sb.append(")");
			query.where(sb.toString());
		}else{
			query.where(" (a.status='"+RulesCommon.rulesInfoStatus_SH+"' or a.status='"+RulesCommon.rulesInfoStatus_FB+"') ");//已发布或审核中
		}
		query.where(" a.status", rulesForm.getRemindStatus());//审核超时或更新超时
		query.where("c.handelStatus",RulesCommon.rulesFlowInsactorsHandelStatus_NONE, QueryAction.EQUAL);//未处理
		query.where("c.timeoutStatus",RulesCommon.rulesFlowInsactorsTimeoutStatuss_Y,QueryAction.EQUAL);//已超时
		query.orderBy("a.createTime", false);
		
		//查询相关文档数量
		return enterpriseService.query(query, rulesForm);
//		List<Object[]> list = new ArrayList<Object[]>();
//		if(itemPage != null && itemPage.getItems() != null) {
//			List<Object[]> objsList = (List<Object[]>) itemPage.getItems();
//			for(Object[] objs : objsList) {
//				Object[] os = new Object[4];
//				os[0] = objs[0];
//				os[1] = objs[1];
//				os[2] = objs[2];
//				RulesInfo rulesinfo = (RulesInfo) objs[0];
//				List<RulesFileUpload> fileList = queryRulesFileUploadByRulesId(rulesinfo.getRulesId(),RulesCommon.rulesFileUploadTypes_ZDXGWD);
//				//相关文档数量
//				if(fileList != null){
//					int count = 0;
//					for (RulesFileUpload rulesFileUpload : fileList) {
//						count += queryRulesFileList(rulesFileUpload.getFileId(),RulesCommon.rulesFileUploadTypes_ZDXGWD).size();
//					}
//					os[3] = count;
//				}else{
//					os[3] = 0;
//				}
//				list.add(os);
//			}
//		}
//		return new ItemPage(list, itemPage.getTotal(), itemPage.getPageIndex(),itemPage.getPageSize());
	
	}

	@Override
	public void updateRulesFlowInsactorTimeoutStatus() {
		//查询所有未超时且有时限的记录
		QueryBuilder builder=new QueryBuilder(RulesFlowInsactor.class);
		builder.where("timeoutDays",0L,QueryAction.NOEQUAL);//超时时间不为0
		builder.where("timeoutStatus",RulesCommon.rulesFlowInsactorsTimeoutStatuss_N,QueryAction.EQUAL);//超时状态为0
		builder.where("handelStatus",RulesCommon.rulesFlowInsactorsHandelStatus_NONE,QueryAction.EQUAL);//未完成
		
		List<RulesFlowInsactor> list = (List<RulesFlowInsactor>)enterpriseService.query(builder,0);
		if(list != null && list.size() > 0){
			for (RulesFlowInsactor rulesFlowInsactor : list) {
				//校验是否超时
				if(RulesCommon.rulesFlowInsactorsType_DB.equals(rulesFlowInsactor.getInsacatorType())){//待办，审核超时
					//若超时
					if(isDaysTimeOut( rulesFlowInsactor.getCreateTime(),rulesFlowInsactor.getTimeoutDays())){
						rulesFlowInsactor.setTimeoutStatus(RulesCommon.rulesFlowInsactorsTimeoutStatuss_Y);
						updateEntity(rulesFlowInsactor);
					}
				}else if(RulesCommon.rulesFlowInsactorsType_FB.equals(rulesFlowInsactor.getInsacatorType())){//发布，更新超时
					//若超时
					if(isMonthsTimeOut( rulesFlowInsactor.getCreateTime(),rulesFlowInsactor.getTimeoutDays())){
						rulesFlowInsactor.setTimeoutStatus(RulesCommon.rulesFlowInsactorsTimeoutStatuss_Y);
						updateEntity(rulesFlowInsactor);
					}
				}
			}
		}
	}


    private boolean isMonthsTimeOut(Date createTime, Long timeoutDays) {
    	boolean b = false;
        if (timeoutDays == null) {
            return b;
        }
    	
    	Query query = enterpriseService.getSessions().createSQLQuery("select months_between(sysdate,to_date(:queryDate,'yyyy-MM-dd hh24:mi:ss')) from dual");
        query.setString("queryDate",DF_DATE_TIME.format(createTime));
        BigDecimal result = (BigDecimal) query.uniqueResult();
        //比较大小
        if(result.compareTo(new BigDecimal(timeoutDays)) == 1  ){
        	b = true;
        }
		return b;
	}

	/**
     * 加入节假日之后判断从起始日期后是否过期
     * @param startTime
     * @param timeLimit
     * @return
     */
    private boolean isDaysTimeOut(Date startTime,Long timeLimit){
    	
    	boolean b = false;
        if (timeLimit == null) {
            return b;
        }
        Query query = enterpriseService.getSessions().createSQLQuery("select is_holiday_time_out(to_date(:queryDate,'yyyy-MM-dd hh24:mi:ss'),:timeLimit) from dual");
        query.setString("queryDate",DF_DATE_TIME.format(startTime));
        query.setLong("timeLimit",timeLimit);
        String result = (String) query.uniqueResult();
        if ("Y".equals(result)) {
        	b =  true;
        }
//        else if ("N".equals(result)) {
//            return b;
//        }
		return b;

    }

    /**
     * 制度已阅
     */
	@Override
	public void releaseRead(RulesInfo rulesInfo, SysUser sysUser) {

		if(sysUser == null){
			return;
		}
		
		//查询最新的未处理流程
		RulesFlowInfo rulesFlowInfo = queryRulesFlowInfoStatus(rulesInfo.getRulesId(),null,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
		if(rulesFlowInfo == null ){
			return;
		}
		//更新新建流程，已阅
		rulesFlowInfo.setHandelTime(new Date());
		rulesFlowInfo.setHandelResult(RulesCommon.rulesFlowInfoHandelResult_YY);//已阅
		rulesFlowInfo.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_DONE);//已处理
		updateEntity(rulesFlowInfo);
		
		
		//查询最新的未处理待办
		RulesFlowInsactor rulesFlowInsactor = queryRulesFlowInsactorStatus(rulesInfo.getRulesId(),RulesCommon.rulesFlowInsactorsType_DY,RulesCommon.rulesFlowInsactorsHandelStatus_NONE);
		if(rulesFlowInsactor == null){
			return;
		}
		//更新待阅
		rulesFlowInsactor.setHandelTime(new Date());
		rulesFlowInsactor.setHandelUserid(sysUser.getUserId());
		rulesFlowInsactor.setHandelUsername(sysUser.getUserName());
		rulesFlowInsactor.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_DONE);
		updateEntity(rulesFlowInsactor);
		logger.info("制度已阅操作……");
	}

	@Override
	public void returnRemain(RulesInfo rulesInfo, SysUser sysUser,String handelOpinion) {

		if(rulesInfo == null || sysUser == null){
			return;
		}
		rulesInfo.setUpdateTime(new Date());
		rulesInfo.setUpdateUserid(sysUser.getUserId());
		rulesInfo.setUpdateUsername(sysUser.getUserName());
		//更新制度信息表
		updateEntity(rulesInfo);
		
		//查询最新的未处理流程
		RulesFlowInfo rulesFlowInfo = queryRulesFlowInfoStatus(rulesInfo.getRulesId(),null,RulesCommon.rulesFlowInfoHandelStatus_NONE,null);
		//查询最新的未处理待办
		RulesFlowInsactor rulesFlowInsactor = queryRulesFlowInsactorStatus(rulesInfo.getRulesId(),RulesCommon.rulesFlowInsactorsType_DB,RulesCommon.rulesFlowInsactorsHandelStatus_NONE);
				
		if(rulesFlowInfo == null || rulesFlowInsactor == null){
			return;
		}
		
		//新增已退回流程
		RulesFlowInfo rulesFlowInfoNew = new RulesFlowInfo();
		rulesFlowInfoNew.setFlowLink(RulesCommon.rulesFlowInfoFlowLink_TH);//制度退回
		rulesFlowInfoNew.setRulesInfoid(rulesInfo.getRulesId());
		rulesFlowInfoNew.setCreateTime(new Date());//创建时间
		rulesFlowInfoNew.setHandelUserid(sysUser.getUserId());//处理人ID
		rulesFlowInfoNew.setHandelUsername(sysUser.getUserName());//处理人
		rulesFlowInfoNew.setHandelOpinion(handelOpinion);//处理意见/描述
		rulesFlowInfoNew.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_NONE);//下一流程处理状态,标识最新.有且仅有一条记录为NONE
		rulesFlowInfoNew.setFromFlowId(rulesFlowInfo.getFlowId());//上一流程
		saveEntity(rulesFlowInfoNew);
		
		//更新发布流程，新增已修订流程
		rulesFlowInfo.setHandelTime(new Date());//处理时间
		rulesFlowInfo.setHandelResult(RulesCommon.rulesFlowInfoHandelResult_TH);//退回
		rulesFlowInfo.setHandelStatus(RulesCommon.rulesFlowInfoHandelStatus_DONE);//已处理
		rulesFlowInfo.setToFlowId(rulesFlowInfoNew.getFlowId());//下一流程
		updateEntity(rulesFlowInfo);
		
		//更新待办信息
		rulesFlowInsactor.setHandelTime(new Date());
		rulesFlowInsactor.setHandelUserid(sysUser.getUserId());
		rulesFlowInsactor.setHandelUsername(sysUser.getUserName());
		rulesFlowInsactor.setHandelStatus(RulesCommon.rulesFlowInsactorsHandelStatus_DONE);
		updateEntity(rulesFlowInsactor);
		logger.info("制度退回操作……");
	}

	@Override
	public ItemPage queryAllRulesInfoItemPage(RulesForm rulesForm,String roleStr) {
		Class<?>[] pojos = {RulesInfo.class,RulesType.class,SysOrganization.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.rulesTypeId=b.typeId ");
		query.where(" a.leadDepId=c.organizationId ");
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesName())){ //制度名称
			query.where("a.rulesName", rulesForm.getRulesName(), QueryAction.LIKE);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesGrade())){//制度等级
			query.where("a.rulesGrade",rulesForm.getRulesGrade(),QueryAction.EQUAL);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesTypeId())){//制度分类
			query.where("a.rulesTypeId",rulesForm.getRulesTypeId(),QueryAction.EQUAL);
		}
		query.where("a.leadDepId", rulesForm.getLeadDepId(), QueryAction.EQUAL);
		//发布时间
		if( rulesForm.getHandelBeginDate() != null ){
			query.where("a.updateTime", rulesForm.getHandelBeginDate(), QueryAction.GT);
		}
		if( rulesForm.getHandelEndDate() != null ){
			query.where("a.updateTime", rulesForm.getHandelEndDate(), QueryAction.LE);
		}
		if( rulesForm.getHandelBeginDate() != null || rulesForm.getHandelEndDate() != null ){
			rulesForm.setStatus(RulesCommon.rulesInfoStatus_FB);//查询已发布
		}
		//普通用户
		if(RulesCommon.userRolesCommon.equals(roleStr)){
			query.where("a.status",RulesCommon.rulesInfoStatus_SH,QueryAction.NOEQUAL);
			//制度状态
			if(StringUtils.isNotEmpty(rulesForm.getStatus())){
				String[] status = rulesForm.getStatus().split(",");
				if(RulesCommon.rulesInfoStatus_QXZ.equals(rulesForm.getStatus())){
					status = new String[]{RulesCommon.rulesInfoStatus_FB,RulesCommon.rulesInfoStatus_FZ,RulesCommon.rulesInfoStatus_XD};
				}
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				for (int i = 0;i < status.length; i++) {
					if(i==0){
						sb.append(" a.status='"+status[i]+"'");
					}else{
						sb.append(" or a.status='"+status[i]+"'");
					}
				}
				sb.append(")");
				query.where(sb.toString());
			}else{
				query.where(" ( a.status='"+RulesCommon.rulesInfoStatus_FB+"') ");//已发布
			}

			//部门制度员
		}else if(RulesCommon.userRolesDep.equals(roleStr)){
			//制度状态
			if(StringUtils.isNotEmpty(rulesForm.getStatus())){
				String[] status = rulesForm.getStatus().split(",");
				if(RulesCommon.rulesInfoStatus_QXZ.equals(rulesForm.getStatus())){
					status = new String[]{RulesCommon.rulesInfoStatus_SH,RulesCommon.rulesInfoStatus_FB,RulesCommon.rulesInfoStatus_FZ,RulesCommon.rulesInfoStatus_XD};
				}
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				for (int i = 0;i < status.length; i++) {
					if(i==0){
						if(RulesCommon.rulesInfoStatus_SH.equals(status[i])){
							sb.append(" ( a.status='"+status[i]+"'  and a.leadDepId='"+rulesForm.getLeadDepId()+"') ");
						}else{
							sb.append(" a.status='"+status[i]+"'");
						}
					}else{
						if(RulesCommon.rulesInfoStatus_SH.equals(status[i])){
							sb.append(" or ( a.status='"+status[i]+"'  and a.leadDepId='"+rulesForm.getLeadDepId()+"') ");
						}else{
							sb.append(" or a.status='"+status[i]+"'");
						}
					}
				}
				sb.append(")");
				query.where(sb.toString());
			}else{
				query.where(" ( (a.status='"+RulesCommon.rulesInfoStatus_SH+"'  and a.leadDepId='"+rulesForm.getLeadDepId()+"') or ( a.status='"+RulesCommon.rulesInfoStatus_FB+"')) ");//已发布或审核中
			}
		}else if(RulesCommon.userRolesAdmin.equals(roleStr)){
			//制度状态
			if(StringUtils.isNotEmpty(rulesForm.getStatus())){
				String[] status = rulesForm.getStatus().split(",");
				if(RulesCommon.rulesInfoStatus_QXZ.equals(rulesForm.getStatus())){
					status = new String[]{RulesCommon.rulesInfoStatus_SH,RulesCommon.rulesInfoStatus_FB,RulesCommon.rulesInfoStatus_FZ,RulesCommon.rulesInfoStatus_XD};
				}
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				for (int i = 0;i < status.length; i++) {
					if(i==0){
						sb.append(" a.status='"+status[i]+"'");
					}else{
						sb.append(" or a.status='"+status[i]+"'");
					}
				}
				sb.append(")");
				query.where(sb.toString());
			}else{
				query.where(" (a.status='"+RulesCommon.rulesInfoStatus_SH+"' or a.status='"+RulesCommon.rulesInfoStatus_FB+"') ");//已发布或审核中
			}
			
		}
		
		
		query.where("a.status", RulesCommon.rulesInfoStatus_CG, QueryAction.NOEQUAL);
		query.where("a.status", RulesCommon.rulesInfoStatus_DEL, QueryAction.NOEQUAL);
		
		query.orderBy("a.createTime", false);
		
		//查询最新发布时间、废止时间、相关文档数量等
		return enterpriseService.query(query, rulesForm);
	
	}

	@Override
	public ItemPage queryRulesFlowInfoList(String rulesId) {
		RulesForm form = new RulesForm();
		form.setPageSize(99999);
		StringBuffer sb = new StringBuffer();
		sb.append("select a.FLOW_ID,a.RULES_INFOID,a.FLOW_LINK,a.HANDEL_USERID,a.HANDEL_USERNAME,to_char(a.create_time,'yyyy-mm-dd HH24:MI:SS') as CREATE_TIME," +
				" to_char(a.HANDEL_TIME,'yyyy-mm-dd HH24:MI:SS') as HANDEL_TIME,a.HANDEL_STATUS,a.HANDEL_OPINION,a.HANDEL_RESULT,a.FROM_FLOW_ID,a.TO_FLOW_ID," +
				" to_char(b.create_time,'yyyy-mm-dd HH24:MI:SS') as CREATE_DATE,to_char(b.HANDEL_TIME,'yyyy-mm-dd HH24:MI:SS') as HANDEL_DATE,b.handel_result as handelResult," +
				" calc_workday(b.create_time,a.create_time) as hours ");
		sb.append(" from rules_flow_info a  left join  rules_flow_info b  ");
		sb.append(" on a.from_flow_id=b.flow_id ");
		sb.append(" where a.rules_infoid=? order by a.create_time asc ");
		
		Query query = enterpriseService.getSessions().createSQLQuery(sb.toString());
        query.setString(0,rulesId);
        query.setFirstResult((form.getPageIndex()-1) * form.getPageSize());
		query.setMaxResults(form.getPageSize());
		List list = query.list();
		return new ItemPage(list, list.size(), form.getPageIndex(), form.getPageSize());
	}

	@Override
	public ItemPage queryDocumentsItemPage(RulesForm rulesForm) {


		Class<?>[] pojos = {RulesFileUpload.class,RulesType.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.types=b.typeId ");//文档分类
		
		if(StringUtils.isNotEmpty(rulesForm.getAbstractName())){ //文档名称
			query.where("a.abstractName", rulesForm.getAbstractName(), QueryAction.LIKE);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getCreateUserid())){ //创建人
			query.where("a.createUserid", rulesForm.getCreateUserid(), QueryAction.EQUAL);
		}
		if(StringUtils.isNotEmpty(rulesForm.getCreateUsername())){ //创建人
			query.where("a.createUsername", rulesForm.getCreateUsername(), QueryAction.EQUAL);
		}
		if(StringUtils.isNotEmpty(rulesForm.getBusTypes())){ //业务类型
			query.where("a.busTypes", rulesForm.getBusTypes(), QueryAction.EQUAL);
		}
		
		//文件类型
		if(StringUtils.isNotEmpty(rulesForm.getTypes())){
			query.where("b.typeId",rulesForm.getTypes(),QueryAction.EQUAL);
		}
		query.where("a.isParent", RulesCommon.rulesFileUploadIsParent_Y, QueryAction.EQUAL );//父信息
		query.where("a.status", RulesCommon.rulesFileUploadStatus_Save, QueryAction.EQUAL );//已存
		query.orderBy("a.createTime", false);
		
		
		//查询相关文档
		ItemPage itemPage = enterpriseService.query(query, rulesForm);
		List<Object[]> list = new ArrayList<Object[]>();
		if(itemPage != null && itemPage.getItems() != null) {
			List<Object[]> objsList = (List<Object[]>) itemPage.getItems();
			for(Object[] objs : objsList) {
				Object[] os = new Object[2];
				RulesFileUpload rulesFileUpload = (RulesFileUpload) objs[0];
				List<RulesFileUpload> fileList = queryRulesFileList(rulesFileUpload.getFileId(),rulesFileUpload.getTypes());
				rulesFileUpload.setFileList(fileList);
				os[0] = rulesFileUpload;
				os[1] = objs[1];
				list.add(os);
			}
		}
		
		return new ItemPage(list, itemPage.getTotal(), itemPage.getPageIndex(),itemPage.getPageSize());
	}

	@Override
	public void updateFileTempId(String fileTempId, String fileId,String types, String busTypes) {
		// TODO Auto-generated method stub
		QueryBuilder query = new QueryBuilder(RulesFileUpload.class);
		query.where("rulesInfoid",fileTempId ,QueryAction.EQUAL);
		query.where("isParent" , RulesCommon.rulesFileUploadIsParent_N , QueryAction.EQUAL ); //非父信息
		List<RulesFileUpload> list = (List<RulesFileUpload>) enterpriseService.query(query, 0);
		if(list != null){
			for (RulesFileUpload file  : list) {
				file.setRulesInfoid(fileId);//关联父信息编号
				file.setTypes(types);//文档类型
				file.setBusTypes(busTypes);//业务类型
				enterpriseService.updateObject(file);
			}
		}
	
	}

	@Override
	public List<Object[]> searchUserSuggest(String userValue) {
    	userValue=userValue.toLowerCase();
        StringBuilder sql = new StringBuilder();
        sql.append("select t1.user_id,t1.user_name,t1.account,t.org_name from sys_user t1 left join sys_organization t on t1.organization_id=t.organization_id ")
        .append("where ( Lower(t1.user_name) like ? or Lower(t1.account) like ? or Lower(t1.mobile) like ? )");

        Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
        query.setString(0,"%"+userValue+"%");
        query.setString(1,"%"+userValue+"%");
        query.setString(2,"%"+userValue+"%");
        return query.list();
	}

	@Override
	public void delRulesFile(RulesFileUpload fileUpload,SysUser visitor) {
		if(fileUpload == null){
			return;
		}
		//20160411注释，不进行物理删除
		//					String filePath = fileUpload.getFilePath();
		//					File file = new File(filePath);
		//					file.delete();
		fileUpload.setStatus(RulesCommon.rulesFileUploadStatus_Del);
		fileUpload.setUpdateTime(new Date());
		
		if(null != visitor){
			fileUpload.setUpdateUserid(visitor.getUserId());
			fileUpload.setUpdateUsername(visitor.getUserName());
		}
		updateEntity(fileUpload);//逻辑删除
		
		//删除子文档，逻辑删除
		if(RulesCommon.rulesFileUploadIsParent_Y.equals(fileUpload.getIsParent())){
			List<RulesFileUpload> fileList = queryRulesFileList(fileUpload.getFileId(),fileUpload.getTypes());
			if(fileList != null && fileList.size() > 0){
				for (RulesFileUpload rulesFileUpload : fileList) {
					//循环调用
					delRulesFile(rulesFileUpload,visitor);
				}
			}
		}
		logger.info("附件删除操作……");
	}

	@Override
	public void addRulesType(RulesType rulesType, SysUser sysUser,String type) {
		RulesType rulesTypeOld = null;
		RulesType rulesTypeParent = null;
		if(StringUtils.isNotEmpty(rulesType.getTypeId())){
			rulesTypeOld = (RulesType) getEntity(RulesType.class, rulesType.getTypeId());
		}
		if(StringUtils.isNotEmpty(rulesType.getParentTypeId())){
			rulesTypeParent = (RulesType) getEntity(RulesType.class, rulesType.getParentTypeId());
		}
		//有父节点
		if(rulesTypeParent != null){
			rulesType.setIsRoot(RulesCommon.rulesTypeIsRoot_N);
			rulesType.setParentTypeName(rulesTypeParent.getTypeName());
		}else{
			//父节点
			rulesType.setParentTypeId(RulesCommon.rulesTypeRootParentId);
			rulesType.setIsRoot(RulesCommon.rulesTypeIsRoot_Y);
		}
		if(StringUtils.isNotEmpty(rulesType.getWorkDay())){
			rulesType.setWorkDay(rulesType.getWorkDay());
		}
		rulesType.setCreateTime(new Date());
		rulesType.setCreateUserid(sysUser.getUserId());
		rulesType.setCreateUsername(sysUser.getUserName());
		rulesType.setBusTypes(type);
		rulesType.setStatus(RulesCommon.rulesTypeStatus_Save);//保存
		if(rulesTypeOld != null){
			updateEntity(rulesType);
			//查询子节点并更新
			QueryBuilder query = new QueryBuilder(RulesType.class);
			query.where("parentTypeId",rulesType.getTypeId() ,QueryAction.EQUAL);
			List<RulesType> listChild = (List<RulesType>) enterpriseService.query(query, 0);
			if(listChild != null && listChild.size() > 0 ){
				//更新
				for (RulesType childType : listChild) {
					childType.setParentTypeName(rulesType.getTypeName());
					updateEntity(childType);
				}
			}
		}else{
			saveEntity(rulesType);
		}
		logger.info("新增业务类型……");
	}

	@Override
	public List<Object[]> queryChartSItemPage(String sql,RulesForm rulesForm) {
        
        QueryBuilder query = new QueryBuilder("");
		
        if(StringUtils.isNotEmpty(rulesForm.getRulesName())){ //制度名称
			query.where(" a.rules_name like '%"+rulesForm.getRulesName()+"%' ");
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesGrade())){//制度等级
			query.where(" a.rules_grade='"+rulesForm.getRulesGrade()+"' ");
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesTypeId())){//制度分类
			query.where(" a.rules_type_id='"+rulesForm.getRulesTypeId()+"' ");
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getLeadDepId())){ //牵头部门
			query.where(" a.lead_dep_id='"+rulesForm.getLeadDepId()+"' ");
		}
		//发布时间
		if( rulesForm.getHandelBeginDate() != null ){
			query.where(" a.update_time>to_date('"+DF_DATE_TIME.format(rulesForm.getHandelBeginDate())+"','yyyy-mm-dd HH24:MI:SS') ");
		}
		if( rulesForm.getHandelEndDate() != null ){
			query.where(" a.update_time<=to_date('"+DF_DATE_TIME.format(rulesForm.getHandelEndDate())+"','yyyy-mm-dd HH24:MI:SS') ");
		}
		if( rulesForm.getHandelBeginDate() != null || rulesForm.getHandelEndDate() != null ){
			rulesForm.setStatus(RulesCommon.rulesInfoStatus_FB);//查询已发布
		}
		
		
		//制度状态
		if(StringUtils.isNotEmpty(rulesForm.getStatus())){
			String[] status = rulesForm.getStatus().split(",");
			if(RulesCommon.rulesInfoStatus_QXZ.equals(rulesForm.getStatus())){
				status = new String[]{RulesCommon.rulesInfoStatus_SH,RulesCommon.rulesInfoStatus_FB,RulesCommon.rulesInfoStatus_FZ,RulesCommon.rulesInfoStatus_XD};
			}
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0;i < status.length; i++) {
				if(i==0){
					sb.append(" a.status='"+status[i]+"'");
				}else{
					sb.append(" or a.status='"+status[i]+"'");
				}
			}
			sb.append(")");
			query.where(sb.toString());
		}else{
			query.where(" (a.status='"+RulesCommon.rulesInfoStatus_SH+"' or a.status='"+RulesCommon.rulesInfoStatus_FB+"') ");//已发布或审核中
		}
		
		query.where(" a.status!='"+RulesCommon.rulesInfoStatus_CG+"' ");
		query.where(" a.status!='"+RulesCommon.rulesInfoStatus_DEL+"' ");
		
		String sql2 = query.getSQL();
		SQLQuery q2 = enterpriseService.getSessions().createSQLQuery(sql2);
		List<ICondition> values = query.getValues();
		for (int i = 0; i < values.size(); i++) {
			q2.setParameter(i, values.get(i).getValue() );
		}
		sql = sql.substring(0,sql.lastIndexOf("group by")) + " and " + q2.getQueryString() + sql.substring(sql.lastIndexOf("group by"));
		Query querySql = enterpriseService.getSessions().createSQLQuery(sql);
        return querySql.list();
	}

	@Override
	public List<Object[]> queryExportRulesInfoItemPage(RulesForm rulesForm,String roleStr) {
		Class<?>[] pojos = {RulesInfo.class,RulesType.class,SysOrganization.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.rulesTypeId=b.typeId ");
		query.where(" a.leadDepId=c.organizationId ");
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesName())){ //制度名称
			query.where("a.rulesName", rulesForm.getRulesName(), QueryAction.LIKE);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesGrade())){//制度等级
			query.where("a.rulesGrade",rulesForm.getRulesGrade(),QueryAction.EQUAL);
		}
		
		if(StringUtils.isNotEmpty(rulesForm.getRulesTypeId())){//制度分类
			query.where("a.rulesTypeId",rulesForm.getRulesTypeId(),QueryAction.EQUAL);
		}
		
		query.where("a.leadDepId", rulesForm.getLeadDepId(), QueryAction.EQUAL);
		//发布时间
		if( rulesForm.getHandelBeginDate() != null ){
			query.where("a.updateTime", rulesForm.getHandelBeginDate(), QueryAction.GT);
		}
		if( rulesForm.getHandelEndDate() != null ){
			query.where("a.updateTime", rulesForm.getHandelEndDate(), QueryAction.LE);
		}
		if( rulesForm.getHandelBeginDate() != null || rulesForm.getHandelEndDate() != null ){
			rulesForm.setStatus(RulesCommon.rulesInfoStatus_FB);//查询已发布
		}
		
		//普通用户
				if(RulesCommon.userRolesCommon.equals(roleStr)){
					query.where("a.status",RulesCommon.rulesInfoStatus_SH,QueryAction.NOEQUAL);
					//制度状态
					if(StringUtils.isNotEmpty(rulesForm.getStatus())){
						String[] status = rulesForm.getStatus().split(",");
						if(RulesCommon.rulesInfoStatus_QXZ.equals(rulesForm.getStatus())){
							status = new String[]{RulesCommon.rulesInfoStatus_FB,RulesCommon.rulesInfoStatus_FZ,RulesCommon.rulesInfoStatus_XD};
						}
						StringBuffer sb = new StringBuffer();
						sb.append("(");
						for (int i = 0;i < status.length; i++) {
							if(i==0){
								sb.append(" a.status='"+status[i]+"'");
							}else{
								sb.append(" or a.status='"+status[i]+"'");
							}
						}
						sb.append(")");
						query.where(sb.toString());
					}else{
						query.where(" ( a.status='"+RulesCommon.rulesInfoStatus_FB+"') ");//已发布
					}

					//部门制度员
				}else if(RulesCommon.userRolesDep.equals(roleStr)){
					//制度状态
					if(StringUtils.isNotEmpty(rulesForm.getStatus())){
						String[] status = rulesForm.getStatus().split(",");
						if(RulesCommon.rulesInfoStatus_QXZ.equals(rulesForm.getStatus())){
							status = new String[]{RulesCommon.rulesInfoStatus_SH,RulesCommon.rulesInfoStatus_FB,RulesCommon.rulesInfoStatus_FZ,RulesCommon.rulesInfoStatus_XD};
						}
						StringBuffer sb = new StringBuffer();
						sb.append("(");
						for (int i = 0;i < status.length; i++) {
							if(i==0){
								if(RulesCommon.rulesInfoStatus_SH.equals(status[i])){
									sb.append(" ( a.status='"+status[i]+"'  and a.leadDepId='"+rulesForm.getLeadDepId()+"') ");
								}else{
									sb.append(" a.status='"+status[i]+"'");
								}
							}else{
								if(RulesCommon.rulesInfoStatus_SH.equals(status[i])){
									sb.append(" or ( a.status='"+status[i]+"'  and a.leadDepId='"+rulesForm.getLeadDepId()+"') ");
								}else{
									sb.append(" or a.status='"+status[i]+"'");
								}
							}
						}
						sb.append(")");
						query.where(sb.toString());
					}else{
						query.where(" ( (a.status='"+RulesCommon.rulesInfoStatus_SH+"'  and a.leadDepId='"+rulesForm.getLeadDepId()+"') or ( a.status='"+RulesCommon.rulesInfoStatus_FB+"')) ");//已发布或审核中
					}
				}else if(RulesCommon.userRolesAdmin.equals(roleStr)){
					//制度状态
					if(StringUtils.isNotEmpty(rulesForm.getStatus())){
						String[] status = rulesForm.getStatus().split(",");
						if(RulesCommon.rulesInfoStatus_QXZ.equals(rulesForm.getStatus())){
							status = new String[]{RulesCommon.rulesInfoStatus_SH,RulesCommon.rulesInfoStatus_FB,RulesCommon.rulesInfoStatus_FZ,RulesCommon.rulesInfoStatus_XD};
						}
						StringBuffer sb = new StringBuffer();
						sb.append("(");
						for (int i = 0;i < status.length; i++) {
							if(i==0){
								sb.append(" a.status='"+status[i]+"'");
							}else{
								sb.append(" or a.status='"+status[i]+"'");
							}
						}
						sb.append(")");
						query.where(sb.toString());
					}else{
						query.where(" (a.status='"+RulesCommon.rulesInfoStatus_SH+"' or a.status='"+RulesCommon.rulesInfoStatus_FB+"') ");//已发布或审核中
					}
					
				}
		
		query.where("a.status", RulesCommon.rulesInfoStatus_CG, QueryAction.NOEQUAL);
		query.where("a.status", RulesCommon.rulesInfoStatus_DEL, QueryAction.NOEQUAL);
		
		query.orderBy("a.createTime", false);
		
		ItemPage itemPage = enterpriseService.query(query, rulesForm);
		List<Object[]> list = new ArrayList<Object[]>();
		if(itemPage != null && itemPage.getItems() != null) {
			List<Object[]> objsList = (List<Object[]>) itemPage.getItems();
			for(Object[] objs : objsList) {
				
				Object[] os = new Object[16];

				RulesInfo rulesinfo = (RulesInfo) objs[0];
				if(rulesForm.getSource() != null && rulesForm.getSource().equals("mxqd") && rulesForm.getSubBoxValue() != null && !rulesForm.getSubBoxValue().contains(rulesinfo.getRulesId())){
					continue;
				}
				SysOrganization sysOrganization = (SysOrganization) objs[2];
				String rulesId = rulesinfo.getRulesId();
				
				RulesType rulesType = (RulesType) getEntity(RulesType.class, rulesinfo.getRulesTypeId());
				
				//父信息
				List<RulesFileUpload> fileUploadPareent =  queryRulesFileUploadByRulesId(rulesId,RulesCommon.rulesFileUploadTypes_ZDXGWD );
				//流程信息查询
//				ItemPage  flowInfoList = queryRulesFlowInfoList(rulesId);//查询列表
				
				//创建时间
				RulesFlowInfo rulesFlowInfoCJ = queryRulesFlowInfoStatus(rulesId,RulesCommon.rulesFlowInfoFlowLink_CJ,null,null);
				//最新发布时间
				RulesFlowInfo rulesFlowInfoFB = queryRulesFlowInfoStatus(rulesId,RulesCommon.rulesFlowInfoFlowLink_FB,null,null);
				//已废止的
				RulesFlowInfo rulesFlowInfoFZ = queryRulesFlowInfoStatus(rulesId,RulesCommon.rulesFlowInfoFlowLink_FZ,null,null);
				
//				 {"制度编号","制度名称","制度分类","制度等级","状态","牵头部门","制度文件","制度附件","发布依据"
//						,"文件个数"
//						,"制度创建","时间","制度发布","时间","制度废止","时间"};
				os[0] = rulesinfo.getRulesCode();//制度编号
				os[1] = rulesinfo.getRulesName();//制度名称
				os[2] = rulesType.getParentTypeName() + "/" + rulesType.getTypeName();//制度分类
				os[3] = SysParamHelper.getSysParamByCode("RULES_GRADES_CONFIG",rulesinfo.getRulesGrade()).getParameterValue();//制度等级
				//状态
				
				os[4] = SysParamHelper.getSysParamByCode("RULES_STATUS",rulesinfo.getStatus()).getParameterValue();
				 
				os[5] = sysOrganization.getOrgName();//牵头部门
				//制度文件
				os[6]  = queryRulesFileList(rulesId,RulesCommon.rulesFileUploadTypes_ZDWJ );
				//制度附件
				os[7] = queryRulesFileList(rulesId,RulesCommon.rulesFileUploadTypes_ZDFJ ).size();
				//发布依据
				os[8] = queryRulesFileList(rulesId,RulesCommon.rulesFileUploadTypes_FBYJ ).size();
				
				//相关文档数

				int sumFileList = 0;
				for (RulesFileUpload rulesFileUpload : fileUploadPareent) {
					sumFileList += queryRulesFileList(rulesFileUpload.getFileId(),RulesCommon.rulesFileUploadTypes_ZDXGWD ).size();
				}
				os[9] = sumFileList;
				if(rulesFlowInfoCJ != null){
					os[10] = rulesFlowInfoCJ.getHandelUsername();
					os[11] = rulesFlowInfoCJ.getCreateTime();
				}
				if(rulesFlowInfoFB != null){
					os[12] = rulesFlowInfoFB.getHandelUsername();
					os[13] = rulesFlowInfoFB.getCreateTime();
				}
				if(rulesFlowInfoFZ != null){
					os[14] = rulesFlowInfoFZ.getHandelUsername();
					os[15] = rulesFlowInfoFZ.getCreateTime();
				}
				list.add(os);
			}
		}
		return list;
	}

	@Override
	public List<RulesFileUpload> queryDelRulesFileUpload() {

		QueryBuilder query = new QueryBuilder(RulesFileUpload.class);
		query.where("status", RulesCommon.rulesFileUploadStatus_Del, QueryAction.EQUAL );//shanchu 
		return (List<RulesFileUpload>) enterpriseService.query(query, 0);
	
	}

	@Override
	public void delRulesType(String typeId) {
		RulesType rulesType = (RulesType) getEntity(RulesType.class, typeId);
		//保存才能删除
		if(rulesType != null && (RulesCommon.rulesTypeStatus_Save.equals(rulesType.getStatus()))){
			rulesType.setStatus(RulesCommon.rulesTypeStatus_Del);//删除状态
			updateEntity(rulesType);//逻辑删除
		}
		//删除子节点
		List<RulesType> list = queryAllByBusType(null,typeId);
		if(list != null){
			//遍历删除
			for (RulesType rt : list) {
				delRulesType(rt.getTypeId());
			}
		}
	}

	@Override
	public RulesInfo queryRulesInfoByName(String rulesName,String... status) {
		QueryBuilder builder=new QueryBuilder(RulesInfo.class);
		builder.where("rulesName",rulesName,QueryAction.EQUAL);
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0;i < status.length; i++) {
			if(i==0){
				sb.append(" status='"+status[i]+"'");
			}else{
				sb.append(" or status='"+status[i]+"'");
			}
		}
		sb.append(")");
		builder.where(sb.toString());
		builder.orderBy("createTime", false);
		List<RulesInfo> list = (List<RulesInfo>)enterpriseService.query(builder,0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public SysOrganization queryParentOrg(String organizationId) {
		SysOrganization  result = (SysOrganization) getEntity(SysOrganization.class, organizationId);
		if(result != null && RulesCommon.sysorganizationrootID.equals(result.getParentId())){
			return result;
		}else if(result != null && StringUtils.isNotEmpty(result.getParentId())){
			return queryParentOrg(result.getParentId());
		}
		return null;
	}

	@Override
	public RulesType queryWoryDay(String id) {
		QueryBuilder query = new QueryBuilder(RulesType.class);
		query.where("typeId",id);
		List<RulesType> list =  (List<RulesType>) enterpriseService.query(query, 0);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public SysRoleUser getRoleUsersByUserId(String roleId, String userId) {
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("roleId", roleId, QueryAction.EQUAL);
		query.where("userId", userId, QueryAction.EQUAL);
		List<SysRoleUser> list = (List<SysRoleUser>)enterpriseService.query(query, 0);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

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
	@Override
	public void sendManagetask(int action, SysUser sysUser, String subject,List<String> taskId, int taskType, String taskURL, List<SysUser> toUserList,
				XMLGregorianCalendar warningTime, String bustypeId,String handelStatus){
		
			logger.info("调用待办待阅任务服务接口start……");
			if(action == RulesCommon.ACTION_ADD){
				//上一处理人
				if(sysUser == null || !StringUtils.isNotEmpty(sysUser.getAccount())){
					logger.info("处理类型为新增，上一处理人不能为空……");
					return;
				}
				//任务所有者
				if(toUserList == null || toUserList.size() == 0){
					logger.info("处理类型为新增，任务所有者不能为空……");
					return;
				}
				//任务主题
				if(!StringUtils.isNotEmpty(subject)){
					logger.info("处理类型为新增，任务主题不能为空……");
					return;
				}
				//处理任务的URL
				if(!StringUtils.isNotEmpty(taskURL)){
					logger.info("处理类型为新增，处理任务的URL不能为空……");
					return;
				}
			}
			
			if(action == RulesCommon.ACTION_DONE){
				//任务ID
				if(taskId == null || taskId.size() == 0){
					logger.info("处理类型为已完成，任务ID不能为空……");
					return;
				}
				//任务所有者
				if(toUserList == null || toUserList.size() == 0){
					logger.info("处理类型为已完成，任务所有者不能为空……");
					return;
				}
			}
			
			List<SBIAPManagerTaskToDoSrvInputItem> inputList = new ArrayList<SBIAPManagerTaskToDoSrvInputItem>();
			for(int i=0;i<toUserList.size();i++){
				//任务所有者
				SysUser toUser=(SysUser)toUserList.get(i);
				//请求参数
				SBIAPManagerTaskToDoSrvInputItem sBIAPManagerTaskToDoSrvInputItem = new SBIAPManagerTaskToDoSrvInputItem();
				sBIAPManagerTaskToDoSrvInputItem.setACTION(new BigDecimal(action));//处理类型，新增
				sBIAPManagerTaskToDoSrvInputItem.setFROMEMPLOYEEID(sysUser.getAccount());//上一处理人"0121021483"ztt
				sBIAPManagerTaskToDoSrvInputItem.setCREATETIME(DateUtils.dateToXmlDate(new Date()));//任务创建时间
				sBIAPManagerTaskToDoSrvInputItem.setSUBJECT(subject);//任务主题
				if(taskId != null){
					sBIAPManagerTaskToDoSrvInputItem.setTASKID(taskId.get(i));//任务ID,新增时可为空
				}
				sBIAPManagerTaskToDoSrvInputItem.setTASKTYPE(new BigDecimal(taskType));//任务类型,待办
				sBIAPManagerTaskToDoSrvInputItem.setTASKURL(taskURL);//处理任务的URL
				if(StringUtils.isNotEmpty(toUser.getAccount())){
					sBIAPManagerTaskToDoSrvInputItem.setTOEMPLOYEEID(toUser.getAccount());//任务所有者
				}
				sBIAPManagerTaskToDoSrvInputItem.setWARNINGTIME(warningTime);//任务提醒时间
				
				inputList.add(sBIAPManagerTaskToDoSrvInputItem);
			}
		
		//任务列表
		SBIAPManagerTaskToDoSrvInputCollection sBIAPManagerTaskToDoSrvInputCollection = new SBIAPManagerTaskToDoSrvInputCollection();
		sBIAPManagerTaskToDoSrvInputCollection.getSBIAPManagerTaskToDoSrvInputItem().addAll(inputList);//添加list
		//请求参数
		SBIAPManagerTaskToDoSrvRequest sBIAPManagerTaskToDoSrvRequest = new SBIAPManagerTaskToDoSrvRequest();
		//设置任务列表
		sBIAPManagerTaskToDoSrvRequest.setSBIAPManagerTaskToDoSrvInputCollection(sBIAPManagerTaskToDoSrvInputCollection);
		//请求并得到响应结果
		SBIAPManagerTaskToDoSrvResponse sBIAPManagerTaskToDoSrvResponse = manageTaskClient.manageTask(sBIAPManagerTaskToDoSrvRequest);
		
		if(sBIAPManagerTaskToDoSrvResponse == null){
			return;
		}
		
		//返回结果列表
				SBIAPManagerTaskToDoSrvOutputCollection sBIAPManagerTaskToDoSrvOutputCollection = sBIAPManagerTaskToDoSrvResponse.getSBIAPManagerTaskToDoSrvOutputCollection();
				List<SBIAPManagerTaskToDoSrvOutputItem> outputList = sBIAPManagerTaskToDoSrvOutputCollection.getSBIAPManagerTaskToDoSrvOutputItem();
				//保存接口调用记录列表
				List<InterfaceManagetaskRecords> saveList = new ArrayList<InterfaceManagetaskRecords>();
				int length = inputList.size();
				for (int i = 0; i < length; i++) {
					SBIAPManagerTaskToDoSrvInputItem sin = inputList.get(i);
					SBIAPManagerTaskToDoSrvOutputItem sout = outputList.get(i);
					InterfaceManagetaskRecords inmr = new InterfaceManagetaskRecords();
					inmr.setBustypeId(bustypeId);//业务记录ID
					inmr.setCreateTime(new Date());
					inmr.setCreateUserid(sysUser.getUserId());
					inmr.setCreateUsername(sysUser.getUserName());
					inmr.setHandelStatus(handelStatus);//处理状态
					//请求体
					inmr.setMsgHeader(sBIAPManagerTaskToDoSrvRequest.getMsgHeader().toString());//请求实体
					inmr.setTaskId(sin.getTASKID());//任务ID
					inmr.setFromEmployeeId(sin.getFROMEMPLOYEEID());//上一任务处理人
					inmr.setToEmployeeId(sin.getTOEMPLOYEEID());//任务处理人
					inmr.setSubject(sin.getSUBJECT());//任务主题
					inmr.setTaskUrl(sin.getTASKURL());//任务URL
					if(sin.getCREATETIME() != null){
						inmr.setCreateTimeStr(sin.getCREATETIME().toString());//创建时间
					}
					if(sin.getWARNINGTIME() != null){
						inmr.setWarningTimeStr(sin.getWARNINGTIME().toString());//提醒时间
					}
					inmr.setTaskType(sin.getTASKTYPE());//任务类型
					inmr.setAction(sin.getACTION());//处理类型
					//响应
					
					inmr.setServiceFlag(sBIAPManagerTaskToDoSrvResponse.getSERVICEFLAG());
					inmr.setServiceMessage(sBIAPManagerTaskToDoSrvResponse.getSERVICEMESSAGE());
					inmr.setInstanceId(sBIAPManagerTaskToDoSrvResponse.getINSTANCEID());
					inmr.setTotalRecord(sBIAPManagerTaskToDoSrvResponse.getTOTALRECORD());
					inmr.setTotalPage(sBIAPManagerTaskToDoSrvResponse.getTOTALPAGE());
					inmr.setPageSize(sBIAPManagerTaskToDoSrvResponse.getPAGESIZE());
					inmr.setCurrentPage(sBIAPManagerTaskToDoSrvResponse.getCURRENTPAGE());
					inmr.setTaskIdRetrun(sout.getTASKID());
					inmr.setReturns(sout.isRETURNS().toString());
					inmr.setMsgerror(sout.getMSGERROR());
					saveList.add(inmr);
					
					logger.info("{任务Id："+sout.getTASKID()+";推送结果："+sout.isRETURNS().toString()+";推送消息："+sout.getMSGERROR()+"}");
				}
		enterpriseService.save(saveList);
		logger.info("调用接口记录保存成功");
		if("Y".equals(sBIAPManagerTaskToDoSrvResponse.getSERVICEFLAG())){
			logger.info("调用待办待阅任务服务接口成功");
		}else{
			logger.info("调用待办待阅任务服务接口失败："+sBIAPManagerTaskToDoSrvResponse.getSERVICEMESSAGE());
		}
		logger.info("调用待办待阅任务服务接口end……");
	}

	@Override
	public String setUserRoles(HttpServletRequest request,RulesForm rulesForm) {
		SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
		String roleStr = RulesCommon.userRolesCommon;
		if(sysUser != null){
			//制度管理员
			SysRole roles = querySysRoleByCode(SysParamHelper.getSysParamByCode(RulesCommon.DICT_TYPE, RulesCommon.RULESER_ADMIN).getParameterValue());
			SysRoleUser rolesUser = getRoleUsersByUserId(roles.getRoleId(),sysUser.getUserId());
			if(rolesUser != null){
				//制度管理员角色
				request.setAttribute("userRoles", RulesCommon.userRolesAdmin);
				roleStr = RulesCommon.userRolesAdmin;
			}
			
			if(rolesUser == null){
				roles = querySysRoleByCode(SysParamHelper.getSysParamByCode(RulesCommon.DICT_TYPE, RulesCommon.RULESER_DEP).getParameterValue());
				rolesUser = getRoleUsersByUserId(roles.getRoleId(),sysUser.getUserId());
				if(rolesUser != null){
					//部门制度员
					request.setAttribute("userRoles", RulesCommon.userRolesDep);
					roleStr = RulesCommon.userRolesDep;
					//所有已发布,本部门正在审核中
					SysOrganization parentOrg = queryParentOrg(sysUser.getOrganizationId());
					rulesForm.setLeadDepId(parentOrg.getOrganizationId());
//					rulesForm.setCreateUserid(sysUser.getUserId());
				}else{
					//普通用户
					request.setAttribute("userRoles", RulesCommon.userRolesCommon);
					roleStr = RulesCommon.userRolesCommon;
				}
			}
			
		}		
		return roleStr;
	}

	@Override
	public List<SysUser> getUserByMenu(String menuCode) {
		QueryBuilder query = new QueryBuilder(
				" SysUser s,SysRoleUser t,SysRole r ,SysRolePrivilges u");
		query.where("t.userId=s.userId");
		query.where("t.roleId=r.roleId");
		query.where("t.roleId=u.roleId");
		query.where("u.moduleCode", menuCode, QueryAction.EQUAL);
		String hh=query.getSQL();
		List<?> list = enterpriseService.query(query, 0);
		if (list != null) {
			List<SysUser> userList = new ArrayList<SysUser>();
			for (Object object : list)
				if (object != null) {
					Object[] objs = (Object[]) object;
					SysUser temp=(SysUser) objs[0];
					if(!userList.contains(temp)){
						userList.add(temp);
					}
				}
			return userList;
		}
		return null;
	}

	@Override
	public String checkCostType(String typeName) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select e.type_name from rules_type e where e.type_name='"+typeName+"' and e.status='1'  and e.bus_types='BZ'");
		Query qeury =  enterpriseService.getSessions().createSQLQuery(sb.toString());
		String result = (String) qeury.uniqueResult();
		return result;
	}

	@Override
	public List<YuanTableColumnManage> queryManageByTableName(int length, String tableName) {
		QueryBuilder query = new QueryBuilder(YuanTableColumnManage.class);
		query.where("tableId",tableName,QueryAction.EQUAL);
		query.orderBy("showOrder");
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
	
}
