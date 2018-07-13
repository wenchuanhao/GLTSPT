package com.cdc.dc.project.jsxm.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryBuilder;
import org.trustel.system.SystemConstant;

import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.attach.model.GcAttach;
import com.cdc.dc.project.attach.tree.One;
import com.cdc.dc.project.attach.tree.Tree;
import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.jsxm.service.IJsxmService;
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.tzbm.service.ITzbmService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.system.core.util.SysRoleUserUtils;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 建设项目管理
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/jsxm/")
public class JsxmController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IJsxmService jsxmService;
	@Autowired
	private ITzbmService tzbmService;
	
    private Log logger = LogFactory.getLog(getClass());

    public static final Map<String, String> map1 = new HashMap<String, String>();
    
	public static final Map<String, String> map2 = new HashMap<String, String>();
	
	public static final Map<String, String> map3 = new HashMap<String, String>();
	
	/**
	 * 业务管理员-角色
	 */
	public static final String GC_YWGLY = "GC_YWGLY";
	
	/**
	 * 项目负责人-角色
	 */
	public static final String GC_XMFZR = "GC_XMFZR";
	
	/**
	 * 其他人员-角色
	 */
	public static final String GC_QTRY = "GC_QTRY";
	
	static{
		map1.put("1", "软件工程");
		map1.put("2", "集成工程");
		map1.put("3", "土建工程");
		map1.put("4", "征地工程");
		
		map3.put("1", "RJGC");
		map3.put("2", "JCGC");
		map3.put("3", "TJGC");
		map3.put("4", "ZDGC");
		
		map2.put("1", "正常");
		map2.put("2", "超前");
		map2.put("3", "滞后");
		map2.put("4", "完工");
	}

	private String CODE(String type){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		
		QueryBuilder query = new QueryBuilder(Jsxm.class);
		query.where(" column02 like '%"+(map3.get(type)+"-"+year)+"%' ");
		query.where(" deleteFlag = 'N' ");
		query.orderBy("column02 desc");
		List<Jsxm> list = (List<Jsxm>) enterpriseService.query(query, 0);
		
		String code = "00001";
		if(list != null && list.size() > 0) {
			Jsxm  vo = list.get(0);
			code = vo.getColumn02();
			if (StringUtils.isNotEmpty(code)) {
				int i = Integer.parseInt(code.substring(code.lastIndexOf("-")+1)) + 1;
				code = ("0000" + i).substring(("0000" + i).length() - 5);
    		}else{
    			code = "00001";
    		}
		}

		return map3.get(type)+"-"+year+"-"+code;
	}
	
	@RequestMapping(value = "getCode", method = {RequestMethod.POST,RequestMethod.GET})
	public String getCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String type = request.getParameter("type");
		PrintWriter writer = response.getWriter();
		writer.write(CODE(type));
		writer.close();
		
		return null;
	}
	
	/**
	 * 用户联想查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchUser", method = {RequestMethod.POST})
	public  void searchUser(HttpServletRequest request,HttpServletResponse response) {
    	try {
    	    response.setContentType("text/html; charset=utf-8");
    	    String userValue = request.getParameter("userValue");
    		String result = "";
  	        if(userValue != null && !"".equals(userValue)){
  	        	JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
  	        	String userNameSql = " u.user_name like '%"+userValue+"%'";
  	        	String accountSql = " or u.account like '%"+userValue+"%'";
//  	        	String sql = 
//  	        			"select u.user_id,u.user_name,u.account,o.org_name from sys_user u,sys_organization o where u.organization_id = o.organization_id(+)	" +
//  	        			" and (o.parent_id in (select organization_id from sys_organization o where o.org_name like '%筹建办%' )	or o.organization_id = '80df8fa55ca4048ac2314dab1a52d75e')" +
//  	        			" and (" +userNameSql+accountSql+")";
  	        	String sql = 
  	        			"select u.user_id,u.user_name,u.account,o.org_name from sys_user u,sys_organization o where u.organization_id = o.organization_id(+)	" +
  	        					" and (o.parent_id in (select organization_id from sys_organization o where 1=1 )	or o.organization_id = '80df8fa55ca4048ac2314dab1a52d75e')" +
  	        					" and (" +userNameSql+accountSql+")";
  	        	
  	        	List<Map<String, Object>> listMap = (List<Map<String, Object>>)jdbcTemplate.queryForList(sql);
  	        	
                JSONArray json = new JSONArray();
                
                if (listMap != null && listMap.size() > 0) {
                    for (int i=0;i<listMap.size();i++) {
                    	
                    	Map<String, Object> objMap = listMap.get(i);
                    	JSONObject jsonObject = new JSONObject();
                        JSONArray jsonArray = new JSONArray();
                        
                        jsonObject.put("userId",objMap.get("user_id"));
                        jsonObject.put("userName",objMap.get("user_name"));
                        jsonObject.put("account",objMap.get("account"));
                        jsonObject.put("orgName",objMap.get("org_name"));
                        jsonArray.add(jsonObject);

                        json.add(jsonArray);
                    }
                }
  	        	result = json.toString();
  	        }
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
    		writer.close();
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
	
	/**
	 * 联想查找建设项目
	 */
	@RequestMapping(value = "searchJSXM", method = {RequestMethod.POST,RequestMethod.GET})
	public String searchJSXM(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Jsxm jsxm = new Jsxm();
		
		String column02 = request.getParameter("column02");
		String column03 = request.getParameter("column03");
		
		jsxm.setColumn02(column02);
		jsxm.setColumn03(column03);
    	SysUser sysUser = getVisitor(request);
    	jsxm.setColumn10(sysUser.getUserId());
		jsxm.setPageSize(Integer.MAX_VALUE - 1);
		
		ItemPage itemPage = jsxmService.findJsxm(jsxm);
		
		List<Jsxm> list = (List<Jsxm>)itemPage.getItems();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray json = JSONArray.fromObject(list,jsonConfig);
		PrintWriter writer = response.getWriter();
		writer.write(json.toString());
		writer.close();
		
		return null;
	}
	
    /**
     * 投资编码列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "tzbmList",method = {RequestMethod.GET,RequestMethod.POST})
    public String tzbmList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	userRole(request);
    	String id = request.getParameter("id");
    	String pageIndex = request.getParameter("pageIndex");
    	String pageSize = request.getParameter("pageSize");
    	if (pageIndex == null || pageIndex.equals("")) {
    		pageIndex = "1";
		}
    	if (pageSize == null || pageSize.equals("")) {
    		pageSize = "15";
		} 
    	//投资编码列表
    	Tzbm tzbm = new Tzbm();
    	tzbm.setColumn04(id);//建设项目编码
    	tzbm.setPageIndex(Integer.valueOf(pageIndex));
    	tzbm.setPageSize(Integer.valueOf(pageSize));
    	ItemPage itemPage = tzbmService.findTzbm(tzbm);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("tzbm", tzbm);
    	
    	//投资编码列表合计
    	Tzbm tzbm_S = new Tzbm();
    	BeanUtils.copyProperties(tzbm, tzbm_S);
    	tzbm_S.setPageSize(Integer.MAX_VALUE - 1);    	
		List<Tzbm> list = (List<Tzbm>)tzbmService.findTzbm(tzbm_S).getItems();
		BigDecimal b1 = new BigDecimal(0);
		Tzbm tzbm_T = new Tzbm();
		for (Tzbm vo : list) {
			b1 = b1.add(vo.getColumn06());
		}
		tzbm_T.setColumn06(b1);
		request.setAttribute("tzbm_T", tzbm_T);
    	
    	//建设项目明细
    	Jsxm jsxm = new Jsxm();
		if (id != null && !id.equals("")) {
			jsxm = jsxmService.findJsxmById(id);
		}
    	request.setAttribute("vo", jsxm);
    	request.getSession().setAttribute("jsxmId", jsxm.getId());
        return "/dc/project/jsxm/tzbmList";
    }
    
    /**
     * 文档列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "fileList",method = {RequestMethod.GET,RequestMethod.POST})
    public String fileList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	userRole(request);
    	Jsxm jsxm = null;
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		if (id != null && !id.equals("")) {
			jsxm = jsxmService.findJsxmById(id);
		}else {
			jsxm = new Jsxm();
		}
    	request.setAttribute("vo", jsxm);
    	GcAttach.JSXM_ID = null;
    	GcAttach.TYPE_ID = jsxm.getId();
    	List<One> one = Tree.getTree(type, "1");//1建设项目文档		2投资编码文档		3子项目文档		4合同文档
    	request.setAttribute("fileType", "1");
    	request.setAttribute("one", one);
    	
        return "/dc/project/attach/fileList";
    }
    
	/**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,Jsxm jsxm) throws Exception{
		
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
		String[] header = {"项目类型","建设项目编号","建设项目名称","项目状态","建设总控","建设项目管理员","创建时间"};
		
		jsxm.setPageSize(Integer.MAX_VALUE - 1);
		jsxm.setIds(request.getParameter("ids"));
		ItemPage itemPage = jsxmService.findJsxm(jsxm);
		
		List<Jsxm> list = (List<Jsxm>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		for (Jsxm vo : list) {
			Object [] obj =  new Object[7];
			obj[0] = map1.get(vo.getColumn01());
			obj[1] = vo.getColumn02();
			obj[2] = vo.getColumn03();
			obj[3] = map2.get(vo.getColumn08());
			obj[4] = vo.getColumn04Name();
			obj[5] = vo.getColumn10Name();
			obj[6] = vo.getColumn07();
			
			listO.add(obj);
		}
		try {
			//文件名
			String fileName = "建设项目列表";
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			
			sysLogService.log(request, getVisitor(request), "工程管理--建设项目管理","导出建设项目", "导出【建设项目】", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * 当前用户角色
	 * @param request
	 * @throws Exception	void
	 */
	public static void userRole(HttpServletRequest request) throws Exception{
		SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
		request.setAttribute("sysUserId", sysUser.getUserId());
		//其他人员-角色
		if(SysRoleUserUtils.existsRole(sysUser.getUserId(), GC_QTRY)){
			request.setAttribute("role", GC_QTRY);
		}
		
		//项目负责人-角色
		if(SysRoleUserUtils.existsRole(sysUser.getUserId(), GC_XMFZR)){
			request.setAttribute("role", GC_XMFZR);
		}

		//业务管理员-角色
		if(SysRoleUserUtils.existsRole(sysUser.getUserId(), GC_YWGLY)){
			request.setAttribute("role", GC_YWGLY);
		}
	}

    /**
     * 树形列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "treeList",method = {RequestMethod.GET,RequestMethod.POST})
    public String treeList(HttpServletRequest request,HttpServletResponse response,Jsxm jsxm) throws Exception{
    	
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			jsxm = jsxmService.findJsxmById(id);
		}else {
			jsxm = new Jsxm();
		}
    	
    	request.setAttribute("vo", jsxm);
    	
        return "/dc/project/jsxm/treeList";
    }	

    /**
     * 选择建设项目列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "selectList",method = {RequestMethod.GET,RequestMethod.POST})
    public String selectList(HttpServletRequest request,HttpServletResponse response,Jsxm jsxm) throws Exception{

    	SysUser sysUser = getVisitor(request);
    	//jsxm.setColumn10(sysUser.getUserId());
    	listCommon(request, response, jsxm);
    	
        return "/dc/project/jsxm/selectList";
    }
    
    private void listCommon(HttpServletRequest request,HttpServletResponse response,Jsxm jsxm) throws Exception{
    	
    	userRole(request);
    	
    	ItemPage itemPage = jsxmService.findJsxm(jsxm);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	request.setAttribute("jsxm", jsxm);
    	request.setAttribute("depList", GcUtils.getDeptList());
    }
    
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,Jsxm jsxm) throws Exception{
    	
    	listCommon(request, response, jsxm);
    	
        return "/dc/project/jsxm/list";
    }	

    /**
     * 添加到首页
     */
    @RequestMapping(value = "addIndex", method = {RequestMethod.GET,RequestMethod.POST})
    public String addIndex(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	String mess = "";
		try {
			String [] ids = request.getParameter("ids").split(",");
			
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					if(!ids[i].equals("")){
						String type = request.getParameter("type");
						Jsxm jsxm = jsxmService.findJsxmById(ids[i]);
						if (type.equals("Y")) {
							jsxm.setIsNew("Y");
						    jsxm.setNewDate(new Date());
						}else {
							jsxm.setIsNew("N");
						    jsxm.setNewDate(null);
						}
						jsxmService.update(jsxm);
						sysLogService.log(request, getVisitor(request), "工程管理--建设项目管理","设置首页模块", "修改建设项目【"+jsxm.getColumn02()+"】", new Date(), "3", new Date(), null);
					}
				}
				mess = "s";
			}
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
    	
		PrintWriter writer = response.getWriter();
		writer.write(mess);
		writer.close();
		return null;
    }
    
    /**
     * 查看
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET,RequestMethod.POST})
    public String edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setAttribute("pageSource", request.getParameter("pageSource"));
    	Jsxm jsxm = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			jsxm = jsxmService.findJsxmById(id);
		}else {
			jsxm = new Jsxm();
		}
    	request.setAttribute("vo", jsxm);
    	
    	return "/dc/project/jsxm/edit";
    }
   
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	
    	Jsxm jsxm = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			jsxm = jsxmService.findJsxmById(id);
		}else {
			jsxm = new Jsxm();
			jsxm.setColumn07(new Date());
			SysUser sysUser = getVisitor(request);
			jsxm.setColumn10(sysUser.getUserId());
		}
    	request.setAttribute("vo", jsxm);
    	
    	return "/dc/project/jsxm/add";
    }

    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate(HttpServletRequest request, HttpServletResponse response,Jsxm jsxm) throws Exception{
		
		SysUser sysUser = getVisitor(request);
		
		String mess = "";
    	
		try {
			String id = jsxm.getId();
			
			String [] column04Id = request.getParameterValues("column04Id");
			String column04IdStr = "";
			if (column04Id != null && column04Id.length > 0) {
				for (int i = 0; i < column04Id.length; i++) {
					column04IdStr += column04Id[i]+",";
				}
			}
			jsxm.setColumn04(column04IdStr);
			
			String [] column05Id = request.getParameterValues("column05Id");
			String column05IdStr = "";
			if (column05Id != null && column05Id.length > 0) {
				for (int i = 0; i < column05Id.length; i++) {
					column05IdStr += column05Id[i]+",";
				}
			}
			jsxm.setColumn05(column05IdStr);
			
			String [] column10Id = request.getParameterValues("column10Id");
			String column10IdStr = "";
			if (column10Id != null && column10Id.length > 0) {
				for (int i = 0; i < column10Id.length; i++) {
					column10IdStr += column10Id[i]+",";
				}
			}
			jsxm.setColumn10(column10IdStr);
			
			if (id == null || id.equals("")) {
				jsxm.setCreateDate(new Date());
				jsxm.setCreateUserName(sysUser.getUserName());
				jsxm.setCreateUserId(sysUser.getUserId());
				jsxm.setColumn02(CODE(jsxm.getColumn01()));
				jsxm.setDeleteFlag("N");
				jsxm.setIsNew("N");
				jsxmService.save(jsxm);
				request.setAttribute("form_S", "add");
				sysLogService.log(request, getVisitor(request), "工程管理--建设项目管理", "新增建设项目", "新增建设项目【"+jsxm.getColumn02()+"】",new Date(), "3", new Date(), null);
			}else {
				jsxm.setUpdateDate(new Date());
				jsxm.setUpdateUserName(sysUser.getUserName());
				jsxm.setUpdateUserId(sysUser.getUserId());
				
				Jsxm oldJsxm = jsxmService.findJsxmById(jsxm.getId());
				jsxm.setCreateDate(oldJsxm.getCreateDate());
				jsxm.setCreateUserName(oldJsxm.getCreateUserName());
				jsxm.setCreateUserId(oldJsxm.getCreateUserId());
				jsxm.setDeleteFlag(oldJsxm.getDeleteFlag());
				jsxm.setIsNew(oldJsxm.getIsNew());
				jsxm.setNewDate(oldJsxm.getNewDate());
				
				BeanUtils.copyProperties(jsxm, oldJsxm);
				
				jsxmService.update(oldJsxm);
				request.setAttribute("form_S", "modify");
				sysLogService.log(request, getVisitor(request), "工程管理--建设项目管理", "修改建设项目","修改建设项目【"+oldJsxm.getColumn02()+"】", new Date(), "3", new Date(), null);
			}
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		
    	request.setAttribute("vo", jsxm);
    	request.setAttribute("mess", mess);
    	
    	return "/dc/project/jsxm/add";
    }


    /**
     * 删除
     */
    @RequestMapping(value = "delete", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String result = "0";
		try{
			if(id != null){
				Jsxm jsxm = jsxmService.findJsxmById(id);
				jsxm.setDeleteFlag("Y");
				jsxmService.update(jsxm);
				sysLogService.log(request, getVisitor(request), "工程管理--建设项目管理","删除建设项目", "删除建设项目【"+jsxm.getColumn02()+"】", new Date(), "3", new Date(), null);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		list(request, response, new Jsxm());
		
		result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);
    }

}
