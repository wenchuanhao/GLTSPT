package com.cdc.sys.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import model.sys.entity.SysRole;

import org.trustel.service.form.PageQueryForm;

public class SysRoleGroupForm extends PageQueryForm implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String roleGroupid;
	
	private String roleGroupcode;
	/**
	 * 角色组名称
	 */
	private String roleGroupname;
	/**
	 * 角色描述
	 */
	private String roleGroupdesc;
	/**
	 * 创建人
	 */
	private String creatorid;
	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 角色
	 */
	private List<SysRole> roles = new ArrayList<SysRole>();
	
	
	public String getRoleGroupid() {
		return roleGroupid;
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
		return roleGroupname;
	}

	public void setRoleGroupname(String roleGroupname) {
		this.roleGroupname = roleGroupname;
	}

	public String getRoleGroupdesc() {
		return roleGroupdesc;
	}

	public void setRoleGroupdesc(String roleGroupdesc) {
		this.roleGroupdesc = roleGroupdesc;
	}

	public String getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	 

}
