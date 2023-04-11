package com.codewen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codewen.dos.Archives;
import com.codewen.pojo.Article;
import com.codewen.vo.params.PageParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author codewen77
 * @date 2022-08-25
 */
@Component
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询归档
     * @param listSize
     * @return
     */
    List<Archives> listArchives(int listSize);

    /**
     * 查询归档的文章
     * @param pageParams
     * @return
     */
    Page<Article> listArticlePage(Page page, @Param("pageParams") PageParams pageParams);
}
