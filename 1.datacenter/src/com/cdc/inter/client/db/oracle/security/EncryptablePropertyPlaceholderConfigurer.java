package com.cdc.inter.client.db.oracle.security;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.cdc.util.DesUtil;

public class EncryptablePropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		try {
			DesUtil des = new DesUtil();
			String username = props
					.getProperty(MyWebConstant.JDBC_DATASOURCE_USERNAME_KEY);
			if (username != null) {
				props.setProperty(MyWebConstant.JDBC_DATASOURCE_USERNAME_KEY,
						des.decrypt(username, DesUtil.DES_KEY_STRING));
			}

			String password = props
					.getProperty(MyWebConstant.JDBC_DATASOURCE_PASSWORD_KEY);
			if (password != null) {
				props.setProperty(MyWebConstant.JDBC_DATASOURCE_PASSWORD_KEY,
						des.decrypt(password, DesUtil.DES_KEY_STRING));
			}

			String url = props
					.getProperty(MyWebConstant.JDBC_DATASOURCE_URL_KEY);
			if (url != null) {
				props.setProperty(MyWebConstant.JDBC_DATASOURCE_URL_KEY,
						des.decrypt(url, DesUtil.DES_KEY_STRING));
			}

			String driverClassName = props
					.getProperty(MyWebConstant.JDBC_DATASOURCE_DRIVERCLASSNAME_KEY);
			if (driverClassName != null) {
				props.setProperty(
						MyWebConstant.JDBC_DATASOURCE_DRIVERCLASSNAME_KEY,
						des.decrypt(driverClassName, DesUtil.DES_KEY_STRING));
			}
			String dbtype = props
					.getProperty(MyWebConstant.JDBC_DATASOURCE_TYPE_KEY);
			if (dbtype != null) {
				props.setProperty(MyWebConstant.JDBC_DATASOURCE_TYPE_KEY,
						des.decrypt(dbtype, DesUtil.DES_KEY_STRING));
			}
			super.processProperties(beanFactory, props);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BeanInitializationException(e.getMessage());
		}
	}
}
