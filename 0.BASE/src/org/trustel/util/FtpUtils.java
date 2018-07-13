package org.trustel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * 
 * 类功能说明：从ftp下载文件<br>
 * 公司名称：CDC<br>
 * 作者：sunsf<br>
 * 创建时间：2012-3-16 下午03:43:46<br>
 * 版本：V1.0 <br>
 */
public class FtpUtils
{
	private FTPClient ftpClient;

	private String ip = "221.179.35.126"; // 服务器IP地址

	private String userName = "mmclient"; // 用户名

	private String userPwd = "mmftpuser"; // 密码

	private int port = 21; // 端口号

	/**
	 * 读取文件的存放目录
	 */
	private String path = "MOBILEINFO";

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public FtpUtils()
	{
		this.resConn();
	}

	/**
	 * 
	 * 函数功能说明 重新链接<br>
	 * sunsf 2012-3-16<br>
	 * 修改者 <br>
	 * 修改日期 <br>
	 * 修改内容 <br>
	 */
	public void resConn()
	{
		if (ftpClient == null)
		{
			this.connectServer(ip, port, userName, userPwd, path);
			System.out.println("获取FTP链接");
		}
	}

	/**
	 * @param ip
	 *            服务IP
	 * @param port
	 *            端口
	 * @param userName
	 *            用户
	 * @param userPwd
	 *            密码
	 * @param path
	 *            默认路径
	 * @throws SocketException
	 * @throws IOException
	 *             function:连接到服务器
	 */
	private void connectServer(String ip, int port, String userName, String userPwd, String path)
	{
		ftpClient = new FTPClient();
		try
		{
			// 连接
			ftpClient.connect(ip, port);
			// 登录
			ftpClient.login(userName, userPwd);
			if (path != null && path.length() > 0)
			{
				// 跳转到指定目录
				ftpClient.changeWorkingDirectory(path);
			}
		} catch (SocketException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 函数功能说明 关闭连接<br>
	 * sunsf 2012-3-16<br>
	 * 修改者 <br>
	 * 修改日期 <br>
	 * 修改内容 <br>
	 */
	private void closeServer()
	{
		if (ftpClient.isConnected())
		{
			try
			{
				System.out.println("关闭FTP链接");
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// public void upLoadFile()
	// {
	// try
	// {
	// InputStream input = new FileInputStream("c:/test.txt");
	// ftpClient.changeWorkingDirectory("MOBILEINFO");
	// ftpClient.storeFile("test.txt", input);
	// } catch (FileNotFoundException e)
	// {
	// e.printStackTrace();
	// } catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 
	 * 函数功能说明 下载<br>
	 * sunsf 2012-3-16<br>
	 * 修改者 <br>
	 * 修改日期 <br>
	 * 修改内容 <br>
	 * 
	 * @param fullPath
	 *            保存到MMAC服务器中的路经
	 * @param fileName
	 *            保存到MMAC服务器中的路经
	 * @return 下完文件 完整的路径
	 */
	public String downloadFile(String fullPath, String fileName)
	{
		// 判断文件是否存在 不存在创建
		File file = new File(fullPath);
		if (!file.exists())
		{
			file.mkdirs();
			System.out.println(fullPath + "文件夹系统不存在 正在创建");
			readDonwLoad(fullPath, fileName);
		} else
		{
			readDonwLoad(fullPath, fileName);
		}
		return fullPath + File.separator + fileName;

	}

	/**
	 * 
	 * 函数功能说明 下载<br>
	 * sunsf 2012-3-16<br>
	 * 
	 * @param fullPath
	 *            保存到MMAC服务器中的路经
	 * @param fileName
	 *            ftp服务器上的文件名字
	 * @return 下载状态 true 成功 false 失败
	 */
	private boolean readDonwLoad(String fullPath, String fileName)
	{
		boolean flag = true;
		String fullFileName = fullPath + File.separator + fileName;
		try
		{
			FileOutputStream fos = new FileOutputStream(fullFileName);
			flag = ftpClient.retrieveFile(fileName, fos);
			fos.flush();
			fos.close();
		} catch (Exception e)
		{
			flag = false;
			e.printStackTrace();
			System.out.println(this.getClass().getName() + " ERROR 从Ftp服务器获取文件失败" + e.toString());
		} finally
		{
			this.closeServer();
			if (flag)
			{
				System.out.println("成功 下载服务器文件：" + path + fileName + " 到本地：" + fullFileName);
			} else
			{
				System.out.println("失败 下载服务器文件：" + path + fileName + " 到本地：" + fullFileName);
			}
		}
		return flag;
	}

	public List<String> getFileList(String path)
	{
		List<String> fileLists = new ArrayList<String>();
		// 获得指定目录下所有文件名
		FTPFile[] ftpFiles = null;
		try
		{
			ftpFiles = ftpClient.listFiles(path);
		} catch (IOException e)
		{
			System.out.println("ERROR 从Ftp服务器获取文件失败" + e.toString());
		}
		if (fileLists != null)
		{
			for (FTPFile file : ftpFiles)
			{
				if (file.isFile())
				{
					System.out.println(file.toString());
					System.out.println(file.getName());
					fileLists.add(file.getName());
				}
			}
		}
		return fileLists;
	}

}
