package com.yhert.project.common.db.operate.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.yhert.project.common.db.dao.support.JdbcUtils;
import com.yhert.project.common.db.operate.ConnectionCallBack;
import com.yhert.project.common.db.operate.DbOperate;
import com.yhert.project.common.db.support.BeanResultSetCallback;
import com.yhert.project.common.db.support.ResultSetCallback;
import com.yhert.project.common.excp.dao.DBOperateException;
import com.yhert.project.common.excp.dao.DaoOperateException;
import com.yhert.project.common.util.db.DBUtils;

/**
 * 数据库基础操作实现
 * 
 * @author Ricardo Li 2017年4月3日 上午7:32:58
 *
 */
public class DbOperateImpl implements DbOperate {

	/**
	 * 数据库连接池
	 */
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 获得连接
	 * 
	 * @return 获得连接
	 */
	protected Connection getConnection() {
		try {
			return this.getDataSource().getConnection();
		} catch (SQLException e) {
			throw new DBOperateException("获得数据库连接失败", e);
		}
	}

	/**
	 * 执行基本操作，增删改查
	 * 
	 * @param sql
	 *            SQL
	 * @param args
	 *            参数
	 * @return 受影响的条数
	 */
	public int exeucte(String sql, Object[] args) {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = this.getConnection();
			ps = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			if (e instanceof DaoOperateException) {
				throw (DaoOperateException) e;
			} else {
				throw new DaoOperateException("SQL执行失败", e);
			}
		} finally {
			DBUtils.close(connection, ps, null);
		}
	}

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
	@Override
	public <T> T queryForObject(String sql, Object[] args, Class<T> type) {
		return this.query(sql, args, new ResultSetCallback<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T callback(ResultSet rs) throws Exception {
				if (rs.next()) {
					return (T) JdbcUtils.getResultSetValue(rs, 1, type);
				} else {
					return null;
				}
			}
		});
	}

	@Override
	public <T> List<T> queryList(String sql, Object[] args, Class<T> type) {
		return this.query(sql, args, new BeanResultSetCallback<T>(type));
	}

	@Override
	public <T> T query(String sql, Object[] args, ResultSetCallback<T> callback) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = this.getConnection();
			ps = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			rs = ps.executeQuery();
			return callback.callback(rs);
		} catch (Exception e) {
			if (e instanceof DaoOperateException) {
				throw (DaoOperateException) e;
			} else {
				throw new DaoOperateException("SQL执行失败", e);
			}
		} finally {
			DBUtils.close(connection, ps, null);
		}
	}

	@Override
	public <T> T execution(ConnectionCallBack<T> callBack) {
		Connection connection = null;
		try {
			connection = this.getConnection();
			return callBack.doInConnection(connection);
		} catch (Exception e) {
			if (e instanceof DaoOperateException) {
				throw (DaoOperateException) e;
			} else {
				throw new DaoOperateException("SQL执行失败", e);
			}
		} finally {
			DBUtils.close(connection, null, null);
		}
	}
}
