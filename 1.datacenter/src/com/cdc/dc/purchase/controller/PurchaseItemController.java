package com.cdc.dc.purchase.controller;

import java.text.DecimalFormat;
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
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 采购项目明细表	
 * @author zengkai
 * @date 2016-09-27 16:31:20
 *
 */
@Controller
@RequestMapping(value = "/purchaseControl/")
public class PurchaseItemController extends DefaultController{
	private Log logger = LogFactory.getLog(getClass());
	public final static String JF =  "JF_THRESHOLD";//采购量化阈值
	public final static String XQ =  "XQ_TH";//需求确认时长超时预警
	public final static String CG =  "CG_TH";//采购时长超时预警
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
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
    	checkPurchaseRole(request, purchaseForm);
    	ItemPage itemPage = purchaseService.findItem(purchaseForm,false);
    	request.setAttribute("purchaseForm",purchaseForm );
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	//需求确认时长超时预警
    	String xq = SysParamHelper.getSysParamByCode(PurchaseItemController.JF, PurchaseItemController.XQ).getParameterValue();
    	String cg = SysParamHelper.getSysParamByCode(PurchaseItemController.JF, PurchaseItemController.CG).getParameterValue();
    	request.setAttribute("xq", xq);//需求确认时长超时预警
    	request.setAttribute("cg", cg);//采购时长超时预警
    	//采购时长超时预警
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
		return "dc/project/purchase/purchaseControlItem";
    }
    
    /**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,PurchaseForm purchaseForm) throws Exception{
		
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
		String[] header = {"序号","项目名称","需求部门","项目所属年份","是否计划内项目","开支类型","预算金额(万元）","经办人","代理公司名称",
				"采购方式","资格审查方式","当前进度（周报）","进度说明（周报）","需求到达时间","工作小组会议召开时间",
				"50万以上方案汇报时间/50万以下需求部门提交方案呈批时间","需求确认时长","需求确认时长超时预警","采购方案决策时间",
				"公告发布开始时间","公告发布截止时间","采购评审/谈判时间","需求确认完毕-评审时间（工作日）",
				"结果决策会通过时间或结果呈批批复时间","合同审批完毕时间","合同编码","中选供应商","合同金额（万元）",
				"下浮率","采购节约金额（万元）","采购时长（工作日）","采购时长超时预警","合同归档时间","流标次数","流标后更改的采购方式",
				"流标原因说明（每次流标都作说明）","采购工作投入天数","技术商务比例是否符合标准","合同模板是否符合标准","技术评分模板招标文件模板是否符合标准",
				"投诉情况","中标单位、结算价格和合同单位、结算价格是否完全一致","采购进度是否影响到成本/投资使用计划一致","特殊情况说明"};
		
		purchaseForm.setPageSize(Integer.MAX_VALUE - 1);
		SysUser sysUser = getVisitor(request);
		String ids = request.getParameter("ids");
		purchaseForm.setIds(ids);
		ItemPage itemPage = purchaseService.findItem(purchaseForm,false);
		
		List<PurchaseInfo> list = (List<PurchaseInfo>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		//需求确认时长超时预警
    	String xq = SysParamHelper.getSysParamByCode(PurchaseItemController.JF, PurchaseItemController.XQ).getParameterValue();
    	String cg = SysParamHelper.getSysParamByCode(PurchaseItemController.JF, PurchaseItemController.CG).getParameterValue();
    	
		int i = 1;
		for (PurchaseInfo vo : list) {
			Object [] obj =  new Object[44];
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
			obj[10] = vo.getColumnK();
			obj[11] = vo.getColumnL();
			obj[12] = vo.getColumnM();
			obj[13] = vo.getColumnO();
			obj[14] = vo.getColumnP();
			obj[15] = vo.getColumnQ();
			Long ct = vo.getConfirmTime();
			obj[16] = ct;
			if(ct < Long.valueOf(xq)){
				obj[17] = "正常";
			}else{
				obj[17] = "超时提醒";
			}
			obj[18] = vo.getColumnR();
			obj[19] = vo.getColumnZ();
			obj[20] = vo.getColumnAa();
			obj[21] = vo.getColumnAb();
			obj[22] = vo.getReviewDays();
			obj[23] = vo.getColumnAr();
			obj[24] = vo.getColumnAs();
			obj[25] = vo.getColumnAu();
			obj[26] = vo.getColumnAv();
			obj[27] = vo.getColumnAw();
			String aw = vo.getColumnAw();
			String n = vo.getColumnN();
			if(StringUtils.isEmpty(aw)){
				aw = "0";
			}
			if(StringUtils.isEmpty(n)){
				n = "0";
			}
			obj[28] = new DecimalFormat("#.##").format((Double.valueOf(aw) - Double.valueOf(n)) / Double.valueOf(n) * 100) + "%";
			obj[29] = Double.valueOf(aw) - Double.valueOf(n);
			Long pt = vo.getPurTime();
			obj[30] = pt;
			if(pt < Long.valueOf(cg)){
				obj[31] = "正常";
			}else{
				obj[31] = "超时提醒";
			}
			obj[32] = vo.getColumnAx();
			obj[33] = vo.getColumnAy();
			obj[34] = vo.getColumnAz();
			obj[35] = vo.getColumnBa();
			obj[36] = vo.getPurDays();
			
			obj[37] = vo.getColumnBe();
			obj[38] = vo.getColumnBf();
			obj[39] = vo.getColumnBg();
			obj[40] = vo.getColumnBh();
			obj[41] = vo.getColumnBi();
			obj[42] = vo.getColumnBj();
			obj[43] = vo.getColumnBk();
			
			listO.add(obj);
			i++;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			//文件名
			String fileName = "采购项目明细表" + sdf.format(new Date());
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
