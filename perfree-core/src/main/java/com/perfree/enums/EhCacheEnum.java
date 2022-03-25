package com.perfree.enums;

public enum EhCacheEnum {

    EHCACHE_KEY_OPTION_DATA("数据字典缓存", "optionData");

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    EhCacheEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
