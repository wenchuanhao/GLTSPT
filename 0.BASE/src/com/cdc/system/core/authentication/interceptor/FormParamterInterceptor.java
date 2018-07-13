package com.cdc.system.core.authentication.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @Description: 放行对应后缀的请求访问系统
 * @Author: nike
 * @UpdateDate: 2012-7-18 下午03:29:11
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
public class FormParamterInterceptor extends HandlerInterceptorAdapter
		implements HandlerInterceptor {

	private Log logger = LogFactory.getLog(getClass());
	
    /**
     * Part of HTTP content type header.
     */
    public static final String MULTIPART = "multipart/";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if(!isMultipartContent(request)){
			Map<String, String[]> sourceParams = request.getParameterMap();
			
			Map targetMap = new HashMap();

			String[] values;
			for (String paramKey : sourceParams.keySet()) {
				values = sourceParams.get(paramKey);
				for(int i = 0 ;values != null && i<values.length;i++){
					values[i] =  repaceChart(values[i]);
				}
				targetMap.put(paramKey, values);
			}
			ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(
					request, targetMap);
			return super.preHandle(wrapRequest, response, handler);
		}
		return super.preHandle(request, response, handler);
	}

	public String repaceChart(String str) {

		if (str != null && !"".equals(str.trim())) {
			str = str.replaceAll(";", " ").replaceAll("&", "&amp;")
					.replaceAll("'", "").replaceAll("%3B", "")
					.replaceAll("%27", "").replaceAll("%3b", "")
					.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
					.replaceAll("\"", "&quot;").replaceAll("\'", "&acute;")
					.replaceAll("%3C", "&lt;").replaceAll("%3E", "&lt;");
		}
		return str;

	}

	public boolean isMultipartContent(HttpServletRequest request) {
		if (!"post".equals(request.getMethod().toLowerCase())) {
			return false;
		}
		String contentType = request.getContentType();
		if (contentType == null) {
			return false;
		}
		if (contentType.toLowerCase().startsWith(MULTIPART)) {
			return true;
		}
		return false;
	}

}
