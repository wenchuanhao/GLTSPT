package com.cdc.dc.rules.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.form.RulesForm;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.dc.rules.model.RulesInfo;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

/**
 * 我的制度
 * @author ZENGKAI
 * @date 2016-04-07 11:58:29
 */
@Controller
@RequestMapping(value = "/rulesController/")
public class RulesMineController extends DefaultController{

	@Autowired
	private IRulesService rulesService;
	
	@ModelAttribute
	public void doBeforeCall(HttpServletRequest request,RulesForm rulesForm){
		SysUser sysUser = getVisitor(request);
		if(sysUser != null){
			SysOrganization parentOrg = rulesService.queryParentOrg(sysUser.getOrganizationId());
			rulesForm.setLeadDepId(parentOrg.getOrganizationId());
			rulesForm.setCreateUserid(sysUser.getUserId());
		}
		ItemPage itemPage =(ItemPage) rulesService.queryMineRulesInfoItemPage(rulesForm);
		request.setAttribute("mineCount",itemPage.getTotal() );
		itemPage =(ItemPage) rulesService.queryDraftRulesInfoItemPage(rulesForm);
		request.setAttribute("draftCount",itemPage.getTotal() );
		itemPage =(ItemPage) rulesService.queryRemindRulesInfoItemPage(rulesForm);
		request.setAttribute("remindCount",itemPage.getTotal() );
	}
	/**
	 * 我的制度
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "mine", method = {RequestMethod.POST,RequestMethod.GET})
	public String mine(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		request.setAttribute("rulesForm",rulesForm);
		SysUser sysUser = getVisitor(request);
		if(sysUser != null){
			SysOrganization parentOrg = rulesService.queryParentOrg(sysUser.getOrganizationId());
			rulesForm.setLeadDepId(parentOrg.getOrganizationId());
			rulesForm.setCreateUserid(sysUser.getUserId());
		}
		ItemPage itemPage =(ItemPage) rulesService.queryMineRulesInfoItemPage(rulesForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		rulesService.setCommonAttribute(request);
		
		return "/dc/rules/rulesMine";
	}
	 
	/***********************************************************************************************
	 * 草稿箱Controller
	 ************************************************************************************************/
	/**
	 * 草稿箱列表查询
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "draft", method = {RequestMethod.POST,RequestMethod.GET})
	public String draft(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		request.setAttribute("rulesForm",rulesForm);
		SysUser sysUser = getVisitor(request);
		if(sysUser != null){
			SysOrganization parentOrg = rulesService.queryParentOrg(sysUser.getOrganizationId());
			rulesForm.setLeadDepId(parentOrg.getOrganizationId());
			rulesForm.setCreateUserid(sysUser.getUserId());
		}
		ItemPage itemPage =(ItemPage) rulesService.queryDraftRulesInfoItemPage(rulesForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "/dc/rules/rulesDraft";
	}
	
	/**
	 * 验证草稿信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkDraft", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void checkDraft(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String rulesId = request.getParameter("rulesId");
		//根据制度名称查询是否重复
		String rulesName = request.getParameter("rulesName");
		PrintWriter writer;
		RulesInfo rulesInfo = null;
		if(StringUtils.isNotEmpty(rulesId)){
			rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, rulesId);
			//校验草稿箱名称
			if(!StringUtils.isNotEmpty(rulesName) && rulesInfo != null){
				rulesName = rulesInfo.getRulesName();
			}
		}
		if(StringUtils.isNotEmpty(rulesName)){
			RulesInfo rulesInfoByName = rulesService.queryRulesInfoByName(rulesName,RulesCommon.rulesInfoStatus_SH,RulesCommon.rulesInfoStatus_FB);
			//说明存在同名制度
			if(rulesInfoByName != null && !rulesInfoByName.getRulesId().equals(rulesId)){
				writer = response.getWriter();
				writer.write("0");
				return;
			}
		}
		Map<String , Object> jsonMap = new HashMap<String, Object>();
		//查询附件
		if(rulesInfo != null){
			//制度文件
			List<RulesFileUpload>  zdwj = rulesService.queryRulesFileList(rulesId,RulesCommon.rulesFileUploadTypes_ZDWJ );
			if(zdwj != null){
				jsonMap.put("zdwj",zdwj.size());
			}
			//制度附件
			List<RulesFileUpload>  zdfj = rulesService.queryRulesFileList(rulesId,RulesCommon.rulesFileUploadTypes_ZDFJ );
			if(zdfj != null){
				jsonMap.put("zdfj",zdfj.size());
			}
			//发布依据
			List<RulesFileUpload>  fbyj = rulesService.queryRulesFileList(rulesId,RulesCommon.rulesFileUploadTypes_FBYJ );
			if(fbyj != null){
				jsonMap.put("fbyj",fbyj.size());
			}
		}
		//内容
		jsonMap.put("rulesInfo",rulesInfo);
		JSONObject json = JSONObject.fromObject(jsonMap);
        
		writer = response.getWriter();
		writer.write(json.toString());
	}
	
	
	/**
	 * 草稿提交动作
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submitDraft", method = {RequestMethod.POST})
	public void submitDraft(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String result = "0";
		SysUser sysUser = getVisitor(request);
		String rulesId = request.getParameter("rulesId");
		RulesInfo rulesInfo = null;
		if(StringUtils.isNotEmpty(rulesId)){
			rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, rulesId);
			if(rulesInfo != null){
				rulesInfo.setStatus(RulesCommon.rulesInfoStatus_SH);//提交至审核中……
				rulesService.submit(rulesInfo,sysUser);
				result = "1";
			}
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	}
	
	/**
	 * 删除草稿
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="delDraft",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void delDraft(HttpServletRequest request,HttpServletResponse response){
		String rulesId = request.getParameter("rulesId");
		String result = "0";
		try{
			if(StringUtils.isNotEmpty(rulesId)){
				String[] rulesIds = rulesId.split(",");
				for (String id : rulesIds) {
					RulesInfo ruletmp = (RulesInfo) rulesService.getEntity(RulesInfo.class, id);
					//只有草稿才能删除
					if(ruletmp != null && (RulesCommon.rulesInfoStatus_CG.equals(ruletmp.getStatus()) || RulesCommon.rulesInfoStatus_TH.equals(ruletmp.getStatus()))){
						ruletmp.setStatus(RulesCommon.rulesInfoStatus_DEL);//删除状态
						rulesService.updateEntity(ruletmp);//逻辑删除
					}
				}
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/***********************************************************************************************
	 * 制度提醒Controller
	 ************************************************************************************************/
	/**
	 * 制度提醒
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "remind", method = {RequestMethod.POST,RequestMethod.GET})
	public String remind(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		request.setAttribute("rulesForm",rulesForm);
		SysUser sysUser = getVisitor(request);
		if(sysUser != null){
			SysOrganization parentOrg = rulesService.queryParentOrg(sysUser.getOrganizationId());
			rulesForm.setLeadDepId(parentOrg.getOrganizationId());
			rulesForm.setCreateUserid(sysUser.getUserId());
		}
		//制度提醒，超时状态批量变更
		rulesService.updateRulesFlowInsactorTimeoutStatus();
		ItemPage itemPage =(ItemPage) rulesService.queryRemindRulesInfoItemPage(rulesForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		rulesService.setCommonAttribute(request);
		
		return "/dc/rules/rulesRemind";
	}
}
