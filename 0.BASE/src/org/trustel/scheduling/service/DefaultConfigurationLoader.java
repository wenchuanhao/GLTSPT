package org.trustel.scheduling.service;

import java.util.List;

public class DefaultConfigurationLoader implements Scheduable {
	private List<Scheduable> jobs = null;

	public boolean execute() {
		if (jobs != null)
			for (int i = 0; i < jobs.size(); i++)
				jobs.get(i).execute();
		return true;
	}

	public void setJobs(List<Scheduable> jobs) {
		this.jobs = jobs;
	}
}
