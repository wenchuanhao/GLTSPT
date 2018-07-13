package com.cdc.dc.cooperation.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.trustel.service.IEnterpriseService;

import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.system.core.util.SpringHelper;

/**
 * 数据录入导入接口记录表
 * @author ZENGKAI
 * @date 2016-05-24 09:58:29
 */


public class CooperationDatasourceRecords implements java.io.Serializable {

	// Fields
	/**
	 * ID
	 */
	private String recordId;
	/**
	 *  * 数据源类型ID
	 */
	private String datasourceId;
	/**
	 *  * 导入文件ID
	 */
	private String fileId;
	/**
	 * 月份
	 */
	private String month;
	/**
	 *  * 创建时间
	 */
	private Date createTime;
	/**
	 *  * 创建人ID
	 */
	private String createUserid;
	/**
	 *  * 创建人名称
	 */
	private String createUsername;
	/**
	 *  * 审核时间
	 */
	private Date checkTime;
	/**
	 *  * 审核人ID
	 */
	private String checkUserid;
	/**
	 *  * 审核人名称
	 */
	private String checkUsername;
	/**
	 *  * 审核意见
	 */
	private String checkOpinion;
	/**
	 *  * 删除时间
	 */
	private Date delTime;
	/**
	 *  * 删除人ID
	 */
	private String delUserid;
	/**
	 *  * 删除人名称
	 */
	private String delUsername;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新人ID
	 */
	private String updateUserid;
	/**
	 * 更新人名称
	 */
	private String updateUsername;
	/**
	 *  * 同步月份
	 */
	private String syncTime;
	/**
	 *  * 同步状态
	 */
	private String syncStatus;
	/**
	 *  * 状态
	 */
	private String status;

	// Constructors

	/** default constructor */
	public CooperationDatasourceRecords() {
	}

	/** minimal constructor */
	public CooperationDatasourceRecords(String recordId) {
		this.recordId = recordId;
	}

	/** full constructor */
	public CooperationDatasourceRecords(String recordId, String datasourceId,
			String fileId, String month, Date createTime, String createUserid,
			String createUsername, Date checkTime, String checkUserid,
			String checkUsername, String checkOpinion, Date delTime,
			String delUserid, String delUsername, Date updateTime,
			String updateUserid, String updateUsername, String syncTime,
			String syncStatus, String status) {
		this.recordId = recordId;
		this.datasourceId = datasourceId;
		this.fileId = fileId;
		this.month = month;
		this.createTime = createTime;
		this.createUserid = createUserid;
		this.createUsername = createUsername;
		this.checkTime = checkTime;
		this.checkUserid = checkUserid;
		this.checkUsername = checkUsername;
		this.checkOpinion = checkOpinion;
		this.delTime = delTime;
		this.delUserid = delUserid;
		this.delUsername = delUsername;
		this.updateTime = updateTime;
		this.updateUserid = updateUserid;
		this.updateUsername = updateUsername;
		this.syncTime = syncTime;
		this.syncStatus = syncStatus;
		this.status = status;
	}

	// Property accessors

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public RulesFileUpload getFileInfo(){
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		if(StringUtils.isNotEmpty(this.fileId)){
			return (RulesFileUpload) enterpriseService.queryEntity(RulesFileUpload.class,this.fileId);
		}
		return null;
	}
	
	public String getDatasourceId() {
		return this.datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserid() {
		return this.createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getCreateUsername() {
		return this.createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public Date getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckUserid() {
		return this.checkUserid;
	}

	public void setCheckUserid(String checkUserid) {
		this.checkUserid = checkUserid;
	}

	public String getCheckUsername() {
		return this.checkUsername;
	}

	public void setCheckUsername(String checkUsername) {
		this.checkUsername = checkUsername;
	}

	public String getCheckOpinion() {
		return this.checkOpinion;
	}

	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public String getDelUserid() {
		return this.delUserid;
	}

	public void setDelUserid(String delUserid) {
		this.delUserid = delUserid;
	}

	public String getDelUsername() {
		return this.delUsername;
	}

	public void setDelUsername(String delUsername) {
		this.delUsername = delUsername;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserid() {
		return this.updateUserid;
	}

	public void setUpdateUserid(String updateUserid) {
		this.updateUserid = updateUserid;
	}

	public String getUpdateUsername() {
		return this.updateUsername;
	}

	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}

	public String getSyncTime() {
		return this.syncTime;
	}

	public void setSyncTime(String syncTime) {
		this.syncTime = syncTime;
	}

	public String getSyncStatus() {
		return this.syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}