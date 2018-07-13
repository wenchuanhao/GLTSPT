package org.trustel.service.sql;

import java.util.List;

import org.trustel.service.sql.a.AbstractModiableQuery;
import org.trustel.service.sql.a.ICondition;
import org.trustel.service.sql.a.IField;
import org.trustel.service.sql.a.IQuery;


public class InsertBuilder extends AbstractModiableQuery {

	public InsertBuilder(Class<?> pojo) {
		super(pojo);
	}

	public InsertBuilder(String tableName) {
		super(tableName);
	}

	public String getSQL(boolean prepare) {
		StringBuffer fields = new StringBuffer("");
		StringBuffer values = new StringBuffer("");
		for (int i = 0; i < items.size(); i++) {
			IField item = (IField) items.get(i);

			if (i == 0) {
				fields.append(item.getName());
				values.append(getExpress(item, prepare));
			} else {
				fields.append("," + item.getName());
				values.append("," + getExpress(item, prepare));
			}
		}
		if (tableName == null || tableName.trim().equals(""))
			return "(" + fields.toString() + ") values(" + values.toString()
					+ ")";
		else

			return "insert " + tableName + "(" + fields.toString()
					+ ") values(" + values.toString() + ")";

	}

	/**
	 * 
	 * @param builder
	 *            查询条件构建对象
	 * @param conditions
	 *            不能为空
	 * @return
	 * 
	 *         1.相当于InsertBuilder.getSQL(true)+" where "+builder.getWhere();<br>
	 *         etc. insert tableName(f1,f2 ...) select f1,f2 from tableName
	 *         where f3= ? and f4 like ?<br>
	 *         2.conditions必须初始化，返回UpdateBuilder和builder的Values之和
	 */
	public String merger(IQuery builder, List<ICondition> conditions) {
		return merger(builder, conditions, "*");
	}
	/**
	 * 
	 * @param builder
	 *            查询条件构建对象
	 * @param conditions
	 *            不能为空
	 * @return
	 * 
	 *         1.相当于InsertBuilder.getSQL(true)+" where "+builder.getWhere();<br>
	 *         etc. insert tableName(f1,f2 ...) select f1,f2 from tableName
	 *         where f3= ? and f4 like ?<br>
	 *         2.conditions必须初始化，返回UpdateBuilder和builder的Values之和
	 */
	public String merger(IQuery builder, List<ICondition> conditions,
			String queryFields) {
		String sql = getSQL(true);
		String sql1 = builder.getSQL(queryFields);
		for (int i = 0; i < items.size(); i++)
			conditions.add(items.get(i));
		List<ICondition> list = builder.getValues();
		for (int i = 0; i < list.size(); i++)
			conditions.add(list.get(i));

		return sql1.equals("") ? sql : sql + sql1;
	}

}
