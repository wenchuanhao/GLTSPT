package com.cdc.dc.account.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class AccountConfigForm extends PageQueryForm{
	private String id;     //id                                   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getAccounting() {
		return accounting;
	}
	public void setAccounting(String accounting) {
		this.accounting = accounting;
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
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	private String department  ;   //部门      
	private String cosId    ;    //   费用类型ID    
	private String costType ;  //费用类型              
	private String accounting;  //初审会计         
	private String accountingId;  //初审会计         
	public String getAccountingId() {
		return accountingId;
	}
	public void setAccountingId(String accountingId) {
		this.accountingId = accountingId;
	}
	private String userId;  //创建人            
	private Date createDate;   //创建时间       
	private String departmentId; //部门ID
}
