package com.perfree.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
@TableName("p_gallery")
@ApiModel(value = "Gallery对象", description = "")
public class Gallery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty("图库ID")
    private Integer gallerysId;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("用户ID")
    private Integer userId;

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
    public Integer getGallerysId() {
        return gallerysId;
    }

    public void setGallerysId(Integer gallerysId) {
        this.gallerysId = gallerysId;
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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        return "Gallery{" +
            "id=" + id +
            ", gallerysId=" + gallerysId +
            ", name=" + name +
            ", desc=" + desc +
            ", url=" + url +
            ", userId=" + userId +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
