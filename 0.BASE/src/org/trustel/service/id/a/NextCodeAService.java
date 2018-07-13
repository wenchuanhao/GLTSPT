package org.trustel.service.id.a;

import org.trustel.service.IEnterpriseService;

public class NextCodeAService implements INextCodeService {
	private IEnterpriseService service;

	public NextCodeAService(IEnterpriseService service) {
		this.service = service;
	}

	public long getNextCode(String sequenceName, int minValue, long maxValue)
			throws Exception {
		return service._getNextCode(sequenceName, minValue, maxValue);
	}

}
