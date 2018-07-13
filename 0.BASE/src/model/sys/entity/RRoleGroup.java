package model.sys.entity;
public class RRoleGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String rRoleGroupId;
	private String roleGroupId;
	private String roleId;

	public RRoleGroup() {
	}

	public RRoleGroup(String roleGroupId, String roleId) {
		this.roleGroupId = roleGroupId;
		this.roleId = roleId;
	}

	public String getrRoleGroupId() {
		return rRoleGroupId;
	}

	public void setrRoleGroupId(String rRoleGroupId) {
		this.rRoleGroupId = rRoleGroupId;
	}

	public String getRoleGroupId() {
		return roleGroupId;
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