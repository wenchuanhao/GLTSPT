package com.cdc.inter.client.ws.manyd;

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
public class ReaderWriterXml {
	
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
//			String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><item><head><node name=\"课程名称\">专家梦工场《FC-引导式顾问》培训</node><node name=\"日期\">2017-01-13 00:00:00.0</node><node name=\"问卷数量\">22</node><node name=\"序号\">1</node><node name=\"宾客姓名\">陈东翔</node><node name=\"房号\">1201</node><node name=\"客房类型\"></node></head><body><node name=\"服务项目和设施设备\" parent=\"康乐\">10</node><node name=\"卫生质量（毛巾、场地和设备清洁度）\" parent=\"康乐\">10</node><node name=\"服务品质（服务热情主动及时、服务过程规范）\" parent=\"康乐\">10</node><node name=\"服务主动、语言规范、微笑答疑\" parent=\"安保\">10</node><node name=\"车辆指引及班车接送服务\" parent=\"安保\">10</node><node name=\"电话咨询及电瓶车调度服务\" parent=\"前台礼宾\">10</node><node name=\"微笑服务、微笑答疑\" parent=\"前台礼宾\">10</node><node name=\"空调及房间温度\" parent=\"客房\">10</node><node name=\"灯光及房间设备\" parent=\"客房\">10</node><node name=\"客房清洁度（卧具、沙发、地毯、日常清洁迅速及时）\" parent=\"客房\">10</node><node name=\"浴室用品（毛巾、洗护用品、洗浴设备、水温）\" parent=\"客房\">10</node><node name=\"客房异味\" parent=\"客房\">10</node><node name=\"客房网络质量\" parent=\"客房\">10</node><node name=\"服务品质（服务热情主动及时、服务过程规范）\" parent=\"客房\">10</node><node name=\"用餐环境（场地、餐具卫生洁净）\" parent=\"餐饮\">10</node><node name=\"食品品质（可选性、口味、质量）\" parent=\"餐饮\">10</node><node name=\"服务品质（微笑热情、主动及时）\" parent=\"餐饮\">10</node><node name=\"会议室温度\" parent=\"会议\">10</node><node name=\"灯光及设施设备（投影、唛、音响等）\" parent=\"会议\">10</node><node name=\"网络质量 \" parent=\"会议\">10</node><node name=\"茶歇质量（可选性、口味）\" parent=\"会议\">10</node><node name=\"服务品质（服务热情主动及时、服务过程规范）\" parent=\"会议\">10</node></body></item></root>";
//			String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><item><head><node name=\"课程名称\">专家梦工场《FC-引导式顾问》培训</node><node name=\"日期\">2017-01-14</node><node name=\"问卷数量\">22</node><node name=\"序号\">1</node><node name=\"宾客姓名\">陈东翔</node><node name=\"房号\">1201</node><node name=\"客房类型\"></node></head><body><node name=\"服务项目和设施设备\" parent=\"康乐\">11</node><node name=\"卫生质量（毛巾、场地和设备清洁度）\" parent=\"康乐\">12</node><node name=\"服务品质（服务热情主动及时、服务过程规范）\" parent=\"康乐\">13</node><node name=\"服务主动、语言规范、微笑答疑\" parent=\"安保\">21</node><node name=\"车辆指引及班车接送服务\" parent=\"安保\">22</node><node name=\"电话咨询及电瓶车调度服务\" parent=\"前台礼宾\">31</node><node name=\"微笑服务、微笑答疑\" parent=\"前台礼宾\">32</node><node name=\"空调及房间温度\" parent=\"客房\">41</node><node name=\"灯光及房间设备\" parent=\"客房\">42</node><node name=\"客房清洁度（卧具、沙发、地毯、日常清洁迅速及时）\" parent=\"客房\">43</node><node name=\"浴室用品（毛巾、洗护用品、洗浴设备、水温）\" parent=\"客房\">44</node><node name=\"客房异味\" parent=\"客房\">45</node><node name=\"客房网络质量\" parent=\"客房\">46</node><node name=\"服务品质（服务热情主动及时、服务过程规范）\" parent=\"客房\">47</node><node name=\"用餐环境（场地、餐具卫生洁净）\" parent=\"餐饮\">51</node><node name=\"食品品质（可选性、口味、质量）\" parent=\"餐饮\">52</node><node name=\"服务品质（微笑热情、主动及时）\" parent=\"餐饮\">53</node><node name=\"会议室温度\" parent=\"会议\">61</node><node name=\"灯光及设施设备（投影、唛、音响等）\" parent=\"会议\">62</node><node name=\"网络质量 \" parent=\"会议\">63</node><node name=\"茶歇质量（可选性、口味）\" parent=\"会议\">64</node><node name=\"服务品质（服务热情主动及时、服务过程规范）\" parent=\"会议\">65</node></body></item></root>";
			String xmlString = "";
			try{
				
				SatisfactionProcessService service = new SatisfactionProcessServiceLocator();
				SatisfactionSurveySoapBindingStub client = (SatisfactionSurveySoapBindingStub) service.getsatisfactionSurvey();
				com.cdc.inter.client.ws.manyd.client.SatisfactionRequest request = new com.cdc.inter.client.ws.manyd.client.SatisfactionRequest();
				request.setBeginTime("2016-01-01");
				request.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				com.cdc.inter.client.ws.manyd.client.SatisfactionResponse response = client.process(request );
				xmlString = response.getResultData();		
			} catch (org.apache.axis.AxisFault e) {
				e.printStackTrace();
			}catch (RemoteException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			return readXmlString(xmlString);
		}
		
		
		public boolean readXmlString(String xmlString) {
			
			boolean retValue = false;
			// 创建一个新的字符串
			StringReader read = new StringReader(xmlString);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder saxbBuilder = new SAXBuilder();
			try {
				
				// 通过输入源构造一个Document
				Document doc = saxbBuilder.build(source);
				// 取root元素
				Element root = doc.getRootElement();
				
				//获取item数据
				List<?> item = root.getChildren();
				
				List<List<Element>> excel = new ArrayList<List<Element>>();
				for (int i = 0; i < item.size(); i++) {
					Element element = (Element) item.get(i);
					//获取head body 数据
					List<?> subNode = element.getChildren();
					//保存每组item中的head body
					List<Element> node = new ArrayList<Element>();
					for (int j = 0; j < subNode.size(); j++) {
						Element subElement = (Element) subNode.get(j);
						if("head".equals(subElement.getName())){
							node.addAll(subElement.getChildren());
						}
						if("body".equals(subElement.getName())){
							node.addAll(subElement.getChildren());
						}
					}
					excel.add(node);
				}
				
				
				int row_num = excel.size();

				Integer startCell = 0;//第一行中文起始列
				boolean b = true;	//只能初始化一次标识，默认true
				boolean first = true;
				String sql = "";
				String  id = "";//资源id
				for (int i = 0; i < row_num; i++) {
					List<Element> r = excel.get(i);
					String cuurentCell = "";
					String prevCell = "";
					int cell_num = r.size();
					
					//1.第一行标题
					for (int j = 0; j < cell_num; j++) {
						//遍历node
						Element nodeElement = (Element) r.get(j);
						Element prevEle = null;
						if(i == 0){
							//计算每个组占用几列
							cuurentCell = nodeElement.getAttributeValue("parent");
							if(StringUtils.isNotEmpty(cuurentCell)){//列为字符型
								cuurentCell = cuurentCell.trim();
								//取上一个类型
								if(j > 0 ){
									prevEle = (Element) r.get(j - 1);
									prevCell = prevEle.getAttributeValue("parent");
								}
								String strCuurent = tableMap.get(cuurentCell);
								String[] strsCuurent = strCuurent.split(",");
								//与上一类型名称不同 或是 最后一条数据  结束列
								if (StringUtils.isNotEmpty(prevCell) && (!cuurentCell.equals(prevCell) ||  j == (cell_num - 1))) {
									String str = tableMap.get(prevCell);
									String[] strs = str.split(",");
									if(j == (cell_num - 1)){
										tableMap.put(prevCell, strs[0]+","+strs[1]+","+(j));//表名称，起始列，结束列
									}else{
										tableMap.put(prevCell, strs[0]+","+strs[1]+","+(j-1));//表名称，起始列，结束列
									}
									first  = true;
								}
								//起始列
								if(first && j != (cell_num - 1)){
									tableMap.put(cuurentCell,strsCuurent[0]+","+j);//表名称，起始列
									first = false;
								}
								if (b) {
									b = false;startCell = j;
								}
							}
						}
						
					}//end for
					//2.第二行标题
					for (int j = 0; j < cell_num; j++) {
						//遍历node
						Element nodeElement = (Element) r.get(j);
						Element prevEle = null;
						if(i == 0){

							if (j < startCell) {
								String cellValue = nodeElement.getAttributeValue("name").trim();
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
									String cellValue = nodeElement.getAttributeValue("name").trim();//中文标题
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
						
						}
						
					}//end for
					
				}

				//重新遍历并形成sql语句
				for (int i = 0; i < row_num; i++) {
					List<Element> r = excel.get(i);
					int cell_num = r.size();
					for (int j = 0; j < cell_num; j++) {
						//遍历node
						Element nodeElement = (Element) r.get(j);
						mapValue.put(""+j,  nodeElement.getValue());
					}
					//赋值
					
					id = "XML_"+System.currentTimeMillis();//资源id
					
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
							if("end_date".equalsIgnoreCase(columnName)){
								value += ",'"+mapValue.get(index).substring(0,10)+"'";
							}else{
								value += ",'"+mapValue.get(index)+"'";
							}
						}
						
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
						String c1 = (tableName.equals("MANYD") ? "create_date":"p_id");
						String v1 = (tableName.equals("MANYD") ? "'"+id+"','"+df.format(new Date())+"'":"SYS_GUID(),'"+id+"'");
						
						//动态生成insert sql
						sql = "insert into "+tableName+"	(id, "+c1+" "+column+") values ("+v1+value+") ";
						
						allSqlList.add(sql);
					}
				
				}
				//清空表数据
				jdbcTemplate.execute("delete from MANYD t where t.id like 'XML_%'");
				jdbcTemplate.execute("delete from MANYD_01 t where t.p_id like 'XML_%'");
				jdbcTemplate.execute("delete from MANYD_02 t where t.p_id like 'XML_%'");
				jdbcTemplate.execute("delete from MANYD_03 t where t.p_id like 'XML_%'");
				jdbcTemplate.execute("delete from MANYD_04 t where t.p_id like 'XML_%'");
				jdbcTemplate.execute("delete from MANYD_05 t where t.p_id like 'XML_%'");
				jdbcTemplate.execute("delete from MANYD_06 t where t.p_id like 'XML_%'");
				//批量执行sql
				jdbcTemplate.batchUpdate(allSqlList.toArray(new String[allSqlList.size()]));
				
				//手工导入的没有日期字段，通过课程名称匹配‘项目清单’中该项目的结束日期。
				jdbcTemplate.update("update manyd t set t.end_date = (select distinct(y.column_04) from yuan_table_029 y where y.column_06 = t.name) where t.end_date is null");
				
				//执行成功
				retValue  = true;
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				allSqlList.clear();
			}
			return retValue;
		}
}
