package org.trustel.service.sql;

import java.util.List;

import org.trustel.service.sql.a.AbstractModiableQuery;
import org.trustel.service.sql.a.ICondition;
import org.trustel.service.sql.a.IField;
import org.trustel.service.sql.a.IQuery;


public class UpdateBuilder extends AbstractModiableQuery {

	public UpdateBuilder(Class<?> classType) {
		super(classType);
	}

	public UpdateBuilder(String tableName) {
		super(tableName);
	}

	public String getSQL(boolean prepare) {
		StringBuffer sql = new StringBuffer("");
		for (int i = 0; i < items.size(); i++) {
			IField item = items.get(i);
			if (i == 0)
				sql.append(item.getName() + "=" + getExpress(item, prepare));
			else
				sql.append("," + item.getName() + "="
						+ getExpress(item, prepare));
		}
		if (tableName == null || tableName.trim().equals(""))
			return sql.toString();
		else

			return "update " + tableName + " set " + sql.toString();
	}

	/**
	 * 
	 * @param builder
	 *            查询条件构建对象
	 * @param conditions
	 *            不能为空
	 * @return
	 * 
	 *         1.相当于UpdateBuilder.getSQL(true)+" where "+builder.getWhere();<br>
	 *         etc. update tableName set f1=?f2=? where f3= ? and f4 like ?<br>
	 *         2.conditions必须初始化，返回UpdateBuilder和builder的Values之和
	 */
	public String merger(IQuery builder, List<ICondition> conditions) {
		String sql = getSQL(true);
		String where = builder.getWhere();
		for (int i = 0; i < items.size(); i++)
			conditions.add(items.get(i));
		List<ICondition> list = builder.getValues();
		for (int i = 0; i < list.size(); i++)
			conditions.add(list.get(i));

		return where.equals("") ? sql : sql + where;
	}


}
