package com.cdc.dc.account.task;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.sys.entity.SysHolidayInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cdc.dc.account.common.AccountCommon;
import com.cdc.dc.account.model.AccountInfo;
import com.cdc.dc.account.model.ProblemList;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.sys.service.IHolidaysService;

/**
 * 定时计算报账时效
 * @author xms
 *
 */
public class AccountTask {
	private Log logger = LogFactory.getLog(AccountInfo.class);
	@Autowired
    private IHolidaysService holidayService;
	@Autowired
	private IAccountService accService;

	private AccountCommon comon = new AccountCommon();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat dateYear = new SimpleDateFormat("yyyy");
	
	public IHolidaysService getHolidayService() {
		return holidayService;
	}
	public void setHolidayService(IHolidaysService holidayService) {
		this.holidayService = holidayService;
	}
	public IAccountService getAccService() {
		return accService;
	}
	public void setAccService(IAccountService accService) {
		this.accService = accService;
	}
	
	public void accountTask() throws Exception{
		//logger.info("***********计算报账定时任务开始**********");
		System.out.println("***********计算报账定时任务开始**********");
		long sysStart = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		List<AccountInfo> aList = accService.listQueryAccount();
		//查询节假日结果集
		List<SysHolidayInfo> sysHolidayInfos = AccountCommon.getHoidlist(dateYear.parse(calendar.get(Calendar.YEAR)+""));
		//根据报账ID查问题列表
		for (int l = 0; l < aList.size(); l++) {
			List<ProblemList> problemList =  accService.queryProblemList(aList.get(l).getOrderId());
			
			//根据费用ID查询超时工作日
			RulesType rulesTypes =accService.queryCostTypeById(aList.get(l).getCosId());
			int wd = Integer.parseInt(rulesTypes.getWorkDay());
			
			//查找最耗时问题类型
			//String haoshiType = accService.queryHaoshiType(aList.get(l).getOrderId());
			//request.setAttribute("haoshiType", haoshiType);
			
			String sysdate = dateFormat.format(new Date());
			Date smdate,bdate;
			smdate=new Date(); 
			int proNum=0;//问题总数
			double proHaoshiSum = 0;//问题总耗时
			
			if(problemList!=null){
				//循环问题列表比较问题整改是否超时
				for (int i = 0; i < problemList.size(); i++) {
					proNum++;
					if(problemList.get(i).getCreateTime()!=null){
						String startDate = dateFormat.format(problemList.get(i).getCreateTime());
						Calendar cal = Calendar.getInstance();  
						//超时计算
						double date2 =0;
						if(problemList.get(i).getEndTime()!=null){
							date2 = comon.getDate(problemList.get(i).getCreateTime(),problemList.get(i).getEndTime(),sysHolidayInfos);
						}else {
							date2 = comon.getDate(problemList.get(i).getCreateTime(),new Date(),sysHolidayInfos);
						}
						
						//超时天数
						BigDecimal b = new BigDecimal((date2-(double)wd*24)/(double)24);
						double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
						if(f1<=0){
							problemList.get(i).setIsOutTime(AccountCommon.PROBLEM_IS_OUTTIME_N);
						}else {
							aList.get(l).setIstimeOut("1");
							problemList.get(i).setOutDay(f1+"");
							problemList.get(i).setIsOutTime(AccountCommon.PROBLEM_IS_OUTTIME_Y);
						}
						
						//耗时计算
						double haoshi = 0;
						if(problemList.get(i).getStartTime()!=null){
							if(problemList.get(i).getEndTime()!=null){
								haoshi = comon.getDate(problemList.get(i).getStartTime(),problemList.get(i).getEndTime(),sysHolidayInfos);
							}else {
								haoshi = comon.getDate(problemList.get(i).getStartTime(),new Date(),sysHolidayInfos);
							}
							//耗时天数
							BigDecimal a = new BigDecimal(haoshi/(double)24);
							double f2 = a.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
							
							//求耗时总天数
							proHaoshiSum = proHaoshiSum+f2;
							BigDecimal c = new BigDecimal(proHaoshiSum);
							proHaoshiSum = c.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
							
							if(f2>0){
								problemList.get(i).setSetTime(""+f2);
								aList.get(l).setRectifyTime(proHaoshiSum+"");
							}
						}
						
						//整改总时长
						if(problemList.get(i).getStartTime()!=null){
							String start = dateFormat.format(problemList.get(i).getStartTime());
							double zgTime = 0; 
							
							if(problemList.get(i).getEndTime()!=null){
								zgTime = comon.getDate(problemList.get(i).getStartTime(),problemList.get(i).getEndTime(),sysHolidayInfos);
							}else {
								zgTime = comon.getDate(problemList.get(i).getStartTime(),new Date(),sysHolidayInfos);
							}
							
							BigDecimal d = new BigDecimal((zgTime)/(double)24);
							double f3 = d.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
						}
						accService.updateProblem(problemList.get(i), null);
					}
				}
			}
			
			
			accService.updateTrialAccount(aList.get(l));
		}
		
		long sysEnd = System.currentTimeMillis();
		//logger.info("***********计算报账定时任务执行耗时:"+(sysStart-sysEnd));
		//logger.info("***********计算报账定时任务结束**********");
		System.out.println("***********计算报账定时任务执行耗时:"+(sysEnd-sysStart));
		System.out.println("***********计算报账定时任务结束**********");
	}
}
