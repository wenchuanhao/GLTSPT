package org.trustel.service.common;

import org.trustel.util.SimpleHashMap;

/**
 * 
 * 类 名：JDBC数据转换类
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-03-21
 * 
 * 
 * 描 述：将当前数据行转换成指定对象返回
 * 
 */
public interface IORTransform {
	/**
	 * 
	 * @param rowIndex
	 *            当前行号
	 * @param fields
	 *            当前行所对应的哈唏表，可以用字段名/别名获取对应值
	 * @return 转换后对象
	 */
	public Object transform(long rowIndex, SimpleHashMap fields);

}
