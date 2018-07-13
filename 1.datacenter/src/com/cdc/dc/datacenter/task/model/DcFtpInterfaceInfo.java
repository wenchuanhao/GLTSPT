package com.cdc.dc.datacenter.task.model;

import java.util.Date;


/**
 * DcFtpInterfaceInfo entity. @author MyEclipse Persistence Tools
 */

public class DcFtpInterfaceInfo  implements java.io.Serializable {


    // Fields    

     private String id;
     private String parentId;
     private String ftpAccount;
     private String ftpPasswd;
     private String filePath;
     private String filePattern;
     private String serviceType;
     private String dbTablename;
     private Date createDate;
     private String createUserId;
     private Date updateDate;
     private String updateUserId;


    // Constructors

    /** default constructor */
    public DcFtpInterfaceInfo() {
    }

    
    /** full constructor */
    public DcFtpInterfaceInfo(String parentId, String ftpAccount, String ftpPasswd, String filePath, String filePattern, String serviceType, String dbTablename, Date createDate, String createUserId, Date updateDate, String updateUserId) {
        this.parentId = parentId;
        this.ftpAccount = ftpAccount;
        this.ftpPasswd = ftpPasswd;
        this.filePath = filePath;
        this.filePattern = filePattern;
        this.serviceType = serviceType;
        this.dbTablename = dbTablename;
        this.createDate = createDate;
        this.createUserId = createUserId;
        this.updateDate = updateDate;
        this.updateUserId = updateUserId;
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

    public String getFtpAccount() {
        return this.ftpAccount;
    }
    
    public void setFtpAccount(String ftpAccount) {
        this.ftpAccount = ftpAccount;
    }

    public String getFtpPasswd() {
        return this.ftpPasswd;
    }
    
    public void setFtpPasswd(String ftpPasswd) {
        this.ftpPasswd = ftpPasswd;
    }

    public String getFilePath() {
        return this.filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePattern() {
        return this.filePattern;
    }
    
    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public String getServiceType() {
        return this.serviceType;
    }
    
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDbTablename() {
        return this.dbTablename;
    }
    
    public void setDbTablename(String dbTablename) {
        this.dbTablename = dbTablename;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserId() {
        return this.createUserId;
    }
    
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUserId() {
        return this.updateUserId;
    }
    
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
   








}