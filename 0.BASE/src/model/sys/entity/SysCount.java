package model.sys.entity;

import java.util.Date;

public class SysCount implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String countId;
	private String type;
	private String count;
	private String formatdate;
	
	
	public SysCount() {
	}

	public SysCount(String countId, String type, String count,String formatdate) {
		this.countId = countId;
		this.type = type;
		this.count = count;
		this.formatdate = formatdate;
	}
	
	public SysCount(String type, String count,String formatdate) {
		this.type = type;
		this.count = count;
		this.formatdate = formatdate;
	}
	public String getCountId() {
		return countId;
	}
	public void setCountId(String countId) {
		this.countId = countId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getFormatdate() {
		return formatdate;
	}
	public void setFormatdate(String formatdate) {
		this.formatdate = formatdate;
	}
	


	

}