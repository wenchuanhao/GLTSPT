package com.cdc.system.core.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Spring的帮助者，用来得到Bean实体
 * @author WEIFEI
 * @date 2016-4-15 下午12:22:43
 */
public class SpringHelper {

	/**
	 * 得到applicationcontext.xml,applicationcontext-hibernate.xml当中定义的的bean实体.
	 * @param beanName
	 * @return object
	 */
	public static Object getBean(String beanName) {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		return context.getBean(beanName);
	}
}
