package com.cdc.dc.rules.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;
/**
 * 制度分页
 * @author ZENGKAI
 * @date 2016-04-07 09:58:29
 */
public class RulesForm extends PageQueryForm{
	private String rulesId;//ID,主键
    private String rulesCode;//制度编号:“NFJD”+部门简拼+三位流水
    private String rulesName;//制度名称
    /**
     * 基地级/跨部门级/部门级
     */
    private String rulesGrade;//制度等级,系统配置
    private String rulesTypeId;//制度分类ID,管理制度分类表
    private String leadDepId;//牵头部门ID
    private Date createTime;//创建时间
    private String createUserid;//创建人ID
    private String createUsername;//创建人名称
    /**
     * 1.草稿；2.已提交审核（审核中）；3.已发布；4.已废止；5.已修订；
     */
    private String status;//状态
    
    private Date handelBeginDate;//处理开始时间
    private Date handelEndDate;//处理结束时间
    
    /**
     * 1.审核超时；3.更新超时
     */
    private String remindStatus;//提醒类型
    
    private String abstractName;//文档名称
    private String types;//文档类型
    private String busTypes;//业务类型
    
    private String subBoxValue;//需要导出的制度id
    private String source;//页面来源
    
	public String getRulesId() {
		return rulesId;
	}
	public void setRulesId(String rulesId) {
		this.rulesId = rulesId;
	}
	public String getRulesCode() {
		return rulesCode;
	}
	public void setRulesCode(String rulesCode) {
		this.rulesCode = rulesCode;
	}
	public String getRulesName() {
		return rulesName;
	}
	public void setRulesName(String rulesName) {
		this.rulesName = rulesName;
	}
	public String getRulesGrade() {
		return rulesGrade;
	}
	public void setRulesGrade(String rulesGrade) {
		this.rulesGrade = rulesGrade;
	}
	public String getRulesTypeId() {
		return rulesTypeId;
	}
	public void setRulesTypeId(String rulesTypeId) {
		this.rulesTypeId = rulesTypeId;
	}
	public String getLeadDepId() {
		return leadDepId;
	}
	public void setLeadDepId(String leadDepId) {
		this.leadDepId = leadDepId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getHandelBeginDate() {
		return handelBeginDate;
	}
	public void setHandelBeginDate(Date handelBeginDate) {
		this.handelBeginDate = handelBeginDate;
	}
	public Date getHandelEndDate() {
		return handelEndDate;
	}
	public void setHandelEndDate(Date handelEndDate) {
		this.handelEndDate = handelEndDate;
	}
	public String getRemindStatus() {
		return remindStatus;
	}
	public void setRemindStatus(String remindStatus) {
		this.remindStatus = remindStatus;
	}
	public String getAbstractName() {
		return abstractName;
	}
	public void setAbstractName(String abstractName) {
		this.abstractName = abstractName;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getBusTypes() {
		return busTypes;
	}
	public void setBusTypes(String busTypes) {
		this.busTypes = busTypes;
	}
	public String getSubBoxValue() {
		return subBoxValue;
	}
	public void setSubBoxValue(String subBoxValue) {
		this.subBoxValue = subBoxValue;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
    
}
