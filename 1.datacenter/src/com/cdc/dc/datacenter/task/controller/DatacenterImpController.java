package com.cdc.dc.datacenter.task.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.datacenter.common.DatacenterConstant;
import com.cdc.dc.datacenter.task.form.DatacenterForm;
import com.cdc.dc.datacenter.task.model.DcFtpInterfaceInfo;
import com.cdc.dc.datacenter.task.model.DcJdbcInterfaceInfo;
import com.cdc.dc.datacenter.task.model.DcServiceJob;
import com.cdc.dc.datacenter.task.service.IDatacenterService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;

/**
 * 数据获取服务
 * @author lxl
 * @date 2016-9-26
 */
@Controller
@RequestMapping(value = "/datacenterImpController/")
public class DatacenterImpController extends CommonController{
	@Autowired
	private IDatacenterService datacenterService;
	
	@RequestMapping(value="queryDcImpServices", method = {RequestMethod.POST,RequestMethod.GET})
	public String queryDcImpServices(HttpServletRequest request,HttpServletResponse response,DatacenterForm datacenterImpForm){
		ItemPage itemPage = datacenterService.getDcImpList(datacenterImpForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		request.setAttribute("datacenterImpForm", datacenterImpForm);
		return "/dc/datacenter/datacenterImpList";
	}
	
	@RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
	public String add(HttpServletRequest request,HttpServletResponse response,DatacenterForm datacenterImpForm){
		DcServiceJob dcServiceJob = null;
		DcFtpInterfaceInfo dcFtpInterfaceInfo = null;
		DcJdbcInterfaceInfo dcJdbcInterInfo = null;
		String jobId = datacenterImpForm.getJobId();
		if(StringUtils.isNotEmpty(jobId)){
			//获取服务信息
			dcServiceJob = datacenterService.findDcServiceJobById(jobId);
			//获取ftp信息
			dcFtpInterfaceInfo = datacenterService.findDcFtpInfoByParentId(jobId);
			//获取jdbc信息
			dcJdbcInterInfo = datacenterService.findDcJdbcByParentId(jobId);
		} else {
			dcServiceJob = new DcServiceJob();
			dcFtpInterfaceInfo = new DcFtpInterfaceInfo();
		}
		request.setAttribute("serviceInfo", dcServiceJob);
		request.setAttribute("ftpInfo", dcFtpInterfaceInfo);
		request.setAttribute("jdbcInfo", dcJdbcInterInfo);
		request.setAttribute("datacenterImpForm", datacenterImpForm);
		return "/dc/datacenter/datacenterImpAdd";
	}
	
	/**
	 * 新增/更新数据获取服务
	 * @param request
	 * @param response
	 * @param datacenterImpForm
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
	public String saveOrUpdate(HttpServletRequest request,HttpServletResponse response,DatacenterForm datacenterImpForm){
		SysUser sysUser = getVisitor(request);
		String mess = "";
		DcServiceJob dcServiceJob = null;
		DcFtpInterfaceInfo dcFtpInfo = null;
		DcJdbcInterfaceInfo dcJdbcInfo = null;
		try {
			if(StringUtils.isEmpty(datacenterImpForm.getJobId())){//新建
				String interfaceType = datacenterImpForm.getInterfaceType().replace(",", "");
				
				dcServiceJob = new DcServiceJob();
				dcServiceJob.setJobGroup(DatacenterConstant.JOB_DEFAULT_GROUP);
				dcServiceJob.setJobName(datacenterImpForm.getJobName());
				dcServiceJob.setJobCode(datacenterImpForm.getJobCode());
				dcServiceJob.setServiceType(DatacenterConstant.IMP);
				dcServiceJob.setInterfaceType(interfaceType);
				dcServiceJob.setBeanClass(datacenterImpForm.getBeanClass());
				dcServiceJob.setCronExpression(datacenterImpForm.getCronExpression());
				dcServiceJob.setCronDesc(datacenterImpForm.getCronDesc());
				dcServiceJob.setServiceStatus(DatacenterConstant.SERVICE_STOP);
				dcServiceJob.setRemark(datacenterImpForm.getRemark());
				dcServiceJob.setCreateDate(new Date());
				dcServiceJob.setCreateUserId(sysUser.getUserId());
				dcServiceJob.setIsTrigging(DatacenterConstant.TRIGGER_STOP);
				datacenterService.saveEntity(dcServiceJob);
				
				if(StringUtils.equals(interfaceType, DatacenterConstant.INTERFACE_FTP)){
					//添加ftp信息
					dcFtpInfo = new DcFtpInterfaceInfo();
					dcFtpInfo.setParentId(dcServiceJob.getJobId());
					dcFtpInfo.setFtpAccount(datacenterImpForm.getFtpAccount());
					dcFtpInfo.setFtpPasswd(datacenterImpForm.getFtpPasswd());
					dcFtpInfo.setFilePath(datacenterImpForm.getFilePath());
					dcFtpInfo.setFilePattern(datacenterImpForm.getFilePattern());
					dcFtpInfo.setServiceType(DatacenterConstant.IMP);
					dcFtpInfo.setDbTablename(datacenterImpForm.getDbTablename());
					dcFtpInfo.setCreateDate(new Date());
					dcFtpInfo.setCreateUserId(sysUser.getUserId());
					datacenterService.saveEntity(dcFtpInfo);
				} else if(StringUtils.equals(interfaceType, DatacenterConstant.INTERFACE_JDBC)){
					//添加jdbc信息
					dcJdbcInfo = new DcJdbcInterfaceInfo();
					dcJdbcInfo.setParentId(dcServiceJob.getJobId());
					dcJdbcInfo.setJdbcUrl(datacenterImpForm.getJdbcUrl());
					dcJdbcInfo.setJdbcUsername(datacenterImpForm.getJdbcUsername());
					dcJdbcInfo.setJdbcPassword(datacenterImpForm.getJdbcPassword());
					dcJdbcInfo.setCreateDate(new Date());
					dcJdbcInfo.setCreateUserId(sysUser.getUserId());
					dcJdbcInfo.setCreateUserName(sysUser.getUserName());
					datacenterService.saveEntity(dcJdbcInfo);
				}
			} else {//修改
				dcServiceJob = datacenterService.findDcServiceJobById(datacenterImpForm.getJobId());
				dcServiceJob.setJobName(datacenterImpForm.getJobName());
				dcServiceJob.setJobCode(datacenterImpForm.getJobCode());
				dcServiceJob.setBeanClass(datacenterImpForm.getBeanClass());
				dcServiceJob.setCronExpression(datacenterImpForm.getCronExpression());
				dcServiceJob.setCronDesc(datacenterImpForm.getCronDesc());
				dcServiceJob.setRemark(datacenterImpForm.getRemark());
				dcServiceJob.setUpdateDate(new Date());
				dcServiceJob.setUpdateUserId(sysUser.getUserId());
				datacenterService.update(dcServiceJob);
				
				if(StringUtils.equals(datacenterImpForm.getInterfaceType(), DatacenterConstant.INTERFACE_FTP)){
					dcFtpInfo = datacenterService.findDcFtpInfoByParentId(datacenterImpForm.getJobId());
					dcFtpInfo.setFtpAccount(datacenterImpForm.getFtpAccount());
					dcFtpInfo.setFtpPasswd(datacenterImpForm.getFtpPasswd());
					dcFtpInfo.setFilePath(datacenterImpForm.getFilePath());
					dcFtpInfo.setFilePattern(datacenterImpForm.getFilePattern());
					dcFtpInfo.setDbTablename(datacenterImpForm.getDbTablename());
					dcFtpInfo.setUpdateDate(new Date());
					dcFtpInfo.setUpdateUserId(sysUser.getUserId());
					datacenterService.update(dcFtpInfo);
				}else if(StringUtils.equals(datacenterImpForm.getInterfaceType(), DatacenterConstant.INTERFACE_JDBC)){
					dcJdbcInfo = datacenterService.findDcJdbcByParentId(datacenterImpForm.getJobId());
					dcJdbcInfo.setJdbcUsername(datacenterImpForm.getJdbcUsername());
					dcJdbcInfo.setJdbcPassword(datacenterImpForm.getJdbcPassword());
					dcJdbcInfo.setJdbcUrl(datacenterImpForm.getJdbcUrl());
					dcJdbcInfo.setUpdateDate(new Date());
					dcJdbcInfo.setUpdateUserId(sysUser.getUserId());
					dcJdbcInfo.setUpdateUserName(sysUser.getUserName());
					datacenterService.update(dcJdbcInfo);
				}
			}
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		request.setAttribute("vo", datacenterImpForm);
		request.setAttribute("serviceInfo", dcServiceJob);
		request.setAttribute("ftpInfo", dcFtpInfo);
		request.setAttribute("jdbcInfo", dcJdbcInfo);
		request.setAttribute("mess", mess);
		
		return "/dc/datacenter/datacenterImpAdd";
	}
	
	/**
	 * 删除数据获取服务
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "delDcService", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void delDcService(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String result = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				String[] ids = id.split(",");
				List<DcServiceJob> list = new ArrayList<DcServiceJob>();
				for(String jobId : ids){
					DcServiceJob dcServiceJob = datacenterService.findDcServiceJobById(jobId);
					dcServiceJob.setServiceStatus(DatacenterConstant.SERVICE_DELETE);
					list.add(dcServiceJob);
				}
				datacenterService.updateAll(list);
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 改变服务状态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "changeServiceStatus", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void changeServiceStatus(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String result = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				String[] ids = id.split(",");
				List<DcServiceJob> list = new ArrayList<DcServiceJob>();
				if(StringUtils.equals(type, "0")){//启动服务
					for(String jobId : ids){
						DcServiceJob dcServiceJob = datacenterService.findDcServiceJobById(jobId);
						dcServiceJob.setServiceStatus(DatacenterConstant.SERVICE_RUNNING);
						list.add(dcServiceJob);
						
						//启动定时任务
						datacenterService.addOrUpdateJob(dcServiceJob);
					}
				} else if(StringUtils.equals(type, "1")){//关闭服务
					for(String jobId : ids){
						DcServiceJob dcServiceJob = datacenterService.findDcServiceJobById(jobId);
						dcServiceJob.setServiceStatus(DatacenterConstant.SERVICE_STOP);
						list.add(dcServiceJob);
						
						//关闭定时任务
						datacenterService.closeJob(dcServiceJob);
					}
				}
				
				datacenterService.updateAll(list);
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 立即执行定时任务
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "triggerNow", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void triggerNow(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String result = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				DcServiceJob dcServiceJob = datacenterService.findDcServiceJobById(id);
				//立即执行定时任务,若该任务已在执行,则进入阻塞状态
				datacenterService.runJobNow(dcServiceJob);
				
				dcServiceJob.setIsTrigging(DatacenterConstant.TRIGGER_RUNNING);
				datacenterService.addOrUpdateJob(dcServiceJob);
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
