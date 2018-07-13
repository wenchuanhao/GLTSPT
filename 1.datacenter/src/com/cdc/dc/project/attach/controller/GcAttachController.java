package com.cdc.dc.project.attach.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trustel.service.IEnterpriseService;
import org.trustel.system.SystemConstant;

import com.cdc.common.WorkorderNo;
import com.cdc.common.properties.DCConfig;
import com.cdc.dc.project.AntUtil;
import com.cdc.dc.project.attach.model.GcAttJy;
import com.cdc.dc.project.attach.model.GcAttach;
import com.cdc.dc.project.attach.service.IGcAttachService;
import com.cdc.dc.project.jsxm.controller.JsxmController;
import com.cdc.dc.project.tzbm.service.ITzbmService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.DateFunc;
import com.trustel.common.ItemPage;

/**
 * 文件上传
 * @author WEIFEI
 * @date 2016-7-26 上午11:21:49
 */
@Controller
@RequestMapping(value = "/fileUpload/")
public class GcAttachController extends CommonController {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private IGcAttachService gcAttachService;
	@Autowired
	private ITzbmService tzbmService;
	
    private Log logger = LogFactory.getLog(getClass());
    
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(HttpServletRequest request,HttpServletResponse response,GcAttach attach) throws Exception{
    	
    	JsxmController.userRole(request);
    	
    	ItemPage itemPage =gcAttachService.findGcAttach(attach);
		
    	request.setAttribute("listAttach",itemPage.getItems());
    	
    	request.setAttribute("attach",attach);
    	request.setAttribute("fileType",request.getParameter("fileType"));
    	
        return "/dc/project/attach/list";
    }	
    
   
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	SysUser sysUser = getVisitor(request);
    	GcAttach attach = null;
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		
		if (id != null && !id.equals("")) {
			attach =gcAttachService.findGcAttachById(id);
		}else {
			attach = new GcAttach();
			attach.setParentId(parentId);
		}
    	request.setAttribute("vo",attach);
    	request.setAttribute("userId", sysUser.getUserId());
    	
    	return "/dc/project/attach/uploadFile";
    }

    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdate(HttpServletRequest request, HttpServletResponse response,GcAttach attach,
    		@RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
		
		String mess = "";
		SysUser sysUser = getVisitor(request);
		try {
			String id = attach.getId();
			GcAttach oldAttach = attach;
			
			if (id == null || id.equals("")) {
				oldAttach.setId(null);
				oldAttach.setDeleteFlag("N");
				oldAttach.setCreateDate(new Date());
				oldAttach.setCreateUserName(sysUser.getUserName());
				oldAttach.setCreateUserId(sysUser.getUserId());
				sysLogService.log(request, getVisitor(request), "工程管理--附件管理","新增附件", "新增附件【"+file.getOriginalFilename()+"】", new Date(), "3", new Date(), null);
			}else {
				oldAttach = gcAttachService.findGcAttachById(id);
			    oldAttach.setColumn01(attach.getColumn01());
			    oldAttach.setColumn02(attach.getColumn02());
			    oldAttach.setColumn03(attach.getColumn03());
			    oldAttach.setColumn04(attach.getColumn04());
			    sysLogService.log(request, getVisitor(request), "工程管理--附件管理","修改附件", "修改附件【"+file.getOriginalFilename()+"】", new Date(), "3", new Date(), null);
			}
			
			if (file != null && request instanceof MultipartHttpServletRequest && !file.getOriginalFilename().equals("")) {
				//上传附件
				String filePath = upload(request, response, file);
				
				oldAttach.setColumn08(file.getOriginalFilename());
				oldAttach.setColumn09(file.getSize()+"");
				oldAttach.setColumn11(filePath);
			}
			oldAttach.setUpdateDate(new Date());
			oldAttach.setUpdateUserName(sysUser.getUserName());
			oldAttach.setUpdateUserId(sysUser.getUserId());
			String queueSize = request.getParameter("queueSize");
			if(queueSize != null && !queueSize.equals("") && Integer.valueOf(queueSize) > 1){
				oldAttach.setColumn01(file.getOriginalFilename());
			}
			gcAttachService.saveOrUpdate(oldAttach);
			
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		
    	request.setAttribute("vo",attach);
    	request.setAttribute("mess", mess);
    	request.setAttribute("userId", sysUser.getUserId());
    	
    	return "/dc/project/attach/uploadFile";
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
     * 删除
     */
    @RequestMapping(value = "delete", method = {RequestMethod.GET,RequestMethod.POST})
    public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String mess = "";
		try{
			if(id != null){
				GcAttach oldAttach = gcAttachService.findGcAttachById(id);
				
//				String filePath = oldAttach.getColumn11();
//				
//				String fileName = filePath.substring(filePath.lastIndexOf(File.separator), filePath.length());
				
				String baseURL = DCConfig.getProperty("GC_ATTACH_URL") + oldAttach.getColumn11();
				File dir = new File(baseURL);
				if (dir.exists()) {
					dir.delete();
				}
				
				gcAttachService.delete(id);
				
				sysLogService.log(request, getVisitor(request), "工程管理--附件管理","删除附件", "删除附件【"+oldAttach.getColumn08()+"】", new Date(), "3", new Date(), null);
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
     * 删除附件
     */
    @RequestMapping(value = "deleteFile", method = {RequestMethod.GET,RequestMethod.POST})
    public String deleteFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String mess = "";
		try{
			if(id != null){
				GcAttach oldAttach = gcAttachService.findGcAttachById(id);
				String baseURL = DCConfig.getProperty("GC_ATTACH_URL") + oldAttach.getColumn11();
				File dir = new File(baseURL);
				if (dir.exists()) {
					dir.delete();
					String filename = oldAttach.getColumn08();
					SysUser sysUser = getVisitor(request);
					oldAttach.setColumn08(null);
					oldAttach.setColumn09(null);
					oldAttach.setColumn11(null);
					oldAttach.setUpdateDate(new Date());
					oldAttach.setUpdateUserName(sysUser.getUserName());
					oldAttach.setUpdateUserId(sysUser.getUserId());
					
					gcAttachService.update(oldAttach);
					
					sysLogService.log(request, getVisitor(request), "工程管理--附件管理","删除附件", "删除附件【"+filename+"】", new Date(), "3", new Date(), null);
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
     * 打开下载附件
     */
    @RequestMapping(value = "open", method = {RequestMethod.GET,RequestMethod.POST})
    public String open(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	request.setAttribute("baseURL", request.getParameter("baseURL"));
    	request.setAttribute("type", request.getParameter("type"));
    	
    	return "/dc/project/attach/parentFileList";
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
				GcAttach oldAttach = gcAttachService.findGcAttachById(id);
				String baseURL = DCConfig.getProperty("GC_ATTACH_URL") + oldAttach.getColumn11();
				File dir = new File(baseURL);
				
				if (dir.exists()) {
	        		response.setContentType("application/x-msdownload;");
	       	        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(oldAttach.getColumn08(), "utf-8"));
	
	    			bis = new BufferedInputStream(new FileInputStream(baseURL));
	    			bos = new BufferedOutputStream(response.getOutputStream());
	    			byte[] buff = new byte[2048];
	    			int bytesRead;
	    			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	    				bos.write(buff, 0, bytesRead);
	    			}
	    			sysLogService.log(request, getVisitor(request), "工程管理--附件管理","下载附件", "下载附件【"+oldAttach.getColumn08()+"】", new Date(), "3", new Date(), null);
	        	}else {
	        		PrintWriter writer = response.getWriter();
	        		writer.write("N");
	        		writer.close();
				}
				
	       }
	       }catch (Exception e) {
				e.printStackTrace();
	       }finally {
				if (bis != null)bis.close();
				if (bos != null)bos.close();
	        }
    }    

    /**
     * 批量下载附件
     */
    @RequestMapping(value = "batchDownFile", method = {RequestMethod.GET,RequestMethod.POST})
    public void batchDownFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		String ids = request.getParameter("ids");
		String toFilePath = DCConfig.getProperty("GC_ATTACH_URL")  + File.separator + "GC_ATTACH_TEMP" + File.separator + System.currentTimeMillis() + File.separator;
        boolean flag = false;
		
		try {
			if(ids != null){
				AntUtil.mkdir(toFilePath);
				String [] attachids = ids.split(",");
				for (int i = 0; i < attachids.length; i++) {
					if (attachids[i].equals("")) {
						continue;
					}
					GcAttach oldAttach = gcAttachService.findGcAttachById(attachids[i]);
					String baseURL = DCConfig.getProperty("GC_ATTACH_URL") + oldAttach.getColumn11();
					File dir = new File(baseURL);
					if (dir.exists()) {
						AntUtil.copyFile(baseURL, toFilePath, oldAttach.getColumn08());
						if(!flag) flag = true;
		        	}
				}
				
				if(!flag){
					PrintWriter writer = response.getWriter();
	        		writer.write("N");
	        		writer.close();
	        		return;
				}
				
				//String zipPath = toFilePath + "附件批量下载"+DateFunc.getCurrentDateString("yyyyMMddHHmmss")+".zip";
				String zipPath = toFilePath + "download"+DateFunc.getCurrentDateString("yyyyMMddHHmmss")+".zip";
				AntUtil.compressor(toFilePath,zipPath);
        		response.setContentType("application/x-msdownload;");
       	        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("附件批量下载"+DateFunc.getCurrentDateString("yyyyMMddHHmmss")+".zip", "utf-8"));
       	        bis = new BufferedInputStream(new FileInputStream(zipPath));
    			bos = new BufferedOutputStream(response.getOutputStream());
    			byte[] buff = new byte[2048];
    			int bytesRead;
    			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
    				bos.write(buff, 0, bytesRead);
    			}
    			sysLogService.log(request, getVisitor(request), "工程管理--附件管理","下载附件", "批量下载【附件】", new Date(), "3", new Date(), null);
	       }
	       }catch (Exception e) {
				e.printStackTrace();
	       }finally {
				if (bis != null)bis.close();
				if (bos != null)bos.close();
				AntUtil.delete(toFilePath);
	        }
    } 
    
    //------------------------------------------------------------------------------------------------------------------------------------------------------
    
    
    /**
     * 列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "listJy",method = {RequestMethod.GET,RequestMethod.POST})
    public String listJy(HttpServletRequest request,HttpServletResponse response,GcAttJy attJy) throws Exception{
    	
    	String attachId = request.getParameter("attachId");
    	
    	GcAttach attach = gcAttachService.findGcAttachById(attachId);
    	attJy.setParentId(attachId);
    	
    	if(attJy.getColumn07() == null || attJy.getColumn07().equals("")){
    		attJy.setColumn07("1");
    	}
    	
    	ItemPage itemPage =gcAttachService.findGcAttJy(attJy);
		
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	request.setAttribute("vo", attJy);
    	request.setAttribute("attach",attach);
    	
        return "/dc/project/attach/jyList";
    }	
    
   
    /**
     * 新增一个表单
     */
    @RequestMapping(value = "addJy", method = {RequestMethod.GET,RequestMethod.POST})
    public String addJy(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	GcAttach attach = new GcAttach();
    	GcAttJy attJy = new GcAttJy();
		String attachId = request.getParameter("attachId");
		
		if (attachId != null && !attachId.equals("")) {
			attach = gcAttachService.findGcAttachById(attachId);
			attJy.setParentId(attachId);
		}
		SysUser sysUser = getVisitor(request);
		
    	request.setAttribute("attach",attach);
    	attJy.setColumn05(sysUser.getUserId());
    	attJy.setColumn06(sysUser.getUserName());
    	request.setAttribute("vo",attJy);
    	
    	return "/dc/project/attach/jyAdd";
    }

    /**
     * 保存或者修改
     */
    @RequestMapping(value = "saveOrUpdateJy", method = {RequestMethod.GET,RequestMethod.POST})
    public  String saveOrUpdateJy(HttpServletRequest request, HttpServletResponse response,GcAttJy attach) throws Exception{
		
    	Integer jyfs = Integer.valueOf(request.getParameter("jyfs"));
    	for (int i = 0; i < jyfs; i++) {
    		attach.setId(null);
    		common(request, response, attach);
    	}
    	
    	return "/dc/project/attach/jyAdd";
    }
    
    /**
     * 归还
     */
    @RequestMapping(value = "gh", method = {RequestMethod.GET,RequestMethod.POST})
    public  String gh(HttpServletRequest request, HttpServletResponse response,GcAttJy attach) throws Exception{
		
    	
		common(request, response, attach);
		attach.setColumn07("1");//文档状态1未归还
    	ItemPage itemPage =gcAttachService.findGcAttJy(attach);
		
    	request.setAttribute("vo",attach);
    	request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE,itemPage );
    	String column10 = request.getParameter("column10");
    	request.setAttribute("column10",column10);
    	
		return "/dc/project/attach/jyList";
    }
    
    private void common(HttpServletRequest request, HttpServletResponse response,GcAttJy attach) throws Exception{
		String mess = "";
		GcAttJy newAttJy = new GcAttJy();
		GcAttach oldAttach = null;
		try {
			SysUser sysUser = getVisitor(request);
			String id = attach.getId();
			newAttJy.setParentId(request.getParameter("attachId"));
			newAttJy.setColumn05(attach.getColumn05());
			newAttJy.setColumn06(attach.getColumn06());
			//修改文件数
			oldAttach = gcAttachService.findGcAttachById(newAttJy.getParentId());
			String column03 = oldAttach.getColumn03();
			if(column03 == null || column03.equals("")){
				column03 = "0";
			}else {
				if (id == null || id.equals("")) {
					column03 = (Integer.valueOf(column03) - 1)+"";
				}else{
					column03 = (Integer.valueOf(column03) + 1)+"";
				}
			}
			
			if (id == null || id.equals("")) {
				newAttJy.setId(null);
			    newAttJy.setColumn05(newAttJy.getColumn05());
			    newAttJy.setColumn06(newAttJy.getColumn06());
			    newAttJy.setColumn07("1");//文档状态1未归还2已还
				newAttJy.setCreateDate(new Date());
				newAttJy.setCreateUserName(sysUser.getUserName());
				newAttJy.setCreateUserId(sysUser.getUserId());
				
				oldAttach.setColumn05(newAttJy.getColumn05());
				oldAttach.setColumn06(newAttJy.getColumn06());
				sysLogService.log(request, getVisitor(request), "工程管理--附件管理","新增附件借阅", "新增附件借阅【"+oldAttach.getColumn08() +"】", new Date(), "3", new Date(), null);
			}else {
				newAttJy = gcAttachService.findGcAttJyById(id);
				newAttJy.setUpdateDate(new Date());
				newAttJy.setUpdateUserName(sysUser.getUserName());
				newAttJy.setUpdateUserId(sysUser.getUserId());
				newAttJy.setColumn07("2");//文档状态1未归还2已还
				
				sysLogService.log(request, getVisitor(request), "工程管理--附件管理","修改附件借阅", "修改附件借阅【"+oldAttach.getColumn08() +"】", new Date(), "3", new Date(), null);
				
				GcAttJy attJy2 = new GcAttJy();
				attJy2.setColumn07("1");//文档状态1未归还
		    	ItemPage itemPage =gcAttachService.findGcAttJy(attach);
				
				if(itemPage.getItems() != null && itemPage.getTotal() == 0){
					oldAttach.setColumn05(null);
					oldAttach.setColumn06(null);
				}
			}

			gcAttachService.saveOrUpdate(newAttJy);
			
			oldAttach.setColumn03(column03);
			gcAttachService.update(oldAttach);
			
			mess = "s";
		} catch (Exception e) {
			mess = "e";
			e.printStackTrace();
		}
		
		request.setAttribute("parentId", request.getParameter("fileId"));
    	request.setAttribute("attach",oldAttach);
    	request.setAttribute("vo",newAttJy);
    	request.setAttribute("mess", mess);
    }
}
