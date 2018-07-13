package com.cdc.system.core.authentication.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @Description: 继承该类的子类需要权限验证
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-18 下午03:25:06
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
public class DefaultController extends CommonController implements Identifiable {
	private Log logger = LogFactory.getLog(getClass());

	public String getPrivilegeCode() {
		return getClass().getSimpleName();
	}

	/**
	 * 文件上传共用方法
	 * @param file
	 * @param fullFileName
	 * @return 上传操作结果
	 * @throws IOException
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