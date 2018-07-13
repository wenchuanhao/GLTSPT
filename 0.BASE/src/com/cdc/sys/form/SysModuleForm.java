package com.cdc.sys.form;

import org.trustel.service.form.PageQueryForm;

/**
 * SysModule entity. @author MyEclipse Persistence Tools
 */

public class SysModuleForm  extends PageQueryForm implements java.io.Serializable {

	// Fields
	/**
	 * 父模块ID
	 */
	private String parentCode;
	/**
	 * 状态 1显示为菜单 0 不显示
	 */
	private String status;
	/**
	 * 模块代码 (最好用类名)
	 */
	private String code;
	private String moduleId;
	private String moduleCode;
	private String moduleName;
	private String buttonName;
	private String parentId;
	private String operationType;
	private String isMenu;
	private String remark;
	private String createId;
	private String createName;

	// Constructors

	/** default constructor */
	public SysModuleForm() {
	}

	/** full constructor */
	public SysModuleForm(String moduleCode, String moduleName, String buttonName, String parentId, String operationType, String isMenu, String remark, String createId, String createName) {
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
		this.buttonName = buttonName;
		this.parentId = parentId;
		this.operationType = operationType;
		this.isMenu = isMenu;
		this.remark = remark;
		this.createId = createId;
		this.createName = createName;
	}

	// Property accessors

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleCode() {
		return this.moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getButtonName() {
		return this.buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getIsMenu() {
		return this.isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}