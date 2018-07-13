package com.cdc.sys.service;


import java.util.List;

import model.sys.entity.MyCollect;
public interface IMyCollectService {

	/**
	 * 添加收藏 
	 * @param collect
	 * @throws RuntimeException
	 */
	public void addCollect(MyCollect collect) throws RuntimeException;
	
	/**
	 * 查询我的收藏
	 * @return
	 * @throws RuntimeException
	 */
	public List<MyCollect> getCollect(String userId)throws RuntimeException;
	
	/**
	 * 根据当前登录帐号 查询是否已经被收藏
	 * @param userId
	 * @return
	 * @throws RuntimeException
	 */
	public List<MyCollect> getIsExsitCollect(String userId)throws RuntimeException;
	
	/**
	 * 根据当前登录帐号、查询名称 查询是否已经被收藏
	 * @param userId
	 * @return
	 * @throws RuntimeException
	 */
	public List<MyCollect> getIsExsitCollectName(String userId,String collectName,String collectType)throws RuntimeException;
	
	
	
	/**
	 * 取消收藏
	 * @param collectId
	 * @throws RuntimeException
	 */
	public void canelCollect(String collectId)throws RuntimeException;
	
	
	/**
	 * 修改收藏
	 * @param collectId
	 * @throws RuntimeException
	 */
	public void updateCollect(String collectName)throws RuntimeException;

	public List<Object[]> getAdvertisement();
	
	
	
	
	
	

}
