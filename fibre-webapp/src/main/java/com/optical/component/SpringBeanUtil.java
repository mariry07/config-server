package com.optical.component;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 自己new出的线程不被spring接管，需要使用spring托管的备案的时候，写这么一个工具类从上下文中获取相应bean，
 * 曲线救国一下
 * Created by mary on 2021/2/20.
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware{

    private static ApplicationContext applicationContext = null;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws
            BeansException {
        // TODO Auto-generated method stub
        SpringBeanUtil.applicationContext = applicationContext;
    }

    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        if(name == null || applicationContext == null)
            return null;
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
