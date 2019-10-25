package com.lhq.superboot.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description: fastjson工具类
 * 
 * @author: lihaoqi
 * @date: 2019年4月13日
 * @version: v1.0.0
 */
public class FastJsonUtils {

	private static final SerializeConfig CONFIG;

	static {
		CONFIG = new SerializeConfig();
		//CONFIG.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
		//CONFIG.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
	}

	private static final SerializerFeature[] FEATURES = { 
			SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.WriteBigDecimalAsPlain  // 处理BigDecimal
	};
	
	private static final SerializerFeature[] NOFEATURES = { 
			SerializerFeature.WriteBigDecimalAsPlain // 处理BigDecimal
	};
	

	public static String toJSONFeatures(Object object) {
		return JSON.toJSONString(object, CONFIG, FEATURES);
	}

	public static String toJSONNoFeatures(Object object) {
		return JSON.toJSONString(object, CONFIG, NOFEATURES);
	}
	

	public static Object toBean(String text) {
		return JSON.parse(text);
	}

	public static <T> T toBean(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	/** 转换为数组 **/
	public static <T> Object[] toArray(String text) {
		return toArray(text, null);
	}

	/** 转换为数组 **/
	public static <T> Object[] toArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz).toArray();
	}

	/** 转换为List **/
	public static <T> List<T> toList(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}


	/**
	 * @Description: 将string转化为序列化的json字符串
	 * 
	 * @param keyvalue
	 * @return
	 */
	public static Object textToJson(String text) {
		Object objectJson = JSON.parse(text);
		return objectJson;
	}

	/**
	 * @Description: json字符串转化为map
	 * 
	 * @param s
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> stringToCollect(String s) {
		Map<K, V> m = (Map<K, V>) JSONObject.parseObject(s);
		return m;
	}

	/**
	 * @Description: 转换JSON字符串为对象
	 * 
	 * @param jsonData
	 * @param clazz
	 * @return
	 */
	public static Object convertJsonToObject(String jsonData, Class<?> clazz) {
		return JSONObject.parseObject(jsonData, clazz);
	}


	/**
	 * @Description: 将map转化为string
	 * 
	 * @param m
	 * @return
	 */
	public static <K, V> String collectToString(Map<K, V> m) {
		String s = JSONObject.toJSONString(m);
		return s;
	}

}
