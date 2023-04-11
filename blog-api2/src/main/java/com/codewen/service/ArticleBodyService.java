package com.codewen.service;

import com.codewen.pojo.ArticleBody;
import com.codewen.vo.ArticleBodyVo;

/**
 * @author codewen77
 * @date 2022-08-31
 */
public interface ArticleBodyService {

    /**
     * 根据bodyId查询文章内容
     * @param bodyId
     * @return
     */
    ArticleBodyVo queryArticleContent(Long bodyId);

    /**
     * 增加一条body记录
     * @param articleBody
     */
    void addArticleBody(ArticleBody articleBody);
}
