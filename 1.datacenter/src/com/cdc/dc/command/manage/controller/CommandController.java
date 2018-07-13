package com.cdc.dc.command.manage.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.command.manage.common.CommandCommon;
import com.cdc.dc.command.manage.form.CommandForm;
import com.cdc.dc.command.manage.model.CommandFlows;
import com.cdc.dc.command.manage.model.CommandInfo;
import com.cdc.dc.command.manage.model.CommandMaterials;
import com.cdc.dc.command.manage.model.CommandWorks;
import com.cdc.dc.command.manage.service.ICommandService;
import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.project.fxk.model.GcFxkStage;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;
/**
 * 工程指令管理
 * @author ZengKai
 * @date 2016-8-29 下午16:21:49
 */
@Controller
@RequestMapping(value = "/command/")
public class CommandController extends DefaultController{
	
	@Autowired
	private ICommandService commandService;
	private Log logger = LogFactory.getLog(getClass());
	
	public static final Map<String, String> map1 = new HashMap<String, String>();
    
	public static final Map<String, String> map2 = new HashMap<String, String>();
	
	static{
		map1.put("1", "A1指令");
		map1.put("2", "A2指令");
		map1.put("3", "B指令");
		map1.put("4", "C指令");
		map1.put("5", "施工类请款");
		map1.put("6", "检测勘探类请款");
		map1.put("7", "监理造价类请款");
		map1.put("8", "设计专家类请款");
		map1.put("9", "材料设备请款");
		map1.put("10", "施工过程资料");

		
		map2.put("1", "未流转");
		map2.put("2", "流转中");
		map2.put("3", "已归档");
		map2.put("4", "已作废");
		map2.put("5", "已撤销归档");
	}
	
	/**
     * 工程指令管理列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,CommandForm command){
    	
    	SysUser sysUser = getVisitor(request);
//    	command.setLaunchUserid(sysUser.getUserId());
    	ItemPage itemPage = commandService.findCommand(command);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("command", command);
    	//资料管理员权限判断
    	IRulesService rulesService = (IRulesService) SpringHelper.getBean("rulesService");
    	SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_ADMIN).getParameterValue());
    	SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
    	if(rolesUser != null){
    		request.setAttribute("COMMAND_ADMIN", "1");
    	}
		return "/dc/command/manage/commandlist";
    }
    
    /**
     * 发起指令
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "launch",method = {RequestMethod.GET,RequestMethod.POST})
    public String launch(HttpServletRequest request,HttpServletResponse response){
    	//获取该用户与指令有个的角色列表
    	SysUser sysUser = getVisitor(request);
    	List<SysRole> srList = commandService.getCommandRoles(sysUser);
    	//业主、专家顾问、资料管理员
	  	String role1 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_OWNER).getParameterValue();
	  	String role2 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_ADVISER).getParameterValue();
	  	String role3 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_ADMIN).getParameterValue();
	  	//施工单位、监理单位、造价单位
	  	String role4 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_CONSTRUCTION).getParameterValue();
	  	String role5 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_SUPERVISION).getParameterValue();
	  	String role6 = SysParamHelper.getSysParamByCode(CommandCommon.COMMAND_DICT_TYPE, CommandCommon.COMMAND_COST).getParameterValue();
    	if(srList != null && srList.size() > 0){
    		request.setAttribute("srList", srList);
    		setRoleIdSession(request,srList.get(0).getRoleId(),srList.get(0).getRoleName());
    		String commandRole = "";
    		for (SysRole sysRole : srList) {
	  			//业主、专家顾问、资料管理员
				if(role1.equals(sysRole.getRoleCode()) || role2.equals(sysRole.getRoleCode()) || role3.equals(sysRole.getRoleCode())){
					commandRole = "1";
					break;
				}
			}
    		if(StringUtils.isEmpty(commandRole)){
    			for (SysRole sysRole : srList) {
    	  			//监理单位
    				if(role5.equals(sysRole.getRoleCode())){
    					commandRole = "2";
    					break;
    				}
    			}
    		}
    		if(StringUtils.isEmpty(commandRole)){
    			for (SysRole sysRole : srList) {
    	  			//施工单位、造价单位
    				if(role4.equals(sysRole.getRoleCode()) || role6.equals(sysRole.getRoleCode())){
    					commandRole = "3";
    					break;
    				}
    			}
    		}
    		request.setAttribute("COMMAND_ROLE", commandRole);
    	}
    	List<SysParameter> types = SysParamHelper.getSysParamListByParamTypeCode(CommandCommon.COMMAND_TYPE);
    	request.setAttribute("types", types);
		return "/dc/command/manage/launch";
    }
    
   
    
    
    
    
    
    
    
	/**
	 * 设置角色id
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "setRoleId", method = {RequestMethod.GET, RequestMethod.POST})
	public void setRoleId(HttpServletRequest request,HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		String roleName = request.getParameter("roleName");
		setRoleIdSession(request,roleId,roleName);
	}
	
    /**
     * 作废
     */
    @RequestMapping(value="del",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String result = "0";
		try{
			if(StringUtils.isNotEmpty(id)){
				SysUser sysUser = getVisitor(request);
				CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
				if(cdinfo != null){
					//保存指令信息及指令详情
					commandService.delCommandInfo(cdinfo,sysUser);
					result = "1";
				}
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
    
    public void setRoleIdSession(HttpServletRequest request,String roleId,String roleName){
    	SysUser sysUser = getVisitor(request);
		sysUser.setCommandRoleId(roleId);
		sysUser.setCommandRoleName(roleName);
		//设置角色session
		request.getSession().setAttribute(SystemConstant.SESSION_VISITOR, sysUser);
    }
    /**
     * 查看流转信息
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "viewFlows",method = {RequestMethod.GET,RequestMethod.POST})
    public String viewFlows(HttpServletRequest request,HttpServletResponse response){
    	String id = request.getParameter("id");
    	
    	if(StringUtils.isNotEmpty(id)){
    		CommandInfo cdinfo = (CommandInfo) commandService.getEntity(CommandInfo.class, id);
			if(cdinfo != null){
				List<CommandFlows> list  = commandService.queryCommandFlowsById(id);
				request.setAttribute("list", list);
			}
    	}
    	request.setAttribute("showback", "1");
    	return "/dc/command/manage/viewFlows";
    }
    
    /**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response, CommandForm command) throws Exception{
		
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");
		HSSFWorkbook book = new HSSFWorkbook();
		//样式设置
		HSSFCellStyle style = book.createCellStyle();
		HSSFFont font = book.createFont();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中对齐
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setWrapText(true); // 设置单元格内容是否自动换行
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);

		String zbIds = request.getParameter("zbIds");
		command.setCommandId(zbIds);
		// 表头状态
		String[] header = {"文件类型","是否已被关联","文件编号","单位（施工/乙方/来文）名称","合同名称","摘要","归档位置","发起人","发起时间","状态"};
		
		command.setPageSize(Integer.MAX_VALUE - 1);
		ItemPage itemPage = commandService.findCommand(command);
		
		List<CommandInfo> list = (List<CommandInfo>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		for (CommandInfo vo : list) {
			Object [] obj =  new Object[10];
			obj[0] = map1.get(vo.getCommandType());//文件类型
			String isRelation = "未关联";
			List<CommandMaterials> listcm = vo.getCommandMaterials();
			if(listcm != null && listcm.size() > 0){
				isRelation = "";
				for (CommandMaterials commandMaterials : listcm) {
					isRelation += commandMaterials.getCommandInfo().getCommandNum() + "\r\n";
				}
			}
			obj[1] = isRelation;
			obj[2] = vo.getCommandNum();//文件编号
			obj[3] = vo.getOrgName();//单位（施工/乙方/来文）名称
			obj[4] = vo.getContractName();//合同名称
			obj[5] = vo.getCommandFolder().getFolderPosition();//归档位置
			obj[6] = vo.getCommandFolder().getDigEst();//摘要
			obj[7] = vo.getLaunchUsername();//发起人
			obj[8] = vo.getLaunchTime();//发起时间
			obj[9] = map2.get(vo.getCommandStatus());//状态
			listO.add(obj);
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			//文件名
			String fileName = "工程指令列表" + sdf.format(new Date());
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
