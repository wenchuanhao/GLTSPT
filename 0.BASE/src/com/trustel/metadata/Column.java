package com.trustel.metadata;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * 表字段信息
 */
public class Column implements Serializable {
	/**
	 * 可空
	 */
	private final int NULLABLE = 1;

	/**
	 * 字段名
	 */
	public String name;
	/**
	 * 字段类型(对应java.sql.Types中的数据类型)
	 */
	public int dataType;
	/**
	 * 字段类型名
	 */
	public String typeName;
	/**
	 * 大小
	 */
	public int size;
	/**
	 * 小数位
	 */
	public int digits;
	/**
	 * 是否可空
	 */
	public boolean nullable;
	/**
	 * 缺省值
	 */
	public String defaultValue;
	
	/**
	 * 备注
	 */
	public String remark;
	
	private Column() {
		
	}
	
	public Column(String name, int dataType, String typeName, int size, int digits, int nullable, String defaultValue, String remark) {
		this.name = name;
		this.dataType = dataType;
		this.typeName = typeName;
		this.size = size;
		this.digits = digits;
		this.nullable = (nullable == NULLABLE) ? true : false;
		this.defaultValue = defaultValue;
		this.remark = remark;
	}
}
