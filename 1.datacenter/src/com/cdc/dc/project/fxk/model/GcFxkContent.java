package com.cdc.dc.project.fxk.model;

import java.util.Date;


/**
 * GcFxkContent entity. @author MyEclipse Persistence Tools
 */

public class GcFxkContent  implements java.io.Serializable {


    // Fields    

     private String contentId;
     private String seq;//序号
     //风险因素			
     private String riskFactor;
     //风险导致的后果
     private String riskAfter;
     //风险应对措施
     private String riskMeasure;
     //风险类别
     private String riskType;
     private String stageId;//阶段id
     private Date createTime;


    // Constructors

    /** default constructor */
    public GcFxkContent() {
    }

	/** minimal constructor */
    public GcFxkContent(String contentId) {
        this.contentId = contentId;
    }
    
    /** full constructor */
    public GcFxkContent(String contentId, String seq, String riskFactor, String riskAfter, String riskMeasure, String riskType, String stageId, Date createTime) {
        this.contentId = contentId;
        this.seq = seq;
        this.riskFactor = riskFactor;
        this.riskAfter = riskAfter;
        this.riskMeasure = riskMeasure;
        this.riskType = riskType;
        this.stageId = stageId;
        this.createTime = createTime;
    }

   
    // Property accessors

    public String getContentId() {
        return this.contentId;
    }
    
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getSeq() {
        return this.seq;
    }
    
    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getRiskFactor() {
        return this.riskFactor;
    }
    
    public void setRiskFactor(String riskFactor) {
        this.riskFactor = riskFactor;
    }

    public String getRiskAfter() {
        return this.riskAfter;
    }
    
    public void setRiskAfter(String riskAfter) {
        this.riskAfter = riskAfter;
    }

    public String getRiskMeasure() {
        return this.riskMeasure;
    }
    
    public void setRiskMeasure(String riskMeasure) {
        this.riskMeasure = riskMeasure;
    }

    public String getRiskType() {
        return this.riskType;
    }
    
    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getStageId() {
        return this.stageId;
    }
    
    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
   








}