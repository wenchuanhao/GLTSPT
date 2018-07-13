package com.cdc.dc.project.zxmht.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
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

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.common.WorkorderNo;
import com.cdc.common.properties.DCConfig;
import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.attach.model.GcAttach;
import com.cdc.dc.project.attach.service.IGcAttachService;
import com.cdc.dc.project.attach.tree.One;
import com.cdc.dc.project.attach.tree.Tree;
import com.cdc.dc.project.jsxm.controller.JsxmController;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxm.service.IZxmService;
import com.cdc.dc.project.zxmht.model.HtKz;
import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.dc.project.zxmht.model.ZxmHtAttach;
import com.cdc.dc.project.zxmht.service.IHtKzService;
import com.cdc.dc.project.zxmht.service.IZxmHtService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.util.SpringHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.DateFunc;
import com.trustel.common.ItemPage;

/**
 *子项目合同管理
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/zxmHt/")
public class ZxmHtController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IZxmHtService zxmHtService;
	@Autowired
	private IGcAttachService gcAttachService;
	@Autowired
	private IHtKzService htKzService;
    private Log logger = LogFactory.getLog(getClass());
    
    /**
     * 文档列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "fileList",method = {RequestMethod.GET,RequestMethod.POST})
    public String fileList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	JsxmController.userRole(request);
    	ZxmHt zxmHt = null;
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		if (id != null && !id.equals("")) {
			zxmHt = zxmHtService.findZxmHtById(id);
		}else {
			zxmHt = new ZxmHt();
		}
    	request.setAttribute("vo", zxmHt);
    	GcAttach.JSXM_ID = null;
    	GcAttach.TYPE_ID = zxmHt.getId();
    	List<One> one = Tree.getTree(type, "4");//1建设项目文档		2投资编码文档		3子项目文档		4合同文档
    	request.setAttribute("fileType", "4");
    	request.setAttribute("one", one);
    	
        return "/dc/project/attach/fileList";
    }
    
	/**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,ZxmHt zxmHt) throws Exception{
		
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
		String[] header = {"子项目编号","子项目名称","合同编号","合同名称","合同含税金额（万元）","合同对方","合同状态","合同签订时间","合同归属人"};
		
		zxmHt.setPageSize(Integer.MAX_VALUE - 1);
		zxmHt.setIds(request.getParameter("ids"));
		ItemPage itemPage = zxmHtService.findZxmHt(zxmHt);
		
		List<ZxmHt> list = (List<ZxmHt>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		for (ZxmHt vo : list) {
			Object [] obj =  new Object[9];
			Zxm zxm = vo.getZxm();
			obj[0] = zxm.getColumn02();
			obj[1] = zxm.getColumn03();
			obj[2] = vo.getColumn01();
			obj[3] = vo.getColumn03();			
			obj[4] = vo.getColumn11();
			obj[5] = vo.getColumn14();
			obj[6] = vo.getColumn12();
			obj[7] = vo.getColumn19();
			obj[8] = vo.getColumn20Name();
			
			listO.add(obj);
		}
		try {
			//文件名
			String fileName = "子项目合同列表";
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			
			sysLogService.log(request, getVisitor(request), "工程管理--项目合同管理","导出项目合同", "导出【项目合同】", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}    
    
	/**
	 * 查找合同
	 */
	@RequestMapping(value = "searchHt", method = {RequestMethod.POST,RequestMethod.GET})
	public String searchHt(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		ZxmHt zxmHt = new ZxmHt();
		
		String column01 = request.getParameter("column01");
		String column03 = request.getParameter("column03");
		
		zxmHt.setColumn01(column01);
		zxmHt.setColumn03(column03);
    	SysUser sysUser = getVisitor(request);
    	zxmHt.setColumn21(sysUser.getUserId());
		zxmHt.setPageSize(Integer.MAX_VALUE - 1);
		
		ItemPage itemPage = zxmHtService.findZxmHt(zxmHt);
		
		List<ZxmHt> list = (List<ZxmHt>)itemPage.getItems();

		List<ZxmHt> listO =  new ArrayList<ZxmHt>();
		
		for (ZxmHt vo : list) {
			listO.add(vo);
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray json = JSONArray.fromObject(listO,jsonConfig);
		PrintWriter writer = response.getWriter();
		writer.write(json.toString());
		writer.close();
		
		return null;
	}
	
    /**
     * 选择项目合同列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "selectList",method = {RequestMethod.GET,RequestMethod.POST})
    public String selectList(HttpServletRequest request,HttpServletResponse response,ZxmHt zxmHt) throws Exception{
    	
    	SysUser sysUser = getVisitor(request);
    	String source = request.getParameter("source");
    	request.setAttribute("source", source);
    	listCommon(request, response, zxmHt);
    	
        return "/dc/project/zxmHt/selectList";
    }
    
    private void listCommon(HttpServletRequest request,HttpServletResponse response,ZxmHt zxmHt) throws Exception{
    	
    	JsxmController.userRole(request);
    	
    	ItemPage itemPage = zxmHtService.findZxmHt(zxmHt);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	request.setAttribute("zxmHt", zxmHt);
    	request.setAttribute("depList", GcUtils.getDeptList());
    	
    	//子项目合同列表合计
    	ZxmHt zxmHt_S = new ZxmHt();
    	BeanUtils.copyProperties(zxmHt, zxmHt_S);
    	zxmHt_S.setPageSize(Integer.MAX_VALUE - 1);    	
		List<ZxmHt> list = (List<ZxmHt>)zxmHtService.findZxmHt(zxmHt_S).getItems();
		BigDecimal column11 = new BigDecimal(0);
//		BigDecimal column09 = new BigDecimal(0);
//		BigDecimal htKzcolumn07 = new BigDecimal(0);
//		BigDecimal htKzcolumn09 = new BigDecimal(0);
//		BigDecimal htKzcolumn11 = new BigDecimal(0);
		for (ZxmHt vo : list) {
			if(vo.getColumn11() == null) continue;
			column11 = column11.add(vo.getColumn11());
//			column09 = column09.add(vo.getColumn09());
//			htKzcolumn07 = htKzcolumn07.add(BigDecimal.valueOf(Double.valueOf(vo.getHtKzcolumn07())));
//			htKzcolumn09 = htKzcolumn09.add(BigDecimal.valueOf(Double.valueOf(vo.getHtKzcolumn09())));
//			htKzcolumn11 = htKzcolumn11.add(BigDecimal.valueOf(Double.valueOf(vo.getHtKzcolumn11())));
		}
		Object [] obj =  new Object[5];
		obj[0] = column11;
//		obj[1] = column09;
//		obj[2] = htKzcolumn07;
//		obj[3] = htKzcolumn09;
//		obj[4] = htKzcolumn11;
		
		request.setAttribute("zxmHt_T", obj);
    }
    
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,ZxmHt zxmHt) throws Exception{
    	
    	listCommon(request, response, zxmHt);
    	
        return "/dc/project/zxmHt/list";
    }	
    
    /**
     * 我的合同列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "myList",method = {RequestMethod.GET,RequestMethod.POST})
    public String myList(HttpServletRequest request,HttpServletResponse response,ZxmHt zxmHt) throws Exception{
    	
    	listCommon(request, response, zxmHt);
    	
        return "/dc/project/zxmHt/list";
    }
    
    /**
     * 合同开支列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "htKzList",method = {RequestMethod.GET,RequestMethod.POST})
    public String htKzList(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
    	
    	//子项目合同明细
    	ZxmHt zxmHt = new ZxmHt();
		if (id != null && !id.equals("")) {
			zxmHt = zxmHtService.findZxmHtById(id);
		}
    	request.setAttribute("vo", zxmHt);
    	request.getSession().setAttribute("htId", zxmHt.getId());
    	
    	//合同开支列表
    	HtKz htKz = new HtKz();
    	htKz.setColumn01(zxmHt.getColumn01());//子项目合同编号
    	htKz.setPageIndex(Integer.valueOf(pageIndex));
    	htKz.setPageSize(Integer.valueOf(pageSize));
    	ItemPage itemPage = htKzService.findHtKz(htKz);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("htKz", htKz);
    	
        return "/dc/project/htKz/htKzList";
    }
    
    /**
     * 查看
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET,RequestMethod.POST})
    public String edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setAttribute("pageSource", request.getParameter("pageSource"));
    	ZxmHt zxmHt = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			zxmHt = zxmHtService.findZxmHtById(id);
		}else {
			zxmHt = new ZxmHt();
		}
    	request.setAttribute("vo", zxmHt);
    	
    	return "/dc/project/zxmHt/edit";
    }
   
    public void add_C(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	ZxmHt zxmHt = null;
		String id = request.getParameter("id");
		
		if (id != null && !id.equals("")) {
			zxmHt = zxmHtService.findZxmHtById(id);
		}else {
			zxmHt = new ZxmHt();
			SysUser sysUser = getVisitor(request);
			zxmHt.setColumn20("1");//合同来源1新增2合同管理系统
			zxmHt.setColumn21(sysUser.getUserId());
		}
		
		String zxmId = request.getParameter("zxmId");
		if(zxmId != null){
			IZxmService zxmService = (IZxmService)SpringHelper.getBean("zxmService");
			Zxm zxm = zxmService.findZxmById(zxmId);
			zxmHt.setColumn04(zxmId);
			zxmHt.setXmType(zxm.getColumn01());
		}
    	request.setAttribute("vo", zxmHt);
    	
    }
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add1", method = {RequestMethod.GET,RequestMethod.POST})
    public String add1(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	add_C(request, response);
    	return "/dc/project/zxmHt/add1";
    }
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	add_C(request, response);
    	return "/dc/project/zxmHt/add";
    }

    public  void saveOrUpdate_C(HttpServletRequest request, HttpServletResponse response,ZxmHt zxmHt,
    		@RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
		SysUser sysUser = getVisitor(request);
		
		String mess = "",column21IdStr = "";
    	
		try {
			String [] column21Id = request.getParameterValues("column21Id");
			if (column21Id != null && column21Id.length > 0) {
				for (int i = 0; i < column21Id.length; i++) {
					column21IdStr += column21Id[i]+",";
				}
			}
			
			String column21Ids = request.getParameter("column21Ids");
			if (column21Ids  != null && !column21Ids.equals("")) {
				String [] ids = column21Ids.substring(1).split(",");
				if (ids != null && ids.length > 0) {
					for (int i = 0; i < ids.length; i++) {
						column21IdStr += ids[i]+",";
					}
				}
			}
			zxmHt.setColumn21(column21IdStr);
			
			String id = zxmHt.getId();
			if (id == null || id.equals("")) {
				zxmHt.setCreateDate(new Date());
				zxmHt.setCreateUserName(sysUser.getUserName());
				zxmHt.setCreateUserId(sysUser.getUserId());
				zxmHt.setDeleteFlag("N");
				zxmHtService.save(zxmHt);
				request.setAttribute("form_S", "add");
				sysLogService.log(request, getVisitor(request), "工程管理--项目合同管理","新增项目合同", "新增项目合同【"+zxmHt.getColumn01()+"】", new Date(), "3", new Date(), null);
			}else {
				zxmHt.setUpdateDate(new Date());
				zxmHt.setUpdateUserName(sysUser.getUserName());
				zxmHt.setUpdateUserId(sysUser.getUserId());
				
				ZxmHt oldZxmHt = zxmHtService.findZxmHtById(zxmHt.getId());
				
				zxmHt.setCreateDate(oldZxmHt.getCreateDate());
				zxmHt.setCreateUserName(oldZxmHt.getCreateUserName());
				zxmHt.setCreateUserId(oldZxmHt.getCreateUserId());
				zxmHt.setDeleteFlag(oldZxmHt.getDeleteFlag());
				
				BeanUtils.copyProperties(zxmHt, oldZxmHt);
				
				zxmHtService.update(oldZxmHt);
				request.setAttribute("form_S", "modify");
				sysLogService.log(request, getVisitor(request), "工程管理--项目合同管理","修改项目合同", "修改项目合同【"+zxmHt.getColumn01()+"】", new Date(), "3", new Date(), null);
			}
			
			//附件上传
			if (file != null && request instanceof MultipartHttpServletRequest && !file.getOriginalFilename().equals("")) {
				//上传附件
				String filePath = upload(request, response, file);
				ZxmHtAttach htAttach = new ZxmHtAttach();
			    htAttach.setParentId(zxmHt.getId());
			    htAttach.setColumn01(file.getOriginalFilename());
			    htAttach.setColumn02(file.getSize()+"");
			    htAttach.setColumn03(filePath);
			    htAttach.setCreateDate(new Date());
			    htAttach.setCreateUserName(sysUser.getUserName());
			    htAttach.setCreateUserId(sysUser.getUserId());
				zxmHtService.saveZxmHtAttach(htAttach);
			}
			
			//附件复制到合同文档
			String [] attachIds = request.getParameterValues("attachIds");
			cp_attach(request,attachIds, zxmHt,"N");
			
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		
		String fileName = request.getParameter("fileName");
		if (fileName != null && !fileName.equals("")) {
			PrintWriter writer = response.getWriter();
			writer.write(zxmHt.getId());
			writer.close();
			return;
		}
		
    	request.setAttribute("vo", zxmHt);
    	request.setAttribute("mess", mess);
    }
    
    /**
     * 附件复制到合同文档
     * @date 2017-1-11 上午11:11:26
     * @param request
     * @param attachIds
     * @param zxmHt
     * @throws Exception	void
     */
    private void cp_attach(HttpServletRequest request,String [] attachIds,ZxmHt zxmHt,String is_cp) throws Exception{
		if (attachIds != null && attachIds.length > 0) {
			SysUser sysUser = getVisitor(request);
			Map<String, String> map = new HashMap<String, String>();
			GcAttach oldAttach = new GcAttach();
			oldAttach.setColumn10(zxmHt.getId());
			List<GcAttach> list = (List<GcAttach>)gcAttachService.findGcAttach(oldAttach).getItems();
			for (int i = 0; i < list.size(); i++) {
				GcAttach ga = list.get(i);
				map.put(ga.getColumn08(),ga.getId());
			}
			
			for (int i = 0; i < attachIds.length; i++) {
				ZxmHtAttach htAttach = zxmHtService.findZxmHtAttachById(attachIds[i]);
				if (htAttach != null) {
					
					if (map.containsKey(htAttach.getColumn01())) {
						gcAttachService.delete(map.get(htAttach.getColumn01()));
					}
					
					GcAttach attach = new GcAttach();
					
					Zxm zxm = zxmHt.getZxm();
					String xmType = zxm.getColumn01();
					if ((is_cp != null && !is_cp.equals("") && is_cp.equals("N")) && (xmType != null && !xmType.equals(""))) {
//						map1.put("1", "软件工程");34
//						map1.put("2", "集成工程");98
//						map1.put("3", "土建工程");138
//						map1.put("4", "征地工程");148
						String pId = "34";
						if (xmType.equals("2")) {
							pId = "98";
						}else if (xmType.equals("3")) {
							pId = "138";
						}else if (xmType.equals("4")) {
							pId = "148";
						}
						
					    attach.setParentId(pId);//父ID
					    attach.setColumn01(htAttach.getColumn01());//文档名称
					    attach.setColumn02(null);//有效期
					    attach.setColumn03("0");//份数
					    attach.setColumn04(null);//存放位置
					    attach.setColumn05(null);//借阅人
					    attach.setColumn06(null);//借阅人ID
				        attach.setColumn07("1");//文档类型1电子文档2纸质原件3纸质复印件
			     	    attach.setColumn08(htAttach.getColumn01());//文档原始名称
					    attach.setColumn09(htAttach.getColumn02());//文档大小
					    attach.setColumn10(zxmHt.getId());//业务类型1建设项目2投资编码3子项目4子项目合同
					    attach.setColumn11(htAttach.getColumn03());//文件目录
					    attach.setJsxmId(zxm.getTzbm().getColumn04());//建设项目ID
					     
					    attach.setDeleteFlag("N");
						attach.setCreateDate(new Date());
						attach.setCreateUserName(sysUser.getUserName());
						attach.setCreateUserId(sysUser.getUserId());
					    attach.setUpdateUserId(sysUser.getUserId());
					    attach.setUpdateUserName(sysUser.getUserName());
					    attach.setUpdateDate(new Date());
					    
					    gcAttachService.save(attach);
					}
				}
			}
		}
    }
    
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate1", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate1(HttpServletRequest request, HttpServletResponse response,ZxmHt zxmHt,
    		@RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
    	
    	saveOrUpdate_C(request, response, zxmHt, file);
    	return "/dc/project/zxmHt/add1";
    }
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate(HttpServletRequest request, HttpServletResponse response,ZxmHt zxmHt,
    		@RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
    	
    	saveOrUpdate_C(request, response, zxmHt, file);
    	return "/dc/project/zxmHt/add";
    }

    /**
     * 附件上传
     * @author WEIFEI
     * @date 2016-8-10 下午6:21:56
     * @param request
     * @param response
     * @param file
     * @throws Exception	void
     */
    private String upload(HttpServletRequest request, HttpServletResponse response,MultipartFile file) throws Exception{
    	
		String baseURL = DCConfig.getProperty("GC_ATTACH_URL");
		
		String filePath = File.separator + DateFunc.getCurrentDateString("yyyyMMdd");
		File dir = new File(baseURL + filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		} 
		String fileName = File.separator+ WorkorderNo.getUNID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		//上传附件
		super.uploadFile(file,dir.getPath()+ fileName);
		return filePath + fileName;
    }
    
    /**
     * 修改合同管理员
     */
    @RequestMapping(value = "modifyColumn21", method = {RequestMethod.GET,RequestMethod.POST})
    public String modifyColumn21(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	String mess = "";
		try {
			String [] ids = request.getParameter("ids").split(",");
			SysUser sysUser = getVisitor(request);
			
			if (ids != null) {
				String type = request.getParameter("type");
				String v = request.getParameter("returnValue");
				for (int i = 0; i < ids.length; i++) {
					if(!ids[i].equals("")){
						ZxmHt zxmHt = zxmHtService.findZxmHtById(ids[i]);
						if (type.equals("N")) {
							if (zxmHt.getColumn21() == null || zxmHt.getColumn21().equals("") || zxmHt.getColumn04() == null  || zxmHt.getColumn04().equals("")) {
								zxmHt.setColumn04(v.split("_")[0]);
								zxmHt.setXmType(v.split("_")[3]);
							}
							zxmHt.setColumn21(sysUser.getUserId());
						}else {
							if (zxmHt.getColumn21().replaceAll(",", "").equals(sysUser.getUserId())) {//当期管理员等于取消认领用户，清空子项目关系
								zxmHt.setColumn04(null);
								zxmHt.setXmType(null);
							}							
							zxmHt.setColumn21(zxmHt.getColumn21().replace(sysUser.getUserId(), ""));
							if (zxmHt.getColumn21() != null && !zxmHt.getColumn21().equals("") && zxmHt.getColumn21().equals(",")) {
								zxmHt.setColumn21(null);
							}
						}
						zxmHt.setUpdateDate(new Date());
						zxmHt.setUpdateUserName(sysUser.getUserName());
						zxmHt.setUpdateUserId(sysUser.getUserId());
						zxmHtService.update(zxmHt);
						
						ZxmHtAttach zh = new ZxmHtAttach();
						zh.setParentId(zxmHt.getId());
						List list = zxmHtService.findZxmHtAttach(zh).getItems();
						if (list != null && list.size() > 0) {
							String [] attachIds = new String[list.size()];
							for (int j = 0; j < list.size(); j++) {
								ZxmHtAttach vo = (ZxmHtAttach) list.get(j);
								attachIds[j] = vo.getId();
							}
							cp_attach(request, attachIds, zxmHt,type);
						}
						sysLogService.log(request, getVisitor(request), "工程管理--项目合同管理","认领项目合同", "认领项目合同【"+zxmHt.getColumn01()+"】", new Date(), "3", new Date(), null);
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
     * 删除附件
     */
    @RequestMapping(value = "deleteFile", method = {RequestMethod.GET,RequestMethod.POST})
    public String deleteFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String mess = "";
		try{
			if(id != null){
				ZxmHtAttach oldAttach = zxmHtService.findZxmHtAttachById(id);
				String baseURL = DCConfig.getProperty("GC_ATTACH_URL") + oldAttach.getColumn03();
				File dir = new File(baseURL);
				if (dir.exists()) {
					dir.delete();
					
				zxmHtService.deleteZxmHtAttach(id);
				sysLogService.log(request, getVisitor(request), "工程管理--项目合同管理","删除项目合同附件", "删除项目合同附件【"+oldAttach.getColumn01()+"】", new Date(), "3", new Date(), null);
				}
				
				mess = "s";
			}
        } catch (Exception e) {
        	mess = "e";
            e.printStackTrace();
        }
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(mess);
		
		return null;

    }
    
    /**
     * 下载附件
     */
    @RequestMapping(value = "downFile", method = {RequestMethod.GET,RequestMethod.POST})
    public void downFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		String id = request.getParameter("id");
        try {
			if(id != null){
				ZxmHtAttach oldAttach = zxmHtService.findZxmHtAttachById(id);
				String baseURL = DCConfig.getProperty("GC_ATTACH_URL") + oldAttach.getColumn03();
				File dir = new File(baseURL);
				if (dir.exists()) {
		        		response.setContentType("application/x-msdownload;");
		       	        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(oldAttach.getColumn01(), "utf-8"));
		
		    			bis = new BufferedInputStream(new FileInputStream(baseURL));
		    			bos = new BufferedOutputStream(response.getOutputStream());
		    			byte[] buff = new byte[2048];
		    			int bytesRead;
		    			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
		    				bos.write(buff, 0, bytesRead);
		    			}
		        	}
				sysLogService.log(request, getVisitor(request), "工程管理--项目合同管理","下载项目合同附件", "下载项目合同附件【"+oldAttach.getColumn01()+"】", new Date(), "3", new Date(), null);
	        	}
	       }catch (Exception e) {
	        	e.printStackTrace();
	       }finally {
				if (bis != null)bis.close();
				if (bos != null)bos.close();
	        }
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
				ZxmHt zxmHt = zxmHtService.findZxmHtById(id);
				zxmHt.setDeleteFlag("Y");
				zxmHtService.update(zxmHt);
				sysLogService.log(request, getVisitor(request), "工程管理--项目合同管理","删除项目合同", "删除项目合同【"+zxmHt.getColumn01()+"】", new Date(), "3", new Date(), null);
			}
        } catch (Exception e) {

            e.printStackTrace();
        }
		
		list(request, response, new ZxmHt());
		
		result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);

    }
    
    /**
	 * 验证合同编码唯一性
	 */
	@RequestMapping(value = "searchCode", method = {RequestMethod.POST,RequestMethod.GET})
	public String searchCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		ZxmHt zxmht = new ZxmHt();
		
		String column01 = request.getParameter("column01");
		
		zxmht.setColumn01(column01);
		
		zxmht.setPageSize(Integer.MAX_VALUE - 1);
		
		ItemPage itemPage = zxmHtService.findZxmHt(zxmht);
		
		List<ZxmHt> list = (List<ZxmHt>)itemPage.getItems();
		int result = 0;
		if(list != null && !list.isEmpty() && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				ZxmHt vo = list.get(i);
				if (vo.getColumn01() != null && !vo.getColumn01().equals("") && vo.getColumn01().equals(column01)) {
					result ++;
				}
			}
		}
		PrintWriter writer = response.getWriter();
		writer.write(result+"");
		writer.close();
		
		return null;
	}

}
