package com.cdc.dc.datacenter.task.job;

import java.util.Date;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.trustel.util.DateUtils;

import com.cdc.dc.datacenter.common.DatacenterConstant;
import com.cdc.dc.datacenter.task.model.DcServiceJob;
import com.cdc.dc.datacenter.task.service.IDatacenterService;
import com.cdc.system.core.util.SpringHelper;

public class InitTaskJob {
	
	public void initJobTrigger(){
		System.out.println("===========================>>开始初始化数据中心定时任务！");
		StdScheduler scheduler = (StdScheduler) SpringHelper.getBean("schedulerFactoryBean");
		
		//获取初始化任务列表
		IDatacenterService datacenterService = (IDatacenterService) SpringHelper.getBean("datacenterService");
		List<DcServiceJob> list = datacenterService.getStartedJob();
		if(null!=list && list.size()>0){
			for(DcServiceJob dcServiceJob : list){
				Class<?> beanClass = null;
				try {
					beanClass = Class.forName(dcServiceJob.getBeanClass());
					//新建任务
					JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) beanClass)   // 任务执行类      
					        .withIdentity(dcServiceJob.getJobId(),
									dcServiceJob.getJobGroup())// 任务名，任务组              
					        .build();
					//新建触发器
					CronTrigger cronTrigger = (CronTrigger)TriggerBuilder.newTrigger()
							.withIdentity(dcServiceJob.getJobId() + "_trigger",dcServiceJob.getJobGroup())
							//为触发器设置定时表达式
							.withSchedule(CronScheduleBuilder.cronSchedule(dcServiceJob.getCronExpression()))
							.build();
					//设置存储数据
					cronTrigger.getJobDataMap().put("isAuto", DatacenterConstant.TRIGGER_TYPE_ZD);//默认为自动触发;
					//启动新增定时器任务
					Date d = scheduler.scheduleJob(jobDetail, cronTrigger);
					System.out.println("===========================>>新增定时器任务：" + jobDetail.getJobDataMap().getString("name") + " "
							+ DateUtils.format(d, "yyyy-MM-dd HH:mm:ss", null));					
					
				} catch (Exception e) {
					System.out.println(String.format("初始化任务列表出错！jobId为 %s，定时表达式为 %s",
							dcServiceJob.getJobId(),
							dcServiceJob.getCronExpression()));
					e.printStackTrace();
					//改变任务状态为未启动
					dcServiceJob.setServiceStatus(0);
					datacenterService.update(dcServiceJob);
				}
			}
		}
		
		try {
			//初始化任务只需要执行一次，执行一次之后移除初始化触发器
			TriggerKey triggerKey = new TriggerKey("InitTaskTrigger", Scheduler.DEFAULT_GROUP);
			boolean b = scheduler.unscheduleJob(triggerKey);
			System.out.println("===========================>>移除开始初始化数据中心定时任务：" + (b==true?"成功":"失败"));
			//任务启动
			scheduler.start();
			System.out.println("===========================>>初始化数据中心定时任务结束！");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
