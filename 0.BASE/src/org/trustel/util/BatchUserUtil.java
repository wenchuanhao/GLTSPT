package org.trustel.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户信息导入读取excel 辅助类
 * @author ZouJing
 *
 */
public class BatchUserUtil {
	
	private static final String TEMP_FOLDER = "temp";
	private static final String  contentType = "application/x-msdownload";

	/**
	 * 文件保存到服务器
	 * @param src
	 * @param des
	 */
	public static boolean copyFile(MultipartFile src, String path) {
		FileOutputStream fos = null;
		InputStream fis = null;
		try {
			fos = new FileOutputStream(path);
			fis = src.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (Exception e) {}
			if (fos != null)
				try {
					fos.close();
				} catch (Exception e) {}
		}
	}

	
	public static String[][] getTxtData(File file, int ignoreRows) throws FileNotFoundException, IOException {
	     List<String[]> result = new ArrayList<String[]>();
	     try {    
             BufferedReader br = null;   
             InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GB2312");  
             br = new BufferedReader(reader);  
             int colsCount = 0;  //文件行数
             ArrayList<String> str = new ArrayList<String>();
             List<String> sBuffer = new ArrayList();
             String value =null;
             while((value = br.readLine()) != null){
            	 colsCount++;
	             str.add(value);	             
             }
             br.close();
             for(int i=1;i<str.size();i++){
            	 sBuffer.add(str.get(i));            	 
             }
             colsCount=colsCount-1;
             String[] values = new String[colsCount];
             String temp="";
             for(int i=0;i<sBuffer.size();i++){
            	 temp=(String) sBuffer.get(i);
            	 String[] Listemp=temp.split("\\|");
            	 result.add(Listemp);
             }
             String[][] returnArray = new String[result.size()][colsCount];
             for (int i = 0; i < returnArray.length; i++) {
              	returnArray[i] = (String[]) result.get(i);
             }
             return returnArray;
         } catch (Exception e) {    
             e.printStackTrace(); 
             return null;
         }  
	     
	}
 
	 /**
	  * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	  * @param file 读取数据的源Excel
	  * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	  * @return 读出的Excel中数据的内容
	  * @throws FileNotFoundException
	  * @throws IOException
	  */
	public static String[][] getData(File file, int ignoreRows,int sheetIndex) throws FileNotFoundException, IOException {
     
     List<String[]> result = new ArrayList<String[]>();
     int rowSize = 0;
     BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

     // 打开HSSFWorkbook
     POIFSFileSystem fs = new POIFSFileSystem(in);
     HSSFWorkbook wb = new HSSFWorkbook(fs);
     HSSFCell cell = null;

     HSSFSheet st = wb.getSheetAt(sheetIndex);
     HSSFRow row0 = st.getRow(1);
     int modelDataSize = row0.getLastCellNum();
     
     //过滤最后几行，如果为空
     int rowCount = st.getLastRowNum();
     lable1:while (rowCount>=0) {
    	 
    	 HSSFRow row = st.getRow(rowCount);
         if (row == null){
             continue;
         }
         int colIndexCount = row.getLastCellNum();
         for (int i = 0; i < colIndexCount+1; i++) {
        	cell = row.getCell(i);
        	String value = "";
          	if (cell != null) {
         		// 注意：一定要设成这个，否则可能会出现乱码
         		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
         		switch (cell.getCellType()) {
         		case HSSFCell.CELL_TYPE_STRING:
         			 value = cell.getStringCellValue();
         			 break;
         		case HSSFCell.CELL_TYPE_NUMERIC:
         		     if (HSSFDateUtil.isCellDateFormatted(cell)) {
         			     Date date = cell.getDateCellValue();
         				 if (date != null) {
         					 value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
         				 } else {
         				     value = "";
         				 }
         			 } else {
         			     value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
         			     String[] valueTemp = value.split("\\.");
         			     if("00".equals(valueTemp[1])){
         			    	 value = valueTemp[0];
         			     }
         			 }
         			 break;
         		case HSSFCell.CELL_TYPE_FORMULA:
         			// 导入时如果为公式生成的数据则无值
         			 if (!cell.getStringCellValue().equals("")) {
         			 	 value = cell.getStringCellValue();
         			 } else {
         			     value = cell.getNumericCellValue() + "";
         			 }
         			 break;
         		case HSSFCell.CELL_TYPE_BLANK:
         			 break;
         		case HSSFCell.CELL_TYPE_ERROR:
         			 value = "";
         			 break;
         		case HSSFCell.CELL_TYPE_BOOLEAN:
         			 value = (cell.getBooleanCellValue() == true ? "Y": "N");
         			 break;
         		default:
         			 value = "";
         		}
         	}
          	
        	 if(StringUtils.isNotBlank(value)){ //假如有1列不为空，认为这行是有数据。
        		 break lable1;
        	 }
		 }
    	 rowCount--;
     }
     cell = null;
     
     
     // 第一行为标题，不取
     for (int rowIndex = ignoreRows; rowIndex <= /*st.getLastRowNum()*/ rowCount ; rowIndex++) {
         HSSFRow row = st.getRow(rowIndex);
         if (row == null){
             continue;
         }
         int tempRowSize = row.getLastCellNum() + 1;
         if (tempRowSize > rowSize) {
         	rowSize = tempRowSize;
         }
         if(rowSize < modelDataSize){//防止读取数据时数组越界
         	rowSize = modelDataSize;
         }
         String[] values = new String[rowSize];
         Arrays.fill(values, "");
         boolean hasValue = false;
         for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
         	String value = "";
         	cell = row.getCell(columnIndex);
         	
         	if (cell != null) {
         		// 注意：一定要设成这个，否则可能会出现乱码
         		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
         		switch (cell.getCellType()) {
         		case HSSFCell.CELL_TYPE_STRING:
         			 value = cell.getStringCellValue();
         			 break;
         		case HSSFCell.CELL_TYPE_NUMERIC:
         		     if (HSSFDateUtil.isCellDateFormatted(cell)) {
         			     Date date = cell.getDateCellValue();
         				 if (date != null) {
         					 value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
         				 } else {
         				     value = "";
         				 }
         			 } else {
         			     value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
         			     String[] valueTemp = value.split("\\.");
         			     if("00".equals(valueTemp[1])){
         			    	 value = valueTemp[0];
         			     }
         			 }
         			 break;
         		case HSSFCell.CELL_TYPE_FORMULA:
         			// 导入时如果为公式生成的数据则无值
         			 if (!cell.getStringCellValue().equals("")) {
         			 	 value = cell.getStringCellValue();
         			 } else {
         			     value = cell.getNumericCellValue() + "";
         			 }
         			 break;
         		case HSSFCell.CELL_TYPE_BLANK:
         			 break;
         		case HSSFCell.CELL_TYPE_ERROR:
         			 value = "";
         			 break;
         		case HSSFCell.CELL_TYPE_BOOLEAN:
         			 value = (cell.getBooleanCellValue() == true ? "Y": "N");
         			 break;
         		default:
         			 value = "";
         		}
         	}
         	//if (columnIndex == 0 && value.trim().equals("")) {
         		//break;
         	//}
         	values[columnIndex] = rightTrim(value);
         	hasValue = true;
         }
         if (hasValue) {
         	result.add(values);
         }
     }
   
     in.close();
     String[][] returnArray = new String[result.size()][rowSize];
     for (int i = 0; i < returnArray.length; i++) {
     	returnArray[i] = (String[]) result.get(i);
     }
     
     return returnArray;
	}

	/**
	* 去掉字符串右边的空格
	* @param str 要处理的字符串
	* @return 处理后的字符串
	*/

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
			}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
				}
			length--;
			}
		return str.substring(0, length);
			
	}
	
	/**
	 * 读取文本文件的记录信息
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String readData(String file) throws Exception {
		String temp = "";
		try {
			FileReader read = new FileReader(file);
			BufferedReader br = new BufferedReader(read);
			do {
				temp += br.readLine();
			} while (br.read() != -1);
			br.close();
		}catch (FileNotFoundException e) {
			return "系统找不到此文件！";
		} catch (Exception e) {
			return "";
		}
		return temp;
	}
	
	
	
	
	  /**
	    * 下载生成文件
	    * @param request
	    * @param response
	    * @param srcName
	    * @param targetName
	    */
		public static void download(HttpServletRequest request,HttpServletResponse response,String srcName,String targetName) {
			try{
				String root = request.getSession().getServletContext().getRealPath("/");
				File file = new File(root+"/"+TEMP_FOLDER+"/"+srcName);
				if(file.exists()){
					response.setContentType(contentType);
					response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(targetName.getBytes("gbk"),"ISO8859-1") + "\"");
					int length = (int)file.length();
					response.setContentLength(length);
					if(length>0){
						InputStream in = new FileInputStream(file);
						ServletOutputStream out = response.getOutputStream();
						byte[] buffer = new byte[4096];
						int readLength = 0 ;
						while((readLength=in.read(buffer))!=-1){
							out.write(buffer, 0, readLength);
						}
						in.close();
						out.flush();
						out.close();
					}
				}
				file.delete();
			}catch (Exception e) {
			}
		}
		
		/**   
	        *常用方法 
	 * 去掉数组中每一个元素的开头和结尾的引号 
	 * @param recArray 要处理的数组 
	 * @return 处理后的数组 
	 */  
	public static String[] minusQuotation(String[] recArray) {  
	      
	    for (int i = 0; i < recArray.length; i++) {  
	        String str = recArray[i];  
	        if (null!=str) {  
	            if(str.indexOf( "\"")==0)  
	                str = str.substring(1,str.length());//去掉开头的分号  
	            if(str.lastIndexOf("\"")==(str.length()-1))  
	                str = str.substring(0,str.length()-1); //去掉最后的分号  
	        }  
	        recArray[i] = str;  
	    }  
	    return recArray;  
	}  
	  
	  
   //按引号截取    
   public static String[] parseRecord(String record) {        
       //String[] recArray = record.split(",(?=\")|(?<=\"),");//按引号截取    
       String[] recArray = record.split("|");//按引号截取    
       //String[] recArray2 = record.    
       return recArray;    
   }    
   //按斜杠截取    
   public static String[] subRecord(String subrString){    
       String[] subArray=subrString.split("/");//按斜杠截取    
       return subArray;    
   }    
   //按逗号截取    
   public static String[] ssubRcord(String sstr){    
       String[] ssubArray=sstr.split(",");//按逗号截取    
       return ssubArray;    
           
   }   
   
   /** 
    * 批量添加航班信息  dao 
    * */  
   public boolean addFlightInfoList(ArrayList<String[]> aList) {  
       // TODO Auto-generated method stub  
	   boolean flag=true;  
      /* boolean flag=true;  
       //session开启  
       Session session=null;  
       //事物开启  
       Transaction transaction=null;  
         
       try {  
             
           session=HibernateSessionFactory.getSession();  
           transaction=session.beginTransaction();  
             
             
             
           for (int i = 0; i < aList.size(); i++) {  //循环遍历所有集合中的数组  
               System.out.println("aList.size()>>>>>>>>>>>>>>"+aList.size());  
               String[] arrayFlight=aList.get(i);   
               for (int j = 0; j < arrayFlight.length; j++) {  
                   System.out.println(arrayFlight[6]);  
                     
               }  
               System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");  
               //将集合中的值遍历存储在每个数组中  
               TbFlightInfo flightInfo=new TbFlightInfo();  
               for (int j = 0; j < arrayFlight.length; j++) {  //循环每个数组 取值  
                     
                   flightInfo.setDefault1(null);  
                   flightInfo.setDefault2(null);  
                   flightInfo.setDefault3(null);  
                   flightInfo.setDefault4(null);  
                   flightInfo.setDefault5(null);  
                   flightInfo.setFlightNo("CA1624");  
                   flightInfo.setGuideTime(null);  
                   Float float1=Float.parseFloat(arrayFlight[9].toString());  
                   Float float2=Float.parseFloat(arrayFlight[10].toString());  
                     
                   flightInfo.setInsurance(float2);  
                   flightInfo.setTicketAll(float1);  
                   flightInfo.setLeaveAddress(arrayFlight[7].toString());  
                   flightInfo.setTicketNo(arrayFlight[7].toString());  
                   flightInfo.setPassenger(arrayFlight[6].toString());  
                   flightInfo.setSauaCost(null);  
                   flightInfo.setTakeOffTime(null);  
                     
                   session.save(flightInfo);  
                     
               }  
                 
               //  判断 数据量到达该数据量时候刷新数据清空缓存 再次加快存储数据速度  
               if ((i+1)%100==0) {  
                   session.flush();  
                   session.clear();  
               }  
                 
           }  
           transaction.commit();  
       } catch (Exception e) {  
           // TODO: handle exception  
           e.printStackTrace();  
           flag=false;  
       }  */
       return flag;  
   }  

}
