package org.trustel.util;

import java.util.HashMap;

public final class ResourceFactory {

	private HashMap<String, ParameterUtility> resources;

	private boolean flag = false;

	private static ResourceFactory instance;

	public final static ResourceFactory getInstance() {
		if (instance == null)
			instance = new ResourceFactory();

		return instance;
	}

	private ResourceFactory() {
		resources = new HashMap<String, ParameterUtility>();
	}

	public synchronized void load(String lang, ParameterUtility resource) {
		flag = true;
		resources.remove(lang);
		resources.put(lang, resource);
		flag = false;
	}

	private String getString(String lang, String name, String defaultMsg) {
		while (flag)
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		return resources.get(lang).getString(name, defaultMsg);
	}

	/**
	 * 获取对应语言的消息描述
	 * 
	 * @param lang
	 *            言语编码如zh_CN,大小写敏感
	 * @param name
	 *            名称
	 * @param defaultMsg
	 *            缺省消息（当未能取到指定名称和语言的消息时返回该值)
	 * @return
	 */
	public static String getMessage(String lang, String name, String defaultMsg) {
		return getInstance().getString(lang, name, defaultMsg);
	}

	public static String getMessage(String lang, String name) {
		return getMessage(lang, name, "");
	}

}
