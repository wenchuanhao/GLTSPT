package org.trustel.util;

import java.util.ArrayList;
import java.util.List;

import org.trustel.common.DefaultListItem;
import org.trustel.common.IListItem;


public class ArrayUtils {
	/**
	 * 将数组转换为列表
	 * 
	 * @param items
	 * @return
	 */
	public static List<?> arrayToList(Object[] items) {

		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < items.length; i++)
			list.add(items[i]);

		return list;
	}

	/**
	 * 将数组转为特定格式的字符串
	 * 
	 * Example: values = new String[] { '1', '2', '3' };<br>
	 * System.out.println('delete from User where code in (' +
	 * Utils.arrayToSting(values, ',', true) + ')');<br>
	 * System.out.println('delete from User where code in (' +
	 * Utils.arrayToSting(values, ',', false) + ')');
	 * 
	 * @param values
	 *            字符数组
	 * @param split
	 *            分隔符
	 * @param asNumber
	 *            作为数字分隔
	 * @return
	 */
	public static String arrayToSting(String[] values, String split,
			boolean asNumber) {
		StringBuffer buf = new StringBuffer("");
		String fix = "'";
		if (asNumber)
			fix = "";
		if (values != null)
			for (int i = 0; i < values.length; i++)
				if (i == 0)
					buf.append(fix + values[i] + fix);
				else
					buf.append(split + fix + values[i] + fix);
		return buf.toString();
	}

	/**
	 * 数组转为字符串
	 * 
	 * @param items
	 * @param splitChar
	 * @return
	 */
	public static String arrayToString(int[] items, String splitChar) {
		StringBuffer buf = new StringBuffer("");
		if (items != null)
			for (int i = 0; i < items.length; i++)
				if (i > 0)
					buf.append(splitChar + items[i]);
				else
					buf.append(items[i]);
		return buf.toString();
	}

	/**
	 * 数组转为字符串
	 * 
	 * @param items
	 * @param splitChar
	 * @return
	 */
	public static String arrayToString(Object[] items, String splitChar) {
		StringBuffer buf = new StringBuffer("");
		if (items != null)
			for (int i = 0; i < items.length; i++)
				if (i > 0)
					buf.append(splitChar + items[i]);
				else
					buf.append(items[i]);
		return buf.toString();
	}

	/**
	 * 转换List<String>或List<?>为整型数组
	 */
	public static int[] convert(List<?> items) {
		return convert(ArrayUtils.listToArray(items));
	}

	/**
	 * 转换字符串数组为整型数组
	 */
	public static int[] convert(String[] items) {
		int[] ret = new int[items.length];
		for (int i = 0; i < items.length; i++)
			ret[i] = Integer.parseInt(items[i]);
		return ret;
	}

	/**
	 * 转换普通值--名称列表为IListItem列表，用来名称转换或列表输出
	 * 
	 * @param items
	 *            以值--名称编码的数据项,以半角逗号分隔
	 * @return IListItem列表
	 */
	public static List<IListItem> convertList(String[] items, String split) {
		List<IListItem> list = new ArrayList<IListItem>();

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				String[] arr = items[i].split(split);
				if (arr.length > 1)
					list.add(new DefaultListItem(arr[0], arr[1]));
				else
					list.add(new DefaultListItem(items[i], items[i]));
			}
		}
		return list;

	}

	/**
	 * 清洗列表的中项
	 * 
	 * @param items
	 *            IListItem列表
	 * @param codes
	 *            编码组
	 * @param excludes
	 *            true 返回结果中排除codes中的对象 否则，只返回 codes中的对象
	 * @return items IListItem列表
	 */
	public static List<?> filterList(List<?> items, String[] arrCode,
			boolean excludes) {
		List<Object> list = new ArrayList<Object>();
		if (arrCode == null) {
			if (excludes)
				list.addAll(items);
			return list;
		}
		String codes = "#@$%^&*;" + arrayToSting(arrCode, ";", true) + ";";
		if (items != null) {
			for (int i = 0; i < items.size(); i++) {
				IListItem item = (IListItem) items.get(i);
				boolean exist = codes.indexOf(";" + item.getCode() + ";") > 0;
				if (excludes) {
					if (!exist)
						list.add(item);
				} else {
					if (exist)
						list.add(item);
				}

			}
		}
		return list;
	}

	/**
	 * 将字符串列表转换为字符串数组
	 * 
	 * @param list
	 *            List<?> or List<String>
	 * @return
	 */
	public static String[] listToArray(List<?> list) {
		String[] names = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof IListItem) {
				names[i] = ((IListItem) list.get(i)).getCode();
			} else {
				names[i] = (String) list.get(i);
			}

		}
		return names;
	}

	/**
	 * 
	 * @param items
	 *            数字型字符数组
	 * @return 整形数组
	 */
	public static int[] strArrayToInt(String[] items) {
		return convert(items);
	}

	/**
	 * 将特定分隔符分隔的字符串转换为列表
	 * 
	 * @param source
	 *            源串
	 * @param splitchar
	 *            分隔符
	 * @return
	 */
	public static List<?> stringToList(String source, String splitchar) {

		if (source == null)
			return new ArrayList<Object>();
		else
			return arrayToList(source.split(splitchar));

	}
}
