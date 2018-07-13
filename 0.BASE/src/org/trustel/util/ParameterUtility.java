package org.trustel.util;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 当参数支持多语言时，请使用参数名+语言编码作为getXX(name)中的name参数
 * 
 * @author Micky
 * 
 */
public class ParameterUtility {

	private final SimpleHashMap instance = new SimpleHashMap();

	public ParameterUtility() {
	}

	public void clear() {
		instance.clear();
	}

	public boolean containsKey(Object key) {
		return instance.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return instance.containsValue(value);
	}

	public boolean exist(String name) {
		return instance.exist(name);
	}

	public Object get(String name) {
		return instance.get(name);
	}

	public boolean getBoolean(String name, boolean defaultvalue) {
		return instance.getBoolean(name, defaultvalue);
	}

	public boolean getBoolean(String name) {
		return instance.getBoolean(name);
	}

	public Date getDate(String name, String format, String zone) {
		return instance.getDate(name, format, zone);
	}

	public Date getDate(String name, String format) {
		return instance.getDate(name, format);
	}

	public Date getDate(String name) {
		return instance.getDate(name);
	}

	public double getDouble(String name, double defaultValue) {
		return instance.getDouble(name, defaultValue);
	}

	public float getFloat(String name, float defaultValue) {
		return instance.getFloat(name, defaultValue);
	}

	public float getFloat(String name) {
		return instance.getFloat(name);
	}

	public int getInt(String name, int defaultValue) {
		return instance.getInt(name, defaultValue);
	}

	public int getInt(String name) {
		return instance.getInt(name);
	}

	public long getLong(String name, long defaultValue) {
		return instance.getLong(name, defaultValue);
	}

	public long getLong(String name) {
		return instance.getLong(name);
	}

	public String getString(String name, String defaultValue) {
		return instance.getString(name, defaultValue);
	}

	public String getString(String name) {
		return instance.getString(name);
	}

	public boolean isEmpty() {
		return instance.isEmpty();
	}

	public Set<String> keySet() {
		return instance.keySet();
	}

	public void put(String name, Object value, boolean replace) {
		instance.put(name, value, replace);
	}

	public void put(String name, Object value) {
		instance.put(name, value);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		instance.putAll(m);
	}

	public void remove(String name) {
		instance.remove(name);
	}

	public int size() {
		return instance.size();
	}

}
