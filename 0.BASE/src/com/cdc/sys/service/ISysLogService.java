package com.cdc.sys.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import model.sys.entity.SysUser;

import com.cdc.sys.form.SysLogForm;
import com.trustel.common.ItemPage;

/**
 * 系统日志 记录操作
 * 
 * @author sunsf
 * 
 */
public interface ISysLogService {
	
	/**
	 * @param request
	 * @param moduleName
	 *            模块名称
	 * @param operatiorId
	 *            操作人id
	 * @param orgId
	 *            操作人组织
	 * @param functionName
	 *            方法名称
	 * @param description
	 *            描述
	 * @throws Exception
	 */
/*	public abstract void log(HttpServletRequest request, String moduleName,
			String operatiorId, String orgId, String functionName,
			String description) throws Exception;

	public void log(HttpServletRequest request, SysUser visitor, String moduleName, String functionName, String description)throws Exception;
	*/
	/**
	 * @param request
	 * @param visitor
	 * @param logModuleType 日志记录类型
	 * @param logModuleNote 日志记录内容
	 * @param logDesc 操作内容
	 * @param operateTime 操作时间
	 * @param logType 日志类型（1：登陆日志， 2：异常日志， 3：操作日志， 4：分配日志）
	 * @param logStartTime 日志开始时间
	 * @param logEndTime 日志结束时间
	 * @throws Exception
	 */
	public  void log(HttpServletRequest request, SysUser visitor,
			String logModuleType, String logModuleNote, String logDesc,
			Date operateTime,String logType ,Date logStartTime,Date logEndTime)
			throws Exception;

	/**
	 * 系统日志列表
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public ItemPage query(SysLogForm form,String logType) throws Exception;
	
	
	
	/**
	 * 系统日志，type=0 记录属于系统的操作
	 */
	public void log(String logModuleType, String logModuleNote, String logDesc,
			Date operateTime, Date logStartTime , Date logEndTime) throws Exception;
	
	
	
}
