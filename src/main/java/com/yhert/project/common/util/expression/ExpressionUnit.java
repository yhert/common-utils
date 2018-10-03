package com.yhert.project.common.util.expression;

/**
 * 表达式单位
 * 
 * @author Ricardo Li 2018年6月29日 下午6:27:18
 *
 */
public interface ExpressionUnit {
	/**
	 * 获得转换率
	 * 
	 * @param unit
	 *            单位
	 * @return 转换率
	 */
	Double getRate(String unit);
}
