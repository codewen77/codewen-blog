package com.codewen.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author codewen77
 * @date 2022-09-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentParams {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
