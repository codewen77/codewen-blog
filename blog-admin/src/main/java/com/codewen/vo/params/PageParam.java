package com.codewen.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author codewen77
 * @date 2022-09-12
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
