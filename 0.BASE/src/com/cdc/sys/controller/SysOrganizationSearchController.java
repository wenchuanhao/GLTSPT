package com.cdc.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysOrganizationForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 组织构架>>查询组织
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/org/*")
public class SysOrganizationSearchController extends CommonController {

	@Autowired
	private ISysOrganizationService organizationService;
	
	@Autowired
	private ISysLogService sysLogService;
	
	@Autowired
	private ISysUserService userService;
	
	
	@RequestMapping(value = "listOrg", method = { RequestMethod.GET, RequestMethod.POST })
	public String listOrg(HttpServletRequest request, SysOrganizationForm form,String mf2,String isPages) {
		try {
			String st=null;
			st=request.getParameter("st");
			request.setAttribute("st", st);
			
			if(mf2 !=null && mf2.equals("y")){
				request.getSession().putValue("depm", form.getParentId());
				request.setAttribute("parentId", form.getParentId());
			}
			if(isPages != null && isPages.equals("N")){
				String dep = (String)request.getSession().getAttribute("depm");
				if(dep !=null && !dep.equals("")){
					form.setParentId(dep);
				}
			}
			request.setAttribute("orgs", organizationService.queryAll());
			ItemPage itemPage = organizationService.queryOrganization(form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			request.setAttribute("back", "search");
			request.setAttribute("form", form);			
			
			request.setAttribute("mf2", mf2);
			request.setAttribute("isPages", isPages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "sys/org/searchOrg";
	}

}
