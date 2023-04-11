package com.codewen.common.aop;

import java.lang.annotation.*;

/**
 * @author codewen77
 * @date 2022-09-06
 */
@Target(ElementType.METHOD) // 只用在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheAnnotation {

    long expire() default 1 * 60 * 1000;

    String name() default "";
}
