package com.cdc.sys.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.HolidayInfoStatus;
import model.sys.entity.SysHolidayInfo;
import model.sys.entity.SysUser;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.sys.service.IHolidaysService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 节假日日历管理界面
 */
@Controller()
@RequestMapping(value = "/sys/holiday")
public class HolidaysCalendarController extends DefaultController{

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    IHolidaysService holidaysService;
    
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 跳往日历查看界面
     * @return
     */
    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public String calendarShow(Model model){

        model.addAttribute("holiday", HolidayInfoStatus.holiday);
        model.addAttribute("workday", HolidayInfoStatus.workday);
        return "sys/holidays/calendar";
    }

    /**
     * 获取指定时间范围的节假日设置列表
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    @ResponseBody
    public String fillData(String start,String end){
        JSON resultJSON = null;
        Map<String,Object> returnMessage = new HashMap<String, Object>();
        if (start == null || end == null) {
            returnMessage.put("flag","error");
            returnMessage.put("message","非法操作");
        } else {
            List<SysHolidayInfo> holidayInfoList = holidaysService.getHolidayByMonth(start,end,returnMessage);
            if (!"error".equals(returnMessage.get("flag"))) {
                Collection<Map> collection = Lists.<SysHolidayInfo,Map>transform(holidayInfoList,new Function<SysHolidayInfo, Map>() {
                    public Map apply(SysHolidayInfo sysHolidayInfo) {
                        Map<String, Object> m = Maps.newHashMap();
                        m.put(dateFormat.format(sysHolidayInfo.getTargetDate()),sysHolidayInfo.getDayType());
                        return m;
                    }
                });
                returnMessage.put("flag","success");
                returnMessage.put("content",JSONArray.fromObject(collection));
            }
        }

        resultJSON = JSONObject.fromObject(returnMessage);
        return resultJSON.toString();
    }

    /**
     * 切换指定日期的节假日标识
     * @param way
     * @param monthay
     * @return
     */
    @RequestMapping(value = "/switch/{way}",method = RequestMethod.POST)
    @ResponseBody
    public String switchDayType(HttpServletRequest request,
                                @PathVariable(value = "way") String way,
                                String monthay){

        SysUser user = getVisitor(request);

        Map<String,Object> returnMessage = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(way) && StringUtils.isNotBlank(monthay)) {
            Long flag = holidaysService.switchDayType(way,monthay,returnMessage, user);
        } else {
            returnMessage.put("flag","error");
            returnMessage.put("message","非法操作");
        }
        return JSONObject.fromObject(returnMessage).toString();
    }
}
