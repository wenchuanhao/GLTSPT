package com.trustel.common;

/**
 * @author Administrator
 *
 * 字符串特殊字符转换器
 */
public class StringMarkuper {
	/**
	 * 将字符串中的xml保留字进行转换生成新的字符串
	 * <p>
	 * 为避免内容中包含xml的关键字(如"<"、">"等)影响xml解析，在内容存入数据库前先将保留字转换为特殊字符，
	 * xml从数据库中取出后将解析出的内容经还原操作后还原为原始内容。
	 * 
	 * @param text 字符串
	 * @return 经字符转换的字符串
	 */
	public static String markup(String text) {
		if (text == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (c) {
				case '<' :
					buffer.append("&lt;");
					break;
				case '&' :
					buffer.append("&amp;");
					break;
				case '>' :
					buffer.append("&gt;");
					break;
				case '"' :
					buffer.append("&quot;");
					break;
				case '\'' :
					buffer.append("&apos;");
					break;
				default :
					buffer.append(c);
					break;
			}
		}

		return buffer.toString();
	}

	/**
	 * 将经过字符转换后的字符串进行反向处理，回复原始内容
	 * 
	 * @param text 字符串
	 * @return 反向恢复后的字符串
	 */
	public static String restore(String text) {
		if (text == null) {
			return null;
		}

		int i = 0;
		StringBuffer buffer = new StringBuffer();
		while (i < text.length()) {
			char c = text.charAt(i);

			if (c == '&') {
				if (text.substring(i).indexOf("&lt;") == 0) {
					i += 4;
					buffer.append('<');
				} else if (text.substring(i).indexOf("&amp;") == 0) {
					i += 5;
					buffer.append('&');
				} else if (text.substring(i).indexOf("&gt;") == 0) {
					i += 4;
					buffer.append('>');
				} else if (text.substring(i).indexOf("&quot;") == 0) {
					i += 6;
					buffer.append('"');
				} else if (text.substring(i).indexOf("&apos;") == 0) {
					i += 6;
					buffer.append('\'');
				} else {
					buffer.append(c);
					i++;
				}
			} else {
				buffer.append(c);
				i++;
			}
		}

		return buffer.toString();
	}
}
