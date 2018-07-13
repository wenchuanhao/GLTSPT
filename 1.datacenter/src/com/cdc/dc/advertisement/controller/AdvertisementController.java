package com.cdc.dc.advertisement.controller;

import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.dc.advertisement.form.AdvertisementForm;
import com.cdc.dc.advertisement.model.Advertisement;
import com.cdc.dc.advertisement.service.IAdvertisementService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;

/**
 * 广告配置列表
 * @author KuangJiaen
 * @date 2016-9-26 下午15:21:30
 */
@Controller
@RequestMapping(value = "/advertisement/")
public class AdvertisementController extends CommonController {
	
	@Autowired
	private IAdvertisementService advertisementService;
	
	
	/**
     * 配置列表
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request,HttpServletResponse response,AdvertisementForm ad) throws Exception{
		request.setAttribute("ad", ad);
		
		ItemPage itemPage =(ItemPage) advertisementService.findAdvertisement(ad);
		
		request.setAttribute("advertisement", itemPage.getItems());
		
		return "/dc/advertisement/advertisementlist";
	}
	
	/**
	  * 新增
	  */
	@RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
	 public String add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("configId");
		Advertisement ad = null;
		if (id != null && !id.equals("")) {
			ad =(Advertisement) advertisementService.getEntity(Advertisement.class, id);
		}
		if (ad == null){
			ad = new Advertisement();
			ad.setConfigId(UUID.randomUUID().toString());
		}
		
		request.setAttribute("ad", ad);
    	request.setAttribute("fileTempId", ad.getConfigId());
		
		return "dc/advertisement/advertisementAdd";
	 }
	
	/**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response,Advertisement ad){
    	//保存失败
    	String result = "0";
    	try{
    		SysUser sysUser = getVisitor(request);
    		
    		advertisementService.saveOrUpdateAdvertisement(ad, sysUser);
    		
    		result = "1";//保存成功
			PrintWriter out = response.getWriter();
			out.write(result);
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 删除
     */
    @RequestMapping(value="deleteAd",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
    public void deleteAd(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");
		String result = "0";
		try{
			if(StringUtils.isNotEmpty(id)){
				Advertisement ad = (Advertisement) advertisementService.getEntity(Advertisement.class, id);
				if(ad != null){
					advertisementService.delAdvertisement(ad);
					result = "1";
				}
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
	 
}
