package com.yhert.project.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.yhert.project.common.excp.SerializableException;
import com.yhert.project.common.util.expression.ExpressionUnit;
import com.yhert.project.common.util.expression.ExpressionUtils;

/**
 * 时间贡酒
 * 
 * @author Ricardo Li 2017年12月12日 下午1:48:21
 *
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	/**
	 * 格式化时间
	 * 
	 * @param date 时间字符串
	 * @return 格式化后的时间
	 */
	public static Date parseDate(Object date) {
		if (CommonFunUtils.isNe(date)) {
			return null;
		}
		String source = date.toString();
		if ((source.length() == 13) && (source.matches("[0-9]+"))) {
			return new Date(Long.parseLong(source));
		}
		String splitChar = "/";
		if (source.indexOf("-") > -1) {
			splitChar = "-";
		}
		Date dateObj = null;
		if (source.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2}"))
			dateObj = parseDate(source, "yyyy" + splitChar + "MM" + splitChar + "dd");
		else if (source.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}:\\d{2}"))
			dateObj = parseDate(source, "yyyy" + splitChar + "MM" + splitChar + "dd HH:mm:ss");
		else if (source.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}"))
			dateObj = parseDate(source, "yyyy" + splitChar + "MM" + splitChar + "dd HH:mm");
		else if (source.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2} \\d{2}"))
			dateObj = parseDate(source, "yyyy" + splitChar + "MM" + splitChar + "dd HH");
		else if (source.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{0,3}"))
			dateObj = parseDate(source, "yyyy" + splitChar + "MM" + splitChar + "dd HH:mm:ss.S");
		else if (source.matches("\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}"))
			dateObj = parseDate(source, "MM" + splitChar + "dd HH:mm");
		else if (source.matches("\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}:\\d{2}"))
			dateObj = parseDate(source, "MM" + splitChar + "dd HH:mm:ss");
		else if (source.matches("\\d{2}:\\d{2}:\\d{2}\\.\\d{0,3}"))
			dateObj = parseDate(source, "HH:mm:ss.S");
		else if (source.matches("\\d{2}:\\d{2}:\\d{2}"))
			dateObj = parseDate(source, "HH:mm:ss");
		else if (source.matches("\\d{2}:\\d{2}"))
			dateObj = parseDate(source, "HH:mm");
		else if (source.matches("\\d{4}" + splitChar + "\\d{2}"))
			dateObj = parseDate(source, "yyyy" + splitChar + "MM");
		else if (source.matches("\\d{4}"))
			dateObj = parseDate(source, "yyyy");
		else if (source.matches("\\w{3} \\w{3}.*")) {
			try {
				dateObj = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us")).parse(source);
			} catch (ParseException e) {
				throw new SerializableException("格式化时间失败", e);
			}
		}
		return dateObj;
	}

	/**
	 * 格式化时间
	 * 
	 * @param date   时间字符串
	 * @param format 格式
	 * @return 解雇偶
	 */
	public static Date parseDate(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			throw new SerializableException("格式化时间失败", e);
		}
	}

	/**
	 * 解析时间（时间长度）
	 * 
	 * @param time 时间
	 * @return 时间长度（毫秒）
	 */
	public static long parseTime(String time) {
		return (long) ExpressionUtils.runArithmetic(time, new ExpressionUnit() {
			@Override
			public Double getRate(String unit) {
				if ("MS".equalsIgnoreCase(unit)) {
					return 1.0;
				} else if ("S".equalsIgnoreCase(unit)) {
					return 1000.0;
				} else if ("M".equalsIgnoreCase(unit)) {
					return 1000.0 * 60;
				} else if ("H".equalsIgnoreCase(unit)) {
					return 1000.0 * 60 * 60;
				} else if ("D".equalsIgnoreCase(unit)) {
					return 1000.0 * 60 * 60 * 24;
				} else if ("W".equalsIgnoreCase(unit)) {
					return 1000.0 * 60 * 60 * 24 * 7;
				} else {
					return 1.0;
				}
			}
		});
	}

	/**
	 * 格式化时间
	 * 
	 * @param date   时间
	 * @param format 格式
	 * @return 结果
	 */
	public static String dateFormat(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
}
