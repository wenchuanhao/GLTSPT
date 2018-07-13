package model.sys.entity;

import java.util.Date;

/**
 * 获取验证码错误信息
 * @author weitw
 * 2014-4-28
 */
public class SysValidCodeFailuer implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 输入验证码错误日志ID
	 */
	private String validCodeFailuerId;
	
	/**
	 * 用户登录账号
	 */
	private String account;
	/**
	 * 登录错误时间
	 */
	private Date loginTime;
	
	public SysValidCodeFailuer() {
		
	}

	public String getValidCodeFailuerId() {
		return validCodeFailuerId;
	}

	public void setValidCodeFailuerId(String validCodeFailuerId) {
		this.validCodeFailuerId = validCodeFailuerId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	

}
