/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">zhilai</a> All rights reserved.
 */
package com.lmxf.post.core.utils.encode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmxf.post.core.utils.SpringContextHolder;
import com.lmxf.post.entity.parameter.Dict;

/**
 * Cache工具类
 * 
 * @author zhilai
 * @version 2013-5-29
 */
public class CacheUtils {

	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);

	public static final String SYS_CACHE = "sysCache";
	
	public static final String DictKey = "DictKey";

	/**
	 * 获取SYS_CACHE缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}
	
	public static Cache getDict() {
		return getCache(SYS_CACHE);
	}

	/**
	 * 获取SYS_CACHE缓存
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String key, Object defaultValue) {
		Object value = get(key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 写入SYS_CACHE缓存
	 * 
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	/**
	 * 从SYS_CACHE缓存中移除
	 * 
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}

	/**
	 * 获取缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		return getCache(cacheName).get(getKey(key));
	}

	/**
	 * 获取缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String cacheName, String key, Object defaultValue) {
		Object value = get(cacheName, getKey(key));
		return value != null ? value : defaultValue;
	}

	/**
	 * 写入缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		getCache(cacheName).put(getKey(key), value);
	}

	/**
	 * 从缓存中移除
	 * 
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(getKey(key));
	}

	/**
	 * 从缓存中移除所有
	 * 
	 * @param cacheName
	 */
	public static void removeAll(String cacheName) {
		Cache<String, Object> cache = getCache(cacheName);
		Set<String> keys = cache.keys();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			cache.remove(it.next());
		}
		logger.info("清理缓存： {} => {}", cacheName, keys);
	}

	private static String getKey(String key) {

		return key;
	}

	/**
	 * 获取缓存键名，多数据源下增加数据源名称前缀
	 * 
	 * @param key
	 * @return
	 */
	private static String getLabel(String type, String value) {
		Cache cache = CacheUtils.getCache(CacheUtils.SYS_CACHE);
		 Map<String, List<Dict>> dictMap=(Map<String, List<Dict>>) cache.get(DictKey);
		List<Dict> dictList = dictMap.get(type);
		for (Dict dict : dictList) {
			if (dict.getDictValue().equals(value)) {
				return dict.getDescription();
			}
		}
		return "";
	}

	/**
	 * 获得一个Cache，没有则显示日志。
	 * 
	 * @param cacheName
	 * @return
	 */
	public static Cache<String, Object> getCache(String cacheName) {
		Cache<String, Object> cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			throw new RuntimeException("当前系统中没有定义“" + cacheName + "”这个缓存。");
		}
		return cache;
	}

}
