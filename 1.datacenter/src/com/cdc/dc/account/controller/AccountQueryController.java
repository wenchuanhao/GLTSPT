package com.cdc.dc.account.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.system.SystemConstant;

import com.cdc.dc.account.common.AccountCommon;
import com.cdc.dc.account.form.AccountForm;
import com.cdc.dc.account.model.AccountInfo;
import com.cdc.dc.account.model.AccountInvoice;
import com.cdc.dc.account.service.IAccountService;
import com.cdc.dc.rules.model.RulesType;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 
 * @author xms
 *报账信息查询
 */
@Controller
@RequestMapping(value="account/")
public class AccountQueryController extends DefaultController{
	
	@Autowired
	private IAccountService accService;
	
	@Autowired
	private ISysRoleService roleService;
	
	@ModelAttribute
	public void doBeforeCall(HttpServletRequest request){
		List<SysParameter> sysParameter = SysParamHelper.getSysParamListByParamTypeCode("BZZT");
		request.setAttribute("sysParameter", sysParameter);
		
	}
	
	//查询
	@RequestMapping(value="accountQuery" , method = {RequestMethod.POST,RequestMethod.GET})
	public String accountQuery(HttpServletRequest request,AccountForm accountForm) throws Exception{
		SysUser visitor=this.getVisitor(request);
		ItemPage itemPage = common(request,accountForm,visitor,"0");
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage);
		
		//是否为报账管理员
		String iSbaozAdmin = accService.queryTrialIsAdmin(visitor.getUserId());
		if(iSbaozAdmin!=null){
			if(iSbaozAdmin.equals(AccountCommon.ACCOUNT_ADMIN)){
				request.setAttribute("iSbaozAdmin", true);
			}else {
				request.setAttribute("iSbaozAdmin", false);
			}
		}
				
		//费用类型
		List<RulesType> rulelist = accService.queryCostType();
		request.setAttribute("rulesList", rulelist);
		return "dc/account/queryAccount";

	}
	
	private ItemPage common(HttpServletRequest request,AccountForm accountForm, SysUser visitor,String fromMethod) throws Exception{
		request.setAttribute("accountForm", accountForm);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM");
		
		if(accountForm.getReachSementStr()!=null && !accountForm.getReachSementStr().trim().equals("")){
			request.setAttribute("reachSementStr", sft.parse(accountForm.getReachSementStr()));
		}
		
		Map<String,String> map = new HashMap();
		if(request.getParameter("reachSement")!=null && !request.getParameter("reachSement").trim().equals("")){
			request.setAttribute("reachSement", request.getParameter("reachSement"));
		}
		String pstartDate = request.getParameter("pstartDate");
		String pendDate = request.getParameter("pendDate");
		if(StringUtils.isNotEmpty(pstartDate)){
			map.put("pstartDate", pstartDate);
			request.setAttribute("pstartDate", sf.parse(pstartDate) );
		}
		if(StringUtils.isNotEmpty(pendDate)){
			map.put("pendDate", pendDate);
			request.setAttribute("pendDate", sf.parse(pendDate) );
		}
		String cstartDate = request.getParameter("cstartDate");
		String cendDate = request.getParameter("cendDate");
		if(StringUtils.isNotEmpty(cstartDate)){
			map.put("cstartDate", cstartDate);
			request.setAttribute("cstartDate", sf.parse(cstartDate));
		}
		if(StringUtils.isNotEmpty(cendDate)){
			map.put("cendDate", cendDate);
			request.setAttribute("cendDate", sf.parse(cendDate));
		}
		String payStartDate = request.getParameter("payStartDate");
		String payEndDate = request.getParameter("payEndDate");
		if(StringUtils.isNotEmpty(payStartDate)){
			map.put("payStartDate", payStartDate);
			request.setAttribute("payStartDate", sf.parse(payStartDate));
		}
		if(StringUtils.isNotEmpty(payEndDate)){
			map.put("payEndDate", payEndDate);
			request.setAttribute("payEndDate", sf.parse(payEndDate));
		}
		
		String people = request.getParameter("people");
		map.put("people", people);
		request.setAttribute("people", people);
		
		//type 1我创建的报账单   2我处理的报账单据  3所有单据
		String type=request.getParameter("type");
		if(type ==null || type.trim().equals("")){
			type="1";
		}
		
		request.setAttribute("type", type);
		ItemPage itemPage = null;
		if("0".equals(fromMethod)){
			itemPage = accService.queryAccountItemPage(type,accountForm ,map, visitor);
		}else if("1".equals(fromMethod)){
			itemPage = accService.queryInvoice(type,accountForm ,map, visitor);
		}
		return itemPage;
	}
	
	//查询部门
	@RequestMapping(value="queryDepartment",method={RequestMethod.POST})
	public @ResponseBody void queryDepartment(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		 response.setContentType("text/html; charset=utf-8");
		 String Value = request.getParameter("Value");
 		String result = "";
	        if(Value != null && !"".equals(Value.trim())){
	        	List<Object[]> userList = accService.queryDepartment(Value);
	        	JSONArray userListJSONArray = new JSONArray();
                if (userList != null && !userList.isEmpty()) {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    Object[] obj  ;
                    for (int i=0;i<userList.size();i++) {
                    	obj = userList.get(i);
                        jsonObject = new JSONObject();
                        jsonArray = new JSONArray();
                    	 jsonObject.put("userId",String.valueOf(obj[0]));
                         jsonObject.put("userName",String.valueOf(obj[1]));
                         jsonObject.put("account",String.valueOf(obj[2]));
                         jsonArray.add(jsonObject);
                         JSONObject jsonObject1 = new JSONObject();
                         jsonObject1.put("orgName",obj[3] == null ? "":String.valueOf(obj[3]));
                         jsonArray.add(jsonObject1);

                         userListJSONArray.add(jsonArray);
                    }
                }
  	        	result = userListJSONArray.toString();
	        }
	        PrintWriter writer = response.getWriter();
    		writer.write(result);
	}
	
	/**
	 * 导出发票信息
	 * @param request
	 * @param response
	 * @param accountForm
	 * @throws Exception 
	 */
	@RequestMapping(value="exportInvoice",method={RequestMethod.POST})
	public void exportInvoice(HttpServletRequest request, HttpServletResponse response, AccountForm accountForm) throws Exception{
		HSSFWorkbook book = new HSSFWorkbook();
		String[] header = {"序号","报账单号","发票代码","发票号码/原凭证号","开票内容/品目名称","销方纳税人识别号","销方纳税人名称","购方纳税人识别号","购方纳税人名称",
							"开票日期/入库日期","金额","税额/实缴金额","税率/单位税额","增值税扣税凭证类型"};
		
		SysUser visitor=this.getVisitor(request);
		accountForm.setPageSize(Integer.MAX_VALUE - 1);
		String accids = request.getParameter("accids");
		accountForm.setAccids(accids);
		ItemPage itemPage = common(request,accountForm,visitor,"1");
		List<Object[]> sourceList = (List<Object[]>) itemPage.getItems();
		
		List<Object[]> targetList =  new ArrayList<Object[]>();
		
		for(int i = 0; i < sourceList.size(); i++){
			Object[] vo = sourceList.get(i);
			AccountInfo accountInfo = (AccountInfo) vo[0];
			AccountInvoice invoice = (AccountInvoice) vo[1];
			Object[] obj = new Object[14];
			obj[0] = i + 1;//序号
			obj[1] = accountInfo.getOrderId();//报账单号
			obj[2] = invoice.getInvoiceCode();//发票代码
			obj[3] = invoice.getInvoiceNum();//发票号码/原凭证号
			obj[4] = invoice.getGoodsName();//开票内容/品目名称
			obj[5] = invoice.getXfTaxpayerNum();//销方纳税人识别号
			obj[6] = invoice.getXfTaxpayerName();//销方纳税人名称
			obj[7] = invoice.getGfTaxpayerNum();//购方纳税人识别号
			obj[8] = invoice.getGfTaxpayerName();//购方纳税人名称
			obj[9] = invoice.getCreateDate();//开票日期/入库日期
			obj[10] = invoice.getMoneyNoTax();//金额
			obj[11] = invoice.getTaxNum();//税额/实缴金额
			obj[12] = invoice.getTaxRate();//税率/单位税额
			obj[13] = invoice.getInvoiceType();//增值税扣税凭证类型
			targetList.add(obj);
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "发票信息" + sdf.format(new Date());
			ExcelUtil.exportForExcel(header, fileName, targetList, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			book.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
