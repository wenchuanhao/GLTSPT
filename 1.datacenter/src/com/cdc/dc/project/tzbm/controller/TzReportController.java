package com.cdc.dc.project.tzbm.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.jsxm.controller.JsxmController;
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.tzbm.model.TzbmReport;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxmht.model.HtKz;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 投资一张表
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/tzReport/")
public class TzReportController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	
    private Log logger = LogFactory.getLog(getClass());
    
	/**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,Tzbm tzbm) throws Exception{
		
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
		String[] header = {"投资编号","项目名称","项目总投资（万元）","资本开支目标（万元）","至上年度安排投资计划（万元）",
				"累计签订合同金额不含税（万元）","累计完成资本开支（万元）","年度资本开支（万元）","本年度资本开支百分比","年度转资目标（万元）",
				"本年累计转资（万元）","累计付款（万元）","负责人","计划书文号","任务书文号"};
		
		tzbm.setPageSize(Integer.MAX_VALUE - 1);
		
		List<TzbmReport> list = list(request, response, tzbm);

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		String[] ids = request.getParameterValues("subBox");
		if(ids == null || ids.length == 0) return;
		List<String> idList = Arrays.asList(ids);
		
		for (TzbmReport vo : list) {
			if(idList.contains(vo.getId())){
				Object [] obj =  new Object[15];
				obj[0] = vo.getA();
				obj[1] = vo.getB();
				obj[2] = vo.getC();
				obj[3] = vo.getD();
				obj[4] = vo.getE();
				obj[5] = vo.getF();
				obj[6] = vo.getG();
				obj[7] = vo.getH();
				obj[8] = vo.getI();
				obj[9] = vo.getJ();
				obj[10] = vo.getK();
				obj[11] = vo.getL();
				obj[12] = vo.getColumn13Name();
				obj[13] = vo.getN();
				obj[14] = vo.getO();
				listO.add(obj);
			}
		}
		try {
			//文件名
			String fileName = "投资一张表";
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			sysLogService.log(request, getVisitor(request), "工程管理--投资一张表","导出模块", "导出【投资一张表】模块", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 投资一张表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "tzList",method = {RequestMethod.GET,RequestMethod.POST})
    public String tzList(HttpServletRequest request,HttpServletResponse response,Tzbm tzbm) throws Exception{
    	JsxmController.userRole(request);
    	list(request, response, tzbm);
    	
        return "/dc/project/tzbm/reportList";
    }	
    
    public List<TzbmReport> list(HttpServletRequest request,HttpServletResponse response,Tzbm tzbm)throws Exception{
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		String column02 = tzbm.getColumn02();
		String column03 = tzbm.getColumn03();
		String column13 = tzbm.getColumn13();
		
		String column02SQL = "",column03SQL = "",column13SQL = "";
		//投资编号
		if (column02 != null && !column02.equals("")) {
			column02SQL = " and A like '%"+column02+"%' ";
		}	     
		//投资项目名称
		if (column03 != null && !column03.equals("")) {
			column03SQL = " and B like '%"+column03+"%' ";
		}
		//投资项目联系人
		if (column13 != null && !column13.equals("")) {
			column13SQL = " and M like '%"+column13+"%' ";
		}
		//科室
		String ks = tzbm.getKs(),ksSQL = "";
		if(ks != null && !ks.equals("")){
			List<SysUser> listU = GcUtils.findSysUserByorgId(ks);
			for (int i = 0; i < listU.size(); i++) {
				ksSQL += " or M like '%"+listU.get(i).getUserId()+"%' ";
			}
			if (!ksSQL.equals("")) {
				ksSQL = "and ("+ksSQL.substring(4)+")";
			}
		}
		String sqlC = "select * from gc_view_tzyzb where 1=1 "+ column02SQL + column03SQL + column13SQL + ksSQL;
		
    	List<TzbmReport> totalsList = jdbcTemplate.query(sqlC, new ReportRowMapper());
    	int pageSize = tzbm.getPageSize();
    	int pageIndex = tzbm.getPageIndex();
    	
    	String sql = "select * from(select a.*,rownum row_num from(" +sqlC+")a where rownum <= "+pageSize*(pageIndex)+")b where b.row_num >=	"+((pageIndex-1)*pageSize+1)+" order by ID asc";
    	
    	List<TzbmReport> list = jdbcTemplate.query(sql, new ReportRowMapper());
		//生成分页结果
		ItemPage itemPage = new ItemPage(list, totalsList.size(), pageIndex,pageSize);
    	
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("tzbm", tzbm);
    	
    	//合计
		String sqlC_total = "select ''ID,''A,'总计'B,sum(C)C,sum(D)D,sum(E)E,sum(F)F,sum(G)G,sum(H)H," +
				"decode(sum(D),'0','0%',(round(nvl(sum(H),0)/sum(D),2)*100||'%'))I," +
				"sum(J)J,sum(K)K,sum(L)L,''M,''N,''O from gc_view_tzyzb where 1=1 "+ column02SQL + column03SQL + column13SQL + ksSQL;
		List<TzbmReport> tList = jdbcTemplate.query(sqlC_total, new ReportRowMapper());
		request.setAttribute("tzbmT", tList.get(0));
		
    	return totalsList;
    }
    
	class ReportRowMapper implements RowMapper{
		
		public TzbmReport mapRow(ResultSet rs, int arg1) throws SQLException {
			TzbmReport vo = new TzbmReport();
			if(rs != null){
			    vo.setId(rs.getString("ID"));	//投资编码ID
			    vo.setA(rs.getString("A"));	//投资编号
			    vo.setB(rs.getString("B"));	//项目名称
			    vo.setC(rs.getString("C"));	//项目总投资
			    vo.setD(rs.getString("D"));	//资本开支目标
			    vo.setE(rs.getString("E"));	//至上年度安排投资计划
			    vo.setF(rs.getString("F"));		//累计签订
			    vo.setG(rs.getString("G"));	//累计完成
			    vo.setH(rs.getString("H"));	//年度资本开支
			    vo.setI(rs.getString("I"));		//本年度资本开支百分比
			    vo.setJ(rs.getString("J"));		//年度转资目标
			    vo.setK(rs.getString("K"));	//本年累计转资
			    vo.setL(rs.getString("L"));		//累计付款
			    vo.setM(rs.getString("M"));	//负责人
			    vo.setN(rs.getString("N"));	//计划任务书
			    vo.setO(rs.getString("O"));	//建设任务书
			}
			return vo;
		}		
	}
    
    //--------------------------------------------------------------------------------------
	
    /**
     * 子项目投资汇总
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "zxmList",method = {RequestMethod.GET,RequestMethod.POST})
    public String zxmList(HttpServletRequest request,HttpServletResponse response,Zxm zxm) throws Exception{
    	JsxmController.userRole(request);
    	list2(request, response, zxm);
    	
        return "/dc/project/tzbm/reportZxmList";
    }	
    
    public List<TzbmReport> list2(HttpServletRequest request,HttpServletResponse response,Zxm zxm)throws Exception{
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		String tzbmId = request.getParameter("tzbmId");
		String column02 = zxm.getColumn02();
		String column03 = zxm.getColumn03();
		
		String column02SQL = "",column03SQL = "";
		//子项目编号
		if (column02 != null && !column02.equals("")) {
			column02SQL = " and A like '%"+column02+"%' ";
		}	     
		//子项目名称
		if (column03 != null && !column03.equals("")) {
			column03SQL = " and B like '%"+column03+"%' ";
		}
		//科室
		String ks = zxm.getKs(),ksSQL = "";
		if(ks != null && !ks.equals("")){
			List<SysUser> listU = GcUtils.findSysUserByorgId(ks);
			for (int i = 0; i < listU.size(); i++) {
				ksSQL += " or M like '%"+listU.get(i).getUserId()+"%' ";
			}
			if (!ksSQL.equals("")) {
				ksSQL = "and ("+ksSQL.substring(4)+")";
			}
		}
		String sqlC = "select * from gc_view_tzyzb_zxm where tzbmId = '"+tzbmId+"' "+ column02SQL + column03SQL + ksSQL;
		
		String sqlC_total = "select ''tzbmId,''ID,''A,'总计'B,sum(F)F,sum(G)G,sum(H)H,sum(K)K,sum(L)L from gc_view_tzyzb_zxm where tzbmId = '"+tzbmId+"' "+ column02SQL + column03SQL + ksSQL;
		
    	List<TzbmReport> totalsList = jdbcTemplate.query(sqlC+" union all "+sqlC_total, new ReportRowMapper2());
    	int pageSize = zxm.getPageSize();
    	int pageIndex = zxm.getPageIndex();
    	
    	String sql = "select * from(select a.*,rownum row_num from(" +sqlC+" union all "+sqlC_total+")a where rownum <= "+pageSize*(pageIndex)+")b where b.row_num >=	"+((pageIndex-1)*pageSize+1)+" order by ID desc";
    	
    	List<TzbmReport> list = jdbcTemplate.query(sql, new ReportRowMapper2());
		if(list.size() == 1 && list.get(0).getB().equals("总计")){
			list = new ArrayList<TzbmReport>();
		}
		//生成分页结果
		ItemPage itemPage = new ItemPage(list, totalsList.size(), pageIndex,pageSize);
    	
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("zxm", zxm);
    	request.setAttribute("tzbmId", tzbmId);
    	
    	return totalsList;
    }
    
	class ReportRowMapper2 implements RowMapper{
		
		public TzbmReport mapRow(ResultSet rs, int arg1) throws SQLException {
			TzbmReport vo = new TzbmReport();
			if(rs != null){
			    vo.setId(rs.getString("ID"));	//投资编码ID
			    vo.setA(rs.getString("A"));	//投资编号
			    vo.setB(rs.getString("B"));	//项目名称
			    vo.setF(rs.getString("F"));		//累计签订
			    vo.setG(rs.getString("G"));	//累计完成
			    vo.setH(rs.getString("H"));	//年度资本开支
			    vo.setK(rs.getString("K"));	//本年累计转资
			    vo.setL(rs.getString("L"));		//累计付款
			}
			return vo;
		}		
	}
	
	//------------------------------------------------------------------------------
    /**
     * 合同开支表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "htkzList",method = {RequestMethod.GET,RequestMethod.POST})
    public String htkzList(HttpServletRequest request,HttpServletResponse response,HtKz htKz) throws Exception{
    	JsxmController.userRole(request);
    	list3(request, response, htKz);
    	return "/dc/project/tzbm/reportHtKzList";
    }
    public List<HtKz> list3(HttpServletRequest request,HttpServletResponse response,HtKz htKz)throws Exception{
    	JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
    	String zxmId = request.getParameter("zxmId");
    	
		String column02 = htKz.getColumn02();
		
		String column02SQL = "";
		//子项目编号
		if (column02 != null && !column02.equals("")) {
			column02SQL = " and column_02 like '%"+column02.trim()+"%' ";
		}
		
		String sql = "select * from gc_view_tzyzb_zxm_htkz where zxmId = '"+zxmId+"' "+column02SQL;
		
    	List<HtKz> totalsList = jdbcTemplate.query(sql, new ReportRowMapper3());
    	int pageSize = htKz.getPageSize();
    	int pageIndex = htKz.getPageIndex();
    	
    	List<HtKz> list = jdbcTemplate.query(sql, new ReportRowMapper3());
		//生成分页结果
		ItemPage itemPage = new ItemPage(list, totalsList.size(), pageIndex,pageSize);
    	
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("htKz", htKz);
    	request.setAttribute("zxmId", zxmId);
    	return totalsList;
    }
    
	class ReportRowMapper3 implements RowMapper{
		
		public HtKz mapRow(ResultSet rs, int arg1) throws SQLException {
			HtKz htKz = new HtKz();
			if(rs != null){
			    htKz.setId(rs.getString("ID"));
			    htKz.setColumn01(rs.getString("COLUMN_01"));
			    htKz.setColumn02(rs.getString("COLUMN_02"));
			    htKz.setColumn03(rs.getBigDecimal("COLUMN_03"));
			    htKz.setColumn04(rs.getBigDecimal("COLUMN_04"));
			    htKz.setColumn05(rs.getString("COLUMN_05"));
			    htKz.setColumn06(rs.getString("COLUMN_06"));
			    htKz.setColumn07(rs.getBigDecimal("COLUMN_07"));
			    htKz.setColumn08(rs.getBigDecimal("COLUMN_08"));
			    htKz.setColumn09(rs.getBigDecimal("COLUMN_09"));
			    htKz.setColumn10(rs.getBigDecimal("COLUMN_10"));
			    htKz.setColumn11(rs.getBigDecimal("COLUMN_11"));
			    htKz.setColumn12(rs.getTimestamp("COLUMN_12"));
			    htKz.setColumn13(rs.getString("COLUMN_13"));
			}
			return htKz;
		}		
	}
}
