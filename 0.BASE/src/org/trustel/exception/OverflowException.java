package org.trustel.exception;

public class OverflowException extends RuntimeException {

	private static final long serialVersionUID = -6188886204144079719L;

	public OverflowException() {
		super("您需要查询的数据已经超出服务限制范围，请调整查询条件，缩小查询范围!");

	}

	public OverflowException(String msg) {
		super(msg);
	}

}
