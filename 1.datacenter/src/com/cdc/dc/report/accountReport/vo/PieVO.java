package com.cdc.dc.report.accountReport.vo;
/**
 * 首页饼图vo
 * @author WEIFEI
 * @date 2016-6-24 下午4:03:53
 */
public class PieVO {
	//(1) 发起报账时间=达到付款条件时间-提交报账单时间
	private String PAYMENT_TIME;	//达到付款条件时间
	private String SUBMITER_DATE;	//提交报账单时间
	private String REACH_SEMENT;  //达到报账时间
	
	//(2)业务审批时间 = 部门领导审批完成时间-提交电子报账单完成时间
	private String BMLDAPPROVE_DATE;	//部门领导审批完成时间
	
	//(3)问题单据整改时间：各个阶段，问题整改时长（初审）进行累加。参考初审处理
	private String RECTIFY_TIME;	//问题单据整改时间
	private String IS_NO;	//是否有问题单据
	
	private String START_TIME; //问题单创建时间
	private String END_TIME;	//问题单据整改完成时间
	
	private String ZBKJAPPROVE_DATE;//主办会计审批时间
	private String PAGE_SUBMIT_DATE;//纸质提交财务时间
	
	//(4) 南基财务审批=南基财务审批完成时间-问题单据整改完成时间（非问题单据为，南基财务审批=南基财务审批完成时间-部门领导审批完成时间）
	private String CWJLAPPROVE_DATE;	//南基财务审批完成时间
	
	//(5) 基地领导审批=基地领导审批完成时间-南基财务审批完成时间
	private String NJLDAPPROVE_DATE;	//基地领导审批完成时间
	
	//(6) 省财务审批=省财务审批完成时间-基地领导审批完成时间
	private String SCWAPPROVE_DATE;	//省财务审批完成时间
	
	//(7) 出纳支付=出纳支付完成时间-省财务审批完成时间
	private String CNFKAPPROVE_DATE;	//出纳支付完成时间
	

	private String HJ_1_NAME;	//环节名称
	private String HJ_1_SC;	//环节时长
	private String HJ_1_BFB;	//百分比
	
	private String HJ_2_NAME;	//环节名称
	private String HJ_2_SC;	//环节时长
	private String HJ_2_BFB;	//百分比
	private String HJ_3_NAME;	//环节名称
	private String HJ_3_SC;	//环节时长
	private String HJ_3_BFB;	//百分比
	private String HJ_4_NAME;	//环节名称
	private String HJ_4_SC;	//环节时长
	private String HJ_4_BFB;	//百分比
	private String HJ_5_NAME;	//环节名称
	private String HJ_5_SC;	//环节时长
	private String HJ_5_BFB;	//百分比
	private String HJ_6_NAME;	//环节名称
	private String HJ_6_SC;	//环节时长
	private String HJ_6_BFB;	//百分比
	private String HJ_7_NAME;	//环节名称
	private String HJ_7_SC;	//环节时长
	private String HJ_7_BFB;	//百分比
	
	/***
	 * 用于封装列表数据
	 */
	private String DEPARTMENT;	//部门
	private String TYPE_NAME;		//报账费用类型
	private String ZQ;				//周期
	private String HJ_NAME;	//环节名称
	private String HJ_PJSC;		//环节平均时长
	private String ZPJSC;			//总平均时长
	private String DJL;				//单据量
	private String TB;				//同比
	private String HB;				//环比
	
	public String getDEPARTMENT() {
		return DEPARTMENT;
	}
	public void setDEPARTMENT(String dEPARTMENT) {
		DEPARTMENT = dEPARTMENT;
	}
	public String getTYPE_NAME() {
		return TYPE_NAME;
	}
	public void setTYPE_NAME(String tYPE_NAME) {
		TYPE_NAME = tYPE_NAME;
	}
	public String getTB() {
		return TB;
	}
	public void setTB(String tB) {
		TB = tB;
	}
	public String getHB() {
		return HB;
	}
	public void setHB(String hB) {
		HB = hB;
	}
	public String getHJ_PJSC() {
		return HJ_PJSC;
	}
	public void setHJ_PJSC(String hJ_PJSC) {
		HJ_PJSC = hJ_PJSC;
	}
	public String getZQ() {
		return ZQ;
	}
	public void setZQ(String zQ) {
		ZQ = zQ;
	}
	public String getHJ_NAME() {
		return HJ_NAME;
	}
	public void setHJ_NAME(String hJ_NAME) {
		HJ_NAME = hJ_NAME;
	}
	public String getZPJSC() {
		return ZPJSC;
	}
	public void setZPJSC(String zPJSC) {
		ZPJSC = zPJSC;
	}
	public String getDJL() {
		return DJL;
	}
	public void setDJL(String dJL) {
		DJL = dJL;
	}
	public String getHJ_1_SC() {
		return HJ_1_SC;
	}
	public void setHJ_1_SC(String hJ_1_SC) {
		HJ_1_SC = hJ_1_SC;
	}
	public String getHJ_2_SC() {
		return HJ_2_SC;
	}
	public void setHJ_2_SC(String hJ_2_SC) {
		HJ_2_SC = hJ_2_SC;
	}
	public String getHJ_3_SC() {
		return HJ_3_SC;
	}
	public void setHJ_3_SC(String hJ_3_SC) {
		HJ_3_SC = hJ_3_SC;
	}
	public String getHJ_4_SC() {
		return HJ_4_SC;
	}
	public void setHJ_4_SC(String hJ_4_SC) {
		HJ_4_SC = hJ_4_SC;
	}
	public String getHJ_5_SC() {
		return HJ_5_SC;
	}
	public void setHJ_5_SC(String hJ_5_SC) {
		HJ_5_SC = hJ_5_SC;
	}
	public String getHJ_6_SC() {
		return HJ_6_SC;
	}
	public void setHJ_6_SC(String hJ_6_SC) {
		HJ_6_SC = hJ_6_SC;
	}
	public String getHJ_7_SC() {
		return HJ_7_SC;
	}
	public void setHJ_7_SC(String hJ_7_SC) {
		HJ_7_SC = hJ_7_SC;
	}
	public String getPAYMENT_TIME() {
		return PAYMENT_TIME;
	}
	public void setPAYMENT_TIME(String pAYMENT_TIME) {
		PAYMENT_TIME = pAYMENT_TIME;
	}
	public String getSUBMITER_DATE() {
		return SUBMITER_DATE;
	}
	public void setSUBMITER_DATE(String sUBMITER_DATE) {
		SUBMITER_DATE = sUBMITER_DATE;
	}
	public String getBMLDAPPROVE_DATE() {
		return BMLDAPPROVE_DATE;
	}
	public void setBMLDAPPROVE_DATE(String bMLDAPPROVE_DATE) {
		BMLDAPPROVE_DATE = bMLDAPPROVE_DATE;
	}
	public String getRECTIFY_TIME() {
		return RECTIFY_TIME;
	}
	public void setRECTIFY_TIME(String rECTIFY_TIME) {
		RECTIFY_TIME = rECTIFY_TIME;
	}
	public String getIS_NO() {
		return IS_NO;
	}
	public void setIS_NO(String iS_NO) {
		IS_NO = iS_NO;
	}
	public String getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}
	
	public String getPAGE_SUBMIT_DATE() {
		return PAGE_SUBMIT_DATE;
	}
	public void setPAGE_SUBMIT_DATE(String pAGE_SUBMIT_DATE) {
		PAGE_SUBMIT_DATE = pAGE_SUBMIT_DATE;
	}
	public String getZBKJAPPROVE_DATE() {
		return ZBKJAPPROVE_DATE;
	}
	public void setZBKJAPPROVE_DATE(String zBKJAPPROVE_DATE) {
		ZBKJAPPROVE_DATE = zBKJAPPROVE_DATE;
	}
	public String getCWJLAPPROVE_DATE() {
		return CWJLAPPROVE_DATE;
	}
	public void setCWJLAPPROVE_DATE(String cWJLAPPROVE_DATE) {
		CWJLAPPROVE_DATE = cWJLAPPROVE_DATE;
	}
	public String getNJLDAPPROVE_DATE() {
		return NJLDAPPROVE_DATE;
	}
	public void setNJLDAPPROVE_DATE(String nJLDAPPROVE_DATE) {
		NJLDAPPROVE_DATE = nJLDAPPROVE_DATE;
	}
	public String getSCWAPPROVE_DATE() {
		return SCWAPPROVE_DATE;
	}
	public void setSCWAPPROVE_DATE(String sCWAPPROVE_DATE) {
		SCWAPPROVE_DATE = sCWAPPROVE_DATE;
	}
	public String getCNFKAPPROVE_DATE() {
		return CNFKAPPROVE_DATE;
	}
	public void setCNFKAPPROVE_DATE(String cNFKAPPROVE_DATE) {
		CNFKAPPROVE_DATE = cNFKAPPROVE_DATE;
	}
	public String getHJ_1_NAME() {
		return HJ_1_NAME;
	}
	public void setHJ_1_NAME(String hJ_1_NAME) {
		HJ_1_NAME = hJ_1_NAME;
	}
	public String getHJ_1_BFB() {
		return HJ_1_BFB;
	}
	public void setHJ_1_BFB(String hJ_1_BFB) {
		HJ_1_BFB = hJ_1_BFB;
	}
	public String getHJ_2_NAME() {
		return HJ_2_NAME;
	}
	public void setHJ_2_NAME(String hJ_2_NAME) {
		HJ_2_NAME = hJ_2_NAME;
	}
	public String getHJ_2_BFB() {
		return HJ_2_BFB;
	}
	public void setHJ_2_BFB(String hJ_2_BFB) {
		HJ_2_BFB = hJ_2_BFB;
	}
	public String getHJ_3_NAME() {
		return HJ_3_NAME;
	}
	public void setHJ_3_NAME(String hJ_3_NAME) {
		HJ_3_NAME = hJ_3_NAME;
	}
	public String getHJ_3_BFB() {
		return HJ_3_BFB;
	}
	public void setHJ_3_BFB(String hJ_3_BFB) {
		HJ_3_BFB = hJ_3_BFB;
	}
	public String getHJ_4_NAME() {
		return HJ_4_NAME;
	}
	public void setHJ_4_NAME(String hJ_4_NAME) {
		HJ_4_NAME = hJ_4_NAME;
	}
	public String getHJ_4_BFB() {
		return HJ_4_BFB;
	}
	public void setHJ_4_BFB(String hJ_4_BFB) {
		HJ_4_BFB = hJ_4_BFB;
	}
	public String getHJ_5_NAME() {
		return HJ_5_NAME;
	}
	public void setHJ_5_NAME(String hJ_5_NAME) {
		HJ_5_NAME = hJ_5_NAME;
	}
	public String getHJ_5_BFB() {
		return HJ_5_BFB;
	}
	public void setHJ_5_BFB(String hJ_5_BFB) {
		HJ_5_BFB = hJ_5_BFB;
	}
	public String getHJ_6_NAME() {
		return HJ_6_NAME;
	}
	public void setHJ_6_NAME(String hJ_6_NAME) {
		HJ_6_NAME = hJ_6_NAME;
	}
	public String getHJ_6_BFB() {
		return HJ_6_BFB;
	}
	public void setHJ_6_BFB(String hJ_6_BFB) {
		HJ_6_BFB = hJ_6_BFB;
	}
	public String getHJ_7_NAME() {
		return HJ_7_NAME;
	}
	public void setHJ_7_NAME(String hJ_7_NAME) {
		HJ_7_NAME = hJ_7_NAME;
	}
	public String getHJ_7_BFB() {
		return HJ_7_BFB;
	}
	public void setHJ_7_BFB(String hJ_7_BFB) {
		HJ_7_BFB = hJ_7_BFB;
	}
	public String getREACH_SEMENT() {
		return REACH_SEMENT;
	}
	public void setREACH_SEMENT(String rEACH_SEMENT) {
		REACH_SEMENT = rEACH_SEMENT;
	}
	public String getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}
}
