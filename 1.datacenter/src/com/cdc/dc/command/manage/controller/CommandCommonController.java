package com.cdc.dc.command.manage.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.common.properties.DCConfig;
import com.cdc.dc.command.manage.common.CommandCommon;
import com.cdc.dc.command.manage.model.CommandFolder;
import com.cdc.dc.command.manage.model.CommandInfo;
import com.cdc.dc.command.manage.service.ICommandService;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.util.QrcodeUtil;
/**
 * 工程指令管理
 * @author ZengKai
 * @date 2016-09-01 下午16:21:49
 */
@Controller
@RequestMapping(value = "/command/")
public class CommandCommonController extends CommonController{
	
	@Autowired
	private ICommandService commandService;
    /**
	 * 发起工程指令 >> 支撑单位联系查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchSupportorg", method = {RequestMethod.POST})
	public @ResponseBody void searchSupportorg(HttpServletRequest request,HttpServletResponse response) {
    	try {
    	    response.setContentType("text/html; charset=utf-8");
    	    String supportorgName = request.getParameter("supportorgName");
    		String result = "";
  	        if(supportorgName != null && !"".equals(supportorgName)){
  	        	List<Object[]> supportorgList = commandService.searchSupportorg(supportorgName);
                JSONArray userListJSONArray = new JSONArray();
                if (supportorgList != null && !supportorgList.isEmpty()) {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    Object[] obj;
                    for (int i=0; i < supportorgList.size(); i++) {
                        obj = supportorgList.get(i);
                        jsonObject = new JSONObject();
                        jsonArray = new JSONArray();
                        jsonObject.put("supportorgId",String.valueOf(obj[0]));
                        jsonObject.put("supportorgName",String.valueOf(obj[1]));
                        jsonArray.add(jsonObject);

                        userListJSONArray.add(jsonArray);
                    }
                }
  	        	result = userListJSONArray.toString();
  	        }
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}

    /**
     * 保存归档信息
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "saveFolder",method = {RequestMethod.GET,RequestMethod.POST})
    public String saveFolder(HttpServletRequest request,HttpServletResponse response){
    	String id = request.getParameter("id");
    	CommandFolder cdfolder = null;
    	if(StringUtils.isNotEmpty(id)){
    		CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
    	
			if(cdinfo != null){
				//归档信息
				cdfolder = commandService.queryLeastCommandFolderById(cdinfo.getCommandId(),CommandCommon.folderStatus_S);
				if(cdfolder == null){
					cdfolder = new CommandFolder();
					cdfolder.setCommandInfoid(cdinfo.getCommandId());
					cdfolder.setFolderId(UUID.randomUUID().toString());
				}else{
					//归档附件
					List<RulesFileUpload> folders =  commandService.queryRulesFileUploadById(cdfolder.getFolderId());
					request.setAttribute("folders", folders);
				}
				
			}
    	}
    	
    	request.setAttribute("vo", cdfolder);
    	request.setAttribute("rulesFileUpload", null);
    	return "/dc/command/manage/saveFolder";
    }
	/**
	 * 指令归档保存
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "setFolder", method = {RequestMethod.GET, RequestMethod.POST})
	public void setFolder(HttpServletRequest request,HttpServletResponse response,CommandFolder cdfolder){
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = getVisitor(request);
		String result = "0";
		try {
			//判断用户是否是资料管理员
			IRulesService rulesService = (IRulesService) SpringHelper.getBean("rulesService");
	    	SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_ADMIN).getParameterValue());
	    	SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
	    	if(rolesUser != null){
	    		sysUser.setCommandRoleId(sysRole.getRoleId());
	    		sysUser.setCommandRoleName(sysRole.getRoleName());
	    		commandService.setCommandFolder(cdfolder,sysUser);
	    		result = "1";
	    	}
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 撤销归档
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "undoFolder", method = {RequestMethod.GET, RequestMethod.POST})
	public void undoFolder(HttpServletRequest request,HttpServletResponse response){
		SysUser sysUser = getVisitor(request);
		String result = "0";
		String id = request.getParameter("id");
		try {
			CommandInfo cdinfo = null;
			if(StringUtils.isNotEmpty(id)){
				cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
			}
			IRulesService rulesService = (IRulesService) SpringHelper.getBean("rulesService");
	    	SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_ADMIN).getParameterValue());
	    	SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
	    	if(rolesUser != null){
	    		if(cdinfo != null){
	    			sysUser.setCommandRoleId(sysRole.getRoleId());
		    		sysUser.setCommandRoleName(sysRole.getRoleName());
	    			commandService.undoCommandFolder(cdinfo,sysUser);
	    		}
	    		result = "1";
	    	}
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据编码查询A类，B类，C类指令
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "searchCommandNum", method = {RequestMethod.POST})
	public @ResponseBody void searchCommandNum(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.setContentType("text/html; charset=utf-8");
			String code = request.getParameter("code");
			String result = "";
			if(StringUtils.isNotEmpty(code)){
				List<Object[]> projectList = commandService.searchCommandNum(code);
				JSONArray userListJSONArray = new JSONArray();
				if (projectList != null && !projectList.isEmpty()) {
					JSONObject jsonObject;
					JSONArray jsonArray;
					Object[] obj;
					for (int i = 0;i < projectList.size(); i++) {
						obj = projectList.get(i);
						jsonObject = new JSONObject();
						jsonArray = new JSONArray();
						jsonObject.put("commandType",String.valueOf(obj[0]));//指令类型
						jsonObject.put("commandNum",String.valueOf(obj[1]));//指令编号
						jsonArray.add(jsonObject);
						
						userListJSONArray.add(jsonArray);
					}
				}
				result = userListJSONArray.toString();
			}
			PrintWriter writer = response.getWriter();
			writer.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 根据编码查询合同信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "searchProjectByCode", method = {RequestMethod.POST})
	public @ResponseBody void searchProjectByCode(HttpServletRequest request,HttpServletResponse response) {
    	try {
    	    response.setContentType("text/html; charset=utf-8");
    	    String code = request.getParameter("code");
    		String result = "";
  	        if(StringUtils.isNotEmpty(code)){
  	        	List<Object[]> projectList = commandService.searchProjectByCode(code);
                JSONArray userListJSONArray = new JSONArray();
                if (projectList != null && !projectList.isEmpty()) {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    Object[] obj;
                    for (int i = 0;i < projectList.size(); i++) {
                        obj = projectList.get(i);
                        jsonObject = new JSONObject();
                        jsonArray = new JSONArray();
                        jsonObject.put("proId",String.valueOf(obj[0]));
                        jsonObject.put("proType",String.valueOf(obj[1]));
                        jsonObject.put("proCode",String.valueOf(obj[2]));
                        jsonObject.put("proName",String.valueOf(obj[3]));
                        jsonObject.put("proCreateUser",String.valueOf(obj[5]));//创建人
                        jsonObject.put("contractAmount",String.valueOf(obj[6]));//合同金额(含税)
                        jsonObject.put("constructionName",String.valueOf(obj[7]));//合同对方(施工单位,乙方单位)
                        jsonArray.add(jsonObject);

                        userListJSONArray.add(jsonArray);
                    }
                }
  	        	result = userListJSONArray.toString();
  	        }
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
    /**
     * 根据编码查询投资项目
     * @param request
     * @param response
     */
    @RequestMapping(value = "searchProByCode", method = {RequestMethod.POST})
    public @ResponseBody void searchProByCode(HttpServletRequest request,HttpServletResponse response) {
    	try {
    		response.setContentType("text/html; charset=utf-8");
    		String code = request.getParameter("code");
    		String result = "";
    		if(StringUtils.isNotEmpty(code)){
    			List<Object[]> projectList = commandService.searchProByCode(code);
    			JSONArray userListJSONArray = new JSONArray();
    			if (projectList != null && !projectList.isEmpty()) {
    				JSONObject jsonObject;
    				JSONArray jsonArray;
    				Object[] obj;
    				for (int i = 0;i < projectList.size(); i++) {
    					obj = projectList.get(i);
    					jsonObject = new JSONObject();
    					jsonArray = new JSONArray();
						
    					jsonObject.put("projectId",String.valueOf(obj[0]));//投资项目id
    					jsonObject.put("projectCode",String.valueOf(obj[1]));//投资项目编码
    					jsonObject.put("projectName",String.valueOf(obj[2]));//投资项目名称
    					jsonArray.add(jsonObject);
    					
    					userListJSONArray.add(jsonArray);
    				}
    			}
    			result = userListJSONArray.toString();
    		}
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    /**
     * 根据指令信息生成二维码
     * @param request
     * @param response
     * @throws Exception 
     */
    @RequestMapping(value = "getQr", method = {RequestMethod.GET,RequestMethod.POST})
    public void getQr(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");
    	String text = "";
    	if(StringUtils.isNotEmpty(id)){
    		CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
    		if(cdinfo != null){
    			text = cdinfo.getCommandNum();
    		}
    	}
    	//type的值可以是image/jpeg  image/gif   image/png  image/bmp 根据图片的格式来定
    	response.setContentType("image/png;charset=utf-8");
    	//img是要显示的文件名
    	String fileName = new String(( text+ ".png").getBytes("GBK"), "ISO-8859-1");
    	response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
    	response.addHeader("Pragma", "no-cache");
    	response.addHeader("Cache-Control", "no-cache");
//    	String path = request.getContextPath();
//    	String basePath = request.getScheme() + "://"
//    			+ request.getServerName() + ":" + request.getServerPort()
//    			+ path + "/command/h5/doMobileLogin?id="+id;
//    	String basePath = "http://221.176.36.63/command/h5/doMobileLogin?id="+id;
    	String path = DCConfig.getProperty("COMMANDQR_DIR");
    	String basePath = (StringUtils.isEmpty(path) ? "http://221.176.36.63/command/h5/doMobileLogin?id=" : path)  + id;
    	InputStream in = QrcodeUtil.createInputStreamImage(basePath, 392, 392);
    	int num = 0;
    	byte[] buf = new byte[1024];
    	while ((num = in.read(buf)) != -1) {
    		//写入
    		response.getOutputStream().write(buf, 0, num);
    	}
    	//刷新
    	response.getOutputStream().flush();
    	response.getOutputStream().close();
    }
}
