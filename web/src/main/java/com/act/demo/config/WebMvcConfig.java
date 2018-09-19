package com.act.demo.config;

import com.act.demo.support.converter.StringToDateConverter;
import com.act.demo.support.interceptor.SessionInterceptor;
import com.act.demo.support.resolver.ListArgumentResolver;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    SessionInterceptor sessionInterceptor;
    @Autowired
    StringToDateConverter stringToDateConverter;
    @Autowired
    ListArgumentResolver listArgumentResolver;
    @Resource(name = "masterDataSource")
    DataSource masterDataSource;
    @Resource(name = "slaveDataSource")
    DataSource slaveDataSource;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToDateConverter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(listArgumentResolver);
    }

    //根据名称自动创建代理
    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setBeanNames("userService");
        beanNameAutoProxyCreator.setInterceptorNames("userLoginMethodInterceptor");
        return beanNameAutoProxyCreator;
    }

//    /**
//     * 数据源切换版本一
//     *
//     * @return
//     */
//    @Bean(name = "dataSource")
//    @Primary
//    public AbstractRoutingDataSource abstractRoutingDataSource() {
//        RoutingDataSource proxy = new RoutingDataSource();
//        Map<Object, Object> targetDataResources = new HashMap<>();
//        targetDataResources.put(DbContextHolder.DbType.MASTER, masterDataSource);
//        targetDataResources.put(DbContextHolder.DbType.SLAVE, slaveDataSource);
//        proxy.setDefaultTargetDataSource(masterDataSource);//默认源
//        proxy.setTargetDataSources(targetDataResources);
//        proxy.afterPropertiesSet();
//        return proxy;
//    }
}
