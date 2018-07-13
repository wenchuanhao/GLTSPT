package com.trustel.algorithm;

import java.util.List;

public interface ObjectComparator {
	/**
	 * 比较两个对象的主键是否相同
	 * <p>
	 * 由于是两组对象按主键混排，如果相邻的两个对象的主键不同则有可能是同组的对象或者是不同组的对象但主键不同
	 * 
	 * @param base 原始对象
	 * @param compare 比较对象
	 * @param define 对象的定义
	 * @return
	 */
	public boolean isKeyEqual(Object base, Object compare, Object define);
	
	/**
	 * 比较两个对象是否相同
	 * 
	 * @param base 原始对象
	 * @param compare 比较对象
	 * @param define 对象的定义
	 * @return
	 */
	public boolean isEqual(Object base, Object compare, Object define);
	
	/**
	 * 判断两个对象是否是同一组的对象
	 * 
	 * @param base 原始对象
	 * @param compare 比较对象
	 * @return
	 */
	public boolean fromSameGroup(Object base, Object compare);
	
	/**
	 * 生成对象文字说明
	 * 
	 * @param result 比较结果集
	 * @param source 对象
	 * @param baseline 基准标志 
	 * @param define 对象定义
	 * @return
	 */
	public void toExtra (List result, Object source, Object baseline, Object define);
	
	/**
	 * 生成比较两个对象差异的文字说明
	 * 
	 * @param result 比较结果集
	 * @param base 原始对象
	 * @param compare 比较对象
	 * @param baseline 基准标志
	 * @param define 对象定义
	 * @return
	 */
	public void toDifference(List result, Object base, Object compare, Object baseline, Object define);
	
	/**
	 * 生成记录重复的文字说明
	 * 
	 * @param result 比较结果集
	 * @param source 原始对象
	 * @param compare 比较对象
	 * @param define 对象定义
	 * @return
	 */
	public void toDuplicate(List result, Object source, Object compare, Object define);
}
