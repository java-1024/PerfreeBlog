package com.perfree.commons;

import com.perfree.enums.EhCacheEnum;
import org.apache.commons.lang3.StringUtils;

public class OptionCacheUtil {

    /**
     * 根据key获取缓存的Option值
     * @param key key
     * @return String
     */
    public static String getValue(String key) {
        Object value = EhCacheUtil.get(EhCacheEnum.EHCACHE_KEY_OPTION_DATA.getValue(), key);
        if (value == null){
            return "";
        }
        return value.toString();
    }

    /**
     * 根据key获取缓存的Option值
     * @param key key
     * @param defaultValue defaultValue
     * @return String
     */
    public static String getDefaultValue(String key, String defaultValue) {
        Object value = EhCacheUtil.get(EhCacheEnum.EHCACHE_KEY_OPTION_DATA.getValue(), key);
        if (value == null){
            return defaultValue;
        }
        if (StringUtils.isBlank(value.toString())) {
            return defaultValue;
        }
        return value.toString();
    }

    /**
     * 根据key获取bool类型的值
     * @param key key
     * @return boolean
     */
    public static boolean getBoolValue(String key) {
        Object value = EhCacheUtil.get(EhCacheEnum.EHCACHE_KEY_OPTION_DATA.getValue(), key);
        if (value == null){
            return false;
        }
        if (StringUtils.isBlank(value.toString())) {
            return false;
        }
        return Boolean.parseBoolean(value.toString());
    }
}
