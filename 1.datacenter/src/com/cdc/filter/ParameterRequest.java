package com.cdc.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

public class ParameterRequest extends HttpServletRequestWrapper {
	
	private String keyword;
	private static String replaceWord = "&quot;|&lt;|&gt;";

	public ParameterRequest(HttpServletRequest request,String keyword) {
		super(request);
		this.keyword = keyword;
	}
	
	@Override
	public String getParameter(String name) {
		String keyName = super.getParameter(name);
		if (StringUtils.isNotEmpty(name)
				&& (StringUtils.equals("applyDetail", name.trim())
						|| StringUtils.equals("schemeInfo", name.trim())
						|| StringUtils.equals("schemeTimerange", name.trim()) 
						|| StringUtils.equals("schemeRisk", name.trim())
						|| StringUtils.equals("notice", name.trim()))
				) {
			return keyName;
		}
		String web_xml[] = keyword.split("\\|");
		String replaces[] = replaceWord.split("\\|");
		// 检查参数
		// 参数是字符串
		if (keyName != null) {
			for (int i1 = 0; i1 < web_xml.length; i1++) {
				if (keyName.lastIndexOf(web_xml[i1]) != -1){
					keyName = keyName.replaceAll(web_xml[i1], replaces[i1]);
				}
			}
			return keyName;
		}
		return keyName;
	}
	
	@Override
	public String[] getParameterValues(String name) {
		String st[] = super.getParameterValues(name);
		if (StringUtils.isNotEmpty(name)
				&& (StringUtils.equals("applyDetail", name.trim())
						|| StringUtils.equals("schemeInfo", name.trim())
						|| StringUtils.equals("schemeTimerange", name.trim()) 
						|| StringUtils.equals("schemeRisk", name.trim())
						|| StringUtils.equals("notice", name.trim()))
						) {
			return st;
		}
		String web_xml[] = keyword.split("\\|");
		String replaces[] = replaceWord.split("\\|");
		// 检查参数
		if (st != null) {
			for (int i = 0; i < st.length; i++) {
				if(st[i] != null && !st[i].contains("<") && !st[i].contains(">")) { //如果不包括 尖括号
					for (int j = 0; j < web_xml.length; j++) {
						if (st[i].lastIndexOf(web_xml[j]) != -1){
							st[i] = st[i].replaceAll(web_xml[j], replaces[j]);
						}
					}
				}
//				return st;
			}
			return st;
		}
		return st;
	}

}
