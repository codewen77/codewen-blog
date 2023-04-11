package com.codewen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codewen.mapper.ArticleTagMapper;
import com.codewen.pojo.ArticleTag;
import com.codewen.pojo.Tag;
import com.codewen.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codewen77
 * @date 2022-09-05
 */
@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public void addArticleTag(ArticleTag articleTag) {

        articleTagMapper.insert(articleTag);
    }

    @Override
    public List<ArticleTag> queryByTagId(Long tagId) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        // 根据tagId来查询所有的article内容
        queryWrapper.eq(ArticleTag::getTagId, tagId);

        return articleTagMapper.selectList(queryWrapper);
    }
}
