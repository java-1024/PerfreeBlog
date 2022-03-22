package com.perfree.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.perfree.dataBase.Table;
import com.perfree.dataBase.TableField;
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
@TableName("p_user")
@ApiModel(value = "User对象", description = "")
@Table(value = "p_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    @TableField(name = "id", type = "int", isEmpty = false, isPrimary = true, autoIncrement = true)
    private Integer id;

    @ApiModelProperty("账户")
    @TableField(name = "account", type = "varchar", length = 32)
    private String account;

    @ApiModelProperty("账户名")
    @TableField(name = "userName", type = "varchar", length = 32)
    private String userName;

    @ApiModelProperty("密码")
    @TableField(name = "password", type = "varchar", length = 32)
    private String password;

    @ApiModelProperty("盐值")
    @TableField(name = "salt", type = "varchar", length = 32)
    private String salt;

    @ApiModelProperty("状态:0正常,1禁用")
    @TableField(name = "status", type = "int", defaultValue = "0")
    private Integer status;

    @ApiModelProperty("头像")
    @TableField(name = "avatar", type = "varchar", length = 256)
    private String avatar;

    @ApiModelProperty("角色id")
    @TableField(name = "roleId", type = "int")
    private Integer roleId;

    @ApiModelProperty("邮箱")
    @TableField(name = "email", type = "varchar", length = 128)
    private String email;

    @ApiModelProperty("网站地址")
    @TableField(name = "website", type = "varchar", length = 256)
    private String website;

    @ApiModelProperty("创建时间")
    @TableField(name = "createTime", type = "datetime")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(name = "updateTime", type = "datetime")
    private Date updateTime;

    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", account=" + account +
            ", userName=" + userName +
            ", password=" + password +
            ", salt=" + salt +
            ", status=" + status +
            ", avatar=" + avatar +
            ", roleId=" + roleId +
            ", email=" + email +
            ", website=" + website +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
