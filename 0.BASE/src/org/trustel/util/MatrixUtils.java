package org.trustel.util;

import java.util.ArrayList;
import java.util.List;

import org.trustel.util.matrix.IMatrixItem;
import org.trustel.util.matrix.IMatrixTransform;


/**
 * 本类用来处理分组字段和原始数据均在同个类的矩阵处理。 仅支持一维矩阵输出处理
 * 
 * @author 万志勇
 * 
 */
public class MatrixUtils {
	protected StringBuffer buf = new StringBuffer("");

	private boolean clearAfterOutRow = true;

	public int rowCount = 0;

	protected List<IMatrixItem> table = new ArrayList<IMatrixItem>();

	private IMatrixItem getCell(String x, String y, IMatrixTransform transform) {
		// 最多循环list.size()次
		IMatrixItem ret = null;
		for (int i = table.size() - 1; i >= 0; i--) {
			IMatrixItem item = table.get(i);
			if (item.getXValue().equalsIgnoreCase(x)
					&& item.getYValue().equalsIgnoreCase(y)) {
				ret = item;
			}
		}
		table.remove(ret);
		return ret;
	}

	private void getRows(String y, String[] titles,
			IMatrixTransform transform, int rowIndex) {

		buf.append(transform.doStartRow(rowIndex));
		buf.append(transform.getRowTitle(y, rowIndex, 0));
		for (int i = 0; i < titles.length; i++) {

			IMatrixItem item = getCell(titles[i], y, transform);
			buf.append(transform.getMatrixCell(item, rowIndex, i + 1));
		}
		buf.append(transform.getAfterMaxtrixLastCell(null, rowIndex,
				titles.length + 1));
		buf.append(transform.doFinishRow(rowIndex));
		if (clearAfterOutRow) {
			boolean find = false;
			for (int i = table.size() - 1; i >= 0; i--) {
				IMatrixItem item = table.get(i);
				if (item.getYValue().equalsIgnoreCase(y)) {
					// System.out.println(item.getHorizontalDimemsionValue()+"
					// VS "+ item.getVerticalDimemsionValue());
					table.remove(item);
					find = true;
				} else if (find) // 输出相同行后，找到第一个非行标记即认为数据已经清理完毕。用来提高输出速度
					break;
			}
		}

	}

	/**
	 * 将指定数据转换成特定数据矩阵,输出行信息后自动清除相同标记的数据
	 * 
	 * @param data
	 *            数据列表 必须按一定规则排序
	 * @param titleValues
	 *            标题列的编码 必须按显示列顺序排序
	 * @param transform
	 *            数据转换对象
	 * @return 转换后的数据
	 */
	public String matrix(List<?> data, String[] titleValues,
			IMatrixTransform transform) {
		return matrix(data, titleValues, transform, false);
	}

	/**
	 * 将指定数据转换成特定数据矩阵
	 * 
	 * @param data
	 *            数据列表 必须按一定规则排序
	 * @param titleValues
	 *            标题列的编码 必须按显示列顺序排序
	 * @param transform
	 *            数据转换对象
	 * @param clearAfterOutRow
	 *            在输出行数据按行标记清除相同标记数据（防止有相同标记但不需要输出的列）
	 * @return 转换后的数据
	 */

	public String matrix(List<?> data, String[] titleValues,
			IMatrixTransform transform, boolean clearAfterOutRow) {
		this.clearAfterOutRow = clearAfterOutRow;
		if (data == null || data.size() < 1 || titleValues == null
				|| titleValues.length < 1 || transform == null)
			return "";

		for (int i = data.size() - 1; i >= 0; i--) {
			table.add(transform.transform(data.get(i)));
		}

		String lastY = null;
		int row = 0, i = table.size() - 1;
		// 最多循环table.size次
		while (table.size() > 0 && (i >= 0)) {
			IMatrixItem item = table.get(i);
			i--;
			String y = item.getYValue();
			// 在同一行不需要特殊处理
			if (!y.equals(lastY)) // // 在新的一行
			{
				row++;
				getRows(y, titleValues, transform, row);
				if (!clearAfterOutRow)
					table.remove(item);
				i = table.size() - 1;
				lastY = y;
			}

		}
		rowCount = row;
		return buf.toString();
	}

}