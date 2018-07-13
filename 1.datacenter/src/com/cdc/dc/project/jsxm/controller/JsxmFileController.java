package com.cdc.dc.project.jsxm.controller;

import java.util.ArrayList;
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
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.dc.project.attach.model.GcAttach;
import com.cdc.dc.project.attach.tree.One;
import com.cdc.dc.project.attach.tree.Tree;
import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.jsxm.service.IJsxmService;
import com.cdc.dc.project.tzbm.service.ITzbmService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 建设项目一览表
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/jsxmfile/")
public class JsxmFileController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IJsxmService jsxmService;
	@Autowired
	private ITzbmService tzbmService;
	
    private Log logger = LogFactory.getLog(getClass());
    
    /**
     * 文档列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "fileList",method = {RequestMethod.GET,RequestMethod.POST})
    public String fileList(HttpServletRequest request,HttpServletResponse response) throws Exception{

    	JsxmController.userRole(request);
    	
    	Jsxm jsxm = null;
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		if (id != null && !id.equals("")) {
			jsxm = jsxmService.findJsxmById(id);
		}else {
			jsxm = new Jsxm();
		}
    	request.setAttribute("vo", jsxm);
    	
    	GcAttach.JSXM_ID = jsxm.getId();
    	GcAttach.TYPE_ID = null;
    	
    	List<One> one = Tree.getTree(type,null);//1软件工程	2集成工程	3土建工程	4征地工程
    	request.setAttribute("fileType", "5");
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
		
		String zbIds = request.getParameter("zbIds");
		jsxm.setId(zbIds);
		
		// 表头状态
		String[] header = {"项目类型","建设项目编号","建设项目名称","项目状态","建设总控","建设项目管理员","创建时间"};
		
		jsxm.setPageSize(Integer.MAX_VALUE - 1);
		
		ItemPage itemPage = jsxmService.findJsxm(jsxm);
		
		List<Jsxm> list = (List<Jsxm>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		for (Jsxm vo : list) {
			Object [] obj =  new Object[7];
			obj[0] = JsxmController.map1.get(vo.getColumn01());
			obj[1] = vo.getColumn02();
			obj[2] = vo.getColumn03();
			obj[3] = JsxmController.map2.get(vo.getColumn08());
			obj[4] = vo.getColumn04Name();
			obj[5] = vo.getColumn10Name();
			obj[6] = vo.getColumn07();
			
			listO.add(obj);
		}
		try {
			//文件名
			String fileName = "建设项目一览表";
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,Jsxm jsxm) throws Exception{
    	
    	JsxmController.userRole(request);
    	
    	ItemPage itemPage = jsxmService.findJsxm(jsxm);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	request.setAttribute("jsxm", jsxm);
    	
        return "/dc/project/jsxm/jsxmFileList";
    }	
}
