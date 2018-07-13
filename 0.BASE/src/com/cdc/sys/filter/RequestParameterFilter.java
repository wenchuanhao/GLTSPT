package com.cdc.sys.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


/**
 * 请求参数过滤器，为了安全性而增加此过滤。
 */
public class RequestParameterFilter implements Filter {
	private FilterConfig filterConfig = null;

	public void destroy() {
		filterConfig = null;
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		//读取请求参数map
		Map newParams = new HashMap(request.getParameterMap());
		Set<String> keys = newParams.entrySet();  
		
		//过滤特殊字符
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			if(mapEntry instanceof Map.Entry){
				String key = (String)mapEntry.getKey();
				Object vObj = newParams.get(key);
				if (vObj == null){
					continue;
				}else if (vObj instanceof String[]){
					String[] oldObj = (String[])vObj;
					String[] newObj = new String[oldObj.length];
					for(int i=0;i<oldObj.length;i++){
						newObj[i] = trimScript(oldObj[i]);
					}
		    		newParams.put(key, newObj);  
				}
				else if (vObj instanceof String){
		    		newParams.put(key, trimScript((String)vObj));  
				}else{
		    		newParams.put(key, trimScript(vObj.toString()));  				
				}
			}
		}
		//新构造request对象
		ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(request,newParams);   
		
		//跨站点请求伪造: HTTP 头设置 Referer过滤
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		if(request.getServerPort()==80)
			basePath = request.getScheme()+"://"+request.getServerName()+request.getContextPath();
		String referer = request.getHeader("Referer");   //REFRESH
		if(referer!=null && referer.indexOf(basePath)<0){			
//			request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
			return;
		}
		
		chain.doFilter(wrapRequest, response);

		return;
	}

	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}
	
	/**
	 * 过滤<script>、</script>，预防script非法注入
	 * @param src
	 * @return
	 */
	private static String trimScript(String src){	 
		if(src != null && !src.equals("")){
			String lower = src.toLowerCase();//读取小写
			if(lower.indexOf("<script")>-1){
				src = StringUtils.filterDangerString(src);			
			}
			else if(lower.indexOf("<iframe")>-1){
				src = StringUtils.filterDangerString(src);					
			}
			else if(lower.indexOf("<html")>-1){
				src = StringUtils.filterDangerString(src);				
			}
			else if(lower.indexOf("<table")>-1){
				src = StringUtils.filterDangerString(src);						
			}
			else if(lower.indexOf("<input")>-1){
				src = StringUtils.filterDangerString(src);						
			}
			else if(lower.indexOf("<select")>-1){
				src = StringUtils.filterDangerString(src);						
			}
			else if(lower.indexOf("<frame")>-1){
				src = StringUtils.filterDangerString(src);						
			}
			else if(lower.indexOf("<form")>-1){
				src = StringUtils.filterDangerString(src);						
			}
			else if(lower.indexOf("<textarea")>-1){
				src = StringUtils.filterDangerString(src);						
			}
			else if(lower.indexOf("<img")>-1){
				src = StringUtils.filterDangerString(src);						
			}
			else if(lower.indexOf("alert(")>-1){
				src = StringUtils.filterDangerString(src);						
			}else {
				src = StringUtils.filterDangerString(src);
			}
			return src;
		}
		return "";
	}
	

	/**
	 * 为了能修改请求参数，增加HttpServletRequestWrapper类
	 */
	static class ParameterRequestWrapper extends HttpServletRequestWrapper {
		
		private Map params;

		/**
		 * 构造方法
		 * @param request
		 * @param newParams
		 */
		public ParameterRequestWrapper(HttpServletRequest request, Map newParams) {
			super(request);
			this.params = newParams;
		}

		public Map getParameterMap() {
			return params;
		}

		public Enumeration getParameterNames() {
			Vector l = new Vector(params.keySet());
			return l.elements();
		}

		public String[] getParameterValues(String name) {
			Object v = params.get(name);
			if (v == null) {
				return null;
			} else if (v instanceof String[]) {
				 String[] strArr = (String[])v;
				 for (int i = 0; i < strArr.length; i++) {
					 strArr[i]= strArr[i];
				 }  
				return strArr;
			} else if (v instanceof String) {
				return new String[] { (String) v };
			} else {
				return new String[] { v.toString() };
			}
		}

		public String getParameter(String name) {
			Object v = params.get(name);
			if (v == null) {
				return null;
			} else if (v instanceof String[]) {
				String[] strArr = (String[]) v;
				if (strArr.length > 0) {
					return strArr[0];
				} else {
					return null;
				}
			} else if (v instanceof String) {
				return (String) v;
			} else {
				return v.toString();
			}
		}
	}

}
