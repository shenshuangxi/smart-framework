package com.sundy.smart_framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sundy.smart_framework.annotation.Aspect;
import com.sundy.smart_framework.annotation.Service;
import com.sundy.smart_framework.proxy.AspectProxy;
import com.sundy.smart_framework.proxy.Proxy;
import com.sundy.smart_framework.proxy.ProxyManager;
import com.sundy.smart_framework.proxy.TransactionProxy;


public class AopHelper {

	private static final Logger logger = LoggerFactory.getLogger(AopHelper.class);
	
	static{
		try {
			Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for(Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()){
				Class<?> targetClass = entry.getKey();
				List<Proxy> proxyList = entry.getValue();
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			logger.error("aop failure", e);
		}
	}

	private static Map<Class<?>, Set<Class<?>>> createProxyMap()throws Exception {
		Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
		addAspectProxy(proxyMap);
		addTransactionProxy(proxyMap);
		return proxyMap;
	}

	private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
		proxyMap.put(TransactionProxy.class, proxyClassSet);
	}

	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap)
			throws Exception {
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for(Class<?> proxyClass : proxyClassSet){
			if(proxyClass.isAnnotationPresent(Aspect.class)){
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClass(aspect);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
	}

	private static Set<Class<?>> createTargetClass(Aspect aspect)throws Exception  {
		Class<? extends Annotation> annotationClass = aspect.value();
		if(annotationClass!=null&&!annotationClass.equals(Aspect.class)){
			return ClassHelper.getClassSetByAnnotation(annotationClass);
		}
		return new HashSet<Class<?>>();
	}
	
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap)throws Exception  {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for(Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()){
			Class<?> proxyClass = entry.getKey();
			Set<Class<?>> targetClassSet = entry.getValue();
			for(Class<?> targetClass : targetClassSet){
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if(!targetMap.containsKey(targetClass)){
					targetMap.put(targetClass, new ArrayList<Proxy>());
				}
				targetMap.get(targetClass).add(proxy);
			}
		}
		return targetMap;
	}
	
}
