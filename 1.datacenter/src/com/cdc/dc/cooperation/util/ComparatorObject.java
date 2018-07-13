package com.cdc.dc.cooperation.util;

import java.util.Comparator;

public class ComparatorObject implements Comparator {

	@Override
	public int compare(Object arg1, Object arg2) {
		Object[] obj1 = (Object[]) arg1;
		Object[] obj2 = (Object[]) arg2;
		//错误数，从大到小
		int flag =  Integer.valueOf((String) obj2[obj2.length - 1]).compareTo(Integer.valueOf((String) obj1[obj1.length - 1]));
		if(flag == 0){
			//根据索引排序，从小到大
			return Integer.valueOf((String) obj1[obj1.length - 2]).compareTo(Integer.valueOf((String) obj2[obj2.length - 2]));
		}
		return flag;
	}

}
