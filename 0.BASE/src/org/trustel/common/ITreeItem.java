package org.trustel.common;

/**
 * 类 名：目录树接口
 * 
 * 日 期：2007-03-17
 * 
 * 设 计：万志勇
 * 
 * 版 本：0.0.0.1
 * 
 */
public interface ITreeItem extends IListItem {

	public String getUcode();

	public int getChildrenCount();
}
