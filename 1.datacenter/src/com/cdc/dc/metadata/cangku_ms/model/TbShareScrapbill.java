package com.cdc.dc.metadata.cangku_ms.model;

import java.util.Date;

/**
 * 共享-报废单
 */

public class TbShareScrapbill implements java.io.Serializable {

	// Fields

	private String id;
	private Integer billitype;
	private Date createtime;
	private String scrapbillcode;
	private Integer catagoryid;
	private String catagoryname;
	private String materialsid;
	private String materialscode;
	private String materialsname;
	private Double price;
	private Double qty;
	private Double amount;
	private Integer warehouseid;
	private String warehousename;
	private Integer departmentid;
	private String departmentname;

	// Constructors

	/** default constructor */
	public TbShareScrapbill() {
	}

	/** minimal constructor */
	public TbShareScrapbill(Integer billitype) {
		this.billitype = billitype;
	}

	/** full constructor */
	public TbShareScrapbill(Integer billitype, Date createtime,
			String scrapbillcode, Integer catagoryid, String catagoryname,
			String materialsid, String materialscode, String materialsname,
			Double price, Double qty, Double amount, Integer warehouseid,
			String warehousename, Integer departmentid, String departmentname) {
		this.billitype = billitype;
		this.createtime = createtime;
		this.scrapbillcode = scrapbillcode;
		this.catagoryid = catagoryid;
		this.catagoryname = catagoryname;
		this.materialsid = materialsid;
		this.materialscode = materialscode;
		this.materialsname = materialsname;
		this.price = price;
		this.qty = qty;
		this.amount = amount;
		this.warehouseid = warehouseid;
		this.warehousename = warehousename;
		this.departmentid = departmentid;
		this.departmentname = departmentname;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBillitype() {
		return this.billitype;
	}

	public void setBillitype(Integer billitype) {
		this.billitype = billitype;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getScrapbillcode() {
		return this.scrapbillcode;
	}

	public void setScrapbillcode(String scrapbillcode) {
		this.scrapbillcode = scrapbillcode;
	}

	public Integer getCatagoryid() {
		return this.catagoryid;
	}

	public void setCatagoryid(Integer catagoryid) {
		this.catagoryid = catagoryid;
	}

	public String getCatagoryname() {
		return this.catagoryname;
	}

	public void setCatagoryname(String catagoryname) {
		this.catagoryname = catagoryname;
	}

	public String getMaterialsid() {
		return this.materialsid;
	}

	public void setMaterialsid(String materialsid) {
		this.materialsid = materialsid;
	}

	public String getMaterialscode() {
		return this.materialscode;
	}

	public void setMaterialscode(String materialscode) {
		this.materialscode = materialscode;
	}

	public String getMaterialsname() {
		return this.materialsname;
	}

	public void setMaterialsname(String materialsname) {
		this.materialsname = materialsname;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(Integer warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWarehousename() {
		return this.warehousename;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}

	public Integer getDepartmentid() {
		return this.departmentid;
	}

	public void setDepartmentid(Integer departmentid) {
		this.departmentid = departmentid;
	}

	public String getDepartmentname() {
		return this.departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

}