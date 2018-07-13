package org.trustel.util.matrix;

import org.trustel.common.Utils;

public abstract class DefaultMatrixTransform implements IMatrixTransform {

	/**
	 * 
	 * @param rowIndex
	 * @return 在输出行前输出处理
	 */
	public String doStartRow(int rowIndex) {
		return "<tr align=right>";
	}

	/**
	 * 
	 * @param rowIndex
	 * @return 在输出行后输出处理
	 */
	public String doFinishRow(int rowIndex) {
		return "</tr>\n";
	}

	public String getAfterMaxtrixLastCell(IMatrixItem item, int rowIndex,
			int colIndex) {
		return "";
	}

	protected String valueOf(int value) {
		return Utils.zero2Str(value, "&nbsp;");
	}

	protected String valueOf(long value) {
		return Utils.zero2Str(value, "&nbsp;");
	}

	protected String valueOf(double value, int scale) {
		return Utils.zero2Str(value, "&nbsp;", scale);
	}

	protected String valueOf(float value, int scale) {
		return Utils.zero2Str(value, "&nbsp;", scale);
	}

}
