package com.codewen.vo.params;

import com.codewen.vo.ArticleBodyVo;
import com.codewen.vo.CategoryVo;
import com.codewen.vo.TagVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author codewen77
 * @date 2022-09-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleParams {

    private Long id;

    private String title;

    private String summary;

    private ArticleBodyVo body;

    private CategoryVo category;

    private List<TagVo> tags;
}
