package com.cdc.dc.project.fxk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.project.fxk.model.GcFxkStage;
import com.cdc.dc.project.fxk.service.IFxkService;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 风险管理库
 * @author ZengKai
 * @date 2016-8-18 下午17:50:49
 */
@Controller
@RequestMapping(value = "/fxk/")
public class FxkController extends CommonController {
	
	@Autowired
	private IFxkService fxkService;
	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IRulesService rulesService;
	/**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,FxkForm fxk) throws Exception{
    	//文件临时id,固定值
    	SysParameter sysp = SysParamHelper.getSysParamByCode("FXK_FILEID", "FXK_FILE_TEMPID");
    	String fileTempId = "685d4e3e-0fd6-4f0f-8a45-b7a4d96cf884";
    	if(sysp != null){
    		fileTempId = sysp.getParameterValue();
    	}
    	request.setAttribute("fileTempId", fileTempId);
    	RulesFileUpload fileUpload = cooperationService.queryRulesFileUploadByInfoId(fileTempId);
		if(fileUpload != null){
			request.setAttribute("fileId", fileUpload.getFileId());
		}
    	request.setAttribute("fxk", fxk);
    	
    	ItemPage itemPage = fxkService.findFxk(fxk);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	//判断用户角色是否是风险库管理员
    	SysUser sysUser = getVisitor(request);
    	SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(FxkForm.FXK_DICT_TYPE, FxkForm.FXK_ADMIN).getParameterValue());
    	SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
    	if(rolesUser != null){
    		request.setAttribute("userRoles", FxkForm.FXK_ADMIN);
    	}
		return "/dc/project/fxk/list";
    }
    /**
     * 详情页
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "view",method = {RequestMethod.GET,RequestMethod.POST})
    public String view(HttpServletRequest request,HttpServletResponse response,FxkForm fxk) throws Exception{
    	String viewId = request.getParameter("viewId");
    	if(StringUtils.isNotEmpty(viewId) && StringUtils.isEmpty(fxk.getId())){
    		fxk.setId(viewId);
    	}
    	//查询阶段列表
    	List<GcFxkStage> list = fxkService.queryGcFxkStageList(fxk.getId());
    	//文件临时id,固定值
    	ItemPage itemPage = fxkService.findFxkView(fxk);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("fxk", fxk);
    	request.setAttribute("list", list);
    	return "/dc/project/fxk/view";
    }
    /**
	 * @author ZENGKAI
	 * 上传完附件之后
	 * @param request
	 * @param response
	 * @param file
	 */
	@RequestMapping(value="afterUploadFiles",method={RequestMethod.POST})
	@ResponseBody
	public void afterUploadFiles(HttpServletRequest request,HttpServletResponse response){
		String result = "0";
		String fileTempId = request.getParameter("fileTempId");//文件ID
		try {
			if(!StringUtils.isNotEmpty(fileTempId)){
				return;
			}
			//上传的文件
			RulesFileUpload fileUpload = cooperationService.queryRulesFileUploadByInfoId(fileTempId);
			if(fileUpload == null){
				return;
			}
			//读取文件
			File file = new File(fileUpload.getFilePath());
			String fileName = file.getName();//文件名
			InputStream is = new FileInputStream(file);//文件流
			int count = 0;
			//从文件导入数据
			if (fileName.endsWith("xlsx")) {
				OPCPackage fs = OPCPackage.open(is);
				XSSFWorkbook wb = new XSSFWorkbook(fs);
				count = wb.getNumberOfSheets();
			}
			
			if (fileName.endsWith("xls")) {
				POIFSFileSystem fs = new POIFSFileSystem(is);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				count = wb.getNumberOfSheets();
			}
			//文档有数据
			if(count > 0){
				
				fxkService.delData();
				for (int i = 0; i < count; i++) {
					List list = ExcelUtil.importFromExcel(file,i);
					fxkService.saveData(list,i);
				}
				
			}
			result = "1";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}finally{
			try {
				PrintWriter out = response.getWriter();
				out.write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
	}
	
	/**
	 * 清空风险库数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="delData",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void delData(HttpServletRequest request,HttpServletResponse response){
		String result = "0";
		try{
			fxkService.delData();
			result = "1";
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
