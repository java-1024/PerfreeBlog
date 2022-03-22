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
@TableName("p_link")
@ApiModel(value = "Link对象", description = "")
@Table(value = "p_link")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    @TableField(name = "id", type = "int", isEmpty = false, isPrimary = true, autoIncrement = true)
    private Integer id;

    @ApiModelProperty("网站名")
    @TableField(name = "name", length = 256, type = "varchar", isEmpty = false)
    private String name;

    @ApiModelProperty("网站logo")
    @TableField(name = "logo", length = 256, type = "varchar")
    private String logo;

    @ApiModelProperty("网站描述")
    @TableField(name = "desc", length = 512, type = "varchar")
    private String desc;

    @ApiModelProperty("网站地址")
    @TableField(name = "address", length = 256, type = "varchar")
    private String address;

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
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        return "Link{" +
            "id=" + id +
            ", name=" + name +
            ", logo=" + logo +
            ", desc=" + desc +
            ", address=" + address +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
