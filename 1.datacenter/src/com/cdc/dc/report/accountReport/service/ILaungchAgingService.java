package com.cdc.dc.report.accountReport.service;

import java.util.List;

import com.cdc.dc.account.model.AccountImportInfo;

/**
 * 报账经分
 * @author xms
 *
 */
public interface ILaungchAgingService {
	
	/**
	 * 查询各个环节时效
	 * @return
	 */
	public List queryLinkAging(String linkName,String startTime,String endTime,String type);
	
	/**
	 * 获取三级页面的时效数据
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param link 环节
	 * @param dimensionKey 维度名
	 * @param dimensionValue 维度值
	 * @param compareType 同环比
	 * @return
	 */
	public List<Object[]> getAgingData(String startDate, String endDate, 
			String link, String dimensionKey, String dimensionValue, int compareType);
	
	/**
	 * 查询某个环节同比、环比时效
	 * @return
	 */
	public List<Object[]> queryTHbi(String linkName,String startTime,String endTime,String type,int compareType,int num);

	/**
	 * 获取三级页面的问题单据占比数据
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param link 环节
	 * @param dimensionKey 维度名
	 * @param dimensionValue 维度值
	 * @param compareType 同环比
	 * @return
	 */
	public List<Object[]> getProblemOrderData(String startDate, String endDate,
			String link, String dimensionKey, String dimensionValue, int compareType);
	
	/**
	 * 根据部门名称获取部门ID
	 * @param depNmae
	 * @return
	 */
	String getDepIdByName(String depNmae);
	
	/**
	 * 根据费用类型名称获取费用类型ID
	 * @param costName
	 * @return
	 */
	List<Object[]> getCostIdByName(String costName); 
	
	/**
	 * 根据问题名称获取问题ID
	 * @param proName
	 * @return
	 */
	String getProTypeByName(String proName);

	/**
	 * 报账经分导出
	 * @param startDate
	 * @param string
	 * @return
	 */
	public List<AccountImportInfo> queryFlowInfoByTime(String startDate, String endDate);
}
