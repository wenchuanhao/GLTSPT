package com.cdc.dc.rules.controller;

import java.io.IOException;
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

import com.cdc.dc.rules.model.RulesType;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;

/**
 * 制度类型配置
 * @author ZENGKAI
 * @date 2016-04-11 09:24:29
 */
@Controller
@RequestMapping(value = "/rulesController/")
public class RulesTypeController extends CommonController {
	@Autowired
	private IRulesService rulesService;
	
	@ModelAttribute
	public void doBeforeCall(HttpServletRequest request){//业务类型，ZD=制度类
		String busTypes = request.getParameter("busTypes");
		String busTypesName = SysParamHelper.getSysParamByCode("BUS_TYPE",busTypes).getParameterValue();
		request.setAttribute("busTypes",busTypes );
		request.setAttribute("busTypesName",busTypesName );
	}
	/**
	 * 制度类型管理首页
	 */
	@RequestMapping(value="queryRulesType", method = {RequestMethod.POST,RequestMethod.GET})
	public String queryRulesType(HttpServletRequest request,RulesType rulesType) {
		String busTypes = request.getParameter("busTypes");
		String busTypesName = SysParamHelper.getSysParamByCode("BUS_TYPE",busTypes).getParameterValue();
		List<RulesType> rulesTypeList = rulesService.queryAllByBusType(busTypes,null);
		request.setAttribute("rulesTypeList", JSONArray.fromObject(rulesTypeList).toString());
		request.setAttribute("rulesType", rulesType);
		request.setAttribute("list", rulesTypeList);
		
		JSONArray list = new JSONArray();
		JSONObject objRoot = new JSONObject();
		objRoot.put("id", "ROOT");
		objRoot.put("pId", 0);
		objRoot.put("name", busTypesName);
		objRoot.put("open", true);
		list.add(objRoot);
		for (int i = 0; i < rulesTypeList.size(); i++) {
			JSONObject obj = new JSONObject();
			RulesType type = rulesTypeList.get(i);
			obj.put("id", type.getTypeId());
			obj.put("pId", type.getParentTypeId());
			obj.put("name", type.getTypeName());
			list.add(obj);
		}
		request.setAttribute("zNodes", list.toString());
		return "/dc/rules/rulesType";
	}
	
	/**
	 * 查询工作日
	 * @throws IOException 
	 */
	@RequestMapping(value="queryWorkDay",method={RequestMethod.POST,RequestMethod.GET})
	public void queryWorkDay(HttpServletResponse response,String datasourceId) throws IOException{
		PrintWriter out = response.getWriter();
		RulesType type = rulesService.queryWoryDay(datasourceId);
		String s = type.getWorkDay();
		if(s!=null){
			out.write(s);
		}else {
			out.write("");
		}
	}
	
	//检查费用类型是否重复
	@RequestMapping(value = "checkCostType", method = {RequestMethod.POST})
	public void checkCostType(HttpServletRequest request,HttpServletResponse response, RulesType rulesType) throws Exception{
		String tempName = request.getParameter("tempName");
		String result ="";
		PrintWriter out = response.getWriter();
		
		if(tempName.equals(rulesType.getTypeName())){
			out.write("0");
		}else {
			result =  rulesService.checkCostType(rulesType.getTypeName());
			if(result== null){
				out.write("0");
			}else {
				out.write("1");
			}
		}
		String busTypes = request.getParameter("busTypes");
		request.setAttribute("busTypes",busTypes );
		
		
	}
	
	/**
	 * 提交动作
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addRulesType", method = {RequestMethod.POST})
	@ResponseBody
	public void addRulesType(HttpServletRequest request, HttpServletResponse response, RulesType rulesType) throws Exception{
		String result = "0";
		SysUser sysUser = getVisitor(request);
		String busTypes = request.getParameter("busTypes");
		rulesService.addRulesType(rulesType,sysUser,busTypes);

		result = "1";
		JSONObject json = new JSONObject();
		json.put("result", result);
		json.put("rulesType", rulesType);
		//我的制度
		PrintWriter out = response.getWriter();
		out.write(json.toString());
	}
	/**
	 * 新增动作
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveRulesType", method = {RequestMethod.POST})
	@ResponseBody
	public void saveRulesType(HttpServletRequest request,HttpServletResponse response, RulesType rulesType) throws Exception{
		String result = "0";
		SysUser sysUser = getVisitor(request);
		rulesType.setTypeId("");//模拟新增
		String busTypes = request.getParameter("busTypes");
		
		rulesService.addRulesType(rulesType,sysUser,busTypes);
		
		result = "1";
		JSONObject json = new JSONObject();
		json.put("result", result);
		json.put("rulesType", rulesType);
		PrintWriter out = response.getWriter();
		out.write(json.toString());
	}
	
	/**
	 * 删除配置
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="delRulesType",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void delRulesType(HttpServletRequest request,HttpServletResponse response){
		String typeId = request.getParameter("typeId");
		String result = "0";
		try{
			if(StringUtils.isNotEmpty(typeId)){
				rulesService.delRulesType(typeId);
				
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
