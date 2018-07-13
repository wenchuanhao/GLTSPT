package com.cdc.dc.purchase.model;

/**
 * 保存新增加列的相关信息
 * @author ywc
 * 
 *
 */
public class PurchaseColumn {
	private String id;
	private int column_order;
	private String column_name;
	private String column_cname;
	private String column_type;
	private String column_length;
	private String isDelete;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getColumn_order() {
		return column_order;
	}
	public void setColumn_order(int column_order) {
		this.column_order = column_order;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getColumn_cname() {
		return column_cname;
	}
	public void setColumn_cname(String column_cname) {
		this.column_cname = column_cname;
	}
	public String getColumn_type() {
		return column_type;
	}
	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}
	public String getColumn_length() {
		return column_length;
	}
	public void setColumn_length(String column_length) {
		this.column_length = column_length;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}	
}
