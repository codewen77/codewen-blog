package com.codewen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author codewen77
 * @date 2022-08-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class ArticleBody implements Serializable {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;
}
