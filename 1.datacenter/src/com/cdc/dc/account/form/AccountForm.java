package com.cdc.dc.account.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.account.model.AccountInvoice;

public class AccountForm extends PageQueryForm implements Serializable{
	
	public final static String BLY_TYPE =  "XXBLY_ROLE";//数据字典类型配置
	public final static String BLY_ADMIN =  "1";//信息补录员
	
	private String onlyProblems;//1:只导出问题工单；2：导出所有工单
	private String eSDate;//纸质财务提交开始时间 yyyy-MM-dd HH:mm:ss
	private String eEDate;//纸质财务提交结束时间 yyyy-MM-dd HH:mm:ss
	
	public String getOnlyProblems() {
		return onlyProblems;
	}
	public void setOnlyProblems(String onlyProblems) {
		this.onlyProblems = onlyProblems;
	}
	public String geteSDate() {
		return eSDate;
	}
	public void seteSDate(String eSDate) {
		this.eSDate = eSDate;
	}
	public String geteEDate() {
		return eEDate;
	}
	public void seteEDate(String eEDate) {
		this.eEDate = eEDate;
	}

	private String id;//接单录入ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSementPeople() {
		return sementPeople;
	}
	public void setSementPeople(String sementPeople) {
		this.sementPeople = sementPeople;
	}
	public String getSementPeopleId() {
		return sementPeopleId;
	}
	public void setSementPeopleId(String sementPeopleId) {
		this.sementPeopleId = sementPeopleId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCosId() {
		return cosId;
	}
	public void setCosId(String cosId) {
		this.cosId = cosId;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public String getPresenterId() {
		return presenterId;
	}
	public void setPresenterId(String presenterId) {
		this.presenterId = presenterId;
	}
	public String getPresenter() {
		return presenter;
	}
	public void setPresenter(String presenter) {
		this.presenter = presenter;
	}
	public Date getPageSubmitDate() {
		return pageSubmitDate;
	}
	public void setPageSubmitDate(Date pageSubmitDate) {
		this.pageSubmitDate = pageSubmitDate;
	}
	public String getIncludeBuckle() {
		return includeBuckle;
	}
	public void setIncludeBuckle(String includeBuckle) {
		this.includeBuckle = includeBuckle;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getCurrentLink() {
		return currentLink;
	}
	public void setCurrentLink(String currentLink) {
		this.currentLink = currentLink;
	}
	public String getDeduction() {
		return deduction;
	}
	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}
	public Date getReachSement() {
		return reachSement;
	}
	public void setReachSement(Date reachSement) {
		this.reachSement = reachSement;
	}
	
	public String getReachSementStr() {
		return reachSementStr;
	}
	public void setReachSementStr(String reachSementStr) {
		this.reachSementStr = reachSementStr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getCollectionMenber() {
		return collectionMenber;
	}
	public void setCollectionMenber(String collectionMenber) {
		this.collectionMenber = collectionMenber;
	}
	public String getCollectionMenberId() {
		return collectionMenberId;
	}
	public void setCollectionMenberId(String collectionMenberId) {
		this.collectionMenberId = collectionMenberId;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getFirstActorUser() {
		return firstActorUser;
	}
	public void setFirstActorUser(String firstActorUser) {
		this.firstActorUser = firstActorUser;
	}
	public String getFirstActorUserId() {
		return firstActorUserId;
	}
	public void setFirstActorUserId(String firstActorUserId) {
		this.firstActorUserId = firstActorUserId;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public String getFirstTrial() {
		return firstTrial;
	}
	public void setFirstTrial(String firstTrial) {
		this.firstTrial = firstTrial;
	}
	public String getFirstTrialId() {
		return firstTrialId;
	}
	public void setFirstTrialId(String firstTrialId) {
		this.firstTrialId = firstTrialId;
	}
	public String getTrialFlat() {
		return trialFlat;
	}
	public void setTrialFlat(String trialFlat) {
		this.trialFlat = trialFlat;
	}
	public Date getRecordSubmitDate() {
		return recordSubmitDate;
	}
	public void setRecordSubmitDate(Date recordSubmitDate) {
		this.recordSubmitDate = recordSubmitDate;
	}
	public String getRecordFlat() {
		return recordFlat;
	}
	public void setRecordFlat(String recordFlat) {
		this.recordFlat = recordFlat;
	}
	public String getTrialAccount() {
		return trialAccount;
	}
	public void setTrialAccount(String trialAccount) {
		this.trialAccount = trialAccount;
	}
    public String getIstimeOut() {
		return istimeOut;
	}
	public void setIstimeOut(String istimeOut) {
		this.istimeOut = istimeOut;
	}
	
	private String trialAccount;    //初审会计
	private String trialAccountId;		//初审会计ID
	public String getTrialAccountId() {
		return trialAccountId;
	}
	public void setTrialAccountId(String trialAccountId) {
		this.trialAccountId = trialAccountId;
	}
	public String getAccountAbstract() {
		return accountAbstract;
	}
	public void setAccountAbstract(String accountAbstract) {
		this.accountAbstract = accountAbstract;
	}

	private String orderId;         //报账单号    
    private String sementPeople;      //报账人  
    private String sementPeopleId;     //报账人ID
    private String departmentId;        //报账部门ID
    private String department;           //报账部门
    private String cosId;               //费用类型ID
    private String costType;            //费用类型
    private String presenterId ;        //交单人ID
    private String presenter;            //交单人
    private Date pageSubmitDate ;    //纸质提交财务时间
    private String includeBuckle ;      //是否包含抵扣
    private String createName ;         //创建人
    private String userId  ;            //创建人ID
    private Date createDate ;         //接单录入时间
    private Date submitDate ;         //初审提交时间
    private String currentLink ;        //当前环节
    private String deduction ;           //是否专票抵扣
    private Date reachSement  ;       //达到报账时间
    private String reachSementStr;		//达到报账时间字符串
    private String status  ;             //状态
    private String istimeOut;             //是否超时 
	private String timeOut;             //超时时长
    private String cost  ;               //报账金额
    private Date paymentTime;         //送达财务付款时间
    private String collectionMenber;    //信息补录员
    private String collectionMenberId;   //信息补录员ID
    private Date endTime;             //整改结束时间
    private String describe ;            //整改不通过原因
    private String firstActorUser;      //第一个初审人
    private String firstActorUserId;   //第一个处理人ID
    private Date recordDate;          //信息补录时间
    private String firstTrial ;         //初审提交人
    private String firstTrialId ;      //初审提交人ID
    private String trialFlat ;          //初审已办状态
    private Date recordSubmitDate;   //补录员提交时间
    private String recordFlat;//补录已办状态
    private String accountAbstract;//摘要
    
    private String accids;
    
    public String getAccids() {
		return accids;
	}
	public void setAccids(String accids) {
		this.accids = accids;
	}

	private List<AccountInvoice> invoice;//发票
	public List<AccountInvoice> getInvoice() {
		return invoice;
	}
	public void setInvoice(List<AccountInvoice> invoice) {
		this.invoice = invoice;
	}
    
}
