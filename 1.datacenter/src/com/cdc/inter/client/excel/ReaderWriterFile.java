package com.cdc.inter.client.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.xml.sax.InputSource;

import com.cdc.inter.client.ws.manyd.client.SatisfactionProcessService;
import com.cdc.inter.client.ws.manyd.client.SatisfactionProcessServiceLocator;
import com.cdc.inter.client.ws.manyd.client.SatisfactionSurveySoapBindingStub;
import com.cdc.system.core.util.SpringHelper;

/**
 * 读写excel文件
 * @date 2016-6-7 下午4:40:10
 */
public class ReaderWriterFile {
	
		private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		//---------------------------------------------------------------------------------------------------
		/**
		 * 满意度，字段对应表（key:中文标题,value:对应数据库列）
		 */
		public static   Map<String, String> mapTitle= new LinkedHashMap<String, String>();
		
		/**
		 * 前台礼宾，字段对应表（key:中文标题,value:对应数据库列）
		 */
		public static   Map<String, String> mapTitle1= new LinkedHashMap<String, String>();
		
		/**
		 * 客房，字段对应表（key:中文标题,value:对应数据库列）
		 */
		public static   Map<String, String> mapTitle2= new LinkedHashMap<String, String>();		
		
		/**
		 * 餐饮，字段对应表（key:中文标题,value:对应数据库列）
		 */
		public static   Map<String, String> mapTitle3= new LinkedHashMap<String, String>();		
		
		/**
		 * 会议，字段对应表（key:中文标题,value:对应数据库列）
		 */
		public static   Map<String, String> mapTitle4= new LinkedHashMap<String, String>();
		
		/**
		 * 康乐，字段对应表（key:中文标题,value:对应数据库列）
		 */
		public static   Map<String, String> mapTitle5= new LinkedHashMap<String, String>();
		
		/**
		 * 安保字段对应表（key:中文标题,value:对应数据库列）
		 */
		public static   Map<String, String> mapTitle6= new LinkedHashMap<String, String>();
		
		//---------------------------------------------------------------------------------------------------
		
		/**
		 * key:对应列坐标，value:对应数据库列
		 */
		Map<String, String> mainMap = new LinkedHashMap<String, String>();
		
		/**
		 * key:对应列坐标，value:cell数据
		 */
		public static   Map<String, String> mapValue= new LinkedHashMap<String, String>();
		
		//---------------------------------------------------------------------------------------------------
		
		
		/**
		 * 统计维度数据库表（key:中文标题，(value:表名称&map key)，起始列，结束列）
		 */
		public static Map<String, String> tableMap = new LinkedHashMap<String, String>();
		
		/**
		 * 每个表对应的数据库字段
		 * key:1~6，value:Map(String,String)
		 */
		public static Map<String, Map<String, String>> columnMap = new LinkedHashMap<String, Map<String, String>>();
		
		/**
		 * 本次导入的excel，生成的所有插入sql语句
		 */
		private static List<String> allSqlList = new ArrayList<String>();
		
		private static String getColSql(String tableName){
			return "select column_name,comments from user_col_comments  where table_name='"+tableName.toUpperCase()+"' order by column_name asc";
		}
		
		static{
			List<Map<String, Object>> tableList = jdbcTemplate.queryForList(getColSql("manyd"));
			for (int i = 0; i < tableList.size(); i++) {
				Map<String, Object> map = tableList.get(i);
				mapTitle.put((String)map.get("comments"),((String)map.get("column_name")).toLowerCase());
			}
			
			tableList = jdbcTemplate.queryForList(getColSql("manyd_01"));
			for (int i = 0; i < tableList.size(); i++) {
				Map<String, Object> map = tableList.get(i);
				mapTitle1.put((String)map.get("comments"),((String)map.get("column_name")).toLowerCase());
			}
			
			tableList = jdbcTemplate.queryForList(getColSql("manyd_02"));
			for (int i = 0; i < tableList.size(); i++) {
				Map<String, Object> map = tableList.get(i);
				mapTitle2.put((String)map.get("comments"),((String)map.get("column_name")).toLowerCase());
			}
			
			tableList = jdbcTemplate.queryForList(getColSql("manyd_03"));
			for (int i = 0; i < tableList.size(); i++) {
				Map<String, Object> map = tableList.get(i);
				mapTitle3.put((String)map.get("comments"),((String)map.get("column_name")).toLowerCase());
			}
			
			tableList = jdbcTemplate.queryForList(getColSql("manyd_04"));
			for (int i = 0; i < tableList.size(); i++) {
				Map<String, Object> map = tableList.get(i);
				mapTitle4.put((String)map.get("comments"),((String)map.get("column_name")).toLowerCase());
			}
			
			tableList = jdbcTemplate.queryForList(getColSql("manyd_05"));
			for (int i = 0; i < tableList.size(); i++) {
				Map<String, Object> map = tableList.get(i);
				mapTitle5.put((String)map.get("comments"),((String)map.get("column_name")).toLowerCase());
			}
			
			tableList = jdbcTemplate.queryForList(getColSql("manyd_06"));
			for (int i = 0; i < tableList.size(); i++) {
				Map<String, Object> map = tableList.get(i);
				mapTitle6.put((String)map.get("comments"),((String)map.get("column_name")).toLowerCase());
			}
			
			tableMap.put("前台礼宾", "MANYD_01&1");
			tableMap.put("客房", "MANYD_02&2");
			tableMap.put("餐饮", "MANYD_03&3");
			tableMap.put("会议", "MANYD_04&4");
			tableMap.put("康乐", "MANYD_05&5");
			tableMap.put("安保", "MANYD_06&6");
			
			columnMap.put("1", mapTitle1);
			columnMap.put("2", mapTitle2);
			columnMap.put("3", mapTitle3);
			columnMap.put("4", mapTitle4);
			columnMap.put("5", mapTitle5);
			columnMap.put("6", mapTitle6);
		}
		
		
		public String isType(XSSFCell cell){
				String value = "";
				 switch (cell.getCellType()) { 
		             case HSSFCell.CELL_TYPE_NUMERIC: // 数字   
		                 	value = String.valueOf(cell.getRawValue()); 
		                 break;   
		             case HSSFCell.CELL_TYPE_STRING: // 字符串   
		             	value = cell.getStringCellValue().trim();   
		                 break;   
		             case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
		            	 value = ""; 
		                 break;   
		             case HSSFCell.CELL_TYPE_FORMULA: // 公式   
		            	 value = ""; 
		                 break;   
		             case HSSFCell.CELL_TYPE_BLANK: // 空值 
		            	 value = ""; 
		                 break;   
		             case HSSFCell.CELL_TYPE_ERROR: // 故障   
		            	 value = "读取故障"; 
		                 break;   
		             default:  
		            	 value = ""; 
		                 break;   
				 }
				 return value;
		}
		
		/**
		 * 动态导入excel数据
		 * 
		 * @date 2016-6-13 下午3:17:55
		 * @param filename
		 * @return	boolean
		 */
		public boolean readExcelFile(String filename) {
			String fileToBeRead = filename;
			boolean retValue = false;
			XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(new FileInputStream(fileToBeRead));
				XSSFSheet sheet = workbook.getSheetAt(0);
				int row_num = sheet.getLastRowNum();

				Integer startCell = 0;//第一行中文起始列
				boolean b = true;	//只能初始化一次标识，默认true
				String sql = "";
				String  id = "";//资源id
				
				for (int i = 0; i <= row_num; i++) {
					XSSFRow r = sheet.getRow(i);
					String cuurentCell = "";
					int cell_num = r.getLastCellNum();
					
					for (int j = 0; j < cell_num; j++) {
						//1.第一行标题
						if(i == 0){
							//计算每个组占用几列
							if(r.getCell(j).getCellType() == HSSFCell.CELL_TYPE_STRING){//列为字符型
								cuurentCell = r.getCell(j).getStringCellValue().trim();
								String str = tableMap.get(cuurentCell);
								tableMap.put(cuurentCell,str.split(",")[0]+","+j);//表名称，起始列
								if (b) {
									b = false;startCell = j;
								}
							}else {
								if (!cuurentCell.equals("")) {
									String str = tableMap.get(cuurentCell);
									tableMap.put(cuurentCell, str.split(",")[0]+","+str.split(",")[1]+","+j);//表名称，起始列，结束列
								}
							}
						}
						//2.第二行标题
						else if(i == 1){
							if (j < startCell) {
								String cellValue = r.getCell(j).getStringCellValue().trim();
								
								if (!mapTitle.containsKey(cellValue)) {
									//动态生成不存在列的字段名
									Integer column_XX = getI(mapTitle);//获取当前字段最后的column_XX
									mapTitle.put(cellValue, "column_"+(column_XX  + 1));
									System.out.println("2行，"+j+"列，不存在列:>"+cellValue);
									
									//组装插入新增字段脚本
									modifyTable("MANYD", mapTitle.get(cellValue), cellValue);
								}
								mainMap.put(j+",MANYD", mapTitle.get(cellValue));//key:对应列坐标,value:对应数据库列
							}else {
								Map<String, String> getS = getS(tableMap);
								//当前列是否是每个组的起始列
								if (getS.containsKey(""+j)) {
									cuurentCell = ""+j;			//当前组起始列标识
								}
								//当前组不为空，开始检查字段是否为新增
								if(!cuurentCell.equals("")){
									String cell =getS.get(cuurentCell);
									String tableName = cell.split(",")[0];
									String key = tableName.split("&")[1];
									Map<String, String> mapTitle = columnMap.get(key);
									String cellValue = r.getCell(j).getStringCellValue().trim();//中文标题
									if (!mapTitle.containsKey(cellValue)) {
										//动态生成不存在列的字段名
										Integer column_XX = getI(mapTitle);//获取当前字段最后的column_XX
										mapTitle.put(cellValue, "column_"+(column_XX  + 1));
										System.out.println("2行，"+j+"列，不存在列:>"+cellValue);
										//组装插入新增字段脚本
										modifyTable(tableName.split("&")[0], mapTitle.get(cellValue), cellValue);
									}
									mainMap.put(j+","+tableName.split("&")[0], mapTitle.get(cellValue));//key:对应列坐标,value:对应数据库列
								}
							}// end else
						//数据集
						}else{
								mapValue.put(""+j,  isType(r.getCell(j)));
						}
					}// end for
					
					if(i > 1){
						id = "IMP_"+System.currentTimeMillis();//资源id
						
						//每个表对应的所有字段
						Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
						List<String> sqlList = null;
						String tableName = "";
						
						for (Map.Entry<String, String> entry : mainMap.entrySet()) {
							//等于空 或者 不相等时赋新值
							if (tableName.equals("") || !tableName.equals(entry.getKey().split(",")[1])) {
								tableName = entry.getKey().split(",")[1];
								sqlList = new ArrayList<String>();
							}
							String index = entry.getKey().split(",")[0];
							sqlList.add(index  + "," + entry.getValue());//对应列坐标，对应数据库列
							map.put(tableName, sqlList);	
						}
						
						for (Entry<String, List<String>> entry : map.entrySet()) {
							tableName = entry.getKey();
							List<String> list = entry.getValue();
							String column = "",value = "";
							for (int c = 0; c < list.size(); c++) {
								String index = list.get(c).split(",")[0];//对应列坐标
								String columnName = list.get(c).split(",")[1];//数据库对应字段
								//1.属性
								column += (","+columnName);
								//2.值
								value += ",'"+mapValue.get(index)+"'";
							}
							
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							
							String c1 = (tableName.equals("MANYD") ? "create_date":"p_id");
							String v1 = (tableName.equals("MANYD") ? "'"+id+"','"+df.format(new Date())+"'":"SYS_GUID(),'"+id+"'");
							
							//动态生成insert sql
							sql = "insert into "+tableName+"	(id, "+c1+" "+column+") values ("+v1+value+") ";
							
							allSqlList.add(sql);
						}
					}// end if
				}
				
				//批量执行sql
				jdbcTemplate.batchUpdate(allSqlList.toArray(new String[allSqlList.size()]));
				
				//手工导入的没有日期字段，通过课程名称匹配‘项目清单’中该项目的结束日期。
				jdbcTemplate.update("update manyd t set t.end_date = (select distinct(y.column_04) from yuan_table_029 y where y.column_06 = t.name) where t.end_date is null");
				
				//执行成功
				retValue = true;
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				allSqlList.clear();
			}
			return retValue;

		}

		/**
		 * 动态生成数据库列字段，并生成列注释 sql
		 * 
		 * @date 2016-6-13 上午11:08:28
		 * @param tableName 数据库表名
		 * @param c 字段名
		 * @param v 中文注释
		 * @return	void
		 */
		private void modifyTable(String tableName,String c,String v){
				
			//1.生成字段
			String column = "alter table "+tableName+" add "+c+" VARCHAR2(32)";
			allSqlList.add(column);
			
			//2.生成注释
			String comment = "comment on column "+tableName+"."+c+" is '"+v+"'";
			allSqlList.add(comment);
			
		}
		
		/**
		 * 获取数据库字段
		 * 
		 * @date 2016-6-8 下午6:25:50
		 * @param map
		 * @return	Integer
		 */
		private Integer getI(Map<String, String> map){
			String value = "";
			for (Map.Entry<String, String> entry : map.entrySet()) {
			    if (entry.getValue().indexOf("column_") != -1) {
			    	value = entry.getValue().split("_")[1];
				}
			}
			return Integer.valueOf(value);
		}
		
		/**
		 * key：起始列；value：表名称,起始列,结束列
		 * 
		 * @date 2016-6-8 下午6:34:33
		 * @param map
		 * @return	Map<String, String>
		 */
		private Map<String, String> getS(Map<String, String> map){
			Map<String, String> mapS = new HashMap<String, String>();
			String value = "";
			for (Map.Entry<String, String> entry : map.entrySet()) {
			    	value = entry.getValue().split(",")[1];
			    	mapS.put(value, entry.getValue());
			}
			return mapS;
		}
		
		public boolean queryAll(){
			return readExcelFile("D:\\mmac_data\\a.xlsx");
		}
		
}
