package com.cdc.dc.cooperation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.common.BusTypes;
import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
import com.trustel.common.ItemPage;
import com.util.ExcelUtil;
/**
 * 合交经分数据权限配置类
 * @author ZENGKAI
 * @date 2016-05-19 10:24:29
 */
@Controller
@RequestMapping(value = "/copperationController/")
public class CooperationPrivilgesController extends DefaultController{
	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IEnterpriseService enterpriseService;
	
	/**
	 * 数据权限管理首页
	 */
	@RequestMapping(value="queryPrivilges", method = {RequestMethod.POST,RequestMethod.GET})
	public String queryPrivilges(HttpServletRequest request,CooperationForm cooperationForm) {
		SysUser sysUser = getVisitor(request);
		String busTypes = cooperationForm.getBusTypes();
		if(!StringUtils.isNotEmpty(busTypes)){
			busTypes = BusTypes.busTypes_DS;
		}
		request.setAttribute("cooperationForm",cooperationForm);
		//一级数据源类型
		List<CooperationDatasourceType> parentDatasourceList = cooperationService.queryCooperationDatasourceTypeList(busTypes,CooperationCommon.datasourceRoot_parentId,CooperationCommon.datasourceStatus_Save);
		request.setAttribute("parentDatasourceList", parentDatasourceList);
		if(StringUtils.isNotEmpty(cooperationForm.getParentDatasourceId())){
			//二级数据源类型
			List<CooperationDatasourceType> datasourceTypeList = cooperationService.queryCooperationDatasourceTypeList(busTypes,cooperationForm.getParentDatasourceId(),CooperationCommon.datasourceStatus_Save);
			request.setAttribute("datasourceTypeList", datasourceTypeList);
		}
		//查询配置列表
		ItemPage itemPage =(ItemPage) cooperationService.queryPrivilgesItemPage(cooperationForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "/dc/cooperation/datasourcePrivilges";
	}
	
	/**
	 * @author ZENGKAI
	 * 异步查询数据源类型
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "changeBusTypes", method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void changeBusTypes(HttpServletRequest request,HttpServletResponse response){
		String parentDatasourceId = request.getParameter("parentDatasourceId");
		String busTypes = request.getParameter("busTypes");
		try{
			List<CooperationDatasourceType> datasourceTypeList = cooperationService.queryCooperationDatasourceTypeList(busTypes,parentDatasourceId,CooperationCommon.datasourceStatus_Save);
			JSONArray object = JSONArray.fromObject(datasourceTypeList);
			PrintWriter out = response.getWriter();
    		out.write(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据权限配置
	 */
	@RequestMapping(value="datasourceConfigure", method = {RequestMethod.POST,RequestMethod.GET})
	public String datasourceConfigure(HttpServletRequest request) {
		String datasourceId = request.getParameter("id");
		String bustype = request.getParameter("bustype");
		
		CooperationDatasourceType cooperationDatasourceType = null;
		if(StringUtils.isNotEmpty(datasourceId)){
			cooperationDatasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
		}
		if(cooperationDatasourceType != null){
			if(!StringUtils.isNotEmpty(bustype)){
				bustype = cooperationDatasourceType.getBusTypes();
			}
			if(StringUtils.isNotEmpty(cooperationDatasourceType.getParentDatasourceId())){
				//二级数据源类型
				List<CooperationDatasourceType> datasourceTypeList = cooperationService.queryCooperationDatasourceTypeList(bustype,cooperationDatasourceType.getParentDatasourceId(),CooperationCommon.datasourceStatus_Save);
				request.setAttribute("datasourceTypeList", datasourceTypeList);
			}
			//查询已配置的用户列表
			//数据源配置
			if(BusTypes.busTypes_DS.equals(bustype)){
				//录入员
				SysRole sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_IMPORTER).getParameterValue());
				List<SysUser> list = cooperationService.fingSysUserListById(datasourceId,sysRole.getRoleId());
				request.setAttribute("importerList", list);
				//审核员
				sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_REMAINER).getParameterValue());
				list = cooperationService.fingSysUserListById(datasourceId,sysRole.getRoleId());
				request.setAttribute("remainerList", list);
				//查询员
				sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_QUERYER).getParameterValue());
				list = cooperationService.fingSysUserListById(datasourceId,sysRole.getRoleId());
				request.setAttribute("queryerList", list);
			}else if(BusTypes.busTypes_YB.equals(bustype)){
				//查询员
				SysRole sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.YB_QUERYER).getParameterValue());
				List<SysUser> list = cooperationService.fingSysUserListById(datasourceId,sysRole.getRoleId());
				request.setAttribute("queryerList", list);
			}
			request.setAttribute("parentDatasourceId", cooperationDatasourceType.getParentDatasourceId());
		}
		//一级数据源类型
		List<CooperationDatasourceType> parentDatasourceList = cooperationService.queryCooperationDatasourceTypeList(bustype,CooperationCommon.datasourceRoot_parentId,CooperationCommon.datasourceStatus_Save);
		request.setAttribute("parentDatasourceList", parentDatasourceList);
		request.setAttribute("datasourceId", datasourceId);
		request.setAttribute("busTypes", bustype);
		String busTypesName = SysParamHelper.getSysParamByCode("BUS_TYPE",bustype).getParameterValue();
		request.setAttribute("busTypesName",busTypesName );
		return "/dc/cooperation/datasourceConfigure";
	}
	
	/**
	 * 权限配置提交
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="configureSubmit",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void configureSubmit(HttpServletRequest request,HttpServletResponse response){
		String result = "0";
		try{
			SysUser sysUser = getVisitor(request);
			//数据源/业务报表类型ID
			String datasourceId = request.getParameter("datasourceId");
			//录入员
			String[] importUserId = request.getParameterValues("importUserId");
			//审核员角色
			String[] remainUserId = request.getParameterValues("remainUserId");
			//查询员
			String[] queryUserId = request.getParameterValues("queryUserId");
			String busTypes = request.getParameter("busTypes");
			cooperationService.configureSubmit(sysUser,datasourceId,importUserId,remainUserId,queryUserId,busTypes,true);
			result = "1";
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * @author ZENGKAI
	 * 导入并保存
	 * @param request
	 * @param response
	 * @param file
	 */
	@RequestMapping(value="importDsConfigure",method={RequestMethod.POST})
	@ResponseBody
	public void importDsConfigure(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file") MultipartFile file){
		PrintWriter out = null;
		try {
			out=response.getWriter();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List list = ExcelUtil.importFromExcel(file,0);
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					String[] strs = (String[]) list.get(i);
//					DATASOURCE_ID	编号		大类		小类		查询员	录入员	审核员

					SysUser sysUser = getVisitor(request);
					//数据源/业务报表类型ID
					String datasourceId = strs[0];
					CooperationDatasourceType cooperationDatasourceType = null;
					if(StringUtils.isNotEmpty(datasourceId)){
						cooperationDatasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
					}
					if(cooperationDatasourceType != null){
						String busTypes = cooperationDatasourceType.getBusTypes();
						//查询员
						String[] queryUserId = strs[4].split("，");
						String[] queryIds = null;
						if(queryUserId != null && queryUserId.length > 0){
							queryIds = new String[queryUserId.length];
							for (int j = 0; j < queryUserId.length; j++) {
								SysUser user = cooperationService.querySysUserByName(queryUserId[j]);
								if(user != null){
									queryIds[j] = user.getUserId();
								}
							}
						}
						//录入员
						String[] importUserId = strs[5].split("，");
						String[] importIds = null;
						if(importUserId != null && importUserId.length > 0){
							importIds = new String[importUserId.length];
							for (int j = 0; j < importUserId.length; j++) {
								SysUser user = cooperationService.querySysUserByName(importUserId[j]);
								if(user != null){
									importIds[j] = user.getUserId();
								}
							}
						}
						//审核员角色
						String[] remainUserId = strs[6].split("，");
						String[] remainIds = null;
						if(remainUserId != null && remainUserId.length > 0){
							remainIds = new String[remainUserId.length];
							for (int j = 0; j < remainUserId.length; j++) {
								SysUser user = cooperationService.querySysUserByName(remainUserId[j]);
								if(user != null){
									remainIds[j] = user.getUserId();
								}
							}
						}
						cooperationService.configureSubmit(sysUser,datasourceId,importIds,remainIds,queryIds,busTypes,false);
					}
				}
			}
			out.print("1");
			out.close();
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
		
	}
	/**
	 * @author ZENGKAI
	 * 异步查询权限配置
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "loadConfigById", method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void loadConfigById(HttpServletRequest request,HttpServletResponse response){

		String datasourceId = request.getParameter("datasourceId");
		String bustype = request.getParameter("busTypes");
		
		JSONObject result = new JSONObject();
		
		CooperationDatasourceType cooperationDatasourceType = null;
		if(StringUtils.isNotEmpty(datasourceId)){
			cooperationDatasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
		}
		if(cooperationDatasourceType != null){
			if(!StringUtils.isNotEmpty(bustype)){
				bustype = cooperationDatasourceType.getBusTypes();
			}
			//查询已配置的用户列表
			//数据源配置
			if(BusTypes.busTypes_DS.equals(bustype)){
				//录入员
				SysRole sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_IMPORTER).getParameterValue());
				List<SysUser> list = cooperationService.fingSysUserListById(datasourceId,sysRole.getRoleId());
				result.put("importerList", list);
				//审核员
				sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_REMAINER).getParameterValue());
				list = cooperationService.fingSysUserListById(datasourceId,sysRole.getRoleId());
				result.put("remainerList", list);
				//查询员
				sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_QUERYER).getParameterValue());
				list = cooperationService.fingSysUserListById(datasourceId,sysRole.getRoleId());
				result.put("queryerList", list);
			}else if(BusTypes.busTypes_YB.equals(bustype)){
				//查询员
				SysRole sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.YB_QUERYER).getParameterValue());
				List<SysUser> list = cooperationService.fingSysUserListById(datasourceId,sysRole.getRoleId());
				result.put("queryerList", list);
			}
		}
	
		try {
			PrintWriter out = response.getWriter();
			out.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
