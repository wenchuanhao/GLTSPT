package com.cdc.dc.project.zb.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.zb.model.GcZbAudits;
import com.cdc.dc.project.zb.model.GczbListView;
import com.cdc.dc.project.zb.model.Zb;
import com.cdc.dc.project.zb.service.IZbService;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.util.ExcelUtil;
import com.trustel.common.ItemPage;

/**
 * 周报管理
 * @author ZengKai
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/zb/")
public class ZbController extends CommonController {

	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IZbService zbService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IRulesService rulesService;
	@Autowired
	private ISysUserService sysUserService;
	
    private Log logger = LogFactory.getLog(getClass());
	
    public static final Map<String, String> map1 = new HashMap<String, String>();
    
	public static final Map<String, String> map2 = new HashMap<String, String>();
	
	static{
		map1.put("1", "软件工程");
		map1.put("2", "集成工程");
		map1.put("3", "土建工程");
		map1.put("4", "征地工程");
		
		map2.put("1", "正常");
		map2.put("2", "超前");
		map2.put("3", "滞后");
		map2.put("4", "完工");
	}

    
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,Zb zb) throws Exception{
    	
    	SysUser sysUser = getVisitor(request);
    	//判断用户是否是周报管理员
    	SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(FxkForm.FXK_DICT_TYPE, FxkForm.GCZB_ADMIN).getParameterValue());
    	SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
    	if(rolesUser != null){
    		request.setAttribute("userRoles", FxkForm.GCZB_ADMIN);
    		zb.setUserRoles(FxkForm.GCZB_ADMIN);
    	}
    	
    	zb.setCreateUserId(sysUser.getUserId());//创建用户id
    	ItemPage itemPage = zbService.findZb(zb);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	
    	request.setAttribute("zb", zb);
    	
    	//牵头部门
		List<SysOrganization> list = zbService.querySysOrganizationList();
		request.setAttribute("leadDepList", list);//牵头部门
    	
        return "/dc/project/zb/list";
    }	
    
   
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	SysUser sysUser = getVisitor(request);
    	Zb zb = null;
    	//不为空则是编辑
		String id = request.getParameter("id");
		//新增时的项目编号:::建设项目/投资编码/子项目管理/子项目合同
		String code = request.getParameter("code");
		//项目类型，1：建设项目，2：投资编码，3：子项目，4：子项目合同，5：建设项目一览表  6：周报审核
		String proType = request.getParameter("proType");
		if (id != null && !id.equals("")) {
			zb = zbService.findZbById(id);
		}else {
			zb = new Zb();
		}
		
		if(zb != null){
			if(zb.getColumn13() == null){
				zb.setColumn13(new Date());//汇报时间
			}
			if(!StringUtils.isNotEmpty(zb.getColumn07UserId())){
				zb.setColumn07UserId(sysUser.getUserId());//负责人id
			}
			if(!StringUtils.isNotEmpty(zb.getColumn07())){
				zb.setColumn07(sysUser.getUserName());//负责人名称
			}
			if(!StringUtils.isNotEmpty(zb.getColumn07Departmen())){
//				SysOrganization parentOrg = rulesService.queryParentOrg(sysUser.getOrganizationId());
				zb.setColumn07Departmen(sysUser.getOrganizationId());//牵头部门
			}
			
			Calendar calendar = Calendar.getInstance();
	        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
	            calendar.add(Calendar.DAY_OF_WEEK, -1);
	        }
	        //汇报周期开始时间
	        if(zb.getColumn08() == null){
	        	zb.setColumn08(calendar.getTime());
	        }
	      //汇报周期结束时间
	        if(zb.getColumn09() == null){
	        	calendar.add(Calendar.DATE, 4);
	        	zb.setColumn09(calendar.getTime());
	        }
		}
		
    	request.setAttribute("vo", zb);
    	request.setAttribute("code", code);
    	request.setAttribute("proType", proType);
    	
    	//部门室经理审核员
    	List<SysUser> audits = zbService.querySysUserByOrg(sysUser.getOrganizationId());
    	request.setAttribute("audits", audits);
    	
    	return "/dc/project/zb/add";
    }

    @RequestMapping(value = "view", method = {RequestMethod.GET,RequestMethod.POST})
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String id = request.getParameter("id");//id
    	String projectId = request.getParameter("code");//项目ID
    	if(StringUtils.isNotEmpty(projectId)){
    		Jsxm jsxm = (Jsxm) enterpriseService.queryEntity(Jsxm.class, projectId);
    		if(jsxm != null){
    			return "redirect:/jsxm/edit?pageSource=zb&id="+projectId;
    		}
    		Tzbm tzbm = (Tzbm) enterpriseService.queryEntity(Tzbm.class, projectId);
    		if(tzbm != null){
    			return "redirect:/tzbm/edit?pageSource=zb&id="+projectId;
    		}
    		Zxm zxm = (Zxm)enterpriseService.queryEntity(Zxm.class, projectId);
    		if(zxm != null){
    			return "redirect:/zxm/edit?pageSource=zb&id="+projectId;
    		}
    		ZxmHt zxmHt = (ZxmHt) enterpriseService.queryEntity(ZxmHt.class, projectId);
    		if(zxmHt != null){
    			return "redirect:/zxmHt/edit?pageSource=zb&id="+projectId;
    		}
    	}
    	if(StringUtils.isNotEmpty(id)){
    		Zb zb = zbService.findZbById(id);
    		request.setAttribute("vo", zb);
    		//周报审核记录
    		List<GcZbAudits> list = zbService.queryGcZbAuditsList(id);
    		request.setAttribute("list", list);
    	}
    	return "/dc/project/zb/view";
    }
    
    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response,Zb zb){
		
		SysUser sysUser = getVisitor(request);
		String result = "0";

		zb.setUpdateDate(new Date());
		zb.setUpdateUserName(sysUser.getUserName());
		zb.setUpdateUserId(sysUser.getUserId());

		try {
			String id = zb.getId();
			if (id == null || "".equals(id.trim())) {
				zb.setCreateDate(new Date());
				zb.setCreateUserName(sysUser.getUserName());
				zb.setCreateUserId(sysUser.getUserId());
				zb.setDeleteFlag("N");
				zb.setZbStatus(Zb.zbStatus_SH);//审核中
				zbService.save(zb);
				sysLogService.log(request, getVisitor(request), "工程管理--周报管理","新增周报", "新增周报【"+zb.getColumn01()+"】", new Date(), "3", new Date(), null);
			}else {
				Zb oldzb = zbService.findZbById(id);
				zb.setCreateDate(oldzb.getCreateDate());
				zb.setCreateUserId(oldzb.getCreateUserId());
				zb.setCreateUserName(oldzb.getCreateUserName());
				zb.setDeleteFlag("N");
				zb.setZbStatus(Zb.zbStatus_SH);
				zbService.update(zb);
				sysLogService.log(request, getVisitor(request), "工程管理--周报管理","修改周报", "修改周报【"+zb.getColumn01()+"】", new Date(), "3", new Date(), null);
			}
			
			//给用户赋予角色
			SysRole sysRole = rulesService.querySysRoleByCode(Zb.zb_audit);
	    	String[] users = {zb.getAuditUserid()};
	    	sysUserService.addSysUserRoleId(sysRole,users);
	    	
			result = "1";
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
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
			if(StringUtils.isNotEmpty(id)){
				Zb zb = zbService.findZbById(id);
				zb.setDeleteFlag("Y");
				zbService.update(zb);
				sysLogService.log(request, getVisitor(request), "工程管理--周报管理","删除周报", "删除周报【"+zb.getColumn01()+"】", new Date(), "3", new Date(), null);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		list(request, response, new Zb());
		
		result = "1";
		PrintWriter out = response.getWriter();
		out.write(result);
    }
    

	/**
	 * 导出附件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportFile", method = {RequestMethod.POST,RequestMethod.GET})
	public void exportFile(HttpServletRequest request, HttpServletResponse response,Zb zb) throws Exception{
		
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
		zb.setZbIds(zbIds);
		// 表头状态
		String[] header = {"项目类型","项目编号","项目名称","周报名称","事项状态","本周状态","汇报周期",
				"本周工作","下周计划","关键点","存在问题","汇报人","汇报时间"};
		
		zb.setPageSize(Integer.MAX_VALUE - 1);
		SysUser sysUser = getVisitor(request);
    	//判断用户是否是周报管理员
    	SysRole sysRole = rulesService.querySysRoleByCode(SysParamHelper.getSysParamByCode(FxkForm.FXK_DICT_TYPE, FxkForm.GCZB_ADMIN).getParameterValue());
    	SysRoleUser rolesUser = rulesService.getRoleUsersByUserId(sysRole.getRoleId(),sysUser.getUserId());
    	if(rolesUser != null){
    		request.setAttribute("userRoles", FxkForm.GCZB_ADMIN);
    		zb.setUserRoles(FxkForm.GCZB_ADMIN);
    	}
    	zb.setCreateUserId(sysUser.getUserId());//创建用户id
		ItemPage itemPage = zbService.findZb(zb);
		
		List<GczbListView> list = (List<GczbListView>)itemPage.getItems();

		List<Object[]> listO =  new ArrayList<Object[]>();
		
		for (GczbListView vo : list) {
			Object [] obj =  new Object[13];
			obj[0] = map1.get(vo.getColumn14());//项目类型
			obj[1] = vo.getColumn02();
			obj[2] = vo.getProjectName();
			obj[3] = vo.getColumn01();
			obj[4] = map2.get(vo.getColumn10());//事项状态
			obj[5] = map2.get(vo.getColumn11());//本周状态
			obj[6] = (vo.getColumn08()==null?"":new SimpleDateFormat("yyyy/MM/dd").format(vo.getColumn08()))+ "--" + (vo.getColumn09()==null?"":new SimpleDateFormat("yyyy/MM/dd").format(vo.getColumn09()));
			obj[7] = vo.getColumn03();
					obj[8] = vo.getColumn04();
					obj[9] = vo.getColumn05();
					obj[10] = vo.getColumn06();
					obj[11] = vo.getColumn07();//汇报人
					obj[12] = vo.getColumn13();//汇报时间
			listO.add(obj);
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			//文件名
			String fileName = "周报列表" + sdf.format(new Date());
			ExcelUtil.exportForExcel(header, fileName, listO, book);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls");
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			book.write(response.getOutputStream());
			
			sysLogService.log(request, getVisitor(request), "工程管理--周报管理","导出周报", "导出【周报】", new Date(), "3", new Date(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	/**
     * 根据编码查询历史周报信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajaxLoadAuditUser", method = {RequestMethod.POST})
	public @ResponseBody void ajaxLoadAuditUser(HttpServletRequest request,HttpServletResponse response) {
    	String proId = request.getParameter("proId");
    	String productType = request.getParameter("productType");
    	List<SysUser> list = zbService.ajaxLoadAuditUser(proId,productType);
		try{
			JSONArray result = JSONArray.fromObject(list);
			PrintWriter out = response.getWriter();
			out.write(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
     * 根据编码查询历史周报信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajaxLoadHistory", method = {RequestMethod.POST})
	public @ResponseBody void ajaxLoadHistory(HttpServletRequest request,HttpServletResponse response) {
    	String proCode = request.getParameter("proCode");
    	SysUser sysUser = getVisitor(request);
		List<Zb> list = zbService.ajaxLoadHistory(proCode,sysUser.getUserId());
		try{
			JSONArray result = JSONArray.fromObject(list);
			PrintWriter out = response.getWriter();
			out.write(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 根据ID查询周报信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajaxCopyZb", method = {RequestMethod.POST})
    public @ResponseBody void ajaxCopyZb(HttpServletRequest request,HttpServletResponse response) {
    	String id = request.getParameter("id");
    	try{
    		if(StringUtils.isNotEmpty(id)){
    			Zb zb = zbService.findZbById(id);
    			JSONObject result = JSONObject.fromObject(zb);
    			PrintWriter out = response.getWriter();
    			out.write(result.toString());
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * 根据编码查询项目信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "searchProjectByCode", method = {RequestMethod.POST})
	public @ResponseBody void searchProjectByCode(HttpServletRequest request,HttpServletResponse response) {
    	try {
    	    response.setContentType("text/html; charset=utf-8");
    	    String code = request.getParameter("code");
    		String result = "";
  	        if(StringUtils.isNotEmpty(code)){
  	        	List<Object[]> projectList = zbService.searchProjectByCode(code);
                JSONArray userListJSONArray = new JSONArray();
                if (projectList != null && !projectList.isEmpty()) {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    Object[] obj;
                    for (int i = 0;i < projectList.size(); i++) {
                        obj = projectList.get(i);
                        jsonObject = new JSONObject();
                        jsonArray = new JSONArray();
                        jsonObject.put("proId",String.valueOf(obj[0]));
                        jsonObject.put("proType",String.valueOf(obj[1]));
                        jsonObject.put("proCode",String.valueOf(obj[2]));
                        jsonObject.put("proName",String.valueOf(obj[3]));
                        String userId = String.valueOf(obj[4]);//负责人ID列表，分隔符","
                        String[] userIds = userId.split(",");
                        String proUser = "";
                        String proUserName = "";
                        if(userIds != null && userIds.length > 0){
                        	for (int j = 0; j < userIds.length; j++) {
                        		String userid = userIds[j];
                        		if(StringUtils.isNotEmpty(userid)){
                        			//根据id查询用户信息
                        			SysUser user = (SysUser) enterpriseService.getById(SysUser.class, userid);
                        			if(j == 0){
                        				proUser += userid;
                        				if(user!=null)proUserName += user.getUserName();
                        			}else{
                        				proUser += ","+userid;
                        				if(user!=null)proUserName += ","+user.getUserName();
                        			}
                        		}
							}
                        }
                        jsonObject.put("proUser",proUser);//负责人列表
                        jsonObject.put("proUserName",proUserName);//负责人名称列表
                        jsonObject.put("proCreateUser",String.valueOf(obj[5]));
                        jsonObject.put("productType",String.valueOf(obj[6]));//项目分类
                        jsonArray.add(jsonObject);

                        userListJSONArray.add(jsonArray);
                    }
                }
  	        	result = userListJSONArray.toString();
  	        }
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
    
    /**
     * 根据名称查询所属科室
     * @param request
     * @param response
     */
    @RequestMapping(value = "searchDepartmenByName", method = {RequestMethod.POST})
	public @ResponseBody void searchDepartmenByName(HttpServletRequest request,HttpServletResponse response) {

    	try {
    	    response.setContentType("text/html; charset=utf-8");
    	    String code = request.getParameter("code");
    		String result = "";
  	        if(StringUtils.isNotEmpty(code)){
  	        	List<Object[]> depList = zbService.searchDepartmenByName(code);
                JSONArray depJson = new JSONArray();
                if (depList != null && !depList.isEmpty()) {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    Object[] obj;
                    for (int i = 0;i < depList.size(); i++) {
                        obj = depList.get(i);
                        jsonObject = new JSONObject();
                        jsonArray = new JSONArray();
                        jsonObject.put("organization_id",String.valueOf(obj[0]));
                        jsonObject.put("organization_name",String.valueOf(obj[1]));
                        jsonArray.add(jsonObject);
                        depJson.add(jsonArray);
                    }
                }
  	        	result = depJson.toString();
  	        }
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	
    }
    /**
     * 周报项目编号选择
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "selectList",method = {RequestMethod.GET,RequestMethod.POST})
    public String selectList(HttpServletRequest request,HttpServletResponse response,Zb zb){
    	ItemPage itemPage = zbService.selectList(zb);
    	if(itemPage != null && itemPage.getItems() != null) {
			List<Object[]> objsList = (List<Object[]>) itemPage.getItems();
			for(Object[] objs : objsList) {
				String[] userIds = String.valueOf(objs[4]).split(",");
                String proUserName = "";
                if(userIds != null && userIds.length > 0){
                	for (int j = 0; j < userIds.length; j++) {
                		String userid = userIds[j];
                		if(StringUtils.isNotEmpty(userid)){
                			//根据id查询用户信息
                			SysUser user = (SysUser) enterpriseService.getById(SysUser.class, userid);
                			if(j == 0){
                				if(user!=null)proUserName += user.getUserName();
                			}else{
                				if(user!=null)proUserName += ","+user.getUserName();
                			}
                		}
					}
                }
                objs[4] = proUserName;
			}
		}
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("zb", zb);
    	return "/dc/project/zb/selectList";
    }
}
