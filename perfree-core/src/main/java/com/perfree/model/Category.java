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
 * 分类表
 * </p>
 *
 * @author Perfree
 * @since 2022-03-19
 */
@TableName("p_category")
@ApiModel(value = "Category对象", description = "分类表")
@Table(value = "p_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    @TableField(name = "id", type = "int", isEmpty = false, isPrimary = true, autoIncrement = true)
    private Integer id;

    @ApiModelProperty("分类名")
    @TableField(name = "name", length = 256, type = "varchar")
    private String name;

    @ApiModelProperty("父级id")
    @TableField(name = "pid", type = "int")
    private Integer pid;

    @ApiModelProperty("描述")
    @TableField(name = "desc", length = 512, type = "varchar")
    private String desc;

    @ApiModelProperty("文章数量")
    @TableField(name = "count", type = "int", defaultValue = "0")
    private Integer count;

    @ApiModelProperty("SEO关键字")
    @TableField(name = "metaKeywords", length = 256, type = "varchar")
    private String metaKeywords;

    @ApiModelProperty("SEO描述内容")
    @TableField(name = "metaDescription", length = 512, type = "varchar")
    private String metaDescription;

    @ApiModelProperty("封面图")
    @TableField(name = "thumbnail", length = 256, type = "varchar")
    private String thumbnail;

    @ApiModelProperty("状态0:正常,1禁用")
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
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }
    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
        return "Category{" +
            "id=" + id +
            ", name=" + name +
            ", pid=" + pid +
            ", desc=" + desc +
            ", count=" + count +
            ", metaKeywords=" + metaKeywords +
            ", metaDescription=" + metaDescription +
            ", thumbnail=" + thumbnail +
            ", status=" + status +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
