package com.cdc.dc.project.zxmht.model;
// default package

import java.util.Date;

import org.trustel.service.form.PageQueryForm;


/**
 * ZxmHtAttach entity. @author MyEclipse Persistence Tools
 */

public class ZxmHtAttach  extends PageQueryForm  implements java.io.Serializable {


    // Fields    

     private String id;
     private String parentId;
     private String column01;
     private String column02;
     private String column03;
     private Date createDate;
     private String createUserName;
     private String createUserId;
     private String updateUserId;
     private String updateUserName;
     private Date updateDate;

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

    public String getColumn02() {
        return this.column02;
    }
    
    public void setColumn02(String column02) {
        this.column02 = column02;
    }

    public String getColumn03() {
        return this.column03;
    }
    
    public void setColumn03(String column03) {
        this.column03 = column03;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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