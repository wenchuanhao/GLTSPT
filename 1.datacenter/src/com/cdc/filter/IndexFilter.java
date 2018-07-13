package com.cdc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.trustel.system.SystemConstant;

public class IndexFilter implements Filter {

	private static String answerPath;
	private static String notAnswerPath;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		//从工作台进入到办理页面,把页面位置改成工作台
		HttpServletRequest request = (HttpServletRequest)req;
		String indexLocation = request.getParameter("indexLocation");
		if(StringUtils.isNotEmpty(indexLocation)) {
			System.out.println("indexLocation ================= " + indexLocation);
			request.setAttribute("indexLocation", indexLocation);
		}
		
		//问题交互是否可回复
		String uri = request.getRequestURI();
		String lastUri = uri.substring(uri.lastIndexOf("/") + 1);
		
		if(StringUtils.contains(answerPath, lastUri)) {
			request.getSession().setAttribute(SystemConstant.IS_ANSWER_FLAG, "1");
		} else if(StringUtils.contains(notAnswerPath, lastUri)) {
			request.getSession().setAttribute(SystemConstant.IS_ANSWER_FLAG, "0");
		}
		
		chain.doFilter(request, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		answerPath = config.getInitParameter("answerPath");
		notAnswerPath = config.getInitParameter("notAnswerPath");
	}

}
