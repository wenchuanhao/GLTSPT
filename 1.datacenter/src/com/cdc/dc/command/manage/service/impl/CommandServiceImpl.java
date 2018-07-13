package com.cdc.dc.command.manage.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import model.sys.entity.SysCount;
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
import org.trustel.common.ConstantDefine;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.ServiceException;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.common.BusTypes;
import com.cdc.common.properties.DCConfig;
import com.cdc.dc.command.manage.common.CommandCommon;
import com.cdc.dc.command.manage.form.CommandForm;
import com.cdc.dc.command.manage.model.CommandFlows;
import com.cdc.dc.command.manage.model.CommandFolder;
import com.cdc.dc.command.manage.model.CommandInfo;
import com.cdc.dc.command.manage.model.CommandMaterials;
import com.cdc.dc.command.manage.model.CommandMoborg;
import com.cdc.dc.command.manage.model.CommandQks;
import com.cdc.dc.command.manage.model.CommandSupportorg;
import com.cdc.dc.command.manage.model.CommandWorks;
import com.cdc.dc.command.manage.service.ICommandService;
import com.cdc.dc.datacenter.task.model.DcServiceJob;
import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.QrcodeUtil;
import com.trustel.common.ItemPage;

public class CommandServiceImpl implements ICommandService {

	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
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
	public ItemPage findCommand(CommandForm command) {
		QueryBuilder query = new QueryBuilder(CommandInfo.class);
		query.where("commandType",command.getCommandType(),QueryAction.EQUAL);
		query.where("commandNum",command.getCommandNum(),QueryAction.LIKE);
		query.where("launchUserid",command.getLaunchUserid(),QueryAction.EQUAL);
		query.where("launchUsername",command.getLaunchUsername(),QueryAction.LIKE);
		query.where("commandStatus",command.getCommandStatus(),QueryAction.EQUAL);
		query.where("orgId",command.getOrgId(),QueryAction.EQUAL);
		query.where("orgName",command.getOrgName(),QueryAction.LIKE);
		query.where("contractCode",command.getContractCode(),QueryAction.EQUAL);
		query.where("contractName",command.getContractName(),QueryAction.LIKE);
		//query.where("digEst",command.getDigEst(),QueryAction.EQUAL);
		
		//根据ID查找内容
		if(StringUtils.isNotEmpty(command.getCommandId())){
			query.where(" commandId in ("+command.getCommandId()+") ");
		}
		
		//时间区间
		if( command.getLaunchBeginTime() != null ){
			query.where("launchTime", command.getLaunchBeginTime(), QueryAction.GT);
		}
		if( command.getLaunchEndTime() != null ){
			Calendar cd = Calendar.getInstance();
            cd.setTime(command.getLaunchEndTime());
            cd.add(Calendar.DATE, 1);//增加一天
			query.where("launchTime", cd.getTime(), QueryAction.LE);
		}
		query.orderBy("launchTime desc");
		return enterpriseService.query(query, command);
	}

	@Override
	public List<Object[]> searchSupportorg(String supportorgName) {
		String sql = "select t.supportorg_id,t.supportorg_name,t.update_time from COMMAND_SUPPORTORG t where t.supportorg_name like ? and t.status='1' order by t.update_time desc ";
		Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
        query.setString(0,"%"+supportorgName+"%");
		return query.list();
	}

	@Override
	public List<Object[]> searchProjectByCode(String code) {
		code = code.toLowerCase().trim();
		//子项目合同
		String sql3 = "select * from ( select t.id,tt.COLUMN_01,t.column_01 as column_02,t.column_03,t.COLUMN_20,t.CREATE_USER_ID,t.COLUMN_11,t.COLUMN_14 from GC_ZXMHT t left join GC_ZXM tt on t.COLUMN_04=tt.id " +
				" where ( Lower(t.column_01) like ? or Lower(t.column_03) like ? ) ) where rownum <= 20";
		Query query3 = enterpriseService.getSessions().createSQLQuery(sql3.toString());
        query3.setString(0,"%"+code+"%");
        query3.setString(1,"%"+code+"%");
		return query3.list();
	}
	
	@Override
	public List<Object[]> searchProByCode(String code) {
		code = code.toLowerCase().trim();
		//子项目合同
		String sql = "select * from (select t.id, t.column_02, t.column_03 from gc_tzbm t where (Lower(t.column_02) like ? or  Lower(t.column_03) like ?))  where rownum <= 20";
		Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
		query.setString(0,"%"+code+"%");
		query.setString(1,"%"+code+"%");
		return query.list();
	}

	@Override
	public void saveOrUpdateCommandWorks(CommandWorks cw, SysUser sysUser) {
		//编辑或保存
		String id = cw.getWorksId();
		if (id == null || "".equals(id.trim())) {
			cw.setWorksId(UUID.randomUUID().toString());
		}
		CommandWorks oldcw = (CommandWorks) getEntity(CommandWorks.class, cw.getWorksId());
		if(oldcw == null){
			cw.setCreateTime(new Date());
			saveEntity(cw);
		}else{
			cw.setCreateTime(oldcw.getCreateTime());
			updateEntity(cw);
		}
		
		CommandForm cdf = new CommandForm();
		cdf.setWorksId(cw.getWorksId());
		cdf.setWorksType(cw.getWorksType());
		cdf.setConstructionId(cw.getConstructionId());
		cdf.setConstructionName(cw.getConstructionName());
		cdf.setContractCode(cw.getContractCode());
		cdf.setContractName(cw.getContractName());
		cdf.setDigEst(cw.getDigEst());
		//传参
		saveOrUpdateCommandInfo(cdf,sysUser);
		
		//若支撑单位名称不存在，则新增
//		saveOrNotSupportorg(cw.getSupportorgName(), sysUser);
	}
	
	@Override
	public void saveOrUpdateCommandMaterials(CommandMaterials cw, SysUser sysUser) {

		//编辑或保存
		String id = cw.getWorksId();
		if (id == null || "".equals(id.trim())) {
			cw.setWorksId(UUID.randomUUID().toString());
		}
		CommandMaterials oldcw = (CommandMaterials) getEntity(CommandMaterials.class, cw.getWorksId());
		if(oldcw == null){
			cw.setCreateTime(new Date());
			saveEntity(cw);
		}else{
			cw.setCreateTime(oldcw.getCreateTime());
			updateEntity(cw);
		}
		CommandForm cdf = new CommandForm();
		cdf.setWorksId(cw.getWorksId());
		cdf.setWorksType(cw.getWorksType());
		cdf.setConstructionId(cw.getConstructionId());
		cdf.setConstructionName(cw.getConstructionName());
		cdf.setContractCode(cw.getContractCode());
		cdf.setContractName(cw.getContractName());
		//传参
		saveOrUpdateCommandInfo(cdf,sysUser);
	}
	
	@Override
	public void saveOrUpdateCommandQks(CommandQks cw, SysUser sysUser) {
		
		//编辑或保存
		String id = cw.getWorksId();
		if (id == null || "".equals(id.trim())) {
			cw.setWorksId(UUID.randomUUID().toString());
		}
		CommandQks oldcw = (CommandQks) getEntity(CommandQks.class, cw.getWorksId());
		if(oldcw == null){
			cw.setCreateTime(new Date());
			saveEntity(cw);
		}else{
			cw.setCreateTime(oldcw.getCreateTime());
			updateEntity(cw);
		}
		CommandForm cdf = new CommandForm();
		cdf.setWorksId(cw.getWorksId());
		cdf.setWorksType(cw.getWorksType());
		cdf.setConstructionId(cw.getConstructionId());
		cdf.setConstructionName(cw.getConstructionName());
		cdf.setContractCode(cw.getContractCode());
		cdf.setContractName(cw.getContractName());
		
		//传参
		saveOrUpdateCommandInfo(cdf,sysUser);
	}
	
	public void saveOrUpdateCommandInfo(CommandForm cw,SysUser sysUser){
		//查询管理指令信息
		CommandInfo old_cdinfo = queryCommandInfoByForid(cw.getWorksId());
		
		if (old_cdinfo == null) {
			
			CommandInfo cdinfo = new CommandInfo();
			
			cdinfo.setCommandForid(cw.getWorksId());//文件（指令）ID（外键）
			cdinfo.setOrgId(cw.getConstructionId());//单位ID
			cdinfo.setOrgName(cw.getConstructionName());//单位（施工/乙方/来文）名称
			cdinfo.setContractCode(cw.getContractCode());//合同编号
			cdinfo.setContractName(cw.getContractName());//合同名称
			cdinfo.setUpdateTime(new Date());
			cdinfo.setCommandType(cw.getWorksType());//文件(指令)类型
			//根据合同编号获取流水号
			String format = cw.getContractCode();//"cw.getWorksType()";
			if(CommandCommon.commandType_A1.equals(cw.getWorksType()) || CommandCommon.commandType_A2.equals(cw.getWorksType())){
				format += "-A";
			}
			if(CommandCommon.commandType_B.equals(cw.getWorksType())){
				format += "-B";
			}
			if(CommandCommon.commandType_C.equals(cw.getWorksType())){
				format += "-C";
			}
			if(CommandCommon.commandType_SGQK.equals(cw.getWorksType()) 
					|| CommandCommon.commandType_JCQK.equals(cw.getWorksType())
					|| CommandCommon.commandType_JLQK.equals(cw.getWorksType())
					|| CommandCommon.commandType_SJQK.equals(cw.getWorksType())
					|| CommandCommon.commandType_CLQK.equals(cw.getWorksType())){
				format += "-QK";
			}
			if(CommandCommon.commandType_GCZL.equals(cw.getWorksType())){
				format += "-GCZL";
			}
			//流水号
			String serial_number = getIds(format,BusTypes.busTypes_ZL);
			String commandNum = format;
			//合同编号（GMGD-XXX)"+"-"+指令类型+“-”+三位数字流水号 
			if(CommandCommon.commandType_A1.equals(cw.getWorksType())){
				commandNum += "1-" + serial_number;
			}else if(CommandCommon.commandType_A2.equals(cw.getWorksType())){
				commandNum += "2-" + serial_number;
			}else{
				commandNum += "-" + serial_number;
			}
			cdinfo.setCommandNum(commandNum);//文件(指令)编号
			cdinfo.setLaunchTime(new Date());//发起时间
			cdinfo.setLaunchUserid(sysUser.getUserId());//发起人ID
			cdinfo.setLaunchUsername(sysUser.getUserName());//发起人名称
			cdinfo.setCommandStatus(CommandCommon.commandStatus_WLZ);//未流转 //状态
			saveEntity(cdinfo);
			
	    	saveInfoPic(cdinfo);

			//第一次新增作为发起人
			createCommandFlows(cdinfo, sysUser, sysUser.getMobile());//记录流程
		}else{
			old_cdinfo.setCommandForid(cw.getWorksId());//文件（指令）ID（外键）
			old_cdinfo.setOrgId(cw.getConstructionId());//单位ID
			old_cdinfo.setOrgName(cw.getConstructionName());//单位（施工/乙方/来文）名称
			old_cdinfo.setContractCode(cw.getContractCode());//合同编号
			old_cdinfo.setContractName(cw.getContractName());//合同名称
			old_cdinfo.setUpdateTime(new Date());
			old_cdinfo.setCommandType(cw.getWorksType());//文件(指令)类型
			//old_cdinfo.setDigEst(cw.getDigEst());//摘要
			updateEntity(old_cdinfo);
		}
	}
	private void saveInfoPic(CommandInfo cdinfo) {
		String path = DCConfig.getProperty("COMMANDQR_DIR");
    	String basePath = (StringUtils.isEmpty(path) ? "http://221.176.36.63/command/h5/doMobileLogin?id=" : path)  + cdinfo.getCommandId();
    	String imgPath = DCConfig.getProperty("COMMANDQR_PIC");
    	if(StringUtils.isEmpty(imgPath)){
    		imgPath = "/webapplication/8080/glts/SRMC/command/images";
    	}
    	imgPath += ConstantDefine.FILE_SEPARATOR + cdinfo.getCommandNum() + ".jpg";
    	try {
			QrcodeUtil.createImage(basePath, 392, 392,imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	cdinfo.setQrDir(imgPath);
    	
////		byte[] buf = new byte[in.available()];
////		in.read(buf);
//		cdinfo.setQrByte(Hibernate.createBlob(in));
	}

	@Override
	public CommandInfo queryCommandInfoByForid(String forid){
		QueryBuilder query = new QueryBuilder(CommandInfo.class);
		query.where("commandForid",forid,QueryAction.EQUAL);
		query.orderBy("launchTime desc");
		List<CommandInfo> list = (List<CommandInfo>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取流水号
	 * @param formatdate 编码
	 * @param type  类型
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
	public void delCommandInfo(CommandInfo cdinfo, SysUser sysUser) {
		cdinfo.setCommandStatus(CommandCommon.commandStatus_YZF);//作废状态
		enterpriseService.updateObject(cdinfo);//作废
		createCommandFlows(cdinfo, sysUser, sysUser.getMobile());//记录流程
	}
	
	public void createCommandFlows(CommandInfo cdinfo, SysUser sysUser,String mobile){
		CommandFlows cdf = new CommandFlows();
		cdf.setCommandInfoid(cdinfo.getCommandId());//指令编号
		cdf.setFlowStatus(cdinfo.getCommandStatus());//流转状态(发起/接收/归档/作废)
		if(StringUtils.isNotEmpty(sysUser.getCommandRoleId())){
			cdf.setFlowRoleid(sysUser.getCommandRoleId());//流转角色/组织
			cdf.setFlowRolename(sysUser.getCommandRoleName());//流转角色/组织名称
		}
		cdf.setFlowUserid(sysUser.getUserId());//流转人id
		cdf.setFlowUsername(sysUser.getUserName());//流转人名称
		cdf.setFlowMobile(mobile);//流转人号码
		cdf.setFlowTime(new Date());
		
		CommandFlows leastCdf = queryLeastCommandFlows(cdinfo.getCommandId());

		enterpriseService.save(cdf);//新增
		if(leastCdf != null){
			leastCdf.setToFlowId(cdf.getFlowId());//下一流转id
			enterpriseService.updateObject(leastCdf);//更新
			
			cdf.setFromFlowId(leastCdf.getFlowId());//上一流转id
			enterpriseService.updateObject(cdf);//更新
		}
		
	}
	
	public CommandFlows queryLeastCommandFlows(String commandInfoid){
		if(!StringUtils.isNotEmpty(commandInfoid)){
			return null;
		}
		QueryBuilder builder= new QueryBuilder(CommandFlows.class);
		builder.where("commandInfoid",commandInfoid,QueryAction.EQUAL);
		builder.orderBy("flowTime", false);
		List<CommandFlows> list = (List<CommandFlows>)enterpriseService.query(builder,0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<CommandFlows> queryCommandFlowsById(String id) {
		QueryBuilder builder= new QueryBuilder(CommandFlows.class);
		builder.where("commandInfoid",id,QueryAction.EQUAL);
		builder.orderBy("flowTime", false);
		return (List<CommandFlows>)enterpriseService.query(builder,0);
	}

	/**
	 * 归档信息保存
	 */
	@Override
	public void setCommandFolder(CommandFolder cdfolder, SysUser sysUser) {
		if(StringUtils.isEmpty(cdfolder.getCommandInfoid())){
			return;
		}
		//查询指令详情
		CommandInfo cdinfo = (CommandInfo)getEntity(CommandInfo.class, cdfolder.getCommandInfoid());
		if(cdinfo == null){
			return;
		}
		//流转中 及 撤销归档的才可归档
		if(CommandCommon.commandStatus_LZZ.equals(cdinfo.getCommandStatus()) || CommandCommon.commandStatus_NGD.equals(cdinfo.getCommandStatus())){
			//归档信息保存
			cdfolder.setFolderTime(new Date());
			cdfolder.setFolderUserid(sysUser.getUserId());
			cdfolder.setFolderUsername(sysUser.getUserName());
			cdfolder.setStatus(CommandCommon.folderStatus_S);
			enterpriseService.save(cdfolder);
			//归档流程记录
			cdinfo.setCommandStatus(CommandCommon.commandStatus_YGD);//归档状态
			enterpriseService.updateObject(cdinfo);//归档
			createCommandFlows(cdinfo, sysUser, sysUser.getMobile());//记录流程
			//已归档，更新信息
		}else if(CommandCommon.commandStatus_YGD.equals(cdinfo.getCommandStatus())){
			//归档信息保存
			cdfolder.setFolderTime(new Date());
			cdfolder.setFolderUserid(sysUser.getUserId());
			cdfolder.setFolderUsername(sysUser.getUserName());
			cdfolder.setStatus(CommandCommon.folderStatus_S);
			enterpriseService.updateObject(cdfolder);
			//归档流程记录
			cdinfo.setCommandStatus(CommandCommon.commandStatus_YGD);//归档状态
			enterpriseService.updateObject(cdinfo);//归档
			createCommandFlows(cdinfo, sysUser, sysUser.getMobile());//记录流程
		}
		
	}

	@Override
	public CommandFolder queryLeastCommandFolderById(String commandId, String status) {
		if(!StringUtils.isNotEmpty(commandId)){
			return null;
		}
		QueryBuilder builder= new QueryBuilder(CommandFolder.class);
		builder.where("commandInfoid",commandId,QueryAction.EQUAL);
		builder.where("status",status,QueryAction.EQUAL);
		builder.orderBy("folderTime", false);
		List<CommandFolder> list = (List<CommandFolder>)enterpriseService.query(builder,0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 撤销归档
	 */
	@Override
	public void undoCommandFolder(CommandInfo cdinfo, SysUser sysUser) {
		if(cdinfo != null){
			CommandFolder cdfolder = queryLeastCommandFolderById(cdinfo.getCommandId(),CommandCommon.folderStatus_S);
			if(cdfolder != null){
				cdfolder.setFolderTime(new Date());
				cdfolder.setFolderUserid(sysUser.getUserId());
				cdfolder.setFolderUsername(sysUser.getUserName());
				cdfolder.setStatus(CommandCommon.folderStatus_D);//撤销归档状态
				enterpriseService.updateObject(cdfolder);
				
			}
			cdinfo.setCommandStatus(CommandCommon.commandStatus_NGD);//撤销归档状态
			enterpriseService.updateObject(cdinfo);//归档
			//撤销状态
			createCommandFlows(cdinfo, sysUser, sysUser.getMobile());//记录流程
		}
	}

	@Override
	public List<RulesFileUpload> queryRulesFileUploadById(String folderId) {
		QueryBuilder query = new QueryBuilder(RulesFileUpload.class);
		query.where("rulesInfoid", folderId, QueryAction.EQUAL);//制度编号
		query.where("status", RulesCommon.rulesFileUploadStatus_Save, QueryAction.EQUAL );//已存
		query.orderBy("createTime desc");
		return (List<RulesFileUpload>) enterpriseService.query(query, 0);
	}

	@Override
	public List<SysRole> getCommandRoles(SysUser sysUser) {
		if(sysUser == null){
			return null;
		}
		String roleCodes = "";
		List<SysParameter>  list = SysParamHelper.getSysParamListByParamTypeCode(CommandCommon.COMMAND_DICT_TYPE);
		if(list != null && list.size() > 0 ){
			for (int i = 0; i < list.size(); i++) {
				if( i == 0){
					roleCodes += "'"+ list.get(i).getParameterValue() +"'";
				}else{
					roleCodes += ",'"+ list.get(i).getParameterValue() +"'";
				}
			}
		}
		//获取指令有关的角色
		Class<?>[] pojos = {SysRole.class,SysRoleUser.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.roleId=b.roleId ");
		query.where(" a.roleCode in (" + roleCodes + ")");
		query.where(" b.userId", sysUser.getUserId(), QueryAction.EQUAL);
		query.orderBy(" a.createTime desc");
		List<SysRole> result = new ArrayList<SysRole>();
		List<?> srSru = enterpriseService.query(query, 0);
		if(srSru != null && srSru.size() > 0 ){
			for (int i = 0; i < srSru.size(); i++) {
				Object[] obj = (Object[]) srSru.get(i);
				result.add((SysRole) obj[0]);
			}
		}
		return result;
		
	}
	
	/**
	 * 生成随机码
	 * @return
	 */
	private static String generateRamdCode(){
		int k = 6;
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for(int i = 0;i < k;i++){
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}

	@Override
	public SysUser querySysUserByMobile(String mobile) {
		if(StringUtils.isEmpty(mobile)){
			return null;
		}
		
		//查询系统
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("mobile", mobile, QueryAction.EQUAL);//制度编号
		query.orderBy("createTime desc");
		List<SysUser> list = (List<SysUser>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public void saveOrNotSupportorg(String supportorgName, SysUser sysUser){
		String sql = "select t.supportorg_id,t.supportorg_name,t.update_time from COMMAND_SUPPORTORG t where t.supportorg_name=? and t.status='1' order by t.update_time desc ";
		Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
        query.setString(0,supportorgName);
        List<Object[]> list = query.list();
		//新建支撑单位
		if(list == null || list.size() == 0){
			CommandSupportorg supportorg = new CommandSupportorg();
			supportorg.setCreateTime(new Date());
    		supportorg.setUpdateTime(new Date());
    		supportorg.setUpdateUsername(sysUser.getUserName());
    		supportorg.setUpdateUserid(sysUser.getUserId());
    		supportorg.setStatus(CommandSupportorg.supportorg_Save);
			supportorg.setSupportorgName(supportorgName);
    		enterpriseService.save(supportorg);
		}
	}

	@Override
	public CommandMoborg queryCommandMoborgByMobile(String mobile, String orgId) {
		if(StringUtils.isEmpty(mobile)){
			return null;
		}
		QueryBuilder query = new QueryBuilder(CommandMoborg.class);
		query.where("mobile", mobile, QueryAction.EQUAL);//手机号码
		query.where("orgId", orgId, QueryAction.EQUAL);//组织架构
		List<CommandMoborg> list = (List<CommandMoborg>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<CommandMoborg> queryCommandMoborgListByMobile(String mobile) {
		if(StringUtils.isEmpty(mobile)){
			return null;
		}
		QueryBuilder query = new QueryBuilder("CommandMoborg cm,SysUser su,SysOrganization so");
		query.where("cm.orgId=so.organizationId");
		query.where("cm.createUserid=su.userId");
		query.where("( su.isActivate='1' or su.isActivate is null ) ");
		query.where("( so.orgState='1' or so.orgState is null )");
		query.where("cm.mobile", mobile, QueryAction.EQUAL);//手机号码
		List<Object[]> list = (List<Object[]>) enterpriseService.query(query, 0);
		List<CommandMoborg> tempCm = new ArrayList<CommandMoborg>();
		if (list != null){
			for(Object[] obj:list){
				if(!tempCm.contains((CommandMoborg)obj[0]))
					tempCm.add((CommandMoborg) obj[0]);
			}
		}
		return tempCm;
	}

	@Override
	public void printCommandInfo(CommandInfo cdinfo) {
		if(cdinfo != null){
			//将未流转的状态变为流转中
    		if(CommandCommon.commandStatus_WLZ.equals(cdinfo.getCommandStatus())){
    			cdinfo.setCommandStatus(CommandCommon.commandStatus_LZZ);
    			enterpriseService.updateObject(cdinfo);
    		}
    	}
	}

	@Override
	public String setCommandReceive(CommandInfo cdinfo, SysUser sysUser, String mobile) {
		String result = "0";
		if(cdinfo == null || sysUser == null){
			return result;
		}
		if(!CommandCommon.commandStatus_WLZ.equals(cdinfo.getCommandStatus()) && !CommandCommon.commandStatus_LZZ.equals(cdinfo.getCommandStatus())){
			return result;
		}
		//查询最新的流转信息
		CommandFlows cdflow = queryleastCommandFlows(cdinfo.getCommandId(),CommandCommon.commandStatus_LZZ,mobile,sysUser.getUserId());
		if(cdflow != null && sysUser.getUserId().equals(cdflow.getFlowUserid())){
			//同一个人不能连续两次接收同一份文档
			result = "2";
			return result;
		}
		//流转流程记录
		cdinfo.setCommandStatus(CommandCommon.commandStatus_LZZ);//归档状态
		enterpriseService.updateObject(cdinfo);//归档
		createCommandFlows(cdinfo, sysUser, mobile);//记录流程
		result = "1";
		return result;
	}

	private CommandFlows queryleastCommandFlows(String commandInfoid,String flowStatus,String mobile,String flowUserid) {
		QueryBuilder query = new QueryBuilder(CommandFlows.class);
		query.where("commandInfoid", commandInfoid, QueryAction.EQUAL);//指令编号
		query.where("flowStatus", flowStatus, QueryAction.EQUAL);//流转状态
//		query.where("flowMobile", mobile, QueryAction.EQUAL);//手机号码
//		query.where("flowUserid", flowUserid, QueryAction.EQUAL);//流转人id
		query.orderBy("flowTime", false);
		List<CommandFlows> list = (List<CommandFlows>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public CommandInfo queryCommandInfoByNum(String commandNum) {
		QueryBuilder query = new QueryBuilder(CommandInfo.class);
		query.where("commandNum",commandNum.toLowerCase(),QueryAction.EQUAL);
		query.orderBy("launchTime desc");
		List<CommandInfo> list = (List<CommandInfo>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ItemPage receiveNewDoc(CommandForm command, String mobile,String userid) {
		Class<?>[] pojos = {CommandInfo.class,CommandFlows.class};
		QueryBuilder query = new QueryBuilder(pojos);
		query.where(" a.commandId=b.commandInfoid ");
		query.where(" a.commandNum ",command.getCommandNum(),QueryAction.LIKE);
		//未流转或流转中
		query.where(" ( a.commandStatus='"+CommandCommon.commandStatus_WLZ+"' or a.commandStatus='"+CommandCommon.commandStatus_LZZ+"' )");
		//最新的一条流转信息不能是同一人
		query.where(" b.flowTime = (select max(flowTime) from CommandFlows t where t.commandInfoid=a.commandId) ");
		query.where(" b.flowStatus",CommandCommon.commandStatus_LZZ,QueryAction.NOEQUAL);
		query.where(" b.flowMobile",mobile,QueryAction.NOEQUAL);
		query.where(" b.flowUserid",userid,QueryAction.NOEQUAL);
		query.orderBy(" a.launchTime desc");
		return enterpriseService.query(query, command);
	}

	@Override
	public List<Object[]> searchCommandNum(String code) {
		code = code.toLowerCase().trim();
		//子项目合同
		String sql = "select * from (select case when command_type = '1' then 'A1类工作指令' when command_type = '2' then 'A2类工作指令' when command_type = '3' then 'B类工作指令' when command_type = '4' then 'C类工作指令' end command_type,command_num " +
				" from command_info  where Lower(command_num) like ? and command_type in ('1', '2', '3', '4') order by update_time desc)  where rownum <= 20";
		Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
		query.setString(0,"%"+code+"%");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryCommandInfoAll() {

			String sql = "select distinct contract_name from command_info";
			Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
			return query.list();

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryorgName(String orgName) {
		String sql = "select distinct org_name from command_info where contract_name=?";
		Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
		query.setString(0, orgName);
		return query.list();
	}
}
