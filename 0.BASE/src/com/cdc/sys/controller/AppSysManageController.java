package com.cdc.sys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.AppSysManage;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.AppSysManageForm;
import com.cdc.sys.service.IAppSysManage;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

/**
 * 应用系统管理controller
 * @author xms
 *
 */
@Controller
@RequestMapping(value="/sys/app/*")
public class AppSysManageController extends DefaultController{
	
	@Autowired
	private IAppSysManage appSysManage;
	
	//首页
	@RequestMapping(value="appSysManage",method={RequestMethod.GET,RequestMethod.POST})
	public String appSysManage(HttpServletRequest request,AppSysManageForm manage){
		request.setAttribute("manage", manage);
		ItemPage itemPage = appSysManage.queryMange(manage);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "sys/appSysManage/appSysManage";
	}
	
	//详情
	@RequestMapping(value="queryManageDetail",method={RequestMethod.GET,RequestMethod.POST})
	public String queryManageDetail(HttpServletRequest request,String id){
		//type1跳转到应用系统管理页面
		request.setAttribute("type",request.getParameter("type"));
		List<AppSysManage> list = appSysManage.queryMangeById(id);
		request.setAttribute("list", list);
		return "sys/appSysManage/appSysManageDetail";
	}
	
	//删除
	@RequestMapping(value="delManage",method={RequestMethod.GET,RequestMethod.POST})
	public String delManage(HttpServletRequest request,String id){
		List<AppSysManage> manage = appSysManage.queryMangeById(id);
		
		for (int i = 0; i < manage.size(); i++) {
			manage.get(i).setDeleted("Y");
		}
		
		appSysManage.delManage(manage);
		return "redirect:/sys/app/appSysManage";
	}
	
	//编辑
	@RequestMapping(value="editManage",method={RequestMethod.GET,RequestMethod.POST})
	public String editManage(HttpServletRequest request,String id){
		List<AppSysManage> list = appSysManage.queryMangeById(id);
		request.setAttribute("list", list);
		return "sys/appSysManage/editManage";
	}
	
	//ajxa根据ID查询应用系统
	@RequestMapping(value="queryAjaxAppManageById",method={RequestMethod.GET,RequestMethod.POST})
	public void queryAjaxAppManageById(HttpServletRequest request,HttpServletResponse response,String id) throws IOException{
		List<AppSysManage> list = appSysManage.queryMangeById(id);
		request.setAttribute("list", list);
		JSONArray qualityTemplateDetailJSONArray = JSONArray.fromObject(list);
		String result = qualityTemplateDetailJSONArray.toString();
		PrintWriter writer = response.getWriter();
		writer.write(result);
	}
	
	@RequestMapping(value="addAppSysManage",method={RequestMethod.GET,RequestMethod.POST})
	public String addAppSysManage(HttpServletRequest request,AppSysManage addMange){
		SysUser visitor=this.getVisitor(request);
		
		//如果add=1，保存
		String add = request.getParameter("add");
		if(request.getParameter("add")!=null){
			addMange.setDeleted("N");
			addMange.setCreateDate(new Date());
			addMange.setCreateUserId(visitor.getUserId());
			addMange.setCreateUserName(visitor.getUserName());
			appSysManage.addMange(addMange,add);
			return "redirect:/sys/app/appSysManage";
		}
		//update=1，修改
		if(request.getParameter("update")!=null){
			addMange.setDeleted("N");
			addMange.setUpdateDate(new Date());
			addMange.setUpdateUserId(visitor.getUserId());
			addMange.setUpdateUserName(visitor.getUserName());
			appSysManage.addMange(addMange,"");
			return "redirect:/sys/app/appSysManage";
		}
		
		return "sys/appSysManage/addAppSysManage";
	}
}
