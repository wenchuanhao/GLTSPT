package com.cdc.dc.rules.model;

import java.util.Date;
import java.util.List;

import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.dc.rules.service.IRulesService;
import com.cdc.system.core.util.SpringHelper;


/**
 * RulesInfo entity. @author MyEclipse Persistence Tools
 * 制度信息表
 */

public class RulesInfo  implements java.io.Serializable {


    // Fields    

     private String rulesId;//ID,主键
     private String rulesCode;//制度编号:“NFJD”+部门简拼+三位流水
     private String rulesName;//制度名称
     /**
      * 基地级/跨部门级/部门级
      */
     private String rulesGrade;//制度等级,系统配置
     private String rulesTypeId;//制度分类ID,管理制度分类表
     private String leadDepId;//牵头部门ID
     private Date createTime;//创建时间
     private String createUserid;//创建人ID
     private String createUsername;//创建人名称
     private Date updateTime;//更新时间
     private String updateUserid;//更新人ID
     private String updateUsername;//更新人名称
     /**
      * 1.草稿；2.已提交审核（审核中）；3.已发布；4.已废止；5.已修订；
      */
     private String status;//状态

    // Constructors

    /** default constructor */
    public RulesInfo() {
    }

	/** minimal constructor */
    public RulesInfo(String rulesId) {
        this.rulesId = rulesId;
    }
    
    /** full constructor */
    public RulesInfo(String rulesId,String rulesCode, String rulesName, String rulesGrade, String rulesTypeId, String leadDepId, Date createTime, String createUserid, String createUsername,Date updateTime, String updateUserid, String updateUsername, String status) {
        this.rulesId = rulesId;
        this.rulesCode = rulesCode;
        this.rulesName = rulesName;
        this.rulesGrade = rulesGrade;
        this.rulesTypeId = rulesTypeId;
        this.leadDepId = leadDepId;
        this.createTime = createTime;
        this.createUserid = createUserid;
        this.createUsername = createUsername;
        this.updateTime = updateTime;
        this.updateUserid = updateUserid;
        this.updateUsername = updateUsername;
        this.status = status;
    }

    public Date getRulesFB(){
    	IRulesService rulesService = (IRulesService)SpringHelper.getBean("rulesService");
		RulesFlowInfo rulesFB = rulesService.queryRulesFlowInfoStatus(this.rulesId,RulesCommon.rulesFlowInfoFlowLink_FB,null,null);
		if(rulesFB != null)	return rulesFB.getCreateTime();
		return null;
	}
    
    public Date getRulesFZ(){
    	IRulesService rulesService = (IRulesService)SpringHelper.getBean("rulesService");
    	RulesFlowInfo rulesFZ = rulesService.queryRulesFlowInfoStatus(this.rulesId,RulesCommon.rulesFlowInfoFlowLink_FZ,null,null);
		if(rulesFZ != null)return rulesFZ.getCreateTime();
		return null;
	}
    
    
    public int getCountFile(){
    	IRulesService rulesService = (IRulesService)SpringHelper.getBean("rulesService");
    	List<RulesFileUpload> fileList = rulesService.queryRulesFileUploadByRulesId(this.rulesId,RulesCommon.rulesFileUploadTypes_ZDXGWD);
		//相关文档数量
		if(fileList != null){
			int count = 0;
			for (RulesFileUpload rulesFileUpload : fileList) {
				count += rulesService.queryRulesFileList(rulesFileUpload.getFileId(),RulesCommon.rulesFileUploadTypes_ZDXGWD).size();
			}
			return count;
		}else{
			return 0;
		}
    }
    
    // Property accessors

    public String getRulesId() {
        return this.rulesId;
    }
    
    public void setRulesId(String rulesId) {
        this.rulesId = rulesId;
    }

    
    public String getRulesCode() {
		return rulesCode;
	}

	public void setRulesCode(String rulesCode) {
		this.rulesCode = rulesCode;
	}

	public String getRulesName() {
        return this.rulesName;
    }
    
    public void setRulesName(String rulesName) {
        this.rulesName = rulesName;
    }

    public String getRulesGrade() {
        return this.rulesGrade;
    }
    
    public void setRulesGrade(String rulesGrade) {
        this.rulesGrade = rulesGrade;
    }

    public String getRulesTypeId() {
        return this.rulesTypeId;
    }
    
    public void setRulesTypeId(String rulesTypeId) {
        this.rulesTypeId = rulesTypeId;
    }

    public String getLeadDepId() {
        return this.leadDepId;
    }
    
    public void setLeadDepId(String leadDepId) {
        this.leadDepId = leadDepId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserid() {
        return this.createUserid;
    }
    
    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public String getCreateUsername() {
        return this.createUsername;
    }
    
    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserid() {
		return updateUserid;
	}

	public void setUpdateUserid(String updateUserid) {
		this.updateUserid = updateUserid;
	}

	public String getUpdateUsername() {
		return updateUsername;
	}

	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}

	public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
   








}