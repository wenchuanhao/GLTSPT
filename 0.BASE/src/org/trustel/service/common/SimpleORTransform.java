package org.trustel.service.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.trustel.system.SystemLogger;
import org.trustel.util.SimpleHashMap;


public class SimpleORTransform implements IORTransform {
	private Class<?> pojo;
	private Map<String, Method> methods = new HashMap<String, Method>();
	private Map<String, java.lang.reflect.Field> fields = new HashMap<String, java.lang.reflect.Field>();

	public SimpleORTransform(Class<?> mapClassInfo) {
		this.pojo = mapClassInfo;
		Method[] arrMethods = mapClassInfo.getMethods();
		for (Method method : arrMethods) {
			String name = method.getName();
			if (name.startsWith("set") && name.length() > 3) {
				if (method.getParameterTypes().length == 1) {
					name = name.replace("set", "").toLowerCase();
					methods.put(name, method);
				}
			}
		}
		java.lang.reflect.Field[] arrField = mapClassInfo.getFields();
		for (java.lang.reflect.Field field : arrField) {
			fields.put(field.getName().toLowerCase(), field);
		}

	}

	public Object transform(long rowIndex, SimpleHashMap fields) {

		Object obj;
		try {
			obj = pojo.newInstance();

		} catch (Exception e) {
			// LogUtils.error(e.getMessage(), e);
			SystemLogger.error(this, e);
			return null;
		}

		Set<?> keys = fields.keySet();
		for (Object key : keys) {
			try {
				if (methods.containsKey(key))
					methods.get(key).invoke(obj, fields.get(key.toString()));
			} catch (Exception e) {
				SystemLogger.error(this, e);
				try {
					this.fields.get(key.toString()).set(key, fields.get(key.toString()));
				} catch (Exception e1) {
					SystemLogger.error(this, e1);
					// LogUtils.error(e.getMessage(), e);
				}
			}
		}

		return obj;
	}

}

class Property {
	public String name;
	public Method method;
}