package com.yhert.project.common.db.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.beans.Result;
import com.yhert.project.common.db.dao.IDao;
import com.yhert.project.common.db.dao.support.SqlOperate;
import com.yhert.project.common.db.dao.support.SqlOperateFactory;
import com.yhert.project.common.db.dao.util.SqlUtils;
import com.yhert.project.common.db.operate.ConnectionCallBack;
import com.yhert.project.common.db.operate.DbOperate;
import com.yhert.project.common.db.support.ResultSetCallback;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.db.Column;

/**
 * Dao对象操作
 * 
 * @author Ricardo Li 2017年5月6日 上午11:31:08
 *
 */
public class IDaoImpl implements IDao {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static ThreadLocal<Integer> limitTL = new ThreadLocal<>();

	/**
	 * 设置limit
	 * 
	 * @param limit
	 *            limit
	 */
	public static void setLimit(Integer limit) {
		limitTL.set(limit);
	}

	/**
	 * 获得limit
	 * 
	 * @return limit
	 */
	public static Integer getLimit() {
		return limitTL.get();
	}

	/**
	 * 数据库执行器
	 */
	private DbOperate dbOperate;

	/**
	 * SQL操作工厂
	 */
	private SqlOperateFactory sqlOperateFactory = null;

	public DbOperate getDbOperate() {
		return dbOperate;
	}

	public void setDbOperate(DbOperate dbOperate) {
		this.dbOperate = dbOperate;
	}

	public SqlOperateFactory getSqlOperateFactory() {
		return sqlOperateFactory;
	}

	public void setSqlOperateFactory(SqlOperateFactory sqlOperateFactory) {
		this.sqlOperateFactory = sqlOperateFactory;
	}

	@Override
	public DataSource getDataSource() {
		return this.dbOperate.getDataSource();
	}

	@Override
	public int exeucte(String sql, Object[] args) {
		return this.dbOperate.exeucte(sql, args);
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, Class<T> type) {
		return this.dbOperate.queryForObject(sql, args, type);
	}

	@Override
	public <T> T query(String sql, Object[] args, ResultSetCallback<T> callback) {
		return this.dbOperate.query(sql, args, callback);
	}

	@Override
	public <T> List<T> queryList(String sql, Object[] args, Class<T> type) {
		return this.dbOperate.queryList(sql, args, type);
	}

	@Override
	public <T> T execution(ConnectionCallBack<T> callBack) {
		return this.dbOperate.execution(callBack);
	}

	/**
	 * 获得SQL操作对象
	 * 
	 * @return SQL操作对象
	 */
	public SqlOperate getSqlOperate() {
		if (sqlOperateFactory == null) {
			sqlOperateFactory = new SqlOperateFactory();
			sqlOperateFactory.setDbExecution(getDbOperate());
		}
		return sqlOperateFactory.getSqlOperate();
	}

	/**
	 * Sql转换（替换占位符）
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数列表
	 */
	protected String sqlSwitch(String sql, Map<String, ?> param, List<Object> args) {
		return SqlUtils.sqlSwitch(sql, param, args);
	}

	/**
	 * 获得完整的表名称
	 * 
	 * @return 完整的表名称
	 */
	protected String getTableName(String tableName) {
		return tableName;
	}

	/**
	 * 插入数据
	 * 
	 * @param param
	 *            参数
	 * @param tableName
	 *            表名称
	 * @return 受影响数目
	 */
	@Override
	public int insert(List<Map<String, ?>> param, String tableName) {
		tableName = getTableName(tableName);
		List<Object> args = new ArrayList<>();
		StringBuilder sql = SqlUtils.getInsertSql(param, tableName, getSqlOperate(), args);
		return exeucte(sql.toString(), args.toArray());
	}

	/**
	 * 插入数据
	 * 
	 * @param param
	 *            参数
	 * @param tableName
	 *            表名称
	 * @return 受影响数目
	 */
	@Override
	public int insert(Map<String, ?> param, String tableName) {
		tableName = getTableName(tableName);
		StringBuilder sql = SqlUtils.getInsertSql(param, tableName, getSqlOperate());
		return this.insert(sql.toString(), param);
	}

	/**
	 * 插入数据
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数
	 * @return 结果
	 */
	@Override
	public int insert(String sql, Map<String, ?> param) {
		return update(sql, param);
	}

	/**
	 * 删除数据
	 * 
	 * @param param
	 *            参数
	 * @param tableName
	 *            表名称
	 * @return 受影响的数据
	 */
	@Override
	public int delete(Map<String, ?> param, String tableName) {
		tableName = getTableName(tableName);
		StringBuilder sql = SqlUtils.getDeleteSql(param, tableName, getSqlOperate());
		return delete(sql.toString(), param);
	}

	/**
	 * 删除数据
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数
	 * @return 受影响的数据
	 */
	@Override
	public int delete(String sql, Map<String, ?> param) {
		List<Object> args = new ArrayList<>();
		String newSql = this.sqlSwitch(sql, param, args);
		return this.exeucte(newSql, args.toArray());
	}

	/**
	 * 更新数据
	 * 
	 * @param updateColumn
	 *            更新的信息，判断内部主键更新其它字段
	 * @param tableName
	 *            表名称
	 * @return 受影响数目
	 */
	@Override
	public <E> int update(Map<String, ?> updateColumn, String tableName) {
		tableName = getTableName(tableName);
		Map<String, Object> whereParam = new HashMap<>();
		List<Column> columns = getSqlOperate().getTables(tableName);
		for (Column column : columns) {
			if (column.isPk()) {
				String name = column.getColumnName();
				String cname = StringUtils.camelName(name);
				String uname = StringUtils.underscoreName(name);
				if (updateColumn.containsKey(cname)) {
					whereParam.put(cname, updateColumn.get(cname));
					updateColumn.remove(cname);
				}
				if (updateColumn.containsKey(uname)) {
					whereParam.put(uname, updateColumn.get(uname));
					updateColumn.remove(uname);
				}
			}
		}
		return this.update(updateColumn, whereParam, tableName);
	}

	/**
	 * 更新指定字段
	 * 
	 * @param updateColumn
	 *            更新字段
	 * @param whereParam
	 *            更新条件
	 * @param tableName
	 *            表名称
	 * @return 受影响数目
	 */
	@Override
	public int update(Map<String, ?> updateColumn, Map<String, ?> whereParam, String tableName) {
		tableName = getTableName(tableName);
		Param param = Param.getParam();
		StringBuilder sql = SqlUtils.getUpdateSql(updateColumn, whereParam, tableName, getSqlOperate(), param);
		return update(sql.toString(), param);
	}

	/**
	 * 更新数据
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数
	 * @return 受影响数目
	 */
	@Override
	public int update(String sql, Map<String, ?> param) {
		List<Object> args = new ArrayList<>();
		String newSql = this.sqlSwitch(sql, param, args);
		return exeucte(newSql, args.toArray());
	}

	/**
	 * 获得查询SQL
	 * 
	 * @param whereParam
	 *            条件
	 * @param tableName
	 *            表名称
	 * @return 查询SQL
	 */
	private StringBuilder getQuerySql(Map<String, ?> whereParam, String tableName) {
		tableName = getTableName(tableName);
		return SqlUtils.getQuerySql(whereParam, tableName, getSqlOperate());
	}

	/**
	 * 查询数据
	 * 
	 * @param sql
	 *            SQL语句
	 * @param whereParam
	 *            参数
	 * @param returnType
	 *            返回值
	 * @return 结果
	 */
	@Override
	public <T> List<T> queryList(String sql, Map<String, ?> whereParam, Class<T> returnType) {
		List<Object> args = new ArrayList<>();
		String newSql = sqlSwitch(sql, whereParam, args);
		return queryList(newSql, args.toArray(), returnType);
	}

	/**
	 * 查询数据
	 * 
	 * @param whereParam
	 *            参数
	 * @param tableName
	 *            数据库表名
	 * @param returnType
	 *            返回值类型
	 * @return 结果
	 */
	@Override
	public <T> List<T> queryList(Map<String, ?> whereParam, String tableName, Class<T> returnType) {
		return queryList(getQuerySql(whereParam, tableName).toString(), whereParam, returnType);
	}

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数
	 * @param returnType
	 *            返回值
	 * @return 结果
	 */
	@Override
	public <T> List<T> queryListLimit(String sql, Map<String, ?> param, Class<T> returnType) {
		List<Object> args = new ArrayList<>();
		String newSql = this.sqlSwitch(sql, param, args);

		// 对分页进行判断
		Integer limit = null;
		Object limitObj = param.get(LIMIT);
		if (ObjectUtils.anyNotNull(limitObj)) {
			try {
				limit = Integer.parseInt(limitObj.toString());
			} catch (Exception e) {
				log.error("string转数字错误", e);
			}
		}
		Integer start = 0;
		String qSql = newSql;
		if (ObjectUtils.allNotNull(limit) && limit > 0) {
			Object startObj = param.get(START);
			if (ObjectUtils.anyNotNull(startObj)) {
				try {
					start = Integer.parseInt(startObj.toString());
				} catch (Exception e) {
					log.error("string转数字错误", e);
				}
			}
		} else {
			limit = limitTL.get();
			if (limit == null) {
				limit = DEFAULT_LIMIT;
			}
		}
		if (Integer.MAX_VALUE != limit && 0 == start) {
			qSql = getSqlOperate().sqlAddPageLimit(newSql, start, limit);
		}
		return queryList(qSql, args.toArray(), returnType);
	}

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param param
	 *            参数
	 * @param tableName
	 *            数据库表名
	 * @param returnType
	 *            返回值类型
	 * @return 结果
	 */
	@Override
	public <T> List<T> queryListLimit(Map<String, ?> param, String tableName, Class<T> returnType) {
		return queryListLimit(getQuerySql(param, tableName).toString(), param, returnType);
	}

	/**
	 * 通过参数直接查询相关数据
	 * 
	 * @param whereParam
	 *            直接查询
	 * @param tableName
	 *            表名称
	 * @param returnType
	 *            返回值类型
	 * @return 结果
	 */
	@Override
	public <T> T queryOne(Map<String, ?> whereParam, String tableName, Class<T> returnType) {
		return queryOne(getQuerySql(whereParam, tableName).toString(), whereParam, returnType);
	}

	/**
	 * 查询一个数据
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数
	 * @param returnType
	 *            类型
	 * @return 結果集
	 */
	@Override
	public <T> T queryOne(String sql, Map<String, ?> param, Class<T> returnType) {
		String qSql = getSqlOperate().sqlAddPageLimit(sql, 0, 1);
		List<T> datas = queryList(qSql, param, returnType);
		if (datas.size() > 0) {
			return datas.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 通过主键查询数据
	 * 
	 * @param pk
	 *            主键
	 * @param tableName
	 *            表名称
	 * @param returnType
	 *            类型
	 * @return 结果
	 */
	@Override
	public <T> T get(Object pk, String tableName, Class<T> returnType) {
		Set<String> pkSet = this.getSqlOperate().getTablePrimaryKey(tableName);
		if (pkSet.size() > 1) {
			throw new IllegalArgumentException("通过主键查询只能适用于只有一个主键的情况，" + getTableName(tableName) + "具有" + pkSet.size() + "个主键");
		}
		Param param = Param.getParam();
		param.put(pkSet.toArray(new String[1])[0], pk);
		return queryOne(param, tableName, returnType);
	}

	/**
	 * 查询单个数据
	 * 
	 * @param sql
	 *            sql语句
	 * @param type
	 *            类型
	 * @return 结果
	 */
	@Override
	public <T> T queryForObject(String sql, final Class<T> type) {
		return queryForObject(sql, new Object[0], type);
	}

	@Override
	public <T> T queryForObject(String sql, Map<String, ?> param, Class<T> type) {
		List<Object> args = new ArrayList<>();
		String newSql = this.sqlSwitch(sql, param, args);
		return queryForObject(newSql, args.toArray(), type);
	}

	/**
	 * 查询功能
	 * 
	 * @param whereParam
	 *            参数
	 * @param tableName
	 *            表名称
	 * @param returnType
	 *            类型
	 * @return 查詢結果
	 */
	public <E> Result<E> query(Map<String, ?> whereParam, String tableName, Class<E> returnType) {
		return query(getQuerySql(whereParam, tableName).toString(), whereParam, returnType);
	}

	/**
	 * 执行查询功能
	 * 
	 * @param sql
	 *            查询SQL
	 * @param param
	 *            参数
	 * @param type
	 *            返回值类型
	 * @return 查询结果
	 */
	public <T> Result<T> query(String sql, Map<String, ?> param, Class<T> type) {
		List<Object> args = new ArrayList<>();
		String newSql = this.sqlSwitch(sql, param, args);
		Result<T> result = new Result<>();

		// 对分页进行判断
		Integer limit = null;
		Object limitObj = param.get(LIMIT);
		if (ObjectUtils.anyNotNull(limitObj)) {
			try {
				limit = Integer.parseInt(limitObj.toString());
			} catch (Exception e) {
				log.error("string转数字错误", e);
			}
		}
		Integer start = 0;
		String qSql = newSql;
		if (ObjectUtils.allNotNull(limit) && limit > 0) {
			Object startObj = param.get(START);
			if (ObjectUtils.anyNotNull(startObj)) {
				try {
					start = Integer.parseInt(startObj.toString());
				} catch (Exception e) {
					log.error("string转数字错误", e);
				}
			}
			result.setStart(start);
		} else {
			limit = limitTL.get();
			if (limit == null) {
				limit = DEFAULT_LIMIT;
			}
		}
		if (Integer.MAX_VALUE != limit || 0 == start) {
			qSql = getSqlOperate().sqlAddPageLimit(newSql, start, limit);
		}
		result.setData(queryList(qSql, args.toArray(), type));
		result.setCount(result.getData().size());

		// 查询总数据条数
		if (ObjectUtils.allNotNull(limit) && limit > 0) {
			StringBuilder limitSql = new StringBuilder();
			limitSql.append("select count(1) as all_count from (");
			limitSql.append(newSql);
			limitSql.append(") ttt___count");
			Integer allCount = queryForObject(limitSql.toString(), args.toArray(), Integer.class);
			result.setAllCount(allCount);
			result.setLimit(limit);
		} else {
			result.setLimit(result.getCount());
			result.setAllCount(result.getCount());
		}
		return result;
	}

}
