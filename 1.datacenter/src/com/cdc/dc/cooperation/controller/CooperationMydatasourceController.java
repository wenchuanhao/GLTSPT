package com.cdc.dc.cooperation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.cooperation.model.CooperationDatasourceRecords;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.model.CooperationTypeRoleUser;
import com.cdc.dc.cooperation.service.ICooperationService;
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
public class CooperationMydatasourceController extends DefaultController{
	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IEnterpriseService enterpriseService;
	
	SysRole sysRoleqImporter = null;
	SysRole	sysRoleQuery = null;
	SysRole sysRoleRemainer = null;
			
	@ModelAttribute
	public void loadMyData(HttpServletRequest request,CooperationForm cooperationForm){
		//查询用户具有录入权限的数据源
		sysRoleqImporter = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_IMPORTER).getParameterValue());
		//查询用户具有查询权限的数据源
		sysRoleQuery = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_QUERYER).getParameterValue());
		//查询用户具有审核权限的数据源
		sysRoleRemainer = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_REMAINER).getParameterValue());
	}
	
	/**
	 * 我的制度
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "myAll", method = {RequestMethod.POST,RequestMethod.GET})
	public String myAll(HttpServletRequest request,CooperationForm cooperationForm) throws Exception{
		request.setAttribute("cooperationForm",cooperationForm);
		SysUser sysUser = getVisitor(request);
		if(sysUser != null){
			cooperationForm.setUserid(sysUser.getUserId());
		}
		//type0:数据源列表   3：已审核  2：审核中  1：草稿   5：已修订
		String type = request.getParameter("type");
		if(!StringUtils.isNotEmpty(type)){
			type = "0";
		}
		request.setAttribute("type", type);
		
//		List<CooperationTypeRoleUser> list = null;
		List<CooperationDatasourceType> datasourceTypeList = getCooperationDatasourceTypeByType(sysUser,cooperationForm,type);
		request.setAttribute("datasourceTypeList", datasourceTypeList);
		request.setAttribute("sonList", JSONArray.fromObject(datasourceTypeList).toString());
		//一级
		List<CooperationDatasourceType> parentDatasourceList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(null,datasourceTypeList), CooperationCommon.datasourceStatus_Save);
		request.setAttribute("parentDatasourceList", parentDatasourceList);
		request.setAttribute("fileTempId", UUID.randomUUID().toString());
		ItemPage itemPage = null;
		if(CooperationCommon.datasourceRecordsStatus_DEL.equals(type)){
			itemPage =(ItemPage) cooperationService.queryMydatasourceItemPage(cooperationForm,sysUser,sysRoleqImporter,sysRoleQuery,sysRoleRemainer);
		}else{
			itemPage =(ItemPage) cooperationService.queryMydatasourceItemPage(cooperationForm, CooperationCommon.getIds(datasourceTypeList));
		}
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		//统计
		ItemPage MY = cooperationService.queryMydatasourceItemPage(cooperationForm,sysUser,sysRoleqImporter,sysRoleQuery,sysRoleRemainer);
		request.setAttribute("MY", MY.getTotal());
		List<CooperationDatasourceType> listSH = getCooperationDatasourceTypeByType(sysUser,cooperationForm,CooperationCommon.datasourceRecordsStatus_TG);;
		ItemPage SH = cooperationService.queryMydatasourceItemPage(cooperationForm, CooperationCommon.getIds(listSH ));
		request.setAttribute("SH", SH.getTotal());
		List<CooperationDatasourceType> listSHZ = getCooperationDatasourceTypeByType(sysUser,cooperationForm,CooperationCommon.datasourceRecordsStatus_SH);;
		ItemPage SHZ = cooperationService.queryMydatasourceItemPage(cooperationForm, CooperationCommon.getIds(listSHZ ));
		request.setAttribute("SHZ", SHZ.getTotal());
		List<CooperationDatasourceType> listCG = getCooperationDatasourceTypeByType(sysUser,cooperationForm,CooperationCommon.datasourceRecordsStatus_CG);;
		ItemPage CG = cooperationService.queryMydatasourceItemPage(cooperationForm, CooperationCommon.getIds(listCG ));
		request.setAttribute("CG", CG.getTotal());
		List<CooperationDatasourceType> listXD = getCooperationDatasourceTypeByType(sysUser,cooperationForm,CooperationCommon.datasourceRecordsStatus_XD);;
		ItemPage XD = cooperationService.queryMydatasourceItemPage(cooperationForm, CooperationCommon.getIds(listXD ));
		request.setAttribute("XD", XD.getTotal());
		
		return "/dc/cooperation/datasourceMyall";
	}
	
	
	private List<CooperationDatasourceType> getCooperationDatasourceTypeByType(SysUser sysUser,CooperationForm cooperationForm,String type){
		List<CooperationDatasourceType> datasourceTypeList = new ArrayList<CooperationDatasourceType>();
		List<CooperationTypeRoleUser> list = null;
		//数据源列表
		if(CooperationCommon.datasourceRecordsStatus_DEL.equals(type)){
			cooperationForm.setStatus("('"+CooperationCommon.datasourceRecordsStatus_CG+"'" +
							",'"+CooperationCommon.datasourceRecordsStatus_SH+"'" +
							",'"+CooperationCommon.datasourceRecordsStatus_TG+"'" +
							",'"+CooperationCommon.datasourceRecordsStatus_FZ+"'" +
							",'"+CooperationCommon.datasourceRecordsStatus_XD+"'" +
							",'"+CooperationCommon.datasourceRecordsStatus_WTG+"'" +
							",'"+CooperationCommon.datasourceRecordsStatus_TB+"'" +
							",'"+CooperationCommon.datasourceRecordsStatus_WTB+"')");
			
			list = cooperationService.queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
			list.addAll(cooperationService.queryCooperationTypeRoleUserList(sysRoleQuery.getRoleId(),sysUser.getUserId()));
			list.addAll(cooperationService.queryCooperationTypeRoleUserList(sysRoleRemainer.getRoleId(),sysUser.getUserId()));
			//二级
			datasourceTypeList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
			
		}else if(CooperationCommon.datasourceRecordsStatus_TG.equals(type)){
			//已审核，录入员，审核员，查询员
			cooperationForm.setStatus("('"+CooperationCommon.datasourceRecordsStatus_TG+"'" +
					",'"+CooperationCommon.datasourceRecordsStatus_FZ+"'" +
					",'"+CooperationCommon.datasourceRecordsStatus_XD+"'" +
					",'"+CooperationCommon.datasourceRecordsStatus_WTG+"'" +
					",'"+CooperationCommon.datasourceRecordsStatus_TB+"'" +
							",'"+CooperationCommon.datasourceRecordsStatus_WTB+"')");
			
			list = cooperationService.queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
			list.addAll(cooperationService.queryCooperationTypeRoleUserList(sysRoleQuery.getRoleId(),sysUser.getUserId()));
			list.addAll(cooperationService.queryCooperationTypeRoleUserList(sysRoleRemainer.getRoleId(),sysUser.getUserId()));
			//二级
			datasourceTypeList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		
		}else if(CooperationCommon.datasourceRecordsStatus_SH.equals(type)){
			//审核中,录入员、审核员
			cooperationForm.setStatus("('"+CooperationCommon.datasourceRecordsStatus_SH+"')");
			list = cooperationService.queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
			list.addAll(cooperationService.queryCooperationTypeRoleUserList(sysRoleRemainer.getRoleId(),sysUser.getUserId()));
			//二级
			datasourceTypeList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		
		}else if(CooperationCommon.datasourceRecordsStatus_CG.equals(type)){
			//草稿箱,录入员
			cooperationForm.setStatus("('"+CooperationCommon.datasourceRecordsStatus_CG+"'" +
					",'"+CooperationCommon.datasourceRecordsStatus_WTG+"')");
			list = cooperationService.queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
			//二级
			datasourceTypeList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		}else if(CooperationCommon.datasourceRecordsStatus_XD.equals(type)){
			//已修订,录入员、审核员
			cooperationForm.setStatus("('"+CooperationCommon.datasourceRecordsStatus_XD+"')");
			list = cooperationService.queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
			list.addAll(cooperationService.queryCooperationTypeRoleUserList(sysRoleRemainer.getRoleId(),sysUser.getUserId()));
			//二级
			datasourceTypeList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		}
		return datasourceTypeList;
	}
	
	/**
	 * 我的制度
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "myAllbak", method = {RequestMethod.POST,RequestMethod.GET})
	public String myAllbak(HttpServletRequest request,CooperationForm cooperationForm) throws Exception{
		request.setAttribute("cooperationForm",cooperationForm);
		SysUser user = this.getVisitor(request);
		SysUser sysUser = getVisitor(request);
		if(sysUser != null){
			cooperationForm.setUserid(sysUser.getUserId());
		}
		//type0:数据源列表   3：已审核  2：审核中  1：草稿   5：已修订
		String type = request.getParameter("type");
		if(type==null){
			type = "0";
		}
		request.setAttribute("type", type);
		Map map = new HashMap();
		
		List<CooperationTypeRoleUser> list = cooperationService.queryCooperationTypeRoleUserList(sysRoleqImporter.getRoleId(),sysUser.getUserId());
		map.put("importer", sysRoleqImporter.getRoleId());
		
		list.addAll(cooperationService.queryCooperationTypeRoleUserList(sysRoleQuery.getRoleId(),sysUser.getUserId()));
		map.put("queryer", sysRoleQuery.getRoleId());
		
		list.addAll(cooperationService.queryCooperationTypeRoleUserList(sysRoleRemainer.getRoleId(),sysUser.getUserId()));
		map.put("remainer", sysRoleRemainer.getRoleId());
		//二级
		List<CooperationDatasourceType> datasourceTypeList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		request.setAttribute("datasourceTypeList", datasourceTypeList);
		request.setAttribute("sonList", JSONArray.fromObject(datasourceTypeList).toString());
		
		//一级
		List<CooperationDatasourceType> parentDatasourceList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(null,datasourceTypeList), CooperationCommon.datasourceStatus_Save);
		request.setAttribute("parentDatasourceList", parentDatasourceList);
		request.setAttribute("fileTempId", UUID.randomUUID().toString());
		ItemPage itemPage =(ItemPage) cooperationService.queryMydatasourceItemPage(cooperationForm, type, sysUser,map);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		return "/dc/cooperation/datasourceMyall";
	}
	
	//提交我的数据源
	@RequestMapping(value="submitMyData",method={RequestMethod.POST,RequestMethod.GET})
	public void submitMyData(HttpServletRequest request,HttpServletResponse response) {
		PrintWriter out = null;
		try {
			SysUser sysUser = getVisitor(request);
			String datasourceRecordsId = request.getParameter("datasourceRecordsId");
			String status = request.getParameter("status");
			//根据记录id判断是否导入或录入
			if(StringUtils.isNotEmpty(datasourceRecordsId)){
				CooperationDatasourceRecords datasourceRecords = (CooperationDatasourceRecords) enterpriseService.queryEntity(CooperationDatasourceRecords.class, datasourceRecordsId);
				datasourceRecords.setStatus(status);//状态，提交或暂存
				cooperationService.submit(datasourceRecords,sysUser);
			}
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("1");
	}
	
	//删除我的数据源
	@RequestMapping(value="delMyData",method={RequestMethod.POST,RequestMethod.GET})
	public void delMyData(HttpServletResponse response,String dataSourceID) {
		PrintWriter out = null;
		try {
			if(dataSourceID!=null && !dataSourceID.trim().equals("")){
				cooperationService.delMyData(dataSourceID);
			}
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.write("1");
		
	}
}
