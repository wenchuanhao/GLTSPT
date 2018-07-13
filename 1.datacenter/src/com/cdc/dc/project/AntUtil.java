package com.cdc.dc.project;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * Ant 工具类
 * @author weifei
 * @date Mar 13, 2012 11:38:40 AM
 */
public class AntUtil {
	
	/**
	 * 创建目录
	 * @author weifei
	 * @date Mar 13, 2012 11:47:27 AM
	 * @param filePath
	 * @throws RuntimeException	void
	 */
	public static void mkdir(String filePath) throws RuntimeException {
		
		File file = new File(filePath);
		if (!file.exists()) {
			Project project = new Project();
			Mkdir mkdir = new Mkdir();
			mkdir.setProject(project);
			mkdir.setDir(file);
			mkdir.execute();
		}
	}
	
	/**
	 * 压缩
	 * @author weifei
	 * @date Mar 13, 2012 11:40:44 AM
	 * @param srcFilePath
	 * @throws RuntimeException void
	 */
	public static void compressor(String srcFilePath, String toFilePath)
			throws RuntimeException {

		File srcDir = new File(srcFilePath);
		if (!srcDir.exists())
			throw new RuntimeException(srcFilePath + "文件不存在！");
		
		File toFile = new File(toFilePath);
		if (toFile.exists()) {
			toFile.delete();
			System.out.println("delete----------------");
		}
		
		Project prj = new Project();

		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcDir);

		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(toFile);
		zip.addFileset(fileSet);
		zip.setEncoding("GBK");
		zip.execute();
	}

	/**
	 * 复制文件目录所有文件
	 * @author weifei
	 * @date Mar 13, 2012 10:59:36 AM
	 * @param srcFilePath
	 * @param toFilePath void
	 */
	public static void copyFiles(String srcFilePath, String toFilePath)
			throws RuntimeException {

		File srcDir = new File(srcFilePath);
		if (!srcDir.exists())
			throw new RuntimeException(srcFilePath + "文件不存在！");

		File toDir = new File(toFilePath);
		if (!toDir.exists())
			throw new RuntimeException(toFilePath + "文件目录不存在！");

		Project project = new Project();

		FileSet fileSet = new FileSet();
		fileSet.setProject(project);
		fileSet.setDir(srcDir);

		Copy copy = new Copy();
		copy.setProject(project);
		copy.addFileset(fileSet);
		copy.setTodir(toDir);
		copy.execute();

	}

	/**
	 * 复制单个文件
	 * @author weifei
	 * @date Mar 13, 2012 11:30:11 AM
	 * @param srcFilePath
	 * @param toFilePath   目标目录，带有/
	 * @param toFileName   
	 */
	public static void copyFile(String srcFileName, String toFilePath, String toFileName)
			throws RuntimeException {

		File srcDir = new File(srcFileName);
		if (!srcDir.exists())
			throw new RuntimeException(srcFileName + "文件不存在！");

		File toDir = new File(toFilePath);
		if (!toDir.exists())
			throw new RuntimeException(toFilePath + "文件目录不存在！");
		
		File toFile = new File(toFilePath+toFileName);

		Project project = new Project();
		Copy copy = new Copy();
		copy.setProject(project);
		copy.setFile(srcDir);
		copy.setTofile(toFile);
		copy.execute();
	}

	/**
	 * 删除目录
	 * @author weifei
	 * @date Mar 13, 2012 11:02:53 AM
	 * @param filePath void
	 */
	public static void delete(String filePath) throws RuntimeException {

		File file = new File(filePath);

		if (!file.exists()) {
			throw new RuntimeException(filePath + "文件目录不存在");
		}

		Project project = new Project();
		Delete delete = new Delete();
		delete.setProject(project);
		delete.setDir(file);
		delete.execute();
	}
}
