package com.yhert.project.common.util.db;

import java.util.List;

/**
 * 数据库元数据操作
 * 
 * @author Ricardo Li 2017年9月6日 下午1:54:42
 *
 */
public interface DbMetadataMessage {
	/**
	 * 获得当前数据库方案
	 * 
	 * @return 当前数据库方案名
	 */
	String getNowSchema();

	/**
	 * 获得表信息
	 * 
	 * @param tableName
	 *            表信息
	 * @return 表信息
	 */
	List<Table> getTableName(String tableName);

	/**
	 * 获得表字段信息
	 * 
	 * @param tableName
	 *            表信息
	 * @return 表信息
	 */
	List<Table> getColumn(String tableName);
}
