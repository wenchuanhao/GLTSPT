package com.cdc.dc.project.zb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.project.zb.model.Zb;
import com.cdc.dc.project.zb.service.IZbService;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.trustel.common.ItemPage;

/**
 * 项目报告管理
 * @author ZengKai
 * @date 2016-8-16 上午11:21:49
 */
@Controller
@RequestMapping(value = "/zbReport/")
public class ZbReportController extends CommonController{

	@Autowired
	private IZbService zbService;
	@Autowired
	private IRulesService rulesService;
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,Zb zb) throws Exception{
    	
    	SysUser sysUser = getVisitor(request);
    	//判断用户是否是周报管理员
    	SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(FxkForm.FXK_DICT_TYPE, FxkForm.GCZB_ADMIN).getParameterValue());
    	SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
    	if(rolesUser != null){
    		request.setAttribute("userRoles", FxkForm.GCZB_ADMIN);
    		zb.setUserRoles(FxkForm.GCZB_ADMIN);
    	}
    	
    	zb.setCreateUserId(sysUser.getUserId());//创建用户id
    	ItemPage itemPage = zbService.findZb(zb);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	request.setAttribute("zb", zb);
    	
    	//牵头部门
		List<SysOrganization> list = zbService.querySysOrganizationList();
		request.setAttribute("leadDepList", list);//牵头部门
    	
        return "/dc/project/zb/listReport";
    }	
}
