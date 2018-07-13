package com.cdc.dc.metadata.cangku_ms.model;

import java.util.Date;

/**
 * 共享-调拨单 日志
 */

public class TbShareDepartmentallLog implements java.io.Serializable {

	// Fields

	private String id;
	private Integer billitype;
	private Date createtime;
	private String allocatebillcode;
	private Integer catagoryid;
	private String catagoryname;
	private String materialsid;
	private String materialscode;
	private String materialsname;
	private Double price;
	private Double qty;
	private Double amount;
	private Integer sourcewarehouseid;
	private String sourcewarehousename;
	private Integer targetwarehouseid;
	private String targetwarehousename;
	private Integer departmentid;
	private String departmentname;

	// Constructors

	/** default constructor */
	public TbShareDepartmentallLog() {
	}

	/** minimal constructor */
	public TbShareDepartmentallLog(Integer billitype) {
		this.billitype = billitype;
	}

	/** full constructor */
	public TbShareDepartmentallLog(Integer billitype, Date createtime,
			String allocatebillcode, Integer catagoryid,
			String catagoryname, String materialsid, String materialscode,
			String materialsname, Double price, Double qty, Double amount,
			Integer sourcewarehouseid, String sourcewarehousename,
			Integer targetwarehouseid, String targetwarehousename,
			Integer departmentid, String departmentname) {
		this.billitype = billitype;
		this.createtime = createtime;
		this.allocatebillcode = allocatebillcode;
		this.catagoryid = catagoryid;
		this.catagoryname = catagoryname;
		this.materialsid = materialsid;
		this.materialscode = materialscode;
		this.materialsname = materialsname;
		this.price = price;
		this.qty = qty;
		this.amount = amount;
		this.sourcewarehouseid = sourcewarehouseid;
		this.sourcewarehousename = sourcewarehousename;
		this.targetwarehouseid = targetwarehouseid;
		this.targetwarehousename = targetwarehousename;
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

	public String getAllocatebillcode() {
		return this.allocatebillcode;
	}

	public void setAllocatebillcode(String allocatebillcode) {
		this.allocatebillcode = allocatebillcode;
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

	public Integer getSourcewarehouseid() {
		return this.sourcewarehouseid;
	}

	public void setSourcewarehouseid(Integer sourcewarehouseid) {
		this.sourcewarehouseid = sourcewarehouseid;
	}

	public String getSourcewarehousename() {
		return this.sourcewarehousename;
	}

	public void setSourcewarehousename(String sourcewarehousename) {
		this.sourcewarehousename = sourcewarehousename;
	}

	public Integer getTargetwarehouseid() {
		return this.targetwarehouseid;
	}

	public void setTargetwarehouseid(Integer targetwarehouseid) {
		this.targetwarehouseid = targetwarehouseid;
	}

	public String getTargetwarehousename() {
		return this.targetwarehousename;
	}

	public void setTargetwarehousename(String targetwarehousename) {
		this.targetwarehousename = targetwarehousename;
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