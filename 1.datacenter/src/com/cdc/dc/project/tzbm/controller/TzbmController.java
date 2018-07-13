package com.cdc.dc.project.tzbm.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
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
import com.cdc.dc.project.jsxm.controller.JsxmController;
import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.jsxm.service.IJsxmService;
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.tzbm.model.TzbmYear;
import com.cdc.dc.project.tzbm.service.ITzbmService;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxm.service.IZxmService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 投资编码管理
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/tzbm/")
public class TzbmController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private ITzbmService tzbmService;
	@Autowired
	private IZxmService zxmService;
	
    private Log logger = LogFactory.getLog(getClass());
	
    private String CODE(String type){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		
		QueryBuilder query = new QueryBuilder(Tzbm.class);
		query.where(" column02 like '%"+(JsxmController.map3.get(type)+"-"+year)+"%' ");
		query.where(" deleteFlag = 'N' ");
		query.orderBy("column02 desc");
		List<Tzbm> list = (List<Tzbm>) enterpriseService.query(query, 0);
		String code = "00001";
		if(list != null && list.size() > 0) {
			Tzbm  vo = list.get(0);
			code = vo.getColumn02();
			if (StringUtils.isNotEmpty(code)) {
				int i = Integer.parseInt(code.substring(code.lastIndexOf("-")+1)) + 1;
				code = ("0000" + i).substring(("0000" + i).length() - 5);
    		}else{
    			code = "00001";
    		}
		}

		return "TZ-"+JsxmController.map3.get(type)+"-"+year+"-"+code;
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
	 * 验证投资编码唯一性
	 */
	@RequestMapping(value = "searchCode", method = {RequestMethod.POST,RequestMethod.GET})
	public String searchCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Tzbm tzbm = new Tzbm();
		
		String column02 = request.getParameter("column02");
		
		tzbm.setColumn02(column02);
		
		tzbm.setPageSize(Integer.MAX_VALUE - 1);
		
		ItemPage itemPage = tzbmService.findTzbm(tzbm);
		
		List<Tzbm> list = (List<Tzbm>)itemPage.getItems();
		int result = 0;
		if(list != null && !list.isEmpty() && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				Tzbm vo = list.get(i);
				if (vo.getColumn02() != null && !vo.getColumn02().equals("") && vo.getColumn02().equals(column02)) {
					result ++;
				}
			}
		}
		PrintWriter writer = response.getWriter();
		writer.write(result+"");
		writer.close();
		
		return null;
	}
	
	/**
	 * 联想查找投资编码
	 */
	@RequestMapping(value = "searchTZBM", method = {RequestMethod.POST,RequestMethod.GET})
	public String searchTZBM(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Tzbm tzbm = new Tzbm();
		
		String column02 = request.getParameter("column02");
		String column03 = request.getParameter("column03");
		
		tzbm.setColumn02(column02);
		tzbm.setColumn03(column03);
    	SysUser sysUser = getVisitor(request);
    	tzbm.setColumn19(sysUser.getUserId());
		tzbm.setPageSize(Integer.MAX_VALUE - 1);
		
		ItemPage itemPage = tzbmService.findTzbm(tzbm);
		
		List<Tzbm> list = (List<Tzbm>)itemPage.getItems();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray json = JSONArray.fromObject(list,jsonConfig);
		PrintWriter writer = response.getWriter();
		writer.write(json.toString());
		writer.close();
		
		return null;
	}
    
    /**
     * 子项目列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "zxmList",method = {RequestMethod.GET,RequestMethod.POST})
    public String zxmList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	JsxmController.userRole(request);
    	String id = request.getParameter("id");
    	String pageIndex = request.getParameter("pageIndex");
    	String pageSize = request.getParameter("pageSize");
    	if (pageIndex == null || pageIndex.equals("")) {
    		pageIndex = "1";
		}
    	if (pageSize == null || pageSize.equals("")) {
    		pageSize = "15";
		} 
    	
    	//子项目列表
    	Zxm zxm = new Zxm();
    	zxm.setColumn07(id);//投资编码
    	zxm.setPageIndex(Integer.valueOf(pageIndex));
    	zxm.setPageSize(Integer.valueOf(pageSize));
    	ItemPage itemPage = zxmService.findZxm(zxm);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("zxm", zxm);
    	
    	//投资编码明细
    	Tzbm tzbm = new Tzbm();
		if (id != null && !id.equals("")) {
			tzbm = tzbmService.findTzbmById(id);
		}
    	request.setAttribute("vo", tzbm);
    	request.getSession().setAttribute("tzbmId", tzbm.getId());
        return "/dc/project/tzbm/zxmList";
    }
    
    /**
     * 文档列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "fileList",method = {RequestMethod.GET,RequestMethod.POST})
    public String fileList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	JsxmController.userRole(request);
    	Tzbm tzbm = null;
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		if (id != null && !id.equals("")) {
			tzbm = tzbmService.findTzbmById(id);
		}else {
			tzbm = new Tzbm();
		}
    	request.setAttribute("vo", tzbm);
    	GcAttach.JSXM_ID = null;
    	GcAttach.TYPE_ID = tzbm.getId();
    	List<One> one = Tree.getTree(type, "2");//1建设项目文档		2投资编码文档		3子项目文档		4合同文档
    	request.setAttribute("fileType", "2");
    	request.setAttribute("one", one);
    	
        return "/dc/project/attach/fileList";
    }
    
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
		String[] header = {"项目类型","投资编号","投资编号名称","投资总金额（万元）","建设期限","投资项目联系人","投资项目督办人","创建时间"};
		
		tzbm.setPageSize(Integer.MAX_VALUE - 1);
		tzbm.setIds(request.getParameter("ids"));
		ItemPage itemPage = tzbmService.findTzbm(tzbm);
		
		List<Tzbm> list = (List<Tzbm>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		for (Tzbm vo : list) {
			Object [] obj =  new Object[8];
			obj[0] = JsxmController.map1.get(vo.getColumn01());
			obj[1] = vo.getColumn02();
			obj[2] = vo.getColumn03();
			obj[3] = vo.getColumn06();
			obj[4] = vo.getColumn07();
			obj[5] = vo.getColumn13Name();
			obj[6] = vo.getColumn14Name();
			obj[7] = vo.getColumn17();
			
			listO.add(obj);
		}
		try {
			//文件名
			String fileName = "投资编码列表";
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			
			sysLogService.log(request, getVisitor(request), "工程管理--投资编码管理","导出投资编码", "导出【投资编码】", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 选择投资编码列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "selectList",method = {RequestMethod.GET,RequestMethod.POST})
    public String selectList(HttpServletRequest request,HttpServletResponse response,Tzbm tzbm) throws Exception{
    	SysUser sysUser = getVisitor(request);
    	//tzbm.setColumn19(sysUser.getUserId());
    	listCommon(request, response, tzbm);
    	
        return "/dc/project/tzbm/selectList";
    }
    
    private void listCommon(HttpServletRequest request,HttpServletResponse response,Tzbm tzbm) throws Exception{
    	
    	JsxmController.userRole(request);
    	
    	ItemPage itemPage = tzbmService.findTzbm(tzbm);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage);
    	
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
		
    	request.setAttribute("tzbm", tzbm);
    	request.setAttribute("depList", GcUtils.getDeptList());
    }
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,Tzbm tzbm) throws Exception{
    	
    	listCommon(request, response, tzbm);
    	
        return "/dc/project/tzbm/list";
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
						Tzbm tzbm = tzbmService.findTzbmById(ids[i]);
						if (type.equals("Y")) {
							tzbm.setIsNew("Y");
							tzbm.setNewDate(new Date());
						}else {
							tzbm.setIsNew("N");
							tzbm.setNewDate(null);
						}
						tzbmService.update(tzbm);
						sysLogService.log(request, getVisitor(request), "工程管理--投资编码管理","设置首页模块", "修改投资编码【"+tzbm.getColumn02()+"】", new Date(), "3", new Date(), null);
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
    	Tzbm tzbm = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			tzbm = tzbmService.findTzbmById(id);
		}else {
			tzbm = new Tzbm();
		}
    	request.setAttribute("vo", tzbm);
    	
    	return "/dc/project/tzbm/edit";
    }
   
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add1", method = {RequestMethod.GET,RequestMethod.POST})
    public String add1(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	add_C(request, response);
    	return "/dc/project/jsxm/tzbmAdd";
    }
    
    public void add_C(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	Tzbm tzbm = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			tzbm = tzbmService.findTzbmById(id);
		}else {
			tzbm = new Tzbm();
			String jsxmId = request.getParameter("jsxmId");
			if(jsxmId != null){
				IJsxmService jsxmService = (IJsxmService)SpringHelper.getBean("jsxmService");
				Jsxm jsxm = jsxmService.findJsxmById(jsxmId);
				tzbm.setColumn04(jsxmId);
				tzbm.setColumn01(jsxm.getColumn01());
			}
			
			tzbm.setColumn17(new Date());
			SysUser sysUser = getVisitor(request);
			tzbm.setColumn19(sysUser.getUserId());
		}
    	request.setAttribute("vo", tzbm);
    }
    
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	add_C(request, response);
    	return "/dc/project/tzbm/add";
    }

    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate1", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate1(HttpServletRequest request, HttpServletResponse response,Tzbm tzbm) throws Exception{
    	saveOrUpdate_C(request, response, tzbm);
    	return "/dc/project/jsxm/tzbmAdd";
    }
    
    public void saveOrUpdate_C(HttpServletRequest request, HttpServletResponse response,Tzbm tzbm){
		SysUser sysUser = getVisitor(request);
		
		String mess = "";
    	
		try {
			String id = tzbm.getId();
			
			String [] column13Id = request.getParameterValues("column13Id");
			String column13IdStr = "";
			if (column13Id != null && column13Id.length > 0) {
				for (int i = 0; i < column13Id.length; i++) {
					column13IdStr += column13Id[i]+",";
				}
			}
			tzbm.setColumn13(column13IdStr);
			
			String [] column14Id = request.getParameterValues("column14Id");
			String column14IdStr = "";
			if (column14Id != null && column14Id.length > 0) {
				for (int i = 0; i < column14Id.length; i++) {
					column14IdStr += column14Id[i]+",";
				}
			}
			tzbm.setColumn14(column14IdStr);
			
			String [] column19Id = request.getParameterValues("column19Id");
			String column19IdStr = "";
			if (column19Id != null && column19Id.length > 0) {
				for (int i = 0; i < column19Id.length; i++) {
					column19IdStr += column19Id[i]+",";
				}
			}
			tzbm.setColumn19(column19IdStr);			
			
			if (id == null || id.equals("")) {
				tzbm.setCreateDate(new Date());
				tzbm.setCreateUserName(sysUser.getUserName());
				tzbm.setCreateUserId(sysUser.getUserId());
				tzbm.setColumn02(tzbm.getColumn02());
				tzbm.setDeleteFlag("N");
				tzbm.setIsNew("N");
				tzbmService.save(tzbm);
				request.setAttribute("form_S", "add");
				sysLogService.log(request, getVisitor(request), "工程管理--投资编码管理","新增投资编码", "新增投资编码【"+tzbm.getColumn02()+"】", new Date(), "3", new Date(), null);
			}else {
				tzbm.setUpdateDate(new Date());
				tzbm.setUpdateUserName(sysUser.getUserName());
				tzbm.setUpdateUserId(sysUser.getUserId());
				
				Tzbm oldTzbm = tzbmService.findTzbmById(tzbm.getId());
				
				tzbm.setCreateDate(oldTzbm.getCreateDate());
				tzbm.setCreateUserName(oldTzbm.getCreateUserName());
				tzbm.setCreateUserId(oldTzbm.getCreateUserId());
				tzbm.setDeleteFlag(oldTzbm.getDeleteFlag());
				tzbm.setIsNew(oldTzbm.getIsNew());
				tzbm.setNewDate(oldTzbm.getNewDate());
				
				BeanUtils.copyProperties(tzbm, oldTzbm);
				tzbmService.update(oldTzbm);
				request.setAttribute("form_S", "modify");
				sysLogService.log(request, getVisitor(request), "工程管理--投资编码管理","修改投资编码", "修改投资编码【"+tzbm.getColumn02()+"】", new Date(), "3", new Date(), null);
			}
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		
    	request.setAttribute("vo", tzbm);
    	request.setAttribute("mess", mess);
    }
    
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate(HttpServletRequest request, HttpServletResponse response,Tzbm tzbm) throws Exception{
		
    	saveOrUpdate_C(request, response, tzbm);
    	
    	return "/dc/project/tzbm/add";
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
				Tzbm tzbm = tzbmService.findTzbmById(id);
				tzbm.setDeleteFlag("Y");
				tzbmService.update(tzbm);
				sysLogService.log(request, getVisitor(request), "工程管理--投资编码管理","删除投资编码", "删除投资编码【"+tzbm.getColumn02()+"】", new Date(), "3", new Date(), null);
			}
        } catch (Exception e) {

            e.printStackTrace();
        }
		
		list(request, response, new Tzbm());
		
		result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);

    }

    //------------------------------------------------------------------------------------------------------------------
    
    public void yearList_C(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	String id = request.getParameter("parentId");
    	
    	//年底目标列表
    	TzbmYear tzbmYear = new TzbmYear();
    	tzbmYear.setParentId(id);//投资编码ID
    	ItemPage itemPage = tzbmService.findTzbmYear(tzbmYear);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	//投资编码明细
    	Tzbm tzbm = new Tzbm();
		if (id != null && !id.equals("")) {
			tzbm = tzbmService.findTzbmById(id);
		}
    	request.setAttribute("vo", tzbm);
    }
    /**
     * 年度目标列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "yearList1",method = {RequestMethod.GET,RequestMethod.POST})
    public String yearList1(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	yearList_C(request, response);
    	
        return "/dc/project/tzbm/yearList1";
    } 
    /**
     * 年度目标列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "yearList",method = {RequestMethod.GET,RequestMethod.POST})
    public String yearList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	yearList_C(request, response);
    	
        return "/dc/project/tzbm/yearList";
    }	
    
    public void yearAdd_C(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	TzbmYear tzbmYear = null;
		String id = request.getParameter("id");
		int sysYear = Calendar.getInstance().get(Calendar.YEAR);
		
		if (id != null && !id.equals("")) {
			tzbmYear = tzbmService.findTzbmYearById(id);
		}else {
			tzbmYear = new TzbmYear();
			tzbmYear.setColumn01(sysYear+"");
		}
		tzbmYear.setParentId(request.getParameter("parentId"));
		
		
		int s_year = sysYear - 5;
		int e_year = sysYear + 5;
		String options = "";
		for (int i = s_year; i <= e_year; i++) {
			options += "<option "+((i == Integer.valueOf(tzbmYear.getColumn01()) ) == true ? "selected=\"selected\"":"")+" value=\""+i+"\">"+i+"</option>";
		}
		request.setAttribute("options", options);
		
		SysUser sysUser = getVisitor(request);
		tzbmYear.setColumn08(sysUser.getUserId());
		tzbmYear.setColumn06(sysUser.getUserName());
		tzbmYear.setColumn07(new Date());
    	request.setAttribute("vo", tzbmYear);
    }
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "yearAdd1", method = {RequestMethod.GET,RequestMethod.POST})
    public String yearAdd1(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	yearAdd_C(request, response);
    	return "/dc/project/tzbm/yearAdd1";
    }
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "yearAdd", method = {RequestMethod.GET,RequestMethod.POST})
    public String yearAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	yearAdd_C(request, response);
    	return "/dc/project/tzbm/yearAdd";
    }
    
    public  void saveOrUpdateYear_C(HttpServletRequest request, HttpServletResponse response,TzbmYear tzbmYear) throws Exception{
SysUser sysUser = getVisitor(request);
		
		String mess = "";
    	
		try {
			String id = tzbmYear.getId();
			if (id == null || id.equals("")) {
				tzbmYear.setCreateDate(new Date());
				tzbmYear.setCreateUserName(sysUser.getUserName());
				tzbmYear.setCreateUserId(sysUser.getUserId());
				tzbmService.save(tzbmYear);
				request.setAttribute("form_S", "add");
				sysLogService.log(request, getVisitor(request), "工程管理--投资编码管理","新增本年度目标", "新增【本年度目标】", new Date(), "3", new Date(), null);
			}else {
				tzbmYear.setUpdateDate(new Date());
				tzbmYear.setUpdateUserName(sysUser.getUserName());
				tzbmYear.setUpdateUserId(sysUser.getUserId());
				
				TzbmYear oldTzbmYear = tzbmService.findTzbmYearById(tzbmYear.getId());
				
				tzbmYear.setCreateDate(oldTzbmYear.getCreateDate());
				tzbmYear.setCreateUserName(oldTzbmYear.getCreateUserName());
				tzbmYear.setCreateUserId(oldTzbmYear.getCreateUserId());
				tzbmYear.setDeleteFlag(oldTzbmYear.getDeleteFlag());
				
				BeanUtils.copyProperties(tzbmYear, oldTzbmYear);
				
				tzbmService.update(oldTzbmYear);
				request.setAttribute("form_S", "modify");
				sysLogService.log(request, getVisitor(request), "工程管理--投资编码管理","修改本年度目标", "修改【本年度目标】", new Date(), "3", new Date(), null);
			}
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		
		int sysYear = Integer.valueOf(tzbmYear.getColumn01());
		int s_year = sysYear - 5;
		int e_year = sysYear + 5;
		String options = "";
		for (int i = s_year; i <= e_year; i++) {
			options += "<option "+((i == sysYear) == true ? "selected=\"selected\"":"")+" value=\""+i+"\">"+i+"</option>";
		}
		request.setAttribute("options", options);
		
    	request.setAttribute("vo", tzbmYear);
    	request.setAttribute("mess", mess);
    	
    }
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdateYear1", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdateYear1(HttpServletRequest request, HttpServletResponse response,TzbmYear tzbmYear) throws Exception{
    	saveOrUpdateYear_C(request, response, tzbmYear);
    	return "/dc/project/tzbm/yearAdd1";
    } 
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdateYear", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdateYear(HttpServletRequest request, HttpServletResponse response,TzbmYear tzbmYear) throws Exception{
    	saveOrUpdateYear_C(request, response, tzbmYear);
    	return "/dc/project/tzbm/yearAdd";
    }

    public void deleteYear_C(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");
		String mess = "";
		try{
			if(id != null){
				tzbmService.deleteTzbmYear(id);
				sysLogService.log(request, getVisitor(request), "工程管理--投资编码管理","删除本年度目标", "删除【本年度目标】", new Date(), "3", new Date(), null);
				mess = "s";
			}
        } catch (Exception e) {
        	mess = "e";
            e.printStackTrace();
        }
		
		yearList(request, response);
		
		request.setAttribute("mess", mess);
    }
    /**
     * 删除
     */
    @RequestMapping(value = "deleteYear1", method = {RequestMethod.GET,RequestMethod.POST})
    public String deleteYear1(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	deleteYear_C(request, response);
		return "/dc/project/tzbm/yearList1";
    }	
    /**
     * 删除
     */
    @RequestMapping(value = "deleteYear", method = {RequestMethod.GET,RequestMethod.POST})
    public String deleteYear(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	deleteYear_C(request, response);
		return "/dc/project/tzbm/yearList";
    }
    
    
    public void useYear_C(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");
		
		String parentId = request.getParameter("parentId");
		String mess = "";
		try{
			if(id != null){
				
				TzbmYear tzbmYear = tzbmService.findTzbmYearById(id);
				
				Tzbm tzbm = tzbmService.findTzbmById(parentId);
				
				tzbm.setColumn08(tzbmYear.getColumn02());//年度投资
				tzbm.setColumn09(tzbmYear.getColumn03());//资本开支目标
				tzbm.setColumn11(tzbmYear.getColumn04());//至上年度安排投资计划
				tzbm.setColumn12(tzbmYear.getColumn05());//年度转资目标
				
				SysUser sysUser = getVisitor(request);
				tzbm.setUpdateDate(new Date());
				tzbm.setUpdateUserName(sysUser.getUserName());
				tzbm.setUpdateUserId(sysUser.getUserId());
				
				tzbmService.update(tzbm);
				sysLogService.log(request, getVisitor(request), "工程管理--投资编码管理","修改本年度目标", "修改【本年度目标】", new Date(), "3", new Date(), null);
				mess = "s";
			}
        } catch (Exception e) {
        	mess = "e";
            e.printStackTrace();
        }
		
		yearList(request, response);
		
		request.setAttribute("mess", mess);
    }
    /**
     * 为本年度目标
     */
    @RequestMapping(value = "useYear1", method = {RequestMethod.GET,RequestMethod.POST})
    public String useYear1(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	useYear_C(request, response);
		return "/dc/project/tzbm/yearList1";
    }
    /**
     * 为本年度目标
     */
    @RequestMapping(value = "useYear", method = {RequestMethod.GET,RequestMethod.POST})
    public String useYear(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	useYear_C(request, response);
		return "/dc/project/tzbm/yearList";
    }
}
