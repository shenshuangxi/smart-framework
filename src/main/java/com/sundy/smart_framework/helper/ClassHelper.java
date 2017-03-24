package com.sundy.smart_framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.sundy.smart_framework.annotation.Controller;
import com.sundy.smart_framework.annotation.Service;
import com.sundy.smart_framework.util.ClassUtil;

public class ClassHelper {

	private static Set<Class<?>> CLASS_SET;
	
	static{
		String packageName = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassUtil.getClassSet(packageName);
	}

	public static Set<Class<?>> getCLASS_SET() {
		return CLASS_SET;
	}
	
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> controllerClassSet = new HashSet<Class<?>>();
		for(Class<?> clazz : CLASS_SET){
			if(clazz.isAnnotationPresent(Controller.class)){
				controllerClassSet.add(clazz);
			}
		}
		return controllerClassSet;
	}
	
	public static Set<Class<?>> getServiceClassSet(){
		Set<Class<?>> serviceClassSet = new HashSet<Class<?>>();
		for(Class<?> clazz : CLASS_SET){
			if(clazz.isAnnotationPresent(Service.class)){
				serviceClassSet.add(clazz);
			}
		}
		return serviceClassSet;
	}
	
	public static Set<Class<?>> getBeanClassSet(){
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getControllerClassSet());
		beanClassSet.addAll(getServiceClassSet());
		return beanClassSet;
	}
	
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> clazz : CLASS_SET){
			if(superClass.isAssignableFrom(clazz)&&!superClass.equals(clazz)){
				classSet.add(clazz);
			}
		}
		return classSet;
	}
	
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> clazz : CLASS_SET){
			if(clazz.isAnnotationPresent(annotationClass)){
				classSet.add(clazz);
			}
		}
		return classSet;
	}
	
}
