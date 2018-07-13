package com.trustel.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

/**
 * @author Administrator
 *
 * 公式计算器
 */
public class FormulaCalculator {
	/**
	 * 加号
	 */
	public static final String PLUS = "+";

	/**
	 * 减号
	 */
	public static final String MINUS = "-";

	/**
	 * 乘号
	 */
	public static final String TIME = "*";

	/**
	 * 除号
	 */
	public static final String DIVIDE = "/";

	/**
	 * 左括号
	 */
	public static final char LEFT_BRACKET = '(';

	/**
	 * 右括号
	 */
	public static final char RIGHT_BRACKET = ')';

	public static final char COMMA = ',';

	public static final char QUESTION = '?';

	/**
	 * 数据库连接
	 */
	private Session session;

	/**
	 * 预定义函数
	 */
	private HashMap functions;

	/**
	 * 计算公式
	 */
	private String formula;

	private FormulaCalculator() {

	}

	/**
	 * 
	 * @param session 数据库连接
	 * @param functions 预定义函数
	 * @param formula 公式表达式
	 */
	public FormulaCalculator(Session session, HashMap functions, String formula) {
		this.session = session;
		this.functions = functions;
		this.formula = formula;
	}

	/**
	 * 公式计算
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public double calculate(HashMap variables) throws RuntimeException {
		double result = 0;
		String symbol = null;
		List partResults = new ArrayList();
		List operators = new ArrayList();

		// 预处理逻辑表达式
		StringBuffer buffer = preProcess(formula);

		if (buffer.length() > 0) { // if no
			do {
				StringBuffer part = new StringBuffer();
				symbol = getPart(buffer, part);

				if (symbol != null) {
					// 计算已分割部分
					partResults.add(new FormulaCalculator(session, functions,
							part.toString()).calculate(variables));
					operators.add(symbol);

					// 清除剩余部分的多余空格
					buffer = new StringBuffer(buffer.toString().trim());
				} else { // 处理基本逻辑表达式
					if (partResults.size() == 0) {
						try {
							Double unit = Double.parseDouble(buffer.toString());
							partResults.add(unit);
						} catch (NumberFormatException e) {
							partResults.add(calculate(buffer.toString(),
									variables));
						}
					} else
						partResults.add(new FormulaCalculator(session,
								functions, buffer.toString())
								.calculate(variables));

				}
			} while (symbol != null);

			result = (Double) partResults.get(0);
			for (int i = 1; i < partResults.size(); i++) {
				String operator = (String) operators.get(i - 1);
				if (operator.compareTo(PLUS) == 0) {
					result += (Double) partResults.get(i);
				} else if (operator.compareTo(MINUS) == 0) {
					result -= (Double) partResults.get(i);
				} else if (operator.compareTo(TIME) == 0) {
					result *= (Double) partResults.get(i);
				} else {
					result /= (Double) partResults.get(i);
				}
			}
		}

		return result;
	}

	/**
	 * 取计算单元
	 * 
	 * @param expression
	 *            表达式
	 * @param part
	 *            计算单元
	 * @return
	 */
	private String getPart(StringBuffer expression, StringBuffer part) {
		String operator = null;
		int startIndex = 0, index = expression.length();

		int leftBracketIndex = expression.indexOf(LEFT_BRACKET + "");

		if (leftBracketIndex == 0)
			startIndex = getCorrespondingBracket(expression.toString());

		int plusIndex = expression.indexOf(PLUS, startIndex);
		if (plusIndex != -1) {
			index = plusIndex;
			operator = PLUS;
		}

		int minusIndex = expression.indexOf(MINUS, startIndex);
		if (minusIndex != -1 && minusIndex < index) {
			index = minusIndex;
			operator = MINUS;
		}

		int timeIndex = expression.indexOf(TIME, startIndex);
		if (timeIndex != -1 && timeIndex < index) {
			index = timeIndex;
			operator = TIME;
		}
		int divideIndex = expression.indexOf(DIVIDE, startIndex);
		if (divideIndex != -1 && divideIndex < index) {
			index = divideIndex;
			operator = DIVIDE;
		}

		if (operator != null) {
			part.append(expression.substring(0, index));
			expression.delete(0, index + operator.length());
		}

		return operator;
	}

	/**
	 * 取左括号的对应右括号
	 * <p>
	 * 当存在多层括号嵌套时，需要找到对应的右括号
	 * 
	 * @param expression
	 *            表达式
	 * @return
	 * @throws RuntimeException
	 */
	private int getCorrespondingBracket(String expression)
			throws RuntimeException {
		int index = 1, level = 1;
		int leftBracketIndex, rightBracketIndex;

		do {
			leftBracketIndex = expression.indexOf(LEFT_BRACKET, index);
			rightBracketIndex = expression.indexOf(RIGHT_BRACKET, index);

			if (rightBracketIndex == -1) {
				throw new RuntimeException(expression + "中缺少对应的右括号");
			} else if (leftBracketIndex == -1
					|| leftBracketIndex > rightBracketIndex) {
				index = rightBracketIndex + 1;
				level--;
			} else {
				index = leftBracketIndex + 1;
				level++;
			}
		} while (level > 0);

		return index - 1;
	}

	/**
	 * 表达式预处理
	 * <p>
	 * 对逻辑表达式进行预处理，去掉多余的空格、括号对等。
	 * 
	 * @param expression
	 *            逻辑表达式
	 * @return 逻辑表达式
	 * @throws WorkflowEngineException
	 */
	private StringBuffer preProcess(String expression) throws RuntimeException {
		expression = expression.trim();

		while (expression.charAt(0) == LEFT_BRACKET
				&& getCorrespondingBracket(expression) == expression.length() - 1) {

			expression = expression.substring(1, expression.length() - 1);

			// 去掉前后的空格
			expression = expression.trim();
		}

		return new StringBuffer(expression);
	}

	/**
	 * 计算函数值
	 * 
	 * @param expression
	 *            函数表达式
	 * @return
	 * @throws RuntimeException
	 */
	private double calculate(String expression, HashMap variables)
			throws RuntimeException {
		FunctionDefine define = null;
		Object[] args = null;
		int leftBracketIndex = expression.indexOf(LEFT_BRACKET);

		if (leftBracketIndex == -1)
			define = (FunctionDefine) functions.get(expression.trim()
					.toLowerCase());
		else {
			define = (FunctionDefine) functions.get(expression.substring(0,
					leftBracketIndex).trim().toLowerCase());

			int rightBracketIndex = leftBracketIndex
					+ getCorrespondingBracket(expression
							.substring(leftBracketIndex));
			args = getParameters(expression.substring(leftBracketIndex + 1,
					rightBracketIndex), variables);
		}

		if (define == null)
			return variables.get(expression.trim()) == null ? 0
					: (Double) ((Variable) variables.get(expression.trim())).value;

		return calculate(define, args);
	}

	/**
	 * 根据函数定义、参数计算函数值
	 * 
	 * @param define
	 *            函数定义
	 * @param args
	 *            参数
	 * @return
	 * @throws RuntimeException
	 */
	private double calculate(FunctionDefine define, Object[] args)
			throws RuntimeException {
		double result = 0;

		try {
			Class c = Class.forName(define.className);
			Object function = c.newInstance();
			Method[] methods = c.getMethods();

			for (int i = 0; i < methods.length; i++) {
				String name = methods[i].getName();
				if (name.equalsIgnoreCase("calculate")) {
					if (args == null
							&& methods[i].getParameterTypes().length == 1) {
						result = (Double) methods[i].invoke(function,
								new Object[] { session });
						break;
					} else if (args != null
							&& methods[i].getParameterTypes().length == 2) {
						result = (Double) methods[i].invoke(function,
								new Object[] { session, args });
						break;
					}
				}
			}
		} catch (InstantiationException e) {
			throw new RuntimeException(e.getClass().getName() + ": "
					+ e.getMessage() + " at " + define.className);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e.getClass().getName() + ": "
					+ e.getMessage() + " at " + define.className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getClass().getName() + ": "
					+ e.getMessage() + " at " + define.className);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getClass().getName() + ": "
					+ e.getTargetException().getMessage() + " at "
					+ define.className);
		}

		return result;
	}

	/**
	 * 取函数参数
	 * 
	 * @param expression
	 *            参数表达式
	 * @return
	 * @throws RuntimeException
	 */
	private String[] getParameters(String expression, HashMap variables)
			throws RuntimeException {
		int startIndex = 0;
		List parameters = new ArrayList();

		do {
			int leftBracketIndex = expression.indexOf(LEFT_BRACKET, startIndex);
			int commaIndex = expression.indexOf(COMMA, startIndex);

			if (leftBracketIndex != -1 && leftBracketIndex < commaIndex)
				commaIndex = expression.indexOf(COMMA, leftBracketIndex
						+ getCorrespondingBracket(expression
								.substring(leftBracketIndex)));

			parameters.add(new FormulaCalculator(session, functions,
					commaIndex == -1 ? expression.substring(startIndex)
							: expression.substring(startIndex, commaIndex))
					.calculate(variables)
					+ "");

			startIndex = commaIndex + 1;
		} while (startIndex != 0);

		String[] args = new String[parameters.size()];
		for (int i = 0; i < args.length; i++)
			args[i] = (String) parameters.get(i);

		return args;
	}
}
