package com.cdc.sys.service;

import java.util.List;

import model.sys.entity.SysUserArea;

public interface ISysUserAreaService {
	
	public void addSysUserArea(SysUserArea	model) throws Exception;
	
	public void delSysUserArea(String userId) throws Exception;
	
	/**
	 * 根据用户id和角色id删除用户所属的区域
	 * @param userId
	 * @param roleId
	 * @throws Exception
	 */
	public void delSysUserArea(String userId,String roleId) throws Exception;
	
	public List<SysUserArea> getSysUserAreaByUserId(String userId) throws Exception;
	
	/**
	 * 根据用户id和角色id去查看已分配的区域
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<SysUserArea> getSysUserAreaByUserId(String userId,String roleId) throws Exception;
}
