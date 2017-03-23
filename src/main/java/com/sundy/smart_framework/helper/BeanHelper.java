package com.sundy.smart_framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sundy.smart_framework.util.ReflectionUtil;

public class BeanHelper {

	private static Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

	static{
		Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
		for(Class<?> clazz : classSet){
			Object obj = ReflectionUtil.newInstance(clazz);
			BEAN_MAP.put(clazz, obj);
		}
	}
	
	public static Map<Class<?>, Object> getBeanMAP() {
		return BEAN_MAP;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz){
		if(!BEAN_MAP.containsKey(clazz)){
			throw new RuntimeException("can not get bean by class: "+clazz);
		}
		return (T) BEAN_MAP.get(clazz);
	}
	
	
	
}
