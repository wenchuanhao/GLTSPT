package org.trustel.util;

import java.util.Date;
import java.util.Set;

/**
 * 
 * @author 万志勇
 * 
 *         异步更新参数 ParameterFactory.getInstance().exchange
 * @version 1.0.0.0 2011-03-01
 * 
 *          系统参数国际化有以下情况：
 *          <OL>
 *          <LI>同一参数有不同语言的描述信息，但参数名和参数值相同</LI>
 *          <LI>同一参数随语言不同有不同的数值，而且参数描述随语言不同而不同</LI>
 *          </OL>
 *          对于第二种情况，我们应该设计成不同参数，以方便数据处理。<br />
 *          例子:
 *          ParameterFactory.getParameterUtility().get(ParameterName+
 *          Language);
 */
public final class ParameterFactory {
	private static ParameterFactory instance;
	private int index = 0;

	public static final ParameterFactory getInstance() {
		if (instance == null)
			instance = new ParameterFactory();
		return instance;
	}

	/**
	 * 
	 * @param lang
	 * @return
	 */
	public static final ParameterUtility getParameterUtility() {
		return getInstance().getCurrentUtility();
	}

	private ParameterUtility[] parameters;

	private ParameterFactory() {

		parameters = new ParameterUtility[2];
		parameters[0] = new ParameterUtility();
		parameters[1] = null;
	}

	public synchronized ParameterUtility getCurrentUtility() {
		return (ParameterUtility) parameters[index];
	}

	/**
	 * 以传入参数对象替换缓存中参数并在下一次访问参数时生效
	 * 
	 * @param utility
	 */
	public synchronized final void exchange(ParameterUtility utility) {
		int next = index == 1 ? 0 : 1;
		parameters[next] = utility;  
		instance.index = next;
	}

	public static void clear() {
		getParameterUtility().clear();
	}

	public static boolean containsKey(Object key) {
		return getParameterUtility().containsKey(key);
	}

	public static boolean containsValue(Object value) {
		return getParameterUtility().containsValue(value);
	}

	public static boolean exist(String name) {
		return getParameterUtility().exist(name);
	}

	public static Object get(String name) {
		return getParameterUtility().get(name);
	}

	public static boolean getBoolean(String name) {
		return getParameterUtility().getBoolean(name);
	}

	public static boolean getBoolean(String name, boolean defaultvalue) {
		return getParameterUtility().getBoolean(name, defaultvalue);
	}

	public static Date getDate(String name) {
		return getParameterUtility().getDate(name);
	}

	public static Date getDate(String name, String format) {
		return getParameterUtility().getDate(name, format);
	}

	public static Date getDate(String name, String format, String zone) {
		return getParameterUtility().getDate(name, format, zone);
	}

	public static double getDouble(String name, double defaultValue) {
		return getParameterUtility().getDouble(name, defaultValue);
	}

	public static float getFloat(String name) {
		return getParameterUtility().getFloat(name);
	}

	public static float getFloat(String name, float defaultValue) {
		return getParameterUtility().getFloat(name, defaultValue);
	}

	public static int getInt(String name) {
		return getParameterUtility().getInt(name);
	}

	public static int getInt(String name, int defaultValue) {
		return getParameterUtility().getInt(name, defaultValue);
	}

	public static long getLong(String name) {
		return getParameterUtility().getLong(name);
	}

	public static long getLong(String name, long defaultValue) {
		return getParameterUtility().getLong(name, defaultValue);
	}

	public static String getString(String name) {
		return getParameterUtility().getString(name);
	}

	public static String getString(String name, String defaultValue) {
		return getParameterUtility().getString(name, defaultValue);
	}

	public static boolean isEmpty() {
		return getParameterUtility().isEmpty();
	}

	public static Set<String> keySet() {
		return getParameterUtility().keySet();
	}

	public static void put(String name, Object value) {
		getParameterUtility().put(name, value);
	}

	public static void put(String name, Object value, boolean replace) {
		getParameterUtility().put(name, value, replace);
	}

	public static void remove(String name) {
		getParameterUtility().remove(name);
	}

	public static int size() {
		return getParameterUtility().size();
	}
}
