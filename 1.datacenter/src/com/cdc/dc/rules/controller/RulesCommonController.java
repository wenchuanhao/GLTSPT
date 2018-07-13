package com.cdc.dc.rules.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trustel.common.ConstantDefine;
import org.trustel.system.SystemConstant;
import org.trustel.util.BatchUserUtil;
import org.trustel.util.FileUpload;

import com.cdc.common.BusTypes;
import com.cdc.common.WorkorderNo;
import com.cdc.dc.account.form.FlowInfoForm;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.form.RulesForm;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.dc.rules.model.RulesFlowInfo;
import com.cdc.dc.rules.model.RulesInfo;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.form.DicFailImportObject;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.CommonUtil;
import com.cdc.util.ExcelUtil;
import com.trustel.common.DateFunc;
import com.trustel.common.ItemPage;
import com.trustel.common.SerialNo;
/**
 * 公用类
 * @author ZENGKAI
 * @date 2016-04-07 17:04:29
 */
@Controller
@RequestMapping(value = "/rulesController/")
public class RulesCommonController extends CommonController{

	@Autowired
	private IRulesService rulesService;

	@Autowired
	private IAccountService accService;
	
	@Autowired
	private ISysLogService logService;
	
	private final static String sysSeparator = System.getProperty("file.separator");
	
	List<DicFailImportObject> listObject=null;
	
	/**
	 * @author ZENGKAI
	 * 上传文件及更新文件
	 * @param request
	 * @param response
	 * @param file
	 */
	@RequestMapping(value="uploadFile",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void uploadFile(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file") MultipartFile file){
		response.setContentType("text/html; charset=utf-8");
		String type = request.getParameter("type");
		String busType = request.getParameter("busType");
		try {
			String fileName=file.getOriginalFilename();
			InputStream fis = file.getInputStream();
			boolean flag = false;
			if(!CommonUtil.checkFileType(fileName,fis)) {
				flag = true;
			}
			//制度文件限制为Word、PDF
			if(RulesCommon.rulesFileUploadTypes_ZDWJ.equals(type) && !"*.doc;*.docx;*.pdf".contains(fileName.toLowerCase().substring(fileName.toLowerCase().lastIndexOf(".")))){
				flag = true;
			}
			//发布依据限制为Word、PDF、图片
			if(RulesCommon.rulesFileUploadTypes_FBYJ.equals(type) && !"*.doc;*.docx;*.pdf;*.gif;*.jpg;*.jpeg;*.png".contains(fileName.toLowerCase().substring(fileName.toLowerCase().lastIndexOf(".")))){
				flag = true;
			}
			//文件（指令）扫描件限制为图片
//			if(RulesCommon.rulesFileUploadTypes_ZLSMJ.equals(type) && !"*.gif;*.jpg;*.jpeg;*.png".contains(fileName.toLowerCase().substring(fileName.toLowerCase().lastIndexOf(".")))){
//				flag = true;
//			}
			if(RulesCommon.rulesFileUploadTypes_AD.equals(type) && !"*.gif;*.jpg;*.jpeg;*.png".contains(fileName.toLowerCase().substring(fileName.toLowerCase().lastIndexOf(".")))){
				flag = true;
			}
			if(flag){
				PrintWriter out = response.getWriter();
				out.print("3");
				if(out != null) {
					out.close();
				}
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SysUser visitor = getVisitor(request);
		
        //获取上传文件的路径  ConstantDefine.REQUIRE_PATH;
		String separator = ConstantDefine.FILE_SEPARATOR;
		//配置管理根目录
		String uploadDir = rulesService.getConfigStr("UPLOAD_DIR", "mmac_data");
		//配置管理业务目录
		String busTypeDIr = SysParamHelper.getSysParamByCode("UPLOAD_DIR",BusTypes.busTypes_ZD).getParameterValue();
		String realPath = separator + uploadDir + separator + busTypeDIr + separator + DateFunc.getCurrentDateString("yyyyMMdd");
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		} 
        //第一次上传的文件
		String fullFileName=null;
		String fileName=null;
	   //创建后 则上传
		fileName=file.getOriginalFilename();
		String fileNameExt = FilenameUtils.getExtension(fileName);
		fullFileName = dir.getPath() + separator+ WorkorderNo.getUNID() + "." + fileNameExt;
			PrintWriter out = null;
			try {
				super.uploadFile(file,fullFileName);	
				out=response.getWriter();
				
				String fileTempId=request.getParameter("fileTempId");//临时文件信息
				String fileId = request.getParameter("fileId");//文件ID，为空则新增，不为空即更新
				
				String isParent = request.getParameter("isParent");
				String status = request.getParameter("status");
				if(!StringUtils.isNotEmpty(type)) {
					type="2"; //默认制度附件
					  //* 1.	制度文件(一个制度只有一个制度文件);
				      //* 2.	制度附件
				      //* 3.	发布依据（基地级、跨部门级制度需上传决策会议纪要，部门级制度需上传经审核签字的扫描件）
					  //* 4.	制度相关文档（可为父信息类型）
				      //* 5.	模板(可为父信息类型)
				      //* 6.	流程知识库(可为父信息类型)
				}
				
	//		    RulesInfo model = rulesService.findRulesInfoById(fileTempId);
			    RulesFileUpload fileUpload = new RulesFileUpload();
			    fileUpload.setAbstractName("附件");
			    fileUpload.setRulesInfoid(fileTempId);//附件关联制度信息ID或知识库、模板（即父信息）ID
			    fileUpload.setIsParent(isParent);//非父信息
				fileUpload.setFileName(fileName);
				fileUpload.setFilePath(fullFileName);
				fileUpload.setFileSize(file.getSize());//以kb为单位
				fileUpload.setTypes(type);
				fileUpload.setStatus(status);//保存
				if(StringUtils.isNotEmpty(busType)){
					fileUpload.setBusTypes(busType);
				}else{
					fileUpload.setBusTypes(BusTypes.busTypes_ZD);//制度管理业务类型
				}
				//新增
				if (null != visitor && !StringUtils.isNotEmpty(fileId)) {
					fileUpload.setCreateTime(new Date());	
					fileUpload.setCreateUserid(visitor.getUserId());
					fileUpload.setCreateUsername(visitor.getUserName());
				}
				
				//更新，该id不为空
				if(StringUtils.isNotEmpty(fileId)){
					RulesFileUpload fileUpload_old = (RulesFileUpload) rulesService.getEntity(RulesFileUpload.class, fileId);
					if(fileUpload_old != null){
						fileUpload.setCreateTime(fileUpload_old.getCreateTime());	
						fileUpload.setCreateUserid(fileUpload_old.getCreateUserid());
						fileUpload.setCreateUsername(fileUpload_old.getCreateUsername());
					}
					fileUpload.setFileId(fileId);
					fileUpload.setUpdateTime(new Date());
					if(null != visitor){
						fileUpload.setUpdateUserid(visitor.getUserId());
						fileUpload.setUpdateUsername(visitor.getUserName());
					}
				}
				rulesService.saveOrUpdateFileInfo(fileUpload);
				
				out.print("1");
				out.close();
			
			}catch (Exception e) {
				out.print("0");
				e.printStackTrace();
			}
		
	}
	
	//导出模板
	@RequestMapping(value="downloadFile",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void downloadFile(HttpServletRequest request , HttpServletResponse response) throws Exception {
		
		java.io.BufferedOutputStream bos = null;
		String type = request.getParameter("type");
		
		if(StringUtils.isBlank(type)){
			return;
		}
		
		InputStream is= null;
		if (type.equals("shuiwu")) {
			is= getClass().getResourceAsStream("shuiwu.xlsx");
		}else if(type.equals("liuchen")){
			is= getClass().getResourceAsStream("liuchen.xls");
		}
		try {
			response.setContentType("application/x-msdownload;");
			if (type.equals("shuiwu")) {
				response.setHeader("Content-disposition", "attachment; filename=" + new String("税务合同导入模板.xlsx".getBytes("gb2312"), "ISO8859-1" ));
			}else if(type.equals("liuchen")){
				response.setHeader("Content-disposition", "attachment; filename=" + new String("MIS流程导入模板.xls".getBytes("gb2312"), "ISO8859-1" ));
			}
			response.setHeader("Content-Length", "");
			
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (bos != null)
				bos.close();
		}
	}
	
	/**
	 * 批量导入信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="importExcel",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public  void importExcel(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file") MultipartFile file) throws Exception {
		SysUser visitor=this.getVisitor(request);
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
		String separator = ConstantDefine.FILE_SEPARATOR;
		//配置管理根目录
		String uploadDir = rulesService.getConfigStr("UPLOAD_DIR", "mmac_data");
		//配置管理业务目录
		String busTypeDIr = SysParamHelper.getSysParamByCode("UPLOAD_DIR",BusTypes.busTypes_BZ).getParameterValue();
		String realPath = separator + uploadDir + separator + busTypeDIr + separator + DateFunc.getCurrentDateString("yyyyMMdd");
		File files = new File(realPath);
       //如果文件夹不存在 则创建
		if(!files.exists() ){
			files.mkdirs();
		}
		String type = request.getParameter("type");	
		//第一次上传的文件
		String fileSavePath=null;
		String fileName=null;
	   //创建后 则上传
		fileName=file.getOriginalFilename();
		String fileNameExt = FilenameUtils.getExtension(fileName);
		fileSavePath = files.getPath();// + separator+ WorkorderNo.getUNID() + "." + fileNameExt;
	 
       //重命名文件名
		SimpleDateFormat sdf  =new  SimpleDateFormat("yyyyMMddHHmmss");
		String datestr =sdf.format(new Date());
		String newFileName = "";
		//根据excel名称判断导入模块
		if("suiwu".equals(type)){
			newFileName = "shuiwu_"+datestr+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		}else if("8".equals(type)){
			newFileName = "commonDic_"+datestr+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		}
		FileUpload.createFolder(fileSavePath);
		
		try {
			
			BatchUserUtil.copyFile(file, fileSavePath + sysSeparator+ newFileName);
			if("suiwu".equals(type)){
				listObject = accService.saveSWData(visitor,fileSavePath + sysSeparator +newFileName);
				//MIS流程文件上传
			}else if("8".equals(type)){
				listObject = accService.saveData(visitor,fileSavePath + sysSeparator +newFileName, request);
			}
			if(null!=listObject && listObject.size()>0){
				request.setAttribute("listObject", listObject);//导入失败原因
			}else{
				request.setAttribute("flag", "ok");//设置批量配置成功的标识
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.write("1");
		out.close();
	}
	
	
	/**
	 * excel导入后异步查询错误信息
	 * @throws IOException 
	 */
	@RequestMapping(value="queryErrorInfo",method={RequestMethod.GET,RequestMethod.POST})
	public void queryErrorInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONArray errorInfo = JSONArray.fromObject(listObject);
		PrintWriter out = response.getWriter();
		out.write(errorInfo.toString());
	}
	
	/**
	 * excel导入后异步查询信息
	 * @throws IOException 
	 */
	@RequestMapping(value="queryImportInfo",method={RequestMethod.GET,RequestMethod.POST})
	public String queryImportInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String importType = request.getParameter("importType");
		if(!StringUtils.isNotEmpty(importType)){
			importType="1";
		}
		FlowInfoForm flowInfoForm = new FlowInfoForm();
		flowInfoForm.setPageIndex(0);
		flowInfoForm.setPageSize(100);
		ItemPage itemPage = null;
		if(importType.equals("3")){
			//查询审批流程信息列表
			itemPage = accService.queryFlowInfoList(flowInfoForm );
		}else{
			//查询税务合同信息列表
			itemPage = accService.queryTaxInfoList(flowInfoForm);
		}
		request.setAttribute("type", importType);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "dc/account/loadInfoCollect";
	}
	
	/**
	 * 导出功能
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,RulesForm rulesForm) {
		
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
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		//设置工作表sheet
		HSSFSheet sheet = book.createSheet("制度详情");

		//设置第一行
		int r = 0;
		HSSFRow row = sheet.createRow(r);
		HSSFCell cel = row.createCell(0);
		cel.setCellStyle(style);
		cel.setCellValue("制度详情");
		HSSFCell cel2 = row.createCell(9);
		cel2.setCellStyle(style);
		cel2.setCellValue("相关文档");
		HSSFCell cel3 = row.createCell(10);
		cel3.setCellStyle(style);
		cel3.setCellValue("流程跟踪");
		
		// 表头状态	牵头部门
		String[] header = {"制度编号","制度名称","制度分类","制度等级","状态","牵头部门","制度文件","制度附件","发布依据"
							,"文件个数"
							,"制度创建","时间","制度发布","时间","制度废止","时间"};
		//合并第一行
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 9));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, header.length-1));
		String flag = request.getParameter("flag");
		if(StringUtils.isNotEmpty(flag)){
			SysUser sysUser = getVisitor(request);
			if(sysUser != null){
				SysOrganization parentOrg = rulesService.queryParentOrg(sysUser.getOrganizationId());
				rulesForm.setLeadDepId(parentOrg.getOrganizationId());
				rulesForm.setCreateUserid(sysUser.getUserId());
			}
		}
		rulesForm.setPageSize(65525);
		rulesForm.setSubBoxValue(request.getParameter("subBoxValue"));
		rulesForm.setSource(request.getParameter("source"));
		String roleStr = rulesService.setUserRoles(request,rulesForm);
		List<Object[]> list = (List<Object[]>) rulesService.queryExportRulesInfoItemPage(rulesForm,roleStr);
		
		try {
			//文件名
			String fileName = "制度详情列表" + SerialNo.getUNID();
			String tempName = "temp" + SerialNo.getUNID();
			ExcelUtil.exportForExcel(header, r, tempName, list, book, row, sheet, style);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author ZENGKAI
	 * 异步查询附件列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryFileList", method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void queryFileList(HttpServletRequest request,HttpServletResponse response){
		String fileTempId=request.getParameter("fileTempId");
		String type = request.getParameter("type");
		
		try{
			List<RulesFileUpload>  fileUploads = rulesService.queryRulesFileList(fileTempId,type );//查询列表
			
			JSONArray userListJSONArray = JSONArray.fromObject(fileUploads);
			PrintWriter out = response.getWriter();
    		out.write(userListJSONArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author ZENGKAI
	 * 下载附件按附件id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "downloadRulesFile", method = RequestMethod.GET)
	public void downloadRulesFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		//附件ID
		String fileId = request.getParameter("fileId");
		RulesFileUpload fileUpload = (RulesFileUpload) rulesService.getEntity(RulesFileUpload.class, fileId);
		String downLoadPath = fileUpload.getFilePath();
		try {
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileUpload.getFileName().getBytes("gb2312"), "ISO8859-1" ));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)bis.close();
			if (bos != null)bos.close();
		}
	}
	
	/**
	 * @author ZENGKAI
	 * 下载附件按附件id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "showImage", method = RequestMethod.GET)
	public void showImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		//附件ID
		String fileId = request.getParameter("fileId");
		RulesFileUpload fileUpload = (RulesFileUpload) rulesService.getEntity(RulesFileUpload.class, fileId);
		String downLoadPath = fileUpload.getFilePath();
		try {
			File file = new File(downLoadPath); 
			if(!file.exists()){
				return;
			}
			long fileLength = file.length();
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileUpload.getFileName().getBytes("gb2312"), "ISO8859-1" ));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				response.getOutputStream().write(buff, 0, bytesRead);
			}
	    	//刷新
	    	response.getOutputStream().flush();
	    	response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)bis.close();
		}
	}
	
	/**
	 * @author ZENGKAI
	 *  删除附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="delRulesFile",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void delRulesFile(HttpServletRequest request,HttpServletResponse response){
		String fileId = request.getParameter("fileId");
		String result = "0";
		try{
			if(StringUtils.isNotEmpty(fileId)){
				String[] fileIds = fileId.split(",");
				for (String id : fileIds) {
					RulesFileUpload fileUpload = (RulesFileUpload) rulesService.getEntity(RulesFileUpload.class, id);
					if(fileUpload != null){
						//删除文件
						SysUser visitor = getVisitor(request);
						rulesService.delRulesFile(fileUpload,visitor);
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
	
	
	
	/**  
	 * 查看制度相关文档
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "viewFileUpload", method = {RequestMethod.GET})
	public String viewFileUpload(HttpServletRequest request) throws Exception{
		String rulesId = request.getParameter("rulesId");
		//查询制度信息
		//如果有ID，查询信息
		if(StringUtils.isNotEmpty(rulesId)){
			//父信息
			List<RulesFileUpload> fileUploadPareent =  rulesService.queryRulesFileUploadByRulesId(rulesId,RulesCommon.rulesFileUploadTypes_ZDXGWD );
			//根据父节点查询列表
			for (RulesFileUpload rulesFileUpload : fileUploadPareent) {
				rulesFileUpload.setFileList(rulesService.queryRulesFileList(rulesFileUpload.getFileId(),RulesCommon.rulesFileUploadTypes_ZDXGWD ));
			}
			request.setAttribute("fileUpload", fileUploadPareent);
		}
		
		return "/dc/rules/fileUploadView";
	}
	
	/**  
	 * 查看制度详情页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "viewRule", method = {RequestMethod.GET})
	public String viewRule(HttpServletRequest request) throws Exception{
		String rulesId = request.getParameter("rulesId");
		//查询制度信息
		RulesInfo rulesInfo = null;
		//如果有ID，查询信息
		if(StringUtils.isNotEmpty(rulesId)){
			rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, rulesId);
		}
		
		RulesType rulesType = null;
		if(rulesInfo != null && StringUtils.isNotEmpty(rulesInfo.getRulesTypeId())){
			rulesType = (RulesType) rulesService.getEntity(RulesType.class, rulesInfo.getRulesTypeId());
		}
		//制度相关文档
		if(StringUtils.isNotEmpty(rulesId)){

			//父信息
			List<RulesFileUpload> fileUploadPareent =  rulesService.queryRulesFileUploadByRulesId(rulesId,RulesCommon.rulesFileUploadTypes_ZDXGWD );
			//根据父节点查询列表
			for (RulesFileUpload rulesFileUpload : fileUploadPareent) {
				rulesFileUpload.setFileList(rulesService.queryRulesFileList(rulesFileUpload.getFileId(),RulesCommon.rulesFileUploadTypes_ZDXGWD ));
			}
		
			request.setAttribute("fileUpload", fileUploadPareent);
		}
		//流程信息查询
		ItemPage  flowInfoList = rulesService.queryRulesFlowInfoList(rulesId);//查询列表
		request.setAttribute("rulesInfo", rulesInfo);//制度信息
		request.setAttribute("rulesType", rulesType);//制度类型
		request.setAttribute("fileTempId", rulesId);//制度编号
		request.setAttribute("flowInfoList", flowInfoList);//制度流程信息
		
		//创建时间
		RulesFlowInfo rulesFlowInfoCJ = rulesService.queryRulesFlowInfoStatus(rulesId,RulesCommon.rulesFlowInfoFlowLink_CJ,null,null);
		request.setAttribute("rulesFlowInfoCJ", rulesFlowInfoCJ);
		//最新发布时间
		RulesFlowInfo rulesFlowInfoFB = rulesService.queryRulesFlowInfoStatus(rulesId,RulesCommon.rulesFlowInfoFlowLink_FB,null,null);
		request.setAttribute("rulesFlowInfoFB", rulesFlowInfoFB);
		//已废止的
		RulesFlowInfo rulesFlowInfoFZ = rulesService.queryRulesFlowInfoStatus(rulesId,RulesCommon.rulesFlowInfoFlowLink_FZ,null,null);
		request.setAttribute("rulesFlowInfoFZ", rulesFlowInfoFZ);
		
		return "/dc/rules/rulesView";
	}
	
	/**  
	 * 跳转至制度废止页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "repealViewRules", method = {RequestMethod.GET})
	public String repealViewRules(HttpServletRequest request) throws Exception{
		String rulesId = request.getParameter("rulesId");
		//如果有ID，查询信息
		if(StringUtils.isNotEmpty(rulesId)){
			request.setAttribute("rulesId", rulesId);
		}
		
		//制度废止页面
		return "/dc/rules/repealViewRules";
	}
	
	/**  
	 * 跳转至文档上传页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "documentUpload", method = {RequestMethod.GET})
	public String documentUpload(HttpServletRequest request) throws Exception{
		String rulesId = request.getParameter("rulesId");
		//查询制度信息
		RulesInfo rulesInfo = null;
		RulesFileUpload rulesFileUpload = null;
		//如果有ID，查询信息
		if(StringUtils.isNotEmpty(rulesId)){
			rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, rulesId);
			//20160506全部上传文档均为新增
//			rulesFileUpload = (RulesFileUpload) rulesService.queryRulesFileUploadByRulesId(rulesId,RulesCommon.rulesFileUploadTypes_ZDXGWD);
		}
		request.setAttribute("rulesInfo", rulesInfo);
		request.setAttribute("rulesFileUpload", rulesFileUpload);
		//文档上传页面
		return "/dc/rules/documentUpload";
	}
	/**
	 * 制度相关文档保存
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "documentsRules", method = {RequestMethod.GET, RequestMethod.POST})
	public void documentsRules(HttpServletRequest request,HttpServletResponse response,RulesFileUpload rulesFileUpload){
		String type = request.getParameter("type");
		String rulesId = request.getParameter("rulesId");
		SysUser sysUser = getVisitor(request);
		String result = "0";
		try {
			if(sysUser != null){
				rulesFileUpload.setCreateTime(new Date());
				rulesFileUpload.setCreateUserid(sysUser.getUserId());
				rulesFileUpload.setCreateUsername(sysUser.getUserName());
			}
			rulesFileUpload.setTypes(type);
			rulesFileUpload.setRulesInfoid(rulesId);
			rulesFileUpload.setBusTypes(BusTypes.busTypes_ZD);
			//原有临时ID
			String tempFileId= rulesFileUpload.getFileId();
			if(StringUtils.isNotEmpty(tempFileId)){
				RulesFileUpload rulesFileUploadtmp = (RulesFileUpload) rulesService.getEntity(RulesFileUpload.class, tempFileId);
				//新增相关文档
				if(rulesFileUploadtmp == null){
					rulesService.saveEntity(rulesFileUpload);
				}else{
					//更新相关文档
					if(rulesFileUploadtmp != null){
						rulesFileUpload.setCreateTime(rulesFileUploadtmp.getCreateTime());	
						rulesFileUpload.setCreateUserid(rulesFileUploadtmp.getCreateUserid());
						rulesFileUpload.setCreateUsername(rulesFileUploadtmp.getCreateUsername());
					}
					rulesFileUpload.setUpdateTime(new Date());
					if(null != sysUser){
						rulesFileUpload.setUpdateUserid(sysUser.getUserId());
						rulesFileUpload.setUpdateUsername(sysUser.getUserName());
					}
					rulesService.updateEntity(rulesFileUpload);
				}
			}else{
				//新增相关文档
				rulesService.saveEntity(rulesFileUpload);
			}

			//更新子文档关联ID
			List<RulesFileUpload> listChilds = rulesService.queryRulesFileList(rulesId, type);
			for (RulesFileUpload ruleschildren : listChilds) {
				ruleschildren.setRulesInfoid(rulesFileUpload.getFileId());
			}
			rulesService.updateAll(listChilds);

			result = "1";
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传文档取消按钮
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "docCancel", method = {RequestMethod.GET, RequestMethod.POST})
	public void docCancel(HttpServletRequest request,HttpServletResponse response){
		String rulesId = request.getParameter("rulesId");
		String type = request.getParameter("type");
		String result = "0";
		//取消按钮对文档进行删除
		List<RulesFileUpload> list  = rulesService.queryRulesFileList(rulesId,type);
		if(list != null){
			for (RulesFileUpload rulesFileUpload : list) {
				
				//附件信息
				if(RulesCommon.rulesFileUploadIsParent_N.equals(rulesFileUpload.getIsParent())){
					String filePath = rulesFileUpload.getFilePath();
					File file = new File(filePath);
					if(file.exists()){
						file.delete();//物理删除
					}
				}
				rulesService.delEntity(rulesFileUpload);
			}
		}
		result = "1";
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 制度修订
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "reviseRules", method = {RequestMethod.GET})
	public String reviseRules(HttpServletRequest request) throws Exception{
		try {
			String rulesId = request.getParameter("rulesId");
			SysUser sysUser = getVisitor(request);
			
			//查询制度信息
			RulesInfo rulesInfo = null;
			RulesInfo rulesInfoNew = new RulesInfo();
			//如果有ID，查询信息
			if(StringUtils.isNotEmpty(rulesId)){
				rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, rulesId);
			}
			
			//如果无ID、并且已发布
			if(rulesInfo != null && sysUser != null  && RulesCommon.rulesInfoStatus_FB.equals(rulesInfo.getStatus())){
				
				//制度修订
				rulesInfo.setStatus(RulesCommon.rulesInfoStatus_XD);
				rulesService.reviseRules(rulesInfo,sysUser);
				//新建制度
				//并弹出创建制度页面，并重新编辑和提交新的制度，其中‘制度分类，制度等级，制度名称’三个字段携带过去，可以修改。
				rulesInfoNew.setRulesId(UUID.randomUUID().toString());
				rulesInfoNew.setRulesName(rulesInfo.getRulesName());
				rulesInfoNew.setRulesTypeId(rulesInfo.getRulesTypeId());
				rulesInfoNew.setRulesGrade(rulesInfo.getRulesGrade());
			}
			
			rulesService.setCommonAttribute(request);
			
			request.setAttribute("rulesInfo", rulesInfoNew);
			request.setAttribute("fileTempId", rulesInfoNew.getRulesId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/dc/rules/rulesAdd";
	}
	
	
	/**  
	 * 制度废止
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "repealRules", method = {RequestMethod.POST,RequestMethod.GET})
	public void repealRules(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String result = "0";
		SysUser sysUser = getVisitor(request);
		String rulesId = request.getParameter("rulesId");
		String handelOpinion = request.getParameter("handelOpinion");
		if(StringUtils.isNotEmpty(rulesId)){
			String[] rulesIds = rulesId.split(",");
			for (String id : rulesIds) {
				RulesInfo rulesInfo = (RulesInfo) rulesService.getEntity(RulesInfo.class, id);
				//已发布才能废止
				if(rulesInfo != null  && RulesCommon.rulesInfoStatus_FB.equals(rulesInfo.getStatus())){
					rulesInfo.setStatus(RulesCommon.rulesInfoStatus_FZ);//已废止
					rulesService.repealRules(rulesInfo,sysUser,handelOpinion);//废止
				}
			}
			result = "1";
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	
	}
	
	/**
	 * 文件上传配置--》用户联想查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchUser", method = {RequestMethod.POST})
	public @ResponseBody void searchUser(HttpServletRequest request,HttpServletResponse response) {
    	try {
    	    response.setContentType("text/html; charset=utf-8");
    	    String userValue = request.getParameter("userValue");
    		String result = "";
  	        if(userValue != null && !"".equals(userValue)){
  	        	List<Object[]> userList = rulesService.searchUserSuggest(userValue);
                JSONArray userListJSONArray = new JSONArray();
                if (userList != null && !userList.isEmpty()) {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    Object[] obj;
                    for (int i=0;i<userList.size();i++) {
                        obj = userList.get(i);
                        jsonObject = new JSONObject();
                        jsonArray = new JSONArray();
                        jsonObject.put("userId",String.valueOf(obj[0]));
                        jsonObject.put("userName",String.valueOf(obj[1]));
                        jsonObject.put("account",String.valueOf(obj[2]));
                        jsonArray.add(jsonObject);
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("orgName",obj[3] == null ? "":String.valueOf(obj[3]));
                        jsonArray.add(jsonObject1);

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
}