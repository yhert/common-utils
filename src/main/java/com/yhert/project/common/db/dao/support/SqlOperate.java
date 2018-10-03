package com.yhert.project.common.db.dao.support;

import java.util.List;

import com.yhert.project.common.util.db.Column;

/**
 * Sql处理逻辑
 * 
 * @author Ricardo Li 2017年3月23日 下午1:19:57
 *
 */
public interface SqlOperate {
	/**
	 * 获得表单名称
	 * 
	 * @param tableName
	 *            表名称
	 * @return 类型
	 */
	List<Column> getTables(String tableName);

	/**
	 * 获得数据表名称，添加引号
	 * 
	 * @param tableName
	 *            表名称
	 * @return 名称
	 */
	String getTableName(String tableName);

	/**
	 * 获得字段名称，添加引号
	 * 
	 * @param columnName
	 *            字段名称
	 * @return 名称
	 */
	String getColumnName(String columnName);

	/**
	 * 添加分页功能
	 * 
	 * @param sql
	 *            原始SQL
	 * @param start
	 *            开始位置，从0开始
	 * @param limit
	 *            分页大小
	 * @return 结果
	 */
	String sqlAddPageLimit(String sql, int start, int limit);

}
