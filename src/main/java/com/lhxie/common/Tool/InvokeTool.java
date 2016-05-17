package com.lhxie.common.Tool;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class InvokeTool {
	
	private static Logger loggerError = Logger.getLogger(InvokeTool.class);

	//获取方法对象
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		Method method = null;
		try {
			method = clazz.getMethod(methodName, parameterTypes);
		} catch (SecurityException e) {
			loggerError.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			//非法请求
			//loggerError.error(e.getMessage(), e);
		}
		return method;
	}
	
	
	public static Object invokeMethod(Method method, Object object,  Object... args) throws Exception {
    	Object value = null;
		try {
			value = method.invoke(object, args);
		} catch (IllegalAccessException e) {
			loggerError.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			loggerError.error(e.getMessage(), e);
//		} catch (InvocationTargetException e) {
//			loggerError.error(e.getMessage(), e);
		}
		return value;
    }

}
