package com.cdc.dc.project.zb.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.cooperation.model.CooperationDatasourceRecords;
import com.cdc.dc.project.zb.model.Zb;
import com.cdc.dc.project.zb.service.IZbService;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 周报审核
 * @author ZengKai
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/zbremain/")
public class ZbRemainController extends CommonController {

	
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IZbService zbService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IRulesService rulesService;
	
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,Zb zb) throws Exception{
    	
    	SysUser sysUser = getVisitor(request);
    	zb.setCreateUserId(sysUser.getUserId());//创建用户id
    	ItemPage itemPage = zbService.findRemainZb(zb);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	request.setAttribute("zb", zb);
    	
    	//牵头部门
		List<SysOrganization> list = zbService.querySysOrganizationList();
		request.setAttribute("leadDepList", list);//牵头部门
    	
        return "/dc/project/zbremain/remainlist";
    }
    
    /**
	 * 周报审核通过
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "ajaxPast", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void ajaxPast(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String result = "0";
		SysUser sysUser = getVisitor(request);
		String ids = request.getParameter("ids");
		if(StringUtils.isNotEmpty(ids)){
			String[] zbIds = ids.split(",");
			for (String id : zbIds) {
				Zb zb = zbService.findZbById(id);
				//审核中
				if(zb != null && Zb.zbStatus_SH.equals(zb.getZbStatus())){
					zbService.ajaxPast(zb,sysUser);
					sysLogService.log(request, getVisitor(request), "工程管理--周报审核","审核周报", "审核周报【"+zb.getColumn01()+"】通过", new Date(), "3", new Date(), null);
				}
			}
			result = "1";
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	}
	/**  
	 * 跳转至周报退回页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "returnZbRemain", method = {RequestMethod.GET})
	public String returnZbRemain(HttpServletRequest request){
		String id = request.getParameter("id");
		//如果有ID，查询信息
		if(StringUtils.isNotEmpty(id)){
			request.setAttribute("id", id);
		}
		return "/dc/project/zbremain/zbreturn";
	}
	
	/**  
	 * 周报审核不通过
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "returnRemain", method = {RequestMethod.POST,RequestMethod.GET})
	public void returnRemain(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String result = "0";
		SysUser sysUser = getVisitor(request);
		String zbid = request.getParameter("id");
		String auditDesc = request.getParameter("auditDesc");
		if(StringUtils.isNotEmpty(zbid)){
			String[] zbIds = zbid.split(",");
			for (String id : zbIds) {
				Zb zb = zbService.findZbById(id);
				//审核中才能不通过
				if(zb != null && Zb.zbStatus_SH.equals(zb.getZbStatus())){
					zbService.returnRemain(zb,auditDesc,sysUser);//周报审核不通过
					sysLogService.log(request, getVisitor(request), "工程管理--周报审核","审核周报", "审核周报【"+zb.getColumn01()+"】不通过", new Date(), "3", new Date(), null);
				}
			}
			result = "1";
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	
	}
}
