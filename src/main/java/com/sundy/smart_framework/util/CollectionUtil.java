package com.sundy.smart_framework.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

public class CollectionUtil {

	public static boolean isNotEmpty(Map<Class<?>, Object> beanMap) {
		if(beanMap.isEmpty()){
			return false;
		}
		return true;
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return CollectionUtils.isNotEmpty(collection);
	}

}
