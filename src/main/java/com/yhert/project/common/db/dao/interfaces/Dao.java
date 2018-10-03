package com.yhert.project.common.db.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.yhert.project.common.beans.Result;
import com.yhert.project.common.db.dao.IDao;

/**
 * 操作接口
 * 
 * @author Ricardo Li 2017年5月6日 下午1:57:22
 *
 * @param <E> 操作
 */
public interface Dao<E> {
	/**
	 * 排序信息，格式：字段 (空格)顺序[asc|desc]，多个使用,(逗号分隔)，示例：create_time desc,id asc,name
	 */
	public static String SORT = IDao.SORT;
	/**
	 * 排序类型常量，asc
	 */
	public static String VALUE_SORT_TTYPE_ASC = IDao.VALUE_SORT_TTYPE_ASC;
	/**
	 * 排序类型常量，desc
	 */
	public static String VALUE_SORT_TTYPE_DESC = IDao.VALUE_SORT_TTYPE_DESC;
	/**
	 * 分页常量
	 */
	public static String LIMIT = IDao.LIMIT;
	/**
	 * 分页时开始位置常量
	 */
	public static String START = IDao.START;

	/**
	 * 批量插入数据
	 * 
	 * @param param 对象
	 * @return 受影响数目
	 */
	int insertMapList(List<?> param);

	/**
	 * 批量插入数据
	 * 
	 * @param objs 对象
	 * @return 受影响数目
	 */
	int insert(List<E> objs);

	/**
	 * 插入对象
	 * 
	 * @param obj 对象
	 * @return 受影响数目
	 */
	int insert(E obj);

	/**
	 * 插入对象
	 * 
	 * @param param 插入的数据
	 * @return 受影响数目
	 */
	@SuppressWarnings("rawtypes")
	int insert(Map param);

	/**
	 * 删除的数据
	 * 
	 * @param param 判断条件
	 * @return 受影响的数据
	 */
	@SuppressWarnings("rawtypes")
	int delete(Map param);

	/**
	 * 删除数据
	 * 
	 * @param obj 对象
	 * @return 受影响的数据
	 */
	int delete(E obj);

	/**
	 * 更新指定字段
	 * 
	 * @param updateColumn 更新字段
	 * @param whereParam   更新条件
	 * @return 受影响数目
	 */
	@SuppressWarnings("rawtypes")
	int update(Map updateColumn, Map whereParam);

	/**
	 * 更新数据
	 * 
	 * @param obj   对象
	 * @param param 更新信息
	 * @return 受影响数目
	 */
	@SuppressWarnings("rawtypes")
	int update(E obj, Map param);

	/**
	 * 更新数据
	 * 
	 * @param obj 对象
	 * @return 受影响数目
	 */
	int update(E obj);

	/**
	 * 查询现骨干数据取出其中的一个
	 * 
	 * @param whereObj   条件
	 * @param returnType 返回值类型
	 * @return 结果
	 */
	<V> V queryOne(Object whereObj, Class<V> returnType);

	/**
	 * 查询现骨干数据取出其中的一个
	 * 
	 * @param whereParam 条件
	 * @param returnType 返回值类型
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	<V> V queryOne(Map whereParam, Class<V> returnType);

	/**
	 * 通过参数直接查询相关数据
	 * 
	 * @param whereObj 直接查询
	 * @return 结果
	 */
	E queryOne(Object whereObj);

	/**
	 * 通过参数直接查询相关数据
	 * 
	 * @param whereParam 直接查询
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	E queryOne(Map whereParam);

	/**
	 * 通过主键查询数据
	 * 
	 * @param pk 主键
	 * @return 结果
	 */
	E get(E pk);

	/**
	 * 通过主键查询数据
	 * 
	 * @param pk 主键
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	E get(Map pk);

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param whereParam 参数
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	<T> List<T> queryListLimit(Map whereParam);

	/**
	 * 查询数据，并进行分页
	 * 
	 * @param whereObj 参数
	 * @return 结果
	 */
	<T> List<T> queryListLimit(Object whereObj);

	/**
	 * 查询功能
	 * 
	 * @param whereObj 参数
	 * @return 查詢結果
	 */
	Result<E> query(Object whereObj);

	/**
	 * 查询功能
	 * 
	 * @param whereParam 参数
	 * @return 查詢結果
	 */
	@SuppressWarnings("rawtypes")
	Result<E> query(Map whereParam);
}
