package org.trustel.service;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -5509387554019360741L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
