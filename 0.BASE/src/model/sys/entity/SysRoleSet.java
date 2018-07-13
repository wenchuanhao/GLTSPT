package model.sys.entity;

import java.io.Serializable;

public class SysRoleSet implements Serializable {

	private static final long serialVersionUID = 1L;
	private String roleSetId;
	private String roleId;
	private String parentId;

	public String getRoleSetId() {
		return roleSetId;
	}

	public void setRoleSetId(String roleSetId) {
		this.roleSetId = roleSetId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	

}
