package com.act.demo.support.annotation;


import java.lang.annotation.*;

/**
 * 不需要登录就可以访问的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface IgnoreLogin {

}
