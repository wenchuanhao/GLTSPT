package com.trustel.algorithm;

import java.io.Serializable;

/**
 * @author Administrator
 *
 *  比较结果
 */
public class CompareResult implements Serializable {
	/**
	 *  多出记录
	 */
	public final static int EXTRA = 1;
	/**
	 * 记录不同
	 */
	public final static int DIFFERENT = 2;
	/**
	 * 记录重复
	 */
	public final static int DUPLICATE = 3;
	/**
	 * 原始记录
	 */
	public Object base;
	/**
	 * 结果类型
	 */
	public int type;
	/**
	 * 结果描述
	 */
	public String description;
	
	private CompareResult() {
		
	}
	
	public CompareResult(Object base, int type, String description) {
		this.base = base;
		this.type = type;
		this.description = description;
	}
}
