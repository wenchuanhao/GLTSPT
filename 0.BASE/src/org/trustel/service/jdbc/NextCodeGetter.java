package org.trustel.service.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.trustel.util.SimpleHashMap;


public class NextCodeGetter implements ICallableStatementGetter {

	private long nextCode;

	public NextCodeGetter(long nextCode) {
		this.nextCode = nextCode;
	}

	public void readOutParameter(CallableStatement cs) throws SQLException {
		nextCode = cs.getLong(1);
	}

	public long getNextCode() {
		return nextCode;
	}

	public Object transform(long index, SimpleHashMap fields) {
		return null;
	}

}
