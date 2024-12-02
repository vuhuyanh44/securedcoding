package com.lgcns.hrm.cv.common.utils;

import com.lgcns.hrm.cv.common.beans.BaseBeanCopier;
import com.lgcns.hrm.cv.common.beans.BaseBeanMap;
import com.lgcns.hrm.cv.common.beans.BeanDiff;
import com.lgcns.hrm.cv.common.beans.BeanProperty;
import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.convert.HrmConverter;
import com.lgcns.hrm.cv.common.exception.PlatformException;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.*;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class BeanUtil extends org.springframework.beans.BeanUtils {


    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        return (T) instantiateClass(clazz);
    }

    public static <T> T newInstance(String clazzStr) {
        return newInstance(forName(clazzStr));
    }


    public static Class<?> forName(String clazzStr) {
        try {
            return ClassUtils.forName(clazzStr, null);
        } catch (ClassNotFoundException e) {
            throw new PlatformException(ErrorCodes.PROVIDER_NOT_FOUND);
        }
    }

    @Nullable
    public static Object getProperty(@Nullable Object bean, String propertyName) {
        if (bean == null) {
            return null;
        }
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        return beanWrapper.getPropertyValue(propertyName);
    }


    public static void setProperty(Object bean, String propertyName, Object value) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(Objects.requireNonNull(bean, "bean Could not null"));
        beanWrapper.setPropertyValue(propertyName, value);
    }


    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T clone(@Nullable T source) {
        if (source == null) {
            return null;
        }
        return (T) BeanUtil.copy(source, source.getClass());
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T deepClone(@Nullable T source) {
        if (source == null) {
            return null;
        }
        FastByteArrayOutputStream fBos = new FastByteArrayOutputStream(1024);
        try (ObjectOutputStream oos = new ObjectOutputStream(fBos)) {
            oos.writeObject(source);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + source.getClass(), ex);
        }
        try (ObjectInputStream ois = new ObjectInputStream(fBos.getInputStream())) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new IllegalArgumentException("Failed to deserialize object", ex);
        }
    }

    @Nullable
    public static <T> T copy(@Nullable Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        return BeanUtil.copy(source, source.getClass(), clazz);
    }


    @Nullable
    public static <T> T copy(@Nullable Object source, Class<?> sourceClazz, Class<T> targetClazz) {
        if (source == null) {
            return null;
        }
        BaseBeanCopier copier = BaseBeanCopier.create(sourceClazz, targetClazz, false);
        T to = newInstance(targetClazz);
        copier.copy(source, to, null);
        return to;
    }


    public static <T> List<T> copy(@Nullable Collection<?> sourceList, Class<T> targetClazz) {
        return copy(sourceList, (List<T>) null, targetClazz);
    }


    public static <T> List<T> copy(@Nullable Collection<?> sourceList, @Nullable List<T> targetList, Class<T> targetClazz) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        if (targetList == null) {
            targetList = new ArrayList<>(sourceList.size());
        }
        Class<?> sourceClazz = null;
        for (Object source : sourceList) {
            if (source == null) {
                continue;
            }
            if (sourceClazz == null) {
                sourceClazz = source.getClass();
            }
            T bean = BeanUtil.copy(source, sourceClazz, targetClazz);
            targetList.add(bean);
        }
        return targetList;
    }

    public static void copy(@Nullable Object source, @Nullable Object targetBean) {
        if (source == null || targetBean == null) {
            return;
        }
        BaseBeanCopier copier = BaseBeanCopier
                .create(source.getClass(), targetBean.getClass(), false);

        copier.copy(source, targetBean, null);
    }

    public static void copyNonNull(@Nullable Object source, @Nullable Object targetBean) {
        if (source == null || targetBean == null) {
            return;
        }
        BaseBeanCopier copier = BaseBeanCopier
                .create(source.getClass(), targetBean.getClass(), false, true);

        copier.copy(source, targetBean, null);
    }


    @Nullable
    public static <T> T copyWithConvert(@Nullable Object source, Class<T> targetClazz) {
        if (source == null) {
            return null;
        }
        return BeanUtil.copyWithConvert(source, source.getClass(), targetClazz);
    }


    @Nullable
    public static <T> T copyWithConvert(@Nullable Object source, Class<?> sourceClazz, Class<T> targetClazz) {
        if (source == null) {
            return null;
        }
        BaseBeanCopier copier = BaseBeanCopier.create(sourceClazz, targetClazz, true);
        T to = newInstance(targetClazz);
        copier.copy(source, to, new HrmConverter(sourceClazz, targetClazz));
        return to;
    }


    public static <T> List<T> copyWithConvert(@Nullable Collection<?> sourceList, Class<T> targetClazz) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> outList = new ArrayList<>(sourceList.size());
        Class<?> sourceClazz = null;
        for (Object source : sourceList) {
            if (source == null) {
                continue;
            }
            if (sourceClazz == null) {
                sourceClazz = source.getClass();
            }
            T bean = BeanUtil.copyWithConvert(source, sourceClazz, targetClazz);
            outList.add(bean);
        }
        return outList;
    }


    @Nullable
    public static <T> T copyProperties(@Nullable Object source, Class<T> targetClazz) throws BeansException {
        if (source == null) {
            return null;
        }
        T to = newInstance(targetClazz);
        BeanUtils.copyProperties(source, to);
        return to;
    }


    public static <T> List<T> copyProperties(@Nullable Collection<?> sourceList, Class<T> targetClazz) throws BeansException {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> outList = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            if (source == null) {
                continue;
            }
            T bean = BeanUtil.copyProperties(source, targetClazz);
            outList.add(bean);
        }
        return outList;
    }


    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(@Nullable Object bean) {
        if (bean == null) {
            return new HashMap<>(0);
        }
        return BaseBeanMap.create(bean);
    }

    public static Map<String, Object> toNewMap(@Nullable Object bean) {
        return new HashMap<>(toMap(bean));
    }

    public static <T> T toBean(Map<String, Object> beanMap, Class<T> valueType) {
        Objects.requireNonNull(beanMap, "beanMap Could not null");
        T to = newInstance(valueType);
        if (beanMap.isEmpty()) {
            return to;
        }
        BeanUtil.copy(beanMap, to);
        return to;
    }

    @Nullable
    public static Object generator(@Nullable Object superBean, BeanProperty... props) {
        if (superBean == null) {
            return null;
        }
        Class<?> superclass = superBean.getClass();
        Object genBean = generator(superclass, props);
        BeanUtil.copy(superBean, genBean);
        return genBean;
    }


    public static Object generator(Class<?> superclass, BeanProperty... props) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(superclass);
        generator.setUseCache(true);
        generator.setContextClass(superclass);
        for (BeanProperty prop : props) {
            generator.addProperty(prop.name(), prop.type());
        }
        return generator.create();
    }

    public static BeanDiff diff(final Object src, final Object dist) {
        Assert.notNull(src, "diff Object src is null.");
        Assert.notNull(src, "diff Object dist is null.");
        return diff(BeanUtil.toMap(src), BeanUtil.toMap(dist));
    }


    public static BeanDiff diff(final Map<String, Object> src, final Map<String, Object> dist) {
        Assert.notNull(src, "diff Map src is null.");
        Assert.notNull(src, "diff Map dist is null.");
        Map<String, Object> difference = new HashMap<>(8);
        difference.putAll(src);
        difference.putAll(dist);
        difference.entrySet().removeAll(src.entrySet());
        Map<String, Object> oldValues = new HashMap<>(8);
        difference.keySet().forEach((k) -> oldValues.put(k, src.get(k)));
        BeanDiff diff = new BeanDiff();
        diff.getFields().addAll(difference.keySet());
        diff.getOldValues().putAll(oldValues);
        diff.getNewValues().putAll(difference);
        return diff;
    }
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    // then use Spring BeanUtils to copy and ignore null using our function
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static Class<?> getTargetClass(Object instance) {
        return (instance instanceof Class) ? (Class<?>) instance : AopProxyUtils.ultimateTargetClass(instance);
    }
}
