package com.cdc.sys.service;

import com.cdc.sys.form.HolidayQueryForm;
import com.trustel.common.ItemPage;
import model.sys.entity.SysHolidayInfo;
import model.sys.entity.SysUser;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 节假日业务处理
 */
public interface IHolidaysService {
	/**
	 * 获取节假日list
	 */
	List<SysHolidayInfo> getHolidayList(Date startDate ,Date endDate);

    /**
     * 获取指定时间范围内节假日列表
     * @param start
     * @param end
     * @return
     */
    public List<SysHolidayInfo> getHolidayByMonth(String start ,String end,Map<String, Object> returnMessage);

    /**
     * 切换对应日期的节假日类型
     * @param way
     * @param monthay
     * @param returnMessage
     * @return
     */
    public Long switchDayType(String way, String monthay, Map<String, Object> returnMessage,SysUser loginUser);


    /**
     * 查询节假日信息
     * @param queryForm
     * @return
     * @throws Exception
     */
    public ItemPage queryHoliday(HolidayQueryForm queryForm) throws Exception;

    /**
     * 撤销配置
     * @param ids
     * @return
     */
    public long removeSetUp(String[] ids);

    /**
     * 判断timeLimit之后,startTime是否超时
     * @param startTime
     * @param timeLimit
     * @return
     */
    public long isTimeOut(Date startTime,Long timeLimit);
    
    /**
     * 
     * @param startTime 业务时间
     * @param timeLimit 设置超时天数
     * @return
     */
    public double outDay(Date startTime,Long timeLimit);
}
