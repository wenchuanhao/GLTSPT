package org.trustel.service.sql.a;

import java.util.Date;


/**
 * 字段，即查询数据时返回的数据值，常于数据转换时使用，其中字段名等应通过代理或友元方式保存
 * 
 * @author 万志勇
 * 
 * 
 *         版本：1.0.0.0 2010-10-21
 */
public interface IField extends ICondition {

	/**
	 * 
	 * @return 字段别名
	 */
	public String getAlais();

	/**
	 * 
	 * @return 字段属性
	 */
	public IColumn getColumn();

	/**
	 * 
	 * @return 字段名
	 */
	public String getName();

	/**
	 * 
	 * @return 字段类型
	 */
	public int getType();

	/**
	 * 
	 * @return 值
	 */
	public Object getValue();

	/**
	 * 
	 * @return 偿试将数据转换为布尔型
	 */
	public boolean toBoolean();

	public boolean toBoolean(boolean defaultValue);

	/**
	 * 
	 * @param tvalue
	 *            即true value
	 * @return 偿试将数据转换为布尔型
	 */
	public boolean toBoolean(int tvalue);

	/**
	 * 
	 * @param tvalue
	 *            即true value
	 * @return 偿试将数据转换为布尔型
	 */
	public boolean toBoolean(String tvalue);

	/**
	 * 
	 * @return 将字段值偿试转换为日期型
	 */
	public Date toDate();

	/**
	 * 
	 * @param timezone
	 *            时区
	 * @return 将字段值偿试换为指定时区日期
	 */
	public Date toDate(String timezone);

	/**
	 * @param pattern
	 *            指定日期格式字串（适用数值实际为字符串格式日期值）
	 * @param timezone
	 *            时区
	 * @return 将字段值偿试换为指定时区日期
	 */
	public Date toDate(String pattern, String timezone);

	/**
	 * 
	 * @return 将字段值偿试转换为双精度数据
	 */
	public double toDouble(double defaultValue);

	/**
	 * 
	 * @return 将数据偿试转换为浮点数
	 */
	public float toFloat(float defaultValue);

	/**
	 * 
	 * @return 将数据偿试转换为整型
	 */
	public int toInteger(int defaultValue);
	
	public short toShort(int defaultValue);

	/**
	 * 
	 * @return 将数据偿试转换为字符串
	 */
	public String toString();

	/**
	 * 
	 * @param pattern
	 *            日期格式
	 * @param timezone
	 *            时区
	 * @return 偿试将数据转换为指定时区格式的字符串
	 */
	public String toString(String pattern, String timezone);

}
