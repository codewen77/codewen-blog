package com.codewen.handler;

import com.codewen.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author codewen77
 * @date 2022-08-30
 */
@RestControllerAdvice
public class AllExceptionHandler {

    /**
     * 进行异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result doExceptionHandle(Exception e) {
        e.printStackTrace();
        return Result.fail(-99, "系统错误!");
    }
}
