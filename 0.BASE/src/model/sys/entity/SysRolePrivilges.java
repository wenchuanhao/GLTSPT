package model.sys.entity;

/**
 * 系统角色权限关系表
 * 
 * @Description:
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-22 上午10:22:40
 * @UpdateRemark:
 * @Version: V1.0
 */
public class SysRolePrivilges implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String rolePrivilgesId;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 模块id
	 */
	private String moduleCode;

	public SysRolePrivilges() {
	}

	public SysRolePrivilges(String roleId, String moduleCode) {
		super();
		this.roleId = roleId;
		this.moduleCode = moduleCode;
	}

	public String getRolePrivilgesId() {
		return this.rolePrivilgesId;
	}

	public void setRolePrivilgesId(String rolePrivilgesId) {
		this.rolePrivilgesId = rolePrivilgesId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

}