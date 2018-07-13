package com.trustel.common;

/**
 * @author Administrator
 * 
 * URL参数分解类
 * 
 */
public class UrlParameter {
	private UrlParameter() {
	}

	/**
	 * 由url参数字符串取出参数
	 * 
	 * @param url 参数字符串
	 * @return 参数集
	 */
	public static java.util.Hashtable getParameter(String url) {
		String[] parameter = null;
		java.util.Hashtable parameters = new java.util.Hashtable();

		if (url != null) {
			String[] items =
				StringFunc.separate(url, StringFunc.AND_SEPARATOR);

			for (int i = 0; i < items.length; i++) {
				parameter =
					StringFunc.separate(
						items[i],
						StringFunc.EQUAL_SEPARATOR);
				if (parameter.length > 0) {
					parameters.put(
						parameter[0],
						(parameter.length == 1) ? "" : parameter[1]);
				}
			}
		}
		
		return parameters;
	}
}
