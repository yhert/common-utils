package com.yhert.project.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yhert.project.common.beans.Param;
import com.yhert.project.common.excp.BeanException;
import com.yhert.project.common.excp.SerializableException;
import com.yhert.project.common.excp.app.ValidationException;

/**
 * Json序列化
 * 
 * @author Ricardo Li 2016年7月30日 下午3:33:27
 *
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static XmlMapper xmlMapper = new XmlMapper();

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		xmlMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
	}

	/**
	 * 获得ObjectMapper
	 * 
	 * @return 获得Mapper
	 */
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/**
	 * 获得XmlMapper
	 * 
	 * @return XmlMapper
	 */
	public static XmlMapper getXmlMapper() {
		return xmlMapper;
	}

	/**
	 * 通过xml创建jsonNode对象
	 * 
	 * @param xml xml字符串
	 * @return Json解析对象
	 */
	public static JsonNode createXmlNode(String xml) {
		try {
			return getObjectMapper().readTree(xml);
		} catch (Exception e) {
			throw new SerializableException("反序列化 " + xml + " 时出现错误", e, xml);
		}
	}

	/**
	 * 将Object序列化为xml
	 * 
	 * @param obj 带序列化的Java对象
	 * @return 序列化后的String
	 */
	public static String object2Xml(Object obj) {
		try {
			return getXmlMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new SerializableException("序列化 " + obj + " 时出现错误", e, obj);
		}
	}

	/**
	 * 通过json创建jsonNode对象
	 * 
	 * @param json Json字符串
	 * @return Json解析对象
	 */
	public static JsonNode createJsonNode(String json) {
		try {
			return getObjectMapper().readTree(json);
		} catch (Exception e) {
			throw new SerializableException("反序列化错误[" + json + "]", e, json);
		}
	}

	/**
	 * 将xml转换为对象
	 * 
	 * @param xml      xml字符串
	 * @param javaType java对象
	 * @return 解析后的Java对象
	 */
	public static <T> T xml2Object(String xml, JavaType javaType) {
		try {
			return getXmlMapper().readValue(xml, javaType);
		} catch (Exception e) {
			throw new SerializableException("反序列化 " + xml + " 时出现错误", e, xml);
		}
	}

	/**
	 * 将xml转换为对象
	 * 
	 * @param xml  xml字符串
	 * @param type 目标类型
	 * @return 解析后的Java对象
	 */
	public static <T> T xml2Object(String xml, Class<T> type) {
		try {
			return getXmlMapper().readValue(xml, type);
		} catch (Exception e) {
			throw new SerializableException("反序列化 " + xml + " 时出现错误", e, xml);
		}
	}

	/**
	 * 将xml转换为对象
	 * 
	 * @param xml           Json字符串
	 * @param typeReference 类型泛型
	 * @return 解析后的Java对象
	 */
	public static <T> T xml2Object(String xml, TypeReference<T> typeReference) {
		try {
			return getXmlMapper().readValue(xml, typeReference);
		} catch (Exception e) {
			throw new SerializableException("反序列化 " + xml + " 时出现错误", e, xml);
		}
	}

	/**
	 * 将json转换为对象
	 * 
	 * @param json     Json字符串
	 * @param javaType java对象
	 * @return 解析后的Java对象
	 */
	public static <T> T json2Object(String json, JavaType javaType) {
		try {
			return getObjectMapper().readValue(json, javaType);
		} catch (Exception e) {
			throw new SerializableException("反序列化 " + json + " 时出现错误", e, json);
		}
	}

	/**
	 * 将json转换为对象
	 * 
	 * @param json Json字符串
	 * @param type 目标类型
	 * @return 解析后的Java对象
	 */
	public static <T> T json2Object(String json, Class<T> type) {
		try {
			return getObjectMapper().readValue(json, type);
		} catch (Exception e) {
			throw new SerializableException("反序列化 " + json + " 时出现错误", e, json);
		}
	}

	/**
	 * 将json转换为对象
	 * 
	 * @param json          Json字符串
	 * @param typeReference 类型泛型
	 * @return 解析后的Java对象
	 */
	public static <T> T json2Object(String json, TypeReference<T> typeReference) {
		try {
			return getObjectMapper().readValue(json, typeReference);
		} catch (Exception e) {
			throw new SerializableException("反序列化 " + json + " 时出现错误", e, json);
		}
	}

	/**
	 * 将对象转换为Map,已经为Map的将不做操作
	 * 
	 * @param objs 对象数组
	 * @return Map结果
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, ?>> objects2Map(List<?> objs) {
		List<Map<String, ?>> result = new LinkedList<>();
		for (Object obj : objs) {
			Map<String, ?> param = null;
			if (obj instanceof Map) {
				param = (Map<String, ?>) obj;
			} else {
				param = BeanUtils.object2Map(obj);
			}
			result.add(param);
		}
		return result;
	}

	/**
	 * 将对象转换为Map
	 * 
	 * @param obj Java对象
	 * @return 转换后的Map对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, ?> object2Map(Object obj) {
		try {
			if (obj instanceof Map) {
				return (Map) obj;
			} else {
				Map<String, Object> map = Param.getParam();
				for (String name : getProperties(obj)) {
					map.put(name, PropertyUtils.getProperty(obj, name));
				}
				return map;
			}
		} catch (Exception e) {
			throw new BeanException("将 " + obj + " 转换为Map时时出现错误", e, obj);
		}
	}

	/**
	 * 构建新对象
	 * 
	 * @param className 新对象名称
	 * @return 新对象
	 */
	public static <F, T extends F> T newInstance(String className, Class<F> ftype) {
		T obj = newInstance(className);
		if (ftype.isAssignableFrom(obj.getClass())) {
			return obj;
		} else {
			throw new BeanException(new ClassCastException(className + "不是[" + ftype.getName() + "]的子类"));
		}
	}

	/**
	 * 构建新对象
	 * 
	 * @param className 新对象名称
	 * @return 新对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className) {
		Class<T> type = null;
		try {
			type = (Class<T>) Class.forName(className);
		} catch (Exception e) {
			throw new BeanException("构建新对象[" + className + "]失败", e);
		}
		return newInstance(type);
	}

	/**
	 * 构建新对象
	 * 
	 * @param type 新对象类型
	 * @return 新对象
	 */
	public static <T> T newInstance(Class<T> type) {
		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new BeanException("构建新对象失败", e);
		}
	}

	/**
	 * 复制新对象
	 * 
	 * @param src  原对象
	 * @param type 目标类型
	 * @return 目标对象
	 */
	public static <T> T copyObject(Object src, Class<T> type) {
		return copyObject(src, null, type);
	}

	/**
	 * 复制新对象,不复制为空的属性
	 * 
	 * @param src  原对象
	 * @param type 目标类型
	 * @return 目标对象
	 */
	public static <T> T copyNotNeObject(Object src, Class<T> type) {
		return copyNotNeObject(src, null, type);
	}

	/**
	 * 复制新对象
	 * 
	 * @param src   原对象
	 * @param names 需要复制的属性
	 * @param type  目标类型
	 * @return 目标对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyObject(Object src, Iterable<String> names, Class<T> type) {
		if (src == null) {
			return null;
		}
		int modifiers = type.getModifiers();
		if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
			if (type.isAssignableFrom(Param.class)) {
				type = (Class<T>) Param.class;
			} else if (type.isAssignableFrom(ArrayList.class)) {
				type = (Class<T>) ArrayList.class;
			}
		}
		return copyObject(newInstance(type), names, src);
	}

	/**
	 * 复制新对象,不复制为空的属性
	 * 
	 * @param src   原对象
	 * @param names 需要复制的属性
	 * @param type  目标类型
	 * @return 目标对象
	 */
	public static <T> T copyNotNeObject(Object src, Iterable<String> names, Class<T> type) {
		if (src == null) {
			return null;
		}
		return copyNotNeObject(newInstance(type), names, src);
	}

	/**
	 * 复制新对象
	 * 
	 * @param dest 目标数据
	 * @param src  原对象
	 * @return 目标对象
	 */
	public static <T> T copyObject(T dest, Object src) {
		return copyObject(dest, null, src);
	}

	/**
	 * 复制新对象,不复制为空的属性
	 * 
	 * @param dest 目标数据
	 * @param src  原对象
	 * @return 目标对象
	 */
	public static <T> T copyNotNeObject(T dest, Object src) {
		return copyNotNeObject(dest, null, src);
	}

	/**
	 * 复制新对象,不复制为空的属性
	 * 
	 * @param dest  目标数据
	 * @param names 需要复制的属性
	 * @param src   原对象
	 * @return 目标对象
	 */
	public static <T> T copyNotNeObject(T dest, final Iterable<String> names, Object src) {
		CopyObjectFilter filter = null;
		final Set<String> fields = new HashSet<>();
		if (names != null) {
			names.forEach(x -> {
				fields.add(x);
			});
		}
		filter = (name, value) -> !CommonFunUtils.isNe(value) && (names == null || fields.contains(name));
		return copyObjectP(dest, src, filter);
	}

	/**
	 * 复制新对象
	 * 
	 * @param dest  目标数据
	 * @param names 需要复制的属性
	 * @param src   原对象
	 * @return 目标对象
	 */
	public static <T> T copyObject(T dest, Iterable<String> names, Object src) {
		CopyObjectFilter filter = null;
		if (names != null) {
			final Set<String> fields = new HashSet<>();
			names.forEach(x -> {
				fields.add(x);
			});
			filter = (name, value) -> fields.contains(name);
		}
		return copyObjectP(dest, src, filter);
	}

	/**
	 * 复制对象
	 * 
	 * @param dest   目标数据
	 * @param src    原对象
	 * @param filter 过滤器
	 * @return 目标对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> T copyObjectP(T dest, Object src, CopyObjectFilter filter) {
		// 判断数据
		if (src == null || dest == null) {
			return dest;
		}
		if (filter == null) {
			filter = new CopyObjectFilter() {
				@Override
				public boolean filter(String name, Object value) {
					return true;
				}
			};
		}
		// 将元数据转换为Map对象
		Map srcMap = null;
		if (src instanceof Map) {
			srcMap = Map.class.cast(src);
		} else {
			srcMap = object2Map(src);
		}
		// 判断并进行复制
		if (dest instanceof Map) {
			// 复制指定对象到新对象
			for (Object entryObj : srcMap.entrySet()) {
				Entry entry = Entry.class.cast(entryObj);
				if (filter.filter((String) entry.getKey(), entry.getValue())) {
					Map.class.cast(dest).put(entry.getKey(), entry.getValue());
				}
			}
		} else {
			// 目标对象是为一个JavaBean
			map2Object(srcMap, filter, dest);
		}
		return dest;
	}

	/**
	 * 复制属性
	 * 
	 * @param dest 目标对象
	 * @param name 需要复制的属性
	 * @param src  原对象
	 * @deprecated 使用{com.yhert.project.common.util.BeanUtils.copyObject(T dest,
	 *             Iterable<String> names, Object src) }代替
	 */
	@Deprecated
	public static void copyProperties(Object dest, String name, Object src) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperty(dest, name, src);
		} catch (Exception e) {
			throw new BeanException("复制属性失败 " + dest + " 时出现错误", e);
		}
	}

	/**
	 * 复制属性
	 * 
	 * @param dest 目标对象
	 * @param src  原对象
	 * @deprecated 使用{com.yhert.project.common.util.BeanUtils.copyObject(T dest,
	 *             Object src) }代替
	 */
	@Deprecated
	public static void copyProperties(Object dest, Object src) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, src);
		} catch (Exception e) {
			throw new BeanException("复制属性失败 " + dest + " 时出现错误", e);
		}
	}

	/**
	 * 复制对象过滤器
	 * 
	 * @author Ricardo Li 2018年5月28日 下午7:10:49
	 *
	 */
	public static interface CopyObjectFilter {
		/**
		 * 过滤器
		 * 
		 * @param name  名称
		 * @param value 值
		 * @return 是否复制
		 */
		boolean filter(String name, Object value);
	}

	/**
	 * Map中的数据赋值为javaBean
	 * 
	 * @param map    待转换的Map对象
	 * @param filter 过滤器
	 * @param t      目标对象
	 * @return 转换后的Java对象
	 */
	private static <T> void map2Object(Map<?, ?> map, CopyObjectFilter filter, T t) {
		try {
			for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(t.getClass())) {
				String name = pd.getName();
				// class是个个数的字段（getClass()方法呗Object占用），所以排除
				if (!"class".equals(name)) {
					if (map.containsKey(name)) {
						Object value = switchType(map.get(name), pd.getPropertyType());
						if (filter.filter(name, value)) {
							PropertyUtils.setProperty(t, name, value);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new BeanException("复制对象出错 " + t + " 时出现错误", e);
		}
	}

	/**
	 * 将Object序列化为Json
	 * 
	 * @param obj 带序列化的Java对象
	 * @return 序列化后的String
	 */
	public static String object2Json(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new SerializableException("序列化 " + obj + " 时出现错误", e, obj);
		}
	}

	/**
	 * 获得属性
	 * 
	 * @param type 类型
	 * @return 属性
	 */
	public static Set<String> getPropertys(Class<?> type) {
		Set<String> pSet = new HashSet<String>();
		for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(type)) {
			if (!"class".equals(pd.getName())) {
				pSet.add(pd.getName());
			}
		}
		return pSet;
	}

	/**
	 * 获得对象中指定的属性值
	 * 
	 * @param bean 对象
	 * @param name 名称
	 * @return 值
	 */
	public static Object getPropertyValue(Object bean, String name) {
		if (bean == null) {
			return null;
		}
		try {
			if (!getPropertys(bean.getClass()).contains(name)) {
				return null;
			} else {
				return PropertyUtils.getProperty(bean, name);
			}
		} catch (Exception e) {
			throw new BeanException("获取" + bean + " 对象的" + name + "属性时发生错误", e, bean, name);
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param bean  对象
	 * @param name  名称
	 * @param value 值
	 * @throws InvocationTargetException if the caller does not have access to the
	 *                                   property accessor method
	 * @throws IllegalAccessException    if the property accessor method throws an
	 *                                   exception
	 */
	@Deprecated
	public static void setProperty(final Object bean, final String name, final Object value)
			throws IllegalAccessException, InvocationTargetException {
		org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
	}

	/**
	 * 设置属性值
	 * 
	 * @param bean  对象
	 * @param name  名称
	 * @param value 值
	 */
	public static void setPropertyValue(final Object bean, final String name, final Object value) {
		if (bean == null || StringUtils.isEmpty(name)) {
			return;
		}
		try {
			if (getPropertys(bean.getClass()).contains(name)) {
				PropertyUtils.setProperty(bean, name, value);
			}
		} catch (Exception e) {
			throw new BeanException("获取" + bean + " 对象的" + name + "属性时发生错误", e, bean, name);
		}
	}

	/**
	 * 获得属性
	 * 
	 * @param obj 对象
	 * @return 属性
	 */
	public static Set<String> getProperties(Object obj) {
		return getPropertys(obj.getClass());
	}

	/**
	 * 转换数据到指定类型
	 * 
	 * @param val  数据
	 * @param type 目标类型
	 * @return 结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T switchType(Object val, Class<T> type) {
		// 如为空不需要进行转换
		if (val == null) {
			return null;
		}
		// 判断是否为子类，子类则不需要进行转换
		if (type.isAssignableFrom(val.getClass())) {
			return (T) val;
		}
		// 如果是数组进行单独处理
		if (type.isArray()) {
			Class<?> ctype = type.getComponentType();
			if (val instanceof Iterable) {
				// 如果为容器就对容器进行分解
				Iterable<Object> objs = Iterable.class.cast(val);
				List<Object> results = new ArrayList<>();
				for (Object obj : objs) {
					results.add(switchType(obj, ctype));
				}
				return (T) results.toArray();
			} else if (val.getClass().isArray()) {
				// 如果为数组就对数组进行分解
				Object[] objs = Object[].class.cast(val);
				List<Object> results = new ArrayList<>();
				for (Object obj : objs) {
					results.add(switchType(obj, ctype));
				}
				return (T) results.toArray();
			} else {
				// 非容器非数组时，对,进行分割
				String str = val.toString();
				String[] strs = str.split(",");
				Object[] objs = new Object[strs.length];
				for (int i = 0; i < objs.length; i++) {
					objs[i] = switchType(strs[i], ctype);
				}
				return (T) objs;
			}
		} else {
			// 非数组且类型不能直接转换，则直接转换为文本再进行二次转换
			String str = val.toString();
			return string2Type(type, str);
		}
	}

	/**
	 * 字符串转指定类型
	 * 
	 * @param type 类型
	 * @param str  数据
	 * @return 结果
	 */
	@SuppressWarnings("unchecked")
	private static <T> T string2Type(Class<T> type, String str) {
		if (type.isEnum()) {
			@SuppressWarnings("rawtypes")
			Class t = (Class) type;
			return (T) EnumUtils.getEnum(t, str);
		} else if (boolean.class.equals(type)) {
			if (StringUtils.isEmpty(str)) {
				return (T) Boolean.FALSE;
			} else {
				if (CommonFunUtils.parseBoolean(str)) {
					return (T) Boolean.TRUE;
				} else {
					return (T) Boolean.FALSE;
				}
			}
		} else if (byte.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return (T) Byte.valueOf((byte) 0);
			else
				return (T) Byte.class.cast(CommonFunUtils.parseLong(str).byteValue());
		} else if (short.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return (T) Short.valueOf((short) 0);
			else
				return (T) Short.class.cast(CommonFunUtils.parseLong(str).shortValue());
		} else if (int.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return (T) Integer.valueOf(0);
			else
				return (T) Integer.class.cast(CommonFunUtils.parseLong(str).intValue());
		} else if (long.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return (T) Long.valueOf(0);
			else
				return (T) Long.class.cast(CommonFunUtils.parseLong(str));
		} else if (float.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return (T) Float.valueOf(0);
			else
				return (T) NumberUtils.createFloat(str);
		} else if (double.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return (T) Double.valueOf(0);
			else
				return (T) NumberUtils.createDouble(str);
		} else if (Boolean.class.equals(type)) {
			if (StringUtils.isEmpty(str)) {
				return null;
			} else {
				str = str.toLowerCase();
				if (CommonFunUtils.parseBoolean(str)) {
					return (T) Boolean.TRUE;
				} else {
					return (T) Boolean.FALSE;
				}
			}
		} else if (Byte.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return null;
			else
				return (T) Byte.class.cast(CommonFunUtils.parseLong(str).byteValue());
		} else if (Short.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return null;
			else
				return (T) Short.class.cast(CommonFunUtils.parseLong(str).shortValue());
		} else if (Integer.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return null;
			else
				return (T) Integer.class.cast(CommonFunUtils.parseLong(str).intValue());
		} else if (Long.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return null;
			else
				return (T) Long.class.cast(CommonFunUtils.parseLong(str));
		} else if (Float.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return null;
			else
				return (T) NumberUtils.createFloat(str);
		} else if (Double.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return null;
			else
				return (T) NumberUtils.createDouble(str);
		} else if (BigInteger.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return null;
			else
				return (T) NumberUtils.createBigInteger(str);
		} else if (BigDecimal.class.equals(type)) {
			if (StringUtils.isEmpty(str))
				return null;
			else
				return (T) NumberUtils.createBigDecimal(str);
		} else if (Date.class.equals(type)) {
			return (T) DateUtils.parseDate(str);
		} else if (BigDecimal.class.equals(type)) {
			return (T) new BigDecimal(str);
		} else if (BigInteger.class.equals(type)) {
			return (T) new BigInteger(str);
		}
		return null;
	}

	/**
	 * 将StringMap依据types为模板进行转换
	 * 
	 * @param param 参数
	 * @param types 类型模板
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	public static Param switchParam(Map param, Class<?>... types) {
		Param result = Param.getParam();
		Map<String, Class<?>> id2Type = new HashMap<>();
		for (Class<?> type : types) {
			if (type != null && !Map.class.isAssignableFrom(type)) {
				for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(type)) {
					id2Type.put(pd.getName(), pd.getPropertyType());
				}
			}
		}
		for (Object entryObj : param.entrySet()) {
			if (entryObj instanceof Entry) {
				Entry entry = Entry.class.cast(entryObj);
				String key = entry.getKey().toString();
				Object value = entry.getValue();
				Class<?> type = id2Type.get(key);
				if (type == null) {
					result.put(key, value);
				} else {
					result.put(key, switchType(value, type));
				}
			}
		}
		return result;
	}

	/**
	 * 效验Javabean
	 * 
	 * @param bean
	 */
	public static <T> void validate(T bean) {
		if (bean == null) {
			return;
		}
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
		Set<ConstraintViolation<T>> set = validator.validate(bean);
		if (set.size() > 0) {
			StringBuilder validateError = new StringBuilder();
			for (ConstraintViolation<T> val : set) {
				validateError.append(val.getMessage() + " ;");
			}
			throw new ValidationException(validateError.toString());
		}
	}
}
