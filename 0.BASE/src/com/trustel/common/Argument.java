package com.trustel.common;

import java.io.Serializable;

/**
 * 参数定义
 */
public class Argument implements Serializable {
	/**
	 *参数id
	 */
	public String id;
	/**
	 *参数名称
	 */
	public String name;
	private Argument() {
	}
	public Argument(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
