package com.cdc.dc.purchase.form;

import org.trustel.service.form.PageQueryForm;

public class PurchaseDataColumnForm extends PageQueryForm {
	private String id;
	private int column_order;//序号
	private String column_name;//数据列名
	private String column_cname;//字段名
	private String column_type;//字段类型
	private String column_length;//文本长度
	private String isDelete;//删除状态
	
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
