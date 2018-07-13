package com.cdc.dc.rules.common;
/**
 * 制度创建
 * @author ZENGKAI
 * @date 2016-04-07 09:58:29
 */
public class RulesCommon {

	/**
	 * 制度管理分类RulesType
	 */
	public final static String rulesTypeIsRoot_N = "0";//不是父节点
	public final static String rulesTypeIsRoot_Y =  "1";//是父节点
	
	public final static String rulesTypeRootParentId =  "ROOT";//root父节点
	
	public final static String rulesTypeStatus_Save =  "1";//已存
	public final static String rulesTypeStatus_Del =  "0";//删除
	
	/**
	 * 文档(附件)信息RulesFileUpload
	 */
	
    public final static String rulesFileUploadIsParent_Y =  "1";//是父信息
    public final static String rulesFileUploadIsParent_N =  "0";//非父信息
    
    public final static String rulesFileUploadStatus_Save =  "1";//已存
    public final static String rulesFileUploadStatus_Del =  "0";//删除
    
    public final static String rulesFileUploadTypes_ZDWJ =  "1";//制度文件
    public final static String rulesFileUploadTypes_ZDFJ =  "2";//制度附件
    public final static String rulesFileUploadTypes_FBYJ =  "3";//发布依据
    public final static String rulesFileUploadTypes_ZDXGWD =  "4";//制度相关文档
    public final static String rulesFileUploadTypes_ZLSMJ =  "12";//文件（指令）扫描件
    
    public final static String rulesFileUploadTypes_AD =  "13";//图片广告
    
    /**
     * 制度信息RulesInfo
     */
//   0:已删除;/ 1.草稿；2.已提交审核（审核中）；3.已发布；4.已废止；5.已修订；6;已退回;
    public final static String rulesInfoStatus_DEL =  "0";//已删除
    public final static String rulesInfoStatus_CG =  "1";//草稿
    public final static String rulesInfoStatus_SH =  "2";//审核中
    public final static String rulesInfoStatus_FB =  "3";//已发布
    public final static String rulesInfoStatus_FZ =  "4";//已废止
    public final static String rulesInfoStatus_XD =  "5";//已修订
    public final static String rulesInfoStatus_TH =  "6";//已退回
    public final static String rulesInfoStatus_QXZ =  "-1";//已退回
    
    
    public final static String rulesInfoGrade_BM =  "BMJ";
    public final static String rulesInfoGrade_LBM =  "KBM";
    public final static String rulesInfoGrade_JD =  "JDJ";
    
    /**
     * 待办待阅表RulesFlowInsactor
     */
    public final static String rulesFlowInsactorsType_DB =  "1";//1:待办
    public final static String rulesFlowInsactorsType_DY =  "2";//2:待阅
    public final static String rulesFlowInsactorsType_FB =  "3";//3:已发布超时记录信息
    
    public final static String rulesFlowInsactorsHandelStatus_DONE =  "1";//已完成
    public final static String rulesFlowInsactorsHandelStatus_NONE =  "0";//未完成
    
    public final static String rulesFlowInsactorsTimeoutStatuss_Y =  "1";//1：已超时，
    public final static String rulesFlowInsactorsTimeoutStatuss_N =  "0";//0：未超时
    
    /**
     * 待办待阅接口参数
     */


//    任务类型
    public final static int TASK_TYPE_DB = 1;//    1:待办
    public final static int TASK_TYPE_DY = 2;//    2:待阅
//    处理类型
    public final static int ACTION_ADD = 1;//    1:新增
    public final static int ACTION_DEL = 2;//    2:删除
    public final static int ACTION_DONE = 3;//   3:完成
    
    
    /**
     * 制度流程信息RulesFlowInfo
     */
    //流程环节
    public final static String rulesFlowInfoFlowLink_CJ =  "1";//1:制度创建;
    public final static String rulesFlowInfoFlowLink_SH =  "2";//2:制度审核;
    public final static String rulesFlowInfoFlowLink_FB =  "3";//3:制度发布;数据审核通过
    public final static String rulesFlowInfoFlowLink_FZ =  "4";//4:制度废止;
    public final static String rulesFlowInfoFlowLink_XD =  "5";//5:制度修订;
    public final static String rulesFlowInfoFlowLink_TH =  "6";//6:制度退回
    //处理状态
    public final static String rulesFlowInfoHandelStatus_NONE =  "1";//1处理中,标识最新.有且仅有一条记录为NONE
    public final static String rulesFlowInfoHandelStatus_DONE =  "2";//2:已处理
    //处理结果
    public final static String rulesFlowInfoHandelResult_TH =  "0";//0:退回,
    public final static String rulesFlowInfoHandelResult_FB =  "1";//1:发布;
    public final static String rulesFlowInfoHandelResult_FZ =  "2";//2:废止;
    public final static String rulesFlowInfoHandelResult_XD =  "3";//3:修订;
    public final static String rulesFlowInfoHandelResult_YY =  "4";//4：已阅;
    public final static String rulesFlowInfoHandelResult_TJ =  "5";//5：退回并提交-新增;
    
    //组织架构根节点ID
    public final static String sysorganizationrootID =  "80df8fa55ca4048ac2314dab1a52d75e";
    
	public final static String DICT_TYPE =  "RULESER_ROLE";//数据字典类型配置
	public final static String RULESER_ADMIN =  "1";//基地制度管理主管
	public final static String RULESER_DEP =  "2";//部门制度员
	//
	public final static String userRolesAdmin =  "1";//基地制度员
	public final static String userRolesDep =  "2";//部门制度员
	public final static String userRolesCommon =  "3";//普通用户
	
	public final static String DEPARTMENT_JP =  "DEPARTMENT_JP";//数据字典类型配置

	public final static String OA_URL =  "OA_URL";//OA待办URL配置
	public final static String COOPERATION_REMAIN_URL =  "1";//数据审核
	public final static String COOPERATION_MYALL_URL =  "2";//我的数据源
	
}


