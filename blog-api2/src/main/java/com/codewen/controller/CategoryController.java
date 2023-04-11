package com.codewen.controller;

import com.codewen.service.CategoryService;
import com.codewen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codewen77
 * @date 2022-09-05
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result listCategories() {

        return categoryService.listCategories();
    }

    @GetMapping("/detail")
    public Result listCategoriesDetail() {

        return categoryService.listCategoriesDetail();
    }

    @GetMapping("/detail/{id}")
    public Result listCategoriesDetailById(@PathVariable("id") Long id) {

        return categoryService.listCategoriesDetailById(id);
    }
}
