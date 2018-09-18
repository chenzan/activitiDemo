package com.act.demo.support.orm;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * spring 提供的数据源切换获取
 */
public class RoutingDataSource extends AbstractRoutingDataSource{

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType();
    }
}
