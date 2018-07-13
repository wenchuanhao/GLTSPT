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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.metadata.yuan.model.YuanTableColumnManage;
import com.cdc.dc.metadata.yuan.model.YuanTableManage;
import com.cdc.dc.metadata.yuan.service.IYuanService;
import com.cdc.system.core.authentication.controller.CommonController;

/**
 * 数据源校验
 * @author WEIFEI
 * @date 2016-7-12 下午4:18:46
 */
@Controller
@RequestMapping(value = "/yuancheck/")
public class YuanCheckController extends CommonController {

	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IYuanService yuanService;
	
    private Log logger = LogFactory.getLog(getClass());

    /**
     * 元数据列表配置
     * @author WEIFEI
     * @date 2016-7-15 下午4:05:31
     * @param request
     * @param response
     * @return	String
     */
	@RequestMapping(value = "listCheck", method = RequestMethod.GET)
    public String listCheck(HttpServletRequest request,HttpServletResponse response) {
		
    	//树结构数据
    	c(request);
		
		QueryBuilder query = new QueryBuilder(YuanTableManage.class);
		query.where("id",request.getParameter("id"),QueryAction.EQUAL);
		query.orderBy("createDate");
		
		List<YuanTableManage> list = (List<YuanTableManage>) enterpriseService.query(query, 0);
		
		YuanTableManage vo = list.get(0);
		
		request.setAttribute("vo", vo);
    	
		return "/dc/metadata/yuan/excel_yuan_tree_list";
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
		
    	return "/dc/metadata/yuan/excel_yuan_tree_list_right";
    }

    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    public @ResponseBody String saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	String result = "0";
		
		SysUser sysUser = getVisitor(request);
		
		String id = request.getParameter("id");

		Integer last = Integer.valueOf(request.getParameter("len"));
		
		for (int i = 1; i <= last; i++) {
			String cuurent_id = request.getParameter("cuurent_id" + i);
			String cuurent_table_id = request.getParameter("cuurent_table_id" + i);
			String cuurent_column_name = request.getParameter("cuurent_column_name"+i);
			String cuurent_data_type = request.getParameter("cuurent_data_type" + i);
			String cuurent_data_length = request.getParameter("cuurent_data_length" + i);
			String cuurent_nullable = request.getParameter("cuurent_nullable" + i);
			String cuurent_data_format = request.getParameter("cuurent_data_format" + i);
			String cuurent_show_order = request.getParameter("cuurent_show_order"+i);
			YuanTableColumnManage vo = new YuanTableColumnManage();
			vo.setId(cuurent_id);
			vo.setTableId(cuurent_table_id);
			vo.setColumnName(cuurent_column_name);
			vo.setDataType(cuurent_data_type);
			vo.setDataLength(Integer.parseInt(cuurent_data_length));
			vo.setNullable(cuurent_nullable);
			vo.setDataFormat(cuurent_data_format);
			vo.setShowOrder(Integer.parseInt(cuurent_show_order));
			yuanService.saveOrUpdate(vo);
		}
		result = "1";
		JSONObject json = new JSONObject();
		json.put("result", result);
		json.put("id", id);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(json.toString());
    	
        return null;
    }

}
