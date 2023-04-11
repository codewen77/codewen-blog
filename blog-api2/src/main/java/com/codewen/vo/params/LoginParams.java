package com.codewen.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author codewen77
 * @date 2022-08-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginParams {

    private String account;

    private String password;

    private String nickname;
}
