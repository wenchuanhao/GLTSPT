package com.cdc.dc.purchase.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class PurchaseForm  extends PageQueryForm{

	//经办人
	private String columnB;
	//*需求部门
    private String columnC;
    
    private String columnN;
    
    private String columnJ;
	//经办人
	private String operatorName;
    
	public String getColumnN() {
		return columnN;
	}

	public void setColumnN(String columnN) {
		this.columnN = columnN;
	}

	public String getColumnJ() {
		return columnJ;
	}

	public void setColumnJ(String columnJ) {
		this.columnJ = columnJ;
	}

	private Date beginCreateTime;
	
	private Date endCreateTime;
	
	private String beginTime;
	private String endTime;
	
	private String Ids;//批量导出
	
	//分管人账号
	private String manageAcount;
	//分管人
	private String manageName; 
	//分管部门
	private String manageAparts;
	
	private String  roleName;
	private String  userId;
	
	private String id;
	
	public String getIds() {
		return Ids;
	}

	public void setIds(String ids) {
		Ids = ids;
	}

	public String getColumnB() {
		return columnB;
	}

	public void setColumnB(String columnB) {
		this.columnB = columnB;
	}

	public String getColumnC() {
		return columnC;
	}

	public void setColumnC(String columnC) {
		this.columnC = columnC;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getManageAcount() {
		return manageAcount;
	}

	public void setManageAcount(String manageAcount) {
		this.manageAcount = manageAcount;
	}

	public String getManageName() {
		return manageName;
	}

	public void setManageName(String manageName) {
		this.manageName = manageName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManageAparts() {
		return manageAparts;
	}

	public void setManageAparts(String manageAparts) {
		this.manageAparts = manageAparts;
	}
	
	
   
}
