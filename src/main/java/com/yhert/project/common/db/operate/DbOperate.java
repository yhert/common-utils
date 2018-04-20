package com.yhert.project.common.db.operate;

import java.util.List;

import javax.sql.DataSource;

import com.yhert.project.common.db.support.ResultSetCallback;

/**
 * 数据库基础操作
 * 
 * @author Ricardo Li 2017年4月3日 上午7:32:31
 *
 */
public interface DbOperate extends DbExecution {
	/**
	 * 获得数据库连接池
	 * 
	 * @return 数据库连接池
	 */
	DataSource getDataSource();

	/**
	 * 更新数据
	 * 
	 * @param sql
	 *            SQL
	 * @param args
	 *            参数
	 * @return 受影响的数据
	 */
	int exeucte(String sql, Object[] args);

	/**
	 * 查询操作
	 * 
	 * @param sql
	 *            SQL
	 * @param args
	 *            参数
	 * @param type
	 *            结果类型
	 * @return 结果
	 */
	<T> T queryForObject(String sql, Object[] args, Class<T> type);

	/**
	 * 查询操作
	 * 
	 * @param sql
	 *            SQL
	 * @param args
	 *            参数
	 * @param type
	 *            结果类型
	 * @return 结果
	 */
	<T> List<T> queryList(String sql, Object[] args, Class<T> type);

	/**
	 * 查询操作
	 * 
	 * @param sql
	 *            SQL
	 * @param args
	 *            参数
	 * @param callback
	 *            操作
	 * @return 结果
	 */
	<T> T query(String sql, Object[] args, ResultSetCallback<T> callback);
}
