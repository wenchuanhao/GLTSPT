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

	public class SysRoleOrg implements java.io.Serializable {

		private static final long serialVersionUID = 1L;
		/**
		 * 主键
		 */
		private String roleOrgId;

		/**
		 * 角色id
		 */
		private String roleId;
		/**
		 * 用户id
		 */
		private String orgId;
		
		public SysRoleOrg() {
		}
		public SysRoleOrg(String roleId, String orgId) {
			this.roleId=roleId;
			this.orgId=orgId;
		}
		public String getRoleOrgId() {
			return roleOrgId;
		}
		public void setRoleOrgId(String roleOrgId) {
			this.roleOrgId = roleOrgId;
		}
		public String getRoleId() {
			return roleId;
		}
		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}
		public String getOrgId() {
			return orgId;
		}
	

}
