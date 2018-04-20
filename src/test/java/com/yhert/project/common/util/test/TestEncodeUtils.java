package com.yhert.project.common.util.test;

import org.junit.Test;

import com.yhert.project.common.util.EncodeUtils;

/**
 * EncodeUtils 测试
 * 
 * @author Ricardo Li 2017年10月30日 下午5:26:38
 *
 */
public class TestEncodeUtils {
	/**
	 * 
	 */
	@Test
	public void test() {
		// 827 3bd c72 9j2 111 a5h
		// System.out.println("b8273bdc729a2111a59");
		// String a = EncodeUtils.switch16to64("b8273bdc729a2111a59");
		// System.out.println(a);
		// String b = EncodeUtils.switch64to16(a);
		// System.out.println(b);
		// System.out.println(EncodeUtils.switch32to16("v"));
		// // System.out.println('Z' - 'A' + 10);
		// // System.out.println('a' - 'a' + 36);
		// // System.out.println('z' - 'a' + 36);
		// System.out.println((long) Math.pow(32, 8) - 1);
		// System.out.println((int) 1 / Math.pow(1.0 / 16, 1.0 / 2));

		System.out.println(EncodeUtils.hexConversion("1000ff0000ff22991000ff0000ff2299", 4, 5));
	}
}
