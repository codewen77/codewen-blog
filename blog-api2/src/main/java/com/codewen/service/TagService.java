package com.codewen.service;

import com.codewen.vo.Result;
import com.codewen.vo.TagVo;

import java.util.List;

/**
 * @author codewen77
 * @date 2022-08-25
 */
public interface TagService {

    /**
     * 根据articleId查询tag
     * @param id
     * @return
     */
    List<TagVo> listTagByArticleId(Long id);

    /**
     * 查询最热前4的标签
     * @param limit_nums
     * @return
     */
    Result listHot(int limit_nums);

    /**
     * 查询所有的tag
     * @return
     */
    Result listTags();

    /**
     * 查询所有的tag详情
     * @return
     */
    Result listTagsDetail();

    /**
     * 根据id查询所有的tag详情
     * @param id
     * @return
     */
    Result listTagsDetailById(Long id);
}
