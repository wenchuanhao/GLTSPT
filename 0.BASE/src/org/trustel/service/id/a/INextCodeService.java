package org.trustel.service.id.a;

public interface INextCodeService {

	public long getNextCode(String sequenceName, int minValue, long maxValue) throws Exception;

}
