package model.sys.entity;

import java.util.Date;

public class UserSetingMapping implements java.io.Serializable{
	private String id;
	 private String userId;//用户编号
	 private String menuCode;//模块编号
	 private Date createtime;//创建时间

	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
