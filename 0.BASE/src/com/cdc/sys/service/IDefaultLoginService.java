package com.cdc.sys.service;

import model.sys.entity.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统登录
 * 
 * @author sunsf
 * 
 */
public interface IDefaultLoginService {

	/**
	 * 用户登录
	 * 
	 * @param account
	 *            帐号
	 * @param pwd
	 *            密码
	 * @return
	 */
	public SysUser checkLogin(String account, String pwd) throws Exception;
	/**
	 * 根据登录帐号查询系统用户
	 * 
	 * @param account 帐号
	 *            
	 * @return
	 */
	public SysUser checkUserByAccount(String account)throws Exception;

    Map<String,List<String>> loadUserPrivilges(String userId) throws Exception;

    /**
	 * 清除用户的登录信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public SysUser cleanSession(HttpServletRequest request) throws Exception;
	
	/**
	 * 检查当天登录错误次数
	 */
	public boolean checkLoginFailure(HttpServletRequest request, String account) throws Exception;

    boolean checkLastUpdatePassword(HttpServletRequest request, String account);

    /**
	 * 保存系统登录错误日志
	 */
	public void saveSysLoginFailure(HttpServletRequest request, String account) throws Exception;

    /**
	 * 更新用户冻结状态
	 */
	public void updateFreezeStatus(String account) throws Exception;
	
	/**
	 * 查询今天登陆错误次数
	 */
	public int querySysLoginFailureTimes(String account);
	
	/**
	 * 清除登录错误记录
	 */
	public void clearLoginFailure(String account) throws Exception;
	/**
	 * 检查当天获取验证码错误次数
	 * @param account
	 * @return
	 */
	public boolean checkGetCodeTimes(String account);
	/**
	 * 保存获取验证码信息
	 * @param account
	 */
	public void saveSysGetCodeTimes(String account);
	/**
	 * 查询今天获取验证码次数
	 * @param account
	 * @return
	 */
	public int queryGetCodeTimes(String account);

    long neverUseBetweenTime(String account, Date startDate, Date endDate,String moduleNote);

    /**
     * 重置登录失败计数器
     * @param userId
     */
    void resetLoginFailCounter(String userId);

    void finalLoginFailCounter(String userId);

    void updateUserFreeStatus(String userId, String freezeStatus);
}
