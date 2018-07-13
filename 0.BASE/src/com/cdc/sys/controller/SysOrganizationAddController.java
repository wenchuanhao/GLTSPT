package com.cdc.sys.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysOrganization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.util.DateUtils;

import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;

/**
 * 
 * @Description: 组织构架>>新增组织
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/org/*")
public class SysOrganizationAddController extends CommonController {

	@Autowired
	private ISysOrganizationService organizationService;
	
	@Autowired
	private ISysLogService sysLogService;
	
	@Autowired
	private ISysUserService userService;
	
	/**
	 * 新增组织机构页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addOrg", method = RequestMethod.GET)
	public String toAddSysOrg(HttpServletRequest request,String perId,String flag) {
		try {
			if(flag!=null && flag.equals("news")){
				perId="80df8fa55ca4048ac2314dab1a52d75e";
			}
			if(!perId.equals("")&& perId!=null){
			 request.setAttribute("perOrg", organizationService.getOrgById(perId));
			 request.setAttribute("ordNumber", organizationService.getOrderNumber(perId));
			}else{
				SysParameter param=SysParamHelper.getSysParamByCode("ROOTID");
				request.setAttribute("perOrg", organizationService.getOrgById(param.getParameterValue()));
				request.setAttribute("ordNumber", organizationService.getOrderNumber(param.getParameterValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/org/addOrg";
	}
	/**
	 * 新增组织机构
	 * @param request
	 * @param organization
	 * @return
	 */
	@RequestMapping(value = "addOrg", method = RequestMethod.POST)
	public @ResponseBody String doAddSysOrg(HttpServletRequest request, SysOrganization organization) {
		String flag="ok";
		try {
			organization.setCreaterId(getVisitor(request).getUserId());
			organization.setCreateTime(DateUtils.get(new Date(), "GM+8"));
			if(organization!=null && organization.getOrgName()!=null && !organization.getOrgName().trim().equals("")){
				organizationService.addOrganization(organization);
			}
			
			sysLogService.log(request, getVisitor(request), "系统管理--组织架构",
					"新增组织", "添加【" + organization.getOrgName() + "】组织", new Date(), "3", new Date(), null);
			
		} catch (Exception e) {
			e.printStackTrace();
			flag = "no";
		}
		
		return flag;
		//return "redirect:/sys/org/queryOrg?liveFlag=1";
	}
	
	
	
	
}
