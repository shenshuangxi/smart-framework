package com.sundy.smart_framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sundy.smart_framework.bean.Data;
import com.sundy.smart_framework.bean.Handler;
import com.sundy.smart_framework.bean.Param;
import com.sundy.smart_framework.bean.View;
import com.sundy.smart_framework.helper.BeanHelper;
import com.sundy.smart_framework.helper.ConfigHelper;
import com.sundy.smart_framework.helper.ControllerHelper;
import com.sundy.smart_framework.helper.HelperLoader;
import com.sundy.smart_framework.util.ArrayUtil;
import com.sundy.smart_framework.util.CodecUtil;
import com.sundy.smart_framework.util.JsonUtil;
import com.sundy.smart_framework.util.ReflectionUtil;
import com.sundy.smart_framework.util.StreamUtil;
import com.sundy.smart_framework.util.StringUtil;

@WebServlet(urlPatterns="/*",loadOnStartup=0)
public class DispatchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		//系统初始化
		HelperLoader.init();
		
		//获取ServletContext，用于注册servlet filter listener
		ServletContext servletContext = config.getServletContext();
		
		//注册jsp的servlet处理器
		ServletRegistration jspServletRegistration = servletContext.getServletRegistration("jsp");
		jspServletRegistration.addMapping(ConfigHelper.getAppJspPath()+"/*");
		
		//注册静态资源的servlet处理器
		ServletRegistration defaultServletRegistration = servletContext.getServletRegistration("default");
		defaultServletRegistration.addMapping(ConfigHelper.getAppAssetPath()+"/*");
		
		
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String requestMethod = request.getMethod().toLowerCase();
		String requestPath = request.getPathInfo();
		
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		
		if(handler!=null){
			Class<?> controllerClass = handler.getControllerClass();
			Method method = handler.getActionMethod();
			Object controllerBean = BeanHelper.getBean(controllerClass);
			Map<String,Object> paramMap = new HashMap<>();
			Enumeration<String> paramNames = request.getParameterNames();
			while(paramNames.hasMoreElements()){
				String key = paramNames.nextElement();
				String value = request.getParameter(key);
				paramMap.put(key, value);
			}
			
			String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
			if(StringUtil.isNotEmpty(body)){
				String[] params = body.split("&");
				if(ArrayUtil.isNotEmpty(params)){
					for(String param : params){
						String[] array = param.split("=");
						if(ArrayUtil.isNotEmpty(array)){
							String paramName = array[0];
							String paramValue = array[1];
							paramMap.put(paramName, paramValue);
						}
					}
				}
			}
			
			Param param = new Param(paramMap);
			Object result = ReflectionUtil.invokeMethod(controllerBean, method, param);
			
			if(result instanceof View){
				View view = (View) result;
				String path = view.getPath();
				if(StringUtil.isNotEmpty(path)){
					if(path.startsWith("/")){
						response.sendRedirect(request.getContextPath()+path);
					}else{
						Map<String,Object> model = view.getModel();
						for(Map.Entry<String, Object> entry : model.entrySet()){
							request.setAttribute(entry.getKey(), entry.getValue());
						}
						request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(request, response);
					}
				}
			}else if(result instanceof Data){
				Object model = ((Data) result).getModel();
				if(model!=null){
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					PrintWriter pw = response.getWriter();
					String json = JsonUtil.toJson(model);
					pw.write(json);
					pw.flush();
					pw.close();
				}
			}
			
		}
		
	
	}

	

	
	
}
