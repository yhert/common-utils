package com.yhert.project.common.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;

/**
 * 查询结果集
 * 
 * @author Ricardo Li 2017年3月23日 下午1:01:05
 *
 */
@ApiModel(value = "数据封装", description = "数据封装包")
public class Record extends HashMap<String, Object> implements Map<String, Object>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
