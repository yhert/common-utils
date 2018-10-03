package com.yhert.project.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

import com.yhert.project.common.excp.CodingException;

/**
 * 编码方式
 * 
 * @author Ricardo Li 2017年10月30日 下午5:06:39
 *
 */
public class EncodeUtils {
	public static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', '_', '@' };

	/**
	 * 进制装换（只能在2的指数倍数进行转换，例如:2,8,16,32,64）
	 * 
	 * @param hex     原数据
	 * @param srcW    原数据进制位数，例如，2进制则输入1,4进制输入2,8进制输入3,16进制输入4。依次类推
	 * @param targetW 目标进制位数，例如，2进制则输入1,4进制输入2,8进制输入3,16进制输入4。依次类推
	 * @return 转换后数据
	 */
	public static String hexConversion(String hex, int srcW, int targetW) {
		StringBuilder sb = new StringBuilder();
		if (hex == null) {
			throw new CodingException("进制装换错误，参数不能为null");
		}
		hex = StringUtils.trim(hex);
		int srcH = (int) Math.pow(2, srcW);
		int targetH = (int) Math.pow(2, targetW);
		if (srcH <= 32) {
			hex = hex.toUpperCase();
		}
		if (StringUtils.isEmpty(hex)) {
			return "";
		}
		char[] hexs = hex.toCharArray();
		int idx = hexs.length / targetW;
		int s = hexs.length - idx * targetW;
		if (s > 0) {
			long i = 0;
			for (int k = 0; k < s; k++) {
				i <<= srcW;
				i = i | char2int(hexs[k]);
			}
			for (int k = srcW - 1; k >= 0; k--) {
				int a = (int) (i >> (targetW * k) & (targetH - 1));
				if (a > 0) {
					sb.append(int2char(a));
				}
			}
		}
		for (int j = 0; j < idx; j++) {
			long i = 0;
			for (int k = 0; k < targetW; k++) {
				i <<= srcW;
				i = i | char2int(hexs[targetW * j + s + k]);
			}
			for (int k = srcW - 1; k >= 0; k--) {
				int a = (int) (i >> (targetW * k) & (targetH - 1));
				sb.append(int2char(a));
			}
		}
		return sb.toString();
	}

	/**
	 * 32进制字符串转16进制
	 * 
	 * @param hex 进制
	 * @return 结果
	 */
	public static String switch32to16(String hex) {
		return hexConversion(hex, 5, 4);
	}

	/**
	 * 16进制字符串转32进制
	 * 
	 * @param hex 进制
	 * @return 结果
	 */
	public static String switch16to32(String hex) {
		return hexConversion(hex, 4, 5);
	}

	/**
	 * 16进制字符串转64进制
	 * 
	 * @param hex 进制
	 * @return 结果
	 */
	public static String switch16to64(String hex) {
		return hexConversion(hex, 4, 6);
	}

	/**
	 * 64进制字符串转16进制
	 * 
	 * @param hex 进制
	 * @return 结果
	 */
	public static String switch64to16(String hex) {
		return hexConversion(hex, 6, 4);
	}

	/**
	 * char转int
	 * 
	 * @param a 数据
	 * @return 目标
	 */
	private static int char2int(char a) {
		if ('0' <= a && a <= '9') {
			return a - '0';
		} else if ('A' <= a && a <= 'Z') {
			return a - 'A' + 10;
		} else if ('a' <= a && a <= 'z') {
			return a - 'a' + 36;
		} else if (digits[62] == a) {
			return 62;
		} else if (digits[63] == a) {
			return 63;
		} else {
			throw new CodingException("进制装换错误，数" + a + "不能装换为数字");
		}
	}

	/**
	 * int转char
	 * 
	 * @param a 数据
	 * @return 目标
	 */
	private static char int2char(int a) {
		return digits[a];
	}

	/**
	 * base64编码
	 * 
	 * @param data 数据
	 * @return 结果
	 */
	public static String base64Encode(String data) {
		try {
			return Base64.getEncoder().encodeToString(data.getBytes("utf-8"));
		} catch (Exception e) {
			throw new CodingException("进行base64编码时出错错误：" + data, data);
		}
	}

	/**
	 * base64解码
	 * 
	 * @param data 数据
	 * @return 结果
	 */
	public static String base64Decode(String data) {
		try {
			return new String(Base64.getDecoder().decode(data), "utf-8");
		} catch (Exception e) {
			throw new CodingException("进行base64解码时出错错误：" + data, data);
		}
	}

	/**
	 * 进行url编码
	 * 
	 * @param data 数据
	 * @return 结果
	 */
	public static String urlEncode(String data) {
		try {
			return URLEncoder.encode(data, "utf-8");
		} catch (Exception e) {
			throw new CodingException("进行url编码时出错错误：" + data, data);
		}
	}

	/**
	 * 进行url解码
	 * 
	 * @param data 数据
	 * @return 结果
	 */
	public static String urlDecoder(String data) {
		try {
			return URLDecoder.decode(data, "utf-8");
		} catch (Exception e) {
			throw new CodingException("进行URL解码时出错错误：" + data, data);
		}
	}
}
