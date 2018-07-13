package org.trustel.exception;

public class NoPrivilegeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1798372892379426574L;

	public NoPrivilegeException() {
		super("您没有使用本业务功能的权限！如确实需要本业务功能，请与系统管理员联系!");
	}
}
