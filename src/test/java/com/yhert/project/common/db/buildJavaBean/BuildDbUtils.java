package com.yhert.project.common.db.buildJavaBean;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.util.StringUtils;

/**
 * 辅助处理工具
 * 
 * @author RicardoLi 2018年8月29日 下午11:21:18
 *
 */
public class BuildDbUtils {
	private static final String START_AT = "@@";

	/**
	 * 解析字符串 例如：id|@type{[(String)]}s，解析后返回：{"@@": "ids", "@type": "String"}
	 * 
	 * @param str 原始数据
	 * @return 解析结果
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> analysisStringByTmp(String str) {
		Param param = Param.getParam();
		if (StringUtils.isEmpty(str)) {
			param.put(START_AT, str);
		} else {
			String content = StringUtils.trim(str);
			Pattern pattern = Pattern.compile("(\\|@[A-Za-z0-9_\\-]+\\{\\[\\(.*?\\)\\]\\})");
			Matcher matcher = pattern.matcher(content);
			StringBuilder result = new StringBuilder();
			int lastEnd = 0;
			while (matcher.find()) {
				// 处理SQL
				int start = matcher.start();
				int end = matcher.end();
				String word = matcher.group();
				result.append(content.substring(lastEnd, start));
				lastEnd = end;
				// 处理参数
				int i = word.indexOf("{[(");
				String key = word.substring(2, i);
				String value = word.substring(i + 3, word.length() - 3);
				param.put("@" + key, value);
			}
			result.append(content.substring(lastEnd, content.length()));
			param.put(START_AT, result.toString());
		}
		return (Map) param;
	}

	public static void main(String[] args) {
		String str = "id|@type{[(String)]}|@description{[(测试{}[]()ds样三)]}sf";
		System.out.println(analysisStringByTmp(str));
	}
}
