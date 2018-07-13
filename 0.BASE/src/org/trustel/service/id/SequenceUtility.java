package org.trustel.service.id;

import org.trustel.service.id.a.INextCodeService;
import org.trustel.util.ParameterFactory;

public class SequenceUtility {
	public static String schema = "";

	private long index = 0;

	private int interval = 10000;

	private long value = -1;

	private long getMaxValue() {
		return ParameterFactory.getLong("SYSTEM_SEQUENCE_DEFAULT_MAVVALUE",
				Integer.MAX_VALUE - 10000);
	}

	private int getMinValue() {
		return ParameterFactory.getInt("SYSTEM_SEQUENCE_DEFAULT_MINVALUE",
				10000);
	}

	public synchronized Long getNextLongValue(INextCodeService service,
			String sequenceName) throws Exception {
		return getNextLongValue(service, sequenceName, getMinValue(),
				getMaxValue());
	}

	public synchronized Long getNextLongValue(INextCodeService service,
			String sequenceName, int minValue, long maxValue) throws Exception {

		if (value < 0)
			value = service.getNextCode(sequenceName, minValue, maxValue);
		if (index < interval) {
			index++;
			return value * interval + index;
		}
		index = 0;
		value = service.getNextCode(sequenceName, minValue, maxValue);
		return value * interval;

	}
}
