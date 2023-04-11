package com.codewen.service;

import com.codewen.pojo.ArticleTag;
import com.codewen.pojo.Tag;

import java.util.List;

/**
 * @author codewen77
 * @date 2022-09-05
 */
public interface ArticleTagService {

    /**
     * article_tag表中插入数据
     * @param articleTag
     */
    void addArticleTag(ArticleTag articleTag);

    /**
     * 根据tagId查询
     * @param tagId
     * @return
     */
    List<ArticleTag> queryByTagId(Long tagId);
}
