package model.sys.entity;

public class TempProgress {
	/**
	 * 需求工单ID
	 */
	private String workorderId;
	/**
	 * 需求名称
	 */
	private String name;
	/**
	 * 总体进度状态
	 */
	private String progressStatus;
	/**
	 * 所属项目
	 */
	private String projectId;
	/**
	 * 项目版本
	 */
	private String versionId;
	/**
	 * 版本编码
	 * */
	private String versionCode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProgressStatus() {
		return progressStatus;
	}
	public void setProgressStatus(String progressStatus) {
		this.progressStatus = progressStatus;
	}
	public String getWorkorderId() {
		return workorderId;
	}
	public void setWorkorderId(String workorderId) {
		this.workorderId = workorderId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	
	
}
