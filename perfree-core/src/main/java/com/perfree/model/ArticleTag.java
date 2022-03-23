package com.perfree.model;

import com.perfree.dataBase.DataTable;
import com.perfree.dataBase.DataTableField;

@DataTable(value = "p_article_tag")
public class ArticleTag {
    @DataTableField(name = "articleId", type = "int")
    private Integer articleId;
    @DataTableField(name = "tagId", type = "int")
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
