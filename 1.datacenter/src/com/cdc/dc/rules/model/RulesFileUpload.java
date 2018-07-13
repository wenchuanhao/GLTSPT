package com.cdc.dc.rules.model;

import java.util.Date;
import java.util.List;


/**
 * RulesFileUpload entity. @author MyEclipse Persistence Tools
 * 文档(附件)信息表
 */

public class RulesFileUpload  implements java.io.Serializable {


    // Fields    

     private String fileId;//附件ID，主键
     private String abstractName;//摘要/名称
     private String rulesInfoid;//制度编号
     private String fileName;//附件名称
     private String filePath;//附件路径
     private Long fileSize;//附件大小(KB)
     private Date createTime;//创建时间
     private String createUserid;//创建人ID
     private String createUsername;//创建人名称
     private Date updateTime;//更新时间
     private String updateUserid;//更新人ID
     private String updateUsername;//更新人名称
     /**
      * 1.	制度文件(一个制度只有一个制度文件);
      * 2.	制度附件
      * 3.	发布依据（基地级、跨部门级制度需上传决策会议纪要，部门级制度需上传经审核签字的扫描件）
      * 4.	制度相关文档（可为父信息类型）
      * 5.	模板(可为父信息类型)
      * 6.	流程知识库(可为父信息类型)
      */
     private String types;//附件分类/类型
     private String isParent;//是否父信息；1:是,0:否,,是则表明(文档/模板/知识库)头信息,否则为附件列表信息
     private String status;//附件状态；1：已存，0：删除
     private String busTypes;//业务 类型

     private List<RulesFileUpload> fileList;
    // Constructors

    /** default constructor */
    public RulesFileUpload() {
    }

	/** minimal constructor */
    public RulesFileUpload(String fileId) {
        this.fileId = fileId;
    }
    
    /** full constructor */
    public RulesFileUpload(String fileId, String abstractName, String rulesInfoid, String fileName, String filePath, Long fileSize, Date createTime, String createUserid, String createUsername, Date updateTime, String updateUserid, String updateUsername, String types, String isParent, String status, String busTypes) {
        this.fileId = fileId;
        this.abstractName = abstractName;
        this.rulesInfoid = rulesInfoid;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.createTime = createTime;
        this.createUserid = createUserid;
        this.createUsername = createUsername;
        this.updateTime = updateTime;
        this.updateUserid = updateUserid;
        this.updateUsername = updateUsername;
        this.types = types;
        this.isParent = isParent;
        this.status = status;
        this.busTypes = busTypes;
    }

   
    // Property accessors

    public String getFileId() {
        return this.fileId;
    }
    
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getAbstractName() {
        return this.abstractName;
    }
    
    public void setAbstractName(String abstractName) {
        this.abstractName = abstractName;
    }

    public String getRulesInfoid() {
        return this.rulesInfoid;
    }
    
    public void setRulesInfoid(String rulesInfoid) {
        this.rulesInfoid = rulesInfoid;
    }

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return this.fileSize;
    }
    
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
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
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserid() {
        return this.updateUserid;
    }
    
    public void setUpdateUserid(String updateUserid) {
        this.updateUserid = updateUserid;
    }

    public String getUpdateUsername() {
        return this.updateUsername;
    }
    
    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername;
    }

    public String getTypes() {
        return this.types;
    }
    
    public void setTypes(String types) {
        this.types = types;
    }

    public String getIsParent() {
        return this.isParent;
    }
    
    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

	public String getBusTypes() {
		return busTypes;
	}

	public void setBusTypes(String busTypes) {
		this.busTypes = busTypes;
	}

	public List<RulesFileUpload> getFileList() {
		return fileList;
	}

	public void setFileList(List<RulesFileUpload> fileList) {
		this.fileList = fileList;
	}


}