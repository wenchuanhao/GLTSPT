package com.cdc.sys.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.form.SysOrganizationForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 组织构架>>组织管理
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/org/*")
public class SysOrganizationController extends CommonController {

	@Autowired
	private ISysOrganizationService organizationService;
	
	@Autowired
	private ISysLogService sysLogService;
	
	@Autowired
	private ISysUserService userService;
	

	/**
	 * 查询所有组织
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryOrg", method = { RequestMethod.GET, RequestMethod.POST })
	public String querySysOrg(HttpServletRequest request, SysOrganizationForm form,String pid,String mf2,String isPages) {
		try {			
			if(mf2 !=null && mf2.equals("y")){
				request.getSession().putValue("depId", form.getParentId());
				request.setAttribute("parentId", form.getParentId());
			}
			if(isPages != null && isPages.equals("N")){
				String dep = (String)request.getSession().getAttribute("depId");
				if(dep !=null && !dep.equals("")){
					form.setParentId(dep);
				}
			}
			String st=null;
			st=request.getParameter("st");
			request.setAttribute("st", st);
			request.setAttribute("orgs", organizationService.queryAll());
			ItemPage itemPage = organizationService.queryOrganization(form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
			request.setAttribute("form", form);
			
			
			request.setAttribute("mf2", mf2);
			request.setAttribute("isPages", isPages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String mf = (String)request.getSession().getAttribute("mf");
		if(mf==null || mf.equals("") || (mf2!=null && mf2.equals("y"))){
			request.getSession().setAttribute("oid", form.getParentId());//用来区分点的是哪一个组织
			request.getSession().setAttribute("pid", pid);//被点击组织的父组织主键
		}
		
		return "sys/org/queryOrg";
	}
	/**
	 * 查询所有组织（树状）
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryOrgTree", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryOrgTree(HttpServletRequest request, SysOrganizationForm form) {
		try {
			request.setAttribute("orgs", organizationService.queryAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/org/dtreeOrg";
	}
	
	/**
	 * 修改组织机构页面
	 * @param request
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "modifyOrg/{orgId}", method = RequestMethod.GET)
	public String toModifySysOrg(HttpServletRequest request, @PathVariable String orgId) {

		try {
			SysOrganization org = organizationService.getOrgById(orgId);
			if(org!=null){
				request.setAttribute("par", organizationService.getOrgById(org.getParentId()));
			}
			request.setAttribute("org", org);
			request.setAttribute("location", "sys/org/queryOrg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/org/modifyOrg";
	}
	/**
	 * 组织机构详细页面
	 * @param request
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "sysOrgInfo/{orgId}", method = RequestMethod.GET)
	public String sysOrgInfo(HttpServletRequest request, @PathVariable String orgId) {

		try {
			SysOrganization org = organizationService.getOrgById(orgId);
			String name="";
			if(org!=null){
				request.setAttribute("PerOrg", organizationService.getOrgById(org.getParentId()));
				if(StringUtils.isNotEmpty(org.getCreaterId()) && userService.getSysUserById(org.getCreaterId())!=null){
					if(null!=userService.getSysUserById(org.getCreaterId()).getUserName()){
						name=userService.getSysUserById(org.getCreaterId()).getUserName();
					}
					request.setAttribute("cerateName",name);
				}
			}
			request.setAttribute("org", org);
			request.setAttribute("location", "sys/org/queryOrg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/org/orgInfo";
	}
	
	@RequestMapping(value = "sysOrgInfo/{back}/{orgId}", method = RequestMethod.GET)
	public String sysOrgInfoAdd(HttpServletRequest request,@PathVariable String back,@PathVariable String orgId) {

		try {
			SysOrganization org = organizationService.getOrgById(orgId);
			String name="";
			if(org!=null){
				request.setAttribute("PerOrg", organizationService.getOrgById(org.getParentId()));
				if(StringUtils.isNotEmpty(org.getCreaterId()) && userService.getSysUserById(org.getCreaterId())!=null){
					if(null!=userService.getSysUserById(org.getCreaterId()).getUserName()){
						name=userService.getSysUserById(org.getCreaterId()).getUserName();
					}
					request.setAttribute("cerateName",name);
				}
			}
			request.setAttribute("org", org);
			if(back.equals("search")){
				request.setAttribute("location", "sys/org/listOrg");
			}else{
				request.setAttribute("location", "sys/org/queryOrg");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/org/orgInfo";
	}
	/**
	 * 修改组织机构
	 * @param request
	 * @param organization
	 * @return
	 */
	@RequestMapping(value = "modifyOrg", method = RequestMethod.POST)
	public String doModifySysOrg(HttpServletRequest request, SysOrganization organization) {
		try {
			if(organization!=null && organization.getOrgName()!=null && !organization.getOrgName().trim().equals("")){
				organizationService.modifyOrganization(organization);
			}
			sysLogService.log(request, getVisitor(request), "系统管理--组织架构",
					"修改组织", "更新【" + organization.getOrgName() + "】组织", new Date(), "3", new Date(), null);
			
			return "redirect:/sys/org/queryOrg?liveFlag=1";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 删除组织机构
	 * @param orgIds
	 * @return
	 */
	@RequestMapping(value = "deleteOrg", method = RequestMethod.POST)
	public @ResponseBody String doDeleteSysOrg(HttpServletRequest request,HttpServletResponse response,String orgIds) {
		try {
			if (null != orgIds && !"".equals(orgIds)) {
				String[] ids = orgIds.split(",");
				String deleteName = "";
				try {
					for (String orgId : ids) {
						SysOrganization sysOrganization = organizationService.getOrgById(orgId);
						deleteName += sysOrganization.getOrgName() + "，";
						organizationService.deleteOrganization(orgId);
							
					}
					
					sysLogService.log(request, getVisitor(request), "系统管理--组织架构", "删除组织",
							"删除了组织【" + deleteName.substring(0, deleteName.length()-1) + "】", new Date(), "3", new Date(), null);
					return "y";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "redirect:/sys/org/queryOrg?liveFlag=1";
		return "n";
	}
	/**
	 * 验证用户组织名称的唯一
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checknameuniqueness", method = RequestMethod.POST)
	public @ResponseBody String checkNameUniqueness(HttpServletRequest request,HttpServletResponse response,String orgName,String parentId){
		SysOrganization organization=null;
		try
		{
			organization = organizationService.queryOrgByName(orgName,parentId);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if(organization==null)
			return "0";
		else
			return "1";
	}
	/**
	 * 上移下移
	 * @param request
	 * @param response
	 * @param orgName
	 * @return
	 */
	 
	@RequestMapping(value = "move", method = RequestMethod.POST)
	public @ResponseBody String moveUp(HttpServletRequest request,String id,String pId,String flag,String mf){
		try
		{	
			if(mf!=null && mf.equals("y")){
				request.getSession().putValue("oid", id);//用来区分点的是哪一个组织
				request.getSession().putValue("pid", pId);//被点击组织的父组织主键
				request.getSession().putValue("mf", "y");//被点击组织的父组织主键
			}
			System.out.println(mf+"*******************");
			if(pId.equals("")){
				SysParameter param=SysParamHelper.getSysParamByCode("ROOTID");
				pId=param.getParameterValue();
			}
			String tag=organizationService.judgment(id, flag);
			if(tag.equals("0")){
				if(flag.equals("up")){
					organizationService.moveUp(id);
					return pId;
				}else{
					organizationService.moveDown(id);
					return pId;
				}
			}else{
				return tag;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return "9";
		}
	}
	/**
	 * 上移下移_越级移动
	 * @param request
	 * @param response
	 * @param orgName
	 * @return
	 */
	 
	@RequestMapping(value = "move2", method = RequestMethod.POST)
	public @ResponseBody String moveUp2(HttpServletRequest request,String id,String pId,String flag,String mf){
		try
		{	
			if(mf!=null && mf.equals("y")){
				request.getSession().putValue("oid", id);//用来区分点的是哪一个组织
				request.getSession().putValue("pid", pId);//被点击组织的父组织主键
				request.getSession().putValue("mf", "y");//被点击组织的父组织主键
			}
			System.out.println(mf+"*******************");
			if(pId.equals("")){
				SysParameter param=SysParamHelper.getSysParamByCode("ROOTID");
				pId=param.getParameterValue();
			}
		//	String tag=organizationService.judgment(id, flag);
		//	if(tag.equals("0")){
				if(flag.equals("up")){
					String levelOne = organizationService.isLevelOne(id);
					if(levelOne.equals("0")){
						organizationService.moveUp2(id,pId);
						return pId;
					}else{
						return "1";
					}
				}else{
					
					List<SysOrganization> list = organizationService.queryNextLevelOrgByOrgId(pId);
					if(list==null || list.size()<=1){
						return "2";
					}
					organizationService.moveDown2(id,pId);
					return pId;
				}
//			}else{
//				return tag;
//			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return "9";
		}
	}
	/**
	 * 验证组织机构下面是否有组织
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checkSub", method = RequestMethod.POST)
	public @ResponseBody String checkSub(HttpServletRequest request,String orgIds){
		try
		{	
			if(orgIds==null){
				return "2";
			}
			String[] ids = orgIds.split(",");
			orgIds="";
			for (String sptId : ids) {
				orgIds+="'"+sptId+"',";
			}
			if(!orgIds.equals("")){
				orgIds = orgIds.substring(0,orgIds.length()-1);
			}
			List<SysOrganization> list=organizationService.checkSubOrg(orgIds);
			if(list!=null && list.size()!=0){
				return "1";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return "1";
		}
		return "0";
	}
}
