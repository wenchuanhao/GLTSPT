package com.cdc.sys.smsManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.dcManage.form.DataClientManageForm;
import com.cdc.sys.dcManage.service.IDataClientManageService;
import com.cdc.sys.smsManage.form.SmsManageForm;
import com.cdc.sys.smsManage.model.SmsManage;
import com.cdc.sys.smsManage.model.SmsSendPerson;
import com.cdc.sys.smsManage.service.ISmsManageService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

@Controller
@RequestMapping(value="/sys/sms/")
public class SmsManageController extends DefaultController{
	
	@Autowired
	private ISmsManageService smsManageService;
	
	@Autowired
	private IDataClientManageService clientManageService;
	
	//查询短信管理列表首页
	@RequestMapping(value="querySms",method={RequestMethod.GET,RequestMethod.POST})
	private String querySmsManage(HttpServletRequest request,SmsManageForm form){
		request.setAttribute("form", form);
		
		ItemPage itemPage = smsManageService.querySmsManage(form);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		return "sys/smsManage/smsManage";
	}
	
	//新增短信管理
	@RequestMapping(value="addSmsManage",method={RequestMethod.GET,RequestMethod.POST})
	public String addSmsManage(HttpServletRequest request){
		return "sys/smsManage/addSmsManage";
	}
	
	@RequestMapping(value="selectSmsManage",method={RequestMethod.GET,RequestMethod.POST})
	public String selectSmsManage(HttpServletRequest request,DataClientManageForm manageForm){
		//修改初始化页面显示条数
		if(manageForm.getPageSize1()==0){
			manageForm.setPageSize(5);
		}
		request.setAttribute("manageForm", manageForm);
		ItemPage itemPage = clientManageService.queryDataManage(manageForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "sys/smsManage/selectSmsManage";
	}
	
	@RequestMapping(value="addSmsManages",method={RequestMethod.GET,RequestMethod.POST})
	public void addSmsManage(HttpServletRequest request,HttpServletResponse response,SmsManage manage,SmsSendPerson sendPerson) throws IOException{
		SysUser user = this.getVisitor(request);
		
		String result = smsManageService.addSmsManage(manage,sendPerson,user);
		PrintWriter out  = response.getWriter();
		out.write(result);
	}
	
	//删除
	@RequestMapping(value="delSmsManage",method={RequestMethod.GET,RequestMethod.POST})
	public void delSmsManage(HttpServletRequest request,HttpServletResponse response,String ids) throws IOException{
		smsManageService.delSmsManage(ids);
		
		PrintWriter out = response.getWriter();
		out.write("1");
	}
	
	//编辑
	@RequestMapping(value="editSmsManage",method={RequestMethod.GET,RequestMethod.POST})
	public String editSmsManage(HttpServletRequest request,SmsManageForm form,SmsManage manage,SmsSendPerson person){
		String smsId = request.getParameter("smsId");
		request.setAttribute("form", form);
		List<SmsManage> smsList = smsManageService.querySmsManageById(smsId);
		List<SmsSendPerson> persons = smsManageService.querySmsSendPersons(smsList.get(0).getId());
		request.setAttribute("smsList", smsList);
		request.setAttribute("sendPersons", persons);
		request.setAttribute("size", persons.size());
		String type = request.getParameter("type");
		
		if(type.equals("1")){
			request.setAttribute("isUpdate",false);
		}else {
			request.setAttribute("isUpdate",true);
		}
		return "sys/smsManage/editSmsManage";
	}
	
	@RequestMapping(value="ajaxSavaSms",method={RequestMethod.GET,RequestMethod.POST})
	public void ajaxSavaSms(HttpServletRequest request,HttpServletResponse response,SmsManage manage,SmsSendPerson person) throws IOException{
		smsManageService.editSmsManage(manage, person);
		PrintWriter out = response.getWriter();
		out.write("1");
	}
}
