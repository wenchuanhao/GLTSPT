package com.cdc.dc.command.clqk.controller;

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

import com.cdc.dc.command.clqk.service.IClqkService;
import com.cdc.dc.command.manage.common.CommandCommon;
import com.cdc.dc.command.manage.model.CommandFlows;
import com.cdc.dc.command.manage.model.CommandFolder;
import com.cdc.dc.command.manage.model.CommandInfo;
import com.cdc.dc.command.manage.model.CommandQks;
import com.cdc.dc.command.manage.service.ICommandService;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.system.core.authentication.controller.CommonController;

/**
 * 材料设备类请款
 * @author kuangjiaen
 * @date 2016-09-01 09:56:10
 */

@Controller
@RequestMapping(value = "/command/")
public class ClqkController extends CommonController {

	@Autowired
	private ICommandService commandService;
	
	@Autowired
	private IClqkService clqkService;
	
	/**
     * 新增一个表单
     */
	 @RequestMapping(value = "clqkAdd", method = {RequestMethod.GET,RequestMethod.POST})
	    public String clqkAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 String id = request.getParameter("id");
		 CommandQks cq = null;
		 if (id != null && !id.equals("")) {
			 cq = (CommandQks) commandService.getEntity(CommandQks.class, id);
		 }else {
			 cq = new CommandQks();
		 }
		 List<Object[]> list = commandService.queryCommandInfoAll();
    	 request.setAttribute("list", list);
		 request.setAttribute("vo", cq);
		 return "/dc/command/clqk/clqkAdd";
	 }
	 	/**
	     * 根据合同名称查询项目名称
	     */
	    @RequestMapping(value = "queryByOrgNameE", method = {RequestMethod.GET,RequestMethod.POST})
	    public void queryByOrgNameE(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    	String orgName = request.getParameter("orgName");
	    	List<Object[]> list=commandService.queryorgName(orgName);
	    	 PrintWriter out = null;  
	         out = response.getWriter();  
	         String str = String.valueOf(list.get(0));
	         out.write(str);
	    }
	 
		/**
	     * 保存或者修改
	     */
	 	@RequestMapping(value = "clqkSave", method = {RequestMethod.GET,RequestMethod.POST})
	    @ResponseBody
	    public void clqkSave(HttpServletRequest request, HttpServletResponse response, CommandQks cq){
		 	//保存失败
	    	String result = "0";
	    	try{
	    		SysUser sysUser = getVisitor(request);
	    		//保存指令信息及指令详情
	    		commandService.saveOrUpdateCommandQks(cq, sysUser);
	    		result = "1";//保存成功
				PrintWriter out = response.getWriter();
				out.write(result);
	    	}catch (Exception e) {
				e.printStackTrace();
			}
	 }
	 	
	 	/**
	     * 详情
	     */
	    @RequestMapping(value = "clqkAddQuery", method = {RequestMethod.GET,RequestMethod.POST})
	    public String clqkAddQuery(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    	String id = request.getParameter("id");
	    	CommandQks cq = null;
	    	if (id != null && !id.equals("")) {
	    		cq = (CommandQks) commandService.getEntity(CommandQks.class, id);
	    	}else {
	    		cq = new CommandQks();
	    	}
	    	CommandInfo cdinfo = commandService.queryCommandInfoByForid(cq.getWorksId());
	    	//指令信息
	    	request.setAttribute("cdinfo", cdinfo);
	    	request.setAttribute("vo", cq);//指令详情
	    	
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
	    	
	    	return "/dc/command/clqk/clqkQuery";
	    }
	 
	 /**
	     * 打印
	     */
	    @RequestMapping(value = "clqkAddView", method = {RequestMethod.GET,RequestMethod.POST})
	    public String clqkAddView(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    	String id = request.getParameter("id");
	    	CommandQks cq = null;
	    	if (id != null && !id.equals("")) {
	    		cq = (CommandQks) commandService.getEntity(CommandQks.class, id);
	    	}else {
	    		cq = new CommandQks();
	    	}
	    	CommandInfo cdinfo = commandService.queryCommandInfoByForid(cq.getWorksId());
	    	//点击“打印”操作后触发指令流转，状态由“未流转”变为“流转中”
	    	commandService.printCommandInfo(cdinfo);
	    	
	    	request.setAttribute("cdinfo", cdinfo);
	    	request.setAttribute("vo", cq);
	    	
	    	return "/dc/command/clqk/clqkView";
	    }
}
