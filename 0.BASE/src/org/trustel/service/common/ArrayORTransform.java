package org.trustel.service.common;

import org.trustel.util.SimpleHashMap;

public class ArrayORTransform implements IORTransform {

	public Object transform(long rowIndex, SimpleHashMap fields) {
		return fields.toArrayByIndex();
	}

}
