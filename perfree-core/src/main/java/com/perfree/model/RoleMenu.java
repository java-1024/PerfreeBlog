package com.perfree.model;

import com.perfree.dataBase.Table;
import com.perfree.dataBase.TableField;

@Table(value = "p_role_menu")
public class RoleMenu {

    @TableField(name = "roleId", type = "int")
    private Integer roleId;
    @TableField(name = "menuId", type = "int")
    private Integer menuId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
