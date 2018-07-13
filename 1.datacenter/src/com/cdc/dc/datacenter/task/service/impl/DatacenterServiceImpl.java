package com.cdc.dc.datacenter.task.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;
import org.trustel.util.DateUtils;

import com.cdc.dc.datacenter.common.DatacenterConstant;
import com.cdc.dc.datacenter.task.form.DatacenterForm;
import com.cdc.dc.datacenter.task.model.DcFtpInterfaceInfo;
import com.cdc.dc.datacenter.task.model.DcJdbcInterfaceInfo;
import com.cdc.dc.datacenter.task.model.DcScheduleJob;
import com.cdc.dc.datacenter.task.model.DcScheduleJobRecord;
import com.cdc.dc.datacenter.task.model.DcServiceJob;
import com.cdc.dc.datacenter.task.service.IDatacenterService;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;

public class DatacenterServiceImpl implements IDatacenterService {
	
	private IEnterpriseService enterpriseService;
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public void saveJob(DcScheduleJob dcScheduleJob) {
		enterpriseService.save(dcScheduleJob);
	}

	@Override
	public void queryAll() {
		
	}

	@Override
	public List<DcServiceJob> getStartedJob() {
		QueryBuilder query = new QueryBuilder(DcServiceJob.class);
		query.where("serviceStatus", 1, QueryAction.EQUAL);
		List<DcServiceJob> list = (List<DcServiceJob>) enterpriseService.query(query, 0);
		return list;
	}

	@Override
	public ItemPage getDcImpList(DatacenterForm datacenterImpForm) {
		QueryBuilder query = new QueryBuilder(DcServiceJob.class);
		if(StringUtils.isNotEmpty(datacenterImpForm.getJobName())){
			query.where("jobName", datacenterImpForm.getJobName(), QueryAction.LIKE);
		}
		if(StringUtils.isNotEmpty(datacenterImpForm.getJobCode())){
			query.where("jobCode", datacenterImpForm.getJobCode(), QueryAction.LIKE);
		}
		if(StringUtils.isNotEmpty(datacenterImpForm.getServiceStatus())){
			query.where("serviceStatus", Integer.valueOf(datacenterImpForm.getServiceStatus()), QueryAction.EQUAL);
		}
		if(null != datacenterImpForm.getBeginCreateTime()){
			query.where("createDate", datacenterImpForm.getBeginCreateTime(), QueryAction.GE);
		}
		if(null != datacenterImpForm.getEndCreateTime()){
			query.where("createDate", datacenterImpForm.getEndCreateTime(), QueryAction.LE);
		}
		query.where("serviceType", DatacenterConstant.IMP, QueryAction.EQUAL);
		query.where("serviceStatus", DatacenterConstant.SERVICE_DELETE, QueryAction.NOEQUAL);
		query.orderBy("createDate", false);
		
		return enterpriseService.query(query, datacenterImpForm);
	}

	@Override
	public DcServiceJob findDcServiceJobById(String jobId) {
		return (DcServiceJob) enterpriseService.getById(DcServiceJob.class, jobId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DcFtpInterfaceInfo findDcFtpInfoByParentId(String jobId) {
		QueryBuilder query = new QueryBuilder(DcFtpInterfaceInfo.class);
		query.where("parentId", jobId, QueryAction.EQUAL);
		List<DcFtpInterfaceInfo> list = (List<DcFtpInterfaceInfo>) enterpriseService.query(query, 0);
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveEntity(Object item) {
		enterpriseService.save(item);
	}

	@Override
	public void addOrUpdateJob(DcServiceJob dcServiceJob) throws SchedulerException, ParseException{
		StdScheduler scheduler = (StdScheduler) SpringHelper.getBean("schedulerFactoryBean");
		TriggerKey triggerKey = new TriggerKey(dcServiceJob.getJobId()+"_trigger", dcServiceJob.getJobGroup());
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if(null == cronTrigger){//新增
			try {
				Class<?> beanClass = null;
				beanClass = Class.forName(dcServiceJob.getBeanClass());
				JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) beanClass)   // 任务执行类      
				        .withIdentity(dcServiceJob.getJobId(),
								dcServiceJob.getJobGroup())// 任务名，任务组              
				        .build();
				//新建触发器
				cronTrigger = (CronTrigger)TriggerBuilder.newTrigger()
						.withIdentity(dcServiceJob.getJobId() + "_trigger",dcServiceJob.getJobGroup())
						//为触发器设置定时表达式
						.withSchedule(CronScheduleBuilder.cronSchedule(dcServiceJob.getCronExpression()))
						.build();
				//设置存储数据
				cronTrigger.getJobDataMap().put("isAuto", DatacenterConstant.TRIGGER_TYPE_ZD);//默认为自动触发;
				//启动新增定时器任务
				Date d = scheduler.scheduleJob(jobDetail, cronTrigger);
				System.out.println("===========================>>新增定时器任务：" + jobDetail.getJobDataMap().getString("name")
						+ DateUtils.format(d, "yyyy-MM-dd HH:mm:ss", null));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {//更新
			//CronTrigger已存在，那么更新相应的定时配置
//			cronTrigger.setCronExpression("0/30 * * * * ?");
			cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(CronScheduleBuilder.cronSchedule(dcServiceJob.getCronExpression())).build();

			Date fireTime = scheduler.rescheduleJob(triggerKey, cronTrigger);
			if(fireTime != null){
				System.out.println("===========================>>启动定时任务"+dcServiceJob.getJobId()
						+",执行时间："+DateUtils.format(fireTime, "yyyy-MM-dd HH:mm:ss", null));
			} else {
				throw new RuntimeException();
			}
			
			//暂停
//			scheduler.pauseJob(dcServiceJob.getJobId(), dcServiceJob.getJobGroup());
//			scheduler.pauseTrigger(dcServiceJob.getJobId()+"_trigger", dcServiceJob.getJobGroup());
			//恢复
//			scheduler.resumeJob(dcServiceJob.getJobId(), dcServiceJob.getJobGroup());
//			scheduler.resumeTrigger(dcServiceJob.getJobId()+"_trigger", dcServiceJob.getJobGroup());
		}
	}
	
	@Override
	public void pauseJob(DcServiceJob dcServiceJob) throws SchedulerException, ParseException {
		StdScheduler scheduler = (StdScheduler) SpringHelper.getBean("schedulerFactoryBean");
		TriggerKey triggerKey = new TriggerKey(dcServiceJob.getJobId()+"_trigger", dcServiceJob.getJobGroup());
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if(cronTrigger != null){
			JobDataMap jobDataMap = cronTrigger.getJobDataMap();
			jobDataMap.put("isAuto", DatacenterConstant.TRIGGER_TYPE_SD);//设置触发类型为手动触发
			JobKey jobKey = new JobKey(dcServiceJob.getJobId(), dcServiceJob.getJobGroup());
			scheduler.triggerJob(jobKey, jobDataMap);
//			scheduler.pauseTrigger(dcServiceJob.getJobId()+"_trigger", dcServiceJob.getJobGroup());
			System.out.println("===========================>>已暂停服务："+dcServiceJob.getJobId());
		}
	}
	
	@Override
	public void findDcServiceDetail(DatacenterForm datacenterImpForm) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		if(StringUtils.isNotEmpty(datacenterImpForm.getJobId())){
			StringBuffer sql = new StringBuffer();
			sql.append("select t1.job_id,t1.job_name,t1.job_code,t1.bean_class,t1.cron_expression,t1.remark,t1.service_status,")
				.append(" t2.ftp_account,t2.ftp_passwd,t2.file_path,t2.file_pattern,t2.db_tablename ")
				.append(" from dc_service_job t1 join dc_ftp_interface_info t2 on t1.job_id = t2.parent_id ")
				.append(" where t1.job_id = '" + datacenterImpForm.getJobId() + "' ");
			
			List<DatacenterForm> list = jdbcTemplate.query(sql.toString(), new RowMapper<DatacenterForm>(){
				@Override
				public DatacenterForm mapRow(ResultSet rs, int rowNum) throws SQLException {
					DatacenterForm vo = new DatacenterForm();
					vo.setJobId(rs.getString("JOB_ID"));
					vo.setJobName(rs.getString("JOB_NAME"));
					vo.setJobCode(rs.getString("JOB_CODE"));
					vo.setBeanClass(rs.getString("BEAN_CLASS"));
					vo.setCronExpression(rs.getString("CRON_EXPRESSION"));
					vo.setRemark(rs.getString("REMARK"));
					vo.setServiceStatus(rs.getString("SERVICE_STATUS"));
					vo.setFtpAccount(rs.getString("FTP_ACCOUNT"));
					vo.setFtpPasswd(rs.getString("FTP_PASSWD"));
					vo.setFilePath(rs.getString("FILE_PATH"));
					vo.setFilePattern(rs.getString("FILE_PATTERN"));
					return vo;
				}
			});
			if(null!=list && list.size()>0){
				DatacenterForm vo = list.get(0);
				try {
					BeanUtils.copyProperties(datacenterImpForm, vo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
	}

	@Override
	public void updateAll(List<DcServiceJob> list) {
		enterpriseService.updateAll(list);
	}

	@Override
	public void update(Object item) {
		enterpriseService.updateObject(item);
	}

	@Override
	public ItemPage getDcExpList(DatacenterForm datacenterImpForm) {
		QueryBuilder query = new QueryBuilder(DcServiceJob.class);
		if(StringUtils.isNotEmpty(datacenterImpForm.getJobName())){
			query.where("jobName", datacenterImpForm.getJobName(), QueryAction.LIKE);
		}
		if(StringUtils.isNotEmpty(datacenterImpForm.getJobCode())){
			query.where("jobCode", datacenterImpForm.getJobCode(), QueryAction.LIKE);
		}
		if(StringUtils.isNotEmpty(datacenterImpForm.getServiceStatus())){
			query.where("serviceStatus", Integer.valueOf(datacenterImpForm.getServiceStatus()), QueryAction.EQUAL);
		}
		if(null != datacenterImpForm.getBeginCreateTime()){
			query.where("createDate", datacenterImpForm.getBeginCreateTime(), QueryAction.GE);
		}
		if(null != datacenterImpForm.getEndCreateTime()){
			query.where("createDate", datacenterImpForm.getEndCreateTime(), QueryAction.LE);
		}
		query.where("serviceType", "EXP", QueryAction.EQUAL);
		query.where("interfaceType", "FTP", QueryAction.EQUAL);
		query.where("serviceStatus", -1, QueryAction.NOEQUAL);
		query.orderBy("createDate", false);
		
		return enterpriseService.query(query, datacenterImpForm);
	}

	@Override
	public ItemPage getDcMonitorList(DatacenterForm datacenterForm) {
		Class<?>[] pojos = {DcServiceJob.class, DcScheduleJobRecord.class};
		QueryBuilder query = new QueryBuilder(pojos);
		if(StringUtils.isNotEmpty(datacenterForm.getJobName())){
			query.where("a.jobName", datacenterForm.getJobName(), QueryAction.LIKE);
		}
		if(StringUtils.isNotEmpty(datacenterForm.getJobCode())){
			query.where("a.jobCode", datacenterForm.getJobCode(), QueryAction.LIKE);
		}
		if(StringUtils.isNotEmpty(datacenterForm.getServiceType())){
			query.where("a.serviceType", datacenterForm.getServiceType(), QueryAction.EQUAL);
		}
		if(StringUtils.isNotEmpty(datacenterForm.getRunStatus())){
			query.where("b.runStatus", Integer.valueOf(datacenterForm.getRunStatus()), QueryAction.EQUAL);
		}
		query.where("a.jobId=b.parentId");
		query.orderBy("b.createDate", false);
		
		return enterpriseService.query(query, datacenterForm);
	}

	@Override
	public DcScheduleJobRecord getJobRecord(String id, Date previousFireTime) {
		if(StringUtils.isNotEmpty(id) && previousFireTime!=null){
			QueryBuilder query = new QueryBuilder(DcScheduleJobRecord.class);
			String prevFireTime = DateUtils.format(previousFireTime, "yyyy-MM-dd HH:mm:ss", null);
			
			query.where("parentId", id, QueryAction.EQUAL);
			query.where(" to_char(prevRunDate, 'yyyy-mm-dd hh24:mi:ss') = '"+prevFireTime+"'");
			
			List<DcScheduleJobRecord> list = (List<DcScheduleJobRecord>) enterpriseService.query(query, 0);
			if(list!=null && list.size()>0){
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public void closeJob(DcServiceJob dcServiceJob) throws SchedulerException {
		if(StringUtils.isNotEmpty(dcServiceJob.getJobId())){
			StdScheduler scheduler = (StdScheduler) SpringHelper.getBean("schedulerFactoryBean");
			TriggerKey triggerKey = new TriggerKey(dcServiceJob.getJobId()+"_trigger", dcServiceJob.getJobGroup());
			scheduler.pauseTrigger(triggerKey);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public DcJdbcInterfaceInfo findDcJdbcByParentId(String jobId) {
		QueryBuilder query = new QueryBuilder(DcJdbcInterfaceInfo.class);
		query.where("parentId", jobId, QueryAction.EQUAL);
		List<DcJdbcInterfaceInfo> list = (List<DcJdbcInterfaceInfo>) enterpriseService.query(query, 0);
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void runJobNow(DcServiceJob dcServiceJob) throws SchedulerException {
		StdScheduler scheduler = (StdScheduler) SpringHelper.getBean("schedulerFactoryBean");
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(new TriggerKey(dcServiceJob.getJobId()+"_trigger", dcServiceJob.getJobGroup()));
		if(cronTrigger != null){
			JobDataMap jobDataMap = cronTrigger.getJobDataMap();
			jobDataMap.put("isAuto", DatacenterConstant.TRIGGER_TYPE_SD);//设置触发类型为手动触发
			JobKey jobKey = new JobKey(dcServiceJob.getJobId(), dcServiceJob.getJobGroup());
			scheduler.triggerJob(jobKey, jobDataMap);
		}
	}
	



}
