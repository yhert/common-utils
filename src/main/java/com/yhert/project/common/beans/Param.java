package com.yhert.project.common.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;

/**
 * 参数
 * 
 * @author Ricardo Li 2017年3月23日 上午9:41:36
 *
 */
@ApiModel(value = "参数封装", description = "请求数据参数的公共封装包")
public class Param extends HashMap<String, Object> implements Map<String, Object>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获得一个带参数的参数对象
	 * 
	 * @param args 参数
	 * @return 参数对象
	 */
	public static Param getParam(Object... args) {
		Param param = new Param();
		param.putParam(args);
		return param;
	}

	/**
	 * 设置值
	 * 
	 * @param args 参数
	 * @return 参数对象
	 */
	public Param putParam(Object... args) {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("Param.putParam参数个数必须为0或者2的倍数");
		}
		for (int i = 0; i < args.length / 2; i++) {
			this.put(args[i * 2].toString(), args[i * 2 + 1]);
		}
		return this;
	}
}
