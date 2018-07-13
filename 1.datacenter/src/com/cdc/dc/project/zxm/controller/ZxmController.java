package com.cdc.dc.project.zxm.controller;

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
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.tzbm.service.ITzbmService;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxm.service.IZxmService;
import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.dc.project.zxmht.service.IZxmHtService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 *子项目管理
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/zxm/")
public class ZxmController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IZxmService zxmService;
	@Autowired
	private IZxmHtService zxmHtService;
	
	public static final Map<String, String> map3 = new HashMap<String, String>();
	
	static{
		map3.put("1", "RJ");
		map3.put("2", "JC");
		map3.put("3", "TJ");
		map3.put("4", "ZD");
	}
    private Log logger = LogFactory.getLog(getClass());
	
    private String CODE(String type){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		
		QueryBuilder query = new QueryBuilder(Zxm.class);
		query.where(" column02 like '%"+(map3.get(type)+"-"+year)+"%' ");
		query.where(" deleteFlag = 'N' ");
		query.orderBy("column02 desc");
		List<Zxm> list = (List<Zxm>) enterpriseService.query(query, 0);
		String code = "00001";
		if(list != null && list.size() > 0) {
			Zxm  vo = list.get(0);
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
	 * 联想查找子项目
	 */
	@RequestMapping(value = "searchZXM", method = {RequestMethod.POST,RequestMethod.GET})
	public String searchZXM(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Zxm zxm = new Zxm();
		
		String column02 = request.getParameter("column02");
		String column03 = request.getParameter("column03");
		
		zxm.setColumn02(column02);
		zxm.setColumn03(column03);
    	SysUser sysUser = getVisitor(request);
    	zxm.setColumn09(sysUser.getUserId());
		zxm.setPageSize(Integer.MAX_VALUE - 1);
		
		ItemPage itemPage = zxmService.findZxm(zxm);
		
		List<Zxm> list = (List<Zxm>)itemPage.getItems();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray json = JSONArray.fromObject(list,jsonConfig);
		PrintWriter writer = response.getWriter();
		writer.write(json.toString());
		writer.close();
		
		return null;
	}
    
    /**
     * 子项目合同列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "htList",method = {RequestMethod.GET,RequestMethod.POST})
    public String htList(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
    	
    	//子项目合同列表
    	ZxmHt zxmHt = new ZxmHt();
    	zxmHt.setColumn04(id);//子项目编号
    	zxmHt.setPageIndex(Integer.valueOf(pageIndex));
    	zxmHt.setPageSize(Integer.valueOf(pageSize));
    	ItemPage itemPage = zxmHtService.findZxmHt(zxmHt);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("zxmHt", zxmHt);
    	
    	//子项目合同列表合计
    	ZxmHt zxmHt_S = new ZxmHt();
    	BeanUtils.copyProperties(zxmHt, zxmHt_S);
    	zxmHt_S.setPageSize(Integer.MAX_VALUE - 1);    	
		List<ZxmHt> list = (List<ZxmHt>)zxmHtService.findZxmHt(zxmHt_S).getItems();
		BigDecimal column11 = new BigDecimal(0);
		BigDecimal column09 = new BigDecimal(0);
		BigDecimal htKzcolumn07 = new BigDecimal(0);
		BigDecimal htKzcolumn09 = new BigDecimal(0);
		BigDecimal htKzcolumn11 = new BigDecimal(0);
		for (ZxmHt vo : list) {
			if(vo.getColumn11() != null){
				column11 = column11.add(vo.getColumn11());
			}
			if(vo.getColumn09() != null){
				column09 = column09.add(vo.getColumn09());
			}
			htKzcolumn07 = htKzcolumn07.add(BigDecimal.valueOf(Double.valueOf(vo.getHtKzcolumn07())));
			htKzcolumn09 = htKzcolumn09.add(BigDecimal.valueOf(Double.valueOf(vo.getHtKzcolumn09())));
			htKzcolumn11 = htKzcolumn11.add(BigDecimal.valueOf(Double.valueOf(vo.getHtKzcolumn11())));
		}
		Object [] obj =  new Object[5];
		obj[0] = column11;
		obj[1] = column09;
		obj[2] = htKzcolumn07;
		obj[3] = htKzcolumn09;
		obj[4] = htKzcolumn11;
		
		request.setAttribute("zxmHt_T", obj);
    	
    	//子项目明细
    	Zxm zxm = new Zxm();
		if (id != null && !id.equals("")) {
			zxm = zxmService.findZxmById(id);
		}
    	request.setAttribute("vo", zxm);
    	request.getSession().setAttribute("zxmId", zxm.getId());
        return "/dc/project/zxm/zxmHtList";
    }
    
    
    /**
     * 文档列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "fileList",method = {RequestMethod.GET,RequestMethod.POST})
    public String fileList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	JsxmController.userRole(request);
    	Zxm zxm = null;
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		if (id != null && !id.equals("")) {
			zxm = zxmService.findZxmById(id);
		}else {
			zxm = new Zxm();
		}
    	request.setAttribute("vo", zxm);
    	GcAttach.JSXM_ID = null;
    	GcAttach.TYPE_ID = zxm.getId();
    	List<One> one = Tree.getTree(type, "3");//1建设项目文档		2投资编码文档		3子项目文档		4合同文档
    	request.setAttribute("fileType", "3");
    	request.setAttribute("one", one);
    	
        return "/dc/project/attach/fileList";
    }
    
	/**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,Zxm zxm) throws Exception{
		
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
		String[] header = {"项目类型","子项目编号","子项目名称","子项目负责人","子项目审核人","创建时间"};
		
		zxm.setPageSize(Integer.MAX_VALUE - 1);
		zxm.setIds(request.getParameter("ids"));
		ItemPage itemPage = zxmService.findZxm(zxm);
		
		List<Zxm> list = (List<Zxm>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		for (Zxm vo : list) {
			Object [] obj =  new Object[6];
			obj[0] = JsxmController.map1.get(vo.getColumn01());
			obj[1] = vo.getColumn02();
			obj[2] = vo.getColumn03();
			obj[3] = vo.getColumn04Name();
			obj[4] = vo.getColumn05Name();
			obj[5] = vo.getColumn08();
			
			listO.add(obj);
		}
		try {
			//文件名
			String fileName = "子项目列表";
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			
			sysLogService.log(request, getVisitor(request), "工程管理--子项目管理","导出子项目管理", "导出【子项目管理】", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 选择子项目列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "selectList",method = {RequestMethod.GET,RequestMethod.POST})
    public String selectList(HttpServletRequest request,HttpServletResponse response,Zxm zxm) throws Exception{
    	
    	SysUser sysUser = getVisitor(request);
    	//zxm.setColumn09(sysUser.getUserId());
    	listCommon(request, response, zxm);
    	
        return "/dc/project/zxm/selectList";
    }
    
    private void listCommon(HttpServletRequest request,HttpServletResponse response,Zxm zxm) throws Exception{
    	
    	JsxmController.userRole(request);
    	
    	ItemPage itemPage = zxmService.findZxm(zxm);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	request.setAttribute("zxm", zxm);
    	request.setAttribute("depList", GcUtils.getDeptList());
    }
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,Zxm zxm) throws Exception{
    	
    	listCommon(request, response, zxm);
    	
        return "/dc/project/zxm/list";
    }	
    
    /**
     * 查看
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET,RequestMethod.POST})
    public String edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	request.setAttribute("pageSource", request.getParameter("pageSource"));
    	Zxm zxm = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			zxm = zxmService.findZxmById(id);
		}else {
			zxm = new Zxm();
		}
    	request.setAttribute("vo", zxm);
    	
    	return "/dc/project/zxm/edit";
    }
    public void add_C(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	Zxm zxm = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			zxm = zxmService.findZxmById(id);
		}else {
			zxm = new Zxm();
			zxm.setColumn08(new Date());
			SysUser sysUser = getVisitor(request);
			zxm.setColumn09(sysUser.getUserId());
		}
		
		String tzbmId = request.getParameter("tzbmId");
		if(tzbmId != null){
			ITzbmService tzbmService = (ITzbmService)SpringHelper.getBean("tzbmService");
			Tzbm tzbm = tzbmService.findTzbmById(tzbmId);
			zxm.setColumn07(tzbmId);
			zxm.setColumn01(tzbm.getColumn01());
		}
		
    	request.setAttribute("vo", zxm);
    }
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add1", method = {RequestMethod.GET,RequestMethod.POST})
    public String add1(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	add_C(request, response);
    	return "/dc/project/zxm/add1";
    }
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	add_C(request, response);
    	return "/dc/project/zxm/add";
    }

    public  void saveOrUpdate_C(HttpServletRequest request, HttpServletResponse response,Zxm zxm) throws Exception{
		SysUser sysUser = getVisitor(request);
		
		String mess = "";
    	
		try {
			String id = zxm.getId();
			String [] column04Id = request.getParameterValues("column04Id");
			String column04IdStr = "";
			if (column04Id != null && column04Id.length > 0) {
				for (int i = 0; i < column04Id.length; i++) {
					column04IdStr += column04Id[i]+",";
				}
			}
			zxm.setColumn04(column04IdStr);
			
			String [] column05Id = request.getParameterValues("column05Id");
			String column05IdStr = "";
			if (column05Id != null && column05Id.length > 0) {
				for (int i = 0; i < column05Id.length; i++) {
					column05IdStr += column05Id[i]+",";
				}
			}
			zxm.setColumn05(column05IdStr);
			
			String [] column09Id = request.getParameterValues("column09Id");
			String column09IdStr = "";
			if (column09Id != null && column09Id.length > 0) {
				for (int i = 0; i < column09Id.length; i++) {
					column09IdStr += column09Id[i]+",";
				}
			}
			zxm.setColumn09(column09IdStr);			
			
			if (id == null || id.equals("")) {
				zxm.setCreateDate(new Date());
				zxm.setCreateUserName(sysUser.getUserName());
				zxm.setCreateUserId(sysUser.getUserId());
				zxm.setColumn02(CODE(zxm.getColumn01()));
				zxm.setDeleteFlag("N");
				zxmService.save(zxm);
				request.setAttribute("form_S", "add");
				sysLogService.log(request, getVisitor(request), "工程管理--子项目管理","新增子项目", "新增子项目【"+zxm.getColumn02()+"】", new Date(), "3", new Date(), null);
			}else {
				zxm.setUpdateDate(new Date());
				zxm.setUpdateUserName(sysUser.getUserName());
				zxm.setUpdateUserId(sysUser.getUserId());
				
				Zxm oldZxm = zxmService.findZxmById(zxm.getId());
				zxm.setCreateDate(oldZxm.getCreateDate());
				zxm.setCreateUserName(oldZxm.getCreateUserName());
				zxm.setCreateUserId(oldZxm.getCreateUserId());
				zxm.setDeleteFlag(oldZxm.getDeleteFlag());
				
				BeanUtils.copyProperties(zxm, oldZxm);
				
				zxmService.update(oldZxm);
				request.setAttribute("form_S", "modify");
				sysLogService.log(request, getVisitor(request), "工程管理--子项目管理","修改子项目", "修改子项目【"+zxm.getColumn02()+"】", new Date(), "3", new Date(), null);
			}
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		
    	request.setAttribute("vo", zxm);
    	request.setAttribute("mess", mess);
    }
    
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate1", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate1(HttpServletRequest request, HttpServletResponse response,Zxm zxm) throws Exception{
    	saveOrUpdate_C(request, response, zxm);
    	return "/dc/project/zxm/add1";
    }
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate(HttpServletRequest request, HttpServletResponse response,Zxm zxm) throws Exception{
    	saveOrUpdate_C(request, response, zxm);
    	return "/dc/project/zxm/add";
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
				Zxm zxm = zxmService.findZxmById(id);
				zxm.setDeleteFlag("Y");
				zxmService.update(zxm);
				sysLogService.log(request, getVisitor(request), "工程管理--子项目管理","删除子项目", "删除子项目【"+zxm.getColumn02()+"】", new Date(), "3", new Date(), null);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		list(request, response, new Zxm());
		
		result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);

    }

}
