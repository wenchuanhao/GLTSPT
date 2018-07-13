package com.cdc.dc.account.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cdc.sys.service.IHolidaysService;
import com.cdc.system.core.util.SpringHelper;

import model.sys.entity.SysHolidayInfo;

public class AccountCommon {
   
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 报账单据状态1-整改中
	 */
	public final static String ACCOUNT_STATUS_ZGZ="1";
	/**
	 * 报账单据状态2-无问题
	 */
	public final static String ACCOUNT_STATUS_WWT="2";
	/**
	 * 报账单据状态3-整改结束
	 */
	public final static String ACCOUNT_STATUS_ZGJS="3";
	/**
	 * 报账单据状态4-退单
	 */
	public final static String ACCOUNT_STATUS_TD="4";
	
	/**
	 * 问题单据状态1-整改中
	 */
	public final static String PROBLEM_STATUS_ZGZ="1";
	/**
	 * 问题单据状态2-整改通过
	 */
	public final static String PROBLEM_STATUS_ZGTG="2";
	/**
	 * 问题单据状态3-整改不通过
	 */
	public final static String PROBLEM_STATUS_ZGBTG="3";
	/**
	 * 问题单据状态4-退单
	 */
	public final static String PROBLEM_STATUS_TD="4";
	/**
	 * 问题单据状态5-草稿暂存
	 */
	public final static String PROBLEM_STATUS_CG="5";
	
	/**
	 * 当前环节-初审录入
	 */
	public final static String CURRENT_LINK_CSLR="初审录入";
	/**
	 * 当前环节-信息补录
	 */
	public final static String CURRENT_LINK_XXBL="信息补录";
	/**
	 * 当前环节-完成
	 */
	public final static String CURRENT_LINK_WC="完成";
	/**
	 * 问题列表-是否超时-1是
	 */
	public final static String PROBLEM_IS_OUTTIME_Y="1";
	/**
	 * 问题列表-是否超时-0否
	 */
	public final static String PROBLEM_IS_OUTTIME_N="0";
	/**
	 * 报账单据-信息补录状态-2审批流程信息已补录
	 */
	public final static String ACCOUNT_RECORD_FLAT="2";
	/**
	 * 报账单据-信息补录状态-3税务信息、合同发票信息已补录
	 */
	public final static String ACCOUNT_RECORD_FLAT_SHUIWU="3";
	/**
	 * 报账单据-初审录入状态-2已办
	 */
	public final static String ACCOUNT_ACCOUNT_FLAT_YB="2";
	/**
	 * 报账单据-初审录入状态-1代办
	 */
	public final static String ACCOUNT_ACCOUNT_FLAT_DB="1";
	
	public final static String ACC_DICT_TYPE =  "ACCOUNT_ROLE";//数据字典类型配置
	public final static String ACCOUNT_KJ =  "1";//初审会计
	
	public final static String ACCOUNT_ADMIN = "baozhangAdmin";//报账管理员
	
	/**
	 * 查询超时工作日 （朝九晚五）返回超时小时
	 * @param startTime
	 * @param endTime
	 * @param List<SysHolidayInfo> holidayInfos
	 * @return
	 */
	public static double getDate(Date startTime,Date endTime,List<SysHolidayInfo> holidayInfos){
		
		if(startTime == null || endTime == null){
			return 0d;
		}
		
		if(startTime.after(endTime)){
			return 0d;
		}
		
	    Calendar calendar=Calendar.getInstance();
	    Calendar calendar2=Calendar.getInstance();
	    Calendar calendar5=Calendar.getInstance();
	    
	    calendar.setTime(startTime);   
	    calendar5.setTime(startTime);   
	    calendar2.setTime(endTime);  
	    int holiday = 0;
		int wordday = 0;
			
		//查出节假日和加班日
	    if(!(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY && calendar.get(Calendar.HOUR_OF_DAY)>17 && (calendar2.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar2.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ))){
			for (int r = 0; r < holidayInfos.size(); r++) {
				if(endTime.after(holidayInfos.get(r).getTargetDate()) && startTime.before(holidayInfos.get(r).getTargetDate()) && holidayInfos.get(r).getDayType().equals("holiday")){
					holiday=holiday+1;
				}else if(endTime.after(holidayInfos.get(r).getTargetDate()) && startTime.before(holidayInfos.get(r).getTargetDate()) && holidayInfos.get(r).getDayType().equals("workday")){
					wordday=wordday+1;
				}
			}
	    	
		    if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ){
		    	calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+2);
		    	calendar.set(Calendar.HOUR_OF_DAY, 9);
		    	calendar.set(Calendar.MINUTE, 0);
		    	calendar.set(Calendar.SECOND, 0);
		    }else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
		    	calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
		    	calendar.set(Calendar.HOUR_OF_DAY, 9);
		    	calendar.set(Calendar.MINUTE, 0);
		    	calendar.set(Calendar.SECOND, 0);
			}
		    int hour1=calendar.get(Calendar.HOUR_OF_DAY);
		    int minute1=calendar.get(Calendar.MINUTE);
	        long time1 = calendar.getTimeInMillis();                 
	          
	        if(calendar2.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ){
				calendar2.set(Calendar.DATE, calendar2.get(Calendar.DATE)-1);
				calendar2.set(Calendar.HOUR_OF_DAY, 17);
				calendar2.set(Calendar.MINUTE, 0);
				calendar2.set(Calendar.SECOND, 0);
				
			}else if(calendar2.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
				calendar2.set(Calendar.DATE, calendar2.get(Calendar.DATE)-2);
				calendar2.set(Calendar.HOUR_OF_DAY, 17);
				calendar2.set(Calendar.MINUTE, 0);
				calendar2.set(Calendar.SECOND, 0);
			}
			
	        int hour2=calendar2.get(Calendar.HOUR_OF_DAY);
	        int minute2=calendar2.get(Calendar.MINUTE);
	        long time2 = calendar2.getTimeInMillis();  
	        
	        int between_dd=(int) ((time2-time1)/(1000*3600*24)); 
	        double between_hh=0;
	        
	        
	        if(hour1>=9 && hour1<17 && hour2>=9 && hour2<17){
	        	if((hour2+(double)minute2/60) - (hour1+(double)minute1/60)<0){
	        		double i = (17-((double)hour1+((double)minute1/60)))+((((double)minute2/60)+(double)hour2)-9);
	        		int n = (int) ((time2-time1)/(1000*3600*24));
	        		between_hh =(double)(n*24)+(i*3);
	        	}else {
	    			double i =(((double)minute2/60)+(double)hour2)-((double)hour1+((double)minute1/60));
	        		int n = (int)((time2-time1)/(1000*3600*24));
	        		between_hh = (double)(n*24)+(i*3); 
				}
	        }else {
	    		if((hour1<9  && hour2>=9 && hour2<17) || (hour1>=17 &&  hour2>=9 && hour2<17)){
    	        	int n = (int) ((time2-time1)/(1000*3600*24));
    	        	between_hh = (double)(n*24+((((double)minute2/60)+(double)hour2)-9)*3);
				}else if((hour1>=9 && hour1<17 && hour2<9) || (hour1>=9 && hour1<17  && hour2>=17)){
					int n = (int) ((time2-time1)/(1000*3600*24));
		        	between_hh = (double)(n*24+(double)(17-((double)hour1+((double)minute1/60)))*3);
				}else if((hour1<9 && hour2>=17)){
					int n = (int) ((time2-time1)/(1000*3600*24));
					between_hh = (double)(n*24+24);
				}else if((hour2<9 && hour1>=17) || (hour1>=17 && hour2>=17)  || (hour1<9 && hour2<9)){
					int n = (int) ((time2-time1)/(1000*3600*24));
					between_hh = (double)(n*24);
				}
	        }
	        
		    int i=0;
		    double j=0;
		    Calendar calendar3=Calendar.getInstance();
		    calendar3.setTime(endTime);
		    while(i<(int)between_dd){
		        calendar.add(Calendar.DATE,1);
		        i++;
		        if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || 
		                calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
		        	if((calendar3.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY && calendar.get(Calendar.DATE)==calendar3.get(Calendar.DATE))                  
		        			|| (  calendar3.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY && calendar.get(Calendar.DATE)==calendar3.get(Calendar.DATE))){
		        		continue ;
		        	}
		        	j++;
		        }
		    }
		    double sum = between_hh-(double)(j*24)-(double)holiday*24+(double)wordday*24;
		    if(sum<0){
			   return 0.0;
		    }
		    return sum;
	    }
	    return 0.0;
	}
	
	/**
	 * 查询>=startDate结果集
	 * 注意startDate参数不能大于getDate()方法参数中的开始日期
	 * 建议startDate为getDate()中的开始日期为最好
	 * @return 返回节假日表信息列表
	 */
	public static List<SysHolidayInfo> getHoidlist(Date startDate){
	    IHolidaysService holidayService = (IHolidaysService) SpringHelper.getBean("holidayService");
	    List<SysHolidayInfo> hoidlist =holidayService.getHolidayList(startDate, null);
		return hoidlist;
	}
	
}
