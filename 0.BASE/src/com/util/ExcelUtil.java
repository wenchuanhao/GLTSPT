package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 * @author ZENGKAI
 * @date 2016-04-26 16:31:29
 */
public class ExcelUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 导出excel通用版
     * @param header	表头
     * @param fileName	文件名
     * @param list	内容列表
     * @param book	excel
     * @return
     */
    public static HSSFWorkbook exportForExcel(String header[],String fileName, List<Object[]> list, HSSFWorkbook book){
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
		HSSFSheet sheet = book.createSheet(fileName);

		//设置表头
		int r = 0;
		HSSFRow row = sheet.createRow(r);
    	row = sheet.createRow(r);
    	for (int s = 0; s < header.length; s++) {
    		HSSFCell cell = row.createCell(s);
    		cell.setCellValue(header[s].toString());
    		cell.setCellStyle(style);
    		sheet.setColumnWidth(s, 4000);
    	}
    	//内容样式
    	HSSFCellStyle style2 = book.createCellStyle();
    	HSSFFont font2 = book.createFont();
    	style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style2.setFont(font2);
    	//数据内容
    	for (int i = 0; i < list.size(); i++) {
    		r++;
    		row = sheet.createRow(r);
    		Object[] objs = (Object[]) list.get(i);
    		HSSFCell cell =null;
    		for (int k = 0; k < objs.length; k++) {
    			cell = row.createCell(k);
    			cell.setCellStyle(style2);
    			//设置值
    			Object value = objs[k];
    			if(value instanceof String){
    				cell.setCellValue(value.toString());
    			}else if(value instanceof Double){
    				cell.setCellValue(((Double)value).doubleValue());
    			}else if(value instanceof Integer){
    				cell.setCellValue(((Integer)value).intValue());
    			}else if(value instanceof Float){
    				cell.setCellValue(((Float)value).floatValue());
    			}else if(value instanceof Long){
    				cell.setCellValue(((Long)value).longValue());
    			}else if(value instanceof Boolean){
    				cell.setCellValue(((Boolean)value).booleanValue());
    			}else if((value instanceof Date) | (value instanceof java.sql.Date)){
					cell.setCellValue(sdf.format(value));
				}
    		}
    	}
    	
    	return book;
    }

	public static List importFromExcel(MultipartFile file,int index) throws IOException {
		InputStream is = file.getInputStream();
		return getResult(is,file.getOriginalFilename(),index);
	}
	
	public static List importFromExcel(File file,int index) throws IOException {
		InputStream is = new FileInputStream(file);
		return getResult(is,file.getName(),index);
	}
	
	private static List getResult(InputStream is,String fileName,int index){
		List result = new ArrayList();
		try {
			int rowSize = 0;
			if (fileName.endsWith("xlsx")) {
				OPCPackage fs = OPCPackage.open(is);
				XSSFWorkbook wb = new XSSFWorkbook(fs);
				XSSFCell cell = null;
				XSSFSheet st = wb.getSheetAt(index);
				for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
					XSSFRow row = st.getRow(rowIndex);
					if (row != null) {
						int tempRowSize = row.getLastCellNum();
						if (tempRowSize > rowSize)
							rowSize = tempRowSize;
						String[] values = new String[rowSize];
						Arrays.fill(values, "");
						boolean hasValue = false;
						for (short columnIndex = 0; columnIndex < row
								.getLastCellNum(); columnIndex = (short) (columnIndex + 1)) {
							String value = "";
							cell = row.getCell(columnIndex);
							if (cell != null) {
								 switch (cell.getCellType()) {   
		                            case HSSFCell.CELL_TYPE_NUMERIC: // 数字   
		                            	
		                            	short format = cell.getCellStyle().getDataFormat();  
		                                if(format == 14 || format == 31 || format == 57 || format == 58 || format == 178){  
		                                    //日期  
		                                    value = new SimpleDateFormat("yyyy-MM-dd").format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())); 
		                                }else if (format == 20 || format == 32) {  
		                                    //时间  
		                                	value = new SimpleDateFormat("HH:mm").format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())); 
		                                }else if(format == 0){
		                                	cell.setCellType(Cell.CELL_TYPE_STRING);
		                                	value = cell.getStringCellValue(); 
		                                }
		                                else{
		                                	value = String.valueOf(cell.getNumericCellValue()); 
		                                }
		                            	
		                                break;   
		                            case HSSFCell.CELL_TYPE_STRING: // 字符串   
		                            	value = cell.getStringCellValue();   
		                                break;   
		                            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean   
		                            	value = String.valueOf(cell.getBooleanCellValue()); 
		                                break;   
		                            case HSSFCell.CELL_TYPE_FORMULA: // 公式   
		                            	try {
		                            		value = new DecimalFormat("#.00").format(cell.getNumericCellValue());
		                            	} catch (IllegalStateException e) {
		                            		value = String.valueOf(cell.getRichStringCellValue());
		                            	}
		                                break;   
		                            case HSSFCell.CELL_TYPE_BLANK: // 空值   
		                                break;   
		                            case HSSFCell.CELL_TYPE_ERROR: // 故障   
		                                break;   
		                            default:   
		                                break;   
		                        }   
							}

							hasValue = true;
							values[columnIndex] = value;
						}
						if (hasValue)
							result.add(values);
					}
				}
			}
			if (fileName.endsWith("xls")) {
				POIFSFileSystem fs = new POIFSFileSystem(is);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFCell cell = null;
				HSSFSheet st = wb.getSheetAt(index);
				for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
					HSSFRow row = st.getRow(rowIndex);
					if (row != null) {
						int tempRowSize = row.getLastCellNum();
						if (tempRowSize > rowSize)
							rowSize = tempRowSize;
						String[] values = new String[rowSize];
						Arrays.fill(values, "");
						boolean hasValue = false;
						for (short columnIndex = 0; columnIndex < row
								.getLastCellNum(); columnIndex = (short) (columnIndex + 1)) {
							String value = "";
							cell = row.getCell(columnIndex);
							if (cell != null) {
								 switch (cell.getCellType()) {   
		                            case HSSFCell.CELL_TYPE_NUMERIC: // 数字   
		                            	short format = cell.getCellStyle().getDataFormat();  
		                                if(format == 14 || format == 31 || format == 57 || format == 58){  
		                                    //日期  
		                                    value = new SimpleDateFormat("yyyy-MM-dd").format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())); 
		                                }else if (format == 20 || format == 32) {  
		                                    //时间  
		                                	value = new SimpleDateFormat("HH:mm").format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())); 
		                                }else{
		                                	value = String.valueOf(cell.getNumericCellValue()); 
		                                }
		                                break;   
		                            case HSSFCell.CELL_TYPE_STRING: // 字符串   
		                            	value = cell.getStringCellValue();   
		                                break;   
		                            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean   
		                            	value = String.valueOf(cell.getBooleanCellValue()); 
		                                break;   
		                            case HSSFCell.CELL_TYPE_FORMULA: // 公式   
		                            	try {
		                            		value = new DecimalFormat("#.00").format(cell.getNumericCellValue());
		                            	} catch (IllegalStateException e) {
		                            		value = String.valueOf(cell.getRichStringCellValue());
		                            	}
		                                break;   
		                            case HSSFCell.CELL_TYPE_BLANK: // 空值   
		                                break;   
		                            case HSSFCell.CELL_TYPE_ERROR: // 故障   
		                                break;   
		                            default:   
		                                break;   
		                        }   
							}

							hasValue = true;
							values[columnIndex] = value;
						}
						if (hasValue)
							result.add(values);
					}
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
