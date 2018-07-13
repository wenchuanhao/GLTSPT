package com.cdc.dc.command.manage.common;

public class CommandCommon {
	
	
	/**
	 * commandType  文件(指令)类型
	 */
	public final static String COMMAND_TYPE =  "COMMAND_TYPE";//数据字典类型配置
	public final static String commandType_A1 = "1";//A1类工作指令
	public final static String commandType_A2 = "2";//A2类工作指令
	public final static String commandType_B = "3";//B类工作指令
	public final static String commandType_C = "4";//C类工作指令
	public final static String commandType_SGQK = "5";//施工类请款指令
	public final static String commandType_JCQK = "6";//检测勘察类请款指令
	public final static String commandType_JLQK = "7";//监理造价类请款指令
	public final static String commandType_SJQK = "8";//设计专家类请款指令
	public final static String commandType_CLQK = "9";//材料设备类请款指令
	public final static String commandType_GCZL = "10";//施工过程资料指令
	
	/**
	 * commandStatus 指令状态
	 * 未流转 流转中 已归档 已作废
	 */
	public final static String COMMAND_STATUS =  "COMMAND_STATUS";//数据字典类型配置
	public final static String commandStatus_DEL = "0";//删除
	public final static String commandStatus_WLZ = "1";//未流转 ： 发起人
	public final static String commandStatus_LZZ = "2";//流转中 ： 接收人
	public final static String commandStatus_YGD = "3";//已归档：归档人
	public final static String commandStatus_YZF = "4";//已作废：作废人
	public final static String commandStatus_NGD = "5";//撤销归档
	
	/**
	 * 指令归档信息 状态
	 * CommandFolder
	 */
	public final static String folderStatus_S = "1";//已归档
	public final static String folderStatus_D = "0";//已撤销
	
	/**
	 * 工程指令角色控制
	 */
	public final static String COMMAND_DICT_TYPE =  "COMMAND_ROLE";//数据字典类型配置
	public final static String COMMAND_OWNER =  "1";//业主 COMMAND_OWNER
	public final static String COMMAND_ADVISER =  "2";//专家顾问
	public final static String COMMAND_CONSTRUCTION =  "3";//施工单位
	public final static String COMMAND_SUPERVISION =  "4";//监理单位
	public final static String COMMAND_COST =  "5";//造价单位
	public final static String COMMAND_ADMIN =  "6";//业主资料管理员
	
	public final static String MOBILESESSION = "mobile";
}
