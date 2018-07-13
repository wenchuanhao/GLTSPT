package org.trustel.common;

/**
 * 类 名：九级结构模型（整型）
 * 
 * 日 期：2007-11-14
 * 
 * 设 计：万志勇
 * 
 * 版 本：0.0.0.1
 * 
 * 功能说明: RANK：本节所属组织结构中的等级（从1开始编码） rankN(N:1..9):表示本节点的第N级上级节点的编码
 */
public interface IIntHierarchy extends ITreeItem {

	/**
	 * 
	 * @return 编码
	 */
	public int getId();

	/**
	 * 
	 * @return 上级节点编码
	 */
	public int getParentId();

	/**
	 * 
	 * @return 所在级别
	 */
	public int getRank();

	/**
	 * 
	 * @return 名称
	 */
	public String getTitle();

	/**
	 * 
	 * @return 一级编码
	 */
	public int getRank1();

	/**
	 * 
	 * @return 二级编码
	 */
	public int getRank2();

	/**
	 * 
	 * @return 三级编码
	 */
	public int getRank3();

	/**
	 * 
	 * @return 四级编码
	 */
	public int getRank4();

	/**
	 * 
	 * @return 五级编码
	 */
	public int getRank5();

	/**
	 * 
	 * @return 六级编码
	 */
	public int getRank6();

	/**
	 * 
	 * @return 七级编码
	 */
	public int getRank7();

	/**
	 * 
	 * @return 八级编码
	 */
	public int getRank8();

	/**
	 * 
	 * @return 九级编码
	 */
	public int getRank9();

	/**
	 * 
	 * @return 描述
	 */
	public String getDescription();

}