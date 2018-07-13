package org.trustel.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.trustel.common.Utils;

public class SimpleHashMap {

	private HashMap<String, Object> map = new HashMap<String, Object>();

	private List<String> fields = new ArrayList<String>();

	public SimpleHashMap() {
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public boolean exist(String name) {
		try {
			return map.containsKey(name.toLowerCase())
					|| map.containsKey(name.toUpperCase());

		} catch (Exception e) {
			return false;
		}
	}

	public Object get(String name) {
		try {
			return map.get(name.toLowerCase());
		} catch (Exception e) {
			return null;
		}

	}

	public boolean getBoolean(String name) {
		return getString(name).compareToIgnoreCase("1") == 0;
	}

	public boolean getBoolean(String name, boolean defaultvalue) {
		String value = getString(name);
		if (value.compareTo("") == 0)
			return defaultvalue;
		else
			return value.compareToIgnoreCase("1") == 0;
	}

	public Date getDate(String name) {
		return getDate(name, "yyyy-MM-dd");// "yyyy-MM-dd hh:mm"
	}

	public Date getDate(String name, String format) {
		return getDate(name, format, "");
	}

	public Date getDate(String name, String format, String zone) {
		Object o = get(name);
		if (o == null)
			return null;
		if (o instanceof java.sql.Date) {
			return new Date(((java.sql.Date) o).getTime());

		} else if (o instanceof Date) {
			return (Date) o;
		}
		String value = getString(name, "");
		return Utils.valueOf(value, format, zone);

	}

	public double getDouble(String name, double defaultValue) {
		try {

			return Double.valueOf(getString(name.toLowerCase()));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public float getFloat(String name) {
		return getFloat(name, 0);
	}

	public float getFloat(String name, float defaultValue) {
		try {
			return Float.parseFloat(get(name.toLowerCase()).toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		try {
			return Integer.parseInt(get(name.toLowerCase()).toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public long getLong(String name) {
		return getLong(name, 0);
	}

	public long getLong(String name, long defaultValue) {
		try {
			return Long.parseLong(get(name.toLowerCase()).toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getString(String name) {
		return getString(name, "");
	}

	/**   
	 */
	public String getString(String name, String defaultValue) {
		try {
			String keyValue = (String) get(name.toLowerCase());
			if (keyValue == null)
				keyValue = defaultValue;
			else
				keyValue = keyValue.trim();

			return keyValue.trim();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public void put(String name, Object value) {
		put(name.toLowerCase().trim(), value, false);
	}

	public void put(String name, Object value, boolean replace) {
		name = name.toLowerCase().trim();
		if (exist(name)) {
			if (replace)
				map.remove(name);
			else
				return;
		}
		map.put(name, value);
		fields.add(name);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		map.putAll(m);
		fields.clear();
	}

	public void remove(String name) {
		map.remove(name.toLowerCase());
	}

	public int size() {
		return map.size();
	}

	public Object[] toArray() {
		return getValues().toArray();
	}

	public Object[] toArrayByIndex() {
		Object[] ret = new Object[fields.size()];
		for (int i = 0; i < fields.size(); i++) {
			ret[i] = get(fields.get(i));
		}
		return ret;

	}

	public Collection<?> getValues() {
		return map.values();
	}

}
