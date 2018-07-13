package com.cdc.sys.dict.controller;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.trustel.system.SystemConstant;

import com.cdc.sys.dict.form.SysParameterTypeForm;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.model.SysParameterType;
import com.cdc.sys.dict.service.ISysParameterService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.ItemPage;
import com.trustel.common.SerialNo;
import com.util.ExcelUtil;

/**
 * 
 * @Description: 参数类型管理
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/paramtype/*")
public class SysParameterTypeController extends CommonController{

	@Autowired
	private ISysParameterService parameterService;
	
	@Autowired
	private ISysLogService sysLogService;
	
	@RequestMapping(value = "listParameter", method = RequestMethod.GET)
	public String listParameter() {
		return "sys/paramtype/listParameter";
	}
	/**
	 * 查询所有参数类型
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryParameterType", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryParameterType(HttpServletRequest request, SysParameterTypeForm form) {
		try {
			//form.setPageSize(5);
			ItemPage itemPage = parameterService.queryParameterType(form);
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/paramtype/queryParameterType";
	}

	/**
	 * 查看参数类型页面
	 * @param request
	 * @param ParId
	 * @return
	 */
	@RequestMapping(value = "parameterTypeDetail/{parId}", method = RequestMethod.GET)
	public String parameterTypeDetail(HttpServletRequest request, @PathVariable String parId) {
		try {
			request.setAttribute("parameterType",parameterService.queryParameterType(parId));
			request.setAttribute("location", "sys/paramtype/queryParameterType");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "sys/paramtype/detailParameterType";
	}
	
	/**
	 * 编辑参数类型页面
	 * @param request
	 * @param ParId
	 * @return
	 */
	@RequestMapping(value = "toModifyParameterType/{parId}", method = RequestMethod.GET)
	public String toModifyParameterType(HttpServletRequest request, @PathVariable String parId) {
		try {
			request.setAttribute("parameterType",parameterService.queryParameterType(parId));
			request.setAttribute("location", "sys/paramtype/queryParameterType");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/paramtype/modifyParameterType";
	}
	/**
	 * 编辑参数类型
	 * @param parameterType
	 * @return
	 */
	@RequestMapping(value = "modifyParameterType", method = RequestMethod.POST)
	public String doModifyParameterType(HttpServletRequest request,SysParameterType parameterType) {
		try {
			parameterService.updateParameterType(parameterType);
			sysLogService.log(request, getVisitor(request), "系统管理--系统参数",
					"管理参数类型", "更新了【" + parameterType.getParameterTypeName() + "】参数类型", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sys/paramtype/queryParameterType";
	}
	
	/**
	 * 批量删除参数类型
	 * @param paramTyprIds
	 * @return
	 */
	@RequestMapping(value = "deleteParameterType", method = RequestMethod.POST)
	public String doDeleteParameterType(HttpServletRequest request,String paramTyprIds) {
		if (null != paramTyprIds && !"".equals(paramTyprIds)) {
			String[] ids = paramTyprIds.split(",");
			String deleteName ="";
			try {
					for (String sptId : ids) {
						SysParameterType sysParameterType = parameterService.queryParameterType(sptId);
						deleteName += sysParameterType.getParameterTypeName() + "，";
						parameterService.deleteParameterType(sptId);
					}
					sysLogService.log(request, getVisitor(request), "系统管理--系统参数", "删除参数类型",
							"删除参数类型【" + deleteName.substring(0,deleteName.length()-1)+ "】", new Date(), "3", new Date(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/sys/paramtype/queryParameterType";
	}
	
	/**
	 * 单行删除参数类型
	 * @param paramTyprIds
	 * @return
	 */
	@RequestMapping(value = "deleteParameterTypeById/{paramTyprId}", method = RequestMethod.GET)
	public String deleteParameterTypeById(HttpServletRequest request,@PathVariable String paramTyprId) {
		if (null != paramTyprId && !"".equals(paramTyprId)) {
			try {	
				SysParameterType sysParameterType = parameterService.queryParameterType(paramTyprId);
				parameterService.deleteParameterType(paramTyprId);	
				sysLogService.log(request, getVisitor(request), "系统管理--系统参数", "删除参数类型",
							"删除参数类型【" + sysParameterType.getParameterTypeName() + "】" , new Date(), "3", new Date(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/sys/paramtype/queryParameterType";
	}
	/**
	 * 验证用户参数类型名称的唯一
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checknameuniqueness", method = RequestMethod.POST)
	public @ResponseBody String checkNameUniqueness(HttpServletRequest request,HttpServletResponse response,String parameterTypeName){
		SysParameterType parameterType=null;
		try
		{
			parameterType = parameterService.queryParameterTypeByName(parameterTypeName);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if(parameterType==null)
			return "0";
		else
			return "1";
	}
	/**
	 * 验证参数类型下是否有参数
	 * @param request
	 * @param response
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "checkSub", method = RequestMethod.POST)
	public @ResponseBody String checkSub(HttpServletRequest request,HttpServletResponse response,String parameterTypeIds){
		try
		{	
			if(parameterTypeIds==null){
				return "2";
			}
			String[] ids = parameterTypeIds.split(",");
			for (String sptId : ids) {
				parameterTypeIds="'"+sptId+"',";
			}
			if(!parameterTypeIds.equals("")){
				parameterTypeIds = parameterTypeIds.substring(0,parameterTypeIds.length()-1);
			}
			List<SysParameter> list=parameterService.checkParameter(parameterTypeIds);
			if(list!=null && list.size()!=0){
				return "1";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return "1";
		}
		return "0";
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
			form.setPageSize(65525);
			ItemPage itemPage = parameterService.queryParameterType(form);
			List<Object> list = (List<Object>) itemPage.getItems();
			
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
					i++;
				}
				arrList.add(obj);
				
			}
			//文件名
			String fileName = "参数类型列表" + SerialNo.getUNID();
			ExcelUtil.exportForExcel(header,fileName,arrList,book);
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
			List list = ExcelUtil.importFromExcel(file,0);
			List<SysParameterType> list_type = new ArrayList<SysParameterType>();
			int length = list.size();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 1; i < length; i++) {
				String[] strs = (String[]) list.get(i);
											
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
//			parameterService.importParameterType(list_type);
			out.print("1");
			out.close();
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
	}
}
