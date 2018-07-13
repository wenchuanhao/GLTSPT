package com.cdc.dc.datacenter.common;

/**
 * 数据中心常量类
 * @author lxl
 * @date 2016-10-12
 */
public class DatacenterConstant {

	/**
	 * 数据获取
	 */
	public static final String IMP = "IMP";
	/**
	 * 数据提供
	 */
	public static final String EXP = "EXP";
	/**
	 * 未手动触发
	 */
	public static final int TRIGGER_STOP = 0;
	/**
	 * 手动触发中
	 */
	public static final int TRIGGER_RUNNING = 1;
	/**
	 * 触发类型：手动触发
	 */
	public static final int TRIGGER_TYPE_SD = 1;
	/**
	 * 触发类型：自动触发
	 */
	public static final int TRIGGER_TYPE_ZD = 2;
	/**
	 * 服务状态：未启动
	 */
	public static final int SERVICE_STOP = 0;
	/**
	 * 服务状态：已启动
	 */
	public static final int SERVICE_RUNNING = 1;
	/**
	 * 服务状态：已删除
	 */
	public static final int SERVICE_DELETE = -1;
	/**
	 * 接口类型：webservice接口
	 */
	public static final String INTERFACE_WS = "WS";
	/**
	 * 接口类型：ftp接口
	 */
	public static final String INTERFACE_FTP = "FTP";
	/**
	 * 接口类型：jdbc接口
	 */
	public static final String INTERFACE_JDBC = "JDBC";
	/**
	 * 定时任务默认分组
	 */
	public static final String JOB_DEFAULT_GROUP = "DEFAULT";
	/**
	 * 任务未执行
	 */
	public static final int JOB_STATUS_NOT_RUNNING = 0;
	/**
	 * 任务执行中
	 */
	public static final int JOB_STATUS_RUNNING = 1;
	/**
	 * 任务执行成功
	 */
	public static final int JOB_STATUS_SUCCESS = 2;
	/**
	 * 任务执行失败
	 */
	public static final int JOB_STATUS_FAILURE = 3;
}
