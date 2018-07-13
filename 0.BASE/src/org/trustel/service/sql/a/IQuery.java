package org.trustel.service.sql.a;

import java.util.List;

public interface IQuery {
	/**
	 * 
	 * @return delete from tablename where
	 */
	public String getDeleteSQL();

	/**
	 * 返回查询语句（非统计行语句）(hibernate:from XX)
	 */
	public String getHQL();

	/**
	 * 返回查询语句（非统计行语句）
	 * 
	 * @param *
	 * @return 返回 select fieldlist from tablename where .... 无表名时返回 where
	 *         之后的条件语句
	 */
	public String getSQL();

	/**
	 * 
	 * @param fieldlist
	 * @return 返回 select fieldlist from tablename where .... 无表名时返回 where
	 *         之后的条件语句
	 */
	public String getSQL(String fieldlist);

	/**
	 * 
	 * @return 类似select count(*) from tableName where语句
	 */
	public String getTotalSQL();

	/**
	 * 
	 * @return 返回查询条件
	 */
	public List<ICondition> getValues();

	/**
	 * 
	 * @return 返回查询中where之后的内容（不包括where)
	 */
	public String getWhere();
}
