package com.perfree.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.perfree.dataBase.Table;
import com.perfree.dataBase.TableField;
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
@TableName("p_attach")
@ApiModel(value = "Attach对象", description = "")
@Table(value = "p_attach")
public class Attach implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    @TableField(name = "id", type = "int", isEmpty = false, isPrimary = true, autoIncrement = true)
    private Integer id;

    @ApiModelProperty("附件名")
    @TableField(name = "name", length = 256, type = "varchar")
    private String name;

    @ApiModelProperty("附件描述")
    @TableField(name = "desc", length = 512, type = "varchar")
    private String desc;

    @ApiModelProperty("附件路径")
    @TableField(name = "path", length = 512, type = "varchar")
    private String path;

    @ApiModelProperty("附件后缀")
    @TableField(name = "suffix", length = 32, type = "varchar")
    private String suffix;

    @ApiModelProperty("标识")
    @TableField(name = "flag", length = 256, type = "varchar")
    private String flag;

    @ApiModelProperty("文件类型")
    @TableField(name = "type", length = 32, type = "varchar")
    private String type;

    @ApiModelProperty("存储方式")
    @TableField(name = "saveType", length = 32, type = "varchar")
    private String saveType;

    @ApiModelProperty("fileKey")
    @TableField(name = "fileKey", length = 512, type = "varchar")
    private String fileKey;

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
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }
    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    @Override
    public String toString() {
        return "Attach{" +
            "id=" + id +
            ", name=" + name +
            ", desc=" + desc +
            ", path=" + path +
            ", suffix=" + suffix +
            ", flag=" + flag +
            ", type=" + type +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", saveType=" + saveType +
            ", fileKey=" + fileKey +
        "}";
    }
}
