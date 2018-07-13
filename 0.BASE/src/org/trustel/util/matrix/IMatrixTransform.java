package org.trustel.util.matrix;

/**
 * 当使用没有继承并实现IMatrixItem接口的数据集生成矩阵时使用本接口进行数据转换
 * 
 * @author 万志勇
 * 
 */
public interface IMatrixTransform {
	/**
	 * 将结果集的数据转换成符合矩阵标准的数据对象
	 * 
	 * 一般建议转换成MatrixItem,在输出数据时可直接使用原始数据进行输出
	 * 
	 * @return IMatrixItem
	 */
	public IMatrixItem transform(Object data);

	/**
	 * 单元格数据含输出样式和各种链接
	 * 
	 * @param item
	 *            数据项目
	 * @param rowIndex
	 *            行，从0开始计算
	 * @param colIndex
	 *            列，从0开始计算
	 * @return 格式化数据
	 */
	public String getMatrixCell(IMatrixItem item, int rowIndex, int colIndex);

	/**
	 * 水平标题单元格数据含输出样式和各种链接
	 * 
	 * @param item
	 *            数据项目
	 * @param rowIndex
	 *            行，从0开始计算
	 * @param colIndex
	 *            列，从0开始计算
	 * @return 格式化数据
	 */
	public String getRowTitle(String hFlag, int rowIndex, int colIndex);

	/**
	 * 最后一个数据单元格输出后的后续处理,一般，本方法返回一个空字符串即可
	 * 
	 * @param item
	 *            数据项目
	 * @param rowIndex
	 *            行，从0开始计算
	 * @param colIndex
	 *            列，从0开始计算
	 * @return 格式化数据
	 */
	public String getAfterMaxtrixLastCell(IMatrixItem item, int rowIndex,
			int colIndex);

	/**
	 * 
	 * @param rowIndex
	 * @return 在输出行前输出处理
	 */
	public String doStartRow(int rowIndex);

	/**
	 * 
	 * @param rowIndex
	 * @return 在输出行后输出处理
	 */
	public String doFinishRow(int rowIndex);
}
