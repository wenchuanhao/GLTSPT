package org.trustel.scheduling.service;

import java.util.List;

import org.trustel.scheduling.service.Scheduable;
import org.trustel.service.IEnterpriseService;

public class ConfigurationLoader
{

	private List<Scheduable> tasks;

	@SuppressWarnings("unused")
	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService)
	{
		this.enterpriseService = enterpriseService;
	}

	public void setTasks(List<Scheduable> tasks)
	{
		this.tasks = tasks;
	}

	public void load()
	{
		for (Scheduable item : tasks)
		{
			item.execute();
		}
		System.err.println("start up...OK");
	}

}
