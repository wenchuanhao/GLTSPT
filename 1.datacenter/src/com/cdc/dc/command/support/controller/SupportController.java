package com.cdc.dc.command.support.controller;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.command.manage.model.CommandSupportorg;
import com.cdc.dc.command.manage.service.ICommandService;
import com.cdc.dc.command.support.service.ISupportService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;
/**
 * 工程指令管理
 * @author ZengKai
 * @date 2016-8-29 下午16:21:49
 */
@Controller
@RequestMapping(value = "/support/")
public class SupportController extends DefaultController{

	@Autowired
	private ICommandService commandService;
	
	@Autowired
	private ISupportService supportService;
	
	
	/**
     * 工程指令管理列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,CommandSupportorg support){
    	
    	ItemPage itemPage = supportService.findSupport(support);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("support", support);
    	
		return "/dc/command/support/supportlist";
    }
    
    /**
     * 发起指令
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "supportAdd",method = {RequestMethod.GET,RequestMethod.POST})
    public String supportAdd(HttpServletRequest request,HttpServletResponse response){
    	CommandSupportorg supportorg = null;
    	String id = request.getParameter("id");
    	if (id != null && !id.equals("")) {
    		supportorg = (CommandSupportorg) commandService.getEntity(CommandSupportorg.class, id);
		}else {
			supportorg = new CommandSupportorg();
		}
    	request.setAttribute("supportorg", supportorg);
		return "/dc/command/support/supportAdd";
		
    }
    
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "supportSave", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void supportSave(HttpServletRequest request, HttpServletResponse response,CommandSupportorg supportorg){
    	//保存失败
    	String result = "0";
    	try{
    		SysUser sysUser = getVisitor(request);

    		supportorg.setUpdateTime(new Date());
    		supportorg.setUpdateUsername(sysUser.getUserName());
    		supportorg.setUpdateUserid(sysUser.getUserId());
    		supportorg.setStatus(CommandSupportorg.supportorg_Save);
    		String id = supportorg.getSupportorgId();
			if (id == null || "".equals(id.trim())) {
				supportorg.setCreateTime(new Date());
				commandService.saveEntity(supportorg);
			}else{
				CommandSupportorg oldso = (CommandSupportorg) commandService.getEntity(CommandSupportorg.class, id);
				supportorg.setCreateTime(oldso.getCreateTime());
				commandService.updateEntity(supportorg);
			}
	    	result = "1";//保存成功
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 删除配置
     */
    @RequestMapping(value = "delSupport", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void delSupport(HttpServletRequest request, HttpServletResponse response){

		String id = request.getParameter("id");
		String result = "0";
		try{
			if(StringUtils.isNotEmpty(id)){
				String[] ids = id.split(",");
				for (String supportorgId : ids) {
					CommandSupportorg supportorg = (CommandSupportorg) commandService.getEntity(CommandSupportorg.class, supportorgId);
					//只有保存才能删除
					if(supportorg != null &&  CommandSupportorg.supportorg_Save.equals(supportorg.getStatus())){
						supportorg.setStatus(CommandSupportorg.supportorg_del);//删除状态
						commandService.updateEntity(supportorg);//逻辑删除
					}
				}
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch (Exception e) {
			e.printStackTrace();
		} 
	
    }
    
}
