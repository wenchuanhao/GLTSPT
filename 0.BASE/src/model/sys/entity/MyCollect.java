package model.sys.entity;

import java.util.Date;

/**
 * 工作台 我的收藏
 * @author Administrator
 *
 */
public class MyCollect implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String collectId;
	private String userId;
	private String collectName;
	private String collectType;
	private String collectStatus;
	private String collectUrl;
	private Date collectTime;

	public MyCollect() {
		super();
	}
	
	
	public MyCollect(String collectId, String userId, String collectName,
			String collectType, String collectStatus, String collectUrl,
			Date collectTime) {
		super();
		this.collectId = collectId;
		this.userId = userId;
		this.collectName = collectName;
		this.collectType = collectType;
		this.collectStatus = collectStatus;
		this.collectUrl = collectUrl;
		this.collectTime = collectTime;
	}


	public String getCollectId() {
		return collectId;
	}
	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCollectName() {
		return collectName;
	}
	public void setCollectName(String collectName) {
		this.collectName = collectName;
	}
	public String getCollectType() {
		return collectType;
	}
	public void setCollectType(String collectType) {
		this.collectType = collectType;
	}
	public String getCollectStatus() {
		return collectStatus;
	}
	public void setCollectStatus(String collectStatus) {
		this.collectStatus = collectStatus;
	}
	public String getCollectUrl() {
		return collectUrl;
	}
	public void setCollectUrl(String collectUrl) {
		this.collectUrl = collectUrl;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
}