package com.sundy.smart_framework.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletHelper {
	private static final Logger logger = LoggerFactory.getLogger(ServletHelper.class);
	
	private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<ServletHelper>();
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private ServletHelper(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public static void init(HttpServletRequest request,
			HttpServletResponse response){
		SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
	}
	
	public static void destroy(){
		SERVLET_HELPER_HOLDER.remove();
	}
	
	public static HttpServletRequest getRequest(){
		return SERVLET_HELPER_HOLDER.get().request;
	}
	
	public static HttpServletResponse getResponse(){
		return SERVLET_HELPER_HOLDER.get().response;
	}
	
	public static HttpSession getSession(){
		return SERVLET_HELPER_HOLDER.get().request.getSession();
	}
	
	public static ServletContext getServletContext(){
		return SERVLET_HELPER_HOLDER.get().request.getServletContext();
	}
	
	
	
}
