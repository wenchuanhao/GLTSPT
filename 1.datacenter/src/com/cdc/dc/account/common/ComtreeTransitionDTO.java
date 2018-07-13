package com.cdc.dc.account.common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import model.sys.entity.SysHolidayInfo;

import com.cdc.dc.account.model.AccountInfo;
import com.cdc.sys.service.IHolidaysService;
/**
 * combotree转换类
 * @author xms
 *
 */
public class ComtreeTransitionDTO implements Serializable{
	@Autowired
    private IHolidaysService holidayService;
	
	public IHolidaysService getHolidayService() {
		return holidayService;
	}
	public void setHolidayService(IHolidaysService holidayService) {
		this.holidayService = holidayService;
	}
	private String id ;
	public String getId() {
		return id;
	}
	public ComtreeTransitionDTO(String id, String text, String iconCls,
			int checked, String parent_id, 
			String state) {
		super();
		this.id = id;
		this.text = text;
		this.iconCls = iconCls;
		this.checked = checked;
		this.parent_id = parent_id;
		this.state = state;
	}
	public ComtreeTransitionDTO() {
		super();
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	private String text ;
	private String iconCls ;
	private int checked ;
	private String parent_id ;
	private String state ;
	
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat fsDateFormat = new SimpleDateFormat("yyyy-M-d hh:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar uuCalendar = Calendar.getInstance();
		uuCalendar.setTime(new Date());
		System.out.println("made: "+dateFormat.format(uuCalendar.getTime()).contains("2016"));
		
		double dd =getDate(fsDateFormat.parse("2016-08-27 16:09:11"),fsDateFormat.parse("2016-08-28 10:01:32"));
		System.out.println("dd: "+dd);
	}
	
	static String str = "";
	public static double getDate(Date startTime,Date endTime) throws ParseException{
		DateFormat dateYear = new SimpleDateFormat("yyyy");
	    Calendar calendar=Calendar.getInstance();
	    Calendar calendar2=Calendar.getInstance();
	    
	    calendar.setTime(startTime);   
	    calendar2.setTime(endTime);   
	    
	    SimpleDateFormat fsDateFormat = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
	    Calendar calendar6 = Calendar.getInstance();
		calendar6.setTime(new Date());
		//查询节假日结果集
		//List<SysHolidayInfo> sysHolidayInfos = AccountCommon.getHoidlist(dateYear.parse(calendar6.get(Calendar.YEAR)+""));
	    System.out.println(fsDateFormat.format(startTime).length());
	    if(fsDateFormat.format(startTime).length() == 0 ){
	    	
	    }
	    
	    if(!(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY && calendar.get(Calendar.HOUR_OF_DAY)>17 && (calendar2.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar2.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ))){
	    if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ){
	    	calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+2);
	    	calendar.set(Calendar.HOUR_OF_DAY, 9);
	    	calendar.set(Calendar.MINUTE, 0);
	    	calendar.set(Calendar.SECOND, 0);
	    }
	    if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
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
			str="1";
		}if(calendar2.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			calendar2.set(Calendar.DATE, calendar2.get(Calendar.DATE)-2);
			calendar2.set(Calendar.HOUR_OF_DAY, 17);
			calendar2.set(Calendar.MINUTE, 0);
			calendar2.set(Calendar.SECOND, 0);
			str = "1";
		}
		
        int hour2=calendar2.get(Calendar.HOUR_OF_DAY);
        int minute2=calendar2.get(Calendar.MINUTE);
        long time2 = calendar2.getTimeInMillis();  
        
        int between_dd=(int) ((time2-time1)/(1000*3600*24)); 
        int n2 =        (int) ((time2-time1)/(1000*3600*24));
        double between_hh=0;
        Calendar calendar4=Calendar.getInstance();
	    calendar4.setTime(startTime);  
	    int n3 =        (int) ((time2-time1)/(1000*3600*24));
        
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
    		if((hour1<9  && hour2>=9 && hour2<17) || (hour1>=17 &&  hour2>=9 && hour2<17 && calendar4.get(Calendar.DAY_OF_WEEK)!=Calendar.SATURDAY) && calendar4.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY){
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
	   System.out.println("j+: "+j);
	   double sum = between_hh-(double)(j*24);
	   if(sum<0){
		   return 0.0;
	   }
	   return between_hh-(double)(j*24);
	   }
	    return 0.0;
	}
	
}
