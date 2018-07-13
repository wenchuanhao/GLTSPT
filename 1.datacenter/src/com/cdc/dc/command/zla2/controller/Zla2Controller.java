package com.cdc.dc.command.zla2.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
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
import com.cdc.dc.command.zla2.service.IZla2Service;
import com.cdc.dc.rules.model.RulesFileUpload;

import com.cdc.system.core.authentication.controller.CommonController;
/**
 * 工程指令，A2类型指令
 * @author zengkai
 * @date 2016-08-30 09:53:00
 */
@Controller
@RequestMapping(value = "/command/")
public class Zla2Controller extends CommonController{

	@Autowired
	private ICommandService commandService;
	@Autowired
	private IZla2Service zla2Service;
	/**
     * 新增一个表单
     */
    @RequestMapping(value = "zla2Add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
//    	String id = request.getParameter("id");
//    	CommandWorks cw = null;
//    	if (id != null && !id.equals("")) {
//    		cw = (CommandWorks) commandService.getEntity(CommandWorks.class, id);
//		}else {
//			cw = new CommandWorks();
//		}
//    	request.setAttribute("vo", cw);
//    	request.setAttribute("fileTempId", cw.getWorksId());
    	String id = request.getParameter("id");
		
    	CommandWorks cw = null;
    	if (id != null && !id.equals("")) {
    		cw = (CommandWorks) commandService.getEntity(CommandWorks.class, id);
		}

    	if(cw == null){
			cw = new CommandWorks();
			cw.setWorksId(UUID.randomUUID().toString());
		}
    	request.setAttribute("vo", cw);
    	request.setAttribute("fileTempId", cw.getWorksId());
    	
    	return "/dc/command/zla2/zla2Add";
    }
    @RequestMapping(value = "zla2Print", method = {RequestMethod.GET,RequestMethod.POST})
    public String Printtest(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
    	return "/dc/command/zla2/zla2Print";
    	}
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "zla2Save", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void zla2Save(HttpServletRequest request, HttpServletResponse response,CommandWorks cw){
    	//保存失败
    	String result = "0";
        	try{
        		SysUser sysUser = getVisitor(request);
        		
    			commandService.saveOrUpdateCommandWorks(cw,sysUser);
    	    	result = "1";//保存成功
    			PrintWriter out = response.getWriter();
    			out.write(result);
    		} 
        	catch (Exception e) {
    			e.printStackTrace();
		}
    
    }

    /**
     * 详情
     */
    @RequestMapping(value = "zla2AddQuery", method = {RequestMethod.GET,RequestMethod.POST})
    public String zla2AddQuery(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
		
		List<RulesFileUpload> wordList =  commandService.queryRulesFileUploadById(id);
		request.setAttribute("wordList", wordList);
		
    	return "/dc/command/zla2/zla2Query";
    }
/**
 * 打印
 */
@RequestMapping(value = "zla2AddView", method = {RequestMethod.GET,RequestMethod.POST})
public String zla2AddView(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
	
	List<RulesFileUpload> wordList =  commandService.queryRulesFileUploadById(id);
	request.setAttribute("wordList", wordList);
	
	return "/dc/command/zla2/zla2View";
}

}