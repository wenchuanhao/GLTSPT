package org.trustel.service.form;

public interface IPageQueryForm extends IExportForm {
	/**
	 * 
	 * @return 当前页，从1开始
	 */
	public int getPageIndex();

	/**
	 * 
	 * @return 分页大小,缺省为25条每页
	 */
	public int getPageSize();

	public void setPageIndex(int pageIndex);

	public void setPageSize(int pageSize);
}
