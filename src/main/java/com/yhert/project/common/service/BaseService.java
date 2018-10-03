package com.yhert.project.common.service;

import java.util.List;
import java.util.Map;

import com.yhert.project.common.beans.Result;

/**
 * 服务层基础功能
 * 
 * @author Ricardo Li 2017年3月23日 下午7:33:04
 *
 */
public interface BaseService<E> {
	/**
	 * 批量插入数据
	 * 
	 * @param objs
	 *            对象
	 * @return 受影响数目
	 */
	int insert(List<E> objs);

	/**
	 * 插入对象
	 * 
	 * @param obj
	 *            对象
	 * @return 受影响数目
	 */
	int insert(E obj);

	/**
	 * 删除的数据
	 * 
	 * @param param
	 *            判断条件
	 * @return 受影响的数据
	 */
	int delete(Map<?, ?> param);

	/**
	 * 删除数据
	 * 
	 * @param obj
	 *            对象
	 * @return 受影响的数据
	 */
	int delete(E obj);

	/**
	 * 更新指定字段
	 * 
	 * @param updateColumn
	 *            更新字段
	 * @param whereParam
	 *            更新条件
	 * @return 受影响数目
	 */
	int update(Map<?, ?> updateColumn, Map<?, ?> whereParam);

	/**
	 * 更新数据
	 * 
	 * @param obj
	 *            对象
	 * @param param
	 *            更新信息
	 * @return 受影响数目
	 */
	int update(E obj, Map<?, ?> param);

	/**
	 * 更新数据
	 * 
	 * @param obj
	 *            对象
	 * @return 受影响数目
	 */
	int update(E obj);

	/**
	 * 通过参数直接查询相关数据
	 * 
	 * @param whereObj
	 *            直接查询
	 * @return 结果
	 */
	E queryOne(Object whereObj);

	/**
	 * 通过参数直接查询相关数据
	 * 
	 * @param whereParam
	 *            直接查询
	 * @return 结果
	 */
	E queryOne(Map<?, ?> whereParam);

	/**
	 * 通过主键查询数据
	 * 
	 * @param pk
	 *            主键
	 * @return 结果
	 */
	E get(E pk);

	/**
	 * 通过主键查询数据
	 * 
	 * @param pk
	 *            主键
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	E get(Map pk);

	/**
	 * 查询功能
	 * 
	 * @param whereObj
	 *            参数
	 * @return 查詢結果
	 */
	Result<E> query(Object whereObj);

	/**
	 * 查询功能
	 * 
	 * @param whereParam
	 *            参数
	 * @return 查詢結果
	 */
	Result<E> query(Map<?, ?> whereParam);

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param whereParam
	 *            参数
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	<T> List<T> queryListLimit(Map whereParam);

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param whereObj
	 *            参数
	 * @return 结果
	 */
	<T> List<T> queryListLimit(Object whereObj);
}
