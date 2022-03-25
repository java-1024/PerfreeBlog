package com.perfree.commons;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheUtil {
    /**
     * 获取缓存
     * @param cacheName 缓存名字
     * @return Cache
     */
    private static Cache getCache(String cacheName) {
        CacheManager cacheManager = CacheManager.getInstance();
        if (null == cacheManager) {
            return null;
        }
        return cacheManager.getCache(cacheName);
    }

    /**
     * 新增缓存记录
     * @param cacheName 缓存名字
     * @param key       缓存key
     */
    public static void put(String cacheName, String key, Object value) {
        Cache cache = getCache(cacheName);
        if (null != cache) {
            Element element = new Element(key, value);
            cache.put(element);
        }
    }

    /**
     * 删除缓存记录
     * @param cacheName 缓存名字
     * @param key       缓存key
     * @return boolean  是否成功删除
     */
    public static boolean remove(String cacheName, String key) {
        Cache cache = getCache(cacheName);
        if (null == cache) {
            return false;
        }
        return cache.remove(key);
    }

    /**
     * 删除全部缓存记录
     * @param cacheName 缓存名字
     */
    public static void removeAll(String cacheName) {
        Cache cache = getCache(cacheName);
        if (null != cache) {
            cache.removeAll();
        }
    }

    /**
     * 获取缓存记录
     * @param cacheName  缓存名字
     * @param key        缓存key
     * @return Object    缓存记录数据Object
     */
    public static Object get(String cacheName, String key) {
        Cache cache = getCache(cacheName);
        if (null == cache) {
            return null;
        }
        Element cacheElement = cache.get(key);
        if (null == cacheElement) {
            return null;
        }
        return cacheElement.getObjectValue();
    }

}
