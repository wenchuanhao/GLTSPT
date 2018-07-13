/*package com.cdc.sys.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.flow.entity.FlowInstance;
import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.flow.service.IFlowCoreService;

import com.cdc.sys.form.BackLogForm;
import com.cdc.sys.service.IMatterQueryService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;
*//**
 * 待办事项管理
 * @author 
 *
 *//*

public class MatterQueryController extends DefaultController{
	@Autowired
	private IFlowCoreService flowCoreService;
	@Autowired
	private IMatterQueryService matterQueryService;
	
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String viewMyBackLog(HttpServletRequest request,
			HttpServletResponse response, BackLogForm backLogForm){
		List flowInstanceList = new ArrayList();
		SysUser sysUser = getVisitor(request);
		String type=request.getParameter("flowid")==null?"1":request.getParameter("flowid");//区分各种类型的流程
		int flowid=Integer.valueOf(type);
		//int workid=Integer.valueOf(backLogForm.getWorkorderId()==null?"5":backLogForm.getWorkorderId());
		//按条件获取待办事项
		flowInstanceList = (flowCoreService.queryAllTaskList(backLogForm,flowid,sysUser.getUserId())).getItems();
		//获取工单ID字段
		String MyBackLogIdstr = "";
		for(int i = 0; i<flowInstanceList.size(); i++){
			FlowInstance flowInstance = (FlowInstance)flowInstanceList.get(i);
			MyBackLogIdstr += "'" + flowInstance.getWorkId() + "',"; //拼接多个工单id，多个ID以逗号分隔
		}
		if(!MyBackLogIdstr.equals("")){
			MyBackLogIdstr = MyBackLogIdstr.substring(0, MyBackLogIdstr.length()-1); //去掉最后一个逗号
		}
		ItemPage item=matterQueryService.queryMatterList(flowid,backLogForm,MyBackLogIdstr);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, item);
		request.setAttribute("flowid", flowid);
		return "core/main/html/home";
	}

	
	@RequestMapping(value = "queryReadList", method = RequestMethod.GET)
	public String viewQueryReadList(HttpServletRequest request,
			HttpServletResponse response, BackLogForm backLogForm){
		List flowInstanceList = new ArrayList();
		SysUser sysUser = getVisitor(request);
		String type=request.getParameter("flowid")==null?"1":request.getParameter("flowid");//区分各种类型的流程
		int flowid=Integer.valueOf(type);
		flowInstanceList = (flowCoreService.queryAllMessageList(backLogForm,flowid,sysUser.getUserId())).getItems();
		//获取工单ID字段
		String MyBackLogIdstr = "";
		for(int i = 0; i<flowInstanceList.size(); i++){
			FlowInstance flowInstance = (FlowInstance)flowInstanceList.get(i);
			MyBackLogIdstr += "'" + flowInstance.getWorkId() + "',"; //拼接多个工单id，多个ID以逗号分隔
		}
		if(!MyBackLogIdstr.equals("")){
			MyBackLogIdstr = MyBackLogIdstr.substring(0, MyBackLogIdstr.length()-1); //去掉最后一个逗号
		}
		ItemPage item=matterQueryService.queryMatterList(flowid,backLogForm,MyBackLogIdstr);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, item);
		request.setAttribute("flowid", flowid);
		return "core/main/html/home";
	}
}*/