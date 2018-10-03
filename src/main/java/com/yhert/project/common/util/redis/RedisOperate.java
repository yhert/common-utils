package com.yhert.project.common.util.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作器
 * 
 * @author Ricardo Li 2017年12月24日 下午12:11:19
 *
 */
public interface RedisOperate {
	/**
	 * 萌宠号数量
	 */
	public static final String REDIS_KEY_SEQUENCE = "sequence";
	/**
	 * 锁前缀
	 */
	public static final String LOCK_KEY_PRX = "lock.key:";

	/**
	 * 获取键
	 * 
	 * @param pattern 表达式
	 * @return 键
	 */
	Set<String> keys(String pattern);

	/**************************************************
	 * 获取序列
	 **************************************************/
	/**
	 * 获取序列号
	 * 
	 * @param key   键
	 * @param count 数量
	 * @return 最后的序号
	 */
	Long getSequence(String key, long count);

	/**
	 * 获得序列号
	 * 
	 * @param key 键
	 * @return 值
	 */
	Long getSequence(String key);

	/**************************************************
	 * 消息队列
	 **************************************************/
	/**
	 * 左侧入栈
	 * 
	 * @param key   键
	 * @param value 值
	 */
	<V> void pushListLeft(String key, Collection<V> value);

	/**
	 * 左侧入栈
	 * 
	 * @param key   键
	 * @param value 值
	 */
	<V> void pushListLeft(String key, V value);

	/**
	 * 右侧读入
	 * 
	 * @param key     键
	 * @param timeout 超时
	 * @param unit    单位
	 * @return 值
	 */
	<V> V popListRight(String key, long timeout, TimeUnit unit);

	/**
	 * 右侧读入
	 * 
	 * @param key 键
	 * @return 值
	 */
	<V> V popListRight(String key);

	/**
	 * 左侧入栈
	 * 
	 * @param key   键
	 * @param value 值
	 */
	<V> void pushListRight(String key, Collection<V> value);

	/**
	 * 左侧入栈
	 * 
	 * @param key   键
	 * @param value 值
	 */
	<V> void pushListRight(String key, V value);

	/**
	 * 右侧读入
	 * 
	 * @param key     键
	 * @param timeout 超时
	 * @param unit    单位
	 * @return 值
	 */
	<V> V popListLeft(String key, long timeout, TimeUnit unit);

	/**
	 * 右侧读入
	 * 
	 * @param key 键
	 * @return 值
	 */
	<V> V popListLeft(String key);

	/**
	 * 获取List范围
	 * 
	 * @param key   键
	 * @param start 开始位置
	 * @param end   结束位置
	 * @return 结果
	 */
	<V> List<V> getListRang(String key, long start, long end);

	/**
	 * 清除List
	 * 
	 * @param key   键
	 * @param start 开始位置
	 * @param end   结束位置
	 */
	void removeList(String key, long start, long end);

	/**
	 * 装饰List（相当于移除指定范围外的所有元素）
	 * 
	 * @param key   键
	 * @param start 开始位置
	 * @param end   结束位置
	 */
	void trimList(String key, long start, long end);

	/**
	 * 设置List值
	 * 
	 * @param key   键
	 * @param index 索引
	 * @param value 值
	 */
	<V> void setList(String key, long index, V value);

	/**
	 * 获得List的尺寸
	 * 
	 * @param key 键
	 * @return 大小
	 */
	Long getListSize(String key);

	/**************************************************
	 * String
	 **************************************************/
	/**
	 * 设置值
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 设置成功
	 */
	<T> Boolean setValueIfAbsent(String key, T value);

	/**
	 * 设置过期时间
	 * 
	 * @param key     键
	 * @param timeout 超时时间
	 * @param unit    单位
	 * 
	 */
	<T> void setExpire(String key, long timeout, TimeUnit unit);

	/**
	 * 判断是否有键
	 * 
	 * @param key 键
	 * 
	 */
	<T> Boolean hasKey(String key);

	/**
	 * 设置值
	 * 
	 * @param key   键
	 * @param value 值
	 */
	<T> void setValue(String key, T value);

	/**
	 * 设置值
	 * 
	 * @param key    键
	 * @param value  值
	 * @param offset 偏移
	 */
	<T> void setValue(String key, T value, long offset);

	/**
	 * 设置值
	 * 
	 * @param key     键
	 * @param value   值
	 * @param timeout 超时
	 * @param unit    单位
	 */
	<T> void setValue(String key, T value, long timeout, TimeUnit unit);

	/**
	 * 增加值
	 * 
	 * @param key   键
	 * @param delta 值
	 * @return 结果
	 */
	Double addValue(String key, double delta);

	/**
	 * 增加值
	 * 
	 * @param key   键
	 * @param delta 值
	 * @return 结果
	 */
	Long addValue(String key, long delta);

	/**
	 * 删除值
	 * 
	 * @param key 键
	 */
	void delete(String key);

	/**
	 * 删除数据
	 * 
	 * @param keys 字段
	 */
	void delete(Collection<String> keys);

	/**
	 * 获得值
	 * 
	 * @param key 键
	 * @return 值
	 */
	<V> V getValue(String key);

	/**
	 * 获得值
	 * 
	 * @param keys 键
	 * @return 值
	 */
	<V> List<V> getValue(Collection<String> keys);

	/**************************************************
	 * Hash
	 ***************************************************/
	/**
	 * 如果没有数据就插入数据到Hash
	 * 
	 * @param key   键
	 * @param field 字段
	 * @param value 值
	 */
	<V> void setHashIfAbsent(String key, String field, V value);

	/**
	 * 插入数据到Hash
	 * 
	 * @param key   键
	 * @param field 字段
	 * @param value 值
	 */
	<V> void setHash(String key, String field, V value);

	/**
	 * 插入数据到Hash
	 * 
	 * @param key 键
	 * @param map 值
	 */
	@SuppressWarnings({ "rawtypes" })
	<V> void setHash(String key, Map map);

	/**
	 * 删除Hash值
	 * 
	 * @param key   键
	 * @param field 值
	 */
	<V> void deleteHash(String key, String... field);

	/**
	 * 是否存在此键
	 * 
	 * @param key   键
	 * @param field 字段
	 * @return 结果
	 */
	<V> Boolean hasHashKey(String key, String field);

	/**
	 * 获取Hash中的值
	 * 
	 * @param key   键
	 * @param field 字段
	 * @return 值
	 */
	<V> V getHash(String key, String field);

	/**
	 * 获取map树
	 * 
	 * @param key 键
	 * @return 值
	 */
	<V> Map<String, V> getHash(String key);

	/**
	 * 获取Hash Long类型的值
	 * 
	 * @param key   键
	 * @param field 值
	 * @return 解雇偶，没有值则返回0
	 */
	<V> Long getHashCount(String key, String field);

	/**
	 * 获取hash数据量
	 * 
	 * @param key 键
	 * @return 值
	 */
	<V> Long getHashSize(String key);

	/**
	 * 获取Long类型的值
	 * 
	 * @param key      键
	 * @param hashKeys 查询的键
	 * @return 值，没有则返回0
	 */
	<V> List<Long> getHashCount(String key, Collection<String> hashKeys);

	/**
	 * 获取值
	 * 
	 * @param key      键
	 * @param hashKeys 查询键
	 * @return 结果
	 */
	<V> List<V> getHash(String key, Collection<String> hashKeys);

	/**
	 * 追加delta
	 * 
	 * @param key   键
	 * @param field 字段
	 * @param delta 追加量
	 * @return 追加后结果
	 */
	<V> Long addHashCount(String key, String field, long delta);

	/**
	 * 追加delta
	 * 
	 * @param key   键
	 * @param field 字段
	 * @param delta 追加量
	 */
	<V> void addHashCount(String key, String field, double delta);

	// public <V> int reduceCount(String key, String field) {
	// Long count = getHashCount(key, field)
	//
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// String str = jedis.hget(key, field);
	// int count = 0;
	// if ("".equals(str) || str == null) {
	// jedis.hset(key, field, "0");
	// } else {
	// count = Integer.valueOf(str);
	// if (count <= 1) {
	// count = 0;
	// } else {
	// count = count - 1;
	// }
	// jedis.hset(key, field, count + "");
	// }
	//
	// return count;
	// } catch (Exception e) {
	//
	// return 0;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// /**************************************************
	// * List
	// ***************************************************/
	//
	// public boolean addToList(String key, String value) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// Long count = jedis.lpush(key, value);
	//
	// return count > 0;
	// } catch (Exception e) {
	//
	// return false;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// public boolean addToList(String key, String... value) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// Long count = jedis.lpush(key, value);
	//
	// return count > 0;
	// } catch (Exception e) {
	//
	// return false;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// public boolean addToListx(String key, String value) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// Long count = jedis.lpushx(key, value);
	//
	// return count > 0;
	// } catch (Exception e) {
	//
	// return false;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// public boolean removeListItem(String key, int count, String value) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// Long result = jedis.lrem(key, count, value);
	//
	// return result > 0;
	// } catch (Exception e) {
	//
	// return false;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// public List<String> getList(String key) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// List<String> result = jedis.lrange(key, 0, -1);
	//
	// return result;
	// } catch (Exception e) {
	//
	// return null;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// public List<String> getList(String key, int end) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// List<String> result = jedis.lrange(key, 0, end);
	//
	// return result;
	// } catch (Exception e) {
	//
	// return null;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// public List<String> getList(String key, int start, int end) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// List<String> list = jedis.lrange(key, start, end);
	//
	// return list;
	// } catch (Exception e) {
	//
	// return null;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// public Long getListSize(String key) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// Long count = jedis.llen(key);
	//
	// return count;
	// } catch (Exception e) {
	//
	// return null;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// /**************************************************
	// * Set
	// ***************************************************/
	//
	// public Set<String> getKeys(String pattern) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// Set<String> set = jedis.keys("*" + pattern + "*");
	//
	// return set;
	// } catch (Exception e) {
	//
	// return null;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }

	/**************************************************
	 * ZSet
	 ***************************************************/
	/**
	 * 添加值到有序set
	 * 
	 * @param key   键
	 * @param value 值
	 * @param score 权重
	 * @return 结果
	 */
	boolean addZSet(String key, String value, double score);

	// public boolean addToZSet(String key, Map<String, Double> map) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// Long count = jedis.zadd(key, map);
	//
	// return count > 0;
	// } catch (Exception e) {
	//
	// return false;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
	//
	// public boolean removeZSetItem(String key, String... member) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// Long count = jedis.zrem(key, member);
	//
	// return count > 0;
	// } catch (Exception e) {
	//
	// return false;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }

	/**
	 * 获取值
	 * 
	 * @param key 键
	 * @return 值
	 */
	<V> Set<V> getZSet(String key);

	/**
	 * 获取反转的值
	 * 
	 * @param key   键
	 * @param start 开始
	 * @param end   结束
	 * @return 结果
	 */
	<V> Set<V> getZSetS(String key, int start, int end);

	/**
	 * 获取反转的值
	 * 
	 * @param key 键
	 * @return 值
	 */
	Set<String> getZSetS(String key);

	/**
	 * 获取值
	 * 
	 * @param key   键
	 * @param start 开始
	 * @param end   结束
	 * @return 值
	 */
	<V> Set<V> getZSet(String key, int start, int end);

	// 模糊查询
	// public <V> Set<V> getZrangeByLex(String key, int start, int end) {
	// RedisTemplate<String, V> redisTemplate = getRedisTemplate();
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// Set<String> set = jedis.zrangeByLex(key, "[" + key, "[" + key, start,
	// end);
	// return set;
	// } catch (Exception e) {
	//
	// return null;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }

	/**
	 * 追加权重
	 * 
	 * @param key   键
	 * @param value 值
	 * @param delta 权重
	 * @return 结果
	 */
	double addZSetScore(String key, String value, double delta);

	// public double redScore(String key, String member) {
	// Jedis jedis = null;
	// try {
	// jedis = jedisPool.getResource();
	// double d = jedis.zincrby(key, -1, member);
	//
	// return d;
	// } catch (Exception e) {
	//
	// return 0;
	// } finally {
	// // 返还到连接池
	// jedis.close();
	// }
	// }
}
