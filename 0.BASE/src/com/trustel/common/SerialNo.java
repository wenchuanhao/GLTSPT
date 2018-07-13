package com.trustel.common;

import java.text.NumberFormat;

/**
 * @author Administrator
 *
 * 取序列号类
 */
public class SerialNo {
    private static long sequence;
    private static String compareTime;
    private static NumberFormat numberFormat;
    
    static {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumIntegerDigits(5);
        numberFormat.setMaximumIntegerDigits(5);
    }
    
	/**
	 * 生成唯一序列号
	 * <p>
	 * 根据当前时间加五位序号，一共20位
	 * 
	 * @return 序列号
	 */
	public static synchronized String getUNID() {		
        String currentTime = DateFunc.getCurrentDateString("yyMMddHHmmssSSS");
        if (compareTime == null || compareTime.compareTo(currentTime) != 0) {
            compareTime = currentTime;
            sequence = 1;
        } else
            sequence++;

        return currentTime + numberFormat.format(sequence);
	}

	/**
	 * 生成唯一序列号
	 * <p>
	 * 根据当前时间生成，用于非批量数据记录生成时，一共15位(如果存在批量插入时，可能出现重复)
	 * 
	 * @return 序列号
	 */	
	public static String getSerialforDB() {
		return DateFunc.getCurrentDateString("yyMMddHHmmssSSS"); 
	}

	/**
	 * 生成短序列号
	 * <p>
	 * 根据当前时间生成，用于少量数据记录时。(可能出现重复，一般用于记录较少且变动不频繁的静态表的记录生成)
	 * 
	 * @return 序列号
	 */	
	public static String getShortSerial() {
		return DateFunc.getCurrentDateString("mmssSSS");
	}
}
