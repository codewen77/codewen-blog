package com.codewen.service;

import com.codewen.vo.CategoryVo;
import com.codewen.vo.Result;

/**
 * @author codewen77
 * @date 2022-08-31
 */
public interface CategoryService {

    /**
     * 根据categoryId查询类别具体内容
     * @param categoryId
     * @return
     */
    CategoryVo queryCategoryById(Long categoryId);

    /**
     * 查询所有的category
     * @return
     */
    Result listCategories();

    /**
     * 查询所有的category详情
     * @return
     */
    Result listCategoriesDetail();

    /**
     * 根据id查询所有的category详情
     * @param id
     * @return
     */
    Result listCategoriesDetailById(Long id);
}
