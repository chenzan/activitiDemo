package com.act.demo.aop.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String name();

    String content();

    Target target() default Target.ALL;

    Level level() default Level.INFO;

    /**
     * 日志输出位置
     */
    enum Target {
        ALL,
        DB,//数据库
        CONSOLE//控制台
    }

    /**
     * 日志级别
     */
    enum Level {
        DEBUG,
        INFO,
        ERROR
    }
}
