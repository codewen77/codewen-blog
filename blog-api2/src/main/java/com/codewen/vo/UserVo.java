package com.codewen.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class UserVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String nickname;

    private String avatar;
}
