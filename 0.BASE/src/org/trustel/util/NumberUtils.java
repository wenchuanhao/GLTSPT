package org.trustel.util;

import java.math.BigDecimal;

public class NumberUtils {

	public static String format(double d, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(d));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();

	}

	public static String zero2Str(int value, String defaultValue) {
		return value == 0 ? defaultValue : String.valueOf(value);
	}

	public static String zero2Str(long value, String defaultValue) {
		return value == 0 ? defaultValue : String.valueOf(value);
	}

	public static String zero2Str(float value, String defaultValue, int scale) {
		return value == 0.0 ? defaultValue : format(value, scale);
	}

	public static String zero2Str(double value, String defaultValue, int scale) {
		return value == 0.0 ? defaultValue : format(value, scale);
	}

}
