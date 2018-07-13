package com.cdc.dc.project.fxk.model;

import java.util.Date;


/**
 * GcFxkStage entity. @author MyEclipse Persistence Tools
 */

public class GcFxkStage  implements java.io.Serializable {


    // Fields    

     private String stageId;
     private String seq;
     private String stageName;//阶段名称
     private String parentId;//父阶段id
     private String levelNum;//阶段级别
     private Date createTime;


    // Constructors

    /** default constructor */
    public GcFxkStage() {
    }

	/** minimal constructor */
    public GcFxkStage(String stageId) {
        this.stageId = stageId;
    }
    
    /** full constructor */
    public GcFxkStage(String stageId, String seq, String stageName, String parentId, String levelNum, Date createTime) {
        this.stageId = stageId;
        this.seq = seq;
        this.stageName = stageName;
        this.parentId = parentId;
        this.levelNum = levelNum;
        this.createTime = createTime;
    }

   
    // Property accessors

    public String getStageId() {
        return this.stageId;
    }
    
    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getSeq() {
        return this.seq;
    }
    
    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getStageName() {
        return this.stageName;
    }
    
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getParentId() {
        return this.parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


	public String getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}

	public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
   








}