package com.yhert.project.common.db.dao.interfaces;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.beans.Record;
import com.yhert.project.common.beans.Result;
import com.yhert.project.common.db.dao.IDao;
import com.yhert.project.common.db.dao.support.SqlOperate;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.db.DBUtils;

/**
 * SpringDao基础实现
 * 
 * @author Ricardo Li 2017年5月6日 下午2:48:29
 *
 * @param <E>
 *            泛型接口，不设置则默认查询出的类型为：com.yhert.project.common.beans.Record
 */
public class SpringDao<E> implements Dao<E> {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected IDao baseDao;

	protected Class<E> type;

	protected Class<E> mapType;

	public SpringDao() {
		this(null);
	}

	public SpringDao(IDao baseDao) {
		super();
		this.baseDao = baseDao;
		setType();
	}

	/**
	 * 获取泛型设置泛型值
	 */
	@SuppressWarnings("unchecked")
	private void setType() {
		Type clazz = this.getClass().getGenericSuperclass();
		if (clazz instanceof ParameterizedType) {
			Type[] pTypes = ((ParameterizedType) clazz).getActualTypeArguments();
			if (pTypes.length > 0) {
				if (pTypes[0] instanceof Class) {
					this.type = (Class<E>) pTypes[0];
				} else if (pTypes[0] instanceof ParameterizedType) {
					Type rtype = ((ParameterizedType) pTypes[0]).getRawType();
					if (rtype instanceof Class) {
						type = (Class<E>) rtype;
					}
				}
			}
		}
		if (type == null) {
			log.warn("类 [" + this.getClass().getName() + "] 没有设置泛型");
		} else {
			if (Map.class.isAssignableFrom(type)) {
				this.mapType = (Class<E>) type;
				this.type = null;
				log.warn("类 [" + this.getClass().getName() + "] 设置的泛型为Map");
			} else if (type.equals(Object.class)) {
				this.type = null;
				log.warn("类 [" + this.getClass().getName() + "] 设置的泛型为Object");
			}
		}
	}

	public IDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 批量插入数据
	 * 
	 * @param param
	 *            对象
	 * @return 受影响数目
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insertMapList(List param) {
		return this.baseDao.insert(param, getTableName());
	}

	/**
	 * 插入对象
	 * 
	 * @param param
	 *            插入的数据
	 * @return 受影响数目
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insert(Map param) {
		return this.baseDao.insert(param, getTableName());
	}

	/**
	 * 批量插入数据
	 * 
	 * @param objs
	 *            对象
	 * @return 受影响数目
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insert(List<E> objs) {
		return this.baseDao.insert((List) objs2Maps(objs), getTableName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insert(E obj) {
		return this.baseDao.insert(obj2Map(obj), getTableName());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int delete(Map param) {
		return this.baseDao.delete(param, getTableName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(E obj) {
		return this.baseDao.delete(obj2Map(obj), getTableName());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int update(Map updateColumn, Map whereParam) {
		return this.baseDao.update(updateColumn, whereParam, getTableName());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int update(E obj, Map param) {
		return this.baseDao.update(obj2Map(obj), param, getTableName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public int update(E obj) {
		return this.baseDao.update(obj2Map(obj), getTableName());
	}

	/**
	 * 查询现骨干数据取出其中的一个
	 * 
	 * @param whereObj
	 *            条件
	 * @param returnType
	 *            返回值类型
	 * @return 结果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V> V queryOne(Object whereObj, Class<V> returnType) {
		return (V) this.baseDao.queryOne(obj2Map(whereObj), getTableName(), returnType);
	}

	/**
	 * 查询现骨干数据取出其中的一个
	 * 
	 * @param whereParam
	 *            条件
	 * @param returnType
	 *            返回值类型
	 * @return 结果
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <V> V queryOne(Map whereParam, Class<V> returnType) {
		return (V) this.baseDao.queryOne(whereParam, getTableName(), returnType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E queryOne(Object whereObj) {
		return (E) this.baseDao.queryOne(obj2Map(whereObj), getTableName(), getReturnType());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public E queryOne(Map whereParam) {
		return (E) this.baseDao.queryOne(whereParam, getTableName(), getReturnType());
	}

	@Override
	public E get(Object pk) {
		return this.baseDao.get(pk, getTableName(), type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result<E> query(Object whereObj) {
		return this.baseDao.query(obj2Map(whereObj), getTableName(), getReturnType());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Result<E> query(Map whereParam) {
		return this.baseDao.query(whereParam, getTableName(), getReturnType());
	}

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param whereParam
	 *            参数
	 * @return 结果
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<E> queryListLimit(Map whereParam) {
		return this.baseDao.queryListLimit(whereParam, getTableName(), getReturnType());
	}

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param whereObj
	 *            参数
	 * @return 结果
	 */
	@SuppressWarnings("unchecked")
	public List<E> queryListLimit(Object whereObj) {
		return this.baseDao.queryListLimit(obj2Map(whereObj), getTableName(), getReturnType());
	}

	/**
	 * SQL查询
	 * 
	 * @param sql
	 *            SQL
	 * @param param
	 *            参数
	 * @return 结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <R> List<R> queryListLimit(String sql, Map param, Class<R> returnType) {
		return this.baseDao.queryListLimit(sql, param, returnType);
	}

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param whereObj
	 *            参数
	 * @param returnType
	 *            返回值类型
	 * @return 结果
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryListLimit(Object whereObj, Class<T> returnType) {
		return this.baseDao.queryListLimit(obj2Map(whereObj), getTableName(), returnType);
	}

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param param
	 *            参数
	 * @param returnType
	 *            返回值类型
	 * @return 结果
	 */
	public <T> List<T> queryListLimit(Map<String, ?> param, Class<T> returnType) {
		return this.baseDao.queryListLimit(param, getTableName(), returnType);
	}

	/**
	 * SQL查询
	 * 
	 * @param sql
	 *            SQL
	 * @param param
	 *            参数
	 * @return 结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <R> Result<R> query(String sql, Map param, Class<R> returnType) {
		return this.baseDao.query(sql, param, returnType);
	}

	/**
	 * SQL查询
	 * 
	 * @param sql
	 *            SQL
	 * @param param
	 *            参数
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	public Result<E> query(String sql, Map param) {
		return query(sql, param, getReturnType());
	}

	/**
	 * 查询功能
	 * 
	 * @param whereObj
	 *            参数
	 * @param returnType
	 *            返回類型
	 * @return 查詢結果
	 */
	public <V> Result<V> query(Object whereObj, Class<V> returnType) {
		return query(obj2Map(whereObj), returnType);
	}

	/**
	 * 查询功能
	 * 
	 * @param whereParam
	 *            参数
	 * @param returnType
	 *            返回類型
	 * @return 查詢結果
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <V> Result<V> query(Map whereParam, Class<V> returnType) {
		return baseDao.query(whereParam, getTableName(), returnType);
	}

	/**
	 * 获得返回值类型
	 * 
	 * @return 返回值类型
	 */
	@SuppressWarnings("unchecked")
	public Class<E> getReturnType() {
		if (type == null) {
			if (mapType == null || mapType.equals(Map.class)) {
				return (Class<E>) Record.class;
			} else {
				return mapType;
			}
		} else {
			return type;
		}
	}

	/**
	 * 对象转为Map
	 * 
	 * @param obj
	 *            对象
	 * @return Map结果
	 */
	@SuppressWarnings("rawtypes")
	protected Map obj2Map(Object obj) {
		return BeanUtils.copyObject(obj, Param.class);
	}

	/**
	 * 对象转换为Map
	 * 
	 * @param objs
	 *            对象
	 * @return Map集合
	 */
	protected List<?> objs2Maps(List<?> objs) {
		return BeanUtils.objects2Map(objs);
	}

	/**
	 * 获得表名称
	 * 
	 * @return 表名称
	 */
	public String getTableName() {
		if (type != null) {
			return DBUtils.getTableName(type);
		} else {
			return null;
		}
	}

	/**
	 * 获得表名称
	 * 
	 * @param type
	 *            JavaBean
	 * @return 表名称
	 */
	public String getTableName(Class<?> type) {
		return DBUtils.getTableName(type);
	}

	/**
	 * 获得Sql操作
	 * 
	 * @return Sql操作工具
	 */
	public SqlOperate getSqlOperate() {
		return baseDao.getSqlOperate();
	}

	/**
	 * 设置参数
	 * 
	 * @param value
	 *            值
	 * @param nsql
	 *            拼接的SQL
	 * @param sql
	 *            sql
	 */
	public void setParameter(Object value, String nsql, StringBuilder sql) {
		DBUtils.setParameter(value, nsql, sql);
	}

	/**
	 * 设置参数
	 * 
	 * @param param
	 *            map对象
	 * @param key
	 *            键
	 * @param nsql
	 *            拼接的SQL
	 * @param sql
	 *            sql
	 */
	@SuppressWarnings("rawtypes")
	public void setParameter(Map param, String key, String nsql, StringBuilder sql) {
		DBUtils.setParameter(param, key, nsql, sql);
	}
}
