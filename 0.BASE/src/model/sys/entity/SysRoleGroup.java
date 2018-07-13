package model.sys.entity;

import java.util.Date;

/**
 * SysRoleGroup entity. @author MyEclipse Persistence Tools
 */

public class SysRoleGroup implements java.io.Serializable {

	// Fields

	private String roleGroupid;
	private String roleGroupcode;
	private String roleGroupname;
	private String roleGroupdesc;
	private Date createTime;
	private String createUserid;
	private String createUsername;

	// Constructors

	/** default constructor */
	public SysRoleGroup() {
	}

	/** minimal constructor */
	public SysRoleGroup(String roleGroupid) {
		this.roleGroupid = roleGroupid;
	}

	/** full constructor */
	public SysRoleGroup(String roleGroupid,String roleGroupcode, String roleGroupname,
			String roleGroupdesc, Date createTime, String createUserid,
			String createUsername) {
		this.roleGroupid = roleGroupid;
		this.roleGroupcode = roleGroupcode;
		this.roleGroupname = roleGroupname;
		this.roleGroupdesc = roleGroupdesc;
		this.createTime = createTime;
		this.createUserid = createUserid;
		this.createUsername = createUsername;
	}

	// Property accessors

	public String getRoleGroupid() {
		return this.roleGroupid;
	}

	public void setRoleGroupid(String roleGroupid) {
		this.roleGroupid = roleGroupid;
	}

	public String getRoleGroupcode() {
		return roleGroupcode;
	}

	public void setRoleGroupcode(String roleGroupcode) {
		this.roleGroupcode = roleGroupcode;
	}

	public String getRoleGroupname() {
		return this.roleGroupname;
	}

	public void setRoleGroupname(String roleGroupname) {
		this.roleGroupname = roleGroupname;
	}

	public String getRoleGroupdesc() {
		return this.roleGroupdesc;
	}

	public void setRoleGroupdesc(String roleGroupdesc) {
		this.roleGroupdesc = roleGroupdesc;
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