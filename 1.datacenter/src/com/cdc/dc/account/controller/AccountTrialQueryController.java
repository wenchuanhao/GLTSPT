package com.cdc.dc.account.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysHolidayInfo;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.common.properties.DCConfig;
import com.cdc.dc.account.common.AccountCommon;
import com.cdc.dc.account.form.AccountForm;
import com.cdc.dc.account.model.AccountInfo;
import com.cdc.dc.account.model.AccountInvoice;
import com.cdc.dc.account.model.FlowInfo;
import com.cdc.dc.account.model.ImportTaxContract;
import com.cdc.dc.account.model.ProblemList;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.inter.client.ws.mail.service.IMailService;
import com.cdc.inter.client.ws.sms.service.ISmsService;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.service.IHolidaysService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.trustel.common.ItemPage;

/**
 * 
 * @author xms
 *初审录入
 */
@Controller
@RequestMapping(value="account/")
public class AccountTrialQueryController extends CommonController{
	@Autowired
	private IAccountService accService;
	
	@Autowired
    private IHolidaysService holidayService;
	
	@Autowired
	private ISmsService smsService;
	@Autowired
	private IMailService mailService;
	
	@Autowired
	private IRulesService rulesService;
	
	private AccountCommon comon = new AccountCommon();

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
    @ModelAttribute
	public void doBeforeCall(HttpServletRequest request,AccountInfo accountInfo,AccountForm accountForm) throws Exception{
    	
    }
    
	//初审录入查询
	@RequestMapping(value="accountTrialQuery" , method = {RequestMethod.POST,RequestMethod.GET})
	public String accountTrialQuery(HttpServletRequest request,AccountForm accountForm) throws Exception{
		SysUser visitor=this.getVisitor(request);
		request.setAttribute("accountForm",accountForm);
		List<RulesType> rulelist = accService.queryCostType();
		request.setAttribute("rulesList", rulelist);
		String type = request.getParameter("type");// 1初审处理   2历史处理结果
		if(type == null || type.equals("")){
			type = "1";
		}
		
		String people = request.getParameter("people");
		request.setAttribute("people", people);
		
		ItemPage itemPage;
		try {
			itemPage =(ItemPage)accService.accountTrialQuery(accountForm,type,visitor,people);
			request.setAttribute("type", type);	
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dc/account/trialAccount";
	}
	
	//检查报账单是否超时
	@RequestMapping(value="checkIsOutTime" , method = {RequestMethod.POST,RequestMethod.GET})
	public void checkIsOutTime(String orderId,HttpServletRequest request, HttpServletResponse response) throws IOException{
		String result = "1";
		List<AccountInfo> list = accService.checkIsOutTime(orderId);
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(!"1".equals(list.get(i).getIstimeOut())){
					result = "0";
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	}
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportProblems", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportProblems(HttpServletRequest request, HttpServletResponse response,AccountForm accountForm) throws Exception{
		
			/*SysUser visitor=this.getVisitor(request);
			String type = request.getParameter("type");// 1初审处理   2历史处理结果
			if(type == null || type.equals("")){
				type = "1";
			}
			accountForm.setOnlyProblems("1");
			accountForm.seteSDate(accountForm.geteSDate().trim());
			accountForm.seteEDate(accountForm.geteEDate().trim());
			String people = request.getParameter("people");
			request.setAttribute("people", people);
			
			List<AccountInfo> list = (List<AccountInfo>) accService.exportProblems(accountForm,type,visitor,people);
			//导出
			doExport(list,response,true);*/
			
			SysUser visitor=this.getVisitor(request);
			request.setAttribute("accountForm",accountForm);
			List<RulesType> rulelist = accService.queryCostType();
			request.setAttribute("rulesList", rulelist);
			String type = request.getParameter("type");// 1初审处理   2历史处理结果
			if(type == null || type.equals("")){
				type = "1";
			}
			
			String people = request.getParameter("people");
			request.setAttribute("people", people);
			
			ItemPage itemPage;
			try {
				//itemPage =(ItemPage)accService.accountTrialQuery(accountForm,type,visitor,people);
				//List<Object[]> objs = (List<Object[]>) itemPage.getItems();
				List<Object[]> objs = accService.accountTrialQueryList(accountForm,type,visitor,people);
				String ids = "";
				for(int i=0;i<objs.size();i++){
					ids += "'" + objs.get(i)[9] + "'"; 
					if(i != objs.size()-1){
						ids += ",";
					}
				}
				List<AccountInfo> res = accService.queryExportDate(ids);
				//导出
				doExport(res,response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	//初审导出
	@RequestMapping(value="toExportTrial" , method = {RequestMethod.POST,RequestMethod.GET})
	public void toExportTrial(AccountForm accountForm,SysUser user,HttpServletRequest request, HttpServletResponse response ,String requireIds) throws Exception{
		SysUser visitor=this.getVisitor(request);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Map<String,String> map = new HashMap();
		if(request.getParameter("reachSement")!=null && !request.getParameter("reachSement").trim().equals("")){
			request.setAttribute("reachSement", request.getParameter("reachSement"));
		}
		String pstartDate = request.getParameter("pstartDate");
		String pendDate = request.getParameter("pendDate");
		if(StringUtils.isNotEmpty(pstartDate)){
			map.put("pstartDate", pstartDate);
			request.setAttribute("pstartDate", sf.parse(pstartDate) );
		}
		if(StringUtils.isNotEmpty(pendDate)){
			map.put("pendDate", pendDate);
			request.setAttribute("pendDate", sf.parse(pendDate) );
		}
		String cstartDate = request.getParameter("cstartDate");
		String cendDate = request.getParameter("cendDate");
		if(StringUtils.isNotEmpty(cstartDate)){
			map.put("cstartDate", cstartDate);
			request.setAttribute("cstartDate", sf.parse(cstartDate));
		}
		if(StringUtils.isNotEmpty(cendDate)){
			map.put("cendDate", cendDate);
			request.setAttribute("cendDate", sf.parse(cendDate));
		}
		String payStartDate = request.getParameter("payStartDate");
		String payEndDate = request.getParameter("payEndDate");
		if(StringUtils.isNotEmpty(payStartDate)){
			map.put("payStartDate", payStartDate);
			request.setAttribute("payStartDate", sf.parse(payStartDate));
		}
		if(StringUtils.isNotEmpty(payEndDate)){
			map.put("payEndDate", payEndDate);
			request.setAttribute("payEndDate", sf.parse(payEndDate));
		}
		
		String people = request.getParameter("people");
		map.put("people", people);
		request.setAttribute("people", people);
		
		//type 1我创建的报账单   2我处理的报账单据  3所有单据
		String type=request.getParameter("type");
		if(type ==null || type.trim().equals("")){
			type="1";
		}
		String accids = request.getParameter("accids");
		accountForm.setAccids(accids);
		accountForm.setOnlyProblems("1");
		
		List<AccountInfo> list = (List<AccountInfo>) accService.queryExportData(type,accountForm ,map, visitor);
		//导出
		doExport(list,response,true);
	}
	
	//初审导出
	@RequestMapping(value="toExportTrial2" , method = {RequestMethod.POST,RequestMethod.GET})
	public void toExportTrial2(HttpServletRequest request, HttpServletResponse response, AccountForm accountForm) throws Exception{
		SysUser visitor=this.getVisitor(request);
		//ItemPage itemPage = common(request,accountForm,visitor,"0");
		
		//List list = commonList(request,accountForm,visitor,"0");
		
		List<Object[]> objs = commonList(request,accountForm,visitor,"0");
		String ids = "";
		for(int i=0;i<objs.size();i++){
			ids += "'" + objs.get(i)[10] + "'"; 
			if(i != objs.size()-1){
				ids += ",";
			}
		}
		List<AccountInfo> res = accService.queryExportDate(ids);
		//导出
		doExport(res,response,true);
	}
	
	private ItemPage common(HttpServletRequest request,AccountForm accountForm, SysUser visitor,String fromMethod) throws Exception{
		request.setAttribute("accountForm", accountForm);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM");
		
		if(accountForm.getReachSementStr()!=null && !accountForm.getReachSementStr().trim().equals("")){
			request.setAttribute("reachSementStr", sft.parse(accountForm.getReachSementStr()));
		}
		
		Map<String,String> map = new HashMap();
		if(request.getParameter("reachSement")!=null && !request.getParameter("reachSement").trim().equals("")){
			request.setAttribute("reachSement", request.getParameter("reachSement"));
		}
		String pstartDate = request.getParameter("pstartDate");
		String pendDate = request.getParameter("pendDate");
		if(StringUtils.isNotEmpty(pstartDate)){
			map.put("pstartDate", pstartDate);
			request.setAttribute("pstartDate", sf.parse(pstartDate) );
		}
		if(StringUtils.isNotEmpty(pendDate)){
			map.put("pendDate", pendDate);
			request.setAttribute("pendDate", sf.parse(pendDate) );
		}
		String cstartDate = request.getParameter("cstartDate");
		String cendDate = request.getParameter("cendDate");
		if(StringUtils.isNotEmpty(cstartDate)){
			map.put("cstartDate", cstartDate);
			request.setAttribute("cstartDate", sf.parse(cstartDate));
		}
		if(StringUtils.isNotEmpty(cendDate)){
			map.put("cendDate", cendDate);
			request.setAttribute("cendDate", sf.parse(cendDate));
		}
		String payStartDate = request.getParameter("payStartDate");
		String payEndDate = request.getParameter("payEndDate");
		if(StringUtils.isNotEmpty(payStartDate)){
			map.put("payStartDate", payStartDate);
			request.setAttribute("payStartDate", sf.parse(payStartDate));
		}
		if(StringUtils.isNotEmpty(payEndDate)){
			map.put("payEndDate", payEndDate);
			request.setAttribute("payEndDate", sf.parse(payEndDate));
		}
		
		String people = request.getParameter("people");
		map.put("people", people);
		request.setAttribute("people", people);
		
		//type 1我创建的报账单   2我处理的报账单据  3所有单据
		String type=request.getParameter("type");
		if(type ==null || type.trim().equals("")){
			type="1";
		}
		
		request.setAttribute("type", type);
		ItemPage itemPage = null;
		if("0".equals(fromMethod)){
			itemPage = accService.queryAccountItemPage(type,accountForm ,map, visitor);
		}else if("1".equals(fromMethod)){
			itemPage = accService.queryInvoice(type,accountForm ,map, visitor);
		}
		return itemPage;
	}
	
	private List commonList(HttpServletRequest request,AccountForm accountForm, SysUser visitor,String fromMethod) throws Exception{
		request.setAttribute("accountForm", accountForm);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM");
		
		if(accountForm.getReachSementStr()!=null && !accountForm.getReachSementStr().trim().equals("")){
			request.setAttribute("reachSementStr", sft.parse(accountForm.getReachSementStr()));
		}
		
		Map<String,String> map = new HashMap();
		if(request.getParameter("reachSement")!=null && !request.getParameter("reachSement").trim().equals("")){
			request.setAttribute("reachSement", request.getParameter("reachSement"));
		}
		String pstartDate = request.getParameter("pstartDate");
		String pendDate = request.getParameter("pendDate");
		if(StringUtils.isNotEmpty(pstartDate)){
			map.put("pstartDate", pstartDate);
			request.setAttribute("pstartDate", sf.parse(pstartDate) );
		}
		if(StringUtils.isNotEmpty(pendDate)){
			map.put("pendDate", pendDate);
			request.setAttribute("pendDate", sf.parse(pendDate) );
		}
		String cstartDate = request.getParameter("cstartDate");
		String cendDate = request.getParameter("cendDate");
		if(StringUtils.isNotEmpty(cstartDate)){
			map.put("cstartDate", cstartDate);
			request.setAttribute("cstartDate", sf.parse(cstartDate));
		}
		if(StringUtils.isNotEmpty(cendDate)){
			map.put("cendDate", cendDate);
			request.setAttribute("cendDate", sf.parse(cendDate));
		}
		String payStartDate = request.getParameter("payStartDate");
		String payEndDate = request.getParameter("payEndDate");
		if(StringUtils.isNotEmpty(payStartDate)){
			map.put("payStartDate", payStartDate);
			request.setAttribute("payStartDate", sf.parse(payStartDate));
		}
		if(StringUtils.isNotEmpty(payEndDate)){
			map.put("payEndDate", payEndDate);
			request.setAttribute("payEndDate", sf.parse(payEndDate));
		}
		
		String people = request.getParameter("people");
		map.put("people", people);
		request.setAttribute("people", people);
		
		//type 1我创建的报账单   2我处理的报账单据  3所有单据
		String type=request.getParameter("type");
		if(type ==null || type.trim().equals("")){
			type="1";
		}
		
		request.setAttribute("type", type);
		List list = null;
		if("0".equals(fromMethod)){
			list = accService.queryAccountList(type,accountForm ,map, visitor);
		}else if("1".equals(fromMethod)){
			list = accService.queryAccountList(type,accountForm ,map, visitor);
		}
		return list;
	}
	
	//通知整改超时跳转
	@RequestMapping(value="noticeOutTime" , method = {RequestMethod.POST,RequestMethod.GET})
	public String noticeOutTime(SysUser user,HttpServletRequest request){
		String orderId=request.getParameter("orderId");
		request.setAttribute("orderId", orderId);
		return "dc/account/noticeOutTime";
	}
	
	//通知整改超时处理
	@RequestMapping(value="noticeOutTimeDo" , method = {RequestMethod.POST,RequestMethod.GET})
	public void noticeOutTimeDo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SysUser user = this.getVisitor(request);
		String orderId=request.getParameter("orderId");
		String noticType=request.getParameter("noticType");
		request.setAttribute("orderId", orderId);
		AccountInfo info = accService.findTrialAccountByid(orderId);
		if(info!=null && info.getNoticeTime()!=null){
			info.setNoticeTime(new Date());
			accService.updateTrialAccount(info);
		}
		
		//根据ID查找问题列表
		List<ProblemList> problemList = accService.queryProblemList(orderId);
		if(problemList!=null && problemList.size()>0){
			for (int i = 0; i < problemList.size(); i++) {
				if(problemList.get(i).getIsOutTime().equals("1")){
					if(problemList.get(i).getNoticeOutTime()!=null){
						problemList.get(i).setNoticeOutTime(new Date());
					}
					
					//整改中的通知整改超时
					if(AccountCommon.PROBLEM_STATUS_ZGZ.equals(problemList.get(i).getStatus())){
						problemList.get(i).setRemindWay(noticType);
						sendMsg(info,user, problemList.get(i));
					}
					accService.updateProblem(problemList.get(i), user);
				}
				
			}
		}
		
		PrintWriter out = response.getWriter();
		out.print("1");
	}
	
	//初审提交跳转
	@RequestMapping(value="trailSubmit",method={RequestMethod.POST,RequestMethod.GET})
	public  String trailSubmit(HttpServletRequest request,AccountInfo accountInfo,AccountForm accountForm) throws ParseException{
		SysUser visitor=this.getVisitor(request); 
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		SysParameter pageCode = SysParamHelper.getSysParamByCode(AccountForm.BLY_TYPE, AccountForm.BLY_ADMIN);
		List<Object[]> list = accService.querySysUser(pageCode.getParameterValue());
		String id="";
		String name="";
		if(list.size()!=0){
			Object obj[];
			for (int i = 0; i < list.size(); i++) {
					obj =  list.get(i);
					if(i==list.size()-1){
						id   += 	obj[0].toString();
						name +=  obj[1].toString();
					}else {
						id   += 	obj[0].toString()+",";
						name +=  obj[1].toString()+",";
					}
			}
		}
		
		//报账单号
		String orderId = request.getParameter("orderId");
		request.setAttribute("orderId", orderId);
		//接单信息
		AccountInfo info = accService.findTrialAccountByid(orderId);
		request.setAttribute("info", info);
		
		//节假日信息
		List<SysHolidayInfo> sysHolidayInfos = AccountCommon.getHoidlist(info.getCreateDate());
		
		ItemPage infos = accService.findTrialAccountByidForm(orderId,accountForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, infos);
		
		//根据报账ID查问题列表
		List<ProblemList> problemList =  accService.queryProblemList(orderId);
		String sysdate = dateFormat.format(new Date());
		double proHaoshiSum = 0;//问题总耗时
		
		if(problemList!=null){
			//循环问题列表比较问题整改是否超时
			for (int i = 0; i < problemList.size(); i++) {
				if(problemList.get(i).getCreateTime()!=null){
					String startDate = dateFormat.format(problemList.get(i).getCreateTime());
					//超时计算
					double date2 =0;
					if(problemList.get(i).getEndTime()!=null){
						date2 = comon.getDate(problemList.get(i).getCreateTime(),problemList.get(i).getEndTime(),sysHolidayInfos);
					}else {
						date2 = comon.getDate(problemList.get(i).getCreateTime(),new Date(),sysHolidayInfos);
					}
					
					//耗时天数
					BigDecimal a = new BigDecimal(date2/(double)24);
					double f2 = a.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					//求耗时总天数
					proHaoshiSum = proHaoshiSum+f2;
					BigDecimal c = new BigDecimal(proHaoshiSum);
					proHaoshiSum = c.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
			}
		}
		//查找最耗时问题类型
		String haoshiType = accService.queryHaoshiType(orderId);
		request.setAttribute("haoshiType", haoshiType);
		request.setAttribute("infoId", id);
		request.setAttribute("proHaoshiSum", proHaoshiSum);
		request.setAttribute("infoName", name);
		return "dc/account/trialSubmit";	
	}
	
	//整改审核跳转
	@RequestMapping(value="accountApprove",method={RequestMethod.GET,RequestMethod.POST})
	public String accountApprove(HttpServletRequest request,HttpServletResponse response){
		String orderId = request.getParameter("orderId");
		request.setAttribute("problemID",orderId);
		String approve = request.getParameter("approve");
		//根据ID查找问题列表
		ProblemList problemList = accService.queryProblem(orderId);
		request.setAttribute("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(problemList.getCreateTime()));
		return "dc/account/accountApprove";
	}
	
	//审核
	@RequestMapping(value="ajaxApprove",method={RequestMethod.GET,RequestMethod.POST})
	public void ajaxApprove(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String orderId = request.getParameter("orderId");
		request.setAttribute("problemID",orderId);
		String approve = request.getParameter("approve");
		String nopassReason = request.getParameter("nopassReason");
		String endTime = request.getParameter("endTime");
		SysUser visitor = this.getVisitor(request);
		//根据ID查找问题
		ProblemList problem = accService.queryProblem(orderId);
		List<ProblemList> problemList = accService.queryProblemList(problem.getAccountOrderId());
		PrintWriter out = response.getWriter();
		problem.setRemindWay(request.getParameter("remindWay"));
		
		if(problem!=null){
			if(!problem.getStatus().equals(AccountCommon.PROBLEM_STATUS_ZGTG)){
				//approve如果不为空，整改处理
				if(approve!=null && !approve.trim().equals("")){
					AccountInfo info = accService.findTrialAccountByid(problem.getAccountOrderId());
					List<AccountInfo> info2 = accService.findTrialAccountTDTime(problem.getAccountOrderId());
					if(approve.equals("tg")){
						problem.setStatus(AccountCommon.PROBLEM_STATUS_ZGTG);
						//设置状态整改结束
						List<ProblemList> zgtgList = accService.queryProblemList(problem.getAccountOrderId(),AccountCommon.PROBLEM_STATUS_ZGTG);
						if(zgtgList.size() == problemList.size()){
							info.setStatus(AccountCommon.ACCOUNT_STATUS_ZGJS);
						}
						out.write("1");
					}else if(approve.equals("td")){
						problem.setStatus(AccountCommon.PROBLEM_STATUS_TD);
						String s = info2.get(0).gettDTime();
						int i = Integer.parseInt(s)+1 ;
						info.settDTime(""+i+"");
						info.setOrderId(info.getOrderId()+"-"+i+"");
						//设置状态退单
						info.setStatus(AccountCommon.ACCOUNT_STATUS_TD);
						if(problemList!=null){
							for (int j = 0; j < problemList.size(); j++) {
								//退单后修改问题列表报账ID
								problemList.get(j).setAccountOrderId(info.getOrderId());
								accService.updateProblem(problemList.get(j), null);
							}
						}
						//通知整改退单
						sendMsg(info,visitor, problem);
						out.write("3");
					}else {
						problem.setStatus(AccountCommon.PROBLEM_STATUS_ZGBTG);
						//设置状态整改结束
//						info.setStatus(AccountCommon.ACCOUNT_STATUS_ZGJS); //整改不通过并不会整改结束啊!
						//通知整改不通过
						sendMsg(info,visitor, problem);
						out.write("1");
					}
					info.setNoPassReason(nopassReason);
					
					//整改结束时间
					try {
						Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
						problem.setEndTime(endDate);
						info.setNoticeEndTime(endDate);
					} catch (ParseException e) {
						info.setNoticeEndTime(new Date());
						problem.setEndTime(new Date());
					}
					problem.setNopassReason(nopassReason);
					accService.updateTrialAccount(info);
					accService.updateProblem(problem, null);
				}
			}else {
				out.write("0");
			}
		}
	}
	/**
	 * 发送整改通知短信
	 * @param info
	 * @param visitor
	 * @param problem
	 */
	private void sendMsg(AccountInfo info, SysUser visitor, ProblemList problem) {

		//获取报账人信息
		SysUser fUser = (SysUser) rulesService.getEntity(SysUser.class, info.getSementPeopleId());
		
		RulesType rulesType =  (RulesType) rulesService.getEntity(RulesType.class, info.getCosId());
        //发短信
		if("1".equals(problem.getRemindWay()) && null != fUser.getMobile() && "Y".equals(DCConfig.getProperty("A_SMS"))){
			String msg ="您提交的报账单（"+info.getOrderId()+"）需要整改，请及时与计划财务室相关同事（"+visitor.getUserName()+visitor.getMobile()+"）联系，以确保及时报账。";
			smsService.sendMessage(fUser.getMobile(), msg);
		}
		//发邮件
		if("0".equals(problem.getRemindWay()) && null != fUser.getEmail() && "Y".equals(DCConfig.getProperty("A_MAIL"))){
			
			List<String> list = new ArrayList<String>();
			list.add(fUser.getEmail());
			String content = "报账单:"+info.getOrderId()+",问题详情:"+problem.getDetail()+",请在["+rulesType.getWorkDay()+"天]内整改 。";
			mailService.SendMail(list, null, "报账单问题", content, visitor.getAccount());
		}
		//短信邮件都发
		if("1,0".equals(problem.getRemindWay())){ 
			if( null != fUser.getMobile() && "Y".equals(DCConfig.getProperty("A_SMS"))){
				String msg ="您提交的报账单（"+info.getOrderId()+"）需要整改，请及时与计划财务室相关同事（"+visitor.getUserName()+visitor.getMobile()+"）联系，以确保及时报账，详见邮件。";
				smsService.sendMessage(fUser.getMobile(), msg);
			}
			if( null != fUser.getEmail() && "Y".equals(DCConfig.getProperty("A_MAIL"))){
				
				List<String> list = new ArrayList<String>();
				list.add(fUser.getEmail());
				String content = "报账单:"+info.getOrderId()+",问题详情:"+problem.getDetail()+",请在["+rulesType.getWorkDay()+"天]内整改。 ";
				mailService.SendMail(list, null, "报账单问题", content, visitor.getAccount());
			}		
		}			
	
	}

	//初审ajax提交
	@RequestMapping(value="ajaxSubmit",method={RequestMethod.POST,RequestMethod.GET})
	public  void ajaxSubmit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		SysUser visitor=this.getVisitor(request);  
		String type = request.getParameter("type");
		String orderId = request.getParameter("orderId");
		String paymentTime =request.getParameter("paymentTime");
		String collectionMenber = request.getParameter("collectionMenber1");
		String menberId =  request.getParameter("collectionMenberId");
		request.setAttribute("orderId", orderId);
		
		//根据ID查找报账单
		AccountInfo info = accService.findTrialAccountByid(orderId);
		if(info!=null){
			//达到财务付款时间  取消
//			info.setPaymentTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentTime));
			info.setPaymentTime(info.getReachSement());//达到报账时间 == 发票时间
			info.setFirstTrial(visitor.getUserName());
			info.setFirstTrialId(visitor.getUserId());
			info.setCollectionMenberId(menberId);
			info.setNoticeEndTime(new Date());
			info.setStatus(AccountCommon.ACCOUNT_STATUS_ZGJS);
			info.setSubmitDate(new Date());
			info.setCollectionMenber(collectionMenber);
			info.setTrialFlat(AccountCommon.ACCOUNT_ACCOUNT_FLAT_YB);
			info.setCurrentLink(AccountCommon.CURRENT_LINK_XXBL);
			//修改报账单
			accService.updateTrialAccount(info);
			
			List<ProblemList> problemList =  accService.queryProblemList(info.getOrderId());
			if(problemList!=null){
				for (int j = 0; j < problemList.size(); j++) {
					if(problemList.get(j).getEndTime()==null){
						problemList.get(j).setEndTime(new Date());
						problemList.get(j).setStatus(AccountCommon.PROBLEM_STATUS_ZGTG);
						accService.updateProblem(problemList.get(j), null);
					}
				}
				
			}
		}
		if(type!=null && !type.equals("")){
			type=type;
		}else{
			type="1";
		}
		PrintWriter out = response.getWriter();
		out.write(type);
	}
	
	/**
	 * 已提交报账单回退至未提交状态
	 * @param orderId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="ajaxCancelBak" , method = {RequestMethod.POST,RequestMethod.GET})
	public void ajaxCancelBak(String orderId,HttpServletRequest request, HttpServletResponse response) throws IOException{
		String result = "0";
		AccountInfo accoutInfo = accService.findTrialAccountByid(orderId);
		accoutInfo.setTrialFlat(AccountCommon.ACCOUNT_ACCOUNT_FLAT_DB);
		accoutInfo.setCurrentLink(AccountCommon.CURRENT_LINK_CSLR);
		accoutInfo.setSubmitDate(null);
		accoutInfo.setNoticeEndTime(new Date());
		accoutInfo.setCollectionMenber(null);
		accoutInfo.setPaymentTime(null);//达到报账时间 == 发票时间
		accoutInfo.setFirstTrial(null);
		accoutInfo.setFirstTrialId(null);
		accService.updateTrialAccount(accoutInfo);
		result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);
	}
	
	//初审详情
	@RequestMapping(value="trailDetail",method={RequestMethod.POST,RequestMethod.GET})
	public  String trailDetail(HttpServletRequest request,AccountInfo accountInfo,AccountForm accountForm) throws Exception{
		
		SysUser visitor=this.getVisitor(request);  
		String type = request.getParameter("type");
		String orderid = request.getParameter("orderId");
		String id = request.getParameter("id");
		String source = request.getParameter("source");
		request.setAttribute("source", source);
		String sourceType = request.getParameter("sourceType");
		request.setAttribute("sourceType", sourceType);
		if(StringUtils.isNotEmpty(orderid)){
			orderid = new String(orderid.getBytes("iso-8859-1"), "utf-8");
		}
		
		//发票信息
		List<AccountInvoice> invoiceList = accService.getInvoiceList(id);
		request.setAttribute("invoiceList", invoiceList);
		
		//发票总额
		double f = 0;
		if(invoiceList != null && invoiceList.size() > 0){
			for (AccountInvoice accountInvoice : invoiceList) {
				if(StringUtils.isNotEmpty(accountInvoice.getMoneyNoTax())){
					f += Double.valueOf(accountInvoice.getMoneyNoTax()) ;
				}
			}
		}
		request.setAttribute("invoice_total", f);
		//接单信息
		AccountInfo info = accService.findTrialAccountByid(orderid.trim());
		if(info == null){
			info = (AccountInfo) rulesService.getEntity(AccountInfo.class, id);
		}
		request.setAttribute("info", info);
		ItemPage infos = accService.findTrialAccountByidForm(orderid,accountForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, infos);
		
		//查询节假日信息
		List<SysHolidayInfo> sysHolidayInfos = AccountCommon.getHoidlist(info.getCreateDate());
		
		//查询审批流程信息列表
		List<FlowInfo> flowInfos = accService.queryFlowInfoById(orderid);
		request.setAttribute("flowInfos",flowInfos );
		
		//是否为报账管理员
		String iSbaozAdmin = accService.queryTrialIsAdmin(visitor.getUserId());
		if(iSbaozAdmin!=null){
			if(iSbaozAdmin.equals(AccountCommon.ACCOUNT_ADMIN)){
				request.setAttribute("iSbaozAdmin", true);
			}else {
				request.setAttribute("iSbaozAdmin", false);
			}
		}
		
		//根据报账ID查问题列表
		List<ProblemList> problemList =  accService.queryProblemList(orderid);
		
		//根据费用ID查询超时工作日
		RulesType rulesTypes =accService.queryCostTypeById(info.getCosId());
		int wd = Integer.parseInt(rulesTypes.getWorkDay());
		
		//查找最耗时问题类型
		String haoshiType = accService.queryHaoshiType(orderid);
		request.setAttribute("haoshiType", haoshiType);
		
		String sysdate = dateFormat.format(new Date());
		Date smdate,bdate;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		smdate=new Date(); 
		int proNum=0;//问题总数
		double proHaoshiSum = 0;//问题总耗时
		
		if(problemList!=null){
			//循环问题列表比较问题整改是否超时
			for (int i = 0; i < problemList.size(); i++) {
				proNum++;
				if(problemList.get(i).getCreateTime()!=null){
					String startDate = dateFormat.format(problemList.get(i).getCreateTime());
					Calendar cal = Calendar.getInstance();  
					//超时计算
					double date2 =0;
					if(problemList.get(i).getEndTime()!=null){
						date2 = comon.getDate(problemList.get(i).getCreateTime(),problemList.get(i).getEndTime(),sysHolidayInfos);
					}else {
						date2 = comon.getDate(problemList.get(i).getCreateTime(),new Date(),sysHolidayInfos);
					}
					
					//超时天数
					BigDecimal b = new BigDecimal((date2-(double)wd*24)/24);
					double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					if(f1<=0){
						problemList.get(i).setIsOutTime(AccountCommon.PROBLEM_IS_OUTTIME_N);
						problemList.get(i).setOutDay("0");
					}else {
						info.setIstimeOut("1");
						problemList.get(i).setOutDay(f1+"");
						problemList.get(i).setIsOutTime(AccountCommon.PROBLEM_IS_OUTTIME_Y);
					}
					
					//耗时计算
					double haoshi = 0;
					if(problemList.get(i).getStartTime()!=null){
						if(problemList.get(i).getEndTime()!=null){
							haoshi = comon.getDate(problemList.get(i).getStartTime(),problemList.get(i).getEndTime(),sysHolidayInfos);
						}else {
							haoshi = comon.getDate(problemList.get(i).getStartTime(),new Date(),sysHolidayInfos);
						}
						//耗时天数
						BigDecimal a = new BigDecimal(haoshi/(double)24);
						double f2 = a.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
						
						//求耗时总天数
						proHaoshiSum = proHaoshiSum+f2;
						BigDecimal c = new BigDecimal(proHaoshiSum);
						proHaoshiSum = c.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
						
						if(f2>0){
							problemList.get(i).setSetTime(""+f2);
							info.setRectifyTime(proHaoshiSum+"");
						}else {
							problemList.get(i).setSetTime("0");
						}
					}
					
					//整改总时长
					if(problemList.get(i).getStartTime()!=null){
						String start = dateFormat.format(problemList.get(i).getStartTime());						
						double zgTime = 0; 
						
						if(problemList.get(i).getEndTime()!=null){
							zgTime = comon.getDate(problemList.get(i).getStartTime(),problemList.get(i).getEndTime(),sysHolidayInfos);
						}else {
							zgTime = comon.getDate(problemList.get(i).getStartTime(),new Date(),sysHolidayInfos);
						}
						
						BigDecimal d = new BigDecimal(zgTime/(double)24);
						double f3 = d.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					accService.updateProblem(problemList.get(i), null);
				}
			}
		}
		
		//发起报账时长、业务审批时长计算
		String str = accService.queryAging(info.getOrderId());
		if(str!=null && str.length()>0){
			request.setAttribute("fqbzTime", str.split(",")[0]);//发起报账时长
			request.setAttribute("ywspTime", str.split(",")[1]);//业务审批时长
		}
		//问题整改耗时改为 最后一条问题的整改时间  - 第一个问题的通知时间（创建时间）而不是在累加 moodify by ywc 20170302
		String wtzgSum = accService.queryWtzgSum(orderid);
		request.setAttribute("proHaoshiSum", wtzgSum);
		//request.setAttribute("proHaoshiSum", proHaoshiSum);
		request.setAttribute("proNum", proNum);
		request.setAttribute("problemList", problemList);
		//流程信息
		List<FlowInfo> flowinfo = accService.queryAccountInfoById(orderid);
		request.setAttribute("flowinfo", flowinfo);
		
		List<ImportTaxContract> itc = accService.queryImportTaxContractListByOrderId(orderid);
		request.setAttribute("itc", itc);
		//总时长：从接单到补录结束的总时长 。info
		if(info.getRecordDate() != null && info.getCreateDate() != null){
			double zgTime = comon.getDate(info.getCreateDate(),info.getRecordDate(),sysHolidayInfos);
			BigDecimal d = new BigDecimal(zgTime/(double)24);
			double f2 = d.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			request.setAttribute("f2", f2);
		}
		
		accService.updateTrialAccount(info);
		
		request.setAttribute("type", type);
		//type 1初审处理,  2详情页面
		if(type==null && type.trim().equals("")){
			type = "1";
		}	
		
		if(type.equals("2")){//详情
			return "dc/account/trialDetail";
		}else {//初审处理
			if("3".equals(type)){
				request.setAttribute("type", "2");
			}
			return "dc/account/traildo";
		}
	}


	private void doExport(List<AccountInfo> list,HttpServletResponse response,boolean problemFlag){
		try {


			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFCellStyle style = book.createCellStyle();
			HSSFFont font = book.createFont();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中对齐
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
			HSSFSheet sheet = book.createSheet("报账工单信息");
			int r = 0;
			HSSFRow row = sheet.createRow(r);
			HSSFCell cel = row.createCell(0);
			cel.setCellStyle(style);
			cel.setCellValue("报账工单信息");
			
			///所有可能输出的字段
			List<String> parameters=new ArrayList<String>();
			//工单信息
			parameters.add("报账单号");
			parameters.add("报账人");
			parameters.add("报账部门");
			parameters.add("费用类型");
			parameters.add("交单人");
			parameters.add("纸质提交财务时间");
			
			//项目信息
			parameters.add("是否包含抵扣");
			parameters.add("经办会计");
			parameters.add("创建人");
			parameters.add("接单录入时间");
			parameters.add("初审提交时间");
			parameters.add("当前环节");
			
			parameters.add("是否专票抵扣");
			parameters.add("发票时间");
			parameters.add("状态");
			parameters.add("是否超时");
			
			//适配测试信息
			parameters.add("送达财务付款时间");
			parameters.add("信息补录员");
			parameters.add("整改结束时间");
			parameters.add("整改不通过原因");
			/*parameters.add("系统版本");*/
			
			parameters.add("信息补录时间");
			parameters.add("补录员提交时间");
			
			String elements="报账单号,报账人,报账部门,费用类型,交单人,纸质提交财务时间,是否包含抵扣,经办会计,创建人," +
			"接单录入时间,初审提交时间,当前环节,是否专票抵扣,发票时间,状态,是否超时,送达财务付款时间," +
			"信息补录员,整改结束时间,整改不通过原因,信息补录时间,补录员提交时间";
			
			if(problemFlag){
				parameters.add("问题类型");
				elements += ",问题类型";
				parameters.add("通知整改时间");
				elements += ",通知整改时间";
				parameters.add("问题详情");
				elements += ",问题详情";
			}
			
			String content = null;
			for(int k=0;k<parameters.size();k++){
				if(elements.indexOf(parameters.get(k))>-1 ){
					if(content==null){
						content = parameters.get(k);
					}else{
						content +=","+parameters.get(k);
					}
				}
			}
			
			String[] titles = content.split(",");
			
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titles.length-1));
			r++;
			row = sheet.createRow(r);
			
			// 表头
			for (int s = 0; s < titles.length; s++) {
				HSSFCell cell = row.createCell(s);
				cell.setCellValue(titles[s].toString());
				cell.setCellStyle(style);
				sheet.setColumnWidth(s, 4000);
			}
			HSSFCellStyle style2 = book.createCellStyle();
			HSSFFont font2 = book.createFont();
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setFont(font2);
			
			for (int i = 0; i < list.size(); i++) {
				r++;
				row = sheet.createRow(r);
				int j=0;
				//Object[] objs = (Object[]) list.get(i);
				
				AccountInfo accountInfo = (AccountInfo) list.get(i);
				HSSFCell cell =null;
				//工单信息
				if(elements.indexOf("报账单号")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getOrderId();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("报账人")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getSementPeople();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("报账部门")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getDepartment();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("费用类型")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getCostType();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("交单人")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getPresenter();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("纸质提交财务时间")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					Date date = accountInfo.getPageSubmitDate();
					if( date==null || "".equals(date) ){
						cell.setCellValue("—");
					}else{
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						cell.setCellValue( dateStr );
					}
					j++;
				}
				if(elements.indexOf("是否包含抵扣")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getIncludeBuckle();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						if(str.equals("1")){
							cell.setCellValue("是");
						}else {
							cell.setCellValue("否");
						}
					}
					j++;
				}
				if(elements.indexOf("经办会计")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getTrialAccount();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("创建人")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getCreateName();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("接单录入时间")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					Date date = accountInfo.getCreateDate();
					if( date==null || "".equals(date) ){
						cell.setCellValue("—");
					}else{
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						cell.setCellValue( dateStr );
					}
					j++;
				}
				if(elements.indexOf("初审提交时间")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					Date date = accountInfo.getSubmitDate();
					if( date==null || "".equals(date) ){
						cell.setCellValue("—");
					}else{
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						cell.setCellValue( dateStr );
					}
					j++;
				}
				if(elements.indexOf("当前环节")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getCurrentLink();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("是否专票抵扣")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getDeduction();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						if(str.equals("1")){
							cell.setCellValue("是");
						}else {
							cell.setCellValue("否");
						}
					}
					j++;
				}
				if(elements.indexOf("发票时间")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					Date date = accountInfo.getReachSement();
					if( date==null || "".equals(date) ){
						cell.setCellValue("—");
					}else{
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						cell.setCellValue( dateStr );
					}
					j++;
				}
				if(elements.indexOf("状态")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getStatus();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						if(str.equals("1")){
							cell.setCellValue("整改中");
						}else if(str.equals("2")){
							cell.setCellValue("无问题");
						}else if(str.equals("3")){
							cell.setCellValue("整改结束");
						}else if(str.equals("4")){
							cell.setCellValue("退单");
						}
						
					}
					j++;
				}
				if(elements.indexOf("是否超时")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getTimeOut();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						if(str.equals("1")){
							cell.setCellValue("是");
						}else {
							cell.setCellValue("否");
						}
					}
					j++;
				}
				if(elements.indexOf("送达财务付款时间")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					Date date = accountInfo.getPaymentTime();
					if( date==null || "".equals(date) ){
						cell.setCellValue("—");
					}else{
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						cell.setCellValue( dateStr );
					}
					j++;
				}
				if(elements.indexOf("信息补录员")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getCollectionMenber();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("整改结束时间")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					Date date  = accountInfo.getNoticeEndTime();
					if( date==null || "".equals(date) ){
						cell.setCellValue("—");
					}else{
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						cell.setCellValue( dateStr );
					}
					j++;
				}
				if(elements.indexOf("整改不通过原因")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					String str = accountInfo.getNoPassReason();
					if( str==null || "".equals(str) ){
						cell.setCellValue("—");
					}else{
						cell.setCellValue( str );
					}
					j++;
				}
				if(elements.indexOf("信息补录时间")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					Date date = accountInfo.getRecordDate();
					if( date==null || "".equals(date) ){
						cell.setCellValue("—");
					}else{
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						cell.setCellValue( dateStr );
					}
					j++;
				}
				if(elements.indexOf("补录员提交时间")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					Date date = accountInfo.getRecordSubmitDate();
					if( date==null || "".equals(date) ){
						cell.setCellValue("—");
					}else{
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						cell.setCellValue( dateStr );
					}
					j++;
				}
				List<ProblemList> problemList = null;
				if(problemFlag){
					problemList =  accService.queryProblemList(accountInfo.getOrderId());
				}
				if(elements.indexOf("问题类型")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					if( problemList==null ||  problemList.size() == 0 ){
						cell.setCellValue("—");
					}else{
						String dateStr = "";
						for (int k = 0; k < problemList.size(); k++) {
							ProblemList problem = problemList.get(k);
							if(k == 0){
								dateStr += SysParamHelper.getSysParamByCode("BZ_TCTYPE", problem.getType()).getParameterValue();
							}else{
								dateStr += "；\r\n"  + SysParamHelper.getSysParamByCode("BZ_TCTYPE", problem.getType()).getParameterValue();
							}
						}
						
						cell.setCellValue(new HSSFRichTextString(dateStr));
					}
					j++;
				}
				if(elements.indexOf("通知整改时间")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					if( problemList==null ||  problemList.size() == 0 ){
						cell.setCellValue("—");
					}else{
						String dateStr = "";
						for (int k = 0; k < problemList.size(); k++) {
							ProblemList problem = problemList.get(k);
							if(k == 0 && problem.getStartTime() != null){
								dateStr += (k + 1)+ "、" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(problem.getStartTime());
							}else if(problem.getStartTime() != null){
								dateStr += "；\r\n"  + (k + 1)+ "、" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(problem.getStartTime());
							}else{
								if(k == 0){
									dateStr += (k + 1)+ "、" + "-";
								}else{
									dateStr += "；\r\n" + (k + 1)+ "、" + "-";
								}
							}
						}
						
						cell.setCellValue(new HSSFRichTextString(dateStr));
					}
					j++;
				}
				if(elements.indexOf("问题详情")>-1){
					cell = row.createCell(j);
					cell.setCellStyle(style2);
					if( problemList==null ||  problemList.size() == 0 ){
						cell.setCellValue("—");
					}else{
						String dateStr = "";
						for (int k = 0; k < problemList.size(); k++) {
							ProblemList problem = problemList.get(k);
							if(k == 0){
								dateStr +=  (k + 1)+ "、" + problem.getDetail();
							}else{
								dateStr += "；\r\n"  + (k + 1) + "、" + problem.getDetail();
							}
						}
						
						cell.setCellValue(new HSSFRichTextString(dateStr));
					}
					j++;
				}
			}
			//设置自动宽度
			for (int k = 0; k<titles.length; k++) {
				sheet.autoSizeColumn(k);
				sheet.setColumnWidth(k, (int)(sheet.getColumnWidth(k)*1) );
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "报账工单信息" + sdf.format(new Date());
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
