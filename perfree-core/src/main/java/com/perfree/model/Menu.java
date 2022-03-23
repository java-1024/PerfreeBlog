package com.perfree.model;

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
@TableName("p_menu")
@ApiModel(value = "Menu对象", description = "")
@DataTable(value = "p_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @DataTableField(name = "id", type = "varchar", length = 64,isEmpty = false, isPrimary = true)
    private String id;

    @ApiModelProperty("父级id")
    @DataTableField(name = "pid", type = "varchar", length = 64)
    private String pid;

    @ApiModelProperty("菜单名")
    @DataTableField(name = "name", type = "varchar", length = 128)
    private String name;

    @ApiModelProperty("菜单链接")
    @DataTableField(name = "url", type = "varchar", length = 128)
    private String url;

    @ApiModelProperty("菜单图标")
    @DataTableField(name = "icon", type = "varchar", length = 64)
    private String icon;

    @ApiModelProperty("排序序号")
    @DataTableField(name = "seq", type = "int", defaultValue = "0")
    private Integer seq;

    @ApiModelProperty("菜单类型0:前台,1:后台")
    @DataTableField(name = "type", type = "int", defaultValue = "0")
    private Integer type;

    @ApiModelProperty("菜单打开方式:0本页,1:新窗口")
    @DataTableField(name = "target", type = "int", defaultValue = "0")
    private Integer target;

    @ApiModelProperty("菜单状态0:启用,1禁用")
    @DataTableField(name = "status", type = "int", defaultValue = "0")
    private Integer status;

    @ApiModelProperty("创建时间")
    @DataTableField(name = "createTime", type = "datetime")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @DataTableField(name = "updateTime", type = "datetime")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
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
        return "Menu{" +
            "id=" + id +
            ", pid=" + pid +
            ", name=" + name +
            ", url=" + url +
            ", icon=" + icon +
            ", seq=" + seq +
            ", type=" + type +
            ", target=" + target +
            ", status=" + status +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
