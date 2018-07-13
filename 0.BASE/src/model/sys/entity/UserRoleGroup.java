package model.sys.entity;

import java.util.Date;

public class UserRoleGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String userRoleGroupId;
	private String roleId;
	private String groupId;
	private String roleName;
	private String groupName;
	private Date createData;
	private String currentUserId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Date getCreateData() {
		return createData;
	}

	public void setCreateData(Date createData) {
		this.createData = createData;
	}
	public UserRoleGroup(String currentUserId,String userId,String groupId,String userName,String groupName,String roleId){
		this.userId=userId;
		this.groupId=groupId;
		this.setRoleName(userName);
		this.groupName=groupName;
		this.currentUserId=currentUserId;
		this.roleId=roleId;
	}
	public UserRoleGroup() {
	}

	public void setUserRoleGroupId(String userRoleGroupId) {
		this.userRoleGroupId = userRoleGroupId;
	}

	public String getUserRoleGroupId() {
		return userRoleGroupId;
	}



	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


}