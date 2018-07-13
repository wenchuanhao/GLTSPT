package com.cdc.dc.command.sgmaterial.contorller;

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
import com.cdc.dc.command.manage.model.CommandMaterials;
import com.cdc.dc.command.manage.service.ICommandService;
import com.cdc.dc.command.sgmaterial.service.ISgMaterialService;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.system.core.authentication.controller.CommonController;
/**
 * 施工过程资料
 * @author kuangjiaen
 * @date 2016-08-31 14:43:55
 */
@Controller
@RequestMapping(value = "/command/")
public class SgMaterialController extends CommonController {
	
	@Autowired
	private ICommandService commandService;
	
	@Autowired
	private ISgMaterialService sgMaterialService;
	/**
     * 新增一个表单
     */
	 @RequestMapping(value = "sgmaterialAdd", method = {RequestMethod.GET,RequestMethod.POST})
	    public String sgmaterialAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 String id = request.getParameter("id");
		 CommandMaterials cm = null;
		 if (id != null && !id.equals("")) {
			 cm = (CommandMaterials) commandService.getEntity(CommandMaterials.class, id);
		 }else {
			 cm = new CommandMaterials();
		 }
		 
		 request.setAttribute("vo", cm);
		 
		 return "/dc/command/sgmaterial/sgmaterialAdd";
	 }
	 
	 	/**
	     * 保存或者修改
	     */
	    @RequestMapping(value = "sgmaterialSave", method = {RequestMethod.GET,RequestMethod.POST})
	    @ResponseBody
	    public void sgmaterialSave(HttpServletRequest request, HttpServletResponse response,CommandMaterials cm){
	    	//保存失败
	    	String result = "0";
	    	try{
	    		SysUser sysUser = getVisitor(request);
	    		//保存指令信息及指令详情
	    		commandService.saveOrUpdateCommandMaterials(cm, sysUser);
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
	    @RequestMapping(value = "sgmaterialAddQuery", method = {RequestMethod.GET,RequestMethod.POST})
	    public String sgmaterialAddQuery(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    	String id = request.getParameter("id");
	    	CommandMaterials cm = null;
	    	if (id != null && !id.equals("")) {
	    		cm = (CommandMaterials) commandService.getEntity(CommandMaterials.class, id);
	    	}else{
	    		cm = new CommandMaterials();
	    	}
	    	CommandInfo cdinfo = commandService.queryCommandInfoByForid(cm.getWorksId());
	    	//指令信息
	    	request.setAttribute("cdinfo", cdinfo);
	    	request.setAttribute("vo", cm);//指令详情
	    	
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
			
	    	return "/dc/command/sgmaterial/sgmaterialQuery";
	    }
	    /**
	     * 打印
	     */
	    @RequestMapping(value = "sgmaterialAddView", method = {RequestMethod.GET,RequestMethod.POST})
	    public String sgmaterialAddView(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    	String id = request.getParameter("id");
	    	CommandMaterials cm = null;
	    	if (id != null && !id.equals("")) {
	    		cm = (CommandMaterials) commandService.getEntity(CommandMaterials.class, id);
	    	}else {
	    		cm = new CommandMaterials();
	    	}
	    	CommandInfo cdinfo = commandService.queryCommandInfoByForid(cm.getWorksId());
	    	//点击“打印”操作后触发指令流转，状态由“未流转”变为“流转中”
	    	commandService.printCommandInfo(cdinfo);
	    	
	    	request.setAttribute("cdinfo", cdinfo);
	    	request.setAttribute("vo", cm);
	    	
	    	return "/dc/command/sgmaterial/sgmaterialView";
	    }
}
