package org.trustel.util;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;



public class JsUtils {

	
	/**
	 * 输出流到页面，参照document.write
	 * @param str 输出的内容
	 */
	public static void write(String str , HttpServletResponse response ){
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(str);
			//response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	

	/**
	 * js alert
	 * @param response response
	 * @param msg 消息
	 */
	public static void alert( HttpServletResponse response , String msg ){
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("<script>alert('" + msg + "');</script>");
			//response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * js 重定向
	 * @param response response
	 * @param msg 消息
	 * @param url 重定向的位置
	 */
	public static void alertWithRedirect( HttpServletResponse response , String msg, String url){
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("<script>alert('" + msg + "'); window.location='" + url + "'</script>");
			//response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 重定向  到 url
	 * @param url 重定向的地址
	 */
	public static void redirect(HttpServletResponse response , String url){
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("<script> window.location='" + url + "'</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
