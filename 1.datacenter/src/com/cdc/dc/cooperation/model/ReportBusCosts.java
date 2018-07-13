package com.cdc.dc.cooperation.model;

import java.util.Date;


/**
 * 业务招待费
 */

public class ReportBusCosts  implements java.io.Serializable {


    // Fields    

     private String recordId;
     private String reimUsername;
     private String createUsername;
     private String commitTime;
     private String recordType;
     private String recordStatus;
     private String applicationAmount;
     private String planAmount;
     private String auditAmount;
     private String summary;
     //固定字段
     private Date createTime;
     private String datasourceRecordsId;
     private String normalId;


    // Constructors

    /** default constructor */
    public ReportBusCosts() {
    }

	/** minimal constructor */
    public ReportBusCosts(String normalId) {
        this.normalId = normalId;
    }
    
    /** full constructor */
    public ReportBusCosts(String recordId, String reimUsername, String createUsername, String commitTime, String recordType, String recordStatus, String applicationAmount, String planAmount, String auditAmount, String summary, Date createTime, String datasourceRecordsId,String normalId) {
    	this.recordId = recordId;
        this.reimUsername = reimUsername;
        this.createUsername = createUsername;
        this.commitTime = commitTime;
        this.recordType = recordType;
        this.recordStatus = recordStatus;
        this.applicationAmount = applicationAmount;
        this.planAmount = planAmount;
        this.auditAmount = auditAmount;
        this.summary = summary;
        this.createTime = createTime;
        this.datasourceRecordsId = datasourceRecordsId;
        this.normalId = normalId;
    }

   
    // Property accessors
    
    public String getRecordId() {
        return this.recordId;
    }
    
    public String getNormalId() {
		return normalId;
	}

	public void setNormalId(String normalId) {
		this.normalId = normalId;
	}

	public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getReimUsername() {
        return this.reimUsername;
    }
    
    public void setReimUsername(String reimUsername) {
        this.reimUsername = reimUsername;
    }

    public String getCreateUsername() {
        return this.createUsername;
    }
    
    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getCommitTime() {
        return this.commitTime;
    }
    
    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getRecordType() {
        return this.recordType;
    }
    
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getRecordStatus() {
        return this.recordStatus;
    }
    
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getApplicationAmount() {
        return this.applicationAmount;
    }
    
    public void setApplicationAmount(String applicationAmount) {
        this.applicationAmount = applicationAmount;
    }

    public String getPlanAmount() {
        return this.planAmount;
    }
    
    public void setPlanAmount(String planAmount) {
        this.planAmount = planAmount;
    }

    public String getAuditAmount() {
        return this.auditAmount;
    }
    
    public void setAuditAmount(String auditAmount) {
        this.auditAmount = auditAmount;
    }

    public String getSummary() {
        return this.summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDatasourceRecordsId() {
        return this.datasourceRecordsId;
    }
    
    public void setDatasourceRecordsId(String datasourceRecordsId) {
        this.datasourceRecordsId = datasourceRecordsId;
    }
   








}