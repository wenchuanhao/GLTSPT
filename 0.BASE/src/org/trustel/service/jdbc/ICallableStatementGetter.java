package org.trustel.service.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.trustel.service.common.IORTransform;

public interface ICallableStatementGetter extends IORTransform {
	/**
	 * 获取存储过程的返回值
	 * 
	 * @param cs
	 *            存储过程
	 * @throws SQLException
	 */
	public void readOutParameter(CallableStatement cs) throws SQLException;
}
