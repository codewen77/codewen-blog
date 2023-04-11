package com.codewen.dos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author codewen77
 * @date 2022-08-30
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Archives {

    private Integer years;
    private Integer months;
    private Long count;
}
