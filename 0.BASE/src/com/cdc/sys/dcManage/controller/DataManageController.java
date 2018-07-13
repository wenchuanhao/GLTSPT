package com.cdc.sys.dcManage.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.dcManage.form.DataClientManageForm;
import com.cdc.sys.dcManage.model.DataClientManage;
import com.cdc.sys.dcManage.service.IDataClientManageService;
import com.cdc.sys.form.AppSysManageForm;
import com.cdc.sys.service.IAppSysManage;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

/**
 * 数据获取管理
 * @author xms
 *
 */
@Controller
@RequestMapping(value="sys/data/*")
public class DataManageController extends DefaultController {
	
	@Autowired
	private IDataClientManageService clientManageService;
	
	@Autowired
	private IAppSysManage appSysManage;
	
	//首页
	@RequestMapping(value="dataManage",method={RequestMethod.GET,RequestMethod.POST})
	public String dataManage(HttpServletRequest request,DataClientManageForm manageForm){
		request.setAttribute("manageForm", manageForm);
		
		ItemPage itemPage = clientManageService.queryDataManagePage(manageForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "sys/dataManage/dataManage";
	}
	
	//新增
	@RequestMapping(value="addDataManage",method={RequestMethod.GET,RequestMethod.POST})
	public String addDataManage(HttpServletRequest request,DataClientManage clientManage){
		String add = request.getParameter("add");
		SysUser user = this.getVisitor(request);
		
		if(add!=null && add.equals("add")){
			clientManage.setDeleted("N");
			clientManage.setCreateDate(new Date());
			clientManage.setCreateUserId(user.getUserId());
			clientManage.setCreateUserName(user.getUserName());
			clientManageService.addDataManage(clientManage, add);
			return "redirect:/sys/data/dataManage";
		}
		if (add!=null && add.equals("update")){
			clientManage.setDeleted("N");
			clientManage.setUpdateDate(new Date());
			clientManage.setUpdateUserId(user.getUserId());
			clientManage.setUpdateUserName(user.getUserName());
			clientManageService.addDataManage(clientManage, add);
			return "redirect:/sys/data/dataManage";
		}
		return "sys/dataManage/addDataManage";
	}
	
	//删除
	@RequestMapping(value="delDataManage",method={RequestMethod.GET,RequestMethod.POST})
	public String delDataManage(HttpServletRequest request,String id){
		List<DataClientManage> list = clientManageService.queryDataManageById(id);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setDeleted("Y");
		}
		
		clientManageService.delDataManage(list);
		return "redirect:/sys/data/dataManage";
	}
	
	//编辑
	@RequestMapping(value="editDataManage",method={RequestMethod.GET,RequestMethod.POST})
	public String editDataManage(HttpServletRequest request,String id){
		List<DataClientManage> list = clientManageService.queryDataManageById(id);
		request.setAttribute("list", list);
		return "sys/dataManage/editDataManage";
	}
	
	//详情
	@RequestMapping(value="dataManageDetail",method={RequestMethod.GET,RequestMethod.POST})
	public String dataManageDetail(HttpServletRequest request,String id){
		List<DataClientManage> list = clientManageService.queryDataManageById(id);
		request.setAttribute("list", list);
		return "sys/dataManage/dataManageDetail";
	}
	
	//查询应用系统
	@RequestMapping(value="selectSysManage",method={RequestMethod.GET,RequestMethod.POST})
	public String selectSysManage(HttpServletRequest request,AppSysManageForm sysManageForm){
		
		//修改初始化页面显示条数
		if(sysManageForm.getPageSize1()==0){
			sysManageForm.setPageSize(5);
		}
		
		request.setAttribute("manage", sysManageForm);
		ItemPage itemPage = appSysManage.querySysMange(sysManageForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "sys/dataManage/selectSysManage";
	}
}
