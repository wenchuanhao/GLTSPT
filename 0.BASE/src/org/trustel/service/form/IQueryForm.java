package org.trustel.service.form;

public interface IQueryForm extends IExportForm {

	/**
	 * 
	 * @return 页面操作行为
	 */
	public String getAction();

	/**
	 * 
	 * @return 需要处理的主键字段
	 */
	public String[] getChk();

	public void setAction(String action);

	public void setChk(String[] chk);
}
