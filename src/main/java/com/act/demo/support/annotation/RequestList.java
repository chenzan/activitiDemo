package com.act.demo.support.annotation;

import java.lang.annotation.*;

/**
 * 处理请求参数
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestList {
    String name();

    String separator() default ",";
}
