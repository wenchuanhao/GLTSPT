package com.cdc.dc.datacenter.task.job;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.trustel.service.IEnterpriseService;
import org.trustel.util.DateUtils;

import com.cdc.dc.datacenter.common.DatacenterConstant;
import com.cdc.dc.datacenter.task.model.DcScheduleJobRecord;
import com.cdc.dc.datacenter.task.model.DcServiceJob;
import com.cdc.dc.datacenter.task.service.IDatacenterService;
import com.cdc.system.core.util.SpringHelper;

public class TestJob implements StatefulJob {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println(String.format(
				"======>> jobName:%s,上次执行时间:%s,本次执行时间:%s,下次执行时间:%s", 
				context.getJobDetail().getJobDataMap().getString("name"),
				DateUtils.format(context.getPreviousFireTime(), "yyyy-MM-dd HH:mm:ss", null),
				DateUtils.format(context.getFireTime(), "yyyy-MM-dd HH:mm:ss", null),
				DateUtils.format(context.getNextFireTime(), "yyyy-MM-dd HH:mm:ss", null)));
		
		IEnterpriseService enterpriseService = (IEnterpriseService) SpringHelper.getBean("enterpriseService");
		IDatacenterService dcService = (IDatacenterService) SpringHelper.getBean("datacenterService");
		
		DcScheduleJobRecord record = dcService.getJobRecord(context.getJobDetail().getJobDataMap().getString("name"), context.getPreviousFireTime());
		JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
		int isAuto = jobDataMap.getInt("isAuto");
		
		if(record != null){
			record.setRunStatus(1);
			record.setRunDate(context.getFireTime());
			record.setNextRunDate(context.getNextFireTime());
			record.setTriggerType(isAuto);
			record.setUpdateDate(new Date());
		} else {
			record = new DcScheduleJobRecord();
			record.setParentId(context.getJobDetail().getJobDataMap().getString("name"));
			record.setRunStatus(1);
			record.setPrevRunDate(context.getPreviousFireTime());
			record.setRunDate(context.getFireTime());
			record.setNextRunDate(context.getNextFireTime());
			record.setTriggerType(isAuto);
			record.setCreateDate(new Date());
		}
		enterpriseService.saveOrUpdate(record);
		
		//如果是自动触发，则插入下次执行的记录
		if(isAuto == DatacenterConstant.TRIGGER_TYPE_ZD){
			DcScheduleJobRecord nextRecord = new DcScheduleJobRecord();
			nextRecord.setParentId(context.getJobDetail().getJobDataMap().getString("name"));
			nextRecord.setPrevRunDate(context.getFireTime());
			nextRecord.setPlanRunDate(context.getNextFireTime());
			nextRecord.setRunStatus(0);
			nextRecord.setTriggerType(isAuto);
			nextRecord.setCreateDate(new Date());
			enterpriseService.save(nextRecord);
		}
		
		//模拟处理过程
		try {Thread.sleep(20*1000);} catch (InterruptedException e) {e.printStackTrace();}
		
		record.setRunStatus(2);//执行成功
		record.setUpdateDate(new Date());
		enterpriseService.updateObject(record);
		
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
