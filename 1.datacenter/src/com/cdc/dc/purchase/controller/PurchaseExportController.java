package com.cdc.dc.purchase.controller;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.dc.purchase.form.PurchaseForm;
import com.cdc.dc.purchase.model.PurchaseRole;
import com.cdc.dc.purchase.service.IPurchaseExpService;
import com.cdc.dc.purchase.service.IPurchaseService;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;
import com.util.ExcelUtil;

/**
 * 采购项目导出
 * @author ywc
 *
 */
@Controller
@RequestMapping(value = "/purchaseExp/")
public class PurchaseExportController extends CommonController{
	@Autowired
	private IEnterpriseService enterpriseService;
	
    private Log logger = LogFactory.getLog(getClass());
    
	@Autowired
	private ISysOrganizationService organizationService;
	@Autowired
    private IPurchaseExpService iPurchaseExpService;
	
	@Autowired
	private IRulesService rulesService;
	@Autowired
    private IPurchaseService iPurchaseService;	
	
	 public void checkPurchaseRole(HttpServletRequest request,PurchaseForm purchaseForm) throws Exception{
	    	SysUser sysUser = getVisitor(request);
	    	String userName = sysUser.getUserName();
			//系统管理员PURCHASE_ADMIN TODO
			//采购量领导PURCHASE_LEADER
			//报表只有领导和管理员可看
			SysRole sysAdmin = rulesService.querySysRoleByCode("PURCHASE_ADMIN");
			SysRoleUser purchaseAdmin = rulesService.getRoleUsersByUserId(sysAdmin.getRoleId(),sysUser.getUserId());
			SysRole  sysLeader = rulesService.querySysRoleByCode("PURCHASE_LEADER");
			SysRoleUser purchaseLeader = rulesService.getRoleUsersByUserId(sysLeader.getRoleId(),sysUser.getUserId());
			SysRole  sysNormal = rulesService.querySysRoleByCode("PURCHASE_NORMAL");
			SysRoleUser purchaseNormal = rulesService.getRoleUsersByUserId(sysNormal.getRoleId(),sysUser.getUserId());

			//需求部门查询下拉
			List<SysOrganization> list = new ArrayList<SysOrganization>();
			
			
			//经办人
			if(null != purchaseNormal && null == purchaseAdmin){
				purchaseForm.setColumnB(userName);
				//经办人一级部门
				list = iPurchaseService.queryAllColumnC(sysUser.getUserName());
			}
			//领导角色
			if(null != purchaseLeader){
				
				list = iPurchaseService.queryAllColumnC(sysUser.getUserName());
				
				List<PurchaseRole> purchaseRoleList = iPurchaseService.queryPurchaseRoleByAcount(sysUser.getUserId());
				StringBuffer roleSB = new StringBuffer();
				if(null != purchaseRoleList && purchaseRoleList.size()>0 ){
					//只会有一条记录
					PurchaseRole purchaseRole = purchaseRoleList.get(0);
					String[] orgIds = purchaseRole.getManageApartId().split(",");
					//拼接分管部门
					if(null != orgIds && orgIds.length>0){
						for(int i=0;i<orgIds.length;i++)
			    		{  
			    			List<SysOrganization> sys = organizationService.getOrgAndSonById(orgIds[i]);
			    			if(StringUtils.isNotEmpty(orgIds[i])){
			    				//领导的一级部门
			    				SysOrganization e = (SysOrganization) enterpriseService.getById(SysOrganization.class, orgIds[i]);
			    				boolean flag = true;
			    				for (int j = 0; j < list.size(); j++) {
									if(list.get(j).getOrgName().equals(e.getOrgName())){
										flag = false;
										break;
									}
								}
			    				if(flag){
			    					list.add(e);
			    				}
			    			}
			    			for(int j=0;j<sys.size();j++){
			    				SysOrganization u = sys.get(j);
			    				roleSB.append("'").append(u.getOrgName()).append("',");
			    			}
		    			
			    		}
			    		//去掉最后一个逗号
		    			roleSB.deleteCharAt(roleSB.length()-1);
					}

				}
				purchaseForm.setManageAparts(roleSB.toString());
				
			}
			//管理员
			if(null != purchaseAdmin){
				purchaseForm.setManageAparts(null);
				request.setAttribute("isAdmin", "true" );
				//所有一级部门
				list = iPurchaseService.queryAllColumnC(null);
			}
			//无关人员
			if(null == purchaseNormal && null == purchaseLeader && null == purchaseAdmin){
				purchaseForm.setManageAparts("'-'");
				list = iPurchaseService.queryAllColumnC(sysUser.getUserName());
			}
			request.setAttribute("depList", list);
	    }
    
	/**
	 * 南方基地项目采购备案表
	 * @param request
	 * @param response
	 * @param purchaseForm
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "purchaseRecQuery", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseRecQuery(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
        //获取数据  
       checkPurchaseRole(request, purchaseForm);
       List<Map<String, Object>> rows= iPurchaseExpService.queryRecPurchase(purchaseForm); 
       int length = rows.size();
   	   //开始索引
		int beginIndex = (purchaseForm.getPageIndex() - 1) * purchaseForm.getPageSize();
		//结束索引
		int endIndex = purchaseForm.getPageIndex() * purchaseForm.getPageSize() > length ? length : purchaseForm.getPageIndex() * purchaseForm.getPageSize();
   	
		List pageList = new ArrayList(purchaseForm.getPageSize());
		
		for (int i = beginIndex; i < endIndex; i++) {
			pageList.add(rows.get(i));
		}
		//生成分页结果
		ItemPage itemPage = new ItemPage(pageList, length, purchaseForm.getPageIndex(),purchaseForm.getPageSize());
     	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "dc/project/purchase/purchaseRecExp";   	
    } 
    
    
    
    /**
     * 南方基地项目采购备案表-导出
     * @param request
     * @param response
     * @param purchaseForm
     * @throws Exception
     */
    @RequestMapping(value = "exportRecFile", method = {RequestMethod.GET,RequestMethod.POST})
    public void purchaseRecExp(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
        //获取数据  
    	checkPurchaseRole(request, purchaseForm);
    	response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");
		HSSFWorkbook book = new HSSFWorkbook();
		String[] header = {"序号","单位","月份","采购项目名称","经办人","采购组织实施单位\r\n/部门（集采类型）","采购组织实施单位/部门","需求单位","需求部门","采购类型","采购内容","开支类型","自行/委托采购",
				"采购方式(首次)","采购方式(最后一次)","项目当前实际状态","操作方式","电子采购项目编号","ES系统中的采购项目名称","采购方案决策层级（决策形式）","采购方案预估金额（万元）","项目启动时间",
				"采购模式","地市采购方案决策时间(首次)","地市采购方案决策时间(最后一次)","地市采购方案纪要下达时间","地市采购方案发文文号","省公司采购方案决策时间(首次)","省公司采购方案决策时间(最后一次)",
				"省公司采购方案纪要/启动通知下达时间","省公司采购方案/启动通知发文文号","采购结果确认层级（确认形式）","采购结果金额（万元）","地市公司采购结果确认时间","地市采购结果上报时间",
				"省公司采购结果确认时间","合同签署时间","采购结果发文文号","单一来源适用场景（大类）","单一来源适用场景（小类）","单一来源适用场景（具体产品服务备注"};
		//表名
        String fileName = "南方基地项目采购备案表";
        //数据
        List<Object[]> listO =  new ArrayList<Object[]>();
        String ids = request.getParameter("ids");
        purchaseForm.setIds(ids);
        List<Map<String, Object>> rows= iPurchaseExpService.queryRecPurchase(purchaseForm); 
        
        for(int i=0;i<rows.size();i++)
        {
        	Map<String, Object> row = rows.get(i);
        	Object [] obj =  new Object[41];
        	obj[0]=row.get("ROWNUM").toString();
        	obj[1]=row.get("UNITS");
        	obj[2]=row.get("FLOWMONTH");
        	obj[3]=row.get("COLUMN_A");
        	obj[4]=row.get("COLUMN_B");
        	obj[5]=row.get("COLUMN_AC");
        	obj[6]=row.get("COLUMN_AD");
        	obj[7]=row.get("COLUMN_AE");
        	obj[8]=row.get("COLUMN_C");
        	obj[9]=row.get("COLUMN_AF");
        	obj[10]=row.get("COLUMN_AG");
        	obj[11]=row.get("COLUMN_F");
        	obj[12]=row.get("COLUMN_G");
        	obj[13]=row.get("COLUMN_I");
        	obj[14]=row.get("COLUMN_J");
        	obj[15]=row.get("COLUMN_AH");
        	obj[16]=row.get("COLUMN_AI");
        	obj[17]=row.get("COLUMN_AJ");
        	obj[18]=row.get("COLUMN_AK");
        	obj[19]=row.get("COLUMN_AL");
        	obj[20]=row.get("COLUMN_N");
        	obj[21]=row.get("COLUMN_O");
        	obj[22]=row.get("COLUMN_AM");
        	obj[23]=row.get("COLUMN_R");
        	obj[24]=row.get("COLUMN_S");
        	obj[25]=row.get("COLUMN_T");
        	obj[26]=row.get("COLUMN_U");
        	obj[27]=row.get("COLUMN_V");
        	obj[28]=row.get("COLUMN_W");
        	obj[29]=row.get("COLUMN_X");
        	obj[30]=row.get("COLUMN_Y");
        	obj[31]=row.get("COLUMN_AN");
        	obj[32]=row.get("COLUMN_AO");
        	obj[33]=row.get("COLUMN_AP");
        	obj[34]=row.get("COLUMN_AQ");
        	obj[35]=row.get("COLUMN_AR");
        	obj[36]=row.get("COLUMN_AS");
        	obj[37]=row.get("COLUMN_AT");
        	obj[38]=row.get("COLUMN_BB");
        	obj[39]=row.get("COLUMN_BC");
        	obj[40]=row.get("COLUMN_BD"); 
        	listO.add(obj);
        }
        
    	ExcelUtil.exportForExcel(header, fileName, listO, book);
    	response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
		response.setContentType("application/x-download");
		response.setContentType("application/vnd.ms-excel");
		book.write(response.getOutputStream());   	
    } 
    
    /**
     * 南方基地项目时段采购统计查询
     * @param request
     * @param response
     * @param purchaseForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "purchaseMonQuery", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseMonQuery(HttpServletRequest request, HttpServletResponse response,String beginCreateTime) throws Exception{
    	 PurchaseForm purchaseForm = new PurchaseForm();
    	 checkPurchaseRole(request, purchaseForm);
    	//获取要查的是哪一个月份(前台需要展示)
    	//如果查询条件存在月份则取查询条件月份 否则取当前时间
    	String month ="";
    	if(beginCreateTime != null && beginCreateTime != ""){
    		month = beginCreateTime.substring(5,7);
    	}
    	else
    	{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		month = sdf.format(new Date()).substring(5,7);		
    	}
    	request.setAttribute("month",month);
    	request.setAttribute("time",beginCreateTime);
    	
    	
        //数据
        List<Map<String, Object>> rows= iPurchaseExpService.queryMonPurchase(beginCreateTime, purchaseForm); 
         request.setAttribute("crrentMonth",rows);
		return "dc/project/purchase/purchaseMonExp";   	
    }
    
    /**
     * 南方基地项目月份采购统计汇总表导出
     * @param request
     * @param response
     * @param purchaseForm
     * @throws Exception
     */
    @RequestMapping(value = "exportMonFile", method = {RequestMethod.GET,RequestMethod.POST})
    public void purchaseMonExp(HttpServletRequest request, HttpServletResponse response,String beginCreateTime) throws Exception{
    	PurchaseForm purchaseForm = new PurchaseForm();
    	checkPurchaseRole(request, purchaseForm);
    	response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");	
		//表名
		String fileName = "南方基地项目月份采购统计汇总表";
        //数据
        List<Object[]> listO =  new ArrayList<Object[]>();
        List<Map<String, Object>> rows= iPurchaseExpService.queryMonPurchase(beginCreateTime,purchaseForm); 
        for(int i=0;i<rows.size();i++)
        {
        	Map<String, Object> row = rows.get(i);
        	Object [] obj =  new Object[9];
        	obj[0]="heji".equals(row.get("TYPE").toString())?"合计":row.get("TYPE").toString();
        	obj[1]=row.get("C1");
        	obj[2]=row.get("C2");
        	obj[3]=row.get("C3");
        	obj[4]=row.get("C4");
        	obj[5]=row.get("C5");
        	obj[6]=row.get("C6");
        	obj[7]=row.get("C7");
        	obj[8]=row.get("C8");
        	listO.add(obj);
        }
        HSSFWorkbook book = getMonBook(fileName,listO,beginCreateTime);
    	response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
		response.setContentType("application/x-download");
		response.setContentType("application/vnd.ms-excel");
		book.write(response.getOutputStream());   	
    }
    
    /**
     * 表格制作
     * @param list
     * @return
     */
    private HSSFWorkbook getMonBook (String fileName, List<Object[]> list,String beginCreateTime) { 
    	String month ="";
    	if(beginCreateTime != null && beginCreateTime != ""){
    		month = beginCreateTime.substring(5,7);
    	}
    	else
    	{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		month = sdf.format(new Date()).substring(5,7);		
    	}
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
		HSSFSheet sheet = book.createSheet(fileName);
		//两行表头
		String[] header1 = {"采购方式",month+"月","年度累计（1-"+month+"月）"};
		
		HSSFRow row = sheet.createRow(0);
		
		for (int s = 0; s < header1.length; s++) {
			if(s==0){
				sheet.addMergedRegion(new CellRangeAddress(0,1, 0, 0));
	    		HSSFCell cell = row.createCell(s);
	    		cell.setCellValue(header1[s].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);
			}
			if(s==1){
			    sheet.addMergedRegion(new CellRangeAddress(0,0, 1, 4));
	    		HSSFCell cell = row.createCell(s);
	    		cell.setCellValue(header1[s].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);
			}
			if(s==2){
				 sheet.addMergedRegion(new CellRangeAddress(0,0, 5, 9));
		    		HSSFCell cell = row.createCell(5);
		    		cell.setCellValue(header1[s].toString());
		    		cell.setCellStyle(style);
		    		sheet.setColumnWidth(s, 4000);
				}
    	}
		
		String[] header2 = {"项目数量(个)","项目量占比","决策总额(万元)","决策额占比","项目数量(个)","项目量占比","决策总额(万元)","决策额占比"};
		HSSFRow row1 = sheet.createRow(1);		
		for (int s = 1; s <= header2.length; s++) {
		
	    		HSSFCell cell = row1.createCell(s);
	    		cell.setCellValue(header2[s-1].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);			
    	}
		//内容样式
    	HSSFCellStyle style2 = book.createCellStyle();
    	HSSFFont font2 = book.createFont();
    	style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style2.setFont(font2);
    	//数据内容  前两行已经是表头
    	for (int i = 2; i < list.size()+2; i++) {
    		HSSFRow row3 = sheet.createRow(i);
    		Object[] objs = (Object[]) list.get(i-2);
    		HSSFCell cell =null;
    		for (int k = 0; k < objs.length; k++) {
    			cell = row3.createCell(k);
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
					cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(value));
				}else{
					cell.setCellValue(value.toString());
				}
    		}
    	}		
		
		return book;  	
	}
    
    /**
     * 南方基地项目时段采购统计汇总表
     * @param request
     * @param response
     * @param beginCreateTime
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "purchasePerQuery", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchasePerQuery(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	checkPurchaseRole(request, purchaseForm);
        //数据
        List<Map<String, Object>> rows= iPurchaseExpService.queryPerPurchase(purchaseForm); 
        request.setAttribute("perData",rows);
        //都为空 给默认值为当天
        String btime ="";
        String etime ="";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
        	 btime = sdf.format(new Date());
        	 etime =  sdf.format(new Date());
        	 purchaseForm.setBeginCreateTime(sdf.parse(btime));
        	 purchaseForm.setEndCreateTime(sdf.parse(etime));
        	 
        }else{
	      	  if(purchaseForm.getBeginCreateTime() != null){
	    		  btime = sdf.format(purchaseForm.getBeginCreateTime());
	    	  }
	    	  if(purchaseForm.getEndCreateTime() != null){
	    		  etime = sdf.format(purchaseForm.getEndCreateTime());      	
	    	  }
	      }
        request.setAttribute("btime",btime);
        request.setAttribute("etime",etime);
		return "dc/project/purchase/purchasePerExp"; 
		
    }
    
    /**
     * 南方基地项目时段采购统计汇总表
     * @param request
     * @param response
     * @param purchaseForm
     * @throws Exception
     */
    @RequestMapping(value = "exportPerFile", method = {RequestMethod.GET,RequestMethod.POST})
    public void purchasePerExp(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	checkPurchaseRole(request, purchaseForm);
    	response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");	
		//表名
		String fileName = "南方基地项目时段采购统计汇总表";
        //数据
        List<Object[]> listO =  new ArrayList<Object[]>();
        List<Map<String, Object>> rows= iPurchaseExpService.queryPerPurchase(purchaseForm);
        for(int i=0;i<rows.size();i++)
        {
        	Map<String, Object> row = rows.get(i);
        	Object [] obj =  new Object[5];
        	obj[0]="heji".equals(row.get("TYPE").toString())?"合计":row.get("TYPE").toString();
        	obj[1]=row.get("C1");
        	obj[2]=row.get("C2");
        	obj[3]=row.get("C3");
        	obj[4]=row.get("C4");
        	listO.add(obj);
        }
        HSSFWorkbook book = getPerBook(fileName,listO,purchaseForm);
    	response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
		response.setContentType("application/x-download");
		response.setContentType("application/vnd.ms-excel");
		book.write(response.getOutputStream());   	
    }
    /**
     * 
     * @param fileName
     * @param list
     * @param beginCreateTime
     * @return
     * @throws ParseException 
     */
    private HSSFWorkbook getPerBook (String fileName, List<Object[]> list,PurchaseForm purchaseForm) throws ParseException { 
    	 //获取数据导出的时间段
        String btime ="";
        String etime ="";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){
        	 btime = sdf.format(new Date());
        	 etime =  sdf.format(new Date());
        	 purchaseForm.setBeginCreateTime(sdf.parse(btime));
        	 purchaseForm.setEndCreateTime(sdf.parse(etime));
        	 
        }else if(purchaseForm.getBeginCreateTime() !=null && purchaseForm.getEndCreateTime()==null){
        	btime = sdf.format(purchaseForm.getBeginCreateTime());
	       	 etime =  sdf.format(new Date());
	       	 purchaseForm.setEndCreateTime(sdf.parse(etime));
	       	 
	       }else if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()!=null){
	    	   etime = sdf.format(purchaseForm.getEndCreateTime());  
	      	 btime = sdf.format(new Date());
	      	 purchaseForm.setBeginCreateTime(sdf.parse(btime));
	      	 
	      }else {
        	btime = sdf.format(purchaseForm.getBeginCreateTime());
       	    etime = sdf.format(purchaseForm.getEndCreateTime());        	
        }
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
		HSSFSheet sheet = book.createSheet(fileName);
		//两行表头
		String[] header1 = {"采购方式",btime+"至"+etime};
		
		HSSFRow row = sheet.createRow(0);
		
		for (int s = 0; s < header1.length; s++) {
			if(s==0){
				sheet.addMergedRegion(new CellRangeAddress(0,1, 0, 0));
	    		HSSFCell cell = row.createCell(s);
	    		cell.setCellValue(header1[s].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);
			}
			if(s==1){
			    sheet.addMergedRegion(new CellRangeAddress(0,0, 1, 4));
	    		HSSFCell cell = row.createCell(s);
	    		cell.setCellValue(header1[s].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);
			}
    	}
		
		String[] header2 = {"项目数量(个)","项目量占比","决策总额(万元)","决策额占比"};
		HSSFRow row1 = sheet.createRow(1);		
		for (int s = 1; s <= header2.length; s++) {
		
	    		HSSFCell cell = row1.createCell(s);
	    		cell.setCellValue(header2[s-1].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);			
    	}
		//内容样式
    	HSSFCellStyle style2 = book.createCellStyle();
    	HSSFFont font2 = book.createFont();
    	style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style2.setFont(font2);
    	//数据内容  前两行已经是表头
    	for (int i = 2; i < list.size()+2; i++) {
    		HSSFRow row3 = sheet.createRow(i);
    		Object[] objs = (Object[]) list.get(i-2);
    		HSSFCell cell =null;
    		for (int k = 0; k < objs.length; k++) {
    			cell = row3.createCell(k);
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
					cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(value));
				}else{
					cell.setCellValue(value.toString());
				}
    		}
    	}		
		
		return book;  	
	}
    
    /**
     * 采购全景监控总表-查询
     * @param request
     * @param response
     * @param purchaseForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "purchaseAllQuery", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseAllQuery(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	checkPurchaseRole(request, purchaseForm);
    	
    	iPurchaseExpService.purchaseAll(request, purchaseForm);
    	
		return "dc/project/purchase/purchaseControlAll"; 
		
    }
    
    
    /**
     * 获取表格图形数据
     * @param request
     * @param response
     * @param purchaseForm
     * @throws Exception
     */
    
    @RequestMapping(value = "getChartData", method = {RequestMethod.GET,RequestMethod.POST})
    public void getChartData(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	String type = request.getParameter("searchType");   	
    	//校验角色
    	checkPurchaseRole(request, purchaseForm);
    	//TODO
        //tqc情况数据数据
    	JSONArray result = null;
    	List<Object[]> list = iPurchaseExpService.getChartData(type,purchaseForm);
        
        result = JSONArray.fromObject(list);          
        PrintWriter out = response.getWriter();
		if(result != null){
			out.write(result.toString());
		}else{
			out.write("[]");
		}
		
    }
    
    /**
     * 柱状图
     * @param request
     * @param response
     * @param purchaseForm
     * @throws Exception
     */
    @RequestMapping(value = "getColumnData", method = {RequestMethod.GET,RequestMethod.POST})
    public void getColumnData(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	String type = request.getParameter("searchType");
    	//校验角色
    	checkPurchaseRole(request, purchaseForm);
    	//TODO
        //tqc情况数据数据
    	JSONArray result = null;
    	List<Object[]> list = iPurchaseExpService.getColumnData(type,purchaseForm);
                
        result = JSONArray.fromObject(list);          
        PrintWriter out = response.getWriter();
		if(result != null){
			out.write(result.toString());
		}else{
			out.write("[]");
		}
		
    }
    
    /**
     * 总表-导出
     * @param request
     * @param response
     * @param purchaseForm
     * @throws Exception
     */
    @RequestMapping(value = "exportAllFile", method = {RequestMethod.GET,RequestMethod.POST})
    public void purchaseAllExp(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	checkPurchaseRole(request, purchaseForm);
    	response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");	
		//表名
		String fileName = "采购项目全景监控总表";
        //数据
        List<Map<String, Object>> tqcRows = iPurchaseExpService.queryTqcPurchase(purchaseForm);
        List<Map<String, Object>> conRows = iPurchaseExpService.queryConTimePurchase(purchaseForm);
        List<Map<String, Object>> revRows = iPurchaseExpService.queryRevTimePurchase(purchaseForm);
        List<Map<String, Object>> apprRows = iPurchaseExpService.queryApprTimePurchase(purchaseForm);
        List<Map<String, Object>> temRows = iPurchaseExpService.queryTemTimePurchase(purchaseForm);
        List<Map<String, Object>> bidRows = iPurchaseExpService.queryBidTimePurchase(purchaseForm);
        List<Map<String, Object>> onlRows = iPurchaseExpService.queryOnlTimePurchase(purchaseForm);
        List<Map<String, Object>> cosRows = iPurchaseExpService.queryCosTimePurchase(purchaseForm);
        
        List<Object[]> listTqc =  new ArrayList<Object[]>();
        List<Object[]> listCon =  new ArrayList<Object[]>();
        List<Object[]> listRev =  new ArrayList<Object[]>();
        List<Object[]> listApp =  new ArrayList<Object[]>();
        List<Object[]> listTem =  new ArrayList<Object[]>();
        List<Object[]> listBid =  new ArrayList<Object[]>();
        List<Object[]> listOnl =  new ArrayList<Object[]>();
        List<Object[]> listCos =  new ArrayList<Object[]>();
        
        for(int i=0;i<tqcRows.size();i++)
        {
        	Map<String, Object> row = tqcRows.get(i);
        	Object [] obj =  new Object[15];
        	obj[0]=row.get("C1");
        	obj[1]=row.get("C2");
        	obj[2]=row.get("C3");
        	obj[3]=row.get("C4");
        	obj[4]=row.get("C5");
        	obj[5]=row.get("C6");
        	obj[6]=row.get("C7");
        	obj[7]=row.get("C8");
        	obj[8]=row.get("C9");
        	obj[9]=row.get("C10");
        	obj[10]=row.get("C11");
        	obj[11]=row.get("C12");
        	obj[12]=row.get("C13");
        	obj[13]=row.get("C14");
        	obj[14]=row.get("C15");
        	listTqc.add(obj);
        }
        
        for(int i=0;i<conRows.size();i++)
        {
        	Map<String, Object> row = conRows.get(i);
        	Object [] obj =  new Object[4];
        	obj[0]=row.get("AVGTIME");
        	obj[1]=row.get("MAXTIME");
        	obj[2]=row.get("MINTIME");
        	obj[3]=row.get("STIME");
        	listCon.add(obj);
        }
        
        for(int i=0;i<revRows.size();i++)
        {
        	Map<String, Object> row = revRows.get(i);
        	Object [] obj =  new Object[3];
        	obj[0]=row.get("AVGTIME");
        	obj[1]=row.get("MAXTIME");
        	obj[2]=row.get("MINTIME");
        	listRev.add(obj);
        }
        
        for(int i=0;i<apprRows.size();i++)
        {
        	Map<String, Object> row = apprRows.get(i);
        	Object [] obj =  new Object[3];
        	obj[0]=row.get("AVGTIME");
        	obj[1]=row.get("MAXTIME");
        	obj[2]=row.get("MINTIME");
        	listApp.add(obj);
        }
        
        for(int i=0;i<temRows.size();i++)
        {
        	Map<String, Object> row = temRows.get(i);
        	Object [] obj =  new Object[4];
        	obj[0]=row.get("C1");
        	obj[1]=row.get("C2");
        	obj[2]=row.get("C3");
        	obj[3]=row.get("C4");
        	listTem.add(obj);
        }
        
        for(int i=0;i<bidRows.size();i++)
        {
        	Map<String, Object> row = bidRows.get(i);
        	Object [] obj =  new Object[3];
        	obj[0]=row.get("C1");
        	obj[1]=row.get("C2");
        	obj[2]=row.get("C3");
        	listBid.add(obj);
        }
        
        for(int i=0;i<onlRows.size();i++)
        {
        	Map<String, Object> row = onlRows.get(i);
        	Object [] obj =  new Object[4];
        	obj[0]=row.get("C1");
        	obj[1]=row.get("C2");
        	obj[2]=row.get("C3");
        	obj[3]=row.get("C4");
        	listOnl.add(obj);
        }
        
        for(int i=0;i<cosRows.size();i++)
        {
        	Map<String, Object> row = cosRows.get(i);
        	Object [] obj =  new Object[4];
        	obj[0]=row.get("C1");
        	obj[1]=row.get("C2");
        	obj[2]=row.get("C3");
        	obj[3]=row.get("C4");
        	listCos.add(obj);
        }
        
        HSSFWorkbook book = getAllBook(fileName,listTqc ,listCon,listRev ,listApp ,listTem ,listBid ,listOnl ,listCos );
    	response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
		response.setContentType("application/x-download");
		response.setContentType("application/vnd.ms-excel");
		book.write(response.getOutputStream());   	
    }
    
    /**
     * 把8个表数据汇总到总的excel表
     * @param fileName
     * @param listTqc
     * @param listCon
     * @param listRev
     * @param listApp
     * @param listTem
     * @param listBid
     * @param listOnl
     * @param listCos
     * @return
     */
	private HSSFWorkbook getAllBook(String fileName, List<Object[]> listTqc,
			List<Object[]> listCon, List<Object[]> listRev,
			List<Object[]> listApp, List<Object[]> listTem,
			List<Object[]> listBid, List<Object[]> listOnl,
			List<Object[]> listCos) {
		
		HSSFWorkbook book = new HSSFWorkbook();
    	//样式设置 表头
		HSSFCellStyle style = book.createCellStyle();
		HSSFFont font = book.createFont();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中对齐
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		//style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);  
		//style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		style.setFont(font);
		//内容样式
    	HSSFCellStyle style2 = book.createCellStyle();
    	HSSFFont font2 = book.createFont();
    	style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style2.setFont(font2);
    	//设置工作表sheet
		HSSFSheet sheet = book.createSheet(fileName);
		//总表第一列表头
		String[] header1 = {"总数","收到请购单","工作组讨论","采购方案呈批","发布公告","完成评审","结果确认","结果公示","合同签订呈批","合同系统审批","签订纸质合同","已归档","已取消","完成评审项目数","已完成项目数"};		
		HSSFRow row = sheet.createRow(0);
		for (int s = 0; s < header1.length; s++) {
	    		HSSFCell cell = row.createCell(s);
	    		cell.setCellValue(header1[s].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);		
    	}
		//第二行写入数据
		HSSFRow row1 = sheet.createRow(1);
        Object[] objs = (Object[]) listTqc.get(0);
 
        for (int k = 0; k < objs.length; k++) {
        	HSSFCell cell = row1.createCell(k);
    		cell.setCellStyle(style2);
    			//设置值
    		Object value = objs[k];   			
			cell.setCellValue(value.toString());				
       }
       //设置第2列表头 空一行(从第三行开始）
       String[] header2 ={"需求确认时长","需求确认完毕时间-评审时长","需求确认完毕-合同系统审批完毕"};
	   HSSFRow row3= sheet.createRow(3);
		for (int s = 0; s < header2.length; s++) {
			if(s==0){
				sheet.addMergedRegion(new CellRangeAddress(3,3, 0, 3));
	    		HSSFCell cell = row3.createCell(s);
	    		cell.setCellValue(header2[s].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);
			}
			if(s==1){
			    sheet.addMergedRegion(new CellRangeAddress(3,3, 5,7));
	    		HSSFCell cell = row3.createCell(5);
	    		cell.setCellValue(header2[s].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);
			}
			if(s==2){
			    sheet.addMergedRegion(new CellRangeAddress(3,3, 9, 11));
	    		HSSFCell cell = row3.createCell(9);
	    		cell.setCellValue(header2[s].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);
			}	
   	    }
	   //第三列表头
	   String[] header3 ={"平均时长(工作日)","最大时长(天)","最小时长(天)","超过标准时长项目数"," ", "平均时长(天)","最大时长(天)","最小时长(天)", " ","平均时长(工作日)","最大时长(天)","最小时长(天)"};
	   HSSFRow row4= sheet.createRow(4);
		for (int s = 0; s < header3.length; s++) {
	    		HSSFCell cell = row4.createCell(s);
	    		if(header3[s] == " ") continue;
	    		cell.setCellValue(header3[s].toString());
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(s, 4000);			
  	    } 
	  //写入数据
		HSSFRow row5 = sheet.createRow(5);
        Object[] objcon = (Object[]) listCon.get(0);
        for (int k = 0; k < objcon.length; k++) {
        	HSSFCell cell = row5.createCell(k);
    		cell.setCellStyle(style2);
    			//设置值
    		Object value = objcon[k];   			
			cell.setCellValue(value.toString());				
       }
        
        Object[] objRev = (Object[]) listRev.get(0);
        for (int k = 5; k < objRev.length+5; k++) {
        	HSSFCell cell = row5.createCell(k);
    		cell.setCellStyle(style2);
    			//设置值
    		Object value = objRev[k-5];   			
			cell.setCellValue(value.toString());				
        }
        
        Object[] objApp = (Object[]) listApp.get(0);
        for (int k = 9; k < objApp.length+9; k++) {
        	HSSFCell cell = row5.createCell(k);
    		cell.setCellStyle(style2);
    			//设置值
    		Object value = objApp[k-9];   			
			cell.setCellValue(value.toString());				
        }
        
       //设置第4列表头 空一行(从第8行开始）
       String[] header4 ={"规范性","流标情况","单一来源采购情况"};
 	   HSSFRow row7= sheet.createRow(7);
 		for (int s = 0; s < header4.length; s++) {
 			if(s==0){
 				sheet.addMergedRegion(new CellRangeAddress(7,7,0, 3));
 	    		HSSFCell cell = row7.createCell(s);
 	    		cell.setCellValue(header4[s].toString());
 	    		cell.setCellStyle(style);
 	    		sheet.setColumnWidth(s, 4000);
 			}
 			if(s==1){
 			    sheet.addMergedRegion(new CellRangeAddress(7,7, 5,7));
 	    		HSSFCell cell = row7.createCell(5);
 	    		cell.setCellValue(header4[s].toString());
 	    		cell.setCellStyle(style);
 	    		sheet.setColumnWidth(s, 4000);
 			}
 			if(s==2){
 			    sheet.addMergedRegion(new CellRangeAddress(7,7, 9, 12));
 	    		HSSFCell cell = row7.createCell(9);
 	    		cell.setCellValue(header4[s].toString());
 	    		cell.setCellStyle(style);
 	    		sheet.setColumnWidth(s, 4000);
 			}	
    	    }
 		 //第6列表头
 	   String[] header5 ={"未使用技术评分表模板项目数","未使用合同模板项目数","技术与商务比例不符合公司规定项目数","采购过程被供应商投诉项目数"," ","发生过流标情况的项目数","流标次数总计","流标后更改采购方式项目数"," ","总数","数量占比","总额(万元)","金额占比"};
 	   HSSFRow row8= sheet.createRow(8);
 		for (int s = 0; s < header5.length; s++) {
 	    		HSSFCell cell = row8.createCell(s);
 	    		if(header5[s] == " ") continue;
 	    		cell.setCellValue(header5[s].toString());
 	    		cell.setCellStyle(style);
 	    		sheet.setColumnWidth(s, 4000);			
   	    } 
 		
 		//写入数据
        
 		HSSFRow row9 = sheet.createRow(9);
        Object[] objTmp = (Object[]) listTem.get(0);
        for (int k = 0; k < objTmp.length; k++) {
        	HSSFCell cell = row9.createCell(k);
    		cell.setCellStyle(style2);
    			//设置值
    		Object value = objTmp[k];   			
			cell.setCellValue(value.toString());				
       }
        
        Object[] objBid = (Object[]) listBid.get(0);
        for (int k = 5; k < objBid.length+5; k++) {
        	HSSFCell cell = row9.createCell(k);
    		cell.setCellStyle(style2);
    			//设置值
    		Object value = objBid[k-5];   			
			cell.setCellValue(value.toString());				
        }
        
        Object[] objOnl = (Object[]) listOnl.get(0);
        for (int k = 9; k < objOnl.length+9; k++) {
        	HSSFCell cell = row9.createCell(k);
    		cell.setCellStyle(style2);
    			//设置值
    		Object value = objOnl[k-9];   			
			cell.setCellValue(value.toString());				
        }
       
       //第5列表头  
       String[] header6 ={"采购项目预算金额(万元)","签订合同金额(万元)","节约金额(万元)","节支率"};
  	   HSSFRow row11= sheet.createRow(11);
  		for (int s = 0; s < header6.length; s++) {  			
  	    		HSSFCell cell = row11.createCell(s);
  	    		cell.setCellValue(header6[s].toString());
  	    		cell.setCellStyle(style);
  	    		sheet.setColumnWidth(s, 4000); 			
     	    }
	 //写入最后一个list数据.......
        Object[] objCos = (Object[]) listCos.get(0);
        HSSFRow row12 = sheet.createRow(12);
        for (int k = 0; k < objCos.length; k++) {
        	HSSFCell cell = row12.createCell(k);
    		cell.setCellStyle(style2);
    			//设置值
    		Object value = objCos[k];   			
			cell.setCellValue(value.toString());				
        }
		
		return book;
	}
    
}
