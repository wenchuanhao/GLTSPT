package com.cdc.dc.rules.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.DefaultController;

/**
 * 制度提醒
 * @author ZENGKAI
 * @date 2016-04-07 11:58:29
 */
@Controller
@RequestMapping(value = "/rulesController/")
public class RulesRemindController extends DefaultController{

	@Autowired
	private IRulesService rulesService;
	 
}
