package com.yhert.project.common.util;

import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

/**
 * 字符串处理
 * 
 * @author Ricardo Li 2017年3月22日 下午8:13:24
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 移除开始位置与结束位置的code
	 * 
	 * @param str  数据
	 * @param code 移除的数据
	 * @return 结果
	 */
	public static String trim(String str, String code) {
		return endWiths(startWiths(str, code), code);
	}

	/**
	 * 移除开始位置与结束位置的code
	 * 
	 * @param str  数据
	 * @param code 移除的数据
	 * @return 结果
	 */
	public static String startWiths(String str, String code) {
		if (str == null) {
			return null;
		}
		if (str.endsWith(code)) {
			return str.substring(0, str.length() - code.length());
		} else {
			return str;
		}
	}

	/**
	 * 移除开始位置与结束位置的code
	 * 
	 * @param str  数据
	 * @param code 移除的数据
	 * @return 结果
	 */
	public static String endWiths(String str, String code) {
		if (str == null) {
			return null;
		}
		if (str.startsWith(code)) {
			return str.substring(code.length());
		} else {
			return str;
		}
	}

	/**
	 * 转换为下划线命名法的缓存
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> toUnderscoreCache = new LRUMap(500);

	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->HELLO_WORLD
	 * 
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		if (name == null) {
			return null;
		}
		String us = toUnderscoreCache.get(name);
		if (us == null) {
			StringBuilder result = new StringBuilder();
			if (name.length() > 0) {
				if (name.indexOf("_") != -1) {
					return name;
				}
				// 将第一个字符处理成大写
				result.append(name.substring(0, 1).toUpperCase());
				// 循环处理其余字符
				for (int i = 1; i < name.length(); i++) {
					String s = name.substring(i, i + 1);
					// 在大写字母前添加下划线
					if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
						result.append("_");
					}
					// 其他字符直接转成大写
					result.append(s.toUpperCase());
				}
			}
			us = result.toString().toLowerCase();
			toUnderscoreCache.put(name, us);
		}
		return us;
	}

	/**
	 * 转换为驼峰命名法的缓存
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> toCamelCache = new LRUMap(500);

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		if (name == null) {
			return null;
		}
		String cs = toCamelCache.get(name);
		if (cs == null) {
			StringBuilder result = new StringBuilder();
			// 快速检查
			if (name.isEmpty()) {
				// 没必要转换
				return "";
			} else if (!name.contains("_")) {
				// 不含下划线，仅将首字母小写
				return name.substring(0, 1).toLowerCase() + name.substring(1);
			}
			// 用下划线将原始字符串分割
			String camels[] = name.split("_");
			for (String camel : camels) {
				// 跳过原始字符串中开头、结尾的下换线或双重下划线
				if (camel.isEmpty()) {
					continue;
				}
				// 处理真正的驼峰片段
				if (result.length() == 0) {
					// 第一个驼峰片段，全部字母都小写
					result.append(camel.toLowerCase());
				} else {
					// 其他的驼峰片段，首字母大写
					result.append(camel.substring(0, 1).toUpperCase());
					result.append(camel.substring(1).toLowerCase());
				}
			}
			cs = result.toString();
			toCamelCache.put(name, cs);
		}
		return cs;
	}

}
