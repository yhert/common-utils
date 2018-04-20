package com.yhert.project.common.db.dao;

import java.util.List;
import java.util.Map;

import com.yhert.project.common.beans.Result;
import com.yhert.project.common.db.dao.support.SqlOperate;
import com.yhert.project.common.db.operate.DbOperate;

/**
 * 基础Dao接口
 * 
 * @author Ricardo Li 2017年3月23日 下午9:04:12
 *
 */
public interface IDao extends DbOperate {
	/**
	 * 排序字段常量
	 */
	public static String SORT_COLUMN = "sortColumn";
	/**
	 * 排序类型常量，asc,desc
	 */
	public static String SORT_TTYPE = "sortType";
	/**
	 * 排序类型常量，asc
	 */
	public static String VALUE_SORT_TTYPE_ASC = "asc";
	/**
	 * 排序类型常量，desc
	 */
	public static String VALUE_SORT_TTYPE_DESC = "desc";
	/**
	 * 分页常量
	 */
	public static String LIMIT = "limit";
	/**
	 * 开始位置常量
	 */
	public static String START = "start";
	/**
	 * 默认分页
	 */
	public static Integer DEFAULT_LIMIT = 200;

	/**
	 * 获得SQL操作工具
	 * 
	 * @return SQL操作工具
	 */
	SqlOperate getSqlOperate();

	/**
	 * 插入数据
	 * 
	 * @param param
	 *            参数
	 * @param tableName
	 *            表名称
	 * @return 受影响数目
	 */
	int insert(List<Map<String, ?>> param, String tableName);

	/**
	 * 插入数据
	 * 
	 * @param param
	 *            参数
	 * @param tableName
	 *            表名称
	 * @return 受影响数目
	 */
	int insert(Map<String, ?> param, String tableName);

	/**
	 * 插入数据
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数
	 * @return 结果
	 */
	int insert(String sql, Map<String, ?> param);

	/**
	 * 删除数据
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数
	 * @return 受影响的数据
	 */
	public int delete(String sql, Map<String, ?> param);

	/**
	 * 删除数据
	 * 
	 * @param param
	 *            参数
	 * @param tableName
	 *            表名称
	 * @return 受影响的数据
	 */
	public int delete(Map<String, ?> param, String tableName);

	/**
	 * 更新数据
	 * 
	 * @param updateColumn
	 *            更新的信息，判断内部主键更新其它字段
	 * @param tableName
	 *            表名称
	 * @return 受影响数目
	 */
	<E> int update(Map<String, ?> updateColumn, String tableName);

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
	int update(Map<String, ?> updateColumn, Map<String, ?> whereParam, String tableName);

	/**
	 * 更新数据
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数
	 * @return 受影响数目
	 */
	int update(String sql, Map<String, ?> param);

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
	<T> List<T> queryList(String sql, Map<String, ?> whereParam, Class<T> returnType);

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
	<T> List<T> queryList(Map<String, ?> whereParam, String tableName, Class<T> returnType);

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
	<T> List<T> queryListLimit(String sql, Map<String, ?> param, Class<T> returnType);

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
	<T> List<T> queryListLimit(Map<String, ?> param, String tableName, Class<T> returnType);

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
	<T> T queryOne(Map<String, ?> whereParam, String tableName, Class<T> returnType);

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
	<T> T queryOne(String sql, Map<String, ?> param, Class<T> returnType);

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
	<T> T get(Object pk, String tableName, Class<T> returnType);

	/**
	 * 查询单个数据
	 * 
	 * @param sql
	 *            sql语句
	 * @param returnType
	 *            类型
	 * @return 结果
	 */
	<T> T queryForObject(String sql, final Class<T> returnType);

	/**
	 * 查询单个数据
	 * 
	 * @param sql
	 *            SQL语句
	 * @param param
	 *            参数
	 * @param returnType
	 *            返回值类型
	 * @return 数据
	 */
	<T> T queryForObject(String sql, Map<String, ?> param, Class<T> returnType);

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
	<E> Result<E> query(Map<String, ?> whereParam, String tableName, Class<E> returnType);

	/**
	 * 执行查询功能
	 * 
	 * @param sql
	 *            查询SQL
	 * @param param
	 *            参数
	 * @param returnType
	 *            返回值类型
	 * @return 查询结果
	 */
	<T> Result<T> query(String sql, Map<String, ?> param, Class<T> returnType);
}
