package com.cdc.dc.rules.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.dc.cooperation.common.CooperationCommon;
import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.form.RulesForm;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.authentication.controller.DefaultController;
import com.cdc.system.core.cache.SysParamHelper;
import com.trustel.common.ItemPage;

/**
 * 制度查询
 * @author ZENGKAI
 * @date 2016-04-13 11:58:29
 */
@Controller
@RequestMapping(value = "/rulesController/")
public class RulesQueryController extends DefaultController{

	@Autowired
	private IRulesService rulesService;
	
	/**
	 * 制度查询
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "query", method = {RequestMethod.POST,RequestMethod.GET})
	public String query(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		request.setAttribute("rulesForm",rulesForm);
		
		String roleStr = rulesService.setUserRoles(request,rulesForm);
		
		//查询待办、已处理的列表
		ItemPage itemPage =(ItemPage) rulesService.queryAllRulesInfoItemPage(rulesForm,roleStr);
		request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
		
		rulesService.setCommonAttribute(request);
		
		return "/dc/rules/rulesQuery";
	}
	
	/**
	 * 图表统计
	 * @param request
	 * @param rulesForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "charts", method = {RequestMethod.POST,RequestMethod.GET})
	public String charts(HttpServletRequest request,RulesForm rulesForm) throws Exception{
		String roleStr = rulesService.setUserRoles(request,rulesForm);
		ItemPage itemPage =(ItemPage) rulesService.queryAllRulesInfoItemPage(rulesForm,roleStr);
		request.setAttribute("queryCount",itemPage.getTotal() );
		
		request.setAttribute("rulesForm",rulesForm);
//		SysUser sysUser = getVisitor(request);
//		//按等级分类
        String sql = "select b.parameter_value as name,count(1) as count from rules_info a,sys_parameter b where a.rules_grade=b.parameter_code and 1=1 group by b.parameter_value,b.order_id order by b.order_id";
        List<Object[]> grades =(List<Object[]>) rulesService.queryChartSItemPage(sql,rulesForm);
        //按部门分类
        sql = "select b.org_name as name,count(1) as count from rules_info a,sys_organization b where a.lead_dep_id=b.organization_id and 1=1 group by b.org_name";
        List<Object[]> organizations =(List<Object[]>) rulesService.queryChartSItemPage(sql,rulesForm);
        //按状态统计
        sql = "select cast(case when status='2' then '审核中' " +
        		" when status='3' then '已发布' " +
        		" when status='4' then '已废止' " +
        		" when status='5' then '已修订' " +
        		" when status='6' then '已退回' " +
        		" when status='0' then '已删除' end as varchar2(10))  as name,count(1) as count from rules_info a where status != '" + RulesCommon.rulesInfoStatus_CG + "' and status != '" + RulesCommon.rulesInfoStatus_DEL + "' and 1=1 group by status";
        List<Object[]> statuses =(List<Object[]>) rulesService.queryChartSItemPage(sql,rulesForm);
        //按类型统计
        sql = "select max(b.parent_type_name)|| '-' ||b.type_name as name,count(1) as count from rules_info a,rules_type b  where  a.rules_type_id=b.type_id and 1=1 group by b.type_name";
        List<Object[]> types =(List<Object[]>) rulesService.queryChartSItemPage(sql,rulesForm);

        request.setAttribute("grades",JSONArray.fromObject(grades).toString());
        request.setAttribute("organizations",JSONArray.fromObject(organizations).toString());
        request.setAttribute("statuses",JSONArray.fromObject(statuses).toString());
        request.setAttribute("types",JSONArray.fromObject(types).toString());
		//按制度等级统计
		rulesService.setCommonAttribute(request);
		
		return "/dc/rules/rulesCharts";
	}
	
}
