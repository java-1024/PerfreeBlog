package com.perfree.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Perfree
 * @since 2022-03-19
 */
@TableName("p_gallerys")
@ApiModel(value = "Gallerys对象", description = "")
public class Gallerys implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty("封面图路径")
    private String coverUrl;

    @ApiModelProperty("是否加密0:否,1是")
    private Integer isEncryption;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("图片数量")
    private Integer imgCount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
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
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public Integer getIsEncryption() {
        return isEncryption;
    }

    public void setIsEncryption(Integer isEncryption) {
        this.isEncryption = isEncryption;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getImgCount() {
        return imgCount;
    }

    public void setImgCount(Integer imgCount) {
        this.imgCount = imgCount;
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
        return "Gallerys{" +
            "id=" + id +
            ", name=" + name +
            ", desc=" + desc +
            ", coverUrl=" + coverUrl +
            ", isEncryption=" + isEncryption +
            ", password=" + password +
            ", userId=" + userId +
            ", imgCount=" + imgCount +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
