package model.sys.entity;

/**
 * 
 * 
 * @Description: 系统角色用户关系表
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-22 上午10:24:03
 * @UpdateRemark:
 * @Version: V1.0
 */

public class SysRoleUser implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String roleuserId;

	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 用户id
	 */
	private String userId;
	
	private String areaName;//假字段:显示区域

	public SysRoleUser() {
	}
	public SysRoleUser(String userId, String roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}
	public String getRoleuserId() {
		return this.roleuserId;
	}

	public void setRoleuserId(String roleuserId) {
		this.roleuserId = roleuserId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
}