package org.trustel.common;

import java.util.Date;
import java.util.List;

import org.trustel.util.ArrayUtils;
import org.trustel.util.DateUtils;
import org.trustel.util.HTMLUtils;
import org.trustel.util.NumberUtils;
import org.trustel.util.StringUtils;

/**
 * 通用工具类
 * 
 * @author 万志勇
 * 
 * @version V4.0 2011年3月2日 在V3.0版本（2008年12月）上改进完成
 * 
 * <pre>
 *   1.提供数据类型转换、数据格式化功能;
 *   2.及HTML组件信息输出（如下拉列表、树状列表以及按钮组等）;
 *   3.提供日期运算如时区转换、日期差异和加减去处等；
 *   4.提供列表到数据转换
 * 
 * </pre>
 */
public class Utils extends DateUtils {

	/**
	 * 将数组转换为列表
	 * 
	 * @param items
	 *            对象数组
	 * @return 列表
	 * 
	 */
	public static List<?> arrayToList(Object[] items) {
		return ArrayUtils.arrayToList(items);
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
		return ArrayUtils.arrayToSting(values, split, asNumber);
	}

	/**
	 * 数组转为字符串
	 * 
	 * @param items
	 * @param splitChar
	 * @return
	 */
	public static String arrayToString(int[] items, String splitChar) {
		return ArrayUtils.arrayToString(items, splitChar);
	}

	/**
	 * 数组转为字符串
	 * 
	 * @param items
	 * @param splitChar
	 * @return
	 */
	public static String arrayToString(Object[] items, String splitChar) {
		return ArrayUtils.arrayToString(items, splitChar);
	}

	/**
	 * 转换List<String>或List<?>为整型数组
	 */
	public static int[] convert(List<?> items) {
		return ArrayUtils.convert(items);
	}

	/**
	 * 转换字符串数组为整型数组
	 */
	public static int[] convert(String[] items) {
		return ArrayUtils.convert(items);
	}

	/**
	 * 转换普通值--名称列表为IListItem列表，用来名称转换或列表输出
	 * 
	 * @param items
	 *            以值--名称编码的数据项,以半角逗号分隔
	 * @return IListItem列表
	 */
	public static List<IListItem> convertList(String[] items, String split) {
		return ArrayUtils.convertList(items, split);
	}

	/**
	 * 取两个日期的差值
	 * 
	 * @param from
	 *            开始时间
	 * @param to
	 *            结束时间
	 * @param part
	 *            Time_Minute--相关多少分,Time_HourOfDay-时，other-天
	 * @return 差值
	 */
	public static long diff(Date from, Date to, int part) {
		return DateUtils.diff(from, to, part);
	}

	/**
	 * 清洗列表的中项
	 * 
	 * @param items
	 *            IListItem列表
	 * @param codes
	 *            编码组
	 * @return items IListItem列表
	 */
	public static List<?> filterList(List<?> items, String[] arrCode) {
		return ArrayUtils.filterList(items, arrCode, false);
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
		return ArrayUtils.filterList(items, arrCode, excludes);
	}

	/**
	 * 返回以0作前缀的指定长度的序列号
	 */
	public static String fixLength(String value, int fixLen) {
		return StringUtils.fixLength(value, fixLen);
	}

	/**
	 * 返回指定名称和长度以及指定前缀的序列号，通常使用0作前缀
	 */
	public static String fixLength(String value, int fixLen, char fixChar) {
		return StringUtils.fixLength(value, fixLen, fixChar);
	}

	public static String format(double d, int scale) {
		return NumberUtils.format(d, scale);
	}

	public static String getButtonsByList(List<?> list, int defaultValue,
			String buttonName, String ext, boolean radio) {
		return getButtonsByList(list, String.valueOf(defaultValue), buttonName,
				ext, radio);
	}

	public static String getButtonsByList(List<?> list, String defaultValue,
			String buttonName, String ext, boolean radio) {
		return HTMLUtils.getButtonsByList(list, defaultValue, buttonName, ext,
				radio);
	}

	/**
	 * 通过标题返回编码
	 * 
	 * @param list
	 *            IListItem列表
	 * @param code
	 *            编码
	 * @return 名称或标题
	 */
	public static String getCodeByTitle(List<?> list, String title) {
		return HTMLUtils.getCodeByTitle(list, title);
	}

	/**
	 * 返回导出成EXCEL的文件名
	 */
	public static String getContentDisposition(String fileName, String pattern) {
		return "attachment;filename="
				+ getExportExcelFileName(fileName, pattern);
	}

	public static String getContentTypeForHTML(String charset, boolean xhtml) {
		String ret = "<meta http-equiv='Content-Type' content='text/html; charset="
				+ charset + "'";
		if (xhtml)
			return ret + " />";
		else
			return ret + ">";

	}

	/**
	 * 当前缺省值，在查询条件输出之后需要立即取出，否则在下次合成查询界面时改变
	 * 有时填充下列表时，没有查询条件，默认第一项为查询的值。此时可能需要取到当前列表的缺省值是什么。
	 * 
	 * @return
	 */
	public static String getCurrentValue() {
		return HTMLUtils.getCurrentValue();
	}

	public static String getExcelContentType(String charset) {
		return "application/vnd.ms-excel; charset=" + charset;
	}

	/**
	 * 取标准的导出文件名
	 * 
	 * @param functionName
	 *            功能名称
	 * @param pattern
	 *            日期格式 如yyyy-MM-dd_HHmm
	 * @return 文件名
	 */
	public static String getExportExcelFileName(String functionName,
			String pattern) {

		Date d = new Date();
		String fileName = functionName + "["
				+ Utils.format(d, pattern, "GMT+8") + "]" + ".xls";
		try {
			return new String(fileName.getBytes("gb2312"), "iso8859-1");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return fileName;
		}
	}

	/**
	 * 根据编码取相应对象
	 * 
	 * @param list
	 *            IListItem对象列表
	 * @param code
	 *            对象ID
	 * @return IListItem
	 */
	public static IListItem getItemById(List<?> list, int code) {
		return HTMLUtils.getItemById(list, code);
	}

	/**
	 * 根据编码取相应对象
	 * 
	 * @param list
	 *            IItemList对象列表
	 * @param code
	 *            对象ID
	 * @return IListItem
	 */
	public static IListItem getItemById(List<?> list, String code) {
		return HTMLUtils.getItemById(list, code);
	}

	/**
	 * 根据标题取相应对象
	 * 
	 * @param list
	 *            IListItem对象列表
	 * @param title
	 *            标题
	 * @return IListItem
	 */
	public static IListItem getItemByTitle(List<?> list, String Title) {
		return HTMLUtils.getItemByTitle(list, Title);
	}

	/**
	 * 生成从from到to的日期下拉选项
	 * 
	 * @param from
	 *            开始时间
	 * @param to
	 *            结束时间
	 * @param format
	 *            日期格式字符串
	 * @param titleformat
	 *            显示内容格式串
	 * @return 下拉列表选项
	 */
	public static String getMonthOption(Date from, Date to, String format,
			String titleformat, String defaultvalue) {
		return HTMLUtils.getMonthOption(from, to, format, titleformat,
				defaultvalue);
	}

	/**
	 * 返回下拉列表选项。
	 * 
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值，可以为空。
	 * @return HTML代码
	 */
	public static String getOptionsAsTree(List<?> list, int defaultValue) {
		return HTMLUtils.getOptionsAsTree(list, defaultValue);
	}

	/**
	 * 返回下拉列表选项。
	 * 
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值，可以为空。
	 * @param allowSelectTop
	 *            是充许选择顶级项
	 * @return HTML代码
	 */
	public static String getOptionsAsTree(List<?> list, int defaultValue,
			boolean allowSelectTop) {
		return HTMLUtils.getOptionsAsTree(list, defaultValue, allowSelectTop);
	}

	/**
	 * 返回下拉列表选项。
	 * 
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值，可以为空。
	 * @return HTML代码
	 */
	public static String getOptionsAsTree(List<?> list, String defaultValue) {
		return getOptionsAsTree(list, defaultValue, true);
	}

	/**
	 * 返回下拉列表选项。
	 * 
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值，可以为空。
	 * @param allowSelectTop
	 *            是充许选择顶级项
	 * @return HTML代码
	 */
	public static String getOptionsAsTree(List<?> list, String defaultValue,
			boolean allowSelectTop) {
		return HTMLUtils.getOptionsAsTree(list, defaultValue, allowSelectTop);
	}

	/**
	 * 返回下拉列表选项。
	 * 
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值，可以为空。
	 * @return HTML代码
	 */
	public static String getOptionsByList(List<?> list, int defaultValue) {
		return HTMLUtils.getOptionsByList(list, defaultValue);
	}

	/**
	 * 
	 * @param name
	 *            The name of Checkbox Control
	 * @param list
	 *            The key-value list of checkbos
	 * @param checkeds
	 *            The values that checked;
	 * @return
	 */
	public static String getCheckBoxGroupByList(String name, List<?> list,
			String[] checkedValues) {
		return HTMLUtils.getCheckBoxGroupByList(name, list, checkedValues);
	}

	/**
	 * 返回下拉列表选项。
	 * 
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值，可以为空。
	 * @return HTML代码
	 */
	public static String getOptionsByList(List<?> list, String defaultValue) {
		return HTMLUtils.getOptionsByList(list, defaultValue);
	}

	/**
	 * 
	 * @param from
	 *            开始日期
	 * @param to
	 *            截止日期
	 * @param format
	 *            Option-value中的年格式
	 * @param defaultvalue
	 *            当前缺省值
	 * @return 季度下拉列表
	 */
	public static String getSeasonOption(Date from, Date to, String format,
			String defaultvalue) {
		return HTMLUtils.getSeasonOption(from, to, format, defaultvalue);

	}

	/**
	 * 
	 * from 1 to 100 step 1 from 100 to 1 step -1
	 */
	public static String getSequenceOptions(int from, int to, int defaultvalue) {
		return HTMLUtils.getSequenceOptions(from, to, defaultvalue, 1);
	}

	public static String getSequenceOptions(int from, int to, int defaultvalue,
			int step) {
		return HTMLUtils.getSequenceOptions(from, to, defaultvalue, step);
	}

	/**
	 * 通过编码返回名称或标题
	 * 
	 * @param list
	 *            IListItem列表
	 * @param code
	 *            编码
	 * @return 名称或标题
	 */
	public static String getTitleByList(List<?> list, int code) {
		return HTMLUtils.getTitleByList(list, code);
	}

	/**
	 * 通过编码返回名称或标题
	 * 
	 * @param list
	 *            IListItem列表
	 * @param code
	 *            编码
	 * @return 名称或标题
	 */
	public static String getTitleByList(List<?> list, String code) {
		return HTMLUtils.getTitleByList(list, code);
	}

	/**
	 * 通过编码返回Tip
	 * 
	 * @param list
	 *            IListItem列表
	 * @param code
	 *            编码
	 * @return 名称或标题
	 */
	public static String getTipByList(List<?> list, int code) {
		return HTMLUtils.getTipByList(list, code);
	}

	/**
	 * 通过编码返回Tip
	 * 
	 * @param list
	 *            IListItem列表
	 * @param code
	 *            编码
	 * @return 名称或标题
	 */
	public static String getTipByList(List<?> list, String code) {
		return HTMLUtils.getTipByList(list, code);
	}

	/**
	 * 将字符串列表转换为字符串数组
	 * 
	 * @param list
	 * @return
	 */
	public static String[] listToArray(List<?> list) {
		return ArrayUtils.listToArray(list);
	}

	/**
	 * 将字符串转为HTML串，当源串为NULL或空时返回&nbsp;
	 * 
	 * @param source
	 * @return
	 */
	public static String null2HTML(String source) {
		return StringUtils.null2HTML(source);

	}

	/**
	 * 当字串超过长度时截短显示，为NULL时显示一个空格
	 * 
	 * @param source
	 * @param len
	 * @return
	 */
	public static String null2HTML(String source, int len) {
		return StringUtils.null2HTML(source, len);
	}

	public static String null2Str(Date value, String format, String timeZone) {
		return format(value, format, timeZone);
	}

	public static String null2Str(String source) {
		return null2Str(source, "");
	}

	/**
	 * 当字串超过指定长度时截断
	 * 
	 * @param source
	 *            源串
	 * @param max
	 *            最大长度
	 * @return 限制长度串
	 */
	public static String null2Str(String source, int max) {
		return StringUtils.null2Str(source, max);
	}

	public static String null2Str(String source, String defaultValue) {
		return valueOf(source, defaultValue, 0);
	}

	/**
	 * 
	 * @param items
	 *            数字型字符数组
	 * @return 整形数组
	 */
	public static int[] strArrayToInt(String[] items) {
		return ArrayUtils.strArrayToInt(items);
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

		return ArrayUtils.stringToList(source, splitchar);

	}

	/**
	 * 根据布尔值取样式
	 * 
	 * @param value
	 *            true or false/yes or no
	 * @return if value is true,return is yes,neight it is no
	 */
	public static String stringYesNo(boolean value) {
		return HTMLUtils.strongYesNo(value);
	}

	/**
	 * 取颜色值
	 * 
	 * @param value
	 *            yes or no
	 * @param yes
	 *            the style of yes
	 * @param no
	 *            the style of no
	 * @return 颜色/样式
	 */
	public static String strongYesNo(boolean value, String yes, String no) {
		return HTMLUtils.strongYesNo(value, yes, no);
	}

	/**
	 * 根据下标取颜色值
	 * 
	 * @param value
	 *            枚举值
	 * @param colors
	 *            颜色/样式数组
	 * @return 颜色/样式
	 */
	public static String strongYesNo(int value, String[] colors) {
		return HTMLUtils.strongYesNo(value, colors);
	}

	/**
	 * 相当于截断字符串
	 * 
	 * @param source
	 * @param defaultValue
	 * @param maxlength
	 * @return
	 */
	public static String valueOf(String source, String defaultValue,
			int maxlength) {
		return StringUtils.valueOf(source, defaultValue, maxlength);
	}

	/**
	 * 将指定双精度变量格式化为指定长度字符串。当值为０时返回默认串
	 * 
	 * @param value
	 *            双精度变量
	 * @param defaultValue
	 *            双精度变量为0时返回本缺省值
	 * @param scale
	 *            小数位数
	 * @return value=1.23 defaultValue="" scale=1 返回1.2
	 * 
	 * value=0.0 defaultValue="空值" 返回空值
	 */
	public static String zero2Str(double value, String defaultValue, int scale) {
		return NumberUtils.zero2Str(value, defaultValue, scale);
	}

	/**
	 * 将指定浮点变量格式化为指定长度字符串。当值为０时返回默认串
	 * 
	 * @param value
	 *            浮点变量
	 * @param defaultValue
	 *            浮点变量为0时返回本缺省值
	 * @param scale
	 *            小数位数
	 * @return value=1.23 defaultValue="" scale=1 返回1.2
	 * 
	 * value=0.0 defaultValue="空值" 返回空值
	 */
	public static String zero2Str(float value, String defaultValue, int scale) {
		return NumberUtils.zero2Str(value, defaultValue, scale);
	}

	public static String zero2Str(int value, String defaultValue) {
		return NumberUtils.zero2Str(value, defaultValue);
	}

	public static String zero2Str(long value, String defaultValue) {
		return NumberUtils.zero2Str(value, defaultValue);
	}

	/**
	 * 根据List<?>和选择的值Values获取所有选中的项目的名称或标题
	 */
	public static String getMultiCheckedTitle(List<?> list, String values) {
		return HTMLUtils.getMultiCheckedTitle(list, values, ",");
	}

	/**
	 * 根据List<?>和选择的值Values获取所有选中的项目的名称或标题
	 */
	public static String getMultiCheckedTitle(List<?> list, String values,
			String splitStr) {
		return HTMLUtils.getMultiCheckedTitle(list, values, splitStr);
	}

	/**
	 * 根据List<?>和选择的值Values获取所有选中的项目的名称或标题
	 */
	public static String getMultiCheckedTitle(List<?> list, String[] values) {
		return HTMLUtils.getMultiCheckedTitle(list, values);
	}

	/**
	 * 过滤特殊字符
	 * @return
	 */
	public static String filterSpecialChar(String str){		
		if(str != null && !"".equals(str)){
			str = str.replaceAll(";", " ").replaceAll("&","&amp;").replaceAll("'", "")
			.replaceAll("%3B", "").replaceAll("%27", "")
			.replaceAll("%3b", "").replaceAll("<", "&lt;")
			.replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("\'", "&acute;")
			.replaceAll("%3C", "&lt;").replaceAll("%3E", "&lt;");
		}
		return str;
	}
}
