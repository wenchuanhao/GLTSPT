package com.cdc.dc.rules.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cdc.dc.rules.model.RulesInfo;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;
/**
 * 制度创建
 * @author ZENGKAI
 * @date 2016-04-07 09:58:29
 */
@Controller
@RequestMapping(value = "/rulesController/")
public class RulesAddController extends DefaultController{

	@Autowired
	private IRulesService rulesService;
	
	/**  
	 * 新建制度页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "add", method = {RequestMethod.GET})
	public String add(HttpServletRequest request) throws Exception{
		try {
			String rulesId = request.getParameter("rulesId");
			String fromDraft = request.getParameter("fromDraft");
			
			SysUser sysUser = getVisitor(request);
			
			//查询制度信息
			RulesInfo rulesInfo = null;
			//如果有ID，查询信息
			if(StringUtils.isNotEmpty(rulesId)){
				rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, rulesId);
			}
			//如果无ID
			if(!StringUtils.isNotEmpty(rulesId) && sysUser != null && rulesInfo == null){
				rulesInfo = new RulesInfo();
				rulesId = UUID.randomUUID().toString();
				rulesInfo.setRulesId(rulesId);
			}
			rulesService.setCommonAttribute(request);
			request.setAttribute("rulesInfo", rulesInfo);
			request.setAttribute("fileTempId", rulesId);
			
			//来至草稿箱,现在审核信息
			if("1".equals(fromDraft)){
				//流程信息查询
				ItemPage  flowInfoList = rulesService.queryRulesFlowInfoList(rulesId);//查询列表
				request.setAttribute("flowInfoList", flowInfoList);//制度流程信息
				request.setAttribute("fromDraft", fromDraft);//是否从草稿箱过来
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/dc/rules/rulesAdd";
	}
	
	/**
	 * 暂存 动作
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "save", method = {RequestMethod.POST})
	public String save(HttpServletRequest request,RulesInfo rulesInfo) throws Exception{
		SysUser sysUser = getVisitor(request);
		if(sysUser != null){
			rulesInfo.setCreateTime(new Date());
			rulesInfo.setCreateUserid(sysUser.getUserId());
			rulesInfo.setCreateUsername(sysUser.getUserName());
			SysOrganization parentOrg = rulesService.queryParentOrg(sysUser.getOrganizationId());
			rulesInfo.setLeadDepId(parentOrg.getOrganizationId());//牵头部门
			rulesInfo.setUpdateTime(new Date());
			rulesInfo.setUpdateUserid(sysUser.getUserId());
			rulesInfo.setUpdateUsername(sysUser.getUserName());
		}
		if(StringUtils.isNotEmpty(rulesInfo.getRulesId())){
			RulesInfo ruletmp = (RulesInfo) rulesService.getEntity(RulesInfo.class, rulesInfo.getRulesId());
			//新增草稿
			if(ruletmp == null){
				rulesService.saveEntity(rulesInfo);
				//更新草稿
			}else{
				rulesInfo.setCreateTime(ruletmp.getCreateTime());
				rulesInfo.setCreateUserid(ruletmp.getCreateUserid());
				rulesInfo.setCreateUsername(ruletmp.getCreateUsername());
				rulesInfo.setLeadDepId(ruletmp.getLeadDepId());//牵头部门
				rulesService.updateEntity(rulesInfo);
			}
		}
		//草稿箱
		return "redirect:/rulesController/draft";
	}
	
	
	/**
	 * 提交动作
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submit", method = {RequestMethod.POST})
	public String submit(HttpServletRequest request,RulesInfo rulesInfo) throws Exception{
		SysUser sysUser = getVisitor(request);
		
		rulesService.submit(rulesInfo,sysUser);

		//我的制度
		return "redirect:/rulesController/mine";
	}
}
