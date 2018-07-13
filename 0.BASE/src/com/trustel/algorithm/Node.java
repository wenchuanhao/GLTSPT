package com.trustel.algorithm;

/**
 * @author Administrator
 *
 * 节点
 */
public interface Node {
	/**
	 * 取节点ID
	 * 
	 * @return
	 */
	public String getId();
	/**
	 * 取父节点ID
	 * 
	 * @return
	 */
	public String getParentId();
	
	/**
	 * 取节点自身统计值
	 * 
	 * @return
	 */
	public Summary getSumary();
}
