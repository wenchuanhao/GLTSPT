package com.cdc.dc.account.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cdc.common.properties.DCConfig;
import com.cdc.dc.account.model.AccountInfo;
import com.cdc.dc.account.model.PersonalOpinion;
import com.cdc.dc.account.model.ProblemList;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.account.common.AccountCommon;
import com.cdc.dc.account.form.AccountForm;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.inter.client.ws.mail.service.IMailService;
import com.cdc.inter.client.ws.sms.service.ISmsService;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.trustel.common.ItemPage;

/**
 * 
 * @author xms
 *新增问题列表
 */
@Controller
@RequestMapping(value="account/")
public class AddProblemController extends CommonController{
	@Autowired
	private IAccountService accService;
	@Autowired
	private ISmsService smsService;
	@Autowired
	private IMailService mailService;	
	@Autowired
	private IRulesService rulesService;
	@ModelAttribute
	public void doBeforeCall(HttpServletRequest request){
		List<SysParameter> sysParameter = SysParamHelper.getSysParamListByParamTypeCode("BZ_PHASE");
		List<SysParameter> proType = SysParamHelper.getSysParamListByParamTypeCode("BZ_TCTYPE");
		request.setAttribute("sysParameter", sysParameter);
		request.setAttribute("proType", proType);
	}
	
	//新增问题跳转
	@RequestMapping(value="addProblem" , method = {RequestMethod.POST,RequestMethod.GET})
	public String addProblem(HttpServletRequest request,AccountForm accountForm){
		SysUser visitor=this.getVisitor(request);
		String orderid = request.getParameter("orderid");
		request.setAttribute("orderid", orderid);
		
		String problemId = request.getParameter("problemId");
		if(problemId != null && !problemId.equals("")){
			ProblemList problemList = accService.queryProblem(problemId);
			request.setAttribute("problemList", problemList);
		}else{
			List<ProblemList> zgtgList = accService.queryProblemList(orderid,visitor.getUserId(),AccountCommon.PROBLEM_STATUS_CG);
			if(zgtgList != null && zgtgList.size() > 0){
				String type_list = "";
				for (ProblemList problemList : zgtgList) {
					type_list += ","+problemList.getType();
				}
				request.setAttribute("type_list", type_list);
			}
			request.setAttribute("draftList", zgtgList);
		}
		//问题详情
		List<PersonalOpinion> personalOpinionList = accService.getPersonalOption(visitor);
		request.setAttribute("personalOpinionList", personalOpinionList);
		return "dc/account/addProblem";
	}
	
	//保存新增问题
	@RequestMapping(value="saveProblem" , method = {RequestMethod.POST,RequestMethod.GET})
	public void saveProblem(HttpServletRequest request,HttpServletResponse response,ProblemList problem) throws Exception{
		String result = "0";
		SysUser visitor=this.getVisitor(request); 
		String orderid = request.getParameter("orderid");
		String typeList = request.getParameter("types"); 
		String  staus = request.getParameter("staus");
		String opitionContent = request.getParameter("opitionContent");
		request.setAttribute("orderid", orderid);
		
		String[] types = null;
		if(StringUtils.isNotEmpty(typeList)){
			types = typeList.split(",");
		}
		//删除草稿问题
		accService.delProblemList(orderid,visitor.getUserId(),AccountCommon.PROBLEM_STATUS_CG);
		if(types != null){
			for (String proType : types) {
				ProblemList problemSave = new ProblemList();
				BeanUtils.copyProperties(problemSave, problem);
				problemSave.setAccountOrderId(orderid);
				problemSave.setDetail(opitionContent);
				problemSave.setStatus(AccountCommon.PROBLEM_STATUS_CG);
				problemSave.setCreateId(visitor.getUserId());
				problemSave.setCreateName(visitor.getUserName());
				problemSave.setType(proType);
				problemSave.setCreateTime(new Date());
				
				//保存并通知整改
				if(staus.equals("2")){
					problemSave.setStatus(AccountCommon.PROBLEM_STATUS_ZGZ);
					problemSave.setStartTime(new Date());
				}
				//保存新增问题
				accService.saveProblem(problemSave);
			}
		}else{
			//暂存单个
			ProblemList problemSave = new ProblemList();
			BeanUtils.copyProperties(problemSave, problem);
			problemSave.setAccountOrderId(orderid);
			problemSave.setDetail(opitionContent);
			problemSave.setStatus(AccountCommon.PROBLEM_STATUS_CG);
			problemSave.setCreateId(visitor.getUserId());
			problemSave.setCreateName(visitor.getUserName());
			problemSave.setCreateTime(new Date());
			
			//保存并通知整改
			if(staus.equals("2")){
				problemSave.setStatus(AccountCommon.PROBLEM_STATUS_ZGZ);
				problemSave.setStartTime(new Date());
			}
			//保存新增问题
			accService.saveProblem(problemSave);
		
		}
		AccountInfo info = accService.findTrialAccountByid(orderid);
		//保存并通知整改
		if(staus.equals("2")){
			if(info.getNoticeUpTime()==null){
				info.setNoticeUpTime(new Date());//通知整改开始时间
			}
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
				String content = "报账单:"+info.getOrderId()+",问题详情:"+opitionContent+",请在["+rulesType.getWorkDay()+"天]内整改 。";
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
					String content = "报账单:"+info.getOrderId()+",问题详情:"+opitionContent+",请在["+rulesType.getWorkDay()+"天]内整改。 ";
					mailService.SendMail(list, null, "报账单问题", content, visitor.getAccount());
				}		
			}			
		}
		//修改第一次新增问题人
		//设置状态整改中
		info.setStatus(AccountCommon.ACCOUNT_STATUS_ZGZ);
		info.setFirstActorUser(visitor.getUserName());
		accService.updateTrialAccount(info);
		
		result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);
		
	}
	
	
	
	/**
	 * 保存问题详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "savePersonalOpinion", method = {RequestMethod.POST})
	public @ResponseBody void savePersonalOpinion(HttpServletRequest request, HttpServletResponse response) {
		try {
			SysUser visitor=this.getVisitor(request);
			String detail = request.getParameter("opitionContent");
			String result = "0";
    		response.setContentType("text/html; charset=utf-8");
    		//SysUser sysUser = getVisitor(request);
    		String outline = detail;
    		if(outline.length() > 7){
    			outline = detail.substring(0, 7) + "...";
    		}
    		String option = accService.savePersonalOpinion(detail, visitor.getUserId(), new Date(), outline);
    		if(StringUtils.isNotEmpty(option)) {
    			result = "1@#@" + option;
    		} else {
    			result = "0@#@";
    		}
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
	
	/**
	 * 查询问题详情列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "queryPersonalOpinionList", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void queryPersonalOpinionList(HttpServletRequest request,HttpServletResponse response,String brandName) throws Exception{
		try {
			SysUser sysUser = getVisitor(request);
			String result = "";
			List personalOpinionList = accService.queryPersonalOpinionList(sysUser.getUserId());
			JSONArray personalOpinionListJSONArray = JSONArray.fromObject(personalOpinionList);
	        result = personalOpinionListJSONArray.toString();
	        PrintWriter writer = response.getWriter();
			writer.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据ID查询个人常用意见
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryPersonalOpinionById", method = {RequestMethod.POST})
	public @ResponseBody void queryPersonalOpinionById(HttpServletRequest request, HttpServletResponse response) {
		try {
    		String personalOpinionId = request.getParameter("personalOpinionId");
    		String result = "";
  	        response.setContentType("text/html; charset=utf-8");
  	        if(personalOpinionId != null && !"".equals(personalOpinionId)){
  	        	PersonalOpinion personalOpinion = accService.queryPersonalOpinionById(personalOpinionId);
  	        	if(personalOpinion != null){
  	        		result = personalOpinion.getContent();
  	        	}
  	        }
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
	
	/**
	 * 删除个人常用意见
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deletePersonalOpinion", method = {RequestMethod.POST})
	public @ResponseBody void deletePersonalOpinion(HttpServletRequest request, HttpServletResponse response) {
		try {
			String orgAppid=request.getParameter("personalOpinion_Id");
			if(orgAppid!=null && !orgAppid.equals("")){
				accService.deletePersonalOpinion(orgAppid);
            }
    		PrintWriter writer = response.getWriter();
    		writer.write("1");
			} catch (Exception e) {
				e.printStackTrace();
			 }
	}
}
