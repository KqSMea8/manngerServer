package com.lmxf.post.core.utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 数据字典缓存工具类
 * 
 * auto_server use
 * 
 */
public class CacheUtils implements DisposableBean {
	
	public static String dictCache="dict_cache";
	public static String errorCodeCache="error_code_cache";
	public static String productInfoCache="product_cache";
	public static String classifyCache="product_class_cache";
	
	public static final String INDEX_SUMMARY_TOTALREVIEW_CACHE_ = "index_summary_totalreview_cache_";	// 首页运营总览缓存key
	public static final String INDEX_SUMMARY_TODAYSALE_CACHE_ = "index_summary_todaysale_cache_";	// 首页今日销售汇总缓存key
	public static final String INDEX_SUMMARY_ONEMONTH_CACHE_ = "index_summary_onemonth_cache_";	// 首页近一月总览缓存key
	
	private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	private static Logger log = Logger.getLogger(CacheUtils.class);

	public static void put(String cacheName,String key, Object value){
		Cache cache=cacheManager.getCache(cacheName);
		if(cache==null) {
			log.debug("初始化:"+cacheName+" 缓存对象....");
			cache = new Cache(cacheName, 5000, false, false, 50000000, 50000000);
			cacheManager.addCache(cacheName);
		}
		cacheManager.getCache(cacheName).put(new Element(key,value));
	}
	
	public static Object get(String cahceName,String key){
		if(cacheManager.getCache(cahceName)==null || cacheManager.getCache(cahceName).get(key)==null){
			return null;
		}
		return cacheManager.getCache(cahceName).get(key).getObjectValue();
	}
	
	
	public void destroy() throws Exception {
		cacheManager.removalAll();
	}
	
}
