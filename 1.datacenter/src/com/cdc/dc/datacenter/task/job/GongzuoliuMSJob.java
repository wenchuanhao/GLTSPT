package com.cdc.dc.datacenter.task.job;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.trustel.service.IEnterpriseService;

import com.cdc.dc.datacenter.common.DatacenterConstant;
import com.cdc.dc.datacenter.task.model.DcScheduleJobRecord;
import com.cdc.dc.datacenter.task.model.DcServiceJob;
import com.cdc.dc.datacenter.task.service.IDatacenterService;
import com.cdc.inter.client.db.sqlserver.GongzuoliuMSService;
import com.cdc.system.core.util.SpringHelper;

/**
 * 工作流数据获取定时任务
 * @author lxl
 * @date 2016-10-18
 */
public class GongzuoliuMSJob implements StatefulJob {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		IDatacenterService dcService = (IDatacenterService) SpringHelper.getBean("datacenterService");
		IEnterpriseService enterpriseService = (IEnterpriseService) SpringHelper.getBean("enterpriseService");
		//更新预插入的记录数据
		DcScheduleJobRecord record = dcService.getJobRecord(context.getJobDetail().getJobDataMap().getString("name"), context.getPreviousFireTime());
		JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
		int isAuto = jobDataMap.getInt("isAuto");
		
		if(record != null){
			record.setRunStatus(DatacenterConstant.JOB_STATUS_RUNNING);
			record.setRunDate(context.getFireTime());
			record.setNextRunDate(context.getNextFireTime());
			record.setTriggerType(isAuto);
			record.setUpdateDate(new Date());
		} else {
			record = new DcScheduleJobRecord();
			record.setParentId(context.getJobDetail().getJobDataMap().getString("name"));
			record.setRunStatus(DatacenterConstant.JOB_STATUS_RUNNING);
			record.setPrevRunDate(context.getPreviousFireTime());
			record.setRunDate(context.getFireTime());
			record.setNextRunDate(context.getNextFireTime());
			record.setTriggerType(isAuto);
			record.setCreateDate(new Date());
		}
		enterpriseService.saveOrUpdate(record);
		
		//如果是自动触发，则预插入下次执行的记录
		if(isAuto == DatacenterConstant.TRIGGER_TYPE_ZD){
			DcScheduleJobRecord nextRecord = new DcScheduleJobRecord();
			nextRecord.setParentId(context.getJobDetail().getJobDataMap().getString("name"));
			nextRecord.setPrevRunDate(context.getFireTime());
			nextRecord.setPlanRunDate(context.getNextFireTime());
			nextRecord.setRunStatus(DatacenterConstant.JOB_STATUS_NOT_RUNNING);
			nextRecord.setTriggerType(isAuto);
			nextRecord.setCreateDate(new Date());
			enterpriseService.save(nextRecord);
		}
		
		//----------------具体业务内容 S-----------------
		boolean retValue = false;
		try {
			retValue = GongzuoliuMSService.queryAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(retValue){
				record.setRunStatus(DatacenterConstant.JOB_STATUS_SUCCESS);
			} else {
				record.setRunStatus(DatacenterConstant.JOB_STATUS_FAILURE);
			}
			record.setUpdateDate(new Date());
			enterpriseService.saveOrUpdate(record);
		}
		//----------------具体业务内容 E-----------------
		
		//如果是手动触发的，则任务执行完要改为未手动触发
		if(isAuto == DatacenterConstant.TRIGGER_TYPE_SD){
			DcServiceJob dcServiceJob = dcService.findDcServiceJobById(context.getJobDetail().getJobDataMap().getString("name"));
			if(dcServiceJob != null){
				dcServiceJob.setIsTrigging(DatacenterConstant.TRIGGER_STOP);
				dcService.update(dcServiceJob);
			}
		}
	}

}
