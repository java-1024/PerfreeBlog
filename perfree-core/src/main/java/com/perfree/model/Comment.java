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
@TableName("p_comment")
@ApiModel(value = "Comment对象", description = "")
@Table(value = "p_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    @TableField(name = "id", type = "int", isEmpty = false, isPrimary = true, autoIncrement = true)
    private Integer id;

    @ApiModelProperty("文章id")
    @TableField(name = "articleId", type = "int", isEmpty = false)
    private Integer articleId;

    @ApiModelProperty("父级id")
    @TableField(name = "pid", type = "int")
    private Integer pid;

    @ApiModelProperty("顶层父级id")
    @TableField(name = "topPid", type = "int", defaultValue = "-1")
    private Integer topPid;

    @ApiModelProperty("用户iD")
    @TableField(name = "userId", type = "int", isEmpty = false)
    private Integer userId;

    @ApiModelProperty("评论内容")
    @TableField(name = "content", length = 2048, type = "varchar", isEmpty = false)
    private String content;

    @ApiModelProperty("状态:0正常,1:待审核")
    @TableField(name = "status", type = "int", isEmpty = false)
    private Integer status;

    @ApiModelProperty("头像")
    @TableField(name = "avatar", length = 256, type = "varchar")
    private String avatar;

    @ApiModelProperty("网站地址")
    @TableField(name = "website", length = 256, type = "varchar")
    private String website;

    @ApiModelProperty("邮箱")
    @TableField(name = "email", length = 256, type = "varchar")
    private String email;

    @ApiModelProperty("评论人")
    @TableField(name = "userName", length = 64, type = "varchar")
    private String userName;

    @ApiModelProperty("设备类型")
    @TableField(name = "device", length = 64, type = "varchar")
    private String device;

    @ApiModelProperty("IP地址")
    @TableField(name = "ip", length = 64, type = "varchar")
    private String ip;

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
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Integer getTopPid() {
        return topPid;
    }

    public void setTopPid(Integer topPid) {
        this.topPid = topPid;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
        return "Comment{" +
            "id=" + id +
            ", articleId=" + articleId +
            ", pid=" + pid +
            ", topPid=" + topPid +
            ", userId=" + userId +
            ", content=" + content +
            ", status=" + status +
            ", avatar=" + avatar +
            ", website=" + website +
            ", email=" + email +
            ", userName=" + userName +
            ", device=" + device +
            ", ip=" + ip +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
