package com.sundy.smart_framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sundy.smart_framework.annotation.Action;
import com.sundy.smart_framework.bean.Handler;
import com.sundy.smart_framework.bean.Request;
import com.sundy.smart_framework.util.ArrayUtil;
import com.sundy.smart_framework.util.CollectionUtil;

public class ControllerHelper {

	private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

	public static Map<Request, Handler> getActionMap() {
		return ACTION_MAP;
	}
	
	static{
		Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
		if(CollectionUtil.isNotEmpty(controllerSet)){
			for(Class<?> controllerClass : controllerSet){
				Method[] methods = controllerClass.getDeclaredMethods();
				if(ArrayUtil.isNotEmpty(methods)){
					for(Method method : methods){
						if(method.isAnnotationPresent(Action.class)){
							Action action = method.getAnnotation(Action.class);
							String rquestMethod = action.method();
							String requestPath = action.path();
							Handler handler = new Handler(controllerClass, method);
							Request request = new Request(rquestMethod, requestPath);
							ACTION_MAP.put(request, handler);
						}
					}
				}
			}
		}
	}
	
	
	public static Handler getHandler(String requestMethod,String requestPath){
		Request request = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
	
	
	
	
	
}
