package com.cdc.dc.datacenter.task.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.quartz.SchedulerException;

import com.cdc.dc.datacenter.task.form.DatacenterForm;
import com.cdc.dc.datacenter.task.model.DcFtpInterfaceInfo;
import com.cdc.dc.datacenter.task.model.DcJdbcInterfaceInfo;
import com.cdc.dc.datacenter.task.model.DcScheduleJob;
import com.cdc.dc.datacenter.task.model.DcScheduleJobRecord;
import com.cdc.dc.datacenter.task.model.DcServiceJob;
import com.trustel.common.ItemPage;

public interface IDatacenterService {
	/**
	 * 查询所有定时任务
	 */
	public void queryAll();

	public void saveJob(DcScheduleJob dcScheduleJob);
	/**
	 * 获取已启动的定时任务
	 * @return
	 */
	public List<DcServiceJob> getStartedJob();

	/**
	 * 获取数据获取服务列表
	 * @param datacenterImpForm
	 * @return
	 */
	public ItemPage getDcImpList(DatacenterForm datacenterImpForm);

	/**
	 * 根据服务Id获取服务
	 * @param jobId
	 * @return
	 */
	public DcServiceJob findDcServiceJobById(String jobId);
	
	public void saveEntity(Object item);

	public void findDcServiceDetail(DatacenterForm datacenterImpForm);

	/**
	 * 根据服务Id获取ftp信息
	 * @param jobId
	 * @return
	 */
	public DcFtpInterfaceInfo findDcFtpInfoByParentId(String jobId);

	public void updateAll(List<DcServiceJob> list);

	public void update(Object item);

	public ItemPage getDcExpList(DatacenterForm datacenterImpForm);

	public ItemPage getDcMonitorList(DatacenterForm datacenterForm);

	/**
	 * 新增/更新定时任务
	 * @param dcServiceJob
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void addOrUpdateJob(DcServiceJob dcServiceJob) throws SchedulerException, ParseException;

	/**
	 * 暂停定时任务
	 * @param dcServiceJob
	 * @throws SchedulerException
	 */
	public void pauseJob(DcServiceJob dcServiceJob) throws SchedulerException, ParseException;

	/**
	 * 根据任务id跟上一次的执行时间获取任务执行记录
	 * @param name
	 * @param previousFireTime
	 * @return
	 */
	public DcScheduleJobRecord getJobRecord(String id, Date previousFireTime);
	
	/**
	 * 关闭定时任务
	 * @param dcServiceJob
	 * @return
	 * @throws SchedulerException
	 */
	public void closeJob(DcServiceJob dcServiceJob) throws SchedulerException;

	/**
	 * 根据服务Id获取jdbc信息
	 * @param jobId
	 * @return
	 */
	public DcJdbcInterfaceInfo findDcJdbcByParentId(String jobId);

	/**
	 * 立即执行定时任务
	 * @param dcServiceJob
	 * @throws SchedulerException 
	 */
	public void runJobNow(DcServiceJob dcServiceJob) throws SchedulerException;


}
