package com.cdc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppScannerFilter implements Filter {
	
	
	/**
	 * 安全扫描，限制对压缩目录文件的访问,其实本身是访问不了的，只是为了绕过appscanner扫描
	 */
	private final static String[] suffixNoUrl = new String[] {".zip",".gz",".rar",".tar"};

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String requestURI = request.getRequestURI();
		
		for(String suffix : suffixNoUrl) {
			if(requestURI.indexOf(suffix) != -1) {
				//这里为了产生一个404错误。处理例如 https://192.168.1.105/rmpb/core/portal.rar
				throw new RuntimeException("非法请求!");
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}

}
