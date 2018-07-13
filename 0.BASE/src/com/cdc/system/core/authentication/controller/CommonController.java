package com.cdc.system.core.authentication.controller;

import java.beans.PropertyEditorSupport;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.trustel.system.SystemConstant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import model.sys.entity.SysUser;

/**
 * 
 * @Description: 继承该类的子类不需要权限验证
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-18 下午03:24:54
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
public class CommonController {
	
	private Log logger = LogFactory.getLog(getClass());
	
	
	/**
	 * 返回当前登录的Visitor
	 * 
	 * @param request
	 * @return
	 */
	protected SysUser getVisitor(HttpServletRequest request) {
		return (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_VISITOR);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		final DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					setValue(dateFormat.parse(value));
				} catch (ParseException e1) {
					try {
						setValue(dateFormat2.parse(value));
					} catch (ParseException e2) {
						try {
							setValue(dateFormat3.parse(value));
						} catch (ParseException e3) {
							setValue(null);
						}
					}
				}
			}

			public String getAsText() {
				return dateFormat.format((Date) getValue());
			}

		});
	}

	/**
	 * 文件上传共用方法
	 * @param file
	 * @param fullFileName
	 * @return 上传操作结果
	 * @throws java.io.IOException
	 * @throws IllegalStateException
	 */
	public boolean uploadFile(MultipartFile file, String fullFileName)
			throws IllegalStateException, IOException {
		logger.info("上传文件：" + fullFileName);
		if (file == null || fullFileName == null
				|| "".equals(fullFileName.trim())) {
			logger.info("上传文件：文件对象为空或文件名为空！");
			return false;
		}
		file.transferTo(new File(fullFileName));
		logger.info("上传文件:成功!");
		return true;
	}

	/**
	 * 文件下载
	 *
	 * @param request
	 * @param response
	 * @param path
	 */
	public void downLoad(HttpServletRequest request,
						 HttpServletResponse response, String path) {
		try {
			String filePath = new String(path.getBytes("ISO-8859-1"), "UTF-8");
			File f = new File(filePath);
			if (!f.exists()) {
				response.sendError(404, "File not found!");
				return;
			}
			BufferedInputStream br = new BufferedInputStream(
					new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setCharacterEncoding("UTF-8");
			String fileName = URLEncoder.encode(f.getName(), "UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			OutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
