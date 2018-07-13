package com.cdc.system.core.authentication.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.regexp.RegexpUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
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
public class IncludeSuffixnInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {

	private Log logger = LogFactory.getLog(getClass());

	private List<String> excludeSuffixn = new ArrayList();;


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		String requestUrl = request.getRequestURI();
		// 输出请求的url
		logger.info("preHandle--> URL: " + requestUrl);
		
		String urlSuffixn = requestUrl.lastIndexOf(".") !=-1?requestUrl.substring(requestUrl.lastIndexOf(".")):null;
		for(String suffixn : excludeSuffixn){
			if(suffixn != null && suffixn.trim() != "" && urlSuffixn != null) {
				if(RegexpUtils.getMatcher(suffixn).matches(urlSuffixn)){
					flag = true;
					break;
				}
				
			}
		}
		
		if(null == urlSuffixn){
			flag = true;
		}
		
		if(!flag){
			response.sendError(HttpStatus.NOT_FOUND.value());
		}
		return flag;
	}


	public List<String> getExcludeSuffixn() {
		return excludeSuffixn;
	}


	public void setExcludeSuffixn(List<String> excludeSuffixn) {
		this.excludeSuffixn = excludeSuffixn;
	}
	
	public static void main(String[] args) {
		String targetStr = ".json";
		String parttern = ".json";
		
		System.out.println(RegexpUtils.getMatcher(parttern).matches(targetStr));
	}

}
