package com.cdc.sys.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.sys.entity.HolidayInfoStatus;
import model.sys.entity.SysHolidayInfo;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.joda.time.LocalDate;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.ServiceException;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.form.HolidayQueryForm;
import com.trustel.common.ItemPage;

/**
 * Created by nike on 2014/4/14.
 */
public class HolidayService implements IHolidaysService {

    private IEnterpriseService enterpriseService;

    private DateFormat DF_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat DF_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<SysHolidayInfo> getHolidayByMonth(String start ,String end,Map<String, Object> returnMessage) {
        LocalDate startDate = null,endDate = null;
        try {
            startDate = LocalDate.parse(start);
            endDate = LocalDate.parse(end);
        } catch (IllegalArgumentException e) {
            returnMessage.put("flag","error");
            returnMessage.put("message","非法操作:错误的目标日期格式[yyyy-MM-dd]");
            e.printStackTrace();
        }
        QueryBuilder queryBuilder = new QueryBuilder(SysHolidayInfo.class);
        queryBuilder.where("targetDate", startDate.toDate(), QueryAction.GE);
        queryBuilder.where("targetDate", endDate.plusDays(1).toDate(),QueryAction.LT);
        return (List<SysHolidayInfo>) enterpriseService.query(queryBuilder,-1);
    }

    public Long switchDayType(String way, String monthay, Map<String, Object> returnMessage,SysUser loginUser) {

        long flag = 0;

        Date targetDate = null;
        HolidayInfoStatus dateType = null;
        try {
            targetDate = LocalDate.parse(monthay).toDate();
        } catch (IllegalArgumentException e) {
            returnMessage.put("flag","error");
            returnMessage.put("message","非法操作:错误的目标日期格式[yyyy-MM-dd]");
            e.printStackTrace();
        }

        try {
            dateType = HolidayInfoStatus.valueOf(way);
        } catch (IllegalArgumentException e) {
            returnMessage.put("flag","error");
            returnMessage.put("message","非法操作:错误类型数据");
            e.printStackTrace();
        }

        QueryBuilder queryBuilder = new QueryBuilder(SysHolidayInfo.class);
        queryBuilder.where("targetDate", LocalDate.parse(monthay).toDate(), QueryAction.EQUAL);

        SysHolidayInfo dbSysHolidayInfo = (SysHolidayInfo)enterpriseService.getById(SysHolidayInfo.class,monthay);

        try {
            if (dbSysHolidayInfo == null) {
                //新增
                SysHolidayInfo addSysHolidayInfo = new SysHolidayInfo();
                addSysHolidayInfo.setDayType(dateType.name());
                addSysHolidayInfo.setId(monthay);
                addSysHolidayInfo.setTargetDate(targetDate);
                addSysHolidayInfo.setLastUpdateDate(new Date());
                addSysHolidayInfo.setLastUpdateUser(loginUser.getUserName());
                addSysHolidayInfo.setLastUpdateUserId(loginUser.getUserId());

                enterpriseService.save(addSysHolidayInfo);
            } else {
                //直接删除
                enterpriseService.getSessions().delete(dbSysHolidayInfo);
//                dbSysHolidayInfo.setLastUpdateDate(new Date());
//                dbSysHolidayInfo.setDayType(dateType.name());
//                enterpriseService.updateObject(dbSysHolidayInfo);
            }

            returnMessage.put("flag","success");
        } catch (ServiceException e) {
            flag = -1;
            returnMessage.put("flag","error");
            returnMessage.put("message","操作失败");
            e.printStackTrace();
        }
        return flag;
    }
    
    public ItemPage queryHoliday(HolidayQueryForm queryForm) throws Exception{

        QueryBuilder queryBuilder = new QueryBuilder(SysHolidayInfo.class);

        if (StringUtils.isNotBlank(queryForm.getYear()) && StringUtils.isNotBlank(queryForm.getMonth())) {
            queryBuilder.where("to_char(targetDate,'yyyy-MM')",queryForm.getYear()+"-"+queryForm.getMonth());
        } else if (StringUtils.isNotBlank(queryForm.getYear())) {
            queryBuilder.where("to_char(targetDate,'yyyy')",queryForm.getYear());
        } else if (StringUtils.isNotBlank(queryForm.getMonth())) {
            queryBuilder.where("to_char(targetDate,'MM')",queryForm.getMonth());
        }

        if (StringUtils.isNotBlank(queryForm.getCreateUser())) {
            queryBuilder.where("lastUpdateUser",queryForm.getCreateUser(),QueryAction.LIKE);
        }

        if (StringUtils.isNotBlank(queryForm.getSourceType())) {
            queryBuilder.where("dayType",queryForm.getSourceType(),QueryAction.NOEQUAL);
        }

        if (StringUtils.isNotBlank(queryForm.getTargetType())) {
            queryBuilder.where("dayType",queryForm.getTargetType());
        }

        if (StringUtils.isNotBlank(queryForm.getQueryStartCreateDate())) {
            queryBuilder.where("lastUpdateDate >= to_date(?,'yyyy-MM-dd hh24:mi:ss')",queryForm.getQueryStartCreateDate(),QueryAction.CUSTOM);
        }

        if (StringUtils.isNotBlank(queryForm.getQueryEndCreateDate())) {
            queryBuilder.where("lastUpdateDate <= to_date(?,'yyyy-MM-dd hh24:mi:ss')",queryForm.getQueryEndCreateDate(),QueryAction.CUSTOM);
        }

        queryBuilder.orderBy("lastUpdateDate",false);

        return enterpriseService.query(queryBuilder,queryForm);
    }

    public long removeSetUp(String[] ids){
        long flag;
        try {
            QueryBuilder deleteHql = new QueryBuilder(SysHolidayInfo.class);
            deleteHql.where("id",ids);
            flag = enterpriseService.delete(deleteHql);
        } catch (ServiceException e) {
            flag = -1;
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 加入节假日之后判断从起始日期后是否过期
     * @param startTime
     * @param timeLimit
     * @return
     */
    public long isTimeOut(Date startTime,Long timeLimit){
        if (timeLimit == null) {
            return 0L;
        }

        Query query = enterpriseService.getSessions().createSQLQuery("select is_holiday_time_out(to_date(:queryDate,'yyyy-MM-dd hh24:mi:ss'),:timeLimit) from dual");
        query.setString("queryDate",DF_DATE_TIME.format(startTime));
        query.setLong("timeLimit",timeLimit);
        String result = (String) query.uniqueResult();
        if ("Y".equals(result)) {
            return 1L;
        }else if ("N".equals(result)) {
            return 2L;
        } else {
            return 0L;
        }
        //return timeOutCompTo(LocalDate.fromDateFields(startTime),timeLimit,cache);

    }

    public boolean timeOutCompTo(LocalDate startTime,Long timeLimit,Map<String ,SysHolidayInfo> data){

        SysHolidayInfo sysHolidayInfo = data.get(DF_DATE.format(startTime));

        if(sysHolidayInfo != null) {
            //根据设定值判断
            if (HolidayInfoStatus.holiday.name().equals(sysHolidayInfo.getDayType())) {
                return timeOutCompTo(startTime.plusDays(1),timeLimit,data);
            } else {
                timeLimit-=1;
                if (timeLimit > 0) {
                    return timeOutCompTo(startTime.plusDays(1),timeLimit,data);
                } else {
                    if(startTime.toDate().before(new Date()) && timeLimit == 0){
                        return timeOutCompTo(startTime.plusDays(1),timeLimit,data);
                    } else {
                        if (startTime.toDate().before(new Date())) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        } else {
            //根据工作日或者休息日判断
            if(startTime.getDayOfWeek() <= 5) {
                timeLimit-=1;
                if (timeLimit > 0) {
                    return timeOutCompTo(startTime.plusDays(1),timeLimit,data);
                } else {
                    if(startTime.toDate().before(new Date()) && timeLimit == 0){
                        return timeOutCompTo(startTime.plusDays(1),timeLimit,data);
                    } else {
                        if (startTime.toDate().before(new Date())) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                return timeOutCompTo(startTime.plusDays(1),timeLimit,data);
            }

        }

    }



    public IEnterpriseService getEnterpriseService() {
        return enterpriseService;
    }

    public void setEnterpriseService(IEnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

	@Override
	public double outDay(Date startTime, Long timeLimit) {
		if (timeLimit == null || timeLimit==0) {
            return 0.0;
        }

        Query query = enterpriseService.getSessions().createSQLQuery("select time_out_day(to_date(:queryDate,'yyyy-MM-dd hh24:mi:ss'),:timeLimit) from dual");
        query.setString("queryDate",DF_DATE_TIME.format(startTime));
        query.setLong("timeLimit",timeLimit);
        String result = (String) query.uniqueResult();
        double db = Double.parseDouble(result); 
        BigDecimal b = new BigDecimal(db); 
        double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		return f1;
	}

	@Override
	public List<SysHolidayInfo> getHolidayList(Date startDate, Date endDate) {
		QueryBuilder query = new QueryBuilder(SysHolidayInfo.class);
		query.where("targetDate",startDate,QueryAction.GE);
		query.where("targetDate",endDate,QueryAction.LE);
		return (List<SysHolidayInfo>) enterpriseService.query(query, 0);
	}
}
