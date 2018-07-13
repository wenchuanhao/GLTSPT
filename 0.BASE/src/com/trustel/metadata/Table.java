package com.trustel.metadata;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * 数据库表结构信息
 */
public class Table implements Serializable {
	/**
	 * schema名称
	 */
	public String schema;
	/**
	 * 类型名(Table、View)
	 */
	public String type;
	/**
	 * 表名
	 */
	public String table;
	
	public Table(String schema, String type, String table) {
		this.schema = schema == null ? "null" : schema;
		this.type = type;
		this.table = table;
	}

}