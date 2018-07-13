package model.sys.entity;

public class RUserGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String RUserGroupId;
	private String roleGroupId;
	private String roleId;

	public RUserGroup() {
	}

	public RUserGroup(String roleGroupId, String roleId) {
		this.roleGroupId = roleGroupId;
		this.setRoleId(roleId);
	}

	public String getRUserGroupId() {
		return this.RUserGroupId;
	}

	public void setRUserGroupId(String RUserGroupId) {
		this.RUserGroupId = RUserGroupId;
	}

	public String getRoleGroupId() {
		return this.roleGroupId;
	}

	public void setRoleGroupId(String roleGroupId) {
		this.roleGroupId = roleGroupId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


}