package org.trustel.service.form;

public interface IExportForm {

	/**
	 * 
	 * @return 导出N条记录
	 */
	public int getExportSize();

	/**
	 * 当使用PageQueryForm时，强制为pageSize*pageIndex
	 * 
	 * @return
	 */
	public int getSkipSize();

	/**
	 * 非页面使用
	 * 
	 * @return 是否导出模式，导出模式在计算数据时需要跳过skipsize条记录
	 */
	public boolean isExport();

	public void setExp(String export);

	public void setExportSize(int exportSize);
}
