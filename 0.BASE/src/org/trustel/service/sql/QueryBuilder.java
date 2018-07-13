package org.trustel.service.sql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.trustel.service.sql.a.ICondition;
import org.trustel.service.sql.a.IQuery;

public class QueryBuilder extends ConditionFactory implements IQuery {

	private StringBuffer orderBuf = new StringBuffer("");

	private String tableName = "";

	private StringBuffer groupBuf = new StringBuffer("");

	private StringBuffer selectBuf = new StringBuffer("");

	private List<Join> joins = null;

	public QueryBuilder() {

	}

	public QueryBuilder(Class<?> pojo) {
		tableName = getClassName(pojo);
	}

	public QueryBuilder(Class<?> pojo, String alias) {
		tableName = getClassName(pojo) + " " + alias;
	}

	/**
	 * 
	 * SAMPLE [User.class,Organization] == User a,Organization b
	 */
	public QueryBuilder(Class<?>[] pojos) {
		tableName = "";
		String alias = "abcdefghijklmnopqrstuvwxyz";
		for (byte i = 0; i < pojos.length; i++) {
			if (i == 0)
				tableName = String.format("%s %s", new Object[] {
						getClassName(pojos[i]), alias.charAt(i) });
			else
				tableName = tableName
						+ String.format(",%s %s", new Object[] {
								getClassName(pojos[i]), alias.charAt(i) });
		}
	}

	/**
	 * 
	 * @param tableName
	 *            如User<br>
	 *            User a,Organization b<br>
	 *            在使用多个表时，增加条件时字段名按Alias.fieldName方式添加<br>
	 *            如果不会发生冲突，也可以按原来方式处理 <BR>
	 *            Example:
	 * 
	 * 关联用户和用户组织查询
	 * 
	 * <pre>
	 * builder = new QueryBuilder(\&quot;User a,Organization b\&quot;);
	 * builder.addCondition(\&quot;a.code=b.code\&quot;);
	 * 
	 * </pre>
	 */
	public QueryBuilder(String tableNames) {
		this.tableName = tableNames;
	}

	public void addBuilder(IQuery builder) {
		String tmp = builder.getSQL();
		if (tmp.equals(""))
			return;
		append("(" + tmp + ")", true);
		fieldValues.addAll(builder.getValues());
		lastOper = "";

	}

	public void addBuilder(IQuery builder, String fieldName,
			String targetFieldName, boolean isRange) {
		addBuilder(builder, fieldName, targetFieldName,
				isRange ? QueryAction.IN : QueryAction.EQUAL);
	}

	public void addBuilder(IQuery builder, String fieldName,
			String targetFieldName, int action) {
		if (fieldName == null || fieldName.trim().equals("")) {
			addBuilder(builder);
			return;
		}
		String tmp = builder.getSQL();
		if (tmp.equals(""))
			return;
		tmp = "select " + att2FName(targetFieldName) + tmp;
		switch (action) {
		case QueryAction.IN:
			append(att2FName(fieldName) + " in (" + tmp + ")", true);
			break;
		case QueryAction.NOT_IN:
			append(att2FName(fieldName) + " not in (" + tmp + ")", true);
			break;
		case QueryAction.EQUAL:
			append(att2FName(fieldName) + " = (" + tmp + ")", true);
			break;
		case QueryAction.NOEQUAL:
			append(att2FName(fieldName) + " <> (" + tmp + ")", true);
			break;
		default:
			String msg = "addBuilder:不可知的逻辑运算行为" + action;
			throw new QueryBuilderException(msg);
		}
		fieldValues.addAll(builder.getValues());
	}

	protected String class2Table(Class<?> type) {
		return class2Table(type.getName());
	}

	protected String class2Table(String className) {
		return className;
	}

	protected synchronized String getClassName(Class<?> type) {
		String name = type.getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}

	public String getDeleteSQL() {
		endGroudCondtion();
		String tmp = whereBuf.toString();

		if (!tableName.equals(""))
			if (tmp.equals(""))
				tmp = "delete from " + tableName;
			else
				tmp = "delete from " + tableName + " where "
						+ whereBuf.toString();
		tmp = tmp + orderBuf.toString();
		logger.info("QueryBuilder.getDeleteSQL:" + tmp);
		return tmp;
	}

	public String getHQL() {
		return getQL(null, false);
	}

	public String getHQL(String fieldNames) {
		return getQL(fieldNames, false);
	}

	protected String getQL(String fieldNames, boolean jdbc) {
		endGroudCondtion();
		String tmp = whereBuf.toString();
		fieldNames = getSelectFieldList(fieldNames);
		if (!tableName.equals("")) {

			if (tmp.equals(""))
				tmp = " from " + tableName + getJoin();
			else
				tmp = " from " + tableName + getJoin() + " where "
						+ whereBuf.toString();

			if (jdbc || !"*".equals(fieldNames.trim()))
				tmp = "select " + fieldNames + tmp;
		}
		// if (returnTotalHQL)
		// tmp = "select count(*)" + tmp;
		// else
		tmp = tmp + groupBuf.toString() + orderBuf.toString();

		logger.info("QueryBuilder.getHQL:" + tmp);
		return tmp;
	}

	private String getJoin() {
		if (joins == null)
			return "";
		StringBuffer buf = new StringBuffer("");
		String[] connExps = new String[] { " USE_HASH(%s) ", " USE_HASH(%s) ",
				" USE_MERGE(%s) ", " inner join %s %s on ",
				" left join %s %s on ", " right join %s %s on ",
				" full join %s %s on ", " cross join %s %s on " };
		List<ICondition> values = new ArrayList<ICondition>();
		for (int i = 0; i < joins.size(); i++) {
			Join item = joins.get(i);
			buf.append(String.format(connExps[item.join], new Object[] {
					item.tableName, item.alias }));
			buf.append(item.getWhere().replace(" where ", ""));
			values.addAll(item.fieldValues);
		}
		values.addAll(fieldValues);
		fieldValues = values;
		return buf.toString();
	}

	private String getSelectFieldList(String fieldList) {
		if (fieldList == null || fieldList.trim().equals(""))
			fieldList = selectBuf.toString();
		if (fieldList == null || fieldList.trim().equals(""))
			fieldList = "*";
		return fieldList;
	}

	public String getSQL() {
		return getQL(null, true);
	}

	public String getSQL(String fieldList) {
		return getQL(fieldList, true);
	}

	public String getTotalSQL() {
		return getQL("count(*)", true);
	}

	public String getUpdate() {
		if (!tableName.equals(""))
			return "update " + tableName;
		else
			return "";
	}

	public List<ICondition> getValues() {
		return fieldValues;
	}

	/**
	 * 
	 * @param field
	 *            多个字段名使用半角逗号分隔
	 */
	public void groupby(String field) {
		if (field == null || field.trim().equals(""))
			return;
		if (groupBuf.toString().trim().equals(""))
			groupBuf.append(" group by ");
		else
			groupBuf.append(",");
		groupBuf.append(field);
	}

	public void groupby(String[] fields) {
		if (fields.length > 0) {
			if (groupBuf.toString().trim().equals(""))
				groupBuf.append(" group by ");
			else
				groupBuf.append(",");
			for (int i = 0; i < fields.length; i++)
				if (i != 0)
					groupBuf.append("," + fields[i]);
				else
					groupBuf.append(fields[i]);
		}
	}

	/**
	 * 
	 * @param tableName
	 * @param join
	 *            JOIN.
	 * @return
	 */
	public Join join(String tableName, int join) {
		if (joins == null)
			joins = new ArrayList<Join>();
		String alias = "abcdefghijklmnopqrstuvwxyz";
		return join(tableName, String.valueOf(alias.charAt(joins.size() + 1)),
				join);
	}

	public Join join(String tableName, String alias, int join) {
		Join j = new Join(tableName, join);

		joins.add(j);
		j.alias = alias;
		return j;
	}

	public void orderBy(String fieldName) {
		if (orderBuf.toString().equals(""))
			orderBuf.append(" order by " + att2FName(fieldName));
		else
			orderBuf.append("," + att2FName(fieldName));

	}

	public void orderBy(String fieldName, boolean asc) {
		if (asc)
			fieldName = att2FName(fieldName) + " asc";
		else
			fieldName = att2FName(fieldName) + " desc";
		if (orderBuf.toString().equals(""))
			orderBuf.append(" order by " + fieldName);
		else
			orderBuf.append("," + fieldName);

	}

	/**
	 * 
	 * @param fieldName
	 *            字段名(Hibernate时使用属性名),多个字段用半角逗号分隔。
	 */
	public void select(String fieldName) {
		if (selectBuf.toString().equals(""))
			selectBuf.append(fieldName);
		else
			selectBuf.append("," + fieldName);
	}

	protected String shortName2Table(String shortName) {
		return shortName;
	}

	public void where(String fieldName, Date value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, double value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, Double value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, float value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, Float value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, ICondition condition, int queryAction) {
		super.where(fieldName, condition, queryAction);
	}

	public void where(String fieldName, int value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, int value) {
		super.where(fieldName, value);
	}

	public void where(String fieldName, int[] values, int action) {
		super.where(fieldName, values, action);
	}

	public void where(String fieldName, int[] values) {
		super.where(fieldName, values);
	}

	public void where(String fieldName, Integer value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String field, IQuery subQuery) {
		super.where(field, subQuery);
	}

	public void where(String fieldName, long value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, long value) {
		super.where(fieldName, value);
	}

	public void where(String fieldName, String value, int queryAction,
			String patterns) {
		super.where(fieldName, value, queryAction, patterns);
	}

	public void where(String fieldName, String value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, String value) {
		super.where(fieldName, value);
	}

	public void where(String fieldName, String[] values) {
		super.where(fieldName, values);
	}

	public void where(String hql) {
		super.where(hql);
	}

	public void where(String[] fieldNames, String value, int queryAction) {
		super.where(fieldNames, value, queryAction);
	}

	public void whereAsDate(String fieldName, String value, int queryAction) {
		super.whereAsDate(fieldName, value, queryAction);
	}

	public void whereAsInt(String fieldName, String value, int queryAction) {
		super.whereAsInt(fieldName, value, queryAction);
	}

}
