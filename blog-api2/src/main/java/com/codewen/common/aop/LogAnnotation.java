package com.codewen.common.aop;

import java.lang.annotation.*;

/**
 * @author codewen77
 * @date 2022-09-05
 */
@Target(ElementType.METHOD) // 只用在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";
}
