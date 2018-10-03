package com.yhert.project.common.util.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.expression.ExpressionException;

import com.yhert.project.common.util.StringUtils;

/**
 * 表达式引擎工具
 * 
 * @author Ricardo Li 2018年6月29日 下午3:56:24
 *
 */
public class ExpressionUtils {

	/**
	 * 进行四则运算的函数
	 * 
	 * @param data 需要计算的数据
	 * @return 结果
	 */
	public static double runArithmetic(String data) {
		return runArithmetic(data, null);
	}

	/**
	 * 进行四则运算的函数
	 * 
	 * @param data           需要计算的数据
	 * @param expressionUnit 单位以及权重
	 * @return 结果
	 */
	public static double runArithmetic(String data, ExpressionUnit expressionUnit) {
		data = StringUtils.trim(data);
		if (StringUtils.isEmpty(data)) {
			return 0;
		}
		if (expressionUnit == null) {
			expressionUnit = new ExpressionUnit() {
				@Override
				public Double getRate(String unit) {
					return 1.0;
				}
			};
		}
		// LRUMap<K, V>
		return runArithmetic(data.toCharArray(), 0, data.length(), expressionUnit);
	}

	/**
	 * 计算符号
	 * 
	 * @author Ricardo Li 2018年7月2日 下午5:14:35
	 *
	 */
	public static enum Symbol {
		/**
		 * 左括号
		 */
		SYMBOL_LEFT_BRACKET,
		/**
		 * 右括号
		 */
		SYMBOL_RIGHT_BRACKET,
		/**
		 * 加
		 */
		ADD,
		/**
		 * 减
		 */
		SUBTRACTION,
		/**
		 * 乘
		 */
		MULTIPLICATION,
		/**
		 * 除
		 */
		DIVISION,
		/**
		 * 指数计算
		 */
		POW
	}

	/**
	 * 进行四则运算的函数
	 * 
	 * @param datas   需要计算的数据
	 * @param gstart  开始位置
	 * @param gend    结束位置
	 * @param unitMap 单位以及权重
	 * @return 结果
	 */
	private static double runArithmetic(char[] datas, int gstart, int gend, ExpressionUnit expressionUnit) {
		List<Object> expressions = erpressionAnalysis(datas, gstart, gend, expressionUnit);

		// 表达式计算
		Stack<Double> numStack = new Stack<>();
		Stack<Symbol> symStack = new Stack<>();
		int i = 0;
		while (i < expressions.size() || !symStack.isEmpty()) {
			Object obj = null;
			if (i < expressions.size()) {
				obj = expressions.get(i);
			}
			if (obj instanceof Double) {
				double num = Double.class.cast(obj);
				numStack.push(num);
				i++;
			} else if (obj == null || obj instanceof Symbol) {
				Symbol symbol = Symbol.class.cast(obj);
				if (symStack.isEmpty()) {
					symStack.push(symbol);
					i++;
				} else {
					Symbol lastSym = symStack.pop();
					int range = symbol2range(symbol);
					int lastRange = symbol2range(lastSym);
					if (Symbol.SYMBOL_RIGHT_BRACKET.equals(symbol) && Symbol.SYMBOL_LEFT_BRACKET.equals(lastSym)) {
						i++;
					} else {
						if (range > lastRange || Symbol.SYMBOL_LEFT_BRACKET.equals(lastSym)
								|| Symbol.SYMBOL_LEFT_BRACKET.equals(symbol)) {
							symStack.push(lastSym);
							symStack.push(symbol);
							i++;
						} else {
							double lnum = numStack.pop();
							double llnum = numStack.pop();
							double r = 0;
							switch (lastSym) {
							case ADD:
								r = llnum + lnum;
								break;
							case SUBTRACTION:
								r = llnum - lnum;
								break;
							case MULTIPLICATION:
								r = llnum * lnum;
								break;
							case DIVISION:
								r = llnum / lnum;
								break;
							case POW:
								r = Math.pow(llnum, lnum);
								break;
							default:
								throw new ExpressionException("表达式异常[" + lastSym + "]");
							}
							numStack.push(r);
						}
					}
				}
			}
		}
		return numStack.pop();
	}

	/**
	 * 对符号评级
	 * 
	 * @param symbol 符号
	 * @return 结果
	 */
	private static int symbol2range(Symbol symbol) {
		if (symbol == null) {
			return 0;
		} else {
			switch (symbol) {
			case SYMBOL_RIGHT_BRACKET:
			case SYMBOL_LEFT_BRACKET:
				return 3;
			case ADD:
			case SUBTRACTION:
				return 5;
			case MULTIPLICATION:
			case DIVISION:
				return 6;
			case POW:
				return 7;
			default:
				return 0;
			}
		}
	}

	/**
	 * 表达式预处理
	 * 
	 * @param datas          字段
	 * @param gstart         开始位置
	 * @param gend           结束位置
	 * @param expressionUnit 单位
	 * @return 分析后结果
	 */
	private static List<Object> erpressionAnalysis(char[] datas, int gstart, int gend, ExpressionUnit expressionUnit) {
		List<Object> expressions = new ArrayList<>();
		int start = gstart;
		int end = start;
		if (datas.length < gstart) {
			gstart = datas.length;
		}
		if (gstart >= gend) {
			throw new ExpressionException("表达式不能为空");
		}
		// 循环遍历
		int bracketCount = 0;
		do {
			char c = datas[end];
			if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '(' || c == ')') {
				if (start < end) {
					expressions.add(dealUnit(datas, start, end, expressionUnit));
				} else {
					if (c == '-') {
						expressions.add(0.0);
					}
				}
				if (c == '+') {
					expressions.add(Symbol.ADD);
				} else if (c == '-') {
					expressions.add(Symbol.SUBTRACTION);
				} else if (c == '*') {
					expressions.add(Symbol.MULTIPLICATION);
				} else if (c == '/') {
					expressions.add(Symbol.DIVISION);
				} else if (c == '(') {
					expressions.add(Symbol.SYMBOL_LEFT_BRACKET);
					bracketCount++;
				} else if (c == ')') {
					expressions.add(Symbol.SYMBOL_RIGHT_BRACKET);
					bracketCount--;
				} else if (c == '^') {
					expressions.add(Symbol.POW);
				}
				end++;
				start = end;
			} else {
				end++;
			}
		} while (end < gend);
		// 处理最末端数据
		if (start < gend) {
			char c = datas[end - 1];
			if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '(' || c == ')') {
				if (c == '+') {
					expressions.add(Symbol.ADD);
				} else if (c == '-') {
					expressions.add(Symbol.SUBTRACTION);
				} else if (c == '*') {
					expressions.add(Symbol.MULTIPLICATION);
				} else if (c == '/') {
					expressions.add(Symbol.DIVISION);
				} else if (c == '(') {
					expressions.add(Symbol.SYMBOL_LEFT_BRACKET);
					bracketCount++;
				} else if (c == ')') {
					expressions.add(Symbol.SYMBOL_RIGHT_BRACKET);
					bracketCount--;
				} else if (c == '^') {
					expressions.add(Symbol.POW);
				}
			} else {
				expressions.add(dealUnit(datas, start, end, expressionUnit));
				end++;
				start = end;
			}
		}
		if (bracketCount != 0) {
			throw new ExpressionException("表达式括号不匹配[" + new String(datas, gstart, gend - gstart) + "]");
		}
		return expressions;
	}

	/**
	 * 处理数据
	 * 
	 * @param data 数据
	 * @return 结果
	 */
	public static double dealUnit(String data) {
		return dealUnit(data.toCharArray(), 0, data.length(), null);
	}

	/**
	 * 处理数据
	 * 
	 * @param data           数据
	 * @param expressionUnit 单位
	 * @return 结果
	 */
	public static double dealUnit(String data, ExpressionUnit expressionUnit) {
		return dealUnit(data.toCharArray(), 0, data.length(), expressionUnit);
	}

	/**
	 * 处理单位
	 * 
	 * @param datas          原始数据
	 * @param gstart         开始位置
	 * @param gend           结束位置
	 * @param expressionUnit 单位映射
	 * @return 结果
	 */
	private static double dealUnit(char[] datas, int gstart, int gend, ExpressionUnit expressionUnit) {
		if (expressionUnit == null) {
			expressionUnit = new ExpressionUnit() {

				@Override
				public Double getRate(String unit) {
					return 1.0;
				}
			};
		}
		if (datas == null || datas.length <= 0 || gstart == gend) {
			return 0.0;
		}
		if (gend > datas.length) {
			gend = datas.length;
		}
		double result = 0.0;
		int start = 0;
		int end = 0;
		int type = 0; // 0：未开始，1：数字，2：字母
		double tmp = 0.0;
		double z = 1.0;
		do {
			char c = datas[gstart + end];
			if ((c >= '0' && c <= '9') || c == '.') {
				if (type == 2) {
					String s = new String(datas, gstart + start, end - start);
					if (StringUtils.isEmpty(s)) {
						result += tmp;
					} else {
						Double rate = expressionUnit.getRate(s);
						if (rate == null) {
							throw new ExpressionException("无法识别的单位[" + s + "].");
						}
						result += tmp * rate;
					}
					start = end;
				}
				type = 1;
				// } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			} else if (c == '-') {
				if (end == 0) {
					z = -1.0;
					start++;
				} else {
					throw new ExpressionException("负号[-]只能放开开始位置.");
				}
			} else {
				if (type == 1) {
					String s = new String(datas, gstart + start, end - start);
					tmp = Double.parseDouble(s);
					start = end;
				}
				type = 2;
				// throw new ExpressionException("无法识别的字符[" + c + "].");
			}
			end++;
		} while (gstart + end < gend);
		// 处理完成的的数据
		if (start < end) {
			if (type == 1) {
				String s = new String(datas, gstart + start, end - start);
				tmp = Double.parseDouble(s);
				type = 2;
				start = end;
			}
			String s = new String(datas, gstart + start, end - start);
			if (StringUtils.isEmpty(s)) {
				result += tmp;
			} else {
				Double rate = expressionUnit.getRate(s);
				if (rate == null) {
					throw new ExpressionException("无法识别的单位[" + s + "].");
				}
				result += tmp * rate;
			}
		}
		return result * z;
	}
}
