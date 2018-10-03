package com.yhert.project.common.db.operate.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.yhert.project.common.db.operate.ConnectionCallBack;
import com.yhert.project.common.db.operate.DbOperate;
import com.yhert.project.common.db.support.JdbcTemplateMapRowMapper;
import com.yhert.project.common.db.support.ResultSetCallback;
import com.yhert.project.common.excp.dao.DbExecuteException;

public class JdbcTemplateDbOperate implements DbOperate {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int exeucte(String sql, Object[] args) {
		filterArgs(args);
		return this.jdbcTemplate.update(sql, args);
	}

	/**
	 * 过滤参数
	 * 
	 * @param args
	 *            参数
	 */
	private void filterArgs(Object[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && args[i].getClass().isEnum()) {
				args[i] = Enum.class.cast(args[i]).name();
			}
		}
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, Class<T> type) {
		filterArgs(args);
		return this.jdbcTemplate.queryForObject(sql, args, type);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T> queryList(String sql, Object[] args, Class<T> type) {
		filterArgs(args);
		if (Map.class.isAssignableFrom(type)) {
			// if (type.equals(Map.class)) {
			// throw new DaoOperateException("为明确定义");
			// }
			Class<Map> mapType = (Class<Map>) type;
			return this.jdbcTemplate.query(sql, args, new JdbcTemplateMapRowMapper(mapType));
		} else {
			return this.jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<T>(type));
		}
	}

	@Override
	public <T> T query(String sql, Object[] args, final ResultSetCallback<T> callback) {
		filterArgs(args);
		return this.jdbcTemplate.query(sql, args, new ResultSetExtractor<T>() {

			@Override
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				try {
					return callback.callback(rs);
				} catch (SQLException e) {
					throw e;
				} catch (DataAccessException e) {
					throw e;
				} catch (Exception e) {
					throw new DbExecuteException("Dao查询数据异常", e);
				}
			}
		});
	}

	@Override
	public DataSource getDataSource() {
		return this.jdbcTemplate.getDataSource();
	}

	@Override
	public <T> T execution(ConnectionCallBack<T> callBack) {
		return this.jdbcTemplate.execute(new ConnectionCallback<T>() {

			@Override
			public T doInConnection(Connection connection) throws SQLException, DataAccessException {
				return callBack.doInConnection(connection);
			}
		});
	}

}
