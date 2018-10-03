package com.yhert.project.common.util.expression;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 表达式测试
 * 
 * @author Ricardo Li 2018年6月29日 下午4:25:18
 *
 */
public class ExpressionUtilsTest {

	@Test
	public void runArithmetic() {
		double t = ExpressionUtils.dealUnit("-9个2.3K0.3", (u) -> {
			u = u.toUpperCase();
			switch (u) {
			case "K":
				return 10.0;
			case "个":
				return 100.0;
			}
			return null;
		});
//		System.out.println(t);
		assertTrue("解析单位为数字时出现错误", -923.3 == t);

//		System.out.println(ExpressionUtils.runArithmetic("-7+4K53*5-3", (u) -> {
		t = ExpressionUtils.runArithmetic("(3+(5)*2)", (u) -> {
//		System.out.println(ExpressionUtils.runArithmetic("(-5K5*4)^(-2*6)+4K5*5", (u) -> {
			u = u.toUpperCase();
			switch (u) {
			case "K":
				return 10.0;
			case "个":
				return 100.0;
			}
			return null;
		});
		assertTrue("解析四则运算时出现错误", 13 == t);
		// System.out.println(ExpressionUtils.runArithmetic("6*5"));
	}
}
