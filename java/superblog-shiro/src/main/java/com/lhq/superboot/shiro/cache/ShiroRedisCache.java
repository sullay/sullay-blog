package com.lhq.superboot.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.lhq.superboot.util.RedisUtils;

/**
 * @Description: 实现redis缓存接口
 * 
 * @author: lihaoqi
 * @version: 1.0.0
 * @date: 2019年4月18日
 */
@SuppressWarnings("unchecked")
public class ShiroRedisCache<K, V> implements Cache<K, V> {

	private static Logger logger = LoggerFactory.getLogger(ShiroRedisCache.class);

	private RedisUtils redisUtils;
	private String keyPrefix = "";
	private int expire = 0;
	
	public ShiroRedisCache() {
		if (redisUtils == null) {
			throw new IllegalArgumentException("redisUtils cannot be null.");
		}
		this.redisUtils.setValueSerializer();
	}

	public ShiroRedisCache(RedisUtils redisUtils, String keyPrefix, int expire) {
		if (redisUtils == null) {
			throw new IllegalArgumentException("redisUtils cannot be null.");
		}
		this.redisUtils = redisUtils;
		this.redisUtils.setValueSerializer();
		if (keyPrefix != null && !"".equals(keyPrefix)) {
			this.keyPrefix = keyPrefix;
		}
		if (expire != -1) {
			this.expire = expire;
		}
	}

	private String getRedisCacheKey(K key) {
		if (key == null) {
			return null;
		}
		return this.keyPrefix + key.toString();
	}

	@Override
	public V get(K key) throws CacheException {
		logger.debug("[Shiro] -> get key [{}]", key);
		if (key == null) {
			return null;
		}

		try {
			String redisCacheKey = getRedisCacheKey(key);
			Object rawValue = redisUtils.get(redisCacheKey);
			if (rawValue == null) {
				return null;
			}
			V value = (V) rawValue;
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public V put(K key, V value) throws CacheException {
		logger.debug("[Shiro] -> put key [{}]", key);
		if (key == null) {
			logger.warn("Saving a null key is meaningless, return value directly without call Redis.");
			return value;
		}
		try {
			String redisCacheKey = getRedisCacheKey(key);
			redisUtils.set(redisCacheKey, value != null ? value : null, expire);
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public V remove(K key) throws CacheException {
		logger.debug("[Shiro] -> remove key [{}]", key);
		if (key == null) {
			return null;
		}
		try {
			String redisCacheKey = getRedisCacheKey(key);
			Object rawValue = redisUtils.get(redisCacheKey);
			V previous = (V) rawValue;
			redisUtils.del(redisCacheKey);
			return previous;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void clear() throws CacheException {
		logger.debug("[Shiro] -> clear cache");
		Set<String> keys = null;
		try {
			keys = redisUtils.scan(this.keyPrefix + "*");
		} catch (Exception e) {
			logger.error("get keys error", e);
		}
		if (keys == null || keys.size() == 0) {
			return;
		}
		for (String key : keys) {
			redisUtils.del(key);
		}
	}

	@Override
	public int size() {
		logger.debug("[Shiro] -> get size");
		Long longSize = 0L;
		try {
			longSize = new Long(redisUtils.scanSize(this.keyPrefix + "*"));
		} catch (Exception e) {
			logger.error("get keys error", e);
		}
		return longSize.intValue();
	}

	@Override
	public Set<K> keys() {
		logger.debug("[Shiro] -> get keys");
		Set<String> keys = null;
		try {
			keys = redisUtils.scan(this.keyPrefix + "*");
		} catch (Exception e) {
			logger.error("get keys error", e);
			return Collections.emptySet();
		}

		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptySet();
		}

		Set<K> convertedKeys = new HashSet<K>();
		for (String key : keys) {
			try {
				convertedKeys.add((K) key);
			} catch (Exception e) {
				logger.error("deserialize keys error", e);
			}
		}
		return convertedKeys;
	}

	@Override
	public Collection<V> values() {
		logger.debug("[Shiro] -> get values");
		Set<String> keys = null;
		try {
			keys = redisUtils.scan(this.keyPrefix + "*");
		} catch (Exception e) {
			logger.error("get values error", e);
			return Collections.emptySet();
		}

		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptySet();
		}

		List<V> values = new ArrayList<V>(keys.size());
		for (String key : keys) {
			V value = null;
			try {
				value = (V) redisUtils.get(key);
			} catch (Exception e) {
				logger.error("deserialize values= error", e);
			}
			if (value != null) {
				values.add(value);
			}
		}
		return Collections.unmodifiableList(values);
	}
	
	public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

}
