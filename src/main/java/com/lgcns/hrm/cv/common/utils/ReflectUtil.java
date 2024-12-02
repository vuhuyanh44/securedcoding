package com.lgcns.hrm.cv.common.utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.core.convert.Property;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
public class ReflectUtil extends ReflectionUtils{

    public static PropertyDescriptor[] getBeanGetters(Class<?> type) {
        return getPropertyDescriptors(type, true, false);
    }

    public static PropertyDescriptor[] getBeanSetters(Class<?> type) {
        return getPropertyDescriptors(type, false, true);
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class type, boolean read, boolean write) {
        try {
            PropertyDescriptor[] all = BeanUtils.getPropertyDescriptors(type);
            if (read && write) {
                return all;
            } else {
                List<PropertyDescriptor> properties = new ArrayList<>(all.length);
                for (PropertyDescriptor pd : all) {
                    if (read && pd.getReadMethod() != null) {
                        properties.add(pd);
                    } else if (write && pd.getWriteMethod() != null) {
                        properties.add(pd);
                    }
                }
                return properties.toArray(new PropertyDescriptor[0]);
            }
        } catch (BeansException ex) {
            throw new CodeGenerationException(ex);
        }
    }

    @Nullable
    public static Property getProperty(Class<?> propertyType, String propertyName) {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(propertyType, propertyName);
        if (propertyDescriptor == null) {
            return null;
        }
        return ReflectUtil.getProperty(propertyType, propertyDescriptor, propertyName);
    }


    public static Property getProperty(Class<?> propertyType, PropertyDescriptor propertyDescriptor, String propertyName) {
        Method readMethod = propertyDescriptor.getReadMethod();
        Method writeMethod = propertyDescriptor.getWriteMethod();
        return new Property(propertyType, readMethod, writeMethod, propertyName);
    }


    @Nullable
    public static TypeDescriptor getTypeDescriptor(Class<?> propertyType, String propertyName) {
        Property property = ReflectUtil.getProperty(propertyType, propertyName);
        if (property == null) {
            return null;
        }
        return new TypeDescriptor(property);
    }

    public static TypeDescriptor getTypeDescriptor(Class<?> propertyType, PropertyDescriptor propertyDescriptor, String propertyName) {
        Method readMethod = propertyDescriptor.getReadMethod();
        Method writeMethod = propertyDescriptor.getWriteMethod();
        Property property = new Property(propertyType, readMethod, writeMethod, propertyName);
        return new TypeDescriptor(property);
    }


    @Nullable
    public static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != Object.class) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }


    @Nullable
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, String fieldName, Class<T> annotationClass) {
        Field field = ReflectUtil.getField(clazz, fieldName);
        if (field == null) {
            return null;
        }
        return field.getAnnotation(annotationClass);
    }


    public static void setField(Field field, @Nullable Object target, @Nullable Object value) {
        makeAccessible(field);
        ReflectionUtils.setField(field, target, value);
    }

    @Nullable
    public static Object getField(Field field, @Nullable Object target) {
        makeAccessible(field);
        return ReflectionUtils.getField(field, target);
    }

    @Nullable
    public static Object getField(String fieldName, @Nullable Object target) {
        if (target == null) {
            return null;
        }
        Class<?> targetClass = target.getClass();
        Field field = getField(targetClass, fieldName);
        if (field == null) {
            throw new IllegalArgumentException(fieldName + " not in" + targetClass);
        }
        return getField(field, target);
    }

    @Nullable
    public static Object invokeMethod(Method method, @Nullable Object target) {
        return ReflectUtil.invokeMethod(method, target, new Object[0]);
    }

    @Nullable
    public static Object invokeMethod(Method method, @Nullable Object target, @Nullable Object... args) {
        makeAccessible(method);
        return ReflectionUtils.invokeMethod(method, target, args);
    }
}
