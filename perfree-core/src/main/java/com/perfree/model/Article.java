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
@TableName("p_article")
@ApiModel(value = "Article对象", description = "")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("文章内容类型:html/markdown")
    private String contentModel;

    @ApiModelProperty("解析后的文章内容")
    private String parseContent;

    @ApiModelProperty("类型:article文章,page页面")
    private String type;

    @ApiModelProperty("文章类型: 0默认, 1置顶")
    private Integer articleType;

    @ApiModelProperty("状态0:已发布,1:草稿")
    private Integer status;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("所属分类")
    private Integer categoryId;

    @ApiModelProperty("SEO关键字")
    private String metaKeywords;

    @ApiModelProperty("SEO描述")
    private String metaDescription;

    @ApiModelProperty("缩略图")
    private String thumbnail;

    @ApiModelProperty("slug")
    private String slug;

    @ApiModelProperty("评论数")
    private Long commentCount;

    @ApiModelProperty("访问量")
    private Long viewCount;

    @ApiModelProperty("点赞数量")
    private Long greatCount;

    @ApiModelProperty("创建人")
    private Integer userId;

    @ApiModelProperty("是否允许评论0:否,1是")
    private Integer isComment;

    @ApiModelProperty("标识")
    private String flag;

    @ApiModelProperty("模板")
    private String template;

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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContentModel() {
        return contentModel;
    }

    public void setContentModel(String contentModel) {
        this.contentModel = contentModel;
    }
    public String getParseContent() {
        return parseContent;
    }

    public void setParseContent(String parseContent) {
        this.parseContent = parseContent;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
    public Long getGreatCount() {
        return greatCount;
    }

    public void setGreatCount(Long greatCount) {
        this.greatCount = greatCount;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", title=" + title +
            ", content=" + content +
            ", contentModel=" + contentModel +
            ", parseContent=" + parseContent +
            ", type=" + type +
            ", articleType=" + articleType +
            ", summary=" + summary +
            ", categoryId=" + categoryId +
            ", metaKeywords=" + metaKeywords +
            ", metaDescription=" + metaDescription +
            ", thumbnail=" + thumbnail +
            ", slug=" + slug +
            ", commentCount=" + commentCount +
            ", viewCount=" + viewCount +
            ", greatCount=" + greatCount +
            ", userId=" + userId +
            ", isComment=" + isComment +
            ", flag=" + flag +
            ", template=" + template +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
