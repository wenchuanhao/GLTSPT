package org.trustel.service.sql;

public final class QueryAction {

	public static final int BETWEEN = 8;//
	
	/**
	 * 等于
	 */
	public static final int EQUAL = 0;

	/**
	 * 大于等于(>=)
	 */
	public static final int GE = 2;

	/**
	 * 大于(>)
	 */
	public static final int GT = 6; // >

	public static final int IN = 4;

	/**
	 * 小于等于(<=)
	 */
	public static final int LE = 3;

	/**
	 * LIKE（SQL）模糊查询
	 */
	public static final int LIKE = 1; // 模糊查询 like

	/**
	 * LIKE (AA%)
	 */
	public static final int LIKE_POSTFIX = 102;

	/**
	 * LIKE (%AA)
	 */
	public static final int LIKE_PREFIX = 101;

	/**
	 * 小于(<)
	 */
	public static final int LT = 7; // <

	/**
	 * 不等于 <>
	 */
	public static final int NOEQUAL = 5; // <>
	/**
	 * not in
	 */
	public static final int NOT_IN = 9;

    /**
     * 自定义
     */
    public static final int CUSTOM = 10;

}
