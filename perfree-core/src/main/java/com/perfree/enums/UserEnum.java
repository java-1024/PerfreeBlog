package com.perfree.enums;

public enum UserEnum {

    USER_STATUS_ENABLE("用户状态:启用", 0),
    USER_STATUS_DISABLED("数据库配置文件地址", 1);

    private String key;

    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    UserEnum(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
