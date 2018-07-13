package com.cdc.sys.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trustel.common.ConstantDefine;
import org.trustel.util.BatchUserUtil;
import org.trustel.util.FileUpload;

import com.cdc.sys.form.DicFailImportObject;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;

/**
 * 用户批量导入
 * @author ZouJing
 *
 */
@Controller
@RequestMapping(value = "/sys/user/*")
public class BulkImportController extends CommonController {

	private static final String TEMP_FOLDER = "temp";
	
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysOrganizationService organizationService;
	@Autowired
	private ISysRoleService roleService;
	@Autowired
	private ISysLogService logService;
	
	private final static String sysSeparator = System.getProperty("file.separator");

	
	/**
	 * 跳转到批量导入用户页面
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "importSysUserView", method ={RequestMethod.GET,RequestMethod.POST})
	public String toImportSysUser(HttpServletRequest request) throws Exception {
		request.setAttribute("listOrg", organizationService.queryAllNoRoot());
		request.setAttribute("listrole", roleService.listAll());
		return "sys/user/importSysUserView";
	}
	/**
	 * 获取下一级组织机构
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "setNestOrg", method = {RequestMethod.POST })
	public @ResponseBody void setNestOrg(HttpServletRequest request,HttpServletResponse response) {
		String orgId=request.getParameter("orgId");
		String selectString="";
    	try {
    		request.setCharacterEncoding("utf-8");
  	        response.setContentType("text/html; charset=utf-8");
  	        if(null!=orgId && !"".equals(orgId)){
  	        	List<SysOrganization> listOrg=sysUserService.getNextOrg(this.getVisitor(request), orgId);	
  	  	        if(null!=listOrg && listOrg.size()>0){
  	  	        	for(int i=0;i<listOrg.size();i++){
  	  	    			SysOrganization org = (SysOrganization) listOrg.get(i);
  	  	    			selectString=selectString+"<option value='"+org.getOrganizationId()+","+org.getOrgName()+"'>"+org.getOrgName()+"</option>";    				
  	  	    		}
  	  	        	selectString=selectString+"</select>";
  	  	        }else{
  	  	        	selectString="0";
  	  	        }
  	        }else{
  	        	selectString="0";
  	        }
    		PrintWriter writer = response.getWriter();
    		writer.write(selectString);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
	/**
	 * 下载模板
	 * 
	 * @return
	 */
	@RequestMapping(value = "dolownTemplate/{rnd}", method = {RequestMethod.GET,RequestMethod.POST})
	public void dolownTemplate(HttpServletRequest request,HttpServletResponse response) {
		String oper = ConstantDefine.FILE_SEPARATOR;
		   File realPathFile = new File(request.getSession().getServletContext().getRealPath(oper));
		   
			File file=new File(realPathFile +oper+ TEMP_FOLDER);
	       //如果文件夹不存在 则创建
			if(!file.exists() ){
				file.mkdirs();
			}
			Integer radNum = new java.util.Random().nextInt(900)+100;
	    	SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss"); 
	        String srcName = f.format(new Date())+radNum+".xls";
			String outputFile =file.getAbsolutePath()+oper+srcName;
			
			try {
		        //创建新的Excel工作簿
		        	HSSFWorkbook workbook = new HSSFWorkbook();
		        //在Excel工作簿中建一工作表
		        	HSSFSheet sheet = workbook.createSheet("Sheet1");
		        // 设置单元格格式(文本)
		        	HSSFCellStyle cellStyle = workbook.createCellStyle();
		        	cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
		            
		        //设置标题字体
		        	HSSFFont font = workbook.createFont();
		        	font.setFontName("Arial Black");
		        	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		        	HSSFCellStyle style = workbook.createCellStyle();
		        	style.setFont(font);
		        	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
		        	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
		        	 	
		       //创建标题行
		        	HSSFRow rowTitle = sheet.createRow((short)0);
		        	HSSFCell cell0 = rowTitle.createCell(0);
		        	cell0.setCellValue("用户信息录入");
		        	cell0.setCellStyle(style);
		        	Region region = new Region((short)0,(short)0,(short)0,(short)4);   
		        	sheet.addMergedRegion(region);   
		        	
		        	HSSFFont font2 = workbook.createFont();
		        	font2.setFontName("楷体");
		        	font2.setColor(HSSFColor.RED.index);//红字
		        	HSSFCellStyle style2 = workbook.createCellStyle();
		        	style2.setFont(font2);


		        // 在索引1的位置创建行（第二行）
		        	HSSFRow row = sheet.createRow((short) 1);
		            HSSFCell cell1 = row.createCell(0);// 第一列
		            HSSFCell cell2 = row.createCell(1);
		            HSSFCell cell3 = row.createCell(2);
		            HSSFCell cell4 = row.createCell(3);
		            HSSFCell cell5 = row.createCell(4);
		            HSSFCell cell6 = row.createCell(5);
		            HSSFCell cell7 = row.createCell(6);

		            // 定义单元格为字符串类型
		            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
		            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		            cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
		            cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
		            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
		            cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
		            cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
		            
		            // 在单元格中输入数据
		            cell1.setCellValue("用户登录账号");
		            cell2.setCellValue("用户姓名");
		            cell3.setCellValue("是否接受SMS");
		            cell4.setCellValue("用户状态");
		            cell5.setCellValue("用户登录密码");
		            cell6.setCellValue("用户邮箱");
		            cell7.setCellValue("用户手机号码");
		            
		            //是否接受SMS下拉框
		            String [] isSMS={"接受","不接受"}; 
		            setRangeAddress(sheet, 2,65535,2,2, isSMS);
		            
                    //用户状态
		            String [] isUserStatus={"正常","禁用"}; 
		            setRangeAddress(sheet, 2,65535,3,3, isUserStatus);
		             
		            // 新建一输出文件流
		            FileOutputStream fOut = new FileOutputStream(outputFile);
		            // 把相应的Excel 工作簿存盘
		            workbook.write(fOut);
		            // 操作结束，关闭文件
		            fOut.flush();
		            fOut.close();
		            BatchUserUtil.download(request, response, srcName, "用户信息批量导入模板.xls"); 	
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	

		/**
		 * 设置下拉框公用方法
		 * @param sheet
		 * @param startRow
		 * @param endRow
		 * @param startCell
		 * @param endCell
		 * @param addressName
		 */
	 private void setRangeAddress(HSSFSheet sheet,int startRow,int endRow,int startCell,int endCell,String[] addressName){
		 CellRangeAddressList regions = new CellRangeAddressList(startRow,endRow,startCell,endCell);
	     DVConstraint constraint = DVConstraint.createExplicitListConstraint(addressName);
	     HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint); 
	     data_validation.createPromptBox("下拉选择提示","请使用下拉方式选择合适的值！");  
	     sheet.addValidationData(data_validation); 
	 }
	 
	 /**
	  * 设置数据有效性序列引用
	  * @param sheet
	  * @param startRow
	  * @param endRow
	  * @param startCell
	  * @param endCell
	  * @param formatStr
	  */
	 private void setReferAddress(HSSFSheet sheet,int startRow,int endRow,int startCell,int endCell,String formatStr){ 
		 CellRangeAddressList regions = new CellRangeAddressList(startRow,endRow,startCell,endCell);
	     DVConstraint constraint =DVConstraint.createFormulaListConstraint(formatStr);
	     HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint); 
	     sheet.addValidationData(data_validation); 
	 }
	 
	 /**
	  * 设置文档说明的字体
	  * @param workbook
	  * @param sheet
	  * @param titleValue
	  * @param cellValue
	  */
	 private void setStyle(HSSFWorkbook workbook,HSSFSheet sheet,String shets){
		 
		  //设置标题字体
	 	HSSFFont font = workbook.createFont();
	 	font.setFontName("Arial Black");
	 	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
	 	HSSFCellStyle style = workbook.createCellStyle();
	 	style.setFont(font);
	 	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
	 	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
	 	
	
	 	HSSFFont font2 = workbook.createFont();
	 	font2.setFontName("楷体");
	 	font2.setColor(HSSFColor.RED.index);//红字
	 	HSSFCellStyle style2 = workbook.createCellStyle();
	 	style2.setFont(font2);
	 	String titleValue="";
	 	String cellValue="";
	 	if(shets.equals("sheet")){
	 		titleValue="用户信息录入";
	 		cellValue="说明：所有值都为必填！";
	 	}
	 	
		 HSSFRow rowTitle = sheet.createRow((short)0);
	 	 HSSFCell cell0 = rowTitle.createCell(0);
	 	 cell0.setCellValue(titleValue);
	 	 cell0.setCellStyle(style);
	 	 Region region = new Region((short)0,(short)0,(short)0,(short)5);   
	 	 sheet.addMergedRegion(region); 
	 	
	 	 HSSFCell cell00 = rowTitle.createCell(7);
	 	 cell00.setCellValue(cellValue);
	 	 cell00.setCellStyle(style2);
	  }
	
	/**
	 * 批量导入用户动作
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "importSysUser/{rnd}", method = RequestMethod.POST)
	public  String importSysUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String organizationId=request.getParameter("organizationId");
		String organizationName=request.getParameter("organizationName");
		String roleId=request.getParameter("roleId");
		 SysUser visitor=this.getVisitor(request);
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
       //根据前台的name名称得到上传的文件
		 MultipartFile file = multipartRequest.getFile("commonupload");
		 String webRootPath = request.getSession().getServletContext().getRealPath("");
		 String imagePath = "/SRMC.war/temp/";
		 File files=new File(imagePath);
	       //如果文件夹不存在 则创建
			if(!files.exists() ){
				files.mkdirs();
			}
		 if ("\\".equals(sysSeparator)) {
				imagePath = imagePath.replace("/", "\\");
		 }
		 
		 String fileSavePath = new File(webRootPath).getParent() + imagePath;
       //重命名文件名
		SimpleDateFormat sdf  =new  SimpleDateFormat("yyyyMMddHHmmss");
		String datestr =sdf.format(new Date());
		String newFileName = "commonDic_"+datestr+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		FileUpload.createFolder(fileSavePath);
		List<DicFailImportObject> listObject=null;
		try {
			
			BatchUserUtil.copyFile(file, fileSavePath + sysSeparator+ newFileName);
			listObject=sysUserService.saveData(visitor,fileSavePath + sysSeparator +newFileName, request, organizationId,organizationName,roleId);
			if(null!=listObject && listObject.size()>0){
				request.setAttribute("listObject", listObject);//导入失败原因
				//return listObject.toString();
			}else{
				request.setAttribute("flag", "ok");//设置批量配置成功的标识
				//return "ok";
				logService.log(request, getVisitor(request), "系统管理--用户管理", "批量导入用户",
						"导入" + String.valueOf(listObject.size()) + "个用户", new Date(), "3", new Date(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("listOrg", organizationService.queryAllNoRoot());
		request.setAttribute("listrole", roleService.listAll());
		return "sys/user/importSysUserView";
	}
	
}
