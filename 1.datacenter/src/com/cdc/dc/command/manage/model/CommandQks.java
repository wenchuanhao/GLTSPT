package com.cdc.dc.command.manage.model;

import java.util.Date;

/**
 * CommandQks entity. @author MyEclipse Persistence Tools
 */

public class CommandQks implements java.io.Serializable {

	// Fields

	private String worksId;//工作指令ID
	private String worksType;//指令类型(5,6,7,8,9)
	private String contractName;//合同名称
	private String contractCode;//合同编号
	private String frameworkName;//框架合同名称
	private String frameworkCode;//框架合同编号
	private String orderName;//订单名称
	private String orderCode;//订单编号
	private String projectName;//投资项目名称
	private String projectCode;//投资项目编号
	private String constructionName;//乙方单位名称
	private String constructionId;//乙方单位id
	private Double contractAmount;//合同金额
	private String qkType;//请款类型
	private String qkTypedec;//请款类型描述
	private String paymentTerms;//对应合同付款条款
	private Double applicationAmount;//乙方申请金额
	private String beginYear;//期间开始年
	private String beginMonth;//期间开始月
	private String endYear;//期间结束年
	private String endMonth;//期间结束月
	private Double engineerAmount;//工程款额
	private String workContent;//工作内容
	private String termsNum;//合同条款条数
	private String termsContent;//合同条款内容
	private Double deductAmount;//本期应扣除金额
	private Double payAmount;//本次申请支付金额
	private String calPro;//计算过程
	private Date createTime;//创建时间

	// Constructors

	/** default constructor */
	public CommandQks() {
	}

	/** minimal constructor */
	public CommandQks(String worksId) {
		this.worksId = worksId;
	}

	/** full constructor */
	public CommandQks(String worksId, String worksType, String contractName,
			String contractCode, String frameworkName, String frameworkCode,
			String orderName, String orderCode, String projectName,
			String projectCode, String constructionName, String constructionId,
			Double contractAmount, String qkType,String qkTypedec, String paymentTerms,
			Double applicationAmount, String beginYear, String beginMonth,
			String endYear, String endMonth, Double engineerAmount,
			String workContent, String termsNum, String termsContent,
			Double deductAmount, Double payAmount, String calPro,
			Date createTime) {
		this.worksId = worksId;
		this.worksType = worksType;
		this.contractName = contractName;
		this.contractCode = contractCode;
		this.frameworkName = frameworkName;
		this.frameworkCode = frameworkCode;
		this.orderName = orderName;
		this.orderCode = orderCode;
		this.projectName = projectName;
		this.projectCode = projectCode;
		this.constructionName = constructionName;
		this.constructionId = constructionId;
		this.contractAmount = contractAmount;
		this.qkType = qkType;
		this.qkTypedec = qkTypedec;
		this.paymentTerms = paymentTerms;
		this.applicationAmount = applicationAmount;
		this.beginYear = beginYear;
		this.beginMonth = beginMonth;
		this.endYear = endYear;
		this.endMonth = endMonth;
		this.engineerAmount = engineerAmount;
		this.workContent = workContent;
		this.termsNum = termsNum;
		this.termsContent = termsContent;
		this.deductAmount = deductAmount;
		this.payAmount = payAmount;
		this.calPro = calPro;
		this.createTime = createTime;
	}

	// Property accessors

	public String getWorksId() {
		return this.worksId;
	}

	public void setWorksId(String worksId) {
		this.worksId = worksId;
	}

	public String getWorksType() {
		return this.worksType;
	}

	public void setWorksType(String worksType) {
		this.worksType = worksType;
	}

	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getFrameworkName() {
		return this.frameworkName;
	}

	public void setFrameworkName(String frameworkName) {
		this.frameworkName = frameworkName;
	}

	public String getFrameworkCode() {
		return this.frameworkCode;
	}

	public void setFrameworkCode(String frameworkCode) {
		this.frameworkCode = frameworkCode;
	}

	public String getOrderName() {
		return this.orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getConstructionName() {
		return this.constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}

	public String getConstructionId() {
		return this.constructionId;
	}

	public void setConstructionId(String constructionId) {
		this.constructionId = constructionId;
	}

	public Double getContractAmount() {
		return this.contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getQkType() {
		return this.qkType;
	}

	public void setQkType(String qkType) {
		this.qkType = qkType;
	}

	public String getQkTypedec() {
		return qkTypedec;
	}

	public void setQkTypedec(String qkTypedec) {
		this.qkTypedec = qkTypedec;
	}

	public String getPaymentTerms() {
		return this.paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public Double getApplicationAmount() {
		return this.applicationAmount;
	}

	public void setApplicationAmount(Double applicationAmount) {
		this.applicationAmount = applicationAmount;
	}

	public String getBeginYear() {
		return this.beginYear;
	}

	public void setBeginYear(String beginYear) {
		this.beginYear = beginYear;
	}

	public String getBeginMonth() {
		return this.beginMonth;
	}

	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}

	public String getEndYear() {
		return this.endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getEndMonth() {
		return this.endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public Double getEngineerAmount() {
		return this.engineerAmount;
	}

	public void setEngineerAmount(Double engineerAmount) {
		this.engineerAmount = engineerAmount;
	}

	public String getWorkContent() {
		return this.workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public String getTermsNum() {
		return this.termsNum;
	}

	public void setTermsNum(String termsNum) {
		this.termsNum = termsNum;
	}

	public String getTermsContent() {
		return this.termsContent;
	}

	public void setTermsContent(String termsContent) {
		this.termsContent = termsContent;
	}

	public Double getDeductAmount() {
		return this.deductAmount;
	}

	public void setDeductAmount(Double deductAmount) {
		this.deductAmount = deductAmount;
	}

	public Double getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getCalPro() {
		return this.calPro;
	}

	public void setCalPro(String calPro) {
		this.calPro = calPro;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}