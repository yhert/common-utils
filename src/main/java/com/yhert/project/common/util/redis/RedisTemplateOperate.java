package com.yhert.project.common.util.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * spring的redisTemplate来实现RedisOperate实现
 * 
 * @author Ricardo Li 2017年12月24日 下午1:45:08
 *
 */
public class RedisTemplateOperate implements RedisOperate {
	/**
	 * 萌宠号数量
	 */
	public static final String REDIS_KEY_SEQUENCE = "sequence";

	/**
	 * 信息
	 */
	@SuppressWarnings("rawtypes")
	private RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	public <V> RedisTemplate<String, V> getRedisTemplate() {
		return redisTemplate;
	}

	@SuppressWarnings("rawtypes")
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取键
	 * 
	 * @param pattern
	 *            表达式
	 * @return 键
	 */
	@Override
	public Set<String> keys(String pattern) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		return redisTemplate.keys(pattern);
	}

	/**
	 * 获取序列号
	 * 
	 * @param key
	 *            键
	 * @param count
	 *            数量
	 * @return 最后的序号
	 */
	@Override
	public Long getSequence(String key, long count) {
		return addHashCount(REDIS_KEY_SEQUENCE, key, count);
	}

	@Override
	public Long getSequence(String key) {
		return getSequence(key, 1);
	}

	@Override
	public <V> List<V> getListRang(String key, long start, long end) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForList().range(key, start, end);
	}

	@Override
	public <V> void pushListLeft(String key, Collection<V> value) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		redisTemplate.opsForList().leftPushAll(key, value);
	}

	@Override
	public <V> void pushListLeft(String key, V value) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		redisTemplate.opsForList().leftPush(key, value);
	}

	@Override
	public <V> V popListRight(String key, long timeout, TimeUnit unit) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForList().rightPop(key, timeout, unit);
	}

	@Override
	public <V> V popListRight(String key) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForList().rightPop(key);
	}

	@Override
	public <V> void pushListRight(String key, Collection<V> values) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		redisTemplate.opsForList().leftPushAll(key, values);
	}

	@Override
	public <V> void pushListRight(String key, V value) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		redisTemplate.opsForList().rightPush(key, value);
	}

	@Override
	public <V> V popListLeft(String key, long timeout, TimeUnit unit) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForList().leftPop(key, timeout, unit);
	}

	@Override
	public <V> V popListLeft(String key) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForList().leftPop(key);
	}

	@Override
	public void removeList(String key, long start, long end) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		redisTemplate.opsForList().remove(key, start, end);
	}

	@Override
	public void trimList(String key, long start, long end) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		redisTemplate.opsForList().trim(key, start, end);
	}

	@Override
	public <V> void setList(String key, long index, V value) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		redisTemplate.opsForList().set(key, index, value);
	}

	@Override
	public Long getListSize(String key) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForList().size(key);
	}

	/**************************************************
	 * String
	 **************************************************/

	@Override
	public <T> Boolean setValueIfAbsent(String key, T value) {
		RedisTemplate<String, T> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	@Override
	public <T> void setExpire(String key, long timeout, TimeUnit unit) {
		RedisTemplate<String, T> redisTemplate = getRedisTemplate();
		redisTemplate.expire(key, timeout, unit);
	}

	@Override
	public <T> Boolean hasKey(String key) {
		RedisTemplate<String, T> redisTemplate = getRedisTemplate();
		return redisTemplate.hasKey(key);
	}

	@Override
	public <T> void setValue(String key, T value) {
		RedisTemplate<String, T> redisTemplate = getRedisTemplate();
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public <T> void setValue(String key, T value, long offset) {
		RedisTemplate<String, T> redisTemplate = getRedisTemplate();
		redisTemplate.opsForValue().set(key, value, offset);
	}

	@Override
	public <T> void setValue(String key, T value, long timeout, TimeUnit unit) {
		RedisTemplate<String, T> redisTemplate = getRedisTemplate();
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	@Override
	public Double addValue(String key, double delta) {
		RedisTemplate<String, Long> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForValue().increment(key, delta);
	}

	@Override
	public Long addValue(String key, long delta) {
		RedisTemplate<String, Long> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 删除数据
	 * 
	 * @param keys
	 *            字段
	 */
	@Override
	public void delete(Collection<String> keys) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		redisTemplate.delete(keys);
	}

	@Override
	public void delete(String key) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		redisTemplate.delete(key);
	}

	@Override
	public <V> V getValue(String key) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public <V> List<V> getValue(Collection<String> keys) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForValue().multiGet(keys);
	}

	@Override
	public <V> void setHashIfAbsent(String key, String field, V value) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		redisTemplate.opsForHash().putIfAbsent(key, field, value);
	}

	@Override
	public <V> void setHash(String key, String field, V value) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		redisTemplate.opsForHash().put(key, field, value);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <V> void setHash(String key, Map map) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		redisTemplate.opsForHash().putAll(key, map);
	}

	@Override
	public <V> void deleteHash(String key, String... field) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		redisTemplate.opsForHash().delete(key, (Object[]) field);
	}

	@Override
	public <V> Boolean hasHashKey(String key, String field) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForHash().hasKey(key, field);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getHash(String key, String field) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return (V) redisTemplate.opsForHash().get(key, field);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <V> Map<String, V> getHash(String key) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		Map reuslt = redisTemplate.opsForHash().entries(key);
		return (Map<String, V>) reuslt;
	}

	@Override
	public <V> Long getHashCount(String key, String field) {
		Long count = getHash(key, field);
		if (count == null) {
			return 0L;
		} else {
			return count;
		}
	}

	@Override
	public <V> Long getHashSize(String key) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForHash().size(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <V> List<Long> getHashCount(String key, Collection<String> hashKeys) {
		List<Number> result = getHash(key, hashKeys);
		int size = result.size();
		for (int i = 0; i < size; i++) {
			Number value = result.get(i);
			if (value == null) {
				result.set(i, 0L);
			} else {
				result.set(i, value.longValue());
			}
		}
		return (List) result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <V> List<V> getHash(String key, Collection<String> hashKeys) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForHash().multiGet(key, (Collection) hashKeys);
	}

	@Override
	public <V> Long addHashCount(String key, String field, long delta) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForHash().increment(key, field, delta);
	}

	@Override
	public <V> void addHashCount(String key, String field, double delta) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		redisTemplate.opsForHash().increment(key, field, delta);
	}

	@Override
	public boolean addZSet(String key, String value, double score) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForZSet().add(key, value, score);
	}

	@Override
	public <V> Set<V> getZSet(String key) {
		return getZSet(key, 0, -1);
	}

	@Override
	public <V> Set<V> getZSetS(String key, int start, int end) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForZSet().reverseRange(key, start, end);
	}

	@Override
	public Set<String> getZSetS(String key) {
		return getZSetS(key, 0, -1);
	}

	@Override
	public <V> Set<V> getZSet(String key, int start, int end) {
		RedisTemplate<String, V> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForZSet().reverseRange(key, start, end);
	}

	@Override
	public double addZSetScore(String key, String value, double delta) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		return redisTemplate.opsForZSet().incrementScore(key, value, delta);
	}

}
