package com.trustel.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Administrator
 * 
 * 查询条件的组成公式
 */
public class Formula {
	/**
	 * BigDecimal
	 */
	public final static int BIGDECIMAL = 1;

	/**
	 * BigInteger
	 */
	public final static int BIGINTEGER = 2;

	/**
	 * Binary
	 */
	public final static int BINARY = 3;

	/**
	 * Boolean
	 */
	public final static int BOOLEAN = 4;

	/**
	 * Byte
	 */
	public final static int BYTE = 5;

	/**
	 * Date
	 */
	public final static int DATE = 6;

	/**
	 * Double
	 */
	public final static int DOUBLE = 7;

	/**
	 * Float
	 */
	public final static int FLOAT = 8;

	/**
	 * Integer
	 */
	public final static int INTEGER = 9;

	/**
	 * Long
	 */
	public final static int LONG = 10;

	/**
	 * Short
	 */
	public final static int SHORT = 11;

	/**
	 * String
	 */
	public final static int STRING = 12;

	/**
	 * Text
	 */
	public final static int TEXT = 13;

	/**
	 * Time
	 */
	public final static int TIME = 14;

	/**
	 * Timestamp
	 */
	public final static int TIMESTAMP = 15;

	/**
	 * 公式(用于生成关联表之间的关系，例如：a.col1 = b.col2)
	 */
	public final static int FORMULA = 100;

	/**
	 * select语句
	 */
	public final static int SELECT = 101;

	/**
	 * =
	 */
	public final static int EQ = 1;

	/**
	 * <>
	 */
	public final static int NE = 2;

	/**
	 * <
	 */
	public final static int LT = 3;

	/**
	 * <=
	 */
	public final static int LE = 4;

	/**
	 * >
	 */
	public final static int GT = 5;

	/**
	 * >=
	 */
	public final static int GE = 6;

	/**
	 * like
	 */
	public final static int LK = 7;

	/**
	 * in
	 */
	public final static int IN = 8;

	/**
	 * not in
	 */
	public final static int NI = 9;

	/**
	 * is null
	 */
	public final static int NU = 10;

	/**
	 * is not null
	 */
	public final static int NN = 11;

	/**
	 * 变量名
	 */
	public String name;

	/**
	 * 类型
	 */
	public int type;

	/**
	 * 操作符
	 */
	public int operator;

	/**
	 * 值(数据类型、字符串表达式、select语句对象等)
	 */
	public Object value;

	/**
	 * 字段类型为字符串，操作符为等于的实例化函数
	 * 
	 * @param name
	 *            变量名
	 * @param value
	 *            变量值
	 */
	public static Formula newInstance(String name, Object value) throws RuntimeException {
		Formula formula = new Formula(name, value);
		formula.setValue();
		
		return formula;
	}

	/**
	 * 操作符为等于的实例化函数
	 * 
	 * @param name
	 *            变量名
	 * @param type
	 *            类型
	 * @param value
	 *            变量值
	 */
	public static Formula newInstance(String name, int type, Object value) throws RuntimeException {
		Formula formula = new Formula(name, type, value);
		formula.setValue();
		
		return formula;
	}

	/**
	 * 字段类型为字符串的实例化函数
	 * 
	 * @param operator
	 *            操作符
	 * @param name
	 *            变量名
	 * @param value
	 *            变量值
	 */
	public static Formula newInstance(int operator, String name, String value) throws RuntimeException {
		Formula formula = new Formula(operator, name, value);
		formula.setValue();
		
		return formula;
	}

	/**
	 * 实例化函数
	 * 
	 * @param name
	 *            变量名
	 * @param type
	 *            变量类型
	 * @param operator
	 *            操作符
	 * @param value
	 *            变量值
	 */
	public static Formula newInstance(String name, int type, int operator, Object value) throws RuntimeException {
		Formula formula = new Formula(name, type, operator, value);
		formula.setValue();
		
		return formula;
	}
	
	private Formula() {

	}

	/**
	 * 字段类型为字符串，操作符为等于的构造函数
	 * 
	 * @param name
	 *            变量名
	 * @param value
	 *            变量值
	 */
	private Formula(String name, Object value) {
		this.name = name;
		this.type = STRING;
		this.operator = EQ;
		this.value = value;
	}

	/**
	 * 操作符为等于的构造函数
	 * 
	 * @param name
	 *            变量名
	 * @param type
	 *            类型
	 * @param value
	 *            变量值
	 */
	private Formula(String name, int type, Object value) {
		this.name = name;
		this.type = type;
		this.operator = EQ;
		this.value = value;
	}

	/**
	 * 字段类型为字符串的构造函数
	 * 
	 * @param operator
	 *            操作符
	 * @param name
	 *            变量名
	 * @param value
	 *            变量值
	 */
	private Formula(int operator, String name, String value) {
		this.name = name;
		this.type = STRING;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * @param name
	 *            变量名
	 * @param type
	 *            变量类型
	 * @param operator
	 *            操作符
	 * @param value
	 *            变量值
	 */
	private Formula(String name, int type, int operator, Object value) {
		this.name = name;
		this.type = type;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * 检查条件是否有效(如果变量未赋值且不是is null或is not null表达式，则条件无效)
	 * 
	 * @return
	 */
	public boolean isValid() {
		return !(value == null && operator != NU && operator != NN);
	}

	/**
	 * 判断value是否有效赋值变量
	 * 
	 * @return
	 */
	public boolean containValue() {
		return value != null && type != FORMULA && operator != NU && operator != NN;
	}

	/*
	 * 生成查询条件表达式
	 */
	public String toString() {
		String expression = null;

		if (value == null && operator != NU && operator != NN)
			expression = "";
		else {
			String variable = (type == FORMULA) ? (String) value : "?";
			switch (operator) {
			case EQ:
				expression = name + " = " + variable;
				break;

			case NE:
				expression = name + " <> " + variable;
				break;

			case LT:
				expression = name + " < " + variable;
				break;

			case LE:
				expression = name + " <= " + variable;
				break;

			case GT:
				expression = name + " > " + variable;
				break;

			case GE:
				expression = name + " >= " + variable;
				break;

			case LK:
				expression = name + " like " + variable;
				break;

			case IN:
				expression = name
						+ " in ("
						+ ((type == FORMULA || type == SELECT) ? value : ":"
								+ name) + ")";
				break;

			case NI:
				expression = name
						+ " not in ("
						+ ((type == FORMULA || type == SELECT) ? value : ":"
								+ name) + ")";
				break;

			case NU:
				expression = name + " is null";
				break;

			case NN:
				expression = name + " is not null";
				break;
			}
		}

		return expression;
	}

	/**
	 * 将空字符串设置为null
	 * 
	 * @param value
	 *            变量值
	 */
	private void setValue() throws RuntimeException {
		if (value != null) {
			switch (type) {
			case BIGDECIMAL:
				if (! (value instanceof BigDecimal))
					throw new RuntimeException("变量" + name + "的类型定义为BigDecimal，实际赋值类型为" + value.getClass().getName());
				break;

			case BIGINTEGER:
				if (! (value instanceof BigInteger))
					throw new RuntimeException("变量" + name + "的类型定义为BigInteger的实际赋值类型为" + value.getClass().getName());
				break;

			case BINARY:
				if (! (value instanceof Byte[]))
					throw new RuntimeException("变量" + name + "的类型定义为Binary的实际赋值类型为" + value.getClass().getName());
				break;

			case BOOLEAN:
				if (! (value instanceof Boolean))
					throw new RuntimeException("变量" + name + "的类型定义为Boolean的实际赋值类型为" + value.getClass().getName());
				break;

			case BYTE:
				if (! (value instanceof Byte))
					throw new RuntimeException("变量" + name + "的类型定义为Byte的实际赋值类型为" + value.getClass().getName());
				break;

			case DATE:
				if (! (value instanceof Date))
					throw new RuntimeException("变量" + name + "的类型定义为Date的实际赋值类型为" + value.getClass().getName());
				break;

			case DOUBLE:
				if (! (value instanceof Double))
					throw new RuntimeException("变量" + name + "的类型定义为Double的实际赋值类型为" + value.getClass().getName());
				break;

			case FLOAT:
				if (! (value instanceof Float))
					throw new RuntimeException("变量" + name + "的类型定义为Float的实际赋值类型为" + value.getClass().getName());
				break;

			case INTEGER:
				if (! (value instanceof Integer))
					throw new RuntimeException("变量" + name + "的类型定义为Integer的实际赋值类型为" + value.getClass().getName());
				break;

			case LONG:
				if (! (value instanceof Long))
					throw new RuntimeException("变量" + name + "的类型定义为Long的实际赋值类型为" + value.getClass().getName());
				break;

			case SHORT:
				if (! (value instanceof Short))
					throw new RuntimeException("变量" + name + "的类型定义为Short的实际赋值类型为" + value.getClass().getName());
				break;

			case STRING:
				if (! (value instanceof String))
					throw new RuntimeException("变量" + name + "的类型定义为String的实际赋值类型为" + value.getClass().getName());
				break;

			case TEXT:
				if (! (value instanceof String))
					throw new RuntimeException("变量" + name + "的类型定义为Text的实际赋值类型为" + value.getClass().getName());
				break;

			case TIME:
				if (! (value instanceof Date))
					throw new RuntimeException("变量" + name + "的类型定义为Time的实际赋值类型为" + value.getClass().getName());
				break;

			case TIMESTAMP:
				if (! (value instanceof Date))
					throw new RuntimeException("变量" + name + "的类型定义为TimeStamp的实际赋值类型为" + value.getClass().getName());

				break;
			}
		}

		if (value instanceof String && ((String) value).length() == 0)
			value = null;
	}
}