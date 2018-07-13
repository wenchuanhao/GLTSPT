package com.cdc.dc.cooperation.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.cooperation.model.CooperationDatasourceRecords;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.model.CooperationTypeRoleUser;
import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.model.RulesInfo;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
import com.trustel.common.ItemPage;
/**
 * 合交经分我的数据源类
 * @author ZENGKAI
 * @date 2016-05-26 16:54:29
 */
@Controller
@RequestMapping(value = "/copperationController/")
public class CooperationRemainController extends DefaultController{
	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IEnterpriseService enterpriseService;
	
	/**
	 * 数据源审核
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "remain", method = {RequestMethod.POST,RequestMethod.GET})
	public String remain(HttpServletRequest request,CooperationForm cooperationForm) throws Exception{
		String status = request.getParameter("status");
		request.setAttribute("status", status);
		request.setAttribute("cooperationForm",cooperationForm);
		SysUser sysUser = getVisitor(request);
		if(sysUser != null){
			cooperationForm.setUserid(sysUser.getUserId());
		}

		//查询用户具有审核权限的数据源
		SysRole sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_REMAINER).getParameterValue());
		List<CooperationTypeRoleUser> list = cooperationService.queryCooperationTypeRoleUserList(sysRole.getRoleId(),sysUser.getUserId());
		//二级
		List<CooperationDatasourceType> datasourceTypeList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		request.setAttribute("datasourceTypeList", datasourceTypeList);
		request.setAttribute("sonList", JSONArray.fromObject(datasourceTypeList).toString());
		cooperationForm.setIds(CooperationCommon.getIds(list,null));
		
		//一级
		List<CooperationDatasourceType> parentDatasourceList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(null,datasourceTypeList), CooperationCommon.datasourceStatus_Save);
		request.setAttribute("parentDatasourceList", parentDatasourceList);
		request.setAttribute("fileTempId", UUID.randomUUID().toString());
		ItemPage itemPage =(ItemPage) cooperationService.queryRemainItemPage(cooperationForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		if(CooperationCommon.datasourceRecordsStatus_SH.equals(cooperationForm.getStatus())){
			request.setAttribute("shz",itemPage.getTotal() );//审核中
			CooperationForm cc = new CooperationForm();
			BeanUtils.copyProperties(cc,cooperationForm);
			cc.setStatus(CooperationCommon.datasourceRecordsStatus_TG);
			ItemPage ysh =(ItemPage) cooperationService.queryRemainItemPage(cc);
			request.setAttribute("ysh",ysh.getTotal() );//已审核
		}else{

			request.setAttribute("ysh",itemPage.getTotal() );//已审核
			CooperationForm cc = new CooperationForm();
			BeanUtils.copyProperties(cc,cooperationForm);
			cc.setStatus(CooperationCommon.datasourceRecordsStatus_SH);
			ItemPage shz =(ItemPage) cooperationService.queryRemainItemPage(cc);
			request.setAttribute("shz",shz.getTotal() );//审核中
		
		}
		
		return "/dc/cooperation/datasourceRemain";
	}
	
	/**
	 * 数据审核通过
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "pastRemain", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void pastRemain(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String result = "0";
		SysUser sysUser = getVisitor(request);
		String recordId = request.getParameter("recordId");
		if(StringUtils.isNotEmpty(recordId)){
			String[] rulesIds = recordId.split(",");
			for (String id : rulesIds) {
				CooperationDatasourceRecords datasourceRecords = (CooperationDatasourceRecords) enterpriseService.queryEntity(CooperationDatasourceRecords.class, id);
				//审核中才能通过
				if(datasourceRecords != null  && CooperationCommon.datasourceRecordsStatus_SH.equals(datasourceRecords.getStatus())){
					datasourceRecords.setStatus(CooperationCommon.datasourceRecordsStatus_TG);//通过
					cooperationService.pastRemain(datasourceRecords,sysUser);//发布
				}
			}
			result = "1";
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	
	}
	
	
	/**  
	 * 数据审核不通过
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "returnRemain", method = {RequestMethod.POST,RequestMethod.GET})
	public void returnRemain(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String result = "0";
		SysUser sysUser = getVisitor(request);
		String recordId = request.getParameter("recordId");
		String handelOpinion = request.getParameter("handelOpinion");
		//下发oa待办
		String checkboxOA = request.getParameter("checkboxOA");
		//下发短信
		String checkboxSMS = request.getParameter("checkboxSMS");
		if(StringUtils.isNotEmpty(recordId)){
			String[] rulesIds = recordId.split(",");
			for (String id : rulesIds) {
				CooperationDatasourceRecords datasourceRecords = (CooperationDatasourceRecords) enterpriseService.queryEntity(CooperationDatasourceRecords.class, id);
				//审核中才能不通过
				if(datasourceRecords != null  && CooperationCommon.datasourceRecordsStatus_SH.equals(datasourceRecords.getStatus())){
					datasourceRecords.setStatus(CooperationCommon.datasourceRecordsStatus_WTG);//不通过
					cooperationService.returnRemain(datasourceRecords,sysUser,handelOpinion,checkboxSMS);//不通过
				}
			}
			result = "1";
		}
		PrintWriter out = response.getWriter();
		out.write(result);
	
	}
	
	/**  
	 * 跳转至制度退回页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "returnDatasourceViewRemain", method = {RequestMethod.GET})
	public String returnDatasourceViewRemain(HttpServletRequest request) throws Exception{
		String recordId = request.getParameter("recordId");
		//如果有ID，查询信息
		if(StringUtils.isNotEmpty(recordId)){
			request.setAttribute("recordId", recordId);
		}
		
		//制度退回页面
		return "/dc/cooperation/datasourceReturnRemain";
	}
}
