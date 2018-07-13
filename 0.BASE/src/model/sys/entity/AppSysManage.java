package model.sys.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author xms
 *应用系统管理实体
 */
public class AppSysManage implements Serializable{
	private String id;//资源ID
	private String code;//系统编码
	private String name;//系统名称
	private int type;//接入的接口类型
	private int domain;//数据分域
	private String adminName;//应用管理员名称
	private String adminPhone;//应用管理员电话
	private String adminEmail;//应用管理员邮箱
	private int status;//服务状态
	private String deleted;//删除状态位
	private String remark;//备注
	private Date createDate;//创建时间
	private String createUserId;//创建人ID	
	private String createUserName;//创建人
	private Date updateDate ;//修改时间
	private String updateUserId;//修改人ID
	private String updateUserName;//修改人
	private int showOrder;//排序
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDomain() {
		return domain;
	}
	public void setDomain(int domain) {
		this.domain = domain;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminPhone() {
		return adminPhone;
	}
	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	
	
}
