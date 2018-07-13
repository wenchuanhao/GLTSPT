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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.datacenter.task.form.DatacenterForm;
import com.cdc.dc.datacenter.task.model.DcFtpInterfaceInfo;
import com.cdc.dc.datacenter.task.model.DcServiceJob;
import com.cdc.dc.datacenter.task.service.IDatacenterService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 数据提供服务
 * @author lxl
 * @date 2016-9-26
 */
@Controller
@RequestMapping(value = "/datacenterExpController/")
public class DatacenterExpController extends CommonController{
	@Autowired
	private IDatacenterService datacenterService;
	
	@RequestMapping(value="queryDcExpServices", method = {RequestMethod.POST,RequestMethod.GET})
	public String queryDcExpServices(HttpServletRequest request,HttpServletResponse response,DatacenterForm datacenterImpForm){
		ItemPage itemPage = datacenterService.getDcExpList(datacenterImpForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		request.setAttribute("datacenterImpForm", datacenterImpForm);
		return "/dc/datacenter/datacenterExpList";
	}
	
	@RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
	public String add(HttpServletRequest request,HttpServletResponse response,DatacenterForm datacenterImpForm){
		DcServiceJob dcServiceJob = null;
		DcFtpInterfaceInfo dcFtpInterfaceInfo = null;
		String jobId = datacenterImpForm.getJobId();
		if(StringUtils.isNotEmpty(jobId)){
			//获取服务信息
			dcServiceJob = datacenterService.findDcServiceJobById(jobId);
			//获取ftp信息
			dcFtpInterfaceInfo = datacenterService.findDcFtpInfoByParentId(jobId);
		} else {
			dcServiceJob = new DcServiceJob();
			dcFtpInterfaceInfo = new DcFtpInterfaceInfo();
		}
		request.setAttribute("serviceInfo", dcServiceJob);
		request.setAttribute("ftpInfo", dcFtpInterfaceInfo);
		request.setAttribute("datacenterImpForm", datacenterImpForm);
		return "/dc/datacenter/datacenterExpAdd";
	}
	
	@RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
	public String saveOrUpdate(HttpServletRequest request,HttpServletResponse response,DatacenterForm datacenterImpForm){
		SysUser sysUser = getVisitor(request);
		String mess = "";
		
		try {
			if(StringUtils.isEmpty(datacenterImpForm.getJobId())){//新建
				DcServiceJob dcServiceJob = new DcServiceJob();
				dcServiceJob.setJobGroup("DEFAULT");//默认分组
				dcServiceJob.setJobName(datacenterImpForm.getJobName());
				dcServiceJob.setJobCode(datacenterImpForm.getJobCode());
				dcServiceJob.setServiceType("EXP");
				dcServiceJob.setInterfaceType("FTP");
				dcServiceJob.setBeanClass(datacenterImpForm.getBeanClass());
				dcServiceJob.setCronExpression(datacenterImpForm.getCronExpression());
				dcServiceJob.setServiceStatus(0);
				dcServiceJob.setRemark(datacenterImpForm.getRemark());
				dcServiceJob.setCreateDate(new Date());
				dcServiceJob.setCreateUserId(sysUser.getUserId());
				datacenterService.saveEntity(dcServiceJob);
				//添加ftp信息
				DcFtpInterfaceInfo dcFtpInfo = new DcFtpInterfaceInfo();
				dcFtpInfo.setParentId(dcServiceJob.getJobId());
				dcFtpInfo.setFtpAccount(datacenterImpForm.getFtpAccount());
				dcFtpInfo.setFtpPasswd(datacenterImpForm.getFtpPasswd());
				dcFtpInfo.setFilePath(datacenterImpForm.getFilePath());
				dcFtpInfo.setFilePattern(datacenterImpForm.getFilePattern());
				dcFtpInfo.setServiceType("EXP");
				dcFtpInfo.setDbTablename(datacenterImpForm.getDbTablename());
				dcFtpInfo.setCreateDate(new Date());
				dcFtpInfo.setCreateUserId(sysUser.getUserId());
				datacenterService.saveEntity(dcFtpInfo);
			} else {//修改
				DcServiceJob dcServiceJob = datacenterService.findDcServiceJobById(datacenterImpForm.getJobId());
				dcServiceJob.setJobName(datacenterImpForm.getJobName());
				dcServiceJob.setJobCode(datacenterImpForm.getJobCode());
				dcServiceJob.setBeanClass(datacenterImpForm.getBeanClass());
				dcServiceJob.setCronExpression(datacenterImpForm.getCronExpression());
				dcServiceJob.setRemark(datacenterImpForm.getRemark());
				dcServiceJob.setUpdateDate(new Date());
				dcServiceJob.setUpdateUserId(sysUser.getUserId());
				datacenterService.update(dcServiceJob);
				
				DcFtpInterfaceInfo dcFtpInfo = datacenterService.findDcFtpInfoByParentId(datacenterImpForm.getJobId());
				dcFtpInfo.setFtpAccount(datacenterImpForm.getFtpAccount());
				dcFtpInfo.setFtpPasswd(datacenterImpForm.getFtpPasswd());
				dcFtpInfo.setFilePath(datacenterImpForm.getFilePath());
				dcFtpInfo.setFilePattern(datacenterImpForm.getFilePattern());
				dcFtpInfo.setDbTablename(datacenterImpForm.getDbTablename());
				dcFtpInfo.setUpdateDate(new Date());
				dcFtpInfo.setUpdateUserId(sysUser.getUserId());
				datacenterService.update(dcFtpInfo);
			}
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		request.setAttribute("vo", datacenterImpForm);
		request.setAttribute("mess", mess);
		
		return "/dc/datacenter/datacenterExpAdd";
	}
	
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
					dcServiceJob.setServiceStatus(-1);
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
	
	@RequestMapping(value = "startService", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void startService(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String result = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				String[] ids = id.split(",");
				List<DcServiceJob> list = new ArrayList<DcServiceJob>();
				for(String jobId : ids){
					DcServiceJob dcServiceJob = datacenterService.findDcServiceJobById(jobId);
					dcServiceJob.setServiceStatus(1);
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
	
	@RequestMapping(value = "pauseService", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void pauseService(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String result = "0";
		try {
			if(StringUtils.isNotEmpty(id)){
				String[] ids = id.split(",");
				List<DcServiceJob> list = new ArrayList<DcServiceJob>();
				for(String jobId : ids){
					DcServiceJob dcServiceJob = datacenterService.findDcServiceJobById(jobId);
					dcServiceJob.setServiceStatus(0);
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
	
}
