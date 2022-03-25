package com.perfree.enums;

public enum SystemEnum {

    DB_SETTING_PATH("数据库配置文件地址", "resources/db.properties"),
    DB_TYPE_MYSQL("MYSQL", "mysql"),
    DB_TYPE_SQLITE("SQLITE", "sqlite"),
    STR_SUCCESS("success", "success"),
    MYSQL_PROD_SCRIPT("mysql生产环境脚本", "resources/Perfree.sql"),
    MYSQL_DEV_SCRIPT("mysql开发环境脚本", "Perfree.sql"),
    SQLITE_PROD_SCRIPT("sqlite生产环境脚本", "resources/Perfree-sqlite.sql"),
    SQLITE_DEV_SCRIPT("sqlite开发环境脚本", "Perfree-sqlite.sql"),
    SYS_SEPARATOR("SEPARATOR", "/");



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
