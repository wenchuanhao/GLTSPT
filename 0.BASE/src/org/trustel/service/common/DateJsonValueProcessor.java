package org.trustel.service.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {

	private String pattern = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public DateJsonValueProcessor() {
	}
	/**
	 * @param _pattern 默认 yyyy-MM-dd HH:mm:ss
	 */
	public DateJsonValueProcessor(String _pattern) {
		this.pattern = _pattern;
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		SimpleDateFormat sdf = new SimpleDateFormat(this.pattern);
		String date = sdf.format(((Date)arg1));
		return date;
	}

}
