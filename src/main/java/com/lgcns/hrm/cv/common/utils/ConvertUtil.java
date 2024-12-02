package com.lgcns.hrm.cv.common.utils;

import com.lgcns.hrm.cv.common.convert.HrmConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

@SuppressWarnings("unchecked")
public class ConvertUtil {

    @Nullable
    public static <T> T convert(@Nullable Object source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        if (ClassUtils.isAssignableValue(targetType, source)) {
            return (T) source;
        }
        GenericConversionService conversionService = HrmConversionService.getInstance();
        return conversionService.convert(source, targetType);
    }

    @Nullable
    public static <T> T convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        GenericConversionService conversionService = HrmConversionService.getInstance();
        return (T) conversionService.convert(source, sourceType, targetType);
    }

    @Nullable
    public static <T> T convert(@Nullable Object source, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        GenericConversionService conversionService = HrmConversionService.getInstance();
        return (T) conversionService.convert(source, targetType);
    }

}

