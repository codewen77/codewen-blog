package com.codewen.controller;

import com.codewen.service.TagService;
import com.codewen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codewen77
 * @date 2022-08-27
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result listHot() {
        int limit_nums = 3;
        return tagService.listHot(limit_nums);
    }

    @GetMapping
    public Result listTags() {

        return tagService.listTags();
    }

    @GetMapping("/detail")
    public Result listTagsDetail() {

        return tagService.listTagsDetail();
    }

    @GetMapping("/detail/{id}")
    public Result listTagsDetailById(@PathVariable("id") Long id) {

        return tagService.listTagsDetailById(id);
    }
}
