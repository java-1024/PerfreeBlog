package com.perfree.model;

import com.perfree.dataBase.Table;
import com.perfree.dataBase.TableField;

@Table(value = "p_role_menu")
public class RoleMenu {

    @TableField(name = "roleId", type = "int")
    private Integer roleId;
    @TableField(name = "menuId", type = "varchar", length = 64)
    private String menuId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
