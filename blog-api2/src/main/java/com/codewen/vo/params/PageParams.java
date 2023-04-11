package com.codewen.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author codewen77
 * @date 2022-08-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {

    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String sort;

    private String year;

    private String month;
}