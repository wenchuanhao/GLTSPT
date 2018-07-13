package model.sys.entity;

import java.util.Date;

/**
 * SysRolegroupRole entity. @author MyEclipse Persistence Tools
 */

public class SysRolegroupRole implements java.io.Serializable {

	// Fields

	private String rgrId;
	private String roleGroupid;
	private String roleId;
	private Date createTime;
	private String createUserid;
	private String createUsername;

	// Constructors

	/** default constructor */
	public SysRolegroupRole() {
	}

	/** minimal constructor */
	public SysRolegroupRole(String rgrId) {
		this.rgrId = rgrId;
	}

	/** full constructor */
	public SysRolegroupRole(String rgrId, String roleGroupid, String roleId,
			Date createTime, String createUserid, String createUsername) {
		this.rgrId = rgrId;
		this.roleGroupid = roleGroupid;
		this.roleId = roleId;
		this.createTime = createTime;
		this.createUserid = createUserid;
		this.createUsername = createUsername;
	}

	// Property accessors

	public String getRgrId() {
		return this.rgrId;
	}

	public void setRgrId(String rgrId) {
		this.rgrId = rgrId;
	}

	public String getRoleGroupid() {
		return this.roleGroupid;
	}

	public void setRoleGroupid(String roleGroupid) {
		this.roleGroupid = roleGroupid;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

}