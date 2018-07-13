package com.cdc.dc.rules.model;

import java.util.Date;


/**
 * RulesFlowInfo entity. @author MyEclipse Persistence Tools
 * 流程信息表
 */

public class RulesFlowInfo  implements java.io.Serializable {


    // Fields    

     private String flowId;//流程ID,主键
     private String rulesInfoid;//制度编号,外键
     /**
      * 1:创建制度;2:审核制度;3:发布制度;4:制度废止;5:制度修订;6:制度退回
      */
     private String flowLink;//流程环节/名称
     private String handelUserid;//处理人ID
     private String handelUsername;//处理人名称
     private Date createTime;//到达时间
     private Date handelTime;//下一环节处理时间
     /**
      * 当前状态,1处理中,2:已处理（同一制度信息表只能有一条处理中的流程信息）
      */
     private String handelStatus;//处理状态
     private String handelOpinion;//审核意见/处理意见
     /**
      * 0:退回,1:发布;2:废止;(最新数据与制度信息表同步)
      */
     private String handelResult;//审核结果/处理结果
     private String fromFlowId;//上一流程ID
     private String toFlowId;//下一流程ID

    // Constructors

    /** default constructor */
    public RulesFlowInfo() {
    }

	/** minimal constructor */
    public RulesFlowInfo(String flowId) {
        this.flowId = flowId;
    }
    
    /** full constructor */
    public RulesFlowInfo(String flowId, String rulesInfoid, String flowLink, String handelUserid, String handelUsername, Date createTime, Date handelTime, String handelStatus, String handelOpinion, String handelResult, String fromFlowId, String toFlowId) {
        this.flowId = flowId;
        this.rulesInfoid = rulesInfoid;
        this.flowLink = flowLink;
        this.handelUserid = handelUserid;
        this.handelUsername = handelUsername;
        this.createTime = createTime;
        this.handelTime = handelTime;
        this.handelStatus = handelStatus;
        this.handelOpinion = handelOpinion;
        this.handelResult = handelResult;
        this.fromFlowId = fromFlowId;
        this.toFlowId = toFlowId;
    }

   
    // Property accessors

    public String getFlowId() {
        return this.flowId;
    }
    
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getRulesInfoid() {
        return this.rulesInfoid;
    }
    
    public void setRulesInfoid(String rulesInfoid) {
        this.rulesInfoid = rulesInfoid;
    }

    public String getFlowLink() {
        return this.flowLink;
    }
    
    public void setFlowLink(String flowLink) {
        this.flowLink = flowLink;
    }

    public String getHandelUserid() {
        return this.handelUserid;
    }
    
    public void setHandelUserid(String handelUserid) {
        this.handelUserid = handelUserid;
    }

    public String getHandelUsername() {
        return this.handelUsername;
    }
    
    public void setHandelUsername(String handelUsername) {
        this.handelUsername = handelUsername;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getHandelTime() {
        return this.handelTime;
    }
    
    public void setHandelTime(Date handelTime) {
        this.handelTime = handelTime;
    }

    public String getHandelStatus() {
        return this.handelStatus;
    }
    
    public void setHandelStatus(String handelStatus) {
        this.handelStatus = handelStatus;
    }

    public String getHandelOpinion() {
        return this.handelOpinion;
    }
    
    public void setHandelOpinion(String handelOpinion) {
        this.handelOpinion = handelOpinion;
    }

    public String getHandelResult() {
        return this.handelResult;
    }
    
    public void setHandelResult(String handelResult) {
        this.handelResult = handelResult;
    }

    public String getFromFlowId() {
        return this.fromFlowId;
    }
    
    public void setFromFlowId(String fromFlowId) {
        this.fromFlowId = fromFlowId;
    }

    public String getToFlowId() {
        return this.toFlowId;
    }
    
    public void setToFlowId(String toFlowId) {
        this.toFlowId = toFlowId;
    }
   








}