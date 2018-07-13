package com.cdc.dc.cooperation.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;

import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.cooperation.model.CooperationDatasourceRecords;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;
import com.trustel.common.SerialNo;
/**
 * 合交经分公用类
 * @author ZENGKAI
 * @date 2016-04-11 17:04:29
 */
@Controller
@RequestMapping(value = "/copperationController/")
public class CooperationCommonController extends CommonController{

	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IEnterpriseService enterpriseService;
	
	/**
	 * 导出功能
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportDatasource", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportDatasource(HttpServletRequest request, HttpServletResponse response,CooperationForm cooperationForm) {
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			HSSFWorkbook book = new HSSFWorkbook();
			  
			cooperationForm.setPageSize(65525);
			ItemPage itemPage = null;
			List talbeInfoList = null;

			String id= request.getParameter("id");
			if(StringUtils.isNotEmpty(id)){
				CooperationDatasourceRecords datasourceRecords = (CooperationDatasourceRecords) enterpriseService.queryEntity(CooperationDatasourceRecords.class, id);
				if(datasourceRecords != null){
					CooperationDatasourceType cdt = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceRecords.getDatasourceId());
					itemPage =(ItemPage) cooperationService.queryReportsItemPage(datasourceRecords,cdt,cooperationForm);
					//根据表名查询表信息，字段，注释等
					talbeInfoList =  cooperationService.queryReportTableVoByTableName(cdt);
				}
			}
			String[] header = new String[talbeInfoList.size()];
			for (int i = 0; i < talbeInfoList.size(); i++) {
				Object[] obj = (Object[]) talbeInfoList.get(i);
				header[i] = (String) obj[2];
			}
			List<Object[]> list = (List<Object[]>) itemPage.getItems();
			
			List<Object[]> arrList = new ArrayList<Object[]>() ;// 结果： List<Object[]>
			for(Object[] object : list) {
				Object[] obj = new Object[object.length - 3];
				int i = 0;
				for (int j = 0; j < object.length - 3; j++) {  
					obj[i] = object[j];
					i++;
				}
				arrList.add(obj);
				
			}
			//文件名
			String fileName = "数据明细导出" + SerialNo.getUNID();
			ExcelUtil.exportForExcel(header,fileName,arrList,book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导出功能
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportTableData", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportTableData(HttpServletRequest request, HttpServletResponse response,CooperationForm cooperationForm) {
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			HSSFWorkbook book = new HSSFWorkbook();
			 //表格数据
			String data = request.getParameter("data");
			if(StringUtils.isNotEmpty(data)){
				
				String[] list = data.split(";");
				if(list != null && list.length > 0){
					//标题
					String[] header = list[0].split("\\|");
					//内容结果集
					List<Object[]> arrList = new ArrayList<Object[]>() ;// 结果： List<Object[]>
					for (int i = 1; i < list.length; i++) {
						arrList.add(list[i].split("\\|"));
					}
					//文件名
					String fileName = "数据导出" + SerialNo.getUNID();
					ExcelUtil.exportForExcel(header,fileName,arrList,book);
					response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
					response.setContentType("application/x-download");
					response.setContentType("application/vnd.ms-excel");
					book.write(response.getOutputStream());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author ZENGKAI
	 * 异步查询数据源配置信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "viewDatasourceType", method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void viewDatasourceType(HttpServletRequest request,HttpServletResponse response){
		String datasourceId = request.getParameter("datasourceId");
		
		try{
			CooperationDatasourceType datasourceType = new CooperationDatasourceType();
			if(StringUtils.isNotEmpty(datasourceId)){
				 datasourceType = (CooperationDatasourceType) enterpriseService.getById(CooperationDatasourceType.class, datasourceId);
			}
			JSONObject object = JSONObject.fromObject(datasourceType);
			PrintWriter out = response.getWriter();
    		out.write(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
