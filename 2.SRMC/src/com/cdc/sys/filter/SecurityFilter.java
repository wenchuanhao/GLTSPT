package com.cdc.sys.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SecurityFilter implements Filter {

	private static String forward;
	private static String forwardKey;
	private static String ServletSrc;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!SecurityFilter.UrlFile((HttpServletRequest) request)) {
			request.getRequestDispatcher(forward).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		forward = filterConfig.getInitParameter("forward");
		forwardKey = filterConfig.getInitParameter("forwardKey");
		// 此处添加特例...
		StringBuffer sb = new StringBuffer(
				"/worksheetManage/worksheetManageFlow/toAddWorkSheet");
		sb.append("|" + "/worksheetManage/worksheetManageFlow/caoGaoBox");
		ServletSrc = sb.toString();
	}

	/**
	 * 查看请求源是否特例
	 */
	public static boolean isCase(HttpServletRequest request) {
		String paths[] = ServletSrc.split("\\|");
		StringBuffer pathstr;
		for (int i = 0; i < paths.length; i++) {
			pathstr = new StringBuffer(request.getServletPath());
			if (pathstr.lastIndexOf(paths[i]) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 参数过滤,true为合法 false为含有非法字符
	 * 
	 * @return
	 */
	public static boolean UrlFile(HttpServletRequest request) {
		String web_xml[] = forwardKey.split("\\|");
		StringBuffer str;
		String st[];
		StringBuffer keyName;
		// 检查请求源
		if (SecurityFilter.isCase(request)) {
			return true;
		}
		// 检查参数
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			keyName = new StringBuffer((String) enu.nextElement());
			// 参数是字符串
			if (request.getParameter(keyName.toString()) != null) {
				str = new StringBuffer(request.getParameter(keyName.toString()));
				for (int i1 = 0; i1 < web_xml.length; i1++) {
					if (str.lastIndexOf(web_xml[i1]) != -1)
						return false;
					if (str.toString().toUpperCase().lastIndexOf(web_xml[i1]) != -1)
						return false;
				}

			}
			// 参数是数组
			else if (request.getParameterValues(keyName.toString()) != null) {
				st = request.getParameterValues(keyName.toString());
				for (int i = 0; i < st.length; i++) {
					for (int j = 0; j < web_xml.length; j++) {
						if (st[i].lastIndexOf(web_xml[j]) != -1)
							return false;
					}
				}

			}
		}
		return true;

	}

	public static String getForward() {
		return forward;
	}

	public static void setForward(String forward) {
		SecurityFilter.forward = forward;
	}

	public static String getForwardKey() {
		return forwardKey;
	}

	public static void setForwardKey(String forwardKey) {
		SecurityFilter.forwardKey = forwardKey;
	}

}
