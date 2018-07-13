package org.trustel.service.sql.a;

public interface IColumn {
	/**
	 * 
	 * @return 字段别名
	 */
	public String getAlais();

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
	 * @return 字段序号，从0开始连续编号
	 */
	public int getColumnIndex();

}
