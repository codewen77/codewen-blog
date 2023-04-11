package com.codewen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codewen.mapper.CategoryMapper;
import com.codewen.pojo.Category;
import com.codewen.service.CategoryService;
import com.codewen.vo.CategoryVo;
import com.codewen.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author codewen77
 * @date 2022-08-31
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo queryCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);

        return categoryVo;
    }

    @Override
    public Result listCategories() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId, Category::getCategoryName);
        List<Category> categoryList = categoryMapper.selectList(queryWrapper);

        return Result.success(copyList(categoryList));
    }

    @Override
    public Result listCategoriesDetail() {
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<>());

        return Result.success(copyList(categoryList));
    }

    @Override
    public Result listCategoriesDetailById(Long id) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId, id);

        return Result.success(copy(categoryMapper.selectOne(queryWrapper)));
    }

    private List<CategoryVo> copyList(List<Category> categoryList) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
