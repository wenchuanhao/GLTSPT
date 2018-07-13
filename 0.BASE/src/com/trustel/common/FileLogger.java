/*
 * 创建日期 2005-6-3
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.trustel.common;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Administrator
 * 
 * 文件日志写入器
 */
public class FileLogger {
	/**
	 * 无
	 */
	public final static int NONE = 0;

	/**
	 * 
	 */
	public final static int FINEST = 1;

	/**
	 * 
	 */
	public final static int FINER = 2;

	/**
	 * 调试
	 */
	public final static int FINE = 3;

	/**
	 * 配置
	 */
	public final static int CONFIG = 4;

	/**
	 * 提醒
	 */
	public final static int INFO = 5;

	/**
	 * 警告
	 */
	public final static int WARNING = 6;

	/**
	 * 严重
	 */
	public final static int SEVERE = 7;

	public final static String DEFAULT_PATTERN = "-%g.log";

	public final static int DEFAULT_LIMIT = 2 * 1024 * 1024;

	public final static int DEFAULT_COUNT = 10;

	private static FileLogger singleton = null;

	private static Logger logger;

	/**
	 * 创建/取日志实例
	 * 
	 * @param pattern
	 *            日志文件格式
	 * @param limit
	 *            文件大小
	 * @param count
	 *            文件数
	 * @return
	 * @throws java.io.IOException
	 */
	public static FileLogger singleton(String pattern, int limit, int count)
			throws RuntimeException {
		if (singleton == null) {
			try {
				FileHandler handler = new FileHandler(
						pattern + DEFAULT_PATTERN, limit, count, true);
				handler.setFormatter(new SimpleFormatter());
				logger = Logger.getLogger("FileLogger");
				logger.setLevel(Level.ALL);
				logger.addHandler(handler);
				singleton = new FileLogger();
			} catch (Exception e) {
				throw new RuntimeException("log file initialize failed: "
						+ e.getMessage());
			}
		}

		return singleton;
	}

	/**
	 * 创建/取日志实例
	 * 
	 * @param pattern
	 *            日志文件格式
	 * @param limit
	 *            文件大小
	 * @param count
	 *            文件数
	 * @return
	 * @throws java.io.IOException
	 */
	public static FileLogger singleton(String pattern, int count)
			throws RuntimeException {
		if (singleton == null) {
			try {
				FileHandler handler = new FileHandler(
						pattern + DEFAULT_PATTERN, DEFAULT_LIMIT, count, true);
				handler.setFormatter(new SimpleFormatter());
				logger = Logger.getLogger("FileLogger");
				logger.setLevel(Level.ALL);
				logger.addHandler(handler);
				singleton = new FileLogger();
			} catch (Exception e) {
				throw new RuntimeException("log file initialize failed: "
						+ e.getMessage());
			}
		}

		return singleton;
	}

	/**
	 * 创建日志写入器实例
	 * 
	 * @return
	 */
	public static FileLogger singleton() throws RuntimeException {
		if (singleton == null) {
			singleton(DEFAULT_PATTERN, DEFAULT_LIMIT, DEFAULT_COUNT);
		}

		return singleton;
	}

	private FileLogger() {
	}

	/**
	 * 取级别
	 * 
	 * @param type
	 * @return
	 */
	private Level getLevel(int type) {
		Level level = Level.FINEST;

		switch (type) {
		case NONE:
			level = Level.OFF;
			break;

		case FINEST:
			level = Level.FINEST;
			break;

		case FINER:
			level = Level.FINER;
			break;

		case FINE:
			level = Level.FINE;
			break;

		case CONFIG:
			level = Level.CONFIG;
			break;

		case INFO:
			level = Level.INFO;
			break;

		case WARNING:
			level = Level.WARNING;
			break;

		case SEVERE:
			level = Level.SEVERE;
			break;

		default:
			break;
		}

		return level;
	}

	/**
	 * 设置日志记录级别
	 * 
	 * @param level
	 *            级别
	 */
	public void setLevel(int level) {
		logger.setLevel(getLevel(level));
	}

	/**
	 * 写日志(日志级别为INFO)
	 * 
	 * @param message
	 *            日志内容
	 */
	public synchronized void log(String message) {
		log(INFO, message);
	}

	/**
	 * 写日志
	 * 
	 * @param level
	 *            日志级别
	 * @param message
	 *            日志内容
	 */
	public synchronized void log(int level, String message) {
		switch (level) {
		case FINEST:
			logger.finest(message);
			break;

		case FINER:
			logger.finer(message);
			break;

		case FINE:
			logger.fine(message);
			break;

		case CONFIG:
			logger.config(message);
			break;

		case INFO:
			logger.info(message);
			break;

		case WARNING:
			logger.warning(message);
			break;

		case SEVERE:
			logger.severe(message);
			break;

		default:
			break;
		}
	}

	/**
	 * 写日志
	 * 
	 * @param level
	 *            级别
	 * @param className
	 *            类名
	 * @param methodName
	 *            方法名
	 * @param message
	 *            日志内容
	 */
	public synchronized void log(int level, String className,
			String methodName, String message) {
		Level le = getLevel(level);
		logger.logp(le, className, methodName, message);
	}

	/**
	 * 写日志
	 * 
	 * @param level
	 *            级别
	 * @param className
	 *            类名
	 * @param methodName
	 *            方法名
	 * @param message
	 *            日志内容
	 * @param parameters
	 *            内容参数
	 */
	public synchronized void log(int level, String className,
			String methodName, String message, Object[] parameters) {
		Level le = getLevel(level);
		logger.logp(le, className, methodName, message, parameters);
	}
}
