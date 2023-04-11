package com.codewen.controller;

import com.codewen.common.aop.LogAnnotation;
import com.codewen.service.ArticleService;
import com.codewen.vo.Result;
import com.codewen.vo.params.ArticleParams;
import com.codewen.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author codewen77
 * @date 2022-08-25
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询一页article数据
     * @param pageParams page和pageSize
     * @return
     */
    @PostMapping
    @LogAnnotation(module="article", operation="查询一页文章")
    public Result listArticlePage(@RequestBody PageParams pageParams) {

        return articleService.listArticlePage(pageParams);
    }

    /**
     * 查询最热的前四篇文章
     * @return
     */
    @PostMapping("/hot")
    public Result listHot() {

        int listSize = 4;
        return articleService.listHot(listSize);
    }

    /**
     * 查询最新的前五篇文章
     * @return
     */
    @PostMapping("/new")
    public Result listNew() {
        int listSize = 5;
        return articleService.listNew(listSize);
    }

    /**
     * 查询归档日期前四的文章
     */
    @PostMapping("/listArchives")
    public Result listArchives() {
        int listSize = 4;
        return articleService.listArchives(listSize);
    }

    /**
     * 查询指定文章的具体内容
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public Result queryArticle(@PathVariable("id") Long id) {

        return articleService.queryArticle(id);
    }

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParams articleParams) {

        return articleService.publish(articleParams);
    }

}
