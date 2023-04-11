package com.codewen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author codewen77
 * @date 2022-09-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Admin implements Serializable {

    private Long id;

    private String username;

    private String password;
}
