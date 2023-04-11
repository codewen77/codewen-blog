package com.codewen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codewen.mapper.CommentMapper;
import com.codewen.pojo.Comment;
import com.codewen.pojo.SysUser;
import com.codewen.service.CommentService;
import com.codewen.service.SysUserService;
import com.codewen.utils.UserThreadLocal;
import com.codewen.vo.CommentVo;
import com.codewen.vo.Result;
import com.codewen.vo.UserVo;
import com.codewen.vo.params.CommentParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author codewen77
 * @date 2022-09-01
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result listCommentByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.eq(Comment::getLevel, 1);

        List<Comment> comments = commentMapper.selectList(queryWrapper);

        return Result.success(copyList(comments));
    }

    @Override
    public Result addComment(CommentParams commentParams) {
        Comment comment = new Comment();
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        comment.setArticleId(commentParams.getArticleId());
        comment.setAuthorId(UserThreadLocal.get().getId());

        Long parent = commentParams.getParent();
        comment.setParentId(parent == null ? 0:parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0:toUserId);
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else{
            comment.setLevel(2);
        }

        commentMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);

        // 处理无法拷贝的属性
        // 时间
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        // 评论用户
        // 通过先查询一级评论之后进行遍历 然后对每条评论查询其子评论，如果子评论还有子子评论，继续递归。
        SysUser sysUser = sysUserService.findUserById(comment.getAuthorId());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser, userVo);
        commentVo.setAuthor(userVo);
        // 被评论用户
        if (comment.getLevel() > 1) {
            SysUser sysUser2 = sysUserService.findUserById(comment.getToUid());
            UserVo userVo2 = new UserVo();
            BeanUtils.copyProperties(sysUser2, userVo2);
            commentVo.setToUser(userVo2);
        } else {
            commentVo.setToUser(new UserVo(0L, "", ""));
        }
        // 子评论(注意传入的ID是当前评论的ID而不是父评论的ID)
        List<CommentVo> childrenCommentVoList = listCommentByParentId(comment.getId());
        commentVo.setChildrens(childrenCommentVoList);

        return commentVo;
    }

    private List<CommentVo> listCommentByParentId(Long parentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, parentId);
        queryWrapper.eq(Comment::getLevel, 2);

        List<Comment> comments = commentMapper.selectList(queryWrapper);

        return copyList(comments);
    }
}
