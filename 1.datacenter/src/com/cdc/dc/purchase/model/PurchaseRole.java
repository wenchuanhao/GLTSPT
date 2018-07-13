package com.cdc.dc.purchase.model;

import java.util.Date;

public class PurchaseRole {
	private String id;
	private String roleName;
	private String manageName;
	private String manageAcount;
	private String manageApartMent;
	private String manageApartId;
	private Date create_time;
	private String create_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getManageName() {
		return manageName;
	}
	public void setManageName(String manageName) {
		this.manageName = manageName;
	}
	public String getManageAcount() {
		return manageAcount;
	}
	public void setManageAcount(String manageAcount) {
		this.manageAcount = manageAcount;
	}
	public String getManageApartMent() {
		return manageApartMent;
	}
	public void setManageApartMent(String manageApartMent) {
		this.manageApartMent = manageApartMent;
	}
	public String getManageApartId() {
		return manageApartId;
	}
	public void setManageApartId(String manageApartId) {
		this.manageApartId = manageApartId;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreate_id() {
		return create_id;
	}
	public void setCreate_id(String create_id) {
		this.create_id = create_id;
	}
	
}
