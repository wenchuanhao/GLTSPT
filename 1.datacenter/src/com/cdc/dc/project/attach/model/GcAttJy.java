package com.cdc.dc.project.attach.model;
// default package

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.project.attach.service.IGcAttachService;
import com.cdc.system.core.util.SpringHelper;


/**
 * GcAttJy entity. @author MyEclipse Persistence Tools
 */

public class GcAttJy extends PageQueryForm  implements java.io.Serializable {


    // Fields    

     private String id;
     private String parentId;
     private String column05;
     private String column06;
     private String column07;
     private Date createDate;
     private String updateUserId;
     private String updateUserName;
     private Date updateDate;
     private String createUserName;
     private String createUserId;

     public GcAttach getAttach() throws Exception{
    	 
    	 IGcAttachService gcAttachService = (IGcAttachService)SpringHelper.getBean("gcAttachService");
    	 GcAttach attach = gcAttachService.findGcAttachById(this.parentId);
    	 
    	 return attach;
     }
    // Constructors

    /** default constructor */
    public GcAttJy() {
    }

	/** minimal constructor */
    public GcAttJy(String parentId) {
        this.parentId = parentId;
    }
    
    /** full constructor */
    public GcAttJy(String parentId, String column05, String column06, String column07, Date createDate, String updateUserId, String updateUserName, Date updateDate, String createUserName, String createUserId) {
        this.parentId = parentId;
        this.column05 = column05;
        this.column06 = column06;
        this.column07 = column07;
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

    public String getColumn05() {
        return this.column05;
    }
    
    public void setColumn05(String column05) {
        this.column05 = column05;
    }

    public String getColumn06() {
        return this.column06;
    }
    
    public void setColumn06(String column06) {
        this.column06 = column06;
    }

    public String getColumn07() {
        return this.column07;
    }
    
    public void setColumn07(String column07) {
        this.column07 = column07;
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
   








}