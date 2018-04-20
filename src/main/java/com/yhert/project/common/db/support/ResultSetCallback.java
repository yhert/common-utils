package com.yhert.project.common.db.support;

import java.sql.ResultSet;

/**
 * 结果操作
 * 
 * @author Ricardo Li 2017年4月3日 上午8:01:37
 *
 */
public interface ResultSetCallback<T> {
	/**
	 * 结果封装
	 * 
	 * @param rs
	 *            结果集
	 * @return 结果
	 * @throws Exception
	 *             异常信息
	 */
	public T callback(ResultSet rs) throws Exception;
}
