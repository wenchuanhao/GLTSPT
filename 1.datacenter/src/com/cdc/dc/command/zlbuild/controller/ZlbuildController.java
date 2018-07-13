package com.cdc.dc.command.zlbuild.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.dc.command.manage.common.CommandCommon;
import com.cdc.dc.command.manage.model.CommandFlows;
import com.cdc.dc.command.manage.model.CommandFolder;
import com.cdc.dc.command.manage.model.CommandInfo;
import com.cdc.dc.command.manage.model.CommandQks;
import com.cdc.dc.command.manage.model.CommandWorks;
import com.cdc.dc.command.manage.service.ICommandService;
import com.cdc.dc.command.zlbuild.service.IZlbuildService;
import com.cdc.dc.rules.model.RulesFileUpload;


import com.cdc.system.core.authentication.controller.CommonController;
/**
 * 工程指令，施工类型指令
 * @author zengkai
 * @date 2016-08-30 09:53:00
 */
@Controller
@RequestMapping(value = "/command/")
public class ZlbuildController extends CommonController{

	@Autowired
	private ICommandService commandService;
	@Autowired
	private IZlbuildService zlbuildService;
	/**
     * 新增一个表单
     */
    @RequestMapping(value = "zlbuildAdd", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	/*String id = request.getParameter("id");
    	CommandQks cw = null;
    	if (id != null && !id.equals("")) {
    		cw = (CommandQks) commandService.getEntity(CommandQks.class, id);
		}else {
			cw = new CommandQks();
		}
    	request.setAttribute("vo", cw);
    	request.setAttribute("fileTempId", cw.getWorksId());*/
    
    	String id = request.getParameter("id");
    	CommandQks cw = null;
    	if (id != null && !id.equals("")) {
    		cw = (CommandQks) commandService.getEntity(CommandQks.class, id);
		}
    	
    	if(cw == null){
			cw = new CommandQks();
			cw.setWorksId(UUID.randomUUID().toString());
		}

    	List<Object[]> list = commandService.queryCommandInfoAll();
    	request.setAttribute("list", list);
    	request.setAttribute("vo", cw);
    	request.setAttribute("fileTempId", cw.getWorksId());
    	return "/dc/command/zlbuild/zlbuildAdd";
    }
    /**
     * 根据合同名称查询项目名称
     */
    @RequestMapping(value = "queryByOrgNameA", method = {RequestMethod.GET,RequestMethod.POST})
    public void queryByOrgNameA(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String orgName = request.getParameter("orgName");
    	List<Object[]> list=commandService.queryorgName(orgName);
    	 PrintWriter out = null;  
         out = response.getWriter();  
         String str = String.valueOf(list.get(0));
         out.write(str);
    }
    
    
    /**
     * 详情
     */
    @RequestMapping(value = "zlbuildAddQuery", method = {RequestMethod.GET,RequestMethod.POST})
    public String zlbuildAddQuery(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");
    	CommandQks cw = null;
    	if (id != null && !id.equals("")) {
    		cw = (CommandQks) commandService.getEntity(CommandQks.class, id);
    	}else {
    		cw = new CommandQks();
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
		
    	return "/dc/command/zlbuild/zlbuildQuery";
    }
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "zlbuildSave", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void zlbuildSave(HttpServletRequest request, HttpServletResponse response,CommandQks cw){
    	//保存失败
    	String result = "0";
        	try{
        		SysUser sysUser = getVisitor(request);
        		
    			commandService.saveOrUpdateCommandQks(cw,sysUser);
    	    	result = "1";//保存成功
    			PrintWriter out = response.getWriter();
    			out.write(result);
    		} 
        	catch (Exception e) {
    			e.printStackTrace();
		}
    
    }
    /**
     * 打印
     */
    @RequestMapping(value = "zlbuildAddView", method = {RequestMethod.GET,RequestMethod.POST})
    public String zlbuildAddView(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");
    	CommandQks cw = null;
    	if (id != null && !id.equals("")) {
    		cw = (CommandQks) commandService.getEntity(CommandQks.class, id);
		}else {
			cw = new CommandQks();
		}
    	CommandInfo cdinfo = commandService.queryCommandInfoByForid(cw.getWorksId());
    	//点击“打印”操作后触发指令流转，状态由“未流转”变为“流转中”
    	commandService.printCommandInfo(cdinfo);
    	
    	request.setAttribute("cdinfo", cdinfo);
    	request.setAttribute("vo", cw);
    	
    	return "/dc/command/zlbuild/zlbuildView";
    }
}

/**
 * 根据名称查询所属科室
 * @param request
 * @param response
 */

