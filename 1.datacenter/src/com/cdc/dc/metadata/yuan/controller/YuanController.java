package com.cdc.dc.metadata.yuan.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.account.common.AccountCommon;
import com.cdc.dc.metadata.yuan.model.YuanTableManage;
import com.cdc.dc.metadata.yuan.service.IYuanService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;

/**
 * 元数据表管理
 * @author WEIFEI
 * @date 2016-7-12 下午4:18:46
 */
@Controller
@RequestMapping(value = "/yuan/")
public class YuanController extends CommonController {

	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IYuanService yuanService;
	
    private Log logger = LogFactory.getLog(getClass());
    
    /**
     * 数据列表
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "rowList", method = RequestMethod.GET)
    public String rowList(HttpServletRequest request,HttpServletResponse response) {
		
		QueryBuilder query = new QueryBuilder(YuanTableManage.class);
		query.where("id",request.getParameter("id"),QueryAction.EQUAL);
		query.orderBy("createDate");
		
		List<YuanTableManage> list = (List<YuanTableManage>) enterpriseService.query(query, 0);
		
		YuanTableManage vo = list.get(0);
		String tableName = vo.getTableName();
		List<YuanTableManage> listColumn = vo.getTableColumn();
		
		request.setAttribute("listColumn", listColumn);
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		List<Map<String, Object>> list1 = jdbcTemplate.queryForList("select * from "+tableName.toUpperCase()+"");
		List<List<Object>> listResult = new ArrayList<List<Object>>();
		
		for (int i = 0; i < list1.size(); i++) {
			Map<String, Object> map = list1.get(i);
			List<Object> l = new ArrayList<Object>();
			for (int j = 0; j < listColumn.size(); j++) {
				String column_name = listColumn.get(j).getColumn_name();
				l.add(map.get(column_name));
			}
			listResult.add(l);
		}
		
		request.setAttribute("listResult", listResult);
		
    	return "/dc/metadata/yuan/yuan_table_list";
    }
    
	/**
	 * 树结构数据
	 * @author WEIFEI
	 * @date 2016-7-14 下午3:49:29
	 * @param request	void
	 */
	private void c(HttpServletRequest request){
	   	List<YuanTableManage> list = (List<YuanTableManage>)enterpriseService.query("from YuanTableManage order by createDate", 0);
    	
			JSONArray jsonList = new JSONArray();
			JSONObject objRoot = new JSONObject();
			objRoot.put("id", "ROOT");
			objRoot.put("pId", 0);
			objRoot.put("name", "元数据列表");
			objRoot.put("open", true);
			jsonList.add(objRoot);
			
			for (int i = 0; i < list.size(); i++) {
				JSONObject obj = new JSONObject();
				YuanTableManage vo = list.get(i);
				obj.put("id", vo.getId());
				obj.put("pId", vo.getParentId());
				obj.put("name", vo.getTableAlias());
				obj.put("open", true);
				jsonList.add(obj);
			}
			
			request.setAttribute("zNodes", jsonList.toString());
	}
	
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpServletRequest request,HttpServletResponse response) {
    	
    	//树结构数据
    	c(request);
		
        return "/dc/metadata/yuan/yuan_tree_list";
    }	
    
    private static Date getStrToDate(String date){
		try{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(date != null){
				return df.parse(date);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(AccountCommon.getDate(getStrToDate("2016-07-14 11:20:35"), getStrToDate("2016-07-15 11:54:29"), null));
	}
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	SysUser sysUser = getVisitor(request);
		
		String id = request.getParameter("id");
		
		YuanTableManage vo = new YuanTableManage();
		
		if (id != null && !id.equals("")) {
			QueryBuilder query = new QueryBuilder(YuanTableManage.class);
			query.where("id",id,QueryAction.EQUAL);
			query.orderBy("createDate");
			
			List<YuanTableManage> list = (List<YuanTableManage>) enterpriseService.query(query, 0);
			vo = list.get(0);
		}else {
			vo.setCreateDate(new Date());
			vo.setCreateUserName(sysUser.getUserName());
			vo.setCreateUserId(sysUser.getUserId());
		}
		
		vo.setUpdateDate(new Date());
		vo.setUpdateUserName(sysUser.getUserName());
		vo.setUpdateUserId(sysUser.getUserId());
		
		Map<String, Object> map = vo.getTable();
		String comments = (String) map.get("comments");
		BigDecimal num_rows = (BigDecimal) map.get("num_rows");
		
		request.setAttribute("numRows", (num_rows == null) == true ? "0":num_rows);
		
		request.setAttribute("vo", vo);
		
		List<YuanTableManage> list = (List<YuanTableManage>)enterpriseService.query("from YuanTableManage order by createDate", 0);
    	
		List<YuanTableManage> newList = new ArrayList<YuanTableManage>();
		
		for (int i = 0; i < list.size(); i++) {
			if ((list.get(i).getParentId()).equals("ROOT")) {
				newList.add(list.get(i));
			}
		}
		request.setAttribute("list", newList);
		
		//树结构数据
		c(request);
		
    	return "/dc/metadata/yuan/yuan_tree_list_right";
    }

    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    public @ResponseBody String saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	String result = "0";
		
		SysUser sysUser = getVisitor(request);
		
		String id = request.getParameter("id");
		String tableName = request.getParameter("tableName");
		String parentId = request.getParameter("parentId");
		String datasourceId = request.getParameter("busTypes");
		
		if (parentId == null || parentId.equals("")) {
			parentId = "ROOT";
		}
		
		YuanTableManage vo = new YuanTableManage();
		
		if (id != null && !id.equals("")) {
			QueryBuilder query = new QueryBuilder(YuanTableManage.class);
			query.where("id",id,QueryAction.EQUAL);
			query.orderBy("createDate");
			
			List<YuanTableManage> list = (List<YuanTableManage>) enterpriseService.query(query, 0);
			vo = list.get(0);
		}else {
			vo.setCreateDate(new Date());
			vo.setCreateUserName(sysUser.getUserName());
			vo.setCreateUserId(sysUser.getUserId());
		}
		
		vo.setTableName(tableName);
		vo.setParentId(parentId);
		vo.setDatasourceId(datasourceId);
		
		vo.setUpdateDate(new Date());
		vo.setUpdateUserName(sysUser.getUserName());
		vo.setUpdateUserId(sysUser.getUserId());
		
		yuanService.saveOrUpdate(vo);
		
		result = "1";
		JSONObject json = new JSONObject();
		json.put("result", result);
		json.put("vo", vo);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(json.toString());
    	
        return null;
    }


    /**
     * 删除
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public  @ResponseBody String delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String result = "0";
		try{
			if(StringUtils.isNotEmpty(id)){
				QueryBuilder query = new QueryBuilder(YuanTableManage.class);
				query.where("id",id,QueryAction.EQUAL);
				query.orderBy("createDate");
				
				enterpriseService.delete(query);
				
				query = new QueryBuilder(YuanTableManage.class);
				query.where("parentId",id,QueryAction.EQUAL);
				query.orderBy("createDate");
				List<YuanTableManage> list = (List<YuanTableManage>) enterpriseService.query(query, 0);
				
				for (int i = 0; i < list.size(); i++) {
					query = new QueryBuilder(YuanTableManage.class);
					query.where("id",list.get(i).getId(),QueryAction.EQUAL);
					query.orderBy("createDate");
					enterpriseService.delete(query);
				}
				
				result = "1";
			}
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }

}
