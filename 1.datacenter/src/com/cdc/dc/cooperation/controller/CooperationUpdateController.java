package com.cdc.dc.cooperation.controller;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.common.BusTypes;
import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.cooperation.model.CooperationDatasourceRecords;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;
import com.trustel.common.SerialNo;
/**
 * 合交经分数据更新类
 * @author ZENGKAI
 * @date 2016-06-01 15:45:29
 */
@Controller
@RequestMapping(value = "/copperationController/")
public class CooperationUpdateController extends DefaultController{
	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IEnterpriseService enterpriseService;
	 
	
	/**
	 * 数据更新首页
	 */
	@RequestMapping(value="updates", method = {RequestMethod.POST,RequestMethod.GET})
	public String updates(HttpServletRequest request,CooperationForm cooperationForm) {
		ItemPage itemPage =(ItemPage) cooperationService.queryCooperationDatasourceTypeItemPage(cooperationForm,BusTypes.busTypes_DS,CooperationCommon.datasourceStatus_Save);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "/dc/cooperation/datasourceUpdates";
	}
	
	
	/**
	 * 数据更新同步数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "syncData", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void syncData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String result = "0";
		SysUser sysUser = getVisitor(request);
		String datasourceId = request.getParameter("datasourceId");
		if(StringUtils.isNotEmpty(datasourceId)){
			final CooperationDatasourceType datasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
			//新建数据录入记录
			final CooperationDatasourceRecords datasourceRecords = new CooperationDatasourceRecords();
			datasourceRecords.setRecordId(UUID.randomUUID().toString());
			datasourceRecords.setDatasourceId(datasourceId);
//			datasourceRecords.setFileId();//录入无附件信息
			Calendar c = Calendar.getInstance();
//	        c.add(Calendar.MONTH, -1);
			datasourceRecords.setMonth(new SimpleDateFormat("yyyy-MM").format(c.getTime()));
			datasourceRecords.setCreateTime(new Date());
			datasourceRecords.setCreateUserid(sysUser.getUserId());
			datasourceRecords.setCreateUsername(sysUser.getUserName());
			datasourceRecords.setStatus(CooperationCommon.datasourceRecordsStatus_SH);//提交
			datasourceRecords.setSyncTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()));
			datasourceRecords.setSyncStatus(CooperationCommon.datasourceRecordsStatus_TBZ);
			cooperationService.saveEntity(datasourceRecords);
			
			 Thread t = new Thread(new Runnable(){
	            public void run(){
	            	boolean b = false;
	            	try {
	            		//数据源类型配置
	            		if(StringUtils.isNotEmpty(datasourceType.getInterfaceTable())){
	            			String[] classStr = datasourceType.getInterfaceTable().split(",");
	            			Class<?> bClazz = Class.forName(classStr[0]);//接口数据源配置类全路径
	            			Object obj = bClazz.newInstance();
	            			Method bMethod = bClazz.getDeclaredMethod(classStr[1]);//接口数据源配置类方法
	            			b = (Boolean) bMethod.invoke(obj); //执行同步方法
	            		}
	            	} catch (Exception e) {
	            		e.printStackTrace();
	            	}finally{
	            		if(b){
	            			datasourceRecords.setSyncStatus(CooperationCommon.datasourceRecordsStatus_TB);
	            		}else{
	            			datasourceRecords.setSyncStatus(CooperationCommon.datasourceRecordsStatus_WTB);
	            		}
	            		ICooperationService service = (ICooperationService)SpringHelper.getBean("cooperationService");
	            		service.updateEntity(datasourceRecords);
	            	}
	            }});
			t.start();
			
		}
		result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);
	}
	
	@RequestMapping(value = "datasourceDetailList", method = {RequestMethod.GET,RequestMethod.POST})
	public String datasourceDetailList(HttpServletRequest request,CooperationForm cooperationForm){
		String id = request.getParameter("id");
		if(StringUtils.isNotEmpty(id)){
			//从数据字典查询数据源明细的表名
			SysParameter sysParameter = SysParamHelper.getSysParamByCode(id);
			if(sysParameter != null){
				String tabStr = sysParameter.getParameterValue();
				if(StringUtils.isNotBlank(tabStr)){
					String[] tables = tabStr.split(",");
					request.setAttribute("tables", tables);
				}
			}
		}
		return "/dc/cooperation/datasourceDetailList";
	}
	
	@RequestMapping(value = "datasourceDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public String datasourceDetail(HttpServletRequest request,CooperationForm cooperationForm){
		String tab = request.getParameter("tab");
		if(StringUtils.isNotBlank(tab)){
			//根据表名查询表信息，字段，注释等
			List tableInfoList = cooperationService.queryReportTableVoByTableName(tab);
			ItemPage itemPage = cooperationService.queryTableItemPageByTableName(tab,cooperationForm);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
			request.setAttribute("talbeInfoList",tableInfoList);
		}
		return "/dc/cooperation/datasourceDetail";
	}
	
	@RequestMapping(value = "datasourceExport", method = {RequestMethod.GET,RequestMethod.POST})
	public void datasourceExport(HttpServletRequest request, HttpServletResponse response,CooperationForm cooperationForm){
		String id = request.getParameter("id");
		if(StringUtils.isNotEmpty(id)){
			ItemPage itemPage = (ItemPage) cooperationService.queryCooperationDatasourceTypeItemPage(cooperationForm,BusTypes.busTypes_DS,CooperationCommon.datasourceStatus_Save);
			List itemList = itemPage.getItems();
			String fileName = "明细";
			if(null != itemList){
				for(int i = 0; i < itemList.size(); i++){
					Object[] objArr = (Object[]) itemList.get(i);
					if(id.equals((String)objArr[1])){
						fileName = (String) objArr[2] + fileName;
						break;
					}
				}
			}
			
			//从数据字典查询数据源明细的表名
			SysParameter sysParameter = SysParamHelper.getSysParamByCode(id);
			String tabStr = sysParameter.getParameterValue();
			String[] tables = null;
			if(StringUtils.isNotBlank(tabStr)){
				tables = tabStr.split(",");
			}
			
			if(null != tables){
				try {
					response.setContentType("application/vnd.ms-excel");
					response.setCharacterEncoding("UTF-8");
					HSSFWorkbook book = new HSSFWorkbook();
					
					cooperationForm.setPageSize(65525);
					
					for (int i = 0; i < tables.length; i++) {
						String table = tables[i];
						itemPage = cooperationService.queryTableItemPageByTableName(table,cooperationForm);
						//根据表名查询表信息，字段，注释等
						List tableInfoList = cooperationService.queryReportTableVoByTableName(table);
						
						String[] header = new String[tableInfoList.size()];
						String sheetName = "";
						if(null!=tableInfoList && tableInfoList.size()>0){
							sheetName = i+1 + "." + (String) ((Object[])tableInfoList.get(0))[3];
							for (int j = 0; j < tableInfoList.size(); j++) {
								Object[] obj = (Object[]) tableInfoList.get(j);
								header[j] = (String) obj[2];
							}
						}
						List<Object[]> list = (List<Object[]>) itemPage.getItems();
						List<Object[]> arrList = new ArrayList<Object[]>();
						for (Object[] object : list) {
							Object[] obj = new Object[header.length];
							int k = 0;
							for (int j = 0; j < header.length; j++) {
								obj[k] = object[j];
								k++;
							}
							arrList.add(obj);
						}
						
						//文件名
						ExcelUtil.exportForExcel(header,sheetName,arrList,book);
					}
					
					fileName += SerialNo.getUNID();
					response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
					response.setContentType("application/x-download");
					response.setContentType("application/vnd.ms-excel");
					book.write(response.getOutputStream());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
