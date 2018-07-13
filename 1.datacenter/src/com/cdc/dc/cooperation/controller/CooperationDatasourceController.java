package com.cdc.dc.cooperation.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;

import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
/**
 * 合交经分数据源配置类
 * @author ZENGKAI
 * @date 2016-04-11 17:04:29
 */
@Controller
@RequestMapping(value = "/copperationController/")
public class CooperationDatasourceController extends DefaultController{
	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IEnterpriseService enterpriseService;
	
	@ModelAttribute
	public void doBeforeCall(HttpServletRequest request){//业务类型，DS=数据源,YB=业务报表
		String busTypes = request.getParameter("busTypes");
		if(busTypes != null){
			String busTypesName = SysParamHelper.getSysParamByCode("BUS_TYPE",busTypes).getParameterValue();
			request.setAttribute("busTypesName",busTypesName );
		}
		request.setAttribute("busTypes",busTypes );
	}
	/**
	 * 数据源类型管理首页
	 */
	@RequestMapping(value="queryDatasourceType", method = {RequestMethod.POST,RequestMethod.GET})
	public String queryDatasourceType(HttpServletRequest request,CooperationDatasourceType datasourceType) {
		String busTypes = request.getParameter("busTypes");
		String busTypesName = "";
		if(busTypes != null){
			busTypesName = SysParamHelper.getSysParamByCode("BUS_TYPE",busTypes).getParameterValue();
			request.setAttribute("busTypesName",busTypesName );
		}
		List<CooperationDatasourceType> datasourceTypeList = cooperationService.queryCooperationDatasourceTypeList(busTypes,null,CooperationCommon.datasourceStatus_Save);
		request.setAttribute("datasourceTypeList", JSONArray.fromObject(datasourceTypeList).toString());
		request.setAttribute("datasourceType", datasourceType);
		request.setAttribute("list", datasourceTypeList);
		
		JSONArray list = new JSONArray();
		JSONObject objRoot = new JSONObject();
		objRoot.put("id", "ROOT");
		objRoot.put("pId", 0);
		objRoot.put("name", busTypesName);
		objRoot.put("open", true);
		list.add(objRoot);
		for (int i = 0; i < datasourceTypeList.size(); i++) {
			JSONObject obj = new JSONObject();
			CooperationDatasourceType type = datasourceTypeList.get(i);
			obj.put("id", type.getDatasourceId());
			obj.put("pId", type.getParentDatasourceId());
			obj.put("name", type.getDatasourceName());
			list.add(obj);
		}
		request.setAttribute("zNodes", list.toString());
		
		return "/dc/cooperation/datasourceType";
	}
	

	/**
	 * 提交动作
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addDatasourceType", method = {RequestMethod.POST})
	@ResponseBody
	public void addDatasourceType(HttpServletRequest request,HttpServletResponse response,CooperationDatasourceType datasourceType) throws Exception{
		String result = "0";
		SysUser sysUser = getVisitor(request);
		String busTypes = request.getParameter("busTypes");
		cooperationService.addDatasourceType(datasourceType, sysUser, busTypes);

		result = "1";
		JSONObject json = new JSONObject();
		json.put("result", result);
		json.put("datasourceType", datasourceType);
		PrintWriter out = response.getWriter();
		out.write(json.toString());
	}
	
	/**
	 * 删除配置
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="delDatasourceType",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void delDatasourceType(HttpServletRequest request,HttpServletResponse response){
		String datasourceId = request.getParameter("datasourceId");
		String result = "0";
		try{
			if(StringUtils.isNotEmpty(datasourceId)){
				SysUser sysUser = getVisitor(request);
				cooperationService.delDatasourceType(sysUser,datasourceId);
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
