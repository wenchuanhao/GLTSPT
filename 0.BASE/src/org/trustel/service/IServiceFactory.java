package org.trustel.service;

public interface IServiceFactory {
	public IEnterpriseService getDefaultService();

	public IEnterpriseService getServiceByName(String name);

}
