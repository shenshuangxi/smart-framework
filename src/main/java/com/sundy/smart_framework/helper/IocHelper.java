package com.sundy.smart_framework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.sundy.smart_framework.annotation.Inject;
import com.sundy.smart_framework.util.ArrayUtil;
import com.sundy.smart_framework.util.CollectionUtil;
import com.sundy.smart_framework.util.ReflectionUtil;

/**
 * 处理依赖注入
 * @author Administrator
 *
 */
public class IocHelper {

	static{
		Map<Class<?>,Object> beanMap = BeanHelper.getBeanMAP();
		if(CollectionUtil.isNotEmpty(beanMap)){
			for(Map.Entry<Class<?>, Object> entry : beanMap.entrySet()){
				Class<?> clazz = entry.getClass();
				Object obj = entry.getValue();
				Field[] fields = clazz.getDeclaredFields();
				if(ArrayUtil.isNotEmpty(fields)){
					for(Field field : fields){
						if(field.isAnnotationPresent(Inject.class)){
							Class<?> type = field.getType();
							Object fieldInstance = beanMap.get(type);
							if(fieldInstance!=null){
								ReflectionUtil.setField(obj, field, fieldInstance);
							}
						}
					}
				}
			}
		}
	}
	
}
