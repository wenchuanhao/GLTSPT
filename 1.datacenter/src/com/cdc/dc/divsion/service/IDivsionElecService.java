package com.cdc.dc.divsion.service;

import java.util.List;

public interface IDivsionElecService {

	
	/**
	 * 根据sql查询图表数据
	 * @param sql
	 * @return
	 */
	List<Object[]> getSeriesDataByType(String sql);
	
	/**
	 * 保存实体
	 * @param item
	 */
	public void saveEntity(Object item);

	/**
	 * 批量保存实体
	 * @param resultList
	 */
	void save(List<?> entityList);
}
