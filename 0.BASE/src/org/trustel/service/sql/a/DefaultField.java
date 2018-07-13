package org.trustel.service.sql.a;

import java.sql.Types;
import java.util.Date;

import org.trustel.common.Utils;


public class DefaultField implements IField {

	private IColumn column;
	private Object value;

	private void init(String name, int type) {
		column = new DefaultColumn(name, type);
	}

	public DefaultField(String name, Object value, int type) {
		init(name, type);
		this.value = value;

	}

	public DefaultField(String name, int value) {
		init(name, Types.INTEGER);
		this.value = Integer.valueOf(value);
	}

	public DefaultField(String name, float value) {
		init(name, Types.FLOAT);
		this.value = new Float(value);
	}

	public DefaultField(String name, double value) {
		init(name, Types.DOUBLE);
		this.value = new Double(value);
	}

	public DefaultField(String name, Date value) {
		init(name, Types.DATE);
		this.value = value;
	}

	public DefaultField(String name, byte[] value) {
		init(name, Types.BINARY);
		this.value = value;
	}

	public DefaultField() {

	}

	public String getAlais() {
		return column.getAlais();
	}

	public IColumn getColumn() {
		return column;
	}

	public String getName() {
		return column.getName();
	}

	public int getType() {
		return column.getType();
	}

	public Object getValue() {
		return value;
	}

	public boolean toBoolean() {
		return toBoolean(false);
	}

	public boolean toBoolean(boolean defaultValue) {
		try {
			return Boolean.valueOf(value.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public boolean toBoolean(int tvalue) {
		return toInteger(tvalue + 1) == tvalue;
	}

	public boolean toBoolean(String tvalue) {
		return tvalue.equals(value);
	}

	public Date toDate() {
		return toDate("");
	}

	public Date toDate(String timezone) {
		if (value == null)
			return null;
		Date ret = null;
		if (value instanceof java.sql.Date) {
			ret = new Date(((java.sql.Date) value).getTime());
		} else if (value instanceof Date) {
			ret = (Date) value;
		} else
			return Utils.valueOf(value.toString(), "yyyy-MM-dd", timezone);
		return Utils.get(ret, timezone);
	}

	public Date toDate(String pattern, String timezone) {
		Date date = toDate(timezone);
		String v = Utils.format(date, pattern,"");
		return Utils.valueOf(v, pattern);
	}

	public double toDouble(double defaultValue) {
		try {
			return Double.valueOf(value.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public float toFloat(float defaultValue) {
		try {
			return Float.valueOf(value.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int toInteger(int defaultValue) {
		try {
			return Integer.valueOf(value.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String toString() {
		if (value == null)
			return null;
		else if (value instanceof String)
			return (String) value;
		else
			return value.toString();
	}

	public String toString(String pattern, String timezone) {
		Date date = toDate(timezone);
		return Utils.format(date, pattern,"");
	}

	public boolean isMuilt() {
		return false;
	}

	public short toShort(int defaultValue) {
		try {
			return Short.valueOf(value.toString());
		} catch (Exception e) {
			return Short.valueOf(Integer.toString(defaultValue));
		}
	}
}
