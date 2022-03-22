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
@TableName("p_plugin")
@ApiModel(value = "Plugin对象", description = "")
@Table(value = "p_plugin")
public class Plugin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    @TableField(name = "id", type = "int", isEmpty = false, isPrimary = true, autoIncrement = true)
    private Integer id;

    @ApiModelProperty("插件名")
    @TableField(name = "name", type = "varchar", length = 256)
    private String name;

    @ApiModelProperty("路径")
    @TableField(name = "path", type = "varchar", length = 256)
    private String path;

    @ApiModelProperty("插件描述")
    @TableField(name = "desc", type = "varchar", length = 512)
    private String desc;

    @ApiModelProperty("版本")
    @TableField(name = "version", type = "varchar", length = 64)
    private String version;

    @ApiModelProperty("作者")
    @TableField(name = "author", type = "varchar", length = 64)
    private String author;

    @ApiModelProperty("插件状态:0禁用,1启用")
    @TableField(name = "status", type = "int", defaultValue = "0")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(name = "createTime", type = "datetime")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(name = "updateTime", type = "datetime")
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
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Plugin{" +
            "id=" + id +
            ", name=" + name +
            ", path=" + path +
            ", desc=" + desc +
            ", version=" + version +
            ", author=" + author +
            ", status=" + status +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
