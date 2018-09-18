package com.act.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DataSourceConfig {
    @Value("${spring.datasource.master.type}")
    Class<? extends DataSource> masterDataSourceType;
    @Value("${spring.datasource.slave.type}")
    Class<? extends DataSource> slaveDataSourceType;

    public static final String MASTER_DATASOURCE_NAME = "masterDataSource";
    public static final String SLAVE_DATASOURCE_NAME = "slaveDataSource";

    @Bean(name = MASTER_DATASOURCE_NAME)
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(masterDataSourceType).build();
    }

    @Bean(name = SLAVE_DATASOURCE_NAME)
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().type(slaveDataSourceType).build();
    }
}
