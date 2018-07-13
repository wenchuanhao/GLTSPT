package org.trustel.common;

import org.trustel.common.ITreeItem;

public interface ITreeItemGenerator {

	public String afterOutItem(ITreeItem item, int level, int index,
			int siblingCount, int childCount);

	/**
	 * 作为TreeUtils的输入参数，用来自定义输出信息的展示方式
	 * 
	 * @param item
	 * @param level
	 * @param index
	 * @param siblingCount
	 * @param childCount
	 * @return
	 */
	public String execute(ITreeItem item, int level, int index,
			int siblingCount, int childCount);

}
