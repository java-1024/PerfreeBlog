package com.perfree.model;

import com.perfree.dataBase.Table;
import com.perfree.dataBase.TableField;

@Table(value = "p_article_tag")
public class ArticleTag {
    @TableField(name = "articleId", type = "int")
    private Integer articleId;
    @TableField(name = "tagId", type = "int")
    private Integer tagId;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
