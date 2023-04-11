package com.codewen.service;

import com.codewen.vo.Result;
import com.codewen.vo.params.ArticleParams;
import com.codewen.vo.params.PageParams;

/**
 * @author codewen77
 * @date 2022-08-25
 */
public interface ArticleService {

    /**
     * 根据pageParams查询一页article数据
     * @param pageParams
     * @return
     */
    Result listArticlePage(PageParams pageParams);

    /**
     * 查询最热的前listSize篇文章
     * @param listSize
     * @return
     */
    Result listHot(int listSize);

    /**
     * 查询最新的前listSize篇文章
     * @param listSize
     * @return
     */
    Result listNew(int listSize);

    /**
     * 查询listSize个归档
     * @param listSize
     * @return
     */
    Result listArchives(int listSize);

    /**
     * 查询指定文章的详情
     * @param id
     * @return
     */
    Result queryArticle(Long id);

    /**
     * 发布文章
     * @param articleParams
     * @return
     */
    Result publish(ArticleParams articleParams);
}
