package com.cdc.sys.dataWarn.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.dataWarn.form.DataSourceWarnForm;
import com.cdc.sys.dataWarn.model.DataSourceWarn;
import com.cdc.sys.dataWarn.service.IDataWarnService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

@Controller
@RequestMapping(value="/sys/dataWarn/")
public class DataWarnController extends DefaultController{
	
	@Autowired
	private IDataWarnService warnService;
	
	@RequestMapping(value="queryIndex",method={RequestMethod.GET,RequestMethod.POST})
	public String queryDataWarn(HttpServletRequest request,DataSourceWarnForm warnForm){
		String type = request.getParameter("type");
		if(type==null){
			type = "1";
			request.setAttribute("type", "1");
		}else {
			request.setAttribute("type", type);
		}
		ItemPage itemPage = warnService.queryDataPage(warnForm, type);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		request.setAttribute("warnForm", warnForm);
		return "sys/dataWarn/warnIndex";
	}
	
	//配置数据源阀值
	@RequestMapping(value="addWarn",method={RequestMethod.GET,RequestMethod.POST})
	public String addWarn(HttpServletRequest request,DataSourceWarn sourceWarn){
		String addType = request.getParameter("addType");
		if(addType!=null && addType.equals("1")){
			return "sys/dataWarn/addWarn";
		}else {
			sourceWarn.setConfig("2");
			sourceWarn.setWarnTime(new Date());
			warnService.addWarn(sourceWarn);
			return "redirect:/sys/dataWarn/queryIndex?type=2";
		}
	}
}
