package com.gwghk.ams.util;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * cache操作类
 * @author Ben.wang
 * @time: 2014-3-6
 */
public class CacheManager {
	private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);
	private static HashMap cacheMap = new HashMap();

    /**
     * This class is singleton so private constructor is used.
     */
    private CacheManager() {
            super();
    }

    /**
     * 获得保存中的cache
     * @param key
     * @return Cache
     */
    private synchronized static Cache getCache(String key) {
            return (Cache)cacheMap.get(key);
    }

    /**
     * Looks at the hashmap if a cache item exists or not
     * @param key
     * @return Cache
     */
    private synchronized static boolean hasCache(String key) {
            return cacheMap.containsKey(key);
    }

    /**
     * 重置所有cache
     */
    public synchronized static void invalidateAll() {
            cacheMap.clear();
    }

    /**
     * remove一个cache
     * @param key
     */
    public synchronized static void invalidate(String key) {
            cacheMap.remove(key);
    }

    /**
     * 添加cache
     * @param key
     * @return Cache
     */
    private synchronized static void putCache(String key, Cache object) {
       cacheMap.put(key, object);
    }

    /**
     * 获得cache
     * @param key
     * @return
     */
    public static Cache getContent(String key) {
             if (hasCache(key)) {
                    Cache cache = getCache(key);
                    if (cacheExpired(cache)) {
                        //cache.setExpired(true);
                    	invalidate(key);
                    	return null;
                    }else{
                    	return cache;
                    }
             } else {
                    return null;
             }
    }

    /**
     * 
     * @param key
     * @param content
     * @param ttl   单位秒(如果小于0，表示永远不会过期)
     */
    public static void putContent(String key, Object content, long ttl) {
            Cache cache = new Cache();
            if(ttl < 0){
            	cache.setTimeOut(-1);
            }else{
            	cache.setTimeOut(ttl*1000 + new Date().getTime());
            }
            cache.setKey(key);
            cache.setValue(content);
            cache.setExpired(false);
            putCache(key, cache);
    }
    
    private static boolean cacheExpired(Cache cache) {
            if (cache == null) {
                    return false;
            }
            long milisNow = new Date().getTime();
            long milisExpire = cache.getTimeOut();
            //logger.debug("milisNow:"+milisNow+",milisExpire:"+milisExpire);
            if (milisExpire < 0) {                // Cache never expires 
                    return false;
            } else if (milisNow >= milisExpire) {
                    return true;
            } else {
                    return false;
            }
    }
}
