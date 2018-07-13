package com.cdc.dc.command.zlc.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.dc.command.manage.common.CommandCommon;
import com.cdc.dc.command.manage.model.CommandFlows;
import com.cdc.dc.command.manage.model.CommandFolder;
import com.cdc.dc.command.manage.model.CommandInfo;
import com.cdc.dc.command.manage.model.CommandWorks;
import com.cdc.dc.command.manage.service.ICommandService;
import com.cdc.dc.command.zlc.service.IZlcService;
import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.system.core.authentication.controller.CommonController;
/**
 * 工程指令，C类型指令
 * @author zengkai
 * @date 2016-08-30 09:53:00
 */
@Controller
@RequestMapping(value = "/command/")
public class ZlcController extends CommonController{

	@Autowired
	private ICommandService commandService;
	@Autowired
	private IZlcService zlcService;
	/**
     * 新增一个表单
     */
    @RequestMapping(value = "zlcAdd", method = {RequestMethod.GET,RequestMethod.POST})
    public String zlcAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");
    	CommandWorks cw = null;
    	if (id != null && !id.equals("")) {
    		cw = (CommandWorks) commandService.getEntity(CommandWorks.class, id);
		}else {
			cw = new CommandWorks();
		}
    	request.setAttribute("vo", cw);
    	
    	return "/dc/command/zlc/zlcAdd";
    }
    
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "zlcSave", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void zlcSave(HttpServletRequest request, HttpServletResponse response,CommandWorks cw){
    	//保存失败
    	String result = "0";
    	try{
    		SysUser sysUser = getVisitor(request);
    		//保存指令信息及指令详情
			commandService.saveOrUpdateCommandWorks(cw,sysUser);
	    	result = "1";//保存成功
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 详情
     */
    @RequestMapping(value = "zlcAddQuery", method = {RequestMethod.GET,RequestMethod.POST})
    public String zlcAddQuery(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");
    	CommandWorks cw = null;
    	if (id != null && !id.equals("")) {
    		cw = (CommandWorks) commandService.getEntity(CommandWorks.class, id);
    	}else {
    		cw = new CommandWorks();
    	}
    	CommandInfo cdinfo = commandService.queryCommandInfoByForid(cw.getWorksId());
    	//指令信息
    	request.setAttribute("cdinfo", cdinfo);
    	request.setAttribute("vo", cw);//指令详情
    	
    	CommandFolder cdfolder = null;
		if(cdinfo != null){
			//归档信息
			cdfolder = commandService.queryLeastCommandFolderById(cdinfo.getCommandId(),CommandCommon.folderStatus_S);
			
			List<CommandFlows> list  = commandService.queryCommandFlowsById(cdinfo.getCommandId());
			request.setAttribute("list", list);//流转信息
		}
		if(cdfolder != null){
			//归档附件
			List<RulesFileUpload> folders =  commandService.queryRulesFileUploadById(cdfolder.getFolderId());
			request.setAttribute("folders", folders);
		}
		request.setAttribute("cdfolder", cdfolder);
		
    	return "/dc/command/zlc/zlcQuery";
    }
    /**
     * 打印
     */
    @RequestMapping(value = "zlcAddView", method = {RequestMethod.GET,RequestMethod.POST})
    public String zlcAddView(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");
    	CommandWorks cw = null;
    	if (id != null && !id.equals("")) {
    		cw = (CommandWorks) commandService.getEntity(CommandWorks.class, id);
		}else {
			cw = new CommandWorks();
		}
    	CommandInfo cdinfo = commandService.queryCommandInfoByForid(cw.getWorksId());
    	//点击“打印”操作后触发指令流转，状态由“未流转”变为“流转中”
    	commandService.printCommandInfo(cdinfo);
    	
    	request.setAttribute("cdinfo", cdinfo);
    	request.setAttribute("vo", cw);
    	
    	return "/dc/command/zlc/zlcView";
    }
}
