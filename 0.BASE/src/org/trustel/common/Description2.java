package org.trustel.common;

/**
 * 
 * 类 名：通用描述信息
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-07-21
 * 
 * 
 * 描 述：本类用来支持国际化应用时涉及文本信息的国际化内容,关联数据为整形
 * 
 */

public class Description2 extends Text {
	/**
	 * 描述编码
	 */
	private int id;

	/**
	 * 关联信息编码
	 */
	private int parentId;

	/**
	 * 
	 * @return 描述编码(id<--id)
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return 关联信息编码(uid<--uid)
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * 设置描述编码
	 * 
	 * @param 描述编码
	 *            (id-->id)
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 设置关联信息编码
	 * 
	 * @param 关联信息编码
	 *            (uid-->uid)
	 */
	public void setParentId(int uid) {
		this.parentId = uid;
	}
}
