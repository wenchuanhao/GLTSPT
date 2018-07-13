package com.cdc.dc.account.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.Query;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;
import org.trustel.util.DateUtils;

import com.cdc.common.BusTypes;
import com.cdc.dc.account.common.AccountCommon;
import com.cdc.dc.account.common.ComtreeTransitionDTO;
import com.cdc.dc.account.form.AccountConfigForm;
import com.cdc.dc.account.form.AccountForm;
import com.cdc.dc.account.form.FlowInfoForm;
import com.cdc.dc.account.model.AccountConfig;
import com.cdc.dc.account.model.AccountImportInfo;
import com.cdc.dc.account.model.AccountInfo;
import com.cdc.dc.account.model.AccountInvoice;
import com.cdc.dc.account.model.FlowInfo;
import com.cdc.dc.account.model.ImportTaxContract;
import com.cdc.dc.account.model.PersonalOpinion;
import com.cdc.dc.account.model.ProblemList;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.form.DicFailImportObject;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;
/**
 * 
 * @author xms
 *报账量化service
 */
public class AccountServiceImpl implements IAccountService{
	private Log logger = LogFactory.getLog(getClass());
	
	private IRulesService rulesService;
	
	public IRulesService getRulesService() {
		return rulesService;
	}

	public void setRulesService(IRulesService rulesService) {
		this.rulesService = rulesService;
	}

	private IEnterpriseService enterpriseService;
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
//	String str = "0";//1代表有可以保存数据
	//查询是否为系统管理员
	public String queryIsAdmin(String userid){
		Query query = enterpriseService.getSessions().createSQLQuery("select l.role_code from sys_user u left join sys_role_user r on u.user_id=r.user_id " +
		" left join sys_role l  on l.role_id=r.role_id where u.user_id= '"+userid+"' and l.role_code='"+AccountCommon.ACCOUNT_ADMIN+"'");
        String result = (String) query.uniqueResult();
		return result;
	}
	
	@Override
	public ItemPage queryAccountItemPage(String type,AccountForm accountForm,Map<String,String> map, SysUser visitor) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select order_id,sement_people,trial_account,department,cost_type,presenter,page_submit_date," +
				"include_buckle,create_name,status,id,deduction from account_tab where ");
		if(accountForm.getReachSementStr()!= null && !accountForm.getReachSementStr().equals("")){
			sb.append(" to_char(PAYMENT_TIME,'yyyy-mm') ='"+accountForm.getReachSementStr()+"'  and ");
		}
		if((accountForm.getCostType()!= null && !accountForm.getCostType().equals("")) || (accountForm.getCosId()!=null && !accountForm.getCosId().equals(""))){
			sb.append(" (cost_type ='"+accountForm.getCostType()+"' or cos_id ='"+accountForm.getCosId()+"')  and  ");
		}
		if(accountForm.getStatus()!= null && !accountForm.getStatus().equals("")){
			if(accountForm.getStatus().equals("5")){//有问题
				sb.append("  status in ('1','3') and ");
			}else{
				sb.append("  status ='"+accountForm.getStatus()+"'  and ");
			}
		}
		if(accountForm.getDepartment()!= null && !accountForm.getDepartment().equals("")){
			sb.append("department like '%"+accountForm.getDepartment()+"%' and ");
		}
		if(accountForm.getOrderId()!= null && !accountForm.getOrderId().equals("")){
			sb.append("order_id like '%"+accountForm.getOrderId()+"%'  and ");
		}
		//纸质提交财务时间
		if(StringUtils.isNotEmpty(map.get("pstartDate"))){
			sb.append("page_submit_date >=to_date('"+map.get("pstartDate")+"','yyyy-MM-dd hh24:mi:ss')  and  ");
		}
		if(StringUtils.isNotEmpty(map.get("pendDate"))){
			sb.append(" page_submit_date <= to_date('"+map.get("pendDate")+"','yyyy-MM-dd hh24:mi:ss')  and ");
		}
		//创建时间
		if(StringUtils.isNotEmpty(map.get("cstartDate"))){
			sb.append("create_date >=to_date('"+map.get("cstartDate")+"','yyyy-MM-dd hh24:mi:ss')  and  ");
		}
		if(StringUtils.isNotEmpty(map.get("cendDate"))){
			sb.append(" create_date <= to_date('"+map.get("cendDate")+"','yyyy-MM-dd hh24:mi:ss')  and ");
		}
		//送达财务付款时间
		if(StringUtils.isNotEmpty(map.get("payStartDate"))){
			sb.append(" PAYMENT_TIME >=to_date('"+map.get("payStartDate")+"','yyyy-MM-dd hh24:mi:ss')  and ");
		}
		if(StringUtils.isNotEmpty(map.get("payEndDate"))){
			sb.append(" PAYMENT_TIME <= to_date('"+map.get("payEndDate")+"','yyyy-MM-dd hh24:mi:ss')  and ");
		}
		if(accountForm.getIncludeBuckle()!= null && !accountForm.getIncludeBuckle().equals("")){
			sb.append("INCLUDE_BUCKLE ='"+accountForm.getIncludeBuckle()+"'  and ");
		}
		//专票抵扣
		if(accountForm.getDeduction()!= null && !accountForm.getDeduction().equals("")){
			sb.append("deduction ='"+accountForm.getDeduction()+"'  and ");
		}
		if(accountForm.getTrialAccountId()!= null && !accountForm.getTrialAccountId().equals("")){
			sb.append("TRIAL_ACCOUNT_ID like '%"+accountForm.getTrialAccountId()+"%' and  ");
		}
		if(accountForm.getCurrentLink()!= null && !accountForm.getCurrentLink().equals("")){
			sb.append("CURRENT_LINK ='"+accountForm.getCurrentLink()+"'  and ");
		}
		if(accountForm.getSementPeople()!=null && !accountForm.getSementPeople().equals("")){
			sb.append("SEMENT_PEOPLE ='"+accountForm.getSementPeople()+"'  and ");
		}
		if(accountForm.getSementPeopleId()!= null && !accountForm.getSementPeopleId().equals("")){
			sb.append(" (SEMENT_PEOPLE_ID ='"+accountForm.getSementPeopleId()+"' or SEMENT_PEOPLE='"+accountForm.getSementPeopleId()+"' )  and ");
		}
		if(StringUtils.isNotEmpty(map.get("people"))){
			sb.append(" (order_id like '%"+map.get("people")+"%' or  SEMENT_PEOPLE like '%"+map.get("people")+"%')  and  ");
		}
		
		if(type.equals("1")){
			sb.append(" user_id='"+visitor.getUserId()+"'");
		}
		if(type.equals("2")){
			sb.append("  ( collection_menber='"+visitor.getUserName()+"' or  first_actorUser_id='"+visitor.getUserId()+"' or TRIAL_ACCOUNT_id='"+visitor.getUserId()+"') " );
		}
		if(type.equals("3")){
			//管理员可查看所有单据
			String result = queryIsAdmin(visitor.getUserId());
			if( result==null || !result.equals(AccountCommon.ACCOUNT_ADMIN) || result.equals("")){
				result = "NOadmin";
				sb.append(" user_id = '"+result+"'");
			}else{
				sb.append(" user_id != '"+result+"'");
			}
		}
		sb.append(" order by create_date desc");
		
		return enterpriseService.findBySql(sb.toString(), accountForm);
	}
	
	@Override
	public List queryAccountList(String type,AccountForm accountForm,Map<String,String> map, SysUser visitor) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select order_id,sement_people,trial_account,department,cost_type,presenter,page_submit_date," +
				"include_buckle,create_name,status,id,deduction from account_tab where ");
		if(accountForm.getReachSementStr()!= null && !accountForm.getReachSementStr().equals("")){
			sb.append(" to_char(PAYMENT_TIME,'yyyy-mm') ='"+accountForm.getReachSementStr()+"'  and ");
		}
		if((accountForm.getCostType()!= null && !accountForm.getCostType().equals("")) || (accountForm.getCosId()!=null && !accountForm.getCosId().equals(""))){
			sb.append(" (cost_type ='"+accountForm.getCostType()+"' or cos_id ='"+accountForm.getCosId()+"')  and  ");
		}
		if(accountForm.getStatus()!= null && !accountForm.getStatus().equals("")){
			if(accountForm.getStatus().equals("5")){//有问题
				sb.append("  status in ('1','3') and ");
			}else{
				sb.append("  status ='"+accountForm.getStatus()+"'  and ");
			}
		}
		if(accountForm.getDepartment()!= null && !accountForm.getDepartment().equals("")){
			sb.append("department like '%"+accountForm.getDepartment()+"%' and ");
		}
		if(accountForm.getOrderId()!= null && !accountForm.getOrderId().equals("")){
			sb.append("order_id like '%"+accountForm.getOrderId()+"%'  and ");
		}
		//纸质提交财务时间
		if(StringUtils.isNotEmpty(map.get("pstartDate"))){
			sb.append("page_submit_date >=to_date('"+map.get("pstartDate")+"','yyyy-MM-dd hh24:mi:ss')  and  ");
		}
		if(StringUtils.isNotEmpty(map.get("pendDate"))){
			sb.append(" page_submit_date <= to_date('"+map.get("pendDate")+"','yyyy-MM-dd hh24:mi:ss')  and ");
		}
		//创建时间
		if(StringUtils.isNotEmpty(map.get("cstartDate"))){
			sb.append("create_date >=to_date('"+map.get("cstartDate")+"','yyyy-MM-dd hh24:mi:ss')  and  ");
		}
		if(StringUtils.isNotEmpty(map.get("cendDate"))){
			sb.append(" create_date <= to_date('"+map.get("cendDate")+"','yyyy-MM-dd hh24:mi:ss')  and ");
		}
		//送达财务付款时间
		if(StringUtils.isNotEmpty(map.get("payStartDate"))){
			sb.append(" PAYMENT_TIME >=to_date('"+map.get("payStartDate")+"','yyyy-MM-dd hh24:mi:ss')  and ");
		}
		if(StringUtils.isNotEmpty(map.get("payEndDate"))){
			sb.append(" PAYMENT_TIME <= to_date('"+map.get("payEndDate")+"','yyyy-MM-dd hh24:mi:ss')  and ");
		}
		if(accountForm.getIncludeBuckle()!= null && !accountForm.getIncludeBuckle().equals("")){
			sb.append("INCLUDE_BUCKLE ='"+accountForm.getIncludeBuckle()+"'  and ");
		}
		//专票抵扣
		if(accountForm.getDeduction()!= null && !accountForm.getDeduction().equals("")){
			sb.append("deduction ='"+accountForm.getDeduction()+"'  and ");
		}
		if(accountForm.getTrialAccountId()!= null && !accountForm.getTrialAccountId().equals("")){
			sb.append("TRIAL_ACCOUNT_ID like '%"+accountForm.getTrialAccountId()+"%' and  ");
		}
		if(accountForm.getCurrentLink()!= null && !accountForm.getCurrentLink().equals("")){
			sb.append("CURRENT_LINK ='"+accountForm.getCurrentLink()+"'  and ");
		}
		if(accountForm.getSementPeople()!=null && !accountForm.getSementPeople().equals("")){
			sb.append("SEMENT_PEOPLE ='"+accountForm.getSementPeople()+"'  and ");
		}
		if(accountForm.getSementPeopleId()!= null && !accountForm.getSementPeopleId().equals("")){
			sb.append(" (SEMENT_PEOPLE_ID ='"+accountForm.getSementPeopleId()+"' or SEMENT_PEOPLE='"+accountForm.getSementPeopleId()+"' )  and ");
		}
		if(StringUtils.isNotEmpty(map.get("people"))){
			sb.append(" (order_id like '%"+map.get("people")+"%' or  SEMENT_PEOPLE like '%"+map.get("people")+"%')  and  ");
		}
		
		if(type.equals("1")){
			sb.append(" user_id='"+visitor.getUserId()+"'");
		}
		if(type.equals("2")){
			sb.append("  ( collection_menber='"+visitor.getUserName()+"' or  first_actorUser_id='"+visitor.getUserId()+"' or TRIAL_ACCOUNT_id='"+visitor.getUserId()+"') " );
		}
		if(type.equals("3")){
			//管理员可查看所有单据
			String result = queryIsAdmin(visitor.getUserId());
			if( result==null || !result.equals(AccountCommon.ACCOUNT_ADMIN) || result.equals("")){
				result = "NOadmin";
				sb.append(" user_id = '"+result+"'");
			}else{
				sb.append(" user_id != '"+result+"'");
			}
		}
		sb.append(" order by create_date desc");
		
		return enterpriseService.findBySqls(sb.toString(), accountForm);
	}

	@Override
	public void addAccount(AccountInfo accountInfo) throws Exception {
		// TODO Auto-generated method stub
		enterpriseService.save(accountInfo);
	}

	@Override
	public ItemPage accountTrialQuery(AccountForm accountForm,String type,SysUser user,String people) throws Exception {
		ItemPage itemPage;
		StringBuffer sb = new StringBuffer();
		sb.append("select b.order_id,b.department,b.cost_type,b.sement_people,b.create_date,b.status,t.time,t.days,b.CURRENT_LINK,b.id from account_tab b ");
		sb.append(" left join  (select  order_id,sum(time) as time,sum(days) as days  from (");
		sb.append(" select t.order_id,0 as time ,sum(days) as days from(");
		sb.append(" select t.order_id,max(f.OUT_DAY) as days from account_tab t left join problem_list f on t.order_id=f.account_order_id");
		sb.append(" where    f.is_outtime ='1'   group by f.phase,t.order_id) t group by order_id");
		sb.append(" union all ");
		sb.append("select t.order_id,count(f.id) as time,0 as days from account_tab t left join problem_list f on t.order_id=f.account_order_id");
		sb.append(" where  f.status!='"+AccountCommon.PROBLEM_STATUS_ZGTG+"' group by order_id ) t group by order_id) t on b.order_id=t.order_id where b.trial_account_id like '%"+user.getUserId()+"%'  and ");
		
		if((accountForm.getCosId()!= null && !accountForm.getCosId().equals("")) || (accountForm.getCostType()!=null && !accountForm.getCostType().equals(""))){
			sb.append(" (b.COS_ID='"+accountForm.getCosId()+"' or b.cost_type='"+accountForm.getCostType()+"') and ");
			
		}
		if(accountForm.getStatus()!= null && !accountForm.getStatus().equals("")){
			if(accountForm.getStatus().equals("5")){//有问题
				sb.append("  b.status in ('1','3') and ");
			}else{
				sb.append("  b.status ='"+accountForm.getStatus()+"'  and ");
			}
		}
		if(StringUtils.isNoneBlank(accountForm.geteSDate())){
			sb.append("to_char(b.PAGE_SUBMIT_DATE,'YYYY-MM-DD HH24:MI:SS') >= '" + accountForm.geteSDate() + "' and ");
		}
		if(StringUtils.isNoneBlank(accountForm.geteEDate())){
			sb.append("to_char(b.PAGE_SUBMIT_DATE,'YYYY-MM-DD HH24:MI:SS') <= '" + accountForm.geteEDate() + "' and ");
		}
		
		if(accountForm.getIstimeOut()!= null && !accountForm.getIstimeOut().equals("")){
			sb.append("b.is_timeout='"+accountForm.getIstimeOut()+"' and ");
		}
		if(people!= null && !people.equals("")){
			sb.append("(b.order_id like'%"+people+"%' or ");
			sb.append("b.sement_people like'%"+people+"%') and ");
		}
		
		// 1初审处理   2历史处理结果
		if(type.equals("2")){
			sb.append("b.TRIAL_FLAT = '"+AccountCommon.ACCOUNT_ACCOUNT_FLAT_YB+"'");
		}else {	
			sb.append("b.TRIAL_FLAT = '"+AccountCommon.ACCOUNT_ACCOUNT_FLAT_DB+"'");		
		}
		sb.append(" and b.TDTIME = '0'");
		sb.append(" order by b.create_date desc");
		itemPage = enterpriseService.findBySql(sb.toString(), accountForm);
		return itemPage;
	}
	
	@Override
	public List accountTrialQueryList(AccountForm accountForm,String type,SysUser user,String people) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select b.order_id,b.department,b.cost_type,b.sement_people,b.create_date,b.status,t.time,t.days,b.CURRENT_LINK,b.id from account_tab b ");
		sb.append(" left join  (select  order_id,sum(time) as time,sum(days) as days  from (");
		sb.append(" select t.order_id,0 as time ,sum(days) as days from(");
		sb.append(" select t.order_id,max(f.OUT_DAY) as days from account_tab t left join problem_list f on t.order_id=f.account_order_id");
		sb.append(" where    f.is_outtime ='1'   group by f.phase,t.order_id) t group by order_id");
		sb.append(" union all ");
		sb.append("select t.order_id,count(f.id) as time,0 as days from account_tab t left join problem_list f on t.order_id=f.account_order_id");
		sb.append(" where  f.status!='"+AccountCommon.PROBLEM_STATUS_ZGTG+"' group by order_id ) t group by order_id) t on b.order_id=t.order_id where b.trial_account_id like '%"+user.getUserId()+"%'  and ");
		
		if((accountForm.getCosId()!= null && !accountForm.getCosId().equals("")) || (accountForm.getCostType()!=null && !accountForm.getCostType().equals(""))){
			sb.append(" (b.COS_ID='"+accountForm.getCosId()+"' or b.cost_type='"+accountForm.getCostType()+"') and ");
			
		}
		if(accountForm.getStatus()!= null && !accountForm.getStatus().equals("")){
			if(accountForm.getStatus().equals("5")){//有问题
				sb.append("  b.status in ('1','3') and ");
			}else{
				sb.append("  b.status ='"+accountForm.getStatus()+"'  and ");
			}
		}
		if(StringUtils.isNoneBlank(accountForm.geteSDate())){
			sb.append("to_char(b.PAGE_SUBMIT_DATE,'YYYY-MM-DD HH24:MI:SS') >= '" + accountForm.geteSDate() + "' and ");
		}
		if(StringUtils.isNoneBlank(accountForm.geteEDate())){
			sb.append("to_char(b.PAGE_SUBMIT_DATE,'YYYY-MM-DD HH24:MI:SS') <= '" + accountForm.geteEDate() + "' and ");
		}
		
		if(accountForm.getIstimeOut()!= null && !accountForm.getIstimeOut().equals("")){
			sb.append("b.is_timeout='"+accountForm.getIstimeOut()+"' and ");
		}
		if(people!= null && !people.equals("")){
			sb.append("(b.order_id like'%"+people+"%' or ");
			sb.append("b.sement_people like'%"+people+"%') and ");
		}
		
		// 1初审处理   2历史处理结果
		if(type.equals("2")){
			sb.append("b.TRIAL_FLAT = '"+AccountCommon.ACCOUNT_ACCOUNT_FLAT_YB+"'");
		}else {	
			sb.append("b.TRIAL_FLAT = '"+AccountCommon.ACCOUNT_ACCOUNT_FLAT_DB+"'");		
		}
		sb.append(" and b.TDTIME = '0'");
		sb.append(" order by b.create_date desc");
		return enterpriseService.findBySqls(sb.toString(), accountForm);
	}
	
	@Override
	public List<AccountInfo> exportProblems(AccountForm accountForm, String type, SysUser user, String people) {
		// TODO Auto-generated method stub
		QueryBuilder builder = new QueryBuilder(AccountInfo.class);
		
		builder.where("trialAccountId",user.getUserId(),QueryAction.LIKE);
		
		if(StringUtils.isNotEmpty(accountForm.getCosId()) || StringUtils.isNotEmpty(accountForm.getCostType())){
			builder.where("( cosId ='"+accountForm.getCosId()+"' or costType='"+accountForm.getCostType()+"' )");
		}
		if(accountForm.getOnlyProblems() != null && accountForm.getOnlyProblems().equals("1")){
			builder.where("status in ('1','3')");
		}else{
			if(StringUtils.isNotEmpty(accountForm.getStatus())){
				builder.where("status",accountForm.getStatus());
			}
		}
		if(StringUtils.isNotBlank(accountForm.geteSDate())){
			builder.where("to_char(pageSubmitDate,'yyyy-MM-dd')",accountForm.geteSDate(),QueryAction.GE);
		}
		if(StringUtils.isNotBlank(accountForm.geteEDate())){
			builder.where("to_char(pageSubmitDate,'yyyy-MM-dd')",accountForm.geteEDate(),QueryAction.LE);
		}
		if(StringUtils.isNotEmpty(accountForm.getIstimeOut())){
			builder.where("istimeOut",accountForm.getIstimeOut());
		}
		if(StringUtils.isNotEmpty(people)){
			builder.where("( orderId like'%"+people+"%' or  sementPeople like '%"+people+"%' )");
		}
		// 1初审处理   2历史处理结果
		if("2".equals(type)){
			builder.where("trialFlat", AccountCommon.ACCOUNT_ACCOUNT_FLAT_YB);
		}else{
			builder.where("trialFlat", AccountCommon.ACCOUNT_ACCOUNT_FLAT_DB);
		}
		builder.where("tDTime", "0");
		//管理员可查看所有单据
		String result = queryIsAdmin(user.getUserId());
		if(AccountCommon.ACCOUNT_ADMIN.equals(result)){
			builder.or();
			if(accountForm.getOnlyProblems() != null && accountForm.getOnlyProblems().equals("1")){
				//防止空数据
				if(StringUtils.isBlank(accountForm.geteSDate())){
					accountForm.seteSDate("1970-01-01");
				}
				if(StringUtils.isBlank(accountForm.geteEDate())){
					accountForm.seteEDate(DateUtils.format(new Date(), "yyyy-MM-dd", null));
				}
				
				builder.where(" ( status in ('1','3') and to_char(pageSubmitDate,'yyyy-MM-dd') >= '" 
						+ accountForm.geteSDate() + "' and to_char(pageSubmitDate,'yyyy-MM-dd') <= '" 
						+ accountForm.geteEDate() + "' )");
			}else{
				builder.where(" 1=1 ");
			}
		}
		builder.orderBy("createDate", false);
		return (List<AccountInfo>) enterpriseService.query(builder, 0);
	}
	/**
	 * 保存个人常用意见
	 */
	@Override
	public String savePersonalOpinion(String content, String creator, Date createDate, String outline){
		QueryBuilder builder = new QueryBuilder(PersonalOpinion.class);
		builder.where("content", content);
		builder.where("creator", creator);
		String opinionId = "";
		StringBuffer result = new StringBuffer(""); 
		List personalOpinionList = enterpriseService.query(builder, 0);
		if(personalOpinionList.size() == 0){
			PersonalOpinion personalOpinion = new PersonalOpinion();
			personalOpinion.setContent(content);
			personalOpinion.setCreator(creator);
			personalOpinion.setCreateDate(createDate);
			personalOpinion.setOutline(outline);
			enterpriseService.save(personalOpinion);
			opinionId = personalOpinion.getPersonalOpinionId();
		}
		if(StringUtils.isNotEmpty(opinionId)) {
			result.append("<option value='").append(opinionId.trim()).append("'>").append(outline).append("</option>").append("@#@"+outline+"@#@"+opinionId.trim());
		}
		return result.toString();
	}
	
	/**
	 * 查询问题详情列表
	 */
	@Override
	public List queryPersonalOpinionList(String userId){
		QueryBuilder builder = new QueryBuilder(PersonalOpinion.class);
		builder.where("creator", userId);
		builder.orderBy("createDate", false);
		List list = enterpriseService.query(builder, 0);
		return list;
	}

	@Override
	public void saveProblem(ProblemList problemList) {
		if(problemList.getId().equals("")){
			enterpriseService.save(problemList);
		}else {
			enterpriseService.updateObject(problemList);
		}
	}
	
	/**
	 * 根据ID查询个人常用意见
	 */
	@Override
	public PersonalOpinion queryPersonalOpinionById(String personalOpinionId){
		if(personalOpinionId != null && !personalOpinionId.equals("")){
			PersonalOpinion personalOpinion = (PersonalOpinion)enterpriseService.getById(PersonalOpinion.class, personalOpinionId);
			return personalOpinion;
		}
		return null;
	}
	
	public List<PersonalOpinion> getPersonalOption(SysUser user) {
		// TODO Auto-generated method stub
		QueryBuilder builder = new QueryBuilder(PersonalOpinion.class);
		builder.where("creator", user.getUserId());
		builder.orderBy("createDate", false);
		return (List<PersonalOpinion>) enterpriseService.query(builder, 0);
	}

	@Override
	public List queryAccountWorkorderByIds(AccountForm accountForm,String people,String userid,String type)
			throws Exception {
		QueryBuilder query = new QueryBuilder(AccountInfo.class);

		if(accountForm.getCosId() != null && !accountForm.getCosId().equals("")){
			query.where("cosId", accountForm.getCosId(), QueryAction.EQUAL);
		}
		if(accountForm.getStatus() != null && !accountForm.getStatus() .equals("")){
			query.where("status", accountForm.getStatus(), QueryAction.EQUAL);
		}
		if(accountForm.getTimeOut() != null && !accountForm.getTimeOut().equals("")){
			query.where("timeOut", accountForm.getTimeOut(), QueryAction.EQUAL);
		}
		if(people != null && !people.trim().equals("")){
			query.where("orderId", people, QueryAction.EQUAL);
			query.or();
			query.where("sementPeople",people,QueryAction.EQUAL);
		}
		// 1初审处理   2历史处理结果
		if(type.equals("2")){
			query.where("trialAccountId",userid,QueryAction.EQUAL);
			query.where("submitDate","null",QueryAction.NOEQUAL);
		}else {	
			query.where("trialAccountId",userid,QueryAction.EQUAL);
		}
		
		List list = enterpriseService.query(query, 0);
		return list;
	}

	@Override
	public void saveTrialConfig(AccountConfig accountConfig) {
		enterpriseService.save(accountConfig);
		
	}

	@Override
	public AccountConfig findTrialConfigById(String id) {
		
		return (AccountConfig) enterpriseService.getById(AccountConfig.class, id);
	}

	@Override
	public ItemPage queryTrialConfigList(AccountConfigForm configForm) {
		QueryBuilder query = new QueryBuilder(AccountConfig.class);
		if((configForm.getDepartment()!=null && !configForm.getDepartment().equals("")) ||
				(configForm.getDepartmentId()!=null && !configForm.getDepartmentId().equals(""))){
			String str = "(department='"+configForm.getDepartment()+"' or departmentId='"+configForm.getDepartmentId()+"')";
			query.where(str);
		}
		if((configForm.getCosId()!=null && !configForm.getCosId().equals("")) ||
				(configForm.getCostType()!=null && !configForm.getCostType().equals(""))){
			String str = "(cosId='"+configForm.getCosId()+"' or costType='"+configForm.getCostType()+"')";
			query.where(str);
		}
		if(configForm.getAccounting()!=null && !configForm.getAccounting().equals("")){
			query.where("accounting",configForm.getAccounting(),QueryAction.LIKE);
		}
		if(configForm.getAccountingId()!=null && !configForm.getAccountingId().equals("")){
			query.where("accountingId",configForm.getAccountingId(),QueryAction.LIKE);
		}
		ItemPage itemPage =(ItemPage) enterpriseService.query(query, configForm);
		return itemPage;
	}
	
	@Override
	public void delTrialConfig(String id, String userId) {
		// TODO Auto-generated method stub
		QueryBuilder query = new QueryBuilder(AccountConfig.class);
		//query.where("userId",userId);
		query.where("id",id.split(","));
		enterpriseService.delete(query);
		
	}

	@Override
	public void updateTrialConfig(AccountConfig config) {
		enterpriseService.updateObject(config);
		
	}

	@Override
	public ItemPage queryInfoCollectList(AccountForm accountForm, String type,
			SysUser visitor, String people) throws ParseException {
		ItemPage itemPage;
		
		Class<?>[]  pojos={AccountInfo.class}; 
		QueryBuilder query = new QueryBuilder(pojos);
		if((accountForm.getCosId()!= null && !accountForm.getCosId().equals("")) || (accountForm.getCostType()!= null && !accountForm.getCostType().equals(""))){
			String str = "(a.cosId = '"+accountForm.getCosId()+"' or a.costType = '"+accountForm.getCostType()+"') ";
			query.where(str);
		}
		if((accountForm.getDepartment()!=null && !accountForm.getDepartment().equals("")) || (accountForm.getDepartmentId()!= null && !accountForm.getDepartmentId().equals(""))){
			String str = "(a.department = '"+accountForm.getDepartment()+"' or a.departmentId = '"+accountForm.getDepartmentId()+"') ";
			query.where(str);
		}
		if(people!= null && !people.equals("")){
			query.where("(a.orderId like '%"+people+"%'or a.sementPeople like '%"+people+"%')");
		}
		query.where("a.collectionMenberId",visitor.getUserId(),QueryAction.LIKE);
		
		//2已办
		if(type.equals("2")){
			query.where("(recordFlat = ' " + AccountCommon.ACCOUNT_RECORD_FLAT + "' or recordFlat = '" 
						+ AccountCommon.ACCOUNT_RECORD_FLAT_SHUIWU + "' and currentLink = '" + AccountCommon.CURRENT_LINK_WC + "')" );
			
			/*query.where("recordFlat",AccountCommon.ACCOUNT_RECORD_FLAT,QueryAction.EQUAL);
			query.or();
			query.where("recordFlat",AccountCommon.ACCOUNT_RECORD_FLAT_SHUIWU,QueryAction.EQUAL);
			query.where("currentLink",AccountCommon.CURRENT_LINK_WC,QueryAction.EQUAL);*/
			
		}else {
			query.where("currentLink",AccountCommon.CURRENT_LINK_XXBL,QueryAction.EQUAL);
		}
		query.orderBy("a.createDate",false);
		itemPage = enterpriseService.query(query, accountForm);
		return itemPage;
	}
	
	/**
	 * * 根据单据编号查询流程信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	 
	@SuppressWarnings("unchecked")
	public AccountInfo getAccountByid(String account) throws Exception {
		QueryBuilder query = new QueryBuilder(AccountInfo.class);
		query.where("orderId",account);
		List <AccountInfo> list= (List<AccountInfo>) enterpriseService.query(query, 0);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * excel数据验证
	 * 
	 * @param visitor
	 * @param file
	 * @param request
	 * @param specificationId
	 * @return
	 * @throws RuntimeException
	 */

	public List<DicFailImportObject> validateWare(SysUser visitor, String file,
			HttpServletRequest request)
			throws RuntimeException {
		String userResult[][] = null;//用户信息
		SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-M-d HH:mm:ss " );
		
		DicFailImportObject dicFailImportObject = null;//记录一条错误的信息
		List<DicFailImportObject> listobject=null;//所有错误
		int row = 1;
		//row忽略行数 row=1,mis流程模板忽略前1行，row=7税务模板忽略前7行
		if(file.contains("shuiwu_")){
			row = 7;
		}
		
		try {
			userResult = getData(new File(file), row, 0,file);
			//读取Excel详情
			//从文件导入数据
			List list = ExcelUtil.importFromExcel(new File(file),0);
			
			File file2 = new File(file);
		    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file2));

		    // 打开HSSFWorkbook
		    POIFSFileSystem fs = new POIFSFileSystem(in);
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    HSSFCell cell = null;
		    HSSFSheet st = wb.getSheetAt(0);
		    HSSFRow row0 = st.getRow(1);
		    
			listobject=new ArrayList<DicFailImportObject>();
			if(userResult==null || userResult.length==0){
				dicFailImportObject = new DicFailImportObject();
				dicFailImportObject.setSheetIndex(0);
				dicFailImportObject.setRow(0);
				dicFailImportObject.setFailReason("至少要填入一条数据!");
				listobject.add(dicFailImportObject);
				return listobject;
			}
			
			//最新导入标示
			String news = queryNews();
			if(news==null){
				news="0";
			}
			
			//循环用户信息
			for (int i = 0; i < userResult.length; i++) {//除检查重复之外的其他数据检查机制
				row0 = st.getRow(i+1);
				//在第103列写入错误信息
				cell = row0.createCell(103);
				if (null == userResult[i][1] ||  "".equals(userResult[i][1])) {
					dicFailImportObject = new DicFailImportObject();
					dicFailImportObject.setSheetIndex(0);
					dicFailImportObject.setRow(i + 2);
					dicFailImportObject.setFailReason("单据编号为空!");
					listobject.add(dicFailImportObject);
					continue ;
				}else{
					//根据单据编号查询信息
					AccountInfo info = this.getAccountByid(userResult[i][0]);

					if(info == null){
						dicFailImportObject = new DicFailImportObject();
						dicFailImportObject.setSheetIndex(0);
						dicFailImportObject.setRow(i + 2);
						dicFailImportObject.setFailReason("该单据未录入!");
						//row0 = st.createRow(i+1);
						cell.setCellValue("该单据未录入!");				
						listobject.add(dicFailImportObject);
						continue ;
					} else {
							//提交电子报账单
							if(!StringUtils.isNotEmpty(userResult[i][2])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("提交电子报账单时间不能为空！");
								cell.setCellValue("提交电子报账单时间不能为空！");
								listobject.add(dicFailImportObject);
								continue;
							}else if(StringUtils.isNotEmpty(userResult[i][2]) && isNotDateFormat(userResult[i][2])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("提交电子报账单时间格式不正确！");
								cell.setCellValue("提交电子报账单时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
								//室经理审批
							} else if(!StringUtils.isNotEmpty(userResult[i][4])){
									dicFailImportObject = new DicFailImportObject();
									dicFailImportObject.setSheetIndex(0);
									dicFailImportObject.setRow(i + 2);
									dicFailImportObject.setFailReason("室经理审批时间不能为空！");
									cell.setCellValue("室经理审批时间不能为空！");
									listobject.add(dicFailImportObject);
									continue;
							}else if(StringUtils.isNotEmpty(userResult[i][4]) && isNotDateFormat(userResult[i][4])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("室经理审批时间格式不正确！");
								cell.setCellValue("室经理审批时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
								//部门领导审批	
							}else if(!StringUtils.isNotEmpty(userResult[i][6])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("部门领导审批时间不能为空！");
								cell.setCellValue("部门领导审批时间不能为空！");
								listobject.add(dicFailImportObject);
								continue;
							}else if(StringUtils.isNotEmpty(userResult[i][6]) && isNotDateFormat(userResult[i][6])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("部门领导审批时间格式不正确！");
								cell.setCellValue("部门领导审批时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
								//初审会计审批
							}else if(!StringUtils.isNotEmpty(userResult[i][8])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("初审会计审批时间不能为空！");
								cell.setCellValue("初审会计审批时间不能为空！");
								listobject.add(dicFailImportObject);
								continue;
							}else if(StringUtils.isNotEmpty(userResult[i][8]) && isNotDateFormat(userResult[i][8])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("初审会计审批时间格式不正确！");
								cell.setCellValue("初审会计审批时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
								//主办会计审批
							}else if(!StringUtils.isNotEmpty(userResult[i][10])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("主办会计审批时间不能为空！");
								cell.setCellValue("主办会计审批时间不能为空！");
								listobject.add(dicFailImportObject);
								continue;
							}else if(StringUtils.isNotEmpty(userResult[i][10]) && isNotDateFormat(userResult[i][10])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("主办会计审批时间格式不正确！");
								cell.setCellValue("主办会计审批时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
								//财务经理审批
//							}else if(!StringUtils.isNotEmpty(userResult[i][12])){
//								dicFailImportObject = new DicFailImportObject();
//								dicFailImportObject.setSheetIndex(0);
//								dicFailImportObject.setRow(i + 2);
//								dicFailImportObject.setFailReason("财务经理审批时间不能为空！");
//								cell.setCellValue("财务经理审批时间不能为空！");
//								listobject.add(dicFailImportObject);
//								continue;
							}else if(StringUtils.isNotEmpty(userResult[i][12]) && isNotDateFormat(userResult[i][12])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("财务经理审批时间格式不正确！");
								cell.setCellValue("财务经理审批时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
								//南基领导审批
								//筹建办公室财务部副总经理BR	筹建办公室财务部副总经理审批人BS
//							}else if(!StringUtils.isNotEmpty(userResult[i][14])){
//								dicFailImportObject = new DicFailImportObject();
//								dicFailImportObject.setSheetIndex(0);
//								dicFailImportObject.setRow(i + 2);
//								dicFailImportObject.setFailReason("南基领导审批时间不能为空！");
//								cell.setCellValue("南基领导审批时间不能为空！");
//								listobject.add(dicFailImportObject);
//								continue;
							}else if(StringUtils.isNotEmpty(userResult[i][14]) && isNotDateFormat(userResult[i][14])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("南基领导审批时间格式不正确！");
								cell.setCellValue("南基领导审批时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
								//公司总经理.时间CF	公司总经理.审批人CG
							}else if(StringUtils.isNotEmpty(userResult[i][16]) && isNotDateFormat(userResult[i][16])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("南基领导审批时间格式不正确！");
								cell.setCellValue("南基领导审批时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
								//省财务审批
							}else if(!StringUtils.isNotEmpty(userResult[i][18])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("省财务审批时间不能为空！");
								cell.setCellValue("省财务审批时间不能为空！");
								listobject.add(dicFailImportObject);
								continue;
							}else if(StringUtils.isNotEmpty(userResult[i][18]) && isNotDateFormat(userResult[i][18])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("省财务审批时间格式不正确！");
								cell.setCellValue("省财务审批时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
								//出纳付款
							}else if(!StringUtils.isNotEmpty(userResult[i][20])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("出纳付款时间不能为空！");
								cell.setCellValue("出纳付款时间不能为空！");
								listobject.add(dicFailImportObject);
								continue;
							}else if(StringUtils.isNotEmpty(userResult[i][20]) && isNotDateFormat(userResult[i][20])){
								dicFailImportObject = new DicFailImportObject();
								dicFailImportObject.setSheetIndex(0);
								dicFailImportObject.setRow(i + 2);
								dicFailImportObject.setFailReason("出纳付款批时间格式不正确！");
								cell.setCellValue("出纳付款时间格式不正确！");
								listobject.add(dicFailImportObject);
								continue;
							}else{
								//如果该记录存在流程信息表中，就删除，然后新增该条记录
								delFlowInofById(info.getOrderId());
								updateInfo(info,i,userResult,news);
								//保存MIS文件信息
								saveMisInfo((String[]) list.get(i+1));
								//如果该row可以保存，那么跳出当前循环
								continue;
							}
						}
					
				
				}
			}
			FileOutputStream out = new FileOutputStream(file);
			wb.write(out);
			in.close();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return listobject;
	}
	
	private void saveMisInfo(String[] obj) {
		//删除原有信息
		if(StringUtils.isNotEmpty(obj[1])){
			QueryBuilder query = new QueryBuilder(AccountImportInfo.class);
			query.where("orderId",obj[1],QueryAction.EQUAL);
			enterpriseService.delete(query);
		}
		AccountImportInfo info = new AccountImportInfo();
		info.setOrderId(obj[1]);
		info.setCreateTime(new Date());
		info.setColumn01(obj[1]);
		info.setColumn02(obj[2]);
		info.setColumn03(obj[3]);
		info.setColumn04(obj[4]);
		info.setColumn05(obj[5]);
		info.setColumn06(obj[6]);
		info.setColumn07(obj[7]);
		info.setColumn08(obj[8]);
		info.setColumn09(obj[9]);
		info.setColumn10(obj[10]);
		info.setColumn11(obj[11]);
		info.setColumn12(obj[12]);
		info.setColumn13(obj[13]);
		info.setColumn14(obj[14]);
		info.setColumn15(obj[15]);
		info.setColumn16(obj[16]);
		info.setColumn17(obj[17]);
		info.setColumn18(obj[18]);
		info.setColumn19(obj[19]);
		info.setColumn20(obj[20]);
		info.setColumn21(obj[21]);
		info.setColumn22(obj[22]);
		info.setColumn23(obj[23]);
		info.setColumn24(obj[24]);
		info.setColumn25(obj[25]);
		info.setColumn26(obj[26]);
		info.setColumn27(obj[27]);
		info.setColumn28(obj[28]);
		info.setColumn29(obj[29]);
		info.setColumn30(obj[30]);
		info.setColumn31(obj[31]);
		info.setColumn32(obj[32]);
		info.setColumn33(obj[33]);
		info.setColumn34(obj[34]);
		info.setColumn35(obj[35]);
		info.setColumn36(obj[36]);
		info.setColumn37(obj[37]);
		info.setColumn38(obj[38]);
		info.setColumn39(obj[39]);
		info.setColumn40(obj[40]);
		info.setColumn41(obj[41]);
		info.setColumn42(obj[42]);
		info.setColumn43(obj[43]);
		info.setColumn44(obj[44]);
		info.setColumn45(obj[45]);
		info.setColumn46(obj[46]);
		info.setColumn47(obj[47]);
		info.setColumn48(obj[48]);
		info.setColumn49(obj[49]);
		info.setColumn50(obj[50]);
		info.setColumn51(obj[51]);
		info.setColumn52(obj[52]);
		info.setColumn53(obj[53]);
		info.setColumn54(obj[54]);
		info.setColumn55(obj[55]);
		info.setColumn56(obj[56]);
		info.setColumn57(obj[57]);
		info.setColumn58(obj[58]);
		info.setColumn59(obj[59]);
		info.setColumn60(obj[60]);
		info.setColumn61(obj[61]);
		info.setColumn62(obj[62]);
		info.setColumn63(obj[63]);
		info.setColumn64(obj[64]);
		info.setColumn65(obj[65]);
		info.setColumn66(obj[66]);
		info.setColumn67(obj[67]);
		info.setColumn68(obj[68]);
		info.setColumn69(obj[69]);
		info.setColumn70(obj[70]);
		info.setColumn71(obj[71]);
		info.setColumn72(obj[72]);
		info.setColumn73(obj[73]);
		info.setColumn74(obj[74]);
		info.setColumn75(obj[75]);
		info.setColumn76(obj[76]);
		info.setColumn77(obj[77]);
		info.setColumn78(obj[78]);
		info.setColumn79(obj[79]);
		info.setColumn80(obj[80]);
		info.setColumn81(obj[81]);
		info.setColumn82(obj[82]);
		info.setColumn83(obj[83]);
		info.setColumn84(obj[84]);
		info.setColumn85(obj[85]);
		info.setColumn86(obj[86]);
		info.setColumn87(obj[87]);
		info.setColumn88(obj[88]);
		info.setColumn89(obj[89]);
		info.setColumn90(obj[90]);
		info.setColumn91(obj[91]);
		info.setColumn92(obj[92]);
		info.setColumn93(obj[93]);
		info.setColumn94(obj[94]);
		info.setColumn95(obj[95]);
		info.setColumn96(obj[96]);
		info.setColumn97(obj[97]);
		info.setColumn98(obj[98]);
		info.setColumn99(obj[99]);
		info.setColumn100(obj[100]);
		info.setColumn101(obj[101]);
		info.setColumn102(obj[102]);
		info.setColumn103(obj[103]);
		enterpriseService.save(info);
	}

	//查询import_flow_info表最新导入记录
	public String queryNews(){
		StringBuilder sb = new StringBuilder();
		sb.append("select to_char(news) from(select max(to_number(a.news)) as news from import_flow_info a) ");
		Query query = enterpriseService.getSessions().createSQLQuery(sb.toString());
		String result = (String) query.uniqueResult();
		return result;
	} 
	
	//修改AccountInfo，保存FlowInfo实体信息
	public void updateInfo(AccountInfo accountInfo,int i,String userResult[][],String news) throws Exception{
		
		//自动提交
		SysParameter pageCode = SysParamHelper.getSysParamByCode(AccountForm.BLY_TYPE, AccountForm.BLY_ADMIN);
		List<Object[]> list = querySysUser(pageCode.getParameterValue());
		String id="";
		String name="";
		if(list != null && list.size() > 0){
			for (int j = 0; j < list.size(); j++) {
				if(j == 0){
					id   += 	list.get(j)[0].toString();
					name +=  list.get(j)[1].toString();
				}else {
					id   += 	"," + list.get(j)[0].toString();
					name +=  "," + list.get(j)[1].toString();
				}
			}
		}
		//达到财务付款时间  取消
		accountInfo.setPaymentTime(accountInfo.getReachSement());
		//自动提交无初审会计
//		accountInfo.setFirstTrial(visitor.getUserName());
//		accountInfo.setFirstTrialId(visitor.getUserId());
		accountInfo.setCollectionMenberId(id);
		accountInfo.setNoticeEndTime(new Date());
		accountInfo.setStatus(AccountCommon.ACCOUNT_STATUS_ZGJS);
		accountInfo.setSubmitDate(new Date());
		accountInfo.setCollectionMenber(name);
		accountInfo.setTrialFlat(AccountCommon.ACCOUNT_ACCOUNT_FLAT_YB);
		accountInfo.setCurrentLink(AccountCommon.CURRENT_LINK_XXBL);
		//修改报账单
		updateTrialAccount(accountInfo);
		
		List<ProblemList> problemList =  queryProblemList(accountInfo.getOrderId());
		if(problemList !=null ){
			for (int j = 0; j < problemList.size(); j++) {
				if(problemList.get(j).getEndTime()==null){
					//整改结束时间改为初审会计完成审批时间
					problemList.get(j).setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(userResult[i][8]));
					problemList.get(j).setStatus(AccountCommon.PROBLEM_STATUS_ZGTG);
					updateProblem(problemList.get(j), null);
				}
			}
			
		}
	
		
//		str="1";
		//2信息补录已办
		//accountInfo.setRecordFlat(AccountCommon.ACCOUNT_RECORD_FLAT);
		//accountInfo.setCurrentLink(AccountCommon.CURRENT_LINK_WC);
		if(StringUtils.isNotBlank(accountInfo.getRecordFlat()) && accountInfo.getRecordFlat().equals("5")){
			accountInfo.setRecordFlat("3");
			accountInfo.setCurrentLink(AccountCommon.CURRENT_LINK_WC);
		}else if(!accountInfo.getCurrentLink().equals(AccountCommon.CURRENT_LINK_WC)){
			accountInfo.setRecordFlat("4");
		}
		accountInfo.setRecordDate(new Date());
		
		//导入成功修改单据信息
		updateTrialAccount(accountInfo);
		
		FlowInfo info2 = new FlowInfo();
		info2.setNews(Integer.parseInt(news)+1+"");
		info2.setOrderId(userResult[i][0]);
		info2.setSubmiter(userResult[i][1]);				
		info2.setSubmiterDate(userResult[i][2]);
		info2.setSjlApprover(userResult[i][3]);
		info2.setSjlApproveDate(userResult[i][4]);
		info2.setBmldApprover(userResult[i][5]);
		info2.setBmldApproveDate(userResult[i][6]);
		info2.setCskjApprover(userResult[i][7]);
		info2.setCskjApproveDate(userResult[i][8]);
		info2.setSwkjApprover("");
		info2.setSwkjApproveDate("");
		info2.setZbkjApprover(userResult[i][9]);
		info2.setZbkjApproveDate(userResult[i][10]);
		info2.setCwjlApprover(userResult[i][11]);
		info2.setCwjlApproveDate(userResult[i][12]);
		//公司总经理.时间CF	公司总经理.审批人CG
		if(StringUtils.isNotEmpty(userResult[i][16])){
			info2.setNjldApprover(userResult[i][15]);
			info2.setNjldApproveDate(userResult[i][16]);
		}else{
			//筹建办公室财务部副总经理BR	筹建办公室财务部副总经理审批人BS
			info2.setNjldApprover(userResult[i][13]);
			info2.setNjldApproveDate(userResult[i][14]);
		}
		info2.setScwApprover(userResult[i][17]);
		info2.setScwApproveDate(userResult[i][18]);
		info2.setCnfkApprover(userResult[i][19]);
		info2.setCnfkApproveDate(userResult[i][20]);	
		//保存流程信息
		addFlowInfoBackId(info2);
		
	}
	
	/**
	 * 获取排序值
	 * @return
	 */
	public int getOrderNum(){
		int orderNum=0;
		QueryBuilder builder=new QueryBuilder(SysUser.class);
		builder.orderBy("orderNum", false);
		List<SysUser> list = (List<SysUser>)this.enterpriseService.query(builder, 0);
		if(list!=null && list.size()>0){
			orderNum=list.get(0).getOrderNum()+1;
		}
		return orderNum;
	}
	
	/**
	 * 导入流程信息-保存
	 */
	public void addFlowInfoBackId(FlowInfo info) throws Exception {
		enterpriseService.save(info);

	}
	
	
	
	/**
	  * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	  * @param file1 读取数据的源Excel
	  * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	  * @return 读出的Excel中数据的内容
	  * @throws FileNotFoundException
	  * @throws IOException
	  */
	public static String[][] getData(File file, int ignoreRows,int sheetIndex,String file1) throws FileNotFoundException, IOException {
    
    List<String[]> result = new ArrayList<String[]>();
    int rowSize = 0;
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file1));

    // 打开HSSFWorkbook
    POIFSFileSystem fs = new POIFSFileSystem(in);
    HSSFWorkbook wb = new HSSFWorkbook(fs);
    HSSFCell cell = null;

    HSSFSheet st = wb.getSheetAt(sheetIndex);
    HSSFRow row0 = st.getRow(1);
    int modelDataSize = row0.getLastCellNum();
    
    //过滤最后几行，如果为空
    int rowCount = st.getLastRowNum();
    lable1:while (rowCount>=0) {
   	 
   	 HSSFRow row = st.getRow(rowCount);
        if (row == null){
        	rowCount--;
            continue;
        }
        int colIndexCount = row.getLastCellNum();
        for (int i = 0; i < colIndexCount+1; i++) {
       	cell = row.getCell(i);
       	String value = "";
         	if (cell != null) {
        		// 注意：一定要设成这个，否则可能会出现乱码
        		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        		switch (cell.getCellType()) {
        		case HSSFCell.CELL_TYPE_STRING:
        			 value = cell.getStringCellValue();
        			 break;
        		case HSSFCell.CELL_TYPE_NUMERIC:
        		     if (HSSFDateUtil.isCellDateFormatted(cell)) {
        			     Date date = cell.getDateCellValue();
        				 if (date != null) {
        					 value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        				 } else {
        				     value = "";
        				 }
        			 } else {
        			     value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
        			     String[] valueTemp = value.split("\\.");
        			     if("00".equals(valueTemp[1])){
        			    	 value = valueTemp[0];
        			     }
        			 }
        			 break;
        		case HSSFCell.CELL_TYPE_FORMULA:
        			// 导入时如果为公式生成的数据则无值
        			 if (!cell.getStringCellValue().equals("")) {
        			 	 value = cell.getStringCellValue();
        			 } else {
        			     value = cell.getNumericCellValue() + "";
        			 }
        			 break;
        		case HSSFCell.CELL_TYPE_BLANK:
        			 break;
        		case HSSFCell.CELL_TYPE_ERROR:
        			 value = "";
        			 break;
        		case HSSFCell.CELL_TYPE_BOOLEAN:
        			 value = (cell.getBooleanCellValue() == true ? "Y": "N");
        			 break;
        		default:
        			 value = "";
        		}
        	}
         	
       	 if(StringUtils.isNotBlank(value)){ //假如有1列不为空，认为这行是有数据。
       		 break lable1;
       	 }
		 }
   	 rowCount--;
    }
    cell = null;
    
    
    // 过滤前ignoreRows，不取
    for (int rowIndex = ignoreRows; rowIndex <= /*st.getLastRowNum()*/ rowCount ; rowIndex++) {
        HSSFRow row = st.getRow(rowIndex);
        if (row == null){
            continue;
        }
        int tempRowSize = row.getLastCellNum() + 1;
        if (tempRowSize > rowSize) {
        	rowSize = tempRowSize;
        }
        if(rowSize < modelDataSize){//防止读取数据时数组越界
        	rowSize = modelDataSize;
        }
        String[] values = new String[rowSize];
        Arrays.fill(values, "");
        boolean hasValue = false;
        int[] colunmNO={1,//单据编号:报账单编号
        		//提交电子报账单
        		2,//报账人
        		7,//提单日期
        		//室经理审批	
        		34,//室经理/室副经理审批人
        		33,//室经理/室副经理时间	
        		//部门领导审批
        		38,//部门总经理审批人
        		37,//部门总经理时间
        		//初审会计审批
        		60,//初审会计审批人
        		59,//初审会计时间	
        		//主办会计审批
        		66,//计划财务室主办会计
        		65,//计划财务室主办会计时间
        		//财务经理审批
        		68,//财务部室经理审批人
        		67,//财务部室经理时间	
        		//南基领导审批
        		70,//筹建办公室财务部副总经理
        		69,//筹建办公室财务部副总经理审批人
        		84,//公司总经理.时间
        		83,//公司总经理.审批人
        		//省财务审批
        		86,//复审会计审批人
        		85,//复审会计时间
        		//出纳付款
        		96,//付款完成.审批人
        		95//付款完成	
        		};
        if(file1.contains("commonDic_")){
        	//mis流程模板取特定列
        	
        }
        //mis流程模板取特定列
        //int[] colunmNO={1,2,7,34,33,38,37,60,59,66,65,68,67,56,55,84,83,94,93};
        
        //for (int columnIndex = 1; columnIndex <= row.getLastCellNum(); columnIndex++) {
        for (int columnIndex = 0; columnIndex <colunmNO.length; columnIndex++) {
        	String value = "";
        	//cell = row.getCell(columnIndex);
        	cell = row.getCell(colunmNO[columnIndex]);
        	
        	if (cell != null) {
        		// 注意：一定要设成这个，否则可能会出现乱码
        		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        		switch (cell.getCellType()) {
        		case HSSFCell.CELL_TYPE_STRING:
        			 value = cell.getStringCellValue();
        			 break;
        		case HSSFCell.CELL_TYPE_NUMERIC:
        		     if (HSSFDateUtil.isCellDateFormatted(cell)) {
        			     Date date = cell.getDateCellValue();
        				 if (date != null) {
        					 value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        				 } else {
        				     value = "";
        				 }
        			 } else {
        			     value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
        			     String[] valueTemp = value.split("\\.");
        			     if("00".equals(valueTemp[1])){
        			    	 value = valueTemp[0];
        			     }
        			 }
        			 break;
        		case HSSFCell.CELL_TYPE_FORMULA:
        			// 导入时如果为公式生成的数据则无值
        			 if (!cell.getStringCellValue().equals("")) {
        			 	 value = cell.getStringCellValue();
        			 } else {
        			     value = cell.getNumericCellValue() + "";
        			 }
        			 break;
        		case HSSFCell.CELL_TYPE_BLANK:
        			 break;
        		case HSSFCell.CELL_TYPE_ERROR:
        			 value = "";
        			 break;
        		case HSSFCell.CELL_TYPE_BOOLEAN:
        			 value = (cell.getBooleanCellValue() == true ? "Y": "N");
        			 break;
        		default:
        			 value = "";
        		}
        	}
        	//if (columnIndex == 0 && value.trim().equals("")) {
        		//break;
        	//}
        	values[columnIndex] = rightTrim(value);
        	hasValue = true;
        }
        if (hasValue) {
        	result.add(values);
        }
    }
  
    in.close();
    String[][] returnArray = new String[result.size()][rowSize];
    for (int i = 0; i < returnArray.length; i++) {
    	returnArray[i] = (String[]) result.get(i);
    }
    
    return returnArray;
	}
	
	/**
	* 去掉字符串右边的空格
	* @param str 要处理的字符串
	* @return 处理后的字符串
	*/

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
			}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
				}
			length--;
			}
		return str.substring(0, length);
			
	}
	
	@Override
	public List<DicFailImportObject> saveData(SysUser visitor, String file,
			HttpServletRequest request) throws RuntimeException {
		List<DicFailImportObject> listObject=null;
		try {
			listObject=this.validateWare(visitor, file, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listObject;
	}
	
	
	public void delFlowInofById(String orderID){
		if(StringUtils.isEmpty(orderID)){
			return;
		}
		QueryBuilder query = new QueryBuilder(FlowInfo.class);
		query.where("orderId",orderID,QueryAction.EQUAL);
		enterpriseService.delete(query);
	}

	@Override
	public void updateTrialAccount(AccountInfo info) {
		enterpriseService.updateObject(info);
		
	}

	@Override
	public ItemPage findTrialAccountByidForm(String id,AccountForm accountForm) {	
		StringBuffer sb = new StringBuffer();
		sb.append(" select b.order_id,b.department,b.cost_type,b.sement_people,b.create_date,b.status,t.time,t.days,t.pblem,t.phase,t.ispblem,t.sumtime from account_tab b  ");
		sb.append("  left join  ( ");
		sb.append(" select  t.order_id,sum(t.time) as time,sum(t.days) as days,sum(t.pblem) as pblem,sum(t.ispblem) as ispblem,sum(t.sumtime) as sumtime ,phase  from ( ");
		sb.append(" select t.order_id,0 as time ,sum(days) as days,0 as pblem,0 as ispblem,0 as sumtime from( ");
		sb.append(" select t.order_id,max(f.OUT_DAY) as days from account_tab t left join problem_list f on t.order_id=f.account_order_id ");
		sb.append(" where    f.is_outtime ='1'   group by f.phase,t.order_id) t group by order_id ");
		sb.append("  union all  ");
		sb.append(" select t.order_id,count(f.id) as time,0 as days，0 as pblem,0 as ispblem,0 as sumtime from account_tab t left join problem_list f on t.order_id=f.account_order_id ");
		sb.append(" where  f.status!='"+AccountCommon.PROBLEM_STATUS_ZGTG+"' group by order_id  ");
		sb.append(" 	    union all ");
		sb.append(" select t.order_id,0 as time,0 as days,count(f.id) as pblem,0 as ispblem, 0 as sumtime from account_tab t left join problem_list f on t.order_id=f.account_order_id ");
		sb.append(" where  f.phase!='CSKJSH' group by order_id  ");
		sb.append(" union all ");
		sb.append(" select t.order_id,0 as time,0 as days,0 as pblem,count(f.id) as ispblem,0 as sumtime from account_tab t left join problem_list f on t.order_id=f.account_order_id ");
		sb.append(" group by order_id ");
		sb.append(" union all ");
		sb.append(" select order_id,0 as time,0 as days,0 as pblem,0 as ispblem,sum(setTime) sumtime from ( ");
		sb.append(" select t.order_id,0 as time,0 as days,0 as pblem,f.phase,sum(to_number(f.SET_TIME)) as setTime from account_tab t left join problem_list f on t.order_id=f.account_order_id ");      
		sb.append(" group by order_id,f.phase order by setTime)  group by order_id  ) t ");                            
		sb.append(" left join  ( ");
		sb.append(" select order_id,0 as time,0 as days,0 as pblem,phase,0 as ispblem, 0 as sumtime from ( ");
		sb.append(" select t.order_id,0 as time,0 as days,0 as pblem,f.phase,sum(to_number(f.SET_TIME)) as setTime from account_tab t left join problem_list f on t.order_id=f.account_order_id ");
		sb.append(" group by order_id,f.phase order by setTime) c where rownum=1 ");
		sb.append(" )  f  on f.order_id=t.order_id  group by t.order_id,phase  ");  
		sb.append(" ) t on b.order_id=t.order_id where "); 
		sb.append(" b.order_id='"+id+"'");
		ItemPage itemPage = enterpriseService.findBySql(sb.toString(), accountForm);
		return itemPage;
	}

	@Override
	public ItemPage queryFlowInfoList(FlowInfoForm flowInfoForm) {
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotEmpty(flowInfoForm.getOrderId())){
			sb.append("select a.* from import_flow_info a  where a.news is not null and a.order_id like '%"+flowInfoForm.getOrderId()+"%' order by a.news desc");
		}else{
			sb.append("select * from (select a.* from import_flow_info a  where a.news is not null order by a.news desc ");
			sb.append(" )  where news=(select max(to_number(a.news)) from import_flow_info a ) ");
		}
		ItemPage itemPage = enterpriseService.findBySql(sb.toString(),flowInfoForm);
		return itemPage;
	}
	
	@Override
	public ItemPage queryTaxInfoList(FlowInfoForm flowInfoForm) {
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotEmpty(flowInfoForm.getOrderId())){
			sb.append("select a.* from IMPORT_TAX_CONTRACT a  where a.sequence is not null and a.column_02 like '%"+flowInfoForm.getOrderId()+"%' order by a.sequence desc ");
		}else{
			sb.append("select * from (select a.* from IMPORT_TAX_CONTRACT a  where a.sequence is not null order by a.sequence desc ");
			sb.append(" )  where sequence=(select max(to_number(a.sequence)) from IMPORT_TAX_CONTRACT a ) ");
		}
		ItemPage itemPage = enterpriseService.findBySql(sb.toString(),flowInfoForm);
		return itemPage;
	}
	
	@Override
	public List<ProblemList> queryProblemList(String orderid) {
		QueryBuilder query = new QueryBuilder(ProblemList.class);
		query.where("accountOrderId",orderid,QueryAction.EQUAL);
		query.where("status", AccountCommon.PROBLEM_STATUS_CG,QueryAction.NOEQUAL);
		return (List<ProblemList>) enterpriseService.query(query, 0);
	}
	
	public List<FlowInfo> queryFloaList(){
		QueryBuilder query  = new QueryBuilder(FlowInfo.class);
		return (List<FlowInfo>) enterpriseService.query(query, 0);
	}
	
	public List<AccountInfo> queryAccountList(){
		QueryBuilder query  = new QueryBuilder(AccountInfo.class);
		return (List<AccountInfo>) enterpriseService.query(query, 0);
	}
	
	@Override
	public List<RulesType> queryCostType() throws Exception {
		QueryBuilder query = new QueryBuilder(RulesType.class);
		query.where("busTypes",BusTypes.busTypes_BZ,QueryAction.EQUAL);
		query.where("parentTypeId","ROOT",QueryAction.NOEQUAL);
		query.where("status","1");
		return (List<RulesType>) enterpriseService.query(query, 0);
	}
	
	@Override
	public List<AccountConfig> queryAccountById(String costTypeId, String depId) throws Exception {
		QueryBuilder query = new QueryBuilder(AccountConfig.class);
		query.where("cosId",costTypeId,QueryAction.EQUAL);
		query.where("departmentId",depId,QueryAction.EQUAL);
		return   (List<AccountConfig>) enterpriseService.query(query, 0);
	}

	@Override
	public RulesType queryCostTypeById(String costTypeid)
			throws Exception {
		return   (RulesType) enterpriseService.getById(RulesType.class, costTypeid);
	}

	@Override
	public void updateProblem(ProblemList problemList,SysUser user) {
		//rulesService.sendManagetask(RulesCommon.ACTION_ADD, user, "", null, RulesCommon.TASK_TYPE_DB, SysParamHelper.getSysParamByCode(RulesCommon.OA_URL, RulesCommon.COOPERATION_REMAIN_URL).getParameterValue(), toUserList, warningTime, recordId, handelstatus);
		enterpriseService.updateObject(problemList);
	}

	@Override
	public ProblemList queryProblem(String id) {
		QueryBuilder query = new QueryBuilder(ProblemList.class);
		query.where("id",id,QueryAction.EQUAL);
		List<ProblemList> list =  (List<ProblemList>) enterpriseService.query(query, 0);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public AccountInfo findTrialAccountByid(String id) {
		QueryBuilder query = new QueryBuilder(AccountInfo.class);
		if(id!=null && !id.equals("")){
			query.where("orderId",id,QueryAction.EQUAL);
		}else {
			query.where("orderId","007",QueryAction.EQUAL);
		}
		List<AccountInfo> list =  (List<AccountInfo>) enterpriseService.query(query, 0);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<AccountInfo> findTrialAccountTDTime(String id) {
		QueryBuilder query = new QueryBuilder(AccountInfo.class);
		query.where("orderId",id,QueryAction.LIKE);
		query.orderBy("tDTime", false);
		return (List<AccountInfo>) enterpriseService.query(query, 0);
	}

	@Override
	public List<Object[]> querySysUser(String pageCode) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct u.user_id,u.user_name from sys_role s left join Sys_Role_User r on(s.role_id=r.role_id) left join sys_user u ");
		sb.append(" on u.user_id=r.user_id where role_code='" + pageCode + "' and u.user_id is not null");
		Query qeury =  enterpriseService.getSessions().createSQLQuery(sb.toString());
		return qeury.list();
	}

	@Override
	public List<SysUser> queSysUserById(String id) {
		List<SysUser> list = new ArrayList<SysUser>();
		String[] userID = id.split(",");
		for (int i = 0; i < userID.length; i++) {
			SysUser sysuser = (SysUser) enterpriseService.getById(SysUser.class, userID[i]);
			if(sysuser!=null){
				list.add(sysuser);
			}
		}
		return list;
	}

	@Override
	public List<AccountInfo> queryExportData(String type, AccountForm accountForm,
			Map<String, String> map, SysUser visitor) {
		QueryBuilder query = new QueryBuilder(AccountInfo.class);
		query.where("to_char(paymentTime,'yyyy-mm')",accountForm.getReachSementStr());
		query.where("cosId",accountForm.getCosId());
		if(accountForm.getOnlyProblems() != null && accountForm.getOnlyProblems().equals("1")){
			query.where("status in ('1','3')");
		}else{
			if(StringUtils.isNotEmpty(accountForm.getStatus())){
				query.where("status",accountForm.getStatus());
			}
		}
		query.where("department",accountForm.getDepartment());
		query.where("orderId",accountForm.getOrderId(),QueryAction.LIKE);
		if(StringUtils.isNotEmpty(map.get("pstartDate"))){
			String pstartDate = " pageSubmitDate>=to_date('"+map.get("pstartDate")+"','yyyy-MM-dd hh24:mi:ss')";
			query.where(pstartDate);
		}
		if(StringUtils.isNotEmpty(map.get("pendDate"))){
			String pendDate = " pageSubmitDate<=to_date('"+map.get("pendDate")+"','yyyy-MM-dd hh24:mi:ss')";
			query.where(pendDate);
		}
		if(StringUtils.isNotBlank(accountForm.geteSDate())){
			query.where("to_char(pageSubmitDate,'yyyy-MM-dd')",accountForm.geteSDate(),QueryAction.GE);
		}
		if(StringUtils.isNotBlank(accountForm.geteEDate())){
			query.where("to_char(pageSubmitDate,'yyyy-MM-dd')",accountForm.geteEDate(),QueryAction.LE);
		}
		if(StringUtils.isNotEmpty(map.get("cstartDate"))){
			String cstartDate = " createDate>=to_date('"+map.get("cstartDate")+"','yyyy-MM-dd hh24:mi:ss')";
			query.where(cstartDate);
		}
		if(StringUtils.isNotEmpty(map.get("cendDate"))){
			String cendDate = " createDate<=to_date('"+map.get("cendDate")+"','yyyy-MM-dd hh24:mi:ss')";
			query.where(cendDate);
		}
		if(StringUtils.isNotEmpty(map.get("payStartDate"))){
			String payStartDate = " paymentTime>=to_date('"+map.get("payStartDate")+"','yyyy-MM-dd hh24:mi:ss')";
			query.where(payStartDate);
		}
		if(StringUtils.isNotEmpty(map.get("payEndDate"))){
			String payEndDate = " paymentTime<=to_date('"+map.get("payEndDate")+"','yyyy-MM-dd hh24:mi:ss')";
			query.where(payEndDate);
		}
		query.where("includeBuckle",accountForm.getIncludeBuckle());
		query.where("trialAccountId",accountForm.getTrialAccountId());
		query.where("currentLink",accountForm.getCurrentLink());
		query.where("sementPeopleId",accountForm.getSementPeopleId());
		
		if(StringUtils.isNotEmpty(map.get("people"))){
			String people = " (orderId like '%"+map.get("people")+"%' or  sementPeople like '%"+map.get("people")+"%') ";
			query.where(people);
		}
		
		if(StringUtils.isNotEmpty(accountForm.getAccids())){
			query.where(" id in ("+accountForm.getAccids()+") ");
		}
		
		if(type.equals("1")){
			query.where("userId",visitor.getUserId());
		}
		if(type.equals("2")){
			String str = "( collectionMenberId='"+visitor.getUserId()+"' or  firstActorUserId='"+visitor.getUserId()+"' or trialAccountId='"+visitor.getUserId()+"') ";
			query.where(str);
		}
		if(type.equals("3")){
			//管理员可查看所有单据
			String result = queryIsAdmin(visitor.getUserId());
			if( result==null || !result.equals(AccountCommon.ACCOUNT_ADMIN) || result.equals("")){
				result = "NOadmin";
				query.where("userId",result);
			}
		}
		return (List<AccountInfo>) enterpriseService.query(query, 0);
		
	}

	@Override
	public String queryHaoshiType(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select  type from ( ");
		sb.append(" select type,max(haoshi) from ( ");
		sb.append(" select f.type,sum(f.SET_TIME) as haoshi from account_tab t  ");
		sb.append(" left join problem_list f on t.order_id=f.account_order_id where t.order_id='"+id+"'  group by f.type  ");  
		sb.append(" ) group by type  order by max(haoshi) desc )  where rownum=1 ");
		Query qeury =  enterpriseService.getSessions().createSQLQuery(sb.toString());
		String result = (String) qeury.uniqueResult();
		return result;
	}

	@Override
	public List<Object[]> queryDepartment(String value) {
		value=value.toLowerCase();
        StringBuilder sql = new StringBuilder();
        sql.append(" select s.organization_id,s.org_name,s.parent_id,s.creater_id from  sys_organization s left join sys_organization p on s.parent_id=p.organization_id ");
		sql.append(" where s.organization_id !='80df8fa55ca4048ac2314dab1a52d75e' and Lower(s.org_name) like ? order by s.org_order ");

        Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
        query.setString(0,"%"+value+"%");
        return query.list();
		
	}

	@Override
	public List<FlowInfo> queryFlowInfoById(String id) throws Exception {
		QueryBuilder query = new QueryBuilder(FlowInfo.class);
		query.where("orderId",id);
		return (List<FlowInfo>) enterpriseService.query(query,0);
	}

	@Override
	public List<ComtreeTransitionDTO> getDepartment(String pid) {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("parentId",pid);
		List<SysOrganization> organizations = (List<SysOrganization>) enterpriseService.query(query, 0);
		List<ComtreeTransitionDTO> organizationDTO = new ArrayList<ComtreeTransitionDTO>();
		if(organizations!=null && organizations.size()>0){
			for (SysOrganization so : organizations) {
				ComtreeTransitionDTO dto = new ComtreeTransitionDTO();
				dto.setId(so.getOrganizationId());
				dto.setParent_id(so.getParentId());
				dto.setText(so.getOrgName());
				dto.setChecked(0);
				/*if(getChildren(so.getOrganizationId())!=null && getChildren(so.getOrganizationId()).size()>0){
					dto.setState("closed");
				}else {
					dto.setState("open");
				}*/
				organizationDTO.add(dto);
			}
		}
		return organizationDTO;
	}
	
	/**
	 *获取分类子节点
	 * @param id
	 * @return
	 */
	public List<SysOrganization> getChildren(String id) {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("parentId",id);
		return (List<SysOrganization>) enterpriseService.query(query, 0);
	}

	@Override
	public List<ComtreeTransitionDTO> getCostType(String id) {
		QueryBuilder query = new QueryBuilder(RulesType.class);
		query.where("parentTypeId",id);
		query.where("busTypes","BZ");
		query.where("status","1");
		query.where("");
		List<RulesType> rulesTypes = (List<RulesType>) enterpriseService.query(query, 0);
		List<ComtreeTransitionDTO> comtreeTransitionDTOs = new ArrayList<ComtreeTransitionDTO>();
		if(rulesTypes!=null && rulesTypes.size()>0){
			for(RulesType rt : rulesTypes){
				ComtreeTransitionDTO dto = new ComtreeTransitionDTO();
				dto.setId(rt.getTypeId());
				dto.setParent_id(rt.getParentTypeId());
				dto.setText(rt.getTypeName());
				dto.setChecked(0);
				if(getRulesTypesChilden(rt.getTypeId())!=null && getRulesTypesChilden(rt.getTypeId()).size()>0){
					dto.setState("closed");
				}else {
					dto.setState("open");
				}
				comtreeTransitionDTOs.add(dto);
			}
			
		}
		return comtreeTransitionDTOs;
	}
	
	//查询费用类型子类
	public List<RulesType> getRulesTypesChilden(String id){
		QueryBuilder query = new QueryBuilder(RulesType.class);
		query.where("parentTypeId",id);
		//增加条件，需查询子类型未删除的列表
		query.where("status","1");
		List<RulesType> rulesTypes = (List<RulesType>) enterpriseService.query(query, 0);
		return rulesTypes;
	}

	@Override
	public List<FlowInfo> queryAccountInfoById(String id) {
		QueryBuilder query = new QueryBuilder(FlowInfo.class); 
		query.where("orderId",id);
		return (List<FlowInfo>) enterpriseService.query(query, 0);
	}

	@Override
	public List<AccountConfig> checkTrialConfig(AccountConfig config) {
		QueryBuilder query = new QueryBuilder(AccountConfig.class);
		query.where("cosId",config.getCosId());
		query.where("departmentId",config.getDepartmentId());
		return (List<AccountConfig>) enterpriseService.query(query, 0);
	}

	@Override
	public List<AccountInfo> checkIsOutTime(String id) {
		QueryBuilder query = new QueryBuilder(AccountInfo.class);
		query.where("orderId",id.split(","));
		return (List<AccountInfo>) enterpriseService.query(query, 0);
	}

	@Override
	public String queryDepartmentById(String userid) {
		StringBuffer sb = new StringBuffer();
		sb.append("select  n.organization_id,n.org_name  from sys_user r left join sys_organization n ");
		sb.append(" on r.organization_id=n.organization_id where r.user_id='"+userid+"'");
	    Query qeury =  enterpriseService.getSessions().createSQLQuery(sb.toString());
	    
	    List<Object[]> result = qeury.list();
	    String str = "" ;
		if(result!=null && result.size()>0){
			for (Object[] obj : result) {
				str = obj[0].toString();
			}
		}
	    while(true){
	    	//如果等于2级部门跳出循环
	    	if(str.split(",")[0].equals("80df8fa55ca4048ac2314dab1a52d75e")){
	    		break;
	    	}
	    	Query query2 = enterpriseService.getSessions().createSQLQuery("select distinct n.parent_id,n.org_name,n.organization_id from  sys_organization n where n.organization_id='"+str.split(",")[0]+"'");
	    	List<Object[]> result2 = query2.list();
	    	if(result2!=null && result2.size()>0){
				for (Object[] obj : result2) {
					str = obj[0].toString();
					str = str +","+obj[1];
					str = str +","+obj[2];
				}
			}
	    }
		return str;
	}

	@Override
	public String queryTrialIsAdmin(String userid) {
		Query query = enterpriseService.getSessions().createSQLQuery("select l.role_code from sys_user u left join sys_role_user r on u.user_id=r.user_id " +
		" left join sys_role l  on l.role_id=r.role_id where u.user_id= '"+userid+"' and l.role_code='"+AccountCommon.ACCOUNT_ADMIN+"'");
        String result = (String) query.uniqueResult();
		return result;
	}
	
	/**
	 * 删除常用审批意见
	 */
	@Override
	public void deletePersonalOpinion(String personalOpinionId){
		QueryBuilder builder = new QueryBuilder(PersonalOpinion.class);
		builder.where("personalOpinionId", personalOpinionId);
		enterpriseService.delete(builder);
	}

	@Override
	public List<AccountInfo> listQueryAccount() {
		QueryBuilder query = new QueryBuilder(AccountInfo.class);
		query.where("status","4",QueryAction.NOEQUAL);
		query.where("currentLink","初审录入",QueryAction.EQUAL);
		return (List<AccountInfo>) enterpriseService.query(query, 0);
	}

	@Override
	public String queryAging(String orderid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select  calc_workday(a.reach_sement,to_date(d.SUBMITER_DATE,'yyyy-mm-dd hh24:mi:ss')) ,calc_workday(to_date(d.SUBMITER_DATE, 'yyyy-mm-dd hh24:mi:ss'),case when to_date(d.BMLDAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss') - a.page_submit_date > 0  then to_date(d.BMLDAPPROVE_DATE, 'yyyy-mm-dd hh24:mi:ss') else a.page_submit_date end ) from  account_tab a left join problem_list b on a.order_id=b.account_order_id ");
		sb.append(" left join rules_type c on a.cos_id=c.type_id left join import_flow_info d on a.order_id=d.order_id where a.RECORD_FLAT='2' and a.TDTIME='0'  and a.order_id='"+orderid+"' ");
		Query query = enterpriseService.getSessions().createSQLQuery(sb.toString());
		List<Object[]> result = query.list();
		String str = "" ;
		if(result!=null && result.size()>0){
			for (Object[] obj : result) {
				str = obj[0].toString();
				str = str +","+obj[1].toString();
			}
		}
		return str;
	}

	@Override
	public List<DicFailImportObject> saveSWData(SysUser visitor, String file) {
		//记录一条错误的信息
		List<DicFailImportObject> listobject= new ArrayList<DicFailImportObject>();//所有错误
		//从文件导入数据
		try {
			
			//批次
			Query query2 = enterpriseService.getSessions().createSQLQuery("select max(to_number(a.sequence)) as seq from IMPORT_TAX_CONTRACT a");
			BigDecimal result = (BigDecimal) query2.uniqueResult();
			
			List list = ExcelUtil.importFromExcel(new File(file),0);
			if(list != null && list.size() > 1){
				//数据从第六行开始
				for (int i = 5; i < list.size(); i++) {
					String[] strs = (String[]) list.get(i);
					
					//报账单号不能为空
					if(!StringUtils.isNotEmpty(strs[2])){
						DicFailImportObject dic = new DicFailImportObject();
						dic.setSheetIndex(0);
						dic.setRow(i + 1);
						dic.setFailReason("单据编号为空!");
						listobject.add(dic);
						continue;
					}
					//根据报账单号查询报账信息
					AccountInfo accountInfo = null;
					try {
						accountInfo = getAccountByid(strs[2]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(accountInfo == null){
						DicFailImportObject dic = new DicFailImportObject();
						dic.setSheetIndex(0);
						dic.setRow(i + 1);
						dic.setFailReason("该单据未录入!");
						listobject.add(dic);
						continue;
					}else{
						if(StringUtils.isNotBlank(accountInfo.getRecordFlat()) && accountInfo.getRecordFlat().equals("4")){
							accountInfo.setRecordFlat("3");
							accountInfo.setCurrentLink(AccountCommon.CURRENT_LINK_WC);
						}else if(!accountInfo.getCurrentLink().equals(AccountCommon.CURRENT_LINK_WC)){
							accountInfo.setRecordFlat("5");
						}
						enterpriseService.updateObject(accountInfo);
					}
					
					//初审提交人
//					if(accountInfo.getFirstTrial() == null ){
//						DicFailImportObject dic = new DicFailImportObject();
//						dic.setSheetIndex(0);
//						dic.setRow(i + 2);
//						dic.setFailReason("该单据初审未提交！");
//						listobject.add(dic);
//						continue;
//					}
					ImportTaxContract itc = null;
					
					boolean saveORupdate = false;
					//根据报账单号查询导入税务合同信息
					itc = queryImportTaxContractById(strs[2],strs[3],strs[4]);
					
					if(itc == null){
						//新增
						itc = new ImportTaxContract();
						saveORupdate = true;
					}
					
					itc.setColumn01(strs[1]);//序号
					itc.setColumn02(strs[2]);//报账单号
					itc.setColumn03(strs[3]);//发票代码
					itc.setColumn04(strs[4]);//发票号码/原凭证号
					itc.setColumn05(strs[5]);//开票内容/品目名称
					itc.setColumn06(strs[6]);//用途
					itc.setColumn07(strs[7]);//销方纳税人识别号
					itc.setColumn08(strs[8]);//开票日期/入库日期
					itc.setColumn09(strs[9]);//金额
					itc.setColumn10(strs[10]);//税额/实缴金额
					if("#DIV/0!".equals(strs[11]) || "#VALUE!".equals(strs[11])){

						strs[11] = "0";
						DicFailImportObject dic = new DicFailImportObject();
						dic.setSheetIndex(0);
						dic.setRow(i + 1);
						dic.setFailReason("税率计算有误！");
						listobject.add(dic);
						continue;
					
					}
					itc.setColumn11(strs[11]);//税率/单位税额
					itc.setColumn12(strs[12]);//认证日期
					itc.setColumn13(strs[13]);//增值税扣税凭证类型
					itc.setColumn14(strs[14]);//主体/缴款单位
					itc.setColumn15(strs[15]);//预算项目编号
					itc.setColumn16(strs[16]);//预算项目名称
					itc.setColumn17(strs[17]);//投资项目编号
					itc.setColumn18(strs[18]);//投资项目名称
					itc.setColumn19(strs[19]);//类别(投资类/成本类）
					itc.setColumn20(strs[20]);//归属部门
					itc.setColumn21(strs[21]);//备注/摘要
					itc.setCreateTime(new Date());//创建时间
					
					itc.setSequence(result == null ? 1L : result.longValue() + 1);
					itc.setAccountTabId(accountInfo.getId());//报账信息id
					
					if(saveORupdate){
						enterpriseService.save(itc);
					}else{
						enterpriseService.updateObject(itc);
					}
				}
			}else{
				DicFailImportObject dic = new DicFailImportObject();
				dic.setSheetIndex(0);
				dic.setRow(0);
				dic.setFailReason("至少要填入一条数据!");
				listobject.add(dic);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listobject;
	}

	@Override
	public ImportTaxContract queryImportTaxContractById(String orderid,String column03,String column04) {
		QueryBuilder query = new QueryBuilder(ImportTaxContract.class);
		query.where("column02",orderid);
		query.where("column03",column03);
		query.where("column04",column04);
		List <ImportTaxContract> listItc = (List<ImportTaxContract>) enterpriseService.query(query, 0);
		if(listItc != null && listItc.size() != 0){
			return listItc.get(0);
		}
		return null;
	}
	
	private boolean isNotDateFormat(String str){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str));
			c.get(Calendar.YEAR);
			System.out.println(c.get(Calendar.YEAR));
			if(c.get(Calendar.YEAR) > 9999){
				return true;
			}
			if(c.get(Calendar.YEAR) < -4713){
				return true;
			}
		} catch (ParseException e) {
			return true;
		}
		return false;
	}

	@Override
	public void saveInvoice(AccountInfo aInfo) {
		List<AccountInvoice> list = aInfo.getInvoice();
		List<AccountInvoice> saveList = new ArrayList<AccountInvoice>();
		if(list!=null){
			for(int i = 0; i < list.size(); i++){
				AccountInvoice invoice = list.get(i);
				if(StringUtils.isNotEmpty(invoice.getInvoiceCode()) && StringUtils.isNotEmpty(invoice.getInvoiceNum())){
					invoice.setParentId(aInfo.getId());
					saveList.add(invoice);
				}
			}
		}
		enterpriseService.save(saveList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountInvoice> getInvoiceList(String id) {
		if(StringUtils.isNotEmpty(id)){
			QueryBuilder builder = new QueryBuilder(AccountInvoice.class);
			builder.where("parentId", id, QueryAction.EQUAL);
			builder.orderBy("updateDate", false);
			return (List<AccountInvoice>) enterpriseService.query(builder, 0);
		}
		return null;
	}

	@Override
	public ItemPage queryInvoice(String type,AccountForm accountForm,Map<String,String> map, SysUser visitor) {
		Class<?>[] pojos = {AccountInfo.class, AccountInvoice.class};
		QueryBuilder builder = new QueryBuilder(pojos);
		builder.where("a.id=b.parentId");

		if(accountForm.getReachSementStr()!= null && !accountForm.getReachSementStr().equals("")){
			builder.where(" to_char(a.paymentTime,'yyyy-mm')='"+accountForm.getReachSementStr()+"' ");
		}
		if((accountForm.getCostType()!= null && !accountForm.getCostType().equals("")) || (accountForm.getCosId()!=null && !accountForm.getCosId().equals(""))){
			builder.where(" (a.costType ='"+accountForm.getCostType()+"' or a.cosId ='"+accountForm.getCosId()+"') ");
		}
		if(accountForm.getStatus()!= null && !accountForm.getStatus().equals("")){
			builder.where(" a.status ='"+accountForm.getStatus()+"' ");
		}
		if(accountForm.getDepartment()!= null && !accountForm.getDepartment().equals("")){
			builder.where(" a.department like '%"+accountForm.getDepartment()+"%' ");
		}
		if(accountForm.getOrderId()!= null && !accountForm.getOrderId().equals("")){
			builder.where(" a.orderId like '%"+accountForm.getOrderId()+"%' ");
		}
		//纸质提交财务时间
		if(StringUtils.isNotEmpty(map.get("pstartDate"))){
			builder.where(" a.pageSubmitDate >=to_date('"+map.get("pstartDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(StringUtils.isNotEmpty(map.get("pendDate"))){
			builder.where(" a.pageSubmitDate <= to_date('"+map.get("pendDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		//创建时间
		if(StringUtils.isNotEmpty(map.get("cstartDate"))){
			builder.where(" a.createDate >=to_date('"+map.get("cstartDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(StringUtils.isNotEmpty(map.get("cendDate"))){
			builder.where(" a.createDate <= to_date('"+map.get("cendDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		//送达财务付款时间
		if(StringUtils.isNotEmpty(map.get("payStartDate"))){
			builder.where(" a.paymentTime >=to_date('"+map.get("payStartDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(StringUtils.isNotEmpty(map.get("payEndDate"))){
			builder.where(" a.paymentTime <= to_date('"+map.get("payEndDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(accountForm.getIncludeBuckle()!= null && !accountForm.getIncludeBuckle().equals("")){
			builder.where(" a.includeBuckle ='"+accountForm.getIncludeBuckle()+"' ");
		}
		//专票抵扣
		if(accountForm.getDeduction()!= null && !accountForm.getDeduction().equals("")){
			builder.where(" a.deduction ='"+accountForm.getDeduction()+"' ");
		}
		if(accountForm.getTrialAccountId()!= null && !accountForm.getTrialAccountId().equals("")){
			builder.where(" a.trialAccountId like '%"+accountForm.getTrialAccountId()+"%' ");
		}
		if(accountForm.getCurrentLink()!= null && !accountForm.getCurrentLink().equals("")){
			builder.where(" a.currentLink ='"+accountForm.getCurrentLink()+"' ");
		}
		if(accountForm.getSementPeople()!=null && !accountForm.getSementPeople().equals("")){
			builder.where(" a.sementPeople ='"+accountForm.getSementPeople()+"' ");
		}
		if(accountForm.getSementPeopleId()!= null && !accountForm.getSementPeopleId().equals("")){
			builder.where(" ( a.sementPeopleId ='"+accountForm.getSementPeopleId()+"' or a.sementPeopleId='"+accountForm.getSementPeopleId()+"' ) ");
		}
		if(StringUtils.isNotEmpty(map.get("people"))){
			builder.where(" ( a.orderId like '%"+map.get("people")+"%' or  a.sementPeople like '%"+map.get("people")+"%') ");
		}
		
		if(StringUtils.isNotEmpty(accountForm.getAccids())){
			builder.where(" a.id in ("+accountForm.getAccids()+") ");
		}
		
		if(type.equals("1")){
			builder.where(" a.userId='"+visitor.getUserId()+"'");
		}
		if(type.equals("2")){
			builder.where("  ( a.collectionMenber='"+visitor.getUserName()+"' or  a.firstActorUserId='"+visitor.getUserId()+"' or a.trialAccountId='"+visitor.getUserId()+"') " );
		}
		if(type.equals("3")){
			//管理员可查看所有单据
			String result = queryIsAdmin(visitor.getUserId());
			if( result==null || !result.equals(AccountCommon.ACCOUNT_ADMIN) || result.equals("")){
				result = "NOadmin";
				builder.where(" a.userId = '"+result+"'");
			}else{
				builder.where(" a.userId != '"+result+"'");
			}
		}
		builder.orderBy("a.createDate", false);
		
		return enterpriseService.query(builder, accountForm);
	}
	
	@Override
	public List queryInvoiceList(String type,AccountForm accountForm,Map<String,String> map, SysUser visitor) {
		Class<?>[] pojos = {AccountInfo.class, AccountInvoice.class};
		QueryBuilder builder = new QueryBuilder(pojos);
		builder.where("a.id=b.parentId");

		if(accountForm.getReachSementStr()!= null && !accountForm.getReachSementStr().equals("")){
			builder.where(" to_char(a.paymentTime,'yyyy-mm')='"+accountForm.getReachSementStr()+"' ");
		}
		if((accountForm.getCostType()!= null && !accountForm.getCostType().equals("")) || (accountForm.getCosId()!=null && !accountForm.getCosId().equals(""))){
			builder.where(" (a.costType ='"+accountForm.getCostType()+"' or a.cosId ='"+accountForm.getCosId()+"') ");
		}
		if(accountForm.getStatus()!= null && !accountForm.getStatus().equals("")){
			builder.where(" a.status ='"+accountForm.getStatus()+"' ");
		}
		if(accountForm.getDepartment()!= null && !accountForm.getDepartment().equals("")){
			builder.where(" a.department like '%"+accountForm.getDepartment()+"%' ");
		}
		if(accountForm.getOrderId()!= null && !accountForm.getOrderId().equals("")){
			builder.where(" a.orderId like '%"+accountForm.getOrderId()+"%' ");
		}
		//纸质提交财务时间
		if(StringUtils.isNotEmpty(map.get("pstartDate"))){
			builder.where(" a.pageSubmitDate >=to_date('"+map.get("pstartDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(StringUtils.isNotEmpty(map.get("pendDate"))){
			builder.where(" a.pageSubmitDate <= to_date('"+map.get("pendDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		//创建时间
		if(StringUtils.isNotEmpty(map.get("cstartDate"))){
			builder.where(" a.createDate >=to_date('"+map.get("cstartDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(StringUtils.isNotEmpty(map.get("cendDate"))){
			builder.where(" a.createDate <= to_date('"+map.get("cendDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		//送达财务付款时间
		if(StringUtils.isNotEmpty(map.get("payStartDate"))){
			builder.where(" a.paymentTime >=to_date('"+map.get("payStartDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(StringUtils.isNotEmpty(map.get("payEndDate"))){
			builder.where(" a.paymentTime <= to_date('"+map.get("payEndDate")+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(accountForm.getIncludeBuckle()!= null && !accountForm.getIncludeBuckle().equals("")){
			builder.where(" a.includeBuckle ='"+accountForm.getIncludeBuckle()+"' ");
		}
		//专票抵扣
		if(accountForm.getDeduction()!= null && !accountForm.getDeduction().equals("")){
			builder.where(" a.deduction ='"+accountForm.getDeduction()+"' ");
		}
		if(accountForm.getTrialAccountId()!= null && !accountForm.getTrialAccountId().equals("")){
			builder.where(" a.trialAccountId like '%"+accountForm.getTrialAccountId()+"%' ");
		}
		if(accountForm.getCurrentLink()!= null && !accountForm.getCurrentLink().equals("")){
			builder.where(" a.currentLink ='"+accountForm.getCurrentLink()+"' ");
		}
		if(accountForm.getSementPeople()!=null && !accountForm.getSementPeople().equals("")){
			builder.where(" a.sementPeople ='"+accountForm.getSementPeople()+"' ");
		}
		if(accountForm.getSementPeopleId()!= null && !accountForm.getSementPeopleId().equals("")){
			builder.where(" ( a.sementPeopleId ='"+accountForm.getSementPeopleId()+"' or a.sementPeopleId='"+accountForm.getSementPeopleId()+"' ) ");
		}
		if(StringUtils.isNotEmpty(map.get("people"))){
			builder.where(" ( a.orderId like '%"+map.get("people")+"%' or  a.sementPeople like '%"+map.get("people")+"%') ");
		}
		
		if(StringUtils.isNotEmpty(accountForm.getAccids())){
			builder.where(" a.id in ("+accountForm.getAccids()+") ");
		}
		
		if(type.equals("1")){
			builder.where(" a.userId='"+visitor.getUserId()+"'");
		}
		if(type.equals("2")){
			builder.where("  ( a.collectionMenber='"+visitor.getUserName()+"' or  a.firstActorUserId='"+visitor.getUserId()+"' or a.trialAccountId='"+visitor.getUserId()+"') " );
		}
		if(type.equals("3")){
			//管理员可查看所有单据
			String result = queryIsAdmin(visitor.getUserId());
			if( result==null || !result.equals(AccountCommon.ACCOUNT_ADMIN) || result.equals("")){
				result = "NOadmin";
				builder.where(" a.userId = '"+result+"'");
			}else{
				builder.where(" a.userId != '"+result+"'");
			}
		}
		builder.orderBy("a.createDate", false);
		
		return enterpriseService.query(builder, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AccountInvoice getInvoiceById(String id) {
		QueryBuilder builder = new QueryBuilder(AccountInvoice.class);
		builder.where("id",id,QueryAction.EQUAL);
		List<AccountInvoice> list = (List<AccountInvoice>) enterpriseService.query(builder, 0);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveInvoice(AccountInvoice invoice) {
		enterpriseService.save(invoice);
	}

	@Override
	public void updateInvoice(AccountInvoice invoice) {
		if(invoice != null){
			enterpriseService.updateObject(invoice);
		}
	}

	@Override
	public long batchDelInvoice(String[] ids) {
		QueryBuilder builder = new QueryBuilder(AccountInvoice.class);
		builder.where("id", ids);
		try {
			return enterpriseService.delete(builder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<Object[]> searchDepartmenByName(String code) {
		code = code.toLowerCase();
		String sql = "select s.organization_id,s.org_name from sys_organization s where s.PARENT_ID ='80df8fa55ca4048ac2314dab1a52d75e' and s.org_name like ?";
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
        query.setString(0,"%"+code+"%");
		return query.list();
	}

	@Override
	public List<ProblemList> queryProblemList(String accountOrderId, String status) {
		QueryBuilder query = new QueryBuilder(ProblemList.class);
		query.where("accountOrderId",accountOrderId,QueryAction.EQUAL);
		query.where("status",status,QueryAction.EQUAL);
		return (List<ProblemList>) enterpriseService.query(query, 0);
	}

	@Override
	public void delProblemList(String orderid,String userId, String status) {
		// TODO Auto-generated method stub
		QueryBuilder query = new QueryBuilder(ProblemList.class);
		query.where("accountOrderId",orderid,QueryAction.EQUAL);
		query.where("createId",userId,QueryAction.EQUAL);
		query.where("status",status,QueryAction.EQUAL);
		enterpriseService.delete(query);
	}

	@Override
	public List<ProblemList> queryProblemList(String orderid, String userId,
			String status) {
		QueryBuilder query = new QueryBuilder(ProblemList.class);
		query.where("accountOrderId",orderid,QueryAction.EQUAL);
		query.where("createId",userId,QueryAction.EQUAL);
		query.where("status",status,QueryAction.EQUAL);
		return (List<ProblemList>) enterpriseService.query(query, 0);
	}

	@Override
	public String queryWtzgSum(String orderid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select calc_workday(min(b.start_time),max(b.end_time)) from problem_list b where b.account_order_id ='"+orderid+"' ");
		Query query = enterpriseService.getSessions().createSQLQuery(sb.toString());
		return  query.list().get(0).toString();
	}

	@Override
	public List<ImportTaxContract> queryImportTaxContractListByOrderId(String orderid) {
		QueryBuilder query = new QueryBuilder(ImportTaxContract.class);
		query.where("column02",orderid);
		return (List<ImportTaxContract>) enterpriseService.query(query, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountInfo> queryExportDate(String ids) {
		QueryBuilder query = new QueryBuilder(AccountInfo.class);
		List<AccountInfo> list = new ArrayList<AccountInfo>();
		if(StringUtils.isNotBlank(ids)){
			query.where("id in ("+ids+")");
			list = (List<AccountInfo>) enterpriseService.query(query, 0);
		}
		return list;
	}
	
}
