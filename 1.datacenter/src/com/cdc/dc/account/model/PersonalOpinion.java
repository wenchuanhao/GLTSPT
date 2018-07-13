package com.cdc.dc.account.model;
// default package

import java.util.Date;

/**
 * 常用意见表
 * 
 * @author liwj
 */

public class PersonalOpinion implements java.io.Serializable {

	// Fields
	/**
	 * 常用意见编号
	 */
	private String personalOpinionId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建者
	 */
	private String creator;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 缩写
	 */
	private String outline;

	// Constructors

	/** default constructor */
	public PersonalOpinion() {
	}

	/** minimal constructor */
	public PersonalOpinion(String personalOpinionId) {
		this.personalOpinionId = personalOpinionId;
	}

	/** full constructor */
	public PersonalOpinion(String personalOpinionId, String content,
			String creator, Date createDate, String outline) {
		this.personalOpinionId = personalOpinionId;
		this.content = content;
		this.creator = creator;
		this.createDate = createDate;
		this.outline = outline;
	}

	// Property accessors

	public String getContent() {
		return this.content;
	}

	public String getPersonalOpinionId() {
		return personalOpinionId;
	}

	public void setPersonalOpinionId(String personalOpinionId) {
		this.personalOpinionId = personalOpinionId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}
	
}