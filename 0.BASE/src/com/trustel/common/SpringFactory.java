package com.trustel.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringFactory implements BeanFactoryAware{

	private static BeanFactory factory;

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		SpringFactory.factory = factory;
	}
	
	/**
	 * Fetch a bean instance
	 * @param className
	 * @return
	 */
	public static Object getBean(String className){
		return factory.getBean(className);
	}

}