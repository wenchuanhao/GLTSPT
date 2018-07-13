package org.trustel.service.form;

import org.trustel.util.ParameterFactory;

public class PageQueryForm extends QueryForm implements IPageQueryForm
{

	private int pageIndex;
	private int pageSize;

	public int getPageIndex()
	{
		return pageIndex < 1 ? 1 : pageIndex;
	}

	public int getPageSize()
	{
		return pageSize < 1 ? ParameterFactory.getInt("SYSTEM_DEFAULTPAGESIZE", 15) : pageSize;
	}

	public int getPageSize1()
	{
		return pageSize;
	}
	public void setPageIndex(int pageIndex)
	{
		this.pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

}
