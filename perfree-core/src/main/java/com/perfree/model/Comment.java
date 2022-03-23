package com.perfree.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("p_comment")
@ApiModel(value = "Comment对象", description = "")
@DataTable(value = "p_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    @DataTableField(name = "id", type = "int", isEmpty = false, isPrimary = true, autoIncrement = true)
    private Integer id;

    @ApiModelProperty("文章id")
    @DataTableField(name = "articleId", type = "int", isEmpty = false)
    private Integer articleId;

    @ApiModelProperty("父级id")
    @DataTableField(name = "pid", type = "int")
    private Integer pid;

    @ApiModelProperty("顶层父级id")
    @DataTableField(name = "topPid", type = "int", defaultValue = "-1")
    private Integer topPid;

    @ApiModelProperty("用户iD")
    @DataTableField(name = "userId", type = "int", isEmpty = false)
    private Integer userId;

    @ApiModelProperty("评论内容")
    @DataTableField(name = "content", length = 2048, type = "varchar", isEmpty = false)
    private String content;

    @ApiModelProperty("状态:0正常,1:待审核")
    @DataTableField(name = "status", type = "int", isEmpty = false)
    private Integer status;

    @ApiModelProperty("头像")
    @DataTableField(name = "avatar", length = 256, type = "varchar")
    private String avatar;

    @ApiModelProperty("网站地址")
    @DataTableField(name = "website", length = 256, type = "varchar")
    private String website;

    @ApiModelProperty("邮箱")
    @DataTableField(name = "email", length = 256, type = "varchar")
    private String email;

    @ApiModelProperty("评论人")
    @DataTableField(name = "userName", length = 64, type = "varchar")
    private String userName;

    @ApiModelProperty("设备类型")
    @DataTableField(name = "device", length = 64, type = "varchar")
    private String device;

    @ApiModelProperty("IP地址")
    @DataTableField(name = "ip", length = 64, type = "varchar")
    private String ip;

    @ApiModelProperty("创建时间")
    @DataTableField(name = "createTime", type = "datetime")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @DataTableField(name = "updateTime", type = "datetime")
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
