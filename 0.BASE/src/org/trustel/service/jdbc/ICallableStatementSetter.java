package org.trustel.service.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;

public interface ICallableStatementSetter {
	/**
	 * 存储过程参数设置
	 * 
	 * @param ps
	 * @param index
	 * 
	 * Sample:
	 * 
	 */
	public void setParameterValues(CallableStatement cs) throws SQLException;

}
