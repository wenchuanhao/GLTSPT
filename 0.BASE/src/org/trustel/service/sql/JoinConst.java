package org.trustel.service.sql;

public final class JoinConst {
	/**
	 * 原理：扫描一个表，每读取驱动表的一条记录，就根据索引去另一个表里面查找，所有匹配记录放在结果集中，然后再读取驱动表的下一行。没有索引一般就不会是
	 * nested loops 。 <br />
	 * 条件：驱动表结果集不大，被驱动表连接字段要有索引。<br />
	 * 特点：使用嵌套循环连接是从连接结果中提取第一批记录的最快速方法。 <br />
	 * 使用： USE_NL(t1 t2) 提示来强制执行 Nested Loops 。
	 */
	public static int NESTED_LOOPs = 0;

	/**
	 * 原理：优化器先扫描小表，根据连接键在内存中建立 hash 表，然后扫描大表，每得到一条记录就探测 hash 表一次，找出匹配行。 <br />
	 * 条件：两个巨大表之间的连接，或一个巨大的表一个小表之间的连接。且连接键无索引。 <br />
	 * 特点：需要较大的内存，如表太大则需要进行分区，并暂时存储至磁盘的临时段。扫描成本 = 全表扫描大表 + 分区数 *
	 * 表全表扫描小表；还需要注意的是：必须将 HASH_JOIN_ENABLED 设为 True, 并且为参数 PGA_AGGREGATE_TARGET
	 * 设置了一个足够大的值后，才可以执行 Hash Join 。 <br />
	 * 使用： USE_HASH(t1 t2) 提示来强制执行 Hash Join
	 */
	public static int HASH_JOIN = 1;

	/**
	 * 原理：将两个表分别进行排序，然后将两个表合并，查找出匹配的记录。<br />
	 * 条件：行源已经被排过序的情况下使用。<br />
	 * 特点：主要花费在两个表的全表扫描和各自的排序上。<br />
	 * 使用： USE_MERGE(t1 t2) 提示来强制执行 Sort Merge Join 。
	 */
	public static int SORT_MERGE = 2;

	/**
	 * 外部连接:完全匹配
	 */
	public static int INNER_JOIN = 3;

	/**
	 * 外部连接:左外连
	 */
	public static int LEFT_JOIN = 4;

	/**
	 * 外部连接:右外连接
	 */
	public static int RIGHT_JOIN = 5;

	/**
	 * 全外连接 合并相同但保留差异,相当于UNION
	 */
	public static int FULL_JOIN = 6;

	/**
	 * 交叉连接，相当于笛卡尔乘积
	 */
	public static int CROSS_JOIN = 7;
}
