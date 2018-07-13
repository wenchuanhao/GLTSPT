package com.cdc.dc.project.zxmht.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.dc.project.jsxm.controller.JsxmController;
import com.cdc.dc.project.zxmht.model.HtKz;
import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.dc.project.zxmht.service.IHtKzService;
import com.cdc.dc.project.zxmht.service.IZxmHtService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 合同开支
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/htKz/")
public class HtKzController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IHtKzService htKzService;
	
    private Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,HtKz htKz) throws Exception{
		
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");
		HSSFWorkbook book = new HSSFWorkbook();
		//样式设置
		HSSFCellStyle style = book.createCellStyle();
		HSSFFont font = book.createFont();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中对齐
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);

		// 表头状态
		String[] header = {"投资编号","合同编号","合同名称","合同不含税金额	（万元）","合同含税金额（万元）","合同对方","合同类型","累计形象进度/MIS接收金额（万元）",
				"本年形象进度/MIS接收金额（万元）","累计转资金额（万元）","本年转资金额（万元）","累计付款金额（万元）"," 	负责人","记录时间"};
		 	 	 	 	 	 	 			
		htKz.setPageSize(Integer.MAX_VALUE - 1);
		
		ItemPage itemPage = htKzService.findHtKz(htKz);
		
		List<Object[]> list = (List<Object[]>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		String[] ids = request.getParameterValues("subBox");
		if(ids == null || ids.length == 0) return;
		List<String> idList = Arrays.asList(ids);
		
		for (Object[] vo : list) {
			Object [] obj =  new Object[14];
			HtKz  zh = (HtKz)vo[0];
			ZxmHt zxm = zh.getZxmHt();
			if(idList.contains(zh.getId())){
				obj[0] = zh.getColumn02();
				obj[1] = zxm.getColumn01();
				obj[2] = zxm.getColumn03();
				obj[3] = zh.getColumn03();			
				obj[4] = zh.getColumn04();
				obj[5] = zh.getColumn05();
				if(StringUtils.isNotBlank(zh.getColumn06())){
					if(zh.getColumn06().equals("1")){
						obj[6] = "费用类";
					}else if(zh.getColumn06().equals("2")){
						obj[6] = "订单类";
					}
				}
				obj[7] = zh.getColumn07();
				obj[8] = zh.getColumn08();
				obj[9] = zh.getColumn09();
				obj[10] = zh.getColumn10();
				obj[11] = zh.getColumn11();
				obj[12] = zh.getColumn13SysUser().getUserName();
				obj[13] = zh.getColumn12();
				
				listO.add(obj);
			}
		}
		try {
			//文件名
			String fileName = "合同开支列表";
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			sysLogService.log(request, getVisitor(request), "工程管理--合同开支","导出合同开支", "导出【合同开支】", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,HtKz htKz) throws Exception{
    	
    	SysUser sysUser = getVisitor(request); 
    	
    	JsxmController.userRole(request);
    	
    	String role = (String)request.getAttribute("role");
    	
    	if(JsxmController.GC_XMFZR.equals(role)){
    		htKz.setCreateUserId(sysUser.getUserId());
		}
    	ItemPage itemPage = htKzService.findHtKz(htKz);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	request.setAttribute("htKz", htKz);
    	
        return "/dc/project/htKz/list";
    }	
    
    /**
     * 查看
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET,RequestMethod.POST})
    public String edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	request.setAttribute("pageSource", request.getParameter("pageSource"));
    	HtKz htKz = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			htKz = htKzService.findHtKzById(id);
		}else {
			htKz = new HtKz();
		}
    	request.setAttribute("vo", htKz);
    	
    	return "/dc/project/htKz/edit";
    }    
   
    public void add_C(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	HtKz htKz = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			htKz = htKzService.findHtKzById(id);
		}else {
			htKz = new HtKz();
			htKz.setColumn12(new Date());
		}
		
		String htId = request.getParameter("htId");
		if(htId != null){
			IZxmHtService zxmHtService = (IZxmHtService)SpringHelper.getBean("zxmHtService");
			ZxmHt zxmHt = zxmHtService.findZxmHtById(htId);
			htKz.setColumn01(htId);
			htKz.setColumn02(zxmHt.getZxm().getTzbm().getColumn02());
			htKz.setColumn03(zxmHt.getColumn09());
			htKz.setColumn04(zxmHt.getColumn11());
			htKz.setColumn05(zxmHt.getColumn14());
			htKz.setColumn06(zxmHt.getColumn22());
			htKz.setColumn13(zxmHt.getColumn21Id());
		}
		
    	request.setAttribute("vo", htKz);
    }
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add1", method = {RequestMethod.GET,RequestMethod.POST})
    public String add1(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	add_C(request, response);
    	return "/dc/project/htKz/add1";
    }
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	add_C(request, response);
    	return "/dc/project/htKz/add";
    }

    public  void saveOrUpdate_C(HttpServletRequest request, HttpServletResponse response,HtKz htKz) throws Exception{
		SysUser sysUser = getVisitor(request);
		
		String mess = "";
    	
		try {
			String id = htKz.getId();
			if (id == null || id.equals("")) {
				htKz.setCreateDate(new Date());
				htKz.setCreateUserName(sysUser.getUserName());
				htKz.setCreateUserId(sysUser.getUserId());
				htKzService.save(htKz);
				request.setAttribute("form_S", "add");
				sysLogService.log(request, getVisitor(request), "工程管理--合同开支","新增合同开支", "新增合同开支", new Date(), "3", new Date(), null);
			}else {
				htKz.setUpdateDate(new Date());
				htKz.setUpdateUserName(sysUser.getUserName());
				htKz.setUpdateUserId(sysUser.getUserId());
				
				HtKz oldHtKz = htKzService.findHtKzById(htKz.getId());
				
				htKz.setCreateDate(oldHtKz.getCreateDate());
				htKz.setCreateUserName(oldHtKz.getCreateUserName());
				htKz.setCreateUserId(oldHtKz.getCreateUserId());
				htKz.setDeleteFlag(oldHtKz.getDeleteFlag());
				
				BeanUtils.copyProperties(htKz, oldHtKz);
				
				htKzService.update(oldHtKz);
				request.setAttribute("form_S", "modify");
				sysLogService.log(request, getVisitor(request), "工程管理--合同开支","修改合同开支", "修改合同开支", new Date(), "3", new Date(), null);
			}
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		
    	request.setAttribute("vo", htKz);
    	request.setAttribute("mess", mess);
    }
    
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate1", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate1(HttpServletRequest request, HttpServletResponse response,HtKz htKz) throws Exception{
    	saveOrUpdate_C(request, response, htKz);
    	return "/dc/project/htKz/add1";
    }
    
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate(HttpServletRequest request, HttpServletResponse response,HtKz htKz) throws Exception{
    	saveOrUpdate_C(request, response, htKz);
    	return "/dc/project/htKz/add";
    }


    /**
     * 删除
     */
    @RequestMapping(value = "delete", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String result = "0";
		try{
			if(id != null){
				htKzService.delete(id);
				sysLogService.log(request, getVisitor(request), "工程管理--合同开支","删除合同开支", "删除合同开支", new Date(), "3", new Date(), null);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		list(request, response, new HtKz());
		
		result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);

    }
    
    /**
	 * 用户联想查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchUser", method = {RequestMethod.POST})
	public  void searchUser(HttpServletRequest request,HttpServletResponse response) {
    	try {
    	    response.setContentType("text/html; charset=utf-8");
    	    String userValue = request.getParameter("userValue");
    		String result = "";
  	        if(userValue != null && !"".equals(userValue)){
  	        	JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
  	        	String userNameSql = " u.user_name like '%"+userValue+"%'";
  	        	String accountSql = " or u.account like '%"+userValue+"%'";
  	        	String sql = 
  	        			"select u.user_id,u.user_name,u.account,o.org_name from sys_user u,sys_organization o where u.organization_id = o.organization_id(+)	" +
  	        			" and (" +userNameSql+accountSql+")";
  	        	
  	        	List<Map<String, Object>> listMap = (List<Map<String, Object>>)jdbcTemplate.queryForList(sql);
  	        	
                JSONArray json = new JSONArray();
                
                if (listMap != null && listMap.size() > 0) {
                    for (int i=0;i<listMap.size();i++) {
                    	
                    	Map<String, Object> objMap = listMap.get(i);
                    	JSONObject jsonObject = new JSONObject();
                        JSONArray jsonArray = new JSONArray();
                        
                        jsonObject.put("userId",objMap.get("user_id"));
                        jsonObject.put("userName",objMap.get("user_name"));
                        jsonObject.put("account",objMap.get("account"));
                        jsonObject.put("orgName",objMap.get("org_name"));
                        jsonArray.add(jsonObject);

                        json.add(jsonArray);
                    }
                }
  	        	result = json.toString();
  	        }
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
    		writer.close();
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}

}
