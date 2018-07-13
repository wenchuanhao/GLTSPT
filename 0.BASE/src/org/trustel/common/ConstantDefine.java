package org.trustel.common;

/**
 * 常量定义
 * 
 * @author liug
 * 
 */
public final class ConstantDefine {

	/**
	 * 路径分隔符 (会自动识别系统)
	 */
	public static String FILE_SEPARATOR = System.getProperty("file.separator");

	public static String BASE_PATH = "SRMC" + FILE_SEPARATOR + "rmpb" + FILE_SEPARATOR + "rs";
	/**
	 * 根路径
	 */
	public static String ROOT_PATH = System.getProperty("webAppRootKey").replace("rmpb", BASE_PATH);

	/**
	 * 需求流程文件保存路径
	 */
	public static String REQUIRE_PATH = ROOT_PATH + FILE_SEPARATOR + "require";
	
	/**
	 * 需求文件保存路径
	 */
	public static String REQUIRE_PATH2 = ROOT_PATH + FILE_SEPARATOR + "requireAttachment";
	
	/**
	 * 研发类型定义
	 *
	 */
	public static String DEVELOP = "SD"; 
	/**
	 * 版本类型定义
	 *
	 */
	public static String VERSION = "VR"; 
	/**
	 * 日常问题流程文件保存路径
	 */
	public static String DAILY_PORTAL_PATH = ROOT_PATH + FILE_SEPARATOR + "DailyPortal";
	
	public static String SYSTEM_DEFAULTE_PASSWORD = "MM2013@CMCC.com";
}
