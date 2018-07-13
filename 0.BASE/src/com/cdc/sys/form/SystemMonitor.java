package com.cdc.sys.form;
/**
 * 系统信息监控（内存、硬盘、CPU等）
 * @author xms
 *
 */
public class SystemMonitor {
	/**
	 * CPU使用率
	 */
	private String cpuUsed;
	/**
	 * 内存总量
	 */
	private String menSum;
	/**
	 * 内存使用量
	 */
	private String menUsed;
	/**
	 * 内存剩余量
	 */
	private String menFree;
	/**
	 * 内存使用百分比
	 */
	private String menUsePercent;
	/**
	 * 硬盘总量
	 */
	private String diskSum;
	/**
	 * 硬盘剩余量 
	 */
	private String diskFree;
	/**
	 * 硬盘使用量
	 * 
	 */
	private String diskUsePercent;
	/**
	 * 硬盘使用百分比
	 * 
	 */
	private String diskUsed;
	public String getMenUsePercent() {
		return menUsePercent;
	}
	public void setMenUsePercent(String menUsePercent) {
		this.menUsePercent = menUsePercent;
	}
	public String getDiskUsePercent() {
		return diskUsePercent;
	}
	public void setDiskUsePercent(String diskUsePercent) {
		this.diskUsePercent = diskUsePercent;
	}
	/**
	 * 盘符
	 * 
	 */
	private String dir;
	/**
	 * 
	 * 操作系统信息
	 */
	private String operaSystem;
	/**
	 * 系统版本号
	 * 
	 */
	private String version;
	
	public String getOperaSystem() {
		return operaSystem;
	}
	public void setOperaSystem(String operaSystem) {
		this.operaSystem = operaSystem;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getDiskUsed() {
		return diskUsed;
	}
	public void setDiskUsed(String diskUsed) {
		this.diskUsed = diskUsed;
	}
	public String getCpuUsed() {
		return cpuUsed;
	}
	public void setCpuUsed(String cpuUsed) {
		this.cpuUsed = cpuUsed;
	}
	public String getMenUsed() {
		return menUsed;
	}
	public void setMenUsed(String menUsed) {
		this.menUsed = menUsed;
	}
	public String getMenSum() {
		return menSum;
	}
	public void setMenSum(String menSum) {
		this.menSum = menSum;
	}
	public String getMenFree() {
		return menFree;
	}
	public void setMenFree(String menFree) {
		this.menFree = menFree;
	}
	public String getDiskSum() {
		return diskSum;
	}
	public void setDiskSum(String diskSum) {
		this.diskSum = diskSum;
	}
	public String getDiskFree() {
		return diskFree;
	}
	public void setDiskFree(String diskFree) {
		this.diskFree = diskFree;
	}
}
