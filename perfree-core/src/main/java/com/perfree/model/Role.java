package com.perfree.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.perfree.dataBase.DataTable;
import com.perfree.dataBase.DataTableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Perfree
 * @since 2022-03-19
 */
@TableName("p_role")
@ApiModel(value = "Role对象", description = "")
@DataTable(value = "p_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    @DataTableField(name = "id", type = "int", isEmpty = false, isPrimary = true, autoIncrement = true)
    private Integer id;

    @ApiModelProperty("角色名")
    @DataTableField(name = "name", type = "varchar", length = 32)
    private String name;

    @ApiModelProperty("角色描述")
    @DataTableField(name = "description", type = "varchar", length = 256)
    private String description;

    @ApiModelProperty("角色码")
    @DataTableField(name = "code", type = "varchar", length = 32)
    private String code;

    @ApiModelProperty("创建时间")
    @DataTableField(name = "createTime", type = "datetime")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @DataTableField(name = "updateTime", type = "datetime")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Role(){

    }

    public Role(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + id +
            ", name=" + name +
            ", description=" + description +
            ", code=" + code +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
