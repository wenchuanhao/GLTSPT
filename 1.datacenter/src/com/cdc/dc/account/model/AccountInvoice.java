package com.cdc.dc.account.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * AccountInvoice entity. @author MyEclipse Persistence Tools
 */
public class AccountInvoice implements java.io.Serializable {

	// Fields

	private String id;
	private String parentId;
	private String invoiceType;
	private String goodsName;
	private String invoiceCode;
	private String invoiceNum;
	private String createDate;
	private String moneyNoTax;
	private String taxNum;
	private String taxRate;
	private String gfTaxpayerNum;
	private String gfTaxpayerName;
	private String xfTaxpayerNum;
	private String xfTaxpayerName;
	private String updateUserId;
	private String updateUserName;
	private Date updateDate;

	// Constructors

	/** default constructor */
	public AccountInvoice() {
	}

	/** full constructor */
	public AccountInvoice(String parentId, String invoiceType,
			String goodsName, String invoiceCode, String invoiceNum,
			String createDate, String moneyNoTax, String taxNum,
			String taxRate, String gfTaxpayerNum, String gfTaxpayerName,
			String xfTaxpayerNum, String xfTaxpayerName, String updateUserId,
			String updateUserName, Date updateDate) {
		this.parentId = parentId;
		this.invoiceType = invoiceType;
		this.goodsName = goodsName;
		this.invoiceCode = invoiceCode;
		this.invoiceNum = invoiceNum;
		this.createDate = createDate;
		this.moneyNoTax = moneyNoTax;
		this.taxNum = taxNum;
		this.taxRate = taxRate;
		this.gfTaxpayerNum = gfTaxpayerNum;
		this.gfTaxpayerName = gfTaxpayerName;
		this.xfTaxpayerNum = xfTaxpayerNum;
		this.xfTaxpayerName = xfTaxpayerName;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateDate = updateDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getInvoiceCode() {
		return this.invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceNum() {
		return this.invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getMoneyNoTax() {
		return this.moneyNoTax;
	}

	public void setMoneyNoTax(String moneyNoTax) {
		this.moneyNoTax = moneyNoTax;
	}

	public String getTaxNum() {
		return this.taxNum;
	}

	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}

	public String getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public String getGfTaxpayerNum() {
		return this.gfTaxpayerNum;
	}

	public void setGfTaxpayerNum(String gfTaxpayerNum) {
		this.gfTaxpayerNum = gfTaxpayerNum;
	}

	public String getGfTaxpayerName() {
		return this.gfTaxpayerName;
	}

	public void setGfTaxpayerName(String gfTaxpayerName) {
		this.gfTaxpayerName = gfTaxpayerName;
	}

	public String getXfTaxpayerNum() {
		return this.xfTaxpayerNum;
	}

	public void setXfTaxpayerNum(String xfTaxpayerNum) {
		this.xfTaxpayerNum = xfTaxpayerNum;
	}

	public String getXfTaxpayerName() {
		return this.xfTaxpayerName;
	}

	public void setXfTaxpayerName(String xfTaxpayerName) {
		this.xfTaxpayerName = xfTaxpayerName;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}