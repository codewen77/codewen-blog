package com.codewen.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.codewen.mapper.ArticleMapper;
import com.codewen.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author codewen77
 * @date 2022-09-01
 */
@Component
@Slf4j
public class ThreadService {

    @Async("taskExecutor")
    public void updateViewCounts(ArticleMapper articleMapper, Article article) {
        // 保证线程安全
        // update ms_article set view_counts=100 where id=1 and view_counts=99
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Article::getViewCounts, article.getViewCounts()+1);
        updateWrapper.eq(Article::getId, article.getId());
        updateWrapper.eq(Article::getViewCounts, article.getViewCounts());

        articleMapper.update(null, updateWrapper);

        // 模型开启多线程不影响主线程的正常使用
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("viewCounts更新完毕~");
    }
}
