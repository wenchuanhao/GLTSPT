package org.trustel.exception;

public class JDBCException extends RuntimeException {

	private static final long serialVersionUID = 941901160150244180L;

	/**
	 * 返回错误信息
	 */
	public String toString() {
		return getCause().getClass().getCanonicalName() + ":" + getMessage();
	}
}
