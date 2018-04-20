package com.yhert.project.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.yhert.project.common.excp.ApplicationException;

/**
 * 字符串操作函数
 * 
 * @author Ricardo Li 2017年1月2日 下午1:01:25
 *
 */
public class CommonFunUtils {
	/**
	 * 地球半径
	 */
	public static final double EARTH_R = 6371.004 * 1000;
	/**
	 * 地球上1°距离是多少
	 */
	public static final double EARTCH_DISTANCE_UNIT_LATITUDE = Math.PI * EARTH_R / 180;

	/**
	 * 将数组合成字符串
	 * 
	 * @param list
	 *            数组
	 * @param code
	 *            分隔符
	 * @return 字符串
	 */
	public static String join(Object[] list, String code) {
		return join(Arrays.asList(list), code);
	}

	/**
	 * 将数组合成字符串
	 * 
	 * @param list
	 *            数组
	 * @param code
	 *            分隔符
	 * @return 字符串
	 */
	@SuppressWarnings("rawtypes")
	public static String join(List list, String code) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(code);
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	/**
	 * 解析文件尺寸
	 * 
	 * @param size
	 *            文件大小
	 * @return 大小
	 */
	public static long parseFileSize(String size) {
		Double dsize = 0D;
		if (!StringUtils.isEmpty(size)) {
			size = size.toUpperCase();
			if (size.endsWith("KB")) {
				dsize = Double.valueOf(size.substring(0, size.length() - 2)) * 1024;
			} else if (size.endsWith("MB")) {
				dsize = Double.valueOf(size.substring(0, size.length() - 2)) * 1024 * 1024;
			} else if (size.endsWith("GB")) {
				dsize = Double.valueOf(size.substring(0, size.length() - 2)) * 1024 * 1024 * 1024;
			} else {
				dsize = Double.valueOf(size);
			}
		}
		return dsize.longValue();
	}

	/**
	 * 将String解析为Boolean类型
	 * 
	 * @param source
	 *            数据
	 * @return 结果
	 */
	public static Boolean parseBoolean(String source) {
		if (CommonFunUtils.isNe(source)) {
			return null;
		} else {
			source = source.toLowerCase();
			try {
				int i = Integer.parseInt(source);
				return i != 0;
			} catch (Exception e) {
			}
			if (source.equals("true") || source.equals("t") || source.equals("y") || source.equals("yes")
					|| source.equals("on")) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 处理为long信息
	 * 
	 * @param source
	 *            原数据
	 * @return 信息
	 */
	public static Long parseLong(String source) {
		if (CommonFunUtils.isNe(source)) {
			return null;
		} else {
			Long nLong = 0L;
			try {
				nLong = NumberUtils.createLong(source);
			} catch (Exception e) {
				nLong = CommonFunUtils.parseBoolean(source) ? 1L : 0L;
			}
			return nLong;
		}
	}

	/**
	 * 装换为List
	 * 
	 * @param t
	 *            数据
	 * @return list
	 */
	public static <T> List<T> toList(T t) {
		List<T> list = new ArrayList<>();
		list.add(t);
		return list;
	}

	/**
	 * 国际距离（米）距离转换为经纬度距离
	 * 
	 * @param dis
	 *            距离（单位：米）
	 * @return 经纬度距离（单位：度）
	 */
	public static double distance2Latitude(double dis) {
		return dis / EARTCH_DISTANCE_UNIT_LATITUDE;
	}

	/**
	 * 经纬度距离转换为国际距离（米）距离
	 * 
	 * @param lat
	 *            经纬度距离（单位：度）
	 * @return 距离（单位：米）
	 */
	public static double latitude2Distance(double lat) {
		return lat * EARTCH_DISTANCE_UNIT_LATITUDE;
	}

	/**
	 * 抛出异常
	 * 
	 * @param obj
	 *            为空时抛出异常
	 * @param msg
	 *            异常信息
	 */
	public static void nullException(Object obj, String msg) {
		if (isNe(obj)) {
			throw new ApplicationException(msg);
		}
	}

	/**
	 * 判断是否空，字符串为空
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @return 判断结果
	 */
	public static boolean isNe(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String) {
			if ("".equals((String) obj)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为空，包括容器是否为空
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @return 判断结果
	 */
	public static boolean isEntry(Object obj) {
		if (isNe(obj)) {
			return true;
		} else {
			if (obj instanceof Object[]) {
				Object[] objs = (Object[]) obj;
				if (objs.length <= 0) {
					return true;
				} else {
					return false;
				}
			} else if (obj instanceof Collection<?>) {
				Collection<?> objs = (Collection<?>) obj;
				if (objs.size() <= 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	/**
	 * 获得Md5类型的ID号
	 * 
	 * @return UUID uuid
	 */
	public static String getUUID(Object... args) {
		// StringBuffer sb = new StringBuffer();
		// sb.append(getTTID());
		// sb.append(RandomUtils.nextLong());
		// for (Object obj : args) {
		// sb.append(obj);
		// }
		// return DigestUtils.md5Hex(sb.toString());
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}

	public static long getLongUUID(Object... args) {
		String uuid = getUUID(args);
		long u1 = passHex(uuid.substring(0, 16));
		long u2 = passHex(uuid.substring(16, 32));
		long u = u1 + u2;
		if (u < 0) {
			u >>>= 1;
		}
		return u;
	}

	/**
	 * 解析十六进制
	 * 
	 * @param hex
	 *            字符串
	 * @return 结果
	 */
	private static long passHex(String hex) {
		if (StringUtils.isEmpty(hex) || hex.length() <= 0) {
			return 0;
		}
		if (hex.length() >= 16) {
			String first = hex.substring(0, 1);
			int tmp = Integer.parseInt(first, 16);
			if (tmp > 7) {
				tmp /= 2;
				hex = tmp + hex.substring(1, 16);
			}
		}
		return Long.parseLong(hex, 16);
	}

	/**
	 * 获得由时间序列,设备号和计数组合成的ID值
	 * 
	 * @return ID编号
	 */
	public static String getTTID() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%013d", System.currentTimeMillis()));
		if (!StringUtils.isEmpty(deviceCode)) {
			sb.append(deviceCode);
		}
		sb.append(String.format("%06d", getidCount()));
		return sb.toString();
	}

	/**
	 * 设备号
	 */
	private static String deviceCode = String.format("%06d", RandomUtils.nextInt(0, 999999));

	/**
	 * IdCount计数，随机数用于保证多个服务器启动时不容易出现相同的开始值
	 */
	private static volatile AtomicLong idCount = new AtomicLong(0);

	/**
	 * 设置设备号（默认6位随机数）
	 * 
	 * @param deviceCode
	 *            设备号
	 */
	public static void setDeviceCode(String deviceCode) {
		CommonFunUtils.deviceCode = deviceCode;
	}

	/**
	 * 获得计数
	 * 
	 * @return 获得计数器
	 */
	private static int getidCount() {
		long id = idCount.addAndGet(1);
		id %= 100000;
		return (int) id;
	}
}
