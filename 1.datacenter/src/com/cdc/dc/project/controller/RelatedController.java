package com.cdc.dc.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.jsxm.service.IJsxmService;
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.tzbm.service.ITzbmService;
import com.cdc.dc.project.zb.model.Zb;
import com.cdc.dc.project.zb.service.IZbService;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxm.service.IZxmService;
import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.dc.project.zxmht.service.IZxmHtService;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.trustel.common.ItemPage;

/**
 * 工程首页
 * @author zengkai
 * @date 2016-11-02 14:58
 */
@Controller
@RequestMapping(value = "/related/")
public class RelatedController extends CommonController {
	
	@Autowired
	private IJsxmService jsxmService;
	@Autowired
	private ITzbmService tzbmService;
	@Autowired
	private IZxmService zxmService;
	@Autowired
	private IZxmHtService zxmHtService;
	@Autowired
	private IZbService zbService;
	@Autowired
	private IRulesService rulesService;
    /**
     * 建设项目
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "jsxm",method = {RequestMethod.GET,RequestMethod.POST})
    public String jsxm(HttpServletRequest request,HttpServletResponse response,Jsxm jsxm) throws Exception{
    	SysUser sysUser = getVisitor(request);
    	jsxm.setRelated(sysUser.getUserId());
    	ItemPage itemPage = jsxmService.findJsxm(jsxm);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("jsxm", jsxm);
    	request.setAttribute("current","1");
        return "/dc/project/related/jsxm";
    }
    
    /**
     * 投资编码
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "tzbm",method = {RequestMethod.GET,RequestMethod.POST})
    public String tzbm(HttpServletRequest request,HttpServletResponse response,Tzbm tzbm) throws Exception{
    	SysUser sysUser = getVisitor(request);
    	tzbm.setRelated(sysUser.getUserId());
    	ItemPage itemPage = tzbmService.findTzbm(tzbm);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("tzbm", tzbm);
    	request.setAttribute("current","2");
        return "/dc/project/related/tzbm";
    }
    /**
     * 子项目
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "zxm",method = {RequestMethod.GET,RequestMethod.POST})
    public String zxm(HttpServletRequest request,HttpServletResponse response,Zxm zxm) throws Exception{
    	SysUser sysUser = getVisitor(request);
    	zxm.setRelated(sysUser.getUserId());
    	ItemPage itemPage = zxmService.findZxm(zxm);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("pageform", zxm);
    	request.setAttribute("current","3");
        return "/dc/project/related/zxm";
    }
    
    /**
     * 子项目合同
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "zxmHt",method = {RequestMethod.GET,RequestMethod.POST})
    public String zxmHt(HttpServletRequest request,HttpServletResponse response,ZxmHt zxmHt) throws Exception{
    	SysUser sysUser = getVisitor(request);
    	zxmHt.setRelated(sysUser.getUserId());
    	ItemPage itemPage = zxmHtService.findZxmHt(zxmHt);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("pageform", zxmHt);
    	request.setAttribute("current","4");
        return "/dc/project/related/zxmHt";
    }
    /**
     * 周报
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "zb",method = {RequestMethod.GET,RequestMethod.POST})
    public String zb(HttpServletRequest request,HttpServletResponse response,Zb zb) throws Exception{
    	SysUser sysUser = getVisitor(request);
    	//判断用户是否是周报管理员
    	SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(FxkForm.FXK_DICT_TYPE, FxkForm.GCZB_ADMIN).getParameterValue());
    	SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
    	if(rolesUser != null){
    		request.setAttribute("userRoles", FxkForm.GCZB_ADMIN);
    		zb.setUserRoles(FxkForm.GCZB_ADMIN);
    	}
    	
    	zb.setCreateUserId(sysUser.getUserId());//创建用户id
    	zb.setRelated(sysUser.getUserId());
    	ItemPage itemPage = zbService.findZb(zb);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("pageform", zb);
    	request.setAttribute("current","5");
    	return "/dc/project/related/zb";
    }
}
