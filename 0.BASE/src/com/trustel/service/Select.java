package com.trustel.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 *
 * 嵌套的select语句
 */
public class Select implements Serializable {
	/**
	 * 查询输出，例如："select a from Object"
	 */
	public String output;

	/**
	 * 查询条件
	 */
	public List conditions;

	/**
	 * group by语句
	 */
	public String groupBy;

	/**
	 * order by 语句
	 */
	public String orderBy;

	private Select() {

	}

	public Select(String output, List conditions, String groupBy, String orderBy) {
		this.output = output;
		this.conditions = conditions;
		this.groupBy = groupBy;
		this.orderBy = orderBy;
	}

	/* 
	 * 生成查询语句
	 */
	public String toString() {
		return output + " " + Condition.toString(conditions, true)
				+ (groupBy == null ? "" : " " + groupBy)
				+ (orderBy == null ? "" : " " + orderBy);
	}
}
