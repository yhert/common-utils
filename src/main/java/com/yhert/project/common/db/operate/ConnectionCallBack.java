package com.yhert.project.common.db.operate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接回调信息
 * 
 * @author Ricardo Li 2018年2月8日 下午4:36:17
 *
 * @param <T>
 *            返回值类型
 */
public interface ConnectionCallBack<T> {
	/**
	 * 操作连接
	 * 
	 * @param connection
	 *            连接
	 * @return 数据
	 * @throws SQLException
	 *             SQL异常
	 */
	T doInConnection(Connection connection) throws SQLException;
}
