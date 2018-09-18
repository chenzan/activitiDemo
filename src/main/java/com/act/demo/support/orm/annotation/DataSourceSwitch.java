package com.act.demo.support.orm.annotation;

import com.act.demo.config.DataSourceConfig;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceSwitch {
    Source dataSource() default Source.MASTER;

    enum Source {
        MASTER(DataSourceConfig.MASTER_DATASOURCE_NAME),
        SLAVE(DataSourceConfig.SLAVE_DATASOURCE_NAME);
        String nameType;

        Source(String nameType) {
            this.nameType = nameType;
        }

        public String getNameType() {
            return nameType;
        }
    }
}
