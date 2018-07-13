package org.trustel.service.sql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.common.Utils;
import org.trustel.service.sql.a.DefaultCondition;
import org.trustel.service.sql.a.ICondition;
import org.trustel.service.sql.a.IQuery;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConditionFactory {
	protected Log logger = LogFactory.getLog(getClass());

	protected StringBuffer whereBuf = new StringBuffer("");

	protected List<ICondition> fieldValues = new ArrayList<ICondition>();

	private int group = 0;

	protected String lastOper = "";

	public void and() {
		lastOper = " and ";
	}

	protected void append(String hql, boolean defaultLogic) {

		if (defaultLogic && lastOper.equals(""))
			lastOper = " and ";
		checkGroup();
		if (!whereBuf.toString().equals(""))
			whereBuf.append(lastOper);
		whereBuf.append(hql);
		lastOper = "";

	}

	protected String att2FName(String attribute) {
		return attribute;
	}

	private void checkGroup() {

		if (group == 1 || group == 2)// 当有一个组信息时始前标记
		{
			if (whereBuf.toString().trim().equalsIgnoreCase(""))
				whereBuf.append("(");
			else {
				lastOper = "";
				whereBuf.append(" and (");
			}
			group = 3;
		}

	}

	public void createCondtionGroup() {
		createCondtionGroup(false);
	}

	public void createCondtionGroup(boolean isOR) {
		if (isOR)
			group = 2;
		else
			group = 1;
	}

	public void endGroudCondtion() { // 当有一个有效组时才结束
		if (group == 3)
			append(")", false);
		group = 0;
	}

	protected String getOperation(int queryAction) {
		String oper = "";
		switch (queryAction) {
		case QueryAction.EQUAL:
			oper = "=?";
			break;
		case QueryAction.LIKE_POSTFIX:
			oper = " like ? ";
			break;
		case QueryAction.LIKE_PREFIX:
			oper = " like ? ";
			break;
		case QueryAction.LIKE:
			oper = " like ? ";
			break;
		case QueryAction.GE:
			oper = ">=?";

			break;
		case QueryAction.LE:
			oper = "<=?";
			break;
		case QueryAction.IN:
			oper = " in (?)";
			break;
		case QueryAction.NOEQUAL:
			oper = "<>?";
			break;
		case QueryAction.GT:
			oper = ">?";

			break;

		case QueryAction.LT:
			oper = "<?";
			break;
		case QueryAction.BETWEEN:
			oper = " between ? and ? ";
			break;
		case QueryAction.NOT_IN:
			oper = " not in (?)";
			break;
		}
		return oper;
	}

	public String getWhere() {
		endGroudCondtion();
		String tmp = whereBuf.toString();
		if (!tmp.equals(""))
			tmp = " where " + tmp;
		logger.info("QueryBuilder.getWhere:" + tmp);
		return tmp;
	}

	public void or() {
		lastOper = " or ";
	}

	protected void where(String hql) {
		if (hql == null || hql.trim().equals(""))
			return;
		append(hql, true);
	}

	protected void where(String fieldName, Date value, int queryAction) {
		if (queryAction == QueryAction.LIKE
				|| queryAction == QueryAction.BETWEEN
				|| queryAction == QueryAction.IN)
			return;
		if (value == null)
			return;
		String oper = getOperation(queryAction);
		if (!oper.equals("")) {
			append(att2FName(fieldName) + oper, true);
			fieldValues.add(new DefaultCondition(value, false, Types.DATE));
		}
	}

	protected void where(String fieldName, double value, int queryAction) {
		if (queryAction == QueryAction.LIKE
				|| queryAction == QueryAction.BETWEEN
				|| queryAction == QueryAction.IN)
			throw new QueryBuilderException("where:浮点型数据不支持" + queryAction
					+ "运算");
		Double v = new Double(value);
		String oper = getOperation(queryAction);
		if (!oper.equals("")) {
			append(att2FName(fieldName) + oper, true);
			fieldValues.add(new DefaultCondition(v, false, Types.DOUBLE));
		}
	}

	protected void where(String fieldName, Double value, int queryAction) {
		if (value == null)
			return;
		where(fieldName, value.doubleValue(), queryAction);
	}

	protected void where(String fieldName, float value, int queryAction) {

		if (queryAction == QueryAction.LIKE
				|| queryAction == QueryAction.BETWEEN
				|| queryAction == QueryAction.IN)
			return;
		Float v = new Float(value);
		String oper = getOperation(queryAction);
		if (!oper.equals("")) {
			append(att2FName(fieldName) + oper, true);
			fieldValues.add(new DefaultCondition(v, false, Types.FLOAT));
		}
	}

	protected void where(String fieldName, Float value, int queryAction) {
		if (value == null)
			return;
		where(fieldName, value.floatValue(), queryAction);

	}

	protected void where(String fieldName, ICondition condition, int queryAction) {
		if (fieldName == null || fieldName.trim().equals(""))
			return;
		if (condition.getValue() == null)
			return;

		if (condition.getType() == Types.VARCHAR) {
			String tmp = (String) condition.getValue();
			if (tmp.trim().equals(""))
				return;
		}
		append(att2FName(fieldName) + getOperation(queryAction), true);
		fieldValues.add(condition);

	}

	protected void where(String fieldName, int value) {
		where(fieldName, value, QueryAction.EQUAL);
	}

	protected void where(String fieldName, int value, int queryAction) {
		if (queryAction == QueryAction.LIKE
				|| queryAction == QueryAction.BETWEEN
				|| queryAction == QueryAction.IN)
			return;

		Integer v = Integer.valueOf(value);
		String oper = getOperation(queryAction);
		if (!oper.equals("")) {
			append(att2FName(fieldName) + oper, true);
			fieldValues.add(new DefaultCondition(v, false, Types.INTEGER));
		}
	}

	protected void where(String fieldName, int[] values) {
		where(fieldName, values, QueryAction.IN);
	}

	protected void where(String fieldName, int[] values, int action) {

		if (action == QueryAction.IN || action == QueryAction.NOT_IN) {
			if (values != null && values.length > 0) {

				StringBuffer stmp = new StringBuffer("");
				for (int i = 0; i < values.length; i++) {
					if (i == 0)
						stmp.append(String.valueOf(values[i]));
					else
						stmp.append("," + String.valueOf(values[i]));
				}
				append(att2FName(fieldName) + " in (" + stmp.toString() + ")",
						true);
			}
		} else
			logger.warn("where(int[]):parameters error!");

	}

	protected void where(String fieldName, Integer value, int queryAction) {
		if (value == null)
			return;
		where(fieldName, value.intValue(), queryAction);
	}

	/**
	 * 构造形如 field in subQuery
	 */
	protected void where(String field, IQuery subQuery) {

		String sql = String.format(" %s in (%s )", new Object[] { field,
				subQuery.getHQL() });
		append(sql, true);
		fieldValues.addAll(subQuery.getValues());
	}

	protected void where(String fieldName, long value) {
		where(fieldName, value, QueryAction.EQUAL);
	}

	protected void where(String fieldName, long value, int queryAction) {

		if (queryAction == QueryAction.LIKE
				|| queryAction == QueryAction.BETWEEN
				|| queryAction == QueryAction.IN)
			return;
		String oper = getOperation(queryAction);
		if (!oper.equals("")) {
			append(att2FName(fieldName) + oper, true);
			fieldValues.add(new DefaultCondition(value, false, Types.BIGINT));
		}
	}

	protected void where(String fieldName, String value) {
		where(fieldName, value, QueryAction.EQUAL);
	}

	protected void where(String fieldName, String value, int queryAction) {
		if (value == null || value.trim().equals(""))
			return;
		if (queryAction == QueryAction.BETWEEN)
			return;
		value = value.trim();
	    value = value.toLowerCase();//不区分大小写
		String oper = getOperation(queryAction);
		if (!oper.equals("")) {
			append("lower("+att2FName(fieldName)+")"+ oper, true);

			switch (queryAction) {
			case QueryAction.LIKE:
				value = "%" + value + "%";
				break;
			case QueryAction.LIKE_PREFIX:
				value = "%" + value;
				break;
			case QueryAction.LIKE_POSTFIX:
				value = value + "%";
				break;
			}

			fieldValues.add(new DefaultCondition(value, false, Types.VARCHAR));
		} else {
            if(queryAction == QueryAction.CUSTOM) {
                append(fieldName,true);
                fieldValues.add(new DefaultCondition(value, false, Types.VARCHAR));
            }
        }
	}

	protected void where(String fieldName, String value, int queryAction,
			String patterns) {
		if (value == null || value.trim().equals(""))
			return;
		if (patterns == null || patterns.trim().equals(""))
			where(fieldName, value, queryAction);
		else {
			Date d = Utils.valueOf(value, patterns, "");
			if (d != null)
				where(fieldName, d, queryAction);
		}
	}

	protected void where(String fieldName, String[] values) {
		if (values == null || values.length < 1)
			return;

		append(att2FName(fieldName) + " in (", true);
		for (int i = 0; i < values.length; i++) {
			if (i == 0)
				append("?", false);
			else
				append(",?", false);
			fieldValues.add(new DefaultCondition(values[i], false,
					Types.VARCHAR));
		}
		append(")", false);
	}

	protected void where(String[] fieldNames, String value, int queryAction) {
		if (fieldNames == null || fieldNames.length < 1 || value == null
				|| value.trim().equals(""))
			return;

		if (queryAction == QueryAction.BETWEEN)
			return;

		String oper = getOperation(queryAction);

		if (!oper.equals("")) {
			append("(", true);
			for (int i = 0; i < fieldNames.length; i++) {
				if (i < fieldNames.length - 1)
					append(att2FName(fieldNames[i]) + oper + " or ", false);
				else
					append(att2FName(fieldNames[i]) + oper, false);
				fieldValues.add(new DefaultCondition(value, false,
						Types.VARCHAR));
			}
			append(")", false);
		}
	}

	protected void whereAsDate(String fieldName, String value, int queryAction) {
		if (value == null || value.equals(""))
			return;
		Date date = Utils.valueOf(value, "yyyy-MM-dd", "");
		where(fieldName, date, queryAction);
	}

	protected void whereAsInt(String fieldName, String value, int queryAction) {
		if (value == null || value.equals(""))
			return;
		try {
			int v = Integer.parseInt(value);
			where(fieldName, v, queryAction);

		} catch (Exception e) {

		}

	}
}
