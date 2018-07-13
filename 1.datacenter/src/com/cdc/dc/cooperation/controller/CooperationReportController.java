package com.cdc.dc.cooperation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.model.CooperationTypeRoleUser;
import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.dc.metadata.manyd.ManYD;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;
import com.trustel.common.SerialNo;
/**
 * 合交经分数据更新类
 * @author ZENGKAI
 * @date 2016-06-01 16:45:29
 */
@Controller
@RequestMapping(value = "/copperationController/")
public class CooperationReportController extends DefaultController{
	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IEnterpriseService enterpriseService;
	 
	
	/**
	 * 报表查询首页
	 */
	@RequestMapping(value="query", method = {RequestMethod.POST,RequestMethod.GET})
	public String query(HttpServletRequest request,CooperationForm cooperationForm) {
		//查询具有查询员权限的报表
		SysUser sysUser = getVisitor(request);
		SysRole sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.YB_QUERYER).getParameterValue());
		List<CooperationTypeRoleUser> list = cooperationService.queryCooperationTypeRoleUserList(sysRole.getRoleId(),sysUser.getUserId());
		
		//二级
		List<CooperationDatasourceType> datasourceTypeList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		request.setAttribute("datasourceTypeList", datasourceTypeList);
		request.setAttribute("sonList", JSONArray.fromObject(datasourceTypeList).toString());
		
		//一级
		List<CooperationDatasourceType> parentDatasourceList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(null,datasourceTypeList), CooperationCommon.datasourceStatus_Save);
		request.setAttribute("parentDatasourceList", parentDatasourceList);
		request.setAttribute("fileTempId", UUID.randomUUID().toString());
		
		if(!StringUtils.isNotEmpty(cooperationForm.getDatasourceId()) && datasourceTypeList != null && datasourceTypeList.size() > 0){
			cooperationForm.setDatasourceId(datasourceTypeList.get(0).getDatasourceId());
			cooperationForm.setParentDatasourceId(datasourceTypeList.get(0).getParentDatasourceId());
		}
		if(StringUtils.isNotEmpty(cooperationForm.getDatasourceId())){
			CooperationDatasourceType datasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, cooperationForm.getDatasourceId());
			if(datasourceType != null){
				if(StringUtils.isNotEmpty(datasourceType.getInterfaceTableName())){
					request.setAttribute("cooperationForm", cooperationForm);
					request.setAttribute("datasourceType", datasourceType);
					
					if("VIEW_MANYD".equals(datasourceType.getInterfaceTableName())){
						request.setAttribute("interfaceTableName", ManYD.buildView().split(","));
					}else if("SHOURU".equals(datasourceType.getInterfaceTableName())){
						String[] viewName = datasourceType.getInterfaceTable().split(",");
						//收入主题维度
						datasourceType.setInterfaceTable(viewName[0]);
						ItemPage itemPagezt =(ItemPage) cooperationService.queryReportQueryItemPage(cooperationForm,datasourceType);
						//收入费用维度
						datasourceType.setInterfaceTable(viewName[1]);
						ItemPage itemPagefy =(ItemPage) cooperationService.queryReportQueryItemPage(cooperationForm,datasourceType);
						
						request.setAttribute("ITEMPAGE_ZT",itemPagezt );
						request.setAttribute("ITEMPAGE_FY",itemPagefy );
						
						request.setAttribute("interfaceTableName", "主题名称,1月,2月,3月,4月,5月,6月,7月,8月,9月,10月,11月,12月,合计".split(","));
						return "/dc/cooperation/datasourceQueryOfShouru";
					}else{
						request.setAttribute("interfaceTableName", datasourceType.getInterfaceTableName().split(","));
					}
				}
				ItemPage itemPage =(ItemPage) cooperationService.queryReportQueryItemPage(cooperationForm,datasourceType);
				
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
			}
		}
		return "/dc/cooperation/datasourceQuery";
	}
	
	/**
	 * 导出功能
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportQuery", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportQuery(HttpServletRequest request, HttpServletResponse response,CooperationForm cooperationForm) {
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			HSSFWorkbook book = new HSSFWorkbook();
			  
			cooperationForm.setPageSize(65525);
			ItemPage itemPage = null;
			if(StringUtils.isNotEmpty(cooperationForm.getDatasourceId())){
				CooperationDatasourceType datasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, cooperationForm.getDatasourceId());
				
				String[] strs = datasourceType.getInterfaceTableName().split(",");
				if("VIEW_MANYD".equals(datasourceType.getInterfaceTableName())){
					strs = ManYD.buildView().split(",");
				}else if("SHOURU".equals(datasourceType.getInterfaceTableName())){
					strs = "主题名称,1月,2月,3月,4月,5月,6月,7月,8月,9月,10月,11月,12月,合计".split(",");
					
				}
				String[] header = new String[strs.length + 1]; //
				header[0] = "年月";
				System.arraycopy(strs, 0, header, 1, strs.length);
				if("SHOURU".equals(datasourceType.getInterfaceTableName())){
					String[] viewName = datasourceType.getInterfaceTable().split(",");
					//收入主题维度
					datasourceType.setInterfaceTable(viewName[0]);
					ItemPage itemPage1 =(ItemPage) cooperationService.queryReportQueryItemPage(cooperationForm,datasourceType);
					//收入费用维度
					datasourceType.setInterfaceTable(viewName[1]);
					ItemPage itemPage2 =(ItemPage) cooperationService.queryReportQueryItemPage(cooperationForm,datasourceType);
					List<Object[]> list = new ArrayList<Object[]>();
					List<Object[]> list1 = (List<Object[]>) itemPage1.getItems();
					List<Object[]> list2 = (List<Object[]>) itemPage2.getItems();
					
					Object[] obj1 = new Object[header.length];
					obj1[0] = "主题维度";
					list.add(obj1);
					list.addAll(list1);
					Object[] obj2 = new Object[header.length];
					obj2[0] = "费用维度";
					list.add(obj2);
					list.addAll(list2);
					
					itemPage = new ItemPage(list, Long.valueOf(list1.size()+list2.size()),(int) itemPage1.getPageCount(), itemPage1.getPageSize());
				}else{
					itemPage =(ItemPage) cooperationService.queryReportQueryItemPage(cooperationForm,datasourceType);
				}
				List<Object[]> list = (List<Object[]>) itemPage.getItems();
				String[] index = request.getParameterValues("subBox");
				String ids = "," + StringUtils.join(index,",") + ",";
				List<Object[]> arrList = new ArrayList<Object[]>() ;// 结果： List<Object[]>
				for(int i=0;i<list.size();i++){
					String id = "," + String.valueOf(i) + ",";
					if(ids.indexOf(id) == -1) continue;
					
					Object[] object = list.get(i);
					Object[] obj = new Object[header.length];
					int k = 0;
					for (int j = 0; j < header.length; j++) {  
						obj[k] = object[j];
						k++;
					}
					arrList.add(obj);
				}
				//文件名
				String fileName = datasourceType.getParentDatasourceName() + "-" + datasourceType.getDatasourceName() + "-" + SerialNo.getUNID();
				ExcelUtil.exportForExcel(header,fileName,arrList,book);
				response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
				response.setContentType("application/x-download");
				response.setContentType("application/vnd.ms-excel");
				book.write(response.getOutputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
