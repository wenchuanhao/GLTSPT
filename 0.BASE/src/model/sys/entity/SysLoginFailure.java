package model.sys.entity;

import java.util.Date;

/**
 * 登录错误时间
 * @author liwj
 */
public class SysLoginFailure implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 登录错误日志ID
	 */
	private String LoginFailureId;
	/**
	 * 用户登录账号
	 */
	private String account;
	/**
	 * 登录错误时间
	 */
	private Date loginTime;
	
	public SysLoginFailure(){};
	
	public SysLoginFailure(String account, Date loginTime){
		this.account = account;
		this.loginTime = loginTime;
	}

	public String getLoginFailureId() {
		return LoginFailureId;
	}

	public void setLoginFailureId(String loginFailureId) {
		LoginFailureId = loginFailureId;
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