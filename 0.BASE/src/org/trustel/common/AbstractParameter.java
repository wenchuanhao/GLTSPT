package org.trustel.common;

/**
 * 
 * 类 名：系统参数[通用]
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-03-22 
 * 
 * 数据源：通常来自系统参数表system_parameters,可以在配置文件中指定
 * 
 * 描 述：配置时使用子类映射
 * 
 **/
public abstract class AbstractParameter implements IListItem {

	/**
	 * 参数编码
	 */
	private String code;
	/**
	 * 参数名称
	 */
	private String name;
	/**
	 * 参数值
	 */
	private String value;
	/**
	 * 参数类型
	 */
	private Integer category = 0;

	/**
	 * 
	 * @return 参数编码(code<--code)
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @return 参数名称(name<--name)
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return 参数值(value<--value)
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @return 参数类型(category<--category)
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 *设置参数编码
	 * 
	 * @param 参数编码
	 *            (code-->code)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 *设置参数名称
	 * 
	 * @param 参数名称
	 *            (name-->name)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *设置参数值
	 * 
	 * @param 参数值
	 *            (value-->value)
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 *设置参数类型
	 * 
	 * @param 参数类型
	 *            (category-->category)
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	public String toString() {
		String ret = String.format("c=%s n=%s v=%s", new Object[] { code,
				getName(), getValue() });
		return ret;
	}

	public String getTitle() {
		return name;
	}

}
