package com.cdc.dc.command.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.command.manage.common.CommandCommon;
import com.cdc.dc.command.manage.form.CommandForm;
import com.cdc.dc.command.manage.model.CommandFolder;
import com.cdc.dc.command.manage.model.CommandInfo;
import com.cdc.dc.command.manage.model.CommandMoborg;
import com.cdc.dc.command.manage.service.ICommandService;
import com.cdc.inter.client.ws.sms.service.ISmsService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.trustel.common.ItemPage;
/**
 * 工程指令管理-前端h5页面
 * @author ZengKai
 * @date 2016-09-05 09:35:49
 */
@Controller
@RequestMapping(value = "/command/h5/")
public class CommandH5Controller extends CommonController{
	@Autowired
	private ICommandService commandService;
	@Autowired
	private ISmsService smsService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	/**
     * 前端号码登录页面
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "doMobileLogin",method = {RequestMethod.GET,RequestMethod.POST})
    public String doMobileLogin(HttpServletRequest request,HttpServletResponse response){
    	String id = request.getParameter("id");
    	request.setAttribute("id", id);//指令信息主键id
    	if(StringUtils.isNotEmpty(id)){
    		CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
    		//未流转
    		if(cdinfo != null && CommandCommon.commandStatus_WLZ.equals(cdinfo.getCommandStatus())){
    			return "/dc/command/h5/notroam";
    		}
    	}
    	String mobile = (String) request.getSession().getAttribute(CommandCommon.MOBILESESSION);
    	SysUser sysUser = getVisitor(request);
    	//号码登录了，用户未登录
    	if(StringUtils.isNotEmpty(mobile) && sysUser == null){
    		return "redirect:/command/h5/doAccountLogin?id="+id;
    	}
    	//号码及用户均登录
    	if(StringUtils.isNotEmpty(mobile) && sysUser != null){
    		return "redirect:/command/h5/receiveDoc?id="+id;
    	}
    	return "/dc/command/h5/mobileLogin";
	}
    
    /**
     * 前端账号登录页面
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "doAccountLogin",method = {RequestMethod.GET,RequestMethod.POST})
    public String doAccountLogin(HttpServletRequest request,HttpServletResponse response){
    	String mobile = (String) request.getSession().getAttribute(CommandCommon.MOBILESESSION);
    	String id = request.getParameter("id");
    	if(StringUtils.isNotEmpty(id)){
    		CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
    		//未流转
    		if(cdinfo != null && CommandCommon.commandStatus_WLZ.equals(cdinfo.getCommandStatus())){
    			return "/dc/command/h5/notroam";
    		}
    	}
    	//未通过短信登录
    	if(StringUtils.isEmpty(mobile)){
    		return "redirect:/command/h5/doMobileLogin?id="+id;
    	}
    	//通过号码查询到了用户信息
    	SysUser sysuser = commandService.querySysUserByMobile(mobile);
    	//查询号码管理的组织架构用户
    	if(sysuser == null){
    		CommandMoborg cmo = commandService.queryCommandMoborgByMobile(mobile, null);
    		if(cmo != null && StringUtils.isNotEmpty(cmo.getCreateUserid())){
    			sysuser = (SysUser) commandService.getEntity(SysUser.class, cmo.getCreateUserid());
    		}
    	}
    	
    	if(sysuser != null){
    		  //业主、专家顾问、施工单位、监理单位、造价单位
    	  	List<SysRole> srList = commandService.getCommandRoles(sysuser);
    	  	if(srList != null && srList.size() > 0){
    	  		//将用户信息放入session，并跳转至接收页面
    	  		request.getSession().setAttribute(SystemConstant.SESSION_VISITOR, sysuser);
    	  		return "redirect:/command/h5/receiveDoc?id="+id;
    	  	}
    	}
 
    	request.setAttribute("id", id);
    	return "/dc/command/h5/accountLogin";
    }
    
    /**
     * 接收文档页面
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "receiveDoc",method = {RequestMethod.GET,RequestMethod.POST})
    public String receiveDoc(HttpServletRequest request,HttpServletResponse response){
    	String mobile = (String) request.getSession().getAttribute(CommandCommon.MOBILESESSION);
    	String id = request.getParameter("id");
    	request.setAttribute("id", id);
    	CommandInfo cdinfo = null;
    	if(StringUtils.isNotEmpty(id)){
    		cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
    		//未流转
    		if(cdinfo != null && CommandCommon.commandStatus_WLZ.equals(cdinfo.getCommandStatus())){
    			return "/dc/command/h5/notroam";
    		}
    	}
    	//未通过短信登录
    	if(StringUtils.isEmpty(mobile)){
    		return "redirect:/command/h5/doMobileLogin?id="+id;
    	}
    	SysUser sysUser = getVisitor(request);
    	//用户登录界面
    	if(sysUser == null){
    		return "redirect:/command/h5/doAccountLogin?id="+id;
    	}
    	if(StringUtils.isNotEmpty(id)){
    		request.setAttribute("vo", cdinfo);
    	}
	  	List<SysRole> srList = commandService.getCommandRoles(sysUser);
	  	//业主、专家顾问、资料管理员
	  	String role1 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_OWNER).getParameterValue();
	  	String role2 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_ADVISER).getParameterValue();
	  	String role3 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_ADMIN).getParameterValue();
	  	//施工单位、监理单位、造价单位
	  	String role4 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_CONSTRUCTION).getParameterValue();
	  	String role5 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_SUPERVISION).getParameterValue();
	  	String role6 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_COST).getParameterValue();
	  	
	  	
	  	List<Map<String, String>> roles = new ArrayList<Map<String, String>>();
	  	if(srList != null && srList.size() > 0 ){
	  		for (SysRole sysRole : srList) {
	  			//业主、专家顾问、资料管理员
				if(role1.equals(sysRole.getRoleCode()) || role2.equals(sysRole.getRoleCode()) || role3.equals(sysRole.getRoleCode())){
					Map<String, String> keyvalue = new HashMap<String, String>();
					keyvalue.put("value", sysRole.getRoleId());
					keyvalue.put("name", sysRole.getRoleName());
					roles.add(keyvalue);
				}
				if(role4.equals(sysRole.getRoleCode()) || role5.equals(sysRole.getRoleCode()) || role6.equals(sysRole.getRoleCode())){
					//登录用户的组织架构
	  				SysOrganization sysOrg = (SysOrganization) commandService.getEntity(SysOrganization.class, sysUser.getOrganizationId());
	  				Map<String, String> keyvalue = new HashMap<String, String>();
	  				keyvalue.put("value", sysOrg.getOrganizationId());
	  				keyvalue.put("name", sysOrg.getOrgName());
	  				roles.add(keyvalue);
				}
				//资料管理员可以归档
				if(role3.equals(sysRole.getRoleCode())){
					request.setAttribute("COMMAND_ADMIN", "1");
				}
			}
	  	}
	  	
	  	List<CommandMoborg> cmolist = commandService.queryCommandMoborgListByMobile(mobile);
	  	if(cmolist != null && cmolist.size() > 0 ){
	  		for (CommandMoborg commandMoborg : cmolist) {
	  			if(StringUtils.isNotEmpty(commandMoborg.getOrgId()) && !commandMoborg.getOrgId().equals(sysUser.getOrganizationId())){
	  				//绑定用户的组织架构
	  				SysOrganization sysOrg = (SysOrganization) commandService.getEntity(SysOrganization.class, commandMoborg.getOrgId());
	  				Map<String, String> keyvalue = new HashMap<String, String>();
	  				keyvalue.put("value", sysOrg.getOrganizationId());
	  				keyvalue.put("name", sysOrg.getOrgName());
	  				roles.add(keyvalue);
	  			}
	  		}
	  	}
		
	  	request.setAttribute("roles", JSONArray.fromObject(roles).toString());
    	return "/dc/command/h5/receiveDoc";
    }
    /**
  	 * 指令接收保存
  	 * @param request
  	 * @param rulesInfo
  	 * @return
  	 * @throws Exception
  	 */
  	@RequestMapping(value = "receive", method = {RequestMethod.GET, RequestMethod.POST})
  	public void receive(HttpServletRequest request,HttpServletResponse response){
  		SysUser sysUser = getVisitor(request);
		String flowRoleid = request.getParameter("flowRoleid");
		String flowRolename = request.getParameter("flowRolename");
		String mobile = (String) request.getSession().getAttribute(CommandCommon.MOBILESESSION);
		if(StringUtils.isEmpty(mobile)){
			print(response,false,"登录信息丢失，请重新登录！");
			return;
		}
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			print(response,false,"文档信息丢失，请选择文档！");
			return;
		}
		if(sysUser != null){
			sysUser.setCommandRoleId(flowRoleid);//组织/角色id
			sysUser.setCommandRoleName(flowRolename);//组织/角色名称
		}
		if(StringUtils.isNotEmpty(id)){
    		CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
    		//接收文档
    		String result = commandService.setCommandReceive(cdinfo,sysUser,mobile);
    		if("1".equals(result)){
    			print(response,true,"文档接收成功");
    		}else if("2".equals(result)){
    			print(response,false,"不能连续两次接收同一文档！");
    		}else{
    			print(response,false,"文档接收失败！");
    		}
    	}
		return;
  	}
	/**
    * 下发验证码
    * @throws IOException 
    * @throws Exception
    */
    @RequestMapping(value="sendSms",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void sendSms(HttpServletRequest request, HttpServletResponse response){
    	String mobile = request.getParameter("mobile");
    	if(!StringUtils.isNotEmpty(mobile)){
    		print(response,false,"手机号码不能为空!");
    		return;
    	}
	   	try{
	   		if(smsService.sendSms(mobile)){
	   			print(response,true,"验证码下发成功!");
	   		}else{
	   			print(response,false,"获取验证码失败");
	   		}
	   	}catch(Exception e){
	   		e.printStackTrace();
	   		print(response,false,"获取验证码失败");
	   		return;
	   	}
	}
    
    /**
     * 校验验证码
     * @throws IOException 
     * @throws Exception
     */
     @RequestMapping(value="checkSms",method={RequestMethod.POST,RequestMethod.GET})
 	@ResponseBody
 	public void checkSms(HttpServletRequest request, HttpServletResponse response){
    	 String mobile = request.getParameter("mobile");
    	 if(StringUtils.isEmpty(mobile)){
    		 print(response,false,"手机号码不能为空!");
    		 return;
    	 }
    	 String smsCode = request.getParameter("smsCode");
    	 if(StringUtils.isEmpty(smsCode)){
    		 print(response,false,"验证码不能为空!");
    		 return;
    	 }
    	 if(smsService.checkSms(mobile,smsCode)){
    		 request.getSession().setAttribute(CommandCommon.MOBILESESSION, mobile);
   			print(response,true,"校验通过！");
    	 }else{
    		print(response,false,"验证失败！");
    	 }
     }
     /**
      * 账号密码校验
      * @throws IOException 
      * @throws Exception
      */
      @RequestMapping(value="afterLogin",method={RequestMethod.POST,RequestMethod.GET})
  	@ResponseBody
  	public void afterLogin(HttpServletRequest request, HttpServletResponse response){
    	  //手机号码
    	  String mobile = (String) request.getSession().getAttribute(CommandCommon.MOBILESESSION);
    	  //登录用户
    	  SysUser sysUser = getVisitor(request);
    	  //校验该用户角色
    	  //业主、专家顾问、施工单位、监理单位、造价单位
    	  List<SysRole> srList = commandService.getCommandRoles(sysUser);
    	  if(srList == null || srList.size() == 0){
    		  print(response,false,"抱歉，您无工程指令相关权限！");
    		  request.getSession().removeAttribute(SystemConstant.SESSION_VISITOR);
    		  return;
    	  }
    	  boolean bindFlag = false;
	  	  //施工单位、监理单位、造价单位
	  	  String role4 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_CONSTRUCTION).getParameterValue();
	  	  String role5 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_SUPERVISION).getParameterValue();
	  	  String role6 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_COST).getParameterValue();
	  	  if(srList != null && srList.size() > 0 ){
		  	for (SysRole sysRole : srList) {
		  			//施工单位、监理单位、造价单位
				if(role4.equals(sysRole.getRoleCode()) || role5.equals(sysRole.getRoleCode()) || role6.equals(sysRole.getRoleCode())){
					bindFlag = true;
				}
			}
		  }
	  	  if(bindFlag){
	  		  //号码和组织绑定关系
	  		  CommandMoborg cmo = commandService.queryCommandMoborgByMobile(mobile,sysUser.getOrganizationId());
	  		  if(cmo == null){
	  			  cmo = new CommandMoborg();
	  			  cmo.setMobile(mobile);
	  			  cmo.setOrgId(sysUser.getOrganizationId());//组织id
	  			  cmo.setCreateUserid(sysUser.getUserId());//登录用户,绑定用户
	  			  cmo.setCreateUsername(sysUser.getUserName());
	  			  cmo.setCreateTime(new Date());
	  			  cmo.setStatus(CommandMoborg.Status_S);//保存
	  			  commandService.saveEntity(cmo);
	  		  }else{
	  			  cmo.setCreateUserid(sysUser.getUserId());//登录用户,绑定用户
	  			  cmo.setCreateUsername(sysUser.getUserName());
	  			  cmo.setCreateTime(new Date());
	  			  cmo.setStatus(CommandMoborg.Status_S);//保存
	  			  commandService.updateEntity(cmo); //更新
	  		  }
	  		  
	  		  print(response,true,"校验通过！");
	  		  return;
	  	  }
	  	  
	  	  print(response,false,"抱歉，您无工程指令相关权限！");
		  request.getSession().removeAttribute(SystemConstant.SESSION_VISITOR);
		  return;
     }
      
      /**
       * 前端退出登录
       */
      @SuppressWarnings("unchecked")
  	@RequestMapping(value = "logout",method = {RequestMethod.GET,RequestMethod.POST})
      public String logout(HttpServletRequest request,HttpServletResponse response){
      	String id = request.getParameter("id");
      	request.getSession().invalidate();//退出登录
  		return "redirect:/command/h5/doMobileLogin?id="+id;
      }
      
      /**
       * 前端提交文档
       */
      @SuppressWarnings("unchecked")
      @RequestMapping(value = "folder",method = {RequestMethod.GET,RequestMethod.POST})
      public String folder(HttpServletRequest request,HttpServletResponse response){
      	String mobile = (String) request.getSession().getAttribute(CommandCommon.MOBILESESSION);
    	String id = request.getParameter("id");
    	request.setAttribute("id", id);
    	if(StringUtils.isNotEmpty(id)){
    		CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
    		//未流转
    		if(cdinfo != null && CommandCommon.commandStatus_WLZ.equals(cdinfo.getCommandStatus())){
    			return "/dc/command/h5/notroam";
    		}
    	}
    	//未通过短信登录
    	if(StringUtils.isEmpty(mobile)){
    		return "redirect:/command/h5/doMobileLogin?id="+id;
    	}
    	SysUser sysUser = getVisitor(request);
    	//用户登录界面
    	if(sysUser == null){
    		return "redirect:/command/h5/doAccountLogin?id="+id;
    	}
    	CommandFolder cdfolder = new CommandFolder();
    	if(StringUtils.isNotEmpty(id)){
    		CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
    		if(cdinfo!=null){
    			cdfolder.setCommandInfoid(cdinfo.getCommandId());
    			request.setAttribute("cdinfo", cdinfo);
    		}
    		
    	}
    	cdfolder.setFolderId(UUID.randomUUID().toString());
    	request.setAttribute("vo", cdfolder);
    	request.setAttribute("rulesFileUpload", null);
      	return "/dc/command/h5/folder";
      }
      
      
      /**
       * 接收新文档
       */
      @SuppressWarnings("unchecked")
      @RequestMapping(value = "receiveNewDoc",method = {RequestMethod.GET,RequestMethod.POST})
      public String receiveNewDoc(HttpServletRequest request,HttpServletResponse response,CommandForm command){
      	String mobile = (String) request.getSession().getAttribute(CommandCommon.MOBILESESSION);
    	String id = request.getParameter("id");
    	request.setAttribute("id", id);
    	if(StringUtils.isNotEmpty(id)){
    		CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
    		//未流转
    		if(cdinfo != null && CommandCommon.commandStatus_WLZ.equals(cdinfo.getCommandStatus())){
    			return "/dc/command/h5/notroam";
    		}
    	}
    	//未通过短信登录
    	if(StringUtils.isEmpty(mobile)){
    		return "redirect:/command/h5/doMobileLogin?id="+id;
    	}
    	SysUser sysUser = getVisitor(request);
    	//用户登录界面
    	if(sysUser == null){
    		return "redirect:/command/h5/doAccountLogin?id="+id;
    	}
    	//默认查询
    	if(StringUtils.isEmpty(command.getCommandNum())){
    		command.setCommandNum("CMGD-");
    	}
    	ItemPage itemPage = commandService.receiveNewDoc(command,mobile,sysUser.getUserId());
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("command", command);
      	return "/dc/command/h5/newdoc";
      }
      
      /**
       * 根据指令编码查找指令
       * @throws IOException 
       * @throws Exception
       */
      @RequestMapping(value="searchDoc",method={RequestMethod.POST,RequestMethod.GET})
	  @ResponseBody
	  public void searchDoc(HttpServletRequest request, HttpServletResponse response){
    	  String commandNum = request.getParameter("commandNum");
    	  if(StringUtils.isEmpty(commandNum)){
     		 print(response,false,"指令编号不能为空");
     		 return;
     	 }
    	  CommandInfo cdinfo = commandService.queryCommandInfoByNum(commandNum);
    	  if(cdinfo  == null){
      		 print(response,false,"指令信息不存在");
      		 return;
      	 }
    	  print(response,true,cdinfo.getCommandId());
      }
	/**
	 * @author ZENGKAI
	 * 返回信息
	 * @param response
	 * @param result
	 * @param message
	 */
	private void print(HttpServletResponse response,boolean result,String message){
		response.setContentType("text/html; charset=utf-8");
		Map jsonMap = new HashMap();
		//代码
		jsonMap.put("result",result);
		//内容
		jsonMap.put("message",message);
		JSONObject json = JSONObject.fromObject(jsonMap);
        PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(json.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
