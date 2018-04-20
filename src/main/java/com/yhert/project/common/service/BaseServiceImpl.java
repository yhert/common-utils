package com.yhert.project.common.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhert.project.common.beans.Result;
import com.yhert.project.common.db.dao.interfaces.Dao;

/**
 * 基础服务
 * 
 * @author Ricardo Li 2017年3月23日 下午8:39:33
 *
 */
public abstract class BaseServiceImpl<DAO extends Dao<E>, E> implements BaseService<E> {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected DAO dao;

	/**
	 * 批量插入数据
	 * 
	 * @param objs
	 *            对象
	 * @return 受影响数目
	 */
	@Override
	public int insert(List<E> objs) {
		return this.dao.insert(objs);
	}

	/**
	 * 插入对象
	 * 
	 * @param obj
	 *            对象
	 * @return 受影响数目
	 */
	@Override
	public int insert(E obj) {
		return this.dao.insert(obj);
	}

	/**
	 * 通过参数直接查询相关数据
	 * 
	 * @param whereObj
	 *            直接查询
	 * @return 结果
	 */
	@Override
	public E queryOne(Object whereObj) {
		return this.dao.queryOne(whereObj);
	}

	/**
	 * 通过参数直接查询相关数据
	 * 
	 * @param whereParam
	 *            直接查询
	 * @return 结果
	 */
	@Override
	public E queryOne(Map<?, ?> whereParam) {
		return this.dao.queryOne(whereParam);
	}

	/**
	 * 通过主键查询数据
	 * 
	 * @param pk
	 *            主键
	 * @return 结果
	 */
	@Override
	public E get(Object pk) {
		return this.dao.get(pk);
	}

	/**
	 * 删除的数据
	 * 
	 * @param param
	 *            判断条件
	 * @return 受影响的数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delete(Map<?, ?> param) {
		return this.dao.delete((Map<String, ?>) param);
	}

	/**
	 * 删除数据
	 * 
	 * @param obj
	 *            对象
	 * @return 受影响的数据
	 */
	@Override
	public int delete(E obj) {
		return this.dao.delete(obj);
	}

	/**
	 * 更新指定字段
	 * 
	 * @param updateColumn
	 *            更新字段
	 * @param whereParam
	 *            更新条件
	 * @return 受影响数目
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int update(Map<?, ?> updateColumn, Map<?, ?> whereParam) {
		return this.dao.update((Map<String, ?>) updateColumn, (Map<String, ?>) whereParam);
	}

	/**
	 * 更新数据
	 * 
	 * @param obj
	 *            对象
	 * @param param
	 *            更新信息
	 * @return 受影响数目
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int update(E obj, Map<?, ?> param) {
		return this.dao.update(obj, (Map<String, ?>) param);
	}

	/**
	 * 更新数据
	 * 
	 * @param obj
	 *            对象
	 * @return 受影响数目
	 */
	@Override
	public int update(E obj) {
		return this.dao.update(obj);
	}

	/**
	 * 查询功能
	 * 
	 * @param whereObj
	 *            参数
	 * @return 查詢結果
	 */
	@Override
	public Result<E> query(Object whereObj) {
		return this.dao.query(whereObj);
	}

	/**
	 * 查询功能
	 * 
	 * @param whereParam
	 *            参数
	 * @return 查詢結果
	 */
	@Override
	public Result<E> query(Map<?, ?> whereParam) {
		return this.dao.query(whereParam);
	}

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param whereParam
	 *            参数
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public <T> List<T> queryListLimit(Map whereParam) {
		return this.dao.queryListLimit(whereParam);
	}

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param whereObj
	 *            参数
	 * @return 结果
	 */
	@Override
	public <T> List<T> queryListLimit(Object whereObj) {
		return this.dao.queryListLimit(whereObj);
	}
}
