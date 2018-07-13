package com.cdc.dc.rules.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.DefaultController;

/**
 * 待阅制度审核
 * @author ZENGKAI
 * @date 2016-04-13 11:58:29
 */
@Controller
@RequestMapping(value = "/rulesController/")
public class RulesTobereadController extends DefaultController{

	@Autowired
	private IRulesService rulesService;
}
