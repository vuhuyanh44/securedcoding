package com.lgcns.hrm.cv.common.convert;

import com.lgcns.hrm.cv.common.function.CheckedFunction;
import com.lgcns.hrm.cv.common.utils.CollectionUtil;
import com.lgcns.hrm.cv.common.utils.ConvertUtil;
import com.lgcns.hrm.cv.common.utils.ReflectUtil;
import com.lgcns.hrm.cv.common.utils.Unchecked;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import org.springframework.core.convert.TypeDescriptor;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@AllArgsConstructor
public class HrmConverter implements Converter {
    private static final ConcurrentMap<String, TypeDescriptor> TYPE_CACHE = new ConcurrentHashMap<>();
    private final Class<?> sourceClazz;
    private final Class<?> targetClazz;

    @Override
    @Nullable
    public Object convert(@Nullable Object value, Class target, final Object fieldName) {
        if (value == null) {
            return null;
        }

        if (ClassUtils.isAssignableValue(target, value)) {
            return value;
        }
        try {
            TypeDescriptor targetDescriptor = HrmConverter.getTypeDescriptor(targetClazz, (String) fieldName);
            if (Map.class.isAssignableFrom(sourceClazz)) {
                return ConvertUtil.convert(value, targetDescriptor);
            } else {
                TypeDescriptor sourceDescriptor = HrmConverter.getTypeDescriptor(sourceClazz, (String) fieldName);
                return ConvertUtil.convert(value, sourceDescriptor, targetDescriptor);
            }
        } catch (Throwable e) {
            log.warn("HrmConverter error", e);
            return null;
        }
    }

    private static TypeDescriptor getTypeDescriptor(final Class<?> clazz, final String fieldName) {
        String srcCacheKey = clazz.getName() + fieldName;

        CheckedFunction<String, TypeDescriptor> uncheckedFunction = (key) -> {
            Field field = ReflectUtil.getField(clazz, fieldName);
            if (field == null) {
                throw new NoSuchFieldException(fieldName);
            }
            return new TypeDescriptor(field);
        };
        return CollectionUtil.computeIfAbsent(TYPE_CACHE, srcCacheKey, Unchecked.function(uncheckedFunction));
    }
}
