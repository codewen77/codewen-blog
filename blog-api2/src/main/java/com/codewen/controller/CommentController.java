package com.codewen.controller;

import com.codewen.service.CommentService;
import com.codewen.vo.Result;
import com.codewen.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author codewen77
 * @date 2022-09-01
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{id}")
    public Result listCommentByArticleId(@PathVariable("id") Long id) {

        return commentService.listCommentByArticleId(id);
    }

    @PostMapping("/create/change")
    public Result addComment(@RequestBody CommentParams commentParams) {

        return commentService.addComment(commentParams);
    }
}
