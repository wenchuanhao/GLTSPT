package com.cdc.dc.project.tzbm.model;
// default package

import java.math.BigDecimal;
import java.util.Date;

import org.trustel.service.form.PageQueryForm;


/**
 * TzbmYear entity. @author MyEclipse Persistence Tools
 */

public class TzbmYear extends PageQueryForm implements java.io.Serializable {


    // Fields    

     private String id;
     private String parentId;
     private String column01;
     private BigDecimal column02;
     private BigDecimal column03;
     private BigDecimal column04;
     private BigDecimal column05;
     private String column06;
     private Date column07;
     private String column08;
     private String deleteFlag;
     private Date createDate;
     private String updateUserId;
     private String updateUserName;
     private Date updateDate;
     private String createUserName;
     private String createUserId;


    // Constructors

    /** default constructor */
    public TzbmYear() {
    }

    
    /** full constructor */
    public TzbmYear(String parentId, String column01, BigDecimal column02, BigDecimal column03, BigDecimal column04, BigDecimal column05, String column06, Date column07, String deleteFlag, Date createDate, String updateUserId, String updateUserName, Date updateDate, String createUserName, String createUserId) {
        this.parentId = parentId;
        this.column01 = column01;
        this.column02 = column02;
        this.column03 = column03;
        this.column04 = column04;
        this.column05 = column05;
        this.column06 = column06;
        this.column07 = column07;
        this.deleteFlag = deleteFlag;
        this.createDate = createDate;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateDate = updateDate;
        this.createUserName = createUserName;
        this.createUserId = createUserId;
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

    public String getColumn01() {
        return this.column01;
    }
    
    public void setColumn01(String column01) {
        this.column01 = column01;
    }

    public BigDecimal getColumn02() {
        return this.column02;
    }
    
    public void setColumn02(BigDecimal column02) {
        this.column02 = column02;
    }

    public BigDecimal getColumn03() {
        return this.column03;
    }
    
    public void setColumn03(BigDecimal column03) {
        this.column03 = column03;
    }

    public BigDecimal getColumn04() {
        return this.column04;
    }
    
    public void setColumn04(BigDecimal column04) {
        this.column04 = column04;
    }

    public BigDecimal getColumn05() {
        return this.column05;
    }
    
    public void setColumn05(BigDecimal column05) {
        this.column05 = column05;
    }

    public String getColumn06() {
        return this.column06;
    }
    
    public void setColumn06(String column06) {
        this.column06 = column06;
    }

    public Date getColumn07() {
        return this.column07;
    }
    
    public void setColumn07(Date column07) {
        this.column07 = column07;
    }

    public String getDeleteFlag() {
        return this.deleteFlag;
    }
    
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getCreateUserName() {
        return this.createUserName;
    }
    
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserId() {
        return this.createUserId;
    }
    
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
   
    public String getColumn08() {
		return column08;
	}

	public void setColumn08(String column08) {
		this.column08 = column08;
	}







}