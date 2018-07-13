package com.cdc.dc.rules.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import org.trustel.system.SystemConstant;

import com.cdc.dc.rules.form.RulesForm;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.trustel.common.ItemPage;

/**
 * 文档管理
 * @author ZENGKAI
 * @date 2016-04-11 09:24:29
 */
@Controller
@RequestMapping(value = "/rulesController/")
public class RulesDocumentController extends DefaultController {
	@Autowired
	private IRulesService rulesService;
	
	
	@ModelAttribute
	public void doBeforeCall(HttpServletRequest request){//业务类型，ZD=制度类
		String busTypes = request.getParameter("busTypes");
		RulesType rulesType = null;
		if(StringUtils.isNotEmpty(busTypes)){
			rulesType = (RulesType) rulesService.getEntity(RulesType.class, busTypes);
		}
		if(rulesType != null){
			request.setAttribute("busTypesValue",rulesType.getTypeName());
			//查询子分类
			List<RulesType> list  = rulesService.queryAllByBusType(rulesType.getBusTypes(),busTypes);
			request.setAttribute("busTypesList",list);
		}
		request.setAttribute("busTypeName",busTypes );
	}
	/**
	 * 文档管理
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "documents", method = {RequestMethod.POST,RequestMethod.GET})
	public String documents(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		request.setAttribute("rulesForm",rulesForm);
		String busTypes = request.getParameter("busTypes");
		rulesForm.setBusTypes(busTypes);
		SysUser sysUser = getVisitor(request);
		//查看用户权限
		String roleStr = rulesService.setUserRoles(request,rulesForm);
		//查询文档列表
		ItemPage itemPage =(ItemPage) rulesService.queryDocumentsItemPage(rulesForm);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		return "/dc/rules/rulesDoc";
	}
	
	/**  
	 * 跳转至文档上传页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "docUploads", method = {RequestMethod.GET})
	public String docUploads(HttpServletRequest request) throws Exception{
		String fileId = request.getParameter("fileId");
		
		//查看用户权限
		String roleStr = rulesService.setUserRoles(request,new RulesForm());
		//查询文档信息
		RulesFileUpload rulesFileUpload = null;
		//如果有ID，查询信息
		if(StringUtils.isNotEmpty(fileId)){
			rulesFileUpload = (RulesFileUpload) rulesService.getEntity(RulesFileUpload.class, fileId);
		}
		if(rulesFileUpload == null){
			rulesFileUpload = new RulesFileUpload();
			fileId = UUID.randomUUID().toString();//临时ID
			rulesFileUpload.setFileId(fileId);//设置ID，保存时需更新
		}
		request.setAttribute("fileTempId", fileId);
		request.setAttribute("rulesFileUpload", rulesFileUpload);
		//文档上传页面
		return "/dc/rules/docUploads";
	}
	
	
	/**
	 * 制度相关文档保存
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "docSubmit", method = {RequestMethod.GET, RequestMethod.POST})
	public void docSubmit(HttpServletRequest request,HttpServletResponse response,RulesFileUpload rulesFileUpload){
		String types = request.getParameter("types");//文档类型
		String busTypes = request.getParameter("busTypes");//业务类型
		String fileTempId = request.getParameter("fileTempId");//文档临时编号
		SysUser sysUser = getVisitor(request);
		String result = "0";
		try {
			if(sysUser != null){
				rulesFileUpload.setCreateTime(new Date());
				rulesFileUpload.setCreateUserid(sysUser.getUserId());
				rulesFileUpload.setCreateUsername(sysUser.getUserName());
			}
			rulesFileUpload.setTypes(types);
			rulesFileUpload.setBusTypes(busTypes);
			if(StringUtils.isNotEmpty(rulesFileUpload.getFileId())){
				RulesFileUpload rulesFileUploadtmp = (RulesFileUpload) rulesService.getEntity(RulesFileUpload.class, rulesFileUpload.getFileId());
				//新增相关文档
				if(rulesFileUploadtmp == null){
					rulesService.saveEntity(rulesFileUpload);
				}else{
					//更新相关文档
					if(rulesFileUploadtmp != null){
						rulesFileUpload.setCreateTime(rulesFileUploadtmp.getCreateTime());	
						rulesFileUpload.setCreateUserid(rulesFileUploadtmp.getCreateUserid());
						rulesFileUpload.setCreateUsername(rulesFileUploadtmp.getCreateUsername());
					}
					rulesFileUpload.setUpdateTime(new Date());
					if(null != sysUser){
						rulesFileUpload.setUpdateUserid(sysUser.getUserId());
						rulesFileUpload.setUpdateUsername(sysUser.getUserName());
					}
					rulesService.updateEntity(rulesFileUpload);
				}
			}else{
				//新增相关文档
				rulesService.saveEntity(rulesFileUpload);
			}
			if(StringUtils.isNotEmpty(fileTempId)){
				rulesService.updateFileTempId(fileTempId,rulesFileUpload.getFileId(),types,busTypes);
			}
			result = "1";
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
