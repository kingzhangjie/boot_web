package com.zj.boot_web.common.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring上下文
 *TypesName(类名)：SpringContextBean
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年5月17日上午11:50:32
 *
 */
@Component
public class SpringContextBean implements ApplicationContextAware {
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        context = applicationContext;
    }

    public static <T> T getBean(String name)
    {
        return (T)context.getBean(name);
    }

    public static <T> T getBean(Class<T> beanClass){
        return context.getBean(beanClass);
    }
}