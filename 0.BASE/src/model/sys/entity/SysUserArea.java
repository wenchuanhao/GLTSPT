package model.sys.entity;

import java.io.Serializable;

public class SysUserArea implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userAreaId;
	private String userId;
	private String parentId;
	private String organizationId;
	private int    ranks;
	
	
	
	private String roleId;//角色id__新增字段
	
	public String getUserAreaId() {
		return userAreaId;
	}

	public void setUserAreaId(String userAreaId) {
		this.userAreaId = userAreaId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public int getRanks() {
		return ranks;
	}

	public void setRanks(int ranks) {
		this.ranks = ranks;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
