package com.act.demo._holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * bean获取辅助类
 * 由于在构造函数中使用了
 * 所以要优先加载
 */
@Component
public class SpringApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContextHelper.applicationContext = applicationContext;
    }

    /**
     * 获取实例
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取实例
     *
     * @param beanName
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> tClass) {
        return applicationContext.getBean(beanName, tClass);
    }
}
