package org.trustel.util.matrix;

/**
 * 按中国式坐标理解纵横坐标
 * 
 * @author 万志勇
 * 
 */
public interface IMatrixItem {

	/**
	 * 取水平维度（表格的第一行）的值 <BR>
	 * 必须是唯一值
	 * 
	 * @return 可以是多个字段合并而成
	 */
	public String getXValue();

	/**
	 * 取纵向维度(数据行标记)的值 <BR>
	 * 必须是唯一值
	 * 
	 * @return 纵向维度标志
	 */
	public String getYValue();

	/**
	 * 是否生成一个新的矩阵表
	 * 
	 * @param XValue
	 *            分类/水平维度数组
	 * @return true-生成新的矩阵表，false-同一个维度表
	 */
	public boolean newMatrix(String XValue);

}
