package com.perfree.enums;

public enum RoleEnum {
    ADMIN_ROLE("管理员","admin","网站管理员"),
    USER_ROLE("用户","user","网站用户"),
    EDITOR_ROLE("文章编辑","editor","文章编辑"),
    CONTRIBUTE_ROLE("文章贡献","contribute","文章贡献");

    private String name;

    private String code;

    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    RoleEnum(String name, String code, String desc) {
        this.name = name;
        this.code = code;
        this.desc = desc;
    }
}
