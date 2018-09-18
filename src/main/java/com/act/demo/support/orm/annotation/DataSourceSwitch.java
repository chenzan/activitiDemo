package com.act.demo.support.orm.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceSwitch {
    Source name() default Source.MASTER;

    enum Source {
        MASTER,
        SLAVE
    }
}
