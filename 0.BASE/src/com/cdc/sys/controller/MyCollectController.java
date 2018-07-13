package com.cdc.sys.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.MyCollect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cdc.sys.service.IMyCollectService;
import com.cdc.system.core.authentication.controller.DefaultController;

/**
 * 我的工作台 收藏
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/sys/mywork/*")
public class MyCollectController extends DefaultController {
	@Autowired
	private IMyCollectService myCollectService;

	@RequestMapping(value = "addCollect", method = {RequestMethod.POST })
	public String addCollect(HttpServletRequest request) {
		String collectName=request.getParameter("collectName");
		String collectType=request.getParameter("collectType");
		String collectUrl=request.getParameter("collectUrl");
		String result="0";
		try {
			MyCollect collect=new MyCollect();
			collect.setCollectName(collectName);
			collect.setCollectStatus("1");
			collect.setCollectType(collectType);			
			collect.setCollectTime(new Date());
			collect.setCollectUrl(collectUrl);
			collect.setUserId(getVisitor(request).getAccount());
			myCollectService.addCollect(collect);
			
			result="1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
