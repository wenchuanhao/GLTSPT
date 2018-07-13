package org.trustel.service.jdbc;

import java.util.List;

public class ExecuteResult {
	/**
	 * 操作是否成功
	 */
	public boolean ret = false;

	/**
	 * 影响记录条数
	 */
	public long updateCount = 0;

	public String toString() {
		return String.format("execute--> %s,affected %d rows ", new Object[] {
				ret, updateCount });
	}

	/**
	 * 当存储过程返回的数据集，目前仅支持一个数据集
	 */
	public List<?> sets;
}
