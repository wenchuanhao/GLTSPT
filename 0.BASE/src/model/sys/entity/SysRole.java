package model.sys.entity;

import java.util.Date;

/**
 * 
 * 
 * @Description: 系统角色实体类
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-22 上午10:16:36
 * @UpdateRemark:
 * @Version: V1.0
 */
public class SysRole implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 角色编码
	 */
	private String roleCode;
	/**
	 * 角色描述
	 */
	private String roleDesc;
	/**
	 * 创建人
	 */
	private String createrId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 是否默认角色————新增字段
	 */
	private String isDefaultRole;//1.不是 2.是
	
	private String createPerson;//创建人__假字段
	private String pzRole;//已配置角色名称_假字段

	public SysRole() {
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getPzRole() {
		return pzRole;
	}

	public void setPzRole(String pzRole) {
		this.pzRole = pzRole;
	}

	public String getIsDefaultRole() {
		return isDefaultRole;
	}

	public void setIsDefaultRole(String isDefaultRole) {
		this.isDefaultRole = isDefaultRole;
	}
	
	
	
}