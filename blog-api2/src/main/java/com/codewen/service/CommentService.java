package com.codewen.service;

import com.codewen.vo.Result;
import com.codewen.vo.params.CommentParams;

/**
 * @author codewen77
 * @date 2022-09-01
 */
public interface CommentService {

    /**
     * 根据文章id查询所有评论
     * @param id
     * @return
     */
    Result listCommentByArticleId(Long id);

    /**
     * 增加一条评论
     * @param commentParams
     * @return
     */
    Result addComment(CommentParams commentParams);
}
