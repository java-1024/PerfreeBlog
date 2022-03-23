package com.perfree.enums;

public enum OptionEnum {
    IS_INSTALLED("是否已安装", "IS_INSTALLED");
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

    OptionEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
