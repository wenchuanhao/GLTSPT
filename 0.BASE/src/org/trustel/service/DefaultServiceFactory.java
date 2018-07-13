package org.trustel.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultServiceFactory implements IServiceFactory {
	private Log logger = LogFactory.getLog(getClass());

	private String defaultServiceName = "enterpriseService";

	private Map<String, IEnterpriseService> map = new HashMap<String, IEnterpriseService>();

	public void setMap(Map<String, IEnterpriseService> map) {
		this.map = map;
	}

	/**
	 * 获取缺省的服务名称，缺省服务通常系统通常依赖缺省服务。如鉴权等
	 */
	public IEnterpriseService getDefaultService() {
		return getServiceByName(defaultServiceName);
	}

	/**
	 * 通过名称获取服务
	 */
	public IEnterpriseService getServiceByName(String name) {
		if (map.containsKey(name))
			return map.get(name);
		logger.warn(String.format(
				"not found %s,you should config in Spring Configuration",
				new Object[] { name }));
		return null;
	}

	/**
	 * 配置缺省服务名称
	 * 
	 * @param defaultServiceName
	 *            缺省名称，通常使用Spring配置
	 */
	public void setDefaultServiceName(String defaultServiceName) {
		this.defaultServiceName = defaultServiceName;
	}

}
