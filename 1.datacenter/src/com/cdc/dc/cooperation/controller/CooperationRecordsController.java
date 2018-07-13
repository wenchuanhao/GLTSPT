package com.cdc.dc.cooperation.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.common.properties.DCConfig;
import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.cooperation.model.CooperationDatasourceRecords;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.model.CooperationTypeRoleUser;
import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.dc.cooperation.util.ComparatorObject;
import com.cdc.dc.metadata.yuan.model.YuanTableColumnManage;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.inter.client.excel.ReaderWriterFile;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;
/**
 * 合交经分数据录入类
 * @author ZENGKAI
 * @date 2016-05-24 09:45:29
 */
@Controller
@RequestMapping(value = "/copperationController/")
public class CooperationRecordsController extends DefaultController{
	@Autowired
	private ICooperationService cooperationService;
	@Autowired
	private IEnterpriseService enterpriseService;
	 
	/**
	 * 数据源类型管理首页
	 */
	@RequestMapping(value="enterRecord", method = {RequestMethod.POST,RequestMethod.GET})
	public String enterRecord(HttpServletRequest request) {
		//查询用户具有录入权限的数据源
		SysUser sysUser = getVisitor(request);
		SysRole sysRole = cooperationService.querySysRoleByCode(SysParamHelper.getSysParamByCode(CooperationCommon.DICT_TYPE, CooperationCommon.DS_IMPORTER).getParameterValue());
		List<CooperationTypeRoleUser> list = cooperationService.queryCooperationTypeRoleUserList(sysRole.getRoleId(),sysUser.getUserId());
		
		//二级
		List<CooperationDatasourceType> datasourceTypeList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(list,null), CooperationCommon.datasourceStatus_Save);
		request.setAttribute("datasourceTypeList", datasourceTypeList);
		request.setAttribute("sonList", JSONArray.fromObject(datasourceTypeList).toString());
		
		//一级
		List<CooperationDatasourceType> parentDatasourceList = cooperationService.queryCooperationDatasourceByIds(CooperationCommon.getIds(null,datasourceTypeList), CooperationCommon.datasourceStatus_Save);
		request.setAttribute("parentDatasourceList", parentDatasourceList);
		request.setAttribute("fileTempId", UUID.randomUUID().toString());
		
		//默认录入上个月数据
		Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        request.setAttribute("month",new SimpleDateFormat("yyyy-MM").format(c.getTime()));
		
		return "/dc/cooperation/dataEnter";
	}
	/**
	 * 导入提交动作
	 * @param request
	 * @param rulesInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submit", method = {RequestMethod.POST})
	public String submit(HttpServletRequest request) throws Exception{
		SysUser sysUser = getVisitor(request);
		String datasourceRecordsId = request.getParameter("datasourceRecordsId");//导入记录id,为空时是录入
		String datasourceId = request.getParameter("datasourceId");//数据源类型
		String month = request.getParameter("month");//月份
		String status = request.getParameter("status");
		CooperationDatasourceType datasourceType = null;
		if(StringUtils.isNotEmpty(datasourceId)){
			datasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
		}
		//根据记录id判断是否导入或录入
		if(StringUtils.isNotEmpty(datasourceRecordsId)){
			CooperationDatasourceRecords datasourceRecords = (CooperationDatasourceRecords) enterpriseService.queryEntity(CooperationDatasourceRecords.class, datasourceRecordsId);
			datasourceRecords.setStatus(status);//状态，提交或暂存
			//从session中获取数据并保存
			List<String[]> list = (List<String[]>) request.getSession().getAttribute(SystemConstant.ATTRIBUTE_LIST);
			List<Object> resultList = new ArrayList<Object>();
			if(list != null && list.size() > 0){
				for (int j = 0; j < list.size(); j++) {
					Object obj = Class.forName(datasourceType.getInterfaceTableName()).newInstance();
					String[] strs = (String[]) list.get(j);
					Field[] fs = obj.getClass().getDeclaredFields();
					int fsLen = fs.length;
					fs[fsLen - 3].setAccessible(true);
					fs[fsLen - 3].set(obj, new Date());//创建时间
					fs[fsLen - 2].setAccessible(true);
					fs[fsLen - 2].set(obj, datasourceRecords.getRecordId());//外键,记录ID
					//-1是为了去除校验位
					for(int i = 0;i < strs.length - 2;i++)
					{
						Field f = fs[i];
						f.setAccessible(true); // 设置些属性是可以访问的
						String type = f.getType().toString();// 得到此属性的类型
						if(type.endsWith("String") && strs[i] != null){
							f.set(obj,strs[i].trim()); // 给属性设值
						}
						
					}
					resultList.add(obj);
				}
			}
			cooperationService.saveEntity(resultList);
			cooperationService.submit(datasourceRecords,sysUser);
			//清除导入并提交的session
			request.getSession().removeAttribute(SystemConstant.ATTRIBUTE_LIST);
			request.getSession().removeAttribute(SystemConstant.ATTRIBUTE_MESSAGE);
		}else{
			//新建数据录入记录
			CooperationDatasourceRecords datasourceRecords = new CooperationDatasourceRecords();
			datasourceRecords.setRecordId(UUID.randomUUID().toString());
			datasourceRecords.setDatasourceId(datasourceId);
//				datasourceRecords.setFileId();//录入无附件信息
			if(StringUtils.isNotEmpty(month)){
				datasourceRecords.setMonth(month);
			}else{
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, -1);
				datasourceRecords.setMonth(new SimpleDateFormat("yyyy-MM").format(c.getTime()));//上个月数据
			}
			datasourceRecords.setCreateTime(new Date());
			datasourceRecords.setCreateUserid(sysUser.getUserId());
			datasourceRecords.setCreateUsername(sysUser.getUserName());
			datasourceRecords.setStatus(status);//提交
			cooperationService.saveEntity(datasourceRecords);
			
			//实体初始化
			Object obj = Class.forName(datasourceType.getInterfaceTableName()).newInstance();
			String[] values = request.getParameterValues("values");
			if(values != null){
				Field[] fs = obj.getClass().getDeclaredFields();
				int fsLen = fs.length;
				fs[fsLen - 3].setAccessible(true);
				fs[fsLen - 3].set(obj, new Date());//创建时间
				fs[fsLen - 2].setAccessible(true);
				fs[fsLen - 2].set(obj, datasourceRecords.getRecordId());//外键,记录id
				for(int i = 0;i < values.length;i++)
				{
					Field f = fs[i];
					f.setAccessible(true); // 设置些属性是可以访问的
					String type = f.getType().toString();// 得到此属性的类型
					if(type.endsWith("String") && values[i] != null){
						f.set(obj,values[i].trim()); // 给属性设值
					}
					
				}
				//保存实体
				cooperationService.saveEntity(obj);
				//提交
				cooperationService.submit(datasourceRecords,sysUser);
			}
		}

		//我的制度
		return "redirect:/copperationController/myAll";
	}

	/**  
	 * 跳转至明细页面页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dataEnterDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public String dataEnterDetail(HttpServletRequest request,CooperationForm cooperationForm) throws Exception{
		String id= request.getParameter("id");
		String flag = request.getParameter("flag");
		request.setAttribute("flag",flag );
		if(StringUtils.isNotEmpty(id)){
			CooperationDatasourceRecords datasourceRecords = (CooperationDatasourceRecords) enterpriseService.queryEntity(CooperationDatasourceRecords.class, id);
			if(datasourceRecords != null){
				CooperationDatasourceType cdt = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceRecords.getDatasourceId());
				if("MANYD".equals(cdt.getInterfaceTableName())){
					//从数据字典查询数据源明细的表名
					SysParameter sysParameter = SysParamHelper.getSysParamByCode(cdt.getDatasourceCode());
					if(sysParameter != null){
						String tabStr = sysParameter.getParameterValue();
						if(StringUtils.isNotBlank(tabStr)){
							String[] tables = tabStr.split(",");
							request.setAttribute("tables", tables);
						}
					}
					return "/dc/cooperation/datasourceDetailList";
				}
				//根据表名查询表信息，字段，注释等
				List talbeInfoList =  cooperationService.queryReportTableVoByTableName(cdt);
				ItemPage itemPage =(ItemPage) cooperationService.queryReportsItemPage(datasourceRecords,cdt,cooperationForm);
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
				request.setAttribute("talbeInfoList",talbeInfoList );
			}
		}
		return "/dc/cooperation/dataEnterDetail";
	}
	/**  
	 * 跳转至明细页面页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dataEnterDetailOfSession", method = {RequestMethod.GET,RequestMethod.POST})
	public String dataEnterDetailOfSession(HttpServletRequest request,CooperationForm cooperationForm) throws Exception{
		String id= request.getParameter("id");
		String flag = request.getParameter("flag");
		request.setAttribute("flag",flag );
		if(StringUtils.isNotEmpty(id)){
			CooperationDatasourceRecords datasourceRecords = (CooperationDatasourceRecords) enterpriseService.queryEntity(CooperationDatasourceRecords.class, id);
			if(datasourceRecords != null){
				CooperationDatasourceType cdt = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceRecords.getDatasourceId());
				if("MANYD".equals(cdt.getInterfaceTableName())){
					//从数据字典查询数据源明细的表名
					SysParameter sysParameter = SysParamHelper.getSysParamByCode(cdt.getDatasourceCode());
					if(sysParameter != null){
						String tabStr = sysParameter.getParameterValue();
						if(StringUtils.isNotBlank(tabStr)){
							String[] tables = tabStr.split(",");
							request.setAttribute("tables", tables);
						}
					}
					return "/dc/cooperation/datasourceDetailList";
				}
				//根据表名查询表信息，字段，注释等
				List talbeInfoList =  cooperationService.queryReportTableVoByTableName(cdt);
//				ItemPage itemPage =(ItemPage) cooperationService.queryReportsItemPage(datasourceRecords,cdt,cooperationForm);
				//从session中获取数据并保存
				List list = (List) request.getSession().getAttribute(SystemConstant.ATTRIBUTE_LIST);
				List list_err = (List) request.getSession().getAttribute(SystemConstant.ATTRIBUTE_MESSAGE);
				int length = list.size();
				
				//zk  20160725
//				long tempPagecount = (length-1)/cooperationForm.getPageSize() +1 ;
//				if(cooperationForm.getPageIndex() > tempPagecount) cooperationForm.setPageIndex( (int)tempPagecount );
				
				//开始索引
				int beginIndex = (cooperationForm.getPageIndex() - 1) * cooperationForm.getPageSize();
				//结束索引
				int endIndex = cooperationForm.getPageIndex() * cooperationForm.getPageSize() > length ? length : cooperationForm.getPageIndex() * cooperationForm.getPageSize();
				
				List pageList = new ArrayList(cooperationForm.getPageSize());
				List errList = new ArrayList(cooperationForm.getPageSize());
				//遍历session list数据
				for (int i = beginIndex; i < endIndex; i++) {
					pageList.add(list.get(i));
					errList.add(list_err.get(i));
				}
				//生成分页结果
				ItemPage itemPage = new ItemPage(pageList, length, cooperationForm.getPageIndex(),cooperationForm.getPageSize());
				request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
				request.setAttribute("talbeInfoList",talbeInfoList );
				request.setAttribute("errList",JSONArray.fromObject(errList));
			}
		}
		return "/dc/cooperation/dataEnterDetailOfSession";
	}
	
	/**
	 * @author ZENGKAI
	 * 上传完附件之后
	 * @param request
	 * @param response
	 * @param file
	 */
	@RequestMapping(value="afterUploadFiles",method={RequestMethod.POST})
	@ResponseBody
	public void afterUploadFiles(HttpServletRequest request,HttpServletResponse response){
		String fileTempId = request.getParameter("fileTempId");//文件ID
		String datasourceId = request.getParameter("datasourceId");//数据源类型ID
		String month = request.getParameter("month");//月份
		SysUser sysUser = getVisitor(request);
		String result = "0";
		try {
			if(!StringUtils.isNotEmpty(fileTempId)){
				return;
			}
			if(!StringUtils.isNotEmpty(datasourceId)){
				return;
			}
			//上传的文件
			RulesFileUpload fileUpload = cooperationService.queryRulesFileUploadByInfoId(fileTempId);
			if(fileUpload == null){
				return;
			}
			//数据源类型配置
			CooperationDatasourceType datasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
			
			//新建数据录入记录
			CooperationDatasourceRecords datasourceRecords = new CooperationDatasourceRecords();
			datasourceRecords.setRecordId(UUID.randomUUID().toString());
			datasourceRecords.setDatasourceId(datasourceId);
			datasourceRecords.setFileId(fileUpload.getFileId());
			if(StringUtils.isNotEmpty(month)){
				datasourceRecords.setMonth(month);
			}else{
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, -1);
				datasourceRecords.setMonth(new SimpleDateFormat("yyyy-MM").format(c.getTime()));//上个月数据
			}
			datasourceRecords.setCreateTime(new Date());
			datasourceRecords.setCreateUserid(sysUser.getUserId());
			datasourceRecords.setCreateUsername(sysUser.getUserName());
			datasourceRecords.setStatus(CooperationCommon.datasourceRecordsStatus_DEL);//删除状态
			cooperationService.saveEntity(datasourceRecords);
			fileUpload.setRulesInfoid(datasourceRecords.getRecordId());
			cooperationService.updateEntity(fileUpload);
			//满意度导入
			if("MANYD".equals(datasourceType.getInterfaceTable())){
				ReaderWriterFile rwf = new ReaderWriterFile();
				rwf.readExcelFile(fileUpload.getFilePath());
				result = datasourceRecords.getRecordId();//满意度
				return;
			}
			//从文件导入数据
			List list = ExcelUtil.importFromExcel(new File(fileUpload.getFilePath()),0);
			
			if(list != null && list.size() > 1){
				list.remove(0);//清除标题行
				Object[] objs = (Object[]) list.get(0);//校验列表长度
				List<YuanTableColumnManage> mangList = cooperationService.queryManageByTableName(objs.length,datasourceType.getInterfaceTable());
				doCheckList(list,mangList,request);//校验数据
				result = datasourceRecords.getRecordId();
			}else{
				result = "1";//导入数据为空
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				PrintWriter out = response.getWriter();
				out.write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @author ZENGKAI
	 * 根据id查询录入字段
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="queryEnterById",method={RequestMethod.POST})
	@ResponseBody
	public void queryEnterById(HttpServletRequest request,HttpServletResponse response){
		String datasourceId = request.getParameter("datasourceId");
		if(StringUtils.isNotEmpty(datasourceId)){
			//数据源类型配置
			CooperationDatasourceType datasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
			//查询表字段和注解
			List list = cooperationService.queryReportTableVoByTableName(datasourceType);
			JSONArray object = JSONArray.fromObject(list);
			try {
				PrintWriter out = response.getWriter();
				out.write(object.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * @author ZENGKAI
	 * 取消--删除记录及导入session
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="cancel",method={RequestMethod.POST})
	@ResponseBody
	public void cancel(HttpServletRequest request,HttpServletResponse response){
		String datasourceRecordsId = request.getParameter("datasourceRecordsId");
		if(StringUtils.isNotEmpty(datasourceRecordsId)){
			//数据源类型配置
			CooperationDatasourceRecords cdr = (CooperationDatasourceRecords) enterpriseService.queryEntity(CooperationDatasourceRecords.class, datasourceRecordsId);
			if(cdr != null){
				cooperationService.delEntity(cdr);
			}
			request.getSession().removeAttribute(SystemConstant.ATTRIBUTE_LIST);
			request.getSession().removeAttribute(SystemConstant.ATTRIBUTE_MESSAGE);
			try {
				PrintWriter out = response.getWriter();
				out.write("1");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "checkEnter", method = {RequestMethod.POST})
	@ResponseBody
	public void checkEnter(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		json.put("code","0");
		json.put("message","录入数据正确！");

		String datasourceId = request.getParameter("datasourceId");//数据源类型
		String month = request.getParameter("month");//月份
		String[] values = request.getParameterValues("values");
		CooperationDatasourceType datasourceType = null;
		if(StringUtils.isNotEmpty(datasourceId)){
			datasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
		}
		//根据表明查询校验规则
		List<YuanTableColumnManage> mangList = cooperationService.queryManageByTableName(values.length,datasourceType.getInterfaceTable());
		
		if(values != null){
			for (int i = 0; i < values.length; i++) {
				JSONObject res = checkStr(mangList.get(i),values[i],i);
				if(!res.getBoolean("flag")){
					json.put("code","1");
					json.put("message",res.get("msg"));//提示信息
					break;
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.write(json.toString());
	}
	
	
	/**
	 * @author ZENGKAI
	 * 下载附件按附件id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "downloadtTemplate", method = RequestMethod.GET)
	public void downloadtTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		//附件ID
		String datasourceId = request.getParameter("datasourceId");
		if(StringUtils.isNotEmpty(datasourceId)){
			//数据源类型配置
			CooperationDatasourceType datasourceType = (CooperationDatasourceType) enterpriseService.queryEntity(CooperationDatasourceType.class, datasourceId);
			//模板路径，服务器路径 + 配置文件路径 + 数据源编号文件名
			String downLoadPath = request.getSession().getServletContext().getRealPath("/") + DCConfig.getProperty("TEMPLATE_DIR") + datasourceType.getDatasourceCode()+".xlsx";
			try {
				long fileLength = new File(downLoadPath).length();
				response.setContentType("application/x-msdownload;");
				response.setHeader("Content-disposition", "attachment; filename=" + new String((datasourceType.getDatasourceCode()+datasourceType.getDatasourceName()+"模板.xlsx").getBytes("gb2312"), "ISO8859-1" ));
				response.setHeader("Content-Length", String.valueOf(fileLength));
				bis = new BufferedInputStream(new FileInputStream(downLoadPath));
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bis != null)bis.close();
				if (bos != null)bos.close();
			}
		}
	}
	/**
	 * 对导入的数据进行校验，并将元数据和校验结果放入session
	 * @param list
	 * @param request
	 */
	private void doCheckList(List list,List<YuanTableColumnManage> mangList,HttpServletRequest request) {
		//清除原有数据
		request.getSession().removeAttribute(SystemConstant.ATTRIBUTE_LIST);
		request.getSession().removeAttribute(SystemConstant.ATTRIBUTE_MESSAGE);
		int length = list.size();
		//校验后的预览数据
		List<String[]> previewList = new ArrayList<String[]>(length);
		//校验结果数据
		List errorList = new ArrayList(length);
		
		//是否为空,长度校验,日期格式
		//循环
		for (int i = 0; i < length; i++) {
			Object[] objs = (Object[]) list.get(i);
			int objLen = objs.length;
			//预览数据
			String[] checkobj = new String[objLen + 2];
			//校验数据
			Object[] error = new Object[3];
			List err = new ArrayList(objLen);
			for (int j = 0; j < objLen; j++) {
				String temp = (String) objs[j];
				checkobj[j] = temp;
				JSONObject res = checkStr(mangList.get(j),temp,j);
				if(!res.getBoolean("flag")){
					err.add(res);
				}
			}
			checkobj[objLen] = "" + i;//添加校验位
			checkobj[objLen + 1] = String.valueOf(err.size());//添加错误个数
			error[0] = err;//校验结果
			error[1] = "" + i;//添加校验位
			error[2] = String.valueOf(err.size());//添加错误个数
			previewList.add(checkobj);//存入list
			errorList.add(error);
		}
		//根据校验位排序
		ComparatorObject comparator=new ComparatorObject();
	    Collections.sort(previewList, comparator);//预览数据
	    Collections.sort(errorList, comparator);//校验数据
		//存入session
		request.getSession().setAttribute(SystemConstant.ATTRIBUTE_LIST, previewList);//预览数据
		request.getSession().setAttribute(SystemConstant.ATTRIBUTE_MESSAGE, errorList);//校验数据
	}
	
	/**
	 * @author zk
	 * @param temp	被校验数据
	 * @param j	数据位置
	 * 表名、列表、类型、是否可为空、长度、格式（正则）……
	 * @return
	 */
	private JSONObject checkStr(YuanTableColumnManage check,String temp,int j) {
		JSONObject errinfo = new JSONObject();
		errinfo.put("index", j);//位置
		errinfo.put("flag", true);//是否正确
		errinfo.put("msg", "正确");//提示消息
		if(check == null){
			return errinfo;
		}
		//是否可为空
		if(!StringUtils.isNotEmpty(temp) && CooperationCommon.yTCMnullableNo.equals(check.getNullable())){
			errinfo.put("flag", false);//是否正确
			errinfo.put("msg", "数据不能为空");//提示消息
			return errinfo;
		}
		//如果内容为空
		if(!StringUtils.isNotEmpty(temp)){
			return errinfo;
		}
		//长度判断
		try {
			//字符长度
			if(temp.getBytes("utf-8").length > check.getDataLength()){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "数据字符长度不能大于"+check.getDataLength());//提示消息
				return errinfo;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//数值型数据校验0,整数
		if(CooperationCommon.yTCMdataTypeNumber.equals(check.getDataType()) && "0".equals(check.getDataFormat())){
			String regEx = "^[-+]?[0-9]+$";
			if(!temp.matches(regEx)){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "请填入整数");//提示消息
				return errinfo;
			}
		}
		//数值型数据校验0.00，保留两位数字的小数
		if(CooperationCommon.yTCMdataTypeNumber.equals(check.getDataType()) && "0.00".equals(check.getDataFormat())){
			String regEx = "[-+]?[0-9]+(\\.{0,1}[0-9]{1,2})?";
			if(!temp.matches(regEx)){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "请填入整数或者两位以内小数");//提示消息
				return errinfo;
			}
		}
		
		//时间型数值校验yyyy
		if(CooperationCommon.yTCMdataTypeDate.equals(check.getDataType()) && "yyyy".equals(check.getDataFormat())){
			String regEx = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})";
			
			if(!temp.matches(regEx)){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "年份格式不正确，应为“2016”格式");//提示消息
			}
		}
		
		//时间型数值校验yyyy-MM
		if(CooperationCommon.yTCMdataTypeDate.equals(check.getDataType()) && "yyyy-MM".equals(check.getDataFormat())){
			String regEx = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(0[1-9]|1[0-2])";
			
			if(!temp.matches(regEx)){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "月份格式不正确，应为“2016-01”格式");//提示消息
			}
		}
		
		//时间型数值校验yyyy-MM-dd
		if(CooperationCommon.yTCMdataTypeDate.equals(check.getDataType()) && "yyyy-MM-dd".equals(check.getDataFormat())){
			String regEx = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
			if(!temp.matches(regEx)){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "日期格式不正确，应为“2016-01-01”格式");//提示消息
		    }
		}

		return errinfo;
	}
}
