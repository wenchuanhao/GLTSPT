package com.cdc.dc.account.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.dc.account.common.ComtreeTransitionDTO;
import com.cdc.dc.account.service.IAccountService;

/**
 * 
 * @author xms
 *
 */
@Controller
@RequestMapping(value="account/")
public class ComonController {
	@Autowired
	private IAccountService accService;
	
	//combotree查询部门
	@RequestMapping(value="getDepartment",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<ComtreeTransitionDTO> getDepartment(HttpServletRequest request,HttpServletResponse response,String id){
		//id = id==null ? "80df8fa55ca4048ac2314dab1a52d75e" : id;
		id="80df8fa55ca4048ac2314dab1a52d75e";//查询2级部门
		List<ComtreeTransitionDTO> list = accService.getDepartment(id);
		return list;
	}
	
	//查询费用类型树
	@RequestMapping(value="getCostType",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<ComtreeTransitionDTO> getCostType(HttpServletRequest request,HttpServletResponse response,String id){
		id = id==null ? "ROOT" : id;
		List<ComtreeTransitionDTO> dtos = accService.getCostType(id);
		return dtos;
	}
	
}
