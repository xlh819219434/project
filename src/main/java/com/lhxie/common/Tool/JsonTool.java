package com.lhxie.common.Tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

public class JsonTool {

	static{
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();	
	}
	/**
	 * 把JSON对象再包装一层
	 * 
	 * @param key
	 * @param objectJson
	 * @return
	 */
	public static String wrapJson(String key, String objectJson) {
		return "{\"" + key + "\":" + objectJson + "}";
	}

	/**
	 * 将对象转换成JSON字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		if (object == null) {
			return null;
		}
		return JSON.toJSONString(object);
	}

	/**
	 * 将多个对象转换成JSON字符串 多个参数KEY和VALUE交替传入
	 * 
	 * @param keysAndValues
	 * @return
	 */
	public static String toJsonString(Object... keysAndValues) {
		int len = keysAndValues.length;
		if (len == 1) {
			return JSON.toJSONString(keysAndValues[0]);
		}
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < len / 2; i++) {
			String key = keysAndValues[i * 2].toString();
			Object value = keysAndValues[i * 2 + 1];
			jsonObject.put(key, value);
		}
		return jsonObject.toString();
	}

	/**
	 * 将JSON数组转换成对象列表
	 * 
	 * @param jsonArray
	 * @param classObject
	 * @return
	 */
	public static <T> List<T> parseArray(String jsonArray, Class<T> classObject) {
		return JSONObject.parseArray(jsonArray, classObject);
	}

	/**
	 * 将JSON字符串转换成对象
	 * 
	 * @param json
	 * @param classObject
	 * @return
	 */
	public static <T> T parseObject(String json, Class<T> classObject) {
		return JSONObject.parseObject(json, classObject);
	}

	/**
	 * 获得指定的JSON属性值
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static Object parseValue(String json, String key) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = JSONObject.parseObject(json, Map.class);
		if (map == null || map.size() == 0) {
			return null;
		}
		Object t = map.get(key);
		return t;
	}

	/**
	 * 获得指定的JSON属性整数值
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static int parseInt(String json, String key) {
		Object val = parseValue(json, key);
		if (val == null) {
			return 0;
		}
		if (val instanceof Integer) {
			return (Integer) val;
		}
		if (val instanceof String) {
			return Integer.parseInt((String) val, 10);
		}
		return 0;
	}

	private JsonTool() {

	}

}
