package com.yhert.project.common.db.operate.impl;

import java.sql.Connection;

import javax.sql.DataSource;

import com.yhert.project.common.db.operate.ConnectionCallBack;
import com.yhert.project.common.db.operate.DbExecution;
import com.yhert.project.common.excp.dao.DbExecuteException;
import com.yhert.project.common.util.db.DBUtils;

/**
 * 数据源处理为数据执行器
 * 
 * @author Ricardo Li 2018年2月8日 下午4:23:34
 *
 */
public class DataSourceDbExecution implements DbExecution {

	private DataSource dataSource;

	public DataSourceDbExecution(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	/**
	 * 连接执行
	 * 
	 * @param callBack
	 *            回调
	 * @return 结果
	 */
	@Override
	public <T> T execution(ConnectionCallBack<T> callBack) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			return callBack.doInConnection(connection);
		} catch (DbExecuteException e) {
			throw e;
		} catch (Exception e) {
			throw new DbExecuteException("执行异常", e);
		} finally {
			DBUtils.close(connection);
		}
	}
}
