package com.cdc.dc.project.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.project.jsxm.controller.JsxmController;
import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.jsxm.service.IJsxmService;
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.tzbm.model.TzbmReport;
import com.cdc.dc.project.zb.model.Zb;
import com.cdc.dc.project.zb.service.IZbService;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;

/**
 * 工程首页
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/GCindex/")
public class GcIndexController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IJsxmService jsxmService;
	@Autowired
	private IZbService zbService;
	@Autowired
	private IRulesService rulesService;
	
    private Log logger = LogFactory.getLog(getClass());
    
    /**
     * 首页
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "index",method = {RequestMethod.GET,RequestMethod.POST})
    public String index(HttpServletRequest request,HttpServletResponse response) throws Exception{

    	JsxmController.userRole(request);
    	SysUser sysUser = getVisitor(request);
    	//建设项目一览表
		Jsxm jsxm = new Jsxm();
		//jsxm.setIsNew("Y");
		jsxm.setPageSize(5);
		ItemPage itemPage = jsxmService.findJsxm(jsxm);
		List<Jsxm> jsxmList = (List<Jsxm>)itemPage.getItems();
		
		//不够自动抽取
		/*int totals = jsxmList.size();
		if(totals < 5){
			jsxm.setIsNew("N");
			jsxm.setPageSize(5-totals);
			ItemPage itemPage1 = jsxmService.findJsxm(jsxm);
			List<Jsxm> jsxmList1 = (List<Jsxm>)itemPage1.getItems();
			for (int i =0; i <jsxmList1.size(); i++) {
				jsxmList.add(jsxmList1.get(i));
			}
		}*/
		
		request.setAttribute("jsxmList", jsxmList);
		
		//投资一张表
		Tzbm tzbm = new Tzbm();
		//tzbm.setIsNew("Y");
		tzbm.setPageSize(5);
		List<TzbmReport> list = list(request, response, tzbm);

		//不够自动抽取
		/*int tz_totals = list.size();
		if(tz_totals < 5){
			tzbm.setIsNew("N");
			tzbm.setPageSize(5-tz_totals);
			List<TzbmReport> list1 = list(request, response, tzbm);
			for (int i =0; i <list1.size(); i++) {
				list.add(list1.get(i));
			}
		}*/
		
		request.setAttribute("tzbmList", list);
		
		//周报一张表
		Zb zb = new Zb();
		zb.setPageSize(5);
		//判断用户是否是周报管理员
    	SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(FxkForm.FXK_DICT_TYPE, FxkForm.GCZB_ADMIN).getParameterValue());
    	SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
    	if(rolesUser != null){
    		request.setAttribute("userRoles", FxkForm.GCZB_ADMIN);
    		zb.setUserRoles(FxkForm.GCZB_ADMIN);
    	}
    	
    	zb.setCreateUserId(sysUser.getUserId());//创建用户id
    	ItemPage zbPage = zbService.findZb(zb);
    	request.setAttribute("zbList", zbPage);
    	
        return "/dc/project/gcIndex";
    }	
    
    public List<TzbmReport> list(HttpServletRequest request,HttpServletResponse response,Tzbm tzbm)throws Exception{

		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		String isNew = tzbm.getIsNew(),isNewSQL="";
		//是否置顶
		//if (isNew != null && !isNew.equals("")) {
			isNewSQL = " and  tzbm.is_new = '"+isNew+"' order by tzbm.new_date desc,tzbm.column_17 desc";
		//}else {
			isNewSQL = " order by tzbm.column_17 desc";
		//}
		
		String sqlC = 
				"select tzbm.is_new,tzbm.new_date,yzb.* from gc_tzbm tzbm,(		" +
				"	       select * from GC_VIEW_TZYZB where 1=1 	" +
				"	)yzb where tzbm.id = yzb.id				" + isNewSQL+"  ";
		
    	int pageSize = tzbm.getPageSize();
    	int pageIndex = 1;
    	
    	String sql = "select * from(select a.*,rownum row_num from(" +sqlC+")a where rownum <= "+pageSize*(pageIndex)+")b where b.row_num >=	"+((pageIndex-1)*pageSize+1);
    	
    	List<TzbmReport> list = jdbcTemplate.query(sql, new ReportRowMapper());
    	
    	return list;
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
			    vo.setJ(rs.getString("J"));		//期初余额
			    vo.setK(rs.getString("K"));	//本年累计转资
			    vo.setL(rs.getString("L"));		//累计付款
			    vo.setM(rs.getString("M"));	//负责人
			    vo.setN(rs.getString("N"));	//计划任务书
			    vo.setO(rs.getString("O"));	//建设任务书
			}
			return vo;
		}		
	}
}
