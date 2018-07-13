package com.cdc.dc.purchase.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.purchase.form.PurchaseForm;
import com.cdc.dc.purchase.model.PurchaseInfo;
import com.cdc.dc.purchase.model.PurchaseRole;
import com.cdc.dc.purchase.service.IPurchaseService;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 采购已取消项目列表
 * @author zengkai
 * @date 2016-09-27 16:31:20
 *
 */
@Controller
@RequestMapping(value = "/purchaseControl/")
public class PurchaseCancelController extends DefaultController{
	private Log logger = LogFactory.getLog(getClass());

	@Autowired
    private IPurchaseService purchaseService;
	
	@Autowired
	private ISysOrganizationService organizationService;
	@Autowired
    private IPurchaseService iPurchaseService;	
	
	@Autowired
	private IRulesService rulesService;
	
	public void checkPurchaseRole(HttpServletRequest request,PurchaseForm purchaseForm) throws Exception{
	    	SysUser sysUser = getVisitor(request);
	    	String userName = sysUser.getUserName();
			//系统管理员PURCHASE_ADMIN TODO
			//采购量领导PURCHASE_LEADER
			//报表只有领导和管理员可看
			SysRole sysAdmin = rulesService.querySysRoleByCode("PURCHASE_ADMIN");
			SysRoleUser purchaseAdmin = rulesService.getRoleUsersByUserId(sysAdmin.getRoleId(),sysUser.getUserId());
			SysRole  sysLeader = rulesService.querySysRoleByCode("PURCHASE_LEADER");
			SysRoleUser purchaseLeader = rulesService.getRoleUsersByUserId(sysLeader.getRoleId(),sysUser.getUserId());
			SysRole  sysNormal = rulesService.querySysRoleByCode("PURCHASE_NORMAL");
			SysRoleUser purchaseNormal = rulesService.getRoleUsersByUserId(sysNormal.getRoleId(),sysUser.getUserId());
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
			//领导角色
			if(null != purchaseLeader){			
				purchaseForm.setManageAparts(roleSB.toString());
			}
			if(null != purchaseAdmin){
				purchaseForm.setManageAparts(null);
				request.setAttribute("isAdmin", "true" );
			}
			//无关人员
			if( null == purchaseNormal && null == purchaseLeader && null == purchaseAdmin){
				purchaseForm.setManageAparts("'-'");
			}
	    }
	
	/**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "cancellist",method = {RequestMethod.GET,RequestMethod.POST})
    public String cancellist(HttpServletRequest request,HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	checkPurchaseRole(request, purchaseForm);
    	ItemPage itemPage = purchaseService.findItem(purchaseForm,true);
    	request.setAttribute("purchaseForm",purchaseForm );
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	 //默认取1.1-当前时间
    	String btime ="";
        String etime ="";
      	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          if(purchaseForm.getBeginCreateTime() ==null && purchaseForm.getEndCreateTime()==null){         	
          	 Calendar calendar1 =Calendar.getInstance();    
          	 Calendar calendar2 =Calendar.getInstance();
          	 calendar2.clear();
          	 calendar2.set(Calendar.YEAR, calendar1.get(Calendar.YEAR));  
          	 Date yearFirst = calendar2.getTime();
          	 btime = sdf.format(yearFirst);
          	 etime =  sdf.format(new Date());
          	 purchaseForm.setBeginCreateTime(sdf.parse(btime));
          	 purchaseForm.setEndCreateTime(sdf.parse(etime));
          	 
          }
		return "dc/project/purchase/purchaseControlCancel";
    }
    
    /**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "cancelFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void cancelFile(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
		
		checkPurchaseRole(request, purchaseForm);
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
		String[] header = {"序号","项目名称","需求部门","项目所属年份","是否计划内项目","开支类型","预算金额（万元）","经办人","代理公司名称","采购方式","取消原因"};
		String ids = request.getParameter("ids");
		purchaseForm.setIds(ids);
		purchaseForm.setPageSize(Integer.MAX_VALUE - 1);
		SysUser sysUser = getVisitor(request);
		ItemPage itemPage = purchaseService.findItem(purchaseForm,true);
		
		List<PurchaseInfo> list = (List<PurchaseInfo>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		int i = 1;
		for (PurchaseInfo vo : list) {
			Object [] obj =  new Object[11];
			obj[0] = i;
			obj[1] = vo.getColumnA();
			obj[2] = vo.getColumnC();
			obj[3] = vo.getColumnD();
			obj[4] = vo.getColumnE();
			obj[5] = vo.getColumnF();
			obj[6] = vo.getColumnN();
			obj[7] = vo.getColumnB();
			obj[8] = vo.getColumnH();
			obj[9] = vo.getColumnJ();
			obj[10] = vo.getColumnBm();
			listO.add(obj);
			i++;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			//文件名
			String fileName = "采购已取消项目列表" + sdf.format(new Date());
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}
