package com.codewen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author codewen77
 * @date 2022-09-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class ArticleTag implements Serializable {

    private Long id;

    private Long articleId;

    private Long tagId;
}
