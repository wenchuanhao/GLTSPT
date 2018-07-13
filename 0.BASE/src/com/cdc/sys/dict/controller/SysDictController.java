package com.cdc.sys.dict.controller;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.trustel.system.SystemConstant;

import com.cdc.sys.dict.form.SysParameterForm;
import com.cdc.sys.dict.form.SysParameterTypeForm;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.model.SysParameterType;
import com.cdc.sys.dict.service.ISysParameterService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;
import com.trustel.common.SerialNo;
import com.util.ExcelUtil;

/**
 * 
 * @Description: 参数管理
 * @Author: zengkai
 * @UpdateDate: 2016-4-28 下午04:45:05
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/dict/*")
public class SysDictController extends CommonController{
	private Log logger = LogFactory.getLog(getClass());
	@Autowired
	private ISysParameterService parameterService;
	
	@RequestMapping(value = "queryDictList", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryDictList(HttpServletRequest request, SysParameterTypeForm form) {
		try {
			ItemPage itemPage = parameterService.queryParameterType(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dc/sys/dict/dictList";
	}
	
	
	/**
	 * 新增参数类型页面
	 * @return
	 */
	@RequestMapping(value = "addDict", method = RequestMethod.GET)
	public String addDict() {
		return "dc/sys/dict/dictAdd";
	}
	/**
	 * 编辑参数类型页面
	 * @return
	 */
	@RequestMapping(value = "editDict", method = RequestMethod.GET)
	public String editDict(HttpServletRequest request) {
		String id = request.getParameter("id");
		SysParameterType sysParameterType = null;
		List<SysParameter> sysParameter = null;
		try {
			if(StringUtils.isNotEmpty(id)){
				sysParameterType = parameterService.queryParameterType(id);
			}
			if(sysParameterType != null && StringUtils.isNotEmpty(sysParameterType.getParameterTypeId())){
				sysParameter = parameterService.checkParameter("'"+sysParameterType.getParameterTypeId()+"'");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("sysParameter", sysParameter);
		request.setAttribute("size", sysParameter.size());
		request.setAttribute("sysParameterType", sysParameterType);
		return "dc/sys/dict/dictEdit";
	}
	
	/**
	 * 新增参数
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "submit", method = { RequestMethod.GET,RequestMethod.POST })
	public void submit(HttpServletRequest request,HttpServletResponse response,SysParameterType parameterType,SysParameterForm parameter) {
		String result = "0";
		try {
			parameterType.setCreateTime(new Date());
			parameterType.setCreaterId(getVisitor(request).getAccount());
			result = parameterService.submit(parameterType,parameter);
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @author ZENGKAI
	 *  删除参数数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="delDict",method={ RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public void delDict(HttpServletRequest request,HttpServletResponse response){
		String spId = request.getParameter("spId");
		String result = "0";
		try{
			if(StringUtils.isNotEmpty(spId)){
				String[] spIds = spId.split(",");
				for (String id : spIds) {
					//删除参数数据
					parameterService.delDict(id);
				}
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 导出功能
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportParameterType", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportParameterType(HttpServletRequest request, HttpServletResponse response,SysParameterTypeForm form) {
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			HSSFWorkbook book = new HSSFWorkbook();
			  
			String[] header = {"参数类型ID","参数类型编码","参数类型值","参数类型名称","参数类型描述","创建者","是否允许修改","创建时间","参数类型状态"};
			
			
			String[] header2 = {"参数ID","所属参数类型ID","参数编码","参数名称","参数值","参数描述","是否允许修改","创建者","创建时间","排序","参数状态"};
			form.setPageSize(65525);
			ItemPage itemPage = parameterService.queryParameterType(form);
			List<Object> list = (List<Object>) itemPage.getItems();
			
			String[] ids = request.getParameterValues("subBox");
			if(ids == null || ids.length == 0) return;
			List<String> idList = Arrays.asList(ids);
			
			StringBuffer sb = new StringBuffer();
			List<Object[]> arrList = new ArrayList<Object[]>() ;// 结果： List<Object[]>
			for(Object object : list) {
				Field[] field = object.getClass().getDeclaredFields();
				Object[] obj = new Object[field.length - 1];
				int i = 0;
				for (int j = 0; j < field.length; j++) {  
					// 获取属性的名字  
					String name = field[j].getName();  
					if("serialVersionUID".equals(name)){
						continue;
					}
					// 将属性的首字符大写，方便构造get，set方法  
					String getMethod = name.substring(0, 1).toUpperCase() + name.substring(1);  
					Method m = object.getClass().getMethod("get" + getMethod);
					obj[i] = m.invoke(object);
					
					if(getMethod.equals("AllowUpdate")){
						if(obj[i] != null && obj[i].equals("1")){
							obj[i] = "是";
						}else{
							obj[i] = "否";
						}
					}
					
					if(getMethod.equals("Status")){
						if(obj[i] != null && obj[i].equals("1")){
							obj[i] = "已存";
						}else{
							obj[i] = "删除";
						}
					}
					i++;
				}
				
				if(idList.contains(object.getClass().getMethod("getParameterTypeId").invoke(object).toString())){
					sb.append("'"+object.getClass().getMethod("getParameterTypeId").invoke(object)+"',");
					arrList.add(obj);
				}
				
			}
			
			List<SysParameter> list1 = parameterService.checkParameter(sb.toString().substring(0,sb.length()-1));
			
			List<Object[]> list2 = new ArrayList<Object[]>();
			
			for (SysParameter sysParameter : list1) {

				Field[] field = sysParameter.getClass().getDeclaredFields();
				Object[] obj = new Object[field.length - 1];
				int i = 0;
				for (int j = 0; j < field.length; j++) {  
					// 获取属性的名字  
					String name = field[j].getName();  
					if("serialVersionUID".equals(name)){
						continue;
					}
					// 将属性的首字符大写，方便构造get，set方法  
					String getMethod = name.substring(0, 1).toUpperCase() + name.substring(1);  
					Method m = sysParameter.getClass().getMethod("get" + getMethod);
					obj[i] = m.invoke(sysParameter);
					if(getMethod.equals("AllowUpdate")){
						if(obj[i].equals("1")){
							obj[i] = "是";
						}else{
							obj[i] = "否";
						}
					}
					
					if(getMethod.equals("Status")){
						if(obj[i].equals("1")){
							obj[i] = "已存";
						}else{
							obj[i] = "删除";
						}
					}
					i++;
				}
				list2.add(obj);
			}
			
			//文件名
			String fileName = "参数类型列表" + SerialNo.getUNID();
			ExcelUtil.exportForExcel(header,fileName,arrList,book);
			ExcelUtil.exportForExcel(header2,"参数列表",list2,book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
		} catch (Exception e) {
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
	@RequestMapping(value="importParameterType",method={RequestMethod.POST})
	@ResponseBody
	public void importParameterType(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file") MultipartFile file){
		PrintWriter out = null;
		try {
			out=response.getWriter();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List list_sysParameterType = ExcelUtil.importFromExcel(file,0);
			int length = list_sysParameterType.size();
			List<SysParameterType> list_type = new ArrayList<SysParameterType>(length);
			for (int i = 1; i < length; i++) {
				String[] strs = (String[]) list_sysParameterType.get(i);
											
				SysParameterType sysParameterType = new SysParameterType();
				sysParameterType.setParameterTypeId(strs[0]);//参数类型ID
				sysParameterType.setParameterTypeCode(strs[1]);//参数类型编码
				sysParameterType.setParameterTypeValue(strs[2]);//参数类型值
				sysParameterType.setParameterTypeName(strs[3]);//参数类型名称
				sysParameterType.setParameterTypeDesc(strs[4]);//参数类型描述
				sysParameterType.setCreaterId(strs[5]);//创建者
				sysParameterType.setAllowUpdate(strs[6]);//是否允许修改
				sysParameterType.setCreateTime(sdf.parse(strs[7]));//创建时间	
				sysParameterType.setStatus(strs[8]);//参数类型状态
				
				list_type.add(sysParameterType);
			}
			
			List list_sysParameter = ExcelUtil.importFromExcel(file,1);
			length = list_sysParameter.size();
			List<SysParameter> list_parameter = new ArrayList<SysParameter>(length);
			for (int i = 1; i < length; i++) {
				String[] strs = (String[]) list_sysParameter.get(i);
											
				SysParameter sysParameter = new SysParameter();
				sysParameter.setParameterId(strs[0]);//参数ID
				sysParameter.setParameterTypeId(strs[1]);//所属参数类型ID
				sysParameter.setParameterCode(strs[2]);//参数编码
				sysParameter.setParameterName(strs[3]);//参数名称
				sysParameter.setParameterValue(strs[4]);//参数值
				sysParameter.setParameterDesc(strs[5]);//参数描述
				sysParameter.setAllowUpdate(strs[6]);//是否允许修改
				sysParameter.setCreaterId(strs[7]);//创建者
				sysParameter.setCreateTime(sdf.parse(strs[8]));//创建时间	
				if(StringUtils.isNotEmpty(strs[9])){
					Double d = Double.parseDouble(strs[9]);
					sysParameter.setOrderId((d.intValue()));//排序
				}
				sysParameter.setStatus(strs[10]);//参数状态
				
				list_parameter.add(sysParameter);
			}
			
			parameterService.importParameterType(list_type,list_parameter);
			out.print("1");
			out.close();
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
	}
}
