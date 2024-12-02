package com.lgcns.hrm.cv.common.utils;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.exception.PlatformException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Lazy(false)
public class ContextUtil implements ApplicationContextAware, ApplicationListener<ApplicationReadyEvent> {

    /***
     * ApplicationContext context
     */
    private static ApplicationContext APPLICATION_CONTEXT = null;

    /**
     * Database type
     */
    private static String DATABASE_TYPE = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
        log.debug("ApplicationContext has been assigned: {}", APPLICATION_CONTEXT.getDisplayName());
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        APPLICATION_CONTEXT = event.getApplicationContext();
        log.debug("ApplicationContext has been injected: {}", APPLICATION_CONTEXT.getDisplayName());
    }

    /***
     * Get the ApplicationContext context
     */
    public static ApplicationContext getApplicationContext() {
        if (APPLICATION_CONTEXT == null) {
            log.debug("ApplicationContext is not initialized, get it through ContextLoader!");
            APPLICATION_CONTEXT = ContextLoader.getCurrentWebApplicationContext();
        }
        if (APPLICATION_CONTEXT == null) {
            throw new PlatformException("Unable to obtain ApplicationContext, please ensure that the ComponentScan scanning path includes the com.diboot package path, and call the interface after Spring initialization!");
        }
        return APPLICATION_CONTEXT;
    }

    /***
     * Get the Bean instance according to the beanId
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId) {
        return getApplicationContext().containsBean(beanId) ? getApplicationContext().getBean(beanId) : null;
    }

    /***
     * Get a single Bean instance of the specified type
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApplicationContext().getBean(clazz);
        } catch (Exception e) {
            log.debug("instance not found: {}", clazz.getSimpleName());
            return null;
        }
    }

    /***
     * Get all implementation classes of the specified type
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeans(Class<T> type) {
        Map<String, T> map = getApplicationContext().getBeansOfType(type);
        if (CollectionUtil.isEmpty(map)) {
            return null;
        }
        List<T> beanList = new ArrayList<>(map.size());
        beanList.addAll(map.values());
        return beanList;
    }

    /***
     * Get beans according to annotations
     * @param annotationType
     * @return
     */
    public static List<Object> getBeansByAnnotation(Class<? extends Annotation> annotationType) {
        Map<String, Object> map = getApplicationContext().getBeansWithAnnotation(annotationType);
        if (CollectionUtil.isEmpty(map)) {
            return null;
        }
        List<Object> beanList = new ArrayList<>();
        beanList.addAll(map.values());
        return beanList;
    }

}

