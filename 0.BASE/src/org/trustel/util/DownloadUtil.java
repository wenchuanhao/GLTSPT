package org.trustel.util;

import javax.servlet.http.HttpServletRequest;

import org.trustel.common.ConstantDefine;

public class DownloadUtil {

	/**
	 * 取得导出文件url
	 * 
	 * @param request
	 * @param visitor
	 * @return
	 */
	public static String getUrl(HttpServletRequest request, String realPath,String fileName) {
		createFolder(realPath);
		return realPath +ConstantDefine.FILE_SEPARATOR + fileName;
	}

	public static void createFolder(String folderpath) {
		java.io.File file = new java.io.File(folderpath);
		if (file.exists() && file.isDirectory()) {
			System.out.println("文件路径" + folderpath + "存在");
		} else {
			file.mkdirs();
			System.out.println("已成功创建文件路径" + folderpath);
		}
	}

}
