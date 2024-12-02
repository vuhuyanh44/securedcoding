package com.lgcns.hrm.cv.common.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
@Slf4j
public class SpringUtil implements ApplicationContextAware {

    @Nullable
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtil.context = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return context.getBean(clazz);
    }

    public static <T> T getBean(String beanId) {
        if (beanId == null) {
            return null;
        }
        return (T) context.getBean(beanId);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null == beanName || "".equals(beanName.trim())) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        return (T) context.getBean(beanName, clazz);
    }

    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(Class<T> clazz) {
        if (context == null) {
            return null;
        }
        return context.getBeanProvider(clazz);
    }

    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(ResolvableType resolvableType) {
        if (context == null) {
            return null;
        }
        return context.getBeanProvider(resolvableType);
    }

    public static ApplicationContext getContext() {
        if (context == null) {
            return null;
        }
        return context;
    }

    public static void publishEvent(ApplicationEvent event) {
        if (context == null) {
            return;
        }
        try {
            context.publishEvent(event);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
    public static <T> T getCurrentProxy() {
        return (T) AopContext.currentProxy();
    }

}
