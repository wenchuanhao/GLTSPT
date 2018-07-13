package org.trustel.scheduling.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class LoadConfigurationJob extends QuartzJobBean
{

	private Scheduable loader = null;

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException
	{

		if (loader != null)
			loader.execute();
	}

	public void setLoader(Scheduable loader)
	{
		this.loader = loader;
	}

}
