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

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    SessionInterceptor sessionInterceptor;
    @Autowired
    StringToDateConverter stringToDateConverter;
    @Autowired
    ListArgumentResolver listArgumentResolver;

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
}
