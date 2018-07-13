package org.trustel.service.sql.a;


public class DefaultCondition implements ICondition {

	public DefaultCondition(Object value, boolean muilt, int type) {
		this.value = value;
		this.muilt = muilt;
		this.type = type;
	}

	private Object value;
	private boolean muilt;
	private int type;

	public Object getValue() {
		return value;
	}

	public boolean isMuilt() {
		return muilt;
	}

	public int getType() {
		return type;
	}

}
