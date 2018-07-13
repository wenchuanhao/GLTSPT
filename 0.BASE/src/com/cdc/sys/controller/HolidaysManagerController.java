package com.cdc.sys.controller;

import com.cdc.sys.form.HolidayQueryForm;
import com.cdc.sys.service.IHolidaysService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;
import model.sys.entity.HolidayInfoStatus;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.trustel.system.SystemConstant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 节假日管理界面
 */
@Controller()
@RequestMapping(value = "/sys/holidaymanager")
public class HolidaysManagerController extends DefaultController{

    @Autowired
    IHolidaysService holidaysService;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 跳往查询列表界面
     * @return
     */
    @RequestMapping(value = {"/main","/query"}, method = {RequestMethod.POST,RequestMethod.GET})
    public String list(Model model,
                       @ModelAttribute("form") HolidayQueryForm form) throws Exception {

        LocalDate nowDate = new LocalDate();
        if (form.getYear() == null )
            form.setYear(nowDate.toString("YYYY"));
        if (form.getMonth() == null )
            form.setMonth(nowDate.toString("MM"));

        ItemPage itemPage = holidaysService.queryHoliday(form);

        model.addAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage);
        model.addAttribute("holiday", HolidayInfoStatus.holiday);
        model.addAttribute("workday", HolidayInfoStatus.workday);
        return "sys/holidays/list";
    }

    @RequestMapping(value = "/remove")
    public String remove(Model model,@RequestParam(required = true) String ids){
        holidaysService.removeSetUp(ids.split(","));
        return "redirect:query";
    }


}
