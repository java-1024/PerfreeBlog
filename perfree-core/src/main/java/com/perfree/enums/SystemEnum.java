package com.perfree.enums;

public enum SystemEnum {

    DB_SETTING_PATH("数据库配置文件地址", "resources/db.properties");

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

    SystemEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
