package com.perfree.model;

import com.perfree.dataBase.DataTable;
import com.perfree.dataBase.DataTableField;

@DataTable(value = "p_role_menu")
public class RoleMenu {

    @DataTableField(name = "roleId", type = "int")
    private Integer roleId;
    @DataTableField(name = "menuId", type = "varchar", length = 64)
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
