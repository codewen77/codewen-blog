package com.codewen.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author codewen77
 * @date 2022-09-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private List<T> list;

    private Long total;
}
