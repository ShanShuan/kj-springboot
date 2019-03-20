package com.wonders.core.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JsonMapper Util
 * 
 * @author silent
 */
public class JsonMapper {
	private ObjectMapper mapper;

	public JsonMapper(Include inclusion) {
		mapper = new ObjectMapper();
		// 设置输出时包含属性的风格
		mapper.setSerializationInclusion(inclusion);
		// 忽略json字符串中不识别的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 忽略无法转换的对象 “No serializer found for class com.xxx.xxx”
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	/**
	 * 创建输出全部属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNormalMapper() {
		return new JsonMapper(Include.ALWAYS);
	}

	/**
	 * 创建只输出非空属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNonNullMapper() {
		return new JsonMapper(Include.NON_NULL);
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNonDefaultMapper() {
		return new JsonMapper(Include.NON_DEFAULT);
	}

	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNonEmptyMapper() {
		return new JsonMapper(Include.NON_EMPTY);
	}

	/**
	 * 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
	 */
	public String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
	 * 
	 * @see #constructParametricType(Class, Class...)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
	 * 
	 * @see #constructParametricType(Class, Class...)
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return (T) mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, Class<T> parametrized, Class<?>... parameterClasses) {
		return (T) this.fromJson(jsonString, constructParametricType(parametrized, parameterClasses));
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> fromJsonToList(String jsonString, Class<T> classMeta) {
		return (List<T>) this.fromJson(jsonString, constructParametricType(List.class, classMeta));
	}

	/**
	 * 構造泛型的Type如List<MyBean>,
	 * 则调用constructParametricType(ArrayList.class,MyBean.class)
	 * Map<String,MyBean>则调用(HashMap.class,String.class, MyBean.class)
	 */
	public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
		return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
	}
}
