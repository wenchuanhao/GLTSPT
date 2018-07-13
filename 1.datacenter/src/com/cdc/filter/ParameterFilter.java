package com.cdc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ParameterFilter implements Filter {

	private static String keyword;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		ParameterRequest paramRequest = new ParameterRequest(req, keyword);
		chain.doFilter(paramRequest, response);
	}

	public void destroy() {

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		keyword = filterConfig.getInitParameter("keyword");
	}

	public static String getKeyword() {

		return keyword;
	}

	public static void setKeyword(String keyword) {
		ParameterFilter.keyword = keyword;
	}

}
