package com.codewen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codewen.dos.Archives;
import com.codewen.mapper.ArticleMapper;
import com.codewen.pojo.Article;
import com.codewen.pojo.ArticleBody;
import com.codewen.pojo.ArticleTag;
import com.codewen.pojo.SysUser;
import com.codewen.service.*;
import com.codewen.utils.UserThreadLocal;
import com.codewen.vo.ArchivesVo;
import com.codewen.vo.ArticleVo;
import com.codewen.vo.Result;
import com.codewen.vo.TagVo;
import com.codewen.vo.params.ArticleParams;
import com.codewen.vo.params.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author codewen77
 * @date 2022-08-25
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {


    private static String DESC = "desc";

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleBodyService articleBodyservice;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public Result listArticlePage(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        Page<Article> articlePage = articleMapper.listArticlePage(page, pageParams);
        List<Article> records = articlePage.getRecords();

        return Result.success(copyList(records, true, true, true));
    }

    @Override
    public Result listHot(int listSize) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        // select id, title from ms_article ORDER BY view_counts DESC limit 3
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.last("limit "+listSize);

        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false, false, false));
    }

    @Override
    public Result listNew(int listSize) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        // select id, title from ms_article ORDER BY create_date DESC LIMIT 3
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.last("limit "+listSize);

        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false, false, false));
    }

    @Override
    public Result listArchives(int listSize) {
        List<Archives> archivesList = articleMapper.listArchives(listSize);
        // copy archives
        List<ArchivesVo> archivesVoList = new ArrayList<>();
        for (Archives archive : archivesList) {
            ArchivesVo archivesVo = new ArchivesVo();
            BeanUtils.copyProperties(archive, archivesVo);
            archivesVo.setYear(archive.getYears().toString());
            archivesVo.setMonth(archive.getMonths().toString());
            archivesVoList.add(archivesVo);
        }
        return Result.success(archivesVoList);
    }

    @Override
    public Result queryArticle(Long id) {
        Article article = articleMapper.selectById(id);
        // 完成阅读文章详细内容之后需要更新阅读量
        // 这里的阅读量更新不能影响当前主程序的进行，所以得开启多线程来做阅读量的更新
        // 开启多线程还得注意线程安全问题
        if (article != null) {
            threadService.updateViewCounts(articleMapper, article);
            ArticleVo articleVo = copy(article, true, true, true, true, true);
            return Result.success(articleVo);
        }

        return Result.success(null);

    }

    @Override
    public Result publish(ArticleParams articleParams) {
        /**
         * 我们发布一篇文章所需要的对象有：Article、ArticleBody、ArticleTag
         */
        Article article = new Article();

        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setSummary(articleParams.getSummary());
        article.setTitle(articleParams.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        // 获取作者id (注意这里需要在登录拦截器中添加publish路径，不然这里得到的用户对象为空)
        SysUser sysUser = UserThreadLocal.get();
        article.setAuthorId(sysUser.getId());

        // 创建一个ArticleBody对象，然后增加一条记录到Body表中，插入成功后获取BodyId
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParams.getBody().getContent());
        articleBody.setContentHtml(articleParams.getBody().getContentHtml());
        articleBody.setArticleId(sysUser.getId());
        articleBodyservice.addArticleBody(articleBody);
        article.setBodyId(articleBody.getId());

        // 文章类别ID
        article.setCategoryId(articleParams.getCategory().getId());

        // 插入之后articleId会返回
        articleMapper.insert(article);

        // article_tag表中增加数据
        for (TagVo tag : articleParams.getTags()) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tag.getId());
            articleTagService.addArticleTag(articleTag);
        }

        Map<String, String> map = new HashMap<>(1);
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isCreateDate, boolean isAuthor, boolean isTags) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isCreateDate, isAuthor, isTags));
        }

        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isCreateDate, boolean isAuthor, boolean isTags) {
        ArticleVo articleVo = new ArticleVo();
        // 将article中的属性值拷贝到articleVo
        BeanUtils.copyProperties(article, articleVo);

        // 处理无法拷贝的属性
        // 创建时间
        if (isCreateDate) {
            articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        }

        // 作者
        if (isAuthor) {
            articleVo.setAuthor(sysUserService.findUserById(article.getAuthorId()).getNickname());
        }

        // 标签信息
        if (isTags) {
            articleVo.setTags(tagService.listTagByArticleId(article.getId()));
        }
        return articleVo;
    }

    private ArticleVo copy(Article article, boolean isCreateDate, boolean isAuthor, boolean isTags, boolean isArticleBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        // 将article中的属性值拷贝到articleVo
        BeanUtils.copyProperties(article, articleVo);

        // 处理无法拷贝的属性
        // 创建时间
        if (isCreateDate) {
            articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        }

        // 作者
        if (isAuthor) {
            articleVo.setAuthor(sysUserService.findUserById(article.getAuthorId()).getNickname());
        }

        // 标签信息
        if (isTags) {
            articleVo.setTags(tagService.listTagByArticleId(article.getId()));
        }

        // 文章内容
        if (isArticleBody) {
            articleVo.setBody(articleBodyservice.queryArticleContent(article.getBodyId()));
        }

        // 类别
        if (isCategory) {
            articleVo.setCategory(categoryService.queryCategoryById(article.getCategoryId()));
        }

        return articleVo;
    }
}
