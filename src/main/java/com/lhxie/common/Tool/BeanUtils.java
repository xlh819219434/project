package com.lhxie.common.Tool;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 仿制并扩展spring BeanUtils工具类。
 * 扩展功能清单： 1、复制对象属性时，忽略空值(copyPropertiesIgnoreNull方法);
 * 
 * @author QICHEN
 * @datetime 2013-1-26上午11:54:01
 * 
 */
public class BeanUtils {
	private static Map<Class<?>, Boolean> ignoreRulesMap;

	static {
		ignoreRulesMap = new HashMap<Class<?>, Boolean>();
		ignoreRulesMap.put(java.util.List.class, true);
		ignoreRulesMap.put(java.util.Map.class, true);
	}

	/**
	 * 复制对象像时，忽略空值，并忽略指定属性
	 * 
	 * @param source
	 * @param target
	 * @param ignoreProperties
	 * @throws  
	 * @throws BeansException
	 */
	public static boolean copyPropertiesIgnoreNull(Object source, Object target, String[] ignoreProperties) {
		PropertyDescriptor[] targetPds;
		try {
			targetPds = Introspector.getBeanInfo(target.getClass()).getPropertyDescriptors();
			List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

			for (PropertyDescriptor targetPd : targetPds) {
				if (targetPd.getWriteMethod() != null
						&& (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
					PropertyDescriptor sourcePd;
					try {
						sourcePd = new PropertyDescriptor(targetPd.getName(), source.getClass());
					} catch (IntrospectionException e) {
						continue;
					}
					if (sourcePd != null && sourcePd.getReadMethod() != null) {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value;
							value = readMethod.invoke(source);
						if (ignoreRules(value)) {
							Method writeMethod = targetPd.getWriteMethod();
							readMethod.getReturnType();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 忽略规则定义
	 * 
	 * @param value
	 * @return
	 */
	private static boolean ignoreRules(Object value) {
		if (value == null)
			return false;
		Boolean rule = ignoreRulesMap.get(value.getClass());
		if (rule != null) {
			return false;
		}
		return true;
	}

}
