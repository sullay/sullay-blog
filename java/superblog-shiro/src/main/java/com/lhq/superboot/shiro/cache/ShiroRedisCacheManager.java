package com.lhq.superboot.shiro.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lhq.superboot.util.RedisUtils;

/**
 * @Description: 重写AbstractCacheManager，写入redis命名
 *
 * @author: lihaoqi
 * @version: 1.0.0
 * @date: 2019年4月18日
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ShiroRedisCacheManager implements CacheManager {

	private final Logger logger = LoggerFactory.getLogger(ShiroRedisCacheManager.class);

	private RedisUtils redisUtils;

	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

	/** 默认时间 **/
	private static final int DEFAULT_EXPIRE = 1800;
	private int expire = DEFAULT_EXPIRE;

	/** 默认key prefix名 **/
	public static final String DEFAULT_CACHE_KEY_PREFIX = "lhq:superboot:shiro:cache:";
	private String keyPrefix = DEFAULT_CACHE_KEY_PREFIX;

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug("get cache, name={}", name);

		Cache cache = caches.get(name);

		if (cache == null) {
			cache = new ShiroRedisCache<K, V>(redisUtils, keyPrefix + name + ":", expire);
			caches.put(name, cache);
		}
		return cache;
	}

	public RedisUtils getRedisManager() {
		return redisUtils;
	}

	public void setRedisManager(RedisUtils redisUtils) {
		this.redisUtils = redisUtils;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

}
