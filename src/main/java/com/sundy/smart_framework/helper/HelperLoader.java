package com.sundy.smart_framework.helper;

import com.sundy.smart_framework.util.ClassUtil;

public class HelperLoader {

	public static void init(){
		Class<?>[] classList = {ClassHelper.class,BeanHelper.class,AopHelper.class,IocHelper.class,ControllerHelper.class};
		for(Class<?> clazz : classList){
			ClassUtil.loadClass(clazz.getName(),true);
		}
	}
	
}
