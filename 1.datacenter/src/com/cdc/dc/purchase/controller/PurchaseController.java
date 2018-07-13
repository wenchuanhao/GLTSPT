package com.cdc.dc.purchase.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.common.properties.DCConfig;
import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.purchase.form.PurchaseDataColumnForm;
import com.cdc.dc.metadata.yuan.model.YuanTableColumnManage;
import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.purchase.form.PurchaseForm;
import com.cdc.dc.purchase.model.Purchase;
import com.cdc.dc.purchase.model.PurchaseColumn;
import com.cdc.dc.purchase.model.PurchaseInfo;
import com.cdc.dc.purchase.model.PurchaseRole;
import com.cdc.dc.purchase.service.IPurchaseService;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;
import com.util.ExcelUtil;

/**
 * 建设项目管理
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/purchase/")
public class PurchaseController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IRulesService rulesService;
	
    private Log logger = LogFactory.getLog(getClass());
    
	@Autowired
	private ISysOrganizationService organizationService;
    
    private Purchase purchase;
    
    private PurchaseColumn purchaseColumn;
    
    public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public PurchaseColumn getPurchaseColumn() {
		return purchaseColumn;
	}

	public void setPurchaseColumn(PurchaseColumn purchaseColumn) {
		this.purchaseColumn = purchaseColumn;
	}

	@Autowired
    private IPurchaseService iPurchaseService;
	

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
  	        			" and (o.parent_id in (select organization_id from sys_organization o where o.org_name like '%筹建办%' )	or o.organization_id = '80df8fa55ca4048ac2314dab1a52d75e')" +
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
    
	/**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,Purchase jsxm) throws Exception{
		
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
		String[] header = {"项目类型","建设项目编号","建设项目名称","项目状态","建设总控","审核人","创建时间"};
		
//		jsxm.setPageSize(Integer.MAX_VALUE - 1);
//		jsxm.setIds(request.getParameter("ids"));
//		ItemPage itemPage = jsxmService.findJsxm(jsxm);
//		
//		List<Purchase> list = (List<Purchase>)itemPage.getItems();
//
//		List<Object[]> listO =  new ArrayList<Object[]>();
//		
//		for (Purchase vo : list) {
//			Object [] obj =  new Object[7];
//			obj[0] = map1.get(vo.getColumn01());
//			obj[1] = vo.getColumn02();
//			obj[2] = vo.getColumn03();
//			obj[3] = map2.get(vo.getColumn08());
//			obj[4] = vo.getColumn04Name();
//			obj[5] = vo.getColumn05Name();
//			obj[6] = vo.getColumn07();
//			
//			listO.add(obj);
//		}
		try {
			//文件名
			String fileName = "建设项目列表";
			//ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			
			sysLogService.log(request, getVisitor(request), "工程管理--建设项目管理","导出模块", "导出【建设项目】模块", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private void listCommon(HttpServletRequest request,HttpServletResponse response,Purchase jsxm) throws Exception{
    	
    	
//    	ItemPage itemPage = jsxmService.findJsxm(jsxm);
		
//    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	request.setAttribute("jsxm", jsxm);
    }
    
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,Purchase jsxm) throws Exception{
    	
    	listCommon(request, response, jsxm);
    	
        return "/dc/project/jsxm/list";
    }	

    
    /**
     * 查看
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET,RequestMethod.POST})
    public String edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	Purchase jsxm = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
//			jsxm = jsxmService.findJsxmById(id);
		}else {
			jsxm = new Purchase();
		}
    	request.setAttribute("vo", jsxm);
    	
    	return "/dc/project/jsxm/edit";
    }
   
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	
    	Purchase jsxm = null;
		String id = request.getParameter("id");
		
//		if (id != null && !id.equals("")) {
//			jsxm = jsxmService.findJsxmById(id);
//		}else {
//			jsxm = new Purchase();
//			jsxm.setColumn07(new Date());
//			SysUser sysUser = getVisitor(request);
//			jsxm.setColumn10(sysUser.getUserId());
//		}
    	request.setAttribute("vo", jsxm);
    	
    	return "/dc/project/jsxm/add";
    }

    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate(HttpServletRequest request, HttpServletResponse response,Purchase jsxm) throws Exception{
		
		SysUser sysUser = getVisitor(request);
		
		String mess = "";
    	
		try {
			String id = jsxm.getId();
			
			String [] column04Id = request.getParameterValues("column04Id");
			String column04IdStr = "";
			if (column04Id != null && column04Id.length > 0) {
				for (int i = 0; i < column04Id.length; i++) {
					column04IdStr += column04Id[i]+",";
				}
			}
//			jsxm.setColumn04(column04IdStr);
			
			String [] column05Id = request.getParameterValues("column05Id");
			String column05IdStr = "";
			if (column05Id != null && column05Id.length > 0) {
				for (int i = 0; i < column05Id.length; i++) {
					column05IdStr += column05Id[i]+",";
				}
			}
//			jsxm.setColumn05(column05IdStr);
			
			String [] column10Id = request.getParameterValues("column10Id");
			String column10IdStr = "";
			if (column10Id != null && column10Id.length > 0) {
				for (int i = 0; i < column10Id.length; i++) {
					column10IdStr += column10Id[i]+",";
				}
			}
//			jsxm.setColumn10(column10IdStr);
			
			if (id == null || id.equals("")) {
//				jsxm.setCreateDate(new Date());
//				jsxm.setCreateUserName(sysUser.getUserName());
//				jsxm.setCreateUserId(sysUser.getUserId());
//				jsxm.setColumn02(CODE(jsxm.getColumn01()));
//				jsxm.setDeleteFlag("N");
//				jsxm.setIsNew("N");
//				jsxmService.save(jsxm);
				sysLogService.log(request, getVisitor(request), "工程管理--建设项目管理","新增模块", "添加【建设项目】模块", new Date(), "3", new Date(), null);
			}else {
//				jsxm.setUpdateDate(new Date());
//				jsxm.setUpdateUserName(sysUser.getUserName());
//				jsxm.setUpdateUserId(sysUser.getUserId());
//				
//				Purchase oldJsxm = jsxmService.findJsxmById(jsxm.getId());
//				jsxm.setCreateDate(oldJsxm.getCreateDate());
//				jsxm.setCreateUserName(oldJsxm.getCreateUserName());
//				jsxm.setCreateUserId(oldJsxm.getCreateUserId());
//				jsxm.setDeleteFlag(oldJsxm.getDeleteFlag());
//				jsxm.setIsNew(oldJsxm.getIsNew());
//				jsxm.setNewDate(oldJsxm.getNewDate());
				
//				BeanUtils.copyProperties(jsxm, oldJsxm);
				
//				jsxmService.update(oldJsxm);
				sysLogService.log(request, getVisitor(request), "工程管理--建设项目管理","修改模块", "修改【建设项目】模块", new Date(), "3", new Date(), null);
			}
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		
    	request.setAttribute("vo", jsxm);
    	request.setAttribute("mess", mess);
    	
    	return "/dc/project/jsxm/add";
    }


    /**
     * 删除
     */
    @RequestMapping(value = "delete", method = {RequestMethod.GET,RequestMethod.POST})
    public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String mess = "";
		try{
			if(id != null){
//				Purchase jsxm = jsxmService.findJsxmById(id);
//				jsxm.setDeleteFlag("Y");
//				jsxmService.update(jsxm);
				sysLogService.log(request, getVisitor(request), "工程管理--建设项目管理","删除模块", "删除【建设项目】模块", new Date(), "3", new Date(), null);
				mess = "s";
			}
        } catch (Exception e) {
        	mess = "e";
            e.printStackTrace();
        }
		
		list(request, response, new Purchase());
		
		request.setAttribute("mess", mess);
		return "/dc/project/jsxm/list";

    }
    
    /**
     * @author ywc
     * 采购项目
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "purchaseItem", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseList(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	//校验角色
    	checkPurchaseRole(request, purchaseForm);
    	//获取新增的表头列
    	List<Map<String, Object>> newHeads = iPurchaseService.queryPurchaseColumn();
    	request.setAttribute ("headInfos",newHeads);
    	//获取数据项(包括增加的列）
    	ItemPage itemPage = iPurchaseService.queryPurchase(purchaseForm); 
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("purchaseForm", purchaseForm);
    	
		return "dc/project/purchase/purchaseList";   	
    }
    
    /**
     * 校验采购量化角色
     * @param purchaseForm
     * @throws Exception 
     */
    public void checkPurchaseRole(HttpServletRequest request,PurchaseForm purchaseForm) throws Exception{
    	SysUser sysUser = getVisitor(request);
		String userName = sysUser.getUserName();
		//系统管理员PURCHASE_ADMIN TODO
		//采购量领导PURCHASE_LEADER
		//采购经办员 purchaseNormal
		SysRole sysAdmin = rulesService.querySysRoleByCode("PURCHASE_ADMIN");
		SysRoleUser purchaseAdmin = rulesService.getRoleUsersByUserId(sysAdmin.getRoleId(),sysUser.getUserId());
		SysRole  sysLeader = rulesService.querySysRoleByCode("PURCHASE_LEADER");
		SysRoleUser purchaseLeader = rulesService.getRoleUsersByUserId(sysLeader.getRoleId(),sysUser.getUserId());
		SysRole  sysNormal = rulesService.querySysRoleByCode("PURCHASE_NORMAL");
		SysRoleUser purchaseNormal = rulesService.getRoleUsersByUserId(sysNormal.getRoleId(),sysUser.getUserId());
		//查看该登录人是否分配了分管部门
		List<PurchaseRole> purchaseRoleList = iPurchaseService.queryPurchaseRoleByAcount(sysUser.getUserId());
		StringBuffer roleSB = new StringBuffer();
		if(null != purchaseRoleList && purchaseRoleList.size()>0 ){
			//只会有一条记录
			PurchaseRole purchaseRole = purchaseRoleList.get(0);
			String[] orgIds = purchaseRole.getManageApartId().split(",");
			//拼接分管部门
			if(null != orgIds && orgIds.length>0){
	    		for(int i=0;i<orgIds.length;i++)
	    		{  
	    			List<SysOrganization> sys = organizationService.getOrgAndSonById(orgIds[i]);
	    			for(int j=0;j<sys.size();j++){
	    				SysOrganization u = sys.get(j);
	    				roleSB.append("'").append(u.getOrgName()).append("',");
	    			}
    			
	    		}
	    		//去掉最后一个逗号
    			roleSB.deleteCharAt(roleSB.length()-1);
			}

		}
		
		//经办人
		if(null != purchaseNormal && null == purchaseAdmin){
			purchaseForm.setColumnB(userName);
		}
		
		//非管理员和领导角色
		if(null == purchaseLeader){			
			request.setAttribute("isLeader", "false");
		}else{
			//是领导角色则查看他分管的部门
			purchaseForm.setManageAparts(roleSB.toString());
			request.setAttribute("isLeader", "true" );
		}
		
		if(null == purchaseAdmin){
			request.setAttribute("isAdmin", "false");
		}
		else{
			
			purchaseForm.setManageAparts(null);
			request.setAttribute("isAdmin", "true" );
		}
		//无关人员
		if(null == purchaseNormal && null == purchaseLeader && null == purchaseAdmin){
			purchaseForm.setManageAparts("'-'");
		}
    	
    }
    
    /**
	 * @author ywc
	 * 导入并保存
	 * @param request
	 * @param response
	 * @param file
     * @throws IOException 
     * @throws ParseException 
	 */
	@RequestMapping(value="purchaseUpload",method={RequestMethod.POST})
	@ResponseBody
	public void importParameterType(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file") MultipartFile file) throws IOException, ParseException{
		PrintWriter out = null;
		request.getSession().removeAttribute("isErrorExcel");//是否是错误excel
		request.getSession().removeAttribute("isExitNumber");//库中是否已经存在相关的电子采购编码
		request.getSession().removeAttribute("headInfoList");
		request.getSession().removeAttribute("purchaseExcelList");
		request.getSession().removeAttribute("isErrorFormat");//是否有格式错误的数据
		request.getSession().removeAttribute("checkExcelList");//校验后的list数据
		try {
			out=response.getWriter();
			List headInfoList = new ArrayList();
			//读取导入excel数据
			List purchaseExcelList = ExcelUtil.importFromExcel(file,0);
			
            //读取excel表头 此模板表头两列
			headInfoList.add(purchaseExcelList.get(0));
			headInfoList.add(purchaseExcelList.get(1));
			purchaseExcelList.remove(0);
			purchaseExcelList.remove(0);
			//表头项塞入session等待预览
			request.getSession().setAttribute("headInfoList", headInfoList);
			
			int errCount = 0;
			List<Map<String, Object>> trList = new ArrayList<Map<String, Object>>();		
			//先校验excel格式是否正确 
			if(null != purchaseExcelList  && purchaseExcelList.size() > 0){
				List<Object[]> rowList = new ArrayList<Object[]>();
				for(int i = 0 ;i<purchaseExcelList.size();i++ ){
					rowList.clear();
					Object[] objs = (Object[]) purchaseExcelList.get(i);
					rowList.add(objs);//一行一行数据进行校验
					String current = (String) objs[11];//当前进度周报
					if(StringUtils.isEmpty(current)){
						//拿第0套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据
					}
					else if("收到请购单".equals(current)){
						//拿第1套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_1");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据
					}
					else if("工作组讨论".equals(current)){
						//拿第2套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_2");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据
						
					}
					else if("采购方案呈批".equals(current)){
						//拿第3套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_3");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据						
					}
					else if("发布公告".equals(current)){
						//拿第4套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_4");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据	
					}
					else if("完成评审".equals(current)){
						//拿第5套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_5");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据	
					}
					else if("结果确认".equals(current)){
						//拿第6套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_6");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据	
					}else if("结果公示".equals(current)){
						//拿第7套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_7");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据							
						
					}else if("合同签订呈批".equals(current)){
						//拿第8套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_8");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据							
						
					}else if("合同系统审批".equals(current)){
						//拿第9套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_9");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据							
						
					}else if("签订纸质合同".equals(current)){
						//拿第10套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_10");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据							
						
					}
					else if("已归档".equals(current)){
						//拿第11套校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO_11");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据							
						
					}					
					else{
						//拿第一种校验规则
						List<YuanTableColumnManage> mangList = rulesService.
								queryManageByTableName(objs.length, "JF_PURCHASE_INFO");
						errCount = errCount + doCheckList(rowList,mangList,trList);//校验数据
					}					
				}
			}
			
			if(errCount > 0){
				request.getSession().setAttribute("isErrorFormat", true);
				request.getSession().setAttribute("checkExcelList", trList);
			} else {//如果没有格式错误的数据，则判断电子采购项目编号的唯一性
				//获取所有电子采购项目编号
				List<String> numbers = new ArrayList<String>();   	
				for (int i = 0; i < purchaseExcelList.size(); i++) {
					String[] strs = (String[]) purchaseExcelList.get(i);
					String num = strs[35] ;
					if(num != "")
					numbers.add(num);
				}
				//电子采购项目编号判断是否有重复
				List<String> fiterNumber = null;
				if(null != numbers && numbers.size()>1){
					fiterNumber = verfNumbers(numbers);	
				}	       
				if(null != fiterNumber && fiterNumber.size()>0){
					request.getSession().setAttribute("isErrorExcel", "true");
				}	    	
				//判断所有电子采购项目编号是否在数据库中存在 
				List<String> dataBasefilter = null;
				if(null != numbers && numbers.size()>0){
					dataBasefilter = verfItemId(numbers);	
				}
				if(null != dataBasefilter && dataBasefilter.size()>0)
					//数据项塞入session等待预览
					request.getSession().setAttribute("isExitNumber", "true");
				//该上传的excel中重复电子采购项目编号信息
				request.getSession().setAttribute("fiterNumber", fiterNumber);						
				request.getSession().setAttribute("dataBasefilter", dataBasefilter);
				request.getSession().setAttribute("purchaseExcelList", purchaseExcelList);
			}
			out.print("1");
			out.close();
		} catch (IOException e) {
			out.print("0");
			out.close();
			e.printStackTrace();
			throw e;			
		}
		
	}
	
	private int doCheckList(List purchaseExcelList, List<YuanTableColumnManage> mangList, List<Map<String, Object>> trList) throws ParseException {
		int errCount = 0;
		int length = purchaseExcelList.size();
		for(int i = 0; i < length; i++){
			Object[] objs = (Object[]) purchaseExcelList.get(i);
			int objLen = objs.length;
			Map<String, Object> tdMap = new HashMap<String, Object>();
			Map<String,String> timeMap = new HashMap<String,String>();//存放excel中最新的一个时间
			List<Map<String, Object>> tdList = new ArrayList<Map<String, Object>>();
			for(int j = 0; j < objLen; j++){				
				Map<String,Object> map = new HashMap<String,Object>();
				String temp = (String) objs[j];
				JSONObject res = checkStr(mangList.get(j),temp,j,objs, timeMap);
				map.put("item", temp);//单元格数据
				if(!res.getBoolean("flag")){//校验出错
					errCount++;
					map.put("errMsg", res.get("msg"));//出错信息
					map.put("errColor", "yellow");//错误标识颜色
				}
				tdList.add(map);
			}
			tdMap.put("td", tdList);
			trList.add(tdMap);
		}
		return errCount;
	}

	private JSONObject checkStr(YuanTableColumnManage check, String temp, int j, Object[] objs, Map<String, String> timeMap) throws ParseException {
		JSONObject errinfo = new JSONObject();
		errinfo.put("index", j);//位置
		errinfo.put("flag", true);//是否正确
		errinfo.put("msg", "正确");//提示消息
		if(check == null){
			return errinfo;
		}
		if("请选择".equals(temp) && CooperationCommon.yTCMnullableNo.equals(check.getNullable())){
			errinfo.put("flag", false);//是否正确
			errinfo.put("msg", "请选择下拉选项");//提示消息
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
		
		//数值型数据校验0.000000，保留六位数字的小数
		if(CooperationCommon.yTCMdataTypeNumber.equals(check.getDataType()) && "0.000000".equals(check.getDataFormat())){
			String regEx = "[-+]?[0-9]+(\\.{0,1}[0-9]{1,6})?";
			if(!temp.matches(regEx)){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "请填入整数或者6位以内小数");//提示消息
				return errinfo;
			}
			
			if(j == 40){
				String temp3 = (String) objs[13];
				if(temp3.matches(regEx) && Double.valueOf(temp) > Double.valueOf(temp3)){
					errinfo.put("flag", false);//是否正确
					errinfo.put("msg", "采购结果金额应小于等于预估金额");//提示消息
					return errinfo;
				}
				
			}
			//合同金额
			if(j==48){
				//采购结果金额
				String temp1 = (String) objs[40];
				//预估金额
				String temp2 = (String) objs[13];
				
				if(temp1.matches(regEx) && Double.valueOf(temp) > Double.valueOf(temp1)){
					errinfo.put("flag", false);//是否正确
					errinfo.put("msg", "合同金额应小于等于采购结果金额");//提示消息
					return errinfo;
				}
				if(temp2.matches(regEx) && Double.valueOf(temp) > Double.valueOf(temp2)){
					errinfo.put("flag", false);//是否正确
					errinfo.put("msg", "合同金额应小于等于预估金额");//提示消息
					return errinfo;
				}
			}
			
		}
		
		
		//时间型数值校验yyyy
		if(CooperationCommon.yTCMdataTypeDate.equals(check.getDataType()) && "yyyy".equals(check.getDataFormat())){
			String regEx = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})";
			
			if(!temp.matches(regEx)){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "年份格式不正确，应为“2016”格式");//提示消息
				return errinfo;
			}
		}
		
		//时间型数值校验yyyy-MM
		if(CooperationCommon.yTCMdataTypeDate.equals(check.getDataType()) && "yyyy-MM".equals(check.getDataFormat())){
			String regEx = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(0[1-9]|1[0-2])";
			
			if(!temp.matches(regEx)){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "月份格式不正确，应为“2016-01”格式");//提示消息
				return errinfo;
			}
		}
		
		//时间型数值校验yyyy-MM-dd
		if(CooperationCommon.yTCMdataTypeDate.equals(check.getDataType()) && "yyyy-MM-dd".equals(check.getDataFormat())){
			String regEx = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
			if(!temp.matches(regEx)){
				errinfo.put("flag", false);//是否正确
				errinfo.put("msg", "日期格式不正确，应为“2016-01-01”格式");//提示消息
				return errinfo;
		    }
			 //excel中时间要逐渐变大
			 SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd");
			 String lastTime = timeMap.get("lastTime");
			 if(StringUtils.isEmpty(lastTime)  ){
				 timeMap.put("lastTime", temp);
			 }else{
				 Date dt1 = sdf.parse(lastTime);
		         Date dt2 = sdf.parse(temp);
		         //后一个时间小于前一个时间
                 if(dt2.getTime() < dt1.getTime()){
                	errinfo.put("flag", false);//是否正确
     				errinfo.put("msg", "当前填写时间不应小于"+ lastTime);//提示消息
     				 timeMap.put("lastTime", temp);
     				return errinfo; 
                 }else{
                	 timeMap.put("lastTime", temp); 
                 }
			 }
			
		}
		
		//判断是否取消
		if(j == 63 && "是".equals(temp) && objs[64]==""){
			errinfo.put("flag", false);//是否正确
			errinfo.put("msg", "取消原因不能为空");//提示消息
			return errinfo;
		}

		return errinfo;
	}

	/**
	 * 判断此采购项目编号是否在数据库中存在
	 * @param data
	 * @return
	 */
	private  List<String> verfItemId(List<String> data){
		List<String> fiterList = new ArrayList<String>();
		for(int i=0;i<data.size();i++){
			int j = iPurchaseService.queryPurchaseByItemid(data.get(i));	
			//数据库中已经存在此
			if(j>0){
				fiterList.add(data.get(i));
			}
		}
		HashSet h  =   new  HashSet(fiterList); 
		fiterList.clear(); 
		fiterList.addAll(h); 		
		return fiterList;		
	}
	/**
	 * 获取list中重复数据
	 * @param data
	 */
	private  List<String> verfNumbers(List<String> data){
		List<String> fiterList = new ArrayList<String>();
		String temp = "";
		for (int i = 0; i < data.size() - 1; i++)
        {
            temp = data.get(i);
            for (int j = i + 1; j < data.size(); j++)
            {
                if (temp.equals(data.get(j)))
                {
                	fiterList.add(temp);
                }
            }
        }		
		HashSet h  =   new  HashSet(fiterList); 
		fiterList.clear(); 
		fiterList.addAll(h); 
		
		return fiterList;
		
	}

	/**
	 * @author ywc
	 * 导入excel预览
	 * @param request
	 * @param response
	 * @param cooperationForm
	 * @return
	 */
    @RequestMapping(value = "purchaseExcelList", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseExcelList(HttpServletRequest request, HttpServletResponse response,CooperationForm cooperationForm){
    	
    	
    	//获取保留在session中的数据list
    	List purchaseExcelList = (List) request.getSession().getAttribute("purchaseExcelList");
    	//获取校验后的数据list
    	List checkExcelList = (List) request.getSession().getAttribute("checkExcelList");
    	
    	if(purchaseExcelList!=null && purchaseExcelList.size()>0){
    		int length = purchaseExcelList.size();
    		//开始索引
    		int beginIndex = (cooperationForm.getPageIndex() - 1) * cooperationForm.getPageSize();
    		//结束索引
    		int endIndex = cooperationForm.getPageIndex() * cooperationForm.getPageSize() > length ? length : cooperationForm.getPageIndex() * cooperationForm.getPageSize();
    		
    		List pageList = new ArrayList(cooperationForm.getPageSize());
    		
    		for (int i = beginIndex; i < endIndex; i++) {
    			pageList.add(purchaseExcelList.get(i));
    		}
    		//生成分页结果
    		ItemPage itemPage = new ItemPage(pageList, length, cooperationForm.getPageIndex(),cooperationForm.getPageSize());
    		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	} else if(checkExcelList!=null &&checkExcelList.size()>0){
    		int length = checkExcelList.size();
    		//开始索引
    		int beginIndex = (cooperationForm.getPageIndex() - 1) * cooperationForm.getPageSize();
    		//结束索引
    		int endIndex = cooperationForm.getPageIndex() * cooperationForm.getPageSize() > length ? length : cooperationForm.getPageIndex() * cooperationForm.getPageSize();
    		
    		List pageList = new ArrayList(cooperationForm.getPageSize());
    		
    		for (int i = beginIndex; i < endIndex; i++) {
    			pageList.add(checkExcelList.get(i));
    		}
    		//生成分页结果
    		ItemPage itemPage = new ItemPage(pageList, length, cooperationForm.getPageIndex(),cooperationForm.getPageSize());    		
    		//request.setAttribute("checkExcelPage",itemPage );
    		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	}
		request.setAttribute("headInfo", request.getSession().getAttribute("headInfoList"));		
		return "dc/project/purchase/purchaseExcelList";
    	
    } 
    /**
     * excel表覆盖提交
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "exceluncoverSubmit", method = {RequestMethod.GET,RequestMethod.POST})
    public String exceluncoverSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	//获取新增的表头列 用来添加input框
    	 List purchaseExcelList = (List) request.getSession().getAttribute("purchaseExcelList");
    	if(purchaseExcelList==null && purchaseExcelList.size()==0){
    		return "redirect:/purchase/purchaseItem";
    	}
        //数据校验(未实现) 
    	//List<String[]> verifyformData = verifyExcelData(purchaseExcelList);
        
    	//把excel表单数据转为bean 并放入List
    	SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
    	List transformData = transExcelData(purchaseExcelList,sysUser.getAccount());   	    	
    	//获取所有电子采购项目编号   	
    	iPurchaseService.uncoverPurchaseByExcel(transformData);
      	return "redirect:/purchase/purchaseItem";
    	
    }
    
    /**
     * excel表覆盖提交
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "excelCoverSubmit", method = {RequestMethod.GET,RequestMethod.POST})
    public String excelCoverSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	//获取新增的表头列 用来添加input框
    	 List purchaseExcelList = (List) request.getSession().getAttribute("purchaseExcelList");
    	if(purchaseExcelList==null && purchaseExcelList.size()==0){
    		return null;
    	}
        //数据校验(未实现) 
    	//List<String[]> verifyformData = verifyExcelData(purchaseExcelList);
        
    	//把excel表单数据转为bean 并放入List
    	SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
    	List transformData = transExcelData(purchaseExcelList,sysUser.getAccount());   	    	
    	//获取所有电子采购项目编号
    	List<String> numbers = new ArrayList<String>();   	
    	for (int i = 0; i < purchaseExcelList.size(); i++) {
			String[] strs = (String[]) purchaseExcelList.get(i);
			String num = strs[35] ;
			numbers.add(num);
		} 
    	
    	iPurchaseService.coverPurchaseByExcel(numbers,transformData);
      	return "redirect:/purchase/purchaseItem";
    	
    }
    
    /**
     * 把excel表单转为bean
     * @param purchaseExcelList
     * @return
     */
	private List transExcelData(List excelList,String operator) {
		
		//存放 转换后数据
		List data = new ArrayList<Object[]>();
		//判空
		if(excelList == null && excelList.size()==0){
			return null;
		}
		
		for(int i=0;i<excelList.size();i++){
			String[] rowData = (String[]) excelList.get(i);
			PurchaseInfo purchaseInfo = new PurchaseInfo();			
			purchaseInfo.setOperator(operator);
			purchaseInfo.setColumnA(rowData[0]);
			purchaseInfo.setColumnB(rowData[1]);
			purchaseInfo.setColumnC(rowData[2]);
			purchaseInfo.setColumnD(rowData[3]);
			purchaseInfo.setColumnE(rowData[4]);
			purchaseInfo.setColumnF(rowData[5]);
			purchaseInfo.setColumnG(rowData[6]);
			purchaseInfo.setColumnH(rowData[7]);
			purchaseInfo.setColumnI(rowData[8]);
			purchaseInfo.setColumnJ(rowData[9]);
			purchaseInfo.setColumnK(rowData[10]);
			purchaseInfo.setColumnL(rowData[11]);
			purchaseInfo.setColumnM(rowData[12]);
			purchaseInfo.setColumnN(rowData[13]);
			purchaseInfo.setColumnO(rowData[14]);
			purchaseInfo.setColumnP(rowData[15]);
			purchaseInfo.setColumnQ(rowData[16]);
			purchaseInfo.setColumnR(rowData[17]);
			purchaseInfo.setColumnS(rowData[18]);
			purchaseInfo.setColumnT(rowData[19]);
			purchaseInfo.setColumnU(rowData[20]);
			purchaseInfo.setColumnV(rowData[21]);
			purchaseInfo.setColumnW(rowData[22]);
			purchaseInfo.setColumnX(rowData[23]);
			purchaseInfo.setColumnY(rowData[24]);
			purchaseInfo.setColumnZ(rowData[25]);
			purchaseInfo.setColumnAa(rowData[26]);
			purchaseInfo.setColumnAb(rowData[27]);
			purchaseInfo.setColumnAc(rowData[28]);
			purchaseInfo.setColumnAd(rowData[29]);
			purchaseInfo.setColumnAe(rowData[30]);
			purchaseInfo.setColumnAf(rowData[31]);
			purchaseInfo.setColumnAg(rowData[32]);
			purchaseInfo.setColumnAh(rowData[33]);
			purchaseInfo.setColumnAi(rowData[34]);
			purchaseInfo.setColumnAj(rowData[35]);
			purchaseInfo.setColumnAk(rowData[36]);
			purchaseInfo.setColumnAl(rowData[37]);
			purchaseInfo.setColumnAm(rowData[38]);
			purchaseInfo.setColumnAn(rowData[39]);
			purchaseInfo.setColumnAo(rowData[40]);
			purchaseInfo.setColumnAp(rowData[41]);
			purchaseInfo.setColumnAq(rowData[42]);
			purchaseInfo.setColumnAr(rowData[43]);
			purchaseInfo.setColumnAs(rowData[44]);
			purchaseInfo.setColumnAt(rowData[45]);
			purchaseInfo.setColumnAu(rowData[46]);
			purchaseInfo.setColumnAv(rowData[47]);
			purchaseInfo.setColumnAw(rowData[48]);
			purchaseInfo.setColumnAx(rowData[49]);
			purchaseInfo.setColumnAy(rowData[50]);
			purchaseInfo.setColumnAz(rowData[51]);
			purchaseInfo.setColumnBa(rowData[52]);
			purchaseInfo.setColumnBb(rowData[53]);
			purchaseInfo.setColumnBc(rowData[54]);
			purchaseInfo.setColumnBd(rowData[55]);
			purchaseInfo.setColumnBe(rowData[56]);
			purchaseInfo.setColumnBf(rowData[57]);
			purchaseInfo.setColumnBg(rowData[58]);
			purchaseInfo.setColumnBh(rowData[59]);
			purchaseInfo.setColumnBi(rowData[60]);
			purchaseInfo.setColumnBj(rowData[61]);
			purchaseInfo.setColumnBk(rowData[62]);
			purchaseInfo.setColumnBl(rowData[63]);
			purchaseInfo.setColumnBm(rowData[64]);
			purchaseInfo.setColumnBn(rowData[65]);
			purchaseInfo.setColumnBo(rowData[66]);
			data.add(purchaseInfo);
			
		}				
		return data;
	}

	/**
     * 新增页面跳转
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "addPurchase", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseAddt(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	//获取新增的表头列 用来添加input框
    	List<Map<String, Object>> newHeads = iPurchaseService.queryPurchaseColumn();
    	request.setAttribute ("headaaa",newHeads); 
    	SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
    	request.setAttribute("currentOperator", sysUser.getUserName());
		return "dc/project/purchase/purchaseAdd";
    	
    }
    
    /**
     * 增加页面表单提交
     * @param request
     * @param response
     * @param purchase
     * @throws Exception 
     */
    @RequestMapping(value = "submitPurchaseForm", method = {RequestMethod.GET,RequestMethod.POST})
    public String submitPurchaseForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
	    //TODO
    	//先获取表的所有字段名 
    	List<String> columNames=iPurchaseService.getTableMetadatas("JF_PURCHASE_info");
    	//拼接成新的column
    	List<String> tableList1 = iPurchaseService.getTableDatas("column_name","JF_PURCHASE_column");
		List<String> tableList2 = iPurchaseService.getTableDatas("column_name","JF_PURCHASE_column where isDelete='1'");
		
		List<String> newColumnNames=new ArrayList<String>();
		for(int i=0;i<columNames.size()-tableList1.size();i++){
			newColumnNames.add(columNames.get(i));
		}
		for(int i=columNames.size()-tableList1.size();i<columNames.size()-tableList1.size()+tableList2.size();i++){
			newColumnNames.add(tableList2.get(i-(columNames.size()-tableList1.size())));
		}
    	//用来存储页面提交过来的数据
    	List<String[]> datas = new ArrayList<String[]>();
    	String[] data = new String[newColumnNames.size()];
        //从第四位开始获取数据(前5个字段非页面表单提交过来的)
    	for(int i=5;i<newColumnNames.size();i++){
    		String vdata = request.getParameter(newColumnNames.get(i).replace("_", "").toLowerCase());
    		 data[i-5]= vdata;
    	} 
    	SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
    	datas.add(data);
    	iPurchaseService.addPurchase(datas,sysUser.getAccount());				
		return "redirect:/purchase/purchaseItem";
    	
    }
    
    /**
     * 数据列列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "dataColunmList", method = {RequestMethod.GET,RequestMethod.POST})
    public String dataColunmList(HttpServletRequest request, HttpServletResponse response,PurchaseDataColumnForm form) throws Exception{
    	ItemPage itemPage = iPurchaseService.queryDataColumn(form); 
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("form", form);
    	
		return "dc/project/purchase/purchaseDataColumnList";   	
    }
    /**
     * 增加数据列
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "dataColunmAdd", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseDataColumnAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{

        
		return "dc/project/purchase/purchaseDataColumnAdd";   	
    }
    
    /**
     * 数据列物理删除
     * @param request
     * @param response
     * @param purchaseForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deleteDataColumn", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void deleteDataColumn(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //获取数据
    	 String id = request.getParameter("id");
    	 String result = "0";
    	 try{
    		 if(StringUtils.isNotEmpty(id)){
    			 PurchaseColumn purchaseColumn = (PurchaseColumn) iPurchaseService.getEntity(PurchaseColumn.class, id);
    			 if(purchaseColumn != null){
    				 iPurchaseService.deleteDataColumn(purchaseColumn);
    				 result = "1";
    			 }
    		 }
    		 PrintWriter out = response.getWriter();
    		 out.write(result);
    	 }catch (Exception e) {
 			e.printStackTrace();
 		 }
    }
    
    /**
     * 动态增加字段
     * @param request
     * @param response
     * @param purchaseColumn
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "submitPurchaseColumn", method = {RequestMethod.GET,RequestMethod.POST})
    public String submitPurchaseColumn(HttpServletRequest request, HttpServletResponse response,PurchaseColumn purchaseColumn) throws Exception{
    	
    	request.removeAttribute("operatResult");
	    //先找出这是第几个新加的列
    	JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
    	int maxColumnorder= jdbcTemplate.queryForObject("select max(column_order) from JF_purchase_column", Integer.class);
    	purchaseColumn.setColumn_name("columnb"+String.valueOf(maxColumnorder+1));
    	purchaseColumn.setColumn_order(maxColumnorder+1);
    	purchaseColumn.setIsDelete("1");
    	
    	//给表jf_purchase_info增加相应列;
    	String alterSql ="";
    	if("1".equals(purchaseColumn.getColumn_type())){
    		alterSql= "alter table jf_purchase_info add("+purchaseColumn.getColumn_name()+" varchar2("+purchaseColumn.getColumn_length()+"))";
    	}
    	//时间格式字段
    	else
    	{
    	   alterSql = "alter table jf_purchase_info add("+purchaseColumn.getColumn_name()+" Date)";	
    	}
    	
    	try{
        //表增加字段
    	jdbcTemplate.execute(alterSql);    	
    	//记录新增的列相关信息到JF_purchase_column表
    	iPurchaseService.savePurchaseColumn(purchaseColumn);
    	}catch(Exception e){
    		alterSql = "alter table JF_PURCHASE_info drop column " + purchaseColumn.getColumn_name();
    		jdbcTemplate.execute(alterSql);
    		request.setAttribute("operatResult", "0");
    	}   
    	   //操作成功
    	   request.setAttribute("operatResult", "1");
   		return "redirect:/purchase/dataColunmList";   	
    } 
    
    @RequestMapping(value = "updatePurchase", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseUpdate(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
        //获取数据
    	String id = request.getParameter("id");   	
    	Map<String, Object> row = iPurchaseService.queryPurchaseByid(id); 
    	//获取新增列的表头
    	List<Map<String, Object>> newHeads = iPurchaseService.queryPurchaseColumn();
    	request.setAttribute ("headUpdate",newHeads); 
    	request.setAttribute("purchase", row); 
    	
		return "dc/project/purchase/purchaseUpdate";   	
    }
    
    /**
     * 修改提交
     * @param request
     * @param response
     * @param purchaseForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deletePurchase", method = {RequestMethod.GET,RequestMethod.POST})
    public String submiPurchaseUpdate(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
        //获取数据
    	 String id = request.getParameter("id");   	
    	 iPurchaseService.deletePurchaseByid(id); 
    	
		return "forward:/purchase/purchaseItem";   	
    }
    /**
     * 修改提交
     * @param request
     * @param response
     * @param purchaseForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updatePurchaseForm", method = {RequestMethod.GET,RequestMethod.POST})
    public String purchaseDelete(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
        //获取数据
    	List<String> columNames=iPurchaseService.getTableMetadatas("JF_PURCHASE_info");
    	//拼接成新的column
    	List<String> tableList1 = iPurchaseService.getTableDatas("column_name","JF_PURCHASE_column");
		List<String> tableList2 = iPurchaseService.getTableDatas("column_name","JF_PURCHASE_column where isDelete='1'");
		
		List<String> newColumnNames=new ArrayList<String>();
		for(int i=0;i<columNames.size()-tableList1.size();i++){
			newColumnNames.add(columNames.get(i));
		}
		for(int i=columNames.size()-tableList1.size();i<columNames.size()-tableList1.size()+tableList2.size();i++){
			newColumnNames.add(tableList2.get(i-(columNames.size()-tableList1.size())));
		}
    	//用来存储页面提交过来的数据
    	List<String[]> datas = new ArrayList<String[]>();
    	String[] data = new String[newColumnNames.size()];
    	//前5个字段非页面提交
    	for(int i=5;i<newColumnNames.size();i++){
    		String vdata = request.getParameter(newColumnNames.get(i).replace("_", "").toLowerCase());
    		 data[i-5]= vdata;
    	} 
    	 datas.add(data);
    	 //获取id
    	 String id = request.getParameter("id"); 
     	 SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);

    	 iPurchaseService.updatePurchaseByid(id,datas,sysUser.getAccount()); 
    	
		return "forward:/purchase/purchaseItem";   	
    }
    
    /**
     * 下载模板
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "downloadTemplate", method = RequestMethod.GET)
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		String fileName = "采购项目_导入数据模板.xlsx";
		String fileNameE = "muban.xlsx";
		String downLoadPath = request.getSession().getServletContext().getRealPath("/") + DCConfig.getProperty("PURCHASE_TEMPLATE_DIR") + fileNameE;
		try {
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ));
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
    
    /**
     * 检查是否录入员、经办人、超级管理员
     * @param request
     * @param response
     */
    @RequestMapping(value = "checkOperator", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public void checkOperator(HttpServletRequest request,HttpServletResponse response){
    	String id = request.getParameter("id");
		String result = "0";
		
		SysUser sysUser = getVisitor(request);
		String userName = sysUser.getUserName();
		String account = sysUser.getAccount();
		//系统管理员
		SysRole sysRole = rulesService.querySysRoleByCode("PURCHASE_ADMIN");
		SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
		
		try {
			if(StringUtils.isNotEmpty(id)){
				Map<String, Object> row = iPurchaseService.queryPurchaseByid(id);
				String COLUMN_B = (String) row.get("COLUMN_B");//经办人
				String OPERATOR = (String) row.get("OPERATOR");//操作员
				if(StringUtils.equals(userName, COLUMN_B) 
						|| StringUtils.equals(account, OPERATOR)
						|| null!=rolesUser){
					result = "1";
				}
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * ajax 删除记录
     * @param request
     * @param response
     */
    @RequestMapping(value = "delPurchase", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public void delPurchase(HttpServletRequest request,HttpServletResponse response){
    	String id = request.getParameter("id");
		String result = "0";
		
		try {
			if(StringUtils.isNotEmpty(id)){
				iPurchaseService.deletePurchaseByid(id);
				result = "1";
			}
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * ajax查询电子编号是否已经存在
     * @param request
     * @param response
     */
    @RequestMapping(value = "queryPurchaseByItemId", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public void queryPurchaseByItemId(HttpServletRequest request,HttpServletResponse response){
    	String id = request.getParameter("itemId");
		String result = "0";
		
		try {			
			int j = iPurchaseService.queryPurchaseByItemid(id);
			if(j>0){
				result = "1";
			}		    		
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
}
