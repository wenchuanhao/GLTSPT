package com.cdc.sys.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trustel.common.ConstantDefine;
import org.trustel.util.BatchUserUtil;
import org.trustel.util.FileUpload;

import com.cdc.sys.form.DicFailImportObject;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysModuleService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.authentication.controller.DefaultController;

/**
 * 
 * @author xms
 *
 */
@Controller
@RequestMapping(value = "/sys/module/*")
public class SysImportModuleController extends CommonController{
	private static final String TEMP_FOLDER = "temp";
	
	private final static String sysSeparator = System.getProperty("file.separator");
	
	@Autowired
	private ISysModuleService moduleService;
	
	@Autowired
	private ISysLogService logService;
	
	//批量导入菜单
	@RequestMapping(value="importModule",method={RequestMethod.GET,RequestMethod.POST})
	public String importModule(HttpServletRequest request,HttpServletResponse response){
		
		return "sys/module/importModule";
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
	        	cell0.setCellValue("菜单信息录入");
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
	            cell1.setCellValue("菜单编码");
	            cell2.setCellValue("菜单名称");
	            cell3.setCellValue("是否菜单");
	            cell4.setCellValue("菜单等级");
	            cell5.setCellValue("菜单父级编码");
	            cell6.setCellValue("菜单URL");
	            cell7.setCellValue("菜单显示顺序");
	            cell7.setCellValue("描述");
	            
	            
	            //是否菜单
	            String [] isSMS={"是","否"}; 
	            setRangeAddress(sheet, 2,65535,2,2, isSMS);
	            
                //菜单等级 1级ROOT
	            String [] isUserStatus={"0级","1级","2级","3级","4级"}; 
	            setRangeAddress(sheet, 2,65535,3,3, isUserStatus);
	             
	            // 新建一输出文件流
	            FileOutputStream fOut = new FileOutputStream(outputFile);
	            // 把相应的Excel 工作簿存盘
	            workbook.write(fOut);
	            // 操作结束，关闭文件
	            fOut.flush();
	            fOut.close();
	            BatchUserUtil.download(request, response, srcName, "菜单信息批量导入模板.xls"); 	
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
	 * 批量导入菜单
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "importSysModules/{rnd}", method = {RequestMethod.POST,RequestMethod.GET})
	public  String importSysUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
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
			listObject=moduleService.saveData(visitor,fileSavePath + sysSeparator +newFileName, request);
			if(null!=listObject && listObject.size()>0){
				request.setAttribute("listObject", listObject);//导入失败原因
				//return listObject.toString();
			}else{
				request.setAttribute("flag", "ok");//设置批量配置成功的标识
				//return "ok";
				logService.log(request, getVisitor(request), "系统管理--菜单管理", "批量导入菜单",
						"导入" + String.valueOf(listObject.size()) + "个菜单", new Date(), "3", new Date(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/module/importModule";
	}
}
