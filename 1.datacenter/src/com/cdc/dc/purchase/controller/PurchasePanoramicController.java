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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
 * 采购全景监控表
 * @author zengkai
 * @date 2016-09-28 09:17:20
 *
 */
@Controller
@RequestMapping(value = "/purchaseControl/")
public class PurchasePanoramicController extends DefaultController{
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
			if(null == purchaseNormal && null == purchaseLeader && null == purchaseAdmin){
				purchaseForm.setManageAparts("'-'");
			}
	    }
	
	/**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "pmlist",method = {RequestMethod.GET,RequestMethod.POST})
    public String pmlist(HttpServletRequest request,HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	checkPurchaseRole(request,purchaseForm);
    	ItemPage itemPage = purchaseService.findItem(purchaseForm,false);
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
		return "dc/project/purchase/purchaseControlPm";
    }
    
    /**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "pmFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void pmFile(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
		checkPurchaseRole(request,purchaseForm);
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
		//设置工作表sheet
		HSSFSheet sheet = book.createSheet("制度详情");

		//设置第一行
		int r = 0;
		HSSFRow row = sheet.createRow(r);
		HSSFCell cel = row.createCell(0);
		cel.setCellStyle(style);
		cel.setCellValue("项目信息");
		HSSFCell cel2 = row.createCell(4);
		cel2.setCellStyle(style);
		cel2.setCellValue("采购进度");
		HSSFCell cel3 = row.createCell(13);
		cel3.setCellStyle(style);
		cel3.setCellValue("采购质量");
		HSSFCell cel4 = row.createCell(21);
		cel4.setCellStyle(style);
		cel4.setCellValue("采购成本");
		HSSFCell cel5 = row.createCell(24);
		cel5.setCellStyle(style);
		cel5.setCellValue("备注");
		
		// 表头状态
		String[] header = {"序号","项目名称","经办人","采购方式","当前进度（周报）","需求到达时间","预算金额(万元）","公告发布开始时间","公告发布截止时间","采购评审/谈判时间",
				"结果决策会通过时间或结果呈批批复时间","合同审批完毕时间","采购时长（工作日）","流标次数","流标原因说明（每次流标都作说明）","技术商务比例是否符合标准","合同模板是否符合标准",
				"技术评分模板招标文件模板是否符合标准","采购货物或服务质量情况","投诉情况","中标单位、结算价格和合同单位、结算价格是否完全一致","采购节约金额（万元）","采购工作投入天数",
				"采购进度是否影响到成本/投资使用计划一致","备注"};
		//合并第一行
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 12));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 20));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 21, 23));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 24, 24));
		
		purchaseForm.setPageSize(Integer.MAX_VALUE - 1);
		String ids = request.getParameter("ids");
		purchaseForm.setIds(ids);
		ItemPage itemPage = purchaseService.findItem(purchaseForm,false);
		
		List<PurchaseInfo> list = (List<PurchaseInfo>)itemPage.getItems();
		List<Object[]> listO =  new ArrayList<Object[]>();
		
		int i = 1;
		for (PurchaseInfo vo : list) {
			Object [] obj =  new Object[25];
			obj[0] = i;
			obj[1] = vo.getColumnA();
			obj[2] = vo.getColumnB();
			obj[3] = vo.getColumnJ();
			obj[4] = vo.getColumnL();
			obj[5] = vo.getColumnO();
			obj[6] = vo.getColumnN();
			obj[7] = vo.getColumnZ();
			obj[8] = vo.getColumnAa();
			obj[9] = vo.getColumnAb();
			obj[10] = vo.getColumnAr();
			obj[11] = vo.getColumnAs();
			
			obj[12] = vo.getPurTime();
			obj[13] = vo.getColumnAy();
			obj[14] = vo.getColumnBa();
			obj[15] = vo.getColumnBe();
			obj[16] = vo.getColumnBf();
			obj[17] = vo.getColumnBg();
			obj[18] = vo.getColumnBn();
			obj[19] = vo.getColumnBh();
			obj[20] = vo.getColumnBi();
			
			if(StringUtils.isEmpty(vo.getColumnAw())){
				vo.setColumnAw("0");
			}
			if(StringUtils.isEmpty(vo.getColumnN())){
				vo.setColumnN("0");
			}
			obj[21] = Double.valueOf(vo.getColumnAw()) - Double.valueOf(vo.getColumnN());
			obj[22] = vo.getPurDays();
			obj[23] = vo.getColumnBj();
			obj[24] = vo.getColumnBo();
			
			listO.add(obj);
			i++;
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			//文件名
			String fileName = "采购全景监控表" + sdf.format(new Date());
			String tempName = "cgqjjkb" + sdf.format(new Date());
			ExcelUtil.exportForExcel(header, r, tempName, listO, book, row, sheet, style);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
