package com.perfree.enums;

public enum MenuEnum {

    MENU_TYPE_ADMIN("菜单类型-后台", 1),
    MENU_TYPE_FRONT("菜单类型-前台", 0);

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

    MenuEnum(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
