package org.trustel.service.sql.a;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.trustel.common.Utils;


public abstract class AbstractModiableQuery implements IModifiableQuery {
	protected List<IField> items = new ArrayList<IField>();

	protected String tableName;

	/**
	 * 构造HQL语句，Hibernate使用
	 * 
	 * @param classType
	 *            O/R映射类名
	 */
	public AbstractModiableQuery(Class<?> pojo) {
		this.tableName = getClassName(pojo);
	}

	/**
	 * 构造SQL语句，JDBC使用
	 * 
	 * @param tableName
	 *            DBMS库表名称
	 */
	public AbstractModiableQuery(String tableName) {
		this.tableName = tableName;
	}

	private void add(IField item) {
		items.add(item);
	}

	/**
	 * 增加一个字节数组更新处理,在非Papare中不能使用。
	 * 
	 * BLOB字段时使用
	 * 
	 * @param fieldName
	 *            字段名，O/R表示属性名
	 * @param value
	 *            字段值
	 */
	public void addField(String fieldName, byte[] value) {
		add(new DefaultField(fieldName, value));
	}

	/**
	 * 日期字段
	 * 
	 * @param fieldName
	 * @param value
	 *            日期值。建议转换成 东八区(GMT+8)时间，取出时不转换
	 */
	public void addField(String fieldName, Date value) {
		add(new DefaultField(fieldName, value));
	}

	public void addField(String fieldName, double value) {
		add(new DefaultField(fieldName, value));
	}

	public void addField(String fieldName, float value) {
		add(new DefaultField(fieldName, value));
	}

	public void addField(String fieldName, int value) {

		add(new DefaultField(fieldName, value));
	}

	public void addField(String fieldName, long value) {
		add(new DefaultField(fieldName, value));
	}

	/**
	 * 
	 * @param fieldName
	 * @param value
	 * @param type
	 *            sql.Types中的值
	 */
	public void addField(String fieldName, Object value, int type) {
		add(new DefaultField(fieldName, value, type));
	}

	public void addField(String fieldName, String value) {
		add(new DefaultField(fieldName, value, Types.VARCHAR));
	}

	protected synchronized String getClassName(Class<?> pojo) {
		String name = pojo.getName();

		return name.substring(name.lastIndexOf('.') + 1);
	}

	protected Object getExpress(IField field, boolean prepare) {
		if (prepare)
			return "?";

		switch (field.getType()) {
		case Types.INTEGER:
			return field.getValue().toString();
		case Types.BIGINT:
			return field.getValue().toString();
		case Types.VARCHAR:
		case Types.CHAR:
			return "'" + field.getValue() + "'";
		case Types.DATE:
			return "'"
					+ Utils.format((Date) field.getValue(),
							"yyyy-MM-dd HH:mm:ss", "") + "'";

		case Types.TIME:
			return "'" + Utils.format((Date) field.getValue(), "HHmm:ss", "")
					+ "'";
		case Types.TIMESTAMP:
			return "'"
					+ Utils.format((Date) field.getValue(),
							"yyyy-MM-dd HHmm:ss", "") + "'";
		default:
			return field.getValue();

		}
	}

	/**
	 * 对应Hibernate返回HQL,JDBC返回SQL
	 * 
	 * 注意byte[]等数据只能使用参数方式
	 * 
	 * @param prepare
	 *            true表示返回使用参数的查询，否则所有参数合成在查询语句中
	 * @return
	 */
	protected abstract String getSQL(boolean prepare);

	public String getSQL() {
		return getSQL(false);
	}

	public String getPrepareSQL() {
		return getSQL(true);
	}

	/**
	 * 参数值
	 * 
	 * @return
	 */
	public List<IField> getValues() {
		return items;
	}

	public List<ICondition> value2Conditions() {
		List<ICondition> conditions = new ArrayList<ICondition>();
		for (int i = 0; i < items.size(); i++)
			conditions.add(items.get(i));
		return conditions;
	}
}
