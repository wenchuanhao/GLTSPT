package com.cdc.dc.command.manage.model;

import java.util.Date;

/**
 * 工程指令归档信息表
 * @author zengkai
 * @date 2016-08-30 09:10:00
 * CommandFolder entity. @author MyEclipse Persistence Tools
 */

public class CommandFolder implements java.io.Serializable {

	// Fields

	private String folderId;//归档ID
	private String folderPosition;//归档位置
	private String commandInfoid;//指令编号
	private Date folderTime;//归档时间
	private String folderUserid;//归档人ID
	private String folderUsername;//归档人名称
	private String status;//状态  1：保存，0：已撤销
	private String digEst;//摘要

	// Constructors

	/** default constructor */
	public CommandFolder() {
	}

	/** minimal constructor */
	public CommandFolder(String folderId) {
		this.folderId = folderId;
	}

	/** full constructor */
	public CommandFolder(String folderId, String folderPosition,
			String commandInfoid, Date folderTime, String folderUserid,
			String folderUsername, String status, String digEst) {
		this.folderId = folderId;
		this.folderPosition = folderPosition;
		this.commandInfoid = commandInfoid;
		this.folderTime = folderTime;
		this.folderUserid = folderUserid;
		this.folderUsername = folderUsername;
		this.status = status;
		this.digEst = digEst;
	}

	// Property accessors

	public String getFolderId() {
		return this.folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getFolderPosition() {
		return this.folderPosition;
	}

	public void setFolderPosition(String folderPosition) {
		this.folderPosition = folderPosition;
	}

	public String getCommandInfoid() {
		return this.commandInfoid;
	}

	public void setCommandInfoid(String commandInfoid) {
		this.commandInfoid = commandInfoid;
	}

	public Date getFolderTime() {
		return this.folderTime;
	}

	public void setFolderTime(Date folderTime) {
		this.folderTime = folderTime;
	}

	public String getFolderUserid() {
		return this.folderUserid;
	}

	public void setFolderUserid(String folderUserid) {
		this.folderUserid = folderUserid;
	}

	public String getFolderUsername() {
		return this.folderUsername;
	}

	public void setFolderUsername(String folderUsername) {
		this.folderUsername = folderUsername;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDigEst() {
		return digEst;
	}

	public void setDigEst(String digEst) {
		this.digEst = digEst;
	}
	
	
}