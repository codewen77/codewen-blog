package com.codewen.service.impl;

import com.codewen.mapper.ArticleBodyMapper;
import com.codewen.pojo.ArticleBody;
import com.codewen.service.ArticleBodyService;
import com.codewen.vo.ArticleBodyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author codewen77
 * @date 2022-08-31
 */
@Service
public class ArticleBodyServiceImpl implements ArticleBodyService {

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBodyVo queryArticleContent(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody, articleBodyVo);
        return articleBodyVo;
    }

    @Override
    public void addArticleBody(ArticleBody articleBody) {
        articleBodyMapper.insert(articleBody);
    }
}
