package org.trustel.system;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemLogger {

	private static Log logger = LogFactory.getLog("Framework");

	public static void debug(Object arg0, Throwable arg1) {
		logger.debug(arg0, arg1);
	}

	public static void debug(Object arg0) {
		logger.debug(arg0);
	}

	public static void error(Object arg0, Throwable arg1) {
		logger.error(arg0, arg1);
	}

	public static void error(Object arg0) {
		logger.error(arg0);
	}

	public static void fatal(Object arg0, Throwable arg1) {
		logger.fatal(arg0, arg1);
	}

	public static void fatal(Object arg0) {
		logger.fatal(arg0);
	}

	public static void info(Object arg0, Throwable arg1) {
		logger.info(arg0, arg1);
	}

	public static void info(Object arg0) {
		logger.info(arg0);
	}

	public static boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public static boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public static boolean isFatalEnabled() {
		return logger.isFatalEnabled();
	}

	public static boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public static boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public static boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public static void trace(Object arg0, Throwable arg1) {
		logger.trace(arg0, arg1);
	}

	public static void trace(Object arg0) {
		logger.trace(arg0);
	}

	public static void warn(Object arg0, Throwable arg1) {
		logger.warn(arg0, arg1);
	}

	public static void warn(Object arg0) {
		logger.warn(arg0);
	}

}
