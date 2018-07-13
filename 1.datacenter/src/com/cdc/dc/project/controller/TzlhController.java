package com.cdc.dc.project.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.jsxm.controller.JsxmController;
import com.cdc.dc.project.model.TzlhReport;
import com.cdc.inter.client.db.oracle.TouziOracleService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 工程投资经分
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/GCTzlh/")
public class TzlhController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	
    private Log logger = LogFactory.getLog(getClass());
    
    /**
     * 首页
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "index",method = {RequestMethod.GET,RequestMethod.POST})
    public String index(HttpServletRequest request,HttpServletResponse response,TzlhReport tzlhReport) throws Exception{

    	JsxmController.userRole(request);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));//月初
		if (tzlhReport.getBeginCreateTime() == null) {
			tzlhReport.setBeginCreateTime(calendar.getTime() );
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));//月末
		if (tzlhReport.getEndCreateTime() == null) {
			tzlhReport.setEndCreateTime(calendar.getTime() );
		}
		
    	ItemPage itemPage = TouziOracleService.getList(tzlhReport);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage);
    	request.setAttribute("tzlhReport", tzlhReport);
    	request.setAttribute("depList", GcUtils.getDeptList());
        return "/dc/project/tzlhIndex";
    }	
    
	
	/**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,TzlhReport tzlhReport) throws Exception{
		
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

		// 表头状态"计划转资金额汇总（万元）","实际转资金额汇总（万元）","项目转资率",
		String[] header = {"序号","年度","部门","科室","负责人","项目编码","项目名称","年度资本开支进度计划（万元）"
				,"年度完成资本开支（万元）","年度投资计划完成率","项目投资总额（万元）","合同金额（万元）","合同数量","合同完成率"};
		
		tzlhReport.setPageSize(Integer.MAX_VALUE - 1);
		
		ItemPage itemPage = TouziOracleService.getList(tzlhReport);
		
		List<TzlhReport> list = (List<TzlhReport>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		for (int i=0;i<list.size();i++) {
			TzlhReport vo = list.get(i);
			Object [] obj =  new Object[14];
			obj[0] = i+1;
			if (vo.getDept() == null || vo.getDept().equals("")) {
				obj[1] = vo.getYyyymm() + " 总计：";
			}else {
				obj[1] = vo.getYyyymm();
			}
			obj[2] = vo.getDept();
			obj[3] = vo.getKs();
			obj[4] = vo.getFzr();
			obj[5] = vo.getA();
			obj[6] = vo.getB();
//			obj[7] = vo.getC();
//			obj[8] = vo.getD();
//			obj[9] = vo.getE() + "%";
			obj[7] = vo.getF();
			obj[8] = vo.getG();
			obj[9] = vo.getH() + "%";
			obj[10] = vo.getI();
			obj[11] = vo.getJ();
			obj[12] = vo.getK();
			obj[13] = vo.getL() + "%";
			
			listO.add(obj);
		}
		try {
			//文件名
			String fileName = "投资经分列表";
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			
			sysLogService.log(request, getVisitor(request), "工程管理--投资经分","导出模块", "导出【投资经分】模块", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
